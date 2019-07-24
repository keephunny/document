



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

