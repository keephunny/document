


### 程序目录
```
[root@10 xx-webservice]# tree
├── bin
│   ├── app.pid
│   ├── err.log
│   ├── service.sh
├── conf
│   ├── application.yml
│   ├── logback.xml
│   └── mapper
│       ├── ReportCityMapper.xml
│       ├── ReportSiteMapper.xml
├── libs
│   ├── mapper-3.5.3.jar
│   ├── mapper-spring-boot-autoconfigure-1.2.4.jar
│   ├── mapper-spring-boot-starter-1.2.4.jar
│   └── xmlschema-core-2.2.2.jar
├── logs
```
### 启动脚本
```
#!/bin/bash 
# chkconfig: 2345 99  90
# description: xx-web-server
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
    nohup  $JAVA $JAVA_OPTS  -classpath .:$CLASSPATH com.xx.xx.web.Application >/dev/null  2>$base/bin/err.log & echo $! > $base/bin/app.pid

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

