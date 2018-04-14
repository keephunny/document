　　CyclicBarriar：用于多个线程在一个指定的公共屏障点（或者说集合点）相互等待，await()方法代表屏障点，每次调用await()，计数（创建CyclicBarriar对象时传入int类型的参数，表示初始计数）减一，直到减到0后，表示所有线程都抵达，然后开始执行后面的任务，示例代码

    public static void testCycliBarries()throws Exception{
        final CyclicBarrier barrier=new CyclicBarrier(2);

        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for(int i=0;i<2;i++){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println("线程："+Thread.currentThread().getName()+" "+barrier.getNumberWaiting()+" 已到达");
                        //如果没有达到公共屏障点。则该线程处于阻塞状态，如果达到公共屏障点则所有处于等待
                        barrier.await();
                        System.out.println("线程："+Thread.currentThread().getName()+" 通过");


                        Thread.sleep(1000);
                        System.out.println("线程："+Thread.currentThread().getName()+" "+barrier.getNumberWaiting()+" 已到达");
                        //如果没有达到公共屏障点。则该线程处于阻塞状态，如果达到公共屏障点则所有处于等待
                        barrier.await();
                        System.out.println("线程："+Thread.currentThread().getName()+" 通过");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        executor.shutdown();
    }