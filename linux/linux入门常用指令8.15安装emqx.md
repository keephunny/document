
### 下载安装包
操作系统:CentOS-7-x86_64-DVD-1810
EMQX：[emqx-centos7-4.2.14-x86_64.rpm](./resources/emqx-centos7-4.2.14-x86_64.rpm)

帮助文档：https://www.emqx.io/docs/zh/v4.3/
```
#查看是否已安装过
[root@localhost src]# rpm -qa|grep emqx
emqx-4.2.14-1.el8.x86_64
[root@localhost src]# rpm -e emqx-4.2.14-1.el8.x86_64

# 一定要选择与操作系统对应的版本
[root@localhost ]# wget https://www.emqx.com/zh/downloads/broker/4.2.14/emqx-centos7-4.2.14-x86_64.rpm
[root@localhost ]# rpm -ivh  emqx-centos7-4.2.14-x86_64.rpm
[root@localhost ]# emqx start
[root@localhost ]# systemctl enable emqx



```

### 前置配置 
创建用户
|描述	 |   ZIP压缩包安装	|二进制包安装   |
|配置文件目录  |	./etc  |	/etc/emqx/etc  |
|数据文件  |	    ./data  |	/var/lib/emqx/data  |
|日志文件  |	    ./log  |	/var/log/emqx  |
|启动相关的脚本  |	./releases  |	/usr/lib/emqx/releases  |
|可执行文件目录  |	./bin  |	/usr/lib/emqx/bin  |
|Erlang 代码  |	    ./lib  |	/usr/lib/emqx/lib  |
|Erlang虚拟机文件  |	./erts-*  |	/usr/lib/emqx/erts-*  |
|插件  |	./plugins  |	/usr/lib/emqx/plugins  |
```

[root@localhost ]# vim /etc/emqx/
    acl.conf       
    emqx.conf      
    plugins/       
    ssl_dist.conf  
    certs/         
    lwm2m_xml/     
    psk.txt        
    vm.args 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 

```


su postgres
pgsql
postgres=# alter user postgres with password 'xxx'

### 安装程序

#### 源码安装

#### yum安装




### 配置调试
dashboard：http://192.168.121.130:18083/
默认密码：admin/pulic
修改密码：emqx_ctl admins passwd admin admin

认证配置
```
[root@localhost ]# vim /etc/emqx/emqx.config
    # 禁止匿名登录
    allow_anonymous = false

在dashboard中开启 Plugins-emqx_auth_pgsql  EMQ X Authentication/ACL with PostgreSQL
[root@localhost ]# vim /etc/emqx/plugins/emqx_auth_pgsql.conf

    auth.pgsql.server = 127.0.0.1:5432
    auth.pgsql.username = postgres
    auth.pgsql.password = postgres
    auth.pgsql.database = mqtt
    #配置数据库中密码的加密方式为
    auth.pgsql.password_hash = sha256
    auth.pgsql.auth_query = select password from mqtt_user where username = '%u' limit 1
    

 
```
初始化pgsql脚本
```
CREATE TABLE mqtt_user (
  id SERIAL PRIMARY KEY,
  username CHARACTER VARYING(100),
  password CHARACTER VARYING(100),
  salt CHARACTER VARYING(40),
  is_superuser BOOLEAN,
  UNIQUE (username)
)

启用 PostgreSQL 认证后，你可以通过用户名： emqx，密码：public 连接。
INSERT INTO mqtt_user (username, password, salt, is_superuser)
VALUES
	('emqx', 'efa1f375d76194fa51a3556a97e641e61685f914d446979da50a551a4333ffd7', NULL, false);

    
```

发布订阅ACL
```

CREATE TABLE mqtt_acl (
  id SERIAL PRIMARY KEY,
  allow INTEGER,
  ipaddr CHARACTER VARYING(60),
  username CHARACTER VARYING(100),
  clientid CHARACTER VARYING(100),
  access  INTEGER,
  topic CHARACTER VARYING(100)
);
CREATE INDEX ipaddr ON mqtt_acl (ipaddr);
CREATE INDEX username ON mqtt_acl (username);
CREATE INDEX clientid ON mqtt_acl (clientid);

-- 所有用户不可以订阅系统主题
INSERT INTO mqtt_acl (allow, ipaddr, username, clientid, access, topic) VALUES (0, NULL, '$all', NULL, 1, '$SYS/#');
-- 允许 10.59.1.100 上的客户端订阅系统主题
INSERT INTO mqtt_acl (allow, ipaddr, username, clientid, access, topic) VALUES (1, '10.59.1.100', NULL, NULL, 1, '$SYS/#');
-- 禁止客户端订阅 /smarthome/+/temperature 主题
INSERT INTO mqtt_acl (allow, ipaddr, username, clientid, access, topic) VALUES (0, NULL, '$all', NULL, 1, '/smarthome/+/temperature');
-- 允许客户端订阅包含自身 Client ID 的 /smarthome/${clientid}/temperature 主题
INSERT INTO mqtt_acl (allow, ipaddr, username, clientid, access, topic) VALUES (1, NULL, '$all', NULL, 1, '/smarthome/%c/temperature');

```

### 开机自启
```
[root@localhost ]# systemctl enable emqx

```

### 集群配置
静态配置
```
#节点1
[root@localhost ]# vim /etc/etc/emqx.conf
  cluster.discovery = static
  cluster.static.seeds = emqx@192.168.1.1 ,emqx@192.168.1.2
  node.name = emqx@192.168.1.1

#节点2
[root@localhost ]# vim /etc/etc/emqx.conf
  cluster.discovery = static
  cluster.static.seeds = emqx@192.168.1.1 ,emqx@192.168.1.2
  node.name = emqx@192.168.1.2
[root@localhost ]# emqx restart
[root@localhost ]# 
[root@localhost ]# 


```

动态配置
```
[root@localhost ]# ./bin/emqx_ctl cluster join emqx@192.168.1.2
[root@localhost ]# ./bin/emqx_ctl cluster status
```

组播集群
```
  cluster.discovery = mcast
  cluster.mcast.addr = 239.192.0.1
  cluster.mcast.ports = 4369,4370
  cluster.mcast.iface = 0.0.0.0
  cluster.mcast.ttl = 255
  cluster.mcast.loop = on
```

退出集群
```
子节点
[root@localhost ]# ./bin/emqx_ctl cluster leave
主节点
[root@localhost ]# ./bin/emqx_ctl cluster force-leave node1@192.168.1.2
```


* 1883	MQTT/TCP 协议端口
* 11883	MQTT/TCP 协议内部端口，仅用于本机客户端连接
* 8883	MQTT/SSL 协议端口
* 8083	MQTT/WS 协议端口
* 8084	MQTT/WSS 协议端口

### 订阅系统主题
EMQX 默认只允许本机的 MQTT 客户端订阅 $SYS 主题，请参照 内置 ACL 修改发布订阅 ACL 规则。
```
[root@localhost ]# vim /etc/emqx/acl.conf
  {allow, {user, "dashboard"}, subscribe, ["$SYS/#"]}.
  {allow, {user, "emqx"}, subscribe, ["$SYS/#"]}.
[root@localhost ]# emqx restart
```
$SYS 系统消息发布周期配置项：
broker.sys_interval = 1m
* $SYS/brokers	集群节点列表
* $SYS/brokers/${node}/version	EMQX 版本
* $SYS/brokers/${node}/uptime	EMQX 运行时间
* $SYS/brokers/${node}/datetime	EMQX 系统时间
* $SYS/brokers/${node}/sysdescr	EMQX 描述

#### 监听设备上下线
topic：$SYS/brokers/+/clients/#
上线：
```
Topic: $SYS/brokers/emqx@127.0.0.1/clients/MQTT_FX_Client/connected
{"username":"emqx","ts":1668474744198,"sockport":1883,"proto_ver":4,"proto_name":"MQTT","keepalive":60,"ipaddress":"192.168.121.100","expiry_interval":0,"connected_at":1668474744198,"connack":0,"clientid":"MQTT_FX_Client","clean_start":true}
```
下线
```
Topic: $SYS/brokers/emqx@127.0.0.1/clients/MQTT_FX_Client/disconnected
{"username":"emqx","ts":1668474760481,"reason":"normal","disconnected_at":1668474760481,"clientid":"MQTT_FX_Client"}
```


### 常见问题

```
/usr/lib/emqx/bin/cuttlefish: error while loading shared libraries: libtinfo.so.6: cannot open shared object file: No such file or directory
在/usr/lib64目录下找到了libtinfo.so.5的包，虽然没有libtinfo.so.6，但有libtinfo.so.5，是可以向下兼容的 ，建一个链接即可解决此报错
解决：
[root@localhost ]# cd /usr/lib64/
[root@localhost ]# ln -s libtinfo.so.5 libtinfo.so.6
```