Logback是由log4j创始人设计开发的日志组件。需要与slft4结合起来使用。  
* logback-core：是基础模块。 
* logback-classic是log4j的一个改良版本，完整实现了slf4j api使你可以很方便的换成其它日志系统。
* logback-access：访问模块与serlet容器集成提供通过http来访问日志功能。
  maven依赖  
    <dependency>  
        <groupId>org.slf4j</groupId>  
        <artifactId>slf4j-api</artifactId>  
        <version>1.6.0</version>  
        <type>jar</type>  
        <scope>compile</scope>  
    </dependency>  
    <dependency>  
        <groupId>ch.qos.logback</groupId>  
        <artifactId>logback-core</artifactId>  
        <version>0.9.28</version>  
        <type>jar</type>  
    </dependency>  
    <dependency>  
        <groupId>ch.qos.logback</groupId>  
        <artifactId>logback-classic</artifactId>  
        <version>0.9.28</version>  
        <type>jar</type>  
    </dependency>  

    ## logback的优点
    1. 更快的实现，logback的内核重写了，在一些关键执行路径上性能提升明显，初始化内存加载也更小了。
    2. 非常充分的测试。
    3. logback-classic实现了slf4。
    4. 自动重新加载配置文件，logback-classic能自动重新加载配置文件，扫描过程快。
    5. Lilith是log事件的观察者，和log4j的chainsaw类似。而lilith还能处理大数量的log数据 。
    6. 在谨慎模式下，多个FileAppender实例跑在多个JVM下，能 够安全地写道同一个日志文件。RollingFileAppender会有些限制。Logback的FileAppender和它的子类包括 RollingFileAppender能够非常友好地从I/O异常中恢复。
    7. 配置文件可以判断不同的环境，可以通过<if>、<then>、<else>标签来实现。
    8. filters过滤器，可以指定条件打印日志，参考MDCFilter。
    9. SiftingAppender可以用来分割日志文件。
    10. rollingFileAppender在产生新文件的时候，可以自动压缩已经打印出来的日志文件。异步压缩。
    11. 打印堆栈树日志时，会带上包的数据。
    12. 自动去除旧的日志文件，TimeBasedRollingPolicy

## logback的配置介绍
1. Logger作为日志记录器，把它关联到应用对应的context上后，主要用于存放日志对象，也可以定义日志类型级别。
2. Appender主要用于指定日志输出的目的地，可以是控制台、文件、远程服务器、DB、JMS等。
3. Layout负责把事件转换成字符串，格式化的日志信息输出。
4. logger context，每个logger都被关联到LoggerContext、loggerContext负责制造logger，也负责以树结构排列各logger，其它所有logger也通过org.slf4j.LoggerFactory类的静态方法getLogger取得,getLogger方法以logger名称参数，同名字调用返回的logger对象永远都是同一个。
5. Logger可以被分为配为TRACE、DEBUG、INFO、WARN 和 ERROR级别。


在程序用引用Logback     

    private static final Logger logger = LoggerFactory.getLogger(XXXXX.class); 
    logger.info("info");



### 常见问题

1. logback打印日志不显示具体的信息，显示问号

<Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{68} %line - %msg%n </Pattern>

AsyncAppender默认是不携带堆栈信息的

```
<appender name="fileAsyncInfoAppender" class="ch.qos.logback.classic.AsyncAppender">
	<!-- 不丢失日志.默认的,如果队列的80%已满,则会丢弃TRACT、DEBUG、INFO级别的日志 -->
	<discardingThreshold>0</discardingThreshold>
	<!-- 更改默认的队列的深度,该值会影响性能.默认值为256 -->
	<queueSize>512</queueSize>
	<!-- 新增这行为了打印栈堆信息 -->
	<includeCallerData>true</includeCallerData>
	<appender-ref ref="orderServerAppender"/>
 </appender>

```

