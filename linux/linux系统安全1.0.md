
### 禁止root用户登录
```
注：判断一个用户是不是超级管理员，看的是用户的ID是否为0。
例：创建一个普通帐号，修改ID为0 然后变成超级管理权限
创建一个用户
useradd xxx 
passwd xxx
vim /etc/passwd
改：root:x:0:0:root:/root:/bin/bash
为：root:x:0:0:root:/sbin/nologin

#改ID0为0
admin2:x:0:0::/home/admin2:/bin/bash  
注：centos7普通用户id是从1000开始，centos7以前的普通用户id是从500开始。
```

### 更新sshd端口号
```
vim /etc/ssh/sshd_config
port 122
# 更改完之后我们需要将我们的122端口开放下。
firewall-cmd --add-port=122/tcp --permanent 
firewall-cmd --reload
```
