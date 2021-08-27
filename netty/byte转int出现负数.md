在字节流读取十六进制时，转换的过程中会出现负数，因为byte是8bit，而int是32bit，在转换的前需要将高位置0，这样就不会出现补码导致转换错误。

```
public static String bytes2HexString(byte[] b) {
    String ret = "";
    for (int i = 0; i < b.length; i++) {
        String hex = Integer.toHexString(b[ i ] & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
            ret += hex.toUpperCase();
    }
    return ret;
}
```
1.byte的大小为8bits而int的大小为32bits
2.java的二进制采用的是补码形式

Java中的一个byte，其范围是-128~127的，而Integer.toHexString的参数本来是int，如果不进行&0xff，那么当一个byte会转换成int时，对于负数，会做位扩展，举例来说，一个byte的-1（即0xff），会被转换成int的-1（即 0xffffffff），那么转化出的结果就不是我们想要的了。


而0xff默认是整形，所以，一个byte跟0xff相与会先将那个byte转化成整形运算，这样，结果中的高的24个比特就总会被清0，于是结果总是我们想要的。