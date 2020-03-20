
### 插件下载

mysql5.6.42
[audit-plugin-mysql-5.6-1.1.7-805](./resources/audit-plugin-mysql-5.6-1.1.7-805-linux-x86_64.zip)



插件下载地址
https://bintray.com/mcafee/mysql-audit-plugin/release/1.1.7-805#files

### 插件安装配置
1. 查看插件安装位置
```
mysql> show variables like 'plugin_dir';
    +---------------+--------------------------+
    | Variable_name | Value                    |
    +---------------+--------------------------+
    | plugin_dir    | /usr/lib64/mysql/plugin/ |
    +---------------+--------------------------+
```
2. 上传插件
```
上传插件到/usr/lib64/mysql/plugin/libaudit_plugin.so
chmod a+x libaudit_plugin.so
```

3. 安装插件
```
mysql> install plugin audit soname 'libaudit_plugin.so';
Query OK, 0 rows affected (0.06 sec) 
```
如果有报错参考error

4. 配置参数
```
[root@localhost mysql]# vim /etc/my.cnf
    audit_json_file = on
    plugin-load=AUDIT=libaudit_plugin.so
    audit_record_cmds='insert,delete,update,create,drop,alter,grant,truncate'
    #audit_json_log_fil= 默认在mysql安装目录mysql-audit.json 
    #audit_record_objs=‘db.*’　　  #设置需要监控的数据库名称和表名，默认全部 
    #audit_whitelist_users　　　　#用户白名单
[root@localhost mysql]# service mysql restart

```


### 插件验证

1. 验证是否安装成功
```
mysql> SET GLOBAL audit_json_file=ON;
mysql> show plugins;
+----------------------------+----------+--------------------+--------------------+---------+
| Name                       | Status   | Type               | Library            | License |
+----------------------------+----------+--------------------+--------------------+---------+
| binlog                     | ACTIVE   | STORAGE ENGINE     | NULL               | GPL     |
| mysql_native_password      | ACTIVE   | AUTHENTICATION     | NULL               | GPL     |
| AUDIT                      | ACTIVE   | AUDIT              | libaudit_plugin.so | GPL     |
+----------------------------+----------+--------------------+--------------------+---------+
mysql> show global status like '%audit%';
+------------------------+-----------+
| Variable_name          | Value     |
+------------------------+-----------+
| Audit_protocol_version | 1.0       |
| Audit_version          | 1.1.7-805 |
+------------------------+-----------+
2 rows in set (0.00 sec)

```
2. 查看审计结果
```
[root@localhost mysql]# tail -fn100 mysql-audit.json
    {
        "msg-type": "activity",
        "date": "1578011039087",
        "thread-id": "1",
        "query-id": "5",
        "user": "root",
        "priv_user": "root",
        "ip": "",
        "host": "localhost",
        "connect_attrs": {
            "_os": "Linux",
            "_client_name": "libmysql",
            "_pid": "7682",
            "_client_version": "5.6.42",
            "_platform": "x86_64",
            "program_name": "mysql"
        },
        "pid": "7682",
        "os_user": "root",
        "appname": "mysql",
        "rows": "1",
        "status": "0",
        "cmd": "insert",
        "objects": [{
            "db": "test",
            "name": "test1",
            "obj_type": "TABLE"
        }],
        "query": "insert into test1 values ('18')"
    }
```

### 插件卸载
在 my.cnf 中 [mysqld] 下添加 audit_uninstall_plugin=1，卸载成功后在删除这行。   
UNINSTALL SONAME 'audit';   
set audit_uninstall_plugin=on;  



### 参数说明
1. audit_json_file：是否开启audit功能。

2. audit_json_log_file：记录文件的路径和名称信息（默认放在mysql数据目录下）。

3. audit_record_cmds：audit记录的命令，默认为记录所有命令。可以设置为任意dml、dcl、ddl的组合。如：audit_record_cmds=select,insert,delete,update。还可以在线设置set global audit_record_cmds=NULL。(表示记录所有命令)

4. audit_record_objs：audit记录操作的对象，默认为记录所有对象，可以用SET GLOBAL audit_record_objs=NULL设置为默认。也可以指定为下面的格式：audit_record_objs=,test.*,mysql.*,information_schema.*。

5. audit_whitelist_users：用户白名单。

1. server_audit_output_type：指定日志输出类型，可为SYSLOG或FILE

1. server_audit_logging：启动或关闭审计

1. server_audit_events：指定记录事件的类型，可以用逗号分隔的多个值(connect,query,table)，如果开启了查询缓存(query cache)，查询直接从查询缓存返回数据，将没有table记录

1. server_audit_file_path：如server_audit_output_type为FILE，使用该变量设置存储日志的文件，可以指定目录，默认存放在数据目录的server_audit.log文件中

1. server_audit_file_rotate_size：限制日志文件的大小

1. server_audit_file_rotations：指定日志文件的数量，如果为0日志将从不轮转

1. server_audit_file_rotate_now：强制日志文件轮转

1. server_audit_incl_users：指定哪些用户的活动将记录，connect将不受此变量影响，该变量比server_audit_excl_users优先级高

1. server_audit_syslog_facility：默认为LOG_USER，指定facility

1. server_audit_syslog_ident：设置ident，作为每个syslog记录的一部分

1. server_audit_syslog_info：指定的info字符串将添加到syslog记录

1. server_audit_syslog_priority：定义记录日志的syslogd priority

1. server_audit_excl_users：该列表的用户行为将不记录，connect将不受该设置影响

1. server_audit_mode：标识版本，用于开发测试







### 参考地址    
https://www.cnblogs.com/cangyuefeng/p/10031124.html 
https://www.cnblogs.com/waynechou/p/mysql_audit.html    
https://github.com/mcafee/mysql-audit/wiki/Configuration    

https://www.cnblogs.com/cangyuefeng/p/10031124.html 

MySQL社区版没有自带的设计功能或插件。调研发现MariaDB的audit plugin 同样适用于MySQL，支持更细粒度的审计，比如只审计DDL操作，满足我们的需求。因为最近测试环境的某表结构经常性的被变更且数据被清空的情况，所以引入MariaDB的插件对DDL进行审计



### error

mysql> INSTALL PLUGIN AUDIT SONAME 'libaudit_plugin.so';
ERROR 1126 (HY000): Can't open shared library '/usr/lib64/mysql/plugin/libaudit_plugin.so' (errno: 2 /usr/lib64/mysql/plugin/libaudit_plugin.so: undefined symbol: my_hrtime)


mysql> INSTALL PLUGIN AUDIT SONAME 'libaudit_plugin.so';
ERROR 1123 (HY000): Can't initialize function 'AUDIT'; Plugin initialization function failed.



[root@localhost mysql]# whereis mysqld
mysqld: /usr/sbin/mysqld /usr/share/man/man8/mysqld.8.gz



[root@localhost plugin]# ./offset-extract.sh /usr/sbin/mysqld
//offsets for: /usr/sbin/mysqld (5.6.42)
{"5.6.42","d5f3a29fec0bd38796d32b70aad5602e", 6992, 7040, 4000, 4520, 72, 2704, 96, 0, 32, 104, 136, 7128, 4392, 2800, 2808, 2812, 536, 0, 0, 6360, 6384, 6368},



[root@localhost plugin]# ./offset-extract.sh /usr/sbin/mysqld
ERROR: gdb not found. Make sure gdb is installed and on the path.
解决
[root@localhost plugin]# yum install gdb
