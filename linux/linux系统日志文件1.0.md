### 系统日志文件

/VAR/LOG/各个日志文件分析   

1. /var/log/messages
包括整体系统信息，其中也包含系统启动期间的日志。此外，mail，cron，daemon，kern和auth等内容也记录在var/log/messages日志中。
1. /var/log/dmesg
包含内核缓冲信息（kernel ring buffer）。在系统启动时，会在屏幕上显示许多与硬件有关的信息。可以用dmesg查看它们。
1. /var/log/auth.log
包含系统授权信息，包括用户登录和使用的权限机制等。
1. /var/log/boot.log
包含系统启动时的日志。
1. /var/log/daemon.log
包含各种系统后台守护进程日志信息。
1. /var/log/dpkg.log
包括安装或dpkg命令清除软件包的日志。
1. /var/log/kern.log
包含内核产生的日志，有助于在定制内核时解决问题。
1. /var/log/lastlog
记录所有用户的最近信息。这不是一个ASCII文件，因此需要用lastlog命令查看内容。
1. /var/log/maillog 
1. /var/log/mail.log
包含来着系统运行电子邮件服务器的日志信息。例如，sendmail日志信息就全部送到这个文件中。
1. /var/log/user.log
记录所有等级用户信息的日志。
1. /var/log/Xorg.x.log
来自X的日志信息。
1. /var/log/alternatives.log
更新替代信息都记录在这个文件中。
1. /var/log/btmp
记录所有失败登录信息。使用last命令可以查看btmp文件。例如，”last -f /var/log/btmp | more“。
1. /var/log/cups
涉及所有打印信息的日志。
1. /var/log/anaconda.log
在安装Linux时，所有安装信息都储存在这个文件中。
1. /var/log/yum.log
包含使用yum安装的软件包信息。
1. /var/log/cron
每当cron进程开始一个工作时，就会将相关信息记录在这个文件中。
1. /var/log/secure
包含验证和授权方面信息。例如，sshd会将所有信息记录（其中包括失败登录）在这里。
1. /var/log/wtmp或/var/log/utmp
包含登录信息。使用wtmp可以找出谁正在登陆进入系统，谁使用命令显示这个文件或信息等。
1. /var/log/faillog
包含用户登录失败信息。此外，错误登录命令也会记录在本文件中。