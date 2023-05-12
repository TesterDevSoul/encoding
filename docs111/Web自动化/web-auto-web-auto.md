---
title: web-auto
date: 2022-10-29 10:18:00.221
updated: 2022-10-29 10:59:23.879
url: /archives/web-auto
categories: 
tags: 
---

企业微信

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




