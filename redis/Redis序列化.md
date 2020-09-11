

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