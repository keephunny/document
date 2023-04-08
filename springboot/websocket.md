websocket 是一种通信协议，只需一次http握手就可以建立持久连接，并且能进客户端与服务端行双向通信，它允许服务端主动给客户端推送消息。
websocket 是现代浏览器的一个内置对象，通过一个构造函数 new Websocket(url) 便可建立连接

### 后端实现
```
    //pom依赖
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>    
```

```
@Configuration
public class WebSocketConfig {
    /**
     * 注入ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

```

/**
 *   var url="ws://127.0.0.1:8081//websocket/0001";
 *   var websock = new WebSocket(url);
 *   websock.onmessage=function(e){
 *     console.log(e.data);
 *   }
 */
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    private final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 用户ID
     */
    private String userId;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            logger.info("【websocket消息】有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            logger.info("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message) {
        logger.info("【websocket消息】收到客户端消息:" + message);
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误,原因:" + error.getMessage());
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        logger.info("【websocket消息】广播消息:" + message);
        for (WebSocket webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                logger.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    logger.info("【websocket消息】 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

```

发送数据
```
    String userId = "0001";
    //创建业务消息信息
    JSONObject obj = new JSONObject();
    obj.put("cmd", "topic");//业务类型
    obj.put("msgId", UUID.randomUUID().toString());//消息id
    obj.put("msgTxt", System.currentTimeMillis());//消息内容

    //全体发送
    //webSocket.sendAllMessage(obj.toJSONString());
    //单个用户发送 (userId为用户id)
    webSocket.sendOneMessage(userId, obj.toJSONString());
    //多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
    //webSocket.sendMoreMessage(userIds, obj.toJSONString());
```


### 前端实现

```
    var url="ws://127.0.0.1:8081//websocket/0002";
    var websock = new WebSocket(url);
    //解收消息
    websock.onmessage=function(e){
        console.log(e.data);
    }
    websock.onerror=function(e){}
    websock.onclose=function(e){}

    websock.onopen=function(){
        setInterval(() => {
        
        //向后端发送数据
        websock.send(JSON.stringify({
            msg: 'wowowoow' + Date.now(),
            type: 'add'
        }))
        }, 1000)
    }

```
1. websock.onopen()连接成功
2. websock.onclose()连接关闭
3. websock.onerror()连接出错
4. websock.onmessage()服务器返回信息解析
5. websock.send()发送信息
6. websock.close()关闭连接


### 获取头信息

### websocket认证
https://juejin.cn/post/7064232762664255525