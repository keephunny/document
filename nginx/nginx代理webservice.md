```
    proxy_redirect default;
    #使用$host变量，它的值相当于服务器的主机名（如果使用域名访问，则该值为域名；如果使用IP访问，则该值为IP）。
    #此外可以将主机名和被代理服务器的端口一起传递  $host:$proxy_port,在设置webservice一定要设置$host:$proxy_port，不然会找不到端口          
    proxy_set_header Host $host:$server_port;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    proxy_redirect default;   
    proxy_set_header Host $host:$server_port;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;




    upstream monitor_soap_servers{
        server 100.09.91.159:8786;
        server 100.09.168.68:8786;
        server 100.09.178.20:8786;
    }
    server {
        listen 80;
        server_name  100.09.129.131;

        #### nginx_proxy session共享:
        ssl_session_timeout         1d;
        ssl_session_cache           shared:SSL:50m;

        resolver                    8.8.4.4 8.8.8.8 valid=300s;
        resolver_timeout            10s;

        location / {
            access_log /data/nginx/log/third_search.kkmh.com.log api;
        # 这个地方的配置十分重要，必须这样配置才可以返回正确的wsdl文档
            proxy_set_header Host $host:$server_port;
            proxy_pass http://monitor_soap_servers/;
        }
    }
```