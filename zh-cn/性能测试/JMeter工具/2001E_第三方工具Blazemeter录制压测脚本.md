# 第三方工具Blazemeter录制脚本

## 本章要点
1. BlazeMeter介绍
2. 录制的流程
3. BlazeMeter录制优点

## BlazeMeter介绍
`Blazemeter`是一个企业级的连续测试平台，可以录制脚本也可以做很多其他的测试相关的事情。
不只是可以做性能测试也可以做功能测试，甚至是模拟你的服务接口进行`mock`。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209114238.png)


可以直接在`Chrome`浏览器中通过[应用商店](https://chrome.google.com/webstore/detail/blazemeter-the-continuous/mbopgmdnpcbohhpnfglgohlbhfongabi?hl=zh-CN)进行安装。

## BlazeMeter安装&注册

首先来看一下对应的`BlazeMeter`官方对于[Chrome扩展的帮助文档](https://guide.blazemeter.com/hc/en-us/articles/206732579-Chrome-Extension)。可以看到在帮助文档页面，不只有对应如何结合`JMeter`进行性能测试还有`Blazemeter`对应的`API`文档。


`BlazeMeter`是一款与`Apache JMeter`兼容的`chrome`插件，采用`BlazeMeter`可以方便的进行流量录制和脚本生成，作为接口测试脚本编写的一个基础。

记录所有浏览活动以创建`JMeter`脚本，并自动将其上传到`BlazeMeter`平台。


### 安装BlazeMeter

`Blazemeter`是一个企业级的连续测试平台，可以直接在`Chrome`浏览器中通过[应用商店](https://chrome.google.com/webstore/detail/blazemeter-the-continuous/mbopgmdnpcbohhpnfglgohlbhfongabi?hl=zh-CN)进行安装。
如果大家由于网络原因不能访问应用商店，可以直接通过扩展程序安装[Blazemeter](Blazemeter-crx/mbopgmdnpcbohhpnfglgohlbhfongabi_v5.2.0.crx)。

步骤：
1. 点击Chrome浏览器右上角图标，选择  **更多工具**——>**扩展程序**。
2. 将`blazeMeter_v5.2.0.crx`文件直接拖拽进入扩展程序空白处，`BlazeMeter`插件自动安装。

#### 验证

浏览器标签栏出现红框部分表示插件安装成功：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230207153832.png)

注意⚠️：
- `Chrome`浏览器版本为**58**以上版本。
- `BlazeMeter`版本：
  - 应用商店直接下载版本5.5.0。
  - crx文件安装5.2.0。


## 录制流程

1. 压测对象。
2. 压测页面。
3. 业务步骤。
4. 录制步骤。


### 压测对象

选用腾讯的[新闻](https://new.qq.com/)页面进行压测脚本的录制。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230213155153.png)

### 压测页面

涉及到的压测页面包括**三**个，分别是：腾讯**新闻首页**、新闻的**财经**页面、新闻的**科技**页面。


### 业务步骤

1. 打开浏览器，访问腾讯[新闻](https://new.qq.com/)页面。
2. 点击页面Tab栏中的[财经](https://new.qq.com/ch/finance/)跳转到财经新闻页面。
3. 点击页面Tab栏中的[科技](https://new.qq.com/ch/tech/)跳转到科技新闻页面。


以上为业务的功能测试流程。

### 录制步骤

下面来看下怎样使用`BlazeMeter`的代理服务器来录制脚本。

1. 账号登录。
2. 录制执行。
   1. 脚本命名。
   2. 录制执行业务步骤。
   3. 脚本更改。
3. 脚本生成。
4. 脚本验证。



#### 1. 账号登录
- 进入BlazeMeter面板登录

点击浏览器对应`BlazeMeter`图标可以看到录制面板。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230207154808.png)

点击右上角的 **登录** 等待几分钟，进入到 **登录/注册** 页面。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230207175339.png)

进入页面后，如果大家有对应的账号则根据提示填入用户名和邮箱信息，保存即可进入`BlazeMeter`界面。
 
如果没有账号可以直接进行注册，注册页面如下：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209110346.png)

也可直接使用`google`账号登录，无论是哪种方式，最终进入的页面如下：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209110013.png)



#### 2. 录制执行

##### 录制界面功能简介

登录成功后，再次打开录制面板，可以看到右上角有对应的用户名的`firstName`显示。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209112222.png)

**测试任务名**内输入脚本文件的名称，在录制完成后以此为名字导出脚本。
>输入的英文名无论大小写都显示为大写，如：QQNEW_DEMO。

点击**停止录制**按钮，`BlazeMeter`停止录制。

点击**开始录制**按钮，`BlazeMeter`开始当前浏览器的页面录制。

点击**回退**按钮，`BlazeMeter`页面恢复默认值。

##### 2.1 脚本命名

- 输入用例脚本名

在页面输入**测试任务名**为`QQNEW_DEMO`。录制完成后保存在本地的`JMX`脚本文件名为该名称。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209150344.png)

##### 2.2 录制执行业务步骤

1. 点击**开始录制**按钮。
2. 打开网址进行**业务步骤**操作，每个步骤为一个step。
3. 点击**停止录制**按钮，等待`BlazeMeter`生成脚本。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/230213143703.gif)

录制后再点开`BlazeMeter`面板如下：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209151846.png)


##### 2.3 脚本更改

1. 点击编辑按钮，进入编辑页面。
  >如果在下载前需要对脚本进行更改，则在保存前点击编辑按钮。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230213145258.png)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230213145545.png)


可以看到有所有的录制请求，可以进行筛选或编辑，本篇文章不做任何修改。



#### 3. 脚本生成

1. 点击**保存**按钮跳转到筛选保存脚本页面。
  >页面有`JMeter`的脚本JMX保存及`Selenium`脚本保存。<br>在使用`BlazeMeter`录制操作业务步骤时，`BlazeMeter`不只是对**接口请求**进行了录制，还对当前`UI`界面的操作进行了录制（有点像`Selenium IDE`）。<br>在这里我们主要是针对接口进行压测脚本的录制，所以，选择第一个选项JMX保存。<br>如果第一个选项置灰，此时第三个选项的按钮也同样置灰，代表当前业务步骤的接口请求录制失败。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209152021.png)

2. 选择要保存的脚本及包含的请求域名。

  >当选择`JMeter`选项后，还会让我们对录制的过程中的域名进行选择。<br>业务请求的操作步骤中只要涉及到了的请求或当前浏览器窗口打开的标签栏内的地址，对应域名都会在包含进来。<br>如果分不清哪些域名为录制的请求所需域名，建议大家都选中。本篇文章中，对应的域名选中`qq.com`。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230213144751.png)

3. 脚本生成并下载。
  >选择完成后，点击`save`按钮需要等待几分钟，脚本生成并自动下载到当前电脑。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230209153704.png)

#### 4. 脚本验证

[QQNEW_DEMO](QQNEW_DEMO.jmx)


`JMeter`添加查看结果树，点击运行查看结果。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230213151933.png)


## 优点

1. `BlazeMeter`自动填充全局配置信息，比如：`Http`信息头。


2. `BlazeMeter`抽取出接口环境变量`BASE_URL`。


## 总结

- `BlazeMeter`界面介绍：
  - 命名
  - 启动
  - 暂停
  - 导出脚本
- 录制步骤：
  - 开启`BlazeMeter`录制
  - 业务操作
  - 选择域名导出脚本
- 运行验证：
  - 添加查看结果树，运行脚本查看结果



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230213154513.png)
