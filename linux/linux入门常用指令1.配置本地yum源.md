
### 创建光盘挂载点
```
[root@localhost /]# mkdir /mnt/cdrom
```
### 挂载光盘
```
#挂载光盘
[root@localhost /]# mount /dev/cdrom /mnt/cdrom/
#卸载光盘
[root@localhost /]# umount /mnt/cdrom/
    mount: /dev/sr0 写保护，将以只读方式挂载
#如果没有光盘可以直接挂载ios文件
[root@localhost /]# mount -o loop centos.iso /mnt/cdrom
#查看挂载成功
[root@localhost /]# ll /mnt/cdrom/
```
### 配置本地yum源
```
[root@localhost /]# cd /etc/yum.repos.d/
[root@localhost /]# mv CentOS-Base.repo CentOS-Base.repo.bak
[root@localhost /]# vi CentOS-Base.repo        
    [base]
    name=base
    baseurl=file:///mnt//cdrom
    enabled=1
    gpgcheck=0
```
### 验证是否生效
```
[root@localhost /]# yum clean all
[root@localhost /]# yum list
[root@localhost /]# yum install vim

```

### 常用yum包
```
[root@localhost /]# yum install vim
[root@localhost /]# yum install net-tools
[root@localhost /]# yum install lzrsz
[root@localhost /]# yum install wget
[root@localhost /]# yum install zip unzip
[root@localhost /]# yum install ntpdate
[root@localhost /]# yum install gcc-c++
[root@localhost /]# yum install pcre pcre-devel
[root@localhost /]# yum install zlib zlib-devel
[root@localhost /]# yum install openssl openssl--devel
```