## 问题

Selenium Grid作用是什么? Selenium Grid 的使用过程？


## 考察点
面试官想了解：
- Selenium Grid是否使用过
- Selenium Grid对应的内容有哪些


## 技术点
涉及的技术点：
- Selenium Grid


## 回答
### SeleniumGrid 作用
Selenium Grid 是 Selenium 的三大组件之一，它可以在多台机器上并行运行测试，集中管理不同的浏览器版本和浏览器配置。通过将客户端命令发送到远程浏览器的实例, Selenium Grid 允许在远程计算机 (虚拟或真实) 上执行 WebDriver 脚本. 它提供一种在多台计算机上并行运行测试的简便方法。

## 使用场景

**场景一：** 实现分布式执行测试，提高执行效率
比如：我们有 1000 条用例执行，如果在本机执行，一条用例耗时 100 秒,执行完成则需要大约 27 小时 `1000*100/60/60=27个小时`。如果让这些用例并发执行，比如分配 6 台计算机，每个计算机执行 1000/6 大约 166 条用例，那时间大约节省了 6 倍，原来需要大约 27 个小时，现在可能只需要 4.5 个小时左右就基本完成了， 分布式并发执行可以让我们用例的执行总时长指数级的缩小，从而效率得到很大的提升。

**场景二：** 解决浏览器兼容性问题

比如还是 1000 条用例，需要分别在 Chrome、Firefox、Edge、Safari 这些浏览器上都执行一遍，保证每个浏览器上都能正常执行，测试浏览器的兼容性。这时也可以使用 Selenium Grid，通过 Selenium Grid 将这些请求分发到不同的系统、不同浏览器中执行。这些浏览器可以分别布署在不同的计算机中比如可以布署在 Linux 、Windows、Mac 上都可以，作为它的 Node 结点，从而解决兼容性测试的问题


### 执行过程

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221145301.png)



 `Client`是本地IDEA要执行的测试用例。

- 1.`Client`的测试用例通过`HTTP`协议发送请求给路由`Router`。

 `Router`是 `Selenium Grid` 内的一个处理器/组件，也是`Selenium Grid`的入口。

- 2.`Router`接受请求并立即进行分发。
  >创建一个新的`Session`会话同时分配对应的`Node`节点机器。

`Router`**作用**：**把请求从路由`Router`发送到Node的机器节点。**

- 3.路由`Router`进行请求分发。
  
  
####  新请求

接收是一个**新请求**时，路由`Router`此时不知往哪个`Node`节点上发送该请求。


- 3.1.1 **新请求**时，路由`Router`将请求发送给**分发器**`Distributor`。
>**分发器** `Distributor` 读取`Client`脚本的`DesiredCapability`**属性**，从中获取用例的 **操作系统**`Platform` 及 **浏览器**`browserName` 属性。


- 3.1.2 `Distributor`根据属性值把请求发送给符合的 **Node节点** ，同时 **创建`session`会话对象** `Session Map`。


>`Session`会话创建时会产生一个`Session ID`，**分发器** `Distributor`会把`Session ID`与对应的`Node`**节点**的`URI`地址一一对应存储在 **Session Map** 中。

- 3.1.3 `Session Map`会把`Session ID`与对应的`Node`发送给`Router`路由，`Router`路由再发送给客户端`Client`。



会话的**职责**就是**创建一个简单的映射**，其中包含正在执行的测试用例的**Session会话ID**及其执行的**物理机器Node的地址**。

####  已存在的请求

接收是一个**已存在的请求**时，路由`Router`直接从`Session Map`中根据对应的`Session ID`查找到`Node`节点的`URI`地址，把用例请求发送到对应节点上直接运行即可。


- 3.2.1 **已存在的请求**时，路由`Router`将请求根据`Session Map`中对应关系直接把请求发送给`Node`节点。



以上是SeleniumGrid的各个组件之间的执行过程。
