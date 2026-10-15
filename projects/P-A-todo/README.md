# P-A — 待办事项管理器（纯 Java 命令行）

> 项目定义见 [`../../docs/P-A-todo.md`](../../docs/P-A-todo.md) → 实际路径 [`projects/P-A-todo.md`](../P-A-todo.md)。
> **约束**：禁用 Spring / MyBatis / 任何第三方持久化或集合库。集合、异常体系、序列化必须手写。

## 环境

```bash
source ../../.toolchain/env.sh   # 激活 Zulu JDK 21（JAVA_HOME + PATH）
```

## 目录约定

```
src/main/java/com/learn/todo/
├── collection/   自研集合：MyList / MyArrayList / MyHashMap   ← 本项目核心，手写
├── model/        领域模型：Todo、Priority                    ← 手写
├── exception/    业务异常体系：TodoException 及子类            ← 手写
├── storage/      文件格式编解码                               ← 手写
└── cli/          命令行入口与参数解析                          ← 手写
src/test/java/com/learn/todo/
├── testing/      Check 断言工具（零依赖，替代 JUnit）
└── collection/   MyArrayListSmokeTest 等
out/              编译产物（已忽略，不入库）
```

> `src/test/.../testing/Check.java` 是**自研断言工具**。为什么不用 JUnit：裸 javac 阶段挂 JUnit 需要手动管 jar 与 classpath，且用库来测自研集合不如顺手把断言也写一遍。接入 Maven 后可平滑换成 JUnit 5。

## 构建与运行（M1 阶段：裸 javac，无 Maven）

`javac` 是**文件路径驱动**，不是包名驱动——所以下面的 `-d` 输出目录 + `find` 把所有 `.java` 一起喂给它，是裸用 javac 的标准做法。classpath（`-cp`）告诉 JVM 去哪找已编译的类，这正是框架平时替你做的事。

```bash
cd projects/P-A-todo

# 编译主程序（首次运行会创建 out/）
javac -encoding UTF-8 -d out $(find src/main/java -name '*.java')

# 编译测试（测试依赖主程序，所以要 -cp out）
javac -encoding UTF-8 -cp out -d out $(find src/test/java -name '*.java')

# 运行集合冒烟测试（M1 第一阶段目标：全绿，退出码 0）
java -cp out com.learn.todo.collection.MyArrayListSmokeTest

# 运行主程序（M1 后段才有 Main）
java -cp out com.learn.todo.cli.Main
```

## 自查命令

```bash
# 不得使用 JDK 集合三件套（只查 import 与实例化，注释里的说明文字不算违规）
grep -rnE '^[[:space:]]*import[[:space:]]+java\.util\.(ArrayList|HashMap|LinkedList)|new[[:space:]]+(java\.util\.)?(ArrayList|HashMap|LinkedList)[[:space:]]*[(<]' src/
```

## M1 任务清单

按难度递进，**前一项跑绿再开下一项**：

- [ ] `MyArrayList<E>` 实现 `MyList<E>` 全部方法 → 冒烟测试全绿
- [ ] `MyArrayList` 扩容逻辑 + 自己的边界测试（越界、空表删除、扩容后数据不丢）
- [ ] `Priority` 枚举 + `Todo` 领域模型（含 `equals`/`hashCode` 的取舍思考）
- [ ] `MyHashMap<K,V>` → 自己的冒烟测试
- [ ] 内存版 CLI：add / list / done / delete
- [ ] `MyHashMap` 树化阈值实验：造哈希冲突，证明 ≥8 且容量 ≥64 触发转树、退化阈值 6

## 待办（自己决策，别问 AI 要答案）

- `MyArrayList` 扩容选 2 倍还是 1.5 倍？写出你的理由与均摊代价推导。
- `Todo.equals` 按 id 比还是按全部字段比？在什么场景下会出错？
- 越界时抛 JDK 的 `IndexOutOfBoundsException` 还是自研异常？两者各自代价是什么？
