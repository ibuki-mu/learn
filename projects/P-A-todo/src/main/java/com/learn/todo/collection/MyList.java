package com.learn.todo.collection;

/**
 * 手写顺序表契约。
 *
 * <p>刻意不实现 {@link java.util.Collection}：本项目的目的是亲手实现，
 * 不是复用 JDK 的抽象骨架。实现类禁止内部使用 {@code java.util.ArrayList} /
 * {@code HashMap} / {@code LinkedList}。
 *
 * <p>契约约定（实现必须遵守，测试会验证）：
 * <ul>
 *   <li>{@code size()} 永远等于逻辑元素个数，且 {@code 0 <= size() <= DEFAULT_CAPACITY 的扩展上限}</li>
 *   <li>所有按下标访问的方法，下标合法区间为 {@code [0, size()-1]}，越界行为见各方法说明</li>
 *   <li>{@code add(null)} 允许（本集合不禁止 null），但实现必须在文档中说明该选择</li>
 *   <li>元素顺序按插入先后保持</li>
 * </ul>
 *
 * @param <E> 元素类型
 */
public interface MyList<E> {

    /** 未指定容量时使用的初始容量。 */
    int DEFAULT_CAPACITY = 10;

    /**
     * 在尾部追加元素。
     *
     * @return 恒为 {@code true}（对齐 JDK 的 {@code List.add} 签名习惯，便于将来替换实现）
     */
    boolean add(E e);

    /**
     * 在指定位置插入元素，原有元素整体后移。
     *
     * @param index 合法区间 {@code [0, size()]}
     * @throws IndexOutOfBoundsException index 越界时
     */
    void add(int index, E element);

    /**
     * 读取指定位置元素。
     *
     * @param index 合法区间 {@code [0, size()-1]}
     * @throws IndexOutOfBoundsException index 越界时
     */
    E get(int index);

    /**
     * 覆盖指定位置元素。
     *
     * @param index 合法区间 {@code [0, size()-1]}
     * @return 被覆盖的旧元素
     * @throws IndexOutOfBoundsException index 越界时
     */
    E set(int index, E element);

    /**
     * 删除指定位置元素，后续元素整体前移，{@code size()} 减一。
     *
     * @param index 合法区间 {@code [0, size()-1]}
     * @return 被删除的元素
     * @throws IndexOutOfBoundsException index 越界时
     */
    E removeAt(int index);

    /**
     * 删除第一个与给定元素「相等」的元素。
     *
     * <p>相等判定：{@code Objects.equals(element, e)}，因此需要元素类型正确实现 equals。
     *
     * @return 命中并删除返回 {@code true}；未命中返回 {@code false}
     */
    boolean remove(E element);

    /**
     * 查询首个匹配元素的下标。
     *
     * @return 命中返回下标，未命中返回 {@code -1}
     */
    int indexOf(E element);

    /** 是否包含至少一个匹配元素。 */
    boolean contains(E element);

    /** 当前逻辑元素个数（不是底层数组长度）。 */
    int size();

    /** 是否没有任何元素，等价于 {@code size() == 0}。 */
    boolean isEmpty();

    /** 清空所有元素。清空后 {@code size() == 0}、{@code isEmpty() == true}。 */
    void clear();
}
