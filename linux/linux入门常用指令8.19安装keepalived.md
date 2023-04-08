
### 下载安装包
```
#查看是否已安装过
[root@localhost ]#  yum install -y curl gcc openssl-devel libnl3-devel net-snmp-devel
[root@localhost ]#  yum install -y keepalived

#源码安装
[root@localhost ]# tar xvf keepalived-2.2.7.tar.gz
[root@localhost ]# cd keepalived-2.2.7.tar.gz
[root@localhost ]# ./configure --prefix=/usr/local/keepalived
[root@localhost ]# make && make install
[root@localhost ]# cp keepalived/etc/init.d/keepalived /etc/init.d/keepalived
[root@localhost ]# ll /etc/init.d/ | grep keepalived
[root@localhost ]# 
[root@localhost ]# 
```

### 前置配置

```

[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
```

### 安装程序
```
[root@localhost ]#  yum install -y curl gcc openssl-devel libnl3-devel net-snmp-devel
[root@localhost ]#  yum install -y keepalived
[root@localhost ]#  


```

### 卸载程序
```
#源码安装卸载
[root@localhost ]#  
[root@localhost ]# 
```


### 配置调试

### 开机自启
启动服务进程：systemctl start   
[root@localhost ~]# systemctl start keepalived
[root@localhost ~]# systemctl enable keepalived
[root@localhost ~]# systemctl restart keepalived
[root@localhost ~]# systemctl status keepalived

### 目录文件

```
/usr/sbin/keepalived	二进制程序
/etc/keepalived/keepalived.conf	配置文件
/usr/lib/systemd/system/keepalived.service	服务文件
```


### 高可用配置
```
#主节点1
[root@localhost ]# vim /etc/keepalived/keepalived.conf 
    ! Configuration File for keepalived
    global_defs {
        router_id DE_HA
    }

    vrrp_instance VI_1 {
        state MASTER
        #网卡名称
        interface ens33
        virtual_router_id 51
        priority 101
        advert_int 1
        authentication {
            auth_type PASS
            auth_pass 1111
        }
        #虚拟IP
        virtual_ipaddress {
            192.168.121.200
        }
    }
[root@localhost ]# ip a
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 00:0c:29:ad:6f:a1 brd ff:ff:ff:ff:ff:ff
    inet 192.168.121.130/24 brd 192.168.121.255 scope global noprefixroute ens33
       valid_lft forever preferred_lft forever
    inet 192.168.121.200/32 scope global ens33
[root@localhost ]#  
[root@localhost ]#  

#备节点2
[root@localhost ]# vim /etc/keepalived/keepalived.conf 
    ! Configuration File for keepalived
    global_defs {
        router_id DE_HA
    }

    vrrp_instance VI_1 {
        state BACKUP
        #网卡名称
        interface ens33
        virtual_router_id 51
        priority 102
        advert_int 1
        authentication {
            auth_type PASS
            auth_pass 1111
        }
        virtual_ipaddress {
            192.168.121.200
        }
    }
[root@localhost ]#  ip a
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 00:0c:29:49:63:0f brd ff:ff:ff:ff:ff:ff
    inet 192.168.121.131/24 brd 192.168.121.255 scope global noprefixroute ens33
[root@localhost ]#  
[root@localhost ]#  
[root@localhost ]#  


```


### 常见错误

```
# 开启多播
ip link set multicast on dev ens33
```


 