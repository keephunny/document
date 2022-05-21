ChannelHanndler

ChannelHanndler是Netty中的通道事件处理器，可以处理各种事件，连接建立、断开、数据接收、异常等。并且将事件转发给下一个ChannelHandler。允许用户自定义ChannelHandler的实现来处理传入和传出的数据，我们使用Netty时，绝大部分时间也都是写在这部分代码。

![aaa7](/Users/apple/Downloads/aaa7.png)

```
ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
ch.pipeline().addLast(new StringDecoder());
```

#### ChannelHandler接口

ChannelHandler有两个重要的接口：ChannelInboundHandler和ChannelOutboundHandler。

```
// 当把ChannelHandler添加到ChannelPipeline中时被调用
void handlerAdded(ChannelHandlerContext ctx) throws Exception;

// 当把ChannelHandler从ChannelPipeline中移除时被调用
void handlerRemoved(ChannelHandlerContext ctx) throws Exception;

// 当处理数据或事件的过程中，在ChannelPipeline中有错误产生时被调用
void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
```

#### ChannelInboundHandler接口

ChannelInboudHandler作为处理事件以及各类状态变化的Handler实现的父接口，它扩展了父接口，新增了一些特有的回调方法。这些方法将会在数据被接收时或者与其对应的Channel状态发生改变时被调用。

以下每个方法都带有ChannelHandlerContext参数，用于在每个回调事件里处理完成之后，使用fireChannelxxx方法来传递下一个ChannelHandler。

```
//当channel已经注册到它的EventLoop并且能够处理I/O时调用
void channelRegistered(ChannelHandlerContext ctx);

//当channel从它所关联的Eventloop中注销并无法处理任何I/O时调用
void channelUnregistered(ChannelHandlerContext ctx);

//当channel处于活动状态时被调用，channel已经连接绑定并且已经就绪
void channelActive(ChannelHandlerContext ctx);

//当channel离开活动状态并且不再连接它的远程节点时调用
void channelInactive(ChannelHandlerContext ctx);

//当channel读取数据时被调用
void channelRead(ChannelHandlerContext ctx,Object mgs);

//当channel上的一个读操作完成时被调用
void channelReadComplete(ChannelHandlerContext ctx);

//当用户事件触发时调用，因为一个POJO被传递给了ChannelPipeline
void userEventTriggered(ChannelHandlerContext ctx,Object evt);

//当channel的可写状态改变时被调用
void channelWritabilityChangedd(ChannelHandlerContext ctx);

@Override
public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    ctx.fireChannelRegistered();
}
```



#### ChannelOutboundHandler接口

ChannelOutboundHandler是处理出栈数据并且允许拦截所有的操作Handler实现父接口，它也对ChannelHandler接口进行了扩展，并定义了一些自身特有的方法。

在每个回调事件处理完成之后，需要向下一个ChannelHandler传递，不同的是，这里需要调用ChannelHandlerContext的write()方法向下传递。

```
public interface ChannelOutboundHandler extends ChannelHandler {
  //当请求将Channel绑定在本地地时被调用 一旦绑定上就调用，而当操作完成时候由promise进行提醒
  void bind(ChannelHandlerContext ctx,SocketAddress localAddress,ChannelPromise promise);

  //当请求将Channel连接到远程节点时被调用 当连接上时候，触发
  void connect(ChannelHandlerContext ctx,SocketAddress remoteAddress,SocketAddress localAddress,ChannelPromise promise);

  //当请求将channel从远程节点断开时被调用
  void disconnect(ChannelHandlerContext ctx,ChannelPromise promise);

  //当请求关闭channel时调用
  void close(ChannelHandlerContext ctx,ChannelPromise promise);

  //当请求将Channel从所有关联的EventLoop上注销时被调用
  void deregister(ChannelHandlerContext ctx,ChannelPromise promise);

	// 拦截ChannelHandlerContext的read方法
  void read(ChannelHandlerContext ctx) throws Exception;

  // 当一个write操作被调用时调用
  void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception;
  
	//flush操作，清空缓存，并把所有缓存数据写入
  void flush(ChannelHandlerContext ctx);
 }
```



#### ChannelDuplexHandler



#### ChannelHandlerAdapter

ChannelInboundHandlerAdapter和ChannelOutboundHandlerAdapter这两个适配器作为上面两个接口的实现类，分别实现了它们所有的方法，实际编程中都是以这两个类作为起点，继承它们并重写其中的方法。如果@Sharable表示可以添加到多个ChannelPipeline中

```

public abstract class ChannelHandlerAdapter implements ChannelHandler {

}
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
 			ByteBuf buf = (ByteBuf) msg;
      logger.info("接收数据:{}", ByteBufUtil.hexDump(buf));
    }
}
```

#### SimpleChannelInboundHandler

SimpleChannelInboundHandler是抽象类，继承了ChannelInboundHandlerAdaptor类，重写了channelRead方法，并增加了ChannelRead0抽象方法。

这里使用的是模版模式，将需要变化的逻辑放在抽象方法channelRead0()中，让子类根据自已的实现进行编码，将处理逻辑不变的内容写在channelRead()中，并在里面调用channelRead0()。

当继承SimpleChannelnboundHandler类时，将自定义逻辑写入channelRead0()，当channelRead真正被触发调用时我们逻加才会被处理。然后根据需求执行ctx.fireChannelRead(msg)传递到下一个ChannelHandler或者执行ReferenceCountUtil.release(msg)自动进行资源释放。而直接继承ChannelInboundHandlerAdapter类，则需要负责显式地释放与池化的 ByteBuf 实例相关的内存。

```
public abstract class SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      boolean release = true;
      try {
          if (acceptInboundMessage(msg)) {
              @SuppressWarnings("unchecked")
              I imsg = (I) msg;
              channelRead0(ctx, imsg);
          } else {
              release = false;
              ctx.fireChannelRead(msg);
          }
      } finally {
          if (autoRelease && release) {
              ReferenceCountUtil.release(msg);
          }
      }
  }

	protected abstract void channelRead0(ChannelHandlerContext ctx, I msg) throws Exception;
}
```

