方法一： 单独在应用上配置如下JVM启动参数

-Djava.rmi.server.hostname=192.168.200.136 -Dcom.sun.management.jmxremote.port=18888 -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.managementote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false

其中java.rmi.server.hostname配置的是运行JVM所在的机器IP
 

https://blog.csdn.net/mn960mn/article/details/73958701