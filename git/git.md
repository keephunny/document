git config --global user.name

git config --global user.email

克隆远程项目 git clone
git clone http://XXX.XXX/XXX.git

查看远程分支

git branch -r


提交代码
git add . 全部文件或指定文件
git commit -m 'xxxx' 提交
git push

## GitLab添加ssh-key，操作无需每次输入账号密码
1. 本地操作
打开本地git brash，在其中输入指令，生成ssh公钥和私钥对
ssh-keygen -t rsa -C 'xxx@xxx.com'    
  点击回车，会让你选择存储路径，此时不用理会直接回车，其会保存到默认路径， 接下来就是输入密码
2. 在cmd中复制到公钥
      打开电脑的cmd，在其中输入命令并回车   type %userprofile%\.ssh\id_rsa.pub | clip

3. GitLab上操作
      到GitLab界面，点击settings，后点击SSH-Keys，将复制过来的公钥黏贴到key框中，下方title可自己命名，点击addkey。