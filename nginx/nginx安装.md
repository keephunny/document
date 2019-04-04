配置yum光盘镜像源
# 挂载光驱
mkdir /media/cdrom
mount /dev/cdrom /media/cdrom

cd /etc/yum.repo.d/
修改成.bak
CentOS-Base.repo.bak
CentOS-Media.repo.bak
CentOS-Debuginfo.repo.bak
CentOS-Vault.repo.bak

CentOS-Media.repo
[c6-media]
name=CentOS-$releasever - Media
baseurl=file:///media/CentOS/
gpgcheck=0
enabled=1


umount /media/cdrom


yum install gcc-c++  
$   yum install pcre pcre-devel  
$   yum install zlib zlib-devel  
$   yum install openssl openssl--devel  

安装之前，最好检查一下是否已经安装有nginx

    $   find -name nginx  

如果系统已经安装了nginx，那么就先卸载

    $   yum remove nginx  

首先进入/usr/local目录

    $   cd /usr/local  


 cd nginx-1.8.0  进入代码目录 
$ ./configure --prefix=/usr/local/nginx   #指定安装目录
$ make
$ make install

.启动
$ /usr/local/nginx/sbin/nginx
重启：
$ /usr/local/nginx/sbin/nginx –s reload

停止：
$ /usr/local/nginx/sbin/nginx –s stop

测试配置文件是否正常：
$ /usr/local/nginx/sbin/nginx –t

强制关闭：
$ pkill nginx

https://www.cnblogs.com/zhanghaoyong/p/7737536.html

安装Nginx过程中，使用make时出现 make： *** 没有规则可以创建“default”需要的目标“build”
先检查编译时显示的内容，看是否有 ***not found，这样我们就可以查清我们缺少的相关依赖包了。
通常会缺少以下几个相关的依赖包
pcre-devel
zlib            zlib-devel
openssl     openssl-devel
 

　　把这几个包安装一下几可以了：

yum install pcre-devel

yum install zlib zlib-devel
yum install openssl openssl-devel
　　也可以用一条命令来代替以上命令：

yum install gcc gcc-c++
yum install pcre-devel zlib zlib-devel openssl openssl-devel

netstat -lnp|grep 88


##-------------------------------------------
1.安装配置,默认安装吧/usr/local/nginx

    yum install gcc gcc-c++
    yum install pcre-devel zlib zlib-devel openssl openssl-devel
    .configure
    make && make install 

2.创建www组以及www用户
    groupadd www
    useradd -g www www -s /sbin/nologin
    chown -R root:root /usr/local/nginx
    vim ./conf/nginx.conf
    user www;

ln -s /usr/local/nginx/sbin/nginx /usr/local/sbin/




# cd /lib/systemd/system/
# vim nginx.service
[Unit]
Description=nginx 
After=network.target 
[Service] 
Type=forking 
ExecStart=/usr/local/nginx/sbin/nginx
ExecReload=/usr/local/nginx/sbin/nginx reload
ExecStop=/usr/local/nginx/sbin/nginx quit
PrivateTmp=true 
[Install] 
WantedBy=multi-user.target

systemctl enable nginx.service
https://blog.csdn.net/stinkstone/article/details/78082748
systemctl start nginx.service    启动nginx

systemctl stop nginx.service    结束nginx

systemctl restart nginx.service    重启nginx