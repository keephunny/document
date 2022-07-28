java安全模型的核心就是java沙箱(sandbox)，沙箱是一种限制程序运行的环境，将java代码限定在虚拟机特定的运行范围中，并且严格限制代码对本地资源的访问，通过这样的措施来保证对代码的有效隔离，防止对本地系统造成破坏。在Java中将执行程序分成本地代码和远程代码两种，本地代码默认视为可信任的，而远程代码则被看作是不受信的。对于授信的本地代码，可以访问一切本地资源。而对于非授信的远程代码在早期的Java实现中，安全依赖于沙箱 (Sandbox) 机制。
#### java沙箱组成
* 字节码校验器(bytecode verifier) 确保java类文件遵循java语言规范，帮助程序实现内存保护，并不是所有类都经过字节码校验器，例如核心类。
* 存取控制器(access controller) 控制核心API对操作系统的存取权限，控制策略可以由用户指定。
* 安全管理器(security manager)是核心API和系统间的主要接口，实现权限控制，比存取控制器优先级高。
* 安全软件包(security package) java.security下的类和扩展包下的类，允许用户为自已的应用增加新的安全特性，包括：安全提供者，消息摘要、数字签名、加密、鉴权等。
* 类加载器(class loader) 双亲委派机制、安全校验等、防止恶意代码干涉，守护类库边界。

JVM为不同类加载器载入的类提供了不同的命名空间，命名空间由一系列唯一的名称组成，每一个被装载的类将有一个名字，这个命名空间是由jvm为每一个类装载器维护的，它们互相之前不可见。




#### 运行时权限 
java.lang.RuntimePermission

* accessClassInPackage：允许代码访问指定包中的类
* accessDeclaredMembers：允许代码使用反射访问其他类中私有或保护的成员
* createClassLoader：允许代码实例化类加载器
* createSecurityManager：允许代码实例化安全管理器，它将允许程序化的实现对沙箱的控制
* defineClassInPackage：允许代码在指定包中定义类
* exitVM：允许代码关闭整个虚拟机
* getClassLoader：允许代码访问类加载器以获得某个特定的类
* getProtectionDomain：允许代码访问保护域对象以获得某个特定类
* loadlibrary：允许代码装载指定类库
* modifyThread：允许代码调整指定的线程参数
* modifyThreadGroup：允许代码调整指定的线程组参数
* queuePrintJob：允许代码初始化一个打印任务
* readFileDescriptor：允许代码读文件描述符（相应的文件是由其他保护域中的代码打开的）
* setContextClassLoader：允许代码为某线程设置上下文类加载器
* setFactory：允许代码创建套接字工厂
* setIO：允许代码重定向System.in、System.out或System.err输入输出流
* setSecurityManager：允许代码设置安全管理器
* stopThread：允许代码调用线程类的stop()方法
* writeFileDescriptor：允许代码写文件描述符 

https://blog.51cto.com/u_15278282/2931901
https://www.runoob.com/manual/jdk11api/java.base/java/lang/SecurityManager.html

JAVA动态加载外部JAR