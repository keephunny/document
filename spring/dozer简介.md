### 为什么要进行数据转换
在一个分层的体系结构中，经常会使用DTO、PO、VO等封装数据，封装数据到特定的数据对象中，然而在很多情况下，某层内部的数据是不允许传递到其它层，不允许对外暴露的，特别是在分布式的系统中，内部服务的数据对外暴露，也不允许不相关的数据传入到本服务，所以需要对数据对象进行转换。

### 为什么要使用Dozer？
前期对于很多程序员来说，数据转换都是通过手工编写转换工具类或工具方法来实现的，这样不仅没有针对性而且工作量很大，编写工具类重用性差，而且不灵活。所以，急需要使用一个通用的映射工具，通过配置或少量的编码就可以轻松的实现数据对象之间的转换，Dozer就是这样的映射工具，它具有通用性，灵活性，可重用性和可配置等特点，并且是开源的。


```
    <dependency>
        <groupId>net.sf.dozer</groupId>
        <artifactId>dozer</artifactId>
        <version>5.5.1</version>
    </dependency>
```

### Dozer支持的转换类型如下：
* Primitive to Primitive Wrapper
* Primitive to Custom Wrapper
* Primitive Wrapper to Primitive Wrapper
* Primitive to Primitive
* Complex Type to Complex Type
* String to Primitive
* String to Primitive Wrapper
* String to Complex Type if the Complex Type contains a String constructor
* String to Map
* Collection to Collection
* Collection to Array
* Map to Complex Type
* Map to Custom Map Type
* Enum to Enum
* Each of these can be mapped to one another: java.util.Date, java.sql.Date, java.sql.Time, * java.sql.Timestamp, java.util.Calendar, java.util.GregorianCalendar
* String to any of the supported Date/Calendar Objects.
* Objects containing a toString() method that produces a long representing time in (ms) to any supported Date/Calendar object.

Primitive表示基本类型，Wrapper表示包装类型，Complex Type表示复杂类型。