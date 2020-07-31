### String字符
string 是 redis 最基本的类型，可以包含任何数据。比如jpg图片或者序列化的对象。值最大能存储 512MB。


#### 常用命令
```
#设置指定 key 的值
set key value

#获取指定 key 的值。
get key

#返回 key 中字符串值的子字符
getrange key start end

#将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
getset key value

#对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
getbit key offset

#获取所有(一个或多个)给定 key 的值。
mget key1 [key2..]

#对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
setbit key offset value

#将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
setex key seconds value

#只有在 key 不存在时设置 key 的值。
setnx key value

#用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。
setrange key offset value

#返回 key 所储存的字符串值的长度。
strlen key

#同时设置一个或多个 key-value 对。
mset key value [key value ...]

#同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
msetnx key value [key value ...]

#这个命令和 setex 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 setex 命令那样，以秒为单位。
psetex key milliseconds value

#将 key 中储存的数字值增一。
incr key

#将 key 所储存的值加上给定的增量值（increment） 。
incrby key increment

#将 key 所储存的值加上给定的浮点增量值（increment） 。
incrbyfloat key increment

#将 key 中储存的数字值减一。
decr key

#key 所储存的值减去给定的减量值（decrement） 。
decrby key decrement

#如果 key 已经存在并且是一个字符串， append 命令将指定的 value 追加到该 key 原来值（value）的末尾。
append key value

```


### hash哈希
hash 是一个键值对集合。是一个string类型的field和value的映射表，hash特别适合用于存储对象。


### list列表
列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素导列表的头部（左边）或者尾部（右边）。

### set集合
的Set是string类型的无序集合。
集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。

### zset集合
zset 和 set 一样也是string类型元素的集合,且不允许重复的成员。不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。zset的成员是唯一的,但分数(score)却可以重复。