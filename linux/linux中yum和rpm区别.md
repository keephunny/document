# 源码安装
绝大多数开源软件都是以原码形式发布的，一般会被打成.tar.gz的归档压缩文件，源码需要编译成二进制形式之后才能够运行使用。
* .configure检查编译环境
* make对原码进行编译
* make install将生成可执行文件安装到当前计算机

# RPM安装
源码包安装操作复杂，编译时间长，极易出现问题，依赖关系复杂。
RPM(redhat package manager)通过将代码基于特定平台系统编译为可执行文件，并保存依赖关系，来简化开源软件的安装管理，针对不同的系统设定不同的包。
* rpm -i xx.rpm安装
* rpm -e xx.rpm卸载
* rpm -U xx.rpm 升级安装
* rpm -ivh xx.rpm 支持通过http/ftp协议形式安装
    -v显示详细信息
    -显示进度条
* rpm -qa 列出已经安装的rpm软件

# yum安装
rpm软件包管理虽然方便，但是需要手工解决软件包的依赖关系，很多时候安装一个软件需要安装多个其它软件，手动解决时很复杂。yum解决了这些问题，yum是rpm的前端程序，主要目的是设计用来自动解决rpm的依赖关系。
* 自动解决依赖关系
* 可以对rpm进行分组，基于组进行安装操作
* 引入了仓库概念
* 配置解单
yum仓库用来存放所有的现有的.rpm包，当使用yum安装一个rpm包时，需要依赖关系，会自动在仓库中查找依赖软件并安装，仓库可以是本地，也可以是http、ftp、nfs(network file system)形式使用的集中地、统一网络仓库。     
仓库的配置文件/etc/yum.repos.d目录下
* yum remove 卸载
* yum update 升级
* yum search 查询软件