

### 依赖包

```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
    </parent>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-pool2</artifactId>
        <version>2.9.0</version>
    </dependency>
```

### yml配置
```
    spring:
    redis:
        password:
        cluster:
        nodes: 10.0.0.1:6379,10.0.0.2:6379,10.0.0.3:6379
        maxRedirects: 3
        timeout: 2000
        lettuce:
            pool:
                # 连接池最大连接数（使用负值表示没有限制）
                max-active: 1000
                # 连接池最大阻塞等待时间（使用负值表示没有限制）
                max-wait: -1
                # 连接池中的最大空闲连接
                max-idle: 10
                # 连接池中的最小空闲连接
                min-idle: 5
    redis-platform:
        password: xxxxxx
        cluster:
        nodes: 20.0.0.1:6379,20.0.0.2:6379,20.0.0.3:6379
        maxRedirects: 3
        timeout: 2000
        lettuce:
            pool:
                # 连接池最大连接数（使用负值表示没有限制）
                max-active: 1000
                # 连接池最大阻塞等待时间（使用负值表示没有限制）
                max-wait: -1
                # 连接池中的最大空闲连接
                max-idle: 10
                # 连接池中的最小空闲连接
                min-idle: 5
```

### 启动
ApplicationRedis
```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class ApplicationRedis {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRedis.class, args);
    }
}

```


### redis集群多数据源配置
RedisMutilClusterConfiig
```
    import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
    import org.springframework.cache.annotation.CachingConfigurerSupport;
    import org.springframework.cache.annotation.EnableCaching;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Primary;
    import org.springframework.data.redis.connection.*;
    import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
    import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
    import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.data.redis.serializer.RedisSerializer;
    import org.springframework.data.redis.serializer.StringRedisSerializer;

    import java.time.Duration;
    import java.util.HashSet;
    import java.util.Set;

    /**
    * redis集群多数据源配置
    */

    @EnableCaching
    @Configuration
    public class RedisMutilClusterConfiig extends CachingConfigurerSupport {

        @Bean
        public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            return createRedisTemplate(redisConnectionFactory);
        }

        /**
        * 数据源2 redis template
        */
        @Bean
        public RedisTemplate redisTemplatePlatform(
                @Value("${spring.redis.timeout}") long timeout,
                @Value("${spring.redis.lettuce.pool.max-active}") int maxActive,
                @Value("${spring.redis.lettuce.pool.max-wait}") int maxWait,
                @Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle,
                @Value("${spring.redis.lettuce.pool.min-idle}") int minIdle,

                @Value("${spring.redis-platform.cluster.nodes}") String clusterNodes,
                @Value("${spring.redis-platform.password}") String password) {

            /* ========= 基本配置 ========= */
            RedisClusterConfiguration configuration = new RedisClusterConfiguration();
            String[] nodes = clusterNodes.split(",");
            Set<RedisNode> setNodes = new HashSet<RedisNode>();
            for (String node : nodes) {
                String[] ipAndPort = node.split(":");
                setNodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            }
            configuration.setClusterNodes(setNodes);
            configuration.setPassword(RedisPassword.of(password));

            /* ========= 连接池通用配置 ========= */
            GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
            genericObjectPoolConfig.setMaxTotal(maxActive);
            genericObjectPoolConfig.setMinIdle(minIdle);
            genericObjectPoolConfig.setMaxIdle(maxIdle);
            genericObjectPoolConfig.setMaxWaitMillis(maxWait);

            /* ========= lettuce pool ========= */
            LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
            builder.poolConfig(genericObjectPoolConfig);
            builder.commandTimeout(Duration.ofSeconds(timeout));
            LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
            connectionFactory.afterPropertiesSet();

            /* ========= 创建 template ========= */
            return createRedisTemplate(connectionFactory);
        }

        /**
        * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
        *
        * @param redisConnectionFactory
        * @return
        */
        public RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);

            RedisSerializer<String> stringSerializer = new StringRedisSerializer();
            redisTemplate.setValueSerializer(stringSerializer);
            redisTemplate.setKeySerializer(stringSerializer);
            redisTemplate.setHashKeySerializer(stringSerializer);
            redisTemplate.setHashValueSerializer(stringSerializer);
            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        }

    }

```


### 测试验证
```

    @Autowired
    @Qualifier("redisTemplatePlatform")
    private RedisTemplate<String, String> redisTemplatePlatform;

    @Autowired
    //@Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping(value = "/redis")
    public String redis() {
        String regKey = "xxxxxxxx";

        Set<String> values = redisTemplatePlatform.opsForZSet().reverseRange(regKey, 0, 10);
        for (String s : values) {
            System.out.println(s);
        }
        System.out.println("--------------");
        values = redisTemplate.opsForZSet().reverseRange(regKey, 0, 10);
        for (String s : values) {
            System.out.println(s);
        }


        redisTemplate.opsForValue().get(key);
        redisTemplatePlatform.opsForValue().get(key);
    }


```
