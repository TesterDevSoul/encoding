---
title: JMeter Introduction
date: 2022-09-04 17:17:39.645
updated: 2022-09-07 09:23:07.808
url: /archives/jmeter06
categories: 
- jmeter
tags: 
- jmeter
---


# JMeter简介
- 常规的菜单功能：新建、保存、查询、配置、帮助等
- 压测运行相关：脚本的开始运行、停止运行、远程运行、停止远程运行等
- JMeter外观长相的更改
- 配置语言，建议英语
# JMeter主要功能模块
- 取样器：进行脚本逻辑控制
- 线程组：压测场景控制
- 监视器：监控压测运行，获取性能指标

# JMeter下载与配置
### 下载
##### 方式一
直接点击：
[JMeter下载链接](https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.5.zip)
##### 方式二
进入[官网](https://jmeter.apache.org/download_jmeter.cgi)下载对应压缩包，如下图：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209041420033.png)

下载完成后解压到自己本地指定目录，
### 配置
#### 配置Java环境
在运行之前验证电脑端是否已经安装了`Java`环境，使用`JMeter`工具的前提是本地电脑安装并配置好了`Java`环境。
```shell
java-version
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209041425647.png)
如上图，我本地的`Java`环境为`11`。


#### 配置JMeter环境
JMeter配置全局环境变量，如下：
```shell
export JMETER_HOME =/apache-jmeter-5.5[jmeter解压路径]
export PATH=$PATH:$JMETER_HOME/bin
```
配置成功后进行`source .zprofile`，使配置文件生效，生效后对应的打开新的命令行窗口，命令行输入`jmeter`验证，命令行如下图，并打开JMeter界面代表安装并配置成功。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209041432466.png)




