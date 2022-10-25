

### 设备连接
#### MQTT接入
MQTT 接入方式为设备和云平台提供双向连接，设备既可上报属性数据，也可接收云端的消息下发。
对于安全要求较高，并且有较多计算资源的设备，可以使用基于 SSL/TLS 的 MQTT 身份验证方式。
```
mqtt://bj-3-mqtt.iot-api.com:1883
MQTT 地址: bj-3-mqtt.iot-api.com 
MQTT 端口: 1883 
```


#### HTTP接入
HTTP 接入方式较为简单快捷，适用于只上报数据，而不需要云端控制的设备。
```
http://bj-3-api.iot-api.com


```


### TCP接入
TCP 接入方式为设备提供了最原始的 TCP/IP 传输方式，支持 ASCII、HEX、JSON 等有效负载，适合一些自定义协议的通信场景，也适用于 TCP 透传方式。

* Binary 二进制消息
* Plaintext 文本消息
* JSON 格式消息
* Modbus RTU
* DL/T645-2007
* HJ212-2017

```
tcp://bj-3-device.iot-api.com:28801
TCP 地址:bj-3-device.iot-api.com 
TCP 端口:28801 
```