# Spring 底层原理详解与手写实现

## 📋 目录
1. [Spring核心概念](#spring核心概念)
2. [IoC容器原理](#ioc容器原理)
3. [依赖注入原理](#依赖注入原理)
4. [Bean生命周期](#bean生命周期)
5. [手写Spring实现](#手写spring实现)
6. [运行演示](#运行演示)
7. [扩展功能](#扩展功能)

## 🎯 Spring核心概念

### 1. IoC (Inversion of Control) 控制反转
**传统方式**：对象自己创建和管理依赖
```java
public class UserService {
    private UserRepository userRepository = new UserRepository(); // 自己创建依赖
}
```

**Spring方式**：容器创建和管理依赖
```java
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository; // 容器注入依赖
}
```

### 2. DI (Dependency Injection) 依赖注入
Spring通过以下方式注入依赖：
- **字段注入**：`@Autowired` 注解字段
- **构造函数注入**：`@Autowired` 注解构造函数
- **Setter注入**：`@Autowired` 注解setter方法

### 3. Bean管理
- **Bean定义**：描述如何创建Bean的元数据
- **Bean实例化**：根据定义创建Bean实例
- **Bean作用域**：singleton（单例）、prototype（原型）等

## 🏗️ IoC容器原理

### 容器启动流程

```
1. 扫描阶段 (Scanning)
   ├── 扫描指定包路径
   ├── 查找@Component注解的类
   └── 生成BeanDefinition

2. 注册阶段 (Registration)
   ├── 将BeanDefinition注册到容器
   └── 建立beanName到BeanDefinition的映射

3. 实例化阶段 (Instantiation)
   ├── 遍历所有BeanDefinition
   ├── 创建Bean实例
   └── 处理依赖注入

4. 初始化阶段 (Initialization)
   ├── 调用初始化方法
   └── Bean准备就绪
```

### 核心数据结构

```java
// Bean定义映射：beanName -> BeanDefinition
Map<String, BeanDefinition> beanDefinitionMap

// 单例Bean缓存：beanName -> Bean实例
Map<String, Object> singletonObjects

// 正在创建的Bean集合（解决循环依赖）
Set<String> singletonsCurrentlyInCreation
```

## 💉 依赖注入原理

### 注入过程

```
1. 创建Bean实例
   └── 调用无参构造函数

2. 扫描字段
   ├── 获取所有字段
   └── 查找@Autowired注解

3. 解析依赖
   ├── 根据字段类型查找Bean
   └── 递归创建依赖Bean

4. 注入依赖
   ├── 设置字段可访问
   └── 将依赖Bean设置到字段
```

### 循环依赖解决

```java
// 检查循环依赖
if (singletonsCurrentlyInCreation.contains(beanName)) {
    throw new RuntimeException("检测到循环依赖: " + beanName);
}

// 标记正在创建
singletonsCurrentlyInCreation.add(beanName);
try {
    // 创建Bean
} finally {
    // 移除创建标记
    singletonsCurrentlyInCreation.remove(beanName);
}
```

## 🔄 Bean生命周期

```
1. 实例化 (Instantiation)
   └── 调用构造函数创建对象

2. 属性填充 (Population)
   └── 依赖注入，设置属性值

3. 初始化 (Initialization)
   ├── 调用@PostConstruct方法
   ├── 调用InitializingBean.afterPropertiesSet()
   └── 调用自定义init-method

4. 使用 (In Use)
   └── Bean可以被应用程序使用

5. 销毁 (Destruction)
   ├── 调用@PreDestroy方法
   ├── 调用DisposableBean.destroy()
   └── 调用自定义destroy-method
```

## 🛠️ 手写Spring实现

### 项目结构

```
src/main/java/
├── com.handwritten.spring/          # 手写Spring框架
│   ├── annotation/                  # 注解定义
│   │   ├── Component.java          # @Component注解
│   │   ├── Autowired.java          # @Autowired注解
│   │   └── ComponentScan.java      # @ComponentScan注解
│   ├── core/                       # 核心类
│   │   └── BeanDefinition.java     # Bean定义
│   └── context/                    # 上下文
│       ├── ApplicationContext.java # 容器接口
│       └── AnnotationConfigApplicationContext.java # 容器实现
└── com.handwritten.demo/           # 演示代码
    ├── config/AppConfig.java       # 配置类
    ├── service/UserService.java    # 服务层
    ├── repository/UserRepository.java # 数据层
    └── SpringDemo.java             # 演示主类
```

### 核心实现原理

#### 1. 注解扫描
```java
private void scanPackage(String packageName) {
    // 将包名转换为文件路径
    String packagePath = packageName.replace(".", "/");
    
    // 获取类加载器和资源
    ClassLoader classLoader = AnnotationConfigApplicationContext.class.getClassLoader();
    URL resource = classLoader.getResource(packagePath);
    
    // 递归扫描目录，查找@Component注解的类
    scanDirectory(directory, packageName, classLoader);
}
```

#### 2. Bean创建
```java
private Object createBean(BeanDefinition beanDefinition) {
    // 1. 实例化Bean
    Object beanInstance = beanClass.getDeclaredConstructor().newInstance();
    
    // 2. 依赖注入
    populateBean(beanInstance);
    
    return beanInstance;
}
```

#### 3. 依赖注入
```java
private void populateBean(Object beanInstance) {
    Field[] fields = beanInstance.getClass().getDeclaredFields();
    
    for (Field field : fields) {
        if (field.isAnnotationPresent(Autowired.class)) {
            // 根据类型获取依赖Bean
            Object dependencyBean = getBean(field.getType());
            
            // 注入依赖
            field.setAccessible(true);
            field.set(beanInstance, dependencyBean);
        }
    }
}
```

## 🚀 运行演示

### 编译和运行

```bash
# 编译项目
mvn clean compile

# 运行演示
mvn exec:java -Dexec.mainClass="com.handwritten.demo.SpringDemo"
```

### 预期输出

```
=== 手写Spring框架演示 ===

1. 创建Spring容器...
发现组件: userRepository -> com.handwritten.demo.repository.UserRepository
发现组件: userService -> com.handwritten.demo.service.UserService
扫描完成，共找到 2 个Bean定义
开始实例化单例Bean...
创建Bean: userRepository -> com.handwritten.demo.repository.UserRepository
创建Bean: userService -> com.handwritten.demo.service.UserService
依赖注入: UserService.userRepository <- UserRepository
单例Bean实例化完成，共实例化 2 个Bean

2. 容器中的Bean列表:
  - userRepository : com.handwritten.demo.repository.UserRepository
  - userService : com.handwritten.demo.service.UserService

3. 测试依赖注入和业务功能:
UserService实例: com.handwritten.demo.service.UserService@xxx
注入的UserRepository实例: com.handwritten.demo.repository.UserRepository@xxx

4. 测试业务逻辑:
UserService: 准备保存用户 张三
UserRepository: 保存用户到数据库 - 张三
UserService: 用户保存完成

UserService: 查询用户 ID=1
UserRepository: 从数据库查询用户 ID=1
查询结果: User_1

5. 验证单例模式:
userService == userService2: true

=== Spring框架演示完成 ===
```

## 🔧 扩展功能

### 已实现功能
- ✅ 组件扫描 (`@ComponentScan`)
- ✅ 组件注册 (`@Component`)
- ✅ 依赖注入 (`@Autowired`)
- ✅ 单例模式
- ✅ 循环依赖检测
- ✅ 按类型获取Bean
- ✅ 按名称获取Bean

### 可扩展功能
- 🔄 AOP面向切面编程
- 🔄 Bean作用域 (prototype, request, session)
- 🔄 Bean生命周期回调
- 🔄 条件注册 (`@Conditional`)
- 🔄 配置属性注入 (`@Value`)
- 🔄 事件机制
- 🔄 国际化支持

## 📚 Spring源码对比

### 真实Spring vs 手写Spring

| 功能 | 真实Spring | 手写Spring |
|------|-----------|------------|
| 包扫描 | ClassPathBeanDefinitionScanner | 自定义文件扫描 |
| Bean定义 | GenericBeanDefinition | BeanDefinition |
| 容器 | DefaultListableBeanFactory | AnnotationConfigApplicationContext |
| 依赖注入 | AutowiredAnnotationBeanPostProcessor | populateBean方法 |
| 循环依赖 | 三级缓存机制 | 简单标记检测 |

## 🎓 学习要点

1. **理解控制反转**：对象的创建和管理交给容器
2. **掌握依赖注入**：容器自动装配对象间的依赖关系
3. **熟悉反射机制**：动态创建对象和调用方法
4. **理解单例模式**：容器中的Bean默认是单例的
5. **掌握注解处理**：通过注解驱动Bean的注册和装配

通过这个手写Spring框架，您可以深入理解Spring的核心原理和实现机制！