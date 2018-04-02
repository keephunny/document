CentOS 7中firewall防火墙是一个非常的强大的功能，在CentOS 6.5中在iptables防火墙中进行了升级了。  

The dynamic firewall daemon firewalld provides a dynamically managed firewall with support for network “zones” to assign a level of trust to a network and its associated connections and interfaces. It has support for IPv4 and IPv6 firewall settings. It supports Ethernet bridges and has a separation of runtime and permanent configuration options. It also has an interface for services or applications to add firewall rules directly.


### 系统配置目录
    目录中存放定义好的网络服务和端口参数，系统参数，不能修改。
    /usr/lib/firewalld/services
### 用户配置目录
    /etc/firewalld/
### 如何自定义添加端口
用户可以通过修改配置文件的方式添加端口，也可以通过命令的方式添加端口，注意，修改的内容会在/etc/firewalld/ 目录下的配置文件中还体现。
* 命令的方式添加端口
    firewall-cmd --permanent --add-port=9527/tcp 
    --permanent：表示设置为持久
    将具体的端口制定到具体的zone配置文件中
    firewall-cmd --zone=public --permanent --add-port=8010/tcp


### Firewall常用命令

 
    启动： systemctl start firewalld
    查看状态： systemctl status firewalld 
    停止： systemctl disable firewalld
    禁用： systemctl stop firewalld

    service firewalld restart 重启
    service firewalld start 开启
    service firewalld stop 关闭

    安装firewalld 防火墙
    #yum install firewalld
    
    开启服务
    #systemctl start firewalld.service
    
    关闭防火墙
    #systemctl stop firewalld.service

    开机自动启动
    #systemctl enable firewalld.service

    停止并禁用开机启动
    #systemctl disable firewalld.service
    
    重启防火墙
    firewall-cmd --reload

    查看状态
    systemctl status firewalld
    firewall-cmd --state

    查看帮助
    firewall-cmd --help

    查看区域信息
    firewall-cmd --get-active-zones
    
    拒绝所有包
    firewall-cmd --panic-on

    取消拒绝状态
    firewall-cmd --panic-off
    
    查看是否拒绝
    firewall-cmd --query-panic

    将接口添加到区域(默认接口都在public)
    firewall-cmd --zone=public --add-interface=eth0(永久生效再加上 --permanent 然后reload防火墙)

    设置默认接口区域
    firewall-cmd --set-default-zone=public(立即生效，无需重启)

    更新防火墙规则
    firewall-cmd --reload或firewall-cmd --complete-reload(两者的区别就是第一个无需断开连接，就是firewalld特性之一动态
添加规则，第二个需要断开连接，类似重启服务)

    查看指定区域所有打开的端口
    firewall-cmd --zone=public --list-ports

    在指定区域打开端口（记得重启防火墙）
    firewall-cmd --zone=public --add-port=80/tcp(永久生效再加上 --permanent)
        –zone 作用域
        –add-port=8080/tcp 添加端口，格式为：端口/通讯协议
        –permanent #永久生效，没有此参数重启后失效  
    查看配置文件
    /etc/firewalld/zones/public.xml
    <?xml version="1.0" encoding="utf-8"?>
    <zone>
        <short>Public</short>
        <description>For use in public areas. You do not trust the other computers on networks to not harm your computer. Only selected incoming connections are accepted.</description>
        <service name="ssh"/>
        <service name="dhcpv6-client"/>
        <port protocol="tcp" port="80"/>
        <port protocol="tcp" port="6379"/>
        <port protocol="tcp" port="8080"/>
    </zone>

    开启某个端口
    #firewall-cmd --permanent --zone=public --add-port=8080-8081/tcp  //永久
    #firewall-cmd  --zone=public --add-port=8080-8081/tcp   //临时
    
