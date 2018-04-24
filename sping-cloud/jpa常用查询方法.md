
## Entity注解
    @Entity：通过@Entity注解将一个类声明为一个实体bean。
        @Table：注解可以为实体bean映射 指定的表，name属性表示实体所对应的表名称，如果没有定义默认实体的类名。
        @Id：用于标记属性的主键
        @GeneratedValue：generator属性可以根据此标识来声明主键生成器。
        @Column：表示持久化属性所映射表的字段，如果属性名与字段名相关同则可省略，可以放在属性或getter方法前面。
            name：字段名
            unique：是否唯一
            nullable：是否可以为空
            inserttable：是否可以插入
            updateable：是否可以更新
            columnDefinition: 定义建表时创建此列的DDL
        @secondaryTable: 从表名。如果此列不建在主表上（默认建在主表），该属性定义该列所在从表的名字。
        @Temporal：如果属性是时间类型，因为数据表对时间类型有更严格的划分，所以必须指定具体时间类型
            TemporalType.DATE 日期
            TemporalType.TIME 时间
            TemporalType.TIMESTAMP 日期时间
        @TableGenerator：表生成器，将当前主键的值单独保存到一个数据库表中，主键的值每次都是从指定的表中查询获得。
            name：表示该表主键生成策略的名称
            table：表标表生成策略所持久化的表格，
            pkColumnName：表生成器中的列名，用来存放其它表的主键键名
            pkColumnValue：实体表所对应到生成器表中的主键名
            valueColumnName：表生成器中的列名
        @ManyToOne 可以建立实体bean之间的多对一的关联
        @Transactional    可以建立实体bean之间的多对多的关联
        @Transient --将忽略这些字段和属性,不用持久化到数据库
        @Formula --一个SQL表达式，这种属性是只读的,不在数据库生成属性(可以使用sum、average、max等)
## DAO接口实现
### JpaRepository主要方法，接口都已经声名，直接继承使用
该接口使用了泛型，需要为其提供两个类型：第一个为该接口处理的域对象类型，第二个为该域对象的主键类型。 如下

    //Spring Data JPA 风格的持久层接口：
    public interface UserDao extends Repository<AccountInfo, Long> {
        //需要UserDao的实现类，框架会为我们完成业务逻辑。
        public AccountInfo save(AccountInfo accountInfo);
    }
    //泛型参数中T代表实体类型，Long是实体中id的类型
    List<T> findAll();
    List<T> findAll(Sort sort);
    List<T> findAll(Iterable<ID> ids);
    <S extends T> List<S> save(Iterable<S> entities);
    <S extends T> S saveAndFlush(S entity);
    void deleteInBatch(Iterable<T> entities);
    void deleteAllInBatch();
    T getOne(ID id);

查询全部方法
    userDao.findAll();
添加对象
    userDao.save(prpduser)；
修改对象 如果key已存在则修改
    userDao.save(prpduser);
批量保存
    userDao.save(List<prpduser>);
### 自定义查询方法
#### @Query()查询注解
value：查询语句，需要严格区分JPQL和原生sql。如果是 JPQL区分大小写。
nativeQuery：是否是原生sql，默认不是。
#### @Modify()修改注解
修改、添加、删除需要使用modify注解
#### @Transactional事务注解
修改、添加、删除需要使用ransactional事务注解
#### @Param("") 
参数注入在方法入参使用
1.  按名称入参
    @Query("select p from PrpDcustomer p where customerCName like :customerName%)”)
    public List<PrpDcustomer> queryJpql(@Param(“customerName”) String customerName);
2. 按位入参
    @Query(value = "update UserInfo  set isEnabled = abs(1-isEnabled) where id=?1")
    public int updateEnabled(int id);//按位参数注入
3. 按对象入参
    @Query(value = "select p from PrpDcustomer p where p.customerCode=:#{#pc.customerCode} and p.customerCName=:#{#pc.customerCName}”)
    public Page<PrpDcustomer> queryJpql(@Param("pc") PrpDcustomer pc, Pageable pageable);
4. 集合入参
    @Query("select p from PrpDcustomer p where customerCName in :customerName”)
    public List<PrpDcustomer> queryJpql(@Param(“customerName”) String[] customerName);
集合入参支持数组Array和集合List
### 分页查询
Pageable对象定义  
Pageable pageable=new PageRequest(0,3,null);
示例：
    //Pageable参数只能放在最后一个
    @Query(value = "select p from PrpDcustomer p ")
    public List<PrpDcustomer> queryJpql(Pageable pageable);

### 聚合查询、自定义列查询
    @Query(value = "select new com.spring.boot.entity.CountEntity(count(p),max(p.customerCode)) from PrpDcustomer p group by p.customerType")
    public List<CountEntity> queryJpql();
    //返回的对象是自定的对象 ，因为聚合后的值都是原对象不存在的。
    Sql语句里的字段顺序要与对象构造函数一致，且count只能用long类型。
    public class CountEntity implements Serializable {
        private  Long m;
        private  String  n;
        public CountEntity(){}
        public CountEntity(Long m, String n) {
            this.m = m;
            this.n = n;
        }
        //省略 get set 
    }
### 其它常用举例
    //JPQL查询
    @Query(value="select u from PrpdUser p where p.userCode=:userCode")
    public List<PrpdUser> queryByUserCode(@Param("userCode") String userCode);
    //原生sql查询
    @Query(value="select * from prpduser where prpduser.usercode=:usercode,,nativeQuery = true)
    public List<PrpdUser> queryByUserCode(@Param("userCode") String userCode);

    //修改字段 修改操作一定要加事务
    @Transactional
    @Modifying(clearAutomatically = true)//自动清除实体里保存的数据。
    @Query(value = "update PrpDuser  set userName =:userName where userCode=:userCode")
    int updateUserNameByCode(@Param("userName") String userName,@Param("userCode") String userCode);

    //模糊查询
    @Query(value="select u from PrpDcustomer u where u.customerCName like %:customerCName%")
    List<PrpDcustomer> findCustomerByName(@Param("customerCName") String customerCName);
    //删除操作

    //按主键查询指定对象

    //统计人数 统计类返回值long,尽量写大类型
    @Query(value = "select count(u) from PrpDuser u")
    Long findCountUser();
## EntityManage
用@PersistenceContext动态注入Entitymanager
    //创建EntityManage
    @PersistenceContext
    private EntityManager entityManager;

jpql查询语句，非原生sql   jpql里全部是对象和属性，不是数据库里的表和字段，所以注意命名和大小写
    String dataSql="select p from PrpDcustomer p where p.customerCName like :customerCName";
    Query dataQuery=entityManager.createQuery(dataSql);
    dataQuery.setParameter("customerCName","张%”);
    //分页设置
    dataQuery.setFirstResult(0);
    dataQuery.setMaxResults(10);
    //获取查询结果
    List<PrpDcustomer> list=dataQuery.getResultList();   
查询行号+对象
    String dataSql="select RowNum as LineNum ,p from PrpDcustomer p";
    Query dataQuery=entityManager.createQuery(dataSql);
    //dataQuery.setMaxResults(10);
    List<Object[]> list=dataQuery.getResultList();
    for (Object[] obj:list){
        System.out.println(obj[0]);
        PrpDcustomer pc=(PrpDcustomer) obj[1];
        System.out.println(pc.getCustomerCName());
    }
原生sql查询对象
    String dataSql = "select userCode,userName,userName,createdBy,createdTime from PrpDuser";
    Query dataQuery = entityManager.createNativeQuery(dataSql,PrpDuser.class);
    List<PrpDuser>  list = dataQuery.getResultList();
    //PrpDuser 需要加entity、id注解 
集合参数
    List<String> stringList = new ArrayList<String>();
    stringList.add("001");
    stringList.add("002");
    stringList.add("003");
    stringList.add("004");
    String dataSql = "select p from PrpDuser p where p.userCode in (:list)";
    Query dataQuery = entityManager.createQuery(dataSql);
    dataQuery.setParameter("list", stringList);
    List<PrpDuser> list = dataQuery.getResultList();
// 执行更新语句
Query query = em.createQuery("update Person as p set p.name =?1 where p. personid=?2");
query.setParameter(1, “黎明”);
query.setParameter(2, new Integer(1) );
int result = query.executeUpdate(); //影响的记录数

插入 方法需要加事务
    PrpDuser prpDuser=prpDuserDao.findOne("001");
    prpDuser.setUserCode("017");
    prpDuser.setUserName("张三017");
    entityManager.persist(prpDuser);
修改 方法需要加事务
    PrpDuser prpDuser=new PrpDuser();
    prpDuser.setUserCode("017");
    prpDuser.setUserName("张三");
    entityManager. merge(prpDuser);

## 事务管理Transactional
@Transactional事务：默认只对 RuntimeException 回滚，而非 Exception 进行回滚如果要对 checked Exceptions 进行回滚，则需要 @Transactional(rollbackFor = Exception.class)  
@Transactional 只能被应用到public方法上, 对于其它非public的方法,如果标记了@Transactional也不会报错,但方法没有事务功能.

    @Transactional
    public void testSysConfig(SysConfigEntity entity) throws Exception {
        //不会回滚
        this.saveSysConfig(entity);
        throw new Exception("sysconfig error");
    }
    @Transactional(rollbackFor = Exception.class)
    public void testSysConfig1(SysConfigEntity entity) throws Exception {
        //会回滚
        this.saveSysConfig(entity);
        throw new Exception("sysconfig error");
    }
    @Transactional
    public void testSysConfig2(SysConfigEntity entity) throws Exception {
        //会回滚
        this.saveSysConfig(entity);
        throw new RuntimeException("sysconfig error");
    }
    @Transactional
    public void testSysConfig3(SysConfigEntity entity) throws Exception {
        //事务仍然会被提交
        this.testSysConfig4(entity);
        throw new Exception("sysconfig error");
    }
@Transactional(propagation=Propagation.REQUIRED) 注解用作定义一个事务的传播特性。
    org.springframework.transaction.annotation.Propagation
    public enum Propagation {  
        REQUIRED(0),
        SUPPORTS(1),
        MANDATORY(2),
        REQUIRES_NEW(3),
        NOT_SUPPORTED(4),
        NEVER(5),
        NESTED(6);
    }
    REQUIRED ：默认参数。如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
    SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
    MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
    REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
    NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
    NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。
    NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED 。
## jpa支持的函数
        方法     说明     类型     支持   测试结果HQL 使用方法  
        ABS(n)     取绝对值    数学函数    JPA QL HQL √   ABS(column_name[数字类型对象属性])
        SQRT(n)     取平方根    数学函数    JPA QL HQL √   SQRT(column_name[数字类型对象属性])
        MOD(x,y)    取余数     数学函数    JPA QL HQL √   MOD([对象属性(数字)或值],[对象属性（数字）或值]数字必须是整型。返回参数1/参数2得的余数。
        SIZE(c)     方法集合内对象数量   集合函数    JPA QL HQL
        MINELEMENT(c)    返回集合中最小元素   集合函数    HQL
        MAXELEMENT(c)    返回集合中最大元素   集合函数    HQL
        MININDEX(c)    返回索引集合最小索引   集合函数    HQL
        MAXINDEX(c)    返回索引集合最大索引   集合函数    HQL
        CONCAT(s1,s2)    连接连个字符串    字符串函数    JPA QL HQL √   CONCAT([对象属性],[对象属性]) 相当与“||”SUBSTRING(s,offset,length) 返回部分字符串    字符串函数    JPA QL HQL √   SUBSTRING([要截取的字符串属性字段]，开始位置，截取长度)
        TRIM([[ BOTH | LEADING   去掉字符串中的某个给定的字符.
        | TRAILING]] char FROM s) 默认去掉字符串两面的空格. 字符串函数    JPA QL HQL √   默认用法，TRIM([字符串对象属性列]) 将字段两端的空格去掉。
        LOWER(s)    小写     字符串函数    JPA QL HQL √   LOWER([字符串对象属性列]) 将该列结果含有的字母全部大写
        UPPER(s)    大写     字符串函数    JPA QL HQL √   UPPER([字符串对象属性列]) 将该列结果含有的字母全部大写  
        LENGTH(s)    返回字符串长度    字符串函数    JPA QL HQL √   LENGTH(字段名) 返回字段内容的长度，包括数字。null值返回null.
        CURRENT_DATE()    返回数据库当前日期   时间函数    JPA QL HQL √   CURRENT_DATE() 返回数据库当前日期
        CURRENT_TIME()    时间     时间函数      √   CURRENT_TIME() 返回数据库当前时间
        CURRENT_    时间戳
        TIMESTAMP()
        SECOND(d)    从日期中提取具体参数分别为: 时间函数    HQL   √   SECOND(时间字段) 空的时候返回null
        MINUTE(d)     秒,分,小时,天,月,年        √   同上
        HOUR(d)               √   同上
        DAY(d)               √   同上
        MONTH(d)              √   同上
        YEAR(d)               √   同上
        CAST(t as type)    强制类型转换    转换函数    HQL   √   CAST([字段或值] as [要转换的类型-int,string...])
        max()
        min()
        count()
            
