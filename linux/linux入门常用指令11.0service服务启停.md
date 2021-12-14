#### service文件

/etc/systemd/system/xxxx.service

```
[Unit]
Description:描述
After: network.target  #在网络服务启动后再启动
ConditionPathExists:执行条件

[Service]
EnviromentFile:变量所在文件
ExecStart:执行启动脚本
Restart:fail时重启

[Install]
Alias:服务别名
WangtedBy:多用户模式下
```

##### [Unit]配置区块

[Unit]区块通常是配置文件的第一个区块，主要用来定义Unit的元数据，以及配置与其它Unit的关系。

* Description：简短描述
* Documentation：帮助文档
* Requires：依赖到的其它units；强依赖，被依赖的units无法激活时，当前unit即无法启动；
* Wants：期望某units启动，弱依赖；本单元启动了，它“想要”的单元也会被启动。但是这个单元若启动不成功，对本单元没有影响。
* BindsTo：:与 Requires 相似，但是一种更强的关联。启动这个服务时会同时启动列出的所有模块，当有模块启动失败时终止当前服务。反之，只要列出的模块全部启动以后，也会自动启动当前服务。并且这些模块中有任意一个出现意外结束或重启，这个服务也会终止或重启。
* Before：如果该字段指定的 Unit 也要启动，那么必须在当前 Unit 之后启动
* After：定义unit的启动次序，表示当前unit应该晚于哪些unit启动；其功能与Before相反
* Conflicts：与某些unit冲突，某些unit启动时此unit无法启动
* Condition...：当前 Unit 运行必须满足的条件，否则不会运行
* Assert...：当前 Unit 运行必须满足的条件，否则会报启动失败



##### [Service]配置

* User：指定运行服务的用户，会影响服务对本地文件系统的访问权限。

* Group：指定运行服务的用户组，会影响服务对本地文件系统的访问权限。

* Type：用于定义影响ExecStart及相关参数功能的Unit进程启动类型

  * simple：默认，最简单的服务类型，启动程序就是主体程序
  * forking：标准unix daemon启动方式，启动程序后会调用fork()函数，把必要的通信频道设置好之后父进程退出，留下守护子进程。如果使用了这个Type，则ExecStart的脚本启动后会调用fork()函数创建一个进程作为其启动的一部分。当一切初始化完毕后，父进程会退出。子进程会继续作为主进程执行。这是传统UNIX主进程的行为。如果这个设置被指定，建议同时设置PIDFile选项来指定pid文件的路径，以便systemd能够识别主进程。
  * oneshot：一次性进程，systemd会等当前服务退出，再继续往下执行。onesh的行为十分类似simple，但是，在systemd启动之前，进程就会退出。这是一次性的行为。可能还需要设置RemainAfterExit=yes，以便systemd认为j进程退出后仍然处于激活状态。
  * dbus：程序启动时需要获取一块DBus空间，所以需要和BusName一起使用。只有获取了DBus空间，依赖它的程序才会启动。
  * notify：当前服务启动完毕，会通知systemd再继续往下执行
  * idle：若有其它任务执行完毕，当前服务才会执行。
  
* EnviromentFile：环境配置文件

* ExecStart：指定启动unit要运行的命令或脚本

* ExecStartPre：在execStart之前运行

* ExecStartPost：在execStart之后运行

* ExecStop：停止unit要运行的命令或脚本

* Restart：当设定restart=1，则当次daemon服务意外终止后，会再次自动启动

* WorkingDirectory：指定服务的工作目录

* RootDirectory：指定服务进程的根目录，如果配置了该目录，服务将无法访问指定目录以外的文件。

* PIDField：pid文件位置

* SuccessExitStatus=143 ,143是spring-boot服务被stop的时候的status code：  如果不加上SuccessExitStatus=143，stop服务的时候会变成failed状态，而不是inactive状态。

  



##### [Install]启动配置

[Install]通常是配置文件的最后一个区块，用来定义如何启动以及是否开机启动

* WantedBy：当前Unit激活时符号链接会放入/etc/systemd/system/目录下以target名+.wants构成的子录中。常用选项multi-user.target， 表明当系统以多用户方式（默认的运行级别）启动时，这个服务需要被自动运行。当然还需要 systemctl enable 激活这个服务以后自动运行才会生效。被哪个target装载
* RequireBy：被哪些units所依赖。
* Alias：当前Unit可用于启动的别名，可使用systemctl command Alias.service
* Also：当前Unit激活时，会被同时激活的Unit

从Centos7以后采用target概念来定义运行级别，含义如下：

​	Runlevel 0           |    runlevel0.target -> poweroff.target
​	Runlevel 1           |    runlevel1.target -> rescue.target
​	Runlevel 2           |    runlevel2.target -> multi-user.target
​	Runlevel 3           |    runlevel3.target -> multi-user.target
​	Runlevel 4           |    runlevel4.target -> multi-user.target
​	Runlevel 5           |    runlevel5.target -> graphical.target
​	Runlevel 6           |    runlevel6.target -> reboot.target







在CentOS7.0后，不再使用service,而是systemctl 。centos7.0是向下兼容的，也是可以用service.





#### 常用命令

* systemctl start 服务名：开启服务
* systemctl stop 服务名：关闭服务
* systemctl status 服务名：显示状态
* systemctl restart 服务名：重启服务
* systemctl enable 服务名：开机自启服务
* systemctl disable 服务名：禁止开机自启服务
* systemctl list-unit-files：查看系统中所有服务的开机启动状态
* systemctl list-dependencies 服务名：查看服务的依赖关系
* systemctl mask 服务名：冻结服务
* systemctl unmask 服务名：解冻服务
* systemctl set-default multi-user.target：开机时不启动图形界面
* systemctl set-default graphical.target：开机时启动图形界面
* systemctl daemon-reload：重新加载服务配置文件
* systemctl is-active application.service： 显示某个 Unit 是否正在运行
* systemctl is-failed application.service ：显示某个 Unit 是否处于启动失败状态
* systemctl is-enabled application.service ：显示某个 Unit 服务是否建立了启动链接
* systemd-analyze blame ：查看每个服务的启动耗时
* systemctl list-units ：查看当前运行的所有服务 
* systemctl list-unit-files ：查看服务是否开机启动
  * enabled：已建立启动链接;表示允许开机启动
  * disabled：没建立启动链接;表示禁止开机启动
  * static：该配置文件没有`[Install]`部分（无法执行），只能作为其他配置文件的依赖
  * masked：该配置文件被禁止建立启动链接
* journalctl -u myservice.service：查看服务的console log
* journalctl -f -u myservice.service：查看服务的console log



服务状态

* active(running)：服务正在运行
* active(exited)：执行一次就正常退出的服务，不在系统中执行任何程序
* active(waiting)：正在执行中，处于阻塞状态，需要等其它程序执行完才能执行
* inactive(dead)：未启动状态 

vendor preset：表示服务默认的启动态

* inactive：服务关闭
* disable：服务开机不启动
* enabled：服务开机启动
* static：服务开机启动项被管理
* failed：服务配置错误



####  补充异常操作

systemclt 启动服务时，增加结果输出，便于了解当前服务是否启动成功。



#### 常见错误

```
# java变量识别不了
Application is running as root (UID 0). This is considered insecure.
Unable to find Java
# 解决办法
ln -s java变量位置 可以通过 which java找到
ln -s /usr/local/java/bin/java /sbin/java
```

