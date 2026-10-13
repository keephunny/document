### postGIS

PostGIS是PostgreSQL的空间拓展组件，它可以在数据库中存储GIS(Geographic Information Systems)的对象。PostGIS提供空间对象、空间索引、空间操作函数和空间操作符等功能，将PostgreSQL数据库扩展成了空间数据库。
PostGIS支持的GIS对象是开放地理空间协会(OGC, Open Geospatial Consortium)定义的“简单几何要素”的一个超集。PostGIS支持OGC“SQL的简单几何要素”规范中指定的所有对象和函数。在OGC规范的基础上，PostGIS还扩展了标准，支持3DZ、3DM和4D坐标。
PostGIS通过向PostgreSQL添加对空间数据类型、空间索引和空间函数的支持，将PostgreSQL数据库管理系统转换为空间数据库。

### 版本对应

| PostgreSQL Version | PostGIS Version |
|  :---:  | :---  |
| 11(EOL)	 | 2.5(EOL)、3.0、3.1、3.2、3.3 |
| 12 | 2.5(EOL)、3.0、3.1、3.2、3.3、3.4、3.5 |
| 13 | 3.0、3.1、3.2、3.3、3.4、3.5 |
| 14 | 3.1、3.2、3.3、3.4、3.5 |
| 15 | 3.2、3.3、3.4、3.5 |
| 16 | 3.3、3.4、3.5 |
| 17 | 3.3、3.4、3.5 |


### 下载地址

https://postgis.net/stuff/postgis-3.6.4dev.tar.gz














https://developer.aliyun.com/article/832610


```
-- 创建使用IDENTITY的表
CREATE TABLE products (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);


-- 创建自定义序列
CREATE SEQUENCE customer_id_seq START WITH 1000 INCREMENT BY 1;

-- 创建关联序列的表
CREATE TABLE customers (
    id INT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20)
);

-- 插入数据
INSERT INTO customers (name, phone) VALUES ('Alice', '123-4567');
INSERT INTO customers (name, phone) VALUES ('Bob', '987-6543');
```