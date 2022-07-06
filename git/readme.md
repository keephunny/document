

### GIT常用命令

* git init：初始化一个仓库
* git status：查看当前版本状态
* git add file1 file2
* git add . 将改变的文件加到暂存区中。
* git rm —cached file1:把暂存区的文件从暂存区中移除
* git commit -m '提交描述'
* git restore file1 ：放弃文件的改变
* git log：查看提交历史
* git reflog：查看命令历史
* git reset —hard commitid：恢复到指定版本

* git push -u origin master 将本地代码推送到远程 首次推送
* git push -f origin master 强制推送本地代码到远程
* git remote 查看远程分支的名称
* git remote -v 查看远程分支的名称及远程仓库的地址
* git remote rm 远程分支名称 删除要本地仓库与远程仓库的关联关系
* git pull niu master 拉取远程服务器代码
* git clone 远程分支地址 将远程代码克隆到本地
* git 命令 --help 查看命令帮助
* git branch 查看当前的分支
* git checkout -b 分支的名称 创建并切换到分支上
* git switch -c 分支名称 创建并切换到分支上
* git branch -d 分支名称 删除分支
* git branch 分支的名称 创建分支
* git checkout 分支的名称 切换分支
* git merge 分支名称 将分支中的内容合并到当前分支
* git switch 分支名称 切换到分支上
* git tag 标签名 创建一个标签 默认该标签指向最新的commitid
* git tag 查看所有标签
* git tag 标签名 commitid 给指定的commit打标签
* git tag -d 标签名 删除标签
* git push 远程分支名称 标签名 将指定标签推送到远程
* git push 远程分支名称 --tags 将所有标签推送到远程
* git push origin(远程分支名称) 本地分支：远程分支 将本地的分支推送到远程分支
* git branch -d 删除本地分支
* git push origin(远程分支名称) --delete 远程仓库分支名称

#### 提交注释

* fix：修复了某个bug
* feat：新增了某个功能
* build：一些影响构建系统的更新
* chore：一些不更改核心代码的更新
* ci：变更了一些CI系统的配置
* docs：对文档做出了一些修改
* test：新增或修改测试文件
* refactor：重构了代码