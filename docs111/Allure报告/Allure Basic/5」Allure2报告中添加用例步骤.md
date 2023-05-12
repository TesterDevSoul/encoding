## Allure2 报告中添加用例步骤

### 应用场景

在Allure2报告中，为测试用例添加步骤可以帮助您更详细地了解测试的执行过程，特别是当测试失败时。通过添加步骤，可以记录测试执行期间的关键操作，并在Allure2报告中查看这些操作的详细信息。这有助于快速识别测试失败的原因，并找到修复问题的方法。

步骤可以是测试中执行的任何操作，例如，单击按钮，输入文本，选择下拉菜单等等。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230314154259.png)

## 添加用例步骤的方式

Allure提供了多种方式来添加测试用例步骤，其中一些常用的方式包括：

1. 使用 `@Step`注解添加。

    >`@Step`注解是`Allure`提供的注解之一，可以用于标记测试用例的每个步骤。<br>在使用`@Step`注解时，可以指定**步骤的名称**和**描述信息**。<br>`@Step`注解只能应用于测试方法和测试类的**非静态方法**。如果您尝试将`@Step`注解应用于静态方法或类级别的方法，则注解将不起作用。 


2. 使用`Allure.step()`方法添加。
    >除了`@Step`注解之外，`Allure`还提供了`Allure.step()`方法，可以在测试用例执行期间添加步骤。



### 方法直接添加

#### 语法结构

```java
Allure.step("步骤描述", () -> {
    // 测试步骤代码
});
```

- "**步骤描述**"：测试步骤的描述信息，可以是任意字符串，用于在报告中展示。

- **() -> { // 测试步骤代码 }**：一个`lambda`表达式，包含了测试步骤的具体代码逻辑。可忽略。

#### 示例代码

```java
@DisplayName("步骤方法验证")
public class StepMethodTest {
    int result;

    @Test
    @DisplayName("加法步骤验证")
    void testSum() {
        Allure.step("输入数字 3");
        int a = pressDigit(3);
        Allure.step("输入加号");
        String str = pressAddition();
        Allure.step("输入数字 2");
        int b = pressDigit(2);
        Allure.step("点击等号");
        result = sum(a, b);
        Allure.step("验证结果是否正确");
        assertEquals(5, result, a + str + b + "计算错误");
    }

    private int pressDigit(int digit) {
        return digit;
    }

    private String pressAddition() {
        return "+";
    }

    public int sum(int... numbers) {
        return IntStream.of(numbers).sum();
    }
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230314153545.png)


#### 一个测试方法中添加多个大步骤

当一个测试方法中有多个大步骤时，可以使用多个 Allure.step 方法来添加不同的步骤。

```java
    @Test
    @DisplayName("一个测试方法中添加多个大步骤")
    void testMultipleSteps() {
        Allure.step("步骤1", () -> {
            // 执行步骤1的测试代码
            System.out.println("登录");
        });
        Allure.step("步骤2", () -> {
            // 执行步骤2的测试代码
            System.out.println("搜索");
        });
        Allure.step("步骤3", () -> {
            // 执行步骤3的测试代码
            System.out.println("播放");
        });
    }
```
在这个示例中，我们使用了三个 `Allure.step()` 方法来添加三个不同的步骤，每个步骤都有一个对应的Lambda表达式，包含了执行该步骤的测试代码。

这样，我们就可以在测试报告中看到一个带有三个大步骤的测试用例，并且可以在每个步骤中看到对应的步骤名称和测试代码。

```java
    @Test
    @DisplayName("多个步骤验证")
    void testSum2() {
        ArrayList<Executable> executables = new ArrayList<>();
        Allure.step("登录步骤", () -> {
            Allure.step("进入登录页面");
            System.out.println("页面跳转");
            Allure.step("输入用户名");
            String name = "admin";
            Allure.step("输入密码");
            String pwd = "123456";
            Allure.step("点击登录");
            String login = name + pwd;
            System.out.println("login:"+login);

            executables.add(() ->
                assertEquals("admin123456", login, "拼接错误"));
        });

        Allure.step("计算器内部步骤", () -> {
            //加法步骤
            Allure.step("输入数字 3");
            int a = pressDigit(3);
            Allure.step("输入加号", () ->  pressAddition());
            Allure.step("输入数字 2");
            int b = pressDigit(2);
            Allure.step("点击等号", () -> sum(a, b));
            int result1 = sum(a, b);
            System.out.println("result1:"+result1);
            executables.add(
                    () ->  assertEquals(5, result1, "计算错误"));
        });
        Allure.step("字符拼接", () -> {
           //string拼接步骤
            Allure.step("输入字符 hello");
            String hello = pressStr("hello");

            Allure.step("输入字符 world");
            String world = pressStr("world");
            Allure.step("字符拼接");

            String addStr = addStr(hello, world);
            Allure.step("验证结果是否正确");
            System.out.println("addStr:"+addStr);

            executables.add(
                    () ->  assertEquals("hello-world", addStr, "拼接错误")
            );
        });
        assertAll(executables);

    }
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230315115203.png)


### 注解方式添加

`@Step`注解是`Allure`提供的注解之一，可以用于标记测试用例的每个步骤。

在使用`@Step`注解时，您可以指定步骤的名称和描述信息。

#### 语法结构

```java
@Step("步骤描述")
public void methodName() {
    // test logic here
}
```

- "**步骤描述**"：测试步骤的描述信息，可以是任意字符串，用于在报告中展示。

#### 示例代码

步骤会被展示为测试用例的执行过程，方便我们了解测试用例的执行情况。

```java
@DisplayName("步骤注解验证")
public class StepAnTest {
    int result;

    // 步骤会被展示为测试用例的执行过程，方便我们了解测试用例的执行情况。
    @Test
    @DisplayName("减法步骤验证")
    void testSub() {
        int a =  pressDigit(6);
        String str = pressSubtraction();
        int b = pressDigit(2);
        result = subtract(a, b);
        assertEquals(4, result, a + str + b + "计算错误");
    }

    @Step("输入数字 {digit}")
    private int pressDigit(int digit) {
        return digit;
    }
    @Step("输入减号")
    private String pressSubtraction() {
        return "-";
    }

    @Step("减法计算")
    public int subtract(int x, int y) {
        return x-y;
    }
}
```


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230314153719.png)


#### 注解传入对象

```java
public class User {
    private String name;
    private int age;
    //getter、setter、toString、有参构造
}
```


```java
@Step("输入减号:{u.age} / {u.name}")
private String pressSubtraction(User u) {
    return "-";
}
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230315160226.png)

#### Step注解不生效

如果在使用@Step注解时遇到不生效的情况，可能有以下原因：

1. 没有正确引入依赖。
   
    >在使用Allure报告时，需要正确引入相关依赖。如果您使用的是Maven，请确保已在pom.xml中添加了以下依赖：

    ```xml
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-junit5</artifactId>
        <version>2.17.0</version>
        <scope>test</scope>
    </dependency>

    <!-- 配置 -->
    <argLine>
        -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
    </argLine>
    ```

2. 没有使用`JUnit5`或`TestNG`测试框架。
   
    >`Allure`只支持`JUnit5`和`TestNG`测试框架，如果您使用的是其他测试框架，`@Step`注解将不会生效。

3. 没有正确使用`@Step`注解。

    >在使用`@Step`注解时，需要将其与一个非静态方法一起使用，例如：
    ```java
    @Step("步骤描述")
    public void methodName() {
        // 执行操作
    }
    ```
4. 未在`Allure`报告中启用步骤。
   
    >在`Allure`报告中，需要启用步骤才能显示`@Step`注解中定义的步骤。<br>可以通过在测试类上添加`@Epic`、`@Feature`、`@Story`或`@DisplayName`注解来启用步骤。例如：
    ```java
    @Epic("测试用例集")
    @Feature("计算器")
    @Story("加法")
    @DisplayName("测试加法")
    public class CalculatorTest {
        // 测试用例代码
    }
    ```


