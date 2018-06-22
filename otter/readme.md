
* Pipeline：从源端到目标端的整个过程描述，主要由一些同步映射过程组成。
* Channel：同步通道，单向同步一个Pipeline组成，双向同步由两个Pipeline组成。
* DataMediaPair：根据业务表定义映射关系，比如源表和目标表，字段映射，字段组成等。
* DataMedia：抽象的数据介质概念，可以理解为数据表/mq队列定义。
* DataMediaSource：抽象的数据介质源信息，补充描述DataMedia。
* ColumnPair：定义字段映射关系。 
* ColumnGroup：定义字段映射组。
* Node：处理同步过程的工作节点，对应一个jvm。

## otter的S/E/T/L stage创段模型
* Select数据接入，eromanaga、table、metaq
* Extract数据提取，数据join、数据据filter
* Transform数据转换，db>db、db>mq
* Load数据载入，db>db、db>mq、db>file
为了更好的支持系统的扩展和灵活性，将整个同步流程抽象为select/extract/transform/load四个阶段 
select阶段是为了解决数据来源的差异性，比如接入canal获取增量数据，也可以接入其它系统获取他数据等。   
ETL阶段类似于数据仓库的etl模型，具体可为数据join，数据转化，数据load。



canale节点配置位置点信息binlog日志。
添加映射关系，可以配置源表和目标表之间的映射关系。EventProcessor类型Source、clazz自定义映射关系。