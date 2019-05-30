
### 程序目录
[root@10 jx-webservice]# ll
drwxr-xr-x.  2 root root 4096 5月  30 14:51 bin
drwxr-xr-x.  4 root root   70 5月  13 15:53 conf
drwxr-xr-x. 10 root root 4096 5月  13 16:26 libs
drwxr-xr-x. 32 root root 4096 5月  30 00:00 logs

### 启动脚本
```
#!/bin/bash 
# chkconfig: 2345 99  90
# description: jx-web-server
# 指定java命令
JAVA=$(which java)

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
    JAVA_OPTS=" $JAVA_OPTS -Dfile.encoding=UTF-8 -Dlogback.configurationFile=$logback_configurationFile -Dspring.config.location=$springconfig"


    for i in $base/lib/*;
        do CLASSPATH=$i:"$CLASSPATH";
        done
        CLASSPATH="$base/conf:$CLASSPATH";

    cd $bin_abs_path
    nohup  $JAVA $JAVA_OPTS  -classpath .:$CLASSPATH com.gsafety.jx.web.Application >/dev/null  2>$base/bin/err.log & echo $! > $base/bin/app.pid

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

