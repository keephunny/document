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



