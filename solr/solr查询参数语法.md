https://blog.csdn.net/zhufenglonglove/article/details/51518846

https://blog.csdn.net/gufengshanyin/article/details/21098879
## 常用查询参数
* defType 指定用于处理查询语句(参数q的内容)的查询解析器，eg:defType=lucene
* sort 指定响应的排序方式：升序asc或降序desc.同时需要指定按哪个字段进行排序。字段 asc|desc
* start 分页，指定查询的开始位置
* rows 分页，指定一次显示多少行结果
* fq 使用Filter Query可以充分利用FilterQuery Cache，提高检索性能。作用：在q查询符合结果中同时是fq查询符合的，例如：q=mm&fq=date_time:[20081001TO 20091031]，找关键字mm，并且date_time是20081001到20091031之间的。（fq查询字段后面的冒号和关键字必须有）