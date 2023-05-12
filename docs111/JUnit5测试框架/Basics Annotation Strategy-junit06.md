---
title: Basics Annotation Strategy
date: 2022-10-11 11:50:16.469
updated: 2022-10-11 20:23:35.903
url: /archives/junit06
categories: 
- JUnit5
tags: 
- junit5
---

# 基本注解测试编写策略
## 本章要点
- [ ] 测试用例+注解的编写策略


##  测试用例策略
`@BeforeAll`、`@BeforeEach`、`@Test`、`@AfterEach`、`@AfterAll` 都是方法上的注解，故只能写在方法上。

### @Test

- void 返回值  
#### 作用
   
- 对应的 Test Case 测试业务逻辑代码
>比如：UI自动化中的具体操作。


### @BeforeAll
- `static`修饰 
- `void`返回值 
- 只在测试类里运行一次，并且在最初的时候运行一次

#### 作用
- 测试用例中，测试数据初始化准备、各种环境准备
>比如：UI自动化中，web打开浏览器的操作、通过登录操作步骤获取cookie；手机上对应版本的apk安装；手机打开app的操作；
>接口自动化中，获取访问服务的权限字段，比如企业微信的为access_token；
>日志文件、截图的清除等等
### @AfterAll
- `static`修饰 
- `void`返回值 
- 只在测试类里运行一次，并且是所有方法运行完了之后再运行
#### 作用
- 测试用例的结尾，软件的退出或卸载
>比如：自动化中，手机上的apk卸载；软件app的退出；等等

### @BeforeEach
- void 返回值  

- 在@Test注解的方法之**前**运行，有多少个@Test注解就运行多少次

#### 作用
- 测试用例中，测试方法初始化某些属性
>比如：自动化中，app / web 自动化测试的每个测试方法运行之前都需要进入固定页面；删除当前测试方法对应的某些业务测试数据；等等
    
### @AfterEach
- void 返回值  

- 在@Test注解的方法之**后**运行，有多少个@Test注解就运行多少次
   
#### 作用
- 测试用例中，销毁测试方法改变的某些属性

>比如：自动化中，app / web自动化测试的每个测试方法运行之后时候返回首页 或 返回固定页面；删除测试方法中产生的业务测试数据

## 抽取原则
- @Test注解对应的测试用例编写在测试用例类中
- @BeforeAll、@BeforeEach、@AfterEach、@AfterAll 注解修饰的方法提取到BaseTest类中
- 测试用例类使用Java的继承 extends，继承对应的基本测试类 BaseTest
- BaseTest类中没有业务逻辑，只是测试用例的前置操作和后置操作

[抽取用例的项目地址](https://github.com/testeru-pro/junit5-demo/tree/main/junit5-basic/src/test/java/top/testeru/basic/strategy)

## 总结
- [ ] 1、@Test注解对应的方法断言是否成功，@AfterEach注解修饰的方法里的代码都是运行的
- [ ] 2、@Test注解有多少个@BeforeEach、@AfterEach就运行多少次
- [ ] 3、@BeforeAll、@BeforeEach、@Test、@AfterEach、@AfterAll 都是方法注解，只能写在方法上
- [ ] 4、注解的运行顺序：
```
 @BeforeAll、
      @BeforeEach、@Test 「sum1Test」、 @AfterEach、
      @BeforeEach、@Test「sum2Test」、@AfterEach、
 @AfterAll
```