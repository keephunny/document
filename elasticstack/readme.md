ELK->Elastic Stack

* Elasticsearch：基于JSON的分布存储、搜索、分析
* Logstash：动态数据收集管道，java
* Kibana：数据可视化
* Beats：轻量级数据采集器，go


#### 目录结构s
* bin 可执行文件，启动脚本
* config
* lib
* data
* logs
* modules
* plugins

```
#启动
./elasticsearch -d
```

#### 集群节点

* master节点

  用于控制集群的操作，比如创建、删除索引，管理其它非master节点等。

* data节点

  用于执行数据相关的操作，比如文档的CURD。

* 客户端节点

  用于响应用户请求，把请求转发到其它节点。

* 部落节点

  当节点配置tribe.*时，它是一佧物殊的客户端，可以连接多个集群，在所有连接的集群上执行搜索和其它操作。





* Node节点：组成es集群的服务单元，同一集群内节点的名字不能重复，通常在一个节点上分配一个或多个分片。
* Shards分片：当索引上的数据量较大时，通常会将一个索引上的数据进行水平拆分，拆分出来的每个数据库叫作一个分片。在一个分片的索引中写入数据时，通过路由来确定具体写入哪一个分片。所以在创建索经卓越就需要指定分片的数量，并且不能修改。分片后的索引带来了规模上和性能上的提升。每个分片都量上luence中的一个索引文件。每人分片必须有一个主分片和零到多个副本分片。
* Replicas副本：主分片和备份分片都可以对外提供查询服务，写操作时先在主分片上完成，然后分发到备份上。当主分片不可用时，会在备份分片中选举一个作为主分片。所以副本不仅可以提升系统的高可用性能，还可以提升搜索时的并发性能。但副本太多，在写操作时也会增加数据同步的负担。
* Index索引：由一个或多个分片组成，通过索引的名字在集群内进行唯一标识。
* Type类别：指索引内部的逻辑分区，通过type名字在索引内进行唯一标识，在查询时如果没有该值，则表示整个索引中查询。
* Document文档：索引中每一条数据叫作一个文档，类似于关系型数据库中的一条数据通过_id在type内进行唯一标识。
* Settings：对集群中索引的定义，比如一个索引默认的分片数，副本数等。
* Mapping：类似于关系型数据中表结构信息，用于定义索引中字段（Field）的存储类型、分词方式、是否存储等信息。Elasticsearch中的mapping是可以动态识别的。如果没有特殊需求，则不需要手动创建mapping，因为Elasticsearch会自动根据数据格式识别它的类型，但是当需要对某些字段添加特殊属性（比如：定义使用其他分词器、是否分词、是否存储等）时，就需要手动设置mapping了。一个索引的mapping一旦创建，若已经存储了数据，就不可修改了。
* Analyzer：字段的分词方式定义，一个analyzer通常由一个tokenizer，零到多个filter组成，比如默认的标准Analyzerr包含一个标准的token和三个filer：standard token filter、lower case token filter、stop token filter。








#### 健康检查
* green
* yellow
* red


_cluster/health


kibana devtool
GET _cat/health?v


#### 数据类型s
* 字符型
  * string：在旧版本使用。5.x后不在支持
  * text：当字段需要全文检索时，字符串会被分析器分成一个个词项，text类型不用于是排序、聚合。s
  * keyword：适用于索引结构化字段，可以排序、聚合，keyword类型支能通过精确值搜索。s
* 整数类型
  * byte：
  * short：
  * integer：
  * long：
* 浮点类型：
  * double：64位双精度
  * float：32位单精度
  * half_float：16位半精度s
  * scaled_float：缩放型浮点数
*  boolean类型
* date类型
  * 格式化的date字符串，例如"2018-01-01"或者"2018-01-01 12:00:00"
  * 一个long型的数字，代表从1970年1月1号0点到现在的毫秒数
  * 一个integer型的数字，代表从1970年1月1号0点到现在的秒数
* binary类型
进制字段是指用base64来表示索引中存储的二进制数据，可用来存储二进制形式的数据，例如图像。默认情况下，该类型的字段只存储不索引。二进制类型只支持index_name属性。
* array类型
  json数组，数组中的所有值必须具有相同的数据类型
* object类型
json对象，文档会包含嵌套的对象
* ip类型
* 地理类型
```
   "location": {
        "lat": 23.11, "lon": 113.33		// 纬度: latitude, 经度: longitude
    }
    "location": "23.11, 113.33" 	
```
* 范围类型

  * integer_range：−231−231 ~ 231−1231−1
  * long_range：−263−263 ~ 263−1263−1
  * float_range：32位单精度浮点型
  * double_range：64位双精度浮点型
  * date_range：64位整数, 毫秒计时
  * ip_range：IP值的范围, 支持IPV4和IPV6, 或者这两种同时存在

##### Mapping
* enabled:是否开启检索和聚合分析s
* index：是否构建倒排索引s
* index_option：存储倒排索引的哪些信息
  * docs：索引文档号
  * freqs：文档号+词频
  * positions：文档号+词频+位置，通常用来距离查询
  * offsets：文档号+词频+位置+偏移量，通常被使用在高亮字段
* norms：是否归一化相关参数，如果字段仅用于过滤和聚合分析、可关闭
分词字段默认配置，不分词字段：默认{“enable”: false}，存储长度因子和索引时boost，建议对需要参加评分字段使用，会额外增加内存消耗
* doc_value：是否开启doc_value，用户聚合和排序分析
对not_analyzed字段，默认都是开启，分词字段不能使用，对排序和聚合能提升较大性能，节约内存
* fielddata：是否为text类型启动fielddata，实现排序和聚合分析
针对分词字段，参与排序或聚合时能提高性能，不分词字段统一建议使用doc_value
* store：是否单独设置此字段的是否存储而从_source字段中分离，只能搜索，不能获取值
* coerce：是否开启自动数据类型转换功能，比如：字符串转数字，浮点转整型
* multifields：灵活使用多字段解决多样的业务需求
* dynamic：控制mapping的自动更新
* data_detection：是否自动识别日期类型
* analyzer：指定分词器，默认分词器为standard analyzer
* boost：字段级别的分数加权，默认值是1.0
* fields：可以对一个字段提供多种索引模式，同一个字段的值，一个分词，一个不分词
* ignore_above：超过100个字符的文本，将会被忽略，不被索引
  "ignore_above": 100
* include_in_all：设置是否此字段包含在_all字段中，默认时true，除非index设置成no
  "include_in_all": true
* null_value：设置一些缺失字段的初始化，只有string可以使用，分词字段的null值也会被分词
  "null_value": "NULL"
* position_increament_gap：影响距离查询或近似查询，可以设置在多值字段的数据上或分词字段上，查询时可以指定slop间隔，默认值时100
  "position_increament_gap": 0
* search_analyzer：设置搜索时的分词器，默认跟analyzer是一致的，比如index时用standard+ngram，搜索时用standard用来完成自动提示功能
  "search_analyzer": "ik"
* similarity：默认时TF/IDF算法，指定一个字段评分策略，仅仅对字符串型和分词类型有效
  "similarity": "BM25"
* trem_vector：默认不存储向量信息，支持参数yes（term存储），with_positions（term+位置），with_offsets（term+偏移量），with_positions_offsets（term+位置+偏移量）对快速高亮fast vector highlighter能提升性能，但开启又会加大索引体积，不适合大数据量用
  "trem_vector": "no"


#### 常用语法
创建索引
```
PUT user
{
  "mapping":{
    "properties":{
      "name":{
        "type":"keyword"
      },
      "name2":{
        "type":"text"
      },
      "age":{
        "type":"integer"
      }
    }
  }
}
```
保存数据
```
PUT /user/_doc/1
{
  "name":"张三",
  "name2":"中国北京",
  "age":23
}
```

查询数据
match查询keyword时，不会对搜索词进行分词，name=‘张’
match在过滤text字段时，会把搜索词进行拆分再查询，
```
GET /user/_doc/_search
{
  "query":{
    "match":{
      "name":"张"
    }
  }
}
```
term精确查询，查询时不会对搜索词进行分词
term不会对搜索词拆分，而es存储的text字段已经分别拆成了[张，三] ，所以这时候用全名去匹配，结果肯定是空的。


```
GET /user/_doc/_search
{
  "query":{
    "term":{
      "name":"张三"
    }
  }
}
```

filter过滤查询
filter查询不考虑评分，它适用于精确匹配、范围过滤，拥有更快的性能，推荐适用。
```
GET /user/_doc/_search
{
  "query":{
    "bool":{
      "filter":{
        "term":{
          "name":"张三"
        }
      }
    }
  }
}
```

range区间过滤
```
GET /user/_doc/_search
{
  "query":{
    "range":{
      "age":{
        "gte":1,
        "lte":10
      }
    }
  }
}
```

sort排序 
from分页
```
{
  "query":{
    "match":{
      "name":"张"
    }
  },
  "sort":{
    "age":{
      "order":"asc"
    }
  },
  "from":0,
  "size":1
}
```

must查询相当于and
should查询相当于or
must_not查询，相当于是!= and !=
```
GET /user/_doc/_search
{
  "query":{
    "bool":{
      "must":[
        {
          "match":{
            "name":"张三"
          }
        },
        {
          "match":{
            "age":11
          }
        }
      ]
    }
  }
}
```





### Elasticsearch索引有效期

```
POST /_ilm/start

GET /_ilm/policy


PUT /_ilm/policy/index_ttl_one_hours_policy
	{
        "policy": {
            "phases": {
                "delete": {
                    "min_age": "1h",
                    "actions": {
                        "delete": {

                        }
                    }
                }
            }
        }
    }

```

