nginx有一个非常灵活的日志记录模式。每个级别的配置可以有各自独立的访问日志。日志格式通过log_format命令来定义。ngx_http_log_module是用来定义请求日志格式的。


### access_log配置
    语法：access_log path [format [buffer=size] [gzip[=level]] [flush=time] [if=condition]]; 
    默认值：access_log logs/access.log combined;
    作用域：http, server, location, if in location, limit_except
    access_log off; 不记录日志
    gzip压缩等级。
    buffer设置内存缓存区大小。
    flush保存在缓存区中的最长时间。

### log_format配置
    语法格式:log_format name [escape=default|json] string ...;
    默认值:log_format combined "...";
    作用域:http

* $bytes_sent：发送给客户端的总字节数
* $body_bytes_sent：记录了nginx响应客户端请求时，发送到客户端的字节数，不包含响应头的大小。
* $connection：连接序列号
* $connection_requests：当前通过连接发出的请求数量
* $msec：日志写入时间，单位为秒，精度是毫秒
* $pipe：如果请求是通过http流水线发送，则其值为"p"，否则为“."
* $request_length：请求长度（包括请求行，请求头和请求体）
* $request_time：请求处理时长，单位为秒，精度为毫秒，从读入客户端的第一个字节开始，直到把最后一个字符发送张客户端进行日志写入为止
* $status：响应状态码，比如200、404等响应码，都记录在此变量中。
* $time_iso8601：标准格式的本地时间,形如“2017-05-24T18:31:27+08:00”
* $time_local：通用日志格式下的本地时间，如"24/May/2017:18:31:27 +0800"
* $http_referer：请求的referer地址。
* $http_user_agent：客户端浏览器信息，浏览器的名称和版本号。
* $remote_addr：客户端IP
* $http_x_forwarded_for：当前端有代理服务器时，设置web节点记录客户端地址的配置，此参数生效的前提是代理服务器也要进行相关的x_forwarded_for设置。
* $request：记录了当前http请求的方法、url和http协议版本，如 "GET / HTTP/1.1"
* $remote_user：当nginx开启了用户认证功能后，此变量记录了客户端使用了哪个用户进行了认证。
* $request_uri：完整的请求地址，如 "https://www.baidu.com/" 



### open_log_file_cache配置
对于每一条日志记录，都将是先打开文件，再写入日志，然后关闭。可以使用open_log_file_cache来设置日志文件缓存(默认是off)，格式如下：
参数注释如下：
    max:设置缓存中的最大文件描述符数量，如果缓存被占满，采用LRU算法将描述符关闭。
    inactive:设置存活时间，默认是10s
    min_uses:设置在inactive时间段内，日志文件最少使用多少次后，该日志文件描述符记入缓存中，默认是1次
    valid:设置检查频率，默认60s
    off：禁用缓存

    语法: open_log_file_cache max=N [inactive=time] [min_uses=N] [valid=time];
    open_log_file_cache off;
    默认值: open_log_file_cache off;
    配置段: http, server, location


### log_not_found配置
是否在error_log中记录不存在的错误。
    语法: log_not_found on | off;
    默认值: log_not_found on;
    作用域: http, server, location


### log_subrequest配置
是否在access_log中记录子请求的访问日志。默认不记录。
    语法: log_subrequest on | off;
    默认值: log_subrequest off;
    作用域: http, server, location

### rewrite_log配置
由ngx_http_rewrite_module模块提供的。用来记录重写日志的。对于调试重写规则建议开启。
    语法: rewrite_log on | off;
    默认值: rewrite_log off;
    作用域: http, server, location, if
    启用时将在error log中记录notice级别的重写日志。

### error_log配置
配置错误日志。
    语法: error_log file | stderr | syslog:server=address[,parameter=value] [debug | info | notice | warn | error | crit | alert | emerg];
    默认值: error_log logs/error.log error;
    作用域: main, http, server, location



### 常用配置
#### 配置json日志格式
```
    http {
        log_format access_json '{"timestamp":"$time_iso8601",'
                            '"host":"$server_addr",'
                            '"clientip":"$remote_addr",'
                            '"size":$body_bytes_sent,'
                            '"responsetime":$request_time,'
                            '"upstreamtime":"$upstream_response_time",'
                            '"upstreamhost":"$upstream_addr",'
                            '"http_host":"$host",'
                            '"url":"$uri",'
                            '"domain":"$host",'
                            '"xff":"$http_x_forwarded_for",'
                            '"referer":"$http_referer",'
                            '"status":"$status"}';
        access_log logs/access.log access_json;
    }
```

#### 指定日志级别
error_log /var/logs/nginx/static-error.log debug; 

##### 设记录来自于指定IP日志
    events {
        debug_connection 1.2.3.4;
    }

##### 调试rewrite规则
调试rewrite规则时，如果规则写错只会看见一个404页面，可以在配置文件中开启nginx rewrite日志，进行调试。
```
    server {
        error_log    /var/logs/nginx/example.com.error.log;
        rewrite_log on;
    }
```
#### 压缩格式
日志中增加了压缩的信息。
```
    http {
        log_format compression '$remote_addr - $remote_user [$time_local] '
                            '"$request" $status $body_bytes_sent '
                            '"$http_referer" "$http_user_agent" "$gzip_ratio"';

        server {
            gzip on;
            access_log /spool/logs/nginx-access.log compression;
            ...
        }
    }
```

#### 统计status 出现的次数
awk '{print $9}' access.log | sort | uniq -c | sort -rn

    36461 200 
    483 500
    87 404