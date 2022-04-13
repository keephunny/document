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

https://docs.emqx.cn/broker/v4.3/

https://github.com/emqx/MQTTX





#### MQTT客户端的生命周期

MQTT客户端整个生命周期的行为可以分为：建立连接、订阅主题、接收消息、并处消息、向指定主题发布消息、取消订阅、断开连接。



* 建立连接：
  * 指定 MQTT Broker 基本信息接入地址与端口
  
  * 指定传输类型是 TCP 还是 MQTT over WebSocket
  
  * 如果启用 TLS 需要选择协议版本并携带相应的的证书
  
  * Broker 启用了认证鉴权则客户端需要携带相应的 MQTT Username Password 信息
  
  * 配置客户端参数如 keepalive 时长、clean session 回话保留标志位、MQTT 协议版本、遗嘱消息（LWT）等
  


* 订阅主题：连接建立成功后可以订阅主题，需要指定主题信息

  * 指定主题过滤器 Topic，订阅的时候支持主题通配符 + 与 # 的使用
  * 指定 QoS，根据客户端库和 Broker 的实现可选 Qos 0 1 2，注意部分 Broker 与云服务提供商不支持部分 QoS 级别，如 AWS IoT 、阿里云 IoT 套件、Azure IoT Hub 均不支持 QoS 2 级别消息
  * 订阅主题可能因为网络问题、Broker 端 ACL 规则限制而失败

* 接收消息并处理：

  * 一般是在连接时指定处理函数，依据客户端库与平台的网络编程模型不同此部分处理方式略有不同

* 发布消息：向指定主题发布消息

  * 指定目标主题，注意该主题不能包含通配符 + 或 #，若主题中包含通配符可能会导致消息发布失败、客户端断开等情况（视 Broker 与客户端库实现方式）
  * 指定消息 QoS 级别，同样存在不同 Broker 与平台支持的 QoS 级别不同，如 Azure IoT Hub 发布 QoS 2 的消息将断开客户端连接
  * 指定消息体内容，消息体内容大小不能超出 Broker 设置最大消息大小
  * 指定消息 Retain 保留消息标志位

* 取消订阅：

  * 指定目标主题即可

* 断开连接：

  * 客户端主动断开连接，服务器端将发布遗嘱消息（LWT）
