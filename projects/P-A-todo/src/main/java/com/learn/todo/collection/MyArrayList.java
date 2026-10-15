package com.learn.todo.collection;

/**
 * {@link MyList} 的顺序表实现 —— <b>本项目核心手写物，禁止 AI 代写。</b>
 *
 * <p>底层使用 {@code Object[]} 存储，元素连续存放，按下标随机访问 O(1)。
 *
 * <p><b>你需要在实现中自己决策（README「待办」里有对应问题）：</b>
 * <ol>
 *   <li>扩容倍数取 2 还是 1.5，以及为什么</li>
 *   <li>越界抛 JDK 的 {@code IndexOutOfBoundsException} 还是自研异常</li>
 *   <li>删除元素后是否要把末尾槽位置为 {@code null}（想清楚为什么 JDK 要这么做）</li>
 * </ol>
 */
public class MyArrayList<E> implements MyList<E> {

    /** 底层存储。逻辑长度为 {@link #size}，物理长度为 {@link #elements}{@code .length}。 */
    private Object[] elements;

    /** 逻辑元素个数。不变量：{@code 0 <= size <= elements.length}。 */
    private int size;

    /** 使用 {@link MyList#DEFAULT_CAPACITY} 作为初始容量。 */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 指定初始容量。
     *
     * @param initialCapacity 若为负数，抛 {@link IllegalArgumentException}
     */
    public MyArrayList(int initialCapacity) {
        // TODO(你): 校验 initialCapacity 并分配底层数组，size 置 0
        throw new UnsupportedOperationException("TODO: 实现 MyArrayList 构造器");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        // TODO(你): 先确保容量够（不够则 grow），再写入 elements[size]，最后 size++
        throw new UnsupportedOperationException("TODO: 实现 add(E)");
    }

    @Override
    public void add(int index, E element) {
        // TODO(你): 校验 index ∈ [0, size]；确保容量；把 [index, size) 整体后移一位；写入；size++
        throw new UnsupportedOperationException("TODO: 实现 add(int, E)");
    }

    @Override
    public E get(int index) {
        // TODO(你): 校验 index ∈ [0, size-1]；强转返回（注意 unchecked cast 的位置）
        throw new UnsupportedOperationException("TODO: 实现 get(int)");
    }

    @Override
    public E set(int index, E element) {
        // TODO(你): 校验下标；取出旧值；覆盖；返回旧值
        throw new UnsupportedOperationException("TODO: 实现 set(int, E)");
    }

    @Override
    public E removeAt(int index) {
        // TODO(你): 校验下标；取出旧值；把 (index, size) 整体前移一位；size--；处理末尾残留引用
        throw new UnsupportedOperationException("TODO: 实现 removeAt(int)");
    }

    @Override
    public boolean remove(E element) {
        // TODO(你): 借助 indexOf 找到下标；命中则 removeAt 并返回 true；否则 false
        //          注意 element 为 null 时 indexOf 的行为
        throw new UnsupportedOperationException("TODO: 实现 remove(E)");
    }

    @Override
    public int indexOf(E element) {
        // TODO(你): 用 Objects.equals 做相等判定，返回首个匹配下标，未命中返回 -1
        throw new UnsupportedOperationException("TODO: 实现 indexOf(E)");
    }

    @Override
    public boolean contains(E element) {
        // TODO(你): 基于 indexOf 实现
        throw new UnsupportedOperationException("TODO: 实现 contains(E)");
    }

    @Override
    public void clear() {
        // TODO(你): 清空所有槽位引用（不要让 GC 无法回收），size 置 0
        //          想清楚：是把数组换成一个新的，还是原地清？
        throw new UnsupportedOperationException("TODO: 实现 clear()");
    }

    /**
     * 确保底层数组至少能容纳 {@code minCapacity} 个元素，不够就扩容。
     *
     * <p><b>这是本项目最需要想清楚的方法。</b>请自己决定：
     * 扩容倍数、新容量如何计算、以及扩容一次的真实代价（要拷贝多少个引用）。
     */
    private void ensureCapacity(int minCapacity) {
        // TODO(你): 实现扩容
        throw new UnsupportedOperationException("TODO: 实现 ensureCapacity(int)");
    }

    /**
     * 下标校验。是否抽成私有方法、抛什么异常，由你决定。
     */
    private void checkIndex(int index) {
        // TODO(你): 实现下标校验
        throw new UnsupportedOperationException("TODO: 实现 checkIndex(int)");
    }
}
