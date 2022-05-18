

### Channel生命周期

* ChannelUnregistered：channel已被创建，未注册到EventLoop
* ChannelRegistered：channel已注册到EventLoop
* ChannelActive：channel已处理活动状态并可以接收和发送数据
* Channellnactive：channel没有连接到远程节点



### ChannelHandler

ChannelHandler是Netty框架中特有的，它是处理Channel中事件一种方式，对于入站与出站消息又分别使用ChannelInboundHandler与ChannelOutboundHandler来处理，

* handlerAdded：添加到channelPipeline
* handlerRemoved：移除channelPipeline
* exceptionCaught：异常

```
.handler(new ChannelInitializer<NioSocketChannel>() {
    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new StringEncoder());
    }
})
ChannelInitializer<C extends Channel> extends ChannelInboundHandlerAdapter
```

#### ChannelInboundHandler

入栈handler读操作

* channelRegistered：当Channel注册到它的EventLoop并且能够处理I/O时调用
* channelUnregistered：当Channel从它的EventLoop中注销并且无法处理任何I/O时调用
* channelActive：当Channel处理于活动状态时被调用
* channelInactive：当Channel不再是活动状态且不再连接它的远程节点时被调用
* channelRead：当从Channel读取数据时被调用
* channelReadComplete：当Channel上的一个读操作完成时被调用
* channelWritabilityChanged：当Channel的可写状态发生改变时被调用
* userEventTriggered：当ChannelInboundHandler.fireUserEventTriggered()方法被调用时触发

channelRead方法读取网络数据，但通过直接继承ChannelInboundHandler的子类来说，使用channelRead方法需要注意需要显示的释放与池化ByteBuf实例相关的内存，为此Netty提供了一个实用方法：ReferenceCountUtil.release()方法。

Netty另外还提供了一个类来简化这一过程SimpleChannelInboundHandler类，这个类通过一个通过的过程简化了上面释放内存的操作，使用示例如下：

```
public class DiscardHandler extends SimpleChannelInboundHandler<Object>{
  @Override
  public void channelRead0(ChannelHandlerContext ctx,Object msg){
		//在这里不需要显示的对资源msg进行释放
  }
}
```



#### ChannelOutboundHandler

出站的数据和操作由ChannelOutboundHandler接口处理，它定义的方法将会被Channel、ChannelPipeline、ChannelHandlerContext调用；ChannelOutBoundHandler的强大之处在于可以按需要推迟操作或者事件，这样就可以处理一些相对复杂的请求，例如远程节点暂停写入，那么可以通过ChannelOutboundHandler的处理推迟冲刷操作并在稍后继续。下面来看看ChannelOutboundHandler定义了那些处理方法：

* bind：当请求将Channel绑定到本地地址时被调用
* connet：当请求将Channel连接到远程节点时被调用
* disconnect：当请求将Channel从远程节点断开时调用
* close：请求关闭Channel时调用
* deregister：请求将Channel从它的EventLoop注销时调用
* read：请求从Channel中读取数据时调用
* flush：请求通过Channel将入队数据冲刷到远程节点时调用
* write：请求通过Channel将数据写入远程节点时被调用

