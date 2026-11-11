# ROADMAP — Java 后端一年路线图

> **单一真相源**：一年路线与项目串联只在本文件定义。
> 关联：`AGENTS.md`（工作规则）、[`KNOWLEDGE-GRAPH.md`](KNOWLEDGE-GRAPH.md)（能力状态）、[`HANDOFF.md`](HANDOFF.md)（会话交接）。
> 基线：2026-01，第 0 周。每周投入 20–25 小时。

## 目标与验收

**目标**：2026 年底具备初级 Java 后端开发工程师的交付能力（主攻应用层）。

**最终验收清单**（第四阶段关门条件）

- [ ] 独立完成带权限控制的 REST API
- [ ] 能解释 HTTP 从请求到响应的完整链路
- [ ] Docker 部署 + CI/CD 跑通
- [ ] 设计带缓存、消息队列的分布式架构并说明取舍
- [ ] 调用大模型 API 搭出 RAG / Agent
- [ ] 面试中讲清项目选型与 trade-off

## 四阶段

| 阶段 | 月份 | 主题 | 阶段产出 |
|---|---|---|---|
| 一 | 1–3 | Java 深化 + 计算机基础 | 纯 Java 命令行工具（含持久化 + 测试） |
| 二 | 4–7 | Web 后端 + 数据库 | 带登录/权限/CRUD 的 Web 系统，部署上云 |
| 三 | 8–10 | 工程化 + 分布式 + AI 应用 | 带 AI 能力的后端服务（RAG/Agent） |
| 四 | 11–12 | 冲刺与打磨 | 重构、系统设计文档、模拟面试、博客 |

各阶段详细知识点见 [`HANDOFF.md`](HANDOFF.md) 第 4 节。

## 项目串联（当前执行口径）

三个项目**由小到大、同域递进**，最后一棒复用第一棒的领域，用于看清每一层各自解决了什么问题。

| 编号 | 项目 | 技术栈 | 主补缺口 | 时间盒 | 状态 |
|---|---|---|---|---|---|
| **P-A** | 待办管理器（命令行） | 纯 Java，**禁用框架** | 集合、泛型、异常、IO、测试 | 3 周 | **M1 进行中**（`MyArrayList` 待用户手写） |
| **P-C** | 图书借阅系统（命令行） | 纯 JDBC + MySQL，**禁用 Spring** | **JDBC（最大断层）**、事务、JOIN、索引 | 3.5 周 | 待定义 |
| **P-B** | 任务管理系统（Web） | Spring Boot + MyBatis + MySQL | 分层、DI、REST、Git、Mock 测试 | 7 周 | 待定义 |

总计约 13 周 ≈ 3 个月。项目定义分别在 `projects/<编号>-<名称>.md`。

**同域递进的杠杆**：P-A（内存/文件）→ P-C（JDBC 手写持久化）→ P-B（框架托管）。同一个"待办/任务"域三次实现，差异即各层的价值。

**P-C 定为图书借阅而非通讯录的原因**：通讯录是单表零事务，练不到 JOIN、事务与索引；图书借阅的"借书 = 扣库存 + 插记录"让事务成为**必需品**。

## 固定节奏

- **每日**：30 min 原理（源码/算法）+ 90 min 项目编码 + 30 min 复盘输出。
- **每周**：1 天**无 AI 手写核心**；1 次代码审查；1 次 AI 模拟面试或系统设计；1 篇技术复盘。
- **三轨配比**：交付轨 60% / AI 应用轨 25% / 基本功轨 15%。

## 阶段关门三件套

每个项目/阶段结束必须依次完成：

1. 自查清单逐条打勾（清单在项目定义文件里）
2. 用户确认
3. 原子 commit（消息格式 `feat(P-A): ...` / `docs(roadmap): ...`）
4. 同步更新 [`KNOWLEDGE-GRAPH.md`](KNOWLEDGE-GRAPH.md) 的状态列

## 环境与开局阻塞项

**已就绪**

| 项 | 版本 | 说明 |
|---|---|---|
| git | 2.56.0 | 仓库已 `git init`，本地 user.name/email 已配 |
| JDK | Zulu 21.0.12.1 LTS (`Zulu21.52+203-CA`) | 免 root 安装于 `.toolchain/jdk21` |
| Maven | 3.9.16 | 免 root 安装于 `.toolchain/maven`；本地仓库固定在 `.toolchain/m2`（不写 `~/.m2`） |
| IDE | IntelliJ IDEA 2026.2 | SDK 已注册为 `zulu-21`，指向 `.toolchain/` |

**工具链使用方式**（每个新 shell 需先激活）

```bash
source .toolchain/env.sh   # JAVA_HOME + Maven + 工作区本地仓库，并自检版本
```

`env.sh` 通过 `MAVEN_ARGS` 把 `-s .toolchain/settings.xml` 传给每个 `mvn` 调用。**不要依赖 `~/.m2`**。

> `.toolchain/` 已在 `.gitignore` 中，不随仓库分发。换机器需重装：
> - JDK：从 `https://cdn.azul.com/zulu/bin/` 取 `zulu21.*-ca-jdk21*-linux_x64.tar.gz`，解压后建软链 `jdk21`
> - Maven：从 `https://dlcdn.apache.org/maven/maven-3/<版本>/binaries/` 取 `-bin.tar.gz`，解压后建软链 `maven`

**未就绪 / 阻塞**

| 项 | 状态 | 影响 | 处理 |
|---|---|---|---|
| `mysql` | ❌ | P-C 无法开工 | 第 5 周前解决（用户已决定到时再议） |
| Docker | ❌ | 第三阶段需要 | 到时再议 |
| **磁盘可用 1.8G（95% 已用）** | ⚠️ | 当前 `.toolchain/m2` 仅 25MB（JUnit 5 全套）；后续加 Mockito/覆盖率插件 + P-C/P-B 依赖会持续增长 | 用户已自行清理；如后续吃紧，优先清 `.toolchain/m2` 中无用依赖与 `target/` |

## 当前状态

- **在途**：P-A 已转为 Maven 工程（`pom.xml` + JUnit 5），**代码尚未实现**。
- **已验证**：`mvn test` 能发现并执行 13 个测试，全部因 `MyArrayList` 构造器为 TODO 而失败（预期状态）。
- **下一步**：实现 `MyArrayList` 让 [`MyArrayListTest`](../projects/P-A-todo/src/test/java/com/learn/todo/collection/MyArrayListTest.java) 全绿。
- **不再讨论**：项目选型、技术栈、时间盒、构建方式（已定 Maven）。

