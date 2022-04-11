### 基础操作 

```
# 创建数据库
create database database_name

# 删除数据库
drop database database_name

# 查看数据库
show databases

# 创建用户和密码
create user <user_name> with password '<password>'

# 赋权
create user <user_name> with password 'xxx' with all privileges

# 修改密码
set password for user_name='newpwd'

# 删除用户
drop user user_name

# 查看用户
show users;

# 授权数据库给指定的用户
grant all privileges on <database_name> to <user_name>
grant [read,write,all] on <database_name> to <user_name>

# 回收权限
revoke all privileges from <user_name>
revoke [read,write,all] on <database_name> from <user_name>

# 查询权限
show grants for <user_name>

# 设备时间格式
precision rfc3339


```



### Measurements

在influxdb中measurement相当于mysql中的表，可以理解为一条一条记录都是存与measurent中的，一个数据库中可以有多个measurement，一个measurement中可以存很多的数据。虽然可将measurement类比为mysql中的表，但是他们之间的差别也挺明显的

```
# 查看数据库中所有的measurement
show measurements



```



### point

在influxdb中，你可以将一条mysql中的记录简单的理解为一个point，它由四个组件

- measurement
- tag set
- field set
- timestamp

```
```





### select查询语句

```
SELECT <field_key>[,<field_key>,<tag_key>] FROM <measurement_name>[,<measurement_name>]
```

**SELECT子句中，如果包含了tag，那么此时就必须指定至少一个field。**

#### select子句

```
#查询所有的字段tag和fields
SELECT *

#查询指定的field
SELECT <field_key>

#查询多个field
SELECT <field_key>,<field_key>

#查询指定的field和tag
SELECT <field_key>,<tag_key>

#如果字段名重复，则需要指定标识符的类型
SELECT <field_key>::field,<tag_key>::tag
```



#### from子句

```
#指定measurement查询
FROM <measurement_name>

#从多个measurement中查询
FROM <measurement_name>,<measurement_name>

#指定database、retention_policy的measurement中查询
FROM <database_name>.<retention_policy_name>.<measurement_name>

#指定database、默认retenttion_policy的measurement中查询
FROM <database_name>..<measurement_name>

FROM子句中还支持正则表达式。
```

**官方推荐，虽然有些标识符不是必须使用双引号，但是推荐对所有标识符使用双引号！**



#### where子句

```
SELECT_clause FROM_clause WHERE <conditional_expression> [(AND|OR) <conditional_expression> [...]]

select * from p0001 where time>=1649493212087352047

```

* 在WHERE子句中，支持在fields, tags, and timestamps上进行条件表达式的运算
* 在InfluxDB的WHERE子句中，不支持使用 OR 来指定不同的time区间

##### Fields表达式

```
field_key <operator> ['string' | boolean | float | integer]

在WHERE子句中，支持对string, boolean, float 和 integer类型的field values进行比较。
如果是string类型的field value，一定要用单引号括起来。如果不适用引号括起来，或者使用的是双引号，将不会返回任何数据，有时甚至都不报错！
```
支持的运算符：

| Operator | Meaning                  |
| :------: | ------------------------ |
|    =     | equal to                 |
|    <>    | not equal to             |
|    !=    | not equal to             |
|    >     | greater than             |
|    >=    | greater than or equal to |
|    <     | less than                |
|    <=    | less than or equal to    |



##### Tags表达款

```
tag_key <operator> ['tag_value']
对于在WHERE子句中的tag values，也要用单引号括起来。如果不用引号括起来，或者使用双引号，则查询不会返回任务数据。甚至不会报错。
```

支持的运算符：

| Operator | Meaning |
| ------------ | ----------- |
| = | equal to |
| <> | not equal to |
| != | not equal to |

##### Timestamps

对于大部分的SELECT 语句来说，默认的时间区间是1677-09-21 00:12:43.145224194 到 2262-04-11T23:47:16.854775806Z UTC.对于有GROUP BY time() 的SELECT 语句，默认的时间区间是1677-09-21 00:12:43.145224194 UTC 到 now()。



#### GROUP BY 子句

```
SELECT_clause FROM_clause [WHERE_clause]
GROUP BY [* | <tag_key>[,<tag_key]]
```

GROUP BY 通过用户指定的tag set，来对查询结果进行分组。

```
#使用所有tag对查询结果进行分组
GROUP BY *

#使用指定tag对查询结果进行分组
GROUP BY <tag_key>

#使用指定的多个tag对查询结果进行分组，其中tag之间的顺序是无关的。
GROUP BY <tag_key>，<tag_key>
```

#### GROUP BY time( intervals)

```
SELECT <function>(<field_key>) FROM_clause
WHERE <time_range>
GROUP BY time(<time_interval>),[tag_key] [fill(<fill_option>)]

SELECT COUNT("water_level") FROM "h2o_feet"
GROUP BY time(12m)
```

GROUP BY time() 查询会将查询结果按照用户指定的时间区间来进行分组。基本的 GROUP BY time() 查询用法需要在SELECT子句中调用相关函数，并且在WHERE子句中调用time时间区间。



#### 高级GROUP BY time() 语法

```
SELECT <function>(<field_key>)
FROM_clause
WHERE <time_range>
GROUP BY time(<time_interval>,<offset_interval>),[tag_key] [fill(<fill_option>)]
```

在GROUP BY time()高级语法中，需要在SELECT子句中调用InfluxDB的函数，并在WHERE子句中指定时间区间。并且需要注意到的是，GROUP BY子句必须在WHERE子句之后！

- time(time_interval,offset_interval)
  在GROUP BY time()子句中的通过time_interval和offset_interval来表示一个连续的时间区间，该时间区间决定了InfluxDB如何通过时间来对查询结果进行分组。比如，如果时间区间为5m，那么它会将查询结果分为5分钟一组（如果在WHERE子句中指定了time区间，那么就是将WHERE中指定的time区间划分为没5分钟一组）。
  offset_interval是持续时间文本。它向前或向后移动InfluxDB数据库的预设时间边界。offset_interval可以为正或负。

- fill(<fill_option>)
  fill(<fill_option>)是可选的。 它可以填充那些没有数据的时间区间的值。 从 GROUP BY time intervals and fill() 部分可查看到关于这部分的更多信息。

  **注**：高级 GROUP BY time() 语法依赖于time_interval、offset_interval、以及 InfluxDB 数据库的预设时间边界来确定每组内的数据条数、以及查询结果的时间戳。

```
ELECT MEAN("water_level") FROM "h2o_feet" GROUP BY time(18m,6m)
将查询结果按照每18m为一组进行了分组，并且将预设的时间界限偏移了6分钟。

time(18m,-12m)，offset_interval是负数，它的查询结果跟使用time(18m,6m)是一样的。因此在决定正负偏移间隔时，请随意选择最直观的选项。
```

##### GROUP BY time intervals and fill()

Fill() 可以填充那些没有数据的时间区间的值。

```
SELECT <function>(<field_key>) FROM_clause
WHERE <time_range>
GROUP BY time(time_interval,[<offset_interval])[,tag_key] [fill(<fill_option>)]
```

默认情况下，在GROUP BY time()查询结果中，若某个时间区间没有数据，则该时间区间对应的值为null。通过fill()，就可以填充那些没有数据的时间区间的值。
需要注意的是，fill()必须出现在GROUP BY子句的最后。

**Fill选项**

1. 任何数学数值
   使用给定的数学数值进行填充
2. linear
   为没有数据值的时间区间线性插入数值，使得插入之后的数值，跟其他本来就有数据的区间的值成线性。（这里翻译的不是很好，看示例就能明白了）
3. none
   若某个时间区间内没有数据，则在查询结果中该区间对应的时间戳将不显示出来
4. null
   没有值的区间，显示为null。这也是默认的选项。
5. previous
   用前一个区间的数值来填充当前没有数据的区间的值。



#### INTO子句

```
SELECT_clause
INTO <measurement_name>
FROM_clause [WHERE_clause] [GROUP_BY_clause]
```

通过INTO子句，可以将用户的查询结果插入到用户指定的measurement中。

```
# 插入到指定measurement中。此时使用的是当前库、使用默认的retention policy
INTO <measurement_name>

# 往全路径的measurement中插入数据。此时指定了库、指定retention policy、指定measurement
INTO <database_name>.<retention_policy_name>.<measurement_name>

# 往指定库的指定measurement中插入数据，使用默认的retention policy
INTO <database_name>..<measurement_name>

# 往指定库、指定retentioin policy，并且符合FROM子句中的正则规则的measurement中插入数据
INTO <database_name>.<retention_policy_name>.:MEASUREMENT FROM /<regular_expression>/
```



#### ORDER BY 子句

```
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause]
[GROUP_BY_clause]
ORDER BY time DESC
```

在InfluxDB中查询时，默认返回的数据时按照time升序排序的。而通过ORDER BY time DESC子句，可以将查询结果进行倒序排序。



#### LIMIT子句

```
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause]
[GROUP_BY_clause] [ORDER_BY_clause]
LIMIT <N>
```

LIMIT和SLIMIT分别用于限制InfluxDB中每次查询时返回的points的数目。

##### SLIMIT子句

SLIMIT 返回指定measurement的前N个series的所有point。

当LIMIT 和SLIMIT 一起使用时，表示的意思是查询指定measurement前N个series的前N个point。英文原文如下：LIMIT followed by SLIMIT returns the first points from series in the specified measurement。它指的是说返回的前N个series中，每个series的前N个points。下面将给出示例sql。

```
SELECT MEAN("water_level") FROM "h2o_feet"
LIMIT 3 SLIMIT 2
查询返回2个series，每个series返回3个points。
```



##### OFFSET子句

```
SELECT_clause [INTO_clause] FROM_clause
[WHERE_clause] [GROUP_BY_clause] [ORDER_BY_clause]
LIMIT_clause OFFSET <N>
[SLIMIT_clause]
```

OFFSET 将从查询结果的第N个points开始进行分页。

OFFSET中的N表示从查询结果的第N个points开始进行分页。注意OFFSET必须和LIMIT搭配使用，如果只有OFFSET而没有LIMIT，将会导致不一致的查询结果。

##### SOFFSET子句

```
SELECT_clause [INTO_clause] FROM_clause [WHERE_clause]
GROUP BY *[,time(time_interval)]
[ORDER_BY_clause]
[LIMIT_clause] [OFFSET_clause]
SLIMIT_clause SOFFSET <N>
```

SOFFSET 将从查询结果的第N个series开始进行分页。

SOFFSET 中的N指定了开始分页的地方，SOFFSET要跟SLIMIT子句一同搭配使用。如果只使用SOFFSET而没有SLIMIT子句，则可能会导致不一致的查询结果。
