### 服务器环境
* 192.168.1.10 A主机
* 192.168.1.11 B主机


### 实现A主机到B主机的免密登录
1. 在A主机生成key
```
    #Enter passphrase (empty for no passphrase): 直接回车
    #Enter same passphrase again: 直接回车
    #生成免密ssh密钥
    [root@localhost conf]# ssh-keygen -t rsa
    Generating public/private rsa key pair.
    Enter file in which to save the key (/root/.ssh/id_rsa): 
    /root/.ssh/id_rsa already exists.
    Overwrite (y/n)? y
    Enter passphrase (empty for no passphrase): 
    Enter same passphrase again: 
    Your identification has been saved in /root/.ssh/id_rsa.
    Your public key has been saved in /root/.ssh/id_rsa.pub.
    The key fingerprint is:
    7a:37:56:7e:27:63:d1:92:37:44:c4:7f:3e:2e:a5:7d root@localhost.localdomain
    The key's randomart image is:
    +--[ RSA 2048]----+
    |              oo |
    |              .. |
    |               ..|
    |              .oo|
    |        S   . +o+|
    |       .   o   *o|
    |      . . + . X o|
    |       . o . = *E|
    |              . .|
    +-----------------+
    #传输到目标主机
    [root@localhost conf]# ssh-copy-id root@192.168.41.120        
    /usr/bin/ssh-copy-id: INFO: attempting to log in with the new key(s), to filter out any that are already installed
    /usr/bin/ssh-copy-id: INFO: 1 key(s) remain to be installed -- if you are prompted now it is to install the new keys
    root@192.168.1.11's password: 

    Number of key(s) added: 1

    Now try logging into the machine, with:   "ssh 'root@192.168.41.120'"
    and check to make sure that only the key(s) you wanted were added.
```
2. 验证
    ```
    [root@localhost conf]# ssh root@192.168.1.11
    Last login: Tue May 28 12:53:50 2019 from 192.168.1.11
```

3. 查看公钥
```
[root@localhost conf]# cd /root/.ssh/
[root@localhost .ssh]# cat id_rsa.pub
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC67YrUR+MLYLNy1hs8iqu7mYNRste1yfLwfrGHm7g58bNFkK6il77c7VxzTIk9HovuA1DFkoWbT8Lcse7CL5Pp6XWfhvQYKI5kev5MhywMk1Z5WYS2Q2WKEhJ02+UoWsHoFRLdmILUxznYO2EM7spYVzVDt/1UU6Rxn4WrzdOBazB3zqAqipvlcYQ7245/wzduJjhkNQYpsDdtRm+zcVqWBcxcEia2aARNQVlspdqi6r6yoRIfOn9gNSSySq1gAlnafwtFwTP9trbhYIhKS3uEcn2LsTzREG9R9jJwSP3ybiw4MZIKtfQcuqZk7yT9gixU3Z1ZQO818YrkZiDSn5JD root@localhost.localdomain 
```

//TODO 增加互访