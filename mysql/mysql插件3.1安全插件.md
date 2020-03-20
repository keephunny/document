

### 插件介绍
https://dev.mysql.com/doc/refman/5.7/en/connection-control.html


 As of MySQL 5.7.17, MySQL Server includes a plugin library that enables administrators to introduce an increasing delay in server response to clients after a certain number of consecutive failed connection attempts. This capability provides a deterrent that slows down brute force attacks that attempt to access MySQL user accounts. The plugin library contains two plugins:

CONNECTION_CONTROL checks incoming connections and adds a delay to server responses as necessary. This plugin also exposes system variables that enable its operation to be configured and a status variable that provides rudimentary monitoring information.

The CONNECTION_CONTROL plugin uses the audit plugin interface (see Section 28.2.4.8, “Writing Audit Plugins”). To collect information, it subscribes to the MYSQL_AUDIT_CONNECTION_CLASSMASK event class, and processes MYSQL_AUDIT_CONNECTION_CONNECT and MYSQL_AUDIT_CONNECTION_CHANGE_USER subevents to check whether the server should introduce a delay before responding to client connection attempts.

CONNECTION_CONTROL_FAILED_LOGIN_ATTEMPTS implements an INFORMATION_SCHEMA table that exposes more detailed monitoring information for failed connection attempts.

从MySQL 5.7.17开始，MySQL Server包含了一个插件库，管理员可以通过这个插件库在连接尝试连续失败一定次数后，向客户端引入越来越大的服务器响应延迟。此功能提供了一种威慑，可以减缓试图访问MySQL用户帐户的暴力攻击。插件库包含两个插件：
连接控制检查传入的连接，并根据需要向服务器响应添加延迟。此插件还公开了使其操作得以配置的系统变量和提供基本监视信息的状态变量。



连接控制插件使用审计插件接口（见第28.2.4.8节“编写审计插件”）。为了收集信息，它订阅MYSQL_AUDIT_CONNECTION_CLASSMASK事件类，并处理MYSQL_AUDIT_CONNECTION和MYSQL_AUDIT_CONNECTION_CHANGE_USER子事件，以检查服务器在响应客户端连接尝试之前是否应该引入延迟。



连接控制失败的登录尝试实现一个信息架构表，该表为失败的连接尝试提供更详细的监视信息。


* CONNECTION_CONTROL
    用来控制登录失败的次数及延迟响应时间
* CONNECTION_CONTROL_FAILED_LOGIN_ATTEMPTS
    该表将登录失败的操作记录至IS库中

    
### 插件配置
```
#配置文件加载插件
[root@10 mysql]# vim /etc/my.cnf
    [mysqld]
        plugin-load-add=connection_control.so
        connection-control= FORCE
        connection-control-failed-login-attempts=FORCE
        connection_control_min_connection_delay=1000
        connection_control_max_connection_delay=86400
        connection_control_failed_connections_threshold=3
[root@10 mysql]# service mysql restart
#命令行加载插件
[root@10 mysql]# mysql -uroot -p
mysql> INSTALL PLUGIN CONNECTION_CONTROL SONAME 'connection_control.so';
mysql> INSTALL PLUGIN CONNECTION_CONTROL_FAILED_LOGIN_ATTEMPTS SONAME 'connection_control.so';
#验证是否成功
mysql> SELECT PLUGIN_NAME, PLUGIN_STATUS FROM INFORMATION_SCHEMA.PLUGINS WHERE PLUGIN_NAME LIKE 'connection%';
    +------------------------------------------+---------------+
    | PLUGIN_NAME                              | PLUGIN_STATUS |
    +------------------------------------------+---------------+
    | CONNECTION_CONTROL                       | ACTIVE        |
    | CONNECTION_CONTROL_FAILED_LOGIN_ATTEMPTS | ACTIVE        |
    +------------------------------------------+---------------+
    2 rows in set (0.01 sec)
mysql> show plugins;
    +------------------------------------------+----------+--------------------+-----------------------+---------+
    | Name                                     | Status   | Type               | Library               | License |
    +------------------------------------------+----------+--------------------+-----------------------+---------+
    | CONNECTION_CONTROL                       | ACTIVE   | AUDIT              | connection_control.so | GPL     |
    | CONNECTION_CONTROL_FAILED_LOGIN_ATTEMPTS | ACTIVE   | INFORMATION SCHEMA | connection_control.so | GPL     |
    +------------------------------------------+----------+--------------------+-----------------------+---------+
44 rows in set (0.00 sec)
mysql> show variables like 'connection_control%';
    +-------------------------------------------------+------------+
    | Variable_name                                   | Value      |
    +-------------------------------------------------+------------+
    | connection_control_failed_connections_threshold | 3          |
    | connection_control_max_connection_delay         | 2147483647 |
    | connection_control_min_connection_delay         | 1000       |
    +-------------------------------------------------+------------+
    3 rows in set (0.00 sec)

```

* connection_control_failed_connections_threshold
失败尝试的次数，默认为3，表示当连接失败3次后启用连接控制，0表示不开启
* connection_control_max_connection_delay
响应延迟的最大时间，默认约25天
* connection_control_min_connection_delay
响应延迟的最小时间，默认1000毫秒

### 验证插件

使用错误密码连续登录三次
```
mysql> show global status like 'Connection_control_delay_generated';
    +------------------------------------+-------+
    | Variable_name                      | Value |
    +------------------------------------+-------+
    | Connection_control_delay_generated | 1     |
    +------------------------------------+-------+
#查看登录错误信息
mysql> select * from information_schema.connection_control_failed_login_attempts;
    +-------------------------+-----------------+
    | USERHOST                | FAILED_ATTEMPTS |
    +-------------------------+-----------------+
    | 'rootmjh'@'127.0.0.1'   |               1 |
    | 'root'@'%'              |               5 |
    +-------------------------+-----------------+
    2 rows in set (0.00 sec)

```

### 卸载插件
```
mysql> uninstall plugin CONNECTION_CONTROL;
mysql> uninstall plugin CONNECTION_CONTROL_FAILED_LOGIN_ATTEMPTS;
mysql> UNINSTALL SONAME CONNECTION_CONTROL;



```