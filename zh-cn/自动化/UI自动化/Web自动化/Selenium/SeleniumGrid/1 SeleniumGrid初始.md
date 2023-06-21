# Selenium Grid
## 课程目标
1.  Selenium Grid 介绍
2.  Selenium Grid 使用场景
3.  Selenium Grid 运行机制
4.  Selenium Grid 执行流程

## Selenium Grid 概念
##### 🤔思考

- 什么是 **Selenium Grid** ？

其实`Selenium`框架整体的包含以下三部分：
1. `Selenium IDE`：主要作用为脚本的录制
2. `Selenium WebDriver`：主要在编写自动化脚本时使用
3. `Selenium Grid`：主要为了解决分布式运行测试用例

由上可知对应的`Selenium Grid` 是 `Selenium` 框架的一部分，使用`Selenium Grid`可以很容易的在多台机器上并行运行自动化的测试用例。（其实简单的理解`Selenium Grid`就是一个代理服务器）


### Grid 作用

专门用于在 **不同机器**、**不同操作系统** 和 **不同浏览器** 上 **并行** 运行多个测试用例。如下图所示：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221220135756.png)

### Grid 实现方式
通过 **Router路由** 远程浏览器的命令来实现，其中 **Grid服务器** 充当 **集线器Hub角色**。

>使用 `Selenium Grid` 运行测试用例时，需要用户首先配置远程服务器才能执行。

## Selenium Grid 使用场景
##### 🤔 思考
- 什么时候会用 **Selenium Grid** ？

未使用`Selenium Grid`之前运行测试用例的时候，无论有多少个测试用例，都要在一台机器 「电脑/服务器」 上执行。<br>

并行执行测试用例，如果都在一台机器上并行执行，如果并发数过多会发生耗尽机器内存的现象。

#### ❓ 问题现象
同时打开**20**个浏览器并行执行测试用例时，会出现**系统的内存耗尽**并且测试**用例刚执行就直接失败**结束的现象。
#### 👉 解决方案
把要执行的测试用例分发到**5**台机器上执行，所有机器同时打开**4**个浏览器并行执行测试用例。

这时会看到**5**台机器都在执行测试用例，对应的打开**20**个浏览器执行测试用例的总时间就是打开**4**个浏览器执行测试用例的时间。

使用该解决方案，会减少执行测试用例所需的时间；如果**不同机器**打开的**浏览器不同**，还可以对测试用例的兼容性做一个测试。

以上为问题的解决方案，下一步需要选择技术架构去实现 **测试套件在不同机器上运行** 的方案。由于UI自动化使用的底层是`Selenium`框架，所以在技术架构选择时需要考虑兼容`Selenium`的`WebDriver`， `Selenium`框架的`Selenium Grid`提供了测试用例的分发技术，即可以把要执行的测试用例往各个机器节点上进行分发。


### Grid 使用场景分两种
1. 针对**不同**的**浏览器** *类型* & *版本*、**操作系统** 并行运行测试用例。
   >确保正在测试的程序与各种浏览器、操作系统组合的兼容性。

2. **减少**测试套件的**执行时间**
    >多机器并行执行测试用例，可以节省用例整体的执行时间。<br> 如果将 Selenium Grid 设置为一次并行运行 4 个测试用例，则可以大约 4 倍的速度完成整个测试用例。


## Selenium Grid 运行机制
##### 🤔 思考
- **Selenium Grid** 是如何将**测试用例**从**本地**IDEA分发到不同的`node`**节点机器** ？
- **Selenium Grid** 是如何把各个`node`**节点的测试结果**汇总在一起，并在执行测试用例的**本地**IDEA生成报告 ？

`Selenium Grid` 在最新版本重新设计了架构，除了对**性能**和**标准合规性**进行全面改进外，还 **分解了网格的不同功能** ，主要为了适应**容器化部署**和**云分布式的可扩展性**。


简单流程如下图：



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221115104.png)

`Selenium Grid` 有一个集线器`Selenium Hub`，它是集中执行的系统。`Selenium Hub`像一个集装箱，里面执行用例分配规则。


测试用例执行时，会首先到`Hub`集线器，集线器作为一个**指挥中心**将决定对测试用例如何进行分配。<br>

>上图中，一共有4台物理机器，每个机器称之为Node节点，即有4个node节点。<br>具体哪个节点来运行哪个测试用例，需要`Hub`集线器通过脚本属性来分配。<br>

#### 场景1：不同节点不同浏览器
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221113510.png)

- 第1个`Node`节点：`Chrome`浏览器
- 第2个`Node`节点：`Safari`浏览器
- 第3个`Node`节点：`Firefox`浏览器
- 第4个`Node`节点：`Edge`浏览器

如果测试用例声明的浏览器是`Chrome`则直接分发到第**1**个`Node`节点执行；<br>如果测试用例声明的浏览器是`Edge`浏览器直接分发到第**4**个节点执行。

#### 场景2：不同节点相同浏览器

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221114646.png)

- 第1个`Node`节点：`Chrome`浏览器
- 第2个`Node`节点：`Safari`浏览器
- 第3个`Node`节点：`Chrome`浏览器
- 第4个`Node`节点：`Chrome`浏览器

如果测试用例声明的浏览器是`Chrome`，则根据均衡原则分发到第**1**、**3**、**4**的`Node`节点执行。

#### 场景3：不同节点不同系统

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221135246.png)

- 第1个`Node`节点：`Mac`系统 **&** `Chrome`浏览器
- 第2个`Node`节点：`Window`系统 **&** `Safari`浏览器
- 第3个`Node`节点：`Linux`系统 **&** `Chrome`浏览器
- 第4个`Node`节点：`Mac`系统 **&** `Chrome`浏览器

如果测试用例声明的是`Mac`系统的`Chrome`浏览器，直接分发到第**1**个`Node`节点执行。


## Selenium Grid 执行流程
- `Selenium hub` 是什么 ?
- `Selenium Grid` 是怎样执行的 ？

可以看到在 `Selenium Grid` 运行机制的三个场景中都有一个`Selenium hub`，对应`Selenium hub`里面具体的内容是什么，它是怎样进行的请求流转？

对应的具体的内部结构及其流转方式如下图：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221145301.png)

❣️ `Client`是本地IDEA要执行的测试用例。

- 1.`Client`的测试用例通过`HTTP`协议发送请求给路由`Router`。

❣️ `Router`是 `Selenium Grid` 内的一个处理器/组件，也是`Selenium Grid`的入口。

- 2.`Router`接受请求并立即进行分发。
  >创建一个新的`Session`会话同时分配对应的`Node`节点机器。

`Router`**作用**：**把请求从路由`Router`发送到Node的机器节点。**

- 3.路由`Router`进行请求分发。
  
  
#### 💕 新请求

接收是一个**新请求**时，路由`Router`此时不知往哪个`Node`节点上发送该请求。


- 3.1.1 **新请求**时，路由`Router`将请求发送给**分发器**`Distributor`。
>**分发器** `Distributor` 读取`Client`脚本的`DesiredCapability`**属性**，从中获取用例的 **操作系统**`Platform` 及 **浏览器**`browserName` 属性。


- 3.1.2 `Distributor`根据属性值把请求发送给符合的 **Node节点** ，同时 **创建`session`会话对象** `Session Map`。


>`Session`会话创建时会产生一个`Session ID`，**分发器** `Distributor`会把`Session ID`与对应的`Node`**节点**的`URI`地址一一对应存储在 **Session Map** 中。

- 3.1.3 `Session Map`会把`Session ID`与对应的`Node`发送给`Router`路由，`Router`路由再发送给客户端`Client`。



会话的**职责**就是**创建一个简单的映射**，其中包含正在执行的测试用例的**Session会话ID**及其执行的**物理机器Node的地址**。

#### 💕 已存在的请求

接收是一个**已存在的请求**时，路由`Router`直接从`Session Map`中根据对应的`Session ID`查找到`Node`节点的`URI`地址，把用例请求发送到对应节点上直接运行即可。


- 3.2.1 **已存在的请求**时，路由`Router`将请求根据`Session Map`中对应关系直接把请求发送给`Node`节点。



## 总结
- Selenium Grid 概念
- Selenium Grid 使用场景
- Selenium Grid 运行机制
- Selenium Grid 执行流程