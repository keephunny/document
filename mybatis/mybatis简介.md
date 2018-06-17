mybatis是一款优秀的持久层框架，它支持定制化sql，存储过程及高级映射，mybatis避免了几科所有的jdbc代码和手动设置参数以及获取结果集，mybatis可以使用简单的xml或注解来配置和映射原生信息。将接口和java的POJOs(Plain Old java Object普通的java对象)映射成数据库中的记录。

## mavent依赖包发
    <dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.0</version>
    </dependency>

## 从xml中构建SqlSessionFactory
每个基于mybatis的应用都是以一个SqlSessionFactory的实例为中心的，SqlSessionFactory的实例可以通过SqlSessionFactoryBuilder获得，而SqlSessionFactoryBuilder则可以从xml配置文件或一个预先定制的Configuration的实例构建出SqlSessionFactory实例。     
从xml文件中构建SqlSession的实例非常简单    
    String resource="mybatis-config.xml";
    InputStream inputStream=Resource.getResourcesAsStream(resource);
    SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
    //xml配置文件
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
    <environments default="development">
        <environment id="development">
        <transactionManager type="JDBC"/>
        <dataSource type="POOLED">
            <property name="driver" value="${driver}"/>
            <property name="url" value="${url}"/>
            <property name="username" value="${username}"/>
            <property name="password" value="${password}"/>
        </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
    </configuration>

## 从代码中直接构建SqlSessionFactory
    DataSource dataSource=BlogDataSourceFactory.getBlogDataSource();
    TransactionFactory transactionFactory=new JdbcTransactionFactory();
    Environment enviroment=new Environment("development",transactionFactory,dataSource);
    Configuration configuration=new Configuration(environment);
    configuration.addMapper(BlogMapp.class);
    SqlSessionFacotry sqlSessionFactory=new SqlSessionFactoryBuilder().build(configuration);

    SqlSession session = sqlSessionFactory.openSession();
    try {
        Blog blog = (Blog) session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);
        } finally {
        session.close();
    }

    SqlSession session = sqlSessionFactory.openSession();
    try {
    BlogMapper mapper = session.getMapper(BlogMapper.class);
    Blog blog = mapper.selectBlog(101);
    } finally {
    session.close();
    }

## 作用域和生命周期
### 对象生命周期和依赖注入框架
依赖注入框架可以创建线程安全的、基于事务的SqlSession和映射器并将它们注入到你的bean中。因此可以直接忽略它们的生命周期。
### SqlSessionFactoryBuilder
这个类可以被实化、使用和丢弃，一是创建了SqlSessionFactory就不需要它了。因此SqlSessionFactoryBuilder实例最佳作用域是方法作用域，也就局部方法变量。可以重用SqlSessionFactoryBuilder来创建多个SqlSessionFactory实例，但是最好还是不要让其一直存在以保证所有的xml解析资源开放给更重要的事情。
### SqlSessionFactory
SqlSessionFactory一旦被创建就应该在应用的运行期间一直存在。没有任何理由对它进行清除或重建，使用SqlSessionFactory的最佳实践是在应用运行期间不要重得创建多次。因此SqlSessionFactory的最佳作用域是应用作用域，有很多方法可以做到，最简单的就是使用单例或静态单例模式。
### SqlSession
每个线程都应该有它自己的SqlSession实例，SqlSession的实例不是线程安全的，因此不能被共享，所以它的最佳作用域是请求或方法作用域，绝对不能将SqlSession实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。
    SqlSession session=sqlSessionFactory.openSession();
    try{

    }finally{
        session.close();
    }
### 映射器实例(Mapper Instances)
映射器是一个创建来绑定映射语句的接口，是映射器接口的实例是从SqlSession中获得的。因此从技术层面讲，任何映射器实例的最大作用域是和请求它们的SqlSession相同的。尽管如此映射器实例最佳作用域是方法作用域，映射器实例应该在调用它的方法中被请求。用过之后即可废弃，并不需要显式地关闭映射器实现，尽管在整个请求作用域(request scope)保持映射器实例也不会有什么问题，像SqlSession一样，在这个作用域上管理太多的资源会难于控制，所以要保持简单，最好把映射器放在方法作用域(method scope)。
    SqlSession session = sqlSessionFactory.openSession();
    try {
    BlogMapper mapper = session.getMapper(BlogMapper.class);
    // do work
    } finally {
    session.close();
    }