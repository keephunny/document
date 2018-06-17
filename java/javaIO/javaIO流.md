I/O流主要分为字节流和字符流
## 基本流
* FileInputStream字节输入流，对文件进行读取操作，父类InputStream
* FileOutputStream字节输出流，对文件进行写入操作，父类OutputStream
* FileReader：字符输入流，父类InputStreamReader
* FileWriter：字符输出流，父类OutputStreamWriter

## 缓冲流
* BufferedInputStream：缓冲字节输入流，父类FileInputStream
* BufferedOutputStream：缓冲字节输出流，父类FileOutputStream
* BufferedReader：缓冲字符输入流，父类Reader。
* BufferedWriter：缓冲字符输出流，父类Writer。

## 转换流
* InputStreamReader：输入流，父类Reader
* OutputStreamWriter：输出流，父类Writer

## 打印流
* PrintStream：基本字节流
* PrintWriter：基本字符流

## 基本字节流和基本字符流的区别
1. 字节流可以读写任何文件，字符流只能读取普通文件。
2. 读写文本文件尽量使用字符流，这要比字节流效率高。
3. 读写媒体文件用字节流。
4. 基本字节流没有缓中区。
5. 基本字符流有缓冲区，默认是8K，不能指定缓冲区大小。
6. 字节流是一个一个字节读，效率比较低，虽然可以读取各种各样的文件，但是最适用于读取媒体文件。
7. 字符流是一个一个字符读，效率比较高。

## 缓冲流和基本流的区别
1. 缓冲流效率要高于基本流。
2. 缓冲字符流弥补了基本字符流不能设置缓冲区大小的缺点。
3. 缓冲字节流和基本字节流相比，建议使用基本字节流。
4. 缓冲字符流和基本字符流相比，建议使用缓冲字符流。

## 转换流的作用
转换流的作用主要解决在进行读写操作时出现的中文乱码，java默认处理文件时是gbk，当将文件保存为utf-8时，时行读写操作就会出现中文乱码。
    //用转换流读写
    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("a.txt"), "utf-8");
    osw.write("中国");
    osw.close();
        
        
    InputStreamReader isr = new InputStreamReader(new FileInputStream("a.txt"),"utf-8");
        
    char[] cc = new char[10];
    isr.read(cc);
    String ss = new String(cc);
    System.out.println(ss);
    isr.close();

    http://www.cnblogs.com/nianzhilian/p/9022366.html