# JUnit5基本注解 - All注解
## 本章要点
1. All 安装
1. All 运行原理
1. @BeforeAll 使用
1. @AfterAll 使用
1. All 计算器实战

## All 注解
### 安装
与@Test注解使用的是同一个依赖，不需要重新导入。
### 运行原理
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230104121305.png)

代码运行工具「IDE」运行`All`注解修饰的方法时通过`JUnit5`框架的`junit-jupiter`组件运行。


`@BeforeAll`注解其实就是告诉 `JUnit5` 框架需要在所有的方法**前**运行一次的代码。
`@AfterAll`注解其实就是告诉 `JUnit5` 框架需要在所有的方法**后**运行一次的代码。

### @BeforeAll 注解

#### 使用
`@BeforeAll`注解修饰的方法相当于**所有测试用例的前置条件**。

1. `@BeforeAll`注解是方法上的注解。「注解位置」
    >`@BeforeAll`注解修饰方法时，放在方法上方。
2. `@BeforeAll`修饰的方法没有**返回值**并且为静态方法，即方法由`static`修饰且返回值类型为`void`。「注解修饰方法声明规则」
3. `@BeforeAll`修饰的方法在测试类里面运行一次，并且是在所有的方法之**前**运行一次，与其在代码中的前后顺序无关。「注解修饰方法运行顺序」
4. `@BeforeAll`注解修饰的方法不可直接运行。
5. `@BeforeAll`注解修饰的方法内编写的内容为：所有的测试方法前需要运行的前置条件。「所有用例统一的前置条件编写」
   >在所有的方法之前运行一次；所以，当前测试类先运行该方法，再去运行其它，无论有多少个Test注解修饰的方法，@BeforeAll只在测试类编译成功后运行一次，并且是**第一个**运行。
6. 一个测试类里可以有多个`@BeforeAll`注解修饰的方法，但是不建议使用。「注解个数」




#### Hello @BeforeAll
```java
public class An_03All_Test {
    static final Logger logger = getLogger(lookup().lookupClass());
    //1、被测系统计算器创建并命名：My Basic Test Project
    MySUT mySUT = new MySUT("My Basic Test Project");
    @AfterAll
    public static void afterAll(){
        logger.info("AfterAll注解");
    }
    @AfterEach
    public void afterEach(){
        //logger.info("AfterEach注解");
        //7、销毁测试方法的唯一的用例ID 调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
    }
    @Test
    public void sum() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(5,result);
    }
    @Test
    public void sumFail() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值, message:断言失败时的解释说明
        assertEquals(7,result,"4+1计算结果错误"+result);
        logger.info("断言失败");
    }


    @Test
    public void sumFailWithSupplier() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        int expected = 7;
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值, messageSupplier:断言失败时解释说明，只有失败时才运行
        assertEquals(expected,result, ()->"4+1计算结果为："+result+"，期望结果为："+expected);
        logger.info("断言失败");
    }

    @BeforeEach
    public void beforeEach(){
        // logger.info("BeforeEach注解");
        //2、为当每个测试方法分配唯一的用例ID - 调用类对象的初始化ID方法 initId()
        mySUT.initId();
        //3、日志打印开始测试：Begin Sum Test
        logger.info("Begin Sum Test");
    }
    @BeforeAll
    public static void beforeAll(){
        logger.info("BeforeAll注解");
    }   
}
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105162926.png)

注意⚠️：
- `@BeforeAll`注解修饰的方法在代码的最后，但是在类运行所有的方法之前进行了打印。

#### 作用
- 测试用例中，**测试类**需要**初始化**的内容及属性
  - 测试对象的声明：Webdriver ;AppiumDriver;  ChromeDriver
  - log日志删除
  - apk包的安装
  - 启动的某些参数的配置

### @AfterAll 注解
#### 使用
`@AfterAll`注解修饰的方法相当于**所有测试用例的后置条件**。

1. `@AfterAll`注解是方法上的注解。「注解位置」
    >`@AfterAll`注解修饰方法时，放在方法上方。
1. `@AfterAll`修饰的方法没有**返回值**并且为静态方法，即方法由`static`修饰且返回值类型为`void`。「注解修饰方法声明规则」
1. `@AfterAll`修饰的方法在测试类里面运行一次，并且是在所有的方法之**后**运行一次，与其在代码中的前后顺序无关。「注解修饰方法运行顺序」
1. `@AfterAll`注解修饰的方法不可直接运行。
1. `@AfterAll`注解修饰的方法内编写的内容为：所有的测试方法后需要运行的后置条件。「所有用例统一的后置条件编写」
   >在所有的方法之后运行一次；所以，当前测试类运行完所有方法之后，再去运行其它，无论有多少个Test注解修饰的方法，@AfterAll只在测试类编译成功后运行一次，并且是**最后**一个运行。
1. 一个测试类里可以有多个`@AfterAll`注解修饰的方法，但是不建议使用。「注解个数」

#### Hello @AfterAll
```java
public class An_03All_Test {
    static final Logger logger = getLogger(lookup().lookupClass());
    //1、被测系统计算器创建并命名：My Basic Test Project
    MySUT mySUT = new MySUT("My Basic Test Project");
    @AfterAll
    public static void afterAll(){
        logger.info("AfterAll注解");
    }
    @AfterEach
    public void afterEach(){
        //logger.info("AfterEach注解");
        //7、销毁测试方法的唯一的用例ID 调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
    }
    @Test
    public void sum() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(5,result);
    }
    @Test
    public void sumFail() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值, message:断言失败时的解释说明
        assertEquals(7,result,"4+1计算结果错误"+result);
        logger.info("断言失败");
    }


    @Test
    public void sumFailWithSupplier() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        int expected = 7;
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值, messageSupplier:断言失败时解释说明，只有失败时才运行
        assertEquals(expected,result, ()->"4+1计算结果为："+result+"，期望结果为："+expected);
        logger.info("断言失败");
    }

    @BeforeEach
    public void beforeEach(){
        // logger.info("BeforeEach注解");
        //2、为当每个测试方法分配唯一的用例ID - 调用类对象的初始化ID方法 initId()
        mySUT.initId();
        //3、日志打印开始测试：Begin Sum Test
        logger.info("Begin Sum Test");
    }
    @BeforeAll
    public static void beforeAll(){
        logger.info("BeforeAll注解");
    }  
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105162736.png)

注意⚠️：
- `@AfterAll`注解修饰的方法在代码的最上，但是所有的方法运行完后输出打印了@AfterAll对应内容。

#### 作用
- 测试用例中，**测试类**需要**销毁**的内容及属性
  - apk卸载
  - app退出
  - 测试用例结束
  - web端关闭浏览器操作
  - 关闭数据库连接


### All注意
`@BeforeAll` + `@AfterAll`可以同时修饰一个方法。



### 计算器测试用例
#### 被测系统
- `close()`：程序被退出关闭
```java
public class MySUT {
    //获得具有所需名称的记录器
    static final Logger logger = getLogger(lookup().lookupClass());
    String name;//用例名

    String id;//唯一ID标识

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

#### 添加类的前、后置条件
##### 步骤
1. 被测系统计算器创建并命名：`My Basic Test Project`
2. 为当每个测试方法分配唯一的用例ID
3. 日志打印开始测试：`Begin Sum Test`
4. 测试用例步骤调用
5. 日志打印计算结果：`Sum Result：`
6. 断言计算结果是否正确
7. 销毁测试方法的唯一的用例ID
8. 被测系统计算器关闭



##### 代码
```java
public class An_03All_Test {
    static final Logger logger = getLogger(lookup().lookupClass());
    
    static MySUT mySUT;
    @AfterAll
    //public void afterAll(){//JUnitException
    public static void afterAll(){
        logger.info("BeforeAll注解");
        //8、被测系统计算器 对象close关闭
        mySUT.close();
    }
    @AfterEach
    public void afterEach(){
        //logger.info("AfterEach注解");
        //7、销毁测试方法的唯一的用例ID 调用类对象的销毁ID方法 - destroyId()
        mySUT.destroyId();
    }
    @Test
    public void sum() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(5,result);
    }
    @Test
    public void sumFail() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值, message:断言失败时的解释说明
        assertEquals(7,result,"4+1计算结果错误"+result);
        logger.info("断言失败");
    }


    @Test
    public void sumFailWithSupplier() {
        //4、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //5、日志打印计算结果：Sum Result
        logger.info("Sum Result：{}",result);
        int expected = 7;
        //6、断言计算结果是否正确 测试用例结果验证
        //expected:期望值,  actual:运算的实际值, messageSupplier:断言失败时解释说明，只有失败时才运行
        assertEquals(expected,result, ()->"4+1计算结果为："+result+"，期望结果为："+expected);
        logger.info("断言失败");
    }

    @BeforeEach
    public void beforeEach(){
        // logger.info("BeforeEach注解");
        //2、为当每个测试方法分配唯一的用例ID - 调用类对象的初始化ID方法 initId()
        mySUT.initId();
        //3、日志打印开始测试：Begin Sum Test
        logger.info("Begin Sum Test");
    }
    @BeforeAll
    //public void beforeAll(){ //JUnitException
    public static void beforeAll(){
        //logger.info("BeforeAll注解");
        //1、被测系统计算器创建并命名：My Basic Test Project
        mySUT = new MySUT("My Basic Test Project");
    }
}
```
运行整个测试类，可以看到无论测试方法断言失败还是成功，最后都会退出，输出结果如下：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105152016.png)




### BUG
#### All注解对应方法报错
```
org.junit.platform.commons.JUnitException: @BeforeAll method 'public void top.testeru.basic.An_03All_Test.beforeAll()' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105161106.png)


```
org.junit.platform.commons.JUnitException: @AfterAll method 'public void top.testeru.basic.An_03All_Test.afterAll()' must be static unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105161520.png)


##### 原因

`@BeforeAll`注解、`@AfterAll`注解 对应方法没有使用`static`关键字修饰导致错误。

##### 解决
`@BeforeAll`注解、`@AfterAll`注解对应方法使用`static`关键字声明。

## 总结
- `All`注解使用
  1. `@BeforeAll`、`@AfterAll`注解修饰的方法不可直接运行
  2. `@BeforeAll`、`@AfterAll`注解可同时修饰一个方法
  3. `@BeforeAll`、`@AfterAll`注解修饰的方法**static修饰**并且**没有返回值**，即方法声明时为`static void`
  4. `@BeforeAll`注解在类的所有方法之**前**运行；`@BeforeAll`注解注解在类的所有方法之**后**运行，**无论断言是否失败**；


[项目演示地址](https://github.com/TesterDevSoul/Tutorials/blob/master/junit5/junit5-basic/src/test/java/top/testeru/basic/An_03All_Test.java)
