# JUnit5基本注解 - Each注解
## 本章要点
1. Each 安装
1. Each 运行原理
1. @BeforeEach 使用
1. @AfterEach 使用
1. Each 计算器实战

## Each 注解
### 安装
与@Test注解使用的是同一个依赖，不需要重新导入。
### 运行原理
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230104121305.png)

代码运行工具「IDE」运行`Each`注解修饰的方法时通过`JUnit5`框架的`junit-jupiter`组件运行。


`@BeforeEach`注解其实就是告诉 `JUnit5` 框架需要在每个`@Test`注解方法**前**需要运行哪些代码。
`@AfterEach`注解其实就是告诉 `JUnit5` 框架需要在每个`@Test`注解方法**后**需要运行哪些代码。

### @BeforeEach 注解

#### 使用
`@BeforeEach`注解修饰的方法相当于**每个测试方法的前置条件**。

1. `@BeforeEach`注解是方法上的注解。「注解位置」
    >`@BeforeEach`注解修饰方法时，放在方法上方。
1. `@BeforeEach`修饰的方法没有**返回值**，即返回值类型为`void`。「注解修饰方法声明规则」
1. `@BeforeEach`修饰的方法是在每一个`@Test`注解修饰方法之**前**运行，与其在代码中的前后顺序无关。「注解修饰方法运行顺序」
1. `@BeforeEach`注解修饰的方法不可直接运行。
1. `@BeforeEach`注解修饰的方法内编写的内容为：每一个测试方法前需要运行的前置条件。「用例前置条件编写」
   >在每一个@Test注解修饰的方法之前运行一次；所以，当前测试类有多少个@Test注解，@BeforeEach注解修饰的方法就运行多少次
1. 一个测试类里可以有多个`@BeforeEach`注解修饰的方法，但是不建议使用。「注解个数」

### Hello @BeforeEach
```java
public class An_02Each_Test {
    static final Logger logger = getLogger(lookup().lookupClass());

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
        //5、测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(5,result);
    }
    @Test
    public void sumFail() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //5、测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(7,result,"4+1计算结果错误"+result);
    }


    @Test
    public void sumFailWithSupplier() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        int expected = 7;
        //5、测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(expected,result, ()->"4+1计算结果为："+result+"，期望结果为："+expected);
    }

    @BeforeEach
    public void beforeEach(){
        logger.info("BeforeEach注解");
    }
}
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105142252.png)

注意⚠️：
- `@BeforeEach`注解修饰的方法在代码的最后，但是每个测试方法前都输出打印了对应内容。

### 作用
- 测试用例中，**测试方法**需要**初始化**的内容及属性
  - UI自动化中需要进入固定页面，回退到固定页面；
  - App自动化中的每个阶段用例需要重启app；
  - 自动化测试中需要提前删除由于测试产生的测试数据

### @AfterEach 注解
#### 使用
`@AfterEach`注解修饰的方法相当于**每个测试方法的后置条件**。

1. `@AfterEach`注解是方法上的注解。「注解位置」
    >`@AfterEach`注解修饰方法时，放在方法上方。
1. `@AfterEach`修饰的方法没有**返回值**，即返回值类型为`void`。「注解修饰方法声明规则」
1. `@AfterEach`修饰的方法是在每一个`@Test`注解修饰方法之**后**运行，与其在代码中的前后顺序无关。「注解修饰方法运行顺序」

1. `@AfterEach`注解修饰的方法不可直接运行。
1. `@AfterEach`注解修饰的方法内编写的内容为：每一个测试方法后需要运行的后置条件。「用例后置条件编写」
   >在每一个@Test注解修饰的方法之后运行一次；所以，当前测试类有多少个@Test注解，@AfterEach注解修饰的方法就运行多少次
2. 无论`@Test`注解修饰的测试方法是否断言成功，`@AfterEach`注解修饰的方法都会运行。
   >`@Test`注解修饰方法哪怕断言失败，`@AfterEach`的内容也会运行。
3. 一个测试类里可以有多个`@AfterEach`注解修饰的方法，但是不建议使用。「注解个数」


### Hello @AfterEach
```java
public class An_02Each_Test {
    static final Logger logger = getLogger(lookup().lookupClass());

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
        //5、测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(5,result);
    }
    @Test
    public void sumFail() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        //5、测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(7,result,"4+1计算结果错误"+result);
    }


    @Test
    public void sumFailWithSupplier() {
        //1、被测系统命名为 - My Basic Test Project
        MySUT mySUT = new MySUT("My Basic Test Project");
        //2、打印日志 - Begin Sum Test
        logger.info("Begin Sum Test");
        //3、测试用例步骤调用 - sum()
        int result = mySUT.sum(4, 1);
        //4、打印结果日志 - Sum Result
        logger.info("Sum Result：{}",result);
        int expected = 7;
        //5、测试用例结果验证
        //expected:期望值,  actual:运算的实际值
        assertEquals(expected,result, ()->"4+1计算结果为："+result+"，期望结果为："+expected);
    }

    @BeforeEach
    public void beforeEach(){
        logger.info("BeforeEach注解");
    }
}
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105143436.png)

注意⚠️：
- `@AfterEach`注解修饰的方法在代码的最前，但是每个测试方法后都输出打印了对应内容。


### 作用
- 测试用例中，**测试方法**需要**销毁**的内容及属性
  - UI自动化中需要返回首页，返回固定页面；
  - App自动化中的每个阶段用例需要退出app；
  - 删除/销毁产生的测试数据

### Each注意
`@BeforeEach` + `@AfterEach`可以同时修饰一个方法。



### 计算器测试用例
#### 被测系统
- `initId()`：初始化ID，每个测试方法分配一个ID
- `destroyId()`：销毁ID，每个测试方法在运行完成后销毁刚刚分配的ID
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
#### 加法测试用例添加前、后置条件
##### 步骤
1. 被测系统计算器创建并命名：`My Basic Test Project`
2. 为当每个测试方法分配唯一的用例ID
3. 日志打印开始测试：`Begin Sum Test`
4. 测试用例步骤调用
5. 日志打印计算结果：`Sum Result：`
6. 断言计算结果是否正确
7. 销毁测试方法的唯一的用例ID



##### 代码
```java
public class An_02Each_Test {
    static final Logger logger = getLogger(lookup().lookupClass());
    //1、被测系统计算器创建并命名：My Basic Test Project
    MySUT mySUT = new MySUT("My Basic Test Project");
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
}
```
运行整个测试类，可以看到无论测试方法断言失败还是成功，对应的ID都会生成和释放，输出结果如下：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230105152016.png)


## 总结
- `Each`注解使用
  1. `@BeforeEach`、`@AfterEach`注解修饰的方法不可直接运行
  2. `@BeforeEach`、`@AfterEach`注解可同时修饰一个方法
  3. `@BeforeEach`、`@AfterEach`注解修饰的方法没有返回值，即方法声明时为`void`
  4. `@BeforeEach`注解在每个`@Test`之**前**都运行一次；`@AfterEach`注解在每个`@Test`之**后**都运行一次，**无论断言是否失败**；


[项目演示地址](https://github.com/TesterDevSoul/Tutorials/blob/master/junit5/junit5-basic/src/test/java/top/testeru/basic/An_02Each_Test.java)
