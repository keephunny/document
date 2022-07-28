tcping命令基于tcp协议监控，可以从较低级别的协议获得简单的，可能不可靠的数据报服务。 

```
#! /bin/bash

cd /usr/local/src
wget https://raw.githubusercontent.com/Fsiyuetian/tcping/master/tcping.c
yum install -y gcc
gcc -o tcping tcping.c
cp tcping /usr/bin
tcping baidu.com 80
```

[tcping.c](./resource/tcping.c)



### tcping与ping的区别
* ping：是Windows、Unix和Linux系统下的一个命令。ping也属于一个通信协议，是TCP/IP协议的一部分。通过ICMP协议发送报文到对方主机上任意一个60000以上的端口，然后获取对方主机的回复
* tcping：是一种面向连接的、可靠的、基于字节流的传输层通信协议。使用tcp协议尝试与某一个端口建立连接，然后获取与对方主机建立一次连接的回复



```
tcping [-flags]server-address[sercer-port]
Usage (完整版): 
tcping [-t] [-d] [-i interval] [-n times] [-w ms] [-b n] [-r times] [-s] [-v] [-j] [-js size] [-4] [-6] [-c] [-g count] [-S source_address] [--file] [--tee filename] [-h] [-u] [--post] [--head] [--proxy-port port] [--proxy-server server] [--proxy-credentials username:password] [-f] server-address [server-port]
-t:连续ping直到control-c停止 
-n 5:例如，发送5个ping
-i 5:例如，每5秒ping一次 
-w 0.5:例如，等待0.5秒响应
-d:在每行包含日期和时间
-b 1:启用beeps(1表示on-down, 2表示on-up，  
3代表变化，4代表总是)  
-r 5:例如，每5次ping就重新查找主机名  
-s: ping成功后自动退出  
-v:打印版本并退出 
-j:包含抖动，使用默认滚动平均  
-js 5:包含抖动，滚动平均大小为(例如)5。  
--tee:将输出镜像到'——tee'后面指定的文件名  
--append:附加到——tee文件名，而不是覆盖它  
-4:首选ipv4  
-6:首选ipv6  
-c:只显示更改状态下的输出行  
--file:将"server-address"作为文件名，逐行遍历文件  
注意:——file与-j和-c等选项不兼容，因为它在不同的目标中循环 有选择地接受服务器端口。例如，“example.org 443”有效。  或者，使用-p强制在命令行上对文件中的所有内容使用端口。  例如，如果我们连续失败5次就放弃  

\- s_x_:指定源地址_X_。 源必须是客户端计算机的有效IP。  
-p _X_:指定端口的替代方法  
--fqdn:如果可用，在每行上打印域名  
--ansi:使用ansi颜色序列(cygwin)  
--color:使用窗口颜色序列  
 
HTTP选项:  
-h: HTTP模式(使用url而不使用http:// for server-address)  
-u:每行包含目标URL  
--post:使用post而不是GET(可能会避免缓存)  
--head:使用head而不是GET  
--proxy-server:指定代理服务器  
--proxy-port指定代理端口  
--proxy-credentials:指定'Proxy-Authorization: Basic'头，格式为username:password  
 调试选项:  
-f:强制tcp至少发送一个字节  

--header:包含一个带有原始参数和日期的头文件。 如果使用——tee，则暗示。  

--block:使用“blocking”套接字连接。 这将阻止-w工作，并使用  

默认超时(在我的情况下，只要20秒)。 然而，它可以检测主动  
拒绝连接vs超时。  
如果不通过服务器端口，默认值为80。
```

