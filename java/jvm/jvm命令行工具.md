
### jps
java virtaul machine process status tool 查询linux系统当前所有的java进程pid的命令。
```
jps [option] [pid]
-q：仅输出vm标识符，不包括classname、jar name、arguments in main method
-m：输出main method的参数
-l：输出完整的包名，应用主类名，jar的完全路径名
-v：输出jvm参数
-V：输出口通过flag文件传递到jvm中的参数
-Joption：传递参数据到vm
```

### jinfo
configuration info for java 实时查看和调整JVM配置参数，并不是所有的参数都支持动态修改
```
jinfo pid
jinfo -flags pid
jinfo -flag MaxHeapSize PID 
jinfo -flag UseG1GC PID   #查看是否用到了G1，有'-'号表示没有用到

#修改
jinfo -flag [+|-] PID 
jinfo -flag <name>=<value> PID

#查看全部
jinfo -flags PID  
```

### jstat
jvm statistics monitoring tool，查看进程的类信息和GC信息，显示进程中的类装载、内存、垃圾收集、JIT编译等运行数据
* 查看虚拟机性能统计信息
* 查看类装载信息
    jstat -class PID 1000 10 查看进程的类装载信息，每1000ms输出一次，共输出10次。可以看到多少类被装载进来，也可以看多少类补卸载了。
* 查看垃圾收集信息
    jstat -gc PID 1000 10

```
jstat -class pid
    Loaded Bytes unloaded Bytes time
```

### jmap
jmap jvm memory map：获取dump文件，获取java内存的相关信息、堆中各区域的使用情况、统计信息、类加载信自己等。
* jmap -heap pid
    获取heap的概要信息，gc使用的算法，heap的配置及jvm堆内存的使用情况
* jmp -histo:live pid
    获取每个class的实例数目、字节数、类全名信息。live参数只统计活的对象数量。
查看堆内存快照,输出jvm的heap内容到文件， live子选项是可选的，假如指定live选项,那么只输出活的对象到文件
jmap -dump:live,format=b,file=myjmapfile.txt 进程id

这个命令执行，JVM会将整个heap的信息dump写入到一个文件，heap如果比较大的话，就会导致这个过程比较耗时，并且执行的过程中为了保证dump的信息是可靠的，所以会暂停应用。
