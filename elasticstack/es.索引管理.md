
#### 索引管理
创建索引
```
PUT /my_index
{
    "settings": {
        "number_of_shards":1,
        "number_of_replicas":1
    },
    "mappings": {
        "type_one": { ... any mappings ... },
        "type_two": { ... any mappings ... }
    }
}
```
禁止自动创建索引
action.auto_create_index: false

删除索引
```
DELETE /my_index
DELETE /index_one,index_two
DELETE /index_*
DELETE /_all
DELETE /*
```

修改索引
```
PUT /my_temp_index/_settings
    {
        "number_of_replicas": 1
    }
```


* number_of_shards：每个索引的主分片数，默认值是 5 。这个配置在索引创建后不能修改。
* number_of_replicas：每个主分片的副本数，默认值是 1 。对于活动的索引库，这个配置可以随时修改。