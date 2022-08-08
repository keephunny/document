


Yet Another Resource Negotiator 
NameNode
SecondaryNameNode
ResourceManager
默认配置文件：
* core-default.xml
* hdfs-default.xml
* yarn-detault.xml
* mapred-default.xml
自定义配置文件
* core-site.xml
* hdfs-site.xml
* yarn-site.xml
* mapred-site.xml
* 配置集群workers
* 配置集集slave(hadoop2.x)

启动集群 
* sbin/start-dfs.sh
* sbin/start-yarn.sh
  

hdfs nameNode：8020/9000/9820
    nameNode对用户查询端口:9870
yarn查看任务运行信息: 8088
历史服务器：19888

##### HDFS
HDFS(Hadoop Distributed File System)它是一个文件系统，用于是存储文件，通过目录树来定位，其次它是分布式的，由很多服务器联合起来实现其功能，集群中的服务器有各自的角色。适合一次写入，多次读出的场景。
高容错性，数据自动保存多个副本，通过增加副本的形式提高容错性。数据处规模大。
不适合低延时数据访问
无法高效的对大量小文件进行存储，会占用NameNode大量的内存来存储文件目录和块信息，这样不可取，因为NameNode的内存总是有限的。小文件存储的寻址时间会超过读取时间，违反了HDFS的设计目标。
不支持并发写入、文件随机修改，仅支持数据追加，不支持文件的随机修改。
* NameNode：它是一个管理者mater，管理hdfs的名称空间，配置副本策略，管理数据块映射信息，处理客户端读写请求。 
* DataNode：是slave执行NameNode下达的指令。存储实际的数据块，执行数据块的读写操作。 
* client：客户端
  * 文件切分，文件上传hdfs时，client将文件切分成一个一个的Block，然后进行上传。
  * 与NameNode交互，获取文件的位置信息
  * 与DataNode交互，读取或写入数据
  * 提供一些命令来管理hdfs，比如NameNode格式化、增删改查操作
* Secondary NameNode：并非NameNode的热备，当NameNode挂掉的时候它并不能马上替换NameNode提供服务，只是分担其工作量。


* hdfs的块设置太小，会增加寻址时间，程序一直在找块的开始位置。
* hdfs的块设置太大，从磁盘传输数据的时间会明显大于定位这个块开始位置所需的时间，导致程序在处理块数据时会非常慢。

常用命令
```
hadoop fs -moveFromLocal  源文件   目标文件
hadoop fs -copyFromLocal  源文件   目标文件
haddop fs -appendToFile  源文件   目标文件
hadoop fs -put 源文件  目标文件

hadoop fs -copyToLocal 源文件  目标文件
hadoop fs -get 源文件  目标文件
hadoop fs -ls
hadoop jar xxx.jar

```


##### MapReduce
MapReduce是一个分布式运程序的编程框架，用户开发基于是Hadoop的数据分析应用的核心框架。MapReduce核心功能是将用户编写的业务逻辑代码稆自带默认组件整合成一个完整的分布式运算程序，并运行在一个hadoop集群上。
* 易于编程，良好的扩展性，高容错性，适合海量数据计算。
* 不擅长实时间计算、流式计算、DAG有向无环图计算。

一个完整的MR程序在分布式运行时有三类：
* MRAppMaster：负责整个MR程序的过程调度及状态协调
* MapTask：负责map阶段的整个数据处理流程
* ReduceTask：负责reduce阶段的整个数据处理流程

Shuffle：Map产生输出数据开始到Reduce拉取数据作为输入之前的过程称作Shuffle。

extends Mapper{

}

InputFormat数据输入
* FileInputFormat
* TextInputFormat
* KeyValueTextInputFormat
* NLineInputFormat
* CombineTextInputFormat：适用于小文件过多的场景，可以将多个小文件从逻辑上规划到一个切版中，这样多个小文件就可以提交给一个MapTask处理。

##### YARN
Yarn是一个资源调度平台，负责为运算程序提供服务器运算资源，相当于一个分布式的操作系统平台，而MapReduce等运算程序相当于是运行于操作系统之上的应用程序。主要帖ResourceManager、NodeManager、ApplicationMaster、Container等组件构成。


###### Hadoop调度策略
* FIFO Scheduler：先进先出策略，即提交任先执行，不考虑优先级和范围，适用于负载较小的规模集群。
* Capacity Scheduler：容器调度策略，允许不同组织不同部分同时共享整个集群资源，Capacity可以理解为一个个资源队列，把一个集群划分成一个个的队列，不同队列之间可以划分子队列。
* Fair Scheduler：公平调度策略，


##### hadoop依赖组件
* zookeeper：一个开源分布式应用程序协调服务，基于zk可以实现同步服务，配置维护，命名服务等。
* flume：一个高可用、高可靠、分布式海量日志采集、聚合、专输的系统。
* hbase：分布式、面向列的开源数据库，利用hdfs作为其存储系统。
* hive：基于hadoop的数据仓库工具，可以将结构化的数据档映射为一张数据库表，并提供简单的sql查询功能，可以将sql语句转换为mapreduce任务进行运行。
* sqoop：将一个关系统数据库中的数据导入到hadoop的hdfs中，也可以将hdfs的数据导到关系型数据库中。







https://blog.csdn.net/shan19920501/article/details/124515213