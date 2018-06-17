<encoder> ： 负责两件事，一是把日志信息转换成字节数组，二是把字节数组写入到输出流。
目前PatternLayoutEncoder 是唯一有用的且默认的encoder ，有一个<pattern>节点，用来设置日志的输入格式。使用“%”加“转换符”方式，如果要输出“%”，则必须用“\”对“\%”进行转义。

## %logger 输出日志的logger名
%c、%lo、%logger 输出日志的logger名，可有一个整形参数，功能是缩短logger名，设置为0表示只输入logger最右边点符号之后的字符串。  
* %logger	mainPackage.sub.sample.Bar	mainPackage.sub.sample.Bar
* %logger{0}	mainPackage.sub.sample.Bar	Bar
* %logger{5}	mainPackage.sub.sample.Bar	m.s.s.Bar
* %logger{10}	mainPackage.sub.sample.Bar	m.s.s.Bar
* %logger{15}	mainPackage.sub.sample.Bar	m.s.sample.Bar
* %logger{16}	mainPackage.sub.sample.Bar	m.sub.sample.Bar
* %logger{26}	mainPackage.sub.sample.Bar	mainPackage.sub.sample.Bar

## %class
%C、%class输出执行记录请求的调用者的全限定名。参数与上面的一样。尽量避免使用，除非执行速度不造成任何问题。

## %contextName 上下文名称
%contextName、%cn输出上下文名称，需要在根结节点设置<contextName>myAppName</contextName>  

## %date 日期
%date、%d输出日志的打印日期，模式语法与java.text.SimpleDateFormat兼容。  
* %d	    2006-10-20 14:06:49,812
* %date	    2006-10-20 14:06:49,812
* %date{ISO8601}	    2006-10-20 14:06:49,812
* %date{HH:mm:ss.SSS}	14:06:49.812
* %date{dd MMM yyyy ;HH:mm:ss.SSS}	20 oct. 2006;14:06:49.812

## %file源文件名称
%file、%F输出执行记录的java源文件名称。尽量避免使用。

## %caller调用者位置
%caller输出生成日志调用的位置信息，整数选项表示输出信息的深度。
    例如， %caller{2}   输出为：
    0    [main] DEBUG - logging statement 
    Caller+0   at mainPackage.sub.sample.Bar.sampleMethodName(Bar.java:22)
    Caller+1   at mainPackage.sub.sample.Bar.createLoggingRequest(Bar.java:17)
    例如， %caller{3}   输出为：

    16   [main] DEBUG - logging statement 
    Caller+0   at mainPackage.sub.sample.Bar.sampleMethodName(Bar.java:22)
    Caller+1   at mainPackage.sub.sample.Bar.createLoggingRequest(Bar.java:17)
    Caller+2   at mainPackage.ConfigTester.main(ConfigTester.java:38)

## %line行号
%line、%L输出执行日志请求的行号，尽量避免使用。

## %message具体信息
%message、%msg、%m：输出应用程序提供的信息

## %method方法名
%method、%M：输出日志请求的方法名，尽量避免使用。

## %n换行符
%n：输出平台无关的换行符\n或\r\n。

## %level日志级别
%level、%p、%le：输出日志级别

## %relative创建时间
%relative、%r：输出从程序启动到创建日志记录的时间，单位是毫秒。

## thread线程名称
%thread、%t：输出日志的线程名。

## replace(p ){r, t}替换
p 为日志内容，r 是正则表达式，将p 中符合r 的内容替换为t 。%replace(%msg){'\s', ''}

## ${PID}进程ID


## 格式修饰符
可选的格式修饰符位于“%”和转换符之间。  
* -：左对齐标志，默认右对齐。
* n：最小宽度修饰符。
* .：最大宽度修饰符。如果字符大于最大宽度，则从前面截断。点符号“.”后面加减号“-”在加数字，表示从尾部截断。

## 定义一个颜色pattern变量
* %highlight(%-5level)
* %boldYellow(%thread)
* %boldGreen(%logger) 
* %cyan(%logger{15})

## conversionRule
* <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
* <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
* <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />

%clr([%level])