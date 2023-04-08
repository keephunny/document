nc是netcat的简写，是一个功能强大的网络工具，有着网络界的瑞士军刀美誉。nc命令在linux系统中实际命令是ncat，nc是软连接到ncat。nc命令的主要作用如下：
* 实现任意TCP/UDP端口的侦听，nc可以作为server以TCP或UDP方式侦听指定端口
* 端口的扫描，nc可以作为client发起TCP或UDP连接
* 机器之间传输文件
* 机器之间网络测速

安装方式：
yum install -y nc


### 常用参数

* -l：用于指定nc将处于侦听模式。指定该参数，则意味着nc被当作server
* -s：指定发送数据的源IP地址，适用于多网卡机
* -u：指定nc使用UDP协议，默认为TCP
* -v：输出交互或出错信息，新手调试时尤为有用
* -w：超时秒数，后面跟数字
* -z：表示zero，表示扫描时不发送任何数据


### 常用示例
```
#启动TCP 20000端口
[root@localhost ~]# nc -lv 192.168.1.1 20000
#启动UDP 20000端口
[root@localhost ~]# nc -lvu 192.168.1.1 20000

[root@localhost ~]# netstat -tunlp|grep 20000
  udp        0      0 192.168.1.1:20000     0.0.0.0:*                           9104/nc 

#连接UDP 20000端口
[root@localhost ~]# nc -uvz 192.168.1.1  20000


#监听端口数据并写入文件 
[root@localhost ~]# nc -l 192.168.1.1  20000 >>20000.log 
#发送文件
[root@localhost ~]# nc -v 192.168.1.1  20000 <xxxxx.log 
  Ncat: Connected to 192.168.1.119:20000.
  Ncat: 54099 bytes sent, 0 bytes received in 0.02 seconds.


```