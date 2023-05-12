---
title: ParameterizedTest DataSource @MethodSource - 上
date: 2022-10-12 11:14:24.27
updated: 2022-10-12 13:55:06.147
url: /archives/junit08
categories: 
- JUnit5
tags: 
- junit5
---

# @MethodSource单个参数
## 本章要点
-  [ ] MethodSource 源的特征
-  [ ] MethodSource 单个参数声明：int、String
## MethodSource

通过使用@ValueSource或@EnumSource不可能将复杂类型作为参数传递，@MethodSource允许我们使用测试类的工厂方法或外部类在测试参数中提供复杂对象。


@MethodSource使用时要注意，测试类中的工厂方法必须是`static`，除非测试类上用`@TestInstance(Lifecycle.PER_CLASS)`注解；而**外部类**中的工厂方法必须始终为`static`。

每个工厂方法都必须生成一个**参数流**，并且流中的每组参数都将作为参数提供给参数化方法单独调用。

如果没有显式提供工厂方法名称给 @MethodSource注解，JUnit Jupiter 将按照约定搜索与当前@ParameterizedTest修饰的方法同名的工厂方法。

## @MethodSource注解特征

1. 测试方法使用@ParameterizedTest注解声明「声明特征」

2. 数据源参数声明必须用static修饰

3. 参数源指定有2种方式：
	
    「1」声明的参数源方法名与@ParameterizedTest注解修饰的测试方法同名
    
    「2」在@MethodSource注解上指定声明的参数方法名
## 单个参数
### int

编写好对应的测试方法及对应数据源后，没有声明@ParameterizedTest注解，则会报错`ParameterResolutionException`：
```java
    @Test
    public void data(int data) {
        System.out.println("当前日期为：" + data);
        assertTrue(data < 20230101);
    }
    //使用的是包装类
    static Stream<Integer> data1() {
        return Stream.of(20221021, 20220921, 20220621);
    }
```
报错如下：
```java
org.junit.jupiter.api.extension.ParameterResolutionException: No ParameterResolver registered for parameter [int data] in method [public void top.testeru.params.MethodOneSourceTest.data(int)].
```

正确代码如下：
```java
    @ParameterizedTest
    @MethodSource("data1")
    public void intData(int data) {
        System.out.println("当前日期为：" + data);
        assertTrue(data < 20230101);
    }

    //使用的是包装类
    static Stream<Integer> data1() {
        return Stream.of(20221021, 20220921, 20220621);
    }
```

#### 数据源- 返回类型为流
```java
static Stream<Integer> data1() {
   return Stream.of(20221021, 20220921, 20220621);
}


static IntStream data3(){
   return IntStream.of(20220309,20220319,20220322);
}

```

#### 数据源- 返回类型为数组

```java
static List<Integer> data2(){
   return Arrays.asList(20220609, 20220709, 20220809);
}
    
static int[] data4(){
   return new int[]{20220409,20220419,20220422};
}
```




### String
#### 数据源- 返回类型为流

```java
    @ParameterizedTest
    @MethodSource("strData1")
    void strData(String str){
        System.out.println("---点击第 " + str + " 个商品---");
        assertTrue(Integer.valueOf(str) < 100);
    }

    static String[] strData1(){
        return new String[]{"2","6","8"};
    }
```

#### 数据源- 返回类型为数组
```java
static List<String> strData2(){
   return Arrays.asList("12","16","18");
}
static Stream<String> strData3(){
   return Stream.of("22","26","28");
}

```



## 总结
- 单个参数声明数据源，可声明返回值类型为流和数组格式





