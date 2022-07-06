ChannelPipeline用于组织全部的ChanneHandler，是ChannelHander的容器。ChannelPipeline将一个ChannelHandler处理后的数据作为下一个ChannelHandler处理的数据源。Netty基于事件驱动，ChannelPipeline中流动的是入栈出栈事件，事件可能会附加数据，事件在ChannelPipeline中不是自动流动，需要调用ChannelHandlerContext中firexx、read()等方法，将事件往下传播。



ChannelPipeline接口定义了很多ChannelHandler的操作方法，比如添加ChannelHandler或者移除ChannelHandler，此外它继承了ChannelInboundInvoker和ChannelOutboundInvoker接口，



#### ChannelInboudInvoker接口源码

定义了ChannelInboundHandler之间的回调事件的回调方法，由用户进行具体实现。ChannelInboundInvoker中定义的方法基本上是用于在 ChannelHandler之前传播事件的方法，以fireXX为主，交给用户自行实现。

```
public interface ChannelInboundInvoker {

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的channelRegistered方法被调用
     */
    ChannelInboundInvoker fireChannelRegistered();

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的channelUnregistered方法被调用
     */
    ChannelInboundInvoker fireChannelUnregistered();

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的channelActive方法被调用
     */
    ChannelInboundInvoker fireChannelActive();

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的channelInactive方法被调用
     */
    ChannelInboundInvoker fireChannelInactive();

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的exceptionCaught方法被调用
     */
    ChannelInboundInvoker fireExceptionCaught(Throwable cause);

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的userEventTriggered方法被调用
     */
    ChannelInboundInvoker fireUserEventTriggered(Object event);

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler的channelRead方法被调用
     */
    ChannelInboundInvoker fireChannelRead(Object msg);

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler一个channelReadComplete事件
     */
    ChannelInboundInvoker fireChannelReadComplete();

    /**
     * 触发ChannelPipeline中后面一个ChannelInboundHandler一个channelWritabilityChanged事件
     */
    ChannelInboundInvoker fireChannelWritabilityChanged();
}
```



#### ChannelOutboundInvoker接口源码

ChannelOutboundInvoker定义了方法进行Channel内部IO操作，供用户在回调方法中调用。

```
/**
 * ChannelOutboundInvoker定义了方法进行Channel内部IO操作（Channel发起bind/connect/close操作，Channel监听OP_READ，Channel写IO数据...），
 * 供用户在回调方法中使用。ChannelPipeline和Channel都继承了ChannelOutboundInvoker接口，因此二者都具备这些方法，而在Channel的骨架子
 * 类AbstractChannel中，对这些方法的实现就是调用内部的ChannelPipeline的实现
 */
public interface ChannelOutboundInvoker {

    /**
     * 绑定到指定地址，完成后会通知ChannelFuture，不管成功或者失败
     */
    ChannelFuture bind(SocketAddress localAddress);

    /**
     * 连接到指定地址，操作完成后通知ChannelFuture，不管成功或者失败
     */
    ChannelFuture connect(SocketAddress remoteAddress);

    /**
     * 请求断开连接，操作完成后通知ChannelFuture，不管成功或者失败
     */
    ChannelFuture disconnect();

    /**
     * 关闭Channel，完成后通知ChannelFuture，不管成功或者失败
     */
    ChannelFuture close();

    /**
     * 将Channel从EventExecutor注销，完成后会通知ChannelFuture，，不管成功或者失败
     */
    ChannelFuture deregister();

    /**
     * 断开和远程地址之间的连接，并且操作完成后通知ChannelFuture，不管成功或者失败
     */
    ChannelFuture disconnect(ChannelPromise promise);

    /**
     * 从Channel读数据到入站buffer，如果读到数据就触发一次channelRead事件，如果可以继续读取数据会触发channelReadComplete事件
     */
    ChannelOutboundInvoker read();

    /**
     * 请求通过ChannelPipeline写数据 不会触发flush
     */
    ChannelFuture write(Object msg);

    /**
     * flush所有消息
     */
    ChannelOutboundInvoker flush();

    /**
     * 写消息，并且flush
     */
    ChannelFuture writeAndFlush(Object msg, ChannelPromise promise);

    /**
     * 返回一个新的ChannelPromise
     */
    ChannelPromise newPromise();

    /**
     * 返回一个新的ChannelProgressivePromise
     */
    ChannelProgressivePromise newProgressivePromise();

    /**
     * 返回成功的ChannelFuture
     */
    ChannelFuture newSucceededFuture();

    /**
     * 返回失败的ChannelFuture
     */
    ChannelFuture newFailedFuture(Throwable cause);

    ChannelPromise voidPromise();
}
```





#### ChannelPipeline接口源码

```
public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {

    /**
     * 在开始的位置添加一个ChannelHandler
     */
    ChannelPipeline addFirst(String name, ChannelHandler handler);

    /**
     * 在末尾位置添加一个ChannelHandler
     */
    ChannelPipeline addLast(String name, ChannelHandler handler);

    /**
     * 在指定ChannelHandler前面添加一个ChannelHandler
     */
    ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler);

    /**
     * 在指定ChannelHandler后面添加一个ChannelHandler
     */
    ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler);

    /**
     * 移除ChannelHandler
     */
    ChannelPipeline remove(ChannelHandler handler);

    /**
     * Returns the last {@link ChannelHandler} in this pipeline.
     * 返回最后一个ChannelHandler
     */
    ChannelHandler last();
 
    /**
     * 根据名字获取ChannelHandler
     */
    ChannelHandler get(String name);
}
```

**默认实现**

```
io.netty.channel.DefaultChannelPipeline
```

事件处理流，是一个双向链表结构，链表中节点元素为ChannelHandlerContext。新的AbstracNioChannel创建时，会创建该Channel对应的DefaultChannelPipeline，用于处理该Channel对应的回调事件。

#### 入栈

* ChannelRegistered()：channel注册到EventLoop
* ChannelActive()：channel激活
* ChannelRead(Object)：Channel读取到数据
* ChannelReadComplete()：channel读取数据完毕
* ExceptionCaught(Throwable)：捕获到异常
* UserEventTriggered(Object)：用户自定义事件
* ChannelWritabilityChanged()：channel可写性改变，则写高低水控制
* ChannelInactive()：channel不再激活
* ChannelUnregistered()：channel从EventLoop中注销

#### 出栈

* bind(SocketAddress,ChannelPromise)：绑定到本地址址
* connect(SockectAddress,SocketAddress,ChannelPromise)：连接一个远端机器
* write(Object,ChannelPromise)：写数据，实际只加到Netty出站缓冲区
* flush()：flush数据，实际执行底层写
* read():读数据，实际设置关心OP_READ事件，当数据到来时触发ChannelRead事件
* disconnect(ChannelPromise)：断开连接，NIO Server和client不支持，实际调用close
* close(ChannelPromise)：关闭channel
* deregister(ChannelPromise)：从EventLoop注销Channel



#### Unsafe作用

Unsafe是channel的内部类，一个channel对应一个Unsafet，用于处理Channel对应网络IO的底层操作，ChannelHandler处理回调事件时产生的相关网络操作最终也委托给Unsafet执行。

Unsafe接口中定义了socket相关操作，包括SocketAddress获取、selector注册、网卡端口绑定、socket建连与断连、socket写数据。这些操作都和jdk底层socket相关。

NioUnsafe在Unsafe基础上增加了几个操作，包括访问jdk的SelectableChannel、socket读数据等。

NioByteUnsafe实现了与socket连接的字节数据读取相关的操作。

NioMessageUnsafe实现了与新连接建立相关的操作。