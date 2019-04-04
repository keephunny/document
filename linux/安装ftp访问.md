1. 安装FTP命令
yum install ftp -y




#!/bin/bash
FTP_USER=ftpuser                #ftp用户名
FTP_PASS=ftpuserpassword        #ftp密码
FTP_IP=xxx.xxx.xxx.xxx          #ftp地址

ftp -v -n $FTP_IP << END
user $FTP_USER $FTP_PASS
type binary
cd $FTP_backup
delete $OldData
delete $OldWeb
put $DataBakName
put $WebBakName
bye







#!/bin/bash
ftp -n<<!
open 192.168.220.129
user ls toor
binary
hash
cd /path/to/backup
lcd /path/to/need/backup
prompt
mkdir `date +"%Y%m%d"`
cd `date +"%Y%m%d"`
mput *
close
bye
!

open行--要备份到的主机IP
user行--ls为要备份到的主机的用户名，toor为前边用户对应的密码
binary行--使用二进制模式进行传输
hash--每传1k的大小输出一个#号，可不用
cd行--备份文件要上传到的目录
lcd行--备份文件在本地的目录
prompt行--使后边mput上传多个文件时不用每次都输入“yes”进行确认
mkdir行--在备份主机按日期创建备份目录，在这里主要是演示此模式中可和ssh一般使用mkdir和反引号
cd行--进入备份文件要上传到的目录
mput行--上传本地目录文件夹下的所有文件，也可以写上具体文件名
close行--关闭ftp连接
bye行--退出ftp