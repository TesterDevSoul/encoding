---
title: JUnit5 Actual Combat
date: 2022-10-12 19:43:19.533
updated: 2022-10-12 19:58:08.654
url: /archives/junit100
categories: 
- JUnit5
tags: 
- junit5
---

# JUnit5 实战

xUnit

是什么?

* 几个单元测试框架统称
结构

所有 xUnit 框架都共享以下基本组件架构，以及一些不同的实现细节。
* Test Runner
    * 一个测试运行器是一个可执行程序，运行测试使用实现的xUnit框架，并上报测试结果
* Test Case
    * 一个测试用例是最基本的类。所有单元测试都是从这里继承的。
* Test Fixtures
    * 测试上下文是一组前提条件运行一个测试需要或状态
    * 开发者应在测试前建立一个已知良好的状态，并在测试后返回到原始状态。
* Test Suites
    * 一个测试套件是一套测试，都有着相同的上下文
* Test Execution
    * 测试用例执行顺序
    * 单个单元测试的执行过程如下：
        * beforeAll() 和 afterAll() 方法用于初始化和清理测试装置
* Test Result Formatter
    * 测试运行产生一个或多个输出格式的结果。
* Assertions
    * 断言。通常，断言表示一个逻辑条件，该条件对于正确运行的预期结果为真。断言失败通常会引发异常，中止当前测试的执行。
框架体系

java

* Junit4
* TestNG
* Junit5
python

* unittest
* pytest
* 几乎所有的语言都有xUint的实现
为什么用junit5不用junit4?

* junit4是研发首先框架,springboot的测试框架集成的是junit4
* testng 大部分是测试工程师
* junit5 又做单测又做集成
* 用例之间单独运行



JUnit5

架构-组成

JUnit Platform

* 作为一个基础发射测试框架在JVM上
* 定义了TestEngine用于开发在平台上运行的测试框架的API
* 提供了一个 控制台启动器来从命令行和JUnit 平台套件引擎启动平台
JUnit Jupiter

* Junit API
* 木星是距离太阳第五近的行星；所以写测试相关代码的时候Jupiter就是你要交互的api
* 在 JUnit 5 中编写测试和扩展子项目提供了一个TestEngine用于在平台上运行基于 Jupiter 的测试
JUnit Vintage

* junit不是开箱即用向老版本兼容的，但是提供了一些API可以向老版本兼容，这样就不必将旧版本的测试用例 弃用/重写他们
* 提供了一个TestEngine用于在平台上运行基于 JUnit 3 和 JUnit 4 的测试
* 要求类路径或模块路径中存在 JUnit 4.12 或更高版本 另一组API；运行老版本
如果有junit4的代码和junit5的代码，同时在一个项目里运行，则新的测试Junit5的代码使用的是JUnit Jupiter，旧版本的使用的是JUnit Vintage

JUnit Jupiter

* JUnit的新编程模型{New programming model in JUnit}
* 扩展模型{Extension model}
    * 不是第三方扩展，
* JUnit5主要从事的工作{What you'll be primarily working with}



![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210121956613.png)





```java
package testeru;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import top.testeru.MySUT;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月11日 20:12:00
 */
public class BaseTest {
    public static final Logger logger = getLogger(lookup().lookupClass());
    public static MySUT mySUT;


    @BeforeAll
    public static void beforeAll(){
        mySUT = new MySUT("My Basic Test Project");

    }

    private static void cleanLog() {
        System.out.println(System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir"));
        File[] files = file.listFiles();
        //1.tostring 结尾是endwith .txt 说明是日志文件，那就删除
        Arrays.stream(files).filter(file1 -> file1.toString().endsWith(".log")).collect(Collectors.toList())
                .forEach(file1 -> {
                    System.gc();
                    file1.delete();
                });
    }

    @BeforeEach
    public void beforeEach(){
        mySUT.initId();
    }


    @AfterEach
    public void afterEach(){
        mySUT.destroyId();
    }

    @AfterAll
    public static void afterAll(){
        mySUT.close();
        //清除log日志
        cleanLog();
    }
}

```


```java
import org.junit.jupiter.api.Test;
import testeru.BaseTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MySUT_Test extends BaseTest {
    // --- sum ---
    @Test
    public void sum() {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(5,result);

    }

    @Test
    public void sumBoundary() {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(99, -98);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(1,result);
    }
    @Test
    public void sumBoundaryError() {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        // assertThrows 抛出异常或异常的父类
        // assertThrowsExactly  抛出当前异常类
        Exception throwException = assertThrows(RuntimeException.class, () -> mySUT.sum(100, 1));
//        Exception exception = assertThrowsExactly(RuntimeException.class, () -> mySUT.sum(100, 1));
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(100, 1));
        assertTrue(exception.getMessage().contains("enter an integer in the range"));
    }


// --- subtract ---
    @Test
    public void subtract(){
        //3、打印日志 - Begin Subtract Test
        logger.info("Begin Subtract Test");
        //4、测试用例步骤调用 - subtract() 减法运算
        int subtract = mySUT.subtract(5, 8);
        //5、打印结果日志 - Operation Result
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(-3,subtract);
    }
    @Test
    public void subtractBoundary(){
        logger.info("Begin Subtract Test");
        //4、测试用例步骤调用 - subtract() 减法运算
        int subtract = mySUT.subtract(99, 98);
        //5、打印结果日志 - Operation Result
        logger.info("Operation result：{}",subtract);

        // expected:期望值,  actual:运算的实际值
        assertEquals(1,subtract);
    }
    @Test
    public void subtractBoundaryError(){
        logger.info("Begin Subtract Test");
        //超过边界值的减法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
    }
}
```






```java
package testeru.basic.basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testeru.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 14:06:00
 */
public class MySUT_Param_Test extends BaseTest {
    // --- sum ---
    @ParameterizedTest
    @MethodSource("sumData")
    public void sum(int num1, int num2, int re) {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(num1, num2);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(re,result);

    }
    static Stream<Arguments> sumData() {
        return Stream.of(
                arguments(4, 1, 5),
                arguments(99, -99, 0),
                arguments(-99, -99, -198)
        );
    }


    @Test
    public void sumBoundaryError() {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        // assertThrows 抛出异常或异常的父类
        // assertThrowsExactly  抛出当前异常类
        Exception throwException = assertThrows(RuntimeException.class, () -> mySUT.sum(100, 1));
//        Exception exception = assertThrowsExactly(RuntimeException.class, () -> mySUT.sum(100, 1));
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(100, 1));
        assertTrue(exception.getMessage().contains("enter an integer in the range"));
    }


// --- subtract ---
    @ParameterizedTest
    @MethodSource("subtractData")
    public void subtract(int num1, int num2, int re){
        //3、打印日志 - Begin Subtract Test
        logger.info("Begin Subtract Test");
        //4、测试用例步骤调用 - subtract() 减法运算
        int subtract = mySUT.subtract(num1, num2);
        //5、打印结果日志 - Operation Result
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(re,subtract);
    }

    static Stream<Arguments> subtractData() {
        return Stream.of(
                arguments(5, 8, -3),
                arguments(99, -99, 198),
                arguments(-99, -99, 0)
        );
    }

    @Test
    public void subtractBoundaryError(){
        logger.info("Begin Subtract Test");
        //超过边界值的减法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
    }
}
```











```java
package testeru.basic.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testeru.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 14:12:00
 */
@DisplayName("加减法模块测试用例")
public class MySUT_Param_Display_Test extends BaseTest {
    // --- sum ---
    @DisplayName("加法模块正向测试用例")
    @ParameterizedTest(name = "[{index}] {0} + {1} = {2}")
    //ARGUMENTS_WITH_NAMES_PLACEHOLDER：带参数的key key=value
//    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("sumData")
    public void sum(int num1, int num2, int re) {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(num1, num2);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(re,result);

    }
    static Stream<Arguments> sumData() {
        return Stream.of(
                arguments(4, 1, 5),
                arguments(99, -99, 0),
                arguments(-99, -99, -198)
        );
    }


    @Test
    @DisplayName("加法模块异常测试用例")
    public void sumBoundaryError() {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(100, 1));
        assertTrue(exception.getMessage().contains("enter an integer in the range"));
    }


    // --- subtract ---
    @DisplayName("减法模块正常测试用例")
    //ARGUMENTS_PLACEHOLDER：不带参数的key，直接是value
//    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @ParameterizedTest(name = "[{index}] {0} - {1} = {2}")
    @MethodSource("subtractData")
    public void subtract(int num1, int num2, int re){
        //3、打印日志 - Begin Subtract Test
        logger.info("Begin Subtract Test");
        //4、测试用例步骤调用 - subtract() 减法运算
        int subtract = mySUT.subtract(num1, num2);
        //5、打印结果日志 - Operation Result
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(re,subtract);
    }

    static Stream<Arguments> subtractData() {
        return Stream.of(
                arguments(5, 8, -3),
                arguments(99, -99, 198),
                arguments(-99, -99, 0)
        );
    }

    @Test
    @DisplayName("减法模块异常测试用例")
    public void subtractBoundaryError(){
        logger.info("Begin Subtract Test");
        //超过边界值的减法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
    }
}
```






```java
package testeru.basic.basic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testeru.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 14:12:00
 */
@DisplayName("算法模块测试用例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MySUT_Param_Display_Order_Test extends BaseTest {
    // --- sum ---
    @DisplayName("加法模块正向测试用例")
    @ParameterizedTest(name = "[{index}] {0} + {1} = {2}")
    //ARGUMENTS_WITH_NAMES_PLACEHOLDER：带参数的key key=value
//    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("sumData")
    @Order(1)
    public void sum(int num1, int num2, int re) {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(num1, num2);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(re,result);

    }
    static Stream<Arguments> sumData() {
        return Stream.of(
                arguments(4, 1, 5),
                arguments(99, -99, 0),
                arguments(-99, -99, -198)
        );
    }


    @ParameterizedTest
    @MethodSource("sumBoundaryErrorData")
    @DisplayName("加法模块异常测试用例")
    @Order(98)
    public void sumBoundaryError(int sum1, int sum2, String message) {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(sum1, sum2));
        assertTrue(exception.getMessage().contains(message));
    }

    static Stream<Arguments> sumBoundaryErrorData() {
        return Stream.of(
                arguments(100, 1, "enter an integer in the range"),
                arguments(200, -100, "enter an integer in the range"),
                arguments(-100, -99, "enter an integer in the range")
        );
    }
    // --- subtract ---
    @DisplayName("减法模块正向测试用例")
    //ARGUMENTS_PLACEHOLDER：不带参数的key，直接是value
//    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @ParameterizedTest(name = "[{index}] {0} - {1} = {2}")
    @MethodSource("subtractData")
    @Order(6)
    public void subtract(int num1, int num2, int re){
        //3、打印日志 - Begin Subtract Test
        logger.info("Begin Subtract Test");
        //4、测试用例步骤调用 - subtract() 减法运算
        int subtract = mySUT.subtract(num1, num2);
        //5、打印结果日志 - Operation Result
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(re,subtract);
    }

    static Stream<Arguments> subtractData() {
        return Stream.of(
                arguments(5, 8, -3),
                arguments(99, -99, 198),
                arguments(-99, -99, 0)
        );
    }

    @Test
    @DisplayName("减法模块异常测试用例")
    @Order(99)
    public void subtractBoundaryError(){
        logger.info("Begin Subtract Test");
        //超过边界值的减法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
    }
}
```











```java
package testeru.basic.num;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testeru.BaseTest;

import java.util.stream.Stream;

import static io.qameta.allure.SeverityLevel.BLOCKER;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 16:03:00
 */
@DisplayName("减法模块测试用例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubtractTest extends BaseTest {
    @DisplayName("减法模块正向测试方法")
    //ARGUMENTS_PLACEHOLDER：不带参数的key，直接是value
//    @ParameterizedTest(name = ARGUMENTS_PLACEHOLDER)
    @ParameterizedTest(name = "[{index}] {0} - {1} = {2}")
    @MethodSource("subtractData")
    @Order(6)
    public void subtract(int num1, int num2, int re){
        //3、打印日志 - Begin Subtract Test
        logger.info("Begin Subtract Test");
        //4、测试用例步骤调用 - subtract() 减法运算
        int subtract = mySUT.subtract(num1, num2);
        //5、打印结果日志 - Operation Result
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(re,subtract);
    }

    static Stream<Arguments> subtractData() {
        return Stream.of(
                arguments(5, 8, -3),
                arguments(99, -99, 198),
                arguments(-99, -99, 0)
        );
    }
  
    @Test
    @DisplayName("减法模块异常测试用例")
    @Order(99)
    public void subtractBoundaryError(){
        logger.info("Begin Subtract Test");
        //超过边界值的减法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
    }
}

```



```java
package testeru.basic.num;

import io.qameta.allure.Param;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testeru.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 16:02:00
 */
@DisplayName("加法模块测试用例")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SumTest extends BaseTest {

    @DisplayName("加法模块正向测试用例")
    @ParameterizedTest(name = "[{index}] {0} + {1} = {2}")
    @MethodSource("sumData")
    @Order(1)
    public void sum(int num1, int num2, int re) {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(num1, num2);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(re,result);

    }
    static Stream<Arguments> sumData() {
        return Stream.of(
                arguments(4, 1, 5),
                arguments(99, -99, 0),
                arguments(-99, -99, -198)
        );
    }


    @ParameterizedTest
    @MethodSource("sumBoundaryErrorData")
    @DisplayName("加法模块异常测试用例")
    @Order(98)
    public void sumBoundaryError(int sum1, int sum2, String message) {
        //3、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(sum1, sum2));
        assertTrue(exception.getMessage().contains(message));
    }

    static Stream<Arguments> sumBoundaryErrorData() {
        return Stream.of(
                arguments(100, 1, "enter an integer in the range"),
                arguments(200, -100, "enter an integer in the range"),
                arguments(-100, -99, "enter an integer in the range")
        );
    }
}

```

```
package testeru.basic.str;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testeru.BaseTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 16:07:00
 */
@DisplayName("字符串模块测试用例")
public class StrTest extends BaseTest {

    @DisplayName("字符串模块正向测试方法")
    @ParameterizedTest(name = "[{index}] {0} + {1} = {2}")
    @MethodSource("strData")
    @Order(56)
    public void str(String str1, String str2, String re) {
        logger.info("Begin concatStr Test");
        String result = mySUT.concatStr(str1, str2);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}", result);
        //6、测试用例结果验证
        assertEquals(re, result);

    }

    static Stream<Arguments> strData() {
        return Stream.of(
                arguments("Hello", "JUnit5", "Hello JUnit5"),
                arguments("北京", "欢迎您", "北京 欢迎您"),
                arguments("家乡", "Good", "家乡 Good")
        );
    }
}
```




```java
package testeru.basic.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import testeru.basic.num.SubtractTest;
import testeru.basic.str.StrTest;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 16:06:00
 */
@Suite
@SelectClasses({
        SubtractTest.class,
        StrTest.class
})
@SuiteDisplayName("套件运行")
public class SuitCalTest {
}
```




```
package testeru.basic.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import testeru.basic.num.SumTest;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 16:06:00
 */
@Suite
@SelectClasses({
        SumTest.class
})

@SuiteDisplayName("加法套件")
public class SuitCal2Test {
}

```




```java
package testeru.basic.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import testeru.basic.num.SubtractTest;
import testeru.basic.str.StrTest;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Prpject junit5-demo
 * @Description
 * @createTime 2022年10月12日 16:06:00
 */
@Suite
@SelectClasses({
        SuitCalTest.class,
        SuitCal2Test.class
})

public class SuitAllTest {
}

```





## 自定义显示名
- 5.`DisplayName`
    - 是**方法**「`ElementType.METHOD`」和**类**「`ElementType.TYPE`」上的注解；
    - 对应`value`为显示的名称
        - 可以是空格、特殊符号、表情符号  {录播课有介绍}
## 测试方法排序
- 6.使用`order`注解对测试方法排序
    - `MethodOrderer`选择排序规则
    - 2种配置方式
        - `JUnit5`配置文件：`junit-platform.properties`
        -  测试类上直接添加注解：`@TestMethodOrder`
        - 优先级：配置文件声明 **<** 类上声明排序规则


## 测试方法参数化
- 7.使用参数化进行多参数运算：`ParameterizedTest`+`*Source`「缺一不可」
    - `ParameterizedTest`
        - 不要在`@ParameterizedTest`注解的方法上添加`Test`注解「参数化注解就已经包含了test」
        - `ARGUMENTS_WITH_NAMES_PLACEHOLDER`
            - `key=value` 的map结构；`key`为方法参数名，`value`为参数值。eg:a=11,b=11,re=22
        - `ARGUMENTS_PLACEHOLDER`
            - `list`结构；直接为`value`的`list`集合。eg:11,11,22
    - `MethodSource`：提供参数源的注解
        - 参数方法与测试方法同名，则直接默认
        - 参数方法与测试方法名不同，则需要在括号内指定参数方法名



## suite套件管理测试集
- 8.使用`suite`套件运行测试集
    - `SelectClasses`
        - 选择多个测试类为测试集
    - `SuiteDisplayName`
        - 自定义对应测试集的显示名





## 标签注解
- Tags
- Tag
- 注意
  - 一个测试方法上共存：一个 Tags + Tag
  - 一个测试方法上可以有多个 Tag
  - 一个测试方法上只能有一个 Tags


- 3.最终打印所有运算结果`result`：`TestInfo`{`BeforeEach`/`AfterEach`都可以获取到}
    - 算数运算的打印`int`
    - 字符的打印`str`
