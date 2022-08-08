cassandra是一套开源分布式Nosql的数据库系统，是一个混合型的非关系型数据库，类似BigTable基于列族的数据模型。
是一堆数据节点共同构成的一个分布式网络服务，对cassandra的写操作，会被复制到其它节点上，对cassandra的读操作，也会被路由到其它节点上。
数据写入密集、修改操作少、通过主键查询、需要对数据进行分区存储。日志存储、物联网海量数据。

* data/：存放数据的目录
* commitlog/：存储未写入SSTable中的数据，每次cassandra系统中有数据写入，都会先将数据记当前在该日志文件上。
* cache/：

数据模型
* 列(column)：列是cassandra的基本数据结构单元，具有名称、值、时间戳。不需要预先定义列，只需要在KeySpace里定义列族，然后就可以开始写数据了。
* 列族(column family)：列族相当于是关系数据的表，是包含多个行的容器。
* row key：列族中每一行都用row key来标识，这相当于关系数据库中的主键，并且总是被索引的。
* 主键：primary key 关键字创建主键。 
* 副本：replication factor
* 集群：cluster集群中节点组织成一个环(ring)，然后把数据分配到集群中的节点上。

数据类型：
* 基本数据类型
* 集合类型：set、list、map
* counter计数器类型

cassandra的5种key
* primary key ：主键
* partition key： 分区key，主键主要标识数据在哪个分区。
* composite key：复合key
* compound key：复合吸
* clustering key：集群

casandra存储的数据
* commitlog：主要记录提交的数据及操作，这种数据被持久化到磁盘中，方便数据没有被持久化到磁盘时可以用来恢复。
* memtable：用户写的数据在内存中的形式。
* SSTable：数据被持久化到磁盘，分为data、index、filterr三种数据格式。

java集成cassandra
* dataStax/java-driver
  cassandra-driver-core
  cassandra-driver-mapping
* springdata-cassandra


https://blog.csdn.net/weixin_39800144/article/details/117259407