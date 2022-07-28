
zookeeper主要用于解决大规模分布式应用场景下，服务协调同步的问题。主要提供统一命名、配置管理、分布式锁、集群管理等功能。

在分布式系统领域中有个著名的CAP定律（C-数据一致性；A-服务可用性；P-服务对网络分区故障的容错性）这三个特定在任何分布式系统中都不能同时满足，最多同事满足2个。zookeeper是CP的。即任何时刻对zookeeper的访问请求能得到一致的数据结果，同时系统对网络分区具有容错性；但是它不保证每次服务请求都是可用的。极端环境下，zookeeper会丢弃一些请求，消费者端需要重新请求才能得到结果（zookeeper为什么不设计成AP的？）。作为zookeeper的核心算法Zab（zookeeper  Atomic Broadcast，全称：原子消息广播协议），就是解决了分布式系统下数据如何在多个服务之间保持同步问题的。


#### CAP原则
分布式系统中CAP原则
一致性(Consistency)
可用性(Availability)
分区容错性(Parition tolerance)
CAP在分布式存储系统中，最多只能实现以上两点。
而由于当前网络延迟故障会导致丢包等问题，所以分区容错性是必须实现的，我们只能在一致性和可用性中进行选择，没有Nosql数据库能同时保证三点。也就是说我们只能选择CP或者AP。







报错 Error contacting service. It is probably not running.
没有创建myid



#### 应用场景

* 数据发布和订阅，配置中心
* 命名服务 Naming Service
* 分布式协调/通知
* Master选举
* 分布式锁 排它锁、共享锁

#### 集群角色

* Leader

* Follower

* Observer

  一个ZooKeeper集群同一时刻只会有一个Leader，其他都是Follower或Observer。

#### 节点类型
* 短暂：ephemeral，断开连接后，创建的节点自动删除
* 持久：persistent，断开连接后，创建的节点不删除

Znode有四种形式的目录节点（默认是persistent ）

* 持久化目录节点（PERSISTENT） 客户端与zookeeper断开连接后，该节点依旧存在
* 持久化顺序编号目录节点（PERSISTENT_SEQUENTIAL）客户端与zookeeper断开连接后，该节点依旧存在，只是Zookeeper给该节点名称进行顺序编号
* 临时目录节点（EPHEMERAL）客户端与zookeeper断开连接后，该节点被删除
* 临时顺序编号目录节点（EPHEMERAL_SEQUENTIAL）客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号


#### 认证方式
* world：默认方式，开放权限随意访问。
* auth：已授权且认证通过的用户可以访问。
* digest：用户名：密码认证方式，最常用
* IP白名单： 授权指定的Ip地址，和指定的权限点，控制访问。
```
#添加认证用户
addauth digest 用户名:密码

#设置权限
setAcl /path auth:用户名:密码:权限

#查看Acl设置
getAcl /path
```