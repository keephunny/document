
https://www.openssl.org/source/old/
```
[root@db1 ~]# openssl version
    OpenSSL 1.1.1j-dev  xx XXX xxxx
    
[root@db1 ~]# wget -P /usr/local/src https://github.com/openssl/openssl/archive/OpenSSL_1_1_1-stable.zip
[root@db1 ~]# unzip OpenSSL_1_1_1-stable.zip
[root@db1 ~]# cd OpenSSL_1_1_1-stable
[root@db1 ~]# ./config --prefix=/usr/local/openssl
[root@db1 ~]# make && make install
    如果报错，需要安装GCC环境 yum install gcc -y
[root@db1 ~]# 
#备份原始文件
[root@db1 ~]#	mv /usr/bin/openssl /usr/bin/openssl.old
[root@db1 ~]#	mv /usr/lib64/openssl /usr/lib64/openssl.old
[root@db1 ~]#	mv /usr/lib64/libssl.so /usr/lib64/libssl.so.old
[root@db1 ~]#	ln -s /usr/local/openssl/bin/openssl /usr/bin/openssl
[root@db1 ~]#	ln -s /usr/local/openssl/include/openssl /usr/include/openssl
[root@db1 ~]#	ln -s /usr/local/openssl/lib/libssl.so /usr/lib64/libssl.so
[root@db1 ~]#	echo "/usr/local/openssl/lib" >> /etc/ld.so.conf
[root@db1 ~]#	ldconfig -v 

[root@db1 ~]# openssl version
[root@db1 ~]# 
[root@db1 ~]# 
[root@db1 ~]# 


```