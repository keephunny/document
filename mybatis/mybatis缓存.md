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

<mapper namespace="com.gscitylifeline.proj.tongling.web.core.dao.UserInfoMapper">

    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
</mapper>
```


# mybatis
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
mybatis.config-location=classpath:mybatis/mybatis-config.xml

spring.cache.jcache.config=classpath:ehcache.xml