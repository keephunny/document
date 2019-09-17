### 配置exchange、queue
在安装并启动rabbitmq后，并不能直接使用，需要配置相关的exchange和queue。
1. 创建Exchange
![](./resources/20190717151845.png)
[Exchanges]-[Add a new exchange]
* type：topic类型，具体类型的区别参考Exchange type说明。
* Durability：durable是否持久化，重启后仍然存在。

2. 创建Queue
![](./resources/20190717152128.png)
[Queues]-[Add a new queue]


3. 配置routing key 绑定exchange和queue
![](./resources/20190717152242.png)
Routing key会识别两个通配符：符号“#”和符号“”。#匹配0个或多个单词，匹配不多不少一个单词。

### 基于springboot的生产和消费

#### pom依赖
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
```
#### yml配置文件
```
    spring:
    rabbitmq:
        addresses: 192.168.26.103:5672
        username: admin
        password: 123456
        virtual-host: /
        connection-timeout: 15000
```

#### 生产者
```
    @Component
    public class RabbitSender {
        @Autowired
        private RabbitTemplate rabbitTemplate;

        /**
        * 发送消息
        *
        * @param userInfo 消息对象
        */
        public void send(UserInfo userInfo) {
            //自定义消息唯一标识
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(userInfo.getId() + userInfo.getUserName());
            //exchange routing 消息内肉 消息唯一标识
            rabbitTemplate.convertAndSend("order-exchange", "order.rk1", userInfo, correlationData);
        }


    
    @Autowired
    private RabbitSender rabbitSender;
    //触发生产消息
    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public ResponseDto test() {
        ResponseDto responseDto = new ResponseDto();
        List<UserInfo> list = userInfoService.selectAll();
        for (UserInfo u : list) {
            rabbitSender.send(u);
        }

        return responseDto;
    }    

```

#### 消费者
```
    @Component
    public class RabbitRevicer {
        @RabbitListener(
                bindings = @QueueBinding(
                        value = @Queue(
                                value = "order-queue",
                                durable = "true"
                        ),
                        exchange = @Exchange(
                                name = "order-exchange",
                                type = "topic"
                        ),
                        key = "order.#"
                )
        )
        @RabbitHandler
        public void onOrderMsg(@Payload UserInfo userInfo, @Headers Map<String, Object> headers) {
            System.out.println("-------开始消费-------");
            System.out.println("用户ID：" + userInfo.getId());
            System.out.println("用户名：" + userInfo.getUserName());
        }
    }
```

#### 多生产者多消费者
多实例话几个生产和消费者实现。


### 普通代码实例
```
    <dependency>
        <groupId>com.rabbitmq</groupId>
        <artifactId>amqp-client</artifactId>
        <version>5.3.0</version>
    </dependency>
```

```
    import java.io.IOException;
    import java.util.concurrent.TimeoutException;

    import com.rabbitmq.client.Channel;
    import com.rabbitmq.client.Connection;
    import com.rabbitmq.client.ConnectionFactory;
    import com.rabbitmq.client.MessageProperties;

    public class RabbitProducer {
        private static final String EXCHANGE_NAME = "exchange_order";
        private static final String ROUTING_KEY = "order.#";
        private static final String QUEUE_NAME = "queue_order";
        private static final String IP_ADDRESS = "127.0.0.1";
        // RabbitMQ服务端默认端口号为5672
        private static final int PORT = 5672;   
        
        public static void main(String[] args) throws IOException, TimeoutException {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(IP_ADDRESS);
            factory.setPort(PORT);
            factory.setUsername("admin");
            factory.setPassword("123456");
            // 建立连接
            Connection connection = factory.newConnection();    
            // 创建信道
            Channel channel = connection.createChannel();       
            // 创建一个type="direct"、持久化的、非自动删除的交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
            // 创建一个持久化、非排他的、非自动删除的队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 将交换器和队列通过路由绑定
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
            // 发送一条持久化的消息：hello world!
            String message = "hello,world!";
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
                        MessageProperties.PERSISTENT_TEXT_PLAIN, 
                        message.getBytes());
            // 关闭资源
            channel.close();
            connection.close();
        }
    }
```

```

    import java.io.IOException;
    import java.util.concurrent.TimeUnit;
    import java.util.concurrent.TimeoutException;

    import com.rabbitmq.client.AMQP;
    import com.rabbitmq.client.Address;
    import com.rabbitmq.client.Channel;
    import com.rabbitmq.client.ConnectionFactory;
    import com.rabbitmq.client.Consumer;
    import com.rabbitmq.client.DefaultConsumer;
    import com.rabbitmq.client.Envelope;
    import com.rabbitmq.client.Connection;

    public class RabbitConsumer {
        private static final String QUEUE_NAME = "queue_order";
        private static final String IP_ADDRESS = "127.0.0.1";
        private static final int PORT = 5672;
        
        public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
            Address[] addresses = new Address[] {
                    new Address(IP_ADDRESS, PORT)
            };
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("zifeiy");
            factory.setPassword("passwd");
            // 这里的连接方式与生产者的demo略有不同，注意区分
            Connection connection = factory.newConnection(addresses);   
            final Channel channel = connection.createChannel(); 
            // 设置客户端最多接受未被ack的消息的个数
            channel.basicQos(64);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, 
                                AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("recv message: " + new String(body));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            channel.basicConsume(QUEUE_NAME, consumer);
            // 等待回调函数执行完毕后，关闭资源
            TimeUnit.SECONDS.sleep(5);
            channel.close();
            connection.close();
        }
    }
```