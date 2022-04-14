
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



### 数据操作

#### String

```
# 设置key的value
set key value

# 获取指定key的value
get key

# 获取value的指定位数

# 为key设置新的value，并返回原来的value
getset key value

# 获取多个key的value
mget key1 key2 …

# 当key不存在时设置key的value
setnx key value

# 返回key的字符串长度
strlen key

```



强化现有设备与拟建系统的兼容性。



#### Hash



#### List



#### Set



#### Zset



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