package com.zw.pattern.test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 8683452581122892189L;

    //默认初始容量
    private static final int DEFAULT_CAPACITY = 10;

    //用于空实例的共享空数组实例
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //默认的空数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    //存储元素的数组
    //被标记为transient的属性在对象被序列化的时候不会被保存。
    //简单点就是把数据写入输出到内存在从内存中读出则字段内容会null
    transient Object[] elementData;

    //数组存储的实际元素大小
    private int size;

    //构造具有指定初始容量的空列表
    public ArrayList(int initialCapacity) {
        //如果传入的容量大于0，则把实际存储元素的数组elementData长度设置为传入的大小
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {//如果传入的容量等于0，则把实际存储元素的数组elementData设置为空数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else {//如果传入的容量小于0，则抛出非法的initialCapacity
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    //构造一个初始容量为十的空列表,想不明白源码注释说的容量为10
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    //按照集合迭代器返回的顺序构造包含指定集合元素的列表
    public ArrayList(Collection<? extends E> c) {
        //传入的集合转为数组
        elementData = c.toArray();
        //数组不为空
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            //c.toarray可能（错误地）不返回对象[]（见JAVA BUG编号6260652）
            //如果返回的不是Object[]对象则用Arrays.copyOf转换一下
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            //将elementData赋为空数组
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    //将这个ArrayList实例的容量修整为列表的当前大小。应用程序可以使用此操作来最小化数组列表实例的存储。
    //使用场景: 内存紧张时，可以调用该方法删除预留的位置，调整容量为元素实际数量。
    //如果确定不会再有元素添加进来时也可以调用该方法来节约空间
    public void trimToSize() {
        modCount++;
        //实际容量大小size小于elementData的长度
        //size == 0代表实际存储的元素为空
        //如果实际数组里没有东西即size为0则把elementData赋为空数组
        //否则把原数组按size长度拷贝一份赋值给elementData
        if (size < elementData.length) {
            elementData = (size == 0)
              ? EMPTY_ELEMENTDATA
              : Arrays.copyOf(elementData, size);
        }
    }

    //使用指定参数设置数组容量
    public void ensureCapacity(int minCapacity) {
        //如果elementData为空则minExpand=0，如果elementData不为空，则minExpand=10（默认容量大小）
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;
        //指定容量minCapacity如果大于minExpand容量则使用指定容量进一步设置数组容量
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    //用于添加元素时，确保数组容量
    private void ensureCapacityInternal(int minCapacity) {
        //如果elementData为空数组
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            //选出默认容量(10)与指定容量minCapacity两个中最大的那个
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //增加数组容量
        ensureExplicitCapacity(minCapacity);
    }

    //增加数组容量
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        //如果传入参数大于数组容量
        if (minCapacity - elementData.length > 0)
            //增加数组容量
            grow(minCapacity);
    }

    //要分配的数组的最大大小，一些VM在数组中保留一些标题字。
    //分配较大数组的尝试可能导致OutOfMemoryError：请求的数组大小超过VM限制
    //MAX_VALUE = 0x7fffffff， 0x ->16进制，f->15
    //7x10^7+15*10^6+15*10^5+15*10^4+15*10^3+15*10^2+15*10^1+15*10^0 = 2147483647
    //MAX_ARRAY_SIZE = 2147483647 - 8 = 2147483639
    //int是有符号的，在java中他代表4字节，一个字节8位也就是32位，
    //能代表的最大整数为0111 1111 1111 1111 1111 1111 1111 1111 = 2147483647
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    //增加容量，确保至少可以支持minCapacity容量的数据元素存储
    private void grow(int minCapacity) {
        //原来的数组容量大小
        int oldCapacity = elementData.length;
        //新的默认容量大小设置为原来的数组容量大小*1.5
        //为什么是1.5倍呢，技巧在这里：oldCapacity >> 1
        //oldCapacity >> 1是向右移动一位
        //比如：8对应的二进制为1111，向右移动一位后为0111,也就是4,8对4相当于缩小一倍
        //向右移动几位相当于原数除以2的多少次方
        //向左移动几位相当于原数乘以2的多少次方
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //newCapacity新容量减去传入的minCapacity容量大于0,则设置新容量=传入容量
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        //newCapacity新容量大于最大容量，则
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        //按照newCapacity来拷贝一份数组重新赋值给elementData
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    //MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    //如果传入容量minCapacity大于MAX_ARRAY_SIZE则返回Integer.MAX_VALUE
    //否则返回MAX_ARRAY_SIZE
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    //数组的大小
    public int size() {
        return size;
    }

    //数组是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //列表是否包含指定元素
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    //返回一个值在数组首次出现的位置，会根据是否为null使用不同方式判断，不存在就返回-1,时间复杂度为O(N)
    //null的话使用==判断，不为null则用equals判断
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    //返回一个值在数组最后一次出现的位置，不存在就返回-1。时间复杂度为O(N)
    //null的话使用==判断，不为null则用equals判断
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    //返回此ArrayList实例的|浅层副本,元素本身不被复制
    //复制过程数组发生改变会抛出异常
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    //返回包含此列表中所有元素的数组
    //按适当顺序（从第一个到最后一个元素）
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    //返回一个数组，使用运行时确定类型，该数组包含在这个列表中的所有元素（从第一到最后一个元素）
    //返回的数组容量由参数和本数组中较大值确定
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    //返回指定位置的值，因为是数组，所以速度特别快
    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    //返回指定位置的值，但是会检查这个位置数否超出数组长度
    public E get(int index) {
        //检查下标是否越界
        rangeCheck(index);
        return elementData(index);
    }

    //设置指定位置为一个新值，并返回之前的值，会检查这个位置是否超出数组长度
    public E set(int index, E element) {
        //检查下标是否越界
        rangeCheck(index);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    //这就是我们最常用的add方法，添加一个值，首先会确保容量
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    //指定位置添加一个值，会检查添加的位置和容量
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        //public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
        //src:源数组； srcPos:源数组要复制的起始位置； dest:目的数组； destPos:目的数组放置的起始位置； length:复制的长度
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }

    //删除指定位置的值，会检查添加的位置，返回之前的值
    //比如数组[1,2,3,4,5]想要把下标2的元素移除
    //将4,5向前依次移动一位，然后最后一位置空即可，下面是代码实现：
    //先检查下标是否越界
    //没有越界找到该元素
    //定义一个变量记录从传入的下标后一位开始至数组结束的长度
    //比如数组总长度为5，传入的下标为2，
    //numMoved = 5 - 2 -1 = 2；
    //然后从index+1的位置开始到最后开始复制给index到最后，复制长度几位numMoved
    //然后把最后一个的引用置为null，便于垃圾回收
    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        //便于垃圾回收期回收
        elementData[--size] = null; // clear to let GC do its work
        return oldValue;
    }

    //删除指定元素首次出现的位置
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    //快速删除指定位置的值，之所以叫快速，应该是不需要检查和返回值，因为只内部使用
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    //清空数组，把每一个值设为null,方便垃圾回收(不同于reset，数组默认大小有改变的话不会重置)
    public void clear() {
        modCount++;
        // clear to let GC do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;
        size = 0;
    }

    //添加一个集合的元素到末端，若要添加的集合为空返回false
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    //功能同上，从指定位置开始添加，还多一个下标是否越界检查
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        //要添加的数组
        Object[] a = c.toArray();
        //要添加的数组长度
        int numNew = a.length;
        //确保容量
        ensureCapacityInternal(size + numNew);  // Increments modCount
        //不会移动的长度(前段部分)
        int numMoved = size - index;
        //有不需要移动的，就通过自身复制，把数组后部分需要移动的移动到正确位置
        if (numMoved > 0)
            //新的数组添加到改变后的原数组中间
            System.arraycopy(elementData, index, elementData, index + numNew,
                             numMoved);
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    //删除指定范围元素。参数为开始删的位置和结束位置
    //将elementData从toIndex位置开始的元素向前移动到fromIndex，然后将toIndex位置之后的元素全部置空然后修改size。
    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        //记录从toIndex到最后的长度，也就是即将复制的长度
        int numMoved = size - toIndex;
        //复制数组元素：从toIndex开始到结尾
        //把复制的元素赋值给数组：开始位置：fromIndex开始，长度为numMoved
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                         numMoved);
        // clear to let GC do its work
        //新的数组长度为老数组长度减去移除的元素个数
        int newSize = size - (toIndex-fromIndex);
        //从新数组长度的开始到结尾，遍历将其元素置为null，利于垃圾回收
        for (int i = newSize; i < size; i++) {
            elementData[i] = null;
        }
        //更新数组长度
        size = newSize;
    }

    //用于删除、获取、set的时候进行检查index否超出数组长度
    //传入下标大于等于数组长度则抛出数组越界
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    //用于单个元素、多个元素添加进行检查index否超出数组长度
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    //抛出的异常的详情
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    //删除指定集合的元素
    public boolean removeAll(Collection<?> c) {
        //判断c是否为空，为空抛出空指针异常
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }

    //仅保留指定集合的元素
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }

    /**
     * @param complement true时从数组保留指定集合中元素的值，为false时从数组删除指定集合中元素的值。
     * @return 数组中重复的元素都会被删除(而不是仅删除一次或几次)，有任何删除操作都会返回true
     */
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            //遍历数组，并检查这个集合是否包含对应的值，移动要保留的值到数组前面，w最后值为要保留的元素的数量
            //简单点：若保留，就将相同元素移动到前段；若删除，就将不同元素移动到前段
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        } finally {//确保异常抛出前的部分可以完成期望的操作，而未被遍历的部分会被接到后面
            //r!=size表示可能出错了：c.contains(elementData[r])抛出异常
            if (r != size) {
                System.arraycopy(elementData, r, elementData, w, - r);
                w += size - r;
            }
            //如果w==size：表示全部元素都保留了，所以也就没有删除操作发生，所以会返回false；反之，返回true，并更改数组
            //而w!=size的时候，即使try块抛出异常，也能正确处理异常抛出前的操作，因为w始终为要保留的前段部分的长度，数组也不会因此乱序
            if (w != size) {
                // clear to let GC do its work
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;//改变的次数
                size = w;    //新的大小为保留的元素的个数
                modified = true;
            }
        }
        return modified;
    }

    //保存数组实例的状态到一个流（即它序列化）。写入过程数组被更改会抛出异常
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        int expectedModCount = modCount;
        //执行默认的反序列化/序列化过程。将当前类的非静态和非瞬态字段写入此流
        s.defaultWriteObject();
        // 写入大小
        s.writeInt(size);
        // 按顺序写入所有元素
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    //上面是写，这个就是读了。
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;
        // 执行默认的序列化/反序列化过程
        s.defaultReadObject();
        // 读入数组长度
        s.readInt(); // ignored
        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            ensureCapacityInternal(size);
            Object[] a = elementData;
            //读入所有元素
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }

    //返回ListIterator，开始位置为指定参数
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index);
        return new ListItr(index);
    }

    //返回ListIterator，开始位置为0
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    //返回普通迭代器
    public Iterator<E> iterator() {
        return new Itr();
    }

    //通用的迭代器实现
    private class Itr implements Iterator<E> {
        int cursor;       //游标，下一个元素的索引，默认初始化为0
        int lastRet = -1; //上次访问的元素的位置,没有就为-1
        int expectedModCount = modCount;//迭代过程不运行修改数组，否则就抛出异常
        //是否还有下一个
        public boolean hasNext() {
            return cursor != size;
        }
        //下一个元素
        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();//检查数组是否被修改
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;//向后移动游标
            return (E) elementData[lastRet = i];//设置访问的位置并返回这个值
        }
        //删除元素
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();//检查数组是否被修改
            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }
        //检查数组是否被修改
        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    //ListIterator迭代器实现
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();
            try {
                ArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();
            try {
                int i = cursor;
                ArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    //返回指定范围的子数组
    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new SubList(this, 0, fromIndex, toIndex);
    }
    //安全检查
    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
    }
    //子数组
    private class SubList extends AbstractList<E> implements RandomAccess {
        private final AbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(AbstractList<E> parent,
                int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = ArrayList.this.modCount;
        }

        public E set(int index, E e) {
            rangeCheck(index);
            checkForComodification();
            E oldValue = ArrayList.this.elementData(offset + index);
            ArrayList.this.elementData[offset + index] = e;
            return oldValue;
        }

        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return ArrayList.this.elementData(offset + index);
        }

        public int size() {
            checkForComodification();
            return this.size;
        }

        public void add(int index, E e) {
            rangeCheckForAdd(index);
            checkForComodification();
            parent.add(parentOffset + index, e);
//            this.modCount = parent.modCount;
            this.size++;
        }

        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = parent.remove(parentOffset + index);
//            this.modCount = parent.modCount;
            this.size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
//            parent.removeRange(parentOffset + fromIndex,
//                               parentOffset + toIndex);
//            this.modCount = parent.modCount;
            this.size -= toIndex - fromIndex;
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(this.size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;

            checkForComodification();
            parent.addAll(parentOffset + index, c);
//            this.modCount = parent.modCount;
            this.size += cSize;
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);
            final int offset = this.offset;

            return new ListIterator<E>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = ArrayList.this.modCount;

                public boolean hasNext() {
                    return cursor != SubList.this.size;
                }

                @SuppressWarnings("unchecked")
                public E next() {
                    checkForComodification();
                    int i = cursor;
                    if (i >= SubList.this.size)
                        throw new NoSuchElementException();
                    Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i + 1;
                    return (E) elementData[offset + (lastRet = i)];
                }

                public boolean hasPrevious() {
                    return cursor != 0;
                }

                @SuppressWarnings("unchecked")
                public E previous() {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0)
                        throw new NoSuchElementException();
                    Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super E> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = SubList.this.size;
                    int i = cursor;
                    if (i >= size) {
                        return;
                    }
                    final Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount) {
                        consumer.accept((E) elementData[offset + (i++)]);
                    }
                    // update once at end of iteration to reduce heap write traffic
                    lastRet = cursor = i;
                    checkForComodification();
                }

                public int nextIndex() {
                    return cursor;
                }

                public int previousIndex() {
                    return cursor - 1;
                }

                public void remove() {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        SubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = ArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void set(E e) {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        ArrayList.this.set(offset + lastRet, e);
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void add(E e) {
                    checkForComodification();

                    try {
                        int i = cursor;
                        SubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = ArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification() {
                    if (expectedModCount != ArrayList.this.modCount)
                        throw new ConcurrentModificationException();
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new SubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+this.size;
        }

        private void checkForComodification() {
            if (ArrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
        }

        public Spliterator<E> spliterator() {
            checkForComodification();
            return new ArrayListSpliterator<E>(ArrayList.this, offset,
                                               offset + this.size, this.modCount);
        }
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@link Spliterator} over the elements in this
     * list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, and {@link Spliterator#ORDERED}.
     * Overriding implementations should document the reporting of additional
     * characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return new ArrayListSpliterator<>(this, 0, -1, 0);
    }

    /** Index-based split-by-two, lazily initialized Spliterator */
    static final class ArrayListSpliterator<E> implements Spliterator<E> {

        /*
         * If ArrayLists were immutable, or structurally immutable (no
         * adds, removes, etc), we could implement their spliterators
         * with Arrays.spliterator. Instead we detect as much
         * interference during traversal as practical without
         * sacrificing much performance. We rely primarily on
         * modCounts. These are not guaranteed to detect concurrency
         * violations, and are sometimes overly conservative about
         * within-thread interference, but detect enough problems to
         * be worthwhile in practice. To carry this out, we (1) lazily
         * initialize fence and expectedModCount until the latest
         * point that we need to commit to the state we are checking
         * against; thus improving precision.  (This doesn't apply to
         * SubLists, that create spliterators with current non-lazy
         * values).  (2) We perform only a single
         * ConcurrentModificationException check at the end of forEach
         * (the most performance-sensitive method). When using forEach
         * (as opposed to iterators), we can normally only detect
         * interference after actions, not before. Further
         * CME-triggering checks apply to all other possible
         * violations of assumptions for example null or too-small
         * elementData array given its size(), that could only have
         * occurred due to interference.  This allows the inner loop
         * of forEach to run without any further checks, and
         * simplifies lambda-resolution. While this does entail a
         * number of checks, note that in the common case of
         * list.stream().forEach(a), no checks or other computation
         * occur anywhere other than inside forEach itself.  The other
         * less-often-used methods cannot take advantage of most of
         * these streamlinings.
         */

        private final ArrayList<E> list;
        private int index; // current index, modified on advance/split
        private int fence; // -1 until used; then one past last index
        private int expectedModCount; // initialized when fence set

        /** Create new spliterator covering the given  range */
        ArrayListSpliterator(ArrayList<E> list, int origin, int fence,
                             int expectedModCount) {
            this.list = list; // OK if null unless traversed
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize fence to size on first use
            int hi; // (a specialized variant appears in method forEach)
            ArrayList<E> lst;
            if ((hi = fence) < 0) {
                if ((lst = list) == null)
                    hi = fence = 0;
                else {
                    expectedModCount = lst.modCount;
                    hi = fence = lst.size;
                }
            }
            return hi;
        }

        public ArrayListSpliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null : // divide range in half unless too small
                new ArrayListSpliterator<E>(list, lo, index = mid,
                                            expectedModCount);
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null)
                throw new NullPointerException();
            int hi = getFence(), i = index;
            if (i < hi) {
                index = i + 1;
                @SuppressWarnings("unchecked") E e = (E)list.elementData[i];
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi, mc; // hoist accesses and checks from loop
            ArrayList<E> lst; Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((lst = list) != null && (a = lst.elementData) != null) {
                if ((hi = fence) < 0) {
                    mc = lst.modCount;
                    hi = lst.size;
                }
                else
                    mc = expectedModCount;
                if ((i = index) >= 0 && (index = hi) <= a.length) {
                    for (; i < hi; ++i) {
                        @SuppressWarnings("unchecked") E e = (E) a[i];
                        action.accept(e);
                    }
                    if (lst.modCount == mc)
                        return;
                }
            }
            throw new ConcurrentModificationException();
        }

        public long estimateSize() {
            return (long) (getFence() - index);
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        // figure out which elements are to be removed
        // any exception thrown from the filter predicate at this stage
        // will leave the collection unmodified
        int removeCount = 0;
        final BitSet removeSet = new BitSet(size);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            @SuppressWarnings("unchecked")
            final E element = (E) elementData[i];
            if (filter.test(element)) {
                removeSet.set(i);
                removeCount++;
            }
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        // shift surviving elements left over the spaces left by removed elements
        final boolean anyToRemove = removeCount > 0;
        if (anyToRemove) {
            final int newSize = size - removeCount;
            for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
                i = removeSet.nextClearBit(i);
                elementData[j] = elementData[i];
            }
            for (int k=newSize; k < size; k++) {
                elementData[k] = null;  // Let gc do its work
            }
            this.size = newSize;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            modCount++;
        }

        return anyToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            elementData[i] = operator.apply((E) elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) elementData, 0, size, c);
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }
}
