
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