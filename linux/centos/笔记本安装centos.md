
ultraiso制作启动U盘。iso镜像写入。

## wifi连接网络

1. 安装固件
    # 查询内核日志，查看是否需要安装无线网卡的固件
    dmesg | grep firmware
    # 正常：iwlwifi loaded firmware version ....
    # 错误：IOCSIFFLAGS: No such file or directory，此时需要安装固件
    # 错误：firmware: requesting iwlwifi-5000-1.ucode

    # 安装firmware，需要查看网卡型号，先安装工具
    yum -y install pciutils*

    # 查看无线网卡型号
     lspci |grep 'network' -i

        Ethernet controller: Intel Corporation 82567LM Gigabit Network Connection (rev 03)
        Network controller: Intel Corporation Ultimate N WiFi Link 5300

    # 查找并安装
    yum list | grep "5300"
    yum -y install iwl5300-firmware
    


2. 查看wifi名称
    # 安装配置工具，安装net-tools后，可以使用ifconfig
    yum install iw
    yum install wpa_supplicant
    yum install net-tools

    # 查看无线网接口
    iw dev
    # interface wls1  ... addr ... type...
    # 有channel 1 (2412 MHz)....表示已连接

    # 查看接口连接信息
    iw wls1 link
    # Not connectted.   未连接
    # Connected to ...  SSID:test... 已连接

    # 查看网络接口/网卡状态
    ifconfig
    # 注：未连接wifi前，/etc/sysconfig/network-scripts没有发现wlp3s0的配置，
    # 连接成功之后，出现同wifi的SSID相同名称的配置


    # 查看网络接口/网卡状态
    ip addr     # 会显示已获取的IP
    ip link     # 显示网卡

    # 启用/禁用wls1接口，两种方法等同。up时需要数秒
    ifconfig wls1 up/down     # ping提示：connect: Network is unreachable
    ip link set dev wls1 up/down  # ping提示：Name or service not known
    

    # 启用wlp3s0接口
    ip link set dev wlp3s0 up

    # 查看周围wifi
    iw dev wlp3s0 scan | grep SSID

    # 连接wifi，指定SSID(wifi名称)和password(wifi密码)
    wpa_supplicant -B -i wlp3s0 -c <(wpa_passphrase "ssid" "password")
    # Successfully initialized wpa_supplicant

    # 或者
    wpa_supplicant -B -i wlan0 -c /etc/wpa_supplicant/wpa_supplicant.conf
    # 内容如下：
    +++++++++++++++++++++++++++++++++++++
    ctrl_interface=/var/run/wpa_supplicant
    #ctrl_interface_group=wheel
    ap_scan=1
    network={
            ssid="ssid名称"
            scan_ssid=1
            key_mgmt=WPA-PSK
            psk="实际密码"
    }
    ++++++++++++++++++++++++++++++++++++

    # 用dhcp获得IP
    dhclient wlp3s0

    # 查看ip
    ip addr show wlp3s0
    # <BROADCAST,MULTICAST,UP,LOWER_UP> UP表示接口已启用
    # wlp3s0 inet 192.168.*.* brd .... int6 .....，无线网卡已获取ip，网络已连接
    # 同时/etc/sysconfig/network-scripts出现ifcfg-"ssid"配置文件
    
    # 查看网络状态
    service network status
    # Configured devices: lo enp0s25 test
    # Currently active devices: enp0s25 wlp3s0

    # 网卡管理、使用、连接情况
    nmcli dev status    # type/state
    nmcli dev show      # 详情

    # 连接/断开连接，connected <-> disconnected，不是启用/禁用接口
    ifdown wlp3s0
    ifup wlp3s0     # 注：测试时，down之后，up不会恢复连接，原因未知，重启后重新连接

    # 要使用静态IP，将 dhclient 命令替换为
    ip addr add 192.168.8.10/24 broadcast 192.168.8.255 dev wlp3s0
    ip route add default via 192.168.8.1

    # 刷新 IP 地址和网关
    ip addr flush dev wlp3s0
    ip route flush dev wlp3s0

    # 临时配置enp0s25接口的IP和掩码，ifcfg-enp0s25文件未改变，重启后失效
    ifconfig enp0s25 192.168.*.* netmask 255.255.255.0


## 查看网络状态
    nmcli dev status

    DEVICE        TYPE        STATUS        CONNECTION
    wlp6s0 　　  wifi           unmanaged  SeaStar
    enp8s0 　　  ethernet   connected      --
    lo                 loopback  unmanaged    --

## Linux 开机时网络自动连接
cd /etc/sysconfig/network-scripts/新建一个无线网卡ifcfg-wls1 
    ONBOOT=yes
    DEVICE=wls1
    NAME=wls1
    BOOTPROTO=dhcp
    #IPADDR=192.168.67.10
    #GETEWAY=192.168.64.1
    #NETMASK=255.255.252.0
https://www.cnblogs.com/dunitian/p/4975830.html
