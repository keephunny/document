flink流处理
是一个分布式处理引擎，用于是对无界和有界数据流进行状态计算。

* 高吞吐，低延迟
* 结果的准确性
* 精确一次(exactly-once)的状态一致性保证
* 可以与众多常用存储系统连接
* 高可用，支持动态扩展

### 分层API
* 越顶层越抽象，表达含义越简明，使用越方便
* 越底层越具体，表达能力越丰富，使用越灵活
 * SQL层
 * Table API层
 * DataStream/DataSet API
 * 有状态流处理 prosessFuntion


 nc -lk

 ### flink数据源
 * 从集合读取
 * 从文件读取
 * 从socket读取
 * 从kafka读取
 * 自定义数据源
 * flink支持的数据类型TypeInformation


 #### P41 转换算子

P43 扁平映射flatMap，主要将数据流中的整体(集合类型)拆分成一个一个的个体使用。消费一个元素可能产生0到多个元素。

P44 聚合算子 Aggregation
* 按键分区(keyBy) flink对海量数据做聚合时需要进行分区
    KeyedStream

P52 输出算子Sink
SinkFunction
writeAsCsv
writeAsText
writeAsSocket


P59 Flink时间窗口