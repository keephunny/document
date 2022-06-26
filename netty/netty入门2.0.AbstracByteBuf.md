```

public abstract class AbstractByteBuf extends ByteBuf {
	private static final boolean checkAccessible;

static {
        checkAccessible = SystemPropertyUtil.getBoolean(PROP_MODE, true);
        if (logger.isDebugEnabled()) {
            logger.debug("-D{}: {}", PROP_MODE, checkAccessible);
        }
    }

    static final ResourceLeakDetector<ByteBuf> leakDetector =
            ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ByteBuf.class);

    int readerIndex;
    int writerIndex;
    private int markedReaderIndex;
    private int markedWriterIndex;
    private int maxCapacity;

    //是否可读
    @Override
    public boolean isReadable() {
        return writerIndex > readerIndex;
    }

    //是否可读numBytes个字节
    @Override
    public boolean isReadable(int numBytes) {
        return writerIndex - readerIndex >= numBytes;
    }

    //是否可写
    @Override
    public boolean isWritable() {
        return capacity() > writerIndex;
    }

    //是否可写numBytes个字节
    @Override
    public boolean isWritable(int numBytes) {
        return capacity() - writerIndex >= numBytes;
    }
    
    //剩余可读字节
    @Override
    public int readableBytes() {
        return writerIndex - readerIndex;
    }

    //剩余可写字节
    @Override
    public int writableBytes() {
        return capacity() - writerIndex;
    }

    //最大可写字节
    @Override
    public int maxWritableBytes() {
        return maxCapacity() - writerIndex;
    }

    //记录读取下标,赋值给markedReaderIndex
    @Override
    public ByteBuf markReaderIndex() {
        markedReaderIndex = readerIndex;
        return this;
    }

    //还原读下标为标记的值
    @Override
    public ByteBuf resetReaderIndex() {
        readerIndex(markedReaderIndex);
        return this;
    }

    //记录写下标，赋值给markedWriterIndex
    @Override
    public ByteBuf markWriterIndex() {
        markedWriterIndex = writerIndex;
        return this;
    }

    //还原写下标为标记的值
    @Override
    public ByteBuf resetWriterIndex() {
        writerIndex(markedWriterIndex);
        return this;
    }

    //丢弃已读字节
    @Override
    public ByteBuf discardReadBytes() {
        ensureAccessible();
        //如果还没读字节，则无动作
        if (readerIndex == 0) {
            return this;
        }

        //如果只是读了一部分，还没读完，则丢弃已经读取部分
        if (readerIndex != writerIndex) {
            //0 1 2 3 4 5 6 7 8
            //    r   w
            //r =2  w = 0
            //将0和1丢弃，充值r，w下标位置
            //2 3 4 5 6 7 8 0 0
            //r   w
            //r = 0 w = 2
            setBytes(0, this, readerIndex, writerIndex - readerIndex);
            writerIndex -= readerIndex;

            //修正markedReaderIndex和markedWriterIndex
            adjustMarkers(readerIndex);
            readerIndex = 0;
        } else {
            //如果已经全部读完，则丢弃所有，r，w下标归0
            adjustMarkers(readerIndex);
            writerIndex = readerIndex = 0;
        }
        return this;
    }

    //丢弃一部分字节
    @Override
    public ByteBuf discardSomeReadBytes() {
        ensureAccessible();
        if (readerIndex == 0) {
            return this;
        }

        if (readerIndex == writerIndex) {
            adjustMarkers(readerIndex);
            writerIndex = readerIndex = 0;
            return this;
        }

        //跟之前逻辑唯一区别就是如果readerIndex >= capacity/2就丢弃， >>>1 除2的意思
        if (readerIndex >= capacity() >>> 1) {
            setBytes(0, this, readerIndex, writerIndex - readerIndex);
            writerIndex -= readerIndex;
            adjustMarkers(readerIndex);
            readerIndex = 0;
        }
        return this;
    }

    //调整标记位下标
    protected final void adjustMarkers(int decrement) {
        int markedReaderIndex = this.markedReaderIndex;
        //如果标记值小于丢弃的值，说明标记位置之前的字节已经被丢弃了。
        if (markedReaderIndex <= decrement) {
            //所以要把标记为设置为0
            this.markedReaderIndex = 0;
            int markedWriterIndex = this.markedWriterIndex;
            //写入标记位如果小于丢弃的字节长度，说明写入标记为之前的字节已经被丢弃
            if (markedWriterIndex <= decrement) {
                //那么写入标记为旧没有意义了，需要设置为0
                this.markedWriterIndex = 0;
            } else {
                //否则写入标记为往前移动
                this.markedWriterIndex = markedWriterIndex - decrement;
            }
        } else {
            //读取标记为往前移动
            this.markedReaderIndex = markedReaderIndex - decrement;
            //写入标记为往前移动,因为读取标记为大于decrement，那么写入标记为一定大于decrement
            markedWriterIndex -= decrement;
        }
    }


    //扩容
    final void ensureWritable0(int minWritableBytes) {
        ensureAccessible();
        //传入的大小 如果小于 capacity() - writerIndex; 则没问题
        if (minWritableBytes <= writableBytes()) {
            return;
        }

        //如果超过最大容量则抛异常
        if (minWritableBytes > maxCapacity - writerIndex) {
            throw new IndexOutOfBoundsException(String.format(
                    "writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s",
                    writerIndex, minWritableBytes, maxCapacity, this));
        }

        // Normalize the current capacity to the power of 2.
        //新的容量，扩大2倍，但是不超过maxCapacity
        int newCapacity = alloc().calculateNewCapacity(writerIndex + minWritableBytes, maxCapacity);

        // Adjust to the new capacity.
        //调整容量
        capacity(newCapacity);
    }

    @Override
    public int ensureWritable(int minWritableBytes, boolean force) {
        //有足够容量，底层没扩容
        if (minWritableBytes <= writableBytes()) {
            return 0;
        }

        final int maxCapacity = maxCapacity();
        final int writerIndex = writerIndex();

        //超过最大容量
        if (minWritableBytes > maxCapacity - writerIndex) {
            if (!force || capacity() == maxCapacity) {
                //容量不够，底层没扩容
                return 1;
            }

            //容量不够，底层扩容
            capacity(maxCapacity);
            return 3;
        }

        //容量够，底层扩容
        // Normalize the current capacity to the power of 2.
        int newCapacity = alloc().calculateNewCapacity(writerIndex + minWritableBytes, maxCapacity);

        // Adjust to the new capacity.
        capacity(newCapacity);
        return 2;
    }

    //返回大端序或小端序的实例
    @Override
    public ByteBuf order(ByteOrder endianness) {
        if (endianness == order()) {
            return this;
        }
        return newSwappedByteBuf();
    }

    //创建一个与当前对象翻转的对象,转换字节序列
    protected SwappedByteBuf newSwappedByteBuf() {
        return new SwappedByteBuf(this);
    }

    //从底层数组index位置开始拷贝字节到dst
    @Override
    public ByteBuf getBytes(int index, byte[] dst) {
        getBytes(index, dst, 0, dst.length);
        return this;
    }

    //从底层数组index位置开始拷贝字节到dst
    @Override
    public ByteBuf getBytes(int index, ByteBuf dst) {
        getBytes(index, dst, dst.writableBytes());
        return this;
    }

    @Override
    public ByteBuf readSlice(int length) {
        checkReadableBytes(length);
        ByteBuf slice = slice(readerIndex, length);
        readerIndex += length;
        return slice;
    }

    @Override
    public ByteBuf readRetainedSlice(int length) {
        checkReadableBytes(length);
        ByteBuf slice = retainedSlice(readerIndex, length);
        readerIndex += length;
        return slice;
    }

    //拷贝对象，读写索引完全独立，底层数组不共享
    @Override
    public ByteBuf copy() {
        return copy(readerIndex, readableBytes());
    }

    //拷贝对象，读写索引完全独立，底层数组共享
    @Override
    public ByteBuf duplicate() {
        ensureAccessible();
        return new UnpooledDuplicatedByteBuf(this);
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return duplicate().retain();
    }

    //底层数组共享，返回byteBuf包含未读数据，读写坐标完全独立
    @Override
    public ByteBuf slice() {
        return slice(readerIndex, readableBytes());
    }

    @Override
    public ByteBuf retainedSlice() {
        return slice().retain();
    }

    @Override
    public ByteBuf slice(int index, int length) {
        ensureAccessible();
        return new UnpooledSlicedByteBuf(this, index, length);
    }

    @Override
    public ByteBuf retainedSlice(int index, int length) {
        return slice(index, length).retain();
    }
}
```

