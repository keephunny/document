HttpSession常用的四个监听器
## HttpSessionListener
是对session的一个监听，主要监听关于session的两个事件，即初始化和销毁。针对所有的session接口
    void sessionCreated(HttpSessionEvent se);//当session创建时会收到通知
    void sessionDestroyed(HttpSessionEvent se);//当session销毁时会收到通知

## HttpSessionAttributeListener
是sessionAttribute的监听器，当在会话中加入属性，移除属性或替换属性时，就会调用httpSessionAttributeListener监听器。

    void attributeAdded(HttpSessionBindingEvent se);//在session添加对象时触发此操作
    void attributeRemoved(HttpSessionBindgEvent se);//修改、删除session中添加对象时触发
    void attributeReplaced(httpSessionBindingEvent se);//在session属性被重新设置时触发

需要设置到web.xml中就可以监听整个应用中的所有session。
不同点：HttpSessionListener只是监听session的创建和销毁，而HttpSessionAttributeListener是监听Session的属性的添加、修改和删除。

## HttpSessionBindingListener
HttpSessionBindingListener不需要配置web.xml。必须实例化后放入某一session中，才可以进行监听，通常是一对一的监听。
如果一个对象想要在自身被绑定到session或者从session中解绑的时候得到通知，则该对象需要实现HttpSessionBindingListener监听器。该监听器的实现类不需要再部署描述文件中配置。
可以用来自作用户列表：我们可以让每个listener对应一个username，这样就不需要每次再去session中读取username，进一步可以将所有操作在线列表的代码都移入listener，更容易维护。

实现了HttpSessionBindingListener接口的JavaBean 对象可以感知自己被绑定到Session 中和从Session 中删除的事件：、

绑定对象：
当对象被绑定到 HttpSession 对象中时，web 服务器调用该对象的  void valueBound(HttpSessionBindingEvent event)方法
当对象从 HttpSession 对象中解除绑定时，web 服务器调用该对象的void valueUnbound(HttpSessionBindingEvent event)方法


## HttpSessionActivationListener
实现了HttpSessionActivationListener接口的JavaBean 对象可以感知自己被活化和钝化的事件

当绑定到HttpSessoion 对象中的对象将要随HttpSession对象被钝化之前，web服务器调用如下方法，

sessionWillPassivate(HttpSessionBindingEvent event)方法；

当绑定到 HttpSession 对象中的对象将要随 HttpSession 对象被活化之后，web服务器调用该对象的
 void sessionDidActive(HttpSessionBindingEvent event)方法

活化和钝化：
1. (钝化) Session对象持久化到一个存储设备中
2. (活化) Session对象从一个存储设备中恢复 
