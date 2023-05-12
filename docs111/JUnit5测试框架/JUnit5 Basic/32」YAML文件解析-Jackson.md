# Jackson-YAML文件解析
## 本章要点
1. 要点一
1. 要点
1. 要点
1. 要点

## Jackson介绍

- Jackson 所依赖的 jar 包较少 ，简单易用。

- Jackson 解析大的 json 文件速度比较快；

- Jackson 运行时占用内存比较低，性能比较好；

- Jackson 有灵活的 API，可以很容易进行扩展和定制。


本篇文章介绍如何使用Jackson来读写 YAML 文件。

获取到`YAML`文件对应的结构后，我们将使用`ObjectMapper`将`YAML`文件读入`Java` 对象，并将对象写入文件。


## pom导入依赖
```xml    
<properties>
  <jackson.version>2.13.1</jackson.version>
</properties>

<!--yaml文件解析-->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>${jackson.version}</version>
</dependency>
<dependency>
  <groupId>com.fasterxml.jackson.dataformat</groupId>
  <artifactId>jackson-dataformat-yaml</artifactId>
  <version>${jackson.version}</version>
</dependency>
<dependency>
  <groupId>com.fasterxml.jackson.datatype</groupId>
  <artifactId>jackson-datatype-jsr310</artifactId>
  <version>${jackson.version}</version>
</dependency>
```

由于 Java 对象使用`LocalDate`类型，所以还要为该数据类型添加依赖项`JSR-310`。

## YAML文件解析
根据 数据和对象结构 进行分类。
### List集合文件
#### 基本数据类型/String
##### YAML文件
`user1.yml`：

```yaml
- "apple"
- "banana"
- "pear"
```

##### 直接声明类型解析
```java
    //直接声明类型解析
    @Test
    public void list() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File listStrFile = Paths.get("src/test/resources/yaml/user1.yml").toFile();
        TypeReference<List<String>> listStr = new TypeReference<List<String>>() {
        };
        List<String> strings = objectMapper.readValue(listStrFile, listStr);
        logger.info("listStr解析的结果：{}", strings);//listStr解析的结果：[apple, banana, pear]
    }
```

#### Map类型

##### YAML文件
`order1.yml`：
  
```yaml
- item: No. 9 Sprockets
  quantity: 12
  unitPrice: 1.23
- item:  No. Widget (10mm)
  quantity: 10
  unitPrice: 3.45
  
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230301140127.png)

##### 直接声明类型解析


```java
@Test
    public void listOrder() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File listStrFile = Paths.get("src/test/resources/yaml/order1.yml").toFile();
        //直接声明类型解析
        TypeReference<List<HashMap<String, Object>>> typeReference =
                new TypeReference<List<HashMap<String, Object>>>() {};
        List<HashMap<String, Object>> listAndMap = objectMapper.readValue(listStrFile, typeReference);
        logger.info("\nlistAndMap解析的结果：{}", listAndMap);
    }
```

##### 实体类解析

成员变量与yaml的key一致时不需要声明对应注解。

成员变量与yaml的key不一致时需要在成员变量上声明对应注解。

**实体类：**
```java
public class Order {
    @JsonProperty("item")
    private String otherItem;
    @JsonProperty("quantity")
    private int qua;
    @JsonProperty("unitPrice")
    private BigDecimal price;
    @JsonProperty("orderDate")
    private LocalDate date;
    // Constructors, Getters, Setters and toString
}
```

**解析代码：**

```java
@Test
    public void listOrder() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File listStrFile = Paths.get("src/test/resources/yaml/order1.yml").toFile();
        TypeReference<List<Order>> typeReference =
                new TypeReference<List<Order>>() {};
        List<Order> listAndMap = objectMapper.readValue(listStrFile, typeReference);
        logger.info("\nlistAndMap解析的结果：{}", listAndMap);
    }
```

### Map集合文件
#### YAML文件
`order2.yml`：
```yaml
orderLines:
  - item: No. 9 Sprockets
    quantity: 12
    unitPrice: 1.23
  - item: No. Widget (10mm)
    quantity: 10
    unitPrice: 3.45
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230301142350.png)

#### 实体类解析

**实体类：**
```java
public class Order2 {
    @JsonProperty("orderLines")
    private List<Order> orders;

    // Constructors, Getters, Setters and toString

}
```

**解析代码：**


```java
    @Test
    public void map() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File listMapFile = Paths.get("src/test/resources/yaml/order2.yml").toFile();
        //直接声明类型解析报错 HashMap<List<HashMap<String, Object>>
        //使用实体类
        TypeReference<Order2> listMap = new TypeReference<Order2>() {
        };
        Order2 listAndMap = objectMapper.readValue(listMapFile, listMap);
        logger.info("\nMaplistMap解析的结果：{}", listAndMap);
    }
```
### List-Map混合文件

#### YAML文件
`order3.yml`：

```yaml
orderNo: A001
date: 2019-04-17
customerName: Customer, Joe
orderLines:
  - item: No. 9 Sprockets
    quantity: 12
    unitPrice: 1.23
  - item: Widget (10mm)
    quantity: 4
    unitPrice: 3.45
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230301143323.png)

#### 实体类解析
**实体类：**
```java
public class Order3 {
    private String orderNo;
    private LocalDate date;
    private String customerName;
    private List<Order> orderLines;
    // Constructors, Getters, Setters and toString
}
```


**解析代码：**

```java
    @Test
    public void order3() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        File listAndMapFile = Paths.get("src/test/resources/yaml/order3.yml").toFile();
        TypeReference<Order3> typeReference =
                new TypeReference<Order3>() {};
        Order3 listAndMap = objectMapper.readValue(listAndMapFile, typeReference);
        logger.info("\nOrder3解析的结果：{}", listAndMap);
    }
```


## 注意⚠️
1. 可以在声明的时候，直接在YAMLFactory 上设置的属性：
```java
YAMLFactory yamlFactory = new YAMLFactory()
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES) //从字符串中删除引号
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)//不需要在YAML文件的开头明确的文档开始标记（“---”）。
                .enable(YAMLGenerator.Feature.INDENT_ARRAYS);// 启用缩进

ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
```

2. 涉及到日期解析需要声明：

```java
//确定日期被序列化为数字时间戳 date=2019-04-17
objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
```
不声明对应的日期解析不正确。


## 总结

