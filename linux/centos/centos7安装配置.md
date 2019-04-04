
## 配置本地yum源
    mkdir /mnt/cdrom
    mount /dev/cdrom /mnt/cdrom

    yum更新
    yum clean all
    yum makecache

## 配置防火墙
    启动： systemctl start firewalld
    关闭： systemctl stop firewalld
    查看状态： systemctl status firewalld 
    开机禁用  ： systemctl disable firewalld
    开机启用  ： systemctl enable firewalld