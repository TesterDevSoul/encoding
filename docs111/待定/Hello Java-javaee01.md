---
title: Hello Java
date: 2022-09-20 09:47:45.23
updated: 2022-09-20 10:28:14.901
url: /archives/javaee01
categories: 
tags: 
---

# Hello Java
## Java 跨平台原理
一张图概括对应  Java代码如何实现跨平台：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/javaee未命名.png)
解读如下：
编写对应的Java文件代码，文件以java结尾。使用命令行`javac Hello.java`进行代码编译，生成Java字节码文件`Hello.class`。
使用命令行`java Hello`来运行Java的字节码文件。由于Java是使用JVM进行编译运行，JVM虚拟机相当于万能翻译器，所以在各个平台都可以执行 Java代码。
## JVM介绍
JVM可以理解为一个可运行 Java 字节码的虚拟计算机系统。
- 它有一个解析器组件，可以实现Java字节码和计算机操作系统之间的通信。
- 对不同系统、不同的运行平台，有不同的JVM。

JVM 屏蔽了底层运行平台的差别，实现了 “**一次编译，到处执行**”。

## GC-垃圾回收器
- 垃圾回收：不再使用的内存空间应当进行回收。

在C/C++等语言中，是由程序员手动负责回收无用内存。
Java则不需要，JVM提供一个系统线程，跟踪存储空间的分配情况，检查并释放那些可以被释放的存储空间。

-  GC在Java程序运行过程中自动启用，程序员无法精准控制和干预。