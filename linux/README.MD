linux安装jdk
下载jdk 
/usr/java/jdk1.8.0_144/bin/java
 vi /etc/profile


export JAVA_HOME=/usr/java/jdk1.8.0_144
export JRE_HOME=$JAVA_HOME/jre
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=./:$JAVA_HOME/lib:$JAVA_HOME/jre/lib

source /etc/profile


安装telnet
yum install telnet

centos7修改hostname
vi /etc/sysconfig/network

hostnamectl set-hostname xxx 
查看
hostname 


修改时间
1.  安装ntpdate工具
# yum -y install ntp ntpdate
2.  设置系统时间与网络时间同步
# ntpdate cn.pool.ntp.org
3.  将系统时间写入硬件时间
# hwclock --systohc
查看系统时间的命令： #date
设置系统时间的命令： #date –set（月/日/年 时：分：秒）
例：#date –set “10/11/10 10:15”
查看硬件时间的命令： # hwclock
设置硬件时间的命令： # hwclock –set –date = （月/日/年 时：分：秒）