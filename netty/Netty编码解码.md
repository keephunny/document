

ByteBuf主要提供以下方法
* readByte：读取1字节内容
* skipBytes(n)：跳过n个字节
* readUnsignedByte：读取1字节内容，返回无符号short类型。（(short) (readByte() & 0xFF)）；
* readShort：读取2字节内容，返回转换后的short类型
* readUnsignedShort：读取2字节内容，返回无符号short类型。
* readMedium：读取3字节内容，返回int类型
* readUnsignedMedium：读取3字节内容，返回无符号int类型
* readInt：读取4字节内容
* readUnginedInt：读取4字节内容，返回无符号int类型
* readLong：读取8字节内容
* readChar：读取1字节内容
* readFloat：读取4字节的int内容，转换换为float类型
* readDouble:读取8字节的long内容，转换为double类型
* readBytes(n)：读取指定长度的内容，返回ByteBuf类型
* readSlice(n)：读取指定长度的内容，返回ByteBuf类型


### Netty解码器
* LineBasedFrameDecoder 回车换行解码器，如果消息以回车换行符作为结束标识，则可以直接使用该解码器。只需要添加到ChannelPipeline中，不需要重新实现。
```
    pipeline().addLast(new LineBasedFrameDecoder(1024));
    pipeline().addLast(new StringDecoder());
    pipeline().addLast(new UserServerHandler());
```

* DelimiterBasedFrameDecoder 分隔符解码器，可以指定消息结束的分隔符，自动完成以分隔符作为码流结束的消息解码。分隔符并非以char或string作为构造参数，而是ByteBuf。
```
    ByteBuf delimiter = Unpooled.copiedBuffer("$".getBytes());
   ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
   ch.pipeline().addLast(new StringDecoder());
   ch.pipeline().addLast(new UserServerHandler());
```

* FixedLengthFrameDecoder 固定长度解码器，能够按照指定长度对消息进行自动解码，不需要考虑TCP的粘包/拆包问题。

* LengthFieldBaseDframeDecoder

* ObejctEncode java序列化编码器，将实了Serializable接口对象序列化为byte[]，然后写入到ByteBuf中用于消息的跨网络传输。

* ByteTOMessageDecoder 抽象解码器，为了方便将ByteBuf解码成业务POJO对象，Netty提供了抽象工具解码类。
* MessageToMessageDecoder 抽象解码器，将一个对象二次解码为其他对象。从SocketChannel读取到的TCP数据报是ByteBuffer，实际是字节数组并将其解码为Java对象，然后对java对象根据某些规则做二次解码，将期解码为另一个POJO对象。

### netty解码器
* DelimiteBaseFrameDecoder：解决TCP的粘包解码器
* StringDecoder：消息转成String的解码器
* LineBaseFrameDecoder：自动完成标识符分隔解码器
* FixedLengthFrameDecoder：固定长度解码器，二进制
* Base64Decoder：解码器，只需要对内容进行base64编码，分隔符不需要编码。

### netty编码器
* Base64Encoder：base64编码器
* StringEncoder：消息转成String编码器
* LineBasedFrameDecoder：自动完成标识符分隔符编码器
* MessageToMessageEncoder：根据消息对象编码为消息对象。
