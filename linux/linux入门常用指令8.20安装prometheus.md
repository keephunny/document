
### 下载安装包

```
[root@localhost ]# tar -zxvf prometheus-2.37.5.linux-amd64.tar
[root@localhost ]# mv prometheus-2.37.5.linux-amd64 /usr/local/prometheus
[root@localhost ]# ./prometheus --config.file=./prometheus.yml &
[root@localhost ]# lsof -i:9090 
    http://127.0.0.1:9090
```


```
#查看是否已安装过
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 



```

### 前置配置

```
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
```

### 开机自启
```
[root@localhost ]# vim /etc/systemd/system/prometheus.service
    [Unit]
    Description=logging prometheus service    
    Documentation=https://prometheus.io

    [Service]
    Type=simple
    User=root
    Group=root
    ExecStart=/usr/local/prometheus/prometheus  --config.file=/usr/local/prometheus/prometheus.yml
    Restart=on-failure

    [Install]
    WantedBy=multi-user.target
[root@localhost ]# systemctl enable prometheus
[root@localhost ]# systemctl start prometheus
[root@localhost ]# systemctl status prometheus
[root@localhost ]# systemctl stop prometheus

```




### 安装node_exporter

```
[root@localhost ]# cd /usr/local/
[root@localhost ]# tar -zxvf node_exporter-1.5.0.linux-amd64.tar.gz -C node_exporter
[root@localhost ]# cd node_exporter/
[root@localhost ]# ./node_exporter
[root@localhost ]# netstat -tunlp|grep 9100
[root@localhost ]# http://192.168.1.1:9100/metrics
[root@localhost ]# vim /etc/systemd/system/node_exporter.service
    [Unit]
    Description=logging prometheus service    
    Documentation=https://prometheus.io

    [Service]
    Type=simple
    User=root
    Group=root
    ExecStart=/usr/local/node_exporter/node_exporter
    Restart=on-failure

    [Install]
    WantedBy=multi-user.target
[root@localhost ]# systemctl enable node_exporter
[root@localhost ]# systemctl start node_exporter
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 


```

### pushgateway

```
pushgateway --web.listen-address=9091 & 
四. prometheus抓取数据
Pushgateway只是指标的临时存放点，最终我们需要通过Prometheus将其存放到时间序列数据库里。对此，我们需要在Prometheus上面创建一个job。

 
- job_name: 'pushgateway'
        honor_labels: true
        static_configs:
          - targets:
            - '192.168.1.10:9091'
```

```
#启动脚本
[root@localhost ]# vim /etc/systemd/system/pushgateway.service
    [Unit]
    Description=logging pushgateway service    
    Documentation=https://prometheus.io

    [Service]
    Type=simple
    User=root
    Group=root
    ExecStart=/usr/local/pushgateway/pushgateway
    Restart=on-failure

    [Install]
    WantedBy=multi-user.target
```

### redis_exporter
```
ExecStart=/data/redis_exporter/redis_exporter -redis.addr 192.168.1.10:6379 -redis.password cbf123456.  -web.listen-address 192.168.1.10:9121 
```


### 安装blackbox_exporter
```
#源码安装卸载
[root@localhost ]# 
[root@localhost ]# vim /etc/systemd/system/blackbox_exporter.service
    [Unit]
    Description=blackbox_exporter
    After=network.target

    [Service]
    Type=simple
    User=root
    Group=root
    ExecStart=/usr/local/blackbox_exporter/blackbox_exporter --config.file=/usr/local/blackbox_exporter/blackbox.yml --web.listen-address=:9115
    Restart=on-failure

    [Install]
    WantedBy=multi-user.target
[root@localhost ]# 
```


### 配置调试 
命令行工具
```
[root@localhost src]# 

```
 
```

#### 文件目录

```
[root@localhost ]# cd /usr/local/t
```



连接数据库
```
[root@localhost ]# 

```


### 开机自启
启动服务进程：systemctl start   


### 目录文件

```
```
服务	作用	端口（默认）
Prometheus	普罗米修斯的主服务器	9090
Node_Exporter	负责收集Host硬件信息和操作系统信息	9100
MySqld_Exporter	负责收集mysql数据信息收集	9104
Cadvisor	负责收集Host上运行的docker容器信息	8080
Grafana	负责展示普罗米修斯监控界面	3000
Altermanager	等待接收prometheus发过来的告警信息，altermanager再发送给定义的收件人	9093 


### 常见错误

```

```