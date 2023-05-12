
# webview 自动化测试方法
## 自动化对比
| 方式       | 技术栈                               | 优点                                | 缺点                                    |
| ---------- | ------------------------------------ | ----------------------------------- | --------------------------------------- |
| 原生自动化 | uiautomator<br>appium<br>atx         | 简单<br>不依赖 webview 调试开关开启 | 不易维护                                |
| web 自动化 | selenium<br>chromedriver<br>minitest | 易维护                              | 不适合混合开发|
| 混合自动化 | appium                               | 易维护，通用                        | 技术复杂<br>依赖 webview 调试开关开启   |



## Hybrid App Auto技术原理
- 底层使用多个引擎管理自动化测试用例
>虽然是多个引擎，但是测试用例及底层是维持一二的原则。「一套 `driver` ，两套 `session` 机制」
- 涉及到 `WebView` 的页面使用 `chromedriver` 自动化 `webview` 组件
>app内嵌套的web页面都是Chrome浏览器，所以对应使用driver的时候我们选择的是Chromedriver，而不是其他的浏览器driver
> 无论是什么app，内部嵌套webview页面或者组件，就是嵌套了一个浏览器的页面组件。
- 使用 native 方式自动化 native 控件
>虽然是用driver进行`WebView`的测试，但是App内原生的内容还是使用原生空间进行自动化


## webview 自动化测试前提
在进行`WebView`自动化测试之前，需要把对应的driver准备好，即需要在电脑上下载Chromedriver。
- `chromedriver` 安装
- `chromedriver` 版本选择正确
- `appium capability` 参数配置

那既然知道需要安装Chromedriver，不同的app可能需要安装的driver版本也不相同，那我怎么知道对应的当前app应该安装哪个版本的driver呢？
这个就需要对应的chromedriver的一个版本选择。


### chromedriver 版本选择
下面的`map.json`就是对应的映射关系，需要把该`json`文件下载到本地。
- [chromedriver 版本与 chrome webview 版本存在绑定关系](https://raw.githubusercontent.com/appium/appium-chromedriver/master/config/mapping.json)
- `appium` 有版本判断，版本不对会出错

>当`app`内的所需要的`chromedriver`和安卓系统默认的`chromedriver`版本不同时，应该怎样选择？
- `app` 内置 `webview` 组件可能会跟系统默认自带的 `webview` 组件不一致，以 `app` 使用的 `webview` 为准


选择好对应版本后，就开始把对应的driver下载到本地。
### [chromedriver 安装](https://chromedriver.storage.googleapis.com/index.html)
- [国内 chromedriver 下载地址](https://registry.npmmirror.com/binary.html?path=chromedriver/)

- 如果未安装 `chromedriver` 会报错：

> No Chromedriver found that can automate Chrome 'x.x.xxxx'. You could also try to enable automated chromedrivers download server feature. See ... for more details

- 安装的`chromedriver`版本不对报错：

> unknown error: Chrome version must be >= xx.x.x.xxxx


## appium Webview

### chromedriver 自动发现机制
设置appium启动的参数，达到chromedriver在自动化过程中可以被自动发现。
- `chromedriverExecutableDir`： 指定 `chromedriver` 可执行文件集合的目录
- `chromedriverChromeMappingFile`： 允许显式指定版本对应关系
- `showChromedriverLog`： 让 `appium` 日志展示 `chromedriver` 的日志方便排查


### 设置 capability 示例 -- java

```java
//简单的方案
caps.setCapability("chromedriverExecutable", "/Users/seveniruby/projects/chromedriver/72/chromedriver");

//完善的自动发现方案
caps.setCapability("chromedriverExecutableDir", "/Users/seveniruby/projects/chromedriver/2.20");
caps.setCapability("chromedriverChromeMappingFile", "/Users/seveniruby/projects/Java3/src/test/java/test_app/wechat/mapping.json");
caps.setCapability("showChromedriverLog", true);
```

设置完成后，对应的就可以对app进行启动，启动后，操作到Webview页面，在进行WebView组件操作之前，需要先获取一下上下文，并且切换到WebView内。
## appium context 上下文机制

- 展示所有的上下文 `contexts` 
>第一个是原生 `NATIVE`，剩下的为 `weview`。如果手机后台有多个Webview，则会都打印出来。
- 获得当前的上下文 `Set<String> contextHandles = driver.getContextHandles();`
- 切换上下文         `driver.context("WEBVIEW_XXXX")`

### webview 自动化演示代码 java 版

```java

```















## 微信小程序自动化测试

---

### 微信小程序自动化测试方法

| 方式       | 技术栈                               | 优点                                | 缺点                                                                                        |
| ---------- | ------------------------------------ | ----------------------------------- | ------------------------------------------------------------------------------------------- |
| 原生自动化 | uiautomator<br>appium<br>atx         | 简单<br>不依赖 webview 调试开关开启 | 不易维护                                                                                    |
| web 自动化 | selenium<br>chromedriver<br>minitest | 易维护                              | 不适合混合开发<br>依赖 webview 调试开关开启<br>小程序不支持浏览器直接访问，此方法基本不可用 |
| 混合自动化 | appium                               | 易维护，通用                        | 技术复杂<br>依赖 webview 调试开关开启                                                       |

---

### 方法选型常见问题

- 使用原生自动化测试方法
  - 个别微信版本可能会遇到 uiautomator2 的 bug 导致无法自动化
  - 定位符基本不可维护，需要研发配合
- 使用 webview 自动化测试方法
  - 个别微信版本可能会遇到无法开启远程调试开关

---

![](assets/2022-02-26-16-22-16.png)

---

### 基于 webview 自动化技术测试小程序

- 上下文切换
  - 开启微信小程序的调试模式
  - 选对 chromedriver 版本
  - 修复 appium webview 与 xweb 的转换 bug
- 窗口切换
- 输入问题

---

### 小程序调试开关

- 如果是 x5 内核，请开启调试开关
- 在聊天窗口输入网址并打开即可进入 http://debugtbs.qq.com
- 如果是非 x5 内核，默认是开启的
- x5 内核切换开关 http://debugmm.qq.com/?forcex5=true
- 出现对应的 domain sockets 代表成功

![](assets/2022-02-23-06-24-43.png)

---

### chromedriver 版本选择

- 禁用 chromedriverExecutableDir
  - 微信使用了多种不同版本的 webview 内核，会出现识别错误
- 开启 chromedriverExecutable
  - 控制 chromedriver 版本

---

### appium webview 上下文识别 bug 修复

- appium 在切换上下文时，会把 xweb 标记错误替换为 webview
- 指令直接通过 adb 的 5037 端口发送
- 通过学社独家首创提供的 [adb_xweb_mock](https://ceshiren.com/t/topic/16397) 工具修复

---

```bash
ceshiren.com: ~ seveniruby$ adb shell cat /proc/net/unix | grep devtools
0000000000000000: 00000002 00000000 00010000 0001 01 11078370 @xweb_devtools_remote_31384
0000000000000000: 00000002 00000000 00010000 0001 01 11081895 @webview_devtools_remote_31145
0000000000000000: 00000002 00000000 00010000 0001 01 11073145 @xweb_devtools_remote_30462
0000000000000000: 00000002 00000000 00010000 0001 01 11073151 @webview_devtools_remote_30462
0000000000000000: 00000002 00000000 00010000 0001 01 11078388 @webview_devtools_remote_31384
```

---

### 窗口切换

- 小程序的每个界面都是新开窗口
- 需要按需切换窗口，可通过标题中的`:VISIBLE`或者 url 进行切换

![](assets/2022-02-26-14-59-53.png)

---

### 输入问题

- 小程序的输入控件有特殊设计，无法直接在 webview 下进行 send_keys
- 可以通过切换到原生去 send_keys 解决

---

### 触发直接输入事件 mobile: type

<div>

Types the given Unicode string. It is expected that the focus is already put to the destination input field before this method is called. The main difference between this method and the sendKeys one is that it emulates `true` typing like it was done from an on-screen keyboard. It also properly supports Unicode input characters.

| Name | Type   | Required | Description      | Example |
| ---- | ------ | -------- | ---------------- | ------- |
| text | string | yes      | The text to type | testing |

</div>

---

### 总结

> 鉴于这么多的坑，最好是封装自己的框架，以规避底层框架的问题

---

### 微信小程序自动化测试代码 python 版本

```python
class TestWechat:
    def setup_class(self):
        capabilities = {
            'platformName': 'android',
            # 为了不清理微信数据
            'noReset': True,
            'appPackage': 'com.tencent.mm',
            'appActivity': 'com.tencent.mm.ui.LauncherUI',
            'unicodeKeyboard': True,
            'resetKeyboard': True,
            'showChromedriverLog': True,
            # appium bug 切换context时会把xweb当成webview，需要借助adb xweb mock技术修复
            'adbPort': 5038,
            'chromedriverExecutable': '/Users/seveniruby/projects/chromedriver/chromedrivers/chromedriver_86.0.4240',
            # appium bug 会自动覆盖自己配置的mapping file，太智能了反而导致了bug
            # 'chromedriverChromeMappingFile': '/Users/seveniruby/PycharmProjects/AppiumDemo/mapping.json',
            # 'chromedriverExecutableDir': '/Users/seveniruby/projects/chromedriver/chromedrivers'
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', capabilities)
        self.driver.implicitly_wait(10)

    def test_wechat(self):
        self.driver.find_element(By.CSS_SELECTOR, '*[description="搜索"]').click()
        self.driver.find_element(By.CSS_SELECTOR, 'android.widget.EditText').send_keys("美团外卖")
        self.driver.find_element(By.XPATH, '//*[contains(@text, "外卖美食")]').click()

        print(self.driver.contexts)
        wait = WebDriverWait(self.driver, 5)
        # 个别app webview组件加载慢的时候不一定会及时出现webview上下文，需要加显式等待
        wait.until(lambda driver: filter(lambda c: "appbrand" in c, self.driver.contexts))
        print(self.driver.contexts)

        # 进入第一个打开的小程序
        self.driver.switch_to.context("WEBVIEW_com.tencent.mm:appbrand0")
        self.driver.implicitly_wait(10)
        self.switch_to_visible_window("/index/index.html")

        search_button = self.driver.find_element(By.CSS_SELECTOR, '.search-index--ellipsis')
        wait.until(expected_conditions.element_to_be_clickable(search_button))
        search_button.click()

        self.switch_to_visible_window("/search/search.html")
        self.driver.find_element(By.CSS_SELECTOR, '#search_input').click()

        print("输入")
        # 只能用这个办法输入
        self.driver.switch_to.context("NATIVE_APP")
        self.driver.execute_script("mobile: type", {"text": "啤酒炸鸡"})
        self.driver.switch_to.context("WEBVIEW_com.tencent.mm:appbrand0")

        print("点击搜索按钮")
        self.driver.find_element(By.CSS_SELECTOR, '#search_button').click()

        print("后退")
        self.driver.switch_to.context("NATIVE_APP")
        self.driver.back()
        self.driver.switch_to.context("WEBVIEW_com.tencent.mm:appbrand0")

        print("断言")
        self.switch_to_visible_window("/search/search.html")
        assert "啤酒炸鸡" in self.driver.find_element(By.CSS_SELECTOR, '[data-history=true]').text

    def switch_to_visible_window(self, pattern=":VISIBLE"):
        signal = False
        while not signal:
            for window in self.driver.window_handles:
                print(window)
                self.driver.switch_to.window(window)
                print(self.driver.current_url)
                print(self.driver.title)
                # 进入可视化的页面
                if pattern in self.driver.title:
                    print(pattern)
                    signal = True
                    break

```

---

### 微信小程序自动化测试代码 java 版本

```java
public class WechatTest {

    private static AndroidDriver driver;

    @BeforeAll
    public static void beforeAll() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("appPackage", "com.tencent.mm");
        desiredCapabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");

        desiredCapabilities.setCapability("adbPort", 5038);
        desiredCapabilities.setCapability("unicodeKeyboard", true);
        desiredCapabilities.setCapability("resetKeyboard", true);

        desiredCapabilities.setCapability("showChromedriverLog", true);
        //设置保存有所有chromedriver的一个目录，让appium自动发现对应的版本
        desiredCapabilities.setCapability(
                "chromedriverExecutable",
                "/Users/seveniruby/projects/chromedriver/chromedrivers/chromedriver_86.0.4240");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(url, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void wechat() {
        driver.findElement(By.cssSelector("*[description='搜索']")).click();
        driver.findElement(By.cssSelector("android.widget.EditText")).sendKeys("美团外卖");
        driver.findElement(By.xpath("//*[contains(@text, '外卖美食')]")).click();

        System.out.println(driver.getContextHandles());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(webDriver -> driver.getContextHandles().stream().anyMatch(context -> context.contains("appbrand")));
        System.out.println(driver.getContextHandles());

        driver.context("WEBVIEW_com.tencent.mm:appbrand0");
        switch_to_visible_window("/index/index.html");

        WebElement search_button = driver.findElement(By.cssSelector(".search-index--ellipsis"));
        wait.until(ExpectedConditions.elementToBeClickable(search_button));
        search_button.click();

        switch_to_visible_window("/search/search.html");
        driver.findElement(By.cssSelector("#search_input")).click();

        driver.context("NATIVE_APP");
        HashMap<String, Object> map = new HashMap();
        map.put("text", "啤酒炸鸡");

        driver.executeScript("mobile: type", map);
        driver.context("WEBVIEW_com.tencent.mm:appbrand0");

        driver.findElement(By.cssSelector("#search_button")).click();

        driver.context("NATIVE_APP");
        driver.navigate().back();
        driver.context("WEBVIEW_com.tencent.mm:appbrand0");

        switch_to_visible_window("/search/search.html");
        assertThat(
                driver.findElement(By.cssSelector("[data-history=true]")).getText(),
                containsString("啤酒炸鸡"));


    }

    public void switch_to_visible_window(String pattern) {
        boolean signal = false;
        while (!signal) {
            Set<String> windows = driver.getWindowHandles();
            for (String window : windows) {
                driver.switchTo().window(window);
                String url = driver.getCurrentUrl();
                String title = driver.getTitle();
                System.out.println(url);
                System.out.println(title);
                if (title.contains(pattern)) {
                    signal = true;
                    break;
                }
            }
        }

    }
}

```

---

## 手机浏览器自动化测试

---

### 手机浏览器自动化前提

- [安装 chromedriver](https://ceshiren.com/t/topic/3275)
- [安装对 chromedriver 版本](https://ceshiren.com/t/topic/3275)
- 设置 capability
- 设置 chromedriver 相关配置
- 使用浏览器的 inspect 工具远程调试

---

## capability 配置

- browserName chrome browser
- chromedriverExecutableDir
- showChromedriverLog

---

### chromedriverExecutableDir

```plain
ceshiren.com: ~ seveniruby$ ls /Users/seveniruby/projects/chromedriver/chromedrivers
chromedriver_2.20		chromedriver_79.0.3945.36
chromedriver_2.23		chromedriver_80.0.3987.106
chromedriver_2.42		chromedriver_81.0.4044.69
chromedriver_74.0.3729.185	chromedriver_95.0.4638.54
chromedriver_76			fakechromedriver_107.sh
chromedriver_78.0.3904.11
```

---

### 手机浏览器自动化测试 python 版

```python
class TestBrowser:
    def setup_class(self):
        # setup the web driver and launch the webview app.
        capabilities = {
            'platformName': 'android',
            'browserName': 'chrome',
            # python appium client 2.x 需要追加这个参数
            'chromeOptions': {'w3c': False},
            'chromedriverExecutableDir': '/Users/seveniruby/projects/chromedriver/chromedrivers'
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', capabilities)
        self.driver.implicitly_wait(10)

    def test_browser(self):
        self.driver.get("https://ceshiren.com")
        self.driver.find_element(AppiumBy.ID, 'search-button').click()
        self.driver.find_element(By.CSS_SELECTOR, '.search-query').send_keys('webview')
        self.driver.find_element(By.CSS_SELECTOR, 'button.search-cta').click()
        assert 'webview' in self.driver.find_element(By.CSS_SELECTOR, '.topic-title').text

```

---

### 手机浏览器自动化测试 java 版

```java
public class WebViewTest {

    private static AndroidDriver driver;

    @BeforeAll
    public static void beforeAll() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("noReset", true);
        desiredCapabilities.setCapability("browserName", "Chrome");
        //设置保存有所有chromedriver的一个目录，让appium自动发现对应的版本
        desiredCapabilities.setCapability("chromedriverExecutableDir",
                "/Users/seveniruby/projects/chromedriver/chromedrivers");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(url, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void browser() {
        driver.get("https://ceshiren.com");
        driver.findElement(By.id("search-button")).click();
        driver.findElement(By.cssSelector(".search-query")).sendKeys("webview");
        driver.findElement(By.cssSelector("button.search-cta")).click();
        assertThat(
                driver.findElement(By.cssSelector(".topic-title")).getText(),
                containsString("webview")
        );
    }
}
```

---

## webview 自动化测试技术原理

---

## appium 是如何做 webview 自动化测试的

- chromedriver 启动
- chromedriver 根据 chrome option 完成对 android 手机的控制
- 利用 adb 寻找/proc/net/unix 下的 domain socket
- 根据 socket 名字中的 pid 寻找进程名，用于生成对应的 context 名字
- 利用 adb forward 将 domain socket 映射为到 pc 机器上的 tcp socket
- chromedriver 与 tcp socket（devtool 协议）交互

---

## 通过 UNIX domain sockets 发现 webview 进程

```bash
#getContextHandles的原理，webview进程
ceshiren.com: android-apidemos seveniruby$ adb shell cat /proc/net/unix | grep webview
Num       RefCount Protocol Flags    Type St Inode Path
0000000000000000: 00000002 00000000 00010000 0001 01 30196220 @webview_devtools_remote_19281
0000000000000000: 00000002 00000000 00010000 0001 01 30098752 @webview_devtools_remote_11395
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_11395
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_11395
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_11395

#浏览器进程
ceshiren.com: android-apidemos seveniruby$ adb shell cat /proc/net/unix | grep chrome
0000000000000000: 00000002 00000000 00010000 0001 01 28684857 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 03 30273863 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 03 30273858 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 03 30273864 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 03 30273865 @chrome_devtools_remote
```

---

## 查找所有的 webview 与浏览器开启的 domain sockets

```bash
ceshiren.com: android-apidemos seveniruby$ adb shell cat /proc/net/unix | awk 'NR==1 || /devtools/'
Num       RefCount Protocol Flags    Type St Inode Path
0000000000000000: 00000002 00000000 00010000 0001 01 30196220 @webview_devtools_remote_19281
0000000000000000: 00000002 00000000 00010000 0001 01 28684857 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 03 30273863 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_19281
0000000000000000: 00000003 00000000 00000000 0001 03 30273858 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 03 30273864 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_19281
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_19281
0000000000000000: 00000003 00000000 00000000 0001 03 30273865 @chrome_devtools_remote
0000000000000000: 00000003 00000000 00000000 0001 02     0 @webview_devtools_remote_19281
```

---

## 分析 domain socket 归属进程

```bash
ceshiren.com: android-apidemos seveniruby$ adb shell cat /proc/net/unix | grep webview
0000000000000000: 00000002 00000000 00010000 0001 01 30196220 @webview_devtools_remote_19281

ceshiren.com: android-apidemos seveniruby$ adb shell ps 19281
USER           PID  PPID     VSZ    RSS WCHAN            ADDR S NAME
u0_a187      19281   699 6321568 161788 0                   0 S io.appium.android.api
```

---

## 连接 domain sockets--context 切换的原理

```bash
#把domain socket映射为本地的socket端口
adb forward tcp:$port localabstract:webview_devtools_remote_$pid

adb forward tcp:9001 localabstract:webview_devtools_remote_19281
9001
```

---

## domain sockets 的通讯协议分析

```bash
ceshiren.com: android-apidemos seveniruby$ curl http://localhost:9001/
<html><head><title>WebView remote debugging</title></head><body>Please use <a href='chrome://inspect'>chrome://inspect</a></body></html>

ceshiren.com: android-apidemos seveniruby$ curl http://localhost:9001/json
[ {
   "description": "{\"attached\":true,\"empty\":false,\"height\":2408,\"never_attached\":false,\"screenX\":0,\"screenY\":292,\"visible\":true,\"width\":1228}",
   "devtoolsFrontendUrl": "https://chrome-devtools-frontend.appspot.com/serve_rev/@30dcf3520abaf635bfb2064039f4078afd12cec7/inspector.html?ws=localhost:9001/devtools/page/51E00D7C2B3C8785879A63D98099B002",
   "id": "51E00D7C2B3C8785879A63D98099B002",
   "title": "I am a page title",
   "type": "page",
   "url": "file:///android_asset/html/index.html",
   "webSocketDebuggerUrl": "ws://localhost:9001/devtools/page/51E00D7C2B3C8785879A63D98099B002"
} ]
```

---

## 系统内置与 app 内置 webview 的不同

```bash
#胸痛内置webview
ceshiren.com: android-apidemos seveniruby$ adb shell dumpsys package com.android.webview | grep versionName
ceshiren.com: android-apidemos seveniruby$ adb shell dumpsys package com.android.chrome | grep versionName
    versionName=76.0.3809.132

#app使用的webview
ceshiren.com: android-apidemos seveniruby$ curl http://localhost:9001/json/version
{
   "Android-Package": "io.appium.android.apis",
   "Browser": "Chrome/88.0.4324.93",
   "Protocol-Version": "1.3",
   "User-Agent": "Mozilla/5.0 (Linux; Android 10; JAD-AL50 Build/HUAWEIJAD-AL50; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/88.0.4324.93 Mobile Safari/537.36",
   "V8-Version": "8.8.278.14",
   "WebKit-Version": "537.36 (@30dcf3520abaf635bfb2064039f4078afd12cec7)",
   "webSocketDebuggerUrl": "ws://localhost:9001/devtools/browser"
}
```

---

## devtools 前端调试界面

![](assets/2022-02-08-15-07-09.png)

---

## chrome devtools 协议

![](assets/2022-02-08-14-30-10.png)

---

## 总结

- 熟悉 webview 自动化测试的关键技术点
- 辅助 webview 测试问题排查
- chrome driver、devtools 是 webview 自动化测试的核心
