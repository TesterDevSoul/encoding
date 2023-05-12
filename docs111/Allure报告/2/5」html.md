## Allure2 报告中添加附件-HTML


### 应用场景

1. 在测试用例执行过程中生成HTML报告并将其作为附件添加到Allure报告中。这种方式可以帮助测试人员更好地了解测试用例的执行过程，同时可以更好地观察测试用例的执行结果。例如，可以在测试用例执行期间生成HTML代码覆盖率报告，并将其作为附件添加到Allure报告中。

2. 在Allure报告的“附加信息”部分中添加HTML代码。这种方式可以用于显示测试用例执行的其他信息，例如测试用例的设计文档或相关的业务流程图。通过在Allure报告中添加HTML代码，测试人员可以更好地了解测试用例的上下文和背景，以便更好地理解测试结果。




```java
    @Test
    public void testWithImageAttachment() throws IOException {
        // 添加HTML
        Allure.addAttachment("首页", "text/html",
                Files.newInputStream(Paths.get("index.html")),
                ".html");

    }
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230317192601.png)

