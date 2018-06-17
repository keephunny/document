## 根节点<configuration>
属性：  
* debug：默认false，设置ture时，会打印出logback内部日志信息，实时查看logback运行状态。
* scan：配置文件如果发生了改变，将会重新加载，默认值true。
* scanPeriod：检测配置文件是否有修改的时间时隔。默认单位为毫秒，当scan为true时，默认间隔为1min。  
    <configuration debug="true" scan="true" scanPeriod="2">
        <!--TODO : 子节点信息-->
    </configuration>

### <appender>日志写组件
appender两个重要属性，name指定appender的名称，class指定appender的全限定名class。
#### ch.qos.logback.core.ConsoleAppender
* <encoder>：对日志进行格式化
* <target>：字符串System.out或System.err  
    <appender name ="console_out" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%date [%thread] %-5level %logger - %message%newline</pattern>
        </encoder>
    </appender>
 
#### ch.qos.logback.core.FileAppender
将日志文件输出到文件  
* <file>：被写入的文件名，可以是相对目录，也可以是绝对目录。
* <append>:如果是true，日志追加到文件，false则清空文件。
* <encoder>：对日志进行格式化。
* <prodent>：如果是true日志会被安全的写入文件，即使其它FileAppender也会向此文件写入操作，默认false。  
    <appender name="file_out" class="ch.qos.logback.core.FileAppender">
        <file>logs/debug.log</file>
        <encoder>
            <pattern>%date [%thread] %-5level %logger - %message%newline</pattern>
        </encoder>
    </appender>
#### ch.qos.logback.core.RollingFileAppender
按规则生成日志文件  
* <file>：被写入的文件名，可以是相对目录，也可以是绝对目录。
* <append>:如果是true，日志追加到文件，false则清空文件。
* <encoder>：对日志进行格式化。
    * <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
    * <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
    * 负责两件事，一是把日志信息转换成字节数组，二是把字节数组写入到输出流。
    * 目前PatternLayoutEncoder 是唯一有用的且默认的encoder ，有一个<pattern>节点，用来设置日志的输入格式。使用“%”加“转换符”方式，如果要输出“%”，则必须用“\”对“\%”进行转义。


* <rollingPolicy>：当发生滚动时，确定文件移动和重命名。
    * TimeBaseRollingPolicy：最常用的滚动策略，根据时间来制定滚动策略。
    * SizeBasedTriggeringPolicy：查看当前活动文件的大小，如果超过指定大小会告知RollingFileAppender，触发当前活动滚动。
        * <maxFileSize>：活动文件的大小，默认10MB
        * <prudent> 当为true时不支持FixedWindowRollingPolicy，支持TimeBasedRollingPolicy，但是有两个限制，1不支持也不允许文件压缩，2不能设置file属性，必须为空。

    ```
    <!-- 设置滚动策略 -->
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <!--设置日志命名模式-->
        <fileNamePattern>error%d{yyyy-MM-dd}.log</fileNamePattern>
        <!--最多保留30天log-->
        <maxHistory>30</maxHistory>
    </rollingPolicy>
    <!-- 超过150MB时，触发滚动策略 -->
    <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
        <maxFileSize>50MB</maxFileSize>
    </triggeringPolicy>

#### 其它appender
SocketAppender、SMTPAppender、DBAppender、SyslogAppender、SiftingAppender


### <logger>    
    <!-- 指定在logback.olf.log包中的log -->
    <logger name="logback.olf.log" level="info">
        <appender-ref ref = "console_out"/>
        <appender-ref ref = "infoAppender"/>
    </logger>
logger用来设置一个包或者具体某个类的日志打印级别。  
* name：用来指定受此loger约束的某一个包或者具体的某一个类
* level：用来设置打印级别，不区分大小写。
* addtivity ： 是否向上级loger传递打印信息，默认为true。
* <appender-ref>元素，表示这个appender将会添加到loger

### 常用logger
    <!-- show parameters for hibernate sql 专为 Hibernate 定制 -->
    <logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE" />
    <logger name="org.hibernate.type.descriptor.sql.BasicExtractor" level="DEBUG" />
    <logger name="org.hibernate.SQL" level="DEBUG" />
    <logger name="org.hibernate.engine.QueryParameters" level="DEBUG" />
    <logger name="org.hibernate.engine.query.HQLQueryPlan" level="DEBUG" />

    <!--myibatis log configure-->
    <logger name="com.apache.ibatis" level="TRACE"/>
    <logger name="java.sql.Connection" level="DEBUG"/>
    <logger name="java.sql.Statement" level="DEBUG"/>
    <logger name="java.sql.PreparedStatement" level="DEBUG"/>


### <root>
    <root level="ALL">
        <appender-ref ref="infoAppender"/>
        <appender-ref ref="debugAppender"/>
        <appender-ref ref="errorAppender"/>
    </root>
元素配置根 logger。该元素有一个 level 属性。没有其它属性。Level 属性的值不区分大小写。  TRACE、DEBUG、INFO、 WARN、ERROR、ALL 和 OFF

## <filter> 过滤节点
Logback 的过滤器基于三值逻辑（ternary logic），允许把它们组装或成链，从而组成任 意的复合过滤策略。这里的所谓三值逻辑是说，过滤器的返回值只能是 ACCEPT、DENY 和 NEUTRAL 的其中一个。
* FilterReply.DENY, 直接退出，不执行后续流程。
* FilterReply.NEUTRA，继续向下执行。
* FilterReply.ACCEPT，不进行步骤二,即类型输出类型检查。

### 级别过滤器（LevelFilter）  
过滤器会根据 onMatch 和 onMismatch 属性接受或拒绝事件     

    <filter class="ch.qos.logback.classic.filter.LevelFilter">
        <!-- 过滤掉非INFO级别 -->
        <level>INFO</level>
        <onMatch>ACCEPT</onMatch>
        <onMismatch>DENY</onMismatch>
    </filter>

### 临界值过滤器（ThresholdFilter）
ThresholdFilter 过滤掉低于指定临界值的事件 . 当记录的级别等于或高于临界值时 , ThresholdFilter 的decide()方法会返回NEUTRAL ; 当记录级别低于临界值时 , 事件会被拒绝 下面是个配置文件例子   

    <filter class="ch.qos.logback.classic.filter.ThresholdFilter">  
        <!-- 过滤掉TRACE和DEBUG级别的日志 -->
        <level>INFO</level> 
    </filter>
### 求值过滤器（EvaluatorFilter）    
    <filter class="ch.qos.logback.classic.filter.EvaluatorFilter">  
        <evaluator>
        <!--过滤掉所有日志中不包含hello字符的日志-->
            <expression>
                message.contains("hello")
            </expression>
            <onMatch>NEUTRAL</onMatch>
            <onMismatch>DENY</onMismatch>
        </evaluator>
    </filter>
### 匹配器（Matchers）
尽管能通过调用 String 类的 matches()方法进行模式匹配，但这会导致每次调用过滤器 时都会创建一个全新的 Pattern 对象。为消除这种开销，你可以预先定义一个或多个 Matcher 对象。一旦定义 matcher 后，就可以在求值表达式里重复引用它。    

    <filter class="ch.qos.logback.classic.filter.EvaluatorFilter">  
        <evaluator> 
            <matcher>
                <Name>odd</Name>
                <!-- 过滤掉序号为奇数的语句-->
                <regex>statement [13579]</regex>
            </matcher>
            <expression>odd.matches(formattedMessage)</expression>
            <onMatch>NEUTRAL</onMatch>
            <onMismatch>DENY</onMismatch>
        </evaluator>
    </filter>

### 绑定变量值

    <springProperty scope="context" name="springAppName" source="spring.application.name"/>
    <springProperty scope="context" name="LOG_PATH" source="logback.Path"/>
    <springProperty scope="context" name="LOG_MAX_HISTORY" source="logback.maxHistory"/>

    <!-- Appender to log to file -->​
    <appender name="flatfile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_PATH:-build}/${springAppName}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_PATH:-build}/${springAppName}.%d{yyyy-MM-dd}.gz</fileNamePattern>
            <maxHistory>${LOG_MAX_HISTORY}</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>