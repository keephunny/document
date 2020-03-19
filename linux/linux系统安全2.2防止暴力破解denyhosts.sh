#!/bin/bash
# # 每分钟检测暴力破解

# */1 * * * * /bin/bash /opt/application/shell/denyhosts.sh
#echo 'ddd'>> /opt/application/shell/logs/denyhosts.txt


cat /var/log/secure|awk '/Failed/{print $(NF-3)}'|sort|uniq -c|awk '{print $2"=" $1;}' >/opt/application/shell/logs/denyhosts.txt

DEFINE=10
#HOSTSFILE='/opt/application/shell/logs//hosts.deny'
HOSTSFILE='/etc/hosts.deny'

for i in `cat /opt/application/shell/logs/denyhosts.txt`
    do
        IP=`echo $i  |awk -F "=" '{print $1}'`
        n=`echo $i |awk -F "=" '{print $2}'`
        if [ ${n} -gt $DEFINE ]; then
            a=`grep $IP $HOSTSFILE`
            if [ -z $a ] ;then
                 echo "sshd:$IP:deny" >>$HOSTSFILE
             fi
    fi
done
