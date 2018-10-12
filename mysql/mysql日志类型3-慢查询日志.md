## 慢查询日志
mysql的慢查询日志是用来记录mysql中响应时间超过阈值(long_query_time)的语句，则会被记录到日志中，日志可以选择存入文件或表。一般不建议启动该参数，因为开启慢查询日志会或多或少带来一定的性能影响。

## 开启配置
mysql默认慢查询日志是没有开启的
```
    show variables like '%slow_query_log%';
    | slow_query_log      | OFF                                      |
    | slow_query_log_file | /usr/local/mysql/data/localhost-slow.log |
```    
### 临时开启
    set global slow_query_log=1;
    set global log_output='FILE';
    默认日志文件位置
    show variables like 'slow_query_log_file';
### 永久开启
vim /etc/my.cnf
```
    #开启
    slow_query_log=ON
    #慢日志存入文件
    slow_query_log_file=/usr/local/mysql/data/slow.log
    #慢查询阈值  
    long_query_time=1
```
### 慢查询日志存储
日志记录到系统的专用日志表中，要比记录到文件耗费更多的系统资源，因此对于需要启用慢查询日志，又需要能够获得更高的系统性能，那么建议优先记录到文件。查看当前存储方式 show variables like '%log_output%';
#### 存储file
默认是存储到file，不需要设置

    set GLOBAL log_output='file';
    set GLOBAL slow_query_log_file='/usr/local/mysql/data/slow.log';
    #或者在/etc/my.cnf
    log_output=file
    slow_query_log_file=/usr/local/mysql/data/slow.log

记得给mysql/data/slow.log加mysql的读写权限 chmod -R a+w /usr/local/mysql/
### 存储table
慢查询日志存到mysql.slow_log表中

    set GLOBAL log_output='table';
    #或者在/etc/my.cnf
    log_output=table
log_output=file|table|none
### 重要：修改my.cnf都需要重启mysql服务
service mysqld restart

 set global 参数  后需要重新连接或新开一个会话才能看到修改值

## 配置参数 
* slow_query_log：是否开启慢查询日志，1表示开启，0表示关闭。
* slow-query-log-file：慢查询日志存储路径。可以不设置该参数，系统则会默认给一个缺省的文件host_name-slow.log
* long_query_time：慢查询阈值，当查询时间多于设定的阈值时，记录日志。
* log_queries_not_using_indexes：未使用索引的查询也被记录到慢查询日志中（可选项）。
* log_slow_admin_statements：是否将慢管理语句例如ANALYZE TABLE和ALTER TABLE等记入慢查询日志
* log_output：日志存储方式。
* log_output='FILE'：表示将日志存入文件，默认值是'FILE'。
* log_output='TABLE'：表示将日志存入数据库，这样日志信息就会被写入到mysql.slow_log表中。 
* Slow_queries：查询有多少条慢查询记录。 

    show variables like '%参数%' 上面的参数，就可以查询具体的值  
    set global 参数=值 可以设置参数的值或者在my.cnf配置 

## 日志字段说明
* start_time:
* user_host:
* query_time:查询语句的查询时间
* lock_time:锁表时间
* rows_sent:
* rows_examined:扫描过的行数
* db:
* last_insert_id:
* insert_id:
* server_id:
* sql_text:慢查询sql语句

 ## 日志分析工具mysqldumpslow
如果要手工分析日志，查找、分析SQL，效率比较低，MySQL自带的慢查询日志分析工具mysqldumpslow分析日志。

    mysqldumpslow  参数 日志文件
* -s, 是表示按照何种方式排序
    * c: 访问计数
    * l: 锁定时间
    * r: 返回记录
    * t: 查询时间
    * al:平均锁定时间
    * ar:平均返回记录数
    * at:平均查询时间
* -t, 是top n的意思，即为返回前面多少条的数据；
* -g, 后边可以写一个正则匹配模式，大小写不敏感的；


    mysqldumpslow -t 10 -s t -g  "like" /usr/local/mysql/data/slow.log

    Count: 1  Time=1.91s (1s)  Lock=0.00s (0s)  Rows=1000.0 (1000), vgos_dba[vgos_dba]@[127.0.0.1]


    Count:语句出现的次数；
    Time：执行最长时间和累计总耗费时间；
    Lock： 等待锁最长时间
    Rows：发送给客户端最多的行数和累计发送给客户端的行数
    