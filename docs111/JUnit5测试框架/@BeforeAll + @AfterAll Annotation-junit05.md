---
title: @BeforeAll + @AfterAll Annotation
date: 2022-10-10 16:13:56.044
updated: 2022-10-11 14:22:02.578
url: /archives/junit05
categories: 
- JUnit5
tags: 
- junit5
---

# @BeforeAll + @AfterAll 注解
## 本章要点
- [ ] `@BeforeAll` 注解特征
- [ ] `@AfterAll` 注解特征

## @BeforeAll
@BeforeAll 注解放在方法上进行修饰，对应的也是一个没有返回值「`void`」的方法。
但是@BeforeAll注解，在方法上添加了该注解可以看到对应idea左边没有运行的按钮，说明@BeforeEach注解对应的方法是不可以单独的运行。

@BeforeAll 注解放在方法上进行修饰，对应的也是一个没有返回值「==void==」的方法。
### 与@Test不同
但是@BeforeAll 注解和`@Test`注解不同的是，在方法上添加了该注解可以看到对应**idea左边没有运行的按钮**，说明@BeforeEach注解对应的方法是**不可以单独的运行**。
### 与@BeforeEach不同
但是@BeforeAll 注解和`@BeforeEach`注解不同的是，运行顺序不同，以及声明不同。

@BeforeAll 注解修饰的必须是静态方法，即方法有static关键字，如果方法声明上未添加该关键字，则会报错，模版如下：
```
org.junit.platform.commons.JUnitException: @BeforeAll method 'public void 包名.类名.方法名' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
```
@BeforeEach注解修饰的方法是在每个`@Test`注解方法前面运行，但是 @BeforeAll 注解修饰的是静态方法，是在所有的方法之前运行一次。由于static关键字特性造成。

>一个测试类中有3个@Test注解修饰的方法，则@BeforeEach/@AfterEach注解修饰的方法就运行3次。
>但是@Test注解修饰的方法只运行一次。
> @BeforeAll 注解修饰的方法，在所有方法的最前面运行一次。

运行顺序为：
```
@BeforeAll   -->   
    @BeforeEach   -->   @Test   -->   @AfterEach  -->   
    @BeforeEach   -->   @Test   -->   @AfterEach  -->   
    @BeforeEach   -->   @Test   -->   @AfterEach  -->   
```
### 何时使用
>既然知道了什么时候运行，那这个注解有什么作用呢？一般什么情况下我们才会用到这个注解呢？

这个主要就是看大家的业务需求了，比如我们目前这个测试系统，那我可以把生成测试系统的对象放到`@BeforeAll`这个注解对应的方法内去运行，确保只声明一次该对象。
##### @BeforeAll 注解特征

1. @BeforeAll 注解修饰的方法需要`static`关键字修饰「声明特征」

2. @BeforeAll 注解修饰的方法不可单独运行，若需要运行则直接运行 测试类 或 测试方法「运行特征」

3. @BeforeAll 注解修饰的方法在 **所有的方法前运行**，即包含@BeforeEach修饰的方法之前运行「运行特征」

4. @BeforeAll 注解修饰的方法没有返回值，即方法声明时为`void`「声明特征」

5. @BeforeAll 注解修饰的方法在一个类中只有一个时，运行测试类时，只运行一次「运行特征」


### 测试用例步骤
1. @BeforeAll 注解内声明被测对象
2. 被测对象初始化用例ID
3. 开始被测对象的业务逻辑
4. 获取业务逻辑结果
5. 断言
6. 被测对象销毁用例ID
### 编写测试用例

```java
public class BeforeAllAnnotation_Test {

    static final Logger logger = getLogger(lookup().lookupClass());
    MySUT mySUT;

    //但是在类下直接使用方法调用则会报错，无法使用。
    //logger.info("Begin Sum Test");

    @BeforeAll
    public void beforeAll(){
        //1、被测系统命名为 - My Basic Test Project
        mySUT = new MySUT("My Basic Test Project");
    }
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
}
```
如果`@BeforeAll`注解修饰的方法没有使用static关键字，则运行时会报错，详细信息如下：
```
org.junit.platform.commons.JUnitException: @BeforeAll method 'public void top.testeru.basic.BeforeAllAnnotation_Test.beforeAll()' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
```


## @AfterAll
`@AfterAll`这个注解和`@BeforeAll`注解类似，使用上相同但是运行时间不同。

- `@BeforeAll`注解修饰的方法是在**所有方法之前**运行，且只运行一次；
- `@AfterAll`注解修饰的方法是在**所有方法之后**运行，且只运行一次。

@AfterAll 注解修饰的必须是静态方法，即方法有static关键字，如果方法声明上未添加该关键字，则会报错，模版如下：
```
org.junit.platform.commons.JUnitException: @AfterAll method 'void 包名.类名.方法名' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
```

>一个测试类中有3个@Test注解修饰的方法，则@BeforeEach/@AfterEach注解修饰的方法就运行3次。
>但是@Test注解修饰的方法只运行一次。
> @BeforeAll 注解修饰的方法，在所有方法的最前面运行一次。
> @AfterAll 注解修饰的方法，在所有方法的最后面运行一次。

运行顺序为：
```
@BeforeAll   -->   
    @BeforeEach   -->   @Test   -->   @AfterEach  -->   
    @BeforeEach   -->   @Test   -->   @AfterEach  -->   
    @BeforeEach   -->   @Test   -->   @AfterEach  -->   
@AfterAll
```
##### @AfterAll 注解特征


1. @AfterAll 注解修饰的方法需要`static`关键字修饰「声明特征」
2. @AfterAll 注解修饰的方法不可单独运行，若需要运行则直接运行 测试类 或 测试方法「运行特征」
3. @AfterAll 注解修饰的方法在 **所有的方法后运行**，即包含@AfterEach 修饰的方法之后运行「运行特征」
4. @AfterAll 注解修饰的方法没有返回值，即方法声明时为`void`「声明特征」
5. @AfterAll 注解修饰的方法在一个类中只有一个时，运行测试类时，只运行一次「运行特征」
6. `@Test`注解对应的测试方法无论断言是否成功，@AfterAll 注解修饰的方法里的代码都是运行的

### 测试用例步骤
1. @BeforeAll 注解内声明被测对象

2. 被测对象初始化用例ID

3. 开始被测对象的业务逻辑

4. 获取业务逻辑结果

5. 断言

6. 被测对象销毁用例ID

7. 退出软件

### 编写测试用例

* 1、@BeforeAll 注解内声明被测对象 - 调用类对象，并命名为 `My Basic Test Project`
* 2、被测对象初始化用例ID - 调用类对象的初始化ID方法，`initId()`
* 3、开始被测对象的业务逻辑 - 测试用例步骤调用，`sum()`
* 4、获取业务逻辑结果 - 调用业务逻辑后赋值给`int`类型
* 5、断言 - 测试用例结果验证，JUnit5自带断言
* 6、被测对象销毁用例ID - 调用类对象的销毁ID方法，`destroyId()`
* 7、退出软件 - 调用类对象的关闭方法，`close()`


```java

    @AfterAll
//    public void afterAll(){//JUnitException
    public static void afterAll(){
        mySUT.close();
    }
```
如果`@AfterAll`注解修饰的方法没有使用static关键字，则运行时会报错，详细信息如下：
```
org.junit.platform.commons.JUnitException: @AfterAll method 'public void top.testeru.basic.BeforeAllAnnotation_Test.AfterAll()' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
```


## 总结
- 声明特征
	
    - @BeforeAll、@AfterAll 注解修饰的方法为静态方法且没有返回值，，即方法声明时为`static void`

- 运行特征
	
    - @BeforeAll、@AfterAll 注解修饰的方法不可单独运行，若需要运行则直接运行 测试类 或 测试方法
	
    - @BeforeAll、@AfterAll 注解修饰的方法在 **所有方法前、后运行**

	- `@Test`注解对应的测试方法无论断言是否成功，@AfterAll 注解修饰的方法里的代码都是运行的

