
### Junit注解
* @Test 通过对测试类和测试方法的命名来确定是否测试。测试方法必须是public void，可以抛出异常。
* @Ignore 可以暂时忽略不运行的方法。
* @BeforeClass 当我们运行几个关联的用例时，可能会在数据准备或其它前期准备中执行一些相同的命令，这时为了让代码更清晰，可以将公用部分提取出来，并为这个方法注解@BeforeClass。意思是在测试类里所有用例之前运一次这个方法。必须是public static void。
* @AfterClass 在测试类所有方法运行之后执行一次。用于处理一些测试后续工作。同样必须是public static void 。
* @Before 与@BeforeClass区别在于@Before不止运行一次，它在每个用例运行之前都会执行一次。
* @After 与@Before类似
* @Runwith 放在测试类名之前，用来确定这个类怎么运行的，可以不标注，使用默认运行器。
    1. @RunWith(Parameterzed.class)参数化运行器，配合@Parameters使用junit参数化功能。
    2. @RunWith(Suite.class)测试集运行器配合使用测试集功能
    3. @RunWith(JUnit4.class)junit4默认运行器
    4. @RunWith(SpringJUnit4ClassRunner.class)集成了spring的一些功能。
* @Parameters 用于使用参数化功能。

### 注解执行过程
* beforeClass(): 方法首先执行，并且只执行一次。
* afterClass():方法最后执行，并且只执行一次。
* before():方法针对每一个测试用例执行，但是是在执行测试用例之前。
* after():方法针对每一个测试用例执行，但是是在执行测试用例之后。
* 在 before() 方法和 after() 方法之间，执行每一个测试用例。



### Assert断言
1. assertEquals()：判断两个对象是否相等
2. assertTrue：判断结果是否为ture
3. assertFalse：判断结果是否为false
4. assertNull：判断结果是否Null
5. assertNotNull
6. assertSame
7. assertNotSame
8. assertArrayEquals：判断两个数组是否相等。
9 assertThat：判断实际值是否满足指定条件。



### Junit异常测试
junit提供了一个追踪异常的选项，可以测试代码是否抛出了指定的异常
@Test(excepted=ArithmeticException.class)

### Junit时间测试
如果一个测试用例超过了指定的时间(ms)，那么Junit将自动将标记为失败。
@Test(timeout=2000)


### Junit参数化测试