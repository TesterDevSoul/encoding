# Selenium Grid 运行方式
## 课程目标
1. 安装
2. 单机运行
3. Hub 和 node


## Selenium Grid 安装
### 前提
- `Java` 环境检测

1. 检查Java环境
   >Java版本建议**11**或以上版本 
    ```bash
    java -version
    javac
    ```
### Server 安装
1. 下载`SeleniumGrid`对应的`jar`包`SeleniumServer`

[最新版本下载地址](https://github.com/SeleniumHQ/selenium/releases/download/selenium-4.7.0/selenium-server-4.7.2.jar)

>[历史版本地址](https://github.com/SeleniumHQ/selenium/releases)<br>[官网下载地址](https://www.selenium.dev/downloads/)


下载到本地后无需解压，直接为单独一个`jar`包：
 ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221220150924.png)

>如果单机运行可下载最新版本jar包；<br>如果分布式角色运行，则建议版本为4.4.0。

##### 版本对应关系
|SeleniumServer 版本|Chrome 版本|
|---|---|
|4.4.0|103.0.5060.134|
##### 运行环境说明
在`Mac`系统、`Window`系统和`Ubuntu`系统上运行方式都相同。

2. 下载`driver`

打开浏览器需要对应浏览器的`driver`，下载当前电脑浏览器版本对应的`driver`。

[driver下载地址](https://www.selenium.dev/downloads/)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221227162005.png)

3. 配置`driver`

`driver`配置有以下**3**种方式，选择任一种实现即可：
- I.`driver`地址配置到`PATH`环境变量中。
- II.`driver`和`seleniumserver-jar`包在同一层级。
- III.`webdrivermanager`自动下载对应浏览器`driver`到本地的`.cache`文件夹下。
    >在`/.cache/selenium`文件夹下自动下载当前电脑浏览器所需要的`driver`
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221220153646.png)

    |SeleniumServer 版本|webdrivermanager<br>是否主动下载driver|
    |---|---|
    |4.7.2|true|
    |4.4.0|false|
  

## 运行
### Standalone 运行
#### 1. 命令行Standalone启动
1. 命令行`cd`到`selenium-server.jar`包下
    ```bash
    cd Desktop 
    ```
2. `java -jar`声明角色为`standalone`启动`jar`包：`java -jar selenium-server-<version>.jar standalone`
    ```bash
    java -jar selenium-server-4.4.0.jar standalone
    ```
    >启动成功后，对应命令行显示`Started Selenium Standalone`，如下图：
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221220153333.png)
    
3. 查看UI界面验证
    >浏览器输入网址查看UI界面，默认使用的是`localhost`下的**4444**端口：<br>[UI界面](http://127.0.0.1:4444/ui)
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221220153158.png)
4. 查看 `Grid status` 状态
    >浏览器输入网址查看`status`状态：[Status状态网址](http://127.0.0.1:4444/status)
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221220153438.png)
#### 2. 代码运行
[项目地址](https://github.com/TesterDevSoul/Tutorials/blob/master/selenium/grid/src/test/java/top/testeru/suite/SuiteTest.java)

直接运行代码，发现测试用例在本地运行，只不过通过`Selenium Grid`来转发用例请求创建`Session`后再运行用例。


### Hub&Node 运行

大多数公司使用这种方案来解决自动化测试用例运行时间过长的问题。

#### 1. 命令行Hub启动
1. 命令行`cd`到`selenium-server.jar`包下
    ```bash
    cd Desktop 
    ```
2. `java -jar`声明角色为`hub`启动`jar`包：`java -jar selenium-server-<version>.jar hub`


    ```bash
    java -jar selenium-server-4.4.0.jar hub
    ```
    >启动`jar`包文件并把它作为集线器`hub`的角色启动。<br>启动成功后，对应命令行显示`Started Selenium Hub`，如下图：
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221163159.png)

3. 查看UI界面验证
    >浏览器输入网址查看UI界面，默认使用的是`localhost`下的**4444**端口：<br>[UI界面](http://127.0.0.1:4444/ui)
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221163619.png)



`SeleniumGrid`以`hub`角色启动时，启动了`Router`,`Distributor`,`Session Map`,`New Session Queue`,`Event Bus`这些组件。

虽然，已经有了集线器`Hub`，但当前没有`node`节点注册到`SeleniumGrid`平台上。
如果`Node`节点不注册进来，集线器`Hub`是不知道哪台物理机器可以执行测试用例的请求，就会导致`Router`路由无法正常分发测试用例。


#### 2. 命令行node启动
##### 2.1 同一机器上启动node

1. 命令行`cd`到`selenium-server.jar`包下
    ```bash
    cd Desktop 
    ```
2. `java -jar`声明角色为`node`启动`jar`包并校验浏览器`driver`是否存在：`java -jar selenium-server-<version>.jar node --detect-drivers true`

    ```bash
    java -jar selenium-server-4.4.0.jar node --detect-drivers true
    ```
    >启动`jar`包文件并把它作为`node`的角色启动。<br>启动成功后，对应命令行显示`Started Selenium node`，如下图：
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221164417.png)
3. 查看`hub`命令行验证
    >`node`节点创建成功，hub上注册对应`node`节点：
    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221164825.png)
    >心跳健康检测就是每隔**2**分钟会`ping`一下对应`URL`看看是否可以`ping`成功，即查看链接否处于活动状态


1. 查看UI界面验证
    >浏览器输入网址查看UI界面，默认使用的是`localhost`下的**4444**端口：<br>[UI界面](http://127.0.0.1:4444/ui)
 ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221164537.png)








![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221164417.png)

此时node节点创建成功，hub上注册对应node节点：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221164825.png)
>健康检测就是每隔2分钟会ping一下对应URL看看是否可以ping成功，对应是否处于活动状态

查看状态的UI界面：



代表一次最多启动16个线程

#### 2.2 不同机器上启动node

不同机器上启动node：`java -jar selenium-server-<version>.jar node --detect-drivers true --publish-events tcp://<ip> --subscribe-events tcp://<ip>`

```bash
java -jar selenium-server-4.4.0.jar node --detect-drivers true --publish-events tcp://10.1.1.178:4442 --subscribe-events tcp://10.1.1.178:4443
```
>不同机器上启动的时候需要安装相同版本的jar及根据机器上的浏览器下载对应的driver

[状态模版](status.json)


启动了一个Mac系统的node及Window系统的node：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/16716180743029.png)


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221182241.png)

##### status
对应的status状态json文件




当运行时可以看到对应session的个数：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20221221182520.png)



## 分布式

在这种模式下，我们必须启动所有组件，而且每个组件都是单独启动的，最好是在不同的机器上启动。比如：Router、Distributor等等。

##### event-bus
- 事件总线：启用不同Grid组件之间的内部通信。
- 默认端口为：4442、4443和5557。
- `java -jar selenium-server-<version>.jar event-bus`
```bash
java -jar selenium-server-4.4.0.jar event-bus
```


###### sessionQueue

- New Session Queue：将新的Session会话请求添加到队列中，该队列将由 Distributor 查询
- 默认端口是5559
- `java -jar selenium-server-<version>.jar sessionqueue`

```bash
java -jar selenium-server-4.4.0.jar sessionqueue
```


##### sessions
- 会话映射：将会话 ID 映射到会话运行的节点
- 默认会话映射端口是5556. 
- Session Map与Event Bus交互。

- `java -jar selenium-server-<version>.jar sessions --publish-events tcp://<event-bus-ip>:4442 --subscribe-events tcp://<event-bus-ip>:4443 --port 5556
`

```
java -jar selenium-server-4.4.0.jar sessions
```

##### distributor

Distributor：查询新会话队列中的新会话请求，并在能力匹配时将它们分配给节点。节点向分发服务器注册的方式与它们在集线器/节点网格中向集线器注册的方式相同。
默认分发服务器端口为5553. Distributor与New Session Queue、Session Map、Event Bus和Node(s)交互。

```
java -jar selenium-server-4.4.0.jar distributor --sessions http://10.1.1.178:5556 --sessionqueue http://10.1.1.178:5559 --bind-bus false
```


##### router
`java -jar selenium-server-4.7.2.jar router --sessions http://<sessions-ip>:5556 --distributor http://<distributor-ip>:5553 --sessionqueue http://<new-session-queue-ip>:5559`




```bash
java -jar selenium-server-4.4.0.jar router --sessions http://10.1.1.178:5556 --distributor http://10.1.1.178:5553 --sessionqueue http://10.1.1.178:5559
```






##### node
- node节点：默认节点端口是5555.

- `java -jar selenium-server-<version>.jar node --publish-events tcp://<event-bus-ip>:4442 --subscribe-events tcp://<event-bus-ip>:4443`



```bash
java -jar selenium-server-4.4.0.jar node --detect-drivers true --publish-events tcp://10.1.1.178:4442 --subscribe-events tcp://10.1.1.178:4443
```
