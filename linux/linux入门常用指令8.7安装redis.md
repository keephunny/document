### 安装文件
redis-4.0.9.tar.gz

### 安装redis
```
[root@localhost src] yum install gcc-c++
[root@localhost src]# cd redis-4.0.9
[root@localhost redis-4.0.9]# make
[root@localhost redis-4.0.9]# cd src
[root@localhost src]# make install PREFIX=/usr/local/redis
[root@localhost src]# cd ../
[root@localhost redis-4.0.9]# cp redis.conf /usr/local/redis/
#修改配置
[root@localhost redis-4.0.9] vim /usr/local/redis/redis.conf
    将daemonize的值改为yes，后台启动
    #取消限制访问
    #bind 127.0.0.1
    #指定端口
    port 6379
    #no 外部网络可以直接访问  yes 保护模式，需配置bind ip或者设置访问密码
    protected-mode no
    #配置密码
    requirepass foobared
    
    #开启持久化 生产系统需要调优配置
    appendonly yes
    #appendfsync always 每次操作都会持久化
    #每秒持久化一次
    appendfsync everysec
	#appendfsync no 不主动持久化，默认30s一次

[root@localhost redis-4.0.9] bin/redis-server ./redis.conf &
[root@localhost redis-4.0.9] bin/redis-cli shutdown
[root@localhost redis-4.0.9] 
[root@localhost redis-4.0.9] 
```



### 配置密码
#### 配置文件设置密码
```
[root@localhost redis-4.0.9] vim /usr/local/redis/redis.conf
    #配置密码 重启生效
    requirepass 123456
```
#### 命令行设置密码
重启后失效
```
[root@localhost redis-4.0.9] bin/redis-cli
    127.0.0.1:6379> config get requirepass
    1) "requirepass"
    2) ""
    127.0.0.1:6379> config set requirepass 123456
    OK
```
#### 密码验证
```
[root@localhost redis]# bin/redis-cli -h 127.0.0.1 -p 6379 -a 123456
    127.0.0.1:6379> auth 123456
        OK
        127.0.0.1:6379> auth 123
        (error) ERR invalid password

```

### 开机自启

```
[root@localhost bin]# vi /etc/systemd/system/redis.service

  [Unit]
  Description=redis-server
  After=network.target

  [Service]
  Type=forking
  ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
  PrivateTmp=true

  [Install]
  WantedBy=multi-user.target

[root@localhost bin]# systemctl daemon-reload
[root@localhost bin]# systemctl start redis

```







```
#创建脚本文件
[root@localhost redis]# vim /etc/init.d/redis

    #!/bin/bash
    #chkconfig: 22345 10 90
    #description: Start and Stop redis

    REDISPORT=6379
    EXEC=/usr/local/redis/bin/redis-server
    CLIEXEC=/usr/local/redis/bin/redis-cli

    #查看 redis.conf  pidfile属性
    PIDFILE=/var/run/redis_6379.pid
    CONF="/usr/local/redis/redis.conf"
    case "$1" in
        start)
            if [ -f $PIDFILE ];then
                echo "$PIDFILE exists,process is already running or crashed"
            else
                echo "Starting Redis server..."
                $EXEC $CONF
            fi
            ;;
        stop)
            if [ ! -f $PIDFILE ];then
                echo "$PIDFILE does not exist,process is not running"
            else
                PID=$(cat $PIDFILE)
                echo "Stopping..."
                $CLIEXEC -p $REDISPORT shutdown
                while [ -x /proc/${PID} ]
                    do
                        echo "Waiting for Redis to shutdown..."
                        sleep 1
                    done
                    echo "Redis stopped"
            fi
            ;;
        restart)
            "$0" stop
            sleep 3
            "$0" start
            ;;
        *)
            echo "Please use start or stop or restart as first argument"
            ;;
    esac

[root@localhost redis]# chmod +x /etc/init.d/redis
[root@localhost redis]# #chkconfig --add redis
[root@localhost redis]# #chkconfig redis on
[root@localhost redis]# chkconfig --list 
[root@localhost redis]# service redis start
[root@localhost redis]# service redis stop
[root@localhost redis]# service redis restart
```

#### 只安装redis-cli

```
wget http://download.redis.io/redis-stable.tar.gz
tar xvzf redis-stable.tar.gz
cd redis-stable
make redis-cli
sudo cp src/redis-cli /usr/local/bin/
```



```
redis-cli [OPTIONS] [cmd [arg [arg ...]]]
-h <主机ip>，默认是127.0.0.1
-p <端口>，默认是6379
-c 指定是集群模式连接
-a <密码>，如果redis加锁，需要传递密码
--help，显示帮助信息

./redis-cli -h 172.0.0.1 -p 6379 -c -a xxxxx
```





### 常用命令

* redis-cli -h host -p port -a password
* redis-cli --raw 可以避免中文乱码
* redis-benchmark：redis性能测试工具
* redis-check-aof：检查aof日志的工具
* redis-check-dump：检查rdb日志的工具
* redis-cli：连接用的客户端
* redis-server：redis服务进程
* /usr/local/redis/bin/redis-cli shutdown
* pkill redis-server


### 常用配置
* bind
    默认绑定接口是 127.0.0.1，也就是本地回环接口，所以是无法从外网连接 redis 服务的。如果想要让外网也能连接使用服务器上的 redis 服务，可以简单地注释掉 bind 这一行。
    bind ip1 ip2
        ip1 ip2 ip3 都是本机所属的ip地址，但ip4如果是你随便乱写的ip，根本不是本机的ip，不好意思，会直接报错，redis都起不来
        只要通过这个网卡地址
        
    如果我们想限制只有指定的主机可以连接到redis中，我们只能通过防火墙来控制，而不能通过redis中的bind参数来限制。
    bind的意思不是绑定外部服务器的IP，而是绑定本机可以接受访问的IP

    bind 127.0.0.1
    bind 0.0.0.0  最特殊的一个IP地址，代表的是本机所有ip地址，不管你有多少个网口，多少个ip，如果监听本机的0.0.0.0上的端口，就等于监听机器上的所有IP端口。等价于 不配置 bind

* protected-mode
    1、关闭protected-mode模式，此时外部网络可以直接访问
    2、开启protected-mode保护模式，需配置bind ip或者设置访问密码
* daemonize：如需要在后台运行，把该项的值改为yes
* pdifile：把pid文件放在/var/run/redis.pid，可以配置到其他地址
* port：监听端口，默认为6379
* timeout：设置客户端连接时的超时时间，单位为秒
* loglevel：等级分为4级，debug，revbose，notice和warning。生产环境下一般开启notice
* logfile：配置log文件地址，默认使用标准输出，即打印在命令行终端的端口上
* database：设置数据库的个数，默认使用的数据库是0
* save：设置redis进行数据库镜像的频率
* rdbcompression：在进行镜像备份时，是否进行压缩
* dbfilename：镜像备份文件的文件名
* dir：数据库镜像备份的文件放置的路径
* slaveof：设置该数据库为其他数据库的从数据库
* masterauth：当主数据库连接需要密码验证时，在这里设定
* requirepass：设置客户端连接后进行任何其他指定前需要使用的密码
* maxclients：限制同时连接的客户端数量
* maxmemory：设置redis能够使用的最大内存
* appendonly：开启appendonly模式后，redis会把每一次所接收到的写操作都追加到appendonly.aof文件中，当redis重新启动时，会从该文件恢复出之前的状态
* appendfsync：设置appendonly.aof文件进行同步的频率
* vm_enabled：是否开启虚拟内存支持
* vm_swap_file：设置虚拟内存的交换文件的路径
* vm_max_momery：设置开启虚拟内存后，redis将使用的最大物理内存的大小，默认为0
* vm_page_size：设置虚拟内存页的大小
* vm_pages：设置交换文件的总的page数量
* vm_max_thrrads：设置vm IO同时使用的线程数量


### 卸载redis
* rm -rf /usr/local/redis //删除安装目录
* rm -rf /usr/bin/redis-* //删除所有redis相关命令脚本
* rm -rf /root/download/redis-4.0.4 //删除redis解压文件夹


### 常见错误
```
    Could not connect to Redis at 192.168.26.103:6379: Connection refused
    注释ip绑定
    #bind 127.0.0.1

```