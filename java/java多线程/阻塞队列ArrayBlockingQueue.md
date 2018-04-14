ArrayBlockingQ，ueue是一个有界的阻塞队列，其内部实现是将对象放到一个数组里。有界意味着，它不能存储无限多数量的元素它有一个同一时间能够存储数量的上限。上限设定了无法修改。些队列按FIFO原则对元素进行排序。队列的头部是在队列中存在时间最长的元素。队列的尾部是在队列中存在时间最短。新元素插入到队列的尾部，队列检索操作是从队列头部开始获得元素。添加和删除使用的是同一把锁ReentrantLock。造成存取时会竟争同一把锁，从而使得性能相对低下。

### 构造方法
    public ArrayBlockingQueue(int capacity) {
        this(capacity, false);
    }

    public ArrayBlockingQueue(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }
    //从源码可以看出ArrayBlockingQueue的是否公平，实际是ReentrantLock的公平。
## 常用方法
### offer添加元素