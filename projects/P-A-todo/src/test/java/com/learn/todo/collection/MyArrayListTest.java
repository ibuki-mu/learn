package com.learn.todo.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link MyArrayList} 冒烟测试（JUnit 5）。
 *
 * <p>运行方式（三选一）：
 * <ul>
 *   <li>IDEA：点类名或单个方法左侧的绿色三角</li>
 *   <li>命令行：{@code mvn test}（记得先 {@code source ../../.toolchain/env.sh}）</li>
 *   <li>只跑本类：{@code mvn test -Dtest=MyArrayListTest}</li>
 * </ul>
 *
 * <p><b>覆盖范围</b>：前半部分是基本路径；{@link EdgeCases} 是边界用例，
 * 断言「非法输入必须被拒绝，且拒绝前后 size 与内容不变」。
 */
@DisplayName("MyArrayList 冒烟测试")
class MyArrayListTest {

    @Nested
    @DisplayName("基本增读")
    class BasicAddAndGet {

        @Test
        @DisplayName("新表 size 为 0 且 isEmpty")
        void newListIsEmpty() {
            MyList<String> list = new MyArrayList<>();
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
        }

        @Test
        @DisplayName("尾部追加后顺序与 size 正确")
        void addAppendsInOrder() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");
            list.add("c");

            assertEquals(3, list.size());
            assertEquals("a", list.get(0));
            assertEquals("b", list.get(1));
            assertEquals("c", list.get(2));
            assertFalse(list.isEmpty());
        }
    }

    @Nested
    @DisplayName("指定位置插入")
    class AddAtIndex {

        @Test
        @DisplayName("中间插入后原有元素后移")
        void insertInMiddleShifts() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("c");
            list.add(1, "b");

            assertEquals(3, list.size());
            assertEquals("a", list.get(0));
            assertEquals("b", list.get(1));
            assertEquals("c", list.get(2));
        }

        @Test
        @DisplayName("index == size 等价于尾部追加")
        void insertAtSizeAppends() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add(1, "b");

            assertEquals(2, list.size());
            assertEquals("b", list.get(1));
        }

        @Test
        @DisplayName("头部插入后 get(0) 为新元素")
        void insertAtHead() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add(0, "z");

            assertEquals("z", list.get(0));
            assertEquals("a", list.get(1));
        }
    }

    @Nested
    @DisplayName("删除与移位")
    class Remove {

        @Test
        @DisplayName("removeAt 返回被删元素且后续元素前移")
        void removeAtShifts() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");
            list.add("c");

            assertEquals("b", list.removeAt(1));
            assertEquals(2, list.size());
            assertEquals("c", list.get(1));
        }

        @Test
        @DisplayName("remove(E) 命中返回 true，未命中返回 false")
        void removeByElement() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");

            assertTrue(list.remove("a"));
            assertEquals(1, list.size());
            assertFalse(list.remove("不存在"));
        }
    }

    @Nested
    @DisplayName("查找")
    class Lookup {

        @Test
        @DisplayName("indexOf 返回首个匹配下标，未命中返回 -1")
        void indexOfReturnsFirstMatch() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");
            list.add("a");

            assertEquals(0, list.indexOf("a"));
            assertEquals(1, list.indexOf("b"));
            assertEquals(-1, list.indexOf("zzz"));
        }

        @Test
        @DisplayName("contains 命中与未命中")
        void containsWorks() {
            MyList<String> list = new MyArrayList<>();
            list.add("b");

            assertTrue(list.contains("b"));
            assertFalse(list.contains("zzz"));
        }
    }

    @Nested
    @DisplayName("覆盖与清空")
    class SetAndClear {

        @Test
        @DisplayName("set 返回旧值且不改变 size")
        void setReturnsOldValue() {
            MyList<String> list = new MyArrayList<>();
            list.add("old");

            assertEquals("old", list.set(0, "new"));
            assertEquals("new", list.get(0));
            assertEquals(1, list.size());
        }

        @Test
        @DisplayName("clear 后 size 归零")
        void clearResetsSize() {
            MyList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");
            list.clear();

            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
        }
    }

    @Nested
    @DisplayName("扩容")
    class Growth {

        /**
         * 初始容量 4、加到 13 个元素，至少触发 2 次扩容。
         *
         * <p>断言只检查「数据完整性」，<b>不锁死倍数</b>——倍数是你的设计决策，
         * 但你必须能解释它（见 README「待办」）。
         */
        @Test
        @DisplayName("扩容后所有元素不丢失")
        void growthKeepsAllElements() {
            MyArrayList<String> list = new MyArrayList<>(4);
            for (int i = 0; i < 13; i++) {
                list.add("v" + i);
            }

            assertEquals(13, list.size());
            for (int i = 0; i < 13; i++) {
                assertEquals("v" + i, list.get(i), "第 " + i + " 个元素在扩容后应保持原位");
            }
        }
    }

    /**
     * 边界用例。断言形状统一的用参数化；「拒绝后状态不变」是每条的核心。
     */
    @Nested
    @DisplayName("边界用例")
    class EdgeCases {

        /** 单元素列表，用于验证非法下标不会改动状态。 */
        private MyArrayList<String> oneElement() {
            MyArrayList<String> list = new MyArrayList<>();
            list.add("a");
            return list;
        }

        @ParameterizedTest(name = "get({0})")
        @ValueSource(ints = {-1, 1, 2})
        void getRejectsAndKeepsState(int index) {
            MyArrayList<String> list = oneElement();
            assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.get(index));
            assertEquals(1, list.size());
            assertEquals("a", list.get(0));
        }

        @ParameterizedTest(name = "set({0}, x)")
        @ValueSource(ints = {-1, 1, 2})
        void setRejectsAndKeepsState(int index) {
            MyArrayList<String> list = oneElement();
            assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.set(index, "x"));
            assertEquals(1, list.size());
            assertEquals("a", list.get(0));
        }

        @ParameterizedTest(name = "removeAt({0})")
        @ValueSource(ints = {-1, 1, 2})
        void removeAtRejectsAndKeepsState(int index) {
            MyArrayList<String> list = oneElement();
            assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.removeAt(index));
            assertEquals(1, list.size());
            assertEquals("a", list.get(0));
        }

        @Test
        @DisplayName("空表 removeAt(0) 抛契约异常")
        void emptyRemoveAtRejects() {
            MyArrayList<String> list = new MyArrayList<>();
            assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.removeAt(0));
            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("满表 removeAt(size) 不能泄漏 ArrayIndexOutOfBoundsException")
        void fullListRemoveAtSizeRejects() {
            MyArrayList<String> list = new MyArrayList<>(2);
            list.add("a");
            list.add("b");
            assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.removeAt(2));
            assertEquals(2, list.size());
            assertEquals("a", list.get(0));
            assertEquals("b", list.get(1));
        }

        @Test
        @DisplayName("add(size) 合法，追加到末尾")
        void addAtSizeAppends() {
            MyArrayList<String> list = oneElement();
            list.add(1, "b");
            assertEquals(2, list.size());
            assertEquals("a", list.get(0));
            assertEquals("b", list.get(1));
        }

        @Test
        @DisplayName("add(0) 插入头部")
        void addAtZeroInserts() {
            MyArrayList<String> list = oneElement();
            list.add(0, "z");
            assertEquals(2, list.size());
            assertEquals("z", list.get(0));
            assertEquals("a", list.get(1));
        }

        @ParameterizedTest(name = "add({0}, x)")
        @ValueSource(ints = {-1, 2})
        void addOutOfRangeRejects(int index) {
            MyArrayList<String> list = oneElement();
            assertThrowsExactly(IndexOutOfBoundsException.class, () -> list.add(index, "x"));
            assertEquals(1, list.size());
            assertEquals("a", list.get(0));
        }

        @Test
        @DisplayName("null 可存、可查、可删，且删除首个")
        void nullIsAllowedAndNullAware() {
            MyArrayList<String> list = new MyArrayList<>();
            list.add(null);
            list.add("a");
            list.add(null);

            assertEquals(0, list.indexOf(null));
            assertTrue(list.contains(null));

            assertTrue(list.remove(null));
            assertEquals(2, list.size());
            assertEquals("a", list.get(0));
            assertNull(list.get(1));
        }

        @Test
        @DisplayName("默认容量 10，装满后扩到 15")
        void defaultCapacityGrowsTo15() {
            MyArrayList<String> list = new MyArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add("v" + i);
            }
            assertEquals(10, list.capacity());
            list.add("v10");
            assertEquals(15, list.capacity());
            assertEquals(11, list.size());
        }

        @Test
        @DisplayName("容量 0 起：1 → 2 → 3 → 4 → 6（minCapacity 兜底）")
        void zeroCapacityBackstop() {
            MyArrayList<String> list = new MyArrayList<>(0);
            int[] expected = {1, 2, 3, 4, 6};
            for (int i = 0; i < expected.length; i++) {
                list.add("v" + i);
                assertEquals(expected[i], list.capacity(), "第 " + (i + 1) + " 次 add 后容量");
            }
        }

        @Test
        @DisplayName("容量 5 加 6 个扩到 7")
        void capacityFiveGrowsToSeven() {
            MyArrayList<String> list = new MyArrayList<>(5);
            for (int i = 0; i < 6; i++) {
                list.add("v" + i);
            }
            assertEquals(7, list.capacity());
        }

        @Test
        @DisplayName("负数容量抛 IllegalArgumentException")
        void negativeCapacityRejected() {
            assertThrowsExactly(IllegalArgumentException.class, () -> new MyArrayList<String>(-1));
        }

        @Test
        @DisplayName("clear 后可继续 add")
        void clearThenAdd() {
            MyArrayList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");
            list.clear();
            assertEquals(0, list.size());
            assertTrue(list.isEmpty());
            list.add("c");
            assertEquals(1, list.size());
            assertEquals("c", list.get(0));
        }

        @Test
        @DisplayName("删首个匹配，保留后面的同名元素")
        void removeDeletesFirstMatch() {
            MyArrayList<String> list = new MyArrayList<>();
            list.add("a");
            list.add("b");
            list.add("a");
            assertTrue(list.remove("a"));
            assertEquals(2, list.size());
            assertEquals("b", list.get(0));
            assertEquals("a", list.get(1));
        }

        @Test
        @DisplayName("未命中返回 false 且不改状态")
        void removeMissingReturnsFalse() {
            MyArrayList<String> list = oneElement();
            assertFalse(list.remove("zzz"));
            assertEquals(1, list.size());
            assertEquals("a", list.get(0));
        }
    }
}
