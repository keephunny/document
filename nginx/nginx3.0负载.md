

### 负载策略
```
http {
    upstream  serverName{
        server 192.169.0.1:8080;
        server 192.168.0.2:8080;
    }

    server {
        listen       80;
        server_name  localhost;
        location / {
                proxy_pass http://appserver1;
        }
    }

}
```


#### 1.轮询
默认策略，每个请求按时间顺序逐一分配到不同的服务器地址，如果检测到服务器失效 ，则自动剔除。

```
upstream  serverName{
    server 192.169.0.1:8080;
    server 192.168.0.2:8080;
}
```

* fail_timeout：与max_fails结合使用。
* max_fails：设置在fail_timeout参数设置的时间内最大失败次数，如果在这个时间内，所有针对该服务器的请求都失败了，那么认为该服务器会被认为是停机了，
* fail_time：服务器会被认为停机的时间长度,默认为10s。
* backup：标记该服务器为备用服务器。当主服务器停止时，请求会被发送到它这里。
* down：标记服务器永久停机了。

#### 2.权重
指定轮询几率，weight和访问比率成正比，用于后端服务器性能不均的情况。权重越高分配到需要处理的请求越多。此策略可以与least_conn和ip_hash结合使用。
```
upstream  serverName{
    server 192.169.0.1:8080 weight=5;
    server 192.168.0.2:8080 weight=5;
}
```
#### 3.IP绑定
ip_hash指定负载均衡器按照基于客户端IP的分配方式，这个方法确保了相同的客户端的请求一直发送到相同的服务器，以保证session会话。这样每个访客都固定访问一个后端服务器，可以解决session不能跨服务器的问题。
```
upstream  serverName{
    ip_hash; 
    server 192.169.0.1:8080;
    server 192.168.0.2:8080;
}
```

#### 4.最少访问
least_conn把请求转发给连接数较少的后端服务器。轮询算法是把请求平均的转发给各个后端，使它们的负载大致相同；但是，有些请求占用的时间很长，会导致其所在的后端负载较高。这种情况下，least_conn这种方式就可以达到更好的负载均衡效果。
```
upstream  serverName{
    least_conn; 
    server 192.169.0.1:8080;
    server 192.168.0.2:8080;
}
```

#### 5.fair（第三方）
按后端服务器的响应时间来分配请求，响应时间短的优先分配。
```
upstream  serverName{
    fair; 
    server 192.169.0.1:8080;
    server 192.168.0.2:8080;
}
```

#### 6.url_hash（第三方）
按访问url的hash结果来分配请求，使每个url定向到同一个后端服务器，后端服务器为缓存时比较有效。 同一个资源多次请求，可能会到达不同的服务器上，导致不必要的多次下载，缓存命中率不高，以及一些资源时间的浪费。而使用url_hash，可以使得同一个url（也就是同一个资源请求）会到达同一台服务器，一旦缓存住了资源，再此收到请求，就可以从缓存中读取。
```
upstream  serverName{
    hash $request_uri;
    server 192.169.0.1:8080;
    server 192.168.0.2:8080;
}
```