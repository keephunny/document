### pom.xml
```
    <!--begin mybatis相关-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.3.1</version>
    </dependency>
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>1.2.4</version>
    </dependency>
    <dependency>
        <groupId>net.sf.ehcache</groupId>
        <artifactId>ehcache</artifactId>
        <version>2.10.6</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis.caches</groupId>
        <artifactId>mybatis-ehcache</artifactId>
        <version>1.1.0</version>
    </dependency>
```

### mapper.xml
```

<mapper namespace="com.proj.web.core.dao.UserInfoMapper">

    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
</mapper>
```


# mybatis
```
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
mybatis.config-location=classpath:mybatis/mybatis-config.xml

spring.cache.jcache.config=classpath:ehcache.xml
```

```
<cache eviction="FIFO" flushInterval="60000" size="512"readOnly="true"/>


```
eviction回收策略:
1. LRU 最近最少使用的，移除最长时间不被使用的对象，这是默认值
2. FIFO 先进先出，按对象进入缓存的顺序来移除它们
3. SOFT 软引用，移除基于垃圾回收器状态和软引用规则的对象
4. WEAK 弱引用，更积极的移除基于垃圾收集器状态和弱引用规则的对象
4. flushInterval刷新间隔
可以被设置为任意的正整数，而且它们代表一个合理的毫秒形式的时间段。 默认情况不设置，即没有刷新间隔，缓存仅仅在调用语句时刷新
4. size引用数目
可以被设置为任意的正整数，要记住缓存的对象数目和运行环境的可用内存资源数目，默认1024
4. readOnly只读
属性可以被设置为true后者false。 只读的缓存会给所有调用者返回缓存对象的相同实例，因此这些对象不能被修改，这提供了很重要的性能优势。 可读写的缓存会通过序列化返回缓存对象的拷贝，这种方式会慢一些，但很安全，因此默认为false




### 二级缓存的说明
1. 缓存是以namespace为单位的，不同namespace下的操作互不影响
2. insert,update,delete操作会清空所在namespace下的全部缓存
3. 通常使用MyBatis Generator生成的代码中，都是各个表独立的，每个表都有自己的namespace

#### 多表操作下不能使用二级缓存
注意：Mybatis二级缓存对于访问多的查询请求且用户对查询结果实时性要求不高，此时可采用Mybatis二级缓存技术降低数据库访问量，提高访问速度。 如果需要多表,以及数据多变的缓存建议使用redis，多表操作不能使用二级缓存，这样会导致数据不一致的问题


#### 一级缓存
使用MyBatis开启一次和数据库的会话，MyBatis会创建出一个SqlSession对象表示一次数据库会话，在对数据库的一次会话中， 有可能会反复地执行完全相同的查询语句，每一次查询都会去查一次数据库,为了减少资源浪费，mybaits提供了一种缓存的方式(一级缓存)。
一级缓存是sqlsession级别的，默认开启，不可关闭。
##### 生命周期
当执行一个查询操作时，mybatis会创建一个sqlsession对象，sqlsession对象找到具体的Executor， Executor持有一个PerpetualCache对象；当查询结束(会话结束)时，SqlSession、Executor、PerpetualCache对象占有的资源一并释放掉。

如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用。
如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用。

SqlSession中执行了任何一个update操作(update()、delete()、insert()) ，都会清空PerpetualCache对象的数据，但是该对象可以继续使用。
##### 失效情况
* sqlSession不同
* sqlSession相同，查询条件不同
* sqlSession相同，两次查询期间，执行更新操作
* sqlSession相同，手动清空了缓存
* 执行commit rollback方法

#### 二级缓存
Mybatis默认对二级缓存是关闭的，一级缓存默认开启，如果需要开启只需在mapper上加入配置就好了。Executor是执行查询的最终接口，它有两个实现类一个是BaseExecutor另外一个是CachingExecutor。CachingExecutor(二级缓存查询)，一级缓存因为只能在同一个SqlSession中共享，所以会存在一个问题，在分布式或者多线程的环境下，不同会话之间对于相同的数据可能会产生不同的结果，因为跨会话修改了数据是不能互相感知的，所以就有可能存在脏数据的问题，正因为一级缓存存在这种不足，需要一种作用域更大的缓存，这就是二级缓存。 
一个事务方法运行时，数据查询出来，缓存在一级缓存了，但是没有到二级缓存，当事务提交后(sqlSession.commit())，数据才放到二级缓存。查询的顺序是，先查二级缓存再查一级缓存然后才去数据库查询。

一级缓存作用域是SqlSession级别，所以它存储的SqlSession中的BaseExecutor之中，但是二级缓存目的要实现作用范围更广，所以要实现跨会话共享，MyBatis二级缓存的作用域是namespace，专门用了一个装饰器来维护，这就是：CachingExecutor。 

```
    1、mybatis-config中有一个全局配置属性，这个不配置也行，因为默认就是true。
      <setting name="cacheEnabled" value="true"/>
      想详细了解mybatis-config的可以点击这里。
      2、在Mapper映射文件内需要配置缓存标签：
      <cache/>
      或
      <cache-ref namespace="com.lonelyWolf.mybatis.mapper.UserAddressMapper"/>
      想详细了解Mapper映射的所有标签属性配置可以点击这里。
      3、在select查询语句标签上配置useCache属性，如下：
      <select id="selectUserAndJob" resultMap="JobResultMap2" useCache="true">
          select * from lw_user
      </select>
      以上配置第1点是默认开启的，也就是说我们只要配置第2点就可以打开二级缓存了，
      而第3点是当我们需要针对某一条语句来配置二级缓存时候则可以使用。
      1、需要commit事务之后才会生效
      2、如果使用的是默认缓存，那么结果集对象需要实现序列化接口(Serializable)
      如果不实现序列化接口则会报如下错误
```

##### 二级缓存工作机制
1）一个会话，查询一条数据，该数据会放在当前会话的一级缓存中。
2）如果当前会话关闭，对应的一级缓存会被保存到二级缓存中，新的会话查询信息，就可以参照二级缓存。
3）不同namespace查询出的数据会放在自己对应的缓存中。
注意：查出的数据都会默认放在一级缓存中，只有会话提交或关闭后，一级缓存的数据才会被转移到二级缓存中。

* 需要注意的是在事务提交之前，并不会真正存储到二级缓存，而是先存储到一个临时属性，
* 等事务提交之后才会真正存储到二级缓存。（？）
Mybatis缓存包装汇总：
PerpetualCache	缓存默认实现类	-	基本功能，默认携带
LruCache	LRU淘汰策略缓存（默认淘汰策略）	当缓存达到上限，删除最近最少使用缓存	eviction=“LRU”
FifoCache	FIFO淘汰策略缓存	当缓存达到上限，删除最先入队的缓存	eviction=“FIFO”
SoftCache	JVM软引用淘汰策略缓存	基于JVM的SoftReference对象	eviction=“SOFT”
WeakCache	JVM弱引用淘汰策略缓存	基于JVM的WeakReference对象	eviction=“WEAK”
LoggingCache	带日志功能缓存	输出缓存相关日志信息	基本功能，默认包装
SynchronizedCache	同步缓存	基于synchronized关键字实现，用来解决并发问题	基本功能，默认包装
BlockingCache	阻塞缓存	get/put操作时会加锁，防止并发，基于Java重入锁实现	blocking=true
SerializedCache	支持序列化的缓存	通过序列化和反序列化来存储和读取缓存	readOnly=false(默认)
ScheduledCache	定时调度缓存	操作缓存时如果缓存已经达到了设置的最长缓存时间时会移除缓存	flushInterval属性不为空
TransactionalCache	事务缓存	在TransactionalCacheManager中用于维护缓存map的value值

###### 缓存失效
所有的update操作(insert,delete,uptede)都会触发缓存的刷新，从而导致二级缓存失效，所以二级缓存适合在读多写少的场景中开启。

二级缓存针对的是同一个namespace，所以建议是在单表操作的Mapper中使用，或者是在相关表的Mapper文件中共享同一个缓存。 


#### 自定义缓存
一级缓存可能存在脏读情况，那么二级缓存是否也可能存在呢？是的，默认的二级缓存也是存储在本地缓存，对于微服务下是可能出现脏读的情况的，这时可能会需要自定义缓存，比如利用redis来存储缓存，而不是存储在本地内存当中。

```
  MyBatis官方也提供了第三方缓存的支持引入pom文件：
      <dependency>
          <groupId>org.mybatis.caches</groupId>
          <artifactId>mybatis-redis</artifactId>
          <version>1.0.0-beta2</version>
      </dependency>
      然后缓存配置如下：
      <cache type="org.mybatis.caches.redis.RedisCache"></cache>
      然后在默认的resource路径下新建一个redis.properties文件：
      host=localhost
      port=6379
```