# HANDOFF — Java 后端一年成长计划（会话交接摘要）

> 用途：**可整份复制给任意新 Agent**。读完本文件即掌握此前全部结论，无需回溯历史。
> 优先级：`AGENTS.md`（工作区硬规则）> 本文件。
> 纪律：本文件**只留无法从仓库推导的事实**，其余给指针（见 §4）。真相源不在此重复定义。
> 状态日期：2026 目标年，一年期计划第 0 周，**P-A M1 进行中**（本文件更新于 2026-11-03，基线已复核）。
> 阅读顺序：本文件 → `AGENTS.md` → 按 §4 指针按需读。

---

## 1. 用户画像与目标

- **身份**：大学生（在校）。
- **目标**：2026 年底具备**初级 Java 后端开发工程师的交付能力**，主攻应用层。
- **周期**：一年，每周 20–25 小时。
- **外部环境**：企业普遍短工期，强制要求 AI 提效开发。
- **核心诉求**：① 短工期下用 AI 提效，但**不被 AI 架空**；② 扩大知识边界，强化 AI 应用能力；③ 不落下自身开发学习；④ 需要可执行的路线、个人知识图谱与具体起步方法。

---

## 2. 真实水平（自测结论，复测复用同一口径）

**总体判断**：不是零基础，是「**框架使用者**」，还不是「**工程师**」。能跑通 CRUD，但不理解框架下的机制；一旦脱离框架或出故障，能力骤降。

**已具备**：Java / Python 基础语法；Spring Boot + MyBatis 能写 CRUD；Maven 能建项目；写过简单 JUnit 断言测试。

**8 题自测逐项结果**

| # | 题目 | 用户答案 | 判定与补正 |
|---|---|---|---|
| 1 | ArrayList / LinkedList | ArrayList 基于数组，LinkedList 是双向链表；认为 LinkedList「已不推荐使用」 | 基本正确。「不推荐」表述不准确：准确说法是**大多数场景不如 ArrayList 和 ArrayDeque** |
| 2 | HashMap | 数组+链表+红黑树，冲突挂链表，超阈值转红黑树 | 主体正确。**漏**：容量、负载因子、扩容机制、转红黑树条件（链表长度 ≥8 **且** 容量 ≥64）、退化阈值 6 |
| 3 | 异常 | checked 编译期检查，unchecked 运行时异常，通常是逻辑错误 | 正确。**缺**：Throwable 体系、Error、业务异常设计 |
| 4 | 接口 / 抽象类 | 接口多继承、抽象类单继承；接口一般不做方法实现，抽象类可以 | 基本正确。**缺**：Java 8+ 接口 default / static / private 方法 |
| 5 | Maven | 会用 pom.xml 创建项目 | 会用不深。**缺**：生命周期、依赖冲突、多模块、scope |
| 6 | 单元测试 | 写过简单断言 | **缺**：JUnit 5 高级特性、Mockito、集成测试、覆盖率 |
| 7 | Git | 一般用 AI | 🔴 **危险依赖**，必须手动掌握：分支、合并、冲突、rebase、PR 流程 |
| 8 | JDBC / MySQL | 没实操过 JDBC，都是直接用 Spring Boot + MyBatis | ❌ **最大知识缺口**，底层被框架掩盖 |

**两大结论**

- **最大断层**：Spring Boot + MyBatis 与 JDBC / MySQL 之间。
- **最大风险**：Git、调试、代码生成依赖 AI。

---

## 3. 已锁定策略与原则（视为已确认决策）

1. **AI 定位**：导师、陪练、评审、模拟器、知识库 —— **不是答案机**。
2. **核心公式**：`AI 加速 = 高质量提问 × 主动验证 × 项目闭环 × 输出倒逼`。
3. **学习范式**：定目标 → AI 诊断缺口 → 最小项目 → 自己实现 → AI 评审 → 追问原理 → 输出复盘。
4. **AI 边界**：可交给 AI —— 样板代码、配置、CRUD、解释报错、生成测试、写文档、重构建议、知识地图；**必须自己来** —— 核心业务逻辑、架构设计、表结构、安全、事务、一致性、性能取舍、故障根因、每一行 AI 代码的解释与验证。
5. **提问纪律**：先自己想 30 分钟再问 AI；AI 产出必须自己敲、跑、改、验证；每周至少一天**无 AI 手写核心模块**。
6. **避坑清单**：不只复制代码；不信 AI 给的版本 / API / 性能数据；不上传敏感代码与数据；不用 AI 替代思考；不跳过低层；不贪多。
7. 三轨配比、每日 / 每周节奏、阶段关门三件套 → 见 [`ROADMAP.md`](ROADMAP.md)。

---

## 4. 真相源指针（本文件不重复定义）

| 主题 | 主文件 |
|---|---|
| 路线图、四阶段、项目串联与时间盒、节奏、阶段关门三件套、环境阻塞项 | [`docs/ROADMAP.md`](ROADMAP.md) |
| 能力状态、依赖图、Top 10 补强清单、修订记录 | [`docs/KNOWLEDGE-GRAPH.md`](KNOWLEDGE-GRAPH.md) |
| P-A 项目定义：功能范围、**必须手写清单**、已定技术决策（含 `MyArrayList` 口径）、里程碑、验收标准、自查清单 | [`projects/P-A-todo.md`](../projects/P-A-todo.md) |
| P-A 代码、构建命令、目录约定、自查 grep、M1 任务清单 | [`projects/P-A-todo/README.md`](../projects/P-A-todo/README.md) |
| 工作区硬规则（最高优先级） | [`AGENTS.md`](../AGENTS.md) |

---

## 5. 对 AI 的态度与危险依赖

- 会用 AI 辅助开发，但**担心过度依赖**。
- 🔴 **危险依赖**：Git（走 AI）、调试、底层知识（偏依赖框架与 AI）。
- 希望强化 AI **应用**能力，同时不落下自身开发学习。处理原则见 §3。

---

## 6. 在途状态（接手第一件事读这里）

**当前单元**：P-A M1 第一步 —— 用户手写 `MyArrayList`，让 13 个单测全绿。

- **工具链就绪**：Zulu JDK 21 + Maven 3.9.16 免 root 安装于 `.toolchain/`；本地仓库固定 `.toolchain/m2`（不写 `~/.m2`）；IDEA 2026.2 的 SDK 注册为 `zulu-21`。
- **环境坑（必读）**：`mvn` / `java` **不在新终端的 `PATH`**（`env.sh` 的 `export` 只对被 source 的那个 shell 生效）。用户尚未决定是否写入 `~/.bashrc`（不擅动工作区外文件）。用法：先 `source /home/ibukimu/learn/.toolchain/env.sh` 再 `mvn test`，或在 IDEA 内点绿三角。
- **测试基线（2026-11-10 复核）**：`mvn test` → `Tests run: 35, Failures: 0, Errors: 0`（23 条边界用例中 3 组为参数化）。
- **`MyArrayList` 实现口径已锁定，不再讨论**（真相源：`projects/P-A-todo.md` §4 + `MyArrayList` 类注释，内含 4 条必踩坑）：**1.5 倍**扩容（`old + (old >> 1)`，对齐 JDK）/ 越界抛 JDK `IndexOutOfBoundsException` / `removeAt` 后末尾槽位置 `null` / 允许 `null` 元素且 `indexOf` 为 null-aware / `clear()` 换新数组、容量取 `DEFAULT_CAPACITY`。
- **代码进度**：`MyArrayList` 已实现（含包级 `capacity()` 测试接缝），`mvn test` 35 个用例全绿；`model` / `exception` / `storage` / `cli` 四个包仍为空。核心代码由用户手写，AI 不代写。
- **git**：HEAD `0c2b56b`，工作树有未提交改动（`MyArrayList` 实现 + 边界测试 + 文档），当前分支 `master`；**feature 分支尚未创建**（计划 `feat/p-a-myarraylist`，由用户自己敲 —— Git 属危险依赖）。
- **复测观察点**：用户曾把 `array.length` 与 `size()` 混同（经查为误开接口 `MyList.java`，非认知错误）；「容量 vs 数量」「接口 vs 实现类字段」两处概念需在下次复测确认。

**下一步**：`MyArrayList` 已完成、35 个用例全绿 → 用户自行建 `feat/p-a-myarraylist` 分支并原子提交 → 进入 M1 下一项 `MyHashMap`。
**不要重新讨论**：项目选型、技术栈、时间盒、构建方式、已锁定的实现口径。

---

## 7. 提交索引

| commit | 内容 |
|---|---|
| `362d62a` | 建立路线图 / 知识图谱真相源 + P-A 定义 + `.gitignore` |
| `95a3ada` | 免 root 安装 Zulu JDK 21 到 `.toolchain/`，`env.sh` 实测通过 |
| `e0d5df8` | M1 脚手架：`MyList` 契约 + `MyArrayList` TODO 骨架 + 冒烟测试 + README |
| `49c239a` | 转 Maven 3.9.16 + JUnit 5（`@Nested` 13 测试）+ `.gitattributes` + `mvnw` wrapper |
| `b5096c6` | 会话 1 交接摘要 |
| `82503cf` | 修正交接摘要的提交索引 |
| `6f243e8` | 锁定 `MyArrayList` 实现口径与必踩坑位（文档 + 脚手架注释） |
| `0c2b56b` | 交接摘要收敛为指针式结构，接上 P-A M1 在途状态 |

---

## 8. 已拦的坑（防重复踩）

- 冒烟测试原用 `java.util.ArrayList` → 会污染「不用 JDK 集合」的自查 grep，改为 JUnit。
- 自查 grep 原样写会因**注释里的字样**假阳性 → 换成只查 `import` 与 `new` 的精确模式。
- `mvnw.cmd` 的 CRLF 被 git 转成 LF → `.gitattributes` 强制保留 CRLF。
- `./mvnw` 不可靠：需要 `JAVA_HOME`，且要写只读的 `~/.m2/wrapper` → 改用 `.toolchain/maven`。
- IDEA 自动把 `src/main` 与 `src/test` 识别成两个模块 → 在 IDEA 中直接打开 `projects/P-A-todo`。
- `old + (old >> 1)` 在 `old <= 1` 时**不增长**（1 → 1），扩容必须用 `minCapacity` 兜底。
- 下标校验只拦 `index > size` → 读/改/删在 `index == size` 时静默返回 `null` 或删掉末尾元素；必须拆成 `checkElementIndex`（`>= size`）与 `checkPositionIndex`（`> size`）。
- `remove(E)` 左移用倒序循环（`elements[i-1] = elements[i]`）→ 源被覆盖，删除后内容错乱；直接复用 `removeAt`。
- `clear()` 误写成 `new MyArrayList[]{...}` → 运行时 `ArrayStoreException`；必须是 `new Object[容量]`。

---

## 9. 给接手 Agent 的指令（强制执行）

1. 把用户视为「**Java 语法入门 + Spring Boot / MyBatis 会用，但底层与工程化薄弱**」的学习者。
2. **不要**重复教基础语法与 Hello World；**不要**重新讨论已确认的项目选型、技术栈、时间盒、构建方式与实现口径。
3. 优先从 **JDBC、MySQL、Git、JUnit + Mockito、并发基础、HTTP** 开始补强。
4. 坚持「**AI 辅助但不代写核心**」——核心逻辑、架构、表结构、安全、事务由用户自己写。
5. 用**短工期项目驱动**，限时交付；AI 全流程参与，用户负责核心与判断。
6. 定期用 §2 的同一套自测题复测，更新 [`KNOWLEDGE-GRAPH.md`](KNOWLEDGE-GRAPH.md)（v1 为开工前基线）。
7. 输出**可执行、不废话**，每条建议必须带**验收标准**。
8. 遵守 `AGENTS.md`：单一真相源、关键决策用户确认、阶段关门三件套（自查 → 用户确认 → 原子 commit）、必须中文输出。

---

## 10. 交接自检

- [x] 用户画像与目标
- [x] 真实水平与 8 题自测结果（复测复用同一口径）
- [x] 已锁定策略与原则（AI 边界、提问纪律、避坑）
- [x] 真相源指针（路线图 / 知识图谱 / 项目定义 / 构建说明 / 硬规则）
- [x] AI 依赖情况与危险依赖标注
- [x] 在途状态、环境坑、下一步与验收标准
- [x] 提交索引与已拦坑位
- [x] 给接手 Agent 的强制指令

---

## 11. 本仓库文件地图

| 文件 | 职责 |
|---|---|
| [`AGENTS.md`](../AGENTS.md) | 工作区硬规则（最高优先级） |
| [`docs/HANDOFF.md`](HANDOFF.md) | 本文件：会话交接摘要 |
| [`docs/ROADMAP.md`](ROADMAP.md) | **路线图主文件**：阶段、项目串联、节奏、关门流程、阻塞项 |
| [`docs/KNOWLEDGE-GRAPH.md`](KNOWLEDGE-GRAPH.md) | **知识图谱主文件**：节点状态、依赖图、Top 10、修订记录 |
| [`projects/P-A-todo.md`](../projects/P-A-todo.md) | **P-A 项目定义主文件**：WBS、里程碑、验收、自查清单 |
| [`projects/P-A-todo/README.md`](../projects/P-A-todo/README.md) | P-A 构建运行说明、目录约定、自查命令 |
| `projects/P-A-todo/src/` | P-A 代码（`collection/` 为核心手写物） |
