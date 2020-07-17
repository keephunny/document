location [修饰符] pattern {…}

=	精确匹配
~	正则表达式模式匹配，区分大小写
~*	正则表达式模式匹配，不区分大小写
^~	前缀匹配，类似于无修饰符的行为，也是以指定模块开始，不同的是，如果模式匹配，那么就停止搜索其他模式了，不支持正则表达式
@	定义命名location区段，这些区段客户端不能访问，只可以有内部产生的请求访问，如try_files或error_page等


location ~ .*\.(jsp|do|action)$
location ~ .*\.(gif|jpg|jpeg|png|bmp|swf|ico|svg)$

基于浏览器实现分离案例
if ($http_user_agent ~ Firefox) {		##火狐浏览器
	rewrite ^(.*)$ /firefox/$1 break;
}
if ($http_user_agent ~ MSIE) {		##微软浏览器
 	rewrite ^(.*)$ /msie/$1 break;
}
if ($http_user_agent ~ Chrome) {		##谷歌浏览器
 	rewrite ^(.*)$ /chrome/$1 break;
}
1
2
3
4
5
6
7
8
9
防盗链案例
location ~* \.(jpg|gif|jpeg|png)$ {
 	valid_referers none blocked DOMAIN_NAME;		##DOMAIN_NAME只公司的域名
 	if ($invalid_referer) {
 	rewrite ^/ http://DOMAIN_NAME/403.html;
 	}
}
