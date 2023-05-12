---
notebook: Junit4
title: Junit4
tags: Xunit
---
# Junit4
- 开发比较喜欢用的单测框架
- springboot中集成的单测框架
- 


## 基本注解
|注解|含义|备注|
|---|---|---|
|`@BeforeClass`|含义|备注|
|`@Before`|含义|备注|
|`@Test`|含义|备注|
|`@After`|含义|备注|
|`@AfterClass`|含义|备注|



https://github.com/junit-team/junit4/wiki/Categories#using-categories-with-maven



https://github.com/google/TestParameterInjector

## 用例间执行顺序
##### `FixMethodOrder(MethodSorters.DEFAULT)` 
取决于反射方法获得的列表,顺序固定
默认顺序由方法名hashcode值来决定，如果hash值大小一致，则按名字的字典顺序确定

##### `FixMethodOrder(MethodSorters.JVM)`
顺序可能变化
按JVM返回的方法名的顺序执行，此种方式下测试方法的执行顺序是不可预测的，即每次运行的顺序可能都不一样(JDK7里尤其如此)

#####  `FixMethodOrder(MethodSorters.NAME_ASCENDING)`
按名字是排序
按字符的字典顺序,这种方式指定执行顺序会始终保持一致；