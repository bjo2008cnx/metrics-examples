package com.alibaba.metrics.example;

import com.alibaba.metrics.MetricLevel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FastCompassMetricTest {

  private static FastCompassMetric metric = new FastCompassMetric("order_save", MetricLevel.MAJOR, null);
  private static ExecutorService pool = Executors.newFixedThreadPool(10);

  public void execute() {
    long startTime = System.currentTimeMillis();
    boolean isSuccess;
    try {
      TimeUnit.MICROSECONDS.sleep(500);
    } catch (Exception e) {
      //.......
    } finally {
      long endTime = System.currentTimeMillis();
      //模拟成功率
      isSuccess = startTime % 10 == 0 ? false : true;
      metric.record(endTime - startTime, isSuccess);
    }
  }

  public static void main(String[] args) {
    FastCompassMetricTest serviceWrapped = new FastCompassMetricTest();
    while (true) {
      pool.submit(() -> serviceWrapped.execute());
    }
  }
}
