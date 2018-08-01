mysql查看当前连接状态

### show status like '%connect%';
* connections：试图连接数（不分成功失败）
* max_used_connections：服务器启动后已经同时使用连接的最大数量
* threads_connected：当前连接数

### show processlist
* id：连接到mysql服务器线程的唯一标识，可以通过kill终止
* user：当前线程连接数据库的用户
* host：显示这个语句从哪个ip端口发出的
* db：线程连接的数据库名
* command：显示当前连接的执行命令，sleep、query、connect
* time：线程处于当前状态的时间，单位秒
* state：显示使用当前连接sql语句的状态描述。state只是语句执行中某一个状态，已查询为例：需要经过copying to tmp table,sorting result,sending data等状态。Master has sent all binlog to slave; waiting for more updates
    state状态：
    * Checking table：正在检查数据表（这是自动的）。
    * Closing tables ：正在将表中修改的数据刷新到磁盘中，同时正在关闭已经用完的表。这是一个很快的操作，如果不是这样的话，就应该确认磁盘空间是否已经满了或者磁盘是否正处于重负中。
    * Connect Out ：复制从服务器正在连接主服务器。
    * Copying to tmp table on disk ：由于临时结果集大于tmp_table_size，正在将临时表从内存存储转为磁盘存储以此节省内存。
    * Creating tmp table ：正在创建临时表以存放部分查询结果。
    * deleting from main table ：服务器正在执行多表删除中的第一部分，刚删除第一个表。
    * deleting from reference tables：服务器正在执行多表删除中的第二部分，正在删除其他表的记录。
    * Flushing tables：正在执行FLUSH TABLES，等待其他线程关闭数据表。
    * Killed：发送了一个kill请求给某线程，那么这个线程将会检查kill标志位，同时会放弃下一个kill请求。MySQL会在每次的主循环中检查kill标志位，不过有些情况下该线程可能会过一小段才能死掉。如果该线程程被其他线程锁住了，那么kill请求会在锁释放 时马上生效。
    * Locked ：被其他查询锁住了。
    * Sending data：正在处理SELECT查询的记录，同时正在把结果发送给客户端。
    * Sorting for group ：正在为GROUP BY做排序。
    * Sorting for order ：正在为ORDER BY做排序。
    * Opening tables ：这个过程应该会很快，除非受到其他因素的干扰。例如，在执ALTER TABLE或LOCK TABLE语句行完以前，数据表无法被其他线程打开。正尝试打开一个表。
    * Removing duplicates ：正在执行一个SELECT DISTINCT方式的查询，但是MySQL无法在前一个阶段优化掉那些重复的记录。因此，MySQL需要再次去掉重复的记录，然后再把结果发送给客户端。
    * Reopen table ：获得了对一个表的锁，但是必须在表结构修改之后才能获得这个锁。已经释放锁，关闭数据表，正尝试重新打开数据表。
    * Repair by sorting ：修复指令正在排序以创建索引。
    * Repair with keycache：修复指令正在利用索引缓存一个一个地创建新索引。它会比Repair by sorting慢些。
    * Searching rows for update ：正在讲符合条件的记录找出来以备更新。它必须在UPDATE要修改相关的记录之前就完成了。
    * Sleeping ：正在等待客户端发送新请求.
    * System lock ：正在等待取得一个外部的系统锁。如果当前没有运行多个mysqld服务器同时请求同一个表，那么可以通过增加–skip-external-locking参数来禁止外部系统锁。
    * Upgrading lock：INSERT DELAYED正在尝试取得一个锁表以插入新记录。
    * Updating：正在搜索匹配的记录，并且修改它们。
    * User Lock ：正在等待GET_LOCK()。
    * Waiting for tables ：该线程得到通知，数据表结构已经被修改了，需要重新打开数据表以取得新的结构。然后，为了能的重新打开数据表，必须等到所有其他线程关闭这个表。以下几种情况下会产生这个通知：FLUSH TABLES tbl_name, ALTER TABLE, * RENAME TABLE, REPAIR TABLE, ANALYZE TABLE,或OPTIMIZE TABLE。
    * waiting for handler insert ：INSERT DELAYED已经处理完了所有待处理的插入操作，正在等待新的请求。 
    　大部分状态对应很快的操作，只要有一个线程保持同一个状态好几秒钟，那么可能是有问题发生了，需要检查一下。 
　还有其他的状态没在上面中列出来，不过它们大部分只是在查看服务器是否有存在错误是才用得着。 

* info：线程执行的sql语句
    -- 查询非 Sleep 状态的链接，按消耗时间倒序展示，自己加条件过滤
    select id, db, user, host, command, time, state, info
    from information_schema.processlist
    where command != 'Sleep' order by time desc 

    -- 查询执行时间超过2分钟的线程，然后拼接成 kill 语句
    select concat('kill ', id, ';')
    from information_schema.processlist
    where command != 'Sleep'
    and time > 2*60
    order by time desc 




 show table status;
解释：显示当前使用或者指定的database中的每个表的信息。信息包括表类型和表的最新更新时间
 
 
 
show columns from table_name from database_name; 或show columns from database_name.table_name;或show fields;
解释：显示表中列名称（和 desc table_name 命令的效果是一样的）
 
show grants for user_name@localhost ;
解释：显示一个用户的权限，显示结果类似于grant 命令
 
show index from table_name;或show keys;
解释：显示表的索引
 
show status;
解释：显示一些系统特定资源的信息，例如，正在运行的线程数量
 
show variables;
解释：显示系统变量的名称和值
 
show privileges;
解释：显示服务器所支持的不同权限
 
show create database database_name;
解释：显示创建指定数据库的SQL语句
 
show create table table_name;
解释：显示创建指定数据表的SQL语句
 
show engies;
解释：显示安装以后可用的存储引擎和默认引擎。
 
show innodb status;
解释：显示innoDB存储引擎的状态
 
show logs;
解释：显示BDB存储引擎的日志
 
show warnings;
解释：显示最后一个执行的语句所产生的错误、警告和通知
 
show errors;
解释：只显示最后一个执行语句所产生的错误   