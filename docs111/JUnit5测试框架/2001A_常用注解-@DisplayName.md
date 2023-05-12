# 常用注解 - @DisplayName注解
## 本章要点
1. @DisplayName注解作用
2. @DisplayName注解使用

## @DisplayName注解

类和方法的注解。

### 运行原理

### 作用

作用就是自定义显示名称，自己想怎么显示就怎么显示。

一般情况下的作用就是在 IDE 和构建工具中的测试报告中自定义显示的类名和方法名，当然可以和`allure`报告结合，在`allure`报告中同样显示**对应方法名和类名**。

### @DisplayName使用

#### 1. 注解位置

`@DisplayName`注解不只是方法上的注解还是类上的注解。是一个类和方法都可以使用的注解。
   
>`@DisplayName`注解修饰方法或者类时，放在上方。

#### 2. 注解方法声明规则

1. 传入参数为`String`类型。
   eg : `@DisplayName("This is a displayname annotation test")`

>对应括号内是`string`类型的字符串就是对应的显示内容。

1. `String`类型内可以填写包含**空格、特殊字符甚至表情符号😊**。
    eg : `@DisplayName("😊计算器测试用例 ╯°□°）╯")`


#### 3. 注解运行规则

1. `@DisplayName`注解修饰的方法必须有 `@Test`注解。

#### 4. 注解个数

1. 一个测试类里可以有多个`@DisplayName`注解修饰的方法。

### Hello @DisplayName

```java
@DisplayName("米小圈上学记")
public class AnDisplayNameTest {
    static final Logger logger = getLogger(lookup().lookupClass());
    @Test
    @DisplayName("😊")
    public void displayName1() {
        logger.info("String类型的表情符号");
    }
    @Test
    @DisplayName("\uD83D\uDE31")
    public void displayName2() {
        logger.info("String类型的特殊字符");
    }
}
```


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227115953.png)

## 总结

@DisplayName注解：

- 带注释的**测试类**或**测试方法**声明自定义显示名称。

- 通常用于 IDE 和构建工具中的测试报告。

- 可包含空格、特殊字符甚至表情符号😊。

- 结合Allure报告，可以显示对应方法名和类名。
