

* show binary logs：查看日志列表
* mysqlbinlog mysql-bin.00001：查看二进制日志文件的内容
* mysqlbinlog -database 数据库名 mysql-bin.000001>xxx.txt：查看指定数据库日志内容
* mysqlbinlog -D mysql-bin.00001：禁止恢复过程中产生日志，避免循环产生日志。
* 输出中控制base-64 BinLog
    * never：在输出中显示base64编码的binlog语句
    * always：只要有可能，它将只显示binlog项，因此只有在专门调试时才使用。
    * decode-rows：把基于行的事件解码成一个sql语句，特别指定-verbose选项时。
    * auto：默认选项，当没有指定任何base64解码项时，将仅为某些事件类型打印binlog项，例如基于行的事件和格式描述事件。
* mysqlbinlog --debug-check mysql-bin.000001：显示额外的调试信息
* mysqlbinlog -o 10 mysql-bin.00001：跳过前10个条目
* mysqlbinlog mysql-bin.00001 >output.log：将日志内容保存到文件
* mysqlbinlog -r output.log mysql-bin.00001：同上
* mysqlbinlog -j n mysql-bin.00001：从一个特定位置读取
* mysqlbinlog --stop-position=n mysql-bin.00001：截止到一个特定的位置
* flush logs：刷新日志以清除binlog输出。如果二进制文件没有被正确的关闭时，将在输出中看到一个警告。
* mysqlbinlog --short-form mysql-bin.00001：在输出时只显示sql语句
* mysqlbinlog --start-datetime='yyyy-MM-dd hh:mm:ss' --stop-datetime='yyyy-MM-dd hh:mm:ss' mysql-bin.00001：指定读取的时间段
* mysqlbinlog -R -h x.x.x.x -p mysql-bin.00001：从远程服务器上读取二进制文件
    * -R：指定从远程服务器读取日志文件
    * -h：指定远程服务器ip
    * -p：输入密码

* mysqlbinlog mysql-bin.00001 | more：分页查看
* 增加 --base64-output=decode-rows –v 选项解析

* 由于binlog是二进制的，所以需要先转换成文本文件，一般可以采用Mysql自带的mysqlbinlog转换成文本。
    mysqlbinlog --no-defaults --base64-output='decode-rows' -d 数据库名 -v mysql-bin.00001 > output.log

```
    mysql> show binlog events\G;
    #指定查询 mysql-bin.00001 ：
    mysql> show binlog events in 'mysql-bin.00001'\G;
    #从pos点:100开始查起：
    mysql> show binlog events in 'mysql-bin.00001' from 100\G;
    # 从pos点:8224开始查起，查询10条
    mysql> show binlog events in 'mysql-bin.00001' from 100 limit 10\G;

```

在MySQL5.5以下版本使用mysqlbinlog命令时如果报错，就加上"--no-defaults"选项


show binlog events

mysql> show binlog events [IN 'log_name'] [FROM pos] [LIMIT [offset,] row_count];