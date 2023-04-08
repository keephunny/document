
### 下载安装包
https://dlcdn.apache.org/cassandra/3.0.28/apache-cassandra-3.0.28-bin.tar.gz
操作系统:CentOS-7-x86_64-DVD-1810
apache-cassandra-3.0.28-bin.tar.gz

https://blog.csdn.net/qq_42553836/article/details/118939522
https://www.cnblogs.com/wwjj4811/p/16008475.html

```
#查看是否已安装过
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 



```

### 前置配置

```
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
```

### 安装程序
```
[root@localhost ]#  


```

### 卸载程序
```
#源码安装卸载
[root@localhost ]#  
[root@localhost ]# 
```


### 配置调试 
命令行工具
```
[root@localhost src]# taos
  CREATE DATABASE demo;
  USE demo;
  CREATE TABLE t (ts TIMESTAMP, speed INT);
  INSERT INTO t VALUES ('2019-07-15 00:00:00', 10);
  INSERT INTO t VALUES ('2019-07-15 01:00:00', 20);
  SELECT * FROM t;
```


集群配置
 130节点、131节点
```
#130
[root@localhost ]# vim conf/cassandra.yaml 
  #种子节点
  -seeds: "192.168.1.130"
  listen_address: 192.168.1.130
  start_rpc: true
  rpc_address: 192.168.1.130
[root@localhost ]# 
#131
[root@localhost ]# vim conf/cassandra.yaml 
  #种子节点
  -seeds: "192.168.1.130"
  listen_address: 192.168.1.131
  start_rpc: true
  rpc_address: 192.168.1.131
[root@localhost ]# 
# 查询环状
[root@localhost ]# ./nodetool ring
# 查看集群物理机状态
[root@localhost ]# ./nodetool status 
  Datacenter: datacenter1
  =======================
  Status=Up/Down
  |/ State=Normal/Leaving/Joining/Moving
  --  Address          Load       Tokens       Owns (effective)  Host ID                               Rack
  UN  192.168.121.130  314.99 KB  256          100.0%            56b7e15d-faac-4622-b6bc-497070ffd798  rack1
  UN  192.168.121.131  184.22 KB  256          100.0%            deca7291-cc61-44c7-8ec8-666dffb7dffb  rack1



```

#### 文件目录

```
[root@localhost ]# cd /usr/local/t
```



连接数据库
```
[root@localhost ]#  bin/cqlsh 192.168.121.131
  cqlsh> describe keyspaces;
    system_schema  iot_spaces  system_distributed
    system_auth    system      system_traces
[root@localhost ]# 

```


### 开机自启
启动服务进程：systemctl start   


### 目录文件

```
```



### 常见错误

```

```




#!/bin/sh
CASSANDRA_DIR="/usr/local/cassandra"
echo "************cassandra***************"
case "$1" in
  start)
    echo "*                                  *"
    echo "*            starting              *"
    nohup $CASSANDRA_DIR/bin/cassandra -R >> $CASSANDRA_DIR/logs/system.log 2>&1 &
    echo "*            started               *"
    echo "*                                  *"
    echo "************************************"
    ;;
  stop)
    echo "*                                  *"
    echo "*           stopping               *"
    PID_COUNT=`ps aux |grep CassandraDaemon |grep -v grep | wc -l`
    PID=`ps aux |grep CassandraDaemon |grep -v grep | awk {'print $2'}`
    if [ $PID_COUNT -gt 0 ];then
            echo "*           try stop               *"
            kill -9 $PID
            echo "*          kill  SUCCESS!          *"
    else
            echo "*          there is no !           *"
    echo "*                                  *"
    echo "************************************"
    fi
    ;;
  restart)
    echo "*                                  *"
    echo "*********     restarting      ******"
    $0 stop
    $0 start
    echo "*                                  *"
    echo "************************************"
    ;;
  status)
    $CASSANDRA_DIR/bin/nodetool status
    ;;
  *)
  echo "Usage:$0 {start|stop|restart|status}"
  exit 1
esac


### 常用命令

DESCRIBE KEYSPACES; 查看keyspace
$nodetool settimeout read 360000
cqlsh -e "SELECT COUNT(*) FROM table;" --request-timeout=3600


时区
;; Display timezone
;timezone = Etc/UTC
https://www.pianshen.com/question/22001321502/


查询时间偏差8小时
```
device_id | ts                       | device_name | humidity | id        | power | press | temperature
-----------+--------------------------+-------------+----------+-----------+-------+-------+-------------
  dev01990 | 2022-12-31 00:47:15+0000 |    dev01990 |     34.5 | 147635423 |  null |  null |        null

问题分析：
时间戳没有问题，是cqlsh显示的问题，配置时区。
[root@ cassandra]# yum install pytz -y
[root@ cassandra]# vim conf/cqlshrc.sample 
  [ui]
  ;; Display timezone
  ;timezone = Etc/UTC
  timezone = Asia/Shanghai
[root@ cassandra]# bin/cqlsh --cqlshrc=./conf/cqlshrc.sample  192.168.1.1 9042
  device_id | ts                       | device_name | humidity | id        | power | press | temperature
  -----------+--------------------------+-------------+----------+-----------+-------+-------+-------------
    dev01990 | 2022-12-31 08:47:15+0800 |    dev01990 |     34.5 | 147635423 |  null |  null |        null
[root@ cassandra]# 
[root@ cassandra]# 
[root@ cassandra]# 
```