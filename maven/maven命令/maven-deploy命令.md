

* pl    --projects
    Build specified reactor projects instead of all projects	
    选项后可跟随{groupId}:{artifactId}或者所选模块的相对路径(多个模块以逗号分隔)
* am    --also-make
    If project list is specified, also build projects required by the list	
    表示同时处理选定模块所依赖的模块
* amd   --also-make-dependents
    If project list is specified, also build projects that depend on projects on the list
    表示同时处理依赖选定模块的模块
* -N    --Non-recursive
    Build projects without recursive
    表示不递归子模块
* rf    --resume-form
    Resume reactor from specified project
    表示从指定模块开始继续处理
    1. 在dailylog-parent目录运行`mvn clean install -pl org.lxp:dailylog-web -am`，结果

dailylog-common成功安装到本地库
dailylog-parent成功安装到本地库
dailylog-web成功安装到本地库
该命令等价于`mvn clean install -pl ../dailylog-web -am`

2. 在dailylog-parent目录运行`mvn clean install -pl ../dailylog-common -am`，结果

dailylog-common成功安装到本地库
dailylog-parent成功安装到本地库
3. 在dailylog-parent目录运行`mvn clean install -pl ../dailylog-common -amd`，结果

dailylog-common成功安装到本地库
dailylog-web成功安装到本地库
由于dailylog-parent并不依赖dailylog-common模块，故没有被安装

4. 在dailylog-parent目录运行`mvn clean install -pl ../dailylog-common,../dailylog-parent -amd`，结果

dailylog-common成功安装到本地库
dailylog-parent成功安装到本地库
dailylog-web成功安装到本地库
5. 在dailylog-parent目录运行`mvn clean install -N`，结果

dailylog-parent成功安装到本地库
-N表示不递归，那么dailylog-parent管理的子模块不会被同时安装

6. 在dailylog-parent目录运行`mvn clean install -pl ../dailylog-parent -N`，结果

dailylog-parent成功安装到本地库
7. 在dailylog-parent目录运行`mvn clean install -rf ../dailylog-common`，结果

dailylog-common成功安装到本地库
dailylog-web成功安装到本地库
https://www.cnblogs.com/hiver/p/7850954.html