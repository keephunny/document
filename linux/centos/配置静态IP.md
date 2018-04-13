mac版安装vm
网络设置默认

宿主机
    vmnet1: flags=8863<UP,BROADCAST,SMART,RUNNING,SIMPLEX,MULTICAST> mtu 1500
	ether 00:50:56:c0:00:01 
	inet 192.168.239.1 netmask 0xffffff00 broadcast 192.168.239.255
    vmnet8: flags=8863<UP,BROADCAST,SMART,RUNNING,SIMPLEX,MULTICAST> mtu 1500
        ether 00:50:56:c0:00:08 

虚拟机 centos
    DEVICE=ens33
    HWADDR=00:0C:29:40:F0:3F
    TYPE=Ethernet
    UUID=a30270cc-ff98-4c5e-ab22-1a46436dd4d1
    ONBOOT=yes
    NM_CONTROLLED=yes
    BOOTPROTO=static
    IPADDR=192.168.54.120
    NETMASK=255.255.255.0
    GATEWAY=192.168.54.2
    DNS1=192.168.54.2


VMware提供:桥接,Host Only和NAT三种连网方式. 我个人认为NAT方式较简单,用他来共享主机的IP,适合那种每台主机只允许一个IP的情况。具体配置如下：


1、VMware网络连接选择的是NAT方式。
2、VMware网络配置里的NAT项中查看VMnet8，NAT的网关配置为192.168.X.2。(注意：此网关为虚拟网关，不是VMnet8的地址。)
3、在windows中，查看任何的网络连接，您应该发现除了原有的网卡之外，又多了Vmnet1和Vmnet8。vmnet1是hostonly的接口，而Vmnet8是就是我们要使用的NAT的网络接口。 
4、在windows主机上用ipconfig查看VMnet8的IP地址，一般是192.168.X.1。此时VMnet8的配置应该是自动获取IP，现在改成静态IP，并把此IP直接填入VMnet8里，不设网关。 
6、在linux下把网卡IP配置成和VMnet8一个网段的IP（192.168.X.Z/255.255.255.0)
7、在linux下网关配置成刚才查看的那个IP192.168.X.2即可。
8、在linux下DNS和windows主机的相同。
9、在linux下运行命令service network restart重新启动linux的网络服务。
10、在linux下ping 192.168.x.1，在windows下ping 192.168.x.z 成功后证实主机和虚拟机已能够互相访问了，现在也能够实现虚拟机共享主机ip上网了。 