linux

/etc/hosts.allow
/etc/hosts.deny

```

/var/log/secure
异常登录日志
Jan  2 14:16:17 localhost sshd[2679]: pam_unix(sshd:auth): authentication failure; logname= uid=0 euid=0 tty=ssh ruser= rhost=192.168.26.1  user=root
Jan  2 14:16:17 localhost sshd[2679]: pam_succeed_if(sshd:auth): requirement "uid >= 1000" not met by user "root"
Jan  2 14:16:20 localhost sshd[2679]: Failed password for root from 192.168.26.1 port 59723 ssh2
Jan  2 14:16:46 localhost sshd[2679]: Failed password for root from 192.168.26.1 port 59723 ssh2
Jan  2 14:16:48 localhost sshd[2679]: Failed password for root from 192.168.26.1 port 59723 ssh2
Jan  2 14:16:49 localhost sshd[2679]: Failed password for root from 192.168.26.1 port 59723 ssh2
Jan  2 14:16:49 localhost sshd[2679]: Failed password for root from 192.168.26.1 port 59723 ssh2
Jan  2 14:16:49 localhost sshd[2679]: Failed password for root from 192.168.26.1 port 59723 ssh2

配置禁止登录
[root@localhost ~]# vim /etc/hosts.deny 
shd:192.168.26.*
拒绝连接日志
Jan  2 14:19:03 localhost sshd[2726]: refused connect from 192.168.26.1 (192.168.26.1)
Jan  2 14:19:16 localhost sshd[2728]: refused connect from 192.168.26.1 (192.168.26.1)


```
定时任务检测
```
#!/bin/bash
# # 每分钟检测暴力破解

# */1 * * * * /bin/bash /opt/application/shell/denyhosts.sh

#可以直接写入 >>/etc/hosts.deny
cat /var/log/secure|awk '/Failed/{print $(NF-3)}'|sort|uniq -c|awk '{print $2"=" $1;}' >/opt/application/shell/logs/denyhosts.txt

DEFINE=10
#HOSTSFILE='/opt/application/shell/logs//hosts.deny'
HOSTSFILE='/etc/hosts.deny'

for i in `cat /opt/application/shell/logs/denyhosts.txt`
    do
        IP=`echo $i  |awk -F "=" '{print $1}'`
        n=`echo $i |awk -F "=" '{print $2}'`
        if [ ${n} -gt $DEFINE ]; then
            a=`grep $IP $HOSTSFILE`
            if [ -z $a ] ;then
                 echo "sshd:$IP:deny" >>$HOSTSFILE
             fi
    fi
done
~      


```


### linux配置IP访问权限
#### 允许访问
```
[root@localhost /]# vim /etc/hosts.allow
	#添加（可以添加多行,其中“:allow”可以省）
	#表示192.168.81.*  ip段都能ssh访问
	sshd:192.168.81.*:allow     
	#表示允许所有ip 的ssh访问                
	sshd:all:allow            
	#表示允许192.168.81.74ssh访问                          
	sshd:192.168.81.74:allow                 
#重启生效
[root@localhost /]# service xinetd restart
```
 

#### 拒绝访问
```
[root@localhost /]# vi /etc/hosts.deny
	添加（可以添加多行,其中“:deny”可以省率）
	#表示拒绝所有ip访问
	sshd:all:deny      
	#表示拒绝210.13.218.* ip段所有ssh访问	
	sshd:210.13.218.*:deny       
	#表示拒绝192.168.81.74主机ssh访问	
	sshd:192.168.81.74                               
#重启生效
[root@localhost /]# service xinetd restart
```
当hosts.allow 与 hosts.deny配置冲突时，以hosts.allow 为准。



### /var/log/secure
/var/log/secure 一般用来记录安全相关的信息，记录最多的是哪些用户登录服务器的相关日志，如果该文件很大，说明有人在破解你的密码
```
	# 表示root用户关闭了会话（也就是关闭了终端）
	pam_unix(sshd:session): session closed for user root   

	# 表示接受来自14.23.168.10的root用户的公钥登录
	Accepted publickey for root from 14.23.168.10 port 36637 ssh2    

	# 表示给root用户打开一个终端
	pam_unix(sshd:session): session opened for user root by (uid=0)   

	# 表示已经连着的终端主动断开连接，并关闭终端
	Received disconnect from 183.60.122.237: 11: disconnected by user    
	pam_unix(sshd:session): session closed for user root

	# 表示对端使用无效的用户redis来连接
	Invalid user redis from 45.115.45.3 port 33274                           

	# 本机对redis用户进行认证，认证失败，发送错误信号给对端
	input_userauth_request: invalid user redis [preauth]     
					
	# 对端接收到错误信号主动断开连接
	Received disconnect from 45.115.45.3 port 33274:11: Bye Bye [preauth]    

	# 连接关闭
	Disconnected from 45.115.45.3 port 33274 [preauth]                       
```


### 修改secure日志保存时间和转储周期
```
[root@localhost /]# vim /etc/logrotate.conf
	/var/log/secure {
		missingok
		monthly
		minsize 10M
		create 0600 root utmp
		rotate 120
	}

#rorate 120 表示保存每隔120个rorate周期，此时设置的是monthly，即保存120月。

```
logrorate通过crontab定时执行，不是通过crontab -l查看，而是查看vim /etc/cron.daily/logrotate
每天cron执行/usr/sbin/logrotate /etc/logrotate.conf来对日志进行转储分割。


