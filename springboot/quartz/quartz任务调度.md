https://blog.csdn.net/weixin_38192427/article/details/121111677


### 配置说明
```
# 调度器实例名称（不配置则使用默认配置:quartzScheduler）
org.quartz.scheduler.instanceName = Scheduler
# 调度器实例编号自动生成
org.quartz.scheduler.instanceId = AUTO

#持久化方式配置   =org.quartz.simpl.RAMJobStore 即存储在内存中
org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
#持久化方式配置数据驱动，MySQL数据库
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#开启分布式部署
org.quartz.jobStore.isClustered = true
#分布式节点有效性检查时间间隔，单位：毫秒
org.quartz.jobStore.clusterCheckinInterval = 10000
# quartz相关数据表前缀名（默认QRTZ_）
org.quartz.jobStore.tablePrefix = quartz_
# JobDataMaps内容是否以key-value形式存储，默认true
org.quartz.jobStore.useProperties = false

#线程池实现类(不配置则使用默认配置)
org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
#执行最大并发线程数量
org.quartz.threadPool.threadCount = 20
#线程优先级
org.quartz.threadPool.threadPriority = 5
#配置是否启动自动加载数据库内的定时任务，默认true
org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread = true
```




```
#主要分为scheduler、threadPool、jobStore、dataSource等部分


org.quartz.scheduler.instanceId=AUTO
org.quartz.scheduler.instanceName=DefaultQuartzScheduler
#如果您希望Quartz Scheduler通过RMI作为服务器导出本身，则将“rmi.export”标志设置为true
#在同一个配置文件中为'org.quartz.scheduler.rmi.export'和'org.quartz.scheduler.rmi.proxy'指定一个'true'值是没有意义的,如果你这样做'export'选项将被忽略
org.quartz.scheduler.rmi.export=false
#如果要连接（使用）远程服务的调度程序，则将“org.quartz.scheduler.rmi.proxy”标志设置为true。您还必须指定RMI注册表进程的主机和端口 - 通常是“localhost”端口1099
org.quartz.scheduler.rmi.proxy=false
org.quartz.scheduler.wrapJobExecutionInUserTransaction=false


#实例化ThreadPool时，使用的线程类为SimpleThreadPool
org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
#threadCount和threadPriority将以setter的形式注入ThreadPool实例
#并发个数  如果你只有几个工作每天触发几次 那么1个线程就可以,如果你有成千上万的工作，每分钟都有很多工作 那么久需要50-100之间.
#只有1到100之间的数字是非常实用的
org.quartz.threadPool.threadCount=5
#优先级 默认值为5
org.quartz.threadPool.threadPriority=5
#可以是“true”或“false”，默认为false
org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread=true


#在被认为“misfired”(失火)之前，调度程序将“tolerate(容忍)”一个Triggers(触发器)将其下一个启动时间通过的毫秒数。默认值（如果您在配置中未输入此属性）为60000（60秒）
org.quartz.jobStore.misfireThreshold=5000
# 默认存储在内存中,RAMJobStore快速轻便，但是当进程终止时，所有调度信息都会丢失
#org.quartz.jobStore.class=org.quartz.simpl.RAMJobStore

#持久化方式，默认存储在内存中，此处使用数据库方式
org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
#您需要为JobStore选择一个DriverDelegate才能使用。DriverDelegate负责执行特定数据库可能需要的任何JDBC工作
# StdJDBCDelegate是一个使用“vanilla”JDBC代码（和SQL语句）来执行其工作的委托,用于完全符合JDBC的驱动程序
org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#可以将“org.quartz.jobStore.useProperties”配置参数设置为“true”（默认为false），以指示JDBCJobStore将JobDataMaps中的所有值都作为字符串，
#因此可以作为名称 - 值对存储而不是在BLOB列中以其序列化形式存储更多复杂的对象。从长远来看，这是更安全的，因为您避免了将非String类序列化为BLOB的类版本问题
org.quartz.jobStore.useProperties=true
#表前缀
org.quartz.jobStore.tablePrefix=QRTZ_
#数据源别名，自定义
org.quartz.jobStore.dataSource=qzDS


#使用阿里的druid作为数据库连接池
org.quartz.dataSource.qzDS.connectionProvider.class=org.example.config.DruidPoolingconnectionProvider
org.quartz.dataSource.qzDS.URL=jdbc:mysql://127.0.0.1:3306/test_quartz?characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=UTC
org.quartz.dataSource.qzDS.user=root
org.quartz.dataSource.qzDS.password=123456
org.quartz.dataSource.qzDS.driver=com.mysql.jdbc.Driver
org.quartz.dataSource.qzDS.maxConnections=10
#设置为“true”以打开群集功能。如果您有多个Quartz实例使用同一组数据库表，则此属性必须设置为“true”，否则您将遇到破坏
#org.quartz.jobStore.isClustered=false


```


```
@Bean
public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    // 设置SCHED_NAME
    schedulerFactoryBean.setSchedulerName("instance_two");
    // 将spring管理job自定义工厂交由调度器维护
    schedulerFactoryBean.setJobFactory(jobFactory);
    // 设置覆盖已存在的任务
    schedulerFactoryBean.setOverwriteExistingJobs(true);
    // 项目启动完成后，等待2秒后开始执行调度器初始化
    schedulerFactoryBean.setStartupDelay(2);
    // 设置调度器自动运行
    schedulerFactoryBean.setAutoStartup(true);
    // 设置数据源，使用与项目统一数据源
    schedulerFactoryBean.setDataSource(dataSource);
    // 设置上下文spring bean name
    schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
    // 设置配置文件位置
    schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
    return schedulerFactoryBean;
}

```