### springboot 1.x
```
	<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
	
	#启用shutdown
	endpoints.shutdown.enabled=true
	#禁用密码验证
	endpoints.shutdown.sensitive=false
	
```
curl -X POST host:port/shutdown
{"message":"Shutting down, bye..."}

### springboot 1.x 安全设置
```
	<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-security</artifactId>
	</dependency>
	
	#开启shutdown的安全验证
	endpoints.shutdown.sensitive=true
	#验证用户名
	security.user.name=admin
	#验证密码
	security.user.password=secret
	#角色
	management.security.role=SUPERUSER
	
	#指定shutdown endpoint的路径
	endpoints.shutdown.path=/custompath
	#也可以统一指定所有endpoints的路径`management.context-path=/manage`
	#指定管理端口和IP
	management.port=8081
	management.address=127.0.0.1
```

### springboot 2.x
```
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>

	
	#启用shutdown
	management:
	  endpoints:
		web:
		  exposure:
			include: shutdown
		#注意下面这个位置！！
	  endpoint:
		shutdown:
		  enabled: true		  
```
	
curl -X POST http://localhost:8080/actuator/shutdown	
{"message":"Shutting down, bye..."}


### 部署为Unix/Linux Service
该方式主要借助官方的spring-boot-maven-plugin创建"Fully executable" jar ，这中jar包内置一个shell脚本，可以方便的将该应用设置为Unix/Linux的系统服务(init.d service),官方对该功能在CentOS和Ubuntu进行了测试，对于OS X和FreeBSD,可能需要自定义。具体步骤如下:
```
	<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
	<configuration>
	  <executable>true</executable>
	</configuration>
	</plugin>
```

2. 设置为系统服务
将你的应用打成jar包，部署到服务器，假设部署路径为/var/app，包名为app.jar，通过如下方式将应该设置为一个系统服务：
sudo ln -s /var/app/app.jar /etc/init.d/app

3. 赋予可执行权限：
chmod u+x app.jar

4. 以系统服务的方式管理
接下来，就可以使用我们熟悉的service foo start|stop|restart来对应用进行启停等管理了
sudo service app start|stop
命令将得到形如Started|Stopped [PID]的结果反馈

默认PID文件路径：/var/run/appname/appname.pid
默认日志文件路径：/var/log/appname.log

在这种方式下，我们还可以使用自定义的.conf文件来变更默认配置，方法如下：

在jar包相同路径下创建一个.conf文件，名称应该与.jar的名称相同，如appname.conf
在其中配置相关变量，如：
JAVA_HOME=/usr/local/jdk
JAVA_OPTS=-Xmx1024M
LOG_FOLDER=/custom/log

安全设置
作为应用服务，安全性是一个不能忽略的问题，如下一些操作可以作为部分基础设置参考：

为服务创建一个独立的用户，同时最好将该用户的shell绑定为/usr/sbin/nologin
赋予最小范围权限：chmod 500 app.jar
阻止修改：sudo chattr +i app.jar
对.conf文件做类似的工作：chmod 400 app.conf,sudo chown root:root app.conf


