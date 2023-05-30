---
title: What is Rest Assured?
date: 2022-08-16 13:59:56.986
updated: 2022-08-16 14:03:38.066
url: /archives/ra07
categories: 
- rest-assured
tags: 
- rest-assured
---

# 介绍 REST Assured 

在我们之前的文章中，我们讨论了REST方法的基础知识，包括熟悉的术语、架构元素等。朝着这个方向前进一步，让我们继续在本文中使用 REST 进行 API 测试。我们用于 API 测试的最流行的技术是“放心”。我们将在本文中介绍与 REST Assured 相关的以下主题：

什么是放心？
为什么我们需要它？
最新版本。
放心的优势。
放心的缺点。
什么是 REST 保证？
Rest-Assured 是一个基于 Java 的开源库，可用于测试和验证REST API或 RESTFul Web 服务。它就像一个访问和测试 REST Web 服务的无头（无 GUI）客户端。它由Johan Haleby在其他几位贡献者的支持下开发和维护。

放心简化了基于 REST 的服务的测试。它带来了像 Groovy 和 Ruby 这样在 Java 中执行 API 测试的动态语言的简单性。该库支持任何 HTTP 方法，并且还明确支持 GET、POST、PUT、DELETE、OPTIONS 和 HEAD。它还包括参数、标头、cookie 等规范和验证。我们还可以使用它来验证和验证 HTTP 请求的响应。

放心

资源

除了测试基于 JSON 的 Web 服务外，Rest Assured 还可用于测试基于 XML 的 Web 服务。我们还可以将此库与JUnit和TestNG框架集成，并为应用程序编写测试用例。此外，它可以与Maven很好地集成，并且其高效的匹配技术会产生直接的结果。

REST 保证的另一个强大功能是它支持 XML 路径和 JSON 路径语法来检查响应数据的特定元素，类似于使用 XPath API。对于这些概念的新手，请参考以下语法示例。

XPath 语法：

<?xml version="1.0" encoding="UTF-8"?>
<bookstore>
<book>
  <title>Freedom In Exile</title>
  <price>14.29</price>
</book>
</bookstore>
JSON路径语法：

$['store']['book'][3]['title']
除了上述所有功能外，这个奇妙的库还提供了各种其他功能，如类似DSL 的语法、规范重用、XPath-Validation、简单的文件上传，以及所有有利于自动化测试的功能。

为什么我们需要放心？
上面关于这个 java 库的讨论让我们相信它是一个可靠的库。但是我们为什么需要它或者在我们的应用程序测试中使用它的原因是什么？

以下是我们需要放心的主要原因：

它是一个开源库，拥有活跃的开发社区，使其成为 API 自动化的绝佳选择。
早些时候，我们不得不使用 Ruby、Groovy 等动态语言进行 API 测试，这非常具有挑战性。
REST 服务的验证和测试在 Java 中更难。使用 REST Assured，它变得更加简单和容易。
该库使用 Java，因此使用基本 Java 代码发送带有自定义的 HTTPS 请求变得简单。一旦我们了解了 API 和集成测试的基础知识，使用 Rest Assured 的自动化就会对后端充满信心。因此，我们可以更多地关注前端测试。
最新版本
以下是我们在应用程序中通常使用的一些 Rest Assured 库的版本。

io.rest-assured -这是一个用于轻松测试 REST 服务的 Java DSL。最后一次发布：2020 年 12 月 11 日（4.3.3）。
com.jayway.restassured -这是一个遗留库，用于使用 Java DSL 轻松测试 REST 服务。最后发布：2016 年 3 月 4 日。
ddf.thirdparty -这是一个第三方库。最后发布：2021 年 4 月 9 日
放心的优势
下表列出了该库的一些优点。

编号	优点
1	它是开源的，因此可以免费使用。
2	它的语法和现成的断言非常丰富。与 Apache HTTP 客户端相比，Rest Assured 需要更少的编码。
3	Rest Assured 的设置简单明了。
4	响应以 JSON 或 XML 格式给出，易于解析和验证。
5	它使用内置的Hemcrest 匹配器来轻松提取值。
6	响应时间和状态代码的断言一样快。
7	该库具有强大的日志记录机制。此外，我们可以即时验证标头、cookie、内容类型等。
8	它可以很容易地与其他 Java 库如 TestNG、JUnit 等集成。我们也可以将它与 Selenium-Java 集成，实现端到端的自动化。
9	它对各种API认证机制有很好的支持。
10	它支持有助于解析 JSON 和 XML 响应的 JsonPath 和 XmlPath。它还支持 JSON Schema Validation 库来验证 JSON Schema。
11	Rest Assured 也可以与 Maven 和 CICD 集成。
12	支持多部分表单数据
13	支持 Spring Mock MVC、Spring Web Test Client、Scala 和 Kotlin。
14	它遵循BDD（行为数据驱动）方法和 given() when()、then() 等关键字，使代码可读并支持干净的编码。此功能从 2.0 版开始提供。
15	REST Assured 4.1.2 添加了对 Java 13 的支持。
放心的缺点
该库有以下缺点。

它不明确支持测试SOAP（简单对象访问协议） API。
使用该库需要用户具备良好的 Java 编程知识
Rest Assured 中没有内置报告。
关键要点
我们从这篇文章中得到以下结论：

它是一个开源 Java 库，可以免费使用。
它用于验证和测试 Java API。
除了 Java DSL 语法等其他功能外，还支持所有 HTTP 方法和 REST 方法，如 GET、POST、PUT、DELETE 等。
我们可以将 Rest Assured 与所有主要的自动化框架（如 TestNG、JUnit）集成，并将其与 maven 和 CI/CD 集成。
该库具有丰富的语法，并且由于它是开源的，因此不断添加越来越多的功能，这使其成为 API 自动化的非常高效和简单的库。
有了这些知识，我们将在后续文章中从实际的 API 测试教程开始。在此之前，我们将讨论使用 Eclipse IDE 配置 Rest Assured。