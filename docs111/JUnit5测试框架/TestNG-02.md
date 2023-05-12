---
notebook: TestNG
title: 2.TestNG参数化
tags: Xunit
---
### 参数化
#### `Parameters from testng.xml`

- 必须结合`testng.xml`

##### 单个参数
###### 单个参数正常传递
- Java文件
```java
package com.tester.testng;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description: 参数化
 * @create: 2021-10-16 14:54
 */
public class TestNGParameters {
    //    如果只有一个参数，则给个双引号

    @Test
    @Parameters("type")
    public void test(String value){
        System.out.println("value:"+value);
    }
}

```
- `testng.xml`
```xml
<?xml version="1.0" encoding="utf-8" ?>
<!--suite 套件/项目-->
<suite name="***" parallel="false">
<!--    模块-->
    <test name="sampleTest">
        <classes>
<!--           测试类/测试用例类-->
            <class name="com.tester.testng.TestNGParameters"></class>
        </classes>
    </test>

<!--    name和value是可以随意取的-->
    <parameter name="type" value="firefox"/>
</suite>
```
![](https://gitee.com/testeru/pichub/raw/master/images/202110161504450.png)
![](https://gitee.com/testeru/pichub/raw/master/images/202110161457073.png)

###### 单个参数找不到并附默认值

- testng01.xml,parameter声明在suite内
```xml
<?xml version="1.0" encoding="utf-8" ?>
<!--suite 套件/项目-->
<suite name="***" parallel="false">
    <parameter name="db" value="mysql"/>
<!--    模块-->
    <test name="sampleTest">
        <classes>
<!--           测试类/测试用例类-->
            <class name="com.tester.testng.TestNGDemo4"></class>
        </classes>
    </test>
</suite>
```
- Java文件
```java
public class TestNGDemo4 {
    @Test
    @Parameters("1db")
    public void test1(@Optional("mysql1") String db){
        System.out.println(db);
    }
}
```
![](https://gitee.com/testeru/pichub/raw/master/images/202110171515240.png)

- `@Optional`如果没有找到,则用声明的默认值,如果找到,则用找到的值

##### 多个参数
- 参数为多个的时候则为列表
```java
package com.tester.testng;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description: 参数化
 * @create: 2021-10-16 14:54
 */
public class TestNGParameters02 {
    //    如果只有一个参数，则给个双引号

    @Test
    @Parameters({"type","version"})
    public void test(String type,String version){
        System.out.println("value:"+type);
        System.out.println("version:"+version);

    }
}

```
- testng2.xml
```xml
<?xml version="1.0" encoding="utf-8" ?>
<!--suite 套件/项目-->
<suite name="***" parallel="false">
<!--    模块-->
    <test name="sampleTest">
        <classes>
<!--           测试类/测试用例类-->
            <class name="com.tester.testng.TestNGParameters02"></class>
        </classes>
    </test>

<!--    name和value是可以随意取的-->
    <parameter name="type" value="firefox"/>
    <parameter name="version" value="1.0"/>
</suite>
```
![](https://gitee.com/testeru/pichub/raw/master/images/202110161546989.png)


##### 总结
- `testng.xml`定义`parameter`
    配合`@Parameters`注解一起使用
- 局限性:
    - 通过`Parameters`注入的参数必须定义在`testng.xml`中
- `@Parameters`注解:
    - 1.有这些注解的方法上:`@Test`, `@Before/After` , `@Factory`
    - 2.`@Optional("mysql1")`方法参数前声明默认值,只有在找不到的时候才生效
- `@Parameters`参数是有范围限制的
    - `testng.xml`的`<suite>`或`<test>`标签下声明.
    - 如果在`<suite>`或`<test>`标签下对应的参数名相同,则最先拿到的是`<test>`标签下定义的参数值.



#### `DataProvider`
重点掌握
- 参数化,不需要依赖testng.xml
- 注解的方法返回值必须是:`Object[][]`或者`Iterator<Object[]>`
- 参数个数、类型必须匹配

##### 基本数据类型传递
```java
package com.tester.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description:
 * @create: 2021-10-16 16:03
 */
public class TestNGDataProvider {

    @Test(dataProvider = "data1")
    public void testLogin(String username,String pwd){
        System.out.println("username:"+username);
        System.out.println("password:"+pwd);
    }
    @DataProvider(name = "data1")
    public Object[][] datas(){
//        Object[][] datas = new Object[3][2];
//        Object[][] datas = new Object[test方法执行次数][test方法参数个数];

        Object[][] datas = {
//                参数类型不能错
//                参数个数不能错，不能传成三、四个 
                {"张三","123"},//二维数组对长度决定test方法执行几次
                {"李四","123"},//二维数组中一位数组对长度就是test方法的参数个数
                {"王武","123"}

        };
        return datas;
    }
}
```

![](https://gitee.com/testeru/pichub/raw/master/images/202110161608066.png)

##### 对象传递参数

实体类
```java
package com.tester.dto;

import lombok.Data;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description:
 * @create: 2021-10-16 16:24
 */
@Data
public class Student {
    private Integer id;
    private String username;
    private Double price;

    public Student(Integer id, String username, Double price) {
        this.id = id;
        this.username = username;
        this.price = price;
    }
}
```

```java
package com.tester.testng;

import com.tester.dto.Student;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @program: testng_demo
 * @author: 盖盖
 * @description:
 * @create: 2021-10-16 16:03
 */
public class TestNGDataProvider02 {
    @Test(dataProvider = "stu1")
    public void testLogin(Student student){
        System.out.println("Student:"+student);
    }
    @DataProvider(name = "stu1")
    public Object[][] datas(){
        Student student1 = new Student(1,"张三",8.0);
        Student student2 = new Student(2,"李四",6.0);
        Student student3 = new Student(3,"王五",810.0);
        Object[][] datas = {
                {student1},
                {student2},
                {student3}
        };
        return datas;
    }
}

```
![](https://gitee.com/testeru/pichub/raw/master/images/202110161639506.png)

##### 总结
- `DataProvider`注解标注一个方法,注解需指定一个`name`属性.
方法返回值为一个`Object`类型的二维数组.
- 注入`DataProvider`的数据,test方法必须声明`DataProvider`属性,值为上面定义的name属性
- `DataProvider`,注解上没有声明对应的`name`,则默认为声明的方法名.
