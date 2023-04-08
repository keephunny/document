git config --global user.name "xx"

git config --global user.email "xx"

克隆远程项目 git clone
git clone http://XXX.XXX/XXX.git

查看远程分支

git branch -r


提交代码
git add . 全部文件或指定文件
git commit -m 'xxxx' 提交
git push 提交
git pull 更新


查看当前User和Email配置

```
git config --local --list 
git config --list 

配置
​​​​​​local   仓库级别 、global 用户级别

法一：使用命令修改git的用户名和提交的邮箱

git config --global user.name "username"
git config --global user.email  useremail@qq.com

git config --local user.name "username"
git config --local user.email  useremail@qq.com

查看是否设置成功
git config user.name
git config user.email
在git中，我们使用git config 命令用来配置git的配置文件，git配置级别主要有以下3类：

1、 local   仓库级别
2、 global 用户级别，当前用户在所有仓库都用这个配置
3、 system 系统级别，整台计算机都用这个配置

优先级
git config （git config  --local ） > git config --global > git config --system。
```


限制文件上传大小
git config --global http.postBuffer 524288000
git config --global http.postBuffer 524288000
(52428000=500×1024×1024,即500M)