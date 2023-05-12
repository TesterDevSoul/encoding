# WebView
本节内容：
- 了解 `webview` 原理
- 掌握 `webview` 分析方法
- 了解 `webview` 基本属性
- 为`webview` 自动化测试做铺垫

## App应用
- 原生应用
- 混合应用
### 原生应用

原生应用就是可以从 `Apple App Store` 和 `Android应用商店` 可以下载的应用。

当创建原生应用程序时，不同的平台所需的编程语言是不同的。
比如，适用于 `Android` 应用程序的 `Java` 或 `Kotlin`。
适用于 `iOS` 应用程序的 `Objective–C` 或 `Swift`。

适用于 `Android` 的原生应用程序无法在 `iOS` (Apple) 生态系统中运行，反之亦然。因此，企业为每个平台开发移动`App`程序。

上面简单介绍了一下对应的原生应用，那么下面来说下为什么还需要这个`WebView`去进行开发`App`。

### WebView应用
根据下图进行一个说明：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/webview/202207251652770.png)



首先，思考一下，什么`App`会用到`WebView`进行开发？

>比如说，微信小程序就是一个`WebView`的应用，还有淘宝、京东 等等这些`App`操作的时候会进行一个分享，进入到第三方`app`内分享链接，一般都是微信也有可能是`QQ`。也就是，我们可以直接在微信或者是在`QQ`里面直接打开的一个网站，那这就是一个混合应用，打开的这个网站界面就是一个`webView`的界面。

目前市面上流行的`App`，大部分都是一个混合应用开发。混合应用的开发技术，就是首先要是一个原生`App`，然后在原生`App`上嵌套了`WebView`的界面，或者说是包裹了一个`WebView`的内核。

`App`开发的时候分为2部分：
- 一个是原生`App`的开发，「`Android`端的研发写的是`Java` 或 `Kotlin`；iOS端的研发，写的是`Objective–C` 或 `Swift`。」
- 一个是H5开发。
>原生端的开发直接写对应的逻辑，原生的业务逻辑不需要用到任何的`H5`，原生开发的`App`一般所有的逻辑都固化在里面，界面要是想进行一个动态的变化，比较困难。一般研发会建议如果是动态页面，交给前端的`H5`开发，而不是原生`App`这边去做这个功能。

混合应用中相对固定的一些组件，百年不变的模块都使用原生来开发，比如微信里面的tab切换，就可以直接使用原生开发。原生的开发只开发出这个壳子，剩下的都通过一个H5来进行开发。这样开发团队，`Android`和`iOS`团队大部分逻辑都是可以复用的。`React`开发`webview`界面，这样`iOS`和`Android`团队就可以复用很多资源。

原生功能一般就是性能比较高，如果是说为了`App`可以跨平台使用，那大部分公司就会选择混合应用的开发方式。
一部分人解决底层的`API`，比如安卓底层的`API`调用，比如安卓的定位，中间封装一层。到了`webView`这一层通过JS去调用
这样的好处就是底层的团队人员比较少，大量的页面、逻辑流程 `iOS`和`Android`端都共用一套。

>手机端的浏览器打开网址，其实浏览器本身也是一个原生的`APP`。上面套了一层`WebView`。浏览器其实也是一个个混合架构



## WebView的识别


### 控件转换

原生布局，对应的页面显示为具体的`View`，比如：
- `android.widget.ImageView`
- `android.widget.TextView`

`WebView`会在页面有具体的`WebView`标签，`html` 控件尽量用原生控件表达，比如：
- 文本转 `android.widget.TextView`
- 图片转 `android.widget.ImageView`

如果没有适合原生控件表达的 `html` 控件用 `android.view.View `表达。




![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/webview/202207261531342.png)
如果公司对于`WebView`进行二次封装改造，也会带着`webview`，只是前缀稍微可能不太一样。

如果解析不出来具体的`View`则会显示为`android.view.View`。





### 属性转换

`html` 控件中的属性用原生控件表达，根据不同的版本转换为 `resource-id`、`content-desc`、`text`；
但是 `native` 无法表达的 `html` 属性会丢失，比如 `class` `href` `src` 等属性。


## `WebView`自动化
`WebView`自动化其实是结合了`Web`自动化和`App`自动化这两种技术。



### 控件在 `appium` 中的抽象

##### `native` 层面支持

- `uiautomator`解析`Webview`中的内容并映射为原生控件。
- `getPageSource`的结果为`dom`结构
>`getPageSource`可发现`webview`组件和控件


##### 切换为 `Webview` 上下文

- 切换上下文后才是正规的`web`
- `getPageSource`为`html`
- 可以使用`css`定位等





### `Webview` 开关
`Webview`打开的方式有两种，一种是直接使用安卓6.0的系统版本，该版本默认是支持打开`Webview`。还有一种是如果使用真机-安卓系统版本是6.0以上，那需要开发在写相关代码的时候打开`app`内的`Webview`的调试开关。

如果`Webview` 的调试开关未打开的情况下，`uiautomator2`会自动把`Webview` 控件解析为原生控件，使用原生自动化方式也可以做简单的`Webview` 控件自动化。


##### [webview 调试开关](https://developers.google.com/web/tools/chrome-devtools/remote-debugging/webviews?hl=zh-cn)

必须从应用中启用`WebView`调试。要启用`WebView`调试，需要在`WebView`类上调用静态方法`setWebContentsDebuggingEnabled`。


这个设置适用于应用的所有`WebView`，不区分页面，整个`app`内只要包含`WebView`的页面都会打开`debug`。
```java

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    WebView.setWebContentsDebuggingEnabled(true);
}

//适合正式上线的app，至于debug开启的时候，才开启webview的调试
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
    if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
    { WebView.setWebContentsDebuggingEnabled(true); }
}

```

>里面可以写开启的条件，比如多次点击某个控件，即可打开该开关。就像安卓手机多次点击系统信息的时候会打开 开发者模式。


#### 各平台 webview 组件的调试开关

| 平台 webview 组件调试开关            | 应用 webview 组件调试开关 | 可调试    |
| ------------------------------------ | ------------------------- | --------- |
| as 模拟器 android 6.0 默认开启       |                           | ✅ 可调试 |
| as 模拟器 android 7.0 及以上默认关闭 | 关闭                      |           |
| as 模拟器 android 7.0 及以上默认关闭 | 开启                      | ✅ 可调试 |
| 真机默认关闭                         | 开启                      | ✅ 可调试 |
| 真机默认关闭                         | 关闭                      |           |
| 微信小程序内核 某些老版本默认开启    |                           | ✅ 可调试 |
| 微信小程序内核 默认关闭              |                           |           |


####  强行打开 webview 调试开关的办法

- root 手机修改 webview 内核 xposed
- 使用沙箱，hook webview 组件的调用 virtualapp
- 自己编译 android 系统

> 因为这些方法不常用且有各种局限，所以使用不多，仅限于安全领域研究。使用最多的办法是通过后门开启调试开关
### webview 分析

- 打开 `webview` 调试开关
- 打开带有 `webview` 的页面
- 打开浏览器的分析工具
  - `chrome://inspect`
  - [推荐] `edge://inspect`
  - firefox `about:debugging`
    直接调试浏览器中的 web app

#### 直接调试浏览器中的 web app
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/webview/202207271452324.png)
>可以看到解析出来的就是一个html的页面，也就是app里面嵌套了web页面。

#### 调试雪球 webview 组件

两种方式调试雪球App的组件：
- 需要在 `Android 6.0` 模拟器上
- 真机上需要开启 `webview` 调试开关的特定版本

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/webview/202207271505010.png)

