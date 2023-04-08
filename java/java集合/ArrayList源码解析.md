ArrayList是一种变长集合类，基于定长数组实现，允许空值和重复元素。当往ArrayList中添加元素数量大于基底层数组容量时，其会通过扩容机制重新生成一个更大的数组，另外，由于ArrayList底层基于数组实现，所以可以保证在O(1)复杂度下完成随机查找操作，其他方面，ArrayList是非线程安全类，并发环境下多个线程同时操作会引发不可预知的错误。  

ArrayList是变长集合类，核心是扩容机制，所以需要了解其扩容实现方式。  

## 源码分析
### 构造方法
ArrayList有两个构造方法，一个无参，一个传入初始容量。
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    //这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化
    transient Object[] elementData;
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+nitialCapacity);
        }
    }
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
两个构造方法并不复杂，目的都是为了初始化底层的数组elementData，区别在于无参构造方法会将elementData初始化一个空数组，插入元素时，扩容将会按默认值重新初始化数组，而有参构造方法会将elementData初始化为参数值大小的数组。一般情况下我们用默认构造方法。如果在已知元素的情况下，应使用有参构造方法，按需分配，避免浪费。
### 插入
对数组结构插入操作分为两种情况，一种在元素序列尾部插入，另一种在元素指定位置插入。
    /** 在元素序列尾部插入 */
    public boolean add(E e) {
        // 1. 检测是否需要扩容
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 2. 将新元素插入序列尾部
        elementData[size++] = e;
        return true;
    }

    /** 在元素序列 index 位置处插入 */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        // 1. 检测是否需要扩容
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 2. 将 index 及其之后的所有元素都向后移一位
        System.arraycopy(elementData, index, elementData, index + 1,size - index);
        // 3. 将新元素插入至 index 处
        elementData[index] = element;
        size++;
    }
    
    /** 计算最小容量 */
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
    
    /** 扩容的入口方法 */
    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }
    
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
    
    /** 扩容的核心方法 */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        // newCapacity = oldCapacity + oldCapacity / 2 = oldCapacity * 1.5
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 扩容
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        // 如果最小容量超过 MAX_ARRAY_SIZE，则将数组容量扩容至 Integer.MAX_VALUE
        return (minCapacity > MAX_ARRAY_SIZE) ?Integer.MAX_VALUE :MAX_ARRAY_SIZE;
    }
### 删除
不同于插入操作，ArrayList没有无参删除方法，只能在删除指定位置的元素。
    /** 删除指定位置的元素 */
    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        // 返回被删除的元素值
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            // 将 index + 1 及之后的元素向前移动一位，覆盖被删除值
            System.arraycopy(elementData, index+1, elementData, index,numMoved);
        // 将最后一个元素置空，并将 size 值减1                
        elementData[--size] = null; // clear to let GC do its work
        return oldValue;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }
    
    /** 删除指定元素，若元素重复，则只删除下标最小的元素 */
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            // 遍历数组，查找要删除元素的位置
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
    
    /** 快速删除，不做边界检查，也不返回删除的元素值 */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }
### 遍历
ArrayList实现了RandomAccess接口这是一个标志性接口。表明他具有随机访问的能力，ArrayList底层是基于数组实现的。所以他可在常数创段的时间内完成随机访问，效率很高。对ArrayList进行遍历时，一般情况下，我们常用foreach循环遍历，但这并不推荐遍历方式，ArrayList具有随机访问的能力，如果在一些效率要求比较高的场景下推荐使用for循环。foreach 最终会被转换成迭代器遍历的形式，效率不如上面的遍历方式。
    for (int i = 0; i < list.size(); i++) {
        list.get(i);
    }
### 遍历时删除
遍历时删除是一个不正确的操作，即使有时候代码不出现异常，但执行逻辑也会出现问题。关于这个问题，阿里巴巴 Java 开发手册里也有所提及。这里引用一下：
【强制】不要在 foreach 循环里进行元素的 remove/add 操作。remove 元素请使用 Iterator 方式，如果并发操作，需要对 Iterator 对象加锁。
    //错误
    List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            System.out.println(temp);
            if("1".equals(temp)){
                a.remove(temp);
            }
        }
    }

    //正确
    List<String> list = new ArrayList<String>();
    list.add("a");
    list.add("b");
    list.add("c");
    list.add("d");
    Iterator<String> iter = list.iterator();
    while(iter.hasNext()){
        String s = iter.next();
        System.out.println(s);
        if(s.equals("b")){
            iter.remove();
        }
    }
    System.out.println(list);


### List初始化

```
List<Integer> test = new ArrayList<Integer>(){{
    add(1);
    add(2);
}};

List<Integer> test = Arrays.asList(1, 2, 3);
List<Integer> test = new ArrayList<>(Arrays.asList(1, 2, 3));

List<Integer> test = Stream.of(1, 2, 3).collect(Collectors.toList());

List<Integer> test = Lists.newArrayList(1, 2, 3);
```

