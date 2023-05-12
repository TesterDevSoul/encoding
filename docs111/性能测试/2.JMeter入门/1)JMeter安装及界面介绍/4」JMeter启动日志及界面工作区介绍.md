# JMeter启动日志及界面工作区介绍

## 本章要点

1. 不同Java版本下JMeter的启动日志
1. JMeter界面介绍


## JMeter启动日志
### Java8启动JMeter
`Java`版本为**8**的`JMeter`启动日志：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116145155.png)

### Java11启动JMeter

`Java`版本为**11**的`JMeter`启动日志：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116145606.png)


#### 警告
```bash
WARNING: package sun.awt.X11 not in java.desktop
```
注意⚠️：这个`WARNING`警告不需要管。如果想要消除警告，则可以直接使用`Java8`的版本启动`JMeter`即可。


#### 报错：_TIPropertyValueIsValid called with 4 on nil context!
如下图所示报错内容：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116143106.png)

如果遇到`_TIPropertyValueIsValid called with 4 on nil context!`的报错信息，可以参考官网的[issue](https://github.com/apache/jmeter/issues/5533)提供的解决方案。


##### 解决方案

方案一：**修改对应`Java`版本号**；

方案二：**重新命令行启动**`JMeter`。

以上两种解决方案选择其中一个即可。


### 命令行启动提示
```bash
# 不要使用GUI模式进行负载测试 GUI模式只是压测脚本的创建和调试
Dont use GUI mode for load testing !, only for Test creation and Test debugging.
# 如果想要进行负载测试 使用CLI模式 即非GUI模式 -- 静默压测会详细介绍
For load testing, use CLI Mode (was NON GUI):
   jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]
# 可以增加Java的堆来满足压测需求
& increase Java Heap to meet your test requirements:
# 修改JMeter文件的Java堆变量
   Modify current env variable HEAP="-Xms1g -Xmx1g -XX:MaxMetaspaceSize=256m" in the jmeter batch file
```

## JMeter界面工作区介绍
`JMeter`的主界面工作区主要分为**4**部分，如下：**菜单栏**、**工具栏**、**树状组件栏**和**组件内容栏**。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116150926.png)

下面👇具体介绍下每一部分的具体功能。

首先第一个是最上面的一层**菜单栏**。
### 菜单栏

`JMeter`全部的功能的都包含在菜单栏中。

### 工具栏

工具栏中的按钮在菜单栏内都可以找到，工具栏就相当于**菜单栏常用功能的快捷按钮**。

### 树状组件栏

用来显示 **测试用例**（计划）及其子组件。

>因为JMeter启动界面化会自动创建一个测试计划，压测脚本中所有的组件都是在测试计划下进行添加。

树状组件栏就是一个目录树，里面存放的是脚本设计过程中使用到的元件。
在JMeter执行脚本的过程中，默认从根节点开始按顺序遍历树状组件栏上的元件。

#### 元件

比如向百度服务器发送一个HTTP协议的GET请求，这个请求是由一个HTTP请求取样器来完成，这个HTTP请求取样器就是元件。

在树状组件栏中添加进来的都是元件。

### 组件内容栏

组件内容栏显示选中组件的具体内容及配置。

**组件内容栏**主要是配合**树状组件栏**来进行使用。在左侧**树状组件栏**中点击具体的某个组件，在右侧**组件内容栏**中就显示该组件的具体内容和配置。



## 总结
- JMeter启动日志
  - Java8正常启动，Java11有告警可忽略。
- JMeter界面工作区分为四部分：**菜单栏**、**工具栏**、**树状组件栏**和**组件内容栏**。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208140446.png)