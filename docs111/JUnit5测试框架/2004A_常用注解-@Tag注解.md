# 常用注解 - @Tag注解
## 本章要点
1. @Tag注解


## Tag注解

类和方法的注解。

#### 作用

`@Tag`注解就是为了对某些方法和类进行归类划分。


在运行`suite`套件的时候可以指定对应`tag`标签来运行特定的类或者方法，只要是对应类或方法上有该标签就会运行。也可以在`pom`文件中进行对应`group`的配置，这样对应的可以只运行特定的方法和类。总结以上，`@Tag`注解就是为了特定运行某些方法和类。


### @Tag 使用


#### 1. 注解位置

1. `@Tag`注解不但可以放在**测试方法**上也可以放在**测试类**的上面。

>是一个类和方法都可以使用的注解。

#### 2. 注解方法声明规则

`Tag`标签的标识名称的**命名规范**：

1. 不能是空的字符串。空字符串对应的没有意义。

2. 标签名称不能有对应的**空格**及**ISO**控制字符。

3. 一般情况下，名称都为英文或者中文。

##### 总结

1. 不为空。
2. 标签不得包含空格。
3. 标签不得包含 ISO 控制字符。
4. 标签不得包含以下任何保留字符：
    ```
    ,
    (
    )
    &
    |
    !
    ```


#### 3. 注解个数

1. 可以在一个类/方法上有多个，也可以只有一个。

1. 如果`@Tags`与`@Tag`共同修饰一个测试类/方法，则只能有一个`@Tag`、一个`@Tags`。

## 应用
### 只有一个@Tag

```java
@Tag("decimal")
```

### 有多个@Tag
```java
@Tags({  
        @Tag("decimal"),  
        @Tag("dev")  
})
```

或者

```java
@Tag("decimal")
@Tag("dev")  
```

或者
```java
@Tag("sub")
@Tags({
        @Tag("num"),
        @Tag("master")
})
```

错误❌：
```java
@Tag("dev")  
@Tag("sub")
@Tags({
        @Tag("num"),
        @Tag("master")
})
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227171725.png)

### 场景

对应测试方法的用例进行分组操作。

- 环境分组： 测试环境、预发布环境
- 阶段分组： 冒烟用例
- 版本分组： V1.1、V1.2



## Hello @Tag

```java
@Tags({
        @Tag("tag"),
        @Tag("master")
})
public class AnTagTest {
    static final Logger logger = getLogger(lookup().lookupClass());
    @Test
    @Order(1)
    @Tag("num")
    public void sum() {
        logger.info("加法测试用例");
    }
    @Test
    @Order(3)
    @Tags({
            @Tag("num"),
            @Tag("dev")
    })
    public void sub() {
        logger.info("减法测试用例");
    }
    @Test
    @Order(5)
    @Tag("dev")
    public void sleep() {
        logger.info("休眠功能");
    }
    @Test
    @Order(9)
    @Tag("test")
    public void close() {
        logger.info("关机功能");
    }
}
```

### 运行
#### 命令行配置

**方式一**：命令行配置`Tag`标签运行。

##### 包含

```bash
# 包含的命令行
mvn clean test -Dgroups=num
```

>运行当前项目下，所有Tag标签是num的测试方法。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227164700.png)

##### 不包含

```bash
# 不包含的命令行
mvn clean test -Dtest=top.testeru.basic.AnTagTest -DexcludedGroups=num,test
```

>运行当前项目下，AnTagTest类下的所有Tag标签不包含num,test的测试方法。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227165304.png)

#### pom文件配置

**方式二**：`pom`文件配置`Tag`标签运行。

##### 包含

包含的配置文件：

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>${maven-surefire-plugin.version}</version>
    <configuration>
        <groups>num,test</groups>
    </configuration>
    <dependencies>
        ...
    </dependencies>
</plugin>
```


>多个标签使用逗号`,`间隔。

```bash
mvn clean test
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227165625.png)

##### 不包含


不包含的配置文件：


```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>${maven-surefire-plugin.version}</version>
    <configuration>
        <excludedGroups>num,test</excludedGroups>
    </configuration>
    <dependencies>
        ...
    </dependencies>
</plugin>
```


>多个标签使用逗号`,`间隔。

```bash
mvn clean test -Dtest=top.testeru.basic.AnTagTest
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230227170033.png)


## 总结

- 标记测试
- **测试类**或**测试方法**声明