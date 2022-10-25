
### 下载安装包
操作系统:CentOS-7-x86_64-DVD-1810
TDengine：TDengine-server-2.6.0.18-Linux-x64.tar.gz



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
[root@localhost ]# ./install.sh 
  Start to install TDengine...
  Created symlink from /etc/systemd/system/multi-user.target.wants/taosd.service to /etc/systemd/system/taosd.service.

  System hostname is: localhost.localdomain

  Enter FQDN:port (like h1.taosdata.com:6030) of an existing TDengine cluster node to join
  OR leave it blank to build one:

  Enter your email address for priority support or enter empty to skip: 

  To configure TDengine : edit /etc/taos/taos.cfg
  To configure taosadapter (if has) : edit /etc/taos/taosadapter.toml
  To start TDengine     : sudo systemctl start taosd
  To access TDengine    : taos -h localhost.localdomain to login into TDengine server

  TDengine is installed successfully!


```

### 卸载程序
```

```


### 配置调试 
命令行工具
[root@localhost src]# taos
  CREATE DATABASE demo;
  USE demo;
  CREATE TABLE t (ts TIMESTAMP, speed INT);
  INSERT INTO t VALUES ('2019-07-15 00:00:00', 10);
  INSERT INTO t VALUES ('2019-07-15 01:00:00', 20);
  SELECT * FROM t;

#### 文件目录
```
[root@localhost ]# cd /usr/local/taos
  drwxr-xr-x.  2 root bin
  drwxr-xr-x.  2 root cfg
  lrwxrwxrwx.  1 root data -> /var/lib/taos
  drwxr-xr-x.  2 root driver
  drwxr-xr-x. 11 root examples
  drwxr-xr-x.  2 root include
  lrwxrwxrwx.  1 root log -> /var/log/taos
```
自动生成配置文件目录、数据库目录、日志目录。
配置文件缺省目录：/etc/taos/taos.cfg， 软链接到 /usr/local/taos/cfg/taos.cfg；
数据库缺省目录：/var/lib/taos， 软链接到 /usr/local/taos/data；
日志缺省目录：/var/log/taos， 软链接到 /usr/local/taos/log；
/usr/local/taos/bin 目录下的可执行文件，会软链接到 /usr/bin 目录下；
/usr/local/taos/driver 目录下的动态库文件，会软链接到 /usr/lib 目录下；
/usr/local/taos/include 目录下的头文件，会软链接到到 /usr/include 目录下；



连接数据库
```
[root@localhost ]# taos
  taos> 
[root@localhost ]# taos
  -h HOST: 要连接的 TDengine 服务端所在服务器的 FQDN, 默认为连接本地服务
  -P PORT: 指定服务端所用端口号
  -u USER: 连接时使用的用户名
  -p PASSWORD: 连接服务端时使用的密码
  -?, --help: 打印出所有命令行参数
[root@localhost ]# 

```
 

### 开机自启
启动服务进程：systemctl start taosd

停止服务进程：systemctl stop taosd

重启服务进程：systemctl restart taosd

查看服务状态：systemctl status taosd

6041端口
systemctl start taosadapter
systemctl stop taosadapter
systemctl status taosadapter

### 常见错误

```

```

```
Exception in thread "main" java.lang.UnsatisfiedLinkError: no taos in java.library.path
 Cause: java.sql.SQLException: TDengine ERROR (8000000b): Unable to establish connection
vim taos.cfg
  firstEp                   iotserver1:6030
  fqdn                      iotserver1
vim /var/lib/taos/dnode/dnodeEps.json
  {
    "dnodeNum": 1,
    "dnodeInfos": [{
      "dnodeId": 1,
      "dnodeFqdn": "iotserver1",
      "dnodePort": 6030
    }]
  }

 本地windows系统一定要安装客户端 tdengine-client
 hostname也要配置

```