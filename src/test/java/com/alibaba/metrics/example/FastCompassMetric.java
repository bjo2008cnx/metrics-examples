package com.alibaba.metrics.example;

import com.alibaba.metrics.FastCompass;
import com.alibaba.metrics.MetricLevel;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.MetricName;
import java.util.Map;

public class FastCompassMetric {

  private FastCompass metric;

  static {
    TestStarter.start();
  }

  /**
   * 构造器:注册性能指标
   *
   * @param metricName 指标名称
   * @param queryTags 在查询条件中需要使用的tag,例如：app_name
   */
  public FastCompassMetric(String metricName, MetricLevel metricLevel, Map<String, String> queryTags) {
    MetricName name =  MetricName.build(metricName).level(metricLevel);
    this.metric = MetricManager.getFastCompass( "test_app", name);
  }

  public void record(long duration, boolean isSuccess) {
    try {
      metric.record(duration, isSuccess ? "success" : "fail");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

