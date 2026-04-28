
### 下载安装包


```
[root@localhost ]# rpm -qa | grep postgres
[root@localhost ]# yum remove postgresql*
[root@localhost ]# 

```


```
# Install the repository RPM:
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm

# Install PostgreSQL:
sudo yum install -y postgresql13-server

# Optionally initialize the database and enable automatic start:
[root@localhost ]# /usr/pgsql-13/bin/postgresql-13-setup initdb
[root@localhost ]# systemctl enable postgresql-13
[root@localhost ]# systemctl start postgresql-13

/var/lib/pgsql/13/data
```
### 前置配置 
创建用户
```
[root@localhost ]# groupadd  postgres
[root@localhost ]# useradd postgres -g postgres -m -d /home/postgres
[root@localhost ]# chown postgres:postgres /home/postgres
[root@localhost ]# passwd postgres
    postgres
```

[root@localhost ]# vim pg_hba.conf 
    # IPv4 local connections:
    host    all             all             127.0.0.1/32            scram-sha-256
    host    all             all             0.0.0.0/0               trust


su postgres
pgsql
postgres=# alter user postgres with password '123456'

 
进入数据库  pgsql -U pgsql -d test 

列出出数据库 \l 

列出表 \d

切换数据库 \c test

删除数据库 drop database test

删除表 drop table xxx

查看表详细结构 \d+

查看表内容 selet * from xxx
查看数据库表专用空间  \dt+
清理数据库表缓存   vacuum FULL 表名


下载地址：
linux 
https://www.postgresql.org/ftp/source/

window
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads


### postGIS



#### 版本对应
|-- PostgreSQL Version -|- PostGIS Version -|
|- 11(EOL)	 -|- 2.5(EOL)、3.0、3.1、3.2、3.3 -|
|- 12 -|- 2.5(EOL)、3.0、3.1、3.2、3.3、3.4、3.5 -|
|- 13 -|- 3.0、3.1、3.2、3.3、3.4、3.5 -|
|- 14 -|- 3.1、3.2、3.3、3.4、3.5 -|
|- 15 -|- 3.2、3.3、3.4、3.5 -|
|- 16 -|- 3.3、3.4、3.5 -|
|- 17 -|- 3.3、3.4、3.5 -|

	
	
	
————————————————
版权声明：本文为CSDN博主「我是小超人－雨石花」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/u013766836/article/details/156392490