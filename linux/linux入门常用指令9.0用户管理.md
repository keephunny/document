### 多用户管理
```
[root@localhost ]# groupadd groupname
[root@localhost ]# useradd -d /opt/application/user1 -m user1 -g groupname
[root@localhost ]# passwd user1
[root@localhost ]# usermod -d /xx
[root@localhost ]# usermod -g ugroup

```


### 添加用户sudo权限

```
# chmod u+w /etc/sudoers
# vim /etc/sudoers
    root    ALL=(ALL)   ALL
    hadoop  ALL=(ALL)       NOPASSWD: NOPASSWD: ALL
    用户名  ALL=(ALL)   ALL
# chmod u-w /etc/sudoers

```