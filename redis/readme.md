



//TODO 

补充数据结构 cnblogs.com/shiblog/p/15843609.html









```
spring:
  application:
    name: lizz-gateway
  redis: #redis配置
    lettuce: #lettuce客户端配置
      pool: #连接池配置
        max-active: 5000 # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-wait: 1000 # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-idle: 2000 # 连接池中的最大空闲连接 默认 8
        min-idle: 1000 # 连接池中的最小空闲连接 默认 0
      cluster:
        refresh: #集群刷新
          adaptive: true #自动刷新集群 默认false关闭
	#period: 10M #定时刷新
    timeout: 1000 # 连接超时时间（毫秒）
    cluster: #集群配置
      nodes: #集群节点
        - 10.2.55.88:7001
        - 10.2.55.88:7002
        - 10.2.55.88:7003
        - 10.2.55.88:7004
        - 10.2.55.88:7005
        - 10.2.55.88:7006
      max-redirects: 3 #集群中重定向最大次数
```