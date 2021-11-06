

### 安装组件
```
yum install gcc-c++ -y
yum install cmake -y
yum install openssl-devel -y
yum install libuuid-devel -y

wget http://mosquitto.org/files/source/mosquitto-1.4.10.tar.gz
wget http://c-ares.haxx.se/download/c-ares-1.10.0.tar.gz
wget https://github.com/warmcat/libwebsockets/archive/v1.3-chrome37-firefox30.tar.gz

http://mosquitto.org/files/source/
```

### 安装c-ares
```
[root@localhost ]# tar -zxvf c-ares-1.10.0.tar.gz
[root@localhost ]# cd c-ares-1.10.0
[root@localhost ]# ./configure
[root@localhost ]# make && make install

```

### 安装libwebsockect
```
[root@localhost ]# tar -zxvf libwebsockets-1.3-chrome37-firefox30.tar.gz
[root@localhost ]# mkdir build
[root@localhost ]# cd build
[root@localhost ]# cmake .. -DLIB_SUFFIX=64
[root@localhost ]# make install
[root@localhost ]# 

```

### 安装 mosquitto
```
[root@localhost ]# tar -zxvf mosquitto-1.4.10.tar.gz
[root@localhost ]# cd mosquitto-1.4.10
# 面的WITH_SRV:=yes和WITH_UUID:=yes都用#号注释掉
[root@localhost ]# vim config.mk
[root@localhost ]# make && make install
[root@localhost ]# mv /etc/mosquitto/mosquitto.conf.example /etc/mosquitto/mosquitto.conf
# 添加用户
[root@localhost ]# groupadd mosquitto
[root@localhost ]# useradd -g mosquitto mosquitto
[root@localhost ]# sudo ln -s /usr/local/lib/libmosquitto.so.1 /usr/lib/libmosquitto.so.1
[root@localhost ]# sudo ldconfig
[root@localhost ]# mosquitto -c /etc/mosquitto/mosquitto.conf -d
[root@localhost ]# mosquitto_sub -t hello
# 另一个（发布）窗口输入：
[root@localhost ]# mosquitto_pub -t hello -h localhost -m "hello world"

    -h：指定要连接的MQTT服务器 
　  -t：订阅主题，此处为mqtt 
　  -v：打印更多的调试信息
    -m：指定消息内容
    -u：用户名
    -P：密码
    -i：客户端id，唯一

```

### 配置
```
# 消息持久存储
persistence true
persistence_location /var/lib/mosquitto/

# 日志文件
log_dest file /var/log/mosquitto/mosquitto.log

# 其他配置
include_dir /etc/mosquitto/conf.d

# 禁止匿名访问
allow_anonymous false
# 认证配置
password_file /etc/mosquitto/pwfile
# 权限配置
acl_file /etc/mosquitto/aclfile

```