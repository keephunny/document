



    Session session = subject.getSession();
* getAttribute(Object key)：获取指定key的session对象。
* getAttributeKeys()：获取在session中存储的所有key。
* getHost()：获取当前主机IP。
* getId()：获取session的唯一id。
* getLastAccessTime()：获取最后访问时间。
* getStartTimestamp()：获取session启动时间。
* getTimeout()：获取session失效时间，单位毫秒。
* setTimeout(long n)：设置session失效时间。
* removeAttribute(key)：移除指定key的对象。
* stop()：销毁会话。
* touch()：更新会话最后访问时间。    

#### shiro三个实现方法
* DefaultSessionManager：使用的默认实现，用于javaSE环境 。
* ServletContainerSessionManager：使用的默认实现，用于web环境，其直接使用servlet容易的会话。
* DefaultWebSessionManager：用于web环境的实现，可以替代ServletContainerSessionManager，自己维护着会话，直接废弃了Servlet容器的会话管理。




在一般系统中，也可能需要在session失效后做一些操作： 
1.控制用户数，当session失效后，系统的用户数减少一个，控制用户数量在一定范围内，确保系统的性能 
2.控制一个用户多次登录，当session有效时，如果相同用户登录，就提示已经登录了，当session失效后，就可以不同提示，直接登录 
那么如何在session失效后，进行一系列的操作呢？ 
这里就需要用到监听器了，即当session因为各种原因失效后，监听器就可以监听到，然后执行监听器中定义好的程序就可以了 
监听器类为：HttpSessionListener类，有sessionCreated和sessionDestroyed两个方法 
自己可以继承这个类，然后分别实现 
sessionCreated指在session创建时执行的方法 
sessionDestroyed指在session失效时执行的方法 
例子：

public class OnlineUserListener implements HttpSessionListener{
      public void sessionCreated(HttpSessionEvent event){
                 //session创建时执行操作
      }

      public void sessionDestroyed(HttpSessionEvent event){
            //session失效时执行操作
    }
 }

