
hadoop、zookeeper、phoenix、hive、hive
海量数据的传输、存储和计算。bitTable是一个稀疏的、分布式的、持久的多维排序map。

Hbase使用与BigTable相似的数据模型，用户将数据行存储在带有标签的表中，数据行具有可排序的键和任意数量的列，该表存储稀疏，同一表中的行可以有不同的列。稀疏、分布式、多维、排序的映射、其中映射map指代非关系型数据库的k-v结构。

列：
列族：
rowkey：按照rowkey的字典排列，rowkey为byte数组
region：行拆分
store：列拆分


命名空间
* hbase：存放Hbase内置表
* default：用户默认的命名空间


put 插入数据
get 读取一行数据
scan 读取多行数据

HBase是一种分布式、可扩展、支持海量数据存储的NoSQL数据库。
* 是一种面向列簇的非关系型数据库。K-V
* 用于是存储结构化和非结构化的数据，适用于单表非关系型数据的存储，不适合做关联查询，类似join操作。
* 基于HDFS数据持久化存储的体现形式是HFile，存放在DataNode中，被ResionServer以rergion的形式进行管理。
* 延迟较低，接入在线业务使用，面对大量的企业数白白已大，HBase可以实现单表大量数据的存储，同提供了高效的数据该问速度。

###### Name Space
命名空间，类似于关系数据库的DataBase概念，每个命名空间下有多个表，HBase有两个自带的hbase和default命名空间。hbase存放内置表，default是用户默认的命名空间
###### Region
region的概念和关系型数据库的分区或分片类似，region是hbase中分布式存储和负载均衡的最小单元，region包括完整的行，所以region是以行为单位，表的一个子集，不同的region可以分别在不同的regionserver上。
每个表一开始只有一个region，每个region会保存一个表里某段连续的数据，随着数据的不断插入，region不断增大，region会二等分，生成两个新白白金言region。
talble中所有的行都是按照rowkey字典排列，table在行的方向分割为多个region，基于rowkey的不同范围分配到不同的region中。

###### Row
Hbase表中的每行数据都是由一个rowkey和一个或多个column组成，数据按照rowkey的字典顺序存储，并且查询数据时只能根据rowkey进行检索，所以rowkey的设计十分重要。

###### Row Key
Rowkey 的概念和 mysql 中的主键类似，Hbase 使用 Rowkey 来唯一的区分某一行的数据。Hbase只支持3种查询方式： 1、基于Rowkey的单行查询，2、基于Rowkey的范围扫描 ，3、全表扫描
因此，Rowkey对Hbase的性能影响非常大。设计的时候要兼顾基于Rowkey的单行查询也要键入Rowkey的范围扫描。

###### Column Family
Hbase通过列族划分数据的存储，列族下面包括任意多的列，实现灵活数据存取，列族是由一个个列组成，在列数据为空的情况下，不会占用存储空间。创建表的时候必须指定列族，列族成员可以动态加入，family下面可以有多个qualifier，所以hbase中的列是二级列，也就是family是一级列，qualifier是二级列。HBase把同一列簇里面的数据存储在同一目录下，由几个文件保存。

##### Column
hbase中的每个列，由列族加上列限定符组成，创建表时需要指明列族。

##### Time Stamp(version)
用于标识数据的不同版本，针对每个列族进行设置。写入数据时，如果没有指定相应的timestamp，hbase会自动加一个timestamp。为了避免数据存在过多版本造成的管理成本，hbase提供了两种数据回收方式：
1.保存数据最后的n个版本
2.保存最近一段时间内的版本

##### Cell
由{Rowkey，ColumnFamily : ColumnQualifier, TimeStamp} 确定唯一的cell。
cell 中的所有字段数据 都没有数据类型，全部是字节码形式存储。被视为字节数组byte[]

##### 索引
一级索引：rowkey
二级索引：
    全局：建立索引表 适合读
    本地：将索引和数据放在原表中(同一个region) 适合写

##### Row key 的设计原则
* 唯一原则
  必须在设计上保证其唯一性，rowkey是按照字典顺序排序存储的，因此，设计rowkey的时候，要充分利用这个排序的特点，将经常读取的数据存储到一块，将最近可能会被访问的数据放到一块。
* 排序 字典排序
* 长度
* 散列 
    如果rowkey按照时间戳的方式递增，不要将时间放在二进制码的前面，建议将rowkey的高位作为散列字段，由程序随机生成，低位放时间字段，这样将提高数据均衡分布在每个RegionServer，以实现负载均衡的几率。如果没有散列字段，首字段直接是时间信息，所有的数据都会集中在一个RegionServer上，这样在数据检索的时候负载会集中在个别的RegionServer上，造成热点问题，会降低查询效率。

hbase是三维有序存储的，通过rowkey、columnkey、timeStamp这三个维度可以对habase中的数据进行快速定位。
rowkey是一个二进制码流，可以是任意字符串，最大长度 64kb ，实际应用中一般为10-100bytes，以 byte[] 形式保存，一般设计成定长。








##### springboot集成HBase
* hbase-client：比较底层，需要自己进一步封装 api，而且版本号和安装的 hbase 也要匹配，否则会报错
* spring-data-hadoop：2019 年 4 月 5 停止维护
* Apache Phoenix：使用 SQL 的方式来操作 HBase。Phoenix 的性能很高（进行简单查询，其性能量级是毫秒），相对于 hbase 原生的 scan 并不会差多少，而对于类似的组件 hive、Impala 等，性能有着显著的提升

```
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>2.3.0</version>
</dependency>

# HBase 数据源配置
hbase.config.hbase.zookeeper.quorum=86.168.xx.xx
hbase.config.hbase.zookeeper.property.clientPort=2181


```