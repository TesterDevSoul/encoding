## Allure2报告中添加用例优先级


### 应用场景


在软件开发的过程中，我们通常需要对测试用例进行优先级分类，以确保高优先级的测试用例能够被更早地发现和解决问题。在 Allure2 报告中添加用例优先级可以帮助测试人员和开发人员更好地了解测试用例的重要性和优先级，从而更好地管理和执行测试用例。

以下是 Allure2 报告中添加用例优先级的应用场景：


1. 针对紧急修复的问题，可以设置一些高优先级的测试用例，确保问题得到尽快解决。

2. 针对功能开发，可以设置不同的优先级测试用例，以确保优先级高的测试用例在开发周期的不同阶段得到执行。

3. 针对版本发布前的测试，可以设置一些高优先级的测试用例，确保核心功能的测试得到充分覆盖。

总之，添加用例优先级可以帮助测试团队更好地规划测试用例的执行，提高测试效率和测试覆盖率。


## 用例优先级级别介绍

`Allure2`报告中添加用例优先级可以使用`@Severity`注解来实现。

`@Severity`注解用于指定测试用例的严重性，表示测试用例的重要程度或者优先级。

Allure支持以下五种级别：

- `BLOCKER`：阻塞级别，表示最高优先级。
    
    >在软件测试中，`Blocker`级别通常被用来指代严重的缺陷或问题，这些问题可能会导致系统的崩溃、数据损坏、安全问题等，阻碍了测试流程的进行。<br>因此，当测试人员在执行测试用例时发现这些问题时，通常会将其标记为`Blocker`级别，以便及时通知相关人员修复问题，确保软件的质量和稳定性。
    
- `CRITICAL`：严重级别。
  
  >`@Severity(CRITICAL)`注解通常用于标识测试用例中的关键功能和业务逻辑，例如登录、支付、提交订单等操作，这些操作的正确性对于应用程序的正常运行至关重要。如果这些用例失败了，那么整个应用程序可能无法正常工作。<br>因此，这些用例的失败将被认为是严重的，应该及时修复。

- `NORMAL`：普通级别（默认）。
  
    >`Normal`级别通常用于表示普通的测试用例，即非严重或紧急的测试用例。这些测试用例可能包含了一些功能或业务逻辑的测试，但不会对系统的重要性或稳定性产生太大的影响。<br>例如，一个普通的测试用例可以测试系统的某个小功能或一个基本的业务流程，这些测试用例通常被认为是非必要的，但仍然需要进行测试以保证系统的完整性和质量。

- `MINOR`：次要级别。

    >一般情况下，`MINOR`级别用于标识一些不影响主要功能，但需要修复的小问题或者改进点。例如，一些小的UI优化、拼写错误、文档错误等。虽然这些问题不会对系统整体的运行产生影响，但是修复它们可以提高系统的可用性和可读性。<br>因此，对于这些小问题，可以使用MINOR级别标记它们，让开发人员知道它们需要被修复和改进。

- `TRIVIAL`：轻微级别，表示最低优先级。
    
    >一般来说，`TRIVIAL`级别的用例是指对系统影响非常小的用例，例如一些细节问题、显示问题等等。这些问题并不会对系统的正常运行造成实质性影响，但是仍然需要被关注和解决。<br>因此，在编写用例时，可以根据实际情况将一些较小的问题标记为`TRIVIAL`级别，以便更好地区分和管理用例。

在报告中，`Allure`会根据严重性级别对测试用例进行排序和分类展示，以方便测试人员和开发人员快速了解测试用例的优先级。

## 添加用例优先级的方式

1. 在测试方法中添加`@Severity`注解。

    >在测试类或测试方法上使用`@Severity`注解来指定测试用例的优先级。


需要注意的是，`@Severity`注解只是用于给测试用例添加一个优先级标记，**并不会对测试用例的执行顺序产生任何影响**，测试用例的**执行顺序应该由测试执行框架（如JUnit、TestNG等）来控制**。



### 注解方式添加

#### 语法结构

```java
@Severity(SeverityLevel.<LEVEL>)
```

`<LEVEL>`可以是以下级别之一：

`BLOCKER`、`CRITICAL`、`NORMAL`、`MINOR`、`TRIVIAL`。



#### 示例代码

```java
@DisplayName("用例优先级注解验证")
public class SeverityAnTest {
    @Test
    @Severity(SeverityLevel.BLOCKER)
    public void test1() throws IOException {
        // Test code
        System.out.println("用例使用Blocker级别实战代码");
        String input = "test data";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        inputStream.close();
        assertEquals("test data", new String(buffer, 0, bytesRead));

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void test2() {
        // Test code
        System.out.println("用例使用CRITICAL级别实战代码");

    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void test3() {
        // Test code
        System.out.println("用例使用NORMAL级别实战代码");

    }

    @Test
    @Severity(SeverityLevel.MINOR)
    public void test4() {
        // Test code
        System.out.println("用例使用MINOR级别实战代码");

    }
    @Test
    @Severity(SeverityLevel.TRIVIAL)
    public void test5() {
        // Test code
        System.out.println("用例使用TRIVIAL级别实战代码");

    }
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230316140950.png)

#### 类上添加示例代码

```java
@DisplayName("用例优先级类添加验证")
@Severity(SeverityLevel.CRITICAL)
public class SeverityClassTest {
    @Test
    public void test1() throws IOException {
        // Test code
        System.out.println("用例使用Blocker级别实战代码");
        String input = "test data";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        inputStream.close();
        assertEquals("test data", new String(buffer, 0, bytesRead));

    }

    @Test
    public void test2() {
        // Test code
        System.out.println("用例级别实战代码");
    }
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230316141114.png)


类上添加的优先级在当前类上的所有测试方法中都匹配适用。