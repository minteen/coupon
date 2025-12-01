# OneCoupon 优惠券系统

## 项目简介

OneCoupon 是一个基于 Spring Cloud 的分布式优惠券系统，提供完整的优惠券业务解决方案。系统采用微服务架构，包含多个核心模块，支持优惠券的创建、分发、搜索、锁定、核销等全链路功能。

## 系统架构

### 技术栈
- **编程语言**: Java 17
- **核心框架**: Spring Boot 3.0.7, Spring Cloud 2022.0.3
- **微服务组件**: Spring Cloud Alibaba 2022.0.0.0-RC2
- **数据库**: MyBatis-Plus 3.5.7
- **数据分片**: ShardingSphere 5.3.2
- **缓存**: Redisson 3.27.2
- **搜索引擎**: Elasticsearch
- **消息队列**: RocketMQ
- **API文档**: Knife4j OpenAPI3

### 模块架构

```
┌─────────────────────────────────────────────────────────────┐
│                    OneCoupon System                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    │
│  │   Gateway   │    │  Merchant   │    │    Engine   │    │
│  │   网关模块   │◄──►│   管理后台   │◄──►│    引擎模块   │    │
│  └─────────────┘    └─────────────┘    └─────────────┘    │
│        │                   │                   │          │
│        ▼                   ▼                   ▼          │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐    │
│  │Distribution │    │   Search    │    │ Settlement  │    │
│  │  分发模块    │    │   搜索模块   │    │  结算模块    │    │
│  └─────────────┘    └─────────────┘    └─────────────┘    │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐  │
│  │                  Framework                          │  │
│  │                基础架构模块                           │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## 功能模块

### 1. Framework（基础架构模块）
- **功能**: 提供公共组件和基础配置
- **特性**:
  - 公共工具类库
  - 统一异常处理
  - 基础配置管理
  - 分布式ID生成
  - 幂等性控制

### 2. Gateway（网关模块）
- **功能**: 提供统一的API网关服务
- **特性**:
  - 动态路由配置
  - 请求日志记录
  - 限流熔断保护
  - 统一认证授权
  - 跨域处理

### 3. Merchant-Admin（商家管理后台）
- **功能**: 商家优惠券管理和运营后台
- **特性**:
  - 优惠券模板创建和管理
  - 优惠券批次管理
  - 商家信息维护
  - 数据统计和分析
  - 任务调度管理

### 4. Distribution（分发模块）
- **功能**: 负责优惠券的分发和通知
- **特性**:
  - 按批次分发优惠券
  - 多种通知方式（应用弹框、站内信、短信）
  - 分发策略配置
  - 用户定向推送
  - 分发结果统计

### 5. Search（搜索模块）
- **功能**: 提供优惠券搜索服务
- **特性**:
  - 全文搜索功能
  - 多维度筛选
  - 搜索建议
  - 热门推荐
  - 搜索历史记录

### 6. Engine（引擎模块）
- **功能**: 优惠券核心业务处理引擎
- **特性**:
  - 优惠券查看（单个/列表）
  - 优惠券锁定机制
  - 优惠券核销处理
  - 库存管理
  - 并发控制

### 7. Settlement（结算模块）
- **功能**: 订单金额计算和优惠券结算
- **特性**:
  - 订单金额计算
  - 优惠券抵扣计算
  - 多种优惠策略
  - 实时价格计算
  - 高并发处理

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Elasticsearch 8.x (搜索模块)
- RocketMQ 4.x (可选)

### 构建和运行

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd onecoupon
   ```

2. **编译项目**
   ```bash
   ./mvnw clean install
   ```

3. **启动服务**（按依赖顺序）
   ```bash
   # 启动基础服务
   cd framework && ../mvnw spring-boot:run

   # 启动网关
   cd ../gateway && ../mvnw spring-boot:run

   # 启动其他服务
   cd ../engine && ../mvnw spring-boot:run
   cd ../distribution && ../mvnw spring-boot:run
   cd ../search && ../mvnw spring-boot:run
   cd ../settlement && ../mvnw spring-boot:run
   cd ../merchant-admin && ../mvnw spring-boot:run
   ```

### 配置文件

各模块的配置文件位于 `src/main/resources/application.yaml`，主要配置项包括：

```yaml
server:
  port: 服务端口

spring:
  datasource:
    url: 数据库连接
    username: 用户名
    password: 密码
  redis:
    host: Redis主机
    port: Redis端口

mybatis-plus:
  configuration:
    日志配置
```

## 数据库设计

### 核心表结构

1. **优惠券模板表 (coupon_template)**
   - id: 主键
   - title: 优惠券标题
   - description: 描述
   - type: 类型
   - validity_start: 有效期开始
   - validity_end: 有效期结束
   - stock: 库存
   - status: 状态

2. **优惠券表 (coupon)**
   - id: 主键
   - template_id: 模板ID
   - user_id: 用户ID
   - status: 状态
   - receive_time: 领取时间
   - use_time: 使用时间

3. **分发任务表 (coupon_task)**
   - id: 主键
   - template_id: 模板ID
   - batch_id: 批次ID
   - status: 状态
   - send_type: 发送类型


## 监控和维护

### 健康检查
各服务提供健康检查端点：
- `/actuator/health`
- `/actuator/info`

### 日志管理
- 统一日志格式
- 日志级别配置
- 日志文件轮转

## 性能优化

### 缓存策略
- Redis缓存热点数据
- 本地缓存减少数据库访问
- 多级缓存架构

### 数据库优化
- 分库分表（ShardingSphere）
- 索引优化
- 查询优化

### 并发控制
- Redisson分布式锁
- 乐观锁机制
- 限流熔断

