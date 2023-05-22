---
notebook: JUnit5
title: 5.JUnit5动态测试
tags: Xunit
---



## 动态测试/用例导入
运维团队有一批做线上配置检查脚本，领导希望将他们的
测试结果整合到我们的`Junit`测试报告里，他们有几千条测试用例，而且是`shell`写成，使用`Java`重写工作量巨大。

### 问题原因：
传统自动化测试思路中，我们的测试逻辑是在以硬编码的形式组织到代码里的，当遇到用例迁移或结果整合时，会产生大量的逻辑重写。
### 解决思路：
我们除了硬编码的脚本编写方式外，还要能动态的在脚本`Runtime`时生成用例。
### 实施方案：
`JUnit5`提供了动态测试方案，让测试人员可以在脚本`Runtime`时动态的批量生成用例。



# 动态测试
- 运行时由工厂方法生成的，`@TestFactory`
- `@TestFactory`本身不是测试用例，而是测试用例的工厂
- 一个`@TestFactory`方法必须返回单个`DynamicNode`或 `Stream`，`Collection`，`Iterable`，`Iterator`，或`DynamicNode`的实例
- `@TestFactory`方法不能是`private`或`static`,并且可以选择声明,有解析的参数`ParameterResolver`
- 动态测试的实现可以作为lambda 表达式或方法引用提供
  



