1. centos-7-DVD:DVD版本是标准的安装盘。
2. centos-7-NetInstall:网络安装镜像
3. centos-7-Everything:对完整版安装盘的软件进行补充，集成所有软件。
4. centos-7-LiveGnome:gnome桌面版
5. centos-7-KdeLive:KDE桌面版。
6. centos-7-livecd:光盘上运行的系统，类似于winpe

1. CentOS-7-x86_64-DVD-1804.iso
    这个DVD image包含了可以使用下载器下载的所有包。想使用CentOS的小伙伴都可以下载这个image，面向大部分用户。
2. CentOS-7-x86_64-NetInstall-1804.iso
    这是网络安装和救援image。安装人员会询问应该从哪里获取要安装的软件包。如果您有CentOS软件包的本地镜像，则非常有用。
3. CentOS-7-x86_64-Everything-1804.iso
    这个image包含Centos Linux 7完整的软件包，可用于安装或填充本地镜像。此图像需要16GB USB 闪存，因为它太大了（9G）。
4. CentOS-7-x86_64-LiveGNOME-1804.iso
5. CentOS-7-x86_64-LiveKDE-1804.iso
    这些images是CentOS Linux 7的Live image。根据名称，它们使用相应的显示管理器。它们专为测试目的和探索CentOS Linux 7环境而设计。除非您选择从Live环境中安装CentOS Linux 7，否则它们不会修改硬盘的内容。请注意，在这种情况下，您无法更改已安装的软件包集。这需要使用yum在已安装的系统中完成。
6. CentOS-7-x86_64-Minimal-1804.iso
    这个image的目的是安装一个非常基本的CentOS Linux 7系统，只需要最少的软件包即可拥有一个功能系统。请将此图像刻录到CD上并启动计算机。系统上将安装一组预先选择的软件包。其他所有东西都需要使用yum安装。此映像安装的软件包集与从完整DVD映像中选择名为“Minimal”的组时安装的软件包相同。
当你使用安装镜像时
=============================
您可以将这些图像刻录到DVD或dd它们到USB闪存驱动器。 准备好引导介质后，从引导介质引导计算机。如果使用这些安装映像对硬盘进行安装，请记住在安装后运行yum update以将系统更新到最新的软件包。


i386是32位系统，x86_64是64位系统
bin——完整版，netinstall——网络安装盘，启动后需要联网安装 minimal——小安装盘，只有必要的软件，自带的软件最少