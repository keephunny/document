在实际应用中不建议每次都创建一个新Mapper的实例，一个系统只需要有一个DozerBeanMapper实例。如果结合Spring的DI，只需要添加如下配置即可，

mobileNo转换后，前三和后四都使用了*进行了脱敏，

```
    <mapping>
        <class-a>com.xx.xx.a</class-a>
        <class-b>com.xxx.xxx.b</class-b>
        <field>
            <a>age</a>
            <b>ageTwo</b>
        </field>
    </mapping>
```

```
<mapping>
        <class-a>com.aui.stock.entity.CartEntity</class-a>
        <class-b>com.aui.stock.controller.mini.vo.CartVO</class-b>
        <field>
		两个字段
            <a>items</a>
            <b>items</b>
	    //两个字段对应的实体类
            <a-hint>com.aui.stock.entity.GoodsEntity</a-hint>
            <b-hint>com.aui.stock.controller.mini.vo.GoodsVO</b-hint>
        </field>
    </mapping>
    //重新创建了一个mapping 用来创建两个字段对应的实体类
    <mapping>
            <class-a>com.aui.stock.entity.GoodsEntity</class-a>
            <class-b>com.aui.stock.controller.mini.vo.GoodsVO</class-b>
           <!-- <field>
                <a>product</a>
                <b>productVO</b>
            </field>-->
	    //这里是 将product中的sn属性赋值给GoodsVO的sn属性
        <field>
            <a>product.sn</a>
            <b>sn</b>
        </field>
 
        <field>
            <a>product.price_retail</a>
            <b>price</b>
        </field>
        <field>
            <a>product.title</a>
            <b>title</b>
        </field>
        <field>
            <a>product.smallImage</a>
            <b>smallImage</b>
        </field>
      <!--  <field>
            <a>product</a>
            <b>productVo</b>
            <a-hint>com.aui.stock.entity.ProductEntity</a-hint>
            <b-hint>com.aui.stock.controller.mini.vo.ProductVO</b-hint>
        </field>-->
    </mapping>
</mappings>
```

日期转换
```
<mappings>
  <configuration>
    <date-format>yyyy-MM-dd HH:mm:ss</date-format>
  </configuration>

  <mapping wildcard="true">
    <class-a>xxxxx.TestObjectA</class-a>
    <class-b>xxxxx.TestObjectB</class-b>
    <field>
      <a>dateString</a>
      <b>dateObject</b>
    </field>
  </mapping>
</mappings>
```