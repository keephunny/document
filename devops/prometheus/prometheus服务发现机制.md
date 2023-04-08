当我们使用各类exporter分别对系统、数据库和HTTP服务进行监控指标采集，对于所有监控指标对应的Target的运行状态和资源使用情况，都是用Prometheus的静态配置功能 static_configs 来手动添加主机IP和端口，然后重载服务让Prometheus发现。

对于一组比较少的服务器的测试环境中，这种手动方式添加配置信息是最简单的方法。但是实际生产环境中，对于成百上千的节点组成的大型集群又或者Kubernetes这样的大型集群，很明显，手动方式捉襟见肘了。为此，Prometheus提前已经设计了一套服务发现功能。

prometheus服务发现实现方式：

1. 基态服务发现
1. 基于文件的服务发现
2. 查询API的服务发现
3. 使用DNS记录的服务发现
4. consul



四种指标类型：

* counter：递增式计数器
* gauge：可以任意变化的数值
* Histogram：对一段时间内数据进行采样，并所有数值求和统计数量
* Summary：与Histogram类似


### 静态配置服务

```
[root@localhost ]# vim prometheus.yml 
    - job_name: "nodes"
    # metrics_path defaults to '/metrics'
        static_configs:
        - targets:  
            - 192.168.1.1:8080
            - 192.168.1.2:8080
```

### 基于文件的服务发现

```
[root@localhost ]#  vim prometheus.yml 
    - job_name: "appserver"
        metrics_path: '/actuator/prometheus'
        scrape_interval: 10s
        file_sd_configs:
        - refresh_interval: 30s
            files:
            - targets/nodes/*.json


[root@localhost ]#  vim target/node/server.json
    [{
        "targets": ["192.168.121.100:8080"],
        "labels": {
            "app": "iotserver01",
            "env": "dev",
            "region": "devregion"
        }
    },
    {
        "targets": ["192.168.121.100:8081"],
        "labels": {
            "app": "iotserver02",
            "env": "dev",
            "region": "devregion"
        }
    }]
```


### 基于微服务发现
使用Spring Cloud构建的微服务，使用的是Eureka作为注册中心，且项目中使用到Prometheus做服务监控。而Prometheus提供了基于Eureka的服务发现支持。


```
[root@localhost ]#    vim prometheus.yml 
    - job_name: eureka
        metrics_path: /metrics
        eureka_sd_configs:
        - server: http://127.0.0.1:8080/eureka

```

### 基于kubernetes服务发现

Prometheus 通过与 Kubernetes API 集成主要支持5种服务发现模式：Node、Service、Pod、Endpoints、Ingress。不同的服务发现模式适用于不同的场景，例如：node适用于与主机相关的监控资源，如节点中运行的Kubernetes 组件状态、节点上运行的容器状态等；service 和 ingress 适用于通过黑盒监控的场景，如对服务的可用性以及服务质量的监控；endpoints 和 pod 均可用于获取 Pod 实例的监控数据，如监控用户或者管理员部署的支持 Prometheus 的应用。


### 基于console发现

### 基于DNS的发现