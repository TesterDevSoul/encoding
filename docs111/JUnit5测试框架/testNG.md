---
notebook: TestNG
title: 1.TestNG简介
tags: Xunit
---
# 简介
testng是一个单元测试框架,提供了一系列的丰富的注解.
帮助我们很方便的编写和管理被测项目的测试类和测试方法.
- 功能比junit4强大,支持依赖测试、忽略测试、异常测试、超时测试、分组测试等多种测试场景.
## 坐标
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.4.0</version>
</dependency>
```
## `testng.xml`
- `suite`元素
    - 测试套件「测试类的集合」
- `test`元素
    - 测试模块
- `classes`
    - 测试类的集合
- `class`
    - 测试类的路径

项目等根目录下创建`testng.xml`
- 一个`suite`「套件」由一个或多个测试模块组成
- 一个`test`「测试」由多个类组成

`testng.xml`执行测试用例时首先按照`class`排序执行,`class`里面方法按照英文自然排序执行

## demo
```java
import org.testng.annotations.Test;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description: testng练习class
 * @create: 2021-10-15 21:17
 */
public class TestNGDemo1 {
    @Test(enabled = false)
    public void test1(){
        System.out.println("test1");
    }
    @Test(dependsOnMethods = "test3")
    public void test2(){
        System.out.println("test2");
    }
    @Test
    public void test3(){
        System.out.println("test3");
    }
}

```
- 点击运行`test2`方法,你会看到`test2`+`test3`一起运行,并且`test3`在`test2`上面
![](https://gitee.com/testeru/pichub/raw/master/images/202110152122760.png)

- 一个项目中可以有多个`testng.xml`,运行的时候进行相关的选择就可以了
## 注解
- `dependsOnMethods`
    - 测试方法依赖于某方法,依赖的那个方法也必须有@Test注解,如果没有则会报错
    ```java
    @Test(dependsOnMethods = "test3")
    public void test2(){
        System.out.println("test2");
    }
    //    @Test
    public void test3(){
        System.out.println("test3");
    }
    ```
    ```
    org.testng.TestNGException: 
    com.tester.testng.TestNGDemo1.test2() is depending on method public void com.tester.testng.TestNGDemo1.test3(), which is not annotated with @Test or not included.
        at org.testng.internal.MethodHelper.findDependedUponMethods(MethodHelper.java:141)
        at org.testng.internal.MethodHelper.topologicalSort(MethodHelper.java:284)
        at org.testng.internal.MethodHelper.sortMethods(MethodHelper.java:377)
        at org.testng.internal.MethodHelper.lambda$collectAndOrderMethods$1(MethodHelper.java:75)
        at org.testng.util.TimeUtils.computeAndShowTime(TimeUtils.java:50)
        at org.testng.internal.MethodHelper.collectAndOrderMethods(MethodHelper.java:74)
        at org.testng.TestRunner.computeAndGetAllTestMethods(TestRunner.java:552)
        at org.testng.internal.TestMethodContainer.getItems(TestMethodContainer.java:32)
        at org.testng.internal.TestMethodContainer.getItems(TestMethodContainer.java:14)
        at org.testng.TestRunner.initMethods(TestRunner.java:522)
        ......
        at org.testng.TestNG.runSuites(TestNG.java:1063)
        at org.testng.TestNG.run(TestNG.java:1031)
        at com.intellij.rt.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:66)
        at com.intellij.rt.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:109)
    ```
- `@Test(enabled = false)`
    - 对应测试方法不执行,取消执行

### 常用注解
- `@BeforeSuite/@AfterSuite`
    - 在某个测试套件(suite)开始之前运行/在某个测试套件所有测试
- `@BeforeTest/@AfterTest`
    - 在某个测试`test`开始之前运行/在某个测试下的所有测试类中的测试方法执行之后运行
- `@BeforeClass/@AfterClass`
    - 在某个测试类class开始之前运行/在某个测试类中的所有测试方法之后运行
- `@BeforeMethod/@AfterMethod`
    - 在某个测试方法method开始之前运行/在某个测试类中的所有测试方法之后运行
- 对应如果不想用的话,就把注解注释掉就可以
- 一般测试方法@Test,可以传参数,但是没有返回值
```java
package com.tester.testng;

import org.testng.annotations.*;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description:
 * @create: 2021-10-15 21:30
 */
public class TestNGDemo2 {
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("testng---beforeSuite");
    }
    @BeforeTest
    public void beforeTest(){
        System.out.println("testng---beforeTest");
    }
    @BeforeClass
    public void beforeClass(){
        System.out.println("testng---beforeClass");
    }
    @BeforeMethod
    public void beforeMethod(){
//        日志
//        资源释放
//        连接数据库
        System.out.println("testng---beforeMethod");
    }
    @Test
    public void test(){
//        专注写测试相关代码
        System.out.println("testng---test");
    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("testng---afterMethod");
    }
    @AfterClass
    public void afterClass(){
        System.out.println("testng---afterClass");
    }
    @AfterTest
    public void afterTest(){
        System.out.println("testng---afterTest");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("testng---afterSuite");
    }
}

```

```
testng---beforeSuite
testng---beforeTest
testng---beforeClass
testng---beforeMethod
testng---test
testng---afterMethod
testng---afterClass
testng---afterTest
testng---afterSuite
```
