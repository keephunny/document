redis持久化



### RDB持久化 

RDB持久化可以在指定的时间间隔内生成数据集的时间点快照(point-in-time snapshot)，RDB是一个非常紧凑的文件，它保存了redis在某个时间点上的数据集。这个文件非常适合用于备份。

rdb实现方式是将存在redis内存中的数据写入到rdb文件中保存到磁盘上从而实现持久化。和aof不同的是rdb保存的是数据而不是操作，在进行数据恢复时，直接把rdb的文件读入到内存，即可完成数据恢复。rdb有两种实现方式：

* save：在主线程中执行，不过会阻塞redis服务进程。

* bgsave：主线程会forkk出一个子线程来负责处理rdb文件创建，是redis默认配置。手动启动后台保存操作，并不是立即执行。

  save和bgsave两种快照方式禁止同时执行，防目产生竟争条件。

```
# 可以使用 save 选项，来配置服务端执行 BGSAVE 命令的间隔时间

save second(监控时间范围) changes(监控key的变化量)

#服务端在900秒，读数据进行了至少1次修改，就会触发一次 BGSAVE 命令
save 900 1 

#服务端在300秒，读数据进行了至少10次修改，就会触发一次 BGSAVE 命令
save 300 10 
```

* dbfilename dump.rdb

  设置本地数据库文件名，默认dump.rdb

* dir

  设置存储rdb文件的路径

* rdbcompression yes

  设置存储至本地数据库时是否压缩数据，默认yes，采用lzf压缩。

* rdbchecksum yes

  设置是否进行RDB文件格式校验，该校验过程与写文件和读文件进程均进行校验。

* stop-writes-on-bgsave-error yes

  后台存储过程中如果出现了错误现象，是否停目保存操作，默认开启。

save指令是阻塞式的，不建议线上服务器使用。



rdb特殊启动开式

* 全量复制时
* 服务器运行过程中重启 debug reload
* 关闭服务器时指定保存数据 shutdown save

RDB快照

默认情况下，redis将数据快照保存在dump.rdb的二进制文件中，

RDB优点

* RDB是一个紧凑压缩的二进制文件，存储效率较高。
* RDB内部存储的是redis在某个时间点的数据快照，适合数据备份，全量复制等场景。
* RDB恢复数据的速度要比AOF要快。
* 应用：服务器中每x小时执行bgsave备份，并将RDB文件拷贝到远程服务器中用于灾备。

RDB缺点

* RDB方式无论是执行指令还是利用配置，无法做到实时持久化，具有较大的可能性丢失数据。
* bgsave指令运行时要执行forkk操作创建子进程，要损耗一些性能。
* RDB文件在不同版本的redis中可能不兼容。



```
lzf压缩：对重复值进行压缩，通过hash表来判断是否重复数据
```



### AOF持久化

AOF(Append Only File)通过保存数据库执行的命令来记录数据库状态。以独立日志的方式记录每次写命令，重启时再重新执行AOF文件中命令达到恢复数据的目的。与RDB相比可以简单描述为由记录数据改为记录数据产生的过程。

AOF默认策略是每秒fsync一次，在这种配置下redis可以保持良好的性能，并且在发生机器故障时，只会丢失1秒钟的数据。

AOF日志对数据库的保存顺序是，redis先执行命令，把数据库写入内存，然后才记录日志。后写日志可以避免记录到错误的命令，因为先执行命令，后写入日志，只有命令执行成功了，命令才能被写入到日志中。避免阻塞当前的写操作，是在命令执行后才记录日志的。

AOF是将所有执行的写命令都写到AOF文件尾，以此来记录数据发生的变化，如果redis想要恢复AOF中的数据，只要重新执行一次AOF文件中所包含的写命令。



#### AOF写数据策略

* always

  每次

* everysec

  每秒

* no

  系统控制

```
vim nginx.cnf

  #是否开启AOF持久化功能，默认不开启
  appendonly yes|no

  #配置AOF写数据策略
  appendfsync always|everysec|no
	
	#aof文件名
	appendfilename filename
	
	#aof文件位置
	dir
	
	#文件大小增长比超过这个值开始自动重写
	auto-aof-rewrite-percentage 100
	
	#文件大小超过这个值才可以有可能自动重写
	auto-aof-rewrite-min-size 64mb

```

##### AOF重写

随着命令不断写入AOF，文件会越来越大，为了解决这个问题，redis引入了AOF重写机制压缩文件体积，AOF文件重写是将redis进程内的数据转化为写命令同步到最新AOF文件的过程，简单的说就是对同一个数据的若干个命令执行结果转化成最终结果数据对应的指令进行记录。