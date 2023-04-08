 prometheus.yml配置文件


 在这个缺省的配置文件里定义了4个单元：global、alerting、rule_files和scrape_configs。
 * global：配置的全局信息，抓取间隔、超时时间、规则执行周期
 * alerting：配置告警发送的地址
 * rule_files：告警规则文件，数据聚合配置
 * scrape_configs：配置抓取监控数据的相关信息，url、间隔时间、超时时间等。
 * remote_write：用于远程存储写配置，将数据投递到远程地址
 * remode_read：用于远程读配置

 ### gloabal
 ```
scrape_interval：全局默认数据拉取间隔
    [ scrape_interval: <duration> | default = 1m ]

scrape_timeout：全局默认的单次数据拉取超时，当报context deadline exceeded错误时需要在特定的job下配置该字段。
    [scrape_timeout: <duration> | default = 10s ]

evaluation_interval：全局默认的规则(主要是报警规则)拉取间隔
    [evaluation_interval: <duration> | default = 1m ]

external_labels：该服务端在与其他系统对接所携带的标签
    [ <labelname>: <labelvalue> ... ]

 ```

### scrape_configs
拉取数据配置，在配置字段内可以配置拉取数据的对象(Targets)，job以及实例
```
job_name：定义job名称，是一个拉取单元。每个job_name都会自动引入默认配置如
scrape_interval：依赖全局配置
scrape_timeout：依赖全局配置
metrics_path：默认为’/metrics’
scheme：默认为’http’
honor_labels：服务端拉取过来的数据也会存在标签，配置文件中也会有标签，这样就可能发生冲突。true就是以抓取数据中的标签为准 false就会重新命名抓取数据中的标签为“exported”形式，然后添加配置文件中的标签
    [ honor_labels: <boolean> | default = false ]
    
scheme：切换抓取数据所用的协议
    [ scheme: <scheme> | default = http ]

params：定义可选的url参数
    [ <string>: [<string>, ...] ]


抓取认证
basic_auth：password和password_file互斥只可以选择其一
  [ username: <string> ]
  [ password: <secret> ]
  [ password_file: <string> ]

bearer_token：bearer_token和bearer_token_file互斥只可以选择其一
    [ bearer_token: <secret> ]
    [ bearer_token_file: /path/to/bearer/token/file ]

tls_config：抓取ssl请求时证书配置
tls_config:
  [ ca_file: <filename> ]
  [ cert_file: <filename> ]
  [ key_file: <filename> ]
  [ server_name: <string> ]
  #禁用证书验证
  [ insecure_skip_verify: <boolean> ]

proxy_url：通过代理去主去数据
    [proxy_url: <string> ]

```

```
job_name：任务名称
honor_labels： 用于解决拉取数据标签有冲突，当设置为 true, 以拉取数据为准，否则以服务配置为准
params：数据拉取访问时带的请求参数
scrape_interval： 拉取时间间隔
scrape_timeout: 拉取超时时间
metrics_path： 拉取节点的 metric 路径
static_configs：配置访问路径前缀，如ip+port，或者域名地址，或者通过服务发现，类似alertmanager.prom-alert.svc:9093
scheme： 拉取数据访问协议，如http
sample_limit： 存储的数据标签个数限制，如果超过限制，该数据将被忽略，不入存储；默认值为0，表示没有限制
relabel_configs： 拉取数据重置标签配置
metric_relabel_configs：metric 重置标签配置
```

### alerting



### 服务发现类
```
#sd就是service discovery的缩写
static_configs: 静态服务发现
eureka_sd_config：eureka服务发现，发现真实的实例节点的ip+port，参考：Configuration | Prometheus
可参考案例：prometheus/prometheus-eureka.yml at release-2.36 · prometheus/prometheus · GitHub
dns_sd_configs: DNS 服务发现
file_sd_configs: 文件服务发现
consul_sd_configs: Consul 服务发现
serverset_sd_configs: Serverset 服务发现
nerve_sd_configs: Nerve 服务发现
marathon_sd_configs: Marathon 服务发现
kubernetes_sd_configs: Kubernetes 服务发现
gce_sd_configs: GCE 服务发现
ec2_sd_configs: EC2 服务发现
openstack_sd_configs: OpenStack 服务发现
azure_sd_configs: Azure 服务发现
triton_sd_configs: Triton 服务发现 

```

### 远程读写 remote_read remote_write

Prometheus可以进行远程读/写数据。字段remote_read和remote_write
```
    url: <string>远程读取的url

    required_matchers:通过标签来过滤读取的数据
    [ <labelname>: <labelvalue> ... ]

    [ remote_timeout: <duration> | default = 1m ]

    #当远端不是存储的时候激活该项
    [ read_recent: <boolean> | default = false ]

    basic_auth:
    [ username: <string> ]
    [ password: <string> ]
    [ password_file: <string> ]
    [ bearer_token: <string> ]
    [ bearer_token_file: /path/to/bearer/token/file ]
    tls_config:
    [ <tls_config> ]
    [ proxy_url: <string> ]
```

remote_write
```
    url: <string>

    [ remote_timeout: <duration> | default = 30s ]

    #写入数据时候进行标签过滤
    write_relabel_configs:
    [ - <relabel_config> ... ]

    basic_auth:
    [ username: <string> ]
    [ password: <string> ]
    [ password_file: <string> ]

    [ bearer_token: <string> ]

    [ bearer_token_file: /path/to/bearer/token/file ]

    tls_config:
    [ <tls_config> ]

    [ proxy_url: <string> ]

    #远端写细粒度配置，这里暂时仅仅列出官方注释
    queue_config:
    # Number of samples to buffer per shard before we start dropping them.
    [ capacity: <int> | default = 10000 ]
    # Maximum number of shards, i.e. amount of concurrency.
    [ max_shards: <int> | default = 1000 ]
    # Maximum number of samples per send.
    [ max_samples_per_send: <int> | default = 100]
    # Maximum time a sample will wait in buffer.
    [ batch_send_deadline: <duration> | default = 5s ]
    # Maximum number of times to retry a batch on recoverable errors.
    [ max_retries: <int> | default = 3 ]
    # Initial retry delay. Gets doubled for every retry.
    [ min_backoff: <duration> | default = 30ms ]
    # Maximum retry delay.
    [ max_backoff: <duration> | default = 100ms ]

```

```
remote_write:
- url: http://xxx:9988/prom2hubble/push?group=xxx
  remote_timeout: 30s
  write_relabel_configs:
  - source_labels: [__name__]
    separator: ;
    regex: obser:(.*)
    replacement: $1
    action: keep
  - separator: ;
    regex: (.*)
    target_label: hubble_endpoint
    replacement: hubble_qpaas_obser
    action: replace
  - separator: ;
    regex: (.*)
    target_label: group
    replacement: hubble
    action: replace
  - separator: ;
    regex: (.*)
    target_label: hubble_step
    replacement: "60"
    action: replace
  - separator: ;
    regex: label_qke_cloud_qiyi_domain_(.*)
    replacement: $1
    action: labelmap
  - separator: ;
    regex: (job|endpoint|service|pod|instance|namespace|prometheus.*|label_qke_cloud_qiyi_domain_.*)
    replacement: $1
    action: labeldrop
  queue_config:
    capacity: 2500
    max_shards: 200
    min_shards: 1
    max_samples_per_send: 500
    batch_send_deadline: 5s
    min_backoff: 30ms
    max_backoff: 100ms
  metadata_config:
    send: true
    send_interval: 1m
```