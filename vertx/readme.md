官方文档

https://vertx.io/docs/

http://vertxchina.github.io/vertx-translation-chinese/start/FAQ.html



Vert.x是基于Netty的高级封装，是netty的超集，它利用Netty的EventLoop为开发者提供了更友好的编程模型。Netty作为网络I/O工具，使用起来能清晰的感受在操作 网络字节。Netty解决的是高吞吐的网络I/O问题，而不是编程问题，我们在使用Netty通常会编写一些ChannelHandler先对请求进行解码，封装成任备再投递到我们自已的业务线程池中，再调用writeAndFlush()响应请求。然而，我们的业务线程池中会跑的任务，往往还是那些会阻塞线程的数据库查询、网络I/O等操作，在执行这些操作时，当前的业务线程一直在等I/O完成也会浪费业务线程池的资源。在这种情况下，使用Netty其实只解决了如何用少量线程支撑起大量连接的问题，你连接是建立起来了，但是你的业务处理逻辑并没有因此而变的更快(吞吐量更高)。

Vert.x官方就提供了很多常见服务的异步化实现，如jdbc-client, redis-client, web-client等。与此同时，Vert.x还提供了很多好用的异步协调工具，如CompositeFeature，允许你将多个异步调用组合起来，当满足一定条件时再触发另一个回调，这正是当你尝试用Netty将业务逻辑异步化时Netty所欠缺的组件。在工程上面Vert.x还提供了"一站式"的工程解决方案， 如Event Bus, 允许你将Verticle部署到多台服务器上(集群)，每个实例通过Event Bus通信，甚至还有针对时下流行的微服务架构而准备的注册中心Vert.x Service Discovery, 断路器Vert.x Circuit Breaker等。

Vert.x是Netty的超集，更加注重解决工程问题，而不仅是单纯的网络I/O问题，Vert.x对二进制协议的解析不够友好。因为封装程度较高，导致对二进制协议的解析不够灵活。

Vert.x运行在Java虚拟机上，支持多种编程语言，Vert.x是高度模块化的，同一个应用，你可以选择多种编程语言同时开发。在Vert.x 2版本，也就是基于JDK7，还没有lambda的时候，一般来讲，使用JavaScript作为开发语言相对较多，到Vert.x3的时代，因为JDK8的出现，Java已经作为Vert.x主流的开发语言，而Vert.x也被更多的开发者所接受。



#### Vert.x用途

1. web开发，Vert.x封装了web开发的常用组件，支持路由，Session管理，模板等，可以非常方便的进行web开发，不需要容器。
2. TCP/UDP开发，Vert.x底层基于Netty，提供了丰富的IO类库，支持多种网络应用开发，不需要处理底层细节如拆包粘包，注重业务代码编写。
3. 提供WebSocket的支持 ，可以做动态推送。
4. Event Bus 事件总线实现分布式消息，远程方法调用等，也可以用于微服务应用。

#### Vert.x技术体系

1. 核心模块

   核心模块包含了一些基础的功能，如http、tcp、udp、文件访问、EventBus、websocket、等其它基础功能，可以通过vertx-core模块引用。

2. web模块

   vertx-web是一个工具集，虽然核心模块提供了http的支持，但要开发复杂的web应用，还需要路由、session、请求数据读取、rest支持等。vertx是一个异步框架，请求http服务是一个耗时操作，会阻塞EventBus，导致整体性能被拖垮。因此对于请求web服务，一定要用vertx提供的vertx-web-client模块。

3. 数据访问模块

   vertx提供了对关系型数据库、nosql、消息中间件的支持 。MongoDB client,JDBC client,SQL common,Redis client,MySQL/PostgreSQLclient

4. Reactive响应式编程

5. 整合其它模块

6. 认证授权

7. 微服务