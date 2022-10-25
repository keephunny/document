

### 创建数据库
不同类型的数据采集点往往具有不同的数据特征，包括数据采集频率的高低，数据保留时间的长短，副本的数目，数据块的大小，是否允许更新数据等等。为了在各种场景下 TDengine 都能最大效率的工作，TDengine 建议将不同数据特征的表创建在不同的库里，因为每个库可以配置不同的存储策略。创建一个库时，除 SQL 标准的选项外，还可以指定保留时长、副本数、内存块个数、时间精度、文件块里最大最小记录条数、是否压缩、一个数据文件覆盖的天数等多种参数。比如：

```
CREATE DATABASE power KEEP 365 DAYS 10 BLOCKS 6 UPDATE 1;
```
上述语句将创建一个名为 power 的库，这个库的数据将保留 365 天（超过 365 天将被自动删除），每 10 天一个数据文件，内存块数为 6，允许更新数据。


### 创建表
一个物联网系统，往往存在多种类型的设备，比如对于电网，存在智能电表、变压器、母线、开关等等。为便于多表之间的聚合，使用 TDengine, 需要对每个类型的数据采集点创建一个超级表。
TDengine 建议将数据采集点的全局唯一 ID 作为表名(比如设备序列号）。但对于有的场景，并没有唯一的 ID，可以将多个 ID 组合成一个唯一的 ID。不建议将具有唯一性的 ID 作为标签值。
```
#超级表
CREATE STABLE meters (ts timestamp, current float, voltage int, phase float) TAGS (location binary(64), groupId int);
#设备表
CREATE TABLE d1001 USING meters TAGS ("California.SanFrancisco", 2);

```
自动创建表
在某些特殊场景中，用户在写数据时并不确定某个数据采集点的表是否存在，此时可在写入数据时使用自动建表语法来创建不存在的表，若该表已存在则不会建立新表且后面的 USING 语句被忽略。比如：

```
INSERT INTO d1001 USING meters TAGS ("California.SanFrancisco", 2) VALUES (now, 10.2, 219, 0.32);
```

### 插入数据