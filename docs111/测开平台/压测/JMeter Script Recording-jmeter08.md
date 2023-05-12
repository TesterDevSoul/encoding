---
title: JMeter Script Recording
date: 2022-09-07 14:56:10.598
updated: 2022-09-07 16:32:23.207
url: /archives/jmeter08
categories: 
- jmeter
tags: 
- jmeter
---

# JMeter 脚本录制

## JMeter 的脚本生成
JMeter的脚本生成方式有三种：
### 借助第三方工具
常见的有两个：
- badboy：只需要了解，基本弃用。
- blazeMeter：本身是一个云测试平台，提供一个基于chrome浏览器的插件去实现基于chrome浏览器的录制。
### JMeter 自带的
- JMeter 自带的代理录制方式


## JMeter 代理录制原理

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209071009741.png)
没有代理服务器前，对应的请求是通过客户端直接与服务器进行交互。
如果有了 JMeter 的代理服务器后，客户端发送的请求，需要先经过 JMeter 再发送给服务器
>在客户端上进行代理设置，使客户端发送请求是通过代理服务器去和被测服务器进行通信。 和我们翻墙浏览谷歌一样
-  JMeter 的代理服务器 只负责数据的传递解码，不做其他事情。聚合支付的商家其实就是中介，两头传递，聚合了各个支付渠道，支付渠道会给相关的优惠，对应的商家需要费率，这样又可以收一笔费用。

#### 注意⚠️
- 客户端可以和JMeter在一台机器上，也可以不在一台机器上，只需要一个可以通信的网络环境即可。
- **客户端** 和 **代理服务器**，**代理服务器** 和 **被测服务器**  都能够通信。
## JMeter脚本录制

### 压测对象
https://litemall-wx.hogwarts.ceshiren.com/#/login?redirect=user
### 压测页面
登录页面，下订单
### 步骤
- 登录页面
- 点击商品
- 下订单
### 操作手段
- 录制回放，上手容易，入门学习好手段
### 脚本录制方法
- 打开`JMeter`，默认新建压测脚本
#### 「1」添加 测试元件：
`Thread  Group`
>	右键测试计划，添加线程--线程组。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209071155661.png)
####  「2」添加 非测试元件：
`HTTP(S) Test Script Recorder`
>右键测试计划，添加 非测试元件--http代理服务器。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209070901631.jpg)

- HTTP(S) 测试脚本记录器：是 `JMeter` 中的一个元素，可以帮助我们记录我们的 `HTTP` 请求。因此，无需在 `HTTP` 采样器中手动添加我们的请求，我们可以非常快速地进行记录，并且所有内容都会被添加。


PS:以上2步没有先后之分。

####  「3」在`Thread  Group`添加 `Recording Controller`
>右键线程组，添加 非测试元件--录制控制器。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209071151115.png)
####  「4」配置并启动代理服务器
 打开 `HTTP(s) Test Script Recorder`「JMeter的代理服务器」
#####  端口
- 端口：设置的是代理服务器的端口「JMeter的服务端口」，和被测服务器没有任何关系。
  	- 要求端口在本机未被占用。
  	>window：netstat -an | findstr "8088" 
  	>Mac：netstat -an | grep "8088" 
- 设置端口8088 `Port=8088`
##### 目标控制器
- 目标控制器：用来指定脚本解析之后的存放位置，设置`Target Controller = Test Plan > Thread Group > Recording Controller`

>把JMeter录制的所有请求流量都保存到Recording Controller下
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209070917509.png)

##### 编码
- `Recording's default encoding`：录制时的默认编码，如果是中文应用，建议设置为utf-8
##### Type
- `Type`：http请求的实现类型，默认就是`httpclient4`，有可能会出错，则可以考虑换成`java`模式。 
##### 过滤器
- 过滤器：可以不用，但是一旦使用，则必须保证过滤规则的准确性。
- 在 `Request Filtering` 添加 `"Include"` 和 `"Exclude"` URL正则匹配表达式
- 目的：避免录制过多没必要的请求

一般的用法有两种：
###### 静态资源：
- 对于静态资源的过滤，建议直接点击添加建议排除，在最后添加.*即可。
			
###### 域名过滤：
- 包含模式，则表示只要和指定域名有关的请求，一般是服务器的IPor域名。
- 排除模式，则表示过滤掉即不录制指定域名有关的请求。
```
Include：`.*\.(baidu.com).*`
Exclude：`.*\.(js|css|PNG|jpg|ico|png|gif).*`
```
正则的写法是统计的，假设说域名为`aaa.bbb.ccc.ddd`，则正则为：
```
.*aaa\.bbb\.ccc\.ddd.*
```

####  「5」 脚本保存

- JMeter脚本保存
####  「6」 启动
当所有配置检查完毕之后，则一定要点击启动按钮，才会启动`JMeter`的代理服务器。

启动之后正常会弹出根证书的提示信息，告知根证书保存在`JMeter`的`bin`目录下。
>Internet属性 --  内容 -- 证书 -- 导入 -- 文件选择证书
>证书导入向导：选择 受信任的根证书颁发机构  ，下一步，完成。
####  「6」 启动
在客户端配置使用代理。

- 对于https应用，需要安装代理服务器所提供的根证书。

- 对于使用https的应用来说，我们必须在客户端or客户端所在的机器上安装jmeter所生成的根证书，并且要求安装在受信任的根证书目录下。

- 对于windows的bs应用而言，即客户端为浏览器的情况下，我们只需要在internet选项中进行设置即可。
	
	internet选项--连接--局域网设置--勾选为局域网启用代理。

	地址：填写jmeter所在的机器的IP地址。如果客户端和jmeter在一台机器，则可以填写127.0.0.1
			
	端口：和jmeter的代理服务器所配置的端口保持一致。



- 打开浏览器，浏览器的代理服务地址指向 `http://localhost:8088`

### 目的
`JMeter`代理服务器就是为了抓包、解包。
