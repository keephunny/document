 ### 安装jenkins rpm包
 ```   
    [root@localhost src]# rpm -ivh jenkins-2.121.3-1.1.noarch.rpm 
    警告：jenkins-2.121.3-1.1.noarch.rpm: 头V4 DSA/SHA1 Signature, 密钥 ID d50582e6: NOKEY
    准备中...                          ################################# [100%]
    正在升级/安装...
    1:jenkins-2.121.3-1.1              ################################# [100%]

    [root@localhost src]# service jenkins start
        Starting jenkins (via systemctl):  Warning: Unit file of jenkins.service changed on disk, 'systemctl daemon-reload' recommended.
                                                            [  确定  ]
    #开机启动
    [root@localhost src]# chkconfig jenkins on

    #WAR包 
    /usr/lib/jenkins/jenkins.war
    #配置文件  
    /etc/sysconfig/jenkins
    #默认的JENKINS_HOME目录
    /var/lib/jenkins/
    #Jenkins日志文件   
    /var/log/jenkins/jenkins.log
```
### 配置jenkins
![](./resources/20190618135321.png)
```
[root@localhost ~]# cat /var/lib/jenkins/secrets/initialAdminPassword 
    57607faaa0f64920b634d1605b88adb7
```
修改端口号 
[root@localhost src]# vim /etc/sysconfig/jenkins 
JENKINS_PORT="8081"
配置jdk
配置maven
配置git


### jenkins插件地址
[https://plugins.jenkins.io/](https://plugins.jenkins.io/)


### error
Starting Jenkins bash: /usr/bin/java: 没有那个文件或目录
[root@localhost ~]# service jenkins start
Starting jenkins (via systemctl):  Job for jenkins.service failed. See 'systemctl status jenkins.service' and 'journalctl -xn' for details.
                                                           [失败]
[root@localhost ~]# systemctl status jenkins.service
jenkins.service - LSB: Jenkins Automation Server
   Loaded: loaded (/etc/rc.d/init.d/jenkins)
   Active: failed (Result: exit-code) since 二 2019-06-18 21:46:06 CST; 10s ago
  Process: 2606 ExecStart=/etc/rc.d/init.d/jenkins start (code=exited, status=1/FAILURE)

6月 18 21:46:06 localhost.localdomain systemd[1]: Starting LSB: Jenkins Automation Server...
6月 18 21:46:06 localhost.localdomain runuser[2607]: pam_unix(runuser:session): session opened for user jenkins by (uid=0)
6月 18 21:46:06 localhost.localdomain jenkins[2606]: Starting Jenkins bash: /usr/bin/java: 没有那个文件或目录
解决 
```
#修改java路径
[root@localhost ~]# vim /etc/init.d/jenkins 
    candidates="
    /usr/local/java/bin/java
    /etc/alternatives/java
    /usr/lib/jvm/java-1.8.0/bin/java
    /usr/lib/jvm/jre-1.8.0/bin/java
    /usr/lib/jvm/java-1.7.0/bin/java
    /usr/lib/jvm/jre-1.7.0/bin/java
    /usr/bin/java
    "
```
