

#### nc命令

安装 
yum install nc -y
yum install nmap -y


nc是netcat的简写，主要作用如下
* 实现任意TCP/UDP端口的侦听，nc可以作为server以TCP或UDP方式侦听指定端口
* 端口的扫描，nc可以作为client发起TCP或UDP连接
* 机器之间传输文件
* 机器之间网络测速

参数    
* -l：用于指定nc将处于侦听模式，指定该参数则nc被当作server，侦听并接受连接，而非向其它地址发起连接。
* -p <port>:指定端口
* -s：指定发送数据的源IP地址，适用于多网卡。
* -u：指定nc使用UDP协议，默认TCP。
* -v：输出交互或出错信息。
* -w：超时秒数
* -z：表示zero，扫描时不发送任何数据

常用指令
* nc -vuz 127.0.0.1 20005：验证UDP端口
* nc -z -v 192.118.20.95 22 TCP

#### netstat
netstat用于查看tcp、udp的端口和进程相关信息。   
netstat -tunlp|grep 端口    
* -t：仅显示TCP相关
* -u：仅显示UDP相关
* -n：拒绝显示别名，能显示数字的全部转化为数字
* -l：仅列出在监听的服务状态
* -p：显示建立相关链接的程序名

常用指令
* netstat -ntlp：查看当前所有TCP端口
* netstat -nulp：查看当前所有UDP端口

* nc  -ul  9998：启动一个UDP服务端
* nc -vuz 127.0.0.1 9998：客户端验证


