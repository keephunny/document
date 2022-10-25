

### pom依赖
```
    <dependency>
        <groupId>org.eclipse.paho</groupId>
        <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
        <version>1.2.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.integration</groupId>
        <artifactId>spring-integration-mqtt</artifactId>
        <version>5.5.13</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.integration</groupId>
        <artifactId>spring-integration-stream</artifactId>
        <version>5.5.13</version>
    </dependency>
```


### application.yml
```
    mqtt:
    test:
        url: tcp://192.168.121.130:1883
        clientId: testmqtt #连接的一个标识
        topic: data_report/
        userName: emqx
        passWord: public
        timeout: 5000
        keepAlive: 10
```

### 主程序
```
    public class Application implements CommandLineRunner {
        private final Logger logger = LoggerFactory.getLogger(Application.class);
        @Autowired
        MqttClient client;
        @Autowired
        MqttConfig mqttConfig;

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

        @Override
        public void run(String... args) throws Exception {
            try {
                MqttMessage message = new MqttMessage();
                message.setQos(0);
                message.setRetained(false);
                for (int i = 0; i < 10; i++) {
                    message.setPayload(("1241234&" + i).getBytes());
                    client.publish("data_report/", message);
                    logger.info("发送成功：{}", i);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
```

消费程序
```
    public class MqttConsume implements MqttCallback {
        private final Logger logger = LoggerFactory.getLogger(MqttConsume.class);

        //连接丢失
        @Override
        public void connectionLost(Throwable throwable) {
            logger.info("连接丢失");
        }

        @Override
        public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
            logger.info("收到消息：topic:{},Qos:{},msg:{}", topic, mqttMessage.getQos(), new String(mqttMessage.getPayload()));
        }

        //传输完成
        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            logger.debug("传输完成");
        }
    }
```