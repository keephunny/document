Sqoop是一款开源工具，主要用于在Hadoop(hive)与传统数据库间进行数据的传递，可以将一个关系型数据库的数据导进hadoop的hdfs中，也可以将hdfs的数据导入到关系型数据库中。

Sqoop的工作机制是将导入或导出命令翻译成mapreduce程序来实现。
* DBinputFormat->TextoutputFormat
* DBoutputFormat<-TextinputFormat