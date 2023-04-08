### yum安装

```
yum install git
```



### 源码包安装

https://git-scm.com/download/linux
wget --no-check-certificate https://mirrors.edge.kernel.org/pub/software/scm/git/git-2.9.5.tar.gz

#### 依赖

```
//实测依赖
yum -y install curl-devel expat-devel openssl-devel zlib-devel -y
//官网完整依赖
#yum -y install curl-devel expat-devel gettext-devel openssl-devel zlib-devel -y
yum -y install gcc -y

tar -zxf git-2.21.0.tar.gz
cd git-2.21.0
./configure
make && make install
```

####  环境变量

```
vim /etc/profile 
	export PATH=$PATH:/usr/local/git/bin
source /etc/profile

#方式二：软链接的方式
ln -s /usr/local/git/bin/git /usr/local/bin/

#安装成功
git --version
```

