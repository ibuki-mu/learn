package com.learn.todo.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
 * <p><b>现状</b>：{@link MyArrayList} 尚未实现，本测试现在必然全红——
 * 这正是 M1 的第一个目标：让它全绿。
 *
 * <p><b>本文件只覆盖基本路径。</b>边界用例由你补进 {@link EdgeCases}，
 * 每条断言写清「你预期的值」。
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
     * 边界用例：<b>由你自己补充</b>，至少覆盖下面这些。
     *
     * <ul>
     *   <li>{@code get(-1)} / {@code get(size)} 抛 {@code IndexOutOfBoundsException}</li>
     *   <li>空表 {@code removeAt(0)} 的行为</li>
     *   <li>{@code add(int, E)} 在 {@code index == size} 与 {@code index == size + 1} 的差异</li>
     *   <li>{@code indexOf(null)} 与 {@code add(null)} 是否符合你在 {@link MyList} 声明的契约</li>
     *   <li>{@code new MyArrayList(-1)} 抛 {@code IllegalArgumentException}</li>
     *   <li>{@code clear()} 之后还能继续 {@code add}</li>
     * </ul>
     *
     * <p>提示：{@code assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));}
     */
    @Nested
    @DisplayName("边界用例（待你补充）")
    class EdgeCases {

        @Test
        @DisplayName("TODO: 用 assertThrows / assertEquals 补边界断言")
        void todo() {
            // TODO(你): 删掉这个占位测试，换成你设计的边界用例
        }
    }
}
