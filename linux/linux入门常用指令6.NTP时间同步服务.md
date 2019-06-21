### 安装ntp服务
```
    [root@localhost src]# yum -y install ntp ntpdate
    #手动同下时间并写入硬件
    [root@localhost src]# ntpdate ntp.ntsc.ac.cn && hwclock -w

```
### 搭建本地ntp服务
```
    [root@localhost src]# cp /etc/ntp.conf /etc/ntp.conf.bak
    #配置参数
    [root@localhost src]# vim /etc/ntp.conf
        restrict 127.0.0.1
        restrict ::1
        #只允许192.168.0.0网段的客户机进行时间同步。如果允许任何IP的客户机都可以进行时间同步，就修改为"restrict default nomodify"
        #restrict 10.0.0.0 mask 255.255.0.0 nomodify
        restrict default nomodify
        server time.windows.com
        server ntp.ntsc.ac.cn
        server ntp1.aliyun.com

    #启动或重启服务
    [root@localhost src]# service ntpd start
    #查看服务端口
    [root@localhost src]# netstat -tulnp | grep ntp
    #开机自启动服务
    [root@localhost src]# chkconfig ntpd on
    #查看版本
    [root@localhost src]# ntpq -c version
        
        
        restrict default kod nomodify notrap nopeer noquery     #<==对默认的client拒绝所有操作
        restrict -6 default kod nomodify notrap nopeer noquery
        restrict 127.0.0.1                                      #<==允许本机的一切操作
        restrict 192.168.1.0 mask 255.255.255.0 nomodify    #<==允许局域网内所有client连接到这台服务器
        restrict -6 ::1                                         同步时间.但是拒绝让他们修改服务器上的时间
```
restrict安全相关参数
* ignore　：关闭所有的 NTP 联机服务 
* nomodify：客户端不能更改服务端的时间参数，但是客户端可以通过服务端进行网络校时。 
* notrust ：客户端除非通过认证，否则该客户端来源将被视为不信任子网 
* noquery ：不提供客户端的时间查询 


### 定时更新本地ntp时间
```
    #设置定时任务
    [root@localhost src]# crontab -e
        #每天两点时
        0 2 * * * ntpdate ntp.ntsc.ac.cn && hwclock -w
        #每小时
        * */1 * * *  ntpdate ntp.ntsc.ac.cn && hwclock -w
    #查看定时任务
    [root@localhost src]# crontab -l
    #重启服务
    [root@localhost src]# service crond restart
```

### 检测服务是否可用
```
    #查看服务端口
    [root@localhost src]# netstat -tulnp | grep ntp
    #查看服务进程
    [root@localhost src]# ps -axu | grep ntp
    #在其它机器上检测本机ntp服务
    [root@localhost src]# ntpdate -q 本机IP
        server 10.0.7.163, stratum 2, offset 7.578599, delay 0.04713
        server 10.0.7.161, stratum 2, offset 7.508745, delay 0.04659
        5 Jun 17:08:58 ntpdate[15558]: step time server 10.0.7.161 offset 7.508745 sec
```
### 查看现有连接客户端
```
    [root@localhost src]# watch ntpq -p
             remote           refid	 st t when poll reach   delay   offset  jitter
            ==============================================================================
            +10.0.7.108 128.138.141.172  2 u  451  512  377  109.453   -2.267   2.347
            +10.0.7.163   210.72.145.18    2 u  480  512  377   18.934    0.310   2.449
            *10.0.7.20   10.137.53.7	  2 u  104  512  377   20.808    1.407   0.294
```
* remote：本地机器连接的远程NTP服务器。
* refid：给远程服务器提供时间同步的服务器。
* st：远程服务器的层级别（stratum）。由于NTP是层型结构，有顶端的服务器，多层的Relay Server再到客户端，所以服务器从高到低级别可以设定为1-16。
* t：本地NTP服务器与远程NTP服务器的通信方式，u：单播；b：广播；i：本地
* when：本地机器和远程服务器进行一次时间同步的剩余时间。
* poll：本地机和远程服务器多少时间进行一次同步（单位为秒）
* reach：测试能够和服务器连接，每成功一次它的值就会增加
* delay：从本地机发送同步要求到服务器的round trip time
* offset：本地机和服务器之间的时间差别。Offset接近0，就和时间服务器的时间越接近。
* jitter：统计了再特定个连续的连接数里offset的分布情况。
* +：它将作为辅助的NTP   Server和带有*号的服务器一起为我们提供同步服务. 当*号服务器不可用时它就可以接管
* *：远端的服务器已经被确认为我们的主NTP Server,我们系统的时间将由这台机器所提供
* -：远程服务器被clustering   algorithm认为是不合格的NTP   Server
* x：远程服务器不可用


### 常用ntp服务地址
* time.windows.com
* ntp.ntsc.ac.cn 
* time1.aliyun.com
* time2.aliyun.com
* time3.aliyun.com