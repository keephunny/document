mysql binlog记录所有操作日志都有对应的事件类型


## STATEMENT模式
### Query   
* Query，包含了insert、delete、update，并且显示相关的sql，而Row不能显示sql。DML操作以文本的形式来记录事务的操作。

## ROW模式
* Query：与STATEMENT模式处理相同，存储的是SQL，主要是一些与数据无关的操作，eg: begin、drop table、truncate table 等；DDL操作

## 常用事件类型
* TABLE_MAP：记录了下一条事件所对应的表信息，在其中存储了数据库名和表名；(test.a_view)
* WRITE_ROWS：操作类型为insert；（insert）
* UPDATE_ROWS：操作类型为update；（update）
* DELETE_ROWS：操作类型为delete；（delete）
* XID， 用于标识事务提交。在事务提交时，不管是STATEMENT还是ROW格式的binlog，都会在末尾添加一个XID_EVENT事件代表事务的结束。该事件记录了该事务的ID，在MySQL进行崩溃恢复时，根据事务在binlog中的提交情况来决定是否提交存储引擎中状态为prepared的事务。
* FORMAT_DESCRIPTION_EVENT 是binlog version 4中为了取代之前版本中的START_EVENT_V3事件而引入的。它是binlog文件中的第一个事件，而且，该事件只会在binlog中出现一次。MySQL根据FORMAT_DESCRIPTION_EVENT的定义来解析其它事件。
* ROTATE_EVENT：当binlog文件的大小达到max_binlog_size的值或者执行flush logs命令时，binlog会发生切换，这个时候会在当前的binlog日志添加一个ROTATE_EVENT事件，用于指定下一个日志的名称和位置。
* GTID_LOG_EVENT：在启用GTID模式后，MySQL实际上为每个事务都分配了个GTID
* PREVIOUS_GTIDS_LOG_EVENT:开启GTID模式后，每个binlog开头都会有一个PREVIOUS_GTIDS_LOG_EVENT事件，它的值是上一个binlog的PREVIOUS_GTIDS_LOG_EVENT+GTID_LOG_EVENT，实际上，在数据库重启的时候，需要重新填充gtid_executed的值，该值即是最新一个binlog的PREVIOUS_GTIDS_LOG_EVENT+GTID_LOG_EVENT。
* STOP_EVENT：当MySQL数据库停止时，会在当前的binlog末尾添加一个STOP_EVENT事件表示数据库停止。

### ROW模式记录sql语句
MySQL实际上提供了一个参数，可以用于输出原生的DML语句，但是该语句仅仅是其注释的作用，并不会被应用。

```   
    set binlog_rows_query_log_events=1;