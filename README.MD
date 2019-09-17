# Java相关
# linux
# mysql


* jdbc
* c3p0
* druid 
* druid monitor 数据持久化
    https://github.com/alibaba/druid
* jvm https://www.cnblogs.com/rainy-shurun/p/5732341.html
* 分布式日志收集

* 静态代理 动态代理


jvm 堆栈 
java  -Xms -Xmx -XX:PermSize -XX:MaxPermSize
jvm按照其存储数据的内容将所需内存分配为堆区和非堆区

dd

Spring + Atomikos 分布式事务实现方式

chkconfig --list


待完成

2019-05-10
1. ffmpeg安装
2. chkconfig
3. jar自启动
5. git使用
6. 海康sdk开发总结 win linux 
    查看so文件是否加载
    ldconfig  -p  | grep hcnetsdk  
7. idea生jar的几种方式

2019-05-17
1. 海康视频无插件播放
https://blog.csdn.net/haoyitech/article/details/79574696
2. web方式程序升级发布
3. jenkins执续集成 执续布署

Subversion Edge
swagger bootstrap
视觉测量 物体体积 VR 虚拟立体
opencv 车牌识别
目标跟随

计算MD5值 签名
##### 文本相似度
余弦相似度
最长公共子序列算法
最小编辑距离算法
Jaccard相似度
比如百度也有去重策略，但是其最后应用到线上的并不是Jaccard相似度，而是找文档中最长的几个句子，根据这几个句子是否一样判断两个文档是否重复，而且准确率出奇的好。
Jaccard系数

正确率（Precision，简称为P）定义为：P=返回结果中相关文档个数/返回结果的数目。
精确率（accuracy，简称为A）定义为：A=判断结果正确的文档数目/所有文档数目。
召回率（Recall，简称为R）定义为：   R=返回结果中相关文档


腾讯视频32位图标删除
    1、使用快捷键Win+R打开运行窗口-regedit-确定；
    2、点击菜单栏-文件-导出-将注册表以文件形式备份至本地计算机；
    3、依次展开如下注册表路径：HKEY_CURRENT_USER---Software---Microsoft--- Windows---CurrentVersion---Explorer---MyComputer---NameSpace；
    4、可以看到NameSpace项目下存在{E701A357-F43B-42c9-98D1-96B6C11EAD87}一类的子项目，在右侧窗口可看到其项目数据为腾讯视频，那么要删除的就是这个项目了；

