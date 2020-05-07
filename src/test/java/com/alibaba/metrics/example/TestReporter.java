/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.metrics.example;

import com.alibaba.metrics.Clock;
import com.alibaba.metrics.ClusterHistogram;
import com.alibaba.metrics.Compass;
import com.alibaba.metrics.Counter;
import com.alibaba.metrics.FastCompass;
import com.alibaba.metrics.Gauge;
import com.alibaba.metrics.Histogram;
import com.alibaba.metrics.IMetricManager;
import com.alibaba.metrics.Meter;
import com.alibaba.metrics.MetricFilter;
import com.alibaba.metrics.MetricName;
import com.alibaba.metrics.Timer;
import com.alibaba.metrics.common.CollectLevel;
import com.alibaba.metrics.common.MetricObject;
import com.alibaba.metrics.common.MetricsCollector;
import com.alibaba.metrics.common.MetricsCollectorFactory;
import com.alibaba.metrics.common.config.MetricsCollectPeriodConfig;
import com.alibaba.metrics.reporter.MetricManagerReporter;
import com.alibaba.metrics.reporter.file.MetricFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class TestReporter extends MetricManagerReporter {

  private static final String INIT_FLAG = "com.alibaba.metrics.file_reporter.init_flag";
  private final Clock clock;
  private final Map<String, String> globalTags;
  private final TimeUnit timestampPrecision;
  private final MetricFormat metricFormat;
  /**
   * 控制metrics落盘时需要收集的指标内容
   */
  private CollectLevel collectLevel;

  protected TestReporter(IMetricManager metricManager, Clock clock,
      TimeUnit rateUnit, TimeUnit timestampPrecision, TimeUnit durationUnit, MetricFilter filter,
      MetricsCollectPeriodConfig metricsReportPeriodConfig, Map<String, String> globalTags,
      MetricFormat metricFormat, CollectLevel collectLevel) {
    super(metricManager, "test-reporter", filter, metricsReportPeriodConfig, rateUnit, durationUnit);
    this.clock = clock;
    this.globalTags = globalTags;
    this.timestampPrecision = timestampPrecision;
    this.metricFormat = metricFormat;
    this.collectLevel = collectLevel;
  }

  public static Builder forMetricManager(IMetricManager metricManager) {
    return new Builder(metricManager);
  }

  @Override
  public void start(long period, TimeUnit unit) {
    String initFlag = System.getProperty(INIT_FLAG);
    if ("false".equals(initFlag)) {
      return;
    }

    if (initFlag == null) {
      System.setProperty(INIT_FLAG, "true");
      super.start(period, unit);
    }
  }

  @Override
  public void report(Map<MetricName, Gauge> gauges, Map<MetricName, Counter> counters,
      Map<MetricName, Histogram> histograms, Map<MetricName, Meter> meters,
      Map<MetricName, Timer> timers, Map<MetricName, Compass> compasses, Map<MetricName, FastCompass> fastCompasses,
      Map<MetricName, ClusterHistogram> clusterHistogrames) {

    long timestamp = clock.getTime();

    if (TimeUnit.MICROSECONDS.equals(timestampPrecision)) {
      timestamp = timestamp / 1000;
    }

    MetricsCollector collector = MetricsCollectorFactory.createNew(collectLevel, globalTags, rateFactor, durationFactor);

    if (fastCompasses.size() > 0) {
      for (Entry<MetricName, FastCompass> entry : fastCompasses.entrySet()) {
        collector.collect(entry.getKey(), entry.getValue(), timestamp);
      }
      write(collector.build());
      collector.clear();
    }
  }

  public static void write(List<MetricObject> metricObjects) {
    for (MetricObject metricObject : metricObjects) {
      System.out.println(metricObject.toString());
    }
  }
}
