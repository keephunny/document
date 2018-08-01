spring创建bean有单例模式(singleton)和原始模型模式(prototype)两种模式    
在默认的情况下，spring中创建bean都是单例模式，一般情况下有状态的bean需要使用prototype模式，而对于无状态的bean一般采用singleton模式，一般dao都是无状态的

### singleton作用域
spring ioc容器中只会存在一个共享的bean实例，并且把所有对bean的请求，只要id与该bean定义匹配，则只会返回该bean的司一实例。线程不安全，性能高。
### prototype作用域
每次对该bean请求都会创建一个新的bean实例，spring不能对该bean的整个生命周期负责，具有prototype作用域的bean创建后交由调用者负责销毁对象回收资源。

* 需要回收重要资源（数据库连接等）的事宜配置为singleton，如果配置成prototype需要应用确保资源正常回收。
* 有状态的bean配置成singleton会引发未知问题。

### 有状态会话bean
每个用户都有自己特有的一个实例，在用户生存期内，bean保持了用户的信息，即有状态，一旦用户灭亡，bean的生命周期也结束，即每个用户最初都会得到一个初始的bean。
### 无状态会话bean
bean一旦实例化就被加进会话池中，各个用户都可以共用，即使用户已消亡，bean的生命周期也不一定结束，它可能依然存在于会话池中。由于没有特定的用户，那么也不能保持某一用户状态，所以叫无状态bean。