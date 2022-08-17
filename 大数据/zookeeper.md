
zookeeper是一个基于观察者模式设计的分布式服务管理框架，它负责存储和管理数据，然后接受观察者注册，一旦这些数据发生了变化，zookeeper就负责通知。zookeeper=文件系统+通知机制

* InterProcessMutex：分布式可重入排它锁
* InterProcessSemaphoreMutex：分布式排它锁
* InterProcessReadWriteLock：分布式读写锁
* InterProcessNultiLock：用多个锁去进行一组操作
* InterProcessSemaphoreV2:共享信号量


常用配置
1. tickTime =2000：通信心跳数，Zookeeper服务器与客户端心跳时间，单位毫秒
2. initLimit =10：LF初始通信时限
3. syncLimit =5：LF同步通信时限，应时间单位，假如响应超过syncLimit * tickTime，Leader认为Follwer死掉，从服务器列表中删除Follwer。
4. dataDir：数据文件目录+数据持久化路径
5. clientPort =2181：客户端连接端口

常用命令
```
ls path 查看当前znode子节点
    -w 监听子节点变化
    -s 附加次级信息
create 创建普通节点
    -s 含有序列
    -e 临时节点
get path 获取节点的值
    -w 监听内容变化
    -s 附加次级信息
set 设置节点的值
stat 查看节点状态
delete 删除节点
deleteall 递归删除节点


```