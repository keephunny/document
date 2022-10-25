MQTT协议中文版

https://mcxiaoke.gitbooks.io/mqtt-cn/content/mqtt/0302-CONNACK.html



Netty实现高性能IOT服务器(Groza)之手撕MQTT协议篇上

https://cloud.tencent.com/developer/article/1504640


mqtt协议
https://www.cnblogs.com/pandaNHF/category/1895867.html



### topic通配符
1. 主题层次分隔符被用来在主题中引入层次。多层的通配符和单层通配符可以被使用，但他们不能被使用来做发布者的消息。
2. Topic命名尽量见名知意，符合规范，主题名字是大小写敏感的。
3. 以/开头会产生一个不同的主题。/topic和topic是一样的

层级分隔符
/用来分隔每一层
/prouctId/deviceId/data

* +：单层通配符，只匹配一层级

```
/productId/+  通配以下topic

/productId/deviceId/
```


* #：多层通配符可以表示任意层级
```
/productId/# 通配以下topic

/productId/deviceId/
/productId/deviceId/data
```
* 特殊topic
```
+/ 通配所有topic
sys/# 通配所有sys开头的topic
sys/#/up
```

* emqx特殊topic
```
$share/<group-name> 为前缀的共享订阅是带群组的共享订阅：
$queue/t/1	$queue/	t/1
$share/abc/t/1	$share/abc	t/1

```