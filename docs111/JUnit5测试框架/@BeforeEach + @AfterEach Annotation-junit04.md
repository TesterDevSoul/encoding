---
title: @BeforeEach + @AfterEach Annotation
date: 2022-08-29 22:14:39.978
updated: 2022-10-11 11:49:38.59
url: /archives/junit04
categories: 
- JUnit5
tags: 
- junit5
---

# @BeforeEach + @AfterEach 注解
## 本章要点
- [ ] `@BeforeEach` 注解特征
- [ ] `@AfterEach` 注解特征


## @BeforeEach
@BeforeEach注解放在方法上进行修饰，对应的也是一个没有返回值「==void==」的方法。
但是@BeforeEach注解和`@Test`注解不同的是，在方法上添加了该注解可以看到对应**idea左边没有运行的按钮**，说明@BeforeEach注解对应的方法是**不可以单独的运行**。
### 何时运行
>既然这个注解对应的方法不能单独运行，那对应的应该在什么时候运行呢？

@BeforeEach注解修饰的方法其实是在每个`@Test`注解方法前面运行。
>一个测试类中有3个@Test注解修饰的方法，则@BeforeEach注解修饰的方法就运行3次。
>但是@Test注解修饰的方法只运行一次。
### 何时使用
>既然知道了什么时候运行，那这个注解有什么作用呢？一般什么情况下我们才会用到这个注解呢？

这个主要就是看大家的业务需求了，比如我们目前这个测试系统，那我可以把生成ID放在每个测试方法之前进行生成， 这个是一个重复的代码，那我们就可以把它提取到`@BeforeEach`这个注解对应的方法内去运行，以减少我们的重复代码量。

##### @BeforeEach注解特征

1. @BeforeEach注解修饰的方法不可单独运行，若需要运行则直接运行 测试类 或 测试方法「运行特征」

2. @BeforeEach注解修饰的方法在 **每一个**`@Test`注解修饰的**方法前运行**「运行特征」

3. @BeforeEach注解修饰的方法没有返回值，即方法声明时为`void`「声明特征」

### 测试用例步骤
1. 声明被测对象
2. 被测对象初始化用例ID
3. 开始被测对象的业务逻辑
4. 获取业务逻辑结果
5. 断言

### 编写测试用例

* 1、声明被测对象 - 调用类对象，并命名为 `My Basic Test Project`
* 2、被测对象初始化用例ID - 调用类对象的初始化ID方法，`initId()`
* 3、开始被测对象的业务逻辑 - 测试用例步骤调用，`sum()`
* 4、获取业务逻辑结果 - 调用业务逻辑后赋值给`int`类型
* 5、断言 - 测试用例结果验证，JUnit5自带断言



>加法测试用例增加ID标识，每个测试用例运行前有对应的ID标识：



```java
public class BeforeEachAnnotation_Test {

    static final Logger logger = getLogger(lookup().lookupClass());

    //1、被测系统命名为 - My Basic Test Project
    //Java：对象声明可以作为成员变量添加在类下面，不使用方法
    MySUT mySUT = new MySUT("My Basic Test Project");
    //但是在类下直接使用方法调用则会报错，无法使用。
    //logger.info("Begin Sum Test");
    @BeforeEach
    public void beforeEach(){
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、调用类对象的初始化ID方法 - initId()
        mySUT.initId();
    }

    @Test
    public void sum() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(5,result);
    }

    @Test
    public void sumBoundary() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(99, -98);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(1,result);
    }
    @Test
    public void sumBoundaryError() {
        // assertThrows 抛出异常或异常的父类
        // assertThrowsExactly  抛出当前异常类
        Exception throwException = assertThrows(RuntimeException.class, () -> mySUT.sum(100, 1));
//        Exception exception = assertThrowsExactly(RuntimeException.class, () -> mySUT.sum(100, 1));
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(100, 1));
        assertTrue(exception.getMessage().contains("enter an integer in the range"));
    }


    /**
    @Test
    public void sum() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、调用类对象的初始化ID方法 - initId()
        mySUT.initId();
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(5,result);
    }

    @Test
    public void sumBoundary() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、调用类对象的初始化ID方法 - initId()
        mySUT.initId();
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(99, -98);
        //5、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //6、测试用例结果验证
        assertEquals(1,result);
    }
    @Test
    public void sumBoundaryError() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、调用类对象的初始化ID方法 - initId()
        mySUT.initId();
        //4、测试用例步骤调用 - sum()
        // assertThrows 抛出异常或异常的父类
        // assertThrowsExactly  抛出当前异常类
        Exception throwException = assertThrows(RuntimeException.class, () -> mySUT.sum(100, 1));
//        Exception exception = assertThrowsExactly(RuntimeException.class, () -> mySUT.sum(100, 1));
        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> mySUT.sum(100, 1));
        assertTrue(exception.getMessage().contains("enter an integer in the range"));
    }
    */
}
```

### 减法练习
- 编写减法的正向测试用例、边界值测试用例


```java
    @Test
    void subtractTest1(){
        logger.info("Begin Subtract Test");
        //减法运算
        int subtract = mySUT.subtract(5, 8);
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(-3,subtract);
    }
    @Test
    void subtractTest2(){
        logger.info("Begin Subtract Test");
        //减法运算
        int subtract = mySUT.subtract(99, 98);
        logger.info("Operation result：{}",subtract);
        // expected:期望值,  actual:运算的实际值
        assertEquals(1,subtract);
    }
    @Test
    void subtractTest3(){
        logger.info("Begin Subtract Test");
        //超过边界值的加法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
    }

```

## @AfterEach
`@AfterEach`这个注解和`@BeforeEach`注解类似，使用上相同但是运行时间不同。

- `@BeforeEach`注解修饰的方法是在每一个@Test注解修饰的测试方法**之前**运行；
- `@AfterEach`注解修饰的方法是在每一个@Test注解修饰的测试方法**之后**运行。

##### @AfterEach注解特征

1. @AfterEach 注解修饰的方法不可单独运行，若需要运行则直接运行 测试类 或 测试方法「运行特征」

2. @AfterEach 注解修饰的方法在 **每一个**`@Test`注解修饰的**方法后运行**「运行特征」

3. @AfterEach 注解修饰的方法没有返回值，，即方法声明时为`void`「声明特征」

4. `@Test`注解对应的测试方法无论断言是否成功，@AfterEach 注解修饰的方法里的代码都是运行的「运行特征」
### 测试用例步骤
1. 声明被测对象
2. 被测对象初始化用例ID
3. 开始被测对象的业务逻辑
4. 获取业务逻辑结果
5. 断言
6. 被测对象销毁用例ID

### 编写测试用例

* 1、声明被测对象 - 调用类对象，并命名为 `My Basic Test Project`
* 2、被测对象初始化用例ID - 调用类对象的初始化ID方法，`initId()`
* 3、开始被测对象的业务逻辑 - 测试用例步骤调用，`sum()`
* 4、获取业务逻辑结果 - 调用业务逻辑后赋值给`int`类型
* 5、断言 - 测试用例结果验证，JUnit5自带断言
* 6、被测对象销毁用例ID - 调用类对象的销毁ID方法，`destroyId()`




>加法测试用例增加`ID`标识，每个测试用例运行后把对应的`ID`标识销毁：「类似线程占用，空闲了则销毁」


```java
public class AfterEachAnnotation_Test {

    static final Logger logger = getLogger(lookup().lookupClass());

    //1、被测系统命名为 - My Basic Test Project
    //Java：对象声明可以作为成员变量添加在类下面，不使用方法
    MySUT mySUT = new MySUT("My Basic Test Project");
    //但是在类下直接使用方法调用则会报错，无法使用。
    //logger.info("Begin Sum Test");
    @BeforeEach
    public void beforeEach(){
        //2、调用类对象的初始化ID方法 - initId()
        mySUT.initId();

    }

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

    @AfterEach
    public void afterEach(){
        //6、调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
    }

    /**

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
        //7、调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
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
        //7、调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
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
        //7、调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
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
        //7、调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
    }
    @Test
    public void subtractBoundaryError(){
        logger.info("Begin Subtract Test");
        //超过边界值的减法运算
        Exception illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> mySUT.subtract(100, -98));
        // expected:期望值,  actual:运算的实际值
        assertTrue(illegalArgumentException.getMessage().contains("enter an integer in the range"));
        //7、调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
    }
    */
}

```
## 总结
- 声明特征
	- @BeforeEach、@AfterEach 注解修饰的方法没有返回值，，即方法声明时为`void`
- 运行特征
	- @BeforeEach、@AfterEach 注解修饰的方法不可单独运行，若需要运行则直接运行 测试类 或 测试方法
	- @BeforeEach、@AfterEach 注解修饰的方法在 **每一个**`@Test`注解修饰的**方法前、后运行**

	- `@Test`注解对应的测试方法无论断言是否成功，@AfterEach 注解修饰的方法里的代码都是运行的
