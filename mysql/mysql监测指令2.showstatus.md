SHOW STATUS命令会显示每个服务器变量的名字和值，状态变量是只读的。我们可以在MySQL客户端下运行SHOW STATUS或者在命令行运用mysqladmin extended-status来查看这些变量。如果使用SQL命令，可以使用LIKE或者WHERE来限制结果。LIKE可以对变量名做标准模式匹配。

SHOW STATUS中混杂了全局和会话变量，其中许多变量有双重域：既是全局变量，也是会话变量，有相同的名字。如果只需要看全局变量，需要改为SHOW GLOBAL STATUS查看。


* Aborted_clients        由于客户没有正确关闭连接已经死掉，已经放弃的连接数量。
* Aborted_connects 尝试已经失败的MySQL服务器的连接的次数。
* Binlog_cache_disk_use 当事务日志比binlog_cache_size大时，他会创建临时文件，该状态表示有多少个事务使用了临时文件
* Binlog_cache_use        表示有多少个事物使用了binlog_cache_size来缓存未提交的事物日志
* Bytes_received        从客户处已经接收到的字节数。
* Bytes_sent        已经发送给所有客户的字节数。
* Com_[statement]        用于每一种语句的这些变量中的一种。变量值表示这条语句被执行的次数，如com_select,表示查询语句被执行的次数。
* Connections        试图连接MySQL服务器的次数。
* Created_tmp_disk_tables 服务器执行语句时在硬盘上自动创建的临时表的数量
* Created_tmp_tables 当执行语句时，已经被创造了的隐含临时表的数量。
* Created_tmp_files mysqld创建的临时文件个数。
* Delayed_insert_threads 正在使用的延迟插入处理器线程的数量。
* Delayed_writes        用INSERT DELAYED写入的行数。
* Delayed_errors        用INSERT DELAYED写入的发生某些错误(可能重复键值)的行数。
* Flush_commands        执行FLUSH命令的次数。
* Handler_commit        内部COMMIT命令的个数
* Handler_delete        请求从一张表中删除行的次数。
* Handler_discover MySQL服务器可以问NDB CLUSTER存储引擎是否知道某一名字的表。这被称作发现。Handler_discover说明通过该方法发现的次数。
* Handler_prepare两阶段提交操作准备阶段的计数器 
* Handler_read_first 请求读入表中第一行的次数。
* Handler_read_key 请求数字基于键读行。如果索引正在工作， Handler_read_key 的值将很高，这个值代表了一个行被索引值读的次数，很低的值表明增加索引得到的性能改善不高，因为索引并不经常使用。
* Handler_read_next 请求读入基于一个键的一行的次数。
* Handler_read_rnd 请求读入基于一个固定位置的一行的次数。
* Handler_read_rnd_next 读取数据文件中下一行数据的请求的个数。一般，这个值不能太高，因为这意味着查询操作不会使用索引，并且必须从数据文件中读取
* Handler_read_prev 按照索引的顺序读取前面一行数据的请求的个数。这个变量值由SELECT fieldlist ORDER BY fields DESC类型的语句使用
* Handler_rollback 内部ROLLBACK命令的数量
* Handler_savepoint 在一个存储引擎放置一个保存点的请求数量
* Handler_savepoint_rollback 在一个存储引擎的要求回滚到一个保存点数目
* Handler_update        请求更新表中一行的次数。
* Handler_write        请求向表中插入一行的次数。
* Innodb_buffer_pool_pages_data 包含数据的页数(脏或干净)。
* Innodb_buffer_pool_pages_dirty 当前的脏页数。
* Innodb_buffer_pool_pages_flushed 要求清空的缓冲池页数。
* Innodb_buffer_pool_pages_free 空页数。
* Innodb_buffer_pool_pages_misc 忙的页数，因为它们已经被分配优先用作管理，例如行锁定或适用的哈希索引。该值还可以计算为Innodb_buffer_pool_pages_total - Innodb_buffer_pool_pages_free - Innodb_buffer_pool_pages_data。
* Innodb_buffer_pool_pages_total 缓冲池总大小（页数）。
* Innodb_buffer_pool_read_ahead_rnd InnoDB初始化的“随机”read-aheads数。当查询以随机顺序扫描表的一大部分时发生。
* Innodb_buffer_pool_read_ahead_seq InnoDB初始化的顺序read-aheads数。当InnoDB执行顺序全表扫描时发生。
* Innodb_buffer_pool_read_requests InnoDB已经完成的逻辑读请求数。
* Innodb_buffer_pool_reads 不能满足InnoDB必须单页读取的缓冲池中的逻辑读数量。
* Innodb_buffer_pool_wait_free 一般情况，通过后台向InnoDB缓冲池写。但是，如果需要读或创建页，并且没有干净的页可用，则它还需要先等待页面清空。该计数器对等待实例进行记数。如果已经适当设置缓冲池大小，该值应小。
* Innodb_buffer_pool_write_requests 向InnoDB缓冲池的写数量。
* Innodb_data_fsyncs fsync()操作数。
* Innodb_data_pending_fsyncs 当前挂起的fsync()操作数。
* Innodb_data_pending_reads 当前挂起的读数。
* Innodb_data_pending_writes 当前挂起的写数。
* Innodb_data_read 至此已经读取的数据数量（字节）。
* Innodb_data_reads 数据读总数量。
* Innodb_data_writes 数据写总数量。
* Innodb_data_written 至此已经写入的数据量（字节）。
* Innodb_dblwr_writes, Innodb_dblwr_pages_written 已经执行的双写操作数量和为此目的已经写好的页数。参见15.2.14.1节，“磁盘I/O”。
* Innodb_log_waits 我们必须等待的时间，因为日志缓冲区太小，我们在继续前必须先等待对它清空。
* Innodb_log_write_requests 日志写请求数。
* Innodb_log_writes 向日志文件的物理写数量。
* Innodb_os_log_fsyncs 向日志文件完成的fsync()写数量。
* Innodb_os_log_pending_fsyncs 挂起的日志文件fsync()操作数量。
* Innodb_os_log_pending_writes 挂起的日志文件写操作。
* Innodb_os_log_written 写入日志文件的字节数。
* Innodb_page_size 编译的InnoDB页大小(默认16KB)。许多值用页来记数；页的大小很容易转换为字节。
* Innodb_pages_created 创建的页数。
* Innodb_pages_read 读取的页数。
* Innodb_pages_written 写入的页数。
* Innodb_row_lock_current_waits 当前等待的待锁定的行数。
* Innodb_row_lock_time 行锁定花费的总时间，单位毫秒。
* Innodb_row_lock_time_avg 行锁定的平均时间，单位毫秒。
* Innodb_row_lock_time_max 行锁定的最长时间，单位毫秒。
* Innodb_row_lock_waits 一行锁定必须等待的时间数。
* Innodb_rows_deleted 从InnoDB表删除的行数
* Innodb_rows_inserted 插入到InnoDB表的行数。
* Innodb_rows_read 从InnoDB表读取的行数。
* Innodb_rows_updated InnoDB表内更新的行数。
* Key_blocks_not_flushed 键缓存内已经更改但还没有清空到硬盘上的键的数据块数量。
* Key_blocks_unused 键缓存内未使用的块数量。你可以使用该值来确定使用了多少键缓存；参见5.3.3节，“服务器系统变量”中Key_buffer_size的讨论。
* Key_blocks_used 键缓存内使用的块数量。该值为高水平线标记，说明已经同时最多使用了多少块。
* Key_read_requests 从缓存读键的数据块的请求数。
* Key_reads 从硬盘读取键的数据块的次数。如果Key_reads较大，则Key_buffer_size值可能太小。可以用Key_reads/Key_read_requests计算缓存损失率。
* Key_write_requests 将键的数据块写入缓存的请求数。
* Key_writes 向硬盘写入将键的数据块的物理写操作的次数。
* Last_query_cost 用查询优化器计算的最后编译的查询的总成本。用于对比同一查询的不同查询方案的成本。默认值0表示还没有编译查询。 默认值是0。Last_query_cost具有会话范围。
* Max_used_connections 服务器启动后已经同时使用的连接的最大数量。
* Not_flushed_delayed_rows 等待写入INSERT DELAY队列的行数。
* Open_files 打开的文件的数目。
* Open_streams 打开的流的数量(主要用于记录)。
* Open_table_definitions缓存的.frm文件数 
* Open_tables 当前打开的表的数量。
* Opened_files 系统打开过的文件总数
* Opened_table_definitions已缓存的.frm文件数 
* Opened_tables 已经打开的表的数量。如果Opened_tables较大，table_cache 值可能太小。
* Prepared_stmt_count 当前prepared statements的个数，最大数会由变量max_prepared_stmt_count控制 ，当DEALLOCATE PREPARE时，改状态值会减小
* QCACHE_free_blocks 查询缓存内自由内存块的数量。
* QCACHE_free_memory 用于查询缓存的自由内存的数量。
* QCACHE_hits 查询缓存被访问的次数。
* QCACHE_inserts 加入到缓存的查询数量。
* QCACHE_lowmem_prunes 由于内存较少从缓存删除的查询数量。
* QCACHE_not_cached 非缓存查询数(不可缓存，或由于query_cache_type设定值未缓存)。
* Qcache_queries_in_cache 登记到缓存内的查询的数量。
* Qcache_total_blocks 查询缓存内的总块数。
* Queries 被服务器执行的语句个数，包括存储过程里的语句，也包括show status之类的
* Questions 发往服务器的查询的数量。
* Rpl_status 完全复制的状态（这个变量只在MYSQL 4之后的版本中使用）。
* Select_full_join 没有使用索引的联接的数量。如果该值不为0,你应仔细检查表的索引。
* Select_full_range_join 在引用的表中使用范围搜索的联接的数量。
* Select_range 在第一个表中使用范围的联接的数量。一般情况不是关键问题，即使该值相当大。
* Select_range_check 在每一行数据后对键值进行检查的不带键值的联接的数量。如果不为0，你应仔细检查表的索引。
* Select_scan 对第一个表进行完全扫描的联接的数量。
* Slave_open_temp_tables 当前由从SQL线程打开的临时表的数量。
* Slave_running 如果该服务器是连接到主服务器的从服务器，则该值为ON。
* Slave_retried_transactions 启动后复制从服务器SQL线程尝试事务的总次数。
* Slow_launch_threads 创建时间超过slow_launch_time秒的线程数。
* Slow_queries 查询时间超过long_query_time秒的查询的个数。参见5.11.4节，“慢速查询日志”。
* Sort_merge_passes 排序算法已经执行的合并的数量。如果这个变量值较大，应考虑增加sort_buffer_size系统变量的值。
* Sort_range 在范围内执行的排序的数量。
* Sort_rows 已经排序的行数。
* Sort_scan 通过扫描表完成的排序的数量。
* Table_locks_immediate 立即获得的表的锁的次数。
* Table_locks_waited 不能立即获得的表的锁的次数。如果该值较高，并且有性能问题，你应首先优化查询，然后拆分表或使用复制。
* Tc_log_max_pages_used 对于mysqld在充当内部xa事务恢复的事务协调器时使用的日志的内存映射实现，此变量表示自服务器启动以来日志使用的最大页数 
* Tc_log_page_size用于XA恢复日志的内存映射实现的页面大小。默认值是使用getpagesize（）确定的。此变量未使用的原因与使用的tc_log_max_pages_所描述的原因相同。
* Tc_log_page_waits对于恢复日志的内存映射实现，每次服务器无法提交事务并且必须等待日志中的空闲页时，此变量都会递增 
* Threads_cached 线程的缓存值
* Threads_connected 当前打开的连接的数量
* Threads_created 创建用来处理连接的线程数。如果Threads_created较大，你可能要增加thread_cache_size值。缓存访问率的计算方法 Threads_created（新建的线程）/Connections（只要有线程连接，该值就增加）
* Threads_running 激活的（非睡眠状态）线程数
* Uptime 服务器已经运行的时间（以秒为单位）
* Uptime_since_flush_status 最近一次使用FLUSH STATUS 的时间（以秒为单位
* ssl_xxx 用于SSL连接的变量。


### 常用命令
* 查询返回的行数
show status like '%innodb_rows_read%'

* 插入成功的行数
show status like '%innodb_rows_inserted%'

* 更新成功的行数
show status like '%innodb_rows_updated%'

* 删除成功的行数
show status like '%innodb_rows_deleted%'

* 查看锁
    SELECT * FROM INFORMATION_SCHEMA.INNODB_LOCKS;
    show status like '%innodb_status%'
    #show status like '%Table%'

* 查看慢查询
show status like '%Slow%'

* 查看运行时间
show status like '%up%'

* 查看锁的时间分布
show status like'%innodb_row_lock%';

* 执行select的计数
show status like '%Com_select%'

* 执行insert的计数，批量插入算一次
show status like '%Com_insert%'

* 执行更新操作的计数
show status like '%Com_update%'

* 执行删除操作的计数
show status like '%Com_delete%'

* 提交事务计数
show status like '%Com_commit%'

* 回滚事务计数
show status like '%Com_rollback%'

* 查看警告信息
show warnings

* 查看MySQL本次启动后的运行时间(单位：秒)
show status like 'uptime';

* 查看select语句的执行数
show [global] status like 'com_select';

* 查看insert语句的执行数
show [global] status like 'com_insert';

* 查看update语句的执行数
show [global] status like 'com_update';

* 查看delete语句的执行数
show [global] status like 'com_delete';

* 查看试图连接到MySQL(不管是否连接成功)的连接数
show status like 'connections';

* 查看线程缓存内的线程的数量
show status like 'threads_cached';

* 查看当前打开的连接的数量
show status like 'threads_connected';

* 查看当前打开的连接的数量
show status like 'threads_connected';

* 查看创建用来处理连接的线程数。如果Threads_created较大，你可能要增加thread_cache_size值
show status like 'threads_created';

* 查看激活的(非睡眠状态)线程数
show status like 'threads_running';

* 查看立即获得的表的锁的次数
show status like 'table_locks_immediate';

* 查看不能立即获得的表的锁的次数。如果该值较高，并且有性能问题，你应首先优化查询，然后拆分表或使用复制
show status like 'table_locks_waited';

* 查看创建时间超过slow_launch_time秒的线程数
show status like 'slow_launch_threads';

* 查看查询时间超过long_query_time秒的查询的个数
show status like 'slow_queries';