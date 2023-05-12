# @MethodSource源
## 本章要点
1. 

## 参数化测试介绍


## @MethodSource
`@MethodSource`是一个 `ArgumentsSource`，提供对从声明此注解的类的工厂方法返回的值或从由完全限定方法名称引用的外部类中的静态工厂方法返回的值的访问。

`@MethodSource`允许引用测试类或外部类的一个或多个工厂方法。

对应的方法必须是`static`修饰，除非测试类被注释为@TestInstance(Lifecycle.PER_CLASS)。

每个工厂方法都必须生成一个参数流，并且流中的每组参数都将作为物理参数提供给注释方法的各个调用@ParameterizedTest。一般来说，这翻译成一个 Streamof Arguments(ie, Stream<Arguments>); 然而，实际的具体返回类型可以采用多种形式。在此上下文中，“流”是 JUnit 可以可靠地转换为 的任何内容Stream，例如Stream、DoubleStream、LongStream、 IntStream、Collection、Iterator、Iterable、对象数组或原语数组。Arguments流中的“参数”可以作为对象数组的实例提供 （例如，Object[])，如果参数化测试方法接受单个参数，则为单个值。

如果您只需要一个参数，则可以返回Stream参数类型的实例，如以下示例所示。


## 参数源声明

### 基本数据类型

比如测试方法为`void test(int)`，对应的参数方法声明可为以下几种：

1. static **`int[]`** 方法名(){...}
2. static **`IntStream`** 方法名(){...}
3. static **`Stream<Integer>`** 方法名(){...}




### String


测试方法为`void test(String)`，对应的参数方法声明可为以下几种：

1. static **`String[]`** 方法名(){...}

2. static **`Stream<String>`** 方法名(){...}




## 一个参数 - 同一测试类中

只需要一个参数时，可以用`Stream`参数类型的实例。


### 使用指定方法名
```java
static final Logger logger = getLogger(lookup().lookupClass());

@ParameterizedTest
@MethodSource("stringProvider")
void testWithExplicitLocalMethodSource(String str) {
    logger.info("今天的水果为：{}", str);
}
static Stream<String> stringProvider() {
    return Stream.of("apple", "banana");
}
```


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230228103646.png)

### 使用同一方法名

```java
@ParameterizedTest
@MethodSource
void testWithExplicitLocalMethodSource1(String str) {
    logger.info("同一方法名的水果：{}", str);
}
static Stream<String> testWithExplicitLocalMethodSource1() {
    return Stream.of("apple", "banana","orange");
}
```


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230228103919.png)

## 多个参数 - 同一测试类中

需要返回`Arguments`实例或`对象数组的集合`、`流`或`数组`...

`Arguments.of(Object…​)`可 `arguments(Object…​)`.

### 使用指定方法名


```java
@ParameterizedTest
@MethodSource("stringIntAndListProvider")
void testWithExplicitLocalMethodSource(String str, int num, List<String> list) {
    logger.info(str);
    logger.info(String.valueOf(num));
    logger.info(list.toString());
}
static Stream<Arguments> stringIntAndListProvider() {
    return Stream.of(
            arguments("apple",1, Arrays.asList("d","e")),
            arguments("orange",2, Arrays.asList("dd","ew"))
    );
}
```


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230228104242.png)

### 使用同一方法名



```java
@ParameterizedTest
@MethodSource
void stringIntAndListTest(String str, int num, List<String> list) {
    logger.info(str);
    logger.info(String.valueOf(num));
    logger.info(list.toString());
}
static Stream<Arguments> stringIntAndListTest() {
    return Stream.of(
            arguments("apple",1, Arrays.asList("d","e")),
            arguments("orange",2, Arrays.asList("dd","ew"))
    );
}
```





## 多个参数 - 非同一测试类中


完全限定的方法名称来引用外部`static` 工厂方法