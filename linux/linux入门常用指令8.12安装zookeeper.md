 ### 安装包下载

```
https://www.apache.org/

https://dlcdn.apache.org/zookeeper/zookeeper-3.8.0/apache-zookeeper-3.8.0-bin.tar.gz
```

[apache-zookeeper-3.5.10-bin.tar.gz](./resources/apache-zookeeper-3.5.10-bin.tar.gz)




```
[root@localhost ]# tar -zxvf apache-zookeeper-3.8.0-bin.tar.gz
[root@localhost ]# mv apache-zookeeper-3.8.0-bin /usr/local/

#配置文件
[root@localhost ]# cp ./conf/zoo_sample.cfg  ./conf/zoo.cfg
[root@localhost ]# ./zkServer.sh start
[root@localhost ]# ./zkServer.sh status

#配置环境变量
[root@localhost ]# vim /etc/profile
	ZOOKEEPER_HOME=/usr/local/zookeeper
    PATH=$PATH:$ZOOKEEPER_HOME/bin
    export PATH ZOOKEEPER_HOME PATH
[root@localhost ]# source /etc/profile

#Error: JAVA_HOME is not set and java could not be found in PATH.
[root@localhost ]# vi conf/zkEnv.sh
	JAVA_HOME="/usr/local/java"
[root@localhost ]# 
#开机自启
[root@localhost ]# vim /usr/lib/systemd/system/zookeeper.service
    [Unit]
    Description=zookeeper
    After=network.target remote-fs.target nss-lookup.target
    [Service]
    Type=forking
    ExecStart=/usr/local/zookeeper/bin/zkServer.sh start
    ExecReload=/usr/local/zookeeper/bin/zkServer.sh restart
    ExecStop=/usr/local/zookeeper/bin/zkServer.sh stop
    [Install]
    WantedBy=multi-user.target

[root@localhost ]# systemctl enable zookeeper
[root@localhost ]# systemctl start zookeeper
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
```

```
[root@localhost zookeeper-3.8.0]# ./bin/zkServer.sh start
    /usr/local/java/bin/java
    ZooKeeper JMX enabled by default
    Using config: /usr/local/zookeeper-3.8.0/bin/../conf/zoo.cfg
    Starting zookeeper ... STARTED
[root@localhost zookeeper-3.8.0]# ./bin/zkServer.sh status
    /usr/local/java/bin/java
    ZooKeeper JMX enabled by default
    Using config: /usr/local/zookeeper-3.8.0/bin/../conf/zoo.cfg
    Client port found: 2181. Client address: localhost. Client SSL: false.
    Mode: standalone 

```

```
报错
localhost.localdomain systemd[1]: Starting zookeeper...
zkServer.sh[28661]: Error: JAVA_HOME is not set and java ...H.
systemd[1]: zookeeper.service: control process exited, co...=1
systemd[1]: Failed to start zookeeper.
systemd[1]: Unit zookeeper.service entered failed state.
systemd[1]: zookeeper.service failed.


vim bin/zkServer.sh
	#
	# If this scripted is run out of /usr/bin or some other system bin directory
	# it should be linked to and not copied. Things like java jar files are found
	# relative to the canonical path of this script.
	#

	export JAVA_HOME=/usr/local/java

```





#### 集群安装

```
server.1=node01:2888:3888
server.2=node02:2888:3888
server.3=node03:2888:3888

2888：Zookeeper服务器之间的通信端口
3888：Leader选举的端口。
```

