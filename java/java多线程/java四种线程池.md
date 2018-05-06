### new Thread的弊端
每次new Thread新建对象性能差，线程缺乏统一管理，可能无限制新建线程，相互之间竟争，及可能占用过多系统资源导致死机或OOM。缺乏更多功能，如定时执行，定期执行，线程中断。
### 线程池的好处
重用存在的线程，减少对象创建，消亡的开销，性能性。可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竟争，避免堵塞，提供定时执行、定期质变行单线程并发数控制等功能。  
线程池作用就是限制系统中执行线程的数量，根据系统的环境情况，可以自动或手动的设置线程数量，达到运行的最佳效果。  
减少了创建和销毁线程的次数，每个工作线程都可以被重复利用，可执行多个任务。  
java里面线程池的顶级接口是Executor，但是严格意义上讲Executor并不是一个线程池，而只是一个执行线程的工具，真正的线程池接口是ExecutorService。  
ExccutorServie：线程池的接口
ScheduledExecutorService：和Timer/TimerTask类似，解决哪些需要任务重复执行的问题
ThreadPoolExecutor：ExecutorServie的默认实现
ScheduledThreadPoolExecutor：继承ThreadPoolExecutor的ScheduledExecutorService接口实现，周期性任务调度的类实现。

### ThreadPoolExecutor
线程池Executor底层实现类的ThreadPoolExecutor的构造函数

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
       ...
    }
* corePoolSize：池中保存的线程数，包括空闲线程，需要注意的是在初创建线程池时线程不会即启动。直到有任务提交才开始启动线程并逐渐时线程数目达到corePoolSize。若想一开始就创建所有核心线程需调用prestartAllCoreThreads()方法。  
* maximumPoolSize：池中允许的最大线程数，需要注意的是当核心线程满且队列满时才会判断当前线程数是否小于最大线程数，并决定是否创建新线程。
* keepAliveTime：当线程数大于核心时，多于的空闲线程最多存活时间。
* unit：keepAliveTime参数的时间单位。
* workQueue：当线程数目超过核核心线程数时用于保存任务队列，主要三种类型的BlockingQueue可供选择。无界队列、有界队列、同步移交。此队列级保存实现Runnable接口的任务。
* threadFactory：执行程序创建新线程时使用的工厂。
* handler：阻塞队列已满且线程数达到最大值时所采取的饱和策略。中目、抛弃、抛弃最旧、调用者运行。

## 阻塞队列
如果运行的线程少于corePoolSize则Execotor始终首选添加新的线程，而不进行排队，如果大于等于corePoolSize则请求加入队列，而不添加新的线程。

## java线程池
java通过Executors提供了四种线程池，分别是：
* newCachedThreadPool创建一个可缓存的线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
* newFixedThreadPool创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
* newScheduledThreadPool创建一个定长线程池，支持定时及周期性任务执行。
* newSingleThreadExecutor创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行。

### newCachedThreadPool
创建一个可缓存的线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    //线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。

    public static void testCachedThreadPool() {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(  1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() +" "+index);
                }
            });
        }
        executor.shutdown();
    }
### newFixedThreadPool
创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

    public static void testFixedThreadPool(){
        ExecutorService executor=Executors.newFixedThreadPool(3);
        for(int i=0;i<10;i++){
            final int index=i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" "+index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    //线程池大小为3，每个任务间隔2秒输出
定长线程池的大小最好根据系统资源进行设置，如Runtime,getRuntime().avaliableProcessors()//返回可用处理器的Java虚拟机的数量

### newScheduledThreadPool
创建一个定长线程池，支持定时及周期性执行

    public static void testScheduledThreadPool() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        //延时执行
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "  " + "delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);

        //定时执行
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " "+(new Date()));
            }
        },1,3,TimeUnit.SECONDS);
        //表示1秒后每隔3秒执行一次

        //executor.shutdown();
    }
ScheduledExecutorService比Timer更安全，功能更强大
### newSingleThreadExecutor
创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有的任务按照指定顺序执行

    public static void testSingleThreadExecutor(){
        ExecutorService executor=Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index=i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" "+(new Date())+" "+index);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        //结果依次输出，相当于顺序质变行
    }

    //定长线程池
    ExecutorService executor=Executors.newFixedThreadPool)()
    //单线程化线程池 保证顺序执行
    ExecutorService executor=Executors.newSingleThreadPool()
    //可缓存线程池
    ExecutorService executor=Executors.newCachedThreadPool()
    //定长线程池，定时器
    ExecutorService executor=Executors.newScheduledThreadPool()
### newWorkStealingPool
jdk1.8新增加的一线程池实现，创建一个拥有多个任务队列的线程池。
    public static ExecutorService newWorkStealingPool() {
        return new ForkJoinPool
            (Runtime.getRuntime().availableProcessors(),
             ForkJoinPool.defaultForkJoinWorkerThreadFactory,
             null, true);
    }

### 线程池关闭
shutdown() 方法在终止前允许执行以前提交的任务。  
shutdownNow() 方法阻止等待任务启动并试图停止当前正在执行的任务。在终止时，执行程序没有任务在执行，也没有任务在等待执行，并且无法提交新任务。  
shutdown调用后，不可以再submit新的task，已经submit的将继续执行。  
shutdownNow试图停止当前正执行的task，并返回尚未执行的task的list  



