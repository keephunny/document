### 基本概念

1. 近实时（NRT）

Elasticsearch是一个接近实时的搜索平台，从索引一个文档到这个文档能够被搜索到有一个轻微的延迟。基于es执行搜索和分析可以达到秒级

2. 集群（Cluster）

一个集群就是由一个或多个节点组织在一起，它们共同的持有整个数据，并一起提供索引和搜索功能。一个集群由一个唯一的标识符，默认是“elasticsearch”。节点只能通过指定某个集群的名字，来加入这个集群。

3. 节点（node）

   节点作为集群中的一部份，可以存储数据、参与集群索引及搜索操作。节点可以通过配置集群名称的方式来加入一个指定的集群。默认情况下，每个节点都会被安排加入到一个叫做“elasticsearch”的集群中。

4. 索引（index）

一个索引就是一个拥有几分相似特征的文档集合，索引标识识符由小字母组成，可以根据这个标识符进行索引、搜索、更新、删除等操作。索引类似于关系型数据中的Database概念。

5. 类型（type）

在一个索引中，可以定义一种或多种类型，一个类型是索引在逻辑上的分类/分区。通常会为具有一组共同字段的文档定义一个类型。类型类似于关系型数据库中Table的概念。

6. 文档（document）

文档就是一行数据，es中的最小数据单元，一个document可以是一条客户数据，一条商品分类数据，一条订单数据，文档以json格式来表示。每个index下的type中，都可以去存储多个document。一个document里面有多个field，每个field就是一个数据字段。文档类似于数据库中的record概念。

7. 分片（shard）

单台服务器无法存储大量数据，es可以将一个索引的数据切分为多个shard，分布在多台服务器上存储。有了shard就可以横向扩展，存储更多的数据。让索引和分析等操作分布到多台服务器上执行，提升吞吐量和性能，每个shard都是一个lucene index。

8. 复制（replica）

任何一个服务器随时可能故障或宕机，此时shard可能就会丢失，因此可以为每个shard创建多个replica副本。replica可以在shard故障时提供备用服务，保证数据不丢失。多个replica还可以提供搜索操作的性能。primary shard建立索引时默认5个不允许修改。replica shard默认1个，可以修改。默认每个索引10个shard，5个primary shard，5个replica shard，最小的高可用配置，是2台服务器。



#### shard与replica

* 一个index包含多个shard，也就是一个index存在于多个服务器上
* 每个shard都是一个最小工作单元，承载部份数据，比如有三台服务器,现在有三条数据,这三条数据在三台服务器上各方一条。
* 增减节点时，shard会自动在nodes中负载。
* primary shard和replica shard，每个document肯定只存在于某一个primary shard以及其对应的replica shard中，不可能存在于多个primary shard
* replica shard是primary shard的副本，负责容错，以及承担读请求负载
* primary shard的数量在创建索引时就固定了，replica shard的数量可以随时修改。
* primary shard的默认数量是5，replica默认是1，默认有10个shard，5个primary shard，5个replicat shard。
* primary shard不能和自已的replica shard放在同一个节点。







**集群健康值**

* green：所有主要分片和复制分片都可用
* yellow：所有主要分片可用，部份复制分片不可用
* red：部份主要分片不可用



#### ES数据类型

- text：全文检索字符串
- keyword：用于精确字符串匹配和聚合
- date、date_nanos：格式化日期或数字日期
- byte、short、integer、long：整型
- boolean：布尔类型
- float、double、half_float：浮点类型
- object：json对象型
- nested：保留子字段之间关系的JSON对象。A JSON object that preserves the relationship between its subfields.



### 创建索引

创建一个索引（index）并插入一个文档（document）。如果在RDMS中，通常需要先创建数据和表才能插入数据。但elasticsearch却不需要，可以直接创建并插入数据。为了提高入门的易用性，Elasticsearchh可以自动动态的创建mapping，当建立一个索引的第一个文档时，如果你没有创建schema，那么es会根据输入的字段猜测数据类型，并自动创建schema，这种方式我们称为schema on write。mapping被称作为es的数据schema。文档中所有字段都需要映射到es中的数据类型。mapping指定每一个字段的数据类型，并确定如何索引和分析字段以进行搜索。mapping 可以显式声明或动态生成。一旦一个索引的某个字段的类型被确定下来之后，那么后续导入的文档的这个字段的类型必须是和之前的是一致，否则写入将导致错误。动态 mapping 还可能导致某些字段未映射到你的预期，从而导致索引请求失败。显式 mapping 允许更好地控制索引中的字段和数据类型。

```
PUT 索引名/文档名/ID
{
	"uid":1,
	"user":"张三",
	"city":"北京"
}

response：
{
	"_index":"索引名",
	"_type":"文档名",
	"_id":"1"
	"_version":4		//版本会每次自动增加1
	"result":"updated",
	"_shards":{
		"total":2,	//两个分版
		"successful":1,
		"failed":0
	},
	"_seq_no":8,
	"_primary_term":1
}
```

**修改自动创建索引**

```
PUT _cluster/settings
{
	"persistent":{
		"action.auto_create_index":"false"
	}
}
```

通常对一个通过上面方法写入到 Elasticsearch 的文档，在默认的情况下并不马上可以进行搜索。这是因为在 Elasticsearch 的设计中，有一个叫做 refresh 的操作。这个周期为1秒。

```
强制更新
PUT twitter/_doc/1?refresh=true
同步的操作，它等待下一个 refresh 周期发生完后，才返回。
PUT twitter/_doc/1?refresh=wait_for
```



