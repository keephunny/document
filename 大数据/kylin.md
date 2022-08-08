
Data Warehouse数据仓库

MOLAP是基于多维数据集，一个多维数据集称为一个LOAP Cube

星型模型：一张事实表、多张维度表
Fact Table：事实表
Dimension Table：维度表
Measure：度量 被分析的指标
维度 分析数据的数据角度

安装kylin之前依赖hadoop、hive、hbase、zk、spark

逐层构渐算法
快速构渐算法

强制维度
层级维度
联合维度

rowkey的设计原则
1. 被用作过滤的维度放在前边
2. 基数大的维度放在基数据小的维度前边

https://blog.csdn.net/shan19920501/article/details/124574245
https://alice.blog.csdn.net/article/details/115267160