```
    #!/bin/sh`
    #crontab -e 定时任务执行 service crond restart
    #10 0 * * * /opt/application/shell/autobak.sh
    #（EOF 是mysql开始的符号）


    #!/bin/sh
    #（EOF 是mysql开始的符号）


    table_current=$(date "+%Y%m")
    table_prev=$(date -d "-1 month" "+%Y%m")
    date=$(date "+%d")

    echo $table_current
    echo $table_prev
    echo $date


    #修改表名
    funRename(){
        mysql -uroot -p123456 <<EOF
        use cityll_tongling;
        alter table test$table_prev rename to test$table_current;
    EOF
    }



    #每月月初修改表名
    if test $date -eq 1
    then
    echo '月初'
    funRename
    fi


    mysql -uroot -p123456 <<EOF
            use cityll_tongling;
            #每天更新数据，省得造数据。保证系统每天都有数据
            update test set  insert_time=DATE_ADD(alarm_time,INTERVAL 1 day);

    EOF`

```