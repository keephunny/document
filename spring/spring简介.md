## sprint特点
* 方例解耦，简化开发。通过spring提供的Ioc容器，我们可以将对象之间的依赖关系交由spring进行控制，避免硬编码所造成的过度程序耦合。用户不必再为单实例模式类，属性文件解析等这些很底层的需求编写代码，可以更专注于上层的应用。
* AOP编程的支持。通过spring提供的AOP功能，方便进行面向切面的编程，许多不容易用传统OOP实现的功能可以通过AOP轻松实现。
* 声明式事务的支持。在spring中，我们可以从单调的事务管理代码中解脱出来，通过声明式方式灵活地进行事务的管理。提高开发效率和质量。
* 方例程序的测试。可以用非容器依赖的编程方式进行几乎所有的测试工作，在spring里很好的支持junit4，可以通过注解方便的测试spring程序。
* 方便集成各种优秀框架。spring不排拆各种优秀的开源框架。spring可以降低各种框架的使用难度。
* 降低Java EE API的使用难度。spring对很多jdbc、javamail、远程调用提供了封装层，通过spring的封装.
* java源码是经典的学习范例。spring源码设计精妙，结构清晰，值复学习和研究。

## spring框架结构
* 核心容器：提供了spring的基本功能，主要组有BeanFactory，它是工厂模式的实现，BeanFactory使用控制反转（IOC）模式将应用程序的配置和依赖性规范与实际应用程序代码分开。
* spring上下文：spring上下文是一个配置文件，向spring框架提供上下文信息，包括企业服务，JNDI、EJB、电子邮件、国际化等。
* spring AOP：通过配置管理特性AOP模块直接将面向切面的编程集成到spring框架中。
* spring DAO：JDBC DAO抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误信息，异常层次结构简化了错误处理。并且极大在降低了需要编写的异常代码数量。
* spring ORM：spring框架插入了若干个ORM框架，从而提供了ORM的对象关系工具。包括JDO、Hibernate、iBatisSQL Map。
* spring web：web上下文模块建立在应用程序上下文模块之上，为基于web的应用程序提供了上下文。简化了部分请求将参数绑定到域对象的工作。
* spring mvc：mvc框架是一个全功能构建web应用程序的mvc实现。通过策略接口，mvc框架变成高度可配置的。mvc容纳了大量的视图技术，jsp、velocity、Tiles、iText、POI。


https://www.cnblogs.com/ysocean/p/7466191.html