jvm 
jmm
指令重排
内存可见性
：自适应的自旋锁、锁粗化、锁消除、轻量级锁等，
https://blog.csdn.net/jiyiqinlovexx/article/details/50989328
https://www.cnblogs.com/leefreeman/p/7364030.html


深入理解JVM（八）——java堆分析
深入理解JVM（七）——性能监控工具
深入理解JVM（六）——类加载器原理
深入理解JVM（五）——垃圾回收器
深入理解JVM（四）——垃圾回收算法
深入理解JVM（三）——配置参数
深入理解JVM（二）——内存模型、可见性、指令重排序
深入理解JVM（一）——基本原理

https://www.cnblogs.com/leefreeman/category/1058724.html

双亲委派模式

jps、jstat、jmap、jstack 
jvisualvm.exe


深入java虚拟机
https://www.cnblogs.com/java-chen-hao/p/10488689.html

垃圾收信要器与内存分配策略
https://www.cnblogs.com/java-chen-hao/p/10488574.html



##### 内存溢出

jvm可用内存不足，常见的内存溢出：

* 栈溢出 死循环 死递归
* 堆溢出
* 方法区溢出
* 本机直接内存溢出

##### 内存泄漏

内存泄漏(memory leak)批无用的对象继续占用内存，没有在恰当的时候释放内存。内存泄潜心一般是资源管理问题或程序问题。