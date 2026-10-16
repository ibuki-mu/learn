# P-A — 待办事项管理器（纯 Java 命令行）

> 项目定义见 [`projects/P-A-todo.md`](../P-A-todo.md)。
> **约束**：禁用 Spring / MyBatis / 任何第三方持久化或集合库。集合、异常体系、序列化必须手写。

## 环境

```bash
source ../../.toolchain/env.sh   # 激活 Zulu JDK 21（JAVA_HOME + PATH）
```

## 目录约定

```
pom.xml                          Maven 构建（唯一构建入口）
mvnw / .mvn/                     Maven Wrapper（保证版本一致）
src/main/java/com/learn/todo/
├── collection/   自研集合：MyList / MyArrayList / MyHashMap   ← 本项目核心，手写
├── model/        领域模型：Todo、Priority                    ← 手写
├── exception/    业务异常体系：TodoException 及子类            ← 手写
├── storage/      文件格式编解码                               ← 手写
└── cli/          命令行入口与参数解析                          ← 手写
src/test/java/com/learn/todo/   JUnit 5 测试
target/                         Maven 编译产物（已忽略，不入库）
```

## 构建与运行

先激活工具链（每个新 shell 都要做）：

```bash
source ../../.toolchain/env.sh   # JAVA_HOME + Maven + 工作区本地仓库
```

常用命令（**在 `projects/P-A-todo` 下执行**）：

```bash
mvn test                         # 编译 + 跑全部测试
mvn test -Dtest=MyArrayListTest  # 只跑指定测试类
mvn test -Dtest='MyArrayListTest$Growth'   # 只跑某个 @Nested 分组
mvn clean test                   # 清掉 target/ 重来（改pom或依赖异常时用）
mvn -q compile                   # 静默模式，只看编译错误
```

**IDEA 里**：直接点类名或方法左侧的绿色三角；或 `Ctrl+Shift+F10`。

### 本地仓库为什么不写 `~/.m2`

`env.sh` 通过 `MAVEN_ARGS` 把 `-s .toolchain/settings.xml` 传给每个 `mvn` 调用，`settings.xml` 里把 `localRepository` 指向 `.toolchain/m2`。原因：磁盘余量紧张，且便于随工作区整体删除。

> **注意**：仓库自带 `./mvnw`（Maven Wrapper），但它会把 Maven 发行版下到 `~/.m2/wrapper`，与 `.toolchain/maven` 重复 9MB。**优先用 `mvn`（经 `env.sh` 激活）**；`./mvnw` 只作为换机器/CI 的兜底。

### 为什么用 Maven 而不是裸 javac

裸 javac 有个值得知道的事实，Maven 全都替你做了：**`javac` 是文件路径驱动，`java -cp` 是 classpath 驱动**。IDEA 和 Maven 把这个过程隐藏了，这也是"底层被框架掩盖"的一种形式。你可以偶尔手跑一次感受它：

```bash
javac -encoding UTF-8 -d /tmp/pA-out $(find src/main/java -name '*.java')
java -cp /tmp/pA-out com.learn.todo.cli.Main
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

- `MyArrayList` 扩容一次要拷贝多少个引用？均摊代价怎么推导？（口径已定 1.5 倍，推导仍由你写）
- `Todo.equals` 按 id 比还是按全部字段比？在什么场景下会出错？

## 已定实现口径

`MyArrayList` 的 5 条决策（扩容 1.5 倍、越界用 JDK 异常、末尾槽位置 `null`、null-aware `indexOf`、`clear` 换新数组）连同**必踩坑位**，统一记录在：

- [`MyArrayList` 类注释](src/main/java/com/learn/todo/collection/MyArrayList.java)
- [`P-A-todo.md`](../P-A-todo.md) §4

**此文件不再重复**（单一真相源）。
