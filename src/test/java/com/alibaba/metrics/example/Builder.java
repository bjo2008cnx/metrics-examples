package com.alibaba.metrics.example;

import com.alibaba.metrics.Clock;
import com.alibaba.metrics.IMetricManager;
import com.alibaba.metrics.MetricFilter;
import com.alibaba.metrics.common.CollectLevel;
import com.alibaba.metrics.common.config.MetricsCollectPeriodConfig;
import com.alibaba.metrics.reporter.file.MetricFormat;
import com.alibaba.metrics.reporter.file.SimpleTextMetricFormat;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Builder {

  private final IMetricManager metricManager;
  private Clock clock;
  private String prefix;
  private TimeUnit rateUnit;
  private TimeUnit durationUnit;
  private MetricFilter filter;
  private MetricsCollectPeriodConfig metricsReportPeriodConfig;
  private Map<String, String> globalTags;
  private MetricFormat metricFormat;

  private CollectLevel collectLevel;
  // 提交到服务器的时间戳的单位，只支持毫秒和秒，默认是秒
  private TimeUnit timestampPrecision = TimeUnit.SECONDS;

  public Builder(IMetricManager metricManager) {
    this.metricManager = metricManager;
    this.clock = Clock.defaultClock();
    this.prefix = null;
    this.rateUnit = TimeUnit.SECONDS;
    this.durationUnit = TimeUnit.MILLISECONDS;
    this.filter = MetricFilter.ALL;
    this.metricFormat = new SimpleTextMetricFormat();
    this.collectLevel = CollectLevel.COMPACT;
  }

  public Builder withClock(Clock clock) {
    this.clock = clock;
    return this;
  }

  public Builder prefixedWith(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public Builder convertRatesTo(TimeUnit rateUnit) {
    this.rateUnit = rateUnit;
    return this;
  }

  public Builder convertDurationsTo(TimeUnit durationUnit) {
    this.durationUnit = durationUnit;
    return this;
  }

  public Builder filter(MetricFilter filter) {
    this.filter = filter;
    return this;
  }

  public Builder metricsReportPeriodConfig(MetricsCollectPeriodConfig metricsReportPeriodConfig) {
    this.metricsReportPeriodConfig = metricsReportPeriodConfig;
    return this;
  }

  public Builder withGlobalTags(Map<String, String> globalTags) {
    this.globalTags = globalTags;
    return this;
  }

  public Builder withCollectLevel(CollectLevel level) {
    this.collectLevel = collectLevel;
    return this;
  }

  public Builder metricFormat(MetricFormat metricFormat) {
    this.metricFormat = metricFormat;
    return this;
  }

  public Builder timestampPrecision(TimeUnit timestampPrecision) {
    if (TimeUnit.SECONDS.equals(timestampPrecision) || TimeUnit.MILLISECONDS.equals(timestampPrecision)) {
      this.timestampPrecision = timestampPrecision;
      return this;
    } else {
      throw new IllegalArgumentException(
          "timestampPrecision must be TimeUnit.SECONDS or TimeUnit.MILLISECONDS!, do not support: "
              + timestampPrecision);
    }
  }

  public TestReporter build() {
    if (globalTags == null) {
      globalTags = Collections.emptyMap();
    }

    return new TestReporter(metricManager,
        clock, rateUnit, timestampPrecision, durationUnit, filter,
        metricsReportPeriodConfig, globalTags, metricFormat, collectLevel);
  }
}