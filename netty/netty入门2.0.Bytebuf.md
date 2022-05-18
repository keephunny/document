### ByteBuf读方法

| 方法               | 长度(字节) | 说明                                  |
| ------------------ | ---------- | ------------------------------------- |
| readByte           | 1          | 读取1字节内容                         |
| readBoolean        | 1          |                                       |
| readUnsignedByte   | 1          | 返回((short) (readByte() & 0xFF))；   |
| readShort          | 2          | 返回short                             |
| readUnsignedShort  | 2          | 返回readShort()& 0xFFFF；             |
| readMedium         | 3          | 返回转换后的int类型；                 |
| readUnsignedMedium | 3          | 返回转换后的int类型；                 |
| readInt            | 4          | 返回转换后的int类型；                 |
| readUnsignedInt    | 4          | 返回readInt()& 0xFFFFFFFFL；          |
| readLong           | 8          | 取8字节的内容；                       |
| readChar           | 1          | 取1字节的内容；                       |
| readFLoat          | 4          | 取4字节的int内容，转换为float类型；   |
| readDouble         | 8          | 取8字节的long内容，转换为double类型； |
| readBytes(n)       | n          | 取指定长度的内容，返回ByteBuf类型；   |
| readSlice(n)       | n          | 取指定长度的内容，返回ByteBuf类型；   |
| readBytes(n)       | n          | 读取指定长度                          |

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
| writeFloat |  |  |
| writeDouble |  |  |
| writeBytes |  |  |
| writeZero |  |  |
| int writeCharSequence(CharSequence sequence, Charset charset) | 写入字符串 | CharSequence为字符串类的父类，第二个参数为对应的字符集 |

* 这些方法的未指明返回值的，其返回值都是 ByteBuf，意味着可以链式调用来写入不同的数据
* 网络传输中，默认习惯是 Big Endian，使用 writeInt(int value)



readableBytes

ByteBuf buf = (ByteBuf)msg;
			byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			String request = new String(data, "utf-8");

