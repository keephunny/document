
1. 数值类型
* smallint：2字节，小范围整数，-32768到+32767
* integer：4字节，常用的整数，-2147483648到+2147483647
* bigint：8字节，大范围整数，-9223372036854775808 到 +9223372036854775807
* decimail：可变长，指定精度
* numeric：可变长，指定精度
* real：4字节，
* double precision：8字节
* smallserial：2字节，自增整数小范围，1到32767
* serial：4字节，自增整数，1到2147483647
* bigserial：8字节，自增整数大范围，1到9223372036854775807

1. 货币类型
money 类型存储带有固定小数精度的货币金额。
numeric、int 和 bigint 类型的值可以转换为 money，不建议使用浮点数来处理处理货币类型，因为存在舍入错误的可能性。
* money：8字节，货币金额


1. 字符类型
* varchar(n)，变长，最大1GB
* char(n)：定长，不足补空白，最大1GB
* text：实际长度，无长度限制

1. 日期/时间类型
* timestamp：8字节，日期和时间(无时区)
* timestamp：8字节，日期和时间(有时区)
* date：4字节，天
* time：8字节，无时区
* time with time zone：12字节，有时区
* interval：12字节，时间间隔


1. 布尔类型
* boolean：1字节

1. 枚举类型
* ENUM：1字节

1. 几何类型
* point：16字节，平面中的点，(x,y)
* line：32字节，无穷直线,((x1,y1),(x2,y2))
* lseg：32字节，有限线段，((x1,y1),(x2,y2))
* box：32字节，矩形,((x1,y1),(x2,y2))
* path：16+16n字节，闭合路径，((x1,y1),…)
* path：16+16n字节，开放路径，((x1,y1),…)
* polygon：40+16n字节，多边形，((x1,y1),…)
* circle：24字节，圆，<(x,y),r>

1. 网络地址类型
* cidr：7或19字节，IPV4、IPV6
* inet：7或19字节，IPV4、IPV6
* macaddr：6字节，MAC地址

1. 位串类型
* bit：

1. 文本搜索类型
* tsvector：无重复值的iexemes排列表
* tsquery：存储用于检索的词汇

1. UUID 类型
用于存府RFC4122，ISO/IEF 9834-8:2005及相关标准定义
1. XML 类型


1. JSON类型
JSON数据可以存储为text，但json数据类型更有利于检查每个存储的JSON值