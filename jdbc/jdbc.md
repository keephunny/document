## 什么是JDBC
JDBC(java database Connectivity)java数据库连接，是一种用于执行sql语句的java api，可以为多种关系数据提供统一访问。它由一组用java语言编写的类和接口组成。JDBC是供了一种基准。据此可以构建更高效的工具和接口。使数据库开发人员能够编写数据库应用程序。
### 数据库驱动
我们安装好数据库之后，我们的应用程序也是不能直接使用数据的。必须要通过相应的数据库驱动程序，通过驱动程序去和数据库打交道。其实也就是数据库厂商的jdbc接口实现，即对connection等接口的实现类。

## 常用接口
### Driver接口
Driver接口由数据库三家提供，在编程中要连接数据库，必须先装载特定的数据库驱动。不同的数据库有不同的装载方法。
    Class.forName("com.mysql.jdbc.Driver);
    Class.forName("oracle.jdbc.driver.OracleDriver);
### Connection接口
Connection与特定的数据库连接会话，在连接上下文中执行sql语句并返回结果，DriverManager.getConnection(url,user,password)j方法建立在jdbc url中定义的数据库connection连接上。  
连接mysql： Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/database","user","password");  
连接oracle：Connection conn=DriverManager.getConnection("jdbc:oracle://thin@localhost:1521:database","user","password");  
连接sqlserver Connection conn=DriverManager.getConnection("jdbc:microsoft:sqlserver://localhost:port;DatabaseName=database","user","password");  
常用方法： 
* createStatement()：创建向数据库发送sql的statement对象。
* prepareStatement(sql)：创建发送预编译的prepareStatement对象。
* prepareCall(sql)：执行存储过程。
* setAutoCommit(boolean autoCommit)：设置事务是否自动提交。
* commit()：在链接上提交事务。
* rollback()：在链接上回滚事务。
### Statement接口
用于执行静态sql语句并返回它所生成的结果对象  
三种类型Statement类:  
* Statement：由createStatement创建，用于发送简单的sql语句，不带参数。
* PreparedStatement：继承自Statement接口，由preparedStatement创建，用于发送含有参数的sql语句，比Statement效率更高，可以防止sql注入。
* CallableStatement：继承自PreparedStatement接口，由方法prepareCall创建，用于调用存储过程。
常用Statement方法：   
* boolean execute(String sql) ：运行sql语句，返回是否有结果集。
* ResultSet executeQuery(String sql) ：运行select语句，返回resultSet结果集。
* int executeUpdate(String sql) ：运行insert、update、delete操作，返回影响行数。
* void addBatch( String sql )：把多条sql语句放到一个批处理中。
* int[] executeBatch() ：

        // 关闭自动执行 
        con.setAutoCommit(false); 
        Statement stmt = con.createStatement(); 
        stmt.addBatch("INSERT INTO employees VALUES (1000, 'Joe Jones')"); 
        stmt.addBatch("INSERT INTO departments VALUES (260, 'Shoe')"); 
        stmt.addBatch("INSERT INTO emp_dept VALUES (1000, 260)"); 
        // 提交一批要执行的更新命令 
        int[] updateCounts = stmt.executeBatch();
* void close() ：
* int getQueryTimeout()：检索驱动程序等待 Statement 对象执行的秒数。
### ResultSet接口
ResultSet提供检索不同类型字段的方法：  
* getString(int index)、getString(String columnName)
* getFloat(int index)、getFloat(String columnName)
* getDate(int index)、getDate(String columnName)
* getBoolean(int index)、getBoolean(String columnName)
* getObject(int index)、getObject(String columnName)
ResultSet对结果集进行滚动的方法：  
* next()：移动下一行
* previouse() ：移动到前一行
* absolute(int row)移动到指定的行
* beforeFirst()移动resultSet的最前面
* afterLast()：移动到resultSet的最后面

使用后依次关闭对象及连接ResultSet-Statement-Connection

## 使用JDBC的步骤
加载JDBC驱动、建立数据库连接Connection、创建执行sql的语句Statement、处理执行结果ResultSet、释放资源。   
1. 注册驱动  
    Class.forName("com.MySQL.jdbc.Driver");//不会对具体的驱动类产生依赖
    DriverManager.registerDriver(com.mysql.jdbc.Driver);
2. 建立连接  
    String url="jdbc:mysql://localhost:3306/db1?useUnicode=true&characterEncoding=utf8
    Connection conn=DriverManager.getConnection(url,user,password);
3. 创建执行sql语句的statement  
    Statement st=conn.createStatement();
    st.executeQuery(sql);
4. 处理执行结果  
    ResultSet rs=st.executeQuery();
    while(rs.next()){
        rs.getString("column");
    }
5. 释放资源  
   try{
       if(rs!=null){
           rs.close();
       }
   }catch(SQLException e){
       e.printStackTrace();
   }finally{
       try{
           if(st!=null){
               st.close();
           }
       }catch(SQLException e){
           e.printStackTrace();
       }finally{
           try{
               if(conn!=null){
                   conn.close();
               }
           }catch(SQLException e){
               e.printStackTrace();
           }
       }
   }

## 事务控制
事务四大特性ACID  
* atomicity原子性：表示事务内的所有操作是一个整体，要么全成功，要么全失败。
* consistency一致性：表示一个事务内有一个操作失败时，所有的更改过的数据都必须回滚到修改前的状态。
* isolation隔离性：事务查看数据时所处的状态，要么是另一并发事务修改前的态态，要么修改后的状态，不会查看中间状态的数据。
* durability持久性：持久性事务完成后，它对于系统的影响是永远的。
事务的事离级别  
* 读取未提交 read Uncommitted，一个事务可以读取另一个未提交事务的数据。
* 读取已提交 read committed，一个事务要等另一个事务提交后才能读取数据。
* 可重复读  repeatable read，在事务开启时，不允许修改操作
* 序列化 serializable，事务的串行化顺序执行。

1. 批处理Batch  
    conn.setAutoCommit(false);//设为手动提交
    Statement stmt=conn.createStatement();
    for(int i=0;i<2000;i++){
        stmt.addBatch("insert into tables values ('1','2');");
    }
    stmt.executeBatch();
    //事务提交
    conn.commit();

    //prepareStatement批处理
    PrepareStatement ps=conn.prepareStatement(Sql);
    String sql="insert into tables values(?,?)";
    for(int i=0;i<100;i++){
        ps.setString(1,"1");
        ps.setString(2,"2");
        //添加批处理
        ps.addBatch();
        if(i%10==0){
            ps.executeBatch();
            ps.clearBatch();
        }
    }
    ps.executeBatch();
通过采用PrepareStatement.addBatch()实现批处理，发送出去的是通过预编译后的sql语句，执行起来更有效率。不足之处，只能在相同的sql中使用，因此适合往同一表中批量插入数据。

## 时间处理  
java.util.Date
* java.sql.Date
* java.sql.time
* java.sql.TimeStamp

    public Date(long date) 
    @Deprecated
    public Date(int year, int month, int day) 

    @Deprecated
    public Time(int hour, int minute, int second)
    public Time(long time)


    public Timestamp(long time)
    @Deprecated
    public Timestamp(int year, int month, int date,int hour, int minute, int second, int nano) 
 