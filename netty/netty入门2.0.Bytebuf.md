netty是一款异步事件驱动的网络应用程序框架，支持快速地开发可维护的高性能面向协议的服务器和客户端。

网络传输数据的基本单位是字节，java nio提供了ByteBuffer作为字节容器，但是这个类比较复制和繁琐。netty提供了ByteBuf提供了丰富的api，ByteBuf维护着两个索引，一个读索引，一个写索引。

* 设计：统一的 API，支持多种传输类型，阻塞的和非阻塞的,简单而强大的线程模型,真正的无连接数据报套接字,支持链接逻辑组件以支持复用
* 易用性：翔实的 Javadoc 和大量的示例集
* 性能：拥有比 Java 的核心 API 更高的吞吐量以及更低的延迟，得益于池化和复用，拥有更低的资源消耗
* 最少的内存复制
* 健壮性：不会因为慢速、快速或者超载的连接而导致OutOfMemoryError，消除在高速网络中 NIO 应用程序常见的不公平读/写比率
* 安全性：完整的 SSL/TLS 以及 StartTLS 支持



 readIndex和writeIndex均为0的16字节的ByteBuf

1. 如果readerIndex和WriterIndex的值一样的时候，如果继续向前读取数据。将会抛出IndexOutOf-BoundsException 。

2. 以read或者write开头的ByteBuf方法，将会推进其对应的索引，而名称以set或get开头操作不会修改索引。

3. ByteBuf具有最大的容量值，试图移动写索引超过这个值，将会触发一个异常。其默认的限制为Integer.MAX_VALUE

#### ByteBuf堆缓冲区

最常用的ByteBuf模式，将数据存储在jvm的堆空间中，这种模式被称为支撑数组(backing array)，它能在没有使用池化的情况下提供快速的分配和释放。

```
ByteBuf byteBuf = Unpooled.buffer();
//向byteBuf中写入数据
for(int i= 65;i<65+26;i++){
	byteBuf.writeByte(i);
}
//从byteBuf中读取数据
byte[] bytes = new byte[26];
byteBuf.readBytes(bytes);
System.out.println(new String(bytes));
```

#### ByteBuf直接缓冲区

直接缓中区是另一种ByteBuf的模式，

```
ByteBuf byteBuf = Unpooled.directBuffer();
//向byteBuf中写入数据
for(int i= 65;i<65+26;i++){
	byteBuf.writeByte(i);
}
//从byteBuf中读取数据
byte[] bytes = new byte[26];
byteBuf.readBytes(bytes);
System.out.println(new String(bytes));
```

#### ByteBuf复合缓冲区

是多个ByteBuf提供一个聚合视图，根据实际的需要添加或删除一个ByteBuf的实例。ByteBuf的子类 – compositeByteBuf实现了这个模式，它提供了一个将多个缓冲区一个统一的视图。





#### ByteBuf基本方法

```
//容量
public abstract int capacity();

//能否读取
isReadable();

//能否写入
isWriteable();
//是否是字节数组
hashArray();
//如果是字节数据，则返回该数组，否则抛出UnsupportedOperationException
array();

//调整此缓冲区的容量
public abstract ByteBuf capacity(int newCapacity);

//最大容量
public abstract int maxCapacity();

//分配器，用于创建ByteBuf对象
public abstract ByteBufAllocator alloc();

//获取被包装wrap的ByteBuf对象
public abstract ByteBuf unwrap();

//是否 NIO Direct Buffer
public abstract boolean isDirect();

//是否为只读
public abstract boolean isReadOnly();

//获取读取的位置
public abstract int readIndex();

//设置读取位置
public abstract ByteBuf readerIndex(int readerIndex)

//获取写入的位置 
public abstract int writerIndex()

//设置写入的位置
public abstract ByteBuf writerIndex(int writerIndex);

//设置读取和写入的位置
public abstract ByteBuf setIndex(int readerIndex,int writerIndex);

//剩余可读字节数
public abstract int readableBytes();

//剩余可写字节数
public abstract int writeableBytes();

//标记读取位置
public abstract ByteBuf markReaderIndex();

//标记写入位置
public abstract ByteBuf markWriterIndex();
```

#### ByteBuf读写方法

```
//绝对读取，不改变索引位置
//指定位置读取数据 默认大端 getShort getLong ……
public abstract int getInt(int index)
//指定位置读取数据 小端
public abstract int getIntLE(int iddex)

//改变索引位置 读取int readerIndex增加4
public abstract int readInt();
//小端读取
public abstract int readIntLE();

//绝对写入，不改变索引位置
//在指定位置写入int 不改变索引位置  setShort setLong
public abastract ByteBuf setInt(int index,int value)
//小端读取
public abstract ByteBuf setIntLE(int index,int value)
```

#### ByteBuf查找方法

```
//指定value在ByteBuf中的位置 包左不包右
public abstract int indexOf(int fromIndex,int toIndex,byte value);

//查找第一次出现value的位置
public abstract int bytesBefore(byte value);

//从读索引开始位查找length长度，查找value出现的位置，不改变索引位置
public abstract int bytesBefore(int length,byte value);

//从index开始查找length长度，查找value出现的位置，不改变索引位置
public abstract int bytesBefore(int index,int length,byte value);

//遍历ByteBuf，进行自定义处理
public abstract int forEachByte(ByteProcessor process);
public abstract int forEachByte(int dex,int length,ByteProcessor process);
 	buf.forEachByte(ByteProcessor.FIND_CRLF);
//倒序遍历
public abstract int forEachByteDesc(ByteProcessor processor);
public abstract int forEachByteDesc(int index,int length,ByteProcessor processor);
```

#### ByteBuf释放操作

```
//释放已读的字节空间
public abstract ByteBuf discardReadBytes();
//释放部份已读的字节空间，可能会释放也可能不释放
public abstract ByteBuf discardSomeReadBytes();
//清空字节空间 readerIndex=writerIndex=0；标记清空
public abstract ByteBuf clear();
```

#### ByteBuf拷贝操作

* 派生拷贝，内容使用相同一部份，相互关联，但读写索引是独立的，不会增加引用计数。

  ```
  //拷贝整个字节数组，共享，相互影响
  public abstract ByteBuf duplicate();
  //拷贝可读部份的字节数组，共享，相互影响
  public abstract ByteBuf slice();
  public abstract ByteBuf slice(int index,int length);
  
  public abstract ByteBuf readSlice(int length);
  public abstract ByteBuf readRelainedSlice(int length)
  
  //retained方法，会自增引用计数
  public abstract ByteBuf retainedSlice();
  public abstract ByteBuf retainedSlice(int index,int length);
  public abstract ByteBuf retainedDuplicate();
  ```

* 原生拷贝，相关互独立，互不影响

  ```
  //拷贝可读部份的字节数组，独立，互不影响
  public abstract ByteBuf copy();
  public abstract ByteBuf copy(int index,int length);
  ```

  





### ByteBuf读方法

| 方法                                          | 长度(字节) | 说明                                  |
| --------------------------------------------- | ---------- | ------------------------------------- |
| readByte                                      | 1          | 读取1字节内容                         |
| readBoolean                                   | 1          |                                       |
| readUnsignedByte                              | 1          | 返回((short) (readByte() & 0xFF))；   |
| readShort                                     | 2          | 返回short                             |
| readUnsignedShort                             | 2          | 返回readShort()& 0xFFFF；             |
| readMedium                                    | 3          | 返回转换后的int类型；                 |
| readUnsignedMedium                            | 3          | 返回转换后的int类型；                 |
| readInt                                       | 4          | 返回转换后的int类型；                 |
| readUnsignedInt                               | 4          | 返回readInt()& 0xFFFFFFFFL；          |
| readLong                                      | 8          | 取8字节的内容；                       |
| readChar                                      | 1          | 取1字节的内容；                       |
| readCharSequence(int length, Charset charset) | length     |                                       |
| readFLoat                                     | 4          | 取4字节的int内容，转换为float类型；   |
| readDouble                                    | 8          | 取8字节的long内容，转换为double类型； |
| readBytes(n)                                  | n          | 取指定长度的内容，返回ByteBuf类型；   |
| readSlice(n)                                  | n          | 取指定长度的内容，返回ByteBuf类型；   |
| readBytes(n)                                  | n          | 读取指定长度                          |

### ByteBuf写方法


| 方法               | 长度(字节) | 说明                                  |
| ------------------ | ---------- | ------------------------------------- |
| writeBoolean           | 1          | 01 true  00 false                     |
| writeByte |  | 写入 byte 值 |
| writeShort |  | 写入 short 值 |
| writeMedium |  | 写入 int 值 |
| writeInt |  | Little Endian（小端写入），即 0x250，写入后 50 02 00 00 |
| writeIntLE(int value) |  | Little Endian（小端写入），即 0x250，写入后 50 02 00 00 |
| writeLong |  |  |
| writeChar |  |  |
| setCharSequence(int index, CharSequence sequence, Charset charset) | | |
| writeFloat |  |  |
| writeDouble |  |  |
| writeBytes |  |  |
| writeZero |  |  |
| int writeCharSequence(CharSequence sequence, Charset charset) | 写入字符串 | CharSequence为字符串类的父类，第二个参数为对应的字符集 |

* 这些方法的未指明返回值的，其返回值都是 ByteBuf，意味着可以链式调用来写入不同的数据
* 网络传输中，默认习惯是 Big Endian，使用 writeInt(int value)





1. ByteBuf内部是字节序列，支持随机访问和顺序访问，可以通过下标访问，使用Unpooled创建
2. 内部通过读写两个索引来控制读写，两个索引都要对应的标记方法，读写需要注意越界异常
3. 已经读取过的内容相当于废弃内容，可以通过discardReadBytes来腾出该部分空间用于写入，不过这取决于不同子类实现
4. 派生缓冲区会共享数据，但是读写索引是独立的，引用计数是共享的，对应的也有一些方法提供buffer并且增加引用计数
5. 可以通过方法获取内部的字节数组



#### 索引管理

markReadIndex()、markWriterIndex()、restWriterIndex()和resetReaderIndex()来标记和重置ByteBuf的ReaderIndex及WriterIndex。

* clear()：将readerIndex和writerIndex的值设置为0,并不会清除内存中的数据。
* readerIndex(int)、writerIndex(int)来将索引移动到指定位置。



**readableBytes**

```
ByteBuf buf = (ByteBuf)msg;
byte[] data = new byte[buf.readableBytes()];
buf.readBytes(data);
String request = new String(data, "utf-8");
```
**写入**
```
ByteBuf buf=Unpooled.buffer(16);
Random random=new Random();
while(buf.writableBytes()>=4){
	buf.writeInt(random.nextInt());
}
```
**读取**

```
ByteBuf buf=Unpoole.buffer(16);
buf.writeBytes("aaaaaa".getBytes());
while(buf.isReadable()){
	System.out.println((char) buf.readByte());
}
```



#### ByteBuf创建

```
#创建一个默认的ByteBuf池化基于直接内存。
ByteBuf buffer=ByteBufAllocator.DEFAULT.buffer(10);
read index:0 write index:0 capacity:10
#池化基于堆的 ByteBuf
ByteBuf buffer=ByteBufAllocator.DEFAULT.heapBuffer(10);
#池化基于直接内存
ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(10);

ByteBuf buf1 = Unpooled.buffer(16);
int initialCapacity, int maxCapacity
ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(16, 20);
DEFAULT_MAX_CAPACITY=Integer.MAX_VALUE
```

##### 直接内存和堆内存

直接内存的创建和销毁代价昂贵，但读写性能较高，少一次内存复制，适合池化功能。

直接内存对GC压力小，因为这部份内容不受JVM垃圾回收的管理

##### 池化和非池化

池化的最大意义是可以重用ByteBuf

* 没有池化，每次都得创建新的ByteBuf实例，这个直接操作内存或堆内存成本较高。

* 池化，可以重用池中的ByteBuf实例，并且采用了与memalloc类似的内存分存算法提升分配效率。

* 高并发时，池化功能更节约内存，减少内存溢出的可能。

  ```
  -Dio.netty.allocator.type={unpooled|pooled}
  ```

#### 写入

```
buffer.writeBytes(new byte[]{1, 2, 3, 4});
read index:0 write index:4 capacity:10
         +-------------------------------------------------+
         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
+--------+-------------------------------------------------+----------------+
|00000000| 01 02 03 04                                     |....            |
+--------+-------------------------------------------------+----------------+
```

##### 扩容

当写入容量不够时，就会引发扩容

* 写入后的数据小于512,扩容到下一个16的倍数。
* 写入后的数据超过512,扩容到下一个2^n
* 扩容容量不能超过max_capacity，默认是 DEFAULT_MAX_CAPACITY=Integer.MAX_VALUE

##### 读取

读过的内容，就属于废弃部分了，再读只能读那些尚未读取的部分

read开头会移动标记

get 开头的一系列方法，这些方法不会改变 read index

```
buffer.markReaderIndex();
System.out.println(buffer.readByte());
buffer.resetReaderIndex();
```

##### 释放

Netty中有堆外内存的ByteBuf实现，堆外内存需要手动释放，而不是等GC回收。

* UnpooledHeapByteBuf使用JVM内存，只需要等待GC回收。
* UnpooledDirecByteBuf使用的是直接内存，需要指定方法回收内存
* PooledByteBuf和它的子类使用了池化机制，需要更复杂的规则回收内存

Netty采用了引用计数法来控制回收内存，每个ByteBuf都实现了ReferenceCounted接口，初始计数为1,release()减1,retain()加1,如果计数为0,ByteBuf内存被回收。基本规则是，谁是最后使用者，谁负责 release。

###### 入站规则

* 入原始的ByteBuf不做处理，调用上ctx.fireChannelRead(msg)向后传递，这时无须release。
* 将原始的ByteBuf转换为其它类型的java对象，这时ByteBuf就没有用了，必须release。
* 如果不调用ctx.fireChannelRead(msg)向后传递，那么也必须release。
* 注意各种异常，如果ByteBuf没有成功传递到下一个ChannelHandler，必须release。
* 假设消息一直向后传递，那么TailContext会负责释放未处理消息，原始的ByteBuf。

###### 出站规则

* 出站消息最终都会转ByteBuf输出，一直向前传递，由HeadContext flush后release。

###### 异常处理

* 有时候不清楚ByteBuf被引用了多少次，但又必须彻底释放，可以循环调用release直到返回true。

TailContext 释放未处理消息逻辑

```
protected void onUnhandledInboundMessage(Object msg) {
    try {
        logger.debug(
            "Discarded inbound message {} that reached at the tail of the pipeline. " +
            "Please check your pipeline configuration.", msg);
    } finally {
        ReferenceCountUtil.release(msg);
    }
}

public static boolean release(Object msg) {
    if (msg instanceof ReferenceCounted) {
        return ((ReferenceCounted) msg).release();
    }
    return false;
}
```

##### 切片

零拷贝的体现之一，对原始ByteBuf进行切片成多个ByteBuf，切片后的ByteBuf并没有发生内存复制，还使用原始的ByteBuf内存，切片后的ByteBuf维护独立的read、write指针。

切片过程中没有发生数据复制，分割出来的ByteBuf不能扩容，因物理内存一致，改变原来的数据，新的数据也同叔叔改变，反之亦然。

无参 slice 是从原始 ByteBuf 的 read index 到 write index 之间的内容进行切片，切片后的 max capacity 被固定为这个区间的大小，因此不能追加 write

##### duplicate

零拷贝的体现之一，截取了原始ByteBuf所有内容，并且没有max capacity的限制，也是与原始ByteBuf使用同一块底层内存，只是读写指针独立的。

##### copy

将底层内存数据进行深拷贝，因此无论读写，都与原始ByteBuf无关。

##### CompositeByteBuf

零拷贝的体现之一，可以将多个ByteBuf合并成一个逻加上ByteBuf，避免拷贝，只会将未来读的数据逻辑组成一个新的ByteBuf。

```
ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(5);
buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});
ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(5);
buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

CompositeByteBuf buf3 = ByteBufAllocator.DEFAULT.compositeBuffer();
// true 表示增加新的 ByteBuf 自动递增 write index, 否则 write index 会始终为 0
buf3.addComponents(true, buf1, buf2);


// 当包装 ByteBuf 个数超过一个时, 底层使用了 CompositeByteBuf
ByteBuf buf3 = Unpooled.wrappedBuffer(buf1, buf2);
```

CompositeByteBuf是一个组合的ByteBuf，内部维护了一个Component数组，每个Component管理一个ByteBuf，记录了这个ByteBuf相对整体偏移量等信息，代表着整体中某段的数据。

* 优点：对外是一个虚拟视图，组合这些ByteBuf不会产生内存复制。
* 缺点：复杂了很多，多次操作会带来性能的损耗。



ByteBuf特点

* 池化可以重用池中的ByteBuf实例，更节约内存，减少内存溢出的问题。

* 读写指针分离，不需要像ByteBuffer一样切换读写模式

* 可以自动扩容

* 支持链式调用，使用方便

* 零拷贝

  

