先查看MySQL版本信息：
mysql> select version();
+------------+
| version()  |
+------------+
| 5.6.16-log |
+------------+

* 帮助查看：
    mysql> help show
    mysql> help SHOW TABLE;
    mysql> help SHOW WARNINGS

* 显示mysql中所有数据库的名称.
mysql>show databases;

* 显示当前数据库中所有表的名称
    mysql>show tables;
    或
    mysql>show tables from database_name;

* 显示表中列名称
mysql>show columns from database_name.table_name;

* 查看某MySQL用户的使用权限
mysql>show grants for user_name;

* 显示create database 语句是否能够创建指定的数据库,并可以查看到创建库语句的SQL信息
mysql>show create database database_name;

* 显示create table 语句是否能够创建指定的数据表,并可以查看到表创建语句的SQL信息
mysql>show create table table_name;

* 显示安装以后可用的存储引擎和默认引擎。
mysql>show engines;

* 显示最后一个执行的语句所产生的错误、警告和通知
mysql> show warnings;

* 只显示最后一个执行语句所产生的错误
mysql>show errors;

* 显示系统中正在运行的所有进程，也就是当前正在执行的查询
mysql> show processlist\G

* 查看所有存储过程
mysql> show procedure status;

* 查看某个存储过程内容
show create procedure 存储过程名称;

* 查看函数的内容
show create function func_name;


* 查看建表语句
show create table 表名