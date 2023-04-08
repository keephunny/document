


元数据
连接数据
查询缓存
最新数据
时序数据







###  常见错误

```
taos> select * from s_00000000028 where device_sn='sdfdfs';
Query OK, 0 rows in database (0.002421s)

taos> select * from s_00000000028 where device_sn='10000010001';

DB error: Unable to establish connection (0.252753s)
taos> select * from s_00000000028 where device_sn='10000010001';

DB error: Database not ready (6.051197s)

```



```
taos> create database shy_iot_2022 KEEP 3650 DURATION 1 CACHEMODEL 'last_row' REPLICA  3;

DB error: Out of dnodes (0.039896s)
REPLICA复本数量大于节点数量。
create database shy_iot_2022 KEEP 3650 DURATION 1 CACHEMODEL 'last_row' REPLICA  1;

```

