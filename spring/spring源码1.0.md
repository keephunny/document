![image-20220218143742731](spring源码1.0.assets/image-20220218143742731.png)





spring源码

IOC：控制反转

DI：依赖注入

Bean的生命周期

创建对象的方式：new factory 反射

```
Class clazz=Class.forName("xxx.xx");
Class clazz=对象.getClass();
Class clazz=类.class;


System.getenv();
System.getProperties();

```

blog.csdn.net/qq_41701956/article/details/116354268



### 核心模块

Spring总共有20多个模块，1300多个不同的文件构成，而这些组件补分别整合在核心容器(core Container)、AOP、Instrmentation、Data Access、web、Messaging、Test等六个模块中。

* spring core：提供了框架的基本组成部份，包括控制反转(IOC)和依赖注入(DI)功能。
* spring beans：提供了BeanFactory，是工厂模式的一个经典实现，Spring将管理对象称为Bean。
* spring context：构建于core封装包基础上的context封装包，提供了一种框架式的对象访问方法。
* spring jdbc：提供了一个JDBC的抽象层，消除了烦琐的JDBC编码和数据库厂商特有的错误代码解析，简化JDBC。
* spring aop：提供了面向切面的编程实现，让你可以自定义拦截器、切片等。
* spring web：提供了针对web开发的集成特性，利用servlet listeners进行IOC容器初始化和针对web的ApplicationContext。
* spring test：主要为测试提供支持的，支持使用Junit或TestNG对spring组件进行单元测试和集成测试。



#### spring设计模式

* 工厂模式：BeanFactory简单工厂模式，用来创建对象的实例。
* 单例模式：Bean默认为单例模式。
* 代理模式：spring的AOP用到了JDK动态代理和CGLIB字节码生成技术。
* 模板方法：用来解决重复的问题，例如RestTemplate、JmsTemplate、JpaTemplate。
* 观察者模式：定义对象键一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖它的对象都会得到通知被更新。

##### spring context

spring context应用上下文模块，提供了spring框架的基础功能，BeanFactory是任何以spring为基础应用的核心，spring框架建立在此模块之上，它使spring成为一个空器。

Bean工厂是工厂模式的一个实现，提供了控制反转功能，用来把应用的配置和依赖从真正的应用代码中分离。最常用的就是XmlBeanFacotry，它根据XML文件中定义加载beans。

##### spring事件类型

* 上下文更新事件(ContextRefreshedEvent)：在调用ConfigurableApplicationContext接口中的refresh方法时被触发。
* 上下文开始事件(ContextStartedEvent)：当容器调用ConfigurableAppliicationContext的start方法开始重新开始容器时触发该事件。
* 上下文停止事件(ContextStoppedEvent)：当容器调用ConfigurableApplicationContext的stop方法停上容器时触发该事件
* 上下文关闭事件(ContextClosedEvent)：当ApplicationContext被关闭时触发该事件，容器被关闭时，其管理的所有单例Bean都被销毁。
* 请求处理事件(RequestHandleEvent)：在web应用中，当一个http请求结触发该事件，如果一个bean实现了ApplicationListener接口，当一个ApplicationEvent被发布以后，bean会自动被通知。

##### spring组件

* 接口：定义功能
* Bean类：包含属性setter、getter、函数等。
* Bean配置文件：包含类的信息以及如何配置它们。
* spring AOP：面向切面编程的功能。
* 用户程序：使用接口。

##### IOC功能

* 依赖注入
* 依赖检查
* 自动装配
* 支持集合
* 指定初始化方法和销毁方法
* 支持回调某些方法

IOC最重要的就是容器，容器管理着Bean的生命周期，控制着Bean的依赖注入。

##### BeanFactory 和 ApplicationContext

BeanFactory和ApplicationContext是spring的两大核心接口，都可以当作spring的容器，其中ApplicationContext是BeanFactory的子接口。

BeanFactory：是spring里面最底层的接口，包含了各种Bean的定义，读取bean配置文件，管理bean的加载、实例化、控制bean的生命周期、维护bean之间的依赖关系。

ApplicationContext接口作为BeanFactory的派生，除了提供BeanFactory所具有的功能外，还提供了更完整的框架功能。

* 继承MessageSource，支持国际化
* 统一的资源文件访问方式 
* 提供在监听器中注册的bean的事件
* 同时加载多个配置文件
* 载入多个（有继承关系）上下文，使得每个上下文都专注于一个特定的层次。