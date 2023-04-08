AOP(Aspect Oriented Programming)，即面向切面编程，可以说是OOP的补充和完善。OOP引入了抽象、封装、继承、多态来建立一种对象层次结构。用于模拟公共行为的一个集合。不过OOP允许开发者定义纵向的关系，但并不适合定义横向的关系。例如日志功能，日志代码往往横向地散布在所有对象层次中，而与它对应的核心功能毫无关系对于其它类型的代码，如安全性、异常处和透明的持续性也是如此，这种散布在各处无关的代码被称为横切。在OOP设计中，它导致大量代码的重复，而不利于各个模块的重用。   
AOP技术恰恰相反，它利用一种称为横切的技术，剖解开封装的对象内部，并将那些影响了多个类的公共行为封装到一个可重用模块，并将其命免为Aspect，切面简单的说就是那些业务无关，却为业务模块所共同调用的逻辑或责任封装起来，便于减少系统的重复代码。降低模块之间的耦合度。并有利于未来的可操作性和可维护性。  
使用模切技术，AOP把软件系统分为两个部分，核心关注点和横切关注点，业务处理的主要流程是核心关注点，与之关系不大的部份是横切关注点。横切关注点是一个特，他们经常发生在核心关注点的多处。而各处基本相似，比如权限认证、日志、事务。AOP作用在于分离系统中各种关注点，将核心关注点和横切关注点分离开来。  

面向过程编程、面向对象编程、函数式编程、事件驱动编程、面向切面编程

## AOP核心概念
1. 横切关注点：对哪些方法进行拦截，拦截后怎么处理，这些关注点称为横切关注点。
2. 切面：类是对物体特征的抽象，切面是对横切关注点的抽象。
3. 连接点：被拉截到的点，因为spring只支持方法类型的连接点，所以在spring连接点指的就是被拉截的方法，实际上连接点还可以是字段或构造方法。
4. 切入点：对连接点进行拦截定义。
5. 通知(advice)：所谓通知就是指拦截到连接点之后要执行的代码，通知分为前置、后置、异常、最终、环绕。
6. 目标对象：代理的目标对象。
7. 织入(weave)：将切面应用到目标对象并导致代理对象创建的过程。
8. 引入(introduction)：在不修改代码的前提下，引入可以在运行期为类动态地添加一些方法或字段。

## spring对AOP的支持
spring中AOP代理由spring的IOC容器负责生成、管理、其依赖关系系也由IOC容器负责管理，因此AOP代理可以直接使用容器中的其它bean实例作为目标，这种关系可由IOC容器依赖注入提供，spring创建代理的规则为：
1. 默认使用java动态代理来创建AOP代理，这样可以为作何接口实例创建代理。
2. 当需要代理的类不是代理接口的时候，spring会切换为使用CGLIB代理，也可强制使用CGLIB。
AOP编程的关键就是定义切入点和定义增强处理，一旦定义了合适的切入点和增强处理，AOP框架就自动生成AOP代理，即代理对象的方法=增强处理+被代理对象。
下面给出一个Spring AOP的.xml文件模板，名字叫做aop.xml，之后的内容都在aop.xml上进行扩展：    
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
            http://www.springframework.org/schema/aop
            http://www.springframework.org/schema/aop/spring-aop-4.2.xsd">
                
    </beans>

## 基于spring的aop简单实现
使用springAOP要成功动行代码，只用spring包是不够，还需要额外两个包，aopalliance.jar、aspectjweaver.jar。  
    //定义一个接口
    public interface HelloWorld{
        void pringHelloWorld();
        void doPrint();
    }

    //定义接口实现类
    public class HelloWorldImpl implements HelloWorld{
        public void printHelloWorld(){
            System.out.println("world");
        }
        public void doPrint(){
            System.out.println("print");
        }
    }
    //定义另一个接口实现类
    public class HelloWorldImpl implements HelloWorld{
        public void printHelloWorld(){
            System.out.println("world-2");
        }
        public void doPrint(){
            System.out.println("print-2");
        }
    }
    //横切关注点。这里是打印时间
    public class TimeHandler{
        public void printTime(){
            System.out.println("currentTime："+System.currentTimeMills());
        }
    }
    //aop.xml配置
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:aop="http://www.springframework.org/schema/aop"
        xmlns:tx="http://www.springframework.org/schema/tx"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
            http://www.springframework.org/schema/aop
            http://www.springframework.org/schema/aop/spring-aop-4.2.xsd">

        <bean id="helloWorldImpl1" class="xxx.xxx.HelloWorldImpl"/>
        <bean id="helloworldImpl2" class="xxx.xxx.HelloworldImpl2"/>
        <bean id="timeHandler" class="xxx.xxx.TimeHandler"/>
        <aop:config>
            <aop:aspect id="time" ref="timeHandler">
                <aop:pointcut id="addAllMethod" express="execution(* xxx.xxx.HelloWorld.*(..))"/>
                <aop:before method="printTime" pointcut-ref="addAllMethod"/>
                <aop:after method="printTime" pointcut-ref="addAllMethod"/>
            </aop:aspect>
        </apo:config>
    </beans>

    //测试验证方法
    ApplicationContext ctx = new ClassPathXmlApplicationContext("aop.xml");
        
    HelloWorld hw1 = (HelloWorld)ctx.getBean("helloWorldImpl1");
    HelloWorld hw2 = (HelloWorld)ctx.getBean("helloWorldImpl2");
    hw1.printHelloWorld();
    System.out.println();
    hw1.doPrint();
    
    System.out.println();
    hw2.printHelloWorld();
    System.out.println();
    hw2.doPrint();



### AOP注解
#### @Pointcut
@Pointcut 注解，用来定义一个切点，即上文中所关注的某件事情的入口，切入点定义了事件触发时机。
一个是使用 execution()，另一个是使用 annotation() 

execution表达式：
    以 execution(* com.mutest.controller...(..))) 表达式为例：
    第一个 * 号的位置：表示返回值类型，* 表示所有类型。
    包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，在本例中指 com.mutest.controller包、子包下所有类的方法。
    第二个 * 号的位置：表示类名，* 表示所有类。
    *(..)：这个星号表示方法名，* 表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
```
    @Pointcut("execution(* com.mutest.controller..*.*(..))")
    public void pointCut() {}
```

annotation() 表达式：
    annotation() 方式是针对某个注解来定义切点，比如我们对具有 @PostMapping 注解的方法做切面，可以如下定义切面：
```
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void annotationPointcut() {}
```

#### @Around
Around注解用于修饰Around增强处理，Around增强处理非常强大，表现在：
@Around可以自由选择增强动作与目标方法的执行顺序，也就是说可以在增强动作前后，甚至过程中执行目标方法。这个特性的实现在于，调用ProceedingJoinPoint参数的procedd()方法才会执行目标方法。
@Around可以改变执行目标方法的参数值，也可以改变执行目标方法之后的返回值。
@Around功能虽然强大，但通常需要在线程安全的环境下使用。因此，如果使用普通的Before、AfterReturning就能解决的问题，就没有必要使用Around了。如果需要目标方法执行之前和之后共享某种状态数据，则应该考虑使用Around。尤其是需要使用增强处理阻止目标的执行，或需要改变目标方法的返回值时，则只能使用Around增强处理了。 
```
@Around("permissionCheck()")
    public Object permissionCheck(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("===================开始增强处理===================");

    //获取请求参数，详见接口类
    Object[] objects = joinPoint.getArgs();
    Long id = ((JSONObject) objects[0]).getLong("id");
    String name = ((JSONObject) objects[0]).getString("name");
    System.out.println("id1->>>>>>>>>>>>>>>>>>>>>>" + id);
    System.out.println("name1->>>>>>>>>>>>>>>>>>>>>>" + name);

    // 修改入参
    JSONObject object = new JSONObject();
    object.put("id", 8);
    object.put("name", "lisi");
    objects[0] = object;

    // 将修改后的参数传入
    return joinPoint.proceed(objects);
}
```


#### @Before
@Before 注解指定的方法在切面切入目标方法之前执行，可以做一些 Log 处理，也可以做一些信息的统计，比如获取用户的请求 URL 以及用户的 IP 地址等等，这个在做个人站点的时候都能用得到，都是常用的方法。例如下面代码：

#### @After
@After 注解和 @Before 注解相对应，指定的方法在切面切入目标方法之后执行，也可以做一些完成某方法之后的 Log 处理。

#### @AfterReturning
@AfterReturning 注解和 @After 有些类似，区别在于 @AfterReturning 注解可以用来捕获切入方法执行完之后的返回值，对返回值进行业务逻辑上的增强处理，例如：
```
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
 
        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getName();
        log.info("方法{}执行完毕，返回参数为：{}", classMethod, result);
        // 实际项目中可以根据业务做具体的返回值增强
        log.info("对返回参数进行业务上的增强：{}", result + "增强版");
    }
```

#### @AfterThrowing
当被切方法执行过程中抛出异常时，会进入@AfterThrowing 注解的方法中执行，在该方法中可以做一些异常的处理逻辑。要注意的是 throwing 属性的值必须要和参数一致，否则会报错。该方法中的第二个入参即为抛出的异常。
```
    @AfterThrowing(pointcut = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
    }
```

```
切点函数execution()的使用
      @Around("execution(* *(..))")  : execution()是一个切点函数,* * (..)是该函数的参数，其格式为:
	    <访问权限>? 返回值类型  包名+类名+方法名(参数类型) <throws 异常类型声明>
	@Around("execution(* * (..))") //all
	@Around("execution(public * * (..))")   //绑定方法的访问权限
	@Around("execution(public * * (..) throws RuntimeException)")   //绑定异常类型声明
   	@Around("execution(public * * (..) throws RuntimeException+)")   //+代表当前类及其子类
	@Around("execution(int * (..))")   //绑定方法的返回值
	@Around("execution(Object+ * (..))")   //绑定方法的返回值
	@Around("execution(void save* (..))")   //绑定方法名,以save开头的方法
	@Around("execution(void *m* (..))")   //包含m的方法
	@Around("execution(void com.dufy.spring.service.*.* (..))")   //绑定包名+类名+方法名
	@Around("execution(void com.dufy.spring..*Service*.update* (..))")   //包名以com.sxt.spring开头的类名中包含Service的类中所有以update开关的方法
	@Around("execution(void *())")   //绑定方法的参数
	@Around("execution(void *(String))")   //绑定方法的参数
	@Around("execution(void *(..,String,..))")   //只要有一个String类型的参数即可
	@Around("args(int,String)")   
	@Around("execution(* save*(..)) || execution(* update*(..))")    //切点运算   (||,or,&&,and)
	@Around("execution(* save*(..)) or execution(* update*(..))")    //切点运算 
```

#### 计算方法执行时间
```
    @Aspect
    @Component
    public class LogAdvice {
        @Around("logAdvicePointcut()")
        public Object permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {

            long n1 = System.currentTimeMillis();
            Object obj = joinPoint.proceed();
            RespResult respResult = new RespResult(200, obj);

            long n2 = System.currentTimeMillis();
            System.out.println(((n2 - n1) / 1000f) + " (s)" + joinPoint.getSignature().getName());

            return respResult;
        }
    }
```