



service



/etc/init.d/



自启服务

自启脚本



#### chkconfig

chkconfig命令用于检查设置系统的服务

* --add： 增加指定的系统服务，让chkconfig指令管理，并同时在系统启动的叙述文件内增加相关数据

* --del：删除指定服务

* -- level： 指定服务在哪个执行等级中开启或关闭。

* --list：列出 chkconfig 所知道的所有的服务的情况

  chkconfig 服务名 on ：开启服务

  chkconfig 服务名 off：关闭服务



#### 开机自启脚本

/etc/init.d/autoStart.sh

```
#!/bin/bash
#chkconfig: 2345 10 90
#description: xxxx

#/opt/application/api-1.0-SNAPSHOT/bin/service.sh start   >/dev/null  2>/etc/rc.d/init.d/errautoStart.log
```

配置启动项

```
[root@localhost T]# chmod +x autoStart.sh 
[root@localhost T]# chkconfig --add autoStart.sh 
[root@localhost T]# chkconfig --list
[root@localhost T]# reboot
```



#### 开机自启service

/etc/systemd/system/myService.service

```
[Unit]
Description=xxxx服务
After=syslog.target network.target

[Service]
Type=forking
User=root
ExecStart=/opt/application/service-api-1.0-SNAPSHOT/bin/service.sh start
PIDFile=/opt/application/serviceapi-1.0-SNAPSHOT/bin/app.pid
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
Alias=myService
```

配置启动项

```
[root@localhost]# systemctl start myService
[root@localhost]# systemctl status myService
[root@localhost]# systemctl stop myService
#开机自启
[root@localhost]# systemctl enable myService
#开启取消自启
[root@localhost]# systemctl disable myService
#查看开机自启
[root@localhost]# systemctl list-unit-files |grep myService
	myService.service         enable
```



#### list-unit-files与chkconfig区别

| 任务                 | 旧指令                       | 新指令                             |
| -------------------- | ---------------------------- | ---------------------------------- |
| 使某服务自动启动     | chkconfig –level 3 httpd on  | systemctl enable httpd.service     |
| 使某服务不自动启动   | chkconfig –level 3 httpd off | systemctl disable httpd.service    |
| 检查服务状态         | service httpd status         | systemctl status httpd.service     |
| 显示所有已启动的服务 | chkconfig –list              | systemctl list-units –type=service |
| 启动某服务           | service httpd start          | systemctl start httpd.service      |
| 停止某服务           | service httpd stop           | systemctl stop httpd.service       |
| 重启某服务           | service httpd restart        | systemctl restart httpd.service    |

centos7开机启动程序

```
systemctl 指令实例：  
设置开机启动指令：systemctl enable tomcat  
取消设置：systemctl disable tomcat  
查看设置：systemctl list-unit-files  
```

```
chkconfig 指令实例：  
使用chkconfig时需要注意，/etc/init.d/下有对应的脚本(这里就是tomcat)，且该脚本头部包含如下注释以支持chkconfig，  
注释表示在rc2|3|4|5.d下生成优先级96的启动脚本软链接，另外在rc0|6.d下生成优先级04的停止脚本软链接：  
#chkconfig: 2345 96 04  
  
设置开机启动指令： chkconfig --add tomcat  
取消设置： chkconfig --del tomcat  
查看设置： chkconfig  
```

