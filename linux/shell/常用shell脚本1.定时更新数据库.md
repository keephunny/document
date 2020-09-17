```
    #!/bin/sh`
    #crontab -e 定时任务执行 service crond restart
    #10 0 * * * /opt/application/shell/autobak.sh
    #（EOF 是mysql开始的符号）


    #!/bin/sh
    #（EOF 是mysql开始的符号）


    table_current=$(date "+%Y%m")
    table_prev=$(date -d "-1 month" "+%Y%m")
    date=$(date "+%d")

    echo $table_current
    echo $table_prev
    echo $date


    #修改表名
    funRename(){
        mysql -uroot -p123456 <<EOF
        use dbname;
        alter table test$table_prev rename to test$table_current;
    EOF
    }



    #每月月初修改表名
    if test $date -eq 1
    then
    echo '月初'
    funRename
    fi


    mysql -uroot -p123456 <<EOF
            use dbname;
            #每天更新数据，省得造数据。保证系统每天都有数据
            update test set  insert_time=DATE_ADD(alarm_time,INTERVAL 1 day);

    EOF`

```



```

#!/bin/sh
time2=$(date "+%Y-%m-%d %H:%M:%S")
echo $time2  >> mysql.log

result=`mysql -uroot -p123456 <<EOF
	show status like '%connect%';
quit
EOF`
echo $result >> mysql.log


```

### 定时检测mysql是否异常，并重启
这里要注意，不能用service mysqld/mysql start 命令来启动，会导致mysql启动不了，应使用绝对路径，/etc/init.d/mysqld start来启动。
```


#以下方法验证通过
#!/bin/bash

# */5 * * * * /home/listener/mysql_listener.sh 
time2=$(date "+%Y-%m-%d %H:%M:%S")
pgrep -x mysqld &> /dev/null
#echo $time2 >> /usr/local/application/xshell/logs/mysql_listener.log
str=$(pgrep -x mysqld)

if [  "$str" =  ""  ]
then
echo 'is stop'
echo "$time mysql is stop" >> /usr/local/application/xshell/logs/mysql_listener.log
echo "$time mysql is stop"  >> /usr/local/application/xshell/logs/mysql.log
#service mysql start
/etc/init.d/mysql start
else
echo 'is start'
echo "$time2 mysql running" >> /usr/local/application/xshell/logs/mysql_listener.log
fi

#-----验证不通过--------------------
#!/bin/bash

# */5 * * * * /home/listener/mysql_listener.sh 

pgrep mysqld &> /dev/null
time2=$(date "+%Y-%m-%d %H:%M:%S")
if [ $? -gt 0 ]
then
echo "$time mysql is stop" >> /usr/local/application/xshell/logs/mysql_listener.log
echo "$time mysql is stop"  >> /usr/local/application/xshell/logs/mysql.log

#service mysql start
/etc/init.d/mysql start
else
echo "$time2 mysql running" >> /usr/local/application/xshell/logs/mysql_listener.log
fi
#-----验证不通过--------------------


```



### 定时监控mysql状态
```
#!/bin/sh
# 注意 EOF` 标点符号
# */1 * * * * /home/listener/mysql_listener.sh 
time2=$(date "+%Y-%m-%d %H:%M:%S")
echo $time2  >> mysql.log

result=`mysql -uroot -pData@123456 <<EOF
        show status like '%connect%';
quit
EOF`
echo $result >> mysql.log



```

### 定时监控java应用程序
```
#!/bin/sh
#应用程序监控
#jx_pid=$(ps -ef | grep "web-server" | grep -v grep | awk '{print $2}')
jx_pid=$(ps -ef | grep 'web-server' | grep 'java' | grep -v grep | awk '{print $2}')

if [  "$jx_pid" =  ""  ]
then
	echo "$time2 web-server  is stop"  >> /usr/local/application/xshell/logs/web-server.log
	/usr/local/application/web-server/bin/service.sh start
else
	echo "$time2 web-server is start"  >> /usr/local/application/xshell/logs/web-server.log
fi


jx_pid=$(ps -ef | grep 'webservice' | grep 'java' | grep -v grep | awk '{print $2}')

if [  "$jx_pid" =  ""  ]
then
        echo "$time2 webservice  is stop"  >> /usr/local/application/xshell/logs/jx-webservice.log
        /usr/local/application/webservice/bin/service.sh start
else
	echo "$time2 webservice is start" >> /usr/local/application/xshell/logs/webservice.log
fi


```


### springboot启动脚本
```
#!/bin/bash 
# chkconfig: 2345 99  90
# description: web-server
# java命令安装路径进行修改  因为开机启动找不到JAVA=$(which java)
JAVA=/usr/local/java/bin/java
#JAVA=$(which java)

case "`uname`" in
    Linux)
		bin_abs_path=$(readlink -f $(dirname $0))
		;;
	*)
		bin_abs_path=`cd $(dirname $0); pwd`
		;;
esac
# 当前路径
base=${bin_abs_path}/..
# 删除错误日志
rm -rf $base/bin/err.log

funStart(){
    logback_configurationFile=$base/conf/logback.xml
    springconfig=$base/conf/application.yml
    JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:MaxPermSize=128m "
    JAVA_OPTS=" $JAVA_OPTS -Dfile.encoding=UTF-8 -Dlogback.configurationFile=$logback_configurationFile -Dspring.config.location=$springconfig -Ddruid.registerToSysProperty=true"


    for i in $base/lib/*;
        do CLASSPATH=$i:"$CLASSPATH";
        done
        CLASSPATH="$base/conf:$CLASSPATH";

    cd $bin_abs_path

    /usr/bin/nohup  $JAVA $JAVA_OPTS  -classpath .:$CLASSPATH com.Application >/dev/null  2>$base/bin/err.log & echo $! > $base/bin/app.pid

    PIDS=$(cat "$base/bin/app.pid")
    echo "PID: $PIDS"
    echo "启动成功"
}
# 安全停止
funStop(){
    curl  -X POST http://127.0.0.1:8080/shutdown
}
# 强制停止
funKill(){
    pidfile=$base/bin/app.pid
    if [ ! -f "$pidfile" ];then
        echo "appication is not running. exists"
        exit
    fi
    pid=`cat $pidfile`
    echo -e "`hostname`: stopping application $pid ... "
    kill $pid
}

case "$1" in
    # 启动
    start)
        funStart
     ;;

    # 停止
    stop)
        #//TODO 安全退出
        funStop
    ;;
    # 强制停止
    kill)
        #//TODO 安全退出
        funKill
    ;;

    # 重启
    restart)
        funStop
        funStart
    ;;
    # 未输入参数
    *)
        echo "please input parameter start,stop,restart,kill"
    ;;
esac



```