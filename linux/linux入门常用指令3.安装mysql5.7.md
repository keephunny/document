


### 下载安装包
mysql-5.7.29-1.el7.x86_64.rpm-bundle.tar
```
[root@localhost src]# mkdir mysql
[root@localhost src]# tar -xvf mysql-5.7.29-1.el7.x86_64.rpm-bundle.tar -C mysql
[root@localhost mysql]# cd mysql
[root@localhost mysql]# ll
    mysql-community-client-5.7.29-1.el7.x86_64.rpm
    mysql-community-common-5.7.29-1.el7.x86_64.rpm
    mysql-community-devel-5.7.29-1.el7.x86_64.rpm
    mysql-community-embedded-5.7.29-1.el7.x86_64.rpm
    mysql-community-embedded-compat-5.7.29-1.el7.x86_64.rpm
    mysql-community-embedded-devel-5.7.29-1.el7.x86_64.rpm
    mysql-community-libs-5.7.29-1.el7.x86_64.rpm
    mysql-community-libs-compat-5.7.29-1.el7.x86_64.rpm
    mysql-community-server-5.7.29-1.el7.x86_64.rpm
    mysql-community-test-5.7.29-1.el7.x86_64.rpm

```
### 检测安装环境
```
#查询有没有安装过，如果有需要卸载
[root@localhost src]# rpm -qa|grep mysql -i
[root@localhost src]# rpm -qa|grep mariadb -i
mariadb-libs-5.5.41-2.el7_0.x86_64
#卸载
[root@localhost src]# rpm -e mariadb-libs-5.5.41-2.el7_0.x86_64 --nodeps

#安装依赖包
[root@localhost mysql]# yum install -y perl-Data-Dumper
[root@localhost mysql]# yum install -y perl perl-devel
[root@localhost mysql]# yum install -y libaio
```
### 安装mysql
```
[root@localhost mysql]# rpm -ivh mysql-community-common-5.7.29-1.el7.x86_64.rpm 
[root@localhost mysql]# rpm -ivh mysql-community-libs-5.7.29-1.el7.x86_64.rpm 
[root@localhost mysql]# rpm -ivh mysql-community-devel-5.7.29-1.el7.x86_64.rpm 
[root@localhost mysql]# rpm -ivh mysql-community-libs-compat-5.7.29-1.el7.x86_64.rpm 
[root@localhost mysql]# rpm -ivh mysql-community-client-5.7.29-1.el7.x86_64.rpm 
[root@localhost mysql]# rpm -ivh mysql-community-server-5.7.29-1.el7.x86_64.rpm 
```

### 启动服务
```
# 开机启动
[root@localhost mysql]# systemctl enable mysqld.service   
[root@localhost mysql]# systemctl start mysqld
[root@localhost mysql]# systemctl status mysqld
[root@localhost mysql]# systemctl restart mysqld
    systemctl stop firewalld ：关闭防火墙服务
    systemctl start firewalld ：开启防火墙服务
    systemctl disable firewalld：开机禁用
    systemctl enable firewalld：开机启用
    firewall-cmd --state：查看防火墙状态
```

### 登录服务
```
#查看初始密码
[root@localhost mysql]# grep 'temporary password' /var/log/mysqld.log
2021-11-09T02:55:20.988009Z 1 [Note] A temporary password is generated for root@localhost: H?Kg3tbNllMV

[root@localhost mysql]# mysql -uroot -p
    mysql> show databases;
    ERROR 1820 (HY000): You must reset your password using ALTER USER statement before executing this 
    mysql> SET PASSWORD FOR 'root'@'localhost'= "Root@123";
    mysql> set password=password("xxxxx");
    #密码强度
    ERROR 1819 (HY000): Your password does not satisfy the current policy requirements
    SHOW VARIABLES LIKE 'validate_password%';
    set global validate_password_policy=LOW; 
    set global validate_password_length=6;
[root@localhost mysql]# 
[root@localhost mysql]# 
[root@localhost mysql]# 
```


### 常用配置
```
#复制默认配置文件
[root@localhost mysql]# cp /etc/my.cnf /etc/my.cnf.bak
#修改配置文件
[root@localhost mysql]# vim  /etc/my.cnf
	
	server-id=1
	binlog_format=ROW
    log-bin=mysql-bin
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


    #密码策略配置
    validate_password_policy=0
    validate_password_length=6
    validate_password_mixed_case_count=0
    validate_password_number_count=0
    validate_password_special_char_count=0
        
#重新启动mysql
[root@localhost mysql]# service mysql restart
#开机启动
[root@localhost mysql]# chkconfig mysql on
```

* service mysql stop：停止
* service mysql start：启动
* service mysql restart：重启
* chkconfig mysql on：开机启动

 

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

#分配root访问域
mysql>   grant all privileges on *.* to 'root'@'10.0.%' identified by '123456';  
#创建其它账号及权限
mysql>  create user 'user1'@'%'  identified by '123456';
mysql>  grant all on `db_name`.* to 'user1'@'%';
mysql>  create user 'user2'@'%'  identified by '123456';
mysql>  grant all on `db_name`.* to 'user2'@'%';
#删除用户
mysql>  drop user water@'%';
mysql>  flush privileges;

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

use dbname;
source /usr/local/src/test.sql