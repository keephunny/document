1. 下载zabbix3.4官网 
    https://nchc.dl.sourceforge.net/project/zabbix/ZABBIX%20Latest%20Stable/3.4.15/zabbix-3.4.15.tar.gz
2. 安装php
    yum本地源
    yum install php
3. 安装httpd apache

    yum install httpd
    查看httpd包是否可用：
    # yum list | grep httpd
    启动
    # httpd
    停止
    # httpd -k stop
    设置开机自动启动：
    chkconfig httpd on

    安装目录介绍
    Apache默认将网站的根目录指向/var/www/html 目录
    默认的主配置文件是/etc/httpd/conf/httpd.conf
    配置存储在的/etc/httpd/conf.d/目录
