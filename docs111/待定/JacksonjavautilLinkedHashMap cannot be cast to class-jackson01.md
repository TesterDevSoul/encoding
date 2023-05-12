---
title: Jackson:java.util.LinkedHashMap cannot be cast to class...
date: 2022-08-25 19:12:41.842
updated: 2022-08-25 21:13:09.442
url: /archives/jackson01
categories: 
- rest-assured
tags: 
- rest-assured
---

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/fasterxml/202208251911875.png)


## 概述
`Jackson`是一个广泛使用的 `Java` 库，它允许我们很方便的序列化/反序列化 `JSON`,`Yaml`或`XML`文件。

一般， 当我们尝试将 `JSON` 或 `XML` 反序列化为对象集合时，可能会遇到如下报错：
```
java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to class...
```

本文章，将讨论为什么会发生此异常？以及如何解决这个异常。
## 异常何时发生
### yaml文件
- 创建一个环境配置的`yaml`文件
>config.yaml文件，该文件由一个包含三个环境的内容组成：


```yaml
#多环境
evnList:
  - env: master
    corpid: 123
    corpsecret: aopli
  - env: dev
    corpid: ww922
    corpsecret: 8gJzO
  - env: test1
    corpid: be1b
    corpsecret: jQCPAksE
```
### 创建实体类
- 根据`yaml`文件格式进行对应的实体类创建
```java
@Getter
@Setter
@ToString
public class EnvData {
    private String env;
    private String corpid;
    private String corpsecret;
}
```




将 `Yaml` 示例反序列化为`Config`时会发生什么？？
### 反序列化
```java
public class JsonExcepTest {
    @Test
    public void throwEx() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        List<EnvData> config = objectMapper.readValue(new File("src/test/resources/config.yaml"), ArrayList.class);
        System.out.println(config.get(0).getCorpid());
    }
}
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/fasterxml/202208252042438.png)

打印结果发现，调用`config.get(0).getCorpid()`时引发了预期的异常，并且它的消息与我们的问题陈述中记录的消息相匹配，这意味着我们已经成功地重现了问题。
### why异常
为什么抛出异常？？
如果我们仔细查看异常消息 `class java.util.LinkedHashMap cannot be cast to class ...`，可能会出现几个问题。

我们已经用`List<EnvData>`类型声明了变量 `config`，但是，
为什么 `Jackson` 尝试将 `LinkedHashMap` 类型转换为我们的`EnvData`类？
`LinkedHashMap`是从哪里来的？
#### 第一点
首先，我们确实用`List<EnvData>`类型声明了 `config`。但是，当我们调用 `objectMapper.readValue()` 方法时，我们将`ArrayList.class`作为`Class`对象传递 。因此，`Jackson` 会将 `JSON` 内容反序列化为`ArrayList`对象，但它不知道`ArrayList`对象中应该包含什么类型的元素。
#### 第二点
其次，当 `Jackson` 尝试反序列化 `JSON` 中的对象但没有给出目标类型信息时，它将使用默认类型：`LinkedHashMap`。
>换句话说，在反序列化之后，我们会得到一个`ArrayList<LinkedHashMap>`对象。在` Map`中，**键**是属性的名称，例如：`env`、`corpid` 等。

这些值是相应属性的值：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/fasterxml/202208252048884.png)




### 异常解决方案一：TypeReference
- 将`TypeReference`传递给`objectMapper.readValue()`
为了解决这个问题，我们需要让`Jackson` 知道元素的类型。但是，编译器不允许我们执行`objectMapper.readValue(jsonString, ArrayList<EnvData>.class)` 之类的操作。

这时，我们可以将`TypeReference`对象传递给`objectMapper.readValue(String content, TypeReference valueTypeRef)`方法

在这种情况下，我们只需要传递`new TypeReference<List<EnvData>>() {}`作为第二个参数：

```java
    @Test
    public void throwEx1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<EnvData>> typeReference = new TypeReference<>() {
        };
        List<EnvData> config = objectMapper.readValue(
                new File("src/test/resources/config.yaml"),
                typeReference);
        System.out.println(config.get(0).getCorpid());
    }
```


运行测试，通过并且没有报错。因此，传递一个`TypeReference`对象解决了我们的问题。

### 异常解决方案二：JavaType
- 将 `JavaType` 传递给 `objectMapper.readValue()`
在上面，我们讨论了异常解决方案一，传递一个`Class`对象或`TypeReference`对象作为第二个参数来调用`objectMapper.readValue()`方法。

`objectMapper.readValue ()`方法仍然接受`JavaType`对象作为第二个参数。
>`JavaType` 是类型标记类的基类。它将被反序列化器使用，以便反序列化器在反序列化期间知道目标类型是什么。 

这个时候可以通过 `TypeFactory` 实例构造一个`JavaType`对象 ，我们可以从`objectMapper.getTypeFactory()`中检索到`TypeFactory`对象。

在当前用例中，我们想要的目标类型是`ArrayList<EnvData>`，因此，可以根据这个要求构造一个`CollectionType` ：
```java
objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, EnvData.class);
```


























