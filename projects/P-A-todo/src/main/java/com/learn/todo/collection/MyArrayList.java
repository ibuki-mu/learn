package com.learn.todo.collection;

import java.util.Objects;

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

    /**
     * 底层存储。逻辑长度为 {@link #size}，物理长度为 {@link #elements}{@code .length}。
     */
    private Object[] elements;

    /**
     * 逻辑元素个数。不变量：{@code 0 <= size <= elements.length}。
     */
    private int size;

    /**
     * 使用 {@link MyList#DEFAULT_CAPACITY} 作为初始容量。
     */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 指定初始容量。
     *
     * @param initialCapacity 若为负数，抛 {@link IllegalArgumentException}
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("列表容量不能为负数");
        }
        elements = new Object[initialCapacity];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** 包级测试接缝：只给同包测试断言扩容行为用，不在 {@link MyList} 契约里。 */
    int capacity() { return elements.length; }

    @Override
    public boolean add(E e) {
        ensureCapacity(size + 1);
        elements[size] = e;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);
        ensureCapacity(size + 1);
        if (index == size) {
            add(element);
        } else {
            for (int i = size - 1; i >= index; i--) {
                elements[i + 1] = elements[i];
            }
            size++;
            elements[index] = element;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkElementIndex(index);
        return (E) elements[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkElementIndex(index);
        Object old = elements[index];
        elements[index] = element;
        return (E) old;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E removeAt(int index) {
        checkElementIndex(index);
        Object old = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return (E) old;
    }

    @Override
    public boolean remove(E element) {
        // element 为 null 时按已定口径走（indexOf 是 null-aware，不会 NPE）
        int index = indexOf(element);
        if (index != -1) {
            removeAt(index);
            return true;
        }
        return false;
    }

    @Override
    public int indexOf(E element) {
        // 坑：别写成 element.equals(...)，element 为 null 时会 NPE
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i],element)) return i;
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public void clear() {
        size = 0;
        elements = new Object[MyList.DEFAULT_CAPACITY];
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
        if (minCapacity <= elements.length) return;
        int oldCapacity = elements.length;
        int newCapacity = Math.max(minCapacity, oldCapacity + (oldCapacity >> 1));
        Object[] newArr = new Object[newCapacity];
        System.arraycopy(elements, 0, newArr, 0, size);
        elements = newArr;
    }

    /**
     * 下标校验。口径已定：抛 JDK {@link IndexOutOfBoundsException}（见类注释）。
     *
     * <p>注意本类有两个上界：{@link #get(int)} / {@link #set(int, Object)} /
     * {@link #removeAt(int)} 用 {@code [0, size-1]}；{@code add(int, E)} 用 {@code [0, size]}。
     * 单开一个只校验 {@code [0, size-1]} 的方法是不够的。
     */
    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("索引超出范围");
        }
    }

    private void checkPositionIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("索引超出范围");
        }
    }
}
