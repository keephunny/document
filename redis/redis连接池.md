#### 直连优缺点

1. 简单方便，适用于少量长期连接的场景
2. 存在每次新建/关闭TCP连接开销 2. 资源无法控制，极端情况下出现连接泄漏 3. Jedis对象线程不安全(Lettuce对象是线程安全的)



#### 连接池优缺点

1. 无需每次连接生成Jedis对象，降低开销 
2. 使用连接池的形式保护和控制资源的使用	
3. 相对于直连，使用更加麻烦，尤其在资源的管理上需要很多参数来保证，一旦规划不合理也会出现问题



Lettuce 和 jedis 的都是连接 Redis Server的客户端，Jedis 在实现上是直连 redis server，多线程环境下非线程安全，除非使用连接池，为每个 redis实例增加 物理连接。

Lettuce 是 一种可伸缩，线程安全，完全非阻塞的Redis客户端，多个线程可以共享一个RedisConnection,它利用Netty NIO 框架来高效地管理多个连接，从而提供了异步和同步数据访问方式，用于构建非阻塞的反应性应用程序。

Jedis 和 Lettuce 是 Java 操作 Redis 的客户端。在 Spring Boot 1.x 版本默认使用的是 jedis ，而在 Spring Boot 2.x 版本默认使用的就是Lettuce。关于 Jedis 跟 Lettuce 的区别如下：

Jedis在实现上是直接连接的redis server，如果在多线程环境下是非线程安全的，这个时候只有使用连接池，为每个Jedis实例增加物理连接
Lettuce的连接是基于Netty的，连接实例（StatefulRedisConnection）可以在多个线程间并发访问，应为StatefulRedisConnection是线程安全的，所以一个连接实例（StatefulRedisConnection）就可以满足多线程环境下的并发访问，当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。



* lettuce：底层是用netty实现，线程安全，默认只有一个实例。
* jedis：可直连redis服务端，配合连接池使用，可增加物理连接。

### lettuce-pool

Lettuce的连接是基于Netty的，连接实例可以在多个线程间共享，所以，一个多线程的应用可以使用同一个连接实例，而不用担心并发线程的数量。当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
通过异步的方式可以让我们更好的利用系统资源，而不用浪费线程等待网络或磁盘I/O。Lettuce 是基于 netty 的，netty 是一个多线程、事件驱动的 I/O 框架，所以 Lettuce 可以帮助我们充分利用异步的优势。





### jedis-pool

Jedis 是直连模式，在多个线程间共享一个 Jedis 实例时是线程不安全的，
如果想要在多线程环境下使用 Jedis，需要使用连接池，
每个线程都去拿自己的 Jedis 实例，当连接数量增多时，物理连接成本就较高了。