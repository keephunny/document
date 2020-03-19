### 下载安装包
MySQL-5.6.42-1.el6.x86_64.rpm-bundle_redhat
```
[root@localhost src]# mkdir mysql
[root@localhost src]# tar -xvf MySQL-5.6.42-1.el6.x86_64.rpm-bundle_redhat.tar -C mysql
[root@localhost mysql]# cd mysql
[root@localhost mysql]# ll
-rw-r--r--. 1 7155 31415 19124980 9月  11 2018 MySQL-client-5.6.42-1.el6.x86_64.rpm
-rw-r--r--. 1 7155 31415  3411264 9月  11 2018 MySQL-devel-5.6.42-1.el6.x86_64.rpm
-rw-r--r--. 1 7155 31415 90167176 9月  11 2018 MySQL-embedded-5.6.42-1.el6.x86_64.rpm
-rw-r--r--. 1 7155 31415 57600260 9月  11 2018 MySQL-server-5.6.42-1.el6.x86_64.rpm
-rw-r--r--. 1 7155 31415  1973900 9月  11 2018 MySQL-shared-5.6.42-1.el6.x86_64.rpm
-rw-r--r--. 1 7155 31415  3969756 9月  11 2018 MySQL-shared-compat-5.6.42-1.el6.x86_64.rpm
-rw-r--r--. 1 7155 31415 51929752 9月  11 2018 MySQL-test-5.6.42-1.el6.x86_64.rpm
```
### 检测安装环境
```
#查询有没有安装过，如果有需要卸载
[root@localhost src]# rpm -qa|grep mysql -i
[root@localhost src]# rpm -qa|grep mariadb -i
mariadb-libs-5.5.41-2.el7_0.x86_64
#卸载
[root@localhost src]# rpm -e mariadb-libs-5.5.41-2.el7_0.x86_64 --nodeps


[root@localhost mysql]# rpm -ivh MySQL-server-5.6.42-1.el6.x86_64.rpm 
警告：MySQL-server-5.6.42-1.el6.x86_64.rpm: 头V3 DSA/SHA1 Signature, 密钥 ID 5072e1f5: NOKEY
错误：依赖检测失败：
	perl(Data::Dumper) 被 MySQL-server-5.6.42-1.el6.x86_64 需要
#安装依赖包
[root@localhost mysql]# yum install -y perl-Data-Dumper
[root@localhost mysql]# yum install -y perl perl-devel
```
### 安装mysql
```
[root@localhost mysql]# rpm -ivh MySQL-server-5.6.42-1.el6.x86_64.rpm 
[root@localhost mysql]# rpm -ivh MySQL-client-5.6.42-1.el6.x86_64.rpm 
[root@localhost mysql]# rpm -ivh MySQL-devel-5.6.42-1.el6.x86_64.rpm 
```

### 常用配置
```
#复制默认配置文件
[root@localhost mysql]# cp /usr/share/mysql/my-default.cnf /etc/my.cnf
#修改配置文件
[root@localhost mysql]# vim  /etc/my.cnf
    character_set_server=utf8
    character_set_client=utf8
    collation-server=utf8_general_ci
    #不区分大小写
    lower_case_table_names=1
    #免密登录
    skip-grant-tables
    #禁用DNS解析
    skip-name-resolve
    #日志文件时间与系统一致
    log_timestamps=SYSTEM

    #默认8小时 2880000
    #数据库连接闲置最大时间值,如果MYSQL中有大量的Sleep进程，则需要修改
    wait_timeout=1800
    interactive_timeout = 1800
        
#重新启动mysql
[root@localhost mysql]# service mysql restart
#开机启动
[root@localhost mysql]# chkconfig mysql on
```

* service mysql stop：停止
* service mysql start：启动
* service mysql restart：重启
* chkconfig mysql on：开机启动


### 登录mysql
由于mysql配置成免密登录，先登录设置root密码
```
[root@localhost mysql]# mysql -uroot
mysql> use mysql; 
mysql> update user set password=password('123456') where user='root'; 
mysql> flush privileges;
mysql> exit;
[root@localhost mysql]# vim /etc/my.cnf
    #skip-grant-tables
[root@localhost mysql]# service mysql restart
[root@localhost mysql]# mysql -uroot -p
mysql> use mysql;
ERROR 1820 (HY000): You must SET PASSWORD before executing this statement
mysql> set password=password('123456');
Query OK, 0 rows affected (0.00 sec)
mysql> user mysql;
mysql> select user,host from user;
```

### 配置mysql账号权限
```
[root@localhost mysql]# mysql -uroot
mysql> use mysql; 
    mysql> select host,user from user;
    +-----------------------+------+
    | host                  | user |
    +-----------------------+------+
    | 127.0.0.1             | root |
    | ::1                   | root |
    | localhost             | root |
    | localhost.localdomain | root |
    +-----------------------+------+
    4 rows in set (0.00 sec)
mysql>     
```




### 创建数据

CREATE DATABASE  yourdbname DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

### 导入数据

linux环境导入

1、导出数据和表结构：
mysqldump -u用户名 -p密码 数据库名 > 数据库名.sql
#/usr/local/mysql/bin/ mysqldump -uroot -p abc > abc.sql
敲回车后会提示输入密码
2、只导出表结构
mysqldump -u用户名 -p密码 -d 数据库名 > 数据库名.sql

#/usr/local/mysql/bin/ mysqldump -uroot -p -d abc > abc.sql

进入mysql
#mysql -uabc_f -p abc < abc.sql