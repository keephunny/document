mysql中可以给你一个用户授予如select,insert,update,delete等其中的一个或者多个权限,主要使用grant命令,用法格式为： 
grant 权限 on 数据库对象 to 用户 
一、grant 普通数据用户，查询、插入、更新、删除 数据库中所有表数据的权利。 
grant select on testdb.* to common_user@’%’ 
grant insert on testdb.* to common_user@’%’ 
grant update on testdb.* to common_user@’%’ 
grant delete on testdb.* to common_user@’%’ 
或者，用一条 MySQL 命令来替代： 
grant select, insert, update, delete on testdb.* to common_user@’%’

二、grant 数据库开发人员，创建表、索引、视图、存储过程、函数。。。等权限。 
grant 创建、修改、删除 MySQL 数据表结构权限。 
grant create on testdb.* to developer@’192.168.0.%’; 
grant alter on testdb.* to developer@’192.168.0.%’; 
grant drop on testdb.* to developer@’192.168.0.%’; 
grant 操作 MySQL 外键权限。 
grant references on testdb.* to developer@’192.168.0.%’; 
grant 操作 MySQL 临时表权限。 
grant create temporary tables on testdb.* to developer@’192.168.0.%’; 
grant 操作 MySQL 索引权限。 
grant index on testdb.* to developer@’192.168.0.%’; 
grant 操作 MySQL 视图、查看视图源代码 权限。 
grant create view on testdb.* to developer@’192.168.0.%’; 
grant show view on testdb.* to developer@’192.168.0.%’; 
grant 操作 MySQL 存储过程、函数 权限。 
grant create routine on testdb.* to developer@’192.168.0.%’; – now, can show procedure status 
grant alter routine on testdb.* to developer@’192.168.0.%’; – now, you can drop a procedure 
grant execute on testdb.* to developer@’192.168.0.%’;

三、grant 普通 DBA 管理某个 MySQL 数据库的权限。 
grant all privileges on testdb to dba@’localhost’ 
其中，关键字 “privileges” 可以省略。

四、grant 高级 DBA 管理 MySQL 中所有数据库的权限。 
grant all on . to dba@’localhost’

五、MySQL grant 权限，分别可以作用在多个层次上。 
1. grant 作用在整个 MySQL 服务器上： 
grant select on . to dba@localhost; – dba 可以查询 MySQL 中所有数据库中的表。 
grant all on . to dba@localhost; – dba 可以管理 MySQL 中的所有数据库 
2. grant 作用在单个数据库上： 
grant select on testdb.* to dba@localhost; – dba 可以查询 testdb 中的表。 
3. grant 作用在单个数据表上： 
grant select, insert, update, delete on testdb.orders to dba@localhost; 
4. grant 作用在表中的列上： 
grant select(id, se, rank) on testdb.apache_log to dba@localhost; 
5. grant 作用在存储过程、函数上： 
grant execute on procedure testdb.pr_add to ’dba’@’localhost’ 
grant execute on function testdb.fn_add to ’dba’@’localhost’

六、查看 MySQL 用户权限 
查看当前用户（自己）权限： 
show grants; 
查看其他 MySQL 用户权限： 
show grants for dba@localhost;

七、撤销已经赋予给 MySQL 用户权限的权限。 
revoke 跟 grant 的语法差不多，只需要把关键字 “to” 换成 “from” 即可： 
grant all on . to dba@localhost; 
revoke all on . from dba@localhost;

八、MySQL grant、revoke 用户权限注意事项 
1. grant, revoke 用户权限后，该用户只有重新连接 MySQL 数据库，权限才能生效。 
2. 如果想让授权的用户，也可以将这些权限 grant 给其他用户，需要选项 “grant option“ 
grant select on testdb.* to dba@localhost with grant option; 
这个特性一般用不到。实际中，数据库权限最好由 DBA 来统一管理。

注意：修改完权限以后 一定要刷新服务，或者重启服务，刷新服务用：FLUSH PRIVILEGES。



---

用户权限管理主要有以下作用： 
1. 可以限制用户访问哪些库、哪些表 
2. 可以限制用户对哪些表执行SELECT、CREATE、DELETE、DELETE、ALTER等操作 
3. 可以限制用户登录的IP或域名 
4. 可以限制用户自己的权限是否可以授权给别的用户

* all privileges：表示将所有权限授予给用户。也可指定具体的权限，如：SELECT、CREATE、DROP等。
* on：表示这些权限对哪些数据库和表生效，格式：数据库名.表名，这里写“*”表示所有数据库，所有表。如果我要指定将权限应用到test库的user表中，可以这么写：* * test.user
* to：将权限授予哪个用户。格式：”用户名”@”登录IP或域名”。%表示没有限制，在任何主机都可以登录。比如：”yangxin”@”192.168.0.%”，表示yangxin这个用户只能在192.168.0IP段登录
* identified by：指定用户的登录密码
* with grant option：表示允许用户将自己的权限授权给其它用户


### 刷新权限
 flush privileges;

### 查看用户权限
    show grants for 'root'@'localhost';
### 回收权限
    revoke create on *.* from 'root@localhost';
    flush privileges;
### 删除用户
    drop user 'root'@'localhost'
### 用户重命名
    rename user 'test3'@'%' to 'test1'@'%';
### 修改密码-更新mysql.user表
    use mysql;
    mysql5.7之前
    update user set password=password('123456') where user='root';
    mysql5.7之后
    update user set authentication_string=password('123456') where user='root';
    flush privileges;
### 修改密码-set password命令
    set password for 'root'@'localhost'=password('123456');
### 修改密码- mysqladmin
    mysqladmin -uroot -p123456 password 1234abcd

### 忘记密码
添加登录跳过权限检查配置，修改my.cnf，在mysqld配置节点添加skip-grant-tables配置
    skip-grant-tables

### mysql权限列表

usage：连接（登陆）权限，建立一个用户，就会自动授予其usage权限（默认授予）。 grant usage on *.* to ‘p1′@’localhost’ identified by ‘123′;该权限只能用于数据库登陆，不能执行任何操作；且usage权限不能被回收，也即REVOKE用户并不能删除用户。

1. 数据类
2. 结构类
3. 管理类

* ALL或ALL PRIVILEGES  代表指定权限等级的所有权限。
* ALTER   允许使用ALTER TABLE来改变表的结构，ALTER TABLE同时也需要CREATE和INSERT权限。重命名一个表需要对旧表具有ALTER和DROP权限，对新表具有CREATE和INSERT权限。
* ALTER ROUTINE   允许改变和删除存储过程和函数
* CREATE  允许创建新的数据库和表
* CREATE ROUTINE  允许创建存储过程和包
* CREATE TABLESPACE   允许创建、更改和删除表空间和日志文件组
* CREATE TEMPORARY TABLES 允许创建临时表
* CREATE USER 允许更改、创建、删除、重命名用户和收回所有权限
* CREATE VIEW     允许创建视图
* DELETE  允许从数据库的表中删除行
* DROP    允许删除数据库、表和视图
* EVENT   允许在事件调度里面创建、更改、删除和查看事件
* EXECUETE    允许执行存储过程和包
* FILE        允许在服务器的主机上通过LOAD DATA INFILE、SELECT ... INTO OUTFILE和LOAD_FILE()函数读写文件
* GRANT OPTION    允许向其他用户授予或移除权限
* INDEX   允许创建和删除索引
* INSERT  允许向数据库的表中插入行
* LOCK TABLE  允许执行LOCK TABLES语句来锁定表
* PROCESS 允许显示在服务器上执行的线程信息，即被会话所执行的语句信息。这个权限允许你执行SHOW PROCESSLIST和mysqladmin processlist命令来查看线程，同时这个权限也允许你执行SHOW ENGINE命令
* PROXY   允许用户冒充成为另外一个用户
* REFERENCES  允许创建外键
* RELOAD  允许使用FLUSH语句
* REPLICATION CLIENT  允许执行SHOW MASTER STATUS,SHOW SLAVE STATUS和SHOW BINARY LOGS命令
* REPLICATION SLAVE   允许SLAVE服务器连接到当前服务器来作为他们的主服务器
* SELECT  允许从数据库中查询表
* SHOW DATABASES  允许账户执行SHOW DATABASE语句来查看数据库。没有这个权限的账户只能看到他们具有权限的数据库。
* SHOW VIEW   允许执行SHOW CREATE VIEW语句
* SHUTDOWN    允许执行SHUTDOWN语句和mysqladmin shutdown已经mysql_shutdown() C API函数
* SUPER   允许用户执行CHANGE MASTER TO,KILL或mysqladmin kill命令来杀掉其他用户的线程，允许执行PURGE BINARY LOGS命令，通过SET GLOBAL来设置系统参数，执行mysqladmin debug命令，开启和关闭日志，即使read_only参数开启也可以执行update语句，打开和关闭从服务器上面的复制，允许在连接数达到max_connections的情况* 下连接到服务器。
* TRIGGER 允许操作触发器
* UPDATE  允许更新数据库中的表
* USAGE   代表没有任何权限，只能登陆

### 系统权限表

