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




### mysql
mysql 主从同步 主主同步
mysql 分库分表 mycat jdbcsharding



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

<<<<<<< HEAD


拦截器统一封装controller返回对象格式。需要解决参数校验问题，复杂业务场景不能大量使用异常抛出。统一异常处理是否有冲突。
https://mp.weixin.qq.com/s/DaS8VbITUbfHxdb-diD8hg




VSFTP 安全漏洞
VSFTP  deny_file
如何拒绝上传某个具体文件
如何拒绝上传某个具体文件
=======
	
###  idea webstorm破解
 C:\Windows\System32\drivers\etc\hosts
 
 0.0.0.0 account.jetbrains.com
 
 MNQ043JMTU-eyJsaWNlbnNlSWQiOiJNTlEwNDNKTVRVIiwibGljZW5zZWVOYW1lIjoiR1VPIEJJTiIsImFzc2lnbmVlTmFtZSI6IiIsImFzc2lnbmVlRW1haWwiOiIiLCJsaWNlbnNlUmVzdHJpY3Rpb24iOiIiLCJjaGVja0NvbmN1cnJlbnRVc2UiOmZhbHNlLCJwcm9kdWN0cyI6W3siY29kZSI6IklJIiwiZmFsbGJhY2tEYXRlIjoiMjAxOS0wNC0wNSIsInBhaWRVcFRvIjoiMjAyMC0wNC0wNCJ9XSwiaGFzaCI6IjEyNjIxNDIwLzAiLCJncmFjZVBlcmlvZERheXMiOjcsImF1dG9Qcm9sb25nYXRlZCI6ZmFsc2UsImlzQXV0b1Byb2xvbmdhdGVkIjpmYWxzZX0=-tltrJHc5lqCKLnza1bcLhMzDkfJeBqhRWbvcrPF9Gqo+X+iFWeKQXoEUOlrG38uSzzmX05ph//PgXgyVfP5RXKsaRMfrv/thoouS5sA0aTemm3z6uRiFirTDj60KSGr5XZoP/WAXO4nuti6SRKZUbr/VSAtRPQRiCJvevq+3gWPDGu2aZ0AemiNLq4qIVWH3wxTN7lK2h5uJssZsyy35Yy9O703c5PFU0fxCj2HRgXq7H/91X+ZNLvvAZAVU9B7bOqnY4ZzNNV/cjY8B5gNo53Lo6s2szEV7DsZ+8e7k8P4Yo81DKxneCBoe4wiZmRaCIT6wK3/27KrtQTFMcGeZMA==-MIIElTCCAn2gAwIBAgIBCTANBgkqhkiG9w0BAQsFADAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBMB4XDTE4MTEwMTEyMjk0NloXDTIwMTEwMjEyMjk0NlowaDELMAkGA1UEBhMCQ1oxDjAMBgNVBAgMBU51c2xlMQ8wDQYDVQQHDAZQcmFndWUxGTAXBgNVBAoMEEpldEJyYWlucyBzLnIuby4xHTAbBgNVBAMMFHByb2QzeS1mcm9tLTIwMTgxMTAxMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcQkq+zdxlR2mmRYBPzGbUNdMN6OaXiXzxIWtMEkrJMO/5oUfQJbLLuMSMK0QHFmaI37WShyxZcfRCidwXjot4zmNBKnlyHodDij/78TmVqFl8nOeD5+07B8VEaIu7c3E1N+e1doC6wht4I4+IEmtsPAdoaj5WCQVQbrI8KeT8M9VcBIWX7fD0fhexfg3ZRt0xqwMcXGNp3DdJHiO0rCdU+Itv7EmtnSVq9jBG1usMSFvMowR25mju2JcPFp1+I4ZI+FqgR8gyG8oiNDyNEoAbsR3lOpI7grUYSvkB/xVy/VoklPCK2h0f0GJxFjnye8NT1PAywoyl7RmiAVRE/EKwIDAQABo4GZMIGWMAkGA1UdEwQCMAAwHQYDVR0OBBYEFGEpG9oZGcfLMGNBkY7SgHiMGgTcMEgGA1UdIwRBMD+AFKOetkhnQhI2Qb1t4Lm0oFKLl/GzoRykGjAYMRYwFAYDVQQDDA1KZXRQcm9maWxlIENBggkA0myxg7KDeeEwEwYDVR0lBAwwCgYIKwYBBQUHAwEwCwYDVR0PBAQDAgWgMA0GCSqGSIb3DQEBCwUAA4ICAQAF8uc+YJOHHwOFcPzmbjcxNDuGoOUIP+2h1R75Lecswb7ru2LWWSUMtXVKQzChLNPn/72W0k+oI056tgiwuG7M49LXp4zQVlQnFmWU1wwGvVhq5R63Rpjx1zjGUhcXgayu7+9zMUW596Lbomsg8qVve6euqsrFicYkIIuUu4zYPndJwfe0YkS5nY72SHnNdbPhEnN8wcB2Kz+OIG0lih3yz5EqFhld03bGp222ZQCIghCTVL6QBNadGsiN/lWLl4JdR3lJkZzlpFdiHijoVRdWeSWqM4y0t23c92HXKrgppoSV18XMxrWVdoSM3nuMHwxGhFyde05OdDtLpCv+jlWf5REAHHA201pAU6bJSZINyHDUTB+Beo28rRXSwSh3OUIvYwKNVeoBY+KwOJ7WnuTCUq1meE6GkKc4D/cXmgpOyW/1SmBz3XjVIi/zprZ0zf3qH5mkphtg6ksjKgKjmx1cXfZAAX6wcDBNaCL+Ortep1Dh8xDUbqbBVNBL4jbiL3i3xsfNiyJgaZ5sX7i8tmStEpLbPwvHcByuf59qJhV/bZOl8KqJBETCDJcY6O2aqhTUy+9x93ThKs1GKrRPePrWPluud7ttlgtRveit/pcBrnQcXOl1rHq7ByB8CFAxNotRUYL9IF5n3wJOgkPojMy6jetQA5Ogc8Sm7RG6vg1yow==

 https://blog.csdn.net/lanmo555/article/details/41248763



http://dqsh.cnpc.com.cn/dqsh/zbgg/zbggjg_list_10.shtml

https://ftp-idc.pconline.com.cn/pub/download/201010/zh-hans_office_standard_2007.zip?md5=n239mZYYhFKtNePgkexSUQ&expires=1599273679

