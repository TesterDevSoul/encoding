---
title: Exception Mechanism
date: 2022-10-10 19:50:48.79
updated: 2022-10-11 20:02:16.054
url: /archives/java11
categories: 
- java
tags: 
- java
- 异常
---

# 异常机制

## 本章要点

概述的内容理解就好，对应应用层的内容需要掌握


## 基本概念

什么是异常？其实就是非正常情况，通俗的说，异常就是程序出现了错误。 
## 异常本质
当程序出现异常，程序安全的退出、处理完后继续执行的机制
     
## 体系结构
Java中的异常是一个很庞大的体系，下图是异常的体系结构划分：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/javaee/202210111605542.png)

`java.lang.Throwable`类是Java语言中 错误(`Error`) 和 异常(`Exception`) 的超类。
### Error类
`Error`类主要用于描述**Java虚拟机无法解决的严重错误**。
- 通常无法编码解决
>如：JVM挂掉了等
### Exception类
其中，`Exception`类主要用于描述因编程错误或偶然外在因素导致的轻微错误。
- 通常可以编码解决
>如：0作为除数等

## 总结