
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
实时查看和调整JVM配置参数
```
jinfo pid
jinfo -flags pid
```

### jstat
jvm statistics monitoring tool，查看进程的类信息和GC信息，显示进程中的类装载、内存、垃圾收集、JIT编译等运行数据


```
jstat -class pid
    Loaded Bytes unloaded Bytes time
```

### jmap
* jmap -heap pid
获取heap的概要信息，gc使用的算法，heap的配置及jvm堆内存的使用情况