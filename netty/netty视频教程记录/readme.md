https://nyimac.gitee.io/2021/04/25/Netty%E5%9F%BA%E7%A1%80/

 * 1  19:08  P1Netty-导学

 * 2  05:52  第1章_01_nio三大组件-channel-buffer

 * 3  06:21  第1章_02_nio三大组件-服务器设计-多线程版

 * 4  05:00  第1章_03_nio三大组件-服务器设计-线程池版

 * 5  06:11  第1章_04_nio三大组件-服务器设计-r版

 * 6  16:55  第1章_05_bytebuffer-基本使用

 * 7  11:13  第1章_06_bytebuffer-内部结构

 * 8  09:37  第1章_07_bytebuffer-方法演示1

 * 9  09:30  第1章_08_bytebuffer-方法演示2

 * 10 09:04  第1章_09_bytebuffer-方法演示3

 * 11 10:00  第1章_10_bytebuffer-方法演示4

 * 12 09:39  第1章_11_bytebuffer-分散读集中写

 * 13 04:01  第1章_12_bytebuffer-黏包半包分析

 * 14 10:14  第1章_13_bytebuffer-黏包半包解析

 * 15 06:35  第1章_14_filechannel-方法简介

 * 16 04:27  第1章_15_filechannel-传输数据

 * 17 05:05  第1章_16_filechannel-传输数据大于2g

 * 18 07:47  第1章_17_path&files

 * 19 11:03  第1章_18_files-walkfiletree

 * 20 06:35  第1章_19_files-walkfiletree-删除多级目录

 * 21 05:39  第1章_20_files-walk-拷贝多级目录

 * 22 12:38  第1章_21_nio-阻塞模式

 * 23 06:31  第1章_22_nio-阻塞模式-调试1

 * 24 06:14  第1章_23_nio-阻塞模式-调试2

 * 25 07:57  第1章_24_nio-非阻塞模式

 * 26 07:05  第1章_25_nio-非阻塞模式-调试

 * 27 22:21  第1章_26_nio-selector-处理accept

 * 28 05:02  第1章_27_nio-selector-cancel

 * 29 09:20  第1章_28_nio-selector-处理read

 * 30 13:27  第1章_29_nio-selector-用完key为何要remove

 * 31 11:05  第1章_30_nio-selector-处理客户端断开

 * 32 03:48  第1章_31_nio-selector-消息边界问题

 * 33 10:14  第1章_32_nio-selector-处理消息边界

 * 34 08:54  第1章_33_nio-selector-处理消息边界-容量超出

 * 35 14:00  第1章_34_nio-selector-处理消息边界-附件与扩容

 * 36 05:20  第1章_35_nio-selector-bytebuffer大小分配

 * 37 16:39  第1章_36_nio-selector-写入内容过多问题

 * 38 10:53  第1章_37_nio-selector-处理可写事件

 * 39 09:33  第1章_38_nio-网络编程小结

 * 40 07:20  第1章_39_nio-多线程优化-分析

 * 41 10:59  第1章_40_nio-多线程优化-worker编写

 * 42 07:59  第1章_41_nio-多线程优化-worker关联

 * 43 06:54  第1章_42_nio-多线程优化-问题分析

 * 44 13:43  第1章_43_nio-多线程优化-问题解决

 * 45 05:40  第1章_44_nio-多线程优化-问题解决-wakeup

 * 46 07:33  第1章_45_nio-多线程优化-多worker

 * 47 03:09  第1章_46_nio-概念剖析-streamvschannel

 * 48 08:31  第1章_47_nio-概念剖析-io模型-阻塞非阻塞

 * 49 08:10  第1章_48_nio-概念剖析-io模型-多路复用

 * 50 08:41  第1章_49_nio-概念剖析-io模型-异步

 * 51 15:13  第1章_50_nio-概念剖析-零拷贝

 * 52 17:03  第1章_51_nio-概念剖析-io模型-异步例子

 * 53 12:07  第2章_01-netty入门-概述

 * 54 15:52  第2章_02-netty入门-hello-server

 * 55 07:33  第2章_03-netty入门-hello-client

 * 56 13:39  第2章_04-netty入门-hello-流程分析

 * 57 12:25  第2章_05-netty入门-hello-正确观念

 * 58 05:53  第2章_06-netty入门-eventloop-概述

   ```
   事件循环对象，本质是一个单线程执行器，同时维护一个selector，里面有run方法处理channel上源源不断的IO事件。
   继承自java ScheduleExecutorService和OrderedEventExecutor
   
   EventLoopGroup是一组EventLoop，channel一般会调用EventLoopGroup的register方法来绑定其中一个EventLoop，后续这个channel上的IO事件都由此EventLoop来处理。
   ```

 * 59 14:18  第2章_07-netty入门-eventloop-普通-定时任务

   ```
   事件循环组
   EventLoopGroup group=new NioEventLoopGroup();可以提交IO事件、普通任务、定时任务 
   EventLoopGroup group=new DefaultEventLoopGroup();普通任务、定时任务
   //获取下一个事件循环对象
   group.next();
   group.next();
   
   group.next().execute();
   						.submit();
   
   //定时任务
   group.next().scheduleAtFixedRate(() -> {
   System.out.println("time");
   }, 0, 1, TimeUnit.SECONDS);
   
   参考代码:TestNetty59
   ```

 * 60 14:32  第2章_08-netty入门-eventloop-io任务

   ```
   同61
   
   ```

   

 * 61 06:49  第2章_09-netty入门-eventloop-分工细化

   ```
   EventLoopGroup 分开创建两个boss worker
   
   ```

   

 * 62 13:24  第2章_10-netty入门-eventloop-分工细化

   ```
   Nio线程与任务处理线程分开处理
   ch.pipeline().addLast(线程组,"name",new ChannelInboundHandlerAdapter())
   
   //将消息传递给下一个handle处理
   ctx.fireChannelRead(msg)
   ```

 * 63 10:03  第2章_11-netty入门-eventloop-切换线程

   ```
   executor.execute(new Runnable(){
   	@Override
   	public void run(){
   		next.invokkeChannelRead(m);
   	}
   })
   ```

 * 64 04:39  第2章_12-netty入门-channel

   ```
   Channel主要作用
   	close关闭channel
   	closeFuture处理channel的关闭
   		sync方法作用同步等待channel关闭
   		addListener方法是异步等channel关闭
   	pipeline()方法添加处理器
   	write()将数据写入
   	writeAndFlush()将数据写入并刷出
   ```

   

 * 65 10:10  第2章_13-netty入门-channelFuture-连接问题

   ```
   
   
   ```

   

 * 66 09:15  第2章_14-netty入门-channelFuture-处理结果

   ```
   channelFuture.sync();阻塞
   
   //回调
   //方法二 回调
   channelFuture.addListener(new ChannelFutureListener() {
   @Override
   public void operationComplete(ChannelFuture future) throws Exception {
   Channel channel = future.channel();
   channel.writeAndFlush("hello world2");
   }
   });
   ```

   

 * 67 06:44  第2章_15-netty入门-channelFuture-关闭问题

   ```
   
   ```

   

 * 68 10:42  第2章_16-netty入门-channelFuture-处理关闭

 * 69 04:48  第2章_17-netty入门-channelFuture-处理关闭

   ```
   安全关闭主线程
   group.shutdownGraefully()
   
   转换lamba表达式
   ```

   

 * 70 09:56  第2章_18-netty入门-为什么要异步

   ```
   netty异步提升了哪些性能
   
   ```

   

 * 71 10:13  第2章_19-netty入门-future-promise-概述

   ```
   promise -> netty Future -> jdk Future
   jdk Future：只能同步等待任务结束才能得到结果。
   	cancel、isCanceled、isDone、get
   netty Future：可以同步等待任务结束得到结果，也可以异步得到结果。
   	getNow、await、sync、isSuccess、cause、addLinstener
   netty Promise：不仅有netty future的功能，而且脱离了任务独立存在，只作为两个线程间传递结果的容器。
   	setSuccess、setFailure
   ```

   

 * 72 07:34  第2章_20-netty入门-jdk-future

   ```
   关联线程池使用
   future.get()获取线程结果
   参考代码TestNetty72
   ```

   

 * 73 07:59  第2章_21-netty入门-netty-future

   ```
   参考代码 TestNetty72
   ```

   

 * 74 10:54  第2章_22-netty入门-netty-promise

   ```
   参考代码 TestNetty72
   ```

   

 * 75 13:23  第2章_23-netty入门-pipeline

 * 76 10:00  第2章_24-netty入门-inbound-handler

 * 77 07:14  第2章_25-netty入门-outbound-handler

 * 78 04:56  第2章_26-netty入门-embedded-channel

 * 79 05:46  第2章_27-netty入门-bytebuf-创建

   ```
   初始化byteBuf
   ByteBuf buf=ByteBufAllocator.DEFAULT.buffer();
           buf.writeBytes();
           buf.writeInt();
   
   ridx读指针，widx写指针，cap容量
   PooledUnsafeDirectByteBuf(ridx: 0, widx: 0, cap: 256)
   
   详细日志输出byteBuf
            +-------------------------------------------------+
            |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
   +--------+-------------------------------------------------+----------------+
   |00000000| 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 |aaaaaaaaaaaaaaaa|
   |00000010| 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 |aaaaaaaaaaaaaaaa|
   +--------+-------------------------------------------------+----------------+
   ```

   

 * 80 09:00  第2章_28-netty入门-bytebuf-是否池化和内存模式

   ```
   直接内存：分配效率低，读写效率高，是系统内存，默认使用。创建和销毁的代价较高，但读写性能高，适合配合池化功能一起使用。这部份内存不受jvm管理，但需要手动释放。
   堆内存：分配效率高，读写效率低，内存回收
   
   ByteBuf buf=ByteBufAllocator.DEFAULT.heapBuffer(10);
   ByteBuf buf=ByteBufAllocator.DEFAULT.directBuffer(10);
   
   池化的意义在于可以重建ByteBuf，默认开启。
   -Dio.netty.allocator.type={unpooled|pooled}
   ```

   

 * 81 05:53  第2章_29-netty入门-bytebuf-组成

   ```
   ByteBuf四部份组成
   废弃部份、可读部份、可写部份、可扩容部份
   ```

 * 82 06:49  第2章_30-netty入门-bytebuf-写入

   ```
   写入相关方法
   writeBoolean();
   writeByte();
   writeShort();
   writeInt();	//大端写入 00 00 02 50
   writeIntLE();	//小端写入 50 02 00 00
   writeLong()
   writeChar();
   writeFloat();
   writeDouble();
   writeBytes(byte[]);
   writeBytes(ByteBuf);
   int writeCharSequence(CharSequence seq,Charset charset) 写入字符串,CharSequence是stringbuild的父类。
   
   未指明返回值，其返回值是ByteBuf，可以链式调用。
   set开头的方法也会写入，但不会改变写指针位置。
   Little Endian
   
   扩容规则：
   容量<512,扩容16的整数倍
   容量>512,扩容下一个2^n
   ```

 * 83 02:55  第2章_31-netty入门-bytebuf-读取

   ```
   
   同write对应的方法
   buf.markReaderIndex()标记位置
   buf.resetReaderIndex()
   
   get开头的方法，按下标读取，不会改变读指针的位置
   ```

 * 84 09:03  第2章_32-netty入门-bytebuf-内存释放

   ```
   UnpooledHeapByteBuf：使用jvm内存管理，GC回收
   UnpooledDirecByteBuf：直接内存
   PooledByteBuf：池化
   
   Netty使用了计数法来控制内存回收，每个ByteBuf都实现了ReferenceCounted接口
   	每个ByteBuf对象的初始计数为1
   	调用release计数减1,为0时被内存回收。
   	调用retain方法计数加1,其它handler即使调用了release也不会造成回收。
   	当计数为0时，底层内存会被回收，即使ByteBuf对象还存在，其方法都不能正常使用。
   
   ```

   

 * 85 05:33  第2章_33-netty入门-bytebuf-头尾释放源码

   ```
   TailContext implements ChannelInboundHandler
   	void onUnhandledInboundMessage(Object msg){
   		ReferenceCountUtils.release(msg);
   	}
   	void release(Object msg){
   		if(msg instanceOf ReferenceCounted){
   			release();
   		}
   	}
   HeadContext implements ChannelOutboundHandler,ChannelInboundHandler
   ```

   

 * 86 08:18  第2章_34-netty入门-bytebuf-零拷贝-slice

   ```
   对原始ByteBuf进行切片成多个ByteBuf，切片后的ByteBuf并没有发生内存复制，还是使用原始的ByteBuf的内存，切片后的ByteBuf维护独立的read、write指针。
   物理共享，逻辑分开。
   ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
   buf.writeBytes(new byte[]{1, 2, 3, 4, 5, 6, 7, 8,9,10});
   ByteBufUtils.log(buf);
   
   ByteBuf b1=buf.slice(0,5);
   ByteBuf b2=buf.slice(5,5);
   ByteBufUtils.log(b1);
   ByteBufUtils.log(b2);
   
   read index:0 write index:10 capacity:10
            +-------------------------------------------------+
            |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
   +--------+-------------------------------------------------+----------------+
   |00000000| 01 02 03 04 05 06 07 08 09 0a                   |..........      |
   +--------+-------------------------------------------------+----------------+
   read index:0 write index:5 capacity:5
            +-------------------------------------------------+
            |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
   +--------+-------------------------------------------------+----------------+
   |00000000| 01 02 03 04 05                                  |.....           |
   +--------+-------------------------------------------------+----------------+
   read index:0 write index:5 capacity:5
            +-------------------------------------------------+
            |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
   +--------+-------------------------------------------------+----------------+
   |00000000| 06 07 08 09 0a                                  |.....           |
   +--------+-------------------------------------------------+----------------+
   ```

   

 * 87 07:38  第2章_35-netty入门-bytebuf-零拷贝-slice

   ```
   slice切片后的值，长度不能不能改变，不然会影响原有值。
   
   duplicate是截取了原有值的所有内容。只是读写指针是独立的。
   cpoy：复制所有内容，并与原有值完全复制。因此读写操作都与原有ByteBuf都无关。
   ```

 * 88 05:56  第2章_36-netty入门-bytebuf-零拷贝-composite

   ```
   将多个ByteBuf组合成一个ByteBuf
   
   CompositeByteBuf byteBufs=ByteBufAllocator.DEFAULT.compositeBuffer();
   byteBufs.addComponents(true,b1,b2);
   ByteBufUtils.log(byteBufs);
   ```

 * 89 02:29  第2章_37-netty入门-bytebuf-小结

   ```
   Unpooled是一个工具类，提供了非池化的ByteBuf创建、组合、复制等操作。
   wrappedBuffer可以用来包装ByteBuf，底层使用的是CompositeByteBuf
   ```

 * 90 04:44  第2章_38-netty入门-思考问题

   ```
   echo server示例 服务端响应输出
   创建ByteBuf byteBuf=ctx.alloc().buffer(n);
   ```

 * 91 08:53  第3章_01-netty进阶-黏包半包-现象演示

   ```
   粘包和拆包
   ctx.writeAndFlush(buf);
   //接收缓冲区
   serverBootstrap.option(ChannelOption.SO_RCVBUF,1024)
   
   参考代码 TestNetty91 TestNetty91Client
   ```

   

 * 92 05:51  第3章_02-netty进阶-黏包半包-滑动窗口

   ```
   发送缓冲区和接收缓冲区 
   
   ```

   

 * 93 06:46  第3章_03-netty进阶-黏包半包-分析

   ```
   粘包：小包合成大包
   	应用层：接收方ByteBuf设置的太大，Netty默认1024
   	滑动窗口：当接收方的滑动窗阻塞时，缓冲区在接收方的滑动窗口中的报文就有可能会粘包。
   	Nagle算法：会造成粘包（TCP/IP协议发送数据时会携带报文头，尽可能多的发送数据）
   拆包
   	应用层：接收方ByteBuf小于实际发送的数据量
   	滑动窗口：接收方的窗口只剩下128bytes，发送方的报文大于这个字节，只能先发送这128bytes，等待ack后才能发送剩余的部份，这就造成了拆包。
   	MSS限制：当发送的数据超过MSS限制后，会将数据切分发送，就会造成拆包。
   	Maximum Segment Size，最大报文长度。是TCP协议定义的一个选项，MSS选项用于在TCP连接建立时，收发双方协商通信时每一个报文段所能承载的最大数据长度。
   	本质是因为TCP是流式协议，消息无边界。需要自已找边界。
   ```

 * 94 10:58  第3章_04-netty进阶-黏包半包-解决-短链接

   ```
   //调整接收缓冲区
   serverBootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(16, 16, 16)); //最大 初始 最小
   
   参考代码：TestNetty91
   ```

   

 * 95 10:12  第3章_05-netty进阶-黏包半包-解决-定长解码器

   ```
   固定长度解码器
   FixedLengthFrameDecoder
   ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
   ```

   

 * 96 07:46  第3章_06-netty进阶-黏包半包-解决-行解码器

   ```
   LineBasedFrameDecoder(maxLength);
   DelimiterBasedFrameDecoder(maxLength,delimiter);
   ch.pipeline.addLast(new LineBasedFrameDecoder(1024));
   ```

 * 97 12:19  第3章_07-netty进阶-黏包半包-解决-LTC解码器

   ```
   LengthFieldBasedFrameDecoder(maxFrameLength,lengthFieldOffset,lengthFieldLength);
   
   lengthFieldOffset 长度字段偏移量
   lengthFieldLength 长度字段长度
   lengthAdjustment  长度字段为基准，还有几个节才是内容
   initialBytesToStrip 从头剥离几个字节，去除帧头
   
    * lengthFieldOffset   = 1 (= the length of HDR1)
    * lengthFieldLength   = 2
    * lengthAdjustment    = 1 (= the length of HDR2)
    * initialBytesToStrip = 3 (= the length of HDR1 + LEN)
    *
    * BEFORE DECODE (16 bytes)                       AFTER DECODE (13 bytes)
    * +------+--------+------+----------------+      +------+----------------+
    * | HDR1 | Length | HDR2 | Actual Content |----->| HDR2 | Actual Content |
    * | 0xCA | 0x000C | 0xFE | "HELLO, WORLD" |      | 0xFE | "HELLO, WORLD" |
    * +------+--------+------+----------------+      +------+----------------+
   ```

   

 * 98 11:25  第3章_08-netty进阶-黏包半包-解决-LTC解码器

   ```
   参考代码：TestNetty98
   
   ```

   

 * 99 09:52  第3章_09-netty进阶-协议设计与解析-redis

   ```
   redis协议
     set name key1
     *3	//数组元素的个数
     $3	//元素的长度
     set
     $4	//元素的长度 key1
     key1
     $6 	//元素的度度 value1
     value1
     
     参考代码 TestNetty99
     
     Netty提供了很多成熟的协议 http协议 redis协议
   ```

   

 * 100    17:02  第3章_10-netty进阶-协议设计与解析-http

   ```
   http协议编解码器
   HttpServerCodec extend CombinedChannelDuplexHandler<HttpRequestDecoder,HttpResponseEncoder>
   
   
    //自定义编解码器 
    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
    	@Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       //查看msg对象类型
       System.out.println(msg.getClass());
       //请求头
       if(msg instanceof HttpRequest){}
       //请求体
       if(msg instanceof HttpContent){}
     }
   });
   class io.netty.handler.codec.http.DefaultHttpRequest
   class io.netty.handler.codec.http.LastHttpContent
   分为httpHead和httpBody
   
   //只处理httpRequest请求解码器
   ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
     @Override
     protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {
       System.out.println("地址:" + httpRequest.getUri());
       //响应返回
       DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
       byte[] bytes="ok".getBytes();
       //指定消息长度，不然浏览器一直等待获取 content-length
       response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,bytes.length);
       response.content().writeBytes(bytes);
       ctx.writeAndFlush(response);
     }
   });
   ```
   
   

 * 101    05:59  第3章_11-netty进阶-协议设计与解析-自定义

   ```
   自定义协议要素
   帧头：用来标识
   版本号：
   序列化算法：json、protobuf、avro、hessian、jdk
   指令类型：功能码
   请求序号：为了双工通信，提供异步能力
   正文长度：
   消息正文
   校验码
   帧结尾标识
   ```

   

 * 102    16:46  第3章_12-netty进阶-协议设计与解析-编码

   ```
   自定义编解码器
   
   pubblic class MessageCodec extend ByteToMessageCodec<Message>{}
     @Override
     protectd void encode(ChannelHandleContext ctx,Message msg,ByteBuf out){
     	//帧头
   		out.writeBytes(new byte[]{1,2,3,4});
   		//协义版本
   		out.writeByte(1);
   		//序列化方式
   		out.writeByte(0);
   		//功能码
   		out.writeByte(msg.getMsgType());
   		
   		
   		//将对象转为字节数组
   		ByteArrayOutputStream bos=new ByteArrayOutputStream();
   		ObjectOutputStream oos=new ObjectOutputStream(bos);
   		oos.writeObject(msg);
   		byte[] bytes=bos.toByteArray();
   		
   		//长度 
   		out.writeInt(bytes.length);
   		//内容
   		out.writeBytes(bytes);
     }
   
     @Override
     proted void decode(ChannelHandlerContext ctx,ByteBuf in,List<Object>out){
     	//读取帧头
   		int frameFlag=in.readBytes(4);
   		//协议版本
   		int ver=in.readByte();
   		……
   		int len=in.readInt();
   		byte[] bytes=new byte[len];
   		in.readBytes(bytes,0,len);
   		ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(bytes));
   		Message message=(Message)ois.readObject();	
     }
   }
   ```

   

 * 103    14:45  第3章_13-netty进阶-协议设计与解析-解码

   ```
   同上
   
   EmbeddedChannel channel=new EmbeddedChannel(
   	new LoggingHandler(),
   	new MessageCodec()
   );
   
   channel.writeOutbound(message);
   
   参考代码com.demo.test.netty102.TestMessageCodec
   ```

   

 * 104    08:35  第3章_14-netty进阶-协议设计与解析-测试

   ```
   同上
   ```

   

 * 105    06:49  第3章_15-netty进阶-协议设计与解析-测试

   ```
   
   拆包发送
   buf.readableBytes() 获取包的长度
   buf.retain() 引用加1
   
   
   ByteBuf s1=buf.slice(0,100);
   ByteBuf s2=buf.slice(100,buf.readableBytes()-100);
   s1.retain();
   channel.writeInbound(s1);
   channel.writeInbound(s2);
   
   ```

   

 * 106    06:44  第3章_16-netty进阶-协议设计与解析-@sharable

   ```
   加了@Sharable注解标记的Handler()可以被被多个channel共享使用
   LoggingHandler可以共享使用
   ```

   

 * 107    13:47  第3章_17-netty进阶-协议设计与解析-@sharable

   ```
   
   Message自定义类
   extends ByteToMessageCodec<Message> 不可以@Sharable
   extends MessageToMessageCodec<ByteBuf,Message> 可以@Sharable
   ```

   

 * 108    07:17  第3章_18-netty进阶-聊天业务-介绍

   ```
   业务场景 登录 退出 当前用户
   ```

   

 * 109    05:13  第3章_19-netty进阶-聊天业务-包结构

   ```
   代码结构组成
   cp.pipeline().addLast(LengthFiledBaseFrameDecoder);
   cp.pipeline().addLast(LoggingHandler)
   cp.pipeline().addLast(MessageCodecSharable)
   
   @Override
   public void channelActive(ctx){
   	new Thread().start();
   }
   ```

   

 * 110    22:01  第3章_20-netty进阶-聊天业务-登录

   ```
   登录输入与请求响应
   ```

   

 * 111    10:30  第3章_21-netty进阶-聊天业务-登录-线程通信

   ```
   
   ```

   

 * 112    10:06  第3章_22-netty进阶-聊天业务-业务消息发送

   ```
   
   ```

   

 * 113    11:55  第3章_23-netty进阶-聊天业务-单聊消息处理

 * 114    12:23  第3章_24-netty进阶-聊天业务-群聊建群处理

 * 115    05:58  第3章_25-netty进阶-聊天业务-群聊消息处理

 * 116    07:14  第3章_26-netty进阶-聊天业务-退出处理

 * 117    12:23  第3章_27-netty进阶-聊天业务-空闲检测

 * 118    09:38  第3章_28-netty进阶-聊天业务-心跳

 * 119    11:52  第4章_01-netty优化-扩展序列化算法

 * 120    11:23  第4章_02-netty优化-扩展序列化算法-json

 * 121    07:26  第4章_03-netty优化-扩展序列化算法-测试

 * 122    10:02  第4章_04-netty优化-参数-连接超时

 * 123    08:57  第4章_05-netty优化-参数-连接超时源码分析

 * 124    06:06  第4章_06-netty优化-参数-backlog-连接队列

 * 125    11:51  第4章_07-netty优化-参数-backlog-作用演示

 * 126    05:07  第4章_08-netty优化-参数-backlog-默认值

 * 127    05:19  第4章_09-netty优化-参数-backlog-ulimit&nodelay

```
ulimit -n 系统参数，一个进程能打开最大的文件描述符的数量

TCP_NODELAY：TCP参数据，关于尽可能多的发送数据

SO_SNDBUF 发送缓冲区，属于SocketChannel参数

SO_RCVBUF  接收缓冲区，既可用于是SocketChannel参数，也可用于是ServerSocketChannel参数

```




 * 128    12:50  第4章_10-netty优化-参数-backlog-分配器

   ```
   ALLOCATOR BtyeBuf分配器，属于SocketChannel参数，用来分配ByteBuf，ctx.alloc()
   
   RCVBUF_ALLOCATOR 属于SockectChannel参数
   
   ByteBuf类型 PooledUnsafeDirectByteBuf 池化的直接内存
   
   基础默认的配置项都在io.netty.channel.DefaultChannelConfig
   
   //判断操作系统类型
   PlatformDependent.isAndroid()
   ```

   

 * 129    16:58  第4章_11-netty优化-参数-backlog-rcv分配器

 * 130    04:54  第4章_12-netty优化-rpc-准备

 * 131    08:49  第4章_13-netty优化-rpc-服务端实现

 * 132    08:29  第4章_14-netty优化-rpc-客户端实现

 * 133    09:06  第4章_15-netty优化-rpc-gson问题解决

 * 134    09:10  第4章_16-netty优化-rpc-客户端-获取channel

 * 135    15:21  第4章_17-netty优化-rpc-客户端-代理

 * 136    19:23  第4章_18-netty优化-rpc-客户端-获取结果

 * 137    05:51  第4章_19-netty优化-rpc-客户端-遗留问题

 * 138    08:12  第4章_20-netty优化-rpc-客户端-异常调用

 * 139    06:35  第4章_21-netty源码-启动流程-nio回顾

 * 140    07:57  第4章_22-netty源码-启动流程-概述

 * 141    06:52  第4章_23-netty源码-启动流程-init

 * 142    09:01  第4章_24-netty源码-启动流程-register

 * 143    07:16  第4章_25-netty源码-启动流程-dobind0

 * 144    06:12  第4章_26-netty源码-启动流程-关注accept事件

 * 145    05:50  第4章_27-netty源码-eventloop-selector何时创建

 * 146    05:40  第4章_28-netty源码-eventloop-2个selector

 * 147    08:22  第4章_29-netty源码-eventloop-线程启动

 * 148    04:27  第4章_30-netty源码-eventloop-wakeup方法

 * 149    06:38  第4章_31-netty源码-eventloop-wakenUp变量

 * 150    06:35  第4章_32-netty源码-eventloop-进入select分支

 * 151    06:53  第4章_33-netty源码-eventloop-select阻塞多久

 * 152    06:24  第4章_34-netty源码-eventloop-select空轮询bug

 * 153    07:05  第4章_35-netty源码-eventloop-ioratio

 * 154    04:25  第4章_36-netty源码-eventloop-处理事件

 * 155    02:18  第4章_37-netty源码-accept流程-nio回顾

 * 156    16:17  第4章_38-netty源码-accept流程

 * 157    05:00  第4章_39-netty源码-read流程