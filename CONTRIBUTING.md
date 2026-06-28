# 提交规范 (Conventional Commits)

本项目遵循 [Conventional Commits](https://www.conventionalcommits.org/) 规范。

## 格式

```
<type>(<scope>): <subject>

[body]

[footer]
```

## 示例

```bash
# 新功能
feat(controller): 添加对话记忆功能

# 修复
fix(config): 修复跨域配置不允许凭证

# 重构
refactor(controller): 优化 ChatClient 初始化逻辑

# 文档
docs(readme): 更新快速开始指南

# 杂项
chore(pom): 升级 Spring AI 版本至 1.0.0

# 附带详细说明
feat(controller): 百炼接口添加对话记忆

- generate 接口新增 chatId 参数
- 使用 ConcurrentHashMap 存储对话历史
- 支持多轮对话上下文
```

## Type 说明

| Type | 场景 |
|---|---|
| `feat` | 新功能 |
| `fix` | 修复 Bug |
| `refactor` | 重构（不改变外部行为） |
| `perf` | 性能优化 |
| `style` | 格式/命名调整（不影响逻辑） |
| `test` | 测试相关 |
| `docs` | 文档变更 |
| `chore` | 构建、依赖、CI、杂项配置 |
| `revert` | 回滚提交 |

## Scope 说明

| Scope | 对应模块 |
|---|---|
| `controller` | 控制器层 |
| `config` | 配置类 |
| `model` | 数据模型 |
| `advisor` | Advisor 模块 |
| `pom` | Maven 构建配置 |
| `readme` | README 文档 |
| `gitignore` | .gitignore 规则 |

Scope 可省略，适用于跨模块或全局性变更。

## Subject 规则

- 使用**祈使句**（"添加"而非"添加了"）
- **≤ 50** 字符
- **不以句号结尾**
- 中文或英文均可，保持团队一致

## 安装 Git Hook（自动校验）

```bash
# 一键启用项目级 Hook
git config core.hooksPath .githooks
```

启用后，每次 `git commit` 会自动校验提交信息格式，不符合规范会被拒绝。
