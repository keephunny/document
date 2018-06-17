## log4j主要有三个主要组件
1. Loggers
Loggers组件在此系统中被分为五个级别debug、info、warn、error、fatal。
2. Appenders
控制日志输出的目标地址，如控制台、文件、数据库等。可以根据天数或者文件大小产生新的文件，可以以流的形式发送到其它地方等。
    * org.apache.log4j.ConsoleAppender控制台
    * org.apache.log4j.FileAppender 文件
    * org.apache.log4j.RollingFileAppender 按文件大小生成日志文件
    * org.apache.log4j.WriteAppender 将日志以流格式发送到任意指定的地方
3. Layouts
输出指定格式的日志
    * org.apache.log4j.HTMLLayout 以html表格形式布局
    * org.apache.log4j.PatternLayout 灵活地指定布局模式
    * org.apache.log4j.SimpleLayout 包含日志信息的级别和信息字符串
    * org.apache.log4j.TTCCLayout 包含日志产生的时间、线程、类别等信息

    https://www.cnblogs.com/wangzhuxing/p/7753420.html
    https://segmentfault.com/q/1010000007407285
    https://blog.csdn.net/azheng270/article/details/2173430/