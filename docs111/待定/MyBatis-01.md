学习思路就是，首先明白JBDC框架存在哪些问题，我们在这个问题的基础上来提供相关的解决思路，在这个基础上完成一个自定义的持久层框架的编写。

# MyBatis
本章要点
- ORM概念
- MyBatis概念

## What is ORM
`ORM`(`Object Relationl Mapping`)，对象关系映射，即在==数据库== 和==对象==之间作映射处理。
如果使用**ORM框架**代替 `JDBC` 后，框架可以自动进行数据库的值与实体类之间的转换，只要像平时一样操作对象，`ORM`框架就会根据`映射`完成对数据库的操作，极大的增强了开发效率。


- 结论：
    `ORM`框架可以完成 ==对象和数据库== 数据之间的转换


## What is MyBatis



- `MyBatis`是一个**半自动的ORM框架**。
- 本质底层是对`JDBC`的封装。
- 使用 `MyBatis` 不需要写`JDBC`代码，但需要写`SQL`语句。
>之前是 apache的一个开源项目iBatis，2010年改名为MyBatis。


##### Hibernate 与 MyBatis 
- `MyBatis`是一个半自动的`ORM`框架，需要手写`SQL`语句。 - `Hibernate`是一个全自动的`ORM`框架，不需要手写`SQL`语句。 
- 使用`MyBatis`的开发量要大于`Hibernate`。
## MyBatis 入门案例
### 前提
#### 数据源准备
- 数据库准备好对应的表及表内有测试数据
```
-- auto-generated definition
create table user
(
    id      int auto_increment
        primary key,
    name    varchar(255) null,
    sex     varchar(255) null,
    address varchar(255) null
)
    charset = utf8mb3;


insert  into `user`(`id`,`name`,`sex`,`address`) values (1,'张三','男','北京'),(2,'李斯','男','上海'),(3,'王武','女','广州'),(4,'陈一凡','男','北京'),(5,'王自然','男','太原'),(6,'刘迪尼','男','西安');
```
#### 创建maven项目
#### 导入依赖
- `MyBatis`依赖
>要使用`MyBatis`框架进行`jdbc`操作，所以，要有该框架的相关依赖，才能进行使用。
- `sql`驱动依赖包
>一般情况要操作的数据库为`mysql`的数据库，操作数据库底层也是需要使用的`jdbc`。`MyBatis`只是对`jdbc`底层进行了一层包装处理

```xml
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
        <java.version>11</java.version>
        <!-- 使用 Java 11 语言特性 ( -source 11 ) 并且还希望编译后的类与 JVM 11 ( -target 11 )兼容，您可以添加以下两个属性，它们是默认属性插件参数的名称-->
        <maven.compiler.target>11</maven.compiler.target>
        <maven.compiler.source>11</maven.compiler.source>
        <!-- 对应junit Jupiter的版本号;放在这里就不需要在每个依赖里面写版本号，导致对应版本号会冲突-->
        <junit.jupiter.version>5.8.2</junit.jupiter.version>
        <!-- plugins -->
        <maven.compiler.version>3.8.1</maven.compiler.version>
        <maven-surefire-plugin.version>3.0.0-M5</maven-surefire-plugin.version>
        <!-- 断言-->
        <hamcrest.version>2.2</hamcrest.version>
        <!--log日志-->
        <slf4j.version>2.0.0-alpha7</slf4j.version>
        <logback.version>1.3.0-alpha16</logback.version>
        <mysql.version>8.0.30</mysql.version>
        <mybatis.version>3.5.10</mybatis.version>
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
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <!--对应添加的依赖的作用范围-->
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-suite</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <scope>test</scope>
        </dependency>
        <!--hamcrest断言-->
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest</artifactId>
            <version>${hamcrest.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-core</artifactId>
            <version>${hamcrest.version}</version>
            <scope>test</scope>
        </dependency>
        <!-- log日志 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>${logback.version}</version>
        </dependency>
        <!-- mysql 驱动的jar包 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>${mybatis.version}</version>
        </dependency>

    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven-surefire-plugin.version}</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.version}</version>
                <configuration>
                    <parameters>true</parameters>
                    <!-- 设置jre版本为 11 -->
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target}</target>
                    <!-- 设置编码为 UTF-8 -->
                    <encoding>${maven.compiler.encoding}</encoding>
                </configuration>
            </plugin>


        </plugins>
    </build>
</project>
```


#### 核心配置文件创建
- 创建`mybatis`核心配置文件`SqlMapConfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 配置环境 -->
    <environments default="mysql">
        <environment id="mysql">
            <!-- 事务类型 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 数据源 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://81.70.96.121:3307/tutorial?characterEncoding=utf-8"/>
                <property name="username" value="testeru"/>
                <property name="password" value="testerutop"/>
            </dataSource>
        </environment>
    </environments>
</configuration>
```
##### 问题
对应标签标红，代表该链接没有下载下来：
```
"http://mybatis.org/dtd/mybatis-3-config.dtd"
```
##### 解决方案
- idea配置相关链接，进行对应的内容下载
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/mybatis/202208012040676.png)

##### 配置文件解读
- `configuration`为根标签
>`<!DOCTYPE configuration PUBLIC ...` DOCTYPE后声明的为当前xml文件的根标签内容
- `environments`配置数据库的环境
>一个数据库环境可以有多个链接组成`environment`标签。默认指定一个`id`名称。
>数据库的环境根据不同的代码部署的环境，指定的数据库连接是不同的。比如：测试环境使用的是qa，灰度环境使用的`dev`，线上环境使用的是`master`。那就可以有多个
- `environment`为一个环境的连接信息

- `transactionManager`事务类型，MyBatis使用的是JDBC的事务连接
- `dataSource`配置连接数据库的具体信息，使用的是数据连接池来进行连接。

```xml
    <!-- 配置环境 -->
    <environments default="dev">
        <environment id="dev">
            <!-- 事务类型 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 数据源 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://81.70.96.121:3307/tutorial?characterEncoding=utf-8"/>
                <property name="username" value="testeru"/>
                <property name="password" value="testerutop"/>
            </dataSource>
        </environment>
        <environment id="qa">
            <!-- 事务类型 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 数据源 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/tutorial?characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
        <environment id="master">
            <!-- 事务类型 -->
            <transactionManager type="JDBC"></transactionManager>
            <!-- 数据源 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://81.70.110.121:3307/tutorial?characterEncoding=utf-8"/>
                <property name="username" value="mastertop"/>
                <property name="password" value="top"/>
            </dataSource>
        </environment>
    </environments>
```
#### log日志配置
- 将`log4j.properties`文件放入`resources`中，让控制台打印`SQL`语句。
#### 创建实体类
```java
public class User {
    private int id;
    private String name;
    private String sex;
    private String address;
    //get set toString constructor
}
```

## 总结
#### MyBatis必须添加的依赖
- `MyBatis`依赖，`Mysql`驱动
#### 数据源配置内容
- `driver`,`url`,`username`,`password`