
### 自定义启动脚本

```
[root@localhost init.d]# cd /etc/rc.d/init.d/
[root@localhost init.d]# vim autostart.sh
    #!/bin/bash
    #chkconfig: 2345 10 90
    #description: xxxx
    /opt/application/bin/service.sh start   >/dev/null  2>/etc/rc.d/init.d/err.log

#启动脚本一定要写绝对路径
[root@localhost init.d]# chmod +x autostart.sh
[root@localhost init.d]# chkconfig --add autostart.sh
[root@localhost init.d]# chkconfig autostart.sh on
[root@localhost init.d]# chkconfig --list
[root@localhost init.d]# 

    #在脚本前面这两行是不能少的，否则不能chkconfig命令会报错误。
    #如果运行chkconfig老是报错，如果脚本没有问题，建议直接在rc0.d~rc6.d下面创建到脚本的文件连接来解决，原理都是一样的。
```


### 自定义服务启动

```
[Unit]：服务说明
Description：描述服务
After：描述服务类型

[Service]：服务运行参数设置
Type=forking是后台运行
User=root
ExecStart：启动命令
ExecReload：重启命令
ExecStop：停止服务
PrivateTem=true：表示给服务分配独立的临时空间
全部要求绝对路径

[Install] 服务安装的相关设置，可设置为多用户

```


### windows开机启动

管理工具-任务计划程序