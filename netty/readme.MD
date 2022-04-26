
TCP数据结构复杂，UDP 数据结构较简单
TCP 是面向连接的，UDP 是面向无连接的
TCP 是面向字节流的，UDP 是基于报文的
TCP 保证数据顺序性，UDP 不保证
TCP 保证数据准确性，UDP 可能丢包，即TCP是可靠传输



### Netty解码器
* DelimiterBaseFrameDecoder：解决TCP的粘包解码器
* StringDecoder：消息转成String解码器
* LineBasedFrameDecoder：自动完成标识符分隔解码器
* FixedLengthFrameDecoder：固定长度解码器，二进制。
* Base64Decoder：base解码器

* Base64Encoder：base编码器
* StringEncoder：String编码器
* LineBasedFrameDecoder：自动完成标识符分隔符编码器
* MessageToMessageEncoder：根据消息对象编码为消息对象


### Channel
* NioSocketChannel：异步客户端TCP Socket连接
* NioServerSocketChannel：异步服务器端TCP Socket连接
* NioDatagramChannel：异步的UDP连接
* NioSctpChannel：异步的客户端Sctp连接
* NioSctpServerChannel：异步的服务器端Sctp
* OioServerSocketChannel：同步的服务器端TCP Socket连接
* OioDatagramChannel：同步的UDP连接
* OioSctpChannel：同步的Sctp服务器端连接
* OioSctpServerChannel：同步的Sctp客户端连接
* OioSctpServerChannel：同步的客户端TCP Socket连接


### Netty处理器Handler
handler都实现自ChannelHandler接口，按照输入输出分为
* ChannelInboundHandler：接受客户端发送的报文处理，一般用来执行解码、读取客户端数据等。
* ChannelOutboundHandler：服务器往客户端的报文进行处理，一般用来编码、发送报文到客户端。
* channelActive：通道激活时触发，当客户端连接成功后事件触发。

### Netty核心组件
* Bootstrap和ServerBootstrap：Netty应用程序通过设置bootstrap引导类来完成，该类提供了一个用于应用程序网层配置的容器。Bootstrap服务端是ServerBootstrap，客户端是Bootstrap。
* Channel：Netty中的接口Channel定义了与socket丰富交互的操作集bind、close、config、connect、isActive、isOpen、isWritable、read、write等。
* ChannelHandler：支持很多协议，并且提供了数据处理的容器，ChannelHandler由特定事件触发，常用一个接口是ChannelInboundHandler，该类型处理入站读数据。
* ChannelPipeline：提供了一个容器给ChannelHandler链并提供了一个API用于管理沿着链入站和出站事件的流动。每个Channel都是自已的ChannelPipeline，当Channel创建时自动创建。
* EventLoop：用于处理Channel的I/O操作，一个单一的EventLoop通常会处理多个Channel事件，一个EventLoopGroup可以包含多个EventLoop和提供了一种迭代用于检索清单中的下一个。
* ChannelFuture：Netty所有的I/O操作都是异步，因为一个操作可能无法立即返回，我们需要一种方法在以后获取它的结果。所以Netty提供了接口ChannelFuture的addListener方法。

### Buffer
* ByteBuf是字节数据的容器，所有的网络通信都是基于底层的字节流传输，ByteBuf是一个经过优化的数据容器。ByteBuf提供了两个索引一个用读，一个用于写。我们也可以按顺序读取，也可以通过调整读取数据的索引或直接读取位置索引作为参数传递给get方法来重复读取数据。

Buffer是一个抽象类，位于java.nio包中，是一块可以写入数据的缓冲区，然后可以从中读取数据的内存。
* capacity：容量
* limit：上界，缓冲区的当前数据量
* position：当前位置
* mark：位置标记



```
// netty发送数据
pubic void channelRead(ChannelHandlerContext ctx,Object msg){
	ByteBuf response=ctx.alloc().buffer(20);
	response.writeBytes("say hello".getBytes());
	ctx.writeAndFlush(response);
}
```

