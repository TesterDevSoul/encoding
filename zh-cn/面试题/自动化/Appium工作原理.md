## 问题

在工作中，使用什么进行的App端自动化？

有没有用到Appium 这个框架？

对Appium熟悉吗？

Appium 是什么？主要用来做什么的？它的核心原理是什么？

Appium底层原理是怎样驱动手机进行操作的？
## 考察点
面试官想了解：
- 是否用过Appium框架
- 对Appium框架的常用API是否熟悉
- 移动端端特殊控件、特殊操作是否有解决方案
- 是否看过Appium的源码
- 是否了解Appium框架的底层工作原理
## 技术点
涉及的技术点：
- Appium API
- Appium 底层通讯协议
- Appium 框架原理
## 回答
首先，对Appium框架进行介绍，说明对应框架的特性；再画出通信原理图进行解释说明；说明一下常用操作的API；最后进行整体的总结。
### Appium框架介绍
>Appium是什么？

首先，Appium 是一款开源框架，生态丰富，社区强大，一直在被开发者维护；

并且是一个灵活的**跨平台**的测试自动化工具，可以在iOS、Android 或 Windows、Mac 设备
上进行移动应用的自动化测试运行，并且能够使用相同的 API 编写多个平台的测试脚本；

Appium与Selenium类似，是一个**跨语言**的自动化框架，并且可以和任何测试框架相结合；可以使用不同的编程语言编写测试脚本，包括 Java、JavaScript、PHP、Ruby、Python 和 C#。

### Appium通信-各个端介绍
`Client`端发送自动化指令给 Appium Server ，Appium Server 接收到 Client端发送的指令后，进行转换，转换为移动端能够识别的指令，然后发送给移动端设备，移动端设备根据指令进行操作。

如下图为自动化通信交互：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211171710234.png)
#### Client端
Client端 就是运行编写的自动化项目代码，使用Appium-client提供的API来进行用例的编写。
其中，声明`DesiredCapabilities`对象，指定被测设备及app的相关信息；`DesiredCapabilities`对象中指定`automationName`的value值为`UiAutomator2`，这样才可以通过 `Appium Server` 去创建一个Android的session会话

自动化项目代码中首先请求创建一个 AndroidDriver 实例，需要传入`Appium Server`的 URL 及 DesiredCapabilities对象

#### Appium Server
Appium Server启动时默认的占用的端口号为4723，主要作用就是进行API请求的监听。
接收Client端发送的命令，并进行转换，转为移动端可识别的指令，并发送给移动端设备进行操作，再等待移动设备返回操作结果。
Appium Server端接收到移动设备返回的结果再将操作结果发送给Client端。

其中，Appium server可以和自动化项目代码同时放在Client端，也可以放在云端。
如果与代码同时放在Client端上，Appium Server的IP地址为127.0.0.1; 
如果放在云端，Appium Server的IP地址为云端服务器的IP地址。

#### Android移动端
Appium 基于JSON Wire协议，通过调用UIAutomatior命令，实现APP的自动化测试。
其中，UIAutomator测试框架是Android SDK自带的APP UI自动化测试Java库。

#### 总结
客户端发送请求给Appium Server，Appium Server转换请求给移动端，在移动端操作完成后返回响应内容给Appium Server，
Appium Server再把结果返回给客户端。
### Appium通信-端与端之间
#### Client端与Appium Server

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/202211161734455.png)

客户端请求创建driver会话，将声明的capabilities启动参数通过端口4723传递给Appium Server；

Appium Server基于capabilities中的automationName属性，重定向到相应的驱动模块。
如果automationName是uiautomator2，Appium Server重定向到UIAutomator2 Driver，
此时创建session会话并生成对应的sessionID。

#### Appium Server与Android移动端
##### APK安装 
- appium settings

为了在Android手机上运行应用程序，Appium 需要有一定的权限来控制系统设置。
Appium安装了一个小应用程序io.appium.settings，安装成功后，Appium Settings在后台运行。

- uiautomator2 server

由于使用的是UIAutomator2 Driver，所以要在Android手机上安装Driver相关的apk，即 appium-uiautomator2-server-xx.apk。

安装完成后，Appium Server的**8200**端口 和 Android设备端的**6790**端口建立映射，通过该映射的链路进行通讯。

#### 总结
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/202211171647746.png)

1. `Client`端发送操作指令给`Appium Server`

2. `Appium Server`通过`appium-uiautomator2-driver`发送`JWP`协议的请求到`Android`的`appium-uiautomator2-server`

3. `appium-uiautomator2-server`调用`Android`系统的 `Google UIAutomator2` 去以执行自动化具体的操作
4. 操作完成后返回结果对象`AppiumResponse`给`appium-uiautomator2-driver`，`AppiumServer`再返回给`Client`端，`Client`端得到最终执行操作的结果


### 注意
- appium-uiautomator2的driver和server之间的通信的协议是JWP协议
- 创建AndroidDriver遵循的是W3C协议进行创建