
## Selenium 介绍

- 官网：https://www.selenium.dev/

Selenium 被我们通常用做测试web的自动化测试工具，其实Selenium 不仅仅是个API，它是一组工具集合，它是由三大组件组成

- WebDriver: 可以模拟真正的用户去操作浏览器页面，主要是通过控制浏览器厂商提供的driver 来控制浏览器

- Selenium IDE: 是安装在浏览器上一个扩展工具，可以将操作浏览器的过程录制下来，可以导出不同的语言，不同的框架格式

- Selenium Grid: 可以在不同电脑上执行测试用例，实现分布式并发执行测试用例的效果。

### Web自动化测试的意义

通常我们所说的 Web 自动化测试，就是使用Selenium WebDriver 来对浏览器进行自动化控制。可以调用 Selenium API 操作浏览器，解放人力成本（比如，打开一个baidu.com ，然后找到搜索框，输入搜索内容，验证结果等一连串的用户行为），它的意义体现在如下几个方面：

- 回归测试

- 可以执行手工测试很难实现或者很耗时的测试工作，比如并发测试、压力测试

- 能够更好的利用资源，节省时间和人力成本

### Selenium 工作原理

![image|800x267](upload://p2o6NSC4pVg09l8PQEIb6YALwk9.png)


如图所示，Selenium 实现自动化测试，主要由三部分在工作：

- 客户端脚本（Client）

- 浏览器驱动（Driver）

- 浏览器（Browser）

1. 开始执行测试时，测试用例会作为Client端，Selenium 启动浏览器，后台会同时启动一个Selenium 的Remote Server 绑定到浏览器上，RemoteServer 是用来监听代码端(Client)发来的操作请求

2. 当代码发送一个http request 请求给Server ,Server 会解析这个请求，然后转发给 webdriver，由webdriver 来实际驱动浏览器执行操作（webdriver 就是不同的浏览器厂商提供的不同的driver驱动）

3. webdriver 操作完浏览器的执行结果，会返回给server ，最终结果返回给client 端

这是一套完整的流程，所以搭建测试环境的时候，一定要 先下载【浏览器对应】的 WebDriver。

## 为什么能够支持这么多种浏览器？

- Selenium WebDriver 是典型的 Server-Client 模式，Server 端就是 Remote Server

- 浏览器的厂商们给我们提供叫做 driver的中间件， 通过编写脚本，去给这个中间件发送请求（遵循WebDriver Wire 协议 ）实现对浏览器的控制

举个例子，比如约定的协议 `/session/{session id}/element`  是查找元素， `

/session/{session id}/element/{element id}/click`  是点击元素

- 通过这个中间件 可以直接驱动浏览器执行各种操作，比如点击，滑动， 输入，下拉等等。

- 通过这个中间件，我们可以控制浏览器

我们可以通过写脚本，去给这个中间件(chrome - chromedrive , firefox-geckodriver)发送请求，那这个请求是如何发送的呢？ 遵循一个特定的协议 进行通讯，

举个例子，提前约定好一个协议 ，你发给我的请求遵循一个特定的协议格式 ，比如约定的协议 /session/{sessionid}/find 就是查找元素，/click 就是点击元素， （这个协议 就是Webdriver 协议）

### 代码演示

可以通过代码看一下具体工作过程

- 第一步：下载对应的chromedriver 并配置环境变量

- 第二步：打印DEBUG级别的日志

- 第三步：创建driver

- 第四步：打开浏览器

- 第五步：driver.quit()退出操作

代码：

```python

# 创建文件 demo.py   ****不要创建以test_开头*****

from selenium import webdriver

import logging

logging.basicConfig(level=logging.DEBUG)  # 打印源码中的日志

driver = webdriver.Chrome() # 打开浏览器

driver.get("https://www.ceshiren.com") # 访问 测试人论坛

```

结果：

```bash

1. DeprecationWarning: executable_path has been deprecated, please pass in a Service object

  driver = webdriver.Chrome(executable_path="D:\\mydriver\\chromedriver.exe") # 打开浏览器

2. DEBUG:selenium.webdriver.remote.remote_connection:POST http://localhost:53116/session {"capabilities": {"firstMatch": [{}], "alwaysMatch": {"browserName": "chrome", "pageLoadStrategy": "normal", "goog:chromeOptions": {"extensions": [], "args": []}}}}

3. DEBUG:urllib3.connectionpool:Starting new HTTP connection (1): localhost:53116

4. DEBUG:urllib3.connectionpool:http://localhost:53116 "POST /session HTTP/1.1" 200 788

5. DEBUG:selenium.webdriver.remote.remote_connection:Remote response: status=200 | data={"value":{"capabilities":{"acceptInsecureCerts":false,"browserName":"chrome","browserVersion":"106.0.5249.119","chrome":{"chromedriverVersion":"106.0.5249.61 (511755355844955cd3e264779baf0dd38212a4d0-refs/branch-heads/5249@{#569})","userDataDir":"C:\\Users\\xixi\\AppData\\Local\\Temp\\scoped_dir9520_379661371"},"goog:chromeOptions":{"debuggerAddress":"localhost:53119"},"networkConnectionEnabled":false,"pageLoadStrategy":"normal","platformName":"windows","proxy":{},"setWindowRect":true,"strictFileInteractability":false,"timeouts":{"implicit":0,"pageLoad":300000,"script":30000},"unhandledPromptBehavior":"dismiss and notify","webauthn:extension:credBlob":true,"webauthn:extension:largeBlob":true,"webauthn:virtualAuthenticators":true},"sessionId":"fea574de37447a010cc20ab992cb9ce9"}} | headers=HTTPHeaderDict({'Content-Length': '788', 'Content-Type': 'application/json; charset=utf-8', 'cache-control': 'no-cache'})

6. DEBUG:selenium.webdriver.remote.remote_connection:Finished Request

7. DEBUG:selenium.webdriver.remote.remote_connection:POST http://localhost:53116/session/fea574de37447a010cc20ab992cb9ce9/url {"url": "https://www.ceshiren.com"}

8. DEBUG:urllib3.connectionpool:http://localhost:53116 "POST /session/fea574de37447a010cc20ab992cb9ce9/url HTTP/1.1" 200 14

9. DEBUG:selenium.webdriver.remote.remote_connection:Remote response: status=200 | data={"value":null} | headers=HTTPHeaderDict({'Content-Length': '14', 'Content-Type': 'application/json; charset=utf-8', 'cache-control': 'no-cache'})

10. DEBUG:selenium.webdriver.remote.remote_connection:Finished Request

```

日志解析

- `DEBUG:selenium.webdriver.remote.remote_connection:`   开始请求的标识

- `Finished Request`  是一次请求结束的标识

- 首先，第二行发送POST请求，带着参数，创建一个http 连接，第五行得到响应状态码为200，并得到响应对象，第六行结束第一次请求

- 然后，第七行，发送一个post请求，打开网址，第9行，得到响应状态码200，以及响应数据，第10行结束第二次请求

### 模拟Selenium 底层请求过程

```python

import requests

# 请求地址(打开浏览器)

driver_url = 'http://127.0.0.1:53119/session'

# 打开浏览器的请求参数

driver_value = {"capabilities": {"firstMatch": [{}], "alwaysMatch": {"browserName": "chrome", "platformName": "any", "goog:chromeOptions": {"extensions": [], "args": []}}}, "desiredCapabilities": {"browserName": "chrome", "version": "", "platform": "ANY", "goog:chromeOptions": {"extensions": [], "args": []}}}

# 发送求清

response_session = requests.post(driver_url, json = driver_value)

result = response_session.json()

sessionId = result['value']['sessionId']

print(f"sessionId ===>> {sessionId}")

# 访问 [测试人论坛]请求地址

url = 'http://127.0.0.1:53119/session/'+sessionId+'/url'

# # 访问我的博客的请求参数

value = {"url": "https://www.ceshiren.com"}

response1 = requests.post(url = url,json = value)

print(response1)

```

- 首先模拟发送一个http 请求， 请求session对象

- 从结果中解析出sessionid

- 再使用这个sessionid 发送一个请求，打开页面，得到响应结果

- 其它操作也是一样（参考w3c webdriver协议）