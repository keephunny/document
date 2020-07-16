### 安装环境
linux centos CentOS-7.6-x86_64-DVD-1810
Apache_OpenOffice_4.1.7_Linux_x86-64_install-rpm_zh-CN.tar.gz

https://downloads.apache.org/openoffice/4.1.7/binaries/zh-CN/Apache_OpenOffice_4.1.7_Linux_x86-64_install-rpm_zh-CN.tar.gz

### 安装openoffice包

```
# 解压安装
[root@localhost src]# cd RPMS
[root@localhost src]# rpm -ivh *.rpm
    desktop-integration                             openoffice-core04-4.1.7-9800.x86_64.rpm             openoffice-ooolinguistic-4.1.7-9800.x86_64.rpm
    openoffice-4.1.7-9800.x86_64.rpm                openoffice-core05-4.1.7-9800.x86_64.rpm             openoffice-pyuno-4.1.7-9800.x86_64.rpm
    openoffice-base-4.1.7-9800.x86_64.rpm           openoffice-core06-4.1.7-9800.x86_64.rpm             openoffice-ure-4.1.7-9800.x86_64.rpm
    openoffice-brand-base-4.1.7-9800.x86_64.rpm     openoffice-core07-4.1.7-9800.x86_64.rpm             openoffice-writer-4.1.7-9800.x86_64.rpm
    openoffice-brand-calc-4.1.7-9800.x86_64.rpm     openoffice-draw-4.1.7-9800.x86_64.rpm               openoffice-xsltfilter-4.1.7-9800.x86_64.rpm
    openoffice-brand-draw-4.1.7-9800.x86_64.rpm     openoffice-gnome-integration-4.1.7-9800.x86_64.rpm  openoffice-zh-CN-4.1.7-9800.x86_64.rpm
    openoffice-brand-impress-4.1.7-9800.x86_64.rpm  openoffice-graphicfilter-4.1.7-9800.x86_64.rpm      openoffice-zh-CN-base-4.1.7-9800.x86_64.rpm
    openoffice-brand-math-4.1.7-9800.x86_64.rpm     openoffice-images-4.1.7-9800.x86_64.rpm             openoffice-zh-CN-calc-4.1.7-9800.x86_64.rpm
    openoffice-brand-writer-4.1.7-9800.x86_64.rpm   openoffice-impress-4.1.7-9800.x86_64.rpm            openoffice-zh-CN-draw-4.1.7-9800.x86_64.rpm
    openoffice-brand-zh-CN-4.1.7-9800.x86_64.rpm    openoffice-javafilter-4.1.7-9800.x86_64.rpm         openoffice-zh-CN-help-4.1.7-9800.x86_64.rpm
    openoffice-calc-4.1.7-9800.x86_64.rpm           openoffice-math-4.1.7-9800.x86_64.rpm               openoffice-zh-CN-impress-4.1.7-9800.x86_64.rpm
    openoffice-core01-4.1.7-9800.x86_64.rpm         openoffice-ogltrans-4.1.7-9800.x86_64.rpm           openoffice-zh-CN-math-4.1.7-9800.x86_64.rpm
    openoffice-core02-4.1.7-9800.x86_64.rpm         openoffice-onlineupdate-4.1.7-9800.x86_64.rpm       openoffice-zh-CN-res-4.1.7-9800.x86_64.rpm
    openoffice-core03-4.1.7-9800.x86_64.rpm         openoffice-ooofonts-4.1.7-9800.x86_64.rpm           openoffice-zh-CN-writer-4.1.7-9800.x86_64.rpm

准备中...                          ################################# [100%]
正在升级/安装...
   1:openoffice-ure-4.1.7-9800        ################################# [  2%]
   2:openoffice-core01-4.1.7-9800     ################################# [  5%]
   3:openoffice-zh-CN-4.1.7-9800      ################################# [  7%]
   4:openoffice-impress-4.1.7-9800    ################################# [ 10%]
   5:openoffice-zh-CN-base-4.1.7-9800 ################################# [ 12%]
   6:openoffice-zh-CN-calc-4.1.7-9800 ################################# [ 15%]
   7:openoffice-zh-CN-draw-4.1.7-9800 ################################# [ 17%]
   8:openoffice-zh-CN-help-4.1.7-9800 ################################# [ 20%]
   9:openoffice-zh-CN-impress-4.1.7-98################################# [ 22%]
  10:openoffice-zh-CN-math-4.1.7-9800 ################################# [ 24%]
  11:openoffice-zh-CN-res-4.1.7-9800  ################################# [ 27%]
  12:openoffice-zh-CN-writer-4.1.7-980################################# [ 29%]
  13:openoffice-base-4.1.7-9800       ################################# [ 32%]
  14:openoffice-calc-4.1.7-9800       ################################# [ 34%]
  15:openoffice-core02-4.1.7-9800     ################################# [ 37%]
  16:openoffice-core03-4.1.7-9800     ################################# [ 39%]
  17:openoffice-core04-4.1.7-9800     ################################# [ 41%]
  18:openoffice-core05-4.1.7-9800     ################################# [ 44%]
  19:openoffice-core06-4.1.7-9800     ################################# [ 46%]
  20:openoffice-core07-4.1.7-9800     ################################# [ 49%]
  21:openoffice-draw-4.1.7-9800       ################################# [ 51%]
  22:openoffice-images-4.1.7-9800     ################################# [ 54%]
  23:openoffice-4.1.7-9800            ################################# [ 56%]
  24:openoffice-math-4.1.7-9800       ################################# [ 59%]
  25:openoffice-writer-4.1.7-9800     ################################# [ 61%]
  26:openoffice-brand-writer-4.1.7-980################################# [ 63%]
  27:openoffice-brand-math-4.1.7-9800 ################################# [ 66%]
  28:openoffice-brand-base-4.1.7-9800 ################################# [ 68%]
  29:openoffice-brand-calc-4.1.7-9800 ################################# [ 71%]
  30:openoffice-brand-draw-4.1.7-9800 ################################# [ 73%]
  31:openoffice-brand-impress-4.1.7-98################################# [ 76%]
  32:openoffice-brand-zh-CN-4.1.7-9800################################# [ 78%]
  33:openoffice-ogltrans-4.1.7-9800   ################################# [ 80%]
  34:openoffice-gnome-integration-4.1.################################# [ 83%]
  35:openoffice-graphicfilter-4.1.7-98################################# [ 85%]
  36:openoffice-javafilter-4.1.7-9800 ################################# [ 88%]
  37:openoffice-onlineupdate-4.1.7-980################################# [ 90%]
  38:openoffice-ooofonts-4.1.7-9800   ################################# [ 93%]
  39:openoffice-ooolinguistic-4.1.7-98################################# [ 95%]
  40:openoffice-pyuno-4.1.7-9800      ################################# [ 98%]
  41:openoffice-xsltfilter-4.1.7-9800 ################################# [100%]


[root@localhost src]# cd desktop-integration
# 安装指定版本
[root@localhost src]# rpm -ivh openoffice4.1.7-redhat-menus-4.1.7-9800.noarch.rpm 
    openoffice4.1.7-freedesktop-menus-4.1.7-9800.noarch.rpm  openoffice4.1.7-redhat-menus-4.1.7-9800.noarch.rpm
    openoffice4.1.7-mandriva-menus-4.1.7-9800.noarch.rpm     openoffice4.1.7-suse-menus-4.1.7-9800.noarch.rpm

准备中...                          ################################# [100%]
正在升级/安装...
   1:openoffice4.1.7-redhat-menus-4.1.################################# [100%]


```

### 启动openoffice
```
# 安装完成后，进入openoffice的安装目录
[root@localhost src]# cd /opt/openoffice4/program
[root@localhost program]# soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard &

直接启动会错1
[1] 2953
[root@localhost program]# /opt/openoffice4/program/soffice.bin: error while loading shared libraries: libXext.so.6: cannot open shared object file: No such file or directory

直接启动会错1
[root@localhost program]# no suitable windowing system found, exiting
```

### 安装桌面化插件
配置cenos.iso镜像源作为本地yum源。

 yum groupinstall "X Window System"
 yum groupinstall 'GNOME Desktop' 
```

# 安装时间较长
[root@localhost program]#  yum groupinstall "X Window System" -y
    验证中      : 1:NetworkManager-1.0.0-14.git20150121.b4ea599c.el7.x86_64   344/352 
    验证中      : 1:NetworkManager-1.0.0-14.git20150121.b4ea599c.el7.x86_64   345/352 
    验证中      : systemd-libs-208-20.el7.x86_64                              346/352 
    验证中      : centos-release-7-1.1503.el7.centos.2.8.x86_64               347/352 
    验证中      : yum-3.4.3-125.el7.centos.noarch                             348/352 
    验证中      : 7:lvm2-libs-2.02.115-3.el7.x86_64                           349/352 
    验证中      : dracut-network-033-240.el7.x86_64                           350/352 
    验证中      : libgudev1-208-20.el7.x86_64                                 351/352 
    验证中      : 1:NetworkManager-tui-1.0.0-14.git20150121.b4ea599c.el7.x86_64 352/352 
    验证中      : 7:device-mapper-libs-1.02.93-3.el7.x86_64                     353/352 
    已安装:
    NetworkManager.x86_64 1:1.12.0-6.el7           glx-utils.x86_64 0:8.3.0-10.el7                                     initial-setup-gui.x86_64 0:0.3.9.43-1.el7.centos        
    mesa-dri-drivers.x86_64 0:18.0.5-3.el7         plymouth-system-theme.x86_64 0:0.8.9-0.31.20140113.el7.centos         python-gobject-base.x86_64 0:3.22.0-1.el7_4.1           
    spice-vdagent.x86_64 0:0.14.0-16.el7           xorg-x11-drivers.x86_64 0:7.7-6.el7                                   xorg-x11-server-Xorg.x86_64 0:1.20.1-3.el7              
    xorg-x11-utils.x86_64 0:7.5-23.el7             xorg-x11-xauth.x86_64 1:1.0.9-1.el7                                   xorg-x11-xinit.x86_64 0:1.3.4-2.el7                     
    xvattr.x86_64 0:1.3-27.el7                    

#如果"X Window System安装后不能启动 就安装这个。
[root@localhost program]#  yum groupinstall 'GNOME Desktop'  -y
[root@localhost program]#  yum install libXext.x86_64

# 启动
[root@localhost program]# 
[root@localhost program]# /opt/openoffice4/program/soffice.bin: error while loading shared libraries: libXext.so.6: cannot open shared object file: No such file or directory
如果启动还报错。重新服务器使xwindow生效。亲测。
[root@localhost program]# reboot
[root@localhost program]# 
# 再次启动成功
[root@localhost program]# soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard &
[1] 10958
[root@localhost program]# ps -aux|grep office
root      10958  0.0  0.1 113116  1464 pts/0    S    17:57   0:00 /bin/sh /usr/bin/soffice -headless -accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard
root      10972  2.1  6.0 633836 61044 pts/0    Sl   17:57   0:00 /opt/openoffice4/program/soffice.bin -headless -accept=socket,host=127.0.0.1,port=8100;urp; -nofirststartwizard
root      10978  0.0  0.0 112656   972 pts/0    S+   17:57   0:00 grep --color=auto office

启动成功
```