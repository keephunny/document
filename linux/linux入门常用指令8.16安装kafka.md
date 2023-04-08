
### 下载安装包
操作系统:CentOS-7-x86_64-DVD-1810 


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


```

### 卸载程序
```

```


### 配置调试 
命令行工具
[root@localhost src]#  

#### 文件目录
```
[root@localhost ]#  
``` 


```
[root@localhost ]# bin/kafka-server-start.sh -daemon  config/server.properties
[root@localhost ]# 

```
 

### 开机自启

```
[root@localhost ]# vim /etc/systemd/system/kafka.service
    [Unit]
    Description=kafka.service
    After=network.target remote-fs.target
    
    [Service]
    Type=forking
    Environment=JAVA_HOME=/usr/local/java
    ExecStart=/usr/local/kafka/bin/kafka-server-start.sh -daemon /usr/local/kafka/config/server.properties
    ExecStop=/usr/local/kafka/bin/kafka-server-stop.sh
    Restart=always
    RestartSec=5
    StartLimitInterval=0
    
    [Install]
    WantedBy=multi-user.target
```
启动服务进程：systemctl start   


### 常用指令

```
#topic管理
[root@localhost ]# ./kafka-topics.sh --create --topic topicname --zookeeper localhost:2181 --partitions 1  --replication-factor 1 

查看topic列表
[root@localhost ]# ./kafka-topics.sh --list --zookeeper localhost:2181

查看topic详情
[root@localhost ]# ./kafka-topics.sh --describe --topic demo --zookeeper localhost:2181
    Topic: demo	TopicId: mQT1IqMDSg28vs51Lab6cA	PartitionCount: 1	ReplicationFactor: 1	Configs: 
	Topic: demo	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
删除topic
[root@localhost ]# ./kafka-topics.sh --delete  --topic demo --zookeeper localhost:2181
修改topic
[root@localhost ]# ./kafka-topics.sh --alter --topic topicname --zookeeper localhost:2181 --partitions 1  --replication-factor 1 

#生产
[root@localhost ]# ./kafka-console-producer.sh --topic demo --broker-list 192.168.121.130:9092
    >dddd
    回车

#消费
[root@localhost ]# ./kafka-console-consumer.sh --topic demo --bootstrap-server 192.168.121.130:9092
    --group 1000phone \		#消费者对应的消费者组
    --offset earliest \		#从什么位置(消息的偏移量)开始消费
    --partition 2		    #消费哪一个分区中的数据
[root@localhost ]#
[root@localhost ]#

```


# 查看kafka的topic
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --list

# 创建topic，并设置分区数和副本数，注意：如果你是集群，副本数值小于等于集群数，单机只能是1
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic demo --create --partitions 1 --replication-factor 1

# 查看主题的详细信息
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic demo --describe

# 修改主题，注意：分区数只能升不能降
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic demo --alter --partitions 3

# 删除主题
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic demo --delete

### 常见问题

```
Java HotSpot(TM) 64-Bit Server VM warning: INFO: os::commit_memory(0x00000000c0000000, 1073741824, 0) failed; error='Cannot allocate memory' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 1073741824 bytes for committing reserved memory.
# An error report file with more information is saved as:

[root@iotserver1 kafka]# vim bin/kafka-server-start.sh 
if [ "x$KAFKA_HEAP_OPTS" = "x" ]; then
    export KAFKA_HEAP_OPTS="-Xmx512M -Xms512M"
fi

```