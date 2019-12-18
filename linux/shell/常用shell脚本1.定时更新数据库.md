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
        use cityll_tongling;
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
            use cityll_tongling;
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