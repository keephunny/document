

### 下载nginx包
nginx-1.10.3.tar.gz
### 解压
```
[root@localhost src]# tar -zxvf nginx-1.10.3.tar.gz 
[root@localhost src]# cd nginx-1.10.3
[root@localhost nginx-1.10.3]# ./configure 
checking for OS
 + Linux 3.10.0-229.el7.x86_64 x86_64
checking for C compiler ... not found
./configure: error: C compiler cc is not found

```
### 安装gcc编译环境
```
[root@localhost nginx-1.10.3]# yum install gcc-c++
已安装:
  gcc-c++.x86_64 0:4.8.3-9.el7 
作为依赖被安装:
  cpp.x86_64 0:4.8.3-9.el7                 gcc.x86_64 0:4.8.3-9.el7                   glibc-devel.x86_64 0:2.17-78.el7    
  glibc-headers.x86_64 0:2.17-78.el7       kernel-headers.x86_64 0:3.10.0-229.el7     libmpc.x86_64 0:1.0.1-3.el7         
  libstdc++-devel.x86_64 0:4.8.3-9.el7     mpfr.x86_64 0:3.1.1-4.el7      
```

### 安装依赖包
```
如果直接安装会包错
./configure: error: the HTTP rewrite module requires the PCRE library.
[root@localhost nginx-1.10.3]# yum install pcre pcre-devel -y
[root@localhost nginx-1.10.3]# yum install zlib zlib-devel -y
[root@localhost nginx-1.10.3]# yum install openssl openssl-devel -y
```

### 安装nginx
```
#配置检测 指定安装目录
[root@localhost nginx-1.10.3]# ./configure --prefix=/usr/local/nginx
    Configuration summary
    + using system PCRE library
    + OpenSSL library is not used
    + using builtin md5 code
    + sha1 library is not found
    + using system zlib library

    nginx path prefix: "/usr/local/nginx"
    nginx binary file: "/usr/local/nginx/sbin/nginx"
    nginx modules path: "/usr/local/nginx/modules"
    nginx configuration prefix: "/usr/local/nginx/conf"
    nginx configuration file: "/usr/local/nginx/conf/nginx.conf"
    nginx pid file: "/usr/local/nginx/logs/nginx.pid"
    nginx error log file: "/usr/local/nginx/logs/error.log"
    nginx http access log file: "/usr/local/nginx/logs/access.log"
    nginx http client request body temporary files: "client_body_temp"
    nginx http proxy temporary files: "proxy_temp"
    nginx http fastcgi temporary files: "fastcgi_temp"
    nginx http uwsgi temporary files: "uwsgi_temp"
    nginx http scgi temporary files: "scgi_temp"
#编译    
[root@localhost nginx-1.10.3]# make
#安装
[root@localhost nginx-1.10.3]# make install
```
### 启动nginx
```
#启动
[root@localhost /]# /usr/local/nginx/sbin/nginx 
#重启
[root@localhost /]# /usr/local/nginx/sbin/nginx -s reload
#关闭
[root@localhost /]# /usr/local/nginx/sbin/nginx  -s stop
#启动成功
[root@localhost nginx]#  ps -axu|grep nginx
root      17003  0.0  0.0  20432   604 ?        Ss   22:11   0:00 nginx: master process /usr/local/nginx/sbin/nginx

#查看防火墙状态
[root@localhost nginx]# firewall-cmd --state
#关闭防火墙服务
[root@localhost nginx]# systemctl stop firewalld 
```

### 开机启动
```
[root@localhost nginx]# vim /lib/systemd/system/nginx.service
    [Unit]
        Description=nginx
        After=network.target
        
        [Service]
        Type=forking
        ExecStart=/usr/local/nginx/sbin/nginx
        ExecReload=/usr/local/nginx/sbin/nginx -s reload
        ExecStop=/usr/local/nginx/sbin/nginx -s quit
        PrivateTmp=true
        
        [Install]
        WantedBy=multi-user.target

#设置开机启动
[root@localhost nginx]# systemctl enable nginx.service
#停止开机自启动
[root@localhost nginx]# systemctl disable nginx.service
```

### 常用配置
```
 server {
        listen  9093;
        root    "/opt/application/proj-web";
        location /{
           index index.html index.htm;
        }
        #代理
        location /api/{
           proxy_pass   "http://localhost:9092/";
           #获取代理真实ip
           proxy_set_header X-forwarded-for $proxy_add_x_forwarded_for;
           proxy_set_header X-Real-IP  $remote_addr;
        }
        location /file/{
          #虚拟目录
          alias /opt/application/proj-data/;
        }
    }
```    