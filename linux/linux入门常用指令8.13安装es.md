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
    * soft nproc 4096
    * hard nproc 4096
[root@localhost]# ulimit -Hn
[root@localhost]#
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
[root@localhost]# vim /usr/lib/systemd/system/elasticsearch.service
    [Unit]
    Description=elasticsearch
    After=network.target

    [Service]
    Type=simple
    User=es1
    ExecStart=/usr/local/elasticsearch/bin/elasticsearch
    PrivateTmp=true

    [Install]
    WantedBy=multi-user.target
[root@localhost]# systemctl enable elasticsearch.service
```


##### 安装kibana
```
[root@localhost]# chown -R es:es kibana/
[root@localhost]# vim kibana.yml
    elasticsearch.hosts: ["http://127.0.0.1:9200"]
[root@localhost]# bin/kibana
[root@localhost]# mkdir logs
[root@localhost]# nohup bin/kibana > logs/kibana-start.log 2>&1 &
[root@localhost]# netstat -tunlp|grep 5601
 

[root@localhost]# curl http://127.0.0.1:5601

```

开机自启
```
[root@localhost]# vim /usr/lib/systemd/system/kibana.service
[Unit]
Description=kibana
After=network.target

[Service]
Type=simple
User=es1
ExecStart=/usr/local/kibana/bin/kibana
PrivateTmp=true

[Install]
WantedBy=multi-user.target
[root@localhost]# systemctl enable kibana.service
```


##### 安装elasticsearch-head
node环境参考相关文档[linux入门常用指令8.4安装nodejs]
```
[root@localhost]# cd elasticsearch-head
[root@localhost]# npm install
[root@localhost]# npm run start


```