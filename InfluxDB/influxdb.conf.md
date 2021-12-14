reporting-disabled=false：是否上报influxdb的使用信息给influxdata公司

\# Bind address to use for the RPC service for backup and restore.

bind-address = "127.0.0.1:8088"



#### [meta] 元数据管理

控制InfluxDB metastore的参数，该metastore存储有关用户，数据库，保留策略，分片和连续查询的信息。

```
# 存储最终数据TSM文件的目录
dir = "/var/lib/influxdb/data"

# 用于新分片的索引的类型，默认inmem索引是在启动时重新创建内存中索引，要启用基于时间序列TSI磁盘索引，请将其设置为tsi1。
index-version="inmem"

# 预写日志（WAL）文件的存储目录
wal-dir = "/var/lib/influxdb/wal"

# 写入在fsyncing之前等待的时间，持 续时间大于0可用于批量处理多个fsync调用，这对于较慢的磁盘或WAL写入争取时很有用。每次写入WAL的值为0s fsyncs，对于非SSD磁盘，建议使用0-100ms范围内。
wal-fsync-delay="0s"

# 验证传入的写操作以确保密钥仅具有有效的unicode字符，因为必须检查每个密钥，所以此设置会产生少量的开销，默认false。
validate-keys=false

# 是否开启tsm引擎查询日志，查询日志对于故障排除很有用，但会记录查询中包含的所有敏感数据
query-log-enabled=true

# 是否开启跟踪trace日志
trace-logging-enabled=false




```





#### [coordinator] 查询管理

```

# 写操作超时时间，默认10s
write-timeout="10s"

# 最大并发查询数，0无限制
max-concurrent-queries=0

# 查询操作超时时间
query-timeout="0s"

# 慢查询超时时间
log-queries-after="0s"

# select语句可以处理最大点数（points）
max-select-point=0

# select语句可以处理最大级数
max-select-series=0

# select语句可以处理最大group by time()的时间周期
max-select-buckets=0

# select 语句可以处理
max-select-buckest=0

```



#### [retention] 保留策略

用于控制淘汰旧数据的保留策略的执行。

```
# 是否启用该模块，默认true，设置为false可以防止influxdb强制执行保留策略
enabled=true

# 检查时间间隔
check=interval="30m0s"


```



#### [shard-precreation] 分区预创建

设置控制分片的增量，以便在数据到达之前可以使用分片，只有创建后将在未来具有开始时间和结束时间的分片才会被创建，永远不会预先创建全部或部份过去的碎片。

```
# 是否启用该模块
enabled=true

# 检查时间间隔
check-interval="10m"

# 预创建分区的最大提前时间
advance-period="30m"


```



#### [monitor] 监控设置

控制influxdb系统的自我监视，默认情况下influxdb将数据写入_internal数据库，如果该数据库不存在，influxdb会自动创建它。_internal数据库上的default保留策略为7天。如果要使用7天以外的策略则必须创建它。

```
# 是否启用该模块
store-enabled=true

# 默认数据库
store-database="_internal"

# 统计间隔
store-interval="10s"

```



#### [http] 端点设置

设置控制influxdb如何配置http端点，这些是将数据传入或传出influxdb的请主要机制。

```
# 是否启用该模块
enabled=true

# 是否启用流查询端点
flux-enabled=false

# 是否启用流查询日志
flux-log-enabled=false

# 绑定地址
bind-address=":8086"

# 是否开启认证
auth-enabled=false

# 是否开启http请求日志
log-enabled=true

# 在启用日志时是否应禁止http写入请求日志
suppress-write-log=false

# 是否开启写操作日志
write-tracing=false

# 是否开启pprof，此端点用于故障排除和监视
pprof-enabled=true

# 是否在/debug端点上启用身份验证
pprof-auth-enabled=false

# 启用默认的pprof端点并绑定到6060端口，对于调试启动性能问题很有用
debug-pprof-enabled=false

# 在/ping /metrics端点上启用身份验证
ping-auth-enabled=false

# 是否启https
https-enabled=false

# https要使用的ssl证书路径
https-certificate="/etc/ssl/influxdb.pem"

# 设置为https私钥
https-private-key=""

# 系统在非分块查询中可以返回的最大行数
max-row-limit=0

# 最大连接数，超出限制的新连接将被删除
max-connection-limit=0

# 用于使用jwt令牌验证公共api请求的共享密钥
shared-secret=""

# 发出基本身份验证询问时发送回的默认域
realm="InfluxDB"

# 通过UNIX域套接字启用HTTP服务。 要通过UNIX域套接字启用HTTP服务，请将值设置为true。
unix-socket-enabled = false

# unix-socket路径，默认值："/var/run/influxdb.sock"。
bind-socket = "/var/run/influxdb.sock"

# 客户端请求正文的最大大小（以字节为单位）， 将此值设置为0将禁用该限制。默认值：25000000。
max-body-size = 25000000

# 启用HTTP请求日志记录时，此选项指定应写入日志条目的路径。
access-log-path = ""

# 并发处理的最大写入次数，将此设置为0将禁用该限制。默认值：0。
max-concurrent-write-limit = 0

# 排队等待处理的最大写入次数。将此设置为0将禁用该限制。默认值：0。
max-enqueued-write-limit = 0

# 写入等待队列中写入的最长持续时间。将此设置为0或将max-concurrent-write-limit设置为0将禁用该限制。默认值：0
enqueued-write-timeout = 0

```



#### [logging] 日志设置

控制记录器如何将日志输出

```
# 确定日志的编码器 auto、logfmt、json。如果输出终端是TTY则auto将使用友好的输出格式
format="auto"

# 日志级别
level="info"

# 禁止在程序启动时打印出徽标
suppres-logo=false
```



#### [subscriber] 订阅设置

控制Kapacitor如何接收数据

```
# 是否启用该模块
enabled=true

# http超时时间
http-timeout="30s"

# 是否允许不安全的证书，当测试自己签发的证书时比较有用
insecure-skip-verify=false

# 设置CA证书
ca-certs=""

# 设置并发数目
write-concurrency=40

# 设置buffer大小
write-buffer-size=1000

```



#### [graphite]设置

控制Graphite数据的一个或多个侦听器。Graphite 是一款开源的，易扩展的，使用简便的监控绘图工具。

```
# 是否启用该模块，默认值 ：false。
enabled=false

# 默认端口。
bind-address=":2003"

# 数据库名称，默认值：“graphite”。
database="graphite"

# 存储策略，无默认值。
retention-policy=""

# 一致性等级，默认值：“one”。
consistency-level="one"

# 是否开启tls，默认值：false。
tls-enabled=false

# 证书路径，默认值："/etc/ssl/influxdb.pem"。
certificate="/etc/ssl/influxdb.pem"


# 这些下一行控制批处理的工作方式。 您应该已启用此功能，否则您可能会丢失指标或性能不佳。 仅通过telnet协议接收的指标进行批处理。如果这么多点被缓冲，请刷新。默认值：1000。
batch-size=1000

# 内存中可能挂起的批次数，默认值：5。
batch-pending=5

# 即使输入未达到配置的批量大小，输入也会至少刷新一次。默认值：“1s”。
batch-timeout="1s"

# 出错时是否记录日志，默认值：true。
log-point-errors=true
```



#### [udp] 设置

设置使用UDP控制InfluxDB线路协议数据的侦听器。

```
# 是否启用该模块，默认值：false。
enabled = false

# 绑定地址，默认值：":8089"。
bind-address = ":8089"

# 数据库名称，默认值：“udp”。
database = "udp"

# 存储策略，无默认值。
retention-policy = ""

# 的行控制批处理的工作原理。 已启用此功能，否则您可能会丢失指标或性能不佳。 如果有很多进入，批处理将缓冲内存中的点。如果这么多点被缓冲，则刷新，默认值：5000。
batch-size = 5000

# 如果这么多点被缓冲，请刷新，默认值：10。
batch-pending = 10

# udp读取buffer的大小，0表示使用操作系统提供的值，如果超过操作系统的默认配置则会出错。 默认值：0。
read-buffer = 0

# 即使输入未达到配置的批量大小，输入也会至少刷新一次。默认值：“1s”。
batch-timeout = "1s"

# 解码时间值时使用的时间精度。 默认值为纳秒，这是数据库的默认值。
precision = ""

```



#### [opentsdb]

#### [continuous_queries] 连续查询设置

设置influxdb中连续查询CQ的运行方式，是在最近的时间间隔内执行的自动查询批次，influxdb每个group by time()间隔执行一个自动生成的查询。

```
# 是否开启CQs功能
enabled=true

# 是否开启日志
log-enabled=true

# 控制是否将查询记录到自我监控数据存储
query-stats-enabled=false

#检查连续查询是否需要运行的时间间隔，默认值：“1s”。
run-interval = "1s"
```



