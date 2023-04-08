https://www.elastic.co/cn/downloads/past-releases/elasticsearch-7-11-0

elasticsearch-7.10.0-linux-x86_64.tar.gz
kibana-7.10.0-linux-x86_64.tar.gz
elasticsearch-head-master.zip
node-v11.0.0.tar.gz


#### 前置配置
```
[root@localhost]# sysctl -a | grep vm.max_map_count
    vm.max_map_count = 65530
#重启后生效
[root@localhost]# vim /etc/sysctl.conf
    vm.max_map_count=262144
#实时生效
[root@localhost]# sysctl -w vm.max_map_count=262144
```

文件最大句柄数
```
[root@localhost]# vim /etc/security/limits.conf
    * soft nofile 65536
    * hard nofile 65536
    * soft nproc 65536
    * hard nproc 65536
[root@localhost]# ulimit -Hn
#可以打开最大文件描述符的数量
[root@localhost]# ulimit -n 65536
#用户最大可用的进程数
[root@localhost]# ulimit -u 65536
```

添加用户
```
[root@localhost]# adduser es
[root@localhost]# passwd es
添加管理员权限
[root@localhost]# vim /etc/sudoers
    es  ALL=(ALL)       ALL
```

#### 安装es
```
[root@localhost]# chown -R es:es elasticsearch/
[root@localhost]# vim config/elasticsearch.yml
    network.host: 0.0.0.0
    node.name: node-1
    #集群主机列表
    discovery.seed_hosts: ["node-1"]
    # 启动时初始化的参与选主的node，生产环境必填
    cluster.initial_master_nodes: ["node-1"]

    #禁止自动创建索引
    action.auto_create_index: ".security*,.monitoring*,.watches,.triggered_watches,.watcher-history*,.ml*"

    #跨域查询 用于es-head插件 生产可不开
    http.cors.enabled: true
    http.cors.allow-origin: "*"
    http.cors.allow-methods: OPTIONS,HEAD,GET,POST,PUT,DELETE
    http.cors.allow-headers: "X-Requested-With,Content-Type,Content-Length,X-User"
[root@localhost]# vim config/jvm.options
    -Xms1024m
    -Xmx1024m

如果不用es自带的jdk 修改成系统的jdk
[root@localhost]# vim bin/elasticsearch
    JAVA=$(which java)
[root@localhost]# su es
[root@localhost]# bin/elasticsearch -d
[root@localhost]# netstat -tunlp|grep 9200
[root@localhost]# tail -fn100 logs/elasticsearch.log 
```

开机自启
```
[root@localhost]# vim /etc/init.d/elasticsearch
    #!/bin/bash
    #chkconfig: 345 63 37
    #description: elasticsearch
    #processname: elasticsearch

    # 这个目录是你Es所在文件夹的目录
    export ES_HOME=/usr/local/elasticsearch
    case $1 in
    start)
        su es<<!
        cd $ES_HOME
        ./bin/elasticsearch -d -p pid
        exit
    !
        echo "elasticsearch is started"
        ;;
    stop)
        pid=`cat $ES_HOME/pid`
        kill -9 $pid
        echo "elasticsearch is stopped"
        ;;
    restart)
        pid=`cat $ES_HOME/pid`
        kill -9 $pid
        echo "elasticsearch is stopped"
        sleep 1
        su es<<!
        cd $ES_HOME
        ./bin/elasticsearch -d -p pid
        exit
    !
        echo "elasticsearch is started"
        ;;
    *)
        echo "start|stop|restart"
        ;;
    esac
    exit 0
[root@localhost]# chmod 777 /etc/init.d/elasticsearch
# 添加系统服务
[root@localhost]# chkconfig --add elasticsearch
# 删除系统服务
[root@localhost]# chkconfig --del elasticsearch
# 启动服务
[root@localhost]# service elasticsearch start
# 停止服务
[root@localhost]# service elasticsearch stop
# 重启服务
[root@localhost]# service elasticsearch restart
# 开启开机自动启动服务
[root@localhost]# chkconfig elasticsearch on
# 关闭开机自动启动服务
[root@localhost]# chkconfig elasticsearch off
```

验证安装成功

```
[root@localhost]# curl http://192.168.1.1:9200/
    {
      "name" : "server1",
      "cluster_name" : "elasticsearch",
      "cluster_uuid" : "hzbVQTK2S06MtEz4NKCOWg",
      "version" : {
        "number" : "7.11.0",
        "build_flavor" : "default",
        "build_type" : "tar",
        "build_hash" : "8ced7813d6f16d2ef30792e2fcde3e755795ee04",
        "build_date" : "2021-02-08T22:44:01.320463Z",
        "build_snapshot" : false,
        "lucene_version" : "8.7.0",
        "minimum_wire_compatibility_version" : "6.8.0",
        "minimum_index_compatibility_version" : "6.0.0-beta1"
      },
      "tagline" : "You Know, for Search"
    }
```

#### 配置IK插件
https://github.com/medcl/elasticsearch-analysis-ik/releases
```
[root@localhost]# cd /usr/local/elasticsearch/plugins/ik
[root@localhost]# unzip elasticsearch-analysis-ik-7.10.0.zip
[root@localhost]# chonw -R es:es ./ik/
[root@localhost]# service elasticsearch restart
```

#### 安装kibana

```
[root@localhost]# chown -R es:es kibana/
[root@localhost]# vim config/kibana.yml
    #配置端口号
    server.port: 5601
    #配置网络访问地址
    server.host: "0.0.0.0"
    elasticsearch.hosts: ["http://127.0.0.1:9200"]
    #配置中文语言界面
    i18n.locale: "zh-CN"
[root@localhost]# bin/kibana
[root@localhost]# mkdir logs
[root@localhost]# nohup bin/kibana > logs/kibana-start.log 2>&1 &
[root@localhost]# netstat -tunlp|grep 5601
 

[root@localhost]# curl http://127.0.0.1:5601

```

开机自启
```
[root@localhost]# vim /etc/init.d/kibana
    #!/bin/bash
    # chkconfig: 2345 98 02
    # description:  kibana

    KIBANA_HOME=/usr/local/kibana
    case $1 in
     start)
        nohup $KIBANA_HOME/bin/kibana  --allow-root >>$KIBANA_HOME/logs/kibana-start.log  2>&1 &
        echo "kibana start"
        ;;
     stop)
        # 这里主要是通过网络端口5601寻找kibana进程的pid
        kibana_pid_str=`netstat -tlnp |grep 5601 | awk '{print $7}'`
        kibana_pid=`echo ${kibana_pid_str%%/*}`
        kill -9 $kibana_pid
        echo "kibana stopped"
        ;;
     restart)
        kibana_pid_str=`netstat -tlnp |grep 5601 | awk '{print $7}'`
        kibana_pid=${kibana_pid_str%%/*}
        kibana_pid=`echo ${kibana_pid_str%%/*}`
        kill -9 $kibana_pid
        echo "kibana stopped"

        nohup $KIBANA_HOME/bin/kibana  --allow-root >>$KIBANA_HOME/logs/kibana-start.log  2>&1 &
        echo "kibana start"
        ;;
     status)
        kibana_pid_str=`netstat -tlnp |grep 5601 | awk '{print $7}'`
        if test -z $kibana_pid_str; then
           echo "kibana is stopped"
        else
           pid=`echo ${kibana_pid_str%%/*}`
           echo "kibana is started,pid:"${pid}
        fi
        ;;
    *)
        echo "start|stop|restart|status"
        ;;
    esac
[root@localhost]# chkconfig --add kibana
[root@localhost]# chkconfig kibana on 
[root@localhost]# service kibana start

```


##### 安装elasticsearch-head
node环境参考相关文档[linux入门常用指令8.4安装nodejs]
```
[root@localhost]# cd elasticsearch-head
[root@localhost]# npm install
[root@localhost]# npm run start


```

### 安装logstash
```
[root@localhost ]# rpm -ivh logstash-7.10.0-x86_64.rpm 
    警告：logstash-7.10.0-x86_64.rpm: 头V4 RSA/SHA512 Signature, 密钥 ID d88e42b4: NOKEY
    准备中...                          ################################# [100%]
    正在升级/安装...
    1:logstash-1:7.10.0-1              ################################# [100%]
    Successfully created system startup script for Logstash

[root@localhost ]# systemctl start logstash
[root@localhost ]# systemctl status logstash
[root@localhost ]# systemctl enable logstash
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# cd /etc/logstash/conf.d
[root@localhost ]# 
    input {
        tcp {
            host => "0.0.0.0"
            port => 9250
            mode => "server"
            }
        }
        output {
        elasticsearch {
            hosts => ["127.0.0.1:9200"]
            index => "springboot-log-%{+YYYY.MM.dd}"
        }
    }

[root@localhost ]# systemctl restart logstash
[root@localhost ]# 
[root@localhost ]# netstat -tunlp|grep 9250
tcp6       0      0 :::9250                 :::*                    LISTEN      16396/java   
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 

```
#### logstash配置索引模版
```
output {
    elasticsearch {
        host => "127.0.0.1:9200"
        manage_template => true
        template_overwrite => true
    }
}

```

### 安装filebeat

```
[root@localhost]# vim /etc/filebeat/filebeat.yml 
    filebeat.inputs:
    - type: log
      enabled: true
      paths:
        - /var/log/nginx/access.log
    output.elasticsearch:
      hosts: ["10.0.0.110:9200"]
[root@localhost]# systemctl restart filebeat
[root@localhost]# systemctl enable filebeat
```



### 常见错误

```
java.lang.RuntimeException: can not run elasticsearch as root
切换es用户启动
[root@localhost]# su es

ERROR: [3] bootstrap checks failed
[1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65535]
[2]: max number of threads [3795] for user [es] is too low, increase to at least [4096]
[3]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
# 文件最大句柄数
[root@localhost]# vim /etc/security/limits.conf


which: no java in (/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin)
[root@localhost]# ln -s /usr/local/java/bin/java /usr/bin/java

```




kibana连接报错
```
[WARN ][r.suppressed ] path: /.kibana_task_manager/_count, params: {index=.kibana_task_manager}
org.elasticsearch.action.search.SearchPhaseExecutionException: all shards failed


http://192.168.1.1:9200/_cluster/health?pretty=true
    {
  "cluster_name" : "elasticsearch",
  "status" : "red",
  "timed_out" : false,
  "number_of_nodes" : 1,
  "number_of_data_nodes" : 1,
  "active_primary_shards" : 21,
  "active_shards" : 21,
  "relocating_shards" : 0,
  "initializing_shards" : 0,
  "unassigned_shards" : 2,
  "delayed_unassigned_shards" : 0,
  "number_of_pending_tasks" : 0,
  "number_of_in_flight_fetch" : 0,
  "task_max_waiting_in_queue_millis" : 0,
  "active_shards_percent_as_number" : 91.30434782608695
}

http://192.168.1.1:9200/_cat/indices?v
health status index                           uuid                   pri rep docs.count docs.deleted store.size pri.store.size
green  open   .monitoring-es-7-2023.02.13     kXKeMbg6STS_8tQbeubLQg   1   0       3283          207      2.5mb          2.5mb
green  open   .kibana_1                       V4IltvdiTzWtnIIXK1INrg   1   0         18            0     14.1kb         14.1kb
green  open   .monitoring-es-7-2023.02.02     L6-qstinRSSPIe6PrHeVXg   1   0      60304        60230     29.1mb         29.1mb
green  open   .tasks                          G1kQbTQTSF-mYZHF4FoaFQ   1   0          1            0      6.9kb          6.9kb
green  open   .monitoring-es-7-2023.02.06     bMZtXDciQna4MWzQUqlePA   1   0      63040            0     23.2mb         23.2mb
green  open   .monitoring-es-7-2023.02.07     IaA_7dXQRa2F2b-bAhxpWw   1   0      61444        61351     29.6mb         29.6mb
green  open   .monitoring-es-7-2023.02.09     5dyJcT80RNqHQFA1i7QBWw   1   0      30838           40     12.5mb         12.5mb
green  open   .apm-custom-link                SdrGnkqVQO2iUDQFxpldOA   1   0          0            0       208b           208b
red    open   .kibana_task_manager_1          pGlLAgRwSmug8CByc_17bQ   1   0                                                  
yellow open   my_index                        bzmZCPluTzi8jhNU_JWsbg   1   1          4            0     12.9kb         12.9kb
green  open   .kibana-event-log-7.10.0-000001 WDa5baL-T5GVRyxNFsPndA   1   0          7            0       38kb           38kb
green  open   .kibana-event-log-7.10.0-000002 H3DxVQ3sQCK7oOd3iOEG0w   1   0          0            0       208b           208b
green  open   .kibana-event-log-7.10.0-000003 tl7SzErmQwGrKzBQcIcTjA   1   0          0            0       208b           208b



删除red状态的索引

curl -XDELETE http://192.168.1.1:9200/.kibana_task_manager_1     
```