 
我们都清楚Net-filter是Linux的一种防火墙机制。而Firewalld是一个在网络区域（networks zones）的支持下动态管理防火墙的守护进程。早期的RHEL版本和CentOS 6使用iptables这个守护进程进行数据包过滤。而在RHEL/CentOS 7和Fedora 21中，iptables接口将被firewalld取代。
  
由于iptables可能会在未来的版本中消失，所以建议从现在起就使用Firewalld来代替iptables。话虽如此，现行版本仍然支持iptables，而且还可以用YUM命令来安装。不过可以肯定的是，在同一个系统中不能同时运行Firewalld和iptables，否则可能引发冲突。
  
在iptables中需要配置INPUT、OUTPUT和FORWARD CHAINS。而在Firewalld中新引入了区域（Zones）这个概念。默认情况下，firewalld中就有一些有效的区域（zones），这也是本文将要讨论的内容。
  
基础区域如同公共区域（public zone）和私有区域（private zone）。为了让作业在这些区域中运行，需要为网络接口添加特定区域（specified zone）支持，好让我们往firewalld中添加服务。
  
默认情况下就有很多生效的服务。firewalld最好的特性之一就是，它本身就提供了一些预定义的服务，而我们可以以这些预定义的服务为模版，复制之以添加我们自己的服务。
  
Firewalld还能很好地兼容IPv4、IPv6和以太网桥接。在Firewalld中，我们可以有独立的运行时间和永久性的配置。接下来让我们看看如何在区域（zones）中作业、创建我们的服务以及更好的利用firewalld这个防火墙机制吧。

    # hostnamectl
        Static hostname: localhost.localdomain
            Icon name: computer-vm
            Chassis: vm
            Machine ID: 6ddb8380bba44c1598e1f76ebd1ca7b4
            Boot ID: 87c9bd6e22f742bf9bcbf883cc708a8a
        Virtualization: vmware
    Operating System: CentOS Linux 7 (Core)
        CPE OS Name: cpe:/o:centos:centos:7
                Kernel: Linux 3.10.0-693.el7.x86_64
        Architecture: x86-64


## 区域Zone
### 什么是区域Zone： 
网络区域定义了网络连接的可信等级。这是一个 一对多的关系，这意味着一次连接可以仅仅是一个区域的一部分，而一个区域可以用于很多连接。默认情况就有一些有效的区域。我们需要网络接口分配区域。区域规定了区域是网络接口信任或者不信任网络连接的标准。区域（zone）包含服务和端口。接下来让我们讨论Firewalld中那些有用的区域（zones）。
### 区域的分类
Firewalls can be used to separate networks into different zones based on the level of trust the user has decided to place on the devices and traffic within that network. NetworkManager informs firewalld to which zone an interface belongs. An interface’s assigned zone can be changed by NetworkManager or via the firewall-config tool which can open the relevant NetworkManager window for you.  
The zone settings in /etc/firewalld/ are a range of preset settings which can be quickly applied to a network interface. They are listed here with a brief explanation:

* drop 
Any incoming network packets are dropped, there is no reply. Only outgoing network connections are possible.
* block 
Any incoming network connections are rejected with an icmp-host-prohibited message for IPv4 and icmp6-adm-prohibited for IPv6. Only network connections initiated from within the system are possible.
* public 
For use in public areas. You do not trust the other computers on the network to not harm your computer. Only selected incoming connections are accepted.
* external 
For use on external networks with masquerading enabled especially for routers. You do not trust the other computers on the network to not harm your computer. Only selected incoming connections are accepted.
* dmz 
For computers in your demilitarized zone that are publicly-accessible with limited access to your internal network. Only selected incoming connections are accepted.
* work 
For use in work areas. You mostly trust the other computers on networks to not harm your computer. Only selected incoming connections are accepted.
* home 
For use in home areas. You mostly trust the other computers on networks to not harm your computer. Only selected incoming connections are accepted.
* internal 
For use on internal networks. You mostly trust the other computers on the networks to not harm your computer. Only selected incoming connections are accepted.
* trusted 
All network connections are accepted. 
It is possible to designate one of these zones to be the default zone. When interface connections are added to NetworkManager, they are assigned to the default zone. On installation, the default zone in firewalld is set to be the public zone.

* 丢弃区域（Drop Zone）：如果使用丢弃区域，任何进入的数据包将被丢弃。这个类似与我们之前使用iptables -j drop。使用丢弃规则意味着将不存在响应，只有流出的网络连接有效。
阻塞区域（Block Zone）：阻塞区域会拒绝进入的网络连接，返回icmp-host-prohibited，只有服务器已经建立的连接会被通过。
* 公共区域（Public Zone）：只接受那些被选中的连接，而这些通过在公共区域中定义相关规则实现。服务器可以通过特定的端口数据，而其它的连接将被丢弃。
* 外部区域（External Zone）：这个区域相当于路由器的启用伪装（masquerading）选项。只有指定的连接会被接受，而其它的连接将被丢弃或者不被接受。
* 隔离区域（DMZ Zone）：如果想要只允许给部分服务能被外部访问，可以在DMZ区域中定义。它也拥有只通过被选中连接的特性。
* 工作区域（Work Zone）：在这个区域，我们只能定义内部网络。比如私有网络通信才被允许。
* 家庭区域（Home Zone）：这个区域专门用于家庭环境。我们可以利用这个区域来信任网络上其它主机不会侵害你的主机。它同样只允许被选中的连接。
* 内部区域（Internal Zone）：这个区域和工作区域（Work Zone）类似，只有通过被选中的连接。
* 信任区域（Trusted Zone）：信任区域允许所有网络通信通过。

### 查看区域
    firewall-cmd --get-zones
    firewall-cmd --get-default-zone

## 过滤规则
* source: 根据源地址过滤
* interface: 根据网卡过滤
* service: 根据服务名过滤
* port: 根据端口过滤
* icmp-block: icmp 报文过滤，按照 icmp 类型配置
* masquerade: ip 地址伪装
* forward-port: 端口转发
* rule: 自定义规则