locale  

vim /etc/sysconfig/i18n
添加如下两行代码
LANG="zh_CN.UTF-8"
LC_ALL="zh_CN.UTF-8"
    #  source    /etc/sysconfig/i18n
    
    再修改 locale.cnf配置文件

    #   vim /etc/locale.conf

    LANG="zh_CN.UTF-8"
    #  source   /etc/locale.conf

    # reboot

    文件->打开->选中会话->右键->属性->终端 （我用的终端连接工具是Xshell，其它连接工具更改编码方式请自行百度）

将编码改为 UTF-8

https://www.linuxidc.com/Linux/2017-07/145572.htm

https://blog.csdn.net/liguangxianbin/article/details/79814964
更改为zh_CN.UTF-8,重启。
    #     reboot
    3.发现重启之后 .locale 和 locale.conf 都是 en_US.UTF-8.

    4.centos7 在开机初始化时，locale.conf  来自 /etc/profile.d/lang.sh 的加载。
    即使修改成zh_CN.UTF-8之后，加载脚本时仍然会初始化为en_US.UTF-8.

    6.修改之后，重启。


centos7
    # vim /etc/locale.conf
    LANG="zh_CN"
    # source   /etc/locale.conf


    https://blog.csdn.net/chen_jl168/article/details/79288584
    1.查看/usr/share目录下是否有fonts和fontconfig目录
    如果没有，执行下面指令：

    #yum -y install fontconfig
    1
    执行完该指令后就可以看到fonts和fontconfig目录了

    2.进入字体目录

    #cd /usr/share/fonts/
    1
    3.创建需要安装的字体目录

    #mkdir invoice_needed_font
    1
    4.修改invoice_needed_font目录的权限使root用户以外的用户也可以使用

    #chmod -R 755 /usr/share/fonts/invoice_needed_font
    1
    5.复制Courier New.ttf和msyhl.ttc字体到invoice_needed_font目录下

    6.建立字体缓存

    #mkfontscale	// 如果提示 mkfontscale: command not found，需自行安装 #yum install mkfontscale
    #mkfontdir
    #fc-cache –fv	// 刷新内存中的字体缓存
    #source /etc/profile	// 执行以下命令让字体生效
    1
    2
    3
    4
    7.查看是否安装成功

    #fc-list
    1
    如下图1出现Microsoft YaHei UI 和Courier New字段则安装成功！
    --------------------- 
    作者：猿球崛起3 
    来源：CSDN 
    原文：https://blog.csdn.net/chen_jl168/article/details/79288584 
    版权声明：本文为博主原创文章，转载请附上博文链接！