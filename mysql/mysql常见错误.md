skip-name-resolve 参数的目的是不再进行反解析（ip不反解成域名），这样可以加快数据库的反应时间。

修改配置文件添加并需要重启：

[mysqld] 

skip-name-resolve

添加后发现错误日志有警告信息
131127 11:09:12 [Warning] 'user' entry 'root@cvs' ignored in --skip-name-resolve mode.
131127 11:09:12 [Warning] 'user' entry '@cvs' ignored in --skip-name-resolve mode.
131127 11:09:12 [Warning] 'proxies_priv' entry '@ root@cvs' ignored in --skip-name-resolve mode

启用后，在mysql的授权表中就不能使用主机名了，只能使用IP ，出现此警告是由于mysql.user表中已经存在有相关的帐号信息。 我们把它删除就好了。
mysql>use mysql; 
mysql> delete  from user where HOST='cvs'; 
Query OK, 2 rows affected (0.00 sec)   

然后删除表mysql.proxies_priv中和cvs类似与具体域名有关的行,方法同上。

重启MYSQL ，发现警告已经没有啦。 



### blocked because of many connection errors; unblock with 'mysqladmin flush-ho

mysqladmin  -uroot -p  -h192.168.1.1 flush-hosts