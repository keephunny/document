

* CPU Utilization
    CPU利用率，某一段时间内CPU资源占用情况。
* CPU load
    某一段时间内，CPU正在处理以及等待CPU处理的进程数之和，load越高说明CPU利用率低。
* CPU jumps
    就是process(thread)的切换，如果切换过多，会证CPU忙于切换，也会导致影响吞吐量，值截止高说明等待共享资源的线程数越多。

* disk space usage
    磁盘使用情况
* memory usage
    内存使用情况
* network traffic on eth0
    网卡流量
* swap usage
    交换分区使用情况

* CPU idle time：空闲的cpu时间比【简称id】
* CPU user time：用户态使用的cpu时间比【简称us】
* CPU system time：系统态使用的cpu时间比【简称sy】
* CPU iowait time：cpu等待磁盘写入完成时间【简称wa】
* CPU nice time：用做nice加权的进程分配的用户态cpu时间比【简称ni】
* CPU interrupt time：硬中断消耗时间【简称hi】
* CPU softirq time：硬中断消耗时间【简称si】
* CPU steal time：虚拟机偷取时间【简称st】