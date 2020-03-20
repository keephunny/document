

### 查看插件位置
```
mysql> show global variables  like 'plugin_dir';
+---------------+--------------------------+
| Variable_name | Value                    |
+---------------+--------------------------+
| plugin_dir    | /usr/lib64/mysql/plugin/ |
+---------------+--------------------------+
1 row in set (0.00 sec)

```

### 拷贝插件到指定目录
将下载的插件拷贝到plugin_dir指定的目录(/usr/lib64/mysql/plugin/)，添加可执行权限。

### 安装插件
```
mysql> install plugin audit soname 'libaudit_plugin.so';
Query OK, 0 rows affected (2.00 sec)
```


### 卸载插件

mysql> uninstall plugin CONNECTION_CONTROL；