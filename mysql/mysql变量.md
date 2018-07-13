mysql存储过程中定义变量有两种方式： 
1. 使用set或seelct直接赋值，变量名以@开头，可以在一个会话的任何地方声明，作用域是整个会话，称为用户变量。
2. 使用declare关键字声明变量，只能在存储过程中使用，称为存储过程变量。
    set @var=1;
    declare var int default 0;
 两者区别：以declare声明的变量都会被初始化为null，而会话变量(@变量)则不会被再初始化，在这个会话内，只须初始化一次，之后会话内都是对上一次计算的结果，就相当于这个会话内的全局变量。     


 ## 局部变量
 只在当前begin/end代码块中有效，局部变量一般用在sql语句块中，比如存储过程的begin/end，其作用域仅限于该语句块，在该语句块执行完毕后，局部变量就消失了。declare语句专门用于定义局部变量，set语句是设置不同类型的变量，包括会话变量和全局变量。    
    -- 方式1
    DECLARE c int DEFAULT 0;
    SET c=a+b;
    SELECT c AS C;

    -- 方式2
    DECLARE v_employee_name VARCHAR(100);
    SELECT employee_name INTO v_employee_name 
    FROM employees
    WHERE employee_id=1;

 ## 用户变理
 在客户端连接到数据库实例整个过程中用户变量都是有效的。mysql中用户变量不用提前申明，在用的时候直接用@变量名使用就可以了。   
    set @num=1; 或set @num:=1;
    
    -- 查询赋值 　如果这查询返回多个值的话，取最后一条记录
    select @vn:=name  from table1 where id=1;
    select @vn;

 ## 会话变量
 服务器为每个连接的客户端维护一系列会话变量，使用相应全局变量的当前值对客户端的会话变量进行初始化，设置会话变量不需特殊权限，但客户端只能更改自己的会话变量。会话变量作用域与用户变量一样，仅限于当前连接，当前连接断开后，其设置的所有会话变量均失效。 
    set session var_name = value;
    set @@session.var_name = value;
    set var_name = value;  #缺省session关键字默认认为是session
    -- 查看所有的会话变量
    SHOW  SESSION VARIABLES;
    查看一个会话变量也有如下三种方式：
    select @@var_name;
    select @@session.var_name;
    show session variables like "%var%";


 ## 全局变量   
 全局变量影响服务器整体操作，当服务器启动时，它将所有的全局变量初始化为默认值，修改全局变量，必须具有supper权限，全局变量作用于server的整个生命周期，但是不能跨重启。要让全局变量重启后继续生效，则需要配置到配置文件。
    //注意：此处的global不能省略。根据手册，set命令设置变量时若不指定GLOBAL、SESSION或者LOCAL，默认使用SESSION
    set global var_name = value; 
    set @@global.var_name = value; 
    select @@global.var_name;
    show global variables like “%var%”;


    https://blog.csdn.net/JQ_AK47/article/details/52087484