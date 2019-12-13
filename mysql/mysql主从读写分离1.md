
## 同步类型
mysql中binlog_format模式与配置详解
1. STATEMENT模式（SBR）
每一条会修改数据的sql语句会记录到binlog中。优点是并不需要记录每一条sql语句和每一行的数据变化，减少了binlog日志量，节约IO，提高性能。缺点是在某些情况下会导致master-slave中的数据不一致(如sleep()函数， last_insert_id()，以及user-defined functions(udf)等会出现问题)

2. ROW模式（RBR）
不记录每条sql语句的上下文信息，仅需记录哪条数据被修改了，修改成什么样了。而且不会出现某些特定情况下的存储过程、或function、或trigger的调用和触发无法被正确复制的问题。缺点是会产生大量的日志，尤其是alter table的时候会让日志暴涨。

3. MIXED模式（MBR）
以上两种模式的混合使用，一般的复制使用STATEMENT模式保存binlog，对于STATEMENT模式无法复制的操作使用ROW模式保存binlog，MySQL会根据执行的SQL语句选择日志保存方式。


## 配置主从同步
数据库地址：
　　192.168.40.100（主）
　　192.168.40.101（从）
### 主库配置
1. 配置
vim /etc/my.cnf
```
    mysqld]
    #开启二进制日志
    log-bin=mysql-bin
    #标识唯一id
    server_id=1
    #不同步的数据库，可设置多个
    binlog-ignore-db=information_schema
    binlog-ignore-db=cluster
    binlog-ignore-db=mysql
    #指定需要同步的数据库（和slave是相互匹配的），可以设置多个
    binlog-do-db=test
    #设置存储模式不设置默认
    binlog_format=MIXED

    #日志清理时间
    expire_logs_days=90
    #日志大小
    max_binlog_size=200m
    #缓存大小
    binlog_cache_size=4m
    #最大缓存大小
    max_binlog_cache_size=521m
```
2. 创建同步用户
赋予从库权限帐号，允许用户在主库上读取日志，赋予192.168.1.2也就是Slave机器有File权限，只赋予Slave机器有File权限还不行，还要给它REPLICATION SLAVE的权限才可以。
    mysql>GRANT replication slave ON *.* TO 'repl'@'192.168.40.%' IDENTIFIED BY '123456';　　#修改用户权限
    mysql>select host ，user ，password from mysql.user;　
    mysql>FLUSH PRIVILEGES; 　　
3. 重启
    service mysqld restart ; 
    show master status;  

### 从库配置
1. 配置
vim /etc/my.cnf
    log-bin=mysql-bin
    server-id=2
    binlog-ignore-db=information_schema
    binlog-ignore-db=cluster
    binlog-ignore-db=mysql#与主库配置一直
    replicate-do-db=test
    replicate-ignore-db=mysql
    #从库做为其他从库的主库时 log-slave-updates参数是必须要添加的，因为从库要作为其他从库的主库
    log-slave-updates
    slave-skip-errors=all
    slave-net-timeout=60
2. 设置连接master

    #关闭Slave
    mysql> stop slave;  #设置连接主库信息
    mysql> change master to master_host='192.168.40.100',
            master_user='repl',
            master_password='123456',
            master_log_file='mysql-bin.000001', 
            master_log_pos=4;

    #开启Slave
    mysql> start slave;
3. 重启
    service mysql restart
    show slave status \G;
    
    #显示yes为成功
        Slave_IO_Running: Yes
    #显示yes为成功，如果为no，一般为没有启动master
        Slave_SQL_Running: Yes　　

    mysql>stop slave;　　//停止
    mysql>reset slave;　　//清空
    mysql>start slave;　　//开启
    reset slave;

配置结束，测试主从test库能否同步。


## 其它常用方法
1. 查看日志
    #查看binlog全部文件
    mysql>show binary logs;
    
    #查看binlog是否开启NO为开启
    mysql> show variables like 'log_bin%';
    
    #详细信息
    mysql>  show variables like 'binlog%';
    
    #查看binlog日志
    mysql> show binlog events in'mysql-bin.000019';
    
    #或者使用mysqlbinlog，如果报错使用--no-defaults（使用全路径）
    [root@localhost ~]# /var/lib/mysql/bin/mysqlbinlog --no-defaults /usr/local/mysql/data/mysql-bin.00001

2. 同步配置
replicate-do-db:指定同步库
replicate-wild-do-table：指定同步库表


reset master; /清空日志
show master logs;

配置从数据库的my.cnf。
　　　　[mysqld]

　　　　server-id=2
　　　　master-host=192.168.1.111
　　　　master-user=mstest      　　//第一步创建账号的用户名
　　　　master-password=123456   //第一步创建账号的密码
　　　　master-port=3306
　　　　master-connect-retry=60
　　　　replicate-do-db=mstest        //要同步的mstest数据库,要同步多个数据库，就多加几个replicate-db-db=数据库名
　　　　replicate-ignore-db=mysql　 //要忽略的数据库　


### 跳过
主键、索引重复
slave_skip_errors = 1062,1061

 Last_SQL_Errno: 1050
               Last_SQL_Error: Error 'Table 'sys_config' already exists' on query. Default database: 'jx_test'. Query: 'CREATE TABLE `sys_config` (
`id`  int NOT NULL AUTO_INCREMENT ,


在从库上指定同点的位点。
change master to master_host='10.0.5.x', master_user='repl', master_password='123456++', master_log_file='mysql-bin.000009', master_log_pos=4;

Last_SQL_Errno: 1032
Last_SQL_Error: Could not execute Update_rows event on table jx_test.report_site; Can't find record in 'report_site', Error_code: 1032; handler error HA_ERR_KEY_NOT_FOUND; the event's master log mysql-bin.000054, end_log_pos 9718696

1、直接跳过错误执行语句
2、找到错误执行语句，修复从库数据
第一种解决方案会有造成主从不一致的隐患（delete语句可以跳过），第二种是从根本上解决问题比较推荐

语句跳过操作方法如下：
--传统模式
mysql> stop slave;
#表示跳过一步错误，后面的数字可变
mysql> set global sql_slave_skip_counter =1;
mysql> start slave;

之后再用mysql> show slave status\G 查看：

Slave_IO_Running: Yes
Slave_SQL_Running: Yes

--GTID模式
mysql> stop slave;

通过show slave status\G;找到Retrieved_Gtid_Set:7800a22c-95ae-11e4-983d-080027de205a:10

mysql> set GTID_NEXT='7800a22c-95ae-11e4-983d-080027de205a:10 '

mysql> begin;commit;

mysql> set GTID_NEXT='AUTOMATIC';

mysql> start slave;