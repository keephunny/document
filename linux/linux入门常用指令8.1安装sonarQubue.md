
### 安装
```
    [root@localhost src]# unzip sonarqube-6.7.7..zip
    [root@localhost src]# mv sonarqube-6.7.4 /usr/local/
    [root@localhost src]# cd /usr/local/
    [root@localhost local]# mv sonarqube-6.7.4 sonarqube
    [root@localhost local]# useradd sonar
    [root@localhost local]# passwd sonar
    [root@localhost local]chown -R sonar:sonar sonarqube/
  
```
### sonar配置
```
    #配置sonar
    [root@localhost sonarqube]# vim conf/sonar.properties 
        sonar.jdbc.url=jdbc:mysql://localhost:3306/sonar?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useConfigs=maxPerformance&useSSL=false
        sonar.jdbc.username=sonar
        sonar.jdbc.password=123456
        sonar.web.host=0.0.0.0
        sonar.web.context=/sonar
        sonar.web.port=9000
    #配置java
    [root@localhost sonarqube]# vim conf/wrapper.conf 
        wrapper.java.command=/usr/local/java/bin/java
```

### mysql配置
```
    mysql> create database sonar character set utf8 collate utf8_general_ci;
    Query OK, 1 row affected (0.02 sec)
    mysql> create user 'sonar' identified by '123456';
    Query OK, 0 rows affected (0.03 sec)
    mysql> grant all on sonar.* to 'sonar'@'%' identified by '123456';
    Query OK, 0 rows affected (0.01 sec)
    mysql> grant all on sonar.* to 'soanr'@'localhost' identified by '123456';
    Query OK, 0 rows affected (0.00 sec)
    mysql> flush privileges;
    Query OK, 0 rows affected (0.02 sec)
```
### 启动
不能用root用户启动
```
    [root@localhost src]# useradd sonar
    [root@localhost src]# passwd sonar    
    [root@localhost src]# chown -R sonar /usr/local/sonarqube/
    [root@localhost src]# su sonar
    [root@localhost src]$ /usr/local/sonarqube/bin/linux-x86-64/sonar.sh start
    [root@localhost src]$ tail -fn100 /usr/local/sonarqube/logs/sonar.log
    #汉化插件可以在线安装或上传到plugin目录中重启
    /usr/local/sonarqube/extensions/plugins/sonar-l10n-zh-plugin-1.19.jar
    [root@localhost src]$ chown -R sonar:sonar sonar-l10n-zh-plugin-1.19.jar
```
### 登录验证
关闭防火墙
http://192.168.41.100:9000/sonar/
默认密码admin admin


### 开机自自动
```
    [root@localhost src]# vi /etc/init.d/sonar
        #!/bin/sh
        
        # rc file for SonarQube 
        # chkconfig: 345 96 10  
        # description:SonarQube system (www.sonarsource.org)  
        ### BEGIN INIT INFO 
        # Provides: sonar  
        # Required-Start:$network 
        # Required-Stop:$network
        # Default-Start: 3 4 5  
        # Default-Stop: 0 1 2 6  
        # Short-Description:SonarQube system (www.sonarsource.org) 
        # Description:SonarQube system (www.sonarsource.org)  
        ### END INIT INFO  
        /usr/local/sonarqube/bin/linux-x86-64/sonar.sh $* 
    [root@localhost src]#chmod +x /etc/init.d/sonar
    [root@localhost src]# chkconfig --add sonar
    [root@localhost src]# service sonar start  
```
### 常见错误 
1. es不能用root用户启动，导到sonar启动失败，删除temp目录，重新用sonar用户启动
    2019.06.15 22:55:12 WARN  app[][o.s.a.p.AbstractProcessMonitor] Process exited with exit value [es]: 1
    不能用root用户启动
    [root@localhost sonarqube]# su sonar
    [sonar@localhost sonarqube]$ ./bin/linux-x86-64/sonar.sh start
    Starting SonarQube...
    Started SonarQube.