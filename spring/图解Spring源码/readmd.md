#### P2 Bean的定义信息反射对象

BeanDefinitionReader接口由xml、注解实现bean的读取

```
1.获取class对象
Class clazz=Class.forName("xx.xxx.xx.Axxx");
Class clazz=对象.getClass();
Class clazz=类名.class;

2.获取构造器
Constructor ctor=clazz.getDeclaredConstructor();
3.创建对象
Object obj=ctor.newInstance();
```

##### P3 P4

PostProcessor：后置处理增强器，提供某些额外的扩展功能

BeanFactory：是一个接口，访问spring Bean 容器的根接口。

ApplicationContext：是一个接口，继承BeanFactory，提供更加丰富的接口功能

PlaceholderConfigurerSupport：抽象的基础类，占位符串替换。



ConfigurationClassParser.java

```
protected final SourceClass doProcessConfigurationClass(){
	判断类的注解
	@Component
	@PropertySource
	@ComponentScan
	@Import
	@ImportResource
	@Bean
}
```

Bean的生命周期

* 实例化：在堆内存空间申请空间，对象的属性值一般是默认值，反射创建对象的过程。createBeanInstance

* 初始化：属性赋值(populateBean)set方法赋值

  * 自定义属性赋值
  * 容器对象属性赋值 实现接口Aware

* 扩展方法

  BeanPostProcessor->AOP->动代代理

  * 执行前置处理方法
  * 执行后置处理方法

* 使用Bean对象

* 销毁Bean对象



spring bean对象按使用者分为：

* 自定义对象
* 容器对象，BeanFactory、ApplicationContext、Environment

##### P5 更高效的学习源码



##### P6 BeanFacotry和FacotryBean的区别

BeanFactory：并不是指根接口，而是bean对象创建的整体流程，bean的生命周期是一个完整的标准化流程。

FactoryBean：用来创建Bean对象

* isSingleton：判断是否是单例对象
* getObjectType：获取返回对象的类型 
* getObject：创建对象



##### P10 spring三级缓存

一级缓存

Map<String,Object> singletonObjects=new ConcurrenthashMap<>(256)

成品对象



二级缓存

Map<String,ObjectFactory<?>> singletonFactories=new HashMap<>(16);

半成品对象



三级缓存

Map<String,Object> earlySingletonObjects=new ConcurrentHashMap<>(16)

lambda表达式



OjbectFactory<T>：函数式接口，可以将lambda表达式作为参数放到方法的参数中。只有在调用getObject方法时才会去调用lambda表达式