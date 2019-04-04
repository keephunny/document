--force进行强制安装
//查询系统自带的数据库
rpm -qa | grep -i mysql
rpm -qa | grep -i mariadb
//卸载查询到的所有mysql
rpm -e --nodeps mysql-libs-5.1.71-1.el6.x86_64

无密码登录
my.cnf
skip-grant-tables

MySQL-client-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL客户端程序
MySQL-devel-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL的库和头文件
MySQL-embedded-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL的嵌入式程序
MySQL-server-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL服务端程序
MySQL-shared-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL的共享库
MySQL-test-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL的测试组件

　一般对于开发而言，我们只需要下面三个文件就可以。

MySQL-client-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL客户端程序
MySQL-server-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL服务端程序 
MySQL-devel-5.6.21-1.linux_glibc2.5.x86_64.rpm #MySQL的库和头文件

1. 在重新进行安装之前，为确保万无一失，我们还是再确认一下系统中是否有MySQL极其相关的RPM安装包。如果有，则先删除。
rpm -qa | grep -i mysql
执行完上述命令后，返回空数据，则可进行第二步。否则，执行下面的命令删除MySQL的相关包文件。

yum -y remove mysql-libs*
2. 将前面提到的三个MySQL安装文件，拷贝到服务器，然后执行下述安装命令
yum -y install autoconf    
rpm -ivh MySQL-server-5.6.21-1.linux_glibc2.5.x86_64.rpm
rpm -ivh MySQL-devel-5.6.21-1.linux_glibc2.5.x86_64.rpm
rpm -ivh MySQL-client-5.6.21-1.linux_glibc2.5.x86_64.rpm


3.执行下述命令，将MySQL的配置文件拷贝到/etc目录下。
cp /usr/share/mysql/my-default.cnf /etc/my.cnf
4.分别运行下述命令，初始化MySQL及设置密码。
/usr/bin/mysql_install_db    #初始化MySQL 
service mysql start        #启动MySQL 
cat /root/.mysql_secret        #查看root账号的初始密码，会出现下述所示信息


5.设置开机启动
chkconfig mysql on
[root@VM_32_234_centos storage]# chkconfig mysql on 
[root@VM_32_234_centos storage]# chkconfig --list | grep mysql
mysql           0:off   1:off   2:on    3:on    4:on    5:on    6:off

6.修改/etc/my.cnf 
　　设置MySQL的字符集，配置MySQL表明不区分大小写（默认情况下，MySQL对表名区分大小写，列名不区分大小写）。在[mysqld]下面加入如下内容：

 

character_set_server=utf8 character_set_client=utf8 collation-server=utf8_general_ci lower_case_table_names=1 max_connections=1000
skip-grant-tables
skip-name-resolve

7.MySQL的默认文件路径
 

/var/lib/mysql/ #数据库目录
/usr/share/mysql #配置文件目录
/usr/bin #相关命令目录 #启动脚本
 

8.修改数据文件路径
　　1.修改 /etc/my.cnf 文件

 　　vi /etc/my.cnf

复制文件 /var/lib/mysql/  到 /storage/server/mysql-5.6.21-1/data/

 

cp -R /var/lib/mysql/* /storage/server/mysql-5.6.21-1/data/

 9.重启MySQL
# service mysql restart 
Shutting down MySQL.. SUCCESS! 
Starting MySQL. SUCCESS!




配置mysql


无密码登录
my.cnf
skip-grant-tables

use mysql; 
update user set password=password('123456') where user='root'; 
flush privileges; 


error you must set password before executing this statement
SET PASSWORD = PASSWORD('123456');


解决远程连接mysql很慢的方法
skip-name-resolve
在linux下配置文件是/etc/my.cnf，在windows下配置文件是mysql安装目录下的my.ini文件。注意该配置是加在 [mysqld]下面，在更改配置并保存后，然后重启mysql并远程连接测试，一切恢复如初。该参数的官方解释信息如下：



mysql>grant all privileges on testDB.* to test@localhost identified by '1234';

 　　mysql>flush privileges;//刷新系统权限表

 



### 
error: Failed dependencies:
    perl(Data::Dumper) is needed by
yum install -y perl-Data-Dumper
yum install -y perl perl-devel

ERROR 1820 (HY000): You must SET PASSWORD before executing this statement
mysql> set password=password('123456');