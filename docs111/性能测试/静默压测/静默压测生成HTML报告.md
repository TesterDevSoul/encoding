# 静默压测生成HTML报告

## 课程目标
1. 


## 介绍
查看[官网](https://jmeter.apache.org/usermanual/generating-dashboard.html)，对应的显示一个HTML的文件报告，但是在这个页面并没有告诉我们应该怎样去生成这样的报告，下面主要就给大家讲解一下对应的如何自动生成HTML报告并且可以自动的多并发执行。

### 静默压测
什么是静默压测呢？

其实我们打开JMeter在界面上对应进行脚本调试运行的时候，这就是一个GUI模式。对应的不使用GUI界面化对脚本进行运行就是静默压测。

脱离了GUI模式，压测脚本运行更稳定。

### 为什么不用GUI模式
- 对资源消耗大，浪费严重
  >有时压测链接超时，有可能不是对应接受请求服务被压爆了。而是我们的压力机出现了问题。<br>一般的压力机配置，在GUI模式下（Windows），最多支持300左右的模拟请求线程，再大的话，容易造成卡顿、无响应等情况，这是JMeter其本身的机制和硬件配置。
- GUI不用于负载测试，仅仅用于测试脚本的创建及调试
- Linux系统上很难打开一个GUI界面，一般都是命令行使用
    >一般服务器都是通过命令行终端，ssh输入对应的命令进行链接操作，很少把Linux服务器做成一个UI界面化的系统去进行操作。
## 生成HTML报告步骤
首先，我们先看一下对应生成HTML报告的步骤，依据以下步骤来生成：
1. 压测脚本创建 - 使用监听器查看结果（树或表中的结果）
2. GUI界面运行脚本
3. 将 `jmx` 文件运行到非 `GUI` 模式并生成 `HTML` 报告

### 前置条件

1. `Java`环境安装配置成功。Java 8+，建议jdk11。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230106140728.png)

1. `Apache JMeter`下载安装成功。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230106140822.png)


```bash
bew install jmeter
```
默认安装的路径为`/usr/local/Cellar/jmeter/5.5`。

如果满足以上条件，就可以进行对应的压测脚本的创建及运行。
### 1. 压测脚本创建
安装`JMeter`，以`GUI`模式启动`JMeter`并构建测试计划。

如果配置了全局环境变量则直接命令行输入`jmeter`启动`JMeter`的界面；

如果没有配置全局变量则命令行`cd`到`{jmeter_path}/bin`路径下，再输入`jmeter`以启动`JMeter`的界面。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230106115317.png)


##### 报错
配置完环境变量后，对应的命令行打开还是不能启动脚本，命令行提示：

```bash
jmeter: Permission denied【拒绝访问】
```

**解决方案**：给`jmeter`启动脚本增加权限。
```bash
chmod +x jmeter

# 移除对应的执行权限
chmod -x jmeter
```


>启动JMeter的界面化，其实就是启动的`{jmeter_path}/bin`下的`jmeter`脚本，Mac/Linux系统下是jmeter，Window系统下是jmeter.bat；<br>看一下脚本的内容：

- `#! /bin/sh`：说明是一个shell脚本。
- `JAVA_HOME`：电脑的环境变量需要配置该变量。
- `MINIMAL_VERSION=8`：启动JMeter的最低Java版本是8。
- `JAVA9_OPTS=`：JMeter也可以使用Java9,但是使用9的时候对应启动命令参数就有变化。
- `: "${HEAP:="-Xms1g -Xmx1g -XX:MaxMetaspaceSize=256m"}"`：JMeter使用的Java环境的基本堆大小。可以修改。
- `: "${JMETER_LANGUAGE:="-Duser.language=en -Duser.region=EN"}"`：默认的语言是英语，可以修改为中文。

#### 1）打开JMeter并添加线程组
要添加线程组，测试计划(`Test Plan`) -> 添加(`Add`) -> 线程(`Threads`) -> 线程组(`Thread Group`)
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107092806.png)

#### 2）添加HTTP请求
导航到 线程组(`Thread Group`) -> 添加(`Add`) -> 采样器(`Sampler`) -> HTTP 请求(`HTTP Request`)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107094231.png)

#### 3）HTTP请求编写 

接下来搜索可在 Internet 上免费获得的[Reqres API](https://reqres.in/)。我们将以 Reqres API 为例。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107100649.png)

GET请求如下：
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107100755.png)

现在你有了API，从链接中找出**服务器名称**、**路径**和**参数**。在JMeter 测试计划的 HTTP 请求中复制这些值。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107100941.png)

#### 4）查看测试结果

添加一个监听器（简单的**数据写入器**）并将结果存储在csv文件中。
对于添加侦听器，导航到 测试计划(`Test Plan`) -> 添加(`Add`) -> 侦听器(`Listener`)  -> 查看结果树(`View Results Tree`)


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107101442.png)

每个监听器中，都会有一个**所有数据写入一个文件**的功能。在创建查看结果树时需要对写出的文件地址进行配置，选择本地任意一个位置后面跟上数据输出的文件名。

我们可以从弹窗的文件类型中看到，支持的文件类型有三种：`xml`、`jtl`、`csv`。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230108121815.png)

哪种类型是JMeter默认的呢？
打开`{jmeter_path}/bin/jmeter.properties`文件，可以看到`output_format`的参数值默认为`csv`，所以`CSV`是JMeter的默认类型。
```
#jmeter.save.saveservice.output_format=csv
```
当我们选择一个路径时，可以选择**已存在的文件**，也可以选择**未存在的文件**。文件名可以以`.csv`或`.jtl`结尾，本文使用`.jtl`结尾文件。

注意⚠️：建议使用**未存在**文件。
>JMeter默认参数配置中，`resultcollector.action_if_file_exists=ASK`，默认是询问用户。还有`APPEND`，`DELETE`选择使用。
```
# Used to control what happens when you start a test and
# have listeners that could overwrite existing result files
# Possible values:
# ASK : Ask user 询问用户
# APPEND : Append results to existing file 在已经存在文件后追加结果
# DELETE : Delete existing file and start a new file  删除已经存在的文件并创建一个新的
#resultcollector.action_if_file_exists=ASK
```
>若文件为已存在的，则在运行时会弹窗提示，弹窗提示如下图：


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230108121415.png)



注意⚠️：一般请求的参数化使用的是`csv`文件，压测结果存储为`jtl`文件，进行区分，不建议使用`xml`存储结果。
>因为XML结果文件不能像csv、jtl一样直接命令行生成HTML报告，如果生成还需要与ant集成。

### 2. GUI界面运行脚本

#### 5）运行

保存压测脚本 (`.jmx`) 并运行它。只需单击**绿色按钮**或快捷键{**Window**}`Ctrl+R`/{**Mac**}`Command+R`即可运行。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230107101938.png)
上面的测试结果显示了HTTP 请求服务器的请求和响应。


#### 结果文件查看

默认配置下，jtl文件保存的字段为：

```
timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect
1673149864221,6824,GET API,200,OK,Thread Group 1-1,text,true,,1744,123,1,1,https://reqres.in/api/users?page=2,6823,0,1
```

|字段|说明|
|---|---|
|timeStamp|时间戳，毫秒；如：1673149864221|
|elapsed|耗时，毫秒；从发送请求到收到最后一个响应，所花费的时间；不包括渲染请求所花费的时间，同时也不包括处理客户端脚本所花费的时间|
|label|取样器名称 如：GET API|
|responseCode|HTTP响应code码；如：200|
|responseMessage|响应的message消息；如：OK|
|threadName|线程名；如：Thread Group 1-1|
|dataType|参数的数据类型；|
|success|请求是否成功；|
|failureMessage|响应的失败的message消息；|
|bytes|请求样本字节数|
|sentBytes|发送样本字节数|
|grpThreads|当前线程组的线程数|
|allThreads|所有线程组的线程数|
|URL|请求地址路径|
|Latency|延迟的耗时|
|IdleTime|空闲时间，毫秒；|
|Connect|连接建立的时间|


在这里，对应的请求和响应的body参数没有写入文件，因为jtl文件中这些参数主要是为了做性能分析生成图表进行使用。

### 3. 生成报告

生成报告有两种方式：一种为使用GUI模式，一种使用命令行模式。

#### 1) GUI 模式生成报告

- 也就是GUI界面**从独立的 csv/jtl 文件创建报告**。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230108150435.png)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230109170459.png)


1. `Results file` 浏览选中存储结果的 `csv`/`jtl` 文件路径
1. `user.properties` 浏览选中`{jmeter_path}/bin/jmeter.properties`文件
1. `Output directory` 浏览输入`HTML`报告的文件夹路径
    >html文件夹可为已存在也可为不存在文件夹；<br>如果存在则生成的文件直接放入该文件夹下，如果不存在则会生成该文件夹再放入生成的报告文件。
    
1. 点击底部`Gererate report`，就可以生成 `HTML` 到`Output directory`的路径文件夹下。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230109171455.png)

##### 报错1

```
Generating report
File '/**/api.jtl' does not contain the field names header, ensure the jmeter.save.saveservice.* properties are the same as when the CSV file was created or the file may be read incorrectly when generating report
An error occurred: null
```

- 由于结果文件没有头信息导致。

**解决方案**： 需要在结果文件`jtl/csv`中添加上以下对应头信息：

```
timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect
```


##### 报错2

```
Output directory :The directory /**/html isnt empty
```

- 若生成HTML的文件夹已存在，需要为空文件夹，否则生成报告会报错。

**解决方案**： 选中HTML的文件夹为空文件夹。

#### 2) 命令行模式生成报告

GUI界面只是为了脚本进行调试时使用，如果想要进行压测，则需要在压测机上使用命令行的模式运行jmx脚本。

可以看到JMeter在启动时，命令行输出打印一条命令，也就是静默压测的命令行。

静默压测，命令可直接⽣成对应HTML压测报告。

* 过程 
    * jtl_file的原始数据 ——> html压测报告 
* 优点 
    * 快，⽅便，省事，节约时间，美观
##### 测试脚本生成HTML报告

```bash
jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]
```
示例：
```bash
jmeter -n -t GETAPI.jmx -l get.jtl -e -o html
```


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230109185313.png)


##### 命令行file含义

- `jmx file`：`JMeter`压测脚本文件
  - 压测脚本的运行逻辑及流程控制过程都在`jmx file`中
- `results file`：`JMeter`压测脚本的结果储存文件
  - `JMeter`静默压测生成的压测数据，原始的各种压测数据文件
  >jtl结果文件需要为空文件，否则命令行运行报错：``
- `Path to web report folder`：`JMeter`压测的`HTML`报告文件
  - 根据`JMeter`静默压测的压测数据生成`HTML`报告文件夹
  - 文件夹可为存在也可为不存在，若存在必为空文件夹。



##### 运行报错
示例：
```bash
jmeter -n -t GETAPI.jmx -l get.jtl -e -o html
```

第一次运行该代码没有问题，第二次运行时报错：
```bash
An error occurred: Cannot write to '/**/html' as folder is not empty
```

**原因**：生成报告文件`html`时报错，由于生成报告的文件夹不是空文件夹。


**解决方案**： 需要**重新指定创建空文件夹**。

##### 参数含义

- `-n`：`nongui`;非 GUI 模式，在非 GUI 模式下运行 JMeter 压测脚本。
- `-t`：`testfile`;指定JMeter脚本，要运行的 JMeter 测试脚本文件。
- `-l`：`logfile`;JMeter压测结果文件，记录结果样本的文件。
- `-e`：`reportatendofloadtests`;测试结束后生成报告。
  >此时只是生成对应的结果报告HTML，但是并没有保存在硬盘上。
- `-o`：`reportoutputfolder`;报告的输出文件地址。
  >需要把HTML报告保存在当前电脑硬盘上，则需要添加-o参数进行保存。

>若想要查看`JMeter`的所有命令行参数，则在终端输入`jmeter -`即可。

##### 静默压测生成结果文件

只想通过静默压测生成对应的结果文件，不需生成`HTML`报告。

`HTML`报告等所有的压测脚本运行完后，统一对结果文件生成报告，则使用以下代码进行压测结果的存储：

```bash
jmeter -n -t [jmx file] -l [results file] 
```

示例：
```bash
jmeter -n -t GETAPI.jmx -l get1.jtl
```

命令行运行如下图：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111175229.png)

可以看到在当前文件夹下生成了`get1.jtl`,并且只是生成该文件，并没有生成`HTML`报告的文件夹。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111174904.png)


##### 结果文件生成报告

已经有了多个压测结果文件`jtl`，想要直接对具体的结果文件`jtl`生成对应的`HTML`报告，则可以使用如下命令：

```bash
jmeter -g [results file] -e -o [Path to web report folder]
```

- `-g`：指定测试结果文件路径，仅用于生成测试报表，参数是csv结果文件
>拿到结果文件直接生成HTML报告，不需要再发送一次脚本请求。

示例：
```bash
jmeter -g get1.jtl -e -o html1
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111175827.png)

可以看到在当前文件夹下生成了名称为`html1`的`HTML`报告的文件夹。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111175939.png)


## 自动化压测

JMeter压测脚本其实就是一个xml结构的文件，里面根据标签把GUI界面的组件和参数进行拼接。

#### 问题

如何通过非GUI修改线程数？

>每次并发线程数更改就需要 使用`JMeter`的`GUI`界面或打开`jmx`文件找到线程组件的标签去更改线程数，造成不必要的时间成本上的浪费。

#### 解决方案
可以在脚本内自定义对应线程变量，引用后通过参数传递对应值给声明的变量即可进行自动化的压测。


想要对线程组件的变量进行自定义，首先要了解对应的压测脚本的结构，下面我们先来看一下对应的JMX脚本的结构的简单介绍：

### jmx脚本解读


jmx脚本按结构自上而下：

#### 测试计划

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110112510.png)


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110112854.png)


其中`<jmeterTestPlan version="1.2" properties="5.0" jmeter="5.5">`标签中的`jmeter="5.5"`代表的是：`JMeter`版本是`5.5`,如果你使用的是其他的版本则会显示你使用的版本号。

#### 线程组

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110115310.png)

- `HTTPSampler`：HTTP取样器

#### 具体解析
```html
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="5.0" jmeter="5.5">
  <hashTree>
    <!-- 测试计划组件  testname：名称 -->
    <TestPlan guiclass="TestPlanGui" testclass="TestPlan" testname="GET请求的测试计划" enabled="true">
      <!-- 字符串 测试计划的注释 注释的内容在标签内显示-->
      <stringProp name="TestPlan.comments">这是一个get请求的测试计划</stringProp>
      <boolProp name="TestPlan.functional_mode">false</boolProp>
      <boolProp name="TestPlan.tearDown_on_shutdown">true</boolProp>
      <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
      <elementProp name="TestPlan.user_defined_variables" elementType="Arguments" guiclass="ArgumentsPanel" testclass="Arguments" testname="User Defined Variables" enabled="true">
        <collectionProp name="Arguments.arguments"/>
      </elementProp>
      <stringProp name="TestPlan.user_define_classpath"></stringProp>
    </TestPlan>
    
    <hashTree>
      <!-- 查看结果树 -->
      <ResultCollector guiclass="ViewResultsFullVisualizer" testclass="ResultCollector" testname="查看结果树" enabled="true">
        <boolProp name="ResultCollector.error_logging">false</boolProp>
        <objProp>
          <name>saveConfig</name>
          <value class="SampleSaveConfiguration">
            <time>true</time>
            <latency>true</latency>
            <timestamp>true</timestamp>
            <success>true</success>
            <label>true</label>
            <code>true</code>
            <message>true</message>
            <threadName>true</threadName>
            <dataType>true</dataType>
            <encoding>false</encoding>
            <assertions>true</assertions>
            <subresults>true</subresults>
            <responseData>false</responseData>
            <samplerData>false</samplerData>
            <xml>false</xml>
            <fieldNames>true</fieldNames>
            <responseHeaders>false</responseHeaders>
            <requestHeaders>false</requestHeaders>
            <responseDataOnError>false</responseDataOnError>
            <saveAssertionResultsFailureMessage>true</saveAssertionResultsFailureMessage>
            <assertionsResultsToSave>0</assertionsResultsToSave>
            <bytes>true</bytes>
            <sentBytes>true</sentBytes>
            <url>true</url>
            <threadCounts>true</threadCounts>
            <idleTime>true</idleTime>
            <connectTime>true</connectTime>
          </value>
        </objProp>
        <stringProp name="filename">/Users/gigai/get.jtl</stringProp>
      </ResultCollector>

      <hashTree/>
      <!-- 线程组 testname：名称 -->
      <ThreadGroup guiclass="ThreadGroupGui" testclass="ThreadGroup" testname="线程组" enabled="true">
        <!-- 取样器错误后执行的动作  单词修改
             继续：continue；
             启动下一进程循环：startnextloop；
             停止线程：stopthread；
             停止测试：stoptest；
             立即停止测试：stoptestnow；
         -->
        <stringProp name="ThreadGroup.on_sample_error">continue</stringProp>
        <elementProp name="ThreadGroup.main_controller" elementType="LoopController" guiclass="LoopControlPanel" testclass="LoopController" testname="循环控制器" enabled="true">
          <!-- 循环次数 是否勾选永远-->
          <boolProp name="LoopController.continue_forever">false</boolProp>
          <!-- 循环次数 -->
          <stringProp name="LoopController.loops">1</stringProp>
        </elementProp>
        <!-- 线程数 -->
        <stringProp name="ThreadGroup.num_threads">1</stringProp>
        <!-- ramp-UP时间 -->
        <stringProp name="ThreadGroup.ramp_time">1</stringProp>
        <!-- 调度器 默认不选中-->
        <boolProp name="ThreadGroup.scheduler">false</boolProp>
        <!-- 持续时间 -->
        <stringProp name="ThreadGroup.duration"></stringProp>
        <!-- 启动延迟 -->
        <stringProp name="ThreadGroup.delay"></stringProp>
        <!-- 循环次数下的选择框 -->
        <boolProp name="ThreadGroup.same_user_on_next_iteration">true</boolProp>
        <!-- 勾选延迟创建线程直到需要，下列标签出现 -->
        <!-- <boolProp name="ThreadGroup.delayedStart">true</boolProp> -->
      </ThreadGroup>
      <hashTree>
        <!-- HTTP请求取样器 -->
        <HTTPSamplerProxy guiclass="HttpTestSampleGui" testclass="HTTPSamplerProxy" testname="HTTP请求" enabled="true">
          <elementProp name="HTTPsampler.Arguments" elementType="Arguments" guiclass="HTTPArgumentsPanel" testclass="Arguments" testname="用户定义的变量" enabled="true">
            <!-- 请求参数 无参数-->
            <collectionProp name="Arguments.arguments"/>
            <!-- 请求参数 有参数-->
            <collectionProp name="Arguments.arguments">
              <!-- 参数key -->
              <elementProp name="a" elementType="HTTPArgument">
                <boolProp name="HTTPArgument.always_encode">false</boolProp>
                <!-- 参数value -->
                <stringProp name="Argument.value">11</stringProp>
                <stringProp name="Argument.metadata">=</stringProp>
                <boolProp name="HTTPArgument.use_equals">true</boolProp>
                <!-- 参数key -->
                <stringProp name="Argument.name">a</stringProp>
              </elementProp>
            </collectionProp>

          </elementProp>
          <!-- IP -->
          <stringProp name="HTTPSampler.domain">reqres.in</stringProp>
          <!-- 端口 -->
          <stringProp name="HTTPSampler.port"></stringProp>
          <!-- 协议 -->
          <stringProp name="HTTPSampler.protocol">https</stringProp>
          <!-- 编码格式 -->
          <stringProp name="HTTPSampler.contentEncoding"></stringProp>
          <!-- 请求路径 -->
          <stringProp name="HTTPSampler.path">/api/users?page=2</stringProp>
          <!-- 请求方式 -->
          <stringProp name="HTTPSampler.method">GET</stringProp>
          <!-- 跟随重定向 -->
          <boolProp name="HTTPSampler.follow_redirects">true</boolProp>
          <!-- 自动重定向 -->
          <boolProp name="HTTPSampler.auto_redirects">false</boolProp>
          <!-- 使用keepalive -->
          <boolProp name="HTTPSampler.use_keepalive">true</boolProp>
          <boolProp name="HTTPSampler.DO_MULTIPART_POST">false</boolProp>
          <stringProp name="HTTPSampler.embedded_url_re"></stringProp>


          <!-- 取样器高级配置，只有配置才显示该标签 客户端实现-->
          <stringProp name="HTTPSampler.implementation">Java</stringProp>
          <!-- 连接时间 -->
          <stringProp name="HTTPSampler.connect_timeout">dfs</stringProp>
          <!-- 响应时间 -->
          <stringProp name="HTTPSampler.response_timeout">fsad</stringProp>
          <!-- 取样器的注释 只有写了内容才显示该标签-->
          <stringProp name="TestPlan.comments">ß</stringProp>


          <stringProp name="HTTPSampler.connect_timeout"></stringProp>
          <stringProp name="HTTPSampler.response_timeout"></stringProp>
        </HTTPSamplerProxy>
        <hashTree/>
      </hashTree>
    </hashTree>
  </hashTree>
</jmeterTestPlan>
```

### 静默压测方式
>这时可能会疑问：为什么要让大家先来看一下对应JMX文件解读？这不是浪费时间吗？
>并不是的，因为后面我们的静默压测是需要用到修改压测脚本JMX。


自定义线程组相关变量名，传给变量名参数值有两种方式。

方式一：直接**命令行**进行参数值传递；

>使用非`GUI`模式执行`JMeter`压测脚本，若需更改参数，必须编辑`jmx`文件，找到对应的变量进行修改，比较麻烦。<br>
>因此，可以`GUI`界面进行参数化一些常用的变量，直接在`JMeter`命令行对该变量参数值进行设置。


方式二：编写**脚本**进行参数值传递。{`shell`+`python`}


无论是**命令行**进行参数值传递还是**脚本**进行参数值传递，它们的前置条件是都需要在`GUI`界面设置传入参数的变量声明。

### 自定义变量声明

#### 1. 添加用户自定义变量

导航到 线程组(`Thread Group`) -> 添加(`Add`) -> 配置元件(`Config Element`) -> 用户定义的变量(`User Defined Variables`)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110150040.png)

#### 2. 添加自定义变量表达式

线程组件中看到对应有3个变量，线程数、Ramp-Up时间、循环次数。

定义**3**个变量和变量对应的默认值。

>定义默认值为了防止没有给变量赋值导致对应脚本出错。

##### 1） 函数表达式生成

1. 点击函数助手
  ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110150600.png)
1. 使用 **__P** 函数生成对应属性，并复制。
  
  >属性与变量不同。**变量**是**线程的局部变量**；<br/>**属性**是**对所有线程都是通用**的，需要使用`__P`或`__property`函数来引用。
  ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110150657.png)
  ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110151326.png)

##### 2） 函数添加

添加线程属性相关的三个函数，分别对应线程组的线程属性。

添加服务器名函数，对应在HTTP请求组件的服务器名称。

>`${__P(thread,1)}`，表示变量`thread`的默认值为 **1**<br>
>`${__P(ramp,1)}`，表示变量`ramp`的默认值为 **1**<br>
>`${__P(loop,1)}`，表示变量`loop`的默认值为 **1**<br>
>`${__P(domain,reqres.in)}`，表示变量`domain`的默认值为 **reqres.in**<br>



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230201180235.png)
#### 3. 引用

线程组的 线程属性 引用自定义的变量。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110152005.png)

HTTP请求组件的 服务器名或IP 引用自定义的变量url。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230201180751.png)

#### 4. 验证

添加**聚合报告**验证对应的样本请求数，导航到 线程组(`Thread Group`) -> 添加(`Add`) -> 监听器(`Listener`) -> 聚合报告(`Aggregate Report`)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110160301.png)

`jmx`文件保存，界面化运行，验证变量是否设置成功。

>样本请求的线程数都为**1**，说明设置成功。因为在用户自定义变量中，**变量**的默认值都为**1**。<br>结果文件`jtl`中请求的`url`是默认地址。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110161950.png)


### 方式一：命令行传参数变量

执行命令：
```bash
jmeter -n -t [jmx file] -l [results file] -JthreadNum=整数 -JloopNum=整数 -JrampNum=整数 -Jurl=服务器域名
```

- `-J`：`jmeterproperty`;定义额外的 `JMeter` 属性


```bash
-J, --jmeterproperty <argument>=<value>
Define additional JMeter properties
```


```bash
# 使用默认域名请求
jmeter -n -t GETAPI.jmx -l get.jtl -Jthread=2 -Jloop=2 -Jramp=1

# 更换测试环境的域名请求
jmeter -n -t GETAPI.jmx -l get.jtl -Jthread=2 -Jloop=2 -Jramp=1 -Jurl=ceshiren.com
```
2个线程循环2次，运行完成后对应命令行终端显示：


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110170321.png)


如果命令行查看不是很明白直观，可以把结果文件导入到`GUI`界面的聚合报告中进行查看。

在聚合报告中导入结果文件`jtl`时要注意，首先清空一下结果，不然对应的聚合报告界面样本数是在以前的界面上进行叠加不是直接显示导入的结果文件的样本数。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110170348.png)

### 方式二：脚本

命令行虽然可以直接更改线程数，但是其实压测时我们每次会是不同的并发数，所以一个压测脚本可能需要在命令行输入多次命令才能完成。

 
#### 并发数规则
压测脚本中压力增量设定的原则为：**在低并发数区域内可以大增量，在高并发数区域内使用小增量**。

>10 50 100 120 140 150 160 170 175

具体的并发数值根据被测服务不同而不同

#### 脚本业务流程图

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230110142435.png)


#### 并发数组

shell脚本设置压测并发数
   >thread_num_array = (10 50 100 120 140)

#### 脚本

1. 判断对应模版压测脚本`PATH`是否存在
2. 读取模版压测脚本写入新生成文件
   1. 文件名唯一性：{}_tn{}_rt{}_lc{}_h{}
3. 对应字段替换
4. JMeter命令行运行



### DIY 定制化 JMeter 聚合压测报告


Aggregate Report 是 JMeter 常用的一个 Listener，中文被翻译为“聚合报告”。

如果大家都是做Web应用的性能测试，例如只有一个登录的请求，那么在Aggregate Report中，会显示一行数据，共有10个字段，含义分别如下。

Label：每个 JMeter 的 element（例如 HTTP Request）都有一个 Name 属性，这里显示的就是 Name 属性的值

#Samples：表示你这次测试中一共发出了多少个请求，如果模拟10个用户，每个用户迭代10次，那么这里显示100

Average：平均响应时间——默认情况下是单个 Request 的平均响应时间，当使用了 Transaction Controller 时，也可以以Transaction 为单位显示平均响应时间

Median：中位数，也就是 50％ 用户的响应时间

90% Line：90％ 用户的响应时间

Note：关于 50％ 和 90％ 并发用户数的含义

Min：最小响应时间

Max：最大响应时间

Error%：本次测试中出现错误的请求的数量/请求的总数

Throughput：吞吐量——默认情况下表示每秒完成的请求数（Request per Second），当使用了 Transaction Controller 时，也可以表示类似 LoadRunner 的Transaction per Second 数

KB/Sec：每秒从服务器端接收到的数据量，相当于LoadRunner中的Throughput/Sec





`JMeter`在`{jmeter_path}/bin`目录新增了`reportgenerator.properties`文件保存了所有关于图形化`HTML`报告生成模块的默认配置。

要变更配置，建议**不要直接编辑**`reportgenerator.properties`，而是推荐在`user.properties`中去配置和覆盖。

#### 报告标题修改

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111112934.png)

```properties
jmeter.reportgenerator.report_title=Apache JMeter Dashboard
```

- `report_title`:定义报告的标题，标题定义为实际测试项名称/公司名
##### 步骤
在`user.properties`中对其进行覆盖，直接在最后进行配置的追加：
```properties
jmeter.reportgenerator.report_title=testeru.top
```



#### 聚合报告百分位修改

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111114032.png)

在`jmeter.properties`中，有关于聚合报告中的三个百分位的默认值：
```properties
#---------------------------------------------------------------------------
# Aggregate Report and Aggregate Graph - configuration
#---------------------------------------------------------------------------
#
# Percentiles to display in reports
# Can be float value between 0 and 100
# First percentile to display, defaults to 90%
#aggregate_rpt_pct1=90
# Second percentile to display, defaults to 95%
#aggregate_rpt_pct2=95
# Second percentile to display, defaults to 99%
#aggregate_rpt_pct3=99
```
##### 步骤

可以在`user.properties`中对其进行覆盖，直接在最后进行配置的追加：
```properties
aggregate_rpt_pct2=70
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111114408.png)


## 响应断言
一个正确的脚本不只是有请求，还有对应的请求需要基本的断言，才是一个测试用例。

### 200

200正常请求通过的响应断言添加：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230131170613.png)

### 404

HTTP请求中并不是都返回200的响应状态码，还会返回400、404、500、502等错误，下面演示对应的404状态码的响应断言：

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230131170746.png)


在响应断言中，一般都会直接勾选响应代码或响应信息，然后匹配对应内容。
404状态下请求的响应信息应该是`Not Found`，但是为什么运行时就是请求失败？？大家有知道的吗？

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230131171358.png)


这跟JMeter的自有机制有关，只有当前请求成功「HTTP响应状态码为200的时候」JMeter才会去运行断言-响应断言，也就才会去匹配响应信息「response message」。

HTTP中4xx和5xx开头的响应状态码一般是被认为请求不成功的，那JMeter就直接忽略，请求都不成功就不会走断言组件。

>就类似代码写出了Exception，并且你没有抛出异常，那代码是不会继续往下运行的。

如果非要断言中匹配4xx和5xx开头的响应状态码，则需要在响应断言组件中勾选上`Ignore status`,也就是不管当前的请求响应状态码是什么，JMeter都会认为是请求成功。既然请求成功，则可以直接判断响应信息「response message」。


以上就是，断言中判断请求失败的响应代码问题
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230131165028.png)

**解决**：

忽略状态，强制性让JMeter认为当前请求是成功的。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230202104851.png)

## 面试题
### 性能测试在什么环境开展？

性能测试不能与功能测试在同一个测试环境，需要搭建完全的一套独立的环境，这时就需要运维帮忙搭建环境，研发去同步部署环境。


搭建一套独立的性能测试环境进行测试，硬件设施尽量和线上环境保持一致


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230201194759.png)

### 性能测试发现的问题，是开发解决，还是你解决？

产研测拉会聊天，确认需求确认场景确认指标后形成文档申请资源搭建环境编写脚本，执行测试然后盯着系统参数接口参数，有问题能解决解决不行找研发。最后输出报告附上结果分析



- 与研发、产品确认需求及指标
- 申请性能资源搭建环境
- 测试编写脚本
- 执行性能测试分析性能参数
- 若涉及调优，需要与开发、DB、运维一起分析


7.与开发，架构，DB 一起去看测试过程，调优

测试：

性能方案、测试脚本编写

性能脚本执行

调优需要协同

性能报告的输出

### 怎样分析性能测试结果

1）首先查看结果概况，查看事物有没有大面积失败，有没有大量的 Error，响应时间有没有异常波动，如果都正常，表明测试结果可信

2）分析性能指标，比如，响应时间，事务通过率，CPU 等指标是否满足需求；如果测试结果不可信，分析异常的原因，开发修改后进行回归测试








## 总结
- Selenium Grid 概念




http://www.ishatrainingsolutions.org/2020/06/30/jmeter-html-dashboard-report-generation/