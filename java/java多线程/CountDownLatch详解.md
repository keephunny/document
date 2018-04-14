CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其它线程的操作执行完后再执行。

## CountDownLatch说明
CountDownLatch在java1.5引入，存在于java.util.concurrent包下。它能够使一个线程等待其它线程完成各自的工作后再执行。例如：应用程序主线程希望在负责启动框架服务线程已经启动所有的框架服务之后再执行。CountDownLatch是通过一个计数器来实现的。计数器的初始值为线程的数量，每当一个线程完成了自已的作务后计数器就会减1。当计数器等于0时，表示所有线程已经完成了作务。然后在闭锁上等待的线程就可以恢复执行作务。  
构造函数
    //Constructs a CountDownLatch initialized with the given count.
    public void CountDownLatch(int count) {...}
        
    //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
    public void await() throws InterruptedException { }; 

    //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  

    //将count值减1
    public void countDown() { };  

构造器中的计数值（count）实际上就是闭锁需要等待的线程数量。
这个值只能被设置一次，而且CountDownLatch没有提供任何机制去重新设置这个计数值。
与CountDownLatch的第一次交互是主线程等待其他线程。
主线程必须在启动其他线程后立即调用CountDownLatch.await()方法。这样主线程的操作就会在这个方法上阻塞，直到其他线程完成各自的任务。（重要）
其他N个线程必须引用闭锁对象，因为他们需要通知CountDownLatch对象，他们已经完成了各自的任务。
这种通知机制是通过 CountDownLatch.countDown()方法来完成的；每调用一次这个方法，在构造函数中初始化的count值就减1。
所以当N个线程都调 用了这个方法，count的值等于0，然后主线程就能通过await()方法，恢复执行自己的任务。

    //CountDownLatch示例代码
    public static void main(String[] args) {
        final CountDownLatch latch=new CountDownLatch(2);

        new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println("子线程：" + Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(1000);
                    System.out.println("子线程："+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                }catch (Exception e){
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread(){
            @Override
            public void run(){
                try{
                    System.out.println("子线程："+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(1000);
                    System.out.println("子线程："+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                }catch (Exception e){
                    e.printStackTrace();
                }
            };
        }.start();

        try{
            //System.out.println("等待2个线程执行完毕");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
这是一个使用CountDownLatch非常简单的例子，创建的时候，需要指定一个初始状态值，本例为2，主线程调用 latch.await时，除非latch状态值为0，否则会一直阻塞休眠。当所有任务执行完后，主线程唤醒，最终执行打印动作。  