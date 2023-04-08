

#### 简单查询
```
GET /msg-log-2023.01.10/_search
{
  "query":{
    "match": {
      "device":"80000"
    }
  },
  "sort":{
    "time":"desc"
  },
  "from":0,
  "size":109,
  "_source":["xx","xx1","xx2"]
}
```


#### match查询
全文匹配，数值类型完全匹配，字符串根据检索条件进行分词匹配，按得分降序。如果字符串需要精确匹配，可以在字段后面加上.keyword
```
GET /索引名/_search
{
  "query":{
    "match":{
      "字段":"xxx"
    }
  }
}

```

#### math_phrase
短语匹配，将检索值当成一个整体单词不分词进行检索。
```
GET /索引名/_search
{
  "query":{
    "match_phrase":{
      "字段":"xx"
    }
  }
}
```

#### multi_match
```
GET /索引名/_search
{
  "query":{
    "multi_match":{
      "query":"xxxx",
      "fields":["字段1","字段2"]
    }
  }
}
```

#### bool复合查询
```
GET /索引名/_search
{
  "query":{
    "bool":{
      "must":[
        {
          "match":{
            "字段1":"xx"
          }
        },
        {
          "match":{
            "字段2":"xx"
          }
        }
      ]
    }
  }
}
```


#### filter
filter是一个过滤器，能够对查询结果进行过滤，且不会贡献相关性得分
```
GET /索引名/_search
{
  "query":{
    "bool":{
      "filter":{
        {
          "range":{
            "字段":{
              "gte":10,
              "lte":20
            }
          }
        }
      }
    }
  }
}
```

#### term
和match一样，匹配某个属性的值，全文检索用match，非text字段使用term
```
GET /索引名/_search
{
  "query":{
    "term":{
      "字段":{
        "value":11
      }
    }
  }
}

```