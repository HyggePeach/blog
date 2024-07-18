# blog

这是一个基于 Java 17 和 Spring Boot 3.x 实现的简单博客系统后端 API。该系统提供了用户管理、博客文章管理等核心功能，并实现了基本的身份认证和权限控制。

## 技术栈

- **Java 17**
- **Spring Boot 3.0.4**
- **MySQL**
- **Redis**: 用于存储用户和 token 的映射，实现基本的身份认证。
- **MyBatis Plus**
- **Hutool**: 提供 MD5 加密和 token 生成工具。

## 功能特性

1. **用户管理**:
   - 用户注册
   - 用户登录
   - 获取当前用户信息
2. **博客文章管理**:
   - 创建新文章
   - 获取某个用户的所有文章列表（支持分页和排序）
   - 获取单篇文章详情
   - 更新文章（仅作者本人有权限）
   - 删除文章（仅作者本人有权限）
3. **权限控制**:
   - 实现了基本的身份认证机制，在用户登录时返回token，后续的敏感操作都需要使用 token 进行用户身份验证。
   - 使用自定义的 TokenFilter 进行 token 的拦截和验证。
4. **RESTful API 设计**:
   - 所有接口均遵循 RESTful 设计风格，使用 HTTP 方法和状态码进行交互。
5. **全局异常处理**:
   - 实现了全局异常处理器，对未捕获的异常进行统一处理，返回统一错误信息。

## 部署说明

#### 使用 Docker 部署

1. 启动 MySQL 容器

2. 启动Redis容器

3. 在命令行中进入项目根目录，确保 Dockerfile 和 JAR 文件在同一目录下。

   使用以下命令构建 Docker 镜像，命名为 `wangjun-blog`（可以根据需要修改名称）：

   ```bash
   docker build -t wangjun-blog 
   ```

4. 使用以下命令启动 Docker 容器：

   ```bash
   docker run -d -p 8080:8080 --name wangjun-blog wangjun-blog
   ```

   

   

```dockerfile


#Dockerfile文件

FROM openjdk:17-jdk-slim
#时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8080

COPYwangjun-blog-0.0.1-SNAPSHOT.jar /app.jar 

# 设置环境变量，覆盖应用程序的默认配置，下面字段用服务器上docker里的mysql信息进行替换
ENV SPRING_DATASOURCE_URL jdbc:mysql://mysql:3306/db_name?useSSL=false&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME your_docker_mysql_username
ENV SPRING_DATASOURCE_PASSWORD your_docker_mysql_password
ENV SPRING_REDIS_HOST redis
ENV SPRING_REDIS_PORT 6379

ENTRYPOINT ["java", "-jar", "/app.jar"]
```



## API 端点

同飞书文档

### UserController

#### login 方法

- **路径**: POST /api/auth/login
- **描述**: 用户登录接口，接收包含用户名和密码的登录请求，验证用户身份并生成认证 token。
- 参数:
  - `LoginRequest`: 包含用户名和密码的请求体。
- **返回**: 登录成功返回包含 token 的成功响应，失败返回错误信息。

#### register 方法

- **路径**: POST  /api/auth/register
- **描述**: 用户注册接口，接收新用户的注册请求，将用户信息存储到数据库中。
- 参数:
  - `RegisterRequest`: 包含用户名、密码和邮箱等注册信息的请求体。
- **返回**: 注册成功返回成功响应，失败返回错误信息。

#### me 方法

- **路径**: GET /api/auth/me
- **描述**: 获取当前登录用户的信息接口，需要认证，从 token 中解析出用户信息并返回。
- **参数**: 无
- **返回**: 当前登录用户的信息，包括用户名、邮箱等。

### PostsController

#### newPost 方法

- **路径**: POST /api/posts
- **描述**: 创建新文章接口，需要登录验证，接收包含文章内容的请求，将文章信息存储到数据库中。
- 参数:
  - `NewPostRequest`: 包含文章标题和内容等信息的请求体。
- **返回**: 创建成功返回成功响应，失败返回错误信息。

#### getPosts 方法

- **路径**: GET /api/posts

- **描述**: 获取某个用户的所有文章列表接口，支持分页和排序。

- 参数:

  - `uid`: 用户 ID，指定获取哪个用户的文章列表。
- `page`, `size`: 分页参数，指定页数和每页数量。
  - `isAsc`: 排序方式，true 表示按创建时间正序，false 表示倒序。
  
- **返回**: 指定用户的文章列表。

#### getPost 方法

- **路径**: GET /api/posts/{postId}
- **描述**: 获取单篇文章详情接口，根据文章 ID 查询并返回文章的详细信息。
- 参数:
  - `postId`: 文章 ID。
- **返回**: 单篇文章的详细信息。

#### updatePost 方法

- **路径**: PUT /api/posts/{postId}
- **描述**: 更新文章接口，需要登录验证和权限判断，接收包含更新内容的请求，更新指定 ID 的文章信息。
- 参数:
  - `UpdatePostRequest`: 包含文章 ID、标题和内容等更新信息的请求体。
- **返回**: 更新成功返回成功响应，失败返回错误信息。

#### deletePost 方法

- **路径**: DELETE /api/posts/{postId}
- **描述**: 删除文章接口，需要登录验证和权限判断，删除指定 ID 的文章。
- 参数:
  - `postId`: 文章 ID。
- **返回**: 删除成功返回成功响应，失败返回错误信息。

## 贡献者

- 王钧