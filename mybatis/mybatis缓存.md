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
注意：Mybatis二级缓存对于访问多的查询请求且用户对查询结果实时性要求不高，此时可采用Mybatis二级缓存技术降低数据库访问量，提高访问速度。 如果需要多表,以及数据多变的缓存建议使用redis，多表操作一定一定不能使用二级缓存，这样会导致数据不一致的问题