# Selenium Grid 组成
## 课程目标
1.  Selenium Grid 组成

## 组成

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/components.png)

### Router

路由`Router`是 Grid 的入口，接收所有外部请求，并将它们转发给正确的组件。



#### 新请求

如果Router收到新的会话请求，它将被转发创建新的`Session Queue`。



#### 已存在的请求
如果请求属于一个已经存在的`session`，Router会查询`Session Map`得到会话运行所在的`Node ID`，然后Router将请求直接转发给对应的Node。
#### 作用
- `Router` 平衡网格中的负载。

### Distributor
分发器 `Distributor` 的两个主要职责：

#### 查询新会话队列并处理任何未决的新会话请求

当一个**新请求**发送到Router时，则会创建新的 `Session Queue`，在队列中等待。

`Distributor`会轮询新会话队列查找未决的新的会话请求，然后根据属性找到可以创建会话的合适Node节点。

创建会话后，`Distributor`在会话映射中存储会话`Session ID` 和正在执行会话的**Node节点**之间的关系。


#### 注册并跟踪所有节点及其功能

Node通过`Event Bus`发送Node注册事件向Distributor注册。`Distributor`读取它，然后尝试通过 HTTP 到达节点以确认它的存在。如果请求成功，`Distributor`注册节点并通过`GridModel`跟踪所有节点功能。

### Session Queue
### Session Map

### Event Bus
### Nodes
运行测试用例的物理机器节点





Selenium Grid 有 2 个版本——较旧的 Grid 1 和较新的 Grid 2。我们将只关注 Grid 2，因为 Grid 1 正逐渐被 Selenium 团队弃用。

Selenium Grid 使用集线器节点概念，您只在称为集线器的一台机器上运行测试，但执行将由称为节点的不同机器完成。 