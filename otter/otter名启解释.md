
* pileline：从源端到目标端的整个过程描述，主要由一些同步映射过程组成。
* channel：同步通道，单向同步中由一个pipeline组成，在双向同步中有两个pipeline组成。
* DataMediaPair：根据业务表定义映射关系，比如源表和目标表演，字段映射，字段组等。
* DataMedia：抽象的数据介质概念，可以理解为数据表演/mq队列定义。
* DataMediaSource：抽象的数据介质源信息，补充


* 为了使用otter必须要canal的支持，otter作为canal的消费方，也可以单独使用canal，如果你有消费mysql binlog的需求。
* canal有几种运行方式，生成环境中推荐使用zookeeper的持久化方式，对应的spring配置文件为default-instance.xml
* 运行otter需要aria2的支持
* canal在otter中只支持嵌入的方式，通过管理界面配置，不需要手动控制canal，canal是通过线程方式运行在node节点
* canal用的zookeeper和node-manager用的zookeeper可用用同一个，数据不会冲突，但是至少要3台机器做集群来HA，也可以利用zookeeper的observer特性构成镜像来提升性能 
* otter只支持row模式的数据同步，源库只支持mysql，目标库支持mysql和oracle
* 同步表必须要有主键，无主键表update会是一个全表扫搭。全字段匹配，如果出现重复记录，同步会导致数据错乱。
* 支持部分ddl同步(create\drop\alter\truncate\rename\create index\drop index)其它类型不支持，同时ddl语句不支持幂等性操作，所以出现重复同步时会导致同步挂起，可能跳过ddl异常。
* 不支持带外键的记录同步，
* 数据库上trigger配置慎重，比如源库有一A表配置trigger，将A表上的变化记录到B表中，面B表也需要同步，如果目标库也有这个trigger，在同时插入一次A表，2次B表。因为A表的同步插入也会触发trigger插入一次Ｂ表。
* 2个manage可以部署2个manager，manager之间本身没有通信，而是通过zk和数据库，配置node的时候需要知道 manager，可以指定其中一个即可，数据会反应到数据库和zk中，但是当某个manager挂了，新配置的node信息就不能反馈到另一个manager上，所以最佳是指定所有的manage。
* https的支持，otter用的jetty修改jetty.xml改成https方式就好，同时要修改otter.properties中的otter.domainName参数，由于菜单是公用的。找到navigation.vm修改url键接http改成https。