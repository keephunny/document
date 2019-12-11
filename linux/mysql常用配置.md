connect_timeout：默认为10S
wait_timeout：默认是8小时，即28800秒
interactive_timeout：默认是8小时，即28800秒
net_read_timeout：默认是30S
net_write_timeout：默认是60S


show status like 'aborted%';
#### Aborted_connects
表示尝试连接到mysql服务器的失败次数，引起原因
1. 用户名密码错误
2. 无权限访问
3. 连接超时
4. 连接信息不正确

#### Aborted_client
表示客户端没有正确关闭连接，而被终止的连接数，引起原因
1. 客户端程序退出前没有调用mysql_close()来关闭mysql连接
2. 客户端休眠时间超过了mysql系统变量wait_timeout和interactive_timeout的值，导致连接被mysql进程终止。
3. 客户端程序在数据传输过程中突然结束。


1. 控制连接最大空闲时长的wait_timeout参数。
2. 对于非交互式连接，类似于jdbc连接，wait_timeout的值继承自服务器端全局变量wait_timeout。
    对于交互式连接，类似于mysql客户单连接，wait_timeout的值继承自服务器端全局变量interactive_timeout。
3. 判断一个连接的空闲时间，可通过show processlist输出中Sleep状态的时间



https://www.cnblogs.com/angryprogrammer/p/6667741.html