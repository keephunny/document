
### output配置ElasticSearch
```
output {
　　elasticsearch{
      action => "index"
      index => "%{[fields][product_type]}-transaction-%{+YYYY-MM}"
   　　hosts => ["10.0.xx.xx:9200", "10.0.xx.xx:9200", "10.0.xx.xx:9200"]
   }
}
```

* action
    * index：给一个文档建立索引
    * delete：通过id值删除一个文档（这个action需要指定一个id值）
    * create：插入一条文档信息，如果这条文档信息在索引中已经存在，那么本次插入工作失败
    * update：通过id值更新一个文档。更新有个特殊的案例upsert，如果被更新的文档还不存在，那么就会用到upsert
* index
    写入事件所用的索引。可以动态的使用%{foo}语法，它的默认值是：
    "logstash-%{+YYYY.MM.dd}"，以天为单位分割的索引，使你可以很容易的删除老的数据或者搜索指定时间范围内的数据。
    索引不能包含大写字母。推荐使用以周为索引的ISO 8601格式，例如logstash-%{+xxxx.ww}
    示例：index => "%{[fields][product_type]}-transaction-%{+YYYY-MM}"
* hosts：地址,http协议使用的是http地址，端口是9200。
    示例：hosts => ["10.0.xx.xx:9200", "10.0.xx.xx:9200", "10.0.xx.xx:9200"]
* document_type：定义es索引的type，一般你应该让同一种类型的日志存到同一种type中，比如debug日志和error日志存到不同的type中
* template：设置指向你自己模板的路径。如果没有设置，那么默认的模板会被使用
* template_name：配置项用来定义在Elasticsearch中模板的命名
* template_overwrite：设置为true表示如果你有一个自定义的模板叫logstash，那么将会用你自定义模板覆盖默认模板logstash
* manage_template：设置为false将关闭logstash自动管理模板功能，比如你定义了一个自定义模板，更加字段名动态生成字段，那么应该设置为false
* order：template 是可以设置 order 参数的！而不写这个参数，默认的 order 值就是 0。order 值越大，在 merge 规则的时候优先级越高。



### 索引生命周期

创建索引生命周期

```
# 创建索引存储策略
PUT /_ilm/policy/logstash_plicy
{
	"policy": {
		"phases": {
			"delete": {
				"min_age": "2d",
				"actions": {
					"delete": {
					}
				}
			}
		}
	}
}
```



创建索引模版

```
PUT _template/logstash_plicy_ilm_template
{
  "index_patterns": ["logstash-*"],
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 1,
    "index.lifecycle.name":"logstash_plicy", 
    "index.lifecycle.rollover_alias": "logstash_log"
  }
}
```



```
output {
  elasticsearch{
        hosts => ["http://localhost:9200"]
        ilm_enabled => true
        ilm_rollover_alias => "nginx_log"
        ilm_pattern => "nginx_log-{now/d}-000001"
        ilm_policy => "nginx_log_ilm_policy"
  }
}
```

* hosts：uri设置远程实例的主机
* ilm_enabled：[“true”, “false”, “auto”]	auto如果Elasticsearch为7.0.0以上版本，且启用了ILM功能则默认设置为会自动启用索引生命周期管理功能，否则将其禁用。
* ilm_rollover_alias：过渡别名
* ilm_pattern：模式中指定的值将附加到写别名，并在ILM创建新索
* ilm_policy：修改此设置以使用自定义的索引生命周期管理策略，而不是默认策略。
* template：可以在此处将路径设置为自己的模板
* template_name：定义如何在Elasticsearch中命名模板
* template_overwrite：选项总是会用Elasticsearch中指定的模板或包含的模板覆盖指定的模板
* timeout：发送到Elasticsearch的请求的超时限制



查看索引生命周期是否生效

```basic
GET logstash-*/_ilm/explain
    
    #不生效
    "logstash-dl-2023.02.23.06" : {
      "index" : "logstash-dl-2023.02.23.06",
      "managed" : false
    }
    
    #生效
    "logstash-dl-2023.02.21" : {
      "index" : "logstash-dl-2023.02.21",
      "managed" : true,
      "policy" : "logstash-plicy-2d"
     }
```



**时间差8小时**

由于Elasticsearch、Logstash内部，对时间类型字段，是统一采用 UTC 时间，outputs/elasticsearch中常用的 %{+YYYY.MM.dd}

这种写法必须读取 @timestamp，为了解决索引产生的时间问题，必须先解决@timestamp时区问题。结合网上资料有以下几种思路：

```
filter {
	#设置一个自定义字段'timestamp'[这个字段可自定义]，将logstash自动生成的时间戳中的值加8小时，赋给这个字段
	ruby {
		code => "event.set('timestamp', event.get('@timestamp').time.localtime + 8*3600)"
	}
	#将自定义时间字段中的值重新赋给@timestamp
	ruby {
		code => "event.set('@timestamp',event.get('timestamp'))"
	}
	#删除自定义字段
	mutate {
		remove_field => ["timestamp"]
	}	
}

filter{
	ruby {
		code => "event.timestamp.time.localtime"
	}
}
或
filter{
  ruby {
    code => "event['@timestamp'] = LogStash::Timestamp.coerce(event['@timestamp'].time.localtime)"
  }
}



code =>   (event.get('@timestamp').time.localtime + 8*60*60).strftime('%Y-%m-%d %H:%M:%S'))"


```



方法一：

```
ruby {
    code => "event.set('s_index_name_time', (event.get('@timestamp').time.localtime + 8*3600).strftime('%Y.%m.%d.%H'))"
  }
index => "logstash-%{app_name}-%{s_index_name_time}"


"_index" : "logstash-dl-iot-link-2023.02.23.19",
"@timestamp" : "2023-02-23T11:22:41.646Z",
"s_index_name_time" : "2023.02.23.19",

currentTime：2023.02.23 19:22:41

code => "event.set('s_index_name_time', (event.get('@timestamp').time.localtime).strftime('%Y.%m.%d.%H'))"



```







### filter

```
filter {

	mutate{
		split => ["message","^"]
		add_field =>   {
            "my_time" => "%{[message][8]}"
            "my_timest" => "%{[message][8]}"
        }
	}
	date {
		match => ["my_time","yyyyMMddHHmmss"]
		target => "my_time"
	}
	date {
		match => ["my_timest","yyyyMMddHHmmss"]
		target => "my_timest"
	}
	ruby {
		code => "event.set('my_time', (event.get('my_time').time.localtime).strftime('%Y-%m-%d %H:%M:%S'))"
    }
	
	ruby {
        code => "event.set('my_timest', (event.get('my_timest').time.localtime).strftime('%Y-%m-%d'))"
    }
 }
```

mutate 插件是 Logstash另一个重要插件。它提供了丰富的基础类型数据处理能力。可以重命名，删除，替换和修改事件中的字段。

