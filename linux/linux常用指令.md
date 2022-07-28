#### Too many open files

java.io.IOException: Too many open files

too many open files是Linux系统中常见的错误，从字面意思上看就是说程序打开的文件数过多，不过这里的files不单是文件的意思，也包括打开的通讯链接(比如socket)，正在监听的端口等等，所以有时候也可以叫做句柄(handle)，这个错误通常也可以叫做句柄数超出系统限制。

```
#查看当前系统设置的最大句柄数是多少
[root@localhost ]# ulimit -a
core file size          (blocks, -c) 0
data seg size           (kbytes, -d) unlimited
scheduling priority             (-e) 0
file size               (blocks, -f) unlimited
pending signals                 (-i) 31155
max locked memory       (kbytes, -l) 64
max memory size         (kbytes, -m) unlimited
open files                      (-n) 102048
pipe size            (512 bytes, -p) 8
POSIX message queues     (bytes, -q) 819200
real-time priority              (-r) 0
stack size              (kbytes, -s) 8192
cpu time               (seconds, -t) unlimited
max user processes              (-u) 31155
virtual memory          (kbytes, -v) unlimited
file locks                      (-x) unlimited

```



查看进程打开文件数

```
lsof -p 进程id |wc -l
lsof -p 1 > openfile.log
```



#### 修改句柄数

```
# 将句柄设置为65535 临时 重启失效
ulimit -n 65535
```



```
[root@localhost ]# vim /etc/security/limits.conf
    *       soft    nofile  65535
    *       hard    nofile  65535
[root@localhost ]# vim /etc/sysctl.conf
	fs.file-max=65535
	
[root@localhost ]# 	sysctl -p

```



查看进程对应的句柄数

[root@xxxxx]# lsof -n|awk '{print $2}'|sort|uniq -c|sort -nr|more|grep '333'
   6619 36576

查看对应的连接

[root@xxxxx]# netstat -lnaop|grep 3333|wc -l
17

查看进程对应线程数
[root@xxxxx]# cat /proc/3333/status|grep 'Threads'
Threads:    33
