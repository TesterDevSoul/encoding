---
title: Create JUnit5 Maven Project
date: 2022-08-29 18:17:34.161
updated: 2022-10-12 09:56:40.953
url: /archives/junit02
categories: 
- JUnit5
tags: 
- junit5
---

# JUnit5 项目创建
## 本章要点
- [ ] maven 项目创建
- [ ] maven 项目中配置 Junit 5 依赖项
- [ ] 被测系统声明


## Junit 5 + Maven
本篇文章使用Maven构建系统开始使用JUnit Jupiter的配置的内容。
在这篇 Junit 5 maven 文章中，将看到如何在idea中创建maven项目，以及如何在idea中运行相关代码。


### 添加JUnit5依赖项
- 为了防止`JUnit5`的各个模块之间的版本冲突，在这里使用`dependencyManagement`标签进行版本管理

有关`JUnit 5`的依赖说明：
##### junit-jupiter
创建 `Junit 5` 测试用例的 API。
##### junit-vintage-engine
兼容`JUnit4`版本的测试用例。


##### 完整的依赖
```xml
    <properties>
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
        <maven.compiler.version>3.10.1</maven.compiler.version>
        <!-- 使用 Java 11 语言特性 ( -source 11 ) 并且还希望编译后的类与 JVM 11 ( -target 11 )兼容，
        可以添加以下两个属性，它们是默认属性插件参数的名称 -->
        <java.version>11</java.version>
        <!-- 对应junit Jupiter的版本号;放在这里就不需要在每个依赖里面写版本号，导致对应版本号会冲突 -->
        <junit.jupiter.version>5.9.1</junit.jupiter.version>
        <!-- log日志 -->
        <slf4j.version>2.0.3</slf4j.version>
        <logback.version>1.4.3</logback.version>

    </properties>
    <!--    物料清单 (BOM)-->
    <dependencyManagement>
        <dependencies>
            <!--当使用 Gradle 或 Maven 引用多个 JUnit 工件时，此物料清单 POM 可用于简化依赖项管理。不再需要在添加依赖时设置版本-->
            <dependency>
                <groupId>org.junit</groupId>
                <artifactId>junit-bom</artifactId>
                <version>${junit.jupiter.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <!--junit5-->
        <!-- 创建 Junit5 测试用例的 API-->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <!--对应添加的依赖的作用范围-->
            <scope>test</scope>
        </dependency>
        <!-- 兼容 JUnit4 版本的测试用例-->
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- log日志 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>${logback.version}</version>
            <scope>compile</scope>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.version}</version>
                <configuration>
                    <parameters>true</parameters>
                    <!-- 设置jre版本为 11 -->
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <!-- 设置编码为 UTF-8 -->
                    <encoding>${maven.compiler.encoding}</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
### 创建被测系统
被测系统说明：
#### 加法运算-sum()
- 连续加法「sum()」：参数如果大于99或者小于-99则报错「请输入范围内的整数」
#### 减法运算-subtract()
- 连续减法：100往下进行减法，参数如果大于99或者小于-99则报错「请输入范围内的整数」
- 两数相减：2个参数直接相减，参数如果大于99或者小于-99则报错「请输入范围内的整数」
#### 平均值运算
- 求平均值：参数如果大于99或者小于-99则报错「请输入范围内的整数」

#### 字段拼接运算
- 字段拼接：不同字段空格拼接

##### 完整代码

```java
package top.testeru;

import org.slf4j.Logger;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description 被测系统
 * @createTime 2022年10月09日 18:15:00
 */
public class MySUT {

    //获得具有所需名称的记录器
    static final Logger logger = getLogger(lookup().lookupClass());
    //用例名
    String name;
    //唯一ID标识
    String id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public MySUT(String name) {
        this.name = name;
        logger.info("Open {} ", name);
    }
    public void initId(){
        id = UUID.randomUUID().toString();
        logger.info("Generate ID：{} ", id);
    }
    public void destroyId() {
        if (id == null) {
            //没有初始化ID
            throw new IllegalArgumentException(name + " No Initialization ID");
        }
        logger.info("Release ID: {} ", id);
        id = null;
    }
    public void close() {
        logger.info("Close {} ", name);
    }
    //连续添加
    public int sum(int... numbers) {
        if(Arrays.stream(numbers).anyMatch(u -> u > 99) | Arrays.stream(numbers).anyMatch(u -> u < -99)){
            // 请输入范围内的整数
            logger.warn("Please enter an integer in the range!");
            throw new IllegalArgumentException("Please enter an integer in the range!");
        }else {
            return IntStream.of(numbers).sum();
        }
    }
    //从100进行减法
    public int subtract(int... numbers) {
        if(Arrays.stream(numbers).anyMatch(u -> u > 99) | Arrays.stream(numbers).anyMatch(u -> u < -99)){
            logger.warn("Please enter an integer in the range!");
            throw new IllegalArgumentException("Please enter an integer in the range!");
        }else {
            return IntStream.of(numbers).reduce(100, (a, b) -> a-b);
        }
    }
    public int subtract(int x,int y) {
        if(x>99 | x<-99 | y>99 | y<-99){
            logger.warn("Please enter an integer in the range!");
            throw new IllegalArgumentException("Please enter an integer in the range!");
        }else {
            return x-y;
        }
    }
    //平均值 average
    public double average(int... numbers) {
        if(Arrays.stream(numbers).anyMatch(u -> u > 99) | Arrays.stream(numbers).anyMatch(u -> u < -99)){
            logger.warn("Please enter an integer in the range!");
            throw new IllegalArgumentException("Please enter an integer in the range!");
        }else {
            return IntStream.of(numbers).average().getAsDouble();
        }
    }
    //连续拼接
    public String concatStr(String... words) {
        return String.join(" ", words);
    }
}
```

### 日志文件配置
logback.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <!-- 默认为 ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
    <encoder>
        <pattern>%date %-5level %logger{36}.%M\(%line\) -- %msg%n</pattern>
    </encoder>

    <!-- name指定<appender>的名称    class指定<appender>的全限定名  ConsoleAppender的作用是将日志输出到控制台-->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--输出时间格式 %-5level：级别从左显示5个字符宽度-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss}  %highlight%-5level %magenta([%thread]) %yellow(%logger{40}.%M\(%class{0}.java:%line\)) -- %msg%throwable%n</pattern>
        </encoder>
    </appender>
    <!-- 通过 "byModel" 将时间格式化成 "yyyyMMdd" 的形式插入到 logger 的上下文中这个值对后续的配置也适用-->
    <timestamp key="byModel" datePattern="yyyyMMdd" />

    <appender name="FILE"  class="ch.qos.logback.core.rolling.RollingFileAppender">

        <file>${byModel}.log</file>

        <!-- 配置日志所生成的目录以及生成文件名的规则-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${log.zip.path}/%d{yyyy-MM-dd}.%i.log.zip</FileNamePattern>
            <!-- 日志总保存量为1GB -->
            <totalSizeCap>1024MB</totalSizeCap>
            <!-- 如果按天来回滚，则最大保存时间为365天，365天之前的都将被清理掉 -->
            <maxHistory>30</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy
                    class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <!--文件达到 最大128MB时会被压缩和切割 -->
                <maxFileSize>128MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>

        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{40}.%M\(%class{0}.java:%line\) -- %msg%n</pattern>

        </encoder>

    </appender>

    <logger name="top.testeru" level="DEBUG" />
    <logger name="ch.qos" level="OFF" />
    <logger name="org" level="WARN" />

    <root level="INFO">
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```
## 总结
- JUnit5依赖导入的相关说明
- 被测系统对应方法说明














