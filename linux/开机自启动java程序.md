
### chkconfig启动命令
    cd /etc/init.d/
    vim jx-server
    chmod +x jx-server
        #!/bin/sh
        # chkconfig: 2345 99  90
        # description: jx-server ....

        # web后端服务
        /usr/local/application/server1/bin/service.sh start &
        /usr/local/application/server2/bin/service.sh start &

    #添加启动指令
    chkconfig --add jx-server  
* 0 为停机，机器关闭。（千万不要把initdefault设置为0 ）  
* 1 为单用户模式，就像Win9x下的安全模式类似。  
* 2 为多用户模式，但是没有NFS支持。  
* 3 为完整的多用户模式，是标准的运行级。  
* 4 一般不用，在一些特殊情况下可以用它来做一些事情。例如在笔记本电脑的电池用尽时，可以切换到这个模式来做一些设置。  
* 5 就是X11，进到X Window系统了。  
* 6 为重启，运行init 6机器就会重启。（千万不要把initdefault设置为6 ） 

chkconfig 99 90 启动和关闭优先级
### java程序启动脚本

    #!/bin/bash
    # chkconfig: 2345 99  90
    # description: jx-web-server
    # java命令安装路径进行修改  因为开机启动找不到JAVA=$(which java)
    #JAVA=/usr/local/java/bin/java
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
        JAVA_OPTS="-server -Xms1024m -Xmx2048m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:MaxPermSize=128m "
        JAVA_OPTS=" $JAVA_OPTS -Dfile.encoding=UTF-8 -Dlogback.configurationFile=$logback_configurationFile -Dspring.config.location=$springconfig"


        for i in $base/lib/*;
            do CLASSPATH=$i:"$CLASSPATH";
            done
            CLASSPATH="$base/conf:$CLASSPATH";

        cd $bin_abs_path

        #$JAVA $JAVA_OPTS  -classpath .:$CLASSPATH com.jx.webservice.Application 1>>$base/logs/start.log 2>&1 & echo $! > $base/bin/app.pid
        nohup  $JAVA $JAVA_OPTS  -classpath .:$CLASSPATH com.jx.webservice.Application >/dev/null  2>$base/bin/err.log & echo $! > $base/bin/app.pid

        PIDS=$(cat "$base/bin/app.pid")
        echo "PID: $PIDS"
        echo "启动成功"
    }
    # 安全停止
    funStop(){
        curl  -X POST http://127.0.0.1:8010/shutdown
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

