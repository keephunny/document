1、首先卸载系统自带的mysql 5.1的包
    yum   -y  remove   mysql-libs-5.1.61-4.el6.x86_64

2、开始安装MySQL 5.7.9的包

3、报错： warning: mysql-community-server-5.7.9-1.el6.x86_64.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
error: Failed dependencies:
        libnuma.so.1()(64bit) is needed by mysql-community-server-5.7.9-1.el6.x86_64
        libnuma.so.1(libnuma_1.1)(64bit) is needed by mysql-community-server-5.7.9-1.el6.x86_64
        libnuma.so.1(libnuma_1.2)(64bit) is needed by mysql-community-server-5.7.9-1.el6.x86_64

4、安装 numactl即可    
   yum  install    numactl
   
   
rpm -ivh mysql-community-devel-5.7.29-1.el7.x86_64
rpm -ivh mysql-community-common-5.7.29-1.el7.x86_64
rpm -ivh mysql-community-libs-5.7.29-1.el7.x86_64
rpm -ivh mysql-community-client-5.7.29-1.el7.x86_64
rpm -ivh mysql-community-server-5.7.29-1.el7.x86_64


查看初始密码
grep 'temporary password' /var/log/mysqld.log
查看状态
systemctl status mysqld


开机启动
vim /etc/rc.local
    service mysqld start


#### 密码规则
```
SHOW VARIABLES LIKE 'validate_password%';

validate_password_check_user_name	OFF
validate_password_dictionary_file	
validate_password_length	8
validate_password_mixed_case_count	1
validate_password_number_count	1
validate_password_policy	MEDIUM
validate_password_special_char_count	1

将密码安全等级设置为low
set global validate_password_policy=0; 
```