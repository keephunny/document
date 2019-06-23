 ### 修改host文件
 
1. C:\Windows\System32\drivers\etc\hosts
2. 在https://www.ipaddress.com/ 查询github的ip
	* github.com
	* ithub.global.ssl.fastly.net
![](./resources/20190623220047.png)
3. 在hosts里填入查询的结果
```	
	# localhost name resolution is handled within DNS itself.
	#	127.0.0.1       localhost
	#	::1             localhost

	192.30.253.112	github.com
	151.101.185.194	github.global.ssl.fastly.net
```
4. 刷新DNS缓存
```
	ipconfig /flushdns
```