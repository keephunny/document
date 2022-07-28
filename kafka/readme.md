#### 消息队列有哪些作用

* 解耦：使用消息队作为两个系统之间的通讯方式。
* 异步：
* 流量削峰



rerbalance机制

partion->leader(读写) flower

副本同步机制



查看topic

```
./bin/kafka-topics.sh --bootstrap-server x.x.x.x:9092
--topic
--create 
--list 查看topic
```


* record：消息，消息队列基础通信单位
* topic：主题，目的是将消息进行分类，不同业务类型的消息通常会被分到不同的主题
* partition：分区，每个主题可以创建多个分区，每个分区都由一系列有序不可变的消息组成
* replica：副本，每个分区都有一个或多个副本，主要是存储数据，以日志对象的形式体现，副本又分为leader和follower副本。
* offset：偏移量，每个消息在日志文件中的位置都对应一个有序递增的偏移量，可以理解为数组的存储形式。
* producer：生产者，生产消息端
* consumer：消费端
* broker：代理端，一个kafka集群由一个或多个kafka实例构成，每个kafka实例就称为一个broker

kafka副本同步机制
业务数据封装成消息在系统中流转，由于各个组件是分布在不同的服务器上，所以生产者、消费者之间的数据同频可能存在一定的时间延迟，kafka通过延时范围，划分了几个不同的集合。
* AR(Assignd Replicas) 指的是已经分配数据的分区副本，通常指的是leader副本 + follower副本。
* ISR(In Sync Replicas)指的是和leader副本数据保持同步的副本集合。当follower副本数据和leader副本数据保持同步，那么这些副本就处在ISR里面，ISR集合会根据数据的同步状态动态变化。
* OSR(Out Sync Replicas)一旦follower副本的数据同步进度跟不上leader了，那么它就会被放进叫做OSR的集合里。也就是这个集合包含的是不处于同步状态的分区副本。通过replica.lag.time.max.ms这个参数来设置数据同步时间差，它的默认值是10s。
一旦从分区副本和主分区副本的消息相差10s以上，那么就认为消息处于OSR不同步的状态。若follower处于OSR集合里，那么在选取新的leader的时候就不会选举它作为新leader。