alibaba metrics 示例。

运行FastCompassMetricTest,部分输出如下：

MetricObject->metric: order_save.success_bucket_count,value: 2822,timestamp: 1588820405000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.bucket_count,value: 3133,timestamp: 1588820405000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.bucket_sum,value: 59643,timestamp: 1588820405000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.qps,value: 626.6,timestamp: 1588820405000,type: GAUGE,tags: {},level: MAJOR
MetricObject->metric: order_save.rt,value: 19.037025215448452,timestamp: 1588820405000,type: GAUGE,tags: {},level: MAJOR
MetricObject->metric: order_save.success_rate,value: 0.9007341206511331,timestamp: 1588820405000,type: GAUGE,tags: {},level: MAJOR

MetricObject->metric: order_save.fail_bucket_count,value: 0,timestamp: 1588820430000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.success_bucket_count,value: 0,timestamp: 1588820430000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.bucket_count,value: 0,timestamp: 1588820430000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.bucket_sum,value: 0,timestamp: 1588820430000,type: DELTA,tags: {},level: MAJOR
MetricObject->metric: order_save.qps,value: 0.0,timestamp: 1588820430000,type: GAUGE,tags: {},level: MAJOR
MetricObject->metric: order_save.rt,value: 0.0,timestamp: 1588820430000,type: GAUGE,tags: {},level: MAJOR
MetricObject->metric: order_save.success_rate,value: 0.0,timestamp: 1588820430000,type: GAUGE,tags: {},level: MAJOR

这里出现很多零值，是否使用姿势不对？待解决.......