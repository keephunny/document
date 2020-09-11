expain出来的信息有10列，分别是id、select_type、table、type、possible_keys、key、key_len、ref、rows、Extra


### id:选择标识符
select查询的序列号，包含一组数字，表示查询中执行select子句或操作表的顺序，该字段通常与table字段搭配来分析。
* id相同，可认为是同一组，执行顺序从上到下。
* id不同，如果是子查询，id的序号会递增，id值越大执行优先级越高。


### select_type:表示查询的类型
查询的类型，主要用于区别普通查询、联合查询、子查询等复杂的查询。
1. SIMPLE

简单的select查询，查询中不包含子查询或union查询。

2. PRIMARY
查询中若包含任何复杂的子部分，最外层查询为PRIMARY，也就是最后加载的就是PRIMARY。

3. SUBQUERY
在select或where列表中包含了子查询，就为被标记为SUBQUERY。

4. DERIVED
在from列表中包含的子查询会被标记为DERIVED(衍生)，MySQL会递归执行这些子查询，将结果放在临时表中。

5. UNION
若第二个select出现在union后，则被标记为UNION，若union包含在from子句的子查询中，外层select将被标记为DERIVED。

6. UNION RESULT
从union表获取结果的select。

7. DEPENDENT SUBQUERY
子查询中的第一个SELECT，依赖于外部查询

8. DERIVED
派生表的SELECT, FROM子句的子查询

9. UNCACHEABLE SUBQUERY
一个子查询的结果不能被缓存，必须重新评估外链接的第一行


### table:输出结果集的表
示这一步所访问数据库中表名称（显示这一行的数据是关于哪张表的），有时不是真实的表名字，可能是简称。

### partitions:匹配的分区
官方定义为The matching partitions（匹配的分区）


### type:表示表的连接类型
对表访问方式，表示MySQL在表中找到所需行的方式，又称“访问类型”。

常用的类型有： ALL、index、range、 ref、eq_ref、const、system、NULL（从左到右，性能从差到好）

1. ALL：Full Table Scan， MySQL将遍历全表以找到匹配的行

2. index: Full Index Scan，全索引扫描，index和ALL的区别：index只遍历索引树，通常比ALL快，因为索引文件通常比数据文件小。虽说index和ALL都是全表扫描，但是index是从索引中读取，ALL是从磁盘中读取。

3. range:只检索给定范围的行，使用一个索引来检索行，可以在key列中查看使用的索引，一般出现在where语句的条件中，如使用between、>、<、in等查询。

4. ref: 非唯一性索引扫描，返回匹配某个单独值的所有行。本质上也是一种索引访问，返回匹配某值（某条件）的多行值，属于查找和扫描的混合体。

5. eq_ref: 类似ref，区别就在使用的索引是唯一索引，对于每个索引键值，表中只有一条记录匹配，简单来说，就是多表连接中使用primary key或者 unique key作为关联条件

6. const、system: 当MySQL对查询某部分进行优化，并转换为一个常量时，使用这些类型访问。如将主键置于where列表中，MySQL就能将该查询转换为一个常量，system是const类型的特例，当查询的表只有一行的情况下，使用system，表示通过一次索引就找到了结果，常出现于primary key或unique索引。因为只匹配一行数据，所以查询非常快。

7. NULL: MySQL在优化过程中分解语句，执行时甚至不用访问表或索引，例如从一个索引列里选取最小值可以通过单独索引查找完成。


### possible_keys:表示查询时，可能使用的索引
指出MySQL能使用哪个索引在表中找到记录，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询使用（该查询可以利用的索引，如果没有任何索引显示 null）


### key:表示实际使用的索引
key列显示MySQL实际决定使用的键（索引），必然包含在possible_keys中
如果没有选择索引，键是NULL。要想强制MySQL使用或忽视possible_keys列中的索引，在查询中使用FORCE INDEX、USE INDEX或者IGNORE INDEX。



### key_len:索引字段的长度
表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度（key_len显示的值为索引字段的最大可能长度，并非实际使用长度，即key_len是根据表定义计算而得，不是通过表内检索出的）

### ref:列与索引的比较
显示关联的字段。如果使用常数等值查询，则显示const，如果是连接查询，则会显示关联的字段。

### rows:扫描出的行数(估算的行数)
根据表统计信息及索引选用情况大致估算出找到所需记录所要读取的行数。当然该值越小越好。


### filtered:按表条件过滤的行百分比
百分比值，表示存储引擎返回的数据经过滤后，剩下多少满足查询条件记录数量的比例。

### Extra:重要的额外信息
1. Using filesort：当Query中包含 order by 操作，而且无法利用索引完成的排序操作称为“文件排序”
2. Using temporary：表示MySQL需要使用临时表来存储结果集，常见于排序和分组查询，常见 group by、order by。
3. Using index：表明相应的select操作中使用了覆盖索引，避免访问表的额外数据行。
4. Using where：不用读取表中所有信息，仅通过索引就可以获取所需数据，这发生在对表的全部的请求列都是同一个索引的部分的时候，表示mysql服务器将在存储引擎检索行后再进行过滤
5. Using join buffer：改值强调了在获取连接条件时没有使用索引，并且需要连接缓冲区来存储中间结果。如果出现了这个值，那应该注意，根据查询的具体情况可能需要添加索引来改进能。
6. Impossible where：这个值强调了where语句会导致没有符合条件的行（通过收集统计信息不可能存在结果）。
7. Select tables optimized away：这个值意味着仅通过使用索引，优化器可能仅从聚合函数结果中返回一行
8. No tables used：Query语句中使用from dual 或不含任何from子句