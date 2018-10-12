## 什么是mysql binlog
mysql的binlog日志作用是用来记录mysql内部增删改查等对数据库更新内容的记录，对数据库的查询select和show等操作不会被binlog记录，主要用于数据库的主从复制以及增量恢复。

### binlog日志文件
binlog日志文件包括两类文件
* 二进制索引文件(.index)用于记录所有的二进制文件
* 二进制文件(.0000*)记录数据库所有DDL和DML语句事件
进入mysql目录查看日志文件
    cd /var/lib/mysql
    ll
![图片](./resources/20180801165417.png)    
## 如何开启binlog
编辑my.cnf配置文件
    #mysql-bin 是日志的基本名或前缀名
    log-bin=mysql-bin   
    # 重启mysqld服务使配置生效
    services mysqld restart
    # 查看是否生效
    show variables like 'log_%'; 

## binlog常用命令

    #查看所有binlog日志列表
    mysql> show master logs;

    #查看master状态，即最后(最新)一个binlog日志的编号名称，及其最后一个操作事件pos结束点(Position)值
    mysql> show master status;

    #刷新log日志，自此刻开始产生一个新编号的binlog日志文件
    #每当mysqld服务重启时，会自动执行此命令，刷新binlog日志；在mysqldump备份数据时加 -F 选项也会刷新binlog日志；
    mysql> flush logs;

    #重置(清空)所有binlog日志
    mysql> reset master;

## binlog参数配置
Mysql BInlog日志格式可以通过mysql的my.cnf文件的属性binlog_format指定。如以下：

    binlog_format = MIXED //binlog日志格式
    log_bin =目录/mysql-bin.log //binlog日志名
    expire_logs_days = 7 //binlog过期清理时间
    max_binlog_size 100m //binlog每个日志文件大小



## binlog工作模式
查看与设置工作模式

### statement模式(默认)
* 优点：不需要记录每一行的变化，减少了binlog日志量，节约IO，提高性能。
* 缺点：由于记录的只是执行语句，为了这些语句能在salve上正确执行，因此还必须记录每条语句在执行的时候一些相关信息，以保证所有语句在slave得到和master端执行结果相同。另外mysql的复制，像一些特定函数功能，slave与master上要保持一致会有很多相关问题，如sleep()、last_insert_id()等。

### row模式
* 优点：日志中会记录每条记录被修改的情况，binlog上可以不记录执行sql语句的上下文相关信息，仅需要记录哪一条记录被修改了。所以row模式的日志内容会非常清楚的记录下每条数据修改的细节，而且不会出现某些特定情况下的存储过程，如funtion、trigger的调用或触发无法被正确复制的问题。    
* 缺点：所有的执行语句记录到日志的时候，都将以每行记录的修改记录下来，这样可能会产生大量的日志内容，比如一条update语句影响多条记录，则binlog中每条修改都会记录，特别是当执行alter table之类的语句时候，由于表结构修改，每条记录都会发生改变，那么该表的每一条记录都会记录到日志中。

### mixed模式
混合模式结合了row和statement的优点，一般的语句修改使用statement格式保存binlog，如一些函数statement无法完成主从复制操作，则采用row格式保存binlog，mysql会根据每一条具体sql语句来区分记录的日志形式。也就是在statement和row之间选一种。

