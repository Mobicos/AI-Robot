# Q-AI-robot Backend

![Java 21](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot 3.4.11](https://img.shields.io/badge/Spring%20Boot-3.4.11-brightgreen.svg)
![Spring AI 1.0.0](https://img.shields.io/badge/Spring%20AI-1.0.0-yellow.svg)
![DeepSeek](https://img.shields.io/badge/Model-DeepSeek-orange.svg)

面向生产的 Spring Boot + Spring AI 后端服务，集成 DeepSeek 模型，提供稳定的 HTTP 接口与完善的多环境、安全与部署实践。

## 目录
- 功能特性
- 快速开始
- 环境与配置
- API 规范
- 安全与密钥管理
- 排障指南
- 构建与部署

## 功能特性
- `GET /ai/generate` 基于 DeepSeek Chat 的文本生成接口
- 多环境：`dev`、`local`（私有本地）、`prod`（生产）
- 环境变量注入密钥，严格避免明文提交
- 清晰的配置分层与覆盖策略

## 快速开始
### 前置条件
- 安装 `Java 21` 与 `Maven`
- 申请并获取 DeepSeek API Key

### 开发环境（dev）
1. 在终端设置密钥（PowerShell）：
   ```powershell
   $env:DEEPSEEK_API_KEY="sk-你的密钥"
   ```
2. 启动应用：
   ```powershell
   mvn -q -DskipTests spring-boot:run
   ```
3. 访问接口：
   ```bash
   curl "http://localhost:8080/ai/generate?message=你是谁?"
   ```

### 本地私有（local）
本地私有配置文件 `src/main/resources/application-local.yml` 会被忽略，不提交到仓库。

1. 创建并填写 `application-local.yml`（示例）：
   ```yaml
   server:
     port: 8077
   spring:
     ai:
       deepseek:
         api-key: sk-你的本地密钥
         base-url: https://api.deepseek.com
         chat:
           options:
             model: deepseek-chat
             temperature: 0.8
   ```
2. 激活 `local`：
   ```powershell
   mvn -q spring-boot:run -Dspring-boot.run.profiles=local
   ```
3. 访问：`http://localhost:8077/ai/generate?message=Hello`

### 生产环境（prod）
使用环境变量注入密钥与端口（默认 8080，可通过 `SERVER_PORT` 覆盖）。

```bash
export DEEPSEEK_API_KEY=sk-你的生产密钥
export SERVER_PORT=8080
java -jar target/ai-robot-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 环境与配置
- 基础：`src/main/resources/application.yml`（默认激活 `dev`）
- 开发：`src/main/resources/application-dev.yml`（端口 `8080`，密钥来源环境变量）
- 本地：`src/main/resources/application-local.yml`（私有本地，忽略提交）
- 生产：`src/main/resources/application-prod.yml`（端口可由 `SERVER_PORT` 覆盖）
- 示例：`src/main/resources/application-example.yml`（可提交，读取环境变量）

配置覆盖优先级：命令行 > 环境变量 > `application-{profile}.yml` > `application.yml`

## API 规范
- 接口：文本生成
  - 方法：`GET`
  - 路径：`/ai/generate`
  - 查询参数：`message`（默认：`你是谁?`）
  - 成功返回：`200`，响应为模型生成的文本
  - 示例：
    ```bash
    curl "http://localhost:8080/ai/generate?message=讲个笑话"
    ```
  - 参考实现：`src/main/java/com/q/airobotbackend/controller/DeepSeekChatController.java`

## 目录结构（关键部分）
- 应用入口：`src/main/java/com/q/airobotbackend/AiRobotBackendApplication.java`
- 控制器：`src/main/java/com/q/airobotbackend/controller/DeepSeekChatController.java`
- 配置：
  - `src/main/resources/application.yml`
  - `src/main/resources/application-dev.yml`
  - `src/main/resources/application-prod.yml`
  - `src/main/resources/application-local.yml`（本地私有，忽略提交）

## 安全与密钥管理
- 严禁将密钥写入受控文件；使用环境变量 `DEEPSEEK_API_KEY`
- `.gitignore` 已忽略：
  - `src/main/resources/application-local.yml|.yaml|.properties`
  - `**/application-secrets.*`
  - `.env`、`.env.local`、`.env.*.local`、`*.env`
- 服务器/CI 使用平台 Secret 注入（如 GitHub Actions `secrets.DEPPSEEK_API_KEY`）

## 排障指南
- 端口未按预期：确认 `--spring.profiles.active`、是否有 `SERVER_PORT` 覆盖
- 401/403：检查 `DEEPSEEK_API_KEY` 是否有效、未过期
- 依赖解析失败：确保访问 Maven Central，或为 AI 依赖添加明确版本
- 接口未触发：确认访问路径与端口正确，控制器包位于 `@SpringBootApplication` 扫描路径下（`com.q.airobotbackend`）

## 构建与部署
```bash
mvn -q -DskipTests package
```

## License
开源前建议使用常见许可证（如 MIT / Apache-2.0）。