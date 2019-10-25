## redhat安装
### 第一步：下载 
下载MySQL5.7：http://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.12-1.el6.x86_64.rpm-bundle.tar
### 第二步：安装 
安装前检查服务器是否已安装MySQL，如已安装则将其卸载:    
```    
    rpm -qa|grep mysql  //查看有没有安装，如果有则卸载
    rpm -e --nodeps mysql-libs-5.1.71-1.el6.x86_64  //卸载
```
将下载的文件进行解压：tar -xf mysql-5.7.12-1.el6.x86_64.rpm-bundle.tar   
yum -y install autoconf    
按顺序依次安装：   
```
    rpm -ivh mysql-community-common-5.7.12-1.el6.x86_64.rpm
    rpm -ivh mysql-community-libs-5.7.12-1.el6.x86_64.rpm
    rpm -ivh mysql-community-devel-5.7.12-1.el6.x86_64.rpm
    rpm -ivh mysql-community-client-5.7.12-1.el6.x86_64.rpm
    rpm -ivh mysql-community-server-5.7.12-1.el6.x86_64.rpm

    rpm -vih perl-Data-Dumper-2.145-3.el7.x86_64.rpm
    http://mirror.centos.org/centos/7/os/x86_64/Packages/perl-Data-Dumper-2.145-3.el7.x86_64.rpm
### 第三步：环境变量设置
1. 启动MySQL：service mysqld start 
2. 登录：mysql -u root -p，初次登录密码为空，直接回车： 
    可能会报错error Access denied for user 错误，原因是因为MySQL5.7中的mysql.user 表中没有Password字段，所以要以安全方式登录，然后修改密码。 
    解决方法如下： 
    修改MySQL配置文件：vim /etc/my.cnf，在文件末尾加上：skip-grant-tables，保存后重启MySQL服务：service mysqld restart，然后重新登录。  
3. 修改密码，用户密码是在名为mysql的database下面： 
    mysql> use mysql
    mysql> update user set password_expired='N' where user='root’;    
    mysql> update user set authentication_string=password('123456') where user=‘root’;
    mysql> flush privileges;
4. 禁止域名解析
skip-name-resolve
### 其他配置 

1. 编码设置：vim /etc/my.cnf，文件末尾加上编码内容default-character-set=utf8
1. 允许远程访问MySQL： 
1. 赋予任何主机访问数据的权限 
    mysql>grant all privileges on . to ‘root’@’%’with grant option; 
    会报错：ERROR 1133 (42000): Can’t find any matching row in the user table 
    其实如果事先在mysql.user表中存在root用户就正常了，或，将这句末尾加上identified by ‘密码’ 也就正常了。如下面的命令行 
    mysql>grant all privileges on . to ‘root’@’%’identified by ‘123456’ with grant option;
1. 更改密码策略：
    mysql> set global validate_password_length=0; --更改密码长度
    mysql> set global validate_password_policy=0; --更改密码策略为LOW
1. 设置允许远程登录
    mysql> use mysql;
    mysql> select host,user,password from user;
    mysql> update user set host='%' where user='root' and host='localhost';
    mysql> flush privileges;
    mysql> exit;
1. 设置开机自启动
    chkconfig mysql on
    chkconfig --list | grep mysql
1. MySQL的默认安装位置说明
    /var/lib/mysql/ #数据库目录
    /usr/share/mysql #配置文件目录
    /usr/bin #相关命令目录
    /etc/init.d/mysql #启动脚本 注：卸载mysql的时候，将这些目录下的文件也删掉。


### 添加新用户
```
    //允许本地 IP 访问 localhost, 127.0.0.1
    create user 'test'@'localhost' identified by '123456';

    //允许外网 IP 访问
    create user 'test'@'%' identified by '123456';

    //取消外网IP访问
    select user,host from mysql.user;
    update mysql.user



    //刷新授权
    flush privileges;

    //为用户创建数据库
    create database test DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

    //授予用户通过外网IP对于该数据库的全部权限
    grant all privileges on `testdb`.* to 'test'@'%' identified by '123456';

    //授予用户在本地服务器对该数据库的全部权限
    grant all privileges on `testdb`.* to 'test'@'localhost' identified by '123456';

    //指定部分权限给一用户，可以这样来写:
　　grant select,update on testDB.* to test@localhost identified by '1234';

　　//授权test用户拥有所有数据库的某些权限： 　 
　　mysql>grant select,delete,update,create,drop,insert on *.* to test@"%" identified by "1234";


    //刷新权限
    flush privileges;
### 设置数据库目录

### 
    error: Failed dependencies:
        perl(Data::Dumper) is needed by
    yum install -y perl-Data-Dumper
    yum install -y perl perl-devel


ERROR 1820 (HY000): You must SET PASSWORD before executing this statement
mysql> set password=password('123456');


###  开机启动
chkconfig --list

### 限制内网访问
```
    grant all privileges on *.* to 'root'@'192.168.0.0/255.255.0.0' identified by '123456';
    flush privilges;

```
  
### 快速导入数据
		mysql -uroot -pTLlifeline@DB2019 cityll_tongling<cityll-tongling.sql   
        sql里use 数据库