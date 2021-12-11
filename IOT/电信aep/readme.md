### 名词解释

* 设备：具体设备添加在平台，根据设备的IMEI号添加，平台生成唯一的设备ID。





* IMEI：国际移动设备识别码(International Mobile Equipment Identity)通称序列号、串号。共15-17位，
  * 第一部分 **TAC**，Type Allocation Code，类型分配码由8位数字组成（早期是6位）86为中国TAF。
  * 第二部分 **FAC**，Final Assembly Code，最终装配地代码，由2位数字构成
  * 第三部分 **SNR**，Serial Number，序列号，由第9位开始的6位数字组成
  * 第四部分 **CD**，Check Digit，验证码，由前14位数字通过 [Luhn算法](http://en.wikipedia.org/wiki/Luhn_algorithm)计算得出。
  * 第五部分 **SVN**，Software Version Number，软件版本号



### 设备接入

#### HTTP(S)协议

​	HTTP协议设备接入平台的地址为http://http.ctwing.cn，端口为8991。

​	HTTPS协议设备接入平台的地址为https://http.ctwing.cn，端口为8992。

​	HTTP(S)协议仅支持JSON数据类型的报文







### 订阅推送

1. HTTP消息推送：物联网开放平台将符合订阅条件的设备信息以HTTP方式推送至北向应用。

2. MQ消息推送：物联网开放平台的消息队列服务，提供基于主题和消息缓存的可靠消息推送服务。

HTTP推送方式适用于消息量较少（一般小于1000TPS）且对少量消息丢失不敏感的应用；MQ推送方式适用于消息量更大或对可靠性要求较高的应用。MQ方式提供数据缓存，实时性和可靠性更强，不会因为网络等因素使数据丢失。

#### 订阅消息格式

<table>
    <tr>
    <th style="width:15%;">接入协议</th>
    <th style="width:85%;">消息类型</th>
    </tr>
    <tr>
    <td>TLINK</td>
    <td>设备数据变化通知、设备指令响应通知、设备事件上报通知、设备上下线通知</td>
    </tr>
    <tr>
    	<td>MQTT</td>
    	<td>设备数据变化通知、设备指令响应通知、设备事件上报通知、设备上下线通知</td>
    </tr>
    <tr>
    	<td>LWM2M</td>
    	<td>设备数据变化通知、设备指令响应通知、设备事件上报通知、设备上下线通知、设备数据批量变化通知</td>
    </tr>
    <tr>
    	<td>NB网关</td>
    	<td>设备数据变化通知、设备指令响应通知、设备事件上报通知、设备上下线通知、设备数据批量变化通知</td>
    </tr>
    <tr>
    	<td>HTTP(S)</td>
    	<td>设备数据变化通知、设备事件上报通知、设备上下线通知</td>
    </tr>
    <tr>
    	<td>TCP</td>
    	<td>设备数据变化通知、设备指令响应通知、设备事件上报通知、设备上下线通知</td>
    </tr>
</table>

1. 设备数据变化

   <table>
       <tr>
       	<th>参数名</th>
           <th>说明</th>
           <th>类型</th>
           <th>必填</th>
       </tr>
       <tr>
       	<td>tenantId</td>
       	<td>租户ID</td>
       	<td>String</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>productId</td>
       	<td>产品ID</td>
       	<td>String</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>deviceId</td>
       	<td>设备ID</td>
       	<td>String</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>messageType</td>
       	<td>消息类型='dataReport'</td>
       	<td>String</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>IMEI</td>
       	<td>NB终端设备识别号</td>
       	<td>String</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>IMSI</td>
       	<td>NB终端sim卡标识</td>
       	<td>String</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>deviceType</td>
       	<td>设备标识</td>
       	<td>String</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>topic</td>
       	<td>数据上报主题</td>
       	<td>String</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>assocAssetId</td>
       	<td>合作伙伴ID</td>
       	<td>String</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>timestamp</td>
       	<td>时间戳</td>
       	<td>Long</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>upPacketSN</td>
       	<td>上行报文序号</td>
       	<td>int</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>upDataSN</td>
       	<td>数据上报报文序号</td>
       	<td>int</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>serviceId</td>
       	<td>服务ID</td>
       	<td>String</td>
       	<td>N</td>
       </tr>
       <tr>
       	<td>protocol</td>
       	<td>协议类型</td>
       	<td>String</td>
       	<td>Y</td>
       </tr>
       <tr>
       	<td>payload</td>
       	<td>消息负载，非透传消息格式为payload:消息内容JSON；透传消息格式为payload:{"APPdata":"消息内容BASE64编码"}</td>
       	<td>json</td>
       	<td>Y</td>
       </tr>
   </table>
   示例数据
    ```java
    {
        "deviceId": "2d1f1a708b5d4cef880937d67b5e5842",
        "IMEI": "",
        "IMSI": "",
        "deviceType": "",
        "tenantId": "1",
        "productId": "1503",
        "messageType": "dataReport",
        "topic": "v1/up/ad",
        "assocAssetId": "",
        "timestamp": 1528183784371,
        "payload": {
            "SignalPower": -792,
            "SNR": -55,
            "TxPower": 50,
            "CellId": 66966098,
            "Length": 3,
            "Updata": "REVG"
        },
        "upPacketSN": -1,
        "upDataSN": -1,
        "serviceId": "",
        "protocol": "tup"
    }
    ```





1. 设备指令响应
2. 设备事件上报
3. 设备上下线
4. TUP合并数据变化



#### HTTP订阅推送

订阅接收方收到消息后，需固定返回HTTP 200，其它返回码平台会认为推送失败。消息推送支持失败重传机制，推送失败（超时、HTTP返回码不等于200等）的消息，平台最多会重试3次。如果消息接收服务连续失败次数超过100次或者最近10s推送失败率超过10%，平台会认为该服务暂时不可用，标记该消息接收服务处于失败冷却状态，1分钟内不再向该消息接收服务推送消息。

推送服务支持HTTPS证书安全认证。推送时，北向消息接收服务作为服务端、平台消息推送服务作为客户端。北向消息接收服务端可通过平台提供的[安全证书](https://www.ctwing.cn/r/cms/www/default/doc/helpdoc/aep-msgpush.crt) 对平台进行客户端身份认证。以Nginx为例，北向消息接收服务开启对平台安全认证的参考配置如下：

```
server {
	listen 443 ssl;
	server_name xxx;
	ssl_certificate /xxx/server.crt;
	ssl_certificate_key /xxx/server.key;
	#平台安全证书
	ssl_client_certificate /xxx/aep-msgpush.crt;
	ssl_verify_client on;
	location / {
		root html;
	}
}
```





#### MQ订阅推送
