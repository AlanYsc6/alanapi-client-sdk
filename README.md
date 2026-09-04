# alanapi-client-sdk — API 开放平台客户端 SDK

给调用方使用的 Java SDK：引入依赖、配置密钥，即可调用 API 开放平台提供的接口。自动完成 **HMAC-SHA256 签名**并解析平台统一响应，调用方无需关心签名细节。

## 引入

```bash
# 本地安装到 Maven 仓库（或发布到私服）
mvn install
```

```xml
<dependency>
    <groupId>com.alan</groupId>
    <artifactId>alanapi-client-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```

## 快速开始

### 方式一：Spring Boot 自动装配（推荐）

引入依赖后，在 `application.yml` 配置凭证即可直接注入 `AlanApiClient`：

```yaml
alan:
  api:
    access-key: 你的accessKey      # 平台「密钥管理」页生成
    secret-key: 你的secretKey      # 仅本地参与签名计算，不要提交到代码仓库
    host: http://localhost:8123/api  # 接口服务地址
```

```java
@Resource
private AlanApiClient alanApiClient;

String result = alanApiClient.getNameByPost("alan");
```

### 方式二：手动构造

```java
AlanApiClient client = new AlanApiClient(accessKey, secretKey);
// 或指定网关地址
AlanApiClient client = new AlanApiClient(accessKey, secretKey, "http://localhost:8123/api");

String r1 = client.getNameByGet("alan");                             // GET 表单接口
String r2 = client.getNameByPost("alan");                            // POST 表单接口
String r3 = client.getUsernameByPost(new User("alan"));              // POST JSON 接口
```

## 当前提供的接口

| 方法 | 对应服务端 | 说明 |
|---|---|---|
| `getNameByGet(String name)` | `GET /api/name/` | 名称回显（表单） |
| `getNameByPost(String name)` | `POST /api/name/` | 名称回显（表单） |
| `getUsernameByPost(User user)` | `POST /api/name/user` | 用户名回显（JSON 体） |

> 注意：调用会消耗账号在平台分配的调用次数，剩余次数可在平台个人中心查看。

## 签名机制

每次请求自动附加四个请求头：`accessKey / nonce / timestamp / sign`。

- `sign`：对 `accessKey、body、nonce、timestamp` 按 key 排序拼接后，用 secretKey 做 HMAC-SHA256
- `body`：表单接口为参数值本身，JSON 接口为原始 JSON 串（需与服务端参与验签的内容一致）
- secretKey 只参与本地签名计算，不随请求发送

服务端（alanapi-interface）会校验时间戳（5 分钟窗口）与 nonce（Redis 防重放），请保证本机时钟基本准确。

## 错误处理

服务端统一返回 `{code, data, message}`：

- `code = 200`：成功，方法直接返回 `data`
- 其他：抛出 `ApiException`，携带服务端错误码与信息（如 `40300 剩余调用次数不足，请联系管理员`）
- 响应不是统一结构（网关异常等）：抛出 `ApiException(50000)`

## 相关项目

- [alanapi-interface](https://github.com/AlanYsc6/alanapi-interface)：接口服务（验签、计数、日志）
- [alanapi-backend](https://github.com/AlanYsc6/alanapi-backend)：平台主后端（密钥管理、次数分配）
