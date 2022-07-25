* 根类加载器(Bootstrap)
* 扩展类加载器(Extension)
    加载/jre/lib/ext或系统变量目录下的类库
* 系统类加载器(AppclassLoad)
    加载classpath目录下的类库

#### 双亲委派机制

* 可以避免类的重复加载，当父类加载器已经加载了该类时，就没必要子再加载一次了。
* 安全因素，java的核心类不会被随意替换。

#### ClassLoader
* loadClass：加载名称为 name 的类，返回的结果是 java.lang.Class 类的实例。和findClass的不同之处在于：loadClass添加了双亲委派和判断
* findClass：查找名称为 name 的类，返回的结果是 java.lang.Class 类的实例()。
* defineClass(String name, byte[] b, int off, int len)
将byte字节解析成虚拟机能够识别的class对象，通常和findclass方法一起使用，在自定义类加载器时，会直接覆盖findclass方法获取要加载类的字节码，然后调用defineClass方法生成class对象。
* resolveClass(Class<?> c)：链接指定的 Java 类。
连接类


##### 类的加载流程
* 加载：加载是通过类加载器classLoader完成，它既可以是饿汉式加载类（预加载），也可以是懒加载lazy load（运行时加载）
* 验证：确保.class文件的字节流中含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身安全。文件格式验证、元数据验证、字节码验证、符号引用验证。
* 准备：准备阶段主是为类变量分配内存，设备类变量的初始值。
* 解析：虚拟机将常量池的符号引用替换成直接引用的过程。
* 初始化：虚拟机执行类构造器方法的过程。
* 使用：正常使用类信息
* 卸载：满足类卸载条件，jvm会从内存中卸载对应的类信息。
* 