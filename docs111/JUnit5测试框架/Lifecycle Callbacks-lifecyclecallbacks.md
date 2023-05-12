---
title: Lifecycle Callbacks
date: 2022-10-19 10:50:24.096
updated: 2022-10-24 14:45:35.094
url: /archives/lifecyclecallbacks
categories: 
- JUnit5
tags: 
- junit5
---

# 生命周期回调
## 本章要点

### 回调接口
这组扩展与测试生命周期中的事件相关，可以通过实现以下接口来定义：

#### BeforeAllCallback 
- 实现`BeforeAllCallback`接口的类将在所有测试用例调用及 `@BeforeAll` 注解修饰的方法执行之前提供额外的行为。
####  AfterAllCallback
- 实现`AfterAllCallback`接口的类将在执行完所有测试用例后 及 `@AfterAll` 注解修饰的方法执行之后提供额外的行为。


>在**所有**测试方法 及 `@BeforeAll`、`@AfterAll` 注解修饰的方法 执行**之前和之后**执行
#### BeforeEachCallBack 
- 实现`BeforeEachCallback`接口的类将在每个测试用例调用前及 `@BeforeEach`注解修饰的方法之前执行并提供额外的行为。

#### AfterEachCallback
- 实现`AfterEachCallback`接口的类将在执行测试用例后及 `@AfterEach`注解修饰的方法之后执行 并提供额外的行为



>在**每个**测试方法及 `@BeforeEach`、`@AfterEach` 注解修饰的方法 **之前和之后**执行
#### BeforeTestExecutionCallback 
- 实现BeforeTestExecutionCallback接口的类将在执行测试用例之前执行并提供额外的行为。


#### AfterTestExecutionCallback
- 实现AfterTestExecutionCallback接口的类将在执行测试用例后立即执行并提供额外的行为


>在测试方法@Test **之前和之后**立即执行


#### TestExecutionExceptionHandler
- TestExecutionExceptionHandler如果在执行测试用例时发生异常并且未正确处理，实现接口的类将执行并提供额外的行为。


#### ParameterResolver
- 实现ParameterResolver接口的类将在执行参数化测试用例之前执行并提供额外的行为。


### 执行顺序
如果测试还定义了它的生命周期方法，则执行顺序为：

1. `BeforeAllCallback`
2. `BeforeAll`
3. `BeforeEachCallback`
4. `BeforeEach`
5. `BeforeTestExecutionCallback`
6. `Test`
7. `AfterTestExecutionCallback`
8. `AfterEach`
9. `AfterEachCallback`
10. `AfterAll`
11. `AfterAllCallback`


### 回调激活

- @ExtendWith 注解
在 JUnit 5 中创建的所有扩展都必须使用 JUnit 注册和激活才能执行。JUnit 提供了@ExtendWith 注解。所有需要激活的扩展都必须注册@ExtendWith注解。


### 示例
示例展示了如何使用这些回调来计算和记录测试方法的执行时间。

TimingExtension既实现了BeforeTestExecutionCallback，又实现了AfterTestExecutionCallback，以便时间和记录测试执行。

```java
package top.testeru.callback;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Project junit5-demo
 * @Description Test前后的回调函数
 * @createTime 2022年10月19日 12:10:00
 */
public class TimingExtension implements
        BeforeTestExecutionCallback, AfterTestExecutionCallback {

    //context：封装了当前测试或容器正在执行的上下文
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method method = context.getRequiredTestMethod();
        Long startTime = context.getStore(
                        ExtensionContext
                                .Namespace
                                .create(getClass(), method))//创建一个命名空间，它将对数据的访问限制为所有使用相同的部件序列来创建命名空间的扩展。
                .remove("start-time", long.class);
        Long durationTime = System.currentTimeMillis() - startTime;
        System.out.println(method.getName() + "持续的时间："+durationTime);

    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        context.getStore(
                    ExtensionContext
                            .Namespace
                            .create(getClass(),
                    context.getRequiredTestMethod()))
                .put("start-time", System.currentTimeMillis());


    }
}

```
  

## 总结
