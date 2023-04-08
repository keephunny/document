https://www.bilibili.com/video/BV1LF411j7rm


# P18 【索引的CRUD】索引的CRUD

常用语法
```
插入数据
PUT /index_name/_doc/[id]
{
	"name":"xxx"
}
修改数据-只修改指定字段
POST /index_name/_update/_id
{
	"name":"xxx"
}
删除数据
DELETE /index_name/_doc/id
```

# P19 【映射】Mapping
定义文档及其包含字段的存储和索引方式的过程。
* dynamic mapping动态映射或自动映射
* expllcit mapping 静态映射或手工映射

### 查看mapping
GET /index_name/_mapping

### ES数据类型
#### 基本类型
1. 数字类型
    long、integer、short、byte、double、float、

2. keywords
    * keyword：用于索引结构化字段，过滤、排序、聚合只能通过精准值（exact)搜索到。
    * constant_keyword：始终包含相同值的关键字段
    * wildcard：可针对类似grep的通配符查询优化日志行和类似的关键字值

3.  Date时间类型
* date
* date

#### 对象类型
* object：json对象
* nested：json对象数组

#### 结构化类型
* geo-point：经纬度
* geo-shape：多边形

### 映射参数

* index：是否创建索引，默认true，false则不能通过该字段检索
* alalyzer：指定分析器，character filter、tokenizer、token filters
* boost：权重
* coerce：是否允许强制类型转换
* doc_value：提代排序和聚合效率
* dynamic：是否可以动态添加字段
    * true：可以动态添加
    * false：索引的 Mapping 是不会被更新的，新增字段的数据无法被索引，也就是无法被搜索，但是信息会出现在 _source 中。
    * strict：文档写入会失败。
* fields：创建多字段，用于不同目的
* format：格式化
* ignore_above：超过长度补忽略
* ignore_malformed：忽略类型错误


禁用source返回
GET /index_name/_search
{
    "_source":false,
    "query":{}
}

测试分词
GET /_analyze
{
    "analyzer":"standard",
    "text":"name xx"
}