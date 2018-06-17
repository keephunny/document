## 属性(properties)
这些属性可以外部配置也可动态替换，既可以在典型的java属性文件中配置，亦可通过properties元素的子元素来传递   
    <properties resource="config.properties">
        <property name="username" value="user11">
        <property name="password" value="xxxxxx">
    </properties>
然后其中的属情可以在整个配置文件用来替换需要动态配置的属性
    <dataSource type="POOLED">
        <property nane="driver" value="${driver}"/>
        <property nane="username" value="${username}"/>
    </dataSource>

## 设置(settings)
这是mybatis中重要的设置，会改变运行时的行为
* cacheEnabled：全局的开启或关闭配置文件中所有映射器已经配置的任何缓存。默认值true.
* lazyLoadingEnabled：延迟加载的全局开关。默认值false。
* aggressiveLazyLoading：当开启时任何方法的调用都会加载该对象的所有属性，否则每个属情会按需加载。默认值false。 
* multipleResultSetsEnabled：是否允计单一语句返回多结果集。true
* useColumnLabel： 使用列标签代替列名，不同的驱动表现不同。true
* useGeneratedKeys：允许JDBC支持自动生成主键。false
* autoMappingBehavior：指定mybatis应如何自动映射列到字段或属性。partial
    * none：表示取消自动映射
    * partial：只会自动映射没有定义嵌套结果集映射的结果集
    * full：自动是映射任意复杂度的结果集
* autoMappingUnknownColumnBehavior：指定必现自动映射目标未生列的行为。none
    * node：不做任何处理
    * warning：输出提醒日志
    * failing：映射失败
* defaultExecutorType：配置默认的执行器。simple
    * simple：普通的执行器
    * reuse：执行器会重用预处理语句prepared statements
    * batch：执行器将重用语句并执行批量更新
* defaultStatementTimeout：设置超时间，它决定驱动等待数据库响应的稍数。
* defaultFetchSize：为驱动的结果集获取数量(fetchSize)设置一个提示值，此参数只可以在查询设置中被覆盖。
* safeRowBoundsEnabled：是否允许嵌套语句中使用分页(RowBounds)。
* safeResultHandlerEnabled： 是否允许在嵌套语句中使用分页(ResultHandler)。
* mapUnderscoreToCamelCase：是否开启自动驼峰命名规则映射，即从经典数据库列名A_COLUMN到经典java属性名aColumn的类似映射。
* localCacheScope：mybatis利用本缓存机制防止循环引用和加速重复嵌套查询。
    * session：会缓存一个会话中执行的所有查询。
    * statements：本地会话仅用在语句执行上，对相同的SqlSession的不同调用将不会共享数据。
* jdbcTypeForNull：当没有参数提供特定的jdbc类型时，为空值指定jdbc类型，某些驱动需要指定列的jdbc类型。
* lazyLoadTriggerMethods：指定哪个对象的方法触发一次延迟加载。equals,clone,hashCode,toString
* defaultScriptingLanguage: 指定动态 SQL 生成的默认语言。

* defaultEnumTypeHandler  指定 Enum 使用的默认 TypeHandler 。
* callSettersOnNulls 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这对于有 Map.keySet() 依赖或 null 值初始化的时候是有用的。注意基本类型（int、boolean等）是不能设置成 null 的。
* returnInstanceForEmptyRow 当返回行的所有列都是空时，MyBatis默认返回null。 当开启这个设置时，MyBatis会返回一个空实例。 
* logPrefix 指定 MyBatis 增加到日志名称的前缀。
* logImpl 指定 MyBatis 所用日志的具体实现，未指定时将自动查找 SLF4J | LOG4J | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING | NO_LOGGING
* proxyFactory 指定 Mybatis 创建具有延迟加载能力的对象所用到的代理工具。
* vfsImpl 指定VFS的实现
* useActualParamName 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的工程必须采用Java 8编译，并且加上-parameters选项。
* configurationFactory 指定一个提供Configuration实例的类。 这个被返回的Configuration实例用来加载被反序列化对象的懒加载属性值。 这个类必须包含一个签名方法static Configuration getConfiguration().    

    <settings>
        <setting name="cacheEnabled" value="true"/>
        <setting name="lazyLoadingEnabled" value="true"/>
        <setting name="multipleResultSetsEnabled" value="true"/>
        <setting name="useColumnLabel" value="true"/>
        <setting name="useGeneratedKeys" value="false"/>
        <setting name="autoMappingBehavior" value="PARTIAL"/>
        <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
        <setting name="defaultExecutorType" value="SIMPLE"/>
        <setting name="defaultStatementTimeout" value="25"/>
        <setting name="defaultFetchSize" value="100"/>
        <setting name="safeRowBoundsEnabled" value="false"/>
        <setting name="mapUnderscoreToCamelCase" value="false"/>
        <setting name="localCacheScope" value="SESSION"/>
        <setting name="jdbcTypeForNull" value="OTHER"/>
        <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
    </settings>

## 类型别名(typeAliases)
类型别名是为java类型设置一个短的名字，它只和xml配置有关，存在的意义仅在于减少类完全限定名的冗余。  
    <typeAliases>
        <typeAlias alias="Author" type="xxx.xxx.Author"/>
        <!--也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，-->
        <package name="xxx.xxx"/>
    </typeAliases>
每个类默认会使用Bean的首字母小写的非限定类来作为它的别名，例如 domain.blog.Author别名为author，也可通过注解指定别名。   
    @Alias("author")
    public class Author{
        
    }
这是一些为常见的 Java 类型内建的相应的类型别名。它们都是大小写不敏感的，需要注意的是由基本类型名称重复导致的特殊处理。   
    别名	映射的类型
    _byte	byte
    _long	long
    _short	short
    _int	int
    _integer	int
    _double	double
    _float	float
    _boolean	boolean
    string	String
    byte	Byte
    long	Long
    short	Short
    int	Integer
    integer	Integer
    double	Double
    float	Float
    boolean	Boolean
    date	Date
    decimal	BigDecimal
    bigdecimal	BigDecimal
    object	Object
    map	Map
    hashmap	HashMap
    list	List
    arraylist	ArrayList
    collection	Collection
    iterator	Iterator
## 类型处理(typeHandlers)
无论是mybatis在预处理语句中设置一个参数，还是从结果集中取出一个值时，都会用类型处理器将获取以合适的方式转换成java类型。   

    类型处理器	Java 类型	JDBC 类型
    BooleanTypeHandler	java.lang.Boolean, boolean	数据库兼容的 BOOLEAN
    ByteTypeHandler	java.lang.Byte, byte	数据库兼容的 NUMERIC 或 BYTE
    ShortTypeHandler	java.lang.Short, short	数据库兼容的 NUMERIC 或 SHORT INTEGER
    IntegerTypeHandler	java.lang.Integer, int	数据库兼容的 NUMERIC 或 INTEGER
    LongTypeHandler	java.lang.Long, long	数据库兼容的 NUMERIC 或 LONG INTEGER
    FloatTypeHandler	java.lang.Float, float	数据库兼容的 NUMERIC 或 FLOAT
    DoubleTypeHandler	java.lang.Double, double	数据库兼容的 NUMERIC 或 DOUBLE
    BigDecimalTypeHandler	java.math.BigDecimal	数据库兼容的 NUMERIC 或 DECIMAL
    StringTypeHandler	java.lang.String	CHAR, VARCHAR
    ClobReaderTypeHandler	java.io.Reader	-
    ClobTypeHandler	java.lang.String	CLOB, LONGVARCHAR
    NStringTypeHandler	java.lang.String	NVARCHAR, NCHAR
    NClobTypeHandler	java.lang.String	NCLOB
    BlobInputStreamTypeHandler	java.io.InputStream	-
    ByteArrayTypeHandler	byte[]	数据库兼容的字节流类型
    BlobTypeHandler	byte[]	BLOB, LONGVARBINARY
    DateTypeHandler	java.util.Date	TIMESTAMP
    DateOnlyTypeHandler	java.util.Date	DATE
    TimeOnlyTypeHandler	java.util.Date	TIME
    SqlTimestampTypeHandler	java.sql.Timestamp	TIMESTAMP
    SqlDateTypeHandler	java.sql.Date	DATE
    SqlTimeTypeHandler	java.sql.Time	TIME
    ObjectTypeHandler	Any	OTHER 或未指定类型
    EnumTypeHandler	Enumeration Type	VARCHAR-任何兼容的字符串类型，存储枚举的名称（而不是索引）
    EnumOrdinalTypeHandler	Enumeration Type	任何兼容的 NUMERIC 或 DOUBLE 类型，存储枚举的索引（而不是名称）。
    InstantTypeHandler	java.time.Instant	TIMESTAMP
    LocalDateTimeTypeHandler	java.time.LocalDateTime	TIMESTAMP
    LocalDateTypeHandler	java.time.LocalDate	DATE
    LocalTimeTypeHandler	java.time.LocalTime	TIME
    OffsetDateTimeTypeHandler	java.time.OffsetDateTime	TIMESTAMP
    OffsetTimeTypeHandler	java.time.OffsetTime	TIME
    ZonedDateTimeTypeHandler	java.time.ZonedDateTime	TIMESTAMP
    YearTypeHandler	java.time.Year	INTEGER
    MonthTypeHandler	java.time.Month	INTEGER
    YearMonthTypeHandler	java.time.YearMonth	VARCHAR or LONGVARCHAR
    JapaneseDateTypeHandler	java.time.chrono.JapaneseDate	DATE
你可以重写类型型处理器或创建你自己的类型处理不支持或非标准的类型，具体做法为实现org.apache.ibatis.type.TypeHandler接口，或继承一个很便利的类org.apache.ibatis.type.BaseTypeHandler，然后可以选择性地将它映射到一个JDBC类型。   
     
    @MappedJdbcTypes(JdbcType.VARCHAR)
    public class ExampleTypeHandler extends BaseTypeHandler<String> {
        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
            ps.setString(i, parameter);
        }
        @Override
        public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
            return rs.getString(columnName);
        }
        @Override
        public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            return rs.getString(columnIndex);
        }
        @Override
        public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            return cs.getString(columnIndex);
        }
    }

    <!-- mybatis-config.xml -->
    <typeHandlers>
    <typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
    </typeHandlers>
使用这个的类型处理器将会覆盖已经存在的处理 Java 的 String 类型属性和 VARCHAR 参数及结果的类型处理器。 要注意 MyBatis 不会窥探数据库元信息来决定使用哪种类型，所以你必须在参数和结果映射中指明那是 VARCHAR 类型的字段， 以使其能够绑定到正确的类型处理器上。 这是因为：MyBatis 直到语句被执行才清楚数据类型。
### 处理枚举类型
EnumTypeHandler和EnumOrdinalTypeHandler都是泛型类型处理器(generic TypeHandlers).  
比如说我们想存储取近似值时用到的舍入模式。默认情况下，MyBatis 会利用 EnumTypeHandler 来把 Enum 值转换成对应的名字.在配置文件中把 EnumOrdinalTypeHandler 加到 typeHandlers 中即可， 这样每个 RoundingMode 将通过他们的序数值来映射成对应的整形。   
    <!-- mybatis-config.xml -->
    <typeHandlers>
    <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
    </typeHandlers>
自动映射器（auto-mapper）会自动地选用 EnumOrdinalTypeHandler 来处理， 所以如果我们想用普通的 EnumTypeHandler，就必须要显式地为那些 SQL 语句设置要使用的类型处理器。
## 对象工厂(objectFactory)
Mybatis每次创建结果对象的新实例时，它都会使用一个对象工厂(objectFactory)实例来完成。默认的对象工厂需要做的仅公是实例化目标类，要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。如果想覆盖对象工厂的默认行为，则可以通过创建自已的对象工厂来实现。    
    // ExampleObjectFactory.java
    public class ExampleObjectFactory extends DefaultObjectFactory {
        public Object create(Class type) {
            return super.create(type);
        }
        public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
            return super.create(type, constructorArgTypes, constructorArgs);
        }
        public void setProperties(Properties properties) {
            super.setProperties(properties);
        }
        public <T> boolean isCollection(Class<T> type) {
            return Collection.class.isAssignableFrom(type);
        }
    }

    <!-- mybatis-config.xml -->
    <objectFactory type="org.mybatis.example.ExampleObjectFactory">
    <property name="someProperty" value="100"/>
    </objectFactory>
## 插件(plugins)
mybatis允许你在已映射语句执行过程中的某一点进行拦截调用，默认情况下，mybatis允许使用插件来拦截方法调用包括：   
* Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
* ParameterHandler (getParameterObject, setParameters)
* ResultSetHandler (handleResultSets, handleOutputParameters)
* StatementHandler (prepare, parameterize, batch, update, query)
通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。    
    ```    
    // ExamplePlugin.java
    @Intercepts({@Signature(
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
        public class ExamplePlugin implements Interceptor {
        public Object intercept(Invocation invocation) throws Throwable {
            return invocation.proceed();
        }
        public Object plugin(Object target) {
            return Plugin.wrap(target, this);
        }
        public void setProperties(Properties properties) {
        }
    }
    <!-- mybatis-config.xml -->
    <plugins>
        <plugin interceptor="org.mybatis.example.ExamplePlugin">
            <property name="someProperty" value="100"/>
        </plugin>
    </plugins>
    ```
上面的插件将会拦截在 Executor 实例中所有的 “update” 方法调用， 这里的 Executor 是负责执行低层映射语句的内部对象。
## 环境配置(environments)
mybatis可以配置成适应多种环境，这种机制将有助于将sql映射应用于多种数据库之中。但是如果你想连接两个数据库，就需要创建两个SqlSessionFactory实例，每个数据库对应一个。     
    //为了指定创建哪种环境，只要将它作为可选的参数传递给 SqlSessionFactoryBuilder 即可。可以接受环境配置的两个方法签名是：
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, properties);
    //环境元素定义了如何配置环境
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
            <property name="..." value="..."/>
            </transactionManager>
            <dataSource type="POOLED">
            <property name="driver" value="${driver}"/>
            <property name="url" value="${url}"/>
            <property name="username" value="${username}"/>
            <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
* 默认的环境 ID（比如:default="development"）。
* 每个 environment 元素定义的环境 ID（比如:id="development"）。
* 事务管理器的配置（比如:type="JDBC"）。
* 数据源的配置（比如:type="POOLED"）。
### 事务管理器（transactionManager）
mybatis有两种类型的事务管理器       
* JDBC：直接使用JDBC的提交和回滚设置，它依赖于从数据源得到的连接来管理事务作用域。
* MANAGED：从不提交或回滚一个连接，而是让容器来管理事务的的整个生命周期，默认情况下会关闭连接。因此需要将 closeConnection 属性设置为 false 来阻止它默认的关闭行为。例如:

    <transactionManager type="MANAGED">
        <property name="closeConnection" value="false"/>
    </transactionManager>
如果你正在使用 Spring + MyBatis，则没有必要配置事务管理器， 因为 Spring 模块会使用自带的管理器来覆盖前面的配置。
### 数据源（dataSource）
dataSource元素使用标准的JDBC数据源接口来配置JDBC连接对象的资源。
### UNPOOLED
这个数据源的实现只是每次被请求时打开和关闭的连接。  
* driver – 这是 JDBC 驱动的 Java 类的完全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
* url – 这是数据库的 JDBC URL 地址。
* username – 登录数据库的用户名。
* password – 登录数据库的密码。
* defaultTransactionIsolationLevel – 默认的连接事务隔离级别。
* driver.encoding=UTF8
### POOLED
这种数据源的实现利用“池”的概念将JDBC连接对象组织起来。避免了创建新的连接实例时所必需的初始化和认证时间。        
* poolMaximumActiveConnections – 在任意时间可以存在的活动（也就是正在使用）连接数量，默认值：10
* poolMaximumIdleConnections – 任意时间可能存在的空闲连接数。
* poolMaximumCheckoutTime – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
* poolTimeToWait – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直安静的失败），默认值：20000 毫秒（即 20 秒）。
* poolMaximumLocalBadConnectionTolerance – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程. 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过
*      poolMaximumIdleConnections 与 poolMaximumLocalBadConnectionTolerance 之和。 默认值：3 (新增于 3.4.5)
* poolPingQuery – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动失败时带有一个恰当的错误消息。
* poolPingEnabled – 是否启用侦测查询。若开启，需要设置 poolPingQuery 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
* poolPingConnectionsNotUsedFor – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。
### JNDI
这个数据源的实现是为了能在EJB或应用服务器容器中使用，容器可以集中或在外部配置数据源。然后放置一个JNDI上下文的引用。

## 数据库厂商标识(databaseProvider)
mybatis可以根据不同的数据库厂商执行不同的语句，基于映射语句中databaseId属性，mybatis会加载不带databaseId属性和带有匹配当前数据库databaseId属性的所有语句。      

    <databaseIdProvider type="DB_VENDOR">
    <property name="SQL Server" value="sqlserver"/>
    <property name="DB2" value="db2"/>        
    <property name="Oracle" value="oracle" />
    </databaseIdProvider>

## 映射器(mappers)
    <!-- 使用相对于类路径的资源引用 -->
    <mappers>
    <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
    <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
    <mapper resource="org/mybatis/builder/PostMapper.xml"/>
    </mappers>
    <!-- 使用完全限定资源定位符（URL） -->
    <mappers>
    <mapper url="file:///var/mappers/AuthorMapper.xml"/>
    <mapper url="file:///var/mappers/BlogMapper.xml"/>
    <mapper url="file:///var/mappers/PostMapper.xml"/>
    </mappers>
    <!-- 使用映射器接口实现类的完全限定类名 -->
    <mappers>
    <mapper class="org.mybatis.builder.AuthorMapper"/>
    <mapper class="org.mybatis.builder.BlogMapper"/>
    <mapper class="org.mybatis.builder.PostMapper"/>
    </mappers>
    <!-- 将包内的映射器接口实现全部注册为映射器 -->
    <mappers>
    <package name="org.mybatis.builder"/>
    </mappers>