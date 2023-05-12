# jsckson介绍
## 本章要点
1. @Tag注解



## Jackson 的核心模块
### jackson-core
核心包，提供基于"流模式"解析的相关 API，它包括 `JsonParser` 和 `JsonGenerator`。

 Jackson 内部实现正是通过高性能的流模式 API 的 JsonGenerator 和 JsonParser 来生成和解析 json。

### jackson-annotations
注解包，提供标准注解功能。

### jackson-databind

数据绑定包， 提供基于"对象绑定" 解析的相关 API （ ObjectMapper ） 和"树模型" 解析的相关 API （JsonNode）；基于"对象绑定" 解析的 API 和"树模型"解析的 API 依赖基于"流模式"解析的 API。
<!-- .slide: data-fullscreen -->---
notebook: JAVA_utils
title: 1.jackson
tags: util
---
# jsckson
`JSON` 文件 进行解析,解析成`Java`对象.
[官网用法](https://github.com/FasterXML/jackson-databind)

pom文件导入对应相关依赖
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>${jackson.version}</version>
</dependency>
```
代码中使用
#### `ObjectMapper`用于所有数据绑定
```java
//com.fasterxml.jackson.databind.ObjectMapper实例，用于所有数据绑定
ObjectMapper objectMapper = new ObjectMapper();
```
#### 读取json
```java
// Note: can use getters/setters as well; here we just use public fields directly:
public class MyValue {
  public String name;
  public int age;
  // NOTE: if using getters/setters, can keep fields `protected` or `private`
}
```
`mapper.readValue()`

##### 1」根据json文件地址
`objectMapper.readValue(new File( `**[文件地址]**`),` **[实例的对象.class]** `);`
```java
MyValue value = mapper.readValue(new File("data.json"), MyValue.class);
```
##### 2」根据json网址

`mapper.readValue(new URL(`**[json网址]**`),` **[实例的对象.class]** `);`

```java
MyValue value = mapper.readValue(new URL("http://some.com/api/entry.json"), MyValue.class);
```
##### 3」根据json字符串
`mapper.readValue("`**[json字符串]** `", `**[实例的对象.class]**`);`


```java
MyValue value = mapper.readValue("{\"name\":\"Bob\", \"age\":13}", MyValue.class);
```

#### 写入json
`mapper.writeValue()`
##### 1」写入json文件
`mapper.writeValue(new File("`**[json文件路径]**`"),` **[实例的对象]**`);`
```java
mapper.writeValue(new File("result.json"), myResultObject);
```

##### 2」写入byte数组
`mapper.writeValueAsBytes(`**[实例的对象]**`);`
```java
byte[] jsonBytes = mapper.writeValueAsBytes(myResultObject);

```
##### 3」写入String
`mapper.writeValueAsString(`**[实例的对象]**`);`
```java
String jsonString = mapper.writeValueAsString(myResultObject);
```


POJO, List, Maps