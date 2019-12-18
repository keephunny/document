## mysql日志类型

###  错误日志：    log-err
###  查询日志：    log
###  慢查询日志:  log-slow-queries
###  更新日志:    log-update
###  二进制日志： log-bin 


## general_log

* show variables like 'general_log'; -- 查看日志是否开启
* set global general_log=on; -- 开启日志功能
* show variables like 'general_log_file'; -- 看看日志文件保存位置
* set global general_log_file='tmp/general.lg'; -- 设置日志文件保存位置
* show variables like 'log_output'; -- 看看日志输出类型 table或file
* set global log_output='table'; -- 设置输出类型为 table
* set global log_output='file'; -- 设置输出类型为file
文件太大，需要关闭日志在清除。



### my.cnf配置
* general_log=ON
* general_log_file=/tmp/mysql.log