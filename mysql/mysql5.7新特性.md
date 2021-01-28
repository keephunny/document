MySQL 5.7的新特性

### 安全性
安全性是数据库永恒的话题，在MySQL 5.7中，有不少安全性相关的改进。
* MySQL数据库初始化完成以后，会产生一个 root@localhost 用户，从MySQL 5.7开始，root用户的密码不再是空，而是随机产生一个密码，这也导致了用户安装5.7时发现的与5.6版本比较大的一个不同点
* MySQL官方已经删除了test数据库，默认安装完后是没有test数据库的，就算用户创建了test库，也可以对test库进行权限控制了
* MySQL 5.7版本提供了更为简单SSL安全访问配置，并且默认连接就采用SSL的加密方式
* 可以为用户设置密码过期策略，一定时间以后，强制用户修改密码
   ALTER USER 'jeffrey'@'localhost' PASSWORD EXPIRE INTERVAL 90 DAY; 
* 可以”锁”住用户，用以暂时禁用某个用户
   ALTER USER  'jeffrey'@'localhost' ACCOUNT LOCK;
   ALTER USER l 'jeffrey'@'localhost'  ACCOUNT UNLOCK; 



### 灵活性
#### JSON
随着非结构化数据存储需求的持续增长，各种非结构化数据存储的数据库应运而生。MySQL数据库从5.7.8版本开始，也提供了对JSON的支持。MySQL对支持JSON的做法是，在server层提供了一堆便于操作JSON的函数，至于存储，就是简单地将JSON编码成BLOB，然后交由存储引擎层进行处理，也就是说，MySQL 5.7的JSON支持与存储引擎没有关系，MyISAM 存储引擎也支持JSON 格式。

####  generate column
generated column是MySQL 5.7引入的新特性，所谓generated column，就是数据库中这一列由其他列计算而得。
 例如，知道直角三角形的两条直角边，要求直角三角形的面积。很明显，面积可以通过两条直角边计算而得，那么，这时候就可以在数据库中只存放直角边，面积使用generated column

### 易用性 

易用性是数据库永恒的话题，MySQL也在持续不断地提高数据库的易用性。在MySQL 5.7中，有很多易用性方面的改进，小到一个客户端快捷键 ctrl+c 的使用，大到专门提供一个系统库(sys)来帮助DBA和开发人员使用数据库。这一节将重点介绍MySQL 5.7引入的sys库。
 •在linux下，我们经常使用 ctrl+c 来终止一个命令的运行，在MySQL 5.7 之前，如果用户输入了错误的SQL语句，按下 ctrl+c ，虽然能够”结束”SQL语句的运行，但是，也会退出当前会话，MySQL 5.7对这一违反直觉的地方进行了改进，不再退出会话。
•MySQL 5.7可以explain一个正在运行的SQL，这对于DBA分析运行时间较长的语句将会非常有用
•在MySQL 5.7中，performance_schema提供了更多监控信息，包括内存使用，MDL锁，存储过程等

sys schema
sys schema是MySQL 5.7.7中引入的一个系统库，包含了一系列视图、函数和存储过程， 该项目专注于MySQL的易用性。例如，我们可以通过sys schema快速的知道，哪些语句使用了临时表，哪个用户请求了最多的io，哪个线程占用了最多的内存，哪些索引是无用索引等
sys schema中包含了大量的视图，


 •如何查看数据库中的冗余索引select * from sys.schema_redundant_indexes;
 •如何获取未使用的索引select * from schema_unused_indexes;
 •如何查看使用全表扫描的SQL语句select * from statements_with_full_table_scans 



### 可用性

MySQL 5.7在可用性方面的改进也带给人不少惊喜。这里介绍特别有用的几项改进，包括：
 •在线设置 复制的过滤规则 不再需要重启MySQL，只需要停止SQL thread，修改完成以后，启动SQL thread
 •在线修改buffer pool的大小
 MySQL 5.7为了支持online buffer pool resize，引入chunk的概念，每个chunk默认是128M，当我们在线修改buffer pool的时候，以chunk为单位进行增长或收缩。这个参数的引入，对innodb_buffer_pool_size的配置有了一定的影响。innodb要求buffer pool size是innodb_buffer_pool_chunk_size* innodb_buffer_pool_instances的倍数，如果不是，将会适当调大innodb_buffer_pool_size，以满足要求，因此，可能会出现buffer pool的实际分配比配置文件中指定的size要大的情况
 •Online DDL MySQL 5.7支持重命名索引和修改varchar的大小，这两项操作在之前的版本中，都需要重建索引或表
   ALTER TABLE t1 ALGORITHM=INPLACE, CHANGE COLUMN c1 c1 VARCHAR(255);
 •在线开启GTID ，在之前的版本中，由于不支持在线开启GTID，用户如果希望将低版本的数据库升级到支持GTID的数据库版本，需要先关闭数据库，再以GTID模式启动，所以导致升级起来特别麻烦。MySQL 5.7以后，这个问题不复存在


### 性能
性能一直都是用户最关心的问题，在MySQL每次新版本中，都会有不少性能提升。在MySQL 5.7中，性能相关的改进非常多，这里仅介绍部分改进，包括临时表相关的性能改进、只读事务的性能优化、连接建立速度的优化和复制性能的改进。

#### 临时表的性能改进
MySQL 5.7 为了提高临时表相关的性能，对临时表相关的部分进行了大幅修改，包括引入新的临时表空间；对于临时表的DDL，不持久化相关表定义；对于临时表的DML，不写redo，关闭change buffer等。所有临时表的改动，都基于以下两个事实 ：
1. 临时表只在当前会话中可见
2. 临时表的生命周期是当前连接（MySQL宕机或重启，则当前连接结束） 也就是说，对于临时表的操作，不需要其他数据一样严格地进行一致性保证。通过不持久化元信息，避免写redo等方式，减少临时表操作的IO，以提高临时表操作的性能。

#### 只读事务性能改进
众所周知，在传统的OLTP应用中，读操作远多于写操作，并且，读操作不会对数据库进行修改，如果是非锁定读，读操作也不需要进行加锁。因此，对只读事务进行优化，是一个不错的选择。
在MySQL 5.6中，已经对只读事务进行了许多优化。例如，将MySQL内部实现中的事务链表分为只读事务链表和普通事务链表，这样在创建ReadView的时候，需要遍历事务链表长度就会小很多。
在MySQL 5.7中，首先假设一个事务是一个只读事务，只有在该事务发起了修改操作时，才会将其转换为一个普通事务。MySQL 5.7通过 避免为只读事务分配事务ID ，不为只读事务分配回滚段，减少锁竞争等多种方式，优化了只读事务的开销，提高了数据库的整体性能。

#### 加速连接处理
在MySQL 5.7之前，变量的初始化操作（THD、VIO）都是在连接接收线程里面完成的，现在将这些工作下发给工作线程，以减少连接接收线程的工作量，提高连接的处理速度。这个优化对那些频繁建立短连接的应用，将会非常有用。

#### 复制性能的改进
MySQL的复制延迟是一直被诟病的问题之一，欣喜的是，MySQL 5.7版本已经支持”真正”的并行复制功能。MySQL 5.7并行复制的思想简单易懂，简而言之，就是”一个组提交的事务都是可以并行回放的”，因为这些事务都已进入到事务的prepare阶段，则说明事务之间没有任何冲突（否则就不可能提交）。MySQL 5.7以后，复制延迟问题永不存在。
 这里需要注意的是，为了兼容MySQL 5.6基于库的并行复制，5.7引入了新的变量slave-parallel-type，该变量可以配置成DATABASE（默认）或LOGICAL_CLOCK。可以看到，MySQL的默认配置是库级别的并行复制，为了充分发挥MySQL 5.7的并行复制的功能，我们需要将slave-parallel-type配置成LOGICAL_CLOCK。
在MySQL5.7版本中mysql数据库下已经没有password这个字段了，password字段改成了authentication_string字段。