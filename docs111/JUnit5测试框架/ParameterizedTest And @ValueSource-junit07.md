---
title: ParameterizedTest And @ValueSource
date: 2022-10-12 10:28:15.5
updated: 2022-10-12 11:23:16.013
url: /archives/junit07
categories: 
- JUnit5
tags: 
- junit5
---

# 参数化测试 - @ValueSource

参数化测试可以使用不同的参数多次运行测试。像常规`@Test`方法一样声明，但使用 @ParameterizedTest注解在对应测试方法上进行代替。

此外，必须声明至少一个为每次调用提供参数的源，然后在测试方法中使用这些参数。

## 本章要点
- [ ] ValueSource源的特征
- [ ] ValueSource常见报错

## ValueSource
@ValueSource是最简单的可能来源之一。它允许指定单个文字值数组，并且只能用于为每个参数化测试调用提供单个参数。

如果方法声明对应的参数类型不一致，则会报错，模版如下：

```
org.junit.jupiter.api.extension.ParameterResolutionException:
Error converting parameter at index 0: 
...
```

## ValueSource特征
1. 测试方法使用@ParameterizedTest注解声明「声明特征」

2. 参数化测试方法必须有对应的参数源@ValueSource的声明「声明特征」

3. 参数化测试方法运行次数是参数值的个数「运行特征」

## 参数源
 @ValueSource源声明类型
- `short`
- `byte`
- `int`
- `long`
- `float`
- `double`
- `char`
- `boolean`
- `java.lang.String`
- `java.lang.Class`
### String
测试使用@ValueSource 注释将String数组指定为参数源

```java
public class ValueSourceTest{


    @ParameterizedTest
    @ValueSource(strings = {"Hello","中国","北京"})
    void strParamTest(String str){
        System.out.println("参数str：" + str);
        assertTrue(str.length() <7);
    }
}
```
运行结果如图：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210121043886.png)

##### 结论
@ParameterizedTest修饰的方法将被调用 3 次，分别为：Hello、中国、北京。

### int
```java
    @ParameterizedTest
    @ValueSource(ints = {20220109,20220109,20220109})
    void numParamTest(String num){
        System.out.println("---点击第" + num + "个商品---");
        assertTrue(Integer.valueOf(num) > 100);
    }
```


如果方法参数对应的类型声明与数据源参数类型不一致，则运行时会报错，详细信息如下：

```
org.junit.jupiter.api.extension.ParameterResolutionException:
Error converting parameter at index 0:
No implicit conversion to convert object of type java.lang.Integer to type java.lang.String
```

#### 示例代码
```java
    @ParameterizedTest
    @ValueSource(ints = {20220109,20220109,20220109})
    void numParamTest(int num){
        System.out.println("---点击第" + num + "个商品---");
        assertTrue(num > 100);
    }
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210121109295.png)

##### 结论
执行上述参数化测试方法时，每次调用都会单独运行。


## 总结
- 参数化测试用例基本源使用@ValueSource注解

- @ValueSource声明的参数类型 与 方法参数类型要一致

- 声明的参数值，每次调用都会单独运行
