xml配置注入方式

### setter注入
set注入是最得单常用的注入方式，set必须小写,后面跟的第一个字母大小写不限，再后面则必须要和xml配置中的property name一致。

    public void setname(String name){}

    <bean id="order" class="test.Order" >
        <!-- 配置要注入字符串 -->
        <property name="name" value="xxxx"/>
        <!-- 配置要注入的对象 -->
        <property name="obj" ref="notify"/>
        <property name="customer">
            <bean class="twm.spring.start.Customer">
                <property name="name" value="陈先生"></property>
                <property name="address" value="深圳南山区"></property>
            </bean>
        </property>
        <!-- 复合属性 -->
        <property name="customer.name" value="陈先生" />
        <property name="customer.address" value="深圳南山区" />
    </bean>

### 构造注入
    /*构造函数注入*/
    public Order(String username, String orderno, NotifyService notifyservice1) {
        this.username = username;
        this.orderno = orderno;
        this.notifyservice1 = notifyservice1;
    }

    //指定参数名
    <bean id="order" class="twm.spring.start.Order" >
        <constructor-arg name="username" value="张老三"></constructor-arg>
        <constructor-arg name="notifyservice1" ref="notify"></constructor-arg>
        <constructor-arg name="orderno" value="1234567"></constructor-arg>
    </bean>

    //智能识别 不建议使用
    <bean id="order" class="twm.spring.start.Order" >
        <constructor-arg value="张老三"></constructor-arg>
        <constructor-arg ref="notify"></constructor-arg>
        <constructor-arg value="1234567"></constructor-arg>
    </bean>
    //Spring这时会先按类型排序，同类型的按先后顺序向构造函数参数赋值。所以如果完全按照构造函数的参数顺序写，肯定是没有问题的。上面这样写，也是没有问题的，两个String类型的参数顺序对了就行。

    //指明参数类型
    <bean id="order" class="twm.spring.start.Order" >
        <constructor-arg type="String" value="张老三"></constructor-arg>
        <constructor-arg type="twm.spring.start.NotifyService" ref="notify"></constructor-arg>
        <constructor-arg type="String" value="1234567"></constructor-arg>
    </bean>

    //指定索引index
    <bean id="order" class="twm.spring.start.Order" >
        <constructor-arg index="0" value="张老三"></constructor-arg>
        <constructor-arg index="2" ref="notify"></constructor-arg>
        <constructor-arg index="1" value="2017877997"></constructor-arg>
    </bean>

#### 构造注入和setter注入比较
强制依赖用构造，可选依赖用setter，注意在setter方法上使用@Required注解即可令属性强制依赖

### 静态工厂注入
静态工厂是通过静态工厂的方法来获取自己需要的对象，为了让spring管理所有的对象，我们不能直接通过“工程类.静态方法“来获取对象，而是依然通过spring注入的形式获取。           

    public class NotifyFactory {
        /*静态工厂方法*/
        public static NotifyService getNotifyService(){
            return new NotifyServiceByWeixinImpl();
        }
    }

    <bean id="order" class="twm.spring.start.Order">
        <constructor-arg name="username" value="张老三"></constructor-arg>
        <constructor-arg name="notifyservice1" ref="notify2"></constructor-arg>
        <constructor-arg name="orderno" value="1234567"></constructor-arg>
    </bean>
    <bean id="notify2" class="twm.spring.start.NotifyFactory" factory-method="getNotifyService" />


### 实例工厂注入
实例工厂方法(非静态）和静态工厂方法本质相同（除了使用facory-bean属性替代class属性，其他都相同。     

    public class NotifyFactory {
        /*普通工厂方法*/
        public NotifyService getNotifyService(){
            return new NotifyServiceByWeixinImpl();
        }
    }
    <bean id="order" class="twm.spring.start.Order">
        <constructor-arg name="username" value="张老三"></constructor-arg>
        <constructor-arg name="notifyservice1" ref="notify2"></constructor-arg>
        <constructor-arg name="orderno" value="1234567"></constructor-arg>
    </bean>


    <bean id="notifyfactory" class="twm.spring.start.NotifyFactory" />
    <bean id="notify2" factory-bean="notifyfactory" factory-method="getNotifyService" />

### 给属性设置null值
    <bean class="ExampleBean">
        <property name="email">
            <null/>
        </property>
    </bean>


spring使用map、set、list、array属性

    <bean id="emp1" class="com.hsp.collection.Employee">  
        <property name="name" value="北京"/>  
        <property name="id" value="1"/>  
    </bean>  
    <bean id="emp2" class="com.hsp.collection.Employee">  
        <property name="name" value="天津"/>  
        <property name="id" value="2"/>  
    </bean> 

### array注入
    <property name='empName'>
        <list>
            <value>xx</value>
            <value>bbb</value>
        </list>
    </property>


### list注入

    <property name="empList">  
        <list>  
            <ref bean="emp2" />  
            <ref bean="emp1"/>  
            <ref bean="emp1"/>  
            <ref bean="emp1"/>  
            <ref bean="emp1"/>  
            <ref bean="emp1"/>  
            <ref bean="emp1"/>  
        </list>  
    </property> 

### set注入
    <property name="empsets">  
        <set>  
            <ref bean="emp1" />  
            <ref bean="emp2"/>  
            <ref bean="emp2"/>  
            <ref bean="emp2"/>  
            <ref bean="emp2"/>  
        </set>  
    </property>

### map注入
    <property name="empMaps">  
        <map>
            <entry key="11" value-ref="emp1" />   
            <entry key="22" value-ref="emp2"/>  
            <entry key="22" value-ref="emp1"/>  
        </map>  
    </property> 


https://www.cnblogs.com/lxl57610/p/6813647.html