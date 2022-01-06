### MQTT
MQTT：Message Queuing Telemetry Transport，消息防列遥测传输，是IBM开发的一个即时通讯协议，比较适合低宽宽不可靠的网络进行远程传感器和控制设备通讯等。，是一种基于发布/订阅（publish/subscribe）模式的轻量级协议，该协议构建于TCP/IP协议之上，


MQTT是二进制的协议，控制字段是精确到Bit级别的，MQTT是不支持分包等机制，并不适宜一些数据包特别大的应用场景。

### MQTT消息类型
* CONNECT：客户端连接到MQTT代理
* CONNACK：连接确认
* PUBLISH：新发布消息
* PUBACK：新发布消息确认
* PUBREC：QoS 2消息流的第一部分，表示消息发布已记录
* PUBREL：QoS 2消息流的第二部分，表示消息发布已释放
* PUBCOMP：QoS 2消息流的第三部分，表示消息发布完成
* SUBSCRIBE：客户端订阅某个主题
* SUBACK：对于SUBSCRIBE消息的确认
* UNSUBSCRIBE：客户端终止订阅的消息
* UNSUBACK：对于UNSUBSCRIBE消息的确认
* PINGREQ：心跳
* PINGRESP：确认心跳
* DISCONNECT：客户端终止连接前优雅地通知MQTT代理


### MQTT代理
#### mosquitto
是一款开源的轻量级的C实现实现了MQTT V3.1的开源消息代理软件，提供轻量经的，支持发布/订阅的消息推送模式。



MQTX

https://mqttx.app/zh

