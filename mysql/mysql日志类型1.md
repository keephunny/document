MySQL中的日志包括：错误日志、二进制日志、通用查询日志、慢查询日志等等。这里主要介绍下比较常用的两个功能：通用查询日志和慢查询日志。

## 错误日志
记录启动、运行或停止mysqld时出现的问题
show variables like 'log_error'; 
/var/log/mysqld.log

## 二进制日志 
记录所有更改数据的语句、还用于主从复制

MySQL的二进制日志可以说是MySQL最重要的日志了，它记录了所有的DDL和DML(除了数据查询语句)语句，以事件形式记录，还包含语句所执行的消耗的时间，MySQL的二进制日志是事务安全型的。

    一般来说开启二进制日志大概会有1%的性能损耗(参见MySQL官方中文手册 5.1.24版)。二进制有两个最重要的使用场景: 
    其一：MySQL Replication在Master端开启binlog，Mster把它的二进制日志传递给slaves来达到master-slave数据一致的目的。 
    其二：自然就是数据恢复了，通过使用mysqlbinlog工具来使恢复数据。
    
    二进制日志包括两类文件：二进制日志索引文件（文件名后缀为.index）用于记录所有的二进制文件，二进制日志文件（文件名后缀为.00000*）记录数据库所有的DDL和DML(除了数据查询语句)语句事件。 


## 通用查询日志
记录建立的客户端连接和执行的所有语句（包括错误的），默认未开启
    show variables '%general_log%';
    general_log	OFF
    general_log_file	/var/lib/mysql/57-220.log
set global general_log=ON
或者修改
vim /etc/my.cnf
global general_log=ON

## 慢查询日志
记录所有执行时间超过long_query_time秒的所有查询或者不使用索引的查询

## 中继日志
它其实跟复制相关的，与二进制日志几乎相同，只不过它不是用于记录事件的，而是作为读取数据的源并且在本地执行的，当然中继日志是在从服务器上。
确保事务的持久性。
防止在发生故障的时间点，尚有脏页未写入磁盘，在重启mysql服务的时候，根据redo log进行重做，从而达到事务的持久性这一特性。

2、内容
物理格式的日志，记录的是物理数据页面的修改的信息，其redo log是顺序写入redo log file的物理文件中去的。

3、什么时候产生
事务开始之后就产生redo log，redo log的落盘并不是随着事务的提交才写入的，而是在事务的执行过程中，便开始写入redo log文件中。

4、什么时候释放
当对应事务的脏页写入到磁盘之后，redo log的使命也就完成了，重做日志占用的空间就可以重用（被覆盖）。

## 事务日志
随机I/O转换为顺序I/O，一般在两个文件存储，一个存满了就换另外一个存。
暂存事务提交的数据，实现将随机I/O转换成顺序I/O；
事务数据提交流程：innodb_buffer-->事务日志-->数据文件；
事务日志文件组，至少应该有2个日志文件，一般保存在数据目录下，为ib_logfile0和ib_logfile1；



MySQL中有六种日志文件，分别是：重做日志（redo log）、回滚日志（undo log）、二进制日志（binlog）、错误日志（errorlog）、慢查询日志（slow query log）、一般查询日志（general log），中继日志（relay log）。

其中重做日志和回滚日志与事务操作息息相关，二进制日志也与事务操作有一定的关系，这三种日志，对理解MySQL中的事务操作有着重要的意义。



http://baijiahao.baidu.com/s?id=1605412388928730420&wfr=spider&for=pc
https://www.cnblogs.com/xiaocen/p/3709114.html