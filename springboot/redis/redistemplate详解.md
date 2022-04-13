springboot集成redis使用starter是spring-boot-starter-data-redis



### redis数据类型

| redis     | java     | 说明 |
| --------- | -------- | ---- |
| String    | String   |      |
| Hash      | HashMap  |      |
| List      | LinkList |      |
| Set       | HashSet  |      |
| SortedSet | TreeSet  |      |







### 主要功能

#### 连接池管理

提供了一个高度封装的"RedisTemplate"类

* ValueOperations：简单的String类型K-V操作
* SetOperations：set类型数据操作
* ZSetOperations：zset类型数据操作
* HashOperations：map类型的数据操作
* ListOperations：list类型的数据操作

提供了对key的boud操作，无须显式的再次指定key，BoudKeyOperations将事务操作封装，由容器控制。

* BoundValueOperations
* BoundSetOperations
* BoudListOperations
* BoudSetOperations
* BoudHashOperations

指定数据序列化方式

* JdkSerializationRedisSerializer：POJO对象的存取场景，使用JDK本身序列化机制，将pojo类通过ObjectInputStream/ObjectOutputStream进行序列化操作，最终redis-server中将存储字节序列。是目前最常用的序列化策略。
* StringRedisSerializer：Key或者value为字符串的场景，根据指定的charset对数据的字节序列编码成string，是“new String(bytes, charset)”和“string.getBytes(charset)”的直接封装。是最轻量级和高效的策略。
* JacksonJsonRedisSerializer：jackson-json工具提供了javabean与json之间的转换能力，可以将pojo实例序列化成json格式存储在redis中，也可以将json格式的数据转换成pojo实例。
* OxmSerializer：提供了将javabean与xml之间的转换能力，