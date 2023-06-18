# JMeter安装 - Mac系统

```plantuml
@startmindmap
title JMeter安装
* JMeter安装
** 环境准备
*** Java环境准备
** 压缩包解压安装
*** Mac系统
*** Window系统
*** Linux系统
** 环境验证
*** Java环境验证
**** java -version 
**** javac
*** JMeter环境验证
**** jmeter -v
@endmindmap
```

## 目录

- Java安装、配置
- JMeter安装、配置


## 学习目标

1. Mac系统安装并打开JMeter工具。


## 思考

> 想要使用JMeter进行压测脚本编写，如何安装并打开JMeter软件？

![](assets/20230206105644.png)


## JMeter安装

- 环境准备
- JMeter压缩包安装


### 环境准备

1. jdk下载。
1. jdk安装。
1. Java环境变量配置。


#### jdk下载

1. 下载合适自己系统的[jdk](https://drive.weixin.qq.com/s?k=ANUAJQeKAAk0t0A01a)。

注意：jdk版本需要1.8+。


![](assets/20230505180526.png)


#### jdk安装

1. 打开下载的dmg文件，选择**pkg**。
    ![](assets/20230505180523)
1. 点击 继续 。 
    ![](assets/20230505180525.png)
1. 点击 安装 。
   ![](assets/20230505180521.png)

1. 点击 使用密码，输入电脑开机密码。

    ![](assets/20230505180522.png)

1. 等待软件安装完成，点击关闭。

    ![](assets/20230505180522)


#### Java环境变量配置

1. 按 command + 空格 键 输入 ter，选择终端打开。

1. 终端确认电脑的bash环境。

    ```bash
    echo $SHELL
    ```
    ![](assets/20230505180520.png)

1. 打开环境变量配置文件。
   - 输出为/bin/bash， 则创建 ~/.bash_profile 配置文件。

   - 输出为/bin/zsh ，则创建 ~/.zshrc 配置文件。

    ![](assets/20230505180519.png)




1. 添加 JAVA_HOME 、CLASSPATH 变量。

1. PATH的变量追加Java配置。

    ```bash
    #------------------JAVA------------------
    JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-1.8.jdk/Contents/Home

    CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:.

    PATH=$JAVA_HOME/bin:$PATH:.

    export JAVA_HOME
    export PATH
    export CLASSPATH
    ```

1. 按 command + 空格 键 输入 ter，选择终端打开。

1. 终端输入 **source 配置文件名** 使环境变量生效。

    ```bash
    source ～/.zshrc 
    ```
    ![](assets/20230505180518.png)

#### Java环境验证

1. 打开一个新的命令行窗口。

1. 输入 java -version 得到版本号相关信息则表示java安装配置成功。
    ```bash
    java -version 

    javac
    ```
    ![](assets/20230505180517.png)



### JMeter压缩包安装

1. 下载Apache JMeter压缩包：[官网](https://jmeter.apache.org/download_jmeter.cgi)。
   
    ![](assets/20230116105918.png)

2. 解压到当前电脑上指定路径。
   
    ![](assets/20230505180516.png)

### JMeter环境变量配置

1. 按 command + 空格 键 输入 ter，选择终端打开。

2. 终端确认电脑的bash环境。

    ```bash
    echo $SHELL
    ```
    ![](assets/20230505170707.png)

1. 打开环境变量配置文件。
   - 输出为/bin/bash， 则创建 ~/.bash_profile 配置文件。

   - 输出为/bin/zsh ，则创建 ~/.zshrc 配置文件。

    ![](assets/20230505170706.png)

1. 添加 JMETER_HOME 变量。

2. PATH的变量追加JMeter配置。

    ```bash
    #-------- jmeter ----------
    JMETER_HOME=/解压路径/apache-jmeter-5.5

    export JMETER_HOME
    export PATH=$PATH:$JMETER_HOME/bin
    ```



1. 按 command + 空格 键 输入 ter，选择终端打开。

2. 终端输入 **source 配置文件名** 使环境变量生效。

    ```bash
    source ～/.zshrc 
    ```

    ![](assets/20230505170705.png)


### JMeter环境验证

1.按 command + 空格 键 输入 ter，选择终端打开。

2. 输入 jmeter -v 得到版本号相关信息则表示JMeter安装配置成功。
    ```bash
    jmeter -v
    ```

![](assets/20230505170704.png)


## 界面化启动

1. 按 command + 空格 键 输入 ter，选择终端打开。

2. 输入 jmeter ，命令行启动JMeter的GUI界面。


![](assets/20230206105644.png)
