---
title: JDBC Introduction
date: 2022-08-02 14:28:43.139
updated: 2022-08-03 16:32:51.566
url: /archives/jdbc
categories: 
- sql
tags: 
- jdbc
- mysql
---

# JDBC 简介
## 本章要点
- [ ] 框架概念
- [ ] 持久层概念
- [ ] JDBC查询代码
- [ ] JDBC问题总结
- [ ] JDBC问题解决思路
# 框架
## 框架是什么？
框架就是一个半成品的软件。
开发一个软件如果是从Java最底层一行一行的代码去写，那对于开发来说要花费很大的精力，于是，其他的有开源精神的项目组开发出半成品的一个软件，让开发者在这个半成品的软件上进行快速的开发，那这个半成品的软件就是框架。
`MyBatis`就是一个框架，`SpringBoot`也是一个框架。
>就相当于我们买房子买的不是精装修，而是一个毛坯房，但是我们住进去的时候肯定是已经装修好了的，那这个毛坯房就相当于是一个框架，当我们搬进去新家的时候，这个新家就是一个可上线的软件。
>有的说我在毛坯房的基础上装修多麻烦，可否直接买一个精装修的房子，到时候买家具直接住进去了。可以的，但是这样的话，我们一些想要定制性的场景就没有办法满足。就像我们可以用通用型的软件，但是大家有没有发现，每个`app`都会有自家特色的业务，所以，为了满足公司这种特色业务，我们需要在半成品的基础上进行开发，而不是直接拿过一个通用型的软件去使用。
## 框架开发的好处
- 省去大量的代码编写、减少开发时间、降低开发难度。
- 限制开发人员的代码规范「必须使用框架规范开发」
    >增强代码的规范性，降低程序员之间沟通及日后维护的成本。
- 将程序员的注意力从技术中抽离出来，更集中在业务层面。


如果使用`JDBC`操作数据库，必须手动进行数据库和对象间的数 据转换，如下对应的原始`JDBC`操作。

# 持久层
## 持久层是什么？
`Java EE` 三层中与数据库交互的一层，持久层往往也被称为`dao`层。
持久层的技术选型有很多，绝不仅仅只有`MyBatis`一种。「这个需要给大家明确，不要说持久层的技术选型只有这一个`MyBatis`框架」 
早期给大家说基础的时候，我们直接给大家说`JDBC`进行一个数据库交互，大家如果有一些Java基础，那对于`JDBC`应该是不陌生的。
那现在有一个问题，就是 JDBC 已经能够完成数据库的交互，已经能够完成增删改查操作，为什么还有其他的框架？国内大部分的项目不直接使用JDBC而是使用MyBatis框架？

>其实是因为，我们在使用`JDBC`完成数据库交互，进行一个增删改查操作的时候，`JDBC`框架本身还是有一些问题的。也就是因为`JDBC`存在的问题，后期才会出现很多持久层框架。这些持久层框架在封装的时候，把`JDBC`存在的问题进行了规避，或者进行了解决、优化。
>`MyBatis`只是这些持久层框架其中之一。


# JDBC
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/mybatis/202208021754144.webp)
## 查询数据
`JDBC` 连接数据库进行查询操作示例：

```java
package top.testeru.jdbc;

import top.testeru.entity.User;

import java.sql.*;

/**
 * @program: mybatis
 * @author: testeru.top
 * @description: jdbc连接数据库操作
 * @Version 1.0
 * @create: 2022/8/1 12:35
 */
public class JDBCAdd {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 通过驱动管理类获取数据库链接
            connection =
                     DriverManager.getConnection("jdbc:mysql://81.70.96.121:3307/tutorial?characterEncoding=utf-8", "testeru", "testerutop");
            // 定义sql语句?表示占位符
            String sql = "select * from user where name = ?";
            // 获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数，第一个参数为sql语句中参数的序号(从1开始)，第二个参数为设置的参数值
            preparedStatement.setString(1, "张三");
            // 向数据库发出sql执行查询，查询出结果集
            resultSet = preparedStatement.executeQuery(); // 遍历查询结果集
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("name");
                // 封装User
                user.setId(id);
                user.setUsername(username);
            }
            System.out.println(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```
##### 报错
```
java.sql.SQLException: No suitable driver found for jdbc:mysql://81.70.96.121:3307/tutorial?characterEncoding=utf-8
	at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:702)
	at java.sql/java.sql.DriverManager.getConnection(DriverManager.java:228)
	at top.testeru.jdbc.JDBCAdd.main(JDBCAdd.java:24)
```
>JDBC连接代码是固定的。
1. 加载数据库驱动
>不同的数据库对应的驱动是不同的，哪怕是同一个数据库，版本不同有可能对应的驱动也不相同；
>比如`mysql`的 5.0 版本的数据库驱动和 8.0 的数据库驱动就是不同。
>使用的是`MySql`的数据库，对应驱动为`com.mysql.jdbc.Driver`
2. 通过驱动管理类 `DriverManager` 获取数据库链接
3. 编写要执行的`sql`语句
4. 获取一个预处理对象 `PreparedStatement`
5. 预处理对象进行`sql`语句的参数的设置
6. 预处理对象进行`sql`语句的执行，返回执行结果集 `ResultSet` 
7. 遍历查询结果集，取出里面的每一个属性值打印 或者 封装成某个实体类对象



##### 解决方案
-  添加 `mysql-connector-java-**.jar` 「与正在连接的 `MySQL` 数据库相对应的 `MySQL JAR`」

    ```
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.30</version>
    </dependency>
    ```

## JDBC缺点
```java
while (resultSet.next()) {
    int id = resultSet.getInt("id");
    String username = resultSet.getString("name");
    // 封装User
    user.setId(id);
    user.setUsername(username);
}
```
- 手动将结果集的内容转为对象
>无论是增加还是查找，都必须手动进行数据库的值与实体类之间的转换。
>`JDBC`代码中，数据库数据与对象数据的转换代码繁琐、无技术含量。

## JDBC问题总结
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/mybatis/202208011925783.png)
原始`jdbc`开发存在的问题如下:
1. 数据库连接创建、释放频繁造成系统资源浪费，从而影响系统性能。
2. `Sql`语句在代码中硬编码，造成代码不易维护，实际应用中`sql`变化的可能较大，`sql`变动需要改变 `java`代码。
3. 使用`preparedStatement`向占有位符号传参数存在硬编码，因为`sql`语句的`where`条件不一定，可能 多也可能 少，修改`sql`还要修改代码，系统不易维护。
4.  对结果集解析存在硬编码(查询列名)，`sql`变化导致解析代码变化，系统不易维护，如果能将数据 库记录封装成 `pojo`对象解析比较方便
## 问题解决思路
1. 使用数据库连接池初始化连接资源
2. 将`sql`语句抽取到`xml`配置文件中 
3. 使用反射、内省等底层技术，自动将实体与表进行属性与字段的自动映射




# 总结
- 框架 就是一个半成品的软件
- 持久层 就是与数据库交互的那一层
- `JDBC` 连接数据库步骤
  1. 加载数据库驱动
  2. 通过驱动管理类 `DriverManager` 获取数据库链接
  3. 编写要执行的`sql`语句
  4. 获取一个预处理对象 `PreparedStatement`
  5. 预处理对象进行`sql`语句的参数的设置
  6. 预处理对象进行`sql`语句的执行，返回执行结果集 `ResultSet` 
  7. 遍历查询结果集，取出里面的每一个属性值打印 或者 封装成某个实体类对象

- JDBC问题
  1. 数据库连接创建、释放频繁。
  2. `Sql`语句在代码中硬编码。
  3. 使用`preparedStatement`向占有位符号传参数存在硬编码。
  4.  对结果集解析存在硬编码(查询列名)。