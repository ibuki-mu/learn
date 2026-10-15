package com.learn.todo.testing;

/**
 * 零依赖断言工具 —— 裸 javac 阶段替代 JUnit。
 *
 * <p>为什么自己写：<b>因为项目自查清单要求全仓库不含 {@code java.util.ArrayList}
 * / {@code HashMap}，用库来测自研集合会污染 grep 检查。</b>
 * 后续接入 Maven + JUnit 5 时，{@link #assertEquals} 的语义可平滑对应到
 * {@code Assertions.assertEquals(expected, actual)}。
 *
 * <p>失败不立即中断，而是收集全部失败后统一报告——一次跑完看到所有问题，
 * 比遇到第一个错就停更省时间。
 */
public final class Check {

    private static int passed;
    private static int failed;
    private static String context = "";

    private Check() {
    }

    /** 设置当前测试场景名，出现在失败信息里便于定位。 */
    public static void scenario(String name) {
        context = name;
    }

    /** 断言两个值相等（用 {@code Objects.equals} 语义，允许 null）。 */
    public static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual == null : expected.equals(actual)) {
            passed++;
        } else {
            failed++;
            report("期望 <" + expected + "> 实际 <" + actual + ">");
        }
    }

    /** 断言条件为真。 */
    public static void assertTrue(boolean condition) {
        if (condition) {
            passed++;
        } else {
            failed++;
            report("期望 true 实际 false");
        }
    }

    /**
     * 断言抛出指定类型的异常。
     *
     * @param expectedType 期望的异常类型
     * @param body         被测代码
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Runnable body) {
        try {
            body.run();
        } catch (Throwable t) {
            if (expectedType.isInstance(t)) {
                passed++;
            } else {
                failed++;
                report("期望抛出 " + expectedType.getSimpleName() + " 实际抛出 " + t.getClass().getSimpleName());
            }
            return;
        }
        failed++;
        report("期望抛出 " + expectedType.getSimpleName() + " 但没有抛出任何异常");
    }

    /**
     * 输出汇总并返回退出码：全部通过为 0，有失败为 1（便于脚本判断）。
     */
    public static int summary() {
        System.out.println();
        System.out.println("通过: " + passed + "，失败: " + failed);
        if (failed == 0) {
            System.out.println("全部通过。");
            return 0;
        }
        return 1;
    }

    private static void report(String detail) {
        System.out.println("  [失败] " + (context.isEmpty() ? "" : context + " — ") + detail);
    }
}
