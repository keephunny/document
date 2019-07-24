* Server：broker、RabbitMQ Server，实现AMOP实体服务，接受客户端的连接。
* Connection：连接，应用程序与server的网络连接。
* Channel：网络信道，进行消息读写的通道，客户端可以建立多个channel，每个channel就是一个会话。
* Message：消息，服务器和应用程序之间传输的数据，由properties和body组成。
* Virtual host：虚拟地址，用于逻辑隔离，是最上层的路由，一个虚拟地址可以有多个Exchange和Queue，但不允许同名。
* Exchange：交换机，用于接收生产者的消息，根据Routing key转发到Queue。
* Queue：Message queue消息队列，保存消息并转发给消费者，消费者监听这个队列达到接收消息的目的。
* Bingding：Exchange和Queue之前的虚拟连接，可以包含多个Routing key。

生产者把消息投递到RabbitMQ Server，在投递时需要指定哪个Exchange和Routing key，因为Queue和Exchange通过Rounting key来建立规则，所以消息一经生产就能投递到具体的队列中。客户端通过监听具体的队列从而获得这个队列的消息。消息首先到server再到Virtual host再到Exchange再到Queue。

RabbitMQ所使用的 AMQP 0-9-1版本

消息队列是典型的生产者、消费者模型，生产者不断向消息队列中生产消息，消费者不断的从队列中获取消息，因为消息的生产和消费都是异步的，而且只关心消息的发送和接收，没有业务逻辑的侵入。这样就实现了生产者和消费者的解耦。

### Exchange类型
生产者将消息发送给Queue中，实际上生产者将消息发送到Exchange，由Exchange将消息路由到一个或多个Queue中。

### Routing key
生产者将消息发送给Exchange时，一般会指定一个Routing key来指定这个消息路由规则。而这个routing key需要与Exchange Type及binding key联合使用才能最终生效。 在Exchange Type与binding key固定的情况下（在正常使用时一般这些内容都是固定配置好的），我们的生产者就可以在发送消息给Exchange时，通过指定routing key来决定消息流向哪里。 RabbitMQ为routing key设定的长度限制为255 bytes。


### 节点类型
* 磁盘节点
磁盘节点顾名思义, 是将交换机, 队列以及消息都存储到硬盘上.


* 内存节点
内存节点则是在内存中保存交换机, 队列这些数据, 因此其不会像磁盘节点那样频繁访问硬盘, 所以会有更好的表现. 但注意队列内的消息数据仍然会存储在硬盘上, 故生产或消费数据并不会得到性能提升, 但对增加/删除队列这类操作还是有点作用的.  
内存节点在启动后会从集群的其他节点上同步数据, 因此请确保集群中至少存在一个磁盘节点. 内存节点非常脆弱, 一旦被关闭, 你将不能再重启它, 并会丢失所有数据. 因此建议一个集群为了性能, 可以部署少量的内存节点, 但不可以全部为内存节点.

 

* 改变节点类型
我们可以在某个节点首次加入集群时, 通过"--ram"标志, 将其声明为内存节点, 如下:
```
    rabbitmqctl stop_app
    rabbitmqctl join_cluster --ram 节点名称
    rabbitmqctl start_app

    也可以改变集群已存在节点的类型:
    rabbitmqctl change_cluster_node_type ram  # 将节点变为内存节点
    rabbitmqctl change_cluster_node_type disc  # 
```