```
    [root@localhost /]# yum install subversion -y
    [root@localhost /]# svnadmin create /home/svn
    [root@localhost /]# vim /home/svn/conf/passwd 
        admin=123456
        test1=123456
    [root@localhost /]# vim /home/svn/conf/authz
        #所有文件
        [/]
        admin=rw
        test1=r
        #其它用户无权限
        *=

        #组配置
        [groups]
        group1 = admin
        group2 = test1,test2
        [/]
        @group1 = rw
        @group2 = r
        * =
    [root@localhost /]# vim /home/svn/conf/svnserve.conf 
        #匿名用户可读
        anon-access = read 
        #授权用户可写
        auth-access = write 
        #使用哪个文件作为账号文件
        password-db = passwd 
        #使用哪个文件作为权限文件
        authz-db = authz 
        # 认证空间名，版本库所在目录
        realm = /home/svn 
    [root@localhost /]# 
    #启动
    [root@localhost /]# svnserve -d -r /home/svn/
    #停止
    [root@localhost /]# killall svnserve
    [root@localhost /]# ps -aux|grep svn
    [root@localhost /]# 

```
svn://192.168.41.100/