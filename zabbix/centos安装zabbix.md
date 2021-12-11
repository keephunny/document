

```
Error downloading packages:
  unixODBC-2.3.1-11.el7.x86_64: [Errno 256] No more mirrors to try.
  OpenIPMI-modalias-2.0.23-2.el7.x86_64: [Errno 256] No more mirrors to try.
  1:net-snmp-libs-5.7.2-37.el7.x86_64: [Errno 256] No more mirrors to try.
  OpenIPMI-libs-2.0.23-2.el7.x86_64: [Errno 256] No more mirrors to try.
  libevent-2.0.21-4.el7.x86_64: [Errno 256] No more mirrors to try.
```

以上报错可能是本地yum源没有配好。









安装nginx 源码安装
nginx-1.10.3.tar.gz
yum install gcc gcc-c++ -y
yum install pcre pcre-devel -y
yum install zlib zlib-devel -y
yum install openssl openssl-devel -y

cd nginx-1.10.3
./configure 
make && make install
安装完成

groupadd www
useradd -g www www -s /sbin/nologin
chown -R www:www /usr/local/nginx
vim ./conf/nginx.conf
	user www;
sbin/nginx 
sbin/nginx -s reload
			-s start  
			 
ln -s /usr/local/nginx/sbin/nginx /usr/local/sbin

firewall-cmd --state
service firewalld stop
systemctl disable firewalld

-------------------------------------------
安装mysql 
一定要先安装autoconf
yum -y install autoconf	

warning: MySQL-server-5.6.42-1.el6.x86_64.rpm: Header V3 DSA/SHA1 Signature, key ID 5072e1f5: NOKEY
error: Failed dependencies:
	perl(Data::Dumper) is needed by MySQL-server-5.6.42-1.el6.x86_64
--force --nodeps
	
FATAL ERROR: please install the following Perl modules before executing /usr/bin/mysql_install_db:
Data::Dumper	
yum -y install autoconf	
/usr/bin/mysql_install_db --user=mysql

rpm -evh mariadb-libs-1:5.5.56-2.el7.x86_64 --nodeps

skip-name-resolve
skip-grant-tables

character_set_server=utf8
character_set_client=utf8
collation-server=utf8_general_ci
lower_case_table_names=1

----------------------------------
安装php
https://blog.csdn.net/qq_39591494/article/details/78983861


#./configure --prefix=/usr/local/php5.6 --with-config-file-path=/etc  --with-mysql=/usr/local/mysql --with-mysqli=/usr/local/mysql/bin/mysql_config --with-mysql-sock=/usr/local/mysql/mysql.sock --with-gd --with-iconv --with-libxml-dir=/usr --with-mhash --with-mcrypt --with-config-file-scan-dir=/etc/php.d --with-bz2 --with-zlib --with-freetype-dir --with-png-dir --with-jpeg-dir --enable-xml --enable-bcmath --enable-shmop --enable-sysvsem --enable-inline-optimization --enable-mbregex --enable-fpm --enable-mbstring --enable-ftp --enable-gd-native-ttf --with-openssl --enable-pcntl --enable-sockets --with-xmlrpc --enable-zip --enable-soap --without-pear --with-gettext --enable-session --with-mcrypt --with-curl && make && make install


./configure --prefix=/usr/local/php5.6 --with-config-file-path=/etc  --with-mysql --with-mysqli --with-mysql-sock=/var/lib/mysql/mysql.sock --with-gd --with-iconv --with-libxml-dir=/usr --with-mhash --with-mcrypt --with-config-file-scan-dir=/etc/php.d --with-bz2 --with-zlib --with-freetype-dir --with-png-dir --with-jpeg-dir --enable-xml --enable-bcmath --enable-shmop --enable-sysvsem --enable-inline-optimization --enable-mbregex --enable-fpm --enable-mbstring --enable-ftp --enable-gd-native-ttf --with-openssl --enable-pcntl --enable-sockets --with-xmlrpc --enable-zip --enable-soap --without-pear --with-gettext --enable-session --with-mcrypt --with-curl && make && make install

find / -name mysql.h
  如果有。请指定--with-mysql=/跟你的正常路径。


error: xml2-config not found
yum install libxml2* -y

configure: error: Please reinstall the BZip2 distribution
yum install bzip2 bzip2-devel -y

Please reinstall the libcurl distribution -     easy.h should be in <curl-dir>/include/curl/
yum install curl curl-devel -y

configure: error: Cannot find OpenSSL's <evp.h>
yum install openssl openssl-devel -y

configure: error: Please reinstall the BZip2 distribution

configure: error: jpeglib.h not found.
yum install libjpeg -y
yum -y install libjpeg-devel -y

png.h not found
yum install libpng -y
yum install libpng-devel -y

configure: error: freetype-config not found.
yum install freetype-devel -y

configure: error: mcrypt.h not found. Please reinstall libmcrypt.
tar zxf libmcrypt-2.5.7.tar.gz 
cd libmcrypt-2.5.7/
./configure && make && make install

configure: error: Cannot find MySQL header files under /usr/local/mysql.
Note that the MySQL client library is not bundled anymore!
安装mysql

yum更新

yum clean all
yum makecache

ifconfig command not found
sudo yum install net-tools

配置 php
cp php.ini-production /usr/local/php/etc/php.ini 
rm -rf /etc/php.ini
ln -s /usr/local/php/etc/php.ini  /etc/php.ini
ls -l /etc/php.ini

cp /usr/local/php/etc/php-fpm.conf.default  /usr/local/php/etc/php-fpm.conf
ln -s /usr/local/php/etc/php-fpm.conf  /etc/php-fpm.conf
vim /usr/local/php/etc/php-fpm.conf
	pid = run/php-fpm.pid
	user = www
	group = www
php.ini修改不生效
	vim /etc/init.d/php-fpm 
	php_opts="--fpm-config $php_fpm_CONF --pid $php_fpm_PID -c /etc/php.ini"
#复制php-fpm到启动目录
[root@ssticentos65 ~]# cp /usr/local/src/php-5.6.28/sapi/fpm/init.d.php-fpm /etc/init.d/php-fpm
[root@ssticentos65 ~]# ls -l /etc/init.d/php-fpm
-rw-r--r--. 1 root root 2354 Jan  6 02:37 /etc/init.d/php-fpm
#赋予php-fpm执行权限
[root@ssticentos65 ~]# chmod 755 /etc/init.d/php-fpm
[root@ssticentos65 ~]# ls -l /etc/init.d/php-fpm
-rwxr-xr-x. 1 root root 2354 Jan  6 02:37 /etc/init.d/php-fpm
#设置php-fpm开机启动
[root@ssticentos65 ~]# chkconfig php-fpm on
[root@ssticentos65 ~]# chkconfig --list php-fpm
php-fpm        0:off   1:off   2:on    3:on    4:on    5:on    6:off
#编辑php配置文件php.ini
[root@ssticentos65 ~]# vim /usr/local/php/etc/php.ini
	找到：disable_functions =
	修改成disable_functions= passthru,exec,system,chroot,scandir,chgrp,chown,shell_exec,proc_open,proc_get_status,ini_alter,ini_alter,ini_restore,dl,openlog,syslog,readlink,symlink,popepassthru,stream_socket_server,escapeshellcmd,dll,popen,disk_free_space,checkdnsrr,checkdnsrr,getservbyname,getservbyport,disk_total_space,posix_ctermid,posix_get_last_error,posix_getcwd,posix_getegid,posix_geteuid,posix_getgid,posix_getgrgid,posix_getgrnam,posix_getgroups,posix_getlogin,posix_getpgid,posix_getpgrp,posix_getpid,posix_getppid,posix_getpwnam,posix_getpwuid, posix_getrlimit,posix_getsid,posix_getuid,posix_isatty, posix_kill,posix_mkfifo,posix_setegid,posix_seteuid,posix_setgid, posix_setpgid,posix_setsid,posix_setuid,posix_strerror,posix_times,posix_ttyname,posix_uname
	找到：date.timezone =
	修改为：date.timezone = PRC #设置时区
	找到：expose_php = On
	修改为：expose_php = Off #禁止显示php版本的信息
	找到：short_open_tag = Off
	修改为：short_open_tag = On #支持php短标签
	找到opcache.enable=0
	修改为opcache.enable=1 #php支持opcode缓存
	找到：opcache.enable_cli=1 #php支持opcode缓存
	修改为：opcache.enable_cli=0
	在最后一行添加：zend_extension=opcache.so #开启opcode缓存功能 (在文本按Shift+G就能跳到最后一行)
	post_max_size=16M
	max_execution_time=300
	max_input_time=300
	always_populate_raw_post_data=-1
	mbstring.func_overload=0
	
配置 nginx代理到php
	vim /usr/local/nginx/conf/nginx.conf
	location / {
		root   html;
		index  index.html index.htm index.php;         #添加index.php
	}

	location ~ \.php$ {
		root           html;
		fastcgi_pass   127.0.0.1:9000;
		fastcgi_index  index.php;
		fastcgi_param  SCRIPT_FILENAME $document_root$fastcgi_script_name;
		include        fastcgi_params;
	
	}
index.php
	<?php
	phpinfo();
	echo "---------------------------------------";
	$link=mysql_connect('127.0.0.1','root','123456');
	if($link) echo "ok";
	mysql_close();
	?>
启动
service php-fpm start

jdk配置 源码安装
chmod -R 755 jdk1.8.0_191/
vim /etc/profile

export JAVA_HOME=/usr/java/jdk1.8.0_144
export CLASSPATH=.:${JAVA_HOME}/jre/lib/rt.jar:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export PATH=$PATH:${JAVA_HOME}/bin
source /etc/profile


安装mysql 
错误：依赖检测失败：
        perl(Data::Dumper) 被 Percona-Server-server-56-5.6.27-rel76.0.el6.x86_64 需要
yum install perl
yum -y install autoconf




yum install -y net-snmp net-snmp-devel curl-devel OpenIPMI-devel  libssh2-devel
yum install -y net-snmp net-snmp-devel curl-devel OpenIPMI-devel  libssh2-devel

---------------------------------------------------
安装zabbix
./configure --prefix=/usr/local/zabbix --enable-server --enable-agent --enable-java --with-mysql=/usr/bin/mysql_config --with-net-snmp --with-libcurl --with-libevent=/opt/libevent/  --with-openipmi
#./configure --prefix=/usr/local/zabbix --enable-server --enable-agent --enable-java --with-mysql=/usr --with-net-snmp --with-libcurl --with-openipmi
mysql=/usr/bin/mysql_config_editor


configure: error: Invalid Net-SNMP directory - unable to find net-snmp-config

 yum install net-snmp-devel -y
 yum install net-snmp-devel -y --nodeps
configure: error: Invalid OPENIPMI directory - unable to find ipmiif.h
  yum install OpenIPMI -y


shell> wget http://monkey.org/~provos/libevent-1.4.14b-stable.tar.gz
shell> tar xf libevent-1.4.14b-stable.tar.gz 
shell>cd libevent-1.4.14b-stable
shell> ./configure --prefix=/opt/libevent
shell> make && make install
shell> ./configure --prefix=/usr/local/zabbix/ --enable-server  --enable-agent --enable-java --with-mysql --with-net-snmp=/ --with-libcurl --with-libxml2 --with-unixodbc --with-libevent=/opt/libevent/
configure: error: Unable to use libpcre (libpcre check failed
```)

解决：libpcre
`shell>yum -y install pcre*`
缺少依赖解决完毕
  yum install OpenIPMI -y
configure: error: Invalid OPENIPMI directory - unable to find ipmiif.h
 rpm -ivh OpenIPMI-devel-2.0.23-2.el7.x86_64.rpm --nodeps
 
configure: error: Invalid Net-SNMP directory - unable to find net-snmp-config

./configure --with-default-snmp-version="3" --with-sys-contact="@@no.where" --with-sys-location="Unknown" --with-logfile="/var/log/snmpd.log" --with-persistent-directory="/var/net-snmp" --with-mib-modules="ucd-snmp/lmsensorsMib" --with-ldflags="-lsensors" --prefix=/usr/local/net-snmp

http://www.mamicode.com/info-detail-2256732.html
./configure --prefix=/usr/local/zabbix -enable-server -enable-agent --with-mysql --with-net-snmp --with-libcurl --with-libevent=/opt/libevent/

configure: error: Invalid Net-SNMP directory - unable to find net-snmp-config

db.c:27:20: fatal error: mysql.h: No such file or directory

登录mysql报错-bash: mysql: 未找到命令
ln -s /usr/local/mysql/bin/mysql /usr/bin

 
db.c:27:20: 致命错误：mysql.h：没有那个文件或目录
http://blog.51cto.com/ityunwei2017/1733294
http://blog.51cto.com/ityumwei2017/1733294
yum install mariadb-devel #如果冲突就先删掉mysql-devel mysql-server

sbin/zabbix_server: error while loading shared libraries: libmysqlclient.so.18: cannot open shared object file: No such file or directory
在源码包里找一个libmysqlclient.so.18文件拷贝到/usr/lib
然后更新生效
/sbin/ldconfig -v

sbin/zabbix_server: error while loading shared libraries: libevent-1.4.so.2: cannot open shared object file: No such file or directory
find / -name libevent-1.4.so.2
/opt/libevent/lib/libevent-1.4.so.2
cp /opt/libevent/lib/libevent-1.4.so.2 /usr/lib
/sbin/ldconfig -v


Stopping zabbix_server (via systemctl):  Warning: zabbix_server.service changed on disk. Run 'systemctl daemon-reload' to reload units.
systemctl daemon-reload

php不生效/etc/php.ini
修改
vim /etc/init.d/php-fpm 

php_opts="--fpm-config $php_fpm_CONF --pid $php_fpm_PID -c /etc/php.ini"
增加-c /etc/php.ini


Cannot create the configuration file.
Unable to create the configuration file.

Error in query [UPDATE users SET lang='zh_CN' WHERE userid='1'] [Lock wait timeout exceeded; try restarting transaction]
SQL statement execution has failed "UPDATE users SET lang='zh_CN' WHERE userid='1'".

ERROR 1045 (28000): Access denied for user 'zabbix'@'localhost' (using password: NO)
grant all privileges on zabbix.* to zabbix@% identified by  'zabbix';
grant all privileges on zabbix.* to zabbix@localhost identified by 'zabbix';
flush privilegs;


Save it as "/usr/local/nginx/html/zabbix/conf/zabbix.conf.php"
chmod a+w -R zabbix/ 增加写权限

update dbversion set mandatory='3040000'
安装fping
cd fping-3.2
./configure && make && make install
chown root:zabbix /usr/local/sbin/fping 
chmod 4710 /usr/local/sbin/fping 

配置zabbix_server
ln -s /usr/local/zabbix/bin/* /usr/local/bin/
ln -s /usr/local/zabbix/sbin/* /usr/local/sbin/
	create database zabbix character set utf8;
	grant all privileges on zabbix.* to zabbix@'%' identified by '123456';
	
cd /usr/local/src/zabbix/zabbix-3.4.15/database/mysql
mysql -uroot -p123456 zabbix<schema.sql 
mysql -uroot -p123456 zabbix<images.sql 
mysql -uroot -p123456 zabbix<data.sql	

groupadd zabbix
useradd -g zabbix zabbix
vim /usr/local/zabbix/etc/zabbix_server
	LogFile=/usr/local/zabbix/logs/zabbix_server.log
	PidFile=/usr/local/zabbix/logs/zabbix_server.pid
	DBHost=localhost
	DBName=zabbix
	DBUser=zabbix
	DBPassword=123456
	DBPort=3306
	DBSocket=/var/lib/mysql//mysql.sock
	FpingLocation=/usr/local/sbin/fping
mkdir -p /usr/local/zabbix/logs
chown -R zabbix:zabbix /usr/local/zabbix/

[root@localhost zabbix-3.4.15]# cp misc/init.d/fedora/core/zabbix_server /etc/rc.d/init.d/zabbix_server
[root@localhost zabbix-3.4.15]# cp misc/init.d/fedora/core/zabbix_agentd /etc/rc.d/init.d/zabbix_agentd
[root@localhost zabbix-3.4.15]# chmod +x /etc/rc.d/init.d/zabbix_server
[root@localhost zabbix-3.4.15]# chmod +x /etc/rc.d/init.d/zabbix_agentd 
[root@localhost zabbix-3.4.15]# chkconfig --add zabbix_server
[root@localhost zabbix-3.4.15]# chkconfig --add zabbix_agentd
[root@localhost zabbix-3.4.15]# chkconfig zabbix_server on 
[root@localhost zabbix-3.4.15]# chkconfig zabbix_agentd on 
vim /etc/rc.d/init.d/zabbix_server 
	BASEDIR=/usr/local/zabbix/
	PIDFILE=/usr/local/zabbix/logs/$BINARY_NAME.pid
vim /etc/rc.d/init.d/zabbix_agentd
	BASEDIR=/usr/local/zabbix/
	PIDFILE=/usr/local/zabbix/logs/$BINARY_NAME.pid
service zabbix_server start

zabbix图片汉字乱码
拷贝楷体（控制面板字体库）到 zabbix/font
修改zabbix/include/defines.inc.php
define('ZBX_GRAPT_FONT_NAME','SIMKAI');
刷新页面


--------------------------------------------------------------------------------
安装zabbix_agent
./configure --prefix=/usr/local/zabbix --enable-agent
make && make install
cp misc/init.d/fedora/core/zabbix_agentd /etc/rc.d/init.d/zabbix_agentd
groupadd zabbix 
useradd -g zabbix zabbix
mkdir -p /usr/local/zabbix/logs
chown -R zabbix:zabbix /usr/local/zabbix/
chmod +x /etc/rc.d/init.d/zabbix_agentd
chkconfig --add zabbix_agentd
chkconfig zabbix_agentd on
vim /etc/rc.d/init.d/zabbix_agentd
	BASEDIR=/usr/local/zabbix
	PIDFILE=/usr/local/zabbix/logs/$BINARY_NAME.pid
	
vim /usr/local/zabbix/etc/zabbix_agentd.conf
	PidFile=/usr/local/zabbix/logs/zabbix_agentd.pid
	LogFile=/usr/local/zabbix/logs/zabbix_agentd.log
	Server=10.5.4.58	
	ServerActive=10.5.4.58
	Hostname=10.5.4.59
service zabbix_agentd start	
systemctl daemon-reload	
	
配置web页面	
mkdir /usr/local/nginx/html/zabbix
chown -R www:www /usr/local/nginx/html/zabbix/
cp -r frontends/php/* /usr/local/nginx/html/zabbix/


无法更新主机 模板不能重复链接到其它模板, 即使透过其它模板	
不要同时添加	Template OS Linux  Template App Zabbix Agent 
	Template OS Linux 包含了zabbix agent模版
	
	
	
	
	
	