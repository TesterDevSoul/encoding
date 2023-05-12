# JUnit5基本注解 - @Test注解
## 本章要点
1. @Test 安装
1. @Test 运行原理
1. @Test 使用
1. @Test 与main()区别
1. @Test 计算器实战

## @Test注解
这个注解放在方法上，对应的方法就称为测试方法。

### 安装

```xml
<properties>
    <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
    <maven.compiler.version>3.8.1</maven.compiler.version>
    <maven-surefire-plugin.version>3.0.0-M7</maven-surefire-plugin.version>
    <java.version>17</java.version>
    <!-- 对应junit Jupiter的版本号;
    放在这里就不需要在每个依赖里面写版本号，导致对应版本号会冲突 -->
    <junit.jupiter.version>5.9.1</junit.jupiter.version>
    <!-- log日志 -->
    <slf4j.version>2.0.5</slf4j.version>
    <logback.version>1.4.5</logback.version>
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
                <!-- 设置jre版本为 17 -->
                <source>${java.version}</source>
                <target>${java.version}</target>
                <!-- 设置编码为 UTF-8 -->
                <encoding>${maven.compiler.encoding}</encoding>
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>${maven-surefire-plugin.version}</version>
            <configuration>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.junit.jupiter</groupId>
                    <artifactId>junit-jupiter-engine</artifactId>
                    <version>${junit.jupiter.version}</version>
                </dependency>
                <dependency>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                    <version>${junit.jupiter.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

### 运行原理
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230104121305.png)

代码运行工具「IDE」运行`@Test`注解修饰的测试用例时通过`JUnit5`框架的`junit-jupiter`组件运行。


`@Test`注解其实就是告诉 `JUnit5` 框架需要运行哪些测试方法。

### @Test 使用
`@Test`注解修饰的方法相当于声明的**测试用例**。
**@Test注解修饰的方法可以直接运行**，在Idea中可以看到添加完`@Test`注解后，对应的左边出现了可运行的绿色按钮，点击该按钮就可运行对应的方法。

1. 编写类名一般以`Test`开头或结尾。「类名规范」
1. `@Test`注解导包为`org.junit.jupiter.api.Test`。「注解导包」
1. `@Test`注解是方法上的注解。「注解位置」
    >`@Test`注解修饰方法时，放在方法上方。
1. `@Test`修饰的方法没有**返回值**，即返回值类型为`void`。「注解修饰方法声明规则」
1. `@Test`注解修饰的方法可直接运行。
1. 一个测试类里可以有多个`@Test`注解修饰的方法。「注解个数」
1. `@Test`注解修饰的方法内编写的内容为：测试用例执行的具体内容及断言结果。「用例编写」
1. `@Test`注解修饰的方法有多个时，运行顺序为方法名的执行顺序。「注解修饰方法运行顺序」

### Hello @Test
```java
import org.junit.jupiter.api.Test;

public class An_01Test_Test {
    @Test
    public void one(){
        System.out.println("第一个测试用例");
    }
}
```  
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230104122217.png)

注意⚠️：
- 导包不要导错，不要导其他框架的`Test`包。
#### IDEA运行
由于目前测试类下只有一个测试用例，则可以直接点**类左侧的运行**按钮，同样，也可以直接点击`@Test`注解修饰的方法的左侧的运行按钮。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230104143613.png)


### 与Java的main()区别
我们可以把它看作一个类似`Java`中`main`方法的入口，但是又和`main`方法有区别，对应的一个`Java`类中只能有一个`main`方法，但是`@Test`注解可以在一个`Java`类中有多个。

同样由于对应的和`main`方法类似，都是没有返回值「==void==」的方法。

- Class类中**方法个数不同**
  - Java中的`main()`方法在一个Class类中只能有**一**个
  - `@Test`注解修饰的方法在一个Class类中可以有**多**个
- Class类中**运行方式不同**
  - `main()`方法运行相当于直接运行类
  - 可以单独的运行每一个@Test注解修饰的方法，互不影响



### 计算器测试用例
#### 被测系统
- `sum()`：加法，连续加
  - 可以传入参数为单个`int`也可以为数组`int[]`
  - 如果传入的参数为**100**，报错
    - 错误类型：`NumberFormatException`
    - 错误信息：`Enter an integer is 100！`
  - 如果传入的参数 **>99** 或者 **<-99**，报错
    - 错误类型：`IllegalArgumentException`
    - 错误信息：`Please enter an integer in the range!`
```java
public class MySUT {
    //获得具有所需名称的记录器
    static final Logger logger = getLogger(lookup().lookupClass());

    String name;//用例名

    public MySUTOne(String name) {
        this.name = name;
        logger.info("Open {} ", name);
    }

    //连续添加
    public int sum(int... numbers) {
        if(Arrays.stream(numbers).anyMatch(u -> u == 100) | Arrays.stream(numbers).anyMatch(u -> u == -100)){
            //
            logger.warn("Enter an integer is 100！");
            throw new NumberFormatException("Enter an integer is 100！");
        }else if (Arrays.stream(numbers).anyMatch(u -> u > 99) |
                  Arrays.stream(numbers).anyMatch(u -> u < -99)){
            // 请输入范围内的整数
            logger.warn("Please enter an integer in the range!");
            throw new IllegalArgumentException("Please enter an integer in the range!");
        }else {
            return IntStream.of(numbers).sum();
        }
    }
}
```

#### 加法正向测试用例
##### 步骤
1. 被测系统计算器创建并命名：`My Basic Test Project`
2. 日志打印开始测试：`Begin Sum Test`
3. 测试用例步骤调用
4. 日志打印计算结果：`Sum Result：`

##### 代码
```java
    @Test
    public void sum() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
    }
```
点击测试方法左侧运行按钮，只运行当前测试方法，输出结果如下：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230104145755.png)

>可以创建多个测试方法sum，分别为:sum1()、sum2()、sum3()、sum4()<br>
>运行顺序为：sum1()、sum2()、sum3()、sum4()；与方法所在前后位置无关
## 总结
- `@Test`注解使用
  1. `@Test`注解修饰的方法可直接运行「`@Test`注解 是方法上的」
  2. 一个测试类里可以有多个`@Test`注解修饰的方法
  3. `@Test`注解作用类似`Java`代码中的`main()`方法入口，但是还是有区别
  4. `@Test`注解修饰的方法没有返回值，即方法声明时为`void`
  5. `@Test`注解里面编写的内容是测试用例执行的具体内容及断言结果
- 导包不要导错误

[项目演示地址](https://github.com/TesterDevSoul/Tutorials/blob/master/junit5/junit5-basic/src/test/java/top/testeru/basic/An_01Test_Test.java)


