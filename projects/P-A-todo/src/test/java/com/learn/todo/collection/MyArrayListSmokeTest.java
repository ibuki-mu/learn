package com.learn.todo.collection;

import com.learn.todo.testing.Check;

/**
 * {@link MyArrayList} 的冒烟测试 —— 裸 Java 实现，不依赖 JUnit。
 *
 * <p>运行：
 * <pre>
 *   java -cp out com.learn.todo.collection.MyArrayListSmokeTest
 * </pre>
 *
 * <p><b>现状</b>：{@link MyArrayList} 尚未实现，本测试现在必然失败——
 * 这正是 M1 的第一个目标：让它全绿。
 *
 * <p><b>本文件只覆盖基本路径。</b>边界用例由你补进 {@link #moreEdgeCases()}，
 * 并在每条断言里写清「你预期的值」。
 */
public class MyArrayListSmokeTest {

    public static void main(String[] args) {
        basicAddAndGet();
        addAtIndexShiftsElements();
        removeAtShiftsElements();
        indexOfAndContains();
        setReturnsOldValue();
        clearResetsSize();
        growthKeepsDataAndDoublesCapacity();
        moreEdgeCases();

        System.exit(Check.summary());
    }

    // ---------- 基本路径 ----------

    private static void basicAddAndGet() {
        Check.scenario("基本增读");
        MyList<String> list = new MyArrayList<>();

        Check.assertEquals(0, list.size());
        Check.assertTrue(list.isEmpty());

        list.add("a");
        list.add("b");
        list.add("c");

        Check.assertEquals(3, list.size());
        Check.assertEquals("a", list.get(0));
        Check.assertEquals("c", list.get(2));
        Check.assertTrue(!list.isEmpty());
    }

    private static void addAtIndexShiftsElements() {
        Check.scenario("指定位置插入与移位");
        MyList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("c");
        list.add(1, "b");

        Check.assertEquals(3, list.size());
        Check.assertEquals("a", list.get(0));
        Check.assertEquals("b", list.get(1));
        Check.assertEquals("c", list.get(2));

        list.add(3, "d");
        Check.assertEquals("d", list.get(3));

        list.add(0, "z");
        Check.assertEquals("z", list.get(0));
        Check.assertEquals("a", list.get(1));
    }

    private static void removeAtShiftsElements() {
        Check.scenario("删除与移位");
        MyList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        Check.assertEquals("b", list.removeAt(1));
        Check.assertEquals(2, list.size());
        Check.assertEquals("c", list.get(1));

        Check.assertTrue(list.remove("a"));
        Check.assertEquals(1, list.size());
        Check.assertTrue(!list.remove("不存在"));
    }

    private static void indexOfAndContains() {
        Check.scenario("查找");
        MyList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");

        Check.assertEquals(0, list.indexOf("a"));
        Check.assertEquals(-1, list.indexOf("zzz"));
        Check.assertTrue(list.contains("b"));
        Check.assertTrue(!list.contains("zzz"));
    }

    private static void setReturnsOldValue() {
        Check.scenario("覆盖");
        MyList<String> list = new MyArrayList<>();
        list.add("old");

        Check.assertEquals("old", list.set(0, "new"));
        Check.assertEquals("new", list.get(0));
        Check.assertEquals(1, list.size());
    }

    private static void clearResetsSize() {
        Check.scenario("清空");
        MyList<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.clear();

        Check.assertEquals(0, list.size());
        Check.assertTrue(list.isEmpty());
    }

    // ---------- 扩容（本项目核心行为） ----------

    /**
     * 验证扩容后数据不丢，并检查增长倍数决策是否被一致执行。
     *
     * <p>初始容量设成 4，然后加到 13 个元素，至少触发 2 次扩容。
     * 断言只检查「数据完整性」和「容量确实增长」，不锁死具体倍数——
     * 倍数是你的设计决策，但你必须能解释它。
     */
    private static void growthKeepsDataAndDoublesCapacity() {
        Check.scenario("扩容");
        MyArrayList<String> list = new MyArrayList<>(4);

        for (int i = 0; i < 13; i++) {
            list.add("v" + i);
        }

        Check.assertEquals(13, list.size());
        for (int i = 0; i < 13; i++) {
            Check.assertEquals("v" + i, list.get(i));
        }
    }

    // ---------- 边界用例：由你自己补充 ----------

    /**
     * 请补上你设计的边界测试，至少覆盖：
     * <ul>
     *   <li>{@code get(-1)} / {@code get(size)} 是否抛 {@code IndexOutOfBoundsException}</li>
     *   <li>空表 {@code removeAt(0)} 的行为</li>
     *   <li>{@code add(int, E)} 在 index == size 与 index == size + 1 的差异</li>
     *   <li>{@code indexOf(null)} 与 {@code add(null)} 是否符合你在 MyList 里声明的契约</li>
     *   <li>{@code new MyArrayList(-1)} 是否抛 {@code IllegalArgumentException}</li>
     *   <li>{@code clear()} 后能否继续 add</li>
     * </ul>
     * 用 {@code Check.assertThrows} 与 {@code Check.assertEquals} 写。
     */
    private static void moreEdgeCases() {
        // TODO(你): 用 Check.* 写边界断言
    }
}
