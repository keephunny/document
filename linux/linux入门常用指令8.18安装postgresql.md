
### 下载安装包

sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
sudo yum install -y postgresql14-server
sudo /usr/pgsql-14/bin/postgresql-14-setup initdb
sudo systemctl enable postgresql-14
sudo systemctl start postgresql-14

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
