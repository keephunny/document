
### 启动服务或应用
* rabbitmq-server
    在当前节点上启动RabbitMQ服务, 包括启动底层的Erlang进程, 并在进程上启动RabbitMQ应用.
* rabbitmqctl start_app
    在当前节点上启动RabbitMQ应用, 注意这和"rabbitmq-server"命令是不一样的. "rabbitmq-server"命令用于启动RabbitMQ服务, 包括启动Erlang进程以及在进程上启动RabbitMQ应用, 而"rabbitmqctl start_app"命令使用的前提是RabbitMQ服务的一部分(Erlang进程)仍存活.


### 停止服务或应用
* rabbitmqctl stop_app
在当前节点上停止RabbitMQ应用, 但其会保持RabbitMQ服务的一部分(Erlang进程)继续运行, 重启应用可通过"rabbitmqctl start_app"命令. 

* rabbitmqctl stop
停止当前节点的RabbitMQ服务, 包括Erlang进程以及进程上的RabbitMQ应用. 重启服务请使用"rabbitmq-server"命令, "rabbitmqctl start_app"命令是起不了作用的, 因为Erlang进程已不存在.

* rabbitmqctl shutdown
强制停止当前节点的RabbitMQ服务, 包括Erlang进程以及进程上的RabbitMQ应用. 如果操作失败, 命令返回非0。

* rabbitmqctl wait [pid_file] [--pid 进程号]
在指定的Erlang进程上启动RabbitMQ应用, 命令会从pid_file指定的文件中读取进程号, 或通过pid参数获得进程号, 并在对应进程号的进程上启动RabbitMQ应用。

### 重置服务
* rabbitmqctl reset
    初始化节点, 即把节点恢复到最原始状态, 即删除其上的虚拟主机/交换机/队列/持久化消息/用户, 退出已加入的集群等。执行该命令前, 请确保RabbitMQ应用已停止.
* rabbitmqctl force_reset
    强制初始化节点, 当配置文件损坏时请使用该操作. 和"rabbitmqctl reset"一样, 其必须在RabbitMQ应用停止后才能操作.


### 消息队列管理
* rabbitmqctl add_vhost 虚拟主机名
在当前节点上增加虚拟主机 

* rabbitmqctl delete_vhost 虚拟主机名
删除当前节点上的某虚拟主机 

* rabbitmqctl list_vhosts
列出当前节点上的所有虚拟机

* rabbitmqctl list_queues [-p 虚拟主机名] [返回字段]
列出某虚拟主机上的所有队列, 未指定p参数时, 默认使用"/"虚拟主机. "返回字段"用以要求命令额外返回队列信息, 常用字段如下:
    * name：队名名称
    * durable：队列是否持久化
    * auto_delete：长时间不使用后是否会自动删除
    * messages_unacknowledged：队列中未确认消息数量
    * messages_ready：队列中等待发送消息数量
    * messages：队列中消息总数, 上面两者之和
    * messages_persistent：队列中持久消息总数
    * consumers：队列连接的消费者数量

* rabbitmqctl list_exchanges [-p 虚拟主机名] [返回字段]
返回某虚拟主机上所有交换机, 未指定p参数时, 默认使用"/"虚拟主机. "返回字段"以要求命令额外返回交换机信息, 常用字段如下:
    * name：交换机名称
    * type：交换机类型。 共四种topic direct fanout header
    * durable：交换机是否持久化
    * auto_delete：长时间不使用后是否会自动删除

* rabbitmqctl list_bindings [-p 虚拟主机名]
返回某虚拟主机上所有绑定的详细信息, 未指定p参数时, 默认使用"/"虚拟主机.

* rabbitmqctl list_connections [返回字段]
返回当前节点上所有连接. "返回字段"用以要求命令额外返回的连接信息, 常用字段如下:
    * pid	连接所在的进程号
    * host	服务器的地址
    * port	服务器的端口
    * ssl	连接是否ssl加密
    * state	连接状态
    * channels	连接下信道数
    * user	连接关联的用户名
    * vhost	所属虚拟主机
    * timeout	连接的超时时间/商议的心跳间隔时间, 单位为秒
    * connected_at	连接的建立时间
* rabbitmqctl list_channels [返回字段]
列出所有的信道, "返回字段"用以要求命令额外返回的信道信息, 常用字段如下：
    * pid	连接所在的进程号
    * connection	连接所在的进程号, 不清楚和上者的区别
    * name	信道的名字, 类似于"客户端ip:端口 -> 服务器ip:端口"的结构
    * user	信道所属用户
    * vhost	信道所属虚拟主机
    * consumer_count	从该信道获取消息的消费者数量
    * messages_unacknowledged	通过该信道已发送但还未确认的消息数量
    * prefetch_count	每个消费者的一次最多从该信道获取未确认消息的数量


* rabbitmqctl list_consumers [-p 虚拟主机名]
列出某虚拟主机上订阅的消费者. 每行依次为"订阅的队列名称", "信道所在的进程id", "订阅的唯一标识", "消费者是否启用消息自动确认机制", "prefetch_count".

* abbitmqctl status
生成消息代理(Broker)的报告.

 

* rabbitmqctl node_health_check [-n 节点名称]
对节点做健康状况检查, 检查是否有报警. 未指定n参数时, 默认对当前节点做检查.


* rabbitmqctl environment
显示当前节点的所有环境变量配置.

 

* rabbitmqctl close_connection 进程号 异常信息
关闭某进程对应的连接, 其中进程号为连接所在的Erlang进程id.   
举例: rabbitmqctl close_connection “<rabbit@node.xx.com>” "user_require", 表示关闭Erlang进程“<rabbit@node.xx.com>”上的所有连接, 关闭时的异常信息为"user_require".
 

* rabbitmqctl close_all_connections [-p 虚拟主机名] [--global]
关闭当前节点的某虚拟主机上所有连接. p参数用于指定某台特定的虚拟主机, 如果没有p参数则一定加上--global, 其表示关闭该节点上的所有连接. 
举例: "rabbitmqctl close_all_connections -p plant", 表示关闭"plant虚拟主机"上的连接. "rabbitmqctl close_all_connections --global", 表示关闭当前节点上的所有连接.
 

* rabbitmqctl set_vm_memory_high_watermark absolute 内存大小
设置消息队列占用内存的限制, 内存大小单位为kB, MB, GB. 一旦内存达到该值, 则启用流量限制.
举例: rabbitmqctl set_vm_memory_high_watermark absolute 200MB 

* rabbitmqctl set_disk_free_limit disk_limit 磁盘大小
设置磁盘的最小剩余空间.

### 集群管理
* rabbitmqctl join_cluster A节点的节点名称 [--ram]
将当前节点加入A节点所在集群, 如果ram参数被指明, 则当前节点将作为内存节点加入集群.

* rabbitmqctl cluster_status
打印集群的目前状态(节点数量, 活跃节点数量, 节点类型).

* rabbitmqctl change_cluster_node_type disc/ram
改变当前节点类型, disc表示类型为磁盘节点, ram表示类型为内存节点. 执行该命令前, 请确保RabbitMQ应用已停止.

* rabbitmqctl -n A节点的节点名称 forget_cluster_node B节点的节点名称
从A节点所在集群中移除B节点.

* rabbitmqctl sync_queue [-p 虚拟主机名] 队列名
要求所有镜像队列对本节点某虚拟主机下的指定队列进行一次同步, 同步时该队列会阻塞住, 未指定p参数时, 默认为"/"虚拟主机.

* rabbitmqctl purge_queue [-p 虚拟主机名] 队列名
清除队列以及队列内的消息, 未指定p参数时, 默认为"/"虚拟主机.

### 用户及权限管理

* rabbitmqctl add_user 用户名 密码
增加用户.
 

* rabbitmqctl delete_user 用户名
删除用户.
 

* rabbitmqctl change_password 用户名 密码
更改某用户的密码.
 

* rabbitmqctl authenticate_user 用户名 密码
用户认证.

* rabbitmqctl list_users
列出当前集群上的所有用户.


* rabbitmqctl set_permissions [-p 虚拟主机名] 用户名 配置权限 写权限 读权限
设置某用户访问某虚拟主机的权限, 未指定p参数时, 默认使用"/"虚拟主机.
举例:
rabbitmqctl set_permissions -p /plant monkey “^xx-.*” “.*” “.*”
授予用户"monkey"对"/plant"虚拟主机的权限, 包括所有以"xx-"开头资源的配置权限, 所有资源的写权限, 所有资源的读权限.


* rabbitmqctl clear_permissions [-p 虚拟主机名] 用户名
收回某用户在某虚拟主机上的所有权限, 未指定p参数时, 默认使用"/"虚拟主机.
举例:
rabbitmqctl clear_permissions -p /plant monkey
收回用户"monkey"对"/plant"虚拟主机的所有权限. 

* rabbitmqctl list_permissions [-p 虚拟主机名]
列出某虚拟主机上所有用户的所有权限, 未指定p参数时, 默认使用"/"虚拟主机. 

* rabbitmqctl list_user_permissions 用户名
列出某用户在所有虚拟主机上的权限.

* rabbitmqctl set_user_tags 用户名 administrator
设置某用户为管理员.

### 后台管理
通过插件, 可以使用Web后台来监控和管理RabbitMQ服务:

rabbitmq-plugins enable rabbitmq_management

即可访问"127.0.0.1:15672", 默认用户名和密码均为"guest".