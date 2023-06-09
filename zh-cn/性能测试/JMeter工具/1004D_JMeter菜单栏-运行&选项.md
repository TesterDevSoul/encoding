# JMeter菜单栏-运行&选项

## 本章要点
1. 运行模块介绍
2. 选项模块介绍


## 运行模块

本篇文章介绍运行下拉菜单，先不介绍远程相关的知识。在后续使用到时再详细介绍。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128152343.png)

### 启动

启动运行当前的测试计划。

当前测试计划有等待脚本就进行等待，没有等待则直接运行请求。

也可直接点击**工具栏**的**启动**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145332.png)


### 不停顿开始

`Start no pauses`：脚本无停顿的启动运行当前的测试计划。


**注意⚠️**：

不停顿开始选项可以**忽略脚本的定时器**并且运行过一次的脚本**再一次启动时开始运行脚本的速度更快**。

也可直接点击**工具栏**的**不停顿开始**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145256.png)

### 清除

只清除**选中**监听器的执行结果。

>一个测试脚本内有多个监听器，比如：查看结果树、聚合报告。

>选中**查看结果树**点击**清除**，发现只清除结果树的运行结果，不清除聚合报告的数据。

选中某一监听器点击清除，发现只清除了当前选中的监听器的执行结果，没有被选中的监听器对应的执行结果是不会被清除数据。


也可直接点击**工具栏**的**清除**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145513.png)

### 清除全部

清除所有监听器的执行结果。

>无论选中哪个监听器组件，对应当前测试计划下的所有监听器的执行结果都会被清除。

也可直接点击**工具栏**的**清除全部**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145619.png)

## 选项模块

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128171027.png)


### 选择语言

最常用的就是语言的切换，根据自己习惯切换到常用语言。

#### 常见问题

每次打开界面都需要进行选择语言，不会在`JMeter`退出界面后进行保存。

#### 解决方案

如果想要解决修改语言并长期保存的问题，可以直接修改配置文件`{jmeter_path}/bin/jmeter.properties `。

```bash
#Preferred GUI language. Comment out to use the JVM default locale's language.
#language=en
language=zh_CN
```


### 日志查看

选择日志查看，可以看到在右下方有对应的日志框显示。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128173324.png)

### 全部折叠

- `Collapse All`：当前测试计划下的所有组件都折叠起来。


也可直接点击**工具栏**的**全部展开**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145919.png)

### 全部展开

- `Expand All`：当前测试计划下只要是折叠的组件就全部打开。



也可直接点击**工具栏**的**全部展开**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145842.png)

## JMeter界面功能总结

- JMeter中常规的菜单功能：新建、保存、查询、配置、帮助 等

- 压测运行相关的功能：开始运行、停止运行、远程运行、停止远程运行 等

- 设定JMeter的界面更改

- 配置语言，建议English

## 总结

- 运行下拉菜单中点击**不停顿开始**节省脚本测试时间。

- 选项下拉菜单中可以直接把  所有组件进行折叠或者展开。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208145149.png)