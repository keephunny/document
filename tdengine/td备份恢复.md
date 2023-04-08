taosdump 是一个支持从运行中的 TDengine 集群备份数据并将备份的数据恢复到相同或另一个运行中的 TDengine 集群中的工具应用程序。

taosdump 可以用数据库、超级表或普通表作为逻辑数据单元进行备份，也可以对数据库、超级 表和普通表中指定时间段内的数据记录进行备份。使用时可以指定数据备份的目录路径，如果 不指定位置，taosdump 默认会将数据备份到当前目录。

如果指定的位置已经有数据文件，taosdump 会提示用户并立即退出，避免数据被覆盖。这意味着同一路径只能被用于一次备份。 如果看到相关提示，请小心操作。

taosdump 是一个逻辑备份工具，它不应被用于备份任何原始数据、环境设置、 硬件信息、服务端配置或集群的拓扑结构。taosdump 使用 Apache AVRO 作为数据文件格式来存储备份数据。依赖taosTools。

```
Avro 是一个数据序列化系统，设计用于支持大 批量数据交换的应用。它的主要特点有：支持二进制序列化方式，可以便捷，快速地处理大量数据；动态语言友好，Avro 提供的机制使动态语言可以方便地处理 Avro 数据。
```

### taosdump备份数据

1. 备份所有数据库：指定 -A 或 --all-databases 参数；

2. 备份多个指定数据库：使用 -D db1,db2,... 参数；

3. 备份指定数据库中的某些超级表或普通表：使用 dbname stbname1 stbname2 tbname1 tbname2 ... 参数，注意这种输入序列第一个参数为数据库名称，且只支持一个数据库，第二个和之后的参数为该数据库中的超级表或普通表名称，中间以空格分隔；

4. 备份系统 log 库：TDengine 集群通常会包含一个系统数据库，名为 log，这个数据库内的数据为 TDengine 自我运行的数据，taosdump 默认不会对 log 库进行备份。如果有特定需求对 log 库进行备份，可以使用 -a 或 --allow-sys 命令行参数。

5. “宽容”模式备份：taosdump 1.4.1 之后的版本提供 -n 参数和 -L 参数，用于备份数据时不使用转义字符和“宽容”模式，可以在表名、列名、标签名没使用转义字符的情况下减少备份数据时间和备份数据占用空间。如果不确定符合使用 -n 和 -L 条件时请使用默认参数进行“严格”模式进行备份。


### taosdump恢复数据

taosdump 备份数据恢复指定路径下的数据文件：使用 -i 参数加上数据文件所在路径。如前面提及，不应该使用同一个目录备份不同数据集合，也不应该在同一路径多次备份同一数据集，否则备份数据会造成覆盖或多次备份。

### taosdump示例

```
#备份数据库
[root@localhosl]# taosdump -h hostname -u root -ptaosdata -o /tmp/dump/dbName -T 8 -D dbName
    taosdump version 2.2.6, commit: c64858f
    host: platform-iot
    user: root
    port: 0
    outpath: /tmp/dump/dbName
    inpath: 
    resultFile: ./dump_result.txt
    all_databases: false
    databases: false
    databasesSeq: (null)
    schemaonly: false
    with_property: true
    answer_yes: false
    avro codec: snappy
    data_batch: 16383
    thread_num: 8
    allow_sys: false
    escape_char: true
    loose_mode: false
    isDumpIn: false
    arg_list_len: 2
    arg_list[0]: shy_iot
    arg_list[1]: s_00000000022
    OK: Database: shy_iot exists
    INFO: Getting table(s) count of db (shy_iot) ...
    INFO: Getting tables' number of super table (s_00000000022) ...
    INFO: dumpNtbOfStbByThreads() LN10329, shy_iot's s_00000000022's total normal table count: 10
    INFO: connection: 0x55575c10e5f0 is dumping out schema of sub-table(s) of s_00000000022 
    INFO: Getting tables' number of super table (s_00000000022) ...
    INFO: The number of tables of s_00000000022 is 10!
    INFO: connection 0x55575c10e5f0 fetched 10% of s_00000000022' tbname
    INFO: connection 0x55575c10e5f0 fetched 100% of s_00000000022' tbname
    OK: total 10 sub-table's name of stable: s_00000000022 fetched
    INFO: The number of tables of s_00000000022 be filled is 10!
    INFO: connection 0x55575c10e5f0 is dumping out schema:0% of s_00000000022
    INFO: connection 0x55575c10e5f0 is dumping out schema:10% of s_00000000022
    INFO: connection 0x55575c10e5f0 is dumping out schema:100% of s_00000000022
    OK: total 10 sub table(s) of stable: s_00000000022 dumped
    .INFO: 100% of c_22000000001
    .INFO: 100% of c_22000000003
    .INFO: 100% of c_22000000002
    OK: table: s_00000000022 dumped
    OK: 3204458 row(s) dumped out!

#备份超级表
[root@localhosl]# taosdump -h hostname -u root -ptaosdata -o /tmp/dump/dbName/stableName -T 8  dbName stableName
#备份普通表
[root@localhosl]# taosdump -h hostname -u root -ptaosdata -o /tmp/dump/dbName/tableName -T 8  dbName tableName
#恢复数据库
[root@localhosl]# taosdump -h hostname -uroot -ptaosdata -i /tmp/dump/dbName -T 8

#恢复表
[root@localhosl]# taosdump -h hostname -uroot -ptaosdata -i /tmp/dump/dbName/tableName -T 8

-h 节点hostname
-u root -ptaosdata 用户名、密码
-o 输出文件目录，一定要先创建
-i 输入文件路径，一定要先创建
-T 8 并发8个进程
-D dbName 备份数据库名称
```

* 恢复数据 支持跨节点恢复，td版本要一致
* 备份子表需要与超级表同时备份，避免还原时报错
* 数据按Avro序列化备份

```
备份后的结构
data0  
dbs.sql  
dbName.3334022932148.avro-tbtags
	dbs.sql  
	shy_iot.3334022932148.avro-tbtags
	data0
		dbName.3334022932365.2.avro
        dbName.3334022932367.1.avro
        dbName.3334022932373.3.avro
```

### 自动备份

自动备份脚本

```
#!/bin/bash

#自动备份td数据库，并压缩存储，删除前30天的压缩文件 autobak.sh
time1=$(date "+%Y%m%d")
timeyesterday=$(date -d "1 day ago" +%Y%m%d)
timepreday=$(date -d "30 day ago" +%Y%m%d)
echo $time1
path=/usr/local/src/dbName/$time1
mkdir -p $path
taosdump -h hostName -uroot -ptaosdata -o $path  -T8 dbName

zip -r $path.zip $path
rm -rf /usr/local/src/dbName/$timeyesterday
rm -rf /usr/local/src/dbName/$timepreday.zip
```



定时任务

```
[root@localhosl]# crontab -e
	#每天两点时
    0 2 * * * sh /usr/local/taos/shell/autobak.sh >>/tmp/tdlog.log 2>&1
#查看定时任务
[root@localhost]# crontab -l
#重启服务
[root@localhost]# service crond restart
```



### 问题

* 不支持A库备份文件，恢复到指定B库，指定新库名。使数据恢复更灵活。
* 不支持增量备份。




### 详细命令行参数

```
Usage: taosdump [OPTION...] dbname [tbname ...]
  or:  taosdump [OPTION...] --databases db1,db2,...
  or:  taosdump [OPTION...] --all-databases
  or:  taosdump [OPTION...] -i inpath
  or:  taosdump [OPTION...] -o outpath
  -h, --host=HOST            Server host dumping data from. Default is
                             localhost.
  -p, --password             User password to connect to server. Default is
                             taosdata.
  -P, --port=PORT            Port to connect
  -u, --user=USER            User name used to connect to server. Default is
                             root.
  -c, --config-dir=CONFIG_DIR   Configure directory. Default is /etc/taos
  -i, --inpath=INPATH        Input file path.
  -o, --outpath=OUTPATH      Output file path.
  -r, --resultFile=RESULTFILE   DumpOut/In Result file path and name.
  -a, --allow-sys            Allow to dump system database
  -A, --all-databases        Dump all databases.
  -D, --databases=DATABASES  Dump inputted databases. Use comma to separate
                             databases' name.
  -N, --without-property     Dump database without its properties.
  -s, --schemaonly           Only dump tables' schema.
  -y, --answer-yes           Input yes for prompt. It will skip data file
                             checking!
  -d, --avro-codec=snappy    Choose an avro codec among null, deflate, snappy,
                             and lzma.
  -S, --start-time=START_TIME   Start time to dump. Either epoch or
                             ISO8601/RFC3339 format is acceptable. ISO8601
                             format example: 2017-10-01T00:00:00.000+0800 or
                             2017-10-0100:00:00:000+0800 or '2017-10-01
                             00:00:00.000+0800'
  -E, --end-time=END_TIME    End time to dump. Either epoch or ISO8601/RFC3339
                             format is acceptable. ISO8601 format example:
                             2017-10-01T00:00:00.000+0800 or
                             2017-10-0100:00:00.000+0800 or '2017-10-01
                             00:00:00.000+0800'
  -B, --data-batch=DATA_BATCH   Number of data per query/insert statement when
                             backup/restore. Default value is 16384. If you see
                             'error actual dump .. batch ..' when backup or if
                             you see 'WAL size exceeds limit' error when
                             restore, please adjust the value to a smaller one
                             and try. The workable value is related to the
                             length of the row and type of table schema.
  -I, --inspect              inspect avro file content and print on screen
  -L, --loose-mode           Using loose mode if the table name and column name
                             use letter and number only. Default is NOT.
  -n, --no-escape            No escape char '`'. Default is using it.
  -T, --thread-num=THREAD_NUM   Number of thread for dump in file. Default is
                             8.
  -C, --cloud=CLOUD_DSN      specify a DSN to access TDengine cloud service
  -R, --restful              Use RESTful interface to connect TDengine
  -t, --timeout=SECONDS      The timeout seconds for websocket to interact.
  -g, --debug                Print debug info.
  -?, --help                 Give this help list
      --usage                Give a short usage message
  -V, --version              Print program version

Mandatory or optional arguments to long options are also mandatory or optional
for any corresponding short options.
Report bugs to <support@taosdata.com>.
```



   

   

   