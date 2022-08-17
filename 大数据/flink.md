

##### 分层API
越顶层越抽象，表达含义越简明，使用越方便
越底层越具体，表达能力越丰富，使用越灵活
SQL：最高层语方
Table API：声明式领域专用语言
DataStream(流)/DataSet(批) API：核心APIs
有状态流处理：底层APIs processFunction


```
flink-java
flink-clients

//1.创建执行环境
ExecutionEnvironment env=Exc
```

Flink提交作业和执行任务，需要以下组件
* 客户端client
* 作业务管理器jobManager
* 任务管理器taskManager

提交任务
flink run -m xxxx:8081 -c com.xxx.App -p 2 ./
finkl cancal jobid

部署模式
* 会话模式 Session Mode
* 单作业模式Per-job Mode
* 应用模式Application Mode

flink cdc
* 清洗 select where not in 
* 聚合 group by、top n、
* 打宽 双流join、维表join、udtf


flink读取kafka数据源
env.addSource(new FlinkKafkaConsumer<String>("topicname",new SimleStringSchema(),properties));

其它组件flink都提供了连接器
SourceFunction接口，主要提供了两个重要方法
* run()：运行时上下文对象sourceContext向下游发送数据
* cancel()：通过标识位控制退出循环，达到中断数据源的效果。

过滤器
stream.filterr(new MyFilter())
myFilter implements FilterFunction<Event>

扁平映射flatMap
主要将数据流中的整体拆分成一个个体使用。先按照某种规则对数进行打散拆分，再对拆分后的元素做转换处理。
stream.flatMap(new MyFlatMap())
数据输出
SingleOutputStreamOperator<String>

聚合算子(Aggregation)
按键分区KeyMap
stream.keyBy(data->data.user)
        .maxBy("time")
        .print("maxBy")

自定义函数UDF


物理分区Physical Partitioning
stream.shuffle().setParallelism(4)
广播
全局分区

数据输出sink
StreamingFileSink
flink-connector-redis
flink-connector-mysql
flink-connector-es

flink时间和窗口
按照time和count划分
* time-tumbling-window 无重叠数据的时间窗口，设置方式举例：timeWindow(Time.seconds(5))
* time-sliding-window 有重叠数据的时间窗口，设置方式举例：timeWindow(Time.seconds(5), Time.seconds(3))
* count-tumbling-window无重叠数据的数量窗口，设置方式举例：countWindow(5)
* count-sliding-window 有重叠数据的数量窗口，设置方式举例：countWindow(5,3)

窗口：
将无限的数据流切割成有限的数据块进行处理，这就是所谓的窗口。在flink中，窗口是用来处理无界流的核心。


https://blog.csdn.net/qq_37056683/article/details/120993905