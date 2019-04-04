top命令是linux下常用的性能分析工具，能够实时显示系统中的各个进程资源占用状况。
## top命令
显示结果
    top - 01:06:48 up  78day,1:22,  1 user,  load average: 0.06, 0.60, 0.48
    Tasks:  29 total,   1 running,  28 sleeping,   0 stopped,   0 zombie
    Cpu(s):  0.3% us,  1.0% sy,  0.0% ni, 98.7% id,  0.0% wa,  0.0% hi,  0.0% si
    Mem:    191272k total,   173656k used,    17616k free,    22052k buffers
    Swap:   192772k total,        0k used,   192772k free,   123988k cached

    PID USER      PR  NI  VIRT  RES  SHR S %CPU %MEM    TIME+  COMMAND
    1379 root      16   0  7976 2456 1980 S  0.7  1.3   0:11.03 sshd
    14704 root      16   0  2128  980  796 R  0.7  0.5   0:02.72 top
    1 root      16   0  1992  632  544 S  0.0  0.3   0:00.90 init
    2 root      34  19     0    0    0 S  0.0  0.0   0:00.00 ksoftirqd/0
    3 root      RT   0     0    0    0 S  0.0  0.0   0:00.00 watchdog/0
### top参数
top [-] [d] [p] [q] [c] [C] [S] [s]  [n]
* d：指定每两次屏幕信息刷新之间的时间间隔。当然用户可以使用s交互命令来改变之。 
* p：通过指定监控进程ID来仅仅监控某个进程的状态。 
* q：该选项将使top没有任何延迟的进行刷新。如果调用程序有超级用户权限，那么top将以尽可能高的优先级运行。 
* S：指定累计模式 
* s：使top命令在安全模式中运行。这将去除交互命令所带来的潜在危险。 
* i：使top不显示任何闲置或者僵死进程。 
* c：显示整个命令行而不只是显示命令名 


### 第一行信息
当前系统时间，系统已经运行时间，当前登录用户数量，最近5、10、15分钟的平均负载

### 第二行信息
任务的总数，运行中的任务(running)，休眼中的任务(sleeping)，停止的任务(stopped)，僵尸状态的任务(zomble) 

### 第三行信息cpu状态
* us：user运行用户进程的cpu时间
* sy：system运行内核进程的cpu时间
* ni：niced运行已调整优先级的用户进程的cpu时间
* id：idle空闲时间
* wa：io wait用于等IO完成的cpu时间
* hi：处理硬件中断的cpu时间
* si：处理软件中断的cpu时间
* st：这个虚拟机被hypervisor偷去的cpu时间

### 第四行信息内存状态
* total：全部可用内存
* free：空闲内存
* used：已使用内存
* buff/cache：缓冲内存

### 第五行信息swap状态
* total：全部可用内存
* free：空闲内存
* used：已使用内存
* buff/cache：缓冲内存

### 第七行列表
* PID：进程ID，进程的唯一标识符
* USER：进程所有者的实际用户名
* PR：进程的调度优先级，rt表示进程运行在实时态
* NI：进程的nice值（优先级），值越小优先级越高。
* VIRT：virtual memory usage虚拟内存，进程使用的虚拟内存。VIRT=SWAP+RES
* RES：resident memory usage常驻内存大小。RES=CODE+DATA
* SHR：shared memory共享内存
* S：进程的状态
    * D：不可中断的睡眠态
    * R：运行态
    * S：睡眠态
    * T：被跟踪或已停止
    * Z：僵尸态
* %CPU：自上一次更新时到现在任务所使用的CPU时间百分比。%cpu显示的是进程占用单核的百分比，而不是整个cpu的百分比。
* %MEM：进程使用的可用物理内存百分比
* TIME+：任务启动后到现在所使用的全部CPU时间，精确到百分之一秒。
* COMMAND：运行进程所使用的命令。

## 交互查看命令
* h|?：显示帮助信息
* z：改变颜色
* B：加粗
* t：显示和隐藏任务/cpu信息
* m：显示和隐藏任务/内存信息
* 1：监控每个逻辑CPU的状况
* f：进入字段显示配置模式，可增加或移除显示字段，按相应的字母操作。
* o：进入字段顺序模式，可配置显示位置顺序，按相应的字母往下移动，按shift+字母往上移动
* F|O：按指定的列排序显示，a-z指定排序列。而R可以将当前的排序列倒序。
* M：根据内存排序
* P：根据CPU排序
* H：显示线程
* s：设置刷新的时间，Change delay from 3.0 to 输入秒
* u：输入用户，显示用户的任务，Wich user(blank for all) 输入用户名
* i：忽略闲置和僵死进程。这是一个开关式命令。
* c：切换显示命令名称的完整命令行
