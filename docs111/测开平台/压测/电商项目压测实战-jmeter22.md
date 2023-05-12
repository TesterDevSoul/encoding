---
title: 电商项目压测实战
date: 2022-09-05 18:19:51.598
updated: 2022-09-07 21:54:47.175
url: /archives/jmeter22
categories: 
- jmeter
tags: 
- jmeter
---

# 电商项目压测实战
## 本章要点
- [ ] 掌握常用的JMeter脚本如何进行开发
- [ ] 了解基础知识：常见协议
- [ ] 了解电商项目运行原理
# 性能测试
## 关键术语解析
### [性能本质](/archives/jmeter01)
### [性能测试分类](/archives/jmeter02)
### [性能测试场景](/archives/jmeter03)
### [性能测试指标](/archives/jmeter03)

基础知识补充
## 性能测试基本流程
1. 性能需求分析
2. 编写性能测试计划、确定压测方案
3. 
4. 




## 性能需求分析
### 被测项目解读
项目web界面地址：[litemall](https://litemall-admin-web.k8s.hogwarts.ceshiren.com/#/login?redirect=%2Fdashboard)

[API](https://litemall-api.k8s.hogwarts.ceshiren.com/swagger-ui.html)
熟悉被压测系统的基本业务流程，明确性能测试要达到的目标，与产品经理、业务人员、架构师、技术经理一起沟通，找到业务需求的性能点。

### 系统架构图

熟悉系统的应用架构、技术架构、数据架构、部署架构等，找到与其他系统的交互流程，明确系统部署的硬件配置信息、软件配置信息等。

把对性能测试有重要影响的**关键点**明确列举出来，一般包括：
a、用户发起请求的顺序，请求之间的相互调用关系
b、业务数据走向、数据是如何流转的、经过了哪些应用服务、经过了哪些存储服务
c、评估被压测系统可能存在的重点资源消耗，是I/O消耗型、CPU消耗型，还是内存消耗型，这样在压测执行时可以重点进行监控
d、关注应用的部署架构
e、和技术经理一起沟通，明确应用的并发架构是采用多线程处理还是多进程处理，重点需要关注是否会有死锁、数据是否存在不一致、线程同步锁是否合理等



## 编写性能测试计划

- 1.测试方案：需求分析、测试指标、监控模型、测试策略
- 2.准备阶段：环境准备、脚本准备、测试数据准备、被测场景准备、监控部署
- 3.执行阶段：基准场景、容量场景、稳定性场景、异常场景
- 4.分析阶段：操作系统分析、整体分析、应用分析、数据库分析、网络分析、缓存分析
- 5.报告阶段：生成对应压测报告

主要分为以上5大部分，对应的每个阶段需要预估出人力及时间节点。

# 业务脚本编写
## [JMeter](/archives/jmeter06)

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209062200090.png)
这个提示是问你是否在运行前先保存一下脚本,选是就可以保存脚本,选否就是不保存



### 设置jmeter的堆内存大小
- JMeter安装目录的bin目录下的【Window】jmeter.bat/【Mac】jmeter文件,找到以下代码：
```
: "${HEAP:="-Xms1g -Xmx1g -XX:MaxMetaspaceSize=256m"}"
```
- `Xms`：起始堆内存
- `Xmx`：最大堆内存

一般是发送压测
## 单用户登录
- 录制脚本


### 添加断言
json
### 添加测试报告
Aggregate Report
集成了  所有的性能指标

- Samples   压测的时候的请求次数；单并发，用户产生的请求是4个
- Average   平均响应时间 4个接口的平均
-  Median  4个请求排队，中间的请求
-  90%     大
-  95%
-  99%
-  Min 最快最小的响应时间
-   Maximum  最大响应时间
-    Error  
-  Throughput
-   Received
-    Sent
#Samples:总共发给服务器的请求数量

Average:单个请求的平均响应时间，单位是毫秒

Median:50%的请求的响应时间

90%Line:90%的请求的响应时间

95%Line:95%的请求的响应时间

99%Line:99%的请求的响应时间

Min:最小的响应时间

Max:最大的响应时间

Error%:错误率=错误的请求的数量/请求的总数

Throughput: 吞吐量即表示每秒完成的请求数

KB/sec: 每秒从服务器端接收到的数据量


### 虚拟并发用户请求
- 设定并发数
- 运行时间
- 解读压测报告

### 并发压测中事务控制
需要统一一组请求完成的总计时间，可以考虑使用事务控制器
- 方法：把需求统计的请求样本放入事务控制器中


所有的请求当作一个事务去查看结果

### 正则匹配

引用名称：就是变量名，调用使用${name}
正则表达式：()括号里表示你要的数值，（相当于LR左边界， ）相当于LR的右边界
模板：$$表示你要是用那个正则表达式那个模板获取的值 -1全部，0 随机，1第一个2第二个，如果只有一个正则一般就填写1
匹配数字：-1表示全部，0随机，1第一个，2第二个
缺省值：这个如果没有匹配到，给它定义的一个默认值，建议写一个，不然没匹配到会很蒙圈

作者：莫尛莫
链接：https://www.jianshu.com/p/e3cac1adfc55
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


### CSV Data Set Config
以下是CSV Data Set Config各个参数的简要说明:

FileName:即同目录下csv文件的名称
File Encoding: 默认为ANSI
Varible Names: 定义文本文件中的参数名,参数之间逗号分隔.定义后可在脚本在以Shell变量的同样的方式引用
Allow Quoated data: 双引号相关
Recycle on EOF: 设置为True后,允许循环取值
Stop Thread on EOF: 当Recycle on EOF为false并且Stop Thread on EOF为true,则读完csv文件中的记录后,停止运行
Sharing Mode: 设置是否线程共享

JMeter会自动从CSV文件的表头中读取参数名作为变量名。

## token
https://segmentfault.com/a/1190000038949288



## 静默压测
脱离UI界面进行JMeter压测

- 好处 命令行运行更容易 搞事情
- 命令行格式：
jmeter -n -t $jmx_file -l $jtl_file

jmx: JMeeter压测程序脚本文件，压测控制过程记录在jmx文件中
jtl文件是JMeter压测请求响应数据的原始文件



web压测报告生成
jmeter -g /result-file -e -o /report/folder







