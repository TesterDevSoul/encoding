---
title: Wework Web Auto
date: 2022-10-30 11:38:43.437
updated: 2022-10-30 12:08:10.684
url: /archives/weworkwebauto
categories: 
tags: 
---

# 企业微信Web自动化实战
## 本章要点

## UI自动化测试

Web自动化我们分为两次实战，第一次实战和第二次实战都是围绕着一款产品，就是企业微信。
会进行一些相关自动化分析，根据特定的业务场景，会让大家进行两个实战练习。
我会根据大家实战练习的结果，带着大家写一遍，然后给大家讲解优化咱们的实战代码。

下节课会稍微难一点，本次课程我们主要写的是web自动化脚本。学会使用selenium相关的功能。
下节课我们会使用PO的设计模式，来对我们实战的代码进行优化。


我们首先来看一下对应的本节课的结果展示：
大家可以看到，我的脚本运行不是很快速，肉眼都是可见的。
我为了脚本的稳定性，只要页面进行变化，就会进行相关的显示等待判断



企业微信这款产品，相对比较成熟，比较稳定

没更新一次，UI不会有一个大变动
企业微信无论是在web\app\接口都是比较完善，
添加部门、添加成员 这些业务，无论是在web端还是在app端还是在接口自动化中都可以进行添加

也是可以进行一个全流程实战的一个产品

无论是哪个公司对应都会有相关的审批流程，企业微信对于该业务也是比较成熟。所以我们这里选择的是企业微信这款产品。

大家如果有什么疑问，可以发在讨论区，我会过段时间就来看一下。今天应该直播不卡了，要是卡的话大家记得及时反馈。

上面说了一下对应的产品介绍
我们来说一下，对应的web自动化的价值，为什么要做这个web自动化


#### 功能测试流程


首先，做自动化之前，我们都做过功能测试
一个测试工程师，我们根据业务需求、测试用例 进行业务测试验证，点击、输入；这些操作
操作只是一个界面的东西，我们会根据自己的操作判断对应的界面响应的结果是否是预期内容。
至于里面服务端怎么流转的，一般不会进行关注


#### UI自动化测试流程



> 前端重用户交互，单纯的接口测试、单元测试不能真实反映用户的操作路径，
> 
> 并且从以往的经验中总结得出，因为各种不可控因素导致的发布 A 功能而 B 功能无法使用，特别是核心简单场景的不可用时有出现，
> 
> 所以每次发布一个应用前，都会将此应用提供的核心功能执行一遍，那随着业务的不断积累，需要回归的测试场景也越来越多，导致回归的工作量巨大。
> 
> 为了降低人力成本，我们亟需通过自动化手段释放劳动力，所以将核心流程回归的 UI 自动化提到了最核心地位。






#### JUnit5
测试框架
- 主要的作用：管理测试用例的
- 管理测试用例集、环境的区分、执行的顺序、数据的隔离、以及相关的数据驱动
- 以上就是测试框架的作用

allure报告
最后一定要做出一个报告来展示。



大家了解selenium吗
它是怎么去做这个 UI自动化的
它是怎么去驱动我们写的脚本？



我拿钥匙去开门

我相当于脚本
门相当于浏览器
钥匙是driver







## WebDriverManager

WebDriverManagerWebDriverManager 使用类（包）提供了一个流畅的 API io.github.bonigarcia.wdm。此类提供一组静态方法来创建管理器，即专门用于提供自动驱动程序管理和其他功能的对象。

WebDriverManager 的主要用途是驱动程序管理的自动化。要使用此功能，您需要在 WebDriverMager API 中选择一个给定的管理器（例如，chromedriver()对于 Chrome）并调用方法setup()。以下示例显示了使用JUnit 5、Selenium WebDriver、WebDriverManager 和AssertJ（用于流畅断言）的测试用例。在这个测试中，我们在所有测试的 setup 方法中调用 WebDriverManager ( @BeforeAll)。这样，所需的驱动程序 (chromedriver) 将可用于此类中使用 Chrome 的所有 WebDriver 测试。



WebDriverManager为 Chrome、Firefox、Edge、Opera、Chromium 和 Internet Explorer sarafi提供了一组管理器。这些管理器的基本用途如下：
```
WebDriverManager.chromedriver().setup();
WebDriverManager.firefoxdriver().setup();
WebDriverManager.edgedriver().setup();
WebDriverManager.operadriver().setup();
WebDriverManager.chromiumdriver().setup()
WebDriverManager.iedriver().setup();
```
浏览器查找器
从版本 5 开始，WebDriverManager 允许检测给定浏览器是否安装在本地系统中。为此，每个管理者都提供了方法getBrowserPath()。此方法返回一个Optional<Path>，如果给定浏览器未安装在系统中或检测到浏览器路径（在可选对象内），则该值为空。

WebDriver 生成器
  
**从版本 5 开始，WebDriverManager 允许使用 WebDriverManager API 实例化WebDriver对象（例如ChromeDriver、FirefoxDriver等）。使用每个经理的方法可以使用此功能create()。以下示例显示了使用此功能的测试。setup()请注意，在使用此功能时不需要WebDriverManager 调用该方法，因为驱动程序管理是由 WebDriverManager 在内部完成的。WebDriverManager 提供了优雅地quit()关闭创建的WebDriver实例的方法来补充此功能。**




