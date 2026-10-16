package com.learn.todo.collection;

/**
 * {@link MyList} 的顺序表实现 —— <b>本项目核心手写物，禁止 AI 代写。</b>
 *
 * <p>底层使用 {@code Object[]} 存储，元素连续存放，按下标随机访问 O(1)。
 *
 * <p><b>已定实现口径（由用户裁决，不要再改）</b>
 * <ol>
 *   <li>扩容 <b>1.5 倍</b>：{@code newCapacity = old + (old >> 1)}，对齐 JDK {@code ArrayList}</li>
 *   <li>越界抛 JDK {@link IndexOutOfBoundsException}，不自研异常</li>
 *   <li>{@link #removeAt(int)} 后把腾出的末尾槽位置 {@code null} —— JDK 原话是
 *       {@code "clear to let GC do its work"}；不清会让被删对象无法回收，并留下「幽灵元素」隐患</li>
 *   <li>允许 {@code null} 元素；{@code indexOf(null)} 返回首个 null 的下标（null-aware），
 *       因此 {@code remove(null)} 命中并返回 {@code true}</li>
 *   <li>{@link #clear()} 换新数组（代价：已扩容的容量被丢弃，之后要重新扩容）</li>
 * </ol>
 *
 * <p><b>必踩的坑（写代码时逐条对照）</b>
 * <ul>
 *   <li>{@code old + (old >> 1)} 在 {@code old <= 1} 时<b>不增长</b>（1 → 1）。而
 *       {@code new MyArrayList(0)} 是合法的，所以扩容必须用 {@code minCapacity} 兜底，
 *       否则会无限循环或下标越界。JDK 的补丁正是
 *       {@code if (newCapacity < minCapacity) newCapacity = minCapacity;}</li>
 *   <li>删「最后一个元素」时内层搬家 0 个元素，但末尾槽位就是被删对象，<b>一样要置 {@code null}</b></li>
 *   <li>{@code indexOf} 用 {@link java.util.Objects#equals}，不要写成 {@code element.equals(...)}：
 *       元素为 {@code null} 时会 NPE</li>
 *   <li>本类有<b>两个不同上界</b>：读/改/删是 {@code [0, size-1]}，插入是 {@code [0, size]}</li>
 * </ul>
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
        // TODO(你): 校验下标；取出旧值；把 (index, size) 整体前移一位；size--；把末尾槽位置 null
        throw new UnsupportedOperationException("TODO: 实现 removeAt(int)");
    }

    @Override
    public boolean remove(E element) {
        // TODO(你): 借助 indexOf 找到下标；命中则 removeAt 并返回 true；否则 false
        //          element 为 null 时按已定口径走（indexOf 是 null-aware，不会 NPE）
        throw new UnsupportedOperationException("TODO: 实现 remove(E)");
    }

    @Override
    public int indexOf(E element) {
        // TODO(你): 用 Objects.equals 做相等判定（null-aware），返回首个匹配下标，未命中返回 -1
        //          坑：别写成 element.equals(...)，element 为 null 时会 NPE
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
        //          口径已定：换新数组（见类注释）。新数组容量取多少由你定，先想清代价
        //          （容量退回初始值后，后续 add 要重新扩容）
        throw new UnsupportedOperationException("TODO: 实现 clear()");
    }

    /**
     * 确保底层数组至少能容纳 {@code minCapacity} 个元素，不够就扩容。
     *
     * <p><b>这是本项目最需要想清楚的方法。</b>口径已定（见类注释）：1.5 倍，
     * {@code newCapacity = old + (old >> 1)}，并用 {@code minCapacity} 兜底
     * （{@code old <= 1} 时上式不增长）。
     *
     * <p>还要能回答：扩容一次要拷贝多少个引用？均摊代价怎么算？
     */
    private void ensureCapacity(int minCapacity) {
        // TODO(你): 实现扩容
        throw new UnsupportedOperationException("TODO: 实现 ensureCapacity(int)");
    }

    /**
     * 下标校验。口径已定：抛 JDK {@link IndexOutOfBoundsException}（见类注释）。
     *
     * <p>注意本类有两个上界：{@link #get(int)} / {@link #set(int, Object)} /
     * {@link #removeAt(int)} 用 {@code [0, size-1]}；{@code add(int, E)} 用 {@code [0, size]}。
     * 单开一个只校验 {@code [0, size-1]} 的方法是不够的。
     */
    private void checkIndex(int index) {
        // TODO(你): 实现下标校验
        throw new UnsupportedOperationException("TODO: 实现 checkIndex(int)");
    }
}
