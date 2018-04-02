https://www.zabbix.com/download?zabbix=3.2&os_distribution=centos&os_version=7&db=MySQL

a. Install Repository with MySQL database
documentation
    # rpm -i http://repo.zabbix.com/zabbix/3.2/rhel/7/x86_64/zabbix-release-3.2-1.el7.noarch.rpm
b. Install Zabbix server, frontend, agent
    # yum install zabbix-server-mysql zabbix-web-mysql zabbix-agent
c. Create initial database
    # mysql -uroot -p
    password
    mysql> create database zabbix character set utf8 collate utf8_bin;
    mysql> grant all privileges on zabbix.* to zabbix@localhost identified by 'password';
    mysql> quit;
Import initial schema and data. You will be prompted to enter your newly created password.
    # zcat /usr/share/doc/zabbix-server-mysql*/create.sql.gz | mysql -uzabbix -p zabbix
d. Configure the database for Zabbix server
    DBPassword=password

e. Start Zabbix server and agent processes
Start Zabbix server and agent processes and make it start at system boot:
    # systemctl start zabbix-server zabbix-agent httpd
    # systemctl enable zabbix-server zabbix-agent httpd
f. Configure PHP for Zabbix frontend
Edit file /etc/httpd/conf.d/zabbix.conf, uncomment and set the right timezone for you.
    # php_value date.timezone Europe/Riga
    php_value date.timezone Asia/Shanghai
    service httpd restart

http://php.net/date.timezone
默认密码
用户名:Admin    注A大写
密码：zabbix


http://blog.chinaunix.net/uid-25266990-id-3380929.html

修改php.ini文件，查找 ;date.timezone = ，把前面的分号去掉在 “=”后面加上时区。 


vi /etc/zabbix/zabbix_server.conf 

重启服务
service httpd restart
service zabbix-agent restart
service zabbix-server restart

查看日志
cat /var/log/zabbix/zabbix_server.log 

zabbix监控mysql 
/etc/zabbix/chk_mysql.sh
http://www.ttlsa.com/zabbix/zabbix-monitor-mysql/


配置静态IP
这里说一下需要修改的位置:

# cd /etc/sysconfig/network-scripts
ifcfg-enoxxxx
#修改
BOOTPROTO=static #这里讲dhcp换成ststic
ONBOOT=yes #将no换成yes
#新增
IPADDR=192.168.85.100 #静态IP
GATEWAY=192.168.85.2 #默认网关
NETMASK=255.255.255.0 #子网掩码
保存退出后,重启网络服务:

# service network restart
Restarting network (via systemctl):                        [  确定  ]


安装zabbix
https://www.linuxidc.com/Linux/2016-08/133866.htm

配置网页监控
https://www.cnblogs.com/ding2016/p/7448055.html

常见问题
zabbix启动报cannot set resource limit: [13] Permission denied问题解决
https://www.90.vc/archives/2084

sudo tee /etc/docker/daemon.json <<-'EOF' {"registry-mirrors": ["https://7ff2x2wd.mirror.aliyuncs.com"]} EOF


centos安装docker
https://www.linuxidc.com/Linux/2017-03/141714.htm


docker exec -it 145f8 bash

网络类型
bridge host
端口映射


    https://hub.docker.com/_/tomcat/

    
    运行镜象
    docker run -d docker.io/nginx 
    进入镜象
    docker exec -it 145f8 bash
    停目镜象
     docker stop 145f8

 


docker run --name some-zabbix-agent -p 10050:10050 -e ZBX_HOSTNAME="hostname" -e ZBX_SERVER_HOST="192.168.54.129" -e ZBX_SERVER_PORT=10051 -d zabbix/zabbix-agent