package com.alibaba.metrics.example;

import com.alibaba.metrics.IMetricManager;
import com.alibaba.metrics.MetricLevel;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.common.config.MetricsCollectPeriodConfig;
import java.util.concurrent.TimeUnit;

public class TestStarter {

  private static IMetricManager metricManager = MetricManager.getIMetricManager();

  private static TestReporter reporter = null;

  public static TestReporter start() {

    if (reporter == null) {
      synchronized (TestReporter.class) {
        if (reporter == null) {
          // 设置全局的report时间间隔
          MetricsCollectPeriodConfig config = new MetricsCollectPeriodConfig(3);

          // 设置CRITICAL的report时间间隔为1秒
          config.configPeriod(MetricLevel.CRITICAL, 1);

          //启动自定义reporter
           reporter = TestReporter.forMetricManager(metricManager)
              .timestampPrecision(TimeUnit.MILLISECONDS)
              .metricsReportPeriodConfig(config)
              .build();
          reporter.start(1, TimeUnit.SECONDS);
        }
      }
    }
    return reporter;
  }
}
