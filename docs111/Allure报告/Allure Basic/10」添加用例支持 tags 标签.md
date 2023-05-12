# Allure2 报告中添加用例支持 tags 标签


### 应用场景

在实际的软件测试过程中，测试用例的分类和组织很重要。通过使用标签（`Tags`）可以轻松地组织和管理测试用例，标签可以根据功能、测试类型、测试阶段、测试计划等进行分类，同时也可以为特定的测试用例设置标签，便于管理和查找。

在`Allure2`报告中添加用例支持tags标签可以帮助测试人员更好地组织和管理测试用例，同时在报告中也可以方便地查看和筛选特定的测试用例。

例如，一个测试用例可以拥有多个标签，比如“smoke”、“regression”、“functional”、“ui”等，可以根据这些标签在`Allure2`报告中进行筛选，方便查找和组织测试用例。

总的来说，使用tags标签可以提高测试用例的管理效率和测试过程的可视化程度。


### 正常测试用例添加标签

```java
@Tag("1")
@Tag("2")
public class TagTest {
    @Test
    @Tag("你好")
    @Tag("世界")
    void reTest() {
        System.out.println("reTest");
    }
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230316153510.png)


### 用例跳过添加标签

`@DisabledIf`注解：该测试方法将被标记为不应该执行，并且该测试将被认为已经通过了。

```java
@Tag("1")
@Tag("2")
public class TagTest {

    //此时该测试方法将被标记为不应该执行，并且该测试将被认为已经通过了。
    @Test
    @DisabledIf("good")
    void test() {
        System.out.println("执行te");
        //Assumptions.assumeFalse(true, "This test is expected to fail");
        // some test code that should fail
    }
    boolean good(){
        //当前条件触发 如果是true 则测试方法test跳过不执行，并且结果默认为通过
        return 2 + 2 != 5;
    }
}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230316153548.png)

### 用例预期失败添加标签

```java
@Tag("1")
@Tag("2")
public class TagTest {
    @Test
    void test1() {
        System.out.println("执行good1");
        Assumptions.assumeFalse(this::good1, "这是一个预期失败的用例");
    }
    boolean good1(){
        //java.lang.System.getProperty('os.name').toLowerCase().contains('mac')
        //当前条件触发 如果是true 则测试方法test跳过不执行，并且结果默认为通过
        return 2 + 2 != 5;
    }

}
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230316153610.png)



