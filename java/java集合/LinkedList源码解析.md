LinkedList是底层采用双向链表结构，和ArrayList一样，LinkedList也支持空值和重复值，由于LinkedList基于链表实现，存储元素过程中，无需像ArrayList那样扩容，但需要额外的空间存储前驱和后继的引用。LinkedList在链表头部和尾部插入效率较高，但在指定位插入时效率低。因为在指定位置插入时需定位到该节点。些操作复杂度为O(n)。LinkedList是非线程安全的集类，并发环境下多个线程同时操作会引发错误。

## 继承体系
LinkedList的继承较为复杂，继承自AbstractSequentialList同时又实现了List和Deque接口。  
AbstractSequentialList提供了一套基于顺序访问的接口，通过继承此类,子类仅需实现部分代码即可拥有完整一套访问某种序列表的接口。
    public E get(int index) {
        try {
            return listIterator(index).next();
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    public void add(int index, E element) {
        try {
            listIterator(index).add(element);
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    // 留给子类实现
    public abstract ListIterator<E> listIterator(int index);

所以只要继承类实现了 listIterator 方法，它不需要再额外实现什么即可使用。对于随机访问集合类一般建议继承 AbstractList 而不是 AbstractSequentialList。LinkedList 和其父类一样，也是基于顺序访问。所以 LinkedList 继承了 AbstractSequentialList，但 LinkedList 并没有直接使用父类的方法，而是重新实现了一套的方法。  

另外，LinkedList 还实现了 Deque (double ended queue)，Deque 又继承自 Queue 接口。这样 LinkedList 就具备了队列的功能。比如，我们可以这样使用：
    Queue<T> queue = new LinkedList<>();

### 查找
LinkedList底层基于链表结构，无法像ArrayList那样随机访问指元素，LinkedList查找需要从链表头节点向后查找，时间复杂度为O(n)
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    Node<E> node(int index) {
        /*
        * 则从头节点开始查找，否则从尾节点查找
        * 查找位置 index 如果小于节点数量的一半，
        */    
        if (index < (size >> 1)) {
            Node<E> x = first;
            // 循环向后查找，直至 i == index
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
### 遍历
链表的遍历从头节点往后遍历，使用foreach遍历LinkedList最终转换成迭代器形式。 
    public ListIterator<E> listIterator(int index) {
        checkPositionIndex(index);
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;
        private int expectedModCount = modCount;

        /** 构造方法将 next 引用指向指定位置的节点 */
        ListItr(int index) {
            // assert isPositionIndex(index);
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;    // 调用 next 方法后，next 引用都会指向他的后继节点
            nextIndex++;
            return lastReturned.item;
        }
        // 省略部分方法
    }
LinkedList 不擅长随机位置访问，如果大家用随机访问的方式遍历 LinkedList，效率会很差。
### 插入
LinkedList 除了实现了 List 接口相关方法，还实现了 Deque 接口的很多方法，所以我们有很多种方式插入元素。
    /** 在链表尾部插入元素 */
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /** 在链表指定位置插入元素 */
    public void add(int index, E element) {
        checkPositionIndex(index);

        // 判断 index 是不是链表尾部位置，如果是，直接将元素节点插入链表尾部即可
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    /** 将元素节点插入到链表尾部 */
    void linkLast(E e) {
        final Node<E> l = last;
        // 创建节点，并指定节点前驱为链表尾节点 last，后继引用为空
        final Node<E> newNode = new Node<>(l, e, null);
        // 将 last 引用指向新节点
        last = newNode;
        // 判断尾节点是否为空，为空表示当前链表还没有节点
        if (l == null)
            first = newNode;
        else
            l.next = newNode;    // 让原尾节点后继引用 next 指向新的尾节点
        size++;
        modCount++;
    }

    /** 将元素节点插入到 succ 之前的位置 */
    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        // 1. 初始化节点，并指明前驱和后继节点
        final Node<E> newNode = new Node<>(pred, e, succ);
        // 2. 将 succ 节点前驱引用 prev 指向新节点
        succ.prev = newNode;
        // 判断尾节点是否为空，为空表示当前链表还没有节点    
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;   // 3. succ 节点前驱的后继引用指向新节点
        size++;
        modCount++;
    }
### 删除
如果大家看懂了上面的插入源码分析，那么再看删除操作实际上也很简单了。删除操作通过解除待删除节点与前后节点的链接，即可完成任务。过程比较简单，看源码吧：
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            // 遍历链表，找到要删除的节点
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);    // 将节点从链表中移除
                    return true;
                }
            }
        }
        return false;
    }

    public E remove(int index) {
        checkElementIndex(index);
        // 通过 node 方法定位节点，并调用 unlink 将节点从链表中移除
        return unlink(node(index));
    }

    /** 将某个节点从链表中移除 */
    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;
        
        // prev 为空，表明删除的是头节点
        if (prev == null) {
            first = next;
        } else {
            // 将 x 的前驱的后继指向 x 的后继
            prev.next = next;
            // 将 x 的前驱引用置空，断开与前驱的链接
            x.prev = null;
        }

        // next 为空，表明删除的是尾节点
        if (next == null) {
            last = prev;
        } else {
            // 将 x 的后继的前驱指向 x 的前驱
            next.prev = prev;
            // 将 x 的后继引用置空，断开与后继的链接
            x.next = null;
        }

        // 将 item 置空，方便 GC 回收
        x.item = null;
        size--;
        modCount++;
        return element;
    }