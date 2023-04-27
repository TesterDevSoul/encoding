# 常用注解 - @Order注解
## 本章要点
1. @Order


## Order注解

方法注解。

必须和`TestMethodOrder`组合使用。

#### 作用

`@Order`注解修饰的方法相当于声明当前测试方法的执行顺序。

### @Order 使用


#### 1. 注解位置

1. `@Order`注解是方法上的注解。
   
>`@Order`注解修饰方法时，放在方法上方。



#### 2. 注解方法声明规则

1. `@Order`注解必须和`testmethod`组合或配置文件声明。否则`@Order`注解不生效。
>testmethod可以直接在配置文件声明，也可以在测试类上声明`@TestMethodOrder`。如果都未声明则Order注解不生效。

1. `@Order`注解内参数值必须是`Integer`类型的数字。



#### 3. 注解运行规则

1. `@Order`注解内参数值不一定是连续的数字，运行规则是比较参数值的大小。

2. 多个测试方法上的`@Order`注解内参数值相同时，根据方法名`MethodName`进行比较的顺序运行。

### Hello @Order

#### 测试类声明

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnOrderTest {
    static final Logger logger = getLogger(lookup().lookupClass());
    @Test
    @Order(1)
    public void sum() {
        logger.info("加法测试用例");
    }
    @Test
    @Order(3)
    public void sub() {
        logger.info("减法测试用例");
    }
    @Test
    @Order(5)
    public void sleep() {
        logger.info("休眠功能");
    }
    @Test
    @Order(9)
    public void close() {
        logger.info("关机功能");
    }
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227155514.png)

#### 配置文件声明

- junit-platform.properties。

```bash
# 测试方法执行顺序 - Order注解
junit.jupiter.testmethod.order.default = org.junit.jupiter.api.MethodOrderer$OrderAnnotation
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227160308.png)


## 总结

- 对应测试方法上添加`@Order`注解。

- `@Order`注解必须和`testmethod`组合或配置文件声明，否则`@Order`注解不生效。
  
- `@Order()`内数字不一定非要按顺序，是按着数字大小进行比较。

- `@Order`未在测试方法上显式声明，则默认顺序值`MethodName`将分配给该方法。

