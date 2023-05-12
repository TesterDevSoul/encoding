# JMeter菜单栏-文件
## 本章要点
1. 模块介绍
2. 文件模块-模版
3. 文件模块-合并
4. 文件模块-保存为测试片段

上篇文章简单的介绍了一下对应的具体模块的功能说明，下面对具体的功能进行详细的使用说明。

## 文件

首先来看第一个就是文件，在文件这个模块内有多个功能，分别是：新建、模版、打开、合并等等，下面根据常用的几个来进行功能的演示说明。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116154447.png)

### 打开

打开一个当前硬盘上的压测脚本即`JMX`文件。

### 模板...

**模板**是对常用的功能使用指导。

主要有**录制**、**JDBC测试**、**BeanShell使用**、**构建Web测试计划**
等等，每个功能界面都分为**基本步骤**和**详细截图**。


也可直接点击**工具栏**的**模板**按钮：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208150013.png)


#### 介绍

在模板界面选择需要的模版后，点击 `Useful links`「用户链接」，则会链接到 `Apache JMeter` 官网查看详细步骤和截图。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116155839.png)

#### 脚本构建
##### 需求

下拉选择模板内的功能，构建一个web的测试计划模版脚本。

##### 步骤

1. 模板界面选择 **构建web测试计划** - `Building a Web Test Plan`。 
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128105709.png)


1. 点击`create`则会直接在`GUI`界面创建整体模版，如下：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116160553.png)


##### 常见问题：模版脚本的存放路径？

模版脚本都在`JMeter`哪里存在呢？

为什么在模版内选择对应功能就能创建出来相关脚本？

##### 答

打开`JMeter`安装路径，可以看到在`{jmeter_path}/bin/templates`文件夹下有所有的模板选项下的脚本。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116161740.png)

### 合并

将多个`JMX`脚本文件合并为一个，也就是把多个`JMX`文件打开放在同一个测试计划下。

**注意⚠️**：先打开的脚本文件中测试计划下的组件会放在上面。

#### 应用场景

功能点分开多个人编写压测脚本，最终链路压测或需求合并压测时，需要把多个压测脚本合并为一个脚本。

>使用文件的合并功能，可以节省复制各种重复组件的时间。



#### 业务需求

将**2**个`JMX`脚本文件合并为一个脚本文件。

##### 准备工作

这里准备了**2**个脚本，一个脚本为`ATest.jmx`，一个脚本为`BTest.jmx`。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128111317.png)


##### 步骤

1. 打开新的测试计划，选择文件下的**合并**，根据路径选择`BTest.jmx`，可以看到当前脚本的测试计划下添加对应组件，但是该脚本还没有进行保存。

   ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128112255.png)

   ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128112821.png)

1. 再次选择合并，根据路径选择`ATest.jmx`，可以看到当前脚本的测试计划下继续添加`ATest`脚本的组件。

   ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128113051.png)


##### 注意

1. 合并功能中可以进行多个脚本的合并，**没有个数限制**。

1. 合并选择对应文件后，**组件**会一直**往后叠加**。


### 保存

仅保存当前`JMeter`界面的测试计划。

### 保存测试计划为

将`JMeter`界面的测试计划另存为在硬盘的指定位置。

### 选中部分保存为...

选中的组件，比如线程组，会直接保存为新的测试计划脚本。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230116174134.png)

### 保存为测试片段

选中的组件保存为一个测试片段。



**测试片段**：该组件是一种特殊类型的控制器，它存在于测试计划树中，**与线程组元素处于同一级别**。


注意⚠️：**线程组**、**测试计划** 组件不能保存为一个测试片段。

若选中**线程组**、**测试计划**组件后点击**保存为测试片段**则会提示如下报错：

```bash
One of the selected nodes cannot be put inside a Test Fragment
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/1674012683977.png)


#### 保存结果

测试片段保存`HTTP`请求组件为新的`JMX`脚本文件。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230118115120.png)


#### 测试片段 VS 线程组

**测试片段**与**线程组**的区别在于：

测试片段不会被执行，除非测试片段被**模块控制器**或`Include_Controller`引用。但是线程组会被脚本执行。

**测试片段**纯粹用于测试计划中的**代码重用**，**线程组**需要调用业务逻辑代码。

### 还原

`Revert`：将现在的`jmx`还原为已经保存过的`JMX`文件。

>打开脚本文件，进行修改后不保存，点击还原，则还原为打开前的脚本文件。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230128174153.png)


## 总结

- 文件模块包含的功能：打开、模板...、合并、保存、保存为测试片段 等等。
- 模版的使用及脚本的存放位置。
- 合并的步骤及使用场景。
- 测试片段的概念。
- 测试片段与线程组的区别。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230208173143.png)