#### 序列化对象

* KeySerializer：普通key,非hash
* ValueSerializer：普通value,非hash
* HashKeySerializer： hash的filed
* HashValueSerializer：hash的value



#### 序列化方式

* GenericToStringSerializer：可以将任何对象泛化为字符串并序列化
* Jackson2JsonRedisSerializer：序列化object对象为json字符串
* JacksonJsonRedisSerializer：序列化object对象为json字符串
* JdkSerializationRedisSerializer：序列化java对象
* StringRdisSerializer：简单的字符串序列化
* GenericToStringSerializer：类似StringRedisSerializer的字符串序列化
* GenericJackson2JsonRedisSerializer：类似Jackson2JsonRedisSerializer，但使用时构造函数不用特定的类参考以上序列化,自定义序列化类。

 

#### 自定义序列化

```

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;
 
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
 
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
 
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }
}

```



```
import cn.fanpz.springbootdemoredis3.util.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
 
@EnableCaching
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {
 
    @Bean(name = "redisTemplate")
    @SuppressWarnings("unchecked")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
 
        //使用fastjson序列化
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
 
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
  
    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory);
        return builder.build();
    }
 
}
```









spring-data-redis默认采用的序列化策略有两种，一种是String的序列化策略，一种是JDK的序列化策略。

redisTemplate可自定义各种key和各种value的序列化方式：
 defaultSerializer： 默认序列化策略
 key：普通key,非hash
 value：
 hashKey：hash的filed
 hashValue：hash的value

//设置序列化Key的实例化对象，普通key,非hash
redisTemplate.setKeySerializer(new StringRedisSerializer());
//设置序列化Value的实例化对象，普通value,非hash
redisTemplate.setValueSerializer(new StringRedisSerializer());


//hash的key也采用String的序列化方式
redisTemplate.setHashKeySerializer(new StringRedisSerializer());
redisTemplate.setHashValueSerializer(new StringRedisSerializer());



* GenericToStringSerializer: 可以将任何对象泛化为字符串并序列化
* Jackson2JsonRedisSerializer: 跟JacksonJsonRedisSerializer实际上是一样的
* JacksonJsonRedisSerializer: 序列化object对象为json字符串
* JdkSerializationRedisSerializer: 序列化java对象（被序列化的对象必须实现Serializable接口）,无法转义成对象。
* StringRedisSerializer: 简单的字符串序列化
* GenericToStringSerializer:类似StringRedisSerializer的字符串序列化
* GenericJackson2JsonRedisSerializer:类似Jackson2JsonRedisSerializer，但使用时构造函数不用特定的类参考以上序列化,自定义序列化类。