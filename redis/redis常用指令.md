
1.	auth password：验证密码是否正确
2.	echo message：打印字符串
3.	ping：查看服务是否运行
4.	quit：关闭当前连接
5.	select index：切换到指定的数据库





### 连接

```
redis-cli [OPTIONS] [cmd [arg [arg ...]]]
	-h <主机ip>，默认是127.0.0.1
	-p <端口>，默认是6379
	-a <密码>，如果redis加锁，需要传递密码
	-c 指定是集群模式连接
	--help，显示帮助信息
	
```



### 键(Key)

```
# 查看数据库匹配pattern的key
keys pattern

# 判断是否存在 返回存在的个数
exists key [key]

# 设置key的超时时间
expire key seconds

# 返回key剩余有效时间 秒
ttl key

# 查看key所存储的数据类型
type key

# 删除key
del key [key2]
```

| 序号 | 指令               | 说明                                                         |
| ---- | ------------------ | ------------------------------------------------------------ |
| 1    | expire key second  | 设置key的过期时间 单位秒                                     |
| 2    | pexpire key second | 设置key的过期时间 单位毫秒                                   |
| 3    | ttl key            | 获取key的剩余过期时间（单位为秒）<br>key是永久的，返回-1 <br>key不存在或者已过期，返回-2 |
| 4    | pttl key           | 同上，单位是毫秒                                             |
| 5    | persist key        | 移除key的过期时间                                            |



### 数据操作

#### String

```
# 设置key的value
set key value

# 获取指定key的value
get key

# 获取value的指定位数
getrange key [start,end]

# 设置value,从偏移量offset开始
setrange key offset value

# 为key设置新的value，并返回原来的value
getset key value

# 对key所储存的字符串，获取指定偏移量上的位(bit)
getbit key [offset]

# 对value值，设置或清除指定偏移量上的位(bit)
setbit key offset [n]

# 获取多个key的value
mget key1 key2 …

# 设置key的value和过期时间
setex key secords [n]

# 当key不存在时设置key的value
setnx key value

# 返回key的字符串长度
strlen key

# 同时设置多个key
mset key value [key2 value2]

# 当key不存在时,同时设置多个key
msetnx key value [key2 value2]

# 已毫秒为单位设置过期时间
psetex key milliseconds n

# 将key的value加一
incr key

# 将key的value加ns
incrby key [n]

# 将key的value加上浮点增量n
incrbyfloat key [n]

# 将key中存储的值减1
decr key

# 将key中存储的值减n
decrby key [n]

# 在key中存储的值追加value
append key value

# 设置key的过期时间
expire key [second]
```



#### Hash

hash是一个String类型的field和value的映射表，特别适合于存储对象。

| 序号 | 指令                                       | d                                                    |
| ---- | ------------------------------------------ | ---------------------------------------------------- |
| 1    | hdel key field1 [field2]                   | 删除指定key的字段                                    |
| 2    | hexists key field                          | 查看指定的字段是否存在                               |
| 3    | hget key field                             | 获取指定字段的值。                                   |
| 4    | hgetall key                                | 获取key中所有的字段和值                              |
| 5    | hincrby key filed n                        | 为指定的字段值加n                                    |
| 6    | hincrbyfloat key field n                   | 为指定的字段值加浮点值n                              |
| 7    | hkeys key                                  | 获取key中所有的字段                                  |
| 8    | hlen key                                   | 获取key中字段的数量                                  |
| 9    | hmget key field [field2]                   | 获取指定字段的值                                     |
| 10   | hmset key field1 value1 field2 value2      | 同时设置多个字段的值。                               |
| 11   | hset key field value                       | 设置指定字段的值                                     |
| 12   | hsetnx key field value                     | 当字段不存在时，才设置字段的值。                     |
| 13   | hvals key                                  | 获取key中所有的值                                    |
| 14   | hscan key cursor [match pattern] [count n] | 迭代获取键值对<br>hscan hash1 3 match 'key*' count 2 |
| 15   | expire key [second]                        | 设置key的过期时间                                    |



#### List

List是简单的字符串列表，按照插入顺序排序。可以列表的头部或属部添加元素。

| 序号 | 指令                                  | 说明                                                         |
| ---- | ------------------------------------- | ------------------------------------------------------------ |
| 1    | BLPOP key1 [key2] timeout             | 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 2    | BRPOP key1 [key2] timeout             | 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 3    | BRPOPLPUSHH sourceKey destKey timeout | 列表中取出最后一个元素，并插入到另外一个列表的头部； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 |
| 4    | LINDEX key index                      | 获取列表中指定下标的元素                                     |
| 5    | linsert key before\|after pivot value | 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。    |
| 6    | LLEN key                              | 获取列表的长度                                               |
| 7    | LPOP key                              | 移出并获取列表的第一个元素                                   |
| 8    | LPUSH key value1 [value2]             | 将一个或多个值插入到列表的头部                               |
| 9    | LPUSH key value                       | 将值插入到列表的头部                                         |
| 10   | LRANGE key start stop                 | 获取列表中指定范围的元素                                     |
| 11   | LREM key count value                  | 移除列中与value相同的元素<br>count>0:从表头开始计算<br>count\<0:从表尾向表头开始搜索<br>count=0:移除所有的 |
| 12   | LSET key index value                  | 通过索引下标设置元素的值                                     |
| 13   | LTRIM key start stop                  | 让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除 |
| 14   | RPOP key                              | 移除并返回列表中最后一个元素                                 |
| 15   | RPOPLPUSH sourceKey destKKey          | 移除列表的最后一个元素，并将该元素添加到另一个列表并返回     |
| 16   | RPUSH key value1 [value2]             | 在列表的尾部添加一个或多个值                                 |
| 17   | RPUSH key value                       | 在列表的尾部添加一个值                                       |



#### Set

Set是String类型的无序集合，成员都是唯一的，不能重复。redis中集合是通过hash实现的，所以添加、删除、查找的复杂度都是O(1)。

| 序号 | 指令                                       | 说明                                                         |
| ---- | ------------------------------------------ | ------------------------------------------------------------ |
| 1    | SADD key value1 [value2]                   | 向集合添加一个或多个元素                                     |
| 2    | SCARD key                                  | 获取集合的元素个数                                           |
| 3    | SDIFF key1  key2                           | 返回第一个集合与其它集合的差异                               |
| 4    | SDIFFSTORE destionKey key1 key2            | 返回给定所有集合的差异并存储在目标key中                      |
| 5    | SINTER key1 key2                           | 返回所集合的交集                                             |
| 6    | SINTERSTORE destKey key1 key2              | 返回所有集合的交集并存储在目标key中                          |
| 7    | SISMEMBER key value                        | 判断value是不是key的成员<br>是：返回1<br>不是：返回0         |
| 8    | SISMEMBER key                              | 返回集合中的所有元素                                         |
| 9    | SMOVE source dest value                    | 将value从source移动到dest集合中                              |
| 10   | SPOP key                                   | 移除并返回集合中的一个随机元素                               |
| 11   | SRANDMEMBER key [count]                    | 返回集合中一个或多个随机元素                                 |
| 12   | SREM key value1 value2                     | 移除集合中一个或多个元素                                     |
| 13   | SUNION key1 key2                           | 返回所有指定集合的并集元素                                   |
| 14   | SUNIONSTORE destKey key1 key2              | 将所有指定集合的并集元素存储到destKey中                      |
| 15   | SSCAN key cursor [match pattern] [count n] | cursor：游标 <br>pattern：匹配的模式<br> count：返回多少元素，默认值为 10 |



#### Zset

Zset有序集合也是String类型元素的集合，且不允许重复。不同的是每个元素都会关联一个double类型的分数，redis通过分数来进行排序。有序集合的分数(score)可以重复。

| 序号 | 指令                                           | 说明                                                         |
| ---- | ---------------------------------------------- | ------------------------------------------------------------ |
| 1    | ZADD key score1 value1 [score2 value2]         | 向有序集合添加一个或多个元素，存在则更新                     |
| 2    | ZCARD key                                      | 获取有序集合的所有元素的个数                                 |
| 3    | ZCOUNT key min max                             | 获取在区间分数内的元素个数                                   |
| 4    | ZINCRBY key increment  value                   | 在指定元素的分数上加1                                        |
| 5    | ZINTERSTORE destKey numkeys key1 key2          | 计算一个或多个有序集的交集，并将结果集储存到 destKey<br>key 的数量必须以 numkeys 参数指定 |
| 6    | ZLEXCOUNT key minVal maxVal                    | 计算指定区间内成员数量                                       |
| 7    | ZRANGE key start stop [withscores]             | 返回指定区间内的成员值                                       |
| 8    | ZRANGEBYLEX key min max [LIMIT offset count]   | 通过字典区间返回有序集合的成员                               |
| 9    | ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT] | 通过分数返回有序集合内指定区间的成员                         |

//TODO 待补充详细说明



### 持久化


1.	DEL key：该命令用于在 key 存在时删除 key。
2.	DUMP key：序列化给定 key ，并返回被序列化的值。
3.	EXISTS key：检查给定 key 是否存在。
4.	EXPIRE key seconds：为给定 key 设置过期时间，以秒计。
5.	EXPIREAT key timestamp：EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置过期时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
6.	PEXPIRE key milliseconds：设置 key 的过期时间以毫秒计。
7.	PEXPIREAT key milliseconds-timestamp：设置 key 过期时间的时间戳(unix timestamp) 以毫秒计
8.	KEYS pattern：查找所有符合给定模式( pattern)的 key 。
9.	MOVE key db：将当前数据库的 key 移动到给定的数据库 db 当中。
10.	PERSIST key：移除 key 的过期时间，key 将持久保持。
11.	PTTL key：以毫秒为单位返回 key 的剩余的过期时间。
12.	TTL key：以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
13.	RANDOMKEY：从当前数据库中随机返回一个 key 。
14.	RENAME key newkey：修改 key 的名称
15.	RENAMENX key newkey：仅当 newkey 不存在时，将 key 改名为 newkey 。
16.	SCAN cursor [MATCH pattern] [COUNT count]：迭代数据库中的数据库键。
17.	TYPE key：返回 key 所储存的值的类型。


Redis 服务器命令

1	BGREWRITEAOF
异步执行一个 AOF（AppendOnly File） 文件重写操作
2	BGSAVE
在后台异步保存当前数据库的数据到磁盘
3	CLIENT KILL [ip:port] [ID client-id]
关闭客户端连接
4	CLIENT LIST
获取连接到服务器的客户端连接列表
5	CLIENT GETNAME
获取连接的名称
6	CLIENT PAUSE timeout
在指定时间内终止运行来自客户端的命令
7	CLIENT SETNAME connection-name
设置当前连接的名称
8	CLUSTER SLOTS
获取集群节点的映射数组
9	COMMAND
获取 Redis 命令详情数组
10	COMMAND COUNT
获取 Redis 命令总数
11	COMMAND GETKEYS
获取给定命令的所有键
12	TIME
返回当前服务器时间
13	COMMAND INFO command-name [command-name ...]
获取指定 Redis 命令描述的数组
14	CONFIG GET parameter
获取指定配置参数的值
15	CONFIG REWRITE
对启动 Redis 服务器时所指定的 redis.conf 配置文件进行改写
16	CONFIG SET parameter value
修改 redis 配置参数，无需重启
17	CONFIG RESETSTAT
重置 INFO 命令中的某些统计数据
18	DBSIZE
返回当前数据库的 key 的数量
19	DEBUG OBJECT key
获取 key 的调试信息
20	DEBUG SEGFAULT
让 Redis 服务崩溃
21	FLUSHALL
删除所有数据库的所有key
22	FLUSHDB
删除当前数据库的所有key
23	INFO [section]
获取 Redis 服务器的各种信息和统计数值
24	LASTSAVE
返回最近一次 Redis 成功将数据保存到磁盘上的时间，以 UNIX 时间戳格式表示
25	MONITOR
实时打印出 Redis 服务器接收到的命令，调试用
26	ROLE
返回主从实例所属的角色
27	SAVE
同步保存数据到硬盘
28	SHUTDOWN [NOSAVE] [SAVE]
异步保存数据到硬盘，并关闭服务器
29	SLAVEOF host port
将当前服务器转变为指定服务器的从属服务器(slave server)
30	SLOWLOG subcommand [argument]
管理 redis 的慢日志
31	SYNC
用于复制功能(replication)的内部命令