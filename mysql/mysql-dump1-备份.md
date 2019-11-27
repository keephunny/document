http://www.cnblogs.com/martinzhang/p/3454337.html


    -B：指定多个库，在备份文件中增加建库语句和use语句
    --compact：去掉备份文件中的注释，适合调试，生产场景不用
    -A：备份所有库
    -F：刷新binlog日志
    --master-data：在备份文件中增加binlog日志文件名及对应的位置点
    -x  --lock-all-tables：锁表
    -l：只读锁表
    -d：只备份表结构
    -t：只备份数据
    --single-transaction：适合innodb事务数据库的备份
    InnoDB表在备份时，通常启用选项--single-transaction来保证备份的一致性，原理是设定本次会话的隔离级别为Repeatable read，来保证本次会话（也就是dump）时，不会看到其它会话已经提交了的数据。



mysqldump常用于数据库的备份与还原，在备份的过程中我们可以根据自己的实际情况添加以上任何参数

语法：mysqldump -u 用户名 -p 密码 数据库 > file.sql


实例：
* 导出整个数据库(包括数据库中的数据和创建表的语句)
mysqldump -u username -p dbname > filename.sql

* 导出数据库结构（不含数据,只包含创建表的语句)
mysqldump -u username -p -d dbname > filename.sql

* 导出数据库中的某张数据表(包含表数据和创建表语句)
mysqldump -u username -p dbname table1 table2 > filename.sql

* 导出数据库中的某张数据表的表结构（不含数据）
mysqldump -u username -p -d dbname tablename > filename.sql

* 指定字符集导出
mysqldump -uroot -p'123456'--default-character-set=utf-8 -B mydb > /tmp/mydb.sql

* 指定压缩命令压缩备份的MySQL数据
mysqldump -uroot -p'123456' mydb|gzip > /tmp/mydb.sql.gz

* 过滤mydqldump导出的mydb.sql文件的未注释部分.
egrep -v "#|\*|--|^$" /tmp/mydb.sql
grep -E -v "#|\/|^$|--" /tmp/mydb.sql
如果要导入数据可执行如下命令：
mysql -u username -p db_name < test_db.sql


* 还原数据库操作还可以使用以下方法：
    mysql> use db_name 
    mysql> source test_db.sql
    mysql source 防止乱码

* 数据库备份命令
    mysqldump -uroot -p --default-character-set=utf8 dbname > /root/dbname.sql
* 导入数据库
    mysql -uroot -p --default-character-set=utf8
    use dbname
    source /root/dbname.sql