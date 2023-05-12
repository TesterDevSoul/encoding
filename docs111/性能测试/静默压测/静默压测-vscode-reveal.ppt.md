---
theme: "league"
transition: "fade"
highlightTheme: "monokai"
slideNumber: false
title: "静默压测生成HTML报告"
use: hogwarts
---


# 静默压测生成HTML报告




---


## 介绍
- [官网](https://jmeter.apache.org/usermanual/generating-dashboard.html)

---



## 什么是静默压测


- 脱离 GUI模式



---


## 为什么不用GUI模式


- 对资源消耗大，浪费严重
  
- GUI不用于负载测试，仅仅用于测试脚本的创建及调试
  
- Linux系统上很难打开一个GUI界面，一般都是命令行使用
  


---



## 生成HTML报告步骤
1. 压测脚本创建 - 使用监听器查看结果（树或表中的结果）
2. GUI界面运行脚本
3. 将 `jmx` 文件运行到非 `GUI` 模式并生成 `HTML` 报告



---



### 前置条件

Java环境安装配置成功。
   
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230106140728.png)


---



### 前置条件

`Apache JMeter`下载安装成功。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230106140822.png)




---


## 1. 压测脚本创建
`GUI`模式启动`JMeter`。
  
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230106115317.png)





---





### 脚本内容解读

- `#! /bin/sh`：
  - shell脚本。
- `JAVA_HOME`：
  - 电脑的环境变量，需要配置该变量。
- `MINIMAL_VERSION=8`：
  - 启动JMeter的最低Java版本是8。




---





### 脚本内容解读

- `JAVA9_OPTS=`：
  - JMeter也可以使用Java9,但是使用9的时候对应启动命令参数就有变化。
- `: "${HEAP:="-Xms1g -Xmx1g -XX:MaxMetaspaceSize=256m"}"`：
  
  - JMeter使用的Java环境的基本堆大小。可以修改。
- `: "${JMETER_LANGUAGE:="-Duser.language=en -Duser.region=EN"}"`：
  
  - 默认的语言是英语，可以修改为中文。




---





### 1）打开JMeter并添加线程组
测试计划(`Test Plan`) -> 添加(`Add`) -> 线程(`Threads`) -> 线程组(`Thread Group`)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107092806.png)







---





### 2）添加HTTP请求
线程组(`Thread Group`) -> 添加(`Add`) -> 采样器(`Sampler`) -> HTTP 请求(`HTTP Request`)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107094231.png)




---




### 3）HTTP请求编写 

[Reqres API](https://reqres.in/)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107100649.png)




---




### 3）HTTP请求编写 

GET请求

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107100755.png)



---




### 3）HTTP请求编写 

**服务器名称**、**路径**和**参数**填写。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107100941.png)




---





### 4）查看测试结果

测试计划(`Test Plan`) -> 添加(`Add`) -> 侦听器(`Listener`)  -> 查看结果树(`View Results Tree`)


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107101442.png)




---





### 4）查看测试结果


`xml`、`jtl`、`csv`。<br>
注意⚠️：建议使用**未存在**文件。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230108121815.png)




---





## 2. GUI界面运行脚本



保存脚本(`.jmx`) 并运行。<br>单击**绿色按钮**或快捷键{**Window**}`Ctrl+R`/{**Mac**}`Command+R`运行。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230107101938.png)





---





## 结果文件查看

- 默认配置下，jtl文件保存的字段为：

    ```
    timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect
    1673149864221,6824,GET API,200,OK,Thread Group 1-1,text,true,,1744,123,1,1,https://reqres.in/api/users?page=2,6823,0,1
    ```




---



## 结果文件字段


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




---





## 3. 生成报告

- 一种使用GUI模式。
- 一种使用命令行模式。





---




### 1) GUI 模式生成报告

GUI界面**从独立的 csv/jtl 文件创建报告**。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230108150435.png)


---




### 1) GUI 模式生成报告


1. `Results file` 浏览选中存储结果的 `csv`/`jtl` 文件路径。
2. `user.properties` 浏览选中`{jmeter_path}/bin/jmeter.properties`文件。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230109170459.png)


---




### 1) GUI 模式生成报告


3. `Output directory` 浏览输入`HTML`报告的文件夹路径。
4. 点击底部`Gererate report`，就可以生成 `HTML` 到`Output directory`的路径文件夹下。



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230109170459.png)



---



### 1) GUI 模式生成报告结果

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230109171455.png)



---




### 报错

报错信息：

```
Generating report
File '/**/api.jtl' does not contain the field names header, ensure the jmeter.save.saveservice.* properties are the same as when the CSV file was created or the file may be read incorrectly when generating report
An error occurred: null
```


---



### 原因及方案

- 原因： 由于结果文件没有头信息导致。

- **解决方案**： 需要在结果文件`jtl/csv`中添加上以下对应头信息：

    ```
    timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect
    ```




---




### 报错

报错信息：
```
Output directory :The directory /**/html isnt empty
```



---


### 原因及方案

- 原因： 若生成HTML的文件夹已存在，需要为空文件夹，否则生成报告会报错。

- **解决方案**： 选中HTML的文件夹为空文件夹。



---




### 2) 使用命令行模式生成报告

* 静默压测后，命令直接⽣成对应html压测报告。 


* 过程 
    * jtl_file的原始数据 ——> html压测报告。
* 优点 
    * 快，⽅便，省事，节约时间，美观。




---




### 测试脚本生成HTML报告

```bash
jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230109185313.png)



---




### 命令行file含义

- `jmx file`：
  - `JMeter`压测脚本文件。
  - 压测脚本的运行逻辑及流程控制过程都在`jmx file`中。
- `results file`：
  - `JMeter`压测脚本的结果储存文件。
  - `JMeter`静默压测生成的压测数据，原始的各种压测数据文件。
- `Path to web report folder`：
  - `JMeter`压测的`HTML`报告文件。
  - 根据`JMeter`静默压测的压测数据生成`HTML`报告文件夹。
  - 文件夹可为存在也可为不存在，若存在必为空文件夹。





---




### 报错
报错信息：

```bash
An error occurred: Cannot write to '/**/html' as folder is not empty
```



---


### 原因及方案

- 原因： 对应生成报告时报错，代表生成报告的文件夹不是空文件夹。


- **解决方案**： 需要**重新指定创建空文件夹**。



---




### 参数含义

- `-n`：
  - `nongui`;非 GUI 模式，在非 GUI 模式下运行 JMeter 压测脚本。
- `-t`：
  - `testfile`;指定JMeter脚本，要运行的 JMeter 测试脚本文件。
- `-l`：
  - `logfile`;JMeter压测结果文件，记录结果样本的文件。
- `-e`：
  - `reportatendofloadtests`;测试结束后生成报告。
- `-o`：
  - `reportoutputfolder`;报告的输出文件地址。





---




### 静默压测生成结果文件


```bash
jmeter -n -t [jmx file] -l [results file] 
```



---




### 结果文件生成报告


```bash
jmeter -g [results file] -e -o [Path to web report folder]
```


---




### 结果文件生成报告



- `-g`：
  - 指定测试结果文件路径，仅用于生成测试报表，参数是csv结果文件。




---




## jmx脚本解读




---




### 测试计划

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110112510.png)


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110112854.png)




---




### 线程组

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110115310.png)





---




## 自动化压测

- 方式一：直接**命令行**进行参数值传递。


- 方式二：编写**脚本**进行参数值传递。{`shell`/`python`}




---




## 自定义变量



---

## 1. 添加用户自定义变量

线程组 -> 添加 -> 配置元件 -> 用户定义的变量

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110150040.png){:height="50%" width="50%"}



---




### 2. 添加自定义变量表达式

定义**3**个变量和变量对应的默认值。



---




### 1） 函数表达式生成
点击函数助手

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110150600.png)


---




### 1） 函数表达式生成

使用`__P`函数生成对应属性，并复制。
 
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110150657.png)

---




### 1） 函数表达式生成

使用`__P`函数生成对应属性，并复制。
 

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110151326.png)



---




### 2） 函数添加

添加三个函数，分别对应线程组的属性。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110151837.png)



---


### 3. 线程组引用

线程组的线程属性引用上面自定义的变量。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110152005.png)




---




### 4. 聚合报告添加验证

线程组(`Thread Group`) -> 添加(`Add`) -> 监听器(`Listener`) -> 聚合报告(`Aggregate Report`)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110160301.png)




---




### 5. 验证

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110161950.png)




---





### 命令行传参

`-J`：`jmeterproperty`;定义额外的 `JMeter` 属性

```bash
jmeter -n -t [jmx file] -l [results file] -Jthread=整数 -Jloop=整数 -Jramp=整数


jmeter -n -t GETAPI.jmx -l get.jtl -Jthread=2 -Jloop=2 -Jramp=1
```



---





### 运行结果


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110170321.png)




---




## 脚本



---




 
### 并发数规则

- **在低并发数区域内可以大增量，在高并发数区域内使用小增量**。

    >10 50 100 120 140 150 160 170 175

- 具体的并发数值根据被测服务不同而不同



---




## 脚本业务流程图

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230110142435.png)




---




### 并发数组
1. 在脚本中设置模版压测脚本的地址
2. 获取当前运行的系统环境并赋值给变量os_type
3. 设置压测并发数
   >thread_num_array = (10 50 100 120 140)



---




### 脚本

1. 判断对应模版压测脚本`PATH`是否存在
2. 读取模版压测脚本写入新生成文件
   1. 对应字段替换使用`${thread}`
3. 文件名唯一性：使用`sample_并发数`。比如：GETAPI_10.jmx



---




## DIY 定制化 JMeter 聚合压测报告

- `reportgenerator.properties`文件保存关于图形化`HTML`报告生成模块的默认配置。




- 要变更报告相关配置信息，在`user.properties`中配置。



---




## 报告标题修改

```properties
jmeter.reportgenerator.report_title=Apache JMeter Dashboard
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230111112934.png)





---




### 步骤
- `user.properties`文件最后进行配置的追加：
  ```properties
  jmeter.reportgenerator.report_title=ceshiren.com
  ```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230111154038.png)


---





## 聚合报告百分位修改

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



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230111114032.png)




---




### 步骤

`user.properties`文件最后进行配置的追加：

```properties
aggregate_rpt_pct2=70
```



---




## 修改结果展示

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//20230111114408.png)




---



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual//111.png)
