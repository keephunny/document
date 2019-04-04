wget http://mirrors.aliyun.com/repo/Centos-7.repo

8.更改文件名称
mv Centos-7.repo CentOS-Base.repo

cd /etc/yum.repos.d/

9.执行yum源更新命令 
yum clean all 
yum makecache 
yum update 


https://www.cnblogs.com/muyunren/p/7221505.html

输入挂载命令：
mount -t auto /dev/cdrom /mnt/cdrom

#查看是否挂载成功
ls /mnt/cdrom

取消挂载命令


  umount /mnt/cdrom