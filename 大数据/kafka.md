kafka是一个分布式的基于发布/订阅模式的消息列，主要应用大数据实时处理领域。分布式事件流平台Event Streaming Platform。


消息队列的优缺点:
* 解耦
* 缓存、削峰
* 异步通信

mq模式
* 点对点模式
  消费者主动拉取数据，消息接收后清除消息
* 发布/订阅模式
  多个消费端可以订阅多个主题，消费后数据不删除。其它消费端可以继续消费。

副本只作为备份，只有当leader挂了，才切换使用。

kafka-topics.sh 查看topic
--bootstrap-server xx.xx.xx.xx:9092
--topic xxx 操作topic的名称
--create 创建
--delete 删除
--alter 修改
--list 查看所有主题
--describe 查看topic详情
--partitions n 设置分区数
--replication-factor n 分区副本数
--config key:value 更新系统默认配置

创建分区
kafka-topics.sh --bootstrap-server xx.xx.xx.xx:9092  --create --partitions 1 --replication-facor 3 --topic firsttopic

生产数据
kafka-console-producer.sh --bootstrap-server xxx:9092 --topic topicname
消费数据
kafka-console-consumer.sh --bootstrap-server xxx:9092 --topic topicnmae
--for-begin

batch.size：只有数据积累到size之后，sender才会发送数白白已大，默认16k。
linger.ms：如果数据迟迟未达到batch.size，sender会等待linger.ms设置的时间之后就会发送数据。单位ms，默认0ms，表示没有延迟。

kafka应答机制
* 0：生产者发送过来的数据，不需要等数据落盘就应答。
* 1：生产者发送过来的数据，leader收到数据后就应答。
* -1:生产者发送过来的数据，leader和ISR队列里面所有的节点收齐数据后应答。

spring kafka生产者
```
properties.put("","");
KafKaProducer<String,String> kafkaProducer=new KafKaProducerr<>(properties);
kafkaProducer.send(new ProducerRecord<>("topic","value"));
kafkaProducer.close();

//回调
@Override
public void onCompletion(RecordMetadata metadata,Exception exception){
    metadata.topic();
    metadata.partition();
}

//同步
kafkaProducer.send().get();
```

分区partition
每个partition在一个broker上存储，可以把海量的数据按照分区切割成一块一块数据存储在多台broker上，合理控制分区的任务，可以实现负载的效果。
提高并行度，生产者可以以分区为单位发送数据，消费都可以分区为单位进行消费数据。

默认分区策略DefaultPartitioner
没有指定partition时，按key的hash值与topic的partition数进行取余，得到partition值。
如果没有指定partition和key，则采用sticky partition黏性分区，随机选择一个分区，并尽可能的使用该分区。等该分区的batch已经满，再随机选择一个分区。

自定义分区
```
propertis.put("partitioner.class","自定义分区类名")
public class MyPartitioner implements Partitioner{
    @Override
    public int partition(String topic,Object key,byte[] key……){

    }
}
```
提高生产者的吐突量
```
缓冲区大小
properties.put(BUFFER_MEMORY_CONFIG,n);

批次大小
properties.put(BATCH_SIZE_CONFIG,n);

等待时间 linger ms
properties.put(LINGER_MS_CONFIG,1);

压缩
properties.put(CONPRESSION_TYPE_CONFIG,"snappy");
none、gzip、snappy、lz4､zstd

retrys
```
数据的可靠性
* acks=0 生产者发送数据就不管了，可靠性差，效率高。很少使用
* acks=1 生产者发送数据等待leader应答，要靠性中等，效率中等，允许个别数据丢失。
* acks=-1 生产者发送数据后需要leader、isr里所有的应答，可靠性高，效率低，一般用于传送敏感数据。
  但可能会出现数据重复问题。


---------
* 至少一次(at least once)=acks:-1 分区副本>=2 isr应答>=2
* 最多一次(at most once)=acks=0
* 精确一次=幂等性+至少一次
  * 幂等性：生产者不论发送多少次重复的数据，只会持久化一条，保证不会重复。
    enable.idempotence=true
事笔墨
propertis.put(TRANSACTIONAL_ID_CONFIG,"事务ID");
kafkaProducer.initTransactions();
kafkaProducer.beginTransaction();

kafkaProducer.commitTransaction();
kafkaProducer.abortTransaction();
kafkaProducer.close();
基本信息
 生产者、消费者、broker、zk
 副本默认1个，可配置2-3个


prettyzoo客户端 zookeeper
zookeeper中存储kafka的信息
* admin
  * delete_topics
* brokers
  * ids
  * topics
  * seqid
* clusterr
  * id
* consumers
* controller 辅助leader选举的信息
* config
  * brokers
  * changes
  * topics
  * clients
  * users
* log_dir_event_notification
* latest_producer_id_block
* lsr_change_notification
* Controller_epoch
* 

 kafka特性
 * 磁盘顺序读写
 * 页缓存 避免使用jvm
 * 零拷贝 内核态-用户态的拷贝
 * 批量操作


* 防止消息丢失
* 防止消息重复消费
* 避免数据积压

常用配置
```
borker.id=0
log.dirs=/xx/xx
zookeeper.connect=xx.xx.xx:2181,xxx:2181/kafka
```


kafka-eagle监控平台


spring集成kafka
```
kafka-clients 3.0.0
```