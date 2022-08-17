自2016年开源依赖 版本号经历过三个历史阶段：
第一阶段：1.1版本 (1.1.54245, 2017-07-04 ---- 1.1.54394, 2018-07-12)
第二阶段：18.1.x---19.17.x (18.1.0, 2018-07-23 --- 19.17.6.36, 2019-12-27)
第三阶段：20.1.2.*版本至今（v20.1.2.4, 2020-01-22 -- now）

clickhouse具有ROLAP、在线实时查询，完整的DBMS、列式存储、不需要数据预处理、支持批量更新、完善的SQL和函数、不依赖Hadoop生态。

实时聚合和预先聚合
* 预先聚合只能技持固定的分析场景，无法满足自定义分析的需求。
* 维度组合爆炸导致数据膨胀。
* 流量数据是在线实时接收的，预聚合还需要考虑如何及时更新。

DBeaver连接数据库

数据库存储引擎
* Ordinary：默认引擎，可以使用任意类型的表引擎
* Dictionary：字典引擎，会自动为所有数据字典创建它们的数据表
* Memory：内存引擎，用于是存放临时数据。
* Lazy：目志引擎
* Mysql：mysql引擎


create database if not exists db_name [engine=engine]
表引擎即表的类型决定了数据的存储方式和位置，支持哪些查询、并发数据访问、索引的使用、多线程方式、数据复制参数。
* MergeTree 适用于高负载任务功能强大的表引擎，可以快速插入数据并进行后续的后台处理。
* 日志 具有最小功能的轻量引擎，当需要快速写入许多小表（最多100万行）并在以后整体读取它们时，该类型的引擎是最有效的。
* 集成引擎 用于与其它数据存储与处理系统集成的引擎，kafka、mysql、hdfs等.

不支持事务、缺少高频率，低延迟的修改或删除数据的能力，仅能用于批量删除或修改数据。稀疏索引使得ClickHouse不适合通过其键检索单行的点查询。

创建表
create table table_name{
    column_name string
}engine=engine

基本数据类型
Array
Tuple元组 不同类型的数组
Enum枚举
Nested嵌套类型
Domain ipv4 vpv6

clickhouse不建议使用null，过多的null值会使查询写入性能变慢，每列字段的数据都被存储在对应的column.bin文件中，如果一个列字段被nullable类型修饰后会额外生成一个column.null.bin文件专门保存它的null值，这意味着在读取和写入数据时需要一倍额外文件操作。

create temporary table 临时表

分区表
create table xx()engin=enginName
partition by toYYYYMM(time)

select * from system.part where table='表名' 


视图
物化视图和普通视图

插入数据
insert into 同mysql
insert into table (c1,c2) format format csv 

数据的删除和修改
clickhouse提供了delete和update的操作统称为mutation查询，它可以看作alter语句的变种。
mutation是一种很重的操作，更适用于批量操作。不支持事务一量提交不可回滚。mutation语句执行是一个异步的后台过程，语句被提交后就立即返回，并不代表逻辑已执行完毕。具体执行进度需要通过system.mutations查询。
alter table tableName delete where id='xx'

数据字典
数据字典是clickhouse提供的一种非常简单、实用的存储媒介，它以键值和属性映射的形式定义数据，字典中的数据会主动或被动的加载到内存，并支持动态更新，比较适合保存常量或维度表数据，避免不必要的join查询。
内置字典

外部扩展字典支持7种内存布局和4类数据来源。
* flat uint64作为key，数组初始1024最大500000
* hashed uint64作为key 内存中以散列结构保存，没有存储上限约束。
* range_hashed 在hashed的基础上增加指定区间的特性。
* cache uint64作为key，字典数据在内存中通过固定长度的向量数组保存，并不会一次性将所有的数据载入内存。
* complex_key_hashed
* complex_key_cache
* ip_trle

字典数据源
* 本地文件 <file><path>xx</path></file>
* 可执行文件 <executable><command>cat xx</command></executable>
* 远程文件<http><url>xx</url></http>
* 数据库类型
  mysql
  clickhouse
  mongoDB

create dictionary xxx(

)primary key id

mergeTree在写入一批数据时，数据总会以数据片的形式写入磁盘，且数据片段不可修改，为了避免片段过多，clickhouse会通过后台线程，定期合并这些数据片段，属于相同的数据片段会被合成一个新片段，这是数据片段复合并的特点。
存储结构
tableName
    partition1


列的独立存储

分区、索引、标记、压缩数据的协同处理
mergeTree首先根据分区索引、一级索引、二级索引，将数据的扫描范围缩小至最小，然后再根据数据标记，将需要解压和计算的数据范围缩至最小。



* 灵活的MPP架构，支持线性扩展，多服务器分布式处理数据完备的DBMS系统
* 底层数据列式存储，支持压缩

https://blog.csdn.net/qq_37056683/article/details/120993905