---
title: @Test Annotation
date: 2022-08-29 21:17:47.508
updated: 2022-10-12 10:32:02.007
url: /archives/junit03
categories: 
- JUnit5
tags: 
- junit5
---

# @Test 注解
## 本章要点
- [ ] `@Test` 注解使用
- [ ] 测试用例编写步骤
- [ ] `JUnit5`自带断言：`assertEquals`
- [ ] `JUnit5`获取业务异常并断言：`assertThrowsExactly`

## @Test注解
我们首先来看第一个注解，是`Test`注解。这个注解放在方法上，对应的方法就称为测试方法。

**@Test注解修饰的方法可以直接运行**，在Idea中可以看到添加完`@Test`注解后，对应的左边出现了可运行的绿色按钮，点击该按钮就可运行对应的方法。

我们可以把它看作一个类似`Java`中`main`方法的入口，但是又和`main`方法有区别，对应的一个`Java`类中只能有一个`main`方法，但是`@Test`注解可以在一个`Java`类中有多个。

同样由于对应的和`main`方法类似，都是没有返回值「==void==」的方法。

### @Test注解特征

1. @Test注解只是方法上的注解，一个类里可以有多个@Test注解修饰的方法「声明特征」

2. @Test注解作用类似Java代码中的`main()`方法入口， 修饰的方法可直接运行「运行特征」

4. @Test注解修饰的方法没有返回值，即方法声明时为`void`「声明特征」

## assertEquals
在这里我们首先要简单介绍第一个`JUnit5`自带的一个基本断言，就是`assertEquals`。
### 作用
该断言只是判断传入的期望值和实际值是否完全一样。
如果不一样，则报错；如果`message`有内容，报错显示的内容就是`message`的描述信息。

 `assertEquals(expected, actual,message)`里面最少是==2个参数==，一个自己的期望值「`expected`」,一个程序的实际值「 `actual`」。
 - 如果想要断言失败的情况下显示自定义的说明，则加上第3个参数，即断言失败说明「`message`」。
 
### `assertEquals(expected, actual,message)`
- 断言「判断」预期的内容和实际的内容是否相等
#### `expected`
- 期望值
#### `actual`
- 代码业务逻辑的实际值
####  `message`
- 断言失败的时候显示说明


## 测试用例步骤
- 1.声明被测对象
- 2.开始被测对象的业务逻辑
- 3.获取业务逻辑结果
- 4.断言


## 编写测试用例
* 1、声明被测对象 - 调用类对象，并命名为 `My Basic Test Project`
* 2、开始被测对象的业务逻辑 - 测试用例步骤调用，`sum()`
* 3、获取业务逻辑结果 - 调用业务逻辑后赋值给`int`类型
* 4、断言 - 测试用例结果验证，JUnit5自带断言

无论是加法、减法还是字符串拼接的功能，我们都需要从三个方面去考虑对应的测试用例：
##### 正向测试用例
##### 边界值测试用例
##### 超出边界值测试用例

即对应调用步骤相同，只需要更改对应参数值即可。
### 正向测试用例
#### 加法正向测试用例步骤


```
public class TestAnnotation_Test {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Test
    public void sum() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //5、测试用例结果验证
        assertEquals(5,result);
    }
}
```
### 边界值测试用例
#### 边界值正向测试用例
```java
    @Test
    public void sumBoundary() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(99, -98);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //5、测试用例结果验证
        assertEquals(1,result);
    }
```
#### 边界值超出测试用例



```java
    @Test
    public void sumBoundaryError() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(100, -99);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //5、测试用例结果验证
        // java.lang.IllegalArgumentException: Please enter an integer in the range!
        assertEquals(5,result);
    }
```


>这个时候发现，对应的超出边界值不能进行断言校验，甚至是相关的业务逻辑都无法进行，那我们应该怎么办呢？
就需要用到下面的`assertThrows`来处理代码抛出异常的断言。
## 异常断言
### assertThrows
`assertThrows`去判断代码抛出的异常是业务代码自定义的异常不，对应的期望值变成了异常类型「`Class<T>`」的期望值，实际的值也是抛出异常的实际值「`Executable`」，同样如果想要断言失败的情况下显示自定义的说明，则加上第3个参数，即断言失败说明「`message`」。

#### `assertThrows(expectedType, executable, message)`  
- 断言提供的业务逻辑的执行是否会引发预期类型的异常并返回异常对象。「抛出异常或异常的子类」
- 抛出异常类型，如果是**当前异常类型的父类不会报错**
##### `expectedType`
- 报错的类型的类
##### `executable`
- 执行的业务流 
##### `message`
- 断言失败的时候显示说明

### assertThrowsExactly 
- 抛出当前异常类
- 只抛出异常类型，**如果是当前异常类型的父类会报错**


### 示例代码
```java
    @Test
    public void sumBoundaryError() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        // assertThrows 抛出异常或异常的父类
        // assertThrowsExactly  抛出当前异常类
        Exception throwException = assertThrows(RuntimeException.class, () -> mySUT.sum(100, 1));
//        Exception exception = assertThrowsExactly(RuntimeException.class, () -> mySUT.sum(100, 1));
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(100, 1));
        //4、测试用例结果验证
        assertTrue(exception.getMessage().contains("enter an integer in the range"));
    }
//    @Test
//    public void sumBoundaryError() {
//        //1、被测系统命名为 - My Basic Test Project
//        MySUT mySUT = new MySUT("My Basic Test Project");
//        //2、打印日志 - Begin Sum Test
//        logger.info("Begin Sum Test");
//         //3、测试用例步骤调用 - sum()
//         int result = mySUT.sum(100, -99);
//         //4、打印结果日志 - Sum Result
//         logger.info("Sum Result：{}",result);
//         //5、测试用例结果验证
//         // java.lang.IllegalArgumentException: Please enter an integer in the range!
//         assertEquals(5,result);
//    }
```

## 总结
-  `@Test` 注解特征
- 测试用例编写步骤
- `JUnit5` 自带断言：`assertEquals`、`assertTrue`、`assertThrows`、`assertThrowsExactly`


[项目演示地址](https://github.com/testeru-pro/junit5-demo/tree/main/junit5-basic)