# 安装keepalived的前置条件
1. 配置本地镜像yum源 
2. gcc 参考gcc安装手册  yum install -y gcc gcc-c++
3. nginx 参考nginx安装手册
4. popt-devel
5. openssl-devel

# 安装keepalive
## 安装popt-devel
    #来源本地yum源
    yum -y install popt-devel 
## 安装openssl-devel

    #来源本地yum源
    yum -y install openssl-devel
## 安装keepalived

    #已提供文件/usr/local/src/keepalived-1.2.2.tar.gz
    cd /usr/local/src/
    tar –zxvf keepalived-1.2.2.tar.gz
    cd keepalived-1.2.2
    ./configure --prefix=/usr/local/keepalived
    make && make install
## 将keepalived安装成linux系统服务

    mkdir /etc/keepalived 
    cp /usr/local/keepalived/etc/keepalived/keepalived.conf /etc/keepalived/  
    #复制 keepalived 服务脚本到默认的地址
    cp /usr/local/keepalived/etc/rc.d/init.d/keepalived /etc/init.d/  
    cp /usr/local/keepalived/etc/sysconfig/keepalived /etc/sysconfig/  
    ln -s /usr/local/sbin/keepalived /usr/sbin/  
    ln -s /usr/local/keepalived/sbin/keepalived /sbin/  
    #设置 keepalived 服务开机启动
    chkconfig keepalived on 
## 查看是否安装成功

    keepalived -v
    #输出 Keepalived v1.2.2 (05/13,2018)
    #启动：service keepalived start
    #停止：service keepalived stop
    #重启：service keepalived restart
# 配置keepalive
## keepalived+nginx高可用配置

    mater: 192.168.54.120
    backup: 192.168.54.121
    vip: 192.168.54.130
    nginx两台机器都是80端口
## keepalived配置主节点
MASTER 节点配置文件（192.168.54.120）

    # vi /etc/keepalived/keepalived.conf 
    global_defs {
        #标识本节点的字条串，通常为 hostname  
        router_id localhost.localdomain.120
    }
    vrrp_script chk_nginx {
        # 检测 nginx 状态的脚本路径  
        script "/etc/keepalived/nginx_check.sh"
        # #每2秒检测一次nginx的运行状态
        interval 2
        #失败一次，将自己的优先级-20
        weight -20
    }
    vrrp_instance VI_1 {
        # 主节点为 MASTER， 对应的备份节点为 BACKUP  
        state MASTER
        # 绑定虚拟 IP 的网络接口，与本机 IP 地址所在的网络接口相同
        interface ens33
        virtual_router_id 33
        # 本机 IP 地址  
        mcast_src_ip 192.168.54.120
        ## 节点优先级， 值范围 0-254， MASTER 要比 BACKUP 高  
        priority 100
        advert_int 1
        # 设置验证信息，两个节点必须一致 
        authentication {
            auth_type PASS
            auth_pass 1111
        }
        track_script {
            chk_nginx
        }
        # 虚拟 IP 池, 两个节点设置必须一样  
        virtual_ipaddress {
            192.168.54.130
        }
    }

## keepalived配置备节点

    global_defs {
        router_id localhost.localdomain.121
    }
    vrrp_script chk_nginx {
        script "/etc/keepalived/nginx_check.sh"
        interval 2
        weight -20
    }
    vrrp_instance VI_1 {
        state BACKUP
        interface ens33
        virtual_router_id 33
        mcast_src_ip 192.168.54.121
        priority 90
        advert_int 1
        authentication {
            auth_type PASS
            auth_pass 1111
        }
        track_script {
            chk_nginx
        }
        virtual_ipaddress {
            192.168.54.130
        }
    }
    
##  nginx_check服务检测脚本

    #脚本内容如下，并赋于执行权限
    #vi /etc/keepalived/nginx_check.sh
    #chmod +x /etc/keepalived/nginx_check.sh

    =`ps -C nginx –no-header |wc -l`
    if [ $A -eq 0 ];then
    /usr/local/nginx/sbin/nginx
    sleep 2
    if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then
        killall keepalived
    fi
    fi  
## 启动keepalived和nginx服务，同时启动两个节点

    service keepalived start
    ps -ef | grep keepalived

分别在两台机器上查看vip状态,输出命令 ip addr，其中有一台会绑定192.168.54.130的vip。 


https://blog.csdn.net/l1028386804/article/details/72801492
https://blog.csdn.net/xyang81/article/details/52556886
https://www.cnblogs.com/xiaoit/p/4499703.html
https://blog.csdn.net/gavid0124/article/details/53786561