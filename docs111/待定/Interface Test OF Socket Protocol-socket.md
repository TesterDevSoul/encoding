---
title:  Interface Test OF Socket Protocol
date: 2022-09-13 10:13:22.317
updated: 2022-09-13 11:22:20.601
url: /archives/socket
categories: 
- interface
tags: 
---

# socket协议的接口测试
## 本章要点
- [ ] 
- [ ] 
- [ ] 
- [ ] 



## 七层协议介绍

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209131038049.webp)
- 七层网络由下往上分为 **物理层** 、**数据链路层** 、 **网络层** 、 **传输层** 、 **会话层** 、 **表现层** 和 **应用层**。

- **IP协议** 对应于 **网络层**，**TCP协议** 对应于 **传输层** ，而 **HTTP协议** 对应于 **应用层**

## What is Socket？
什么是Socket？？


- 在网络上运行的**程序之间**双向通信的端点。
>程序之间：意味着有2个不同的程序在网络中的两台不同的机器上运行。

TCP/IP只是一个协议栈，就像操作系统的运行机制一样，必须要具体实现，同时还要提供对外的操作接口。
　　这个就像操作系统会提供标准的编程接口，比如win32编程接口一样。
　　TCP/IP也要提供可供程序员做网络开发所用的接口，这就是Socket编程接口。”
  
  
  

`Socket` 这个名字很有意思，可以作 **插口** 或者 **插槽** 讲。
>虽然我们是写软件程序，但是你可以想象为弄一根网线，一头插在客户端，一头插在服务端，然后进行通信。所以在通信之前，双方都要建立一个 `Socket`。所以， `Socket` 其实就是通信是需要建立的东西，就是 Socket 那么我们平时说的最多的



![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/jmeter/202209131100809.png)



## 实质
- 实际上socket是对TCP/IP协议的封装
- 它的出现只是使得程序员更方便地使用TCP/IP协议栈而已
- socket本身并不是协议，它是应用层与TCP/IP协议族通信的中间软件抽象层，是一组调用接口（TCP/IP网络的API函数） “TCP/IP只是一个协议栈，就像操作系统的运行机制一样，必须要具体实现，同时还要提供对外的操作接口。 　　这个就像操作系统会提供标准的编程接口，比如win32编程接口一样。 　　TCP/IP也要提供可供程序员做网络开发所用的接口，这就是Socket编程接口。” https://blog.csdn.net/github_34606293/article/details/78230456









本教程介绍 Java 中的套接字编程，从一个简单的客户端-服务器示例开始，演示 Java I/O 的基本特性。将向您介绍原始 包和 NIO，即Java 1.4 中引入java.io 的非阻塞 I/O ( ) API。java.nio最后，您将看到一个示例，该示例演示了在 NIO.2 中从 Java 7 开始实现的 Java 网络。



套接字编程归结为两个系统相互通信。通常，网络通信有两种形式：传输控制协议 (TCP) 和用户数据报协议 (UDP)。TCP 和 UDP 用于不同的目的，并且都有独特的约束：

TCP 是一种相对简单且可靠的协议，它使客户端能够连接到服务器并让两个系统进行通信。在 TCP 中，每个实体都知道它的通信有效负载已被接收。
UDP 是一种无连接协议，适用于您不一定需要每个数据包都到达其目的地的场景，例如媒体流。
要了解 TCP 和 UDP 之间的区别，请考虑如果您正在从您最喜欢的网站流式传输视频并且它丢帧会发生什么。您希望客户端放慢您的电影以接收丢失的帧，还是希望视频继续播放？视频流协议通常利用 UDP。因为 TCP 保证传递，所以它是 HTTP、FTP、SMTP、POP3 等的首选协议。

在本教程中，我将向您介绍 Java 中的套接字编程。我展示了一系列客户端-服务器示例，演示了原始 Java I/O 框架的特性，然后逐步使用 NIO.2 中引入的特性。

[也在 InfoWorld 上：JDK 19：Java 19 中的新特性]
老式 Java 套接字
在 NIO 之前的实现中，Java TCP 客户端套接字代码由java.net.Socket类处理。以下代码打开与服务器的连接：

	Socket socket = new Socket ( server , port );  
一旦我们的socket实例连接到服务器，我们就可以开始获取服务器的输入和输出流。输入流用于从服务器读取数据，而输出流用于将数据写入服务器。我们可以执行以下方法来获取输入和输出流：

	InputStream in =套接字。获取输入流（）；输出流输出=套接字。获取输出流（）；  
	  
因为这些是普通流，与我们用来读取和写入文件的流相同，所以我们可以将它们转换为最适合我们用例的形式。例如，我们可以OutputStream用 a包裹 ，PrintStream这样我们就可以轻松地用println(). InputStream再举一个例子，我们可以用 a包装BufferedReader，通过 an InputStreamReader，以便使用类似 的方法轻松阅读文本readLine()。

下载
下载源代码
“Java 中的套接字编程：教程”的源代码。由 Steven Haines 为 JavaWorld 创建。
Java 套接字客户端示例
让我们看一个对 HTTP 服务器执行 HTTP GET 的简短示例。HTTP 比我们的示例允许的更复杂，但我们可以编写客户端代码来处理最简单的情况：从服务器请求资源，服务器返回响应并关闭流。本案例需要以下步骤：

[立即注册 CIO 100：研讨会和颁奖大会，8 月 15 日至 17 日]
为侦听端口 80 的 Web 服务器创建一个套接字。
获取PrintStream到服务器并发送请求GET PATH HTTP/1.0，PATH服务器上请求的资源在哪里。例如，如果我们想打开一个网站的根目录，那么路径就是/.
获取InputStream服务器的 an，用 a 包装BufferedReader并逐行读取响应。
清单 1 显示了此示例的源代码。

清单 1. SimpleSocketClientExample.java
包com 。极客帽。爪哇世界。简单套接字客户端；

导入java 。伊奥。缓冲读取器；导入java 。伊奥。输入流读取器；导入java 。伊奥。打印流; 导入java 。净。插座；




公共类SimpleSocketClientExample { public static void main ( String [ ] args ) { if ( args.length < 2 ) { System . _ 出来。println ( "用法：SimpleSocketClientExample <server> <path>" ); 系统。退出（0 ）；}字符串服务器= args [ 0 ]; 字符串路径=  

       
    
          
        
              
              
        
          
        参数[ 1 ];  

        系统。出来。println ( "正在加载 URL 的内容：" + server );  

        try { // 连接到服务器Socket socket = new Socket ( server , 80 );
        
            
                

            // 创建输入和输出流以读取和写入服务器PrintStream out = new PrintStream ( socket .getOutputStream ( ) ); BufferedReader in = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) ) ;
                 
                    

            // 遵循 GET <path> HTTP/1.0 的 HTTP 协议，后跟一个空行out 。println （“GET” +路径+ “HTTP/1.0” ）；出来。打印（）；
                
            

            // 从服务器读取数据，直到我们完成读取文档String line = in . 读线（）；而（线！=空）{系统。出来。println (行); 
                线=在。读线（）；}
             
              
            
                 
            

            // 关闭我们的流。关闭（）；出来。关闭（）；
            插座。关闭（）；}捕捉（异常e ）{ 
            e 。打印堆栈跟踪（）；} } }
            
            
        
         
        
        
    
清单 1 接受两个命令行参数：要连接的服务器（假设我们连接到端口 80 上的服务器）和要检索的资源。它创建一个Socket指向服务器并明确指定端口的a 80。然后它执行命令：

获取路径 HTTP / 1.0
例如：

获取/HTTP/1.0 _ _ _
刚才发生了什么？
当您从 Web 服务器www.google.com（com例如www.google.com. 然后它向域名服务器询问 IP 地址（或地址）www.google.com。接下来，它在端口 80 上打开一个到该服务器的套接字。（或者，如果您想定义一个不同的端口，您可以通过在端口号后添加一个冒号来实现，例如：:8080。）最后，HTTP 客户端执行指定的 HTTP 方法，例如GET, POST, PUT, DELETE, HEAD, 或OPTI/ONS. 每种方法都有自己的语法。如上面的代码片段所示，该GET方法需要一个路径，后跟HTTP/version number和一个空行。如果我们想添加 HTTP 标头，我们可以在输入新行之前完成。

在清单 1 中，我们检索了 anOutputStream并将其包装在 a 中PrintStream，以便我们可以更轻松地执行基于文本的命令。我们的代码获得了一个InputStream，将其包装在一个InputStreamReader中，然后将其转换为一个Reader，然后将其包装在一个中BufferedReader。我们使用PrintStream执行我们的GET方法，然后使用BufferedReader逐行读取响应，直到收到null响应，表明套接字已关闭。

现在执行这个类并将以下参数传递给它：

爪哇网。极客帽。爪哇世界。简单套接字客户端。SimpleSocketClient 示例www 。爪哇世界。通讯/
您应该会看到类似于以下内容的输出：

正在加载URL 的内容：www 。爪哇世界。com
HTTP / 1.1 200 OK
日期：星期日，2014年9月21日22:20:13 GMT
服务器：Apache 
X - Gas_TTL ：10缓存控制：max - age = 10 
X - GasHost ：gas2 。_ _ 美西        

X -烹饪-使用：汽油-本地
X -汽油-年龄：8内容-长度：168最后-修改时间：周二，2012年 1 月24日00：09：09格林威治标准时间
Etag ：“ 60001b - a8-4b73af4bf3340 ”内容-类型：文本/ html
变化：接受-编码  
 
      
 
连接：关闭

<! DOCTYPE html > < html lang = "en" > <head> < meta charset = "utf-8" /> <title>汽油测试页</ title > </ head > <body> <br><br> <center >成功</ center > </ body > </ html >


	 
	  





此输出显示 JavaWorld 网站上的测试页面。它回复说它使用 HTTP 1.1 版，响应是200 OK.

Java 套接字服务器示例
我们已经介绍了客户端，幸运的是服务器端的通信方面同样简单。从简单的角度来看，过程如下：

创建一个ServerSocket，指定要监听的端口。
调用ServerSocket'accept()方法来侦听客户端连接的配置端口。
当客户端连接到服务器时，该accept()方法返回一个Socket服务器可以通过它与客户端通信。这与Socket我们用于客户端的类相同，因此过程是相同的：InputStream从客户端获取要读取的内容并OutputStream写入客户端。
如果您的服务器需要可扩展，您需要将其传递Socket给另一个线程进行处理，以便您的服务器可以继续侦听其他连接。
再次调用ServerSocket'accept()方法以侦听另一个连接。
您很快就会看到，NIO 对这种情况的处理会有些不同。不过，现在，我们可以ServerSocket通过传递一个端口来直接创建 a 以进行侦听（ServerSocketFactory下一节将详细介绍 s）：

	ServerSocket serverSocket = new ServerSocket (端口);  
现在我们可以通过以下方法接受传入的连接accept()：

	套接字套接字=服务器套接字。接受（）；// 处理连接...
	
使用 Java 套接字进行多线程编程
下面的清单 2 将到目前为止的所有服务器代码组合成一个更健壮的示例，该示例使用线程来处理多个请求。显示的服务器是一个回显服务器，这意味着它会回显它收到的任何消息。

虽然清单 2 中的示例并不复杂，但它确实预测了下一部分关于 NIO 的内容。请特别注意我们必须编写的线程代码量，以便构建可以处理多个同时请求的服务器。

清单 2. SimpleSocketServer.java
包com 。极客帽。爪哇世界。简单套接字客户端；

导入java 。伊奥。缓冲读取器；导入java 。伊奥。I / O异常；导入java 。伊奥。输入流读取器；导入java 。伊奥。打印作家；导入java 。净。服务器套接字；导入java 。净。插座；






公共类SimpleSocketServer扩展Thread { private ServerSocket serverSocket ; 私有int端口；私人布尔运行=假；    

     
     
      

    公共SimpleSocketServer （int端口）{这个。端口=端口；}  
    
        
    

    public void startServer () { try { 
            serverSocket = new ServerSocket (端口); 这个。开始（）；} catch ( I / OException e ) { 
            e . 打印堆栈跟踪（）；} } 
    
        
          
            
        
         
        
        
    

    公共无效停止服务器（）{
        运行=假；这个。中断（）；} 
     
        
    

    @Override public void run () { 
        running = true ; 而（运行）{尝试{系统。出来。println ( "监听连接" );
     
     
        
        
            
            
                  

                // 调用accept() 接收下一个连接Socket socket = serverSocket . 接受（）；
                

                // 将socket传给RequestHandler线程处理RequestHandler requestHandler = new RequestHandler ( socket ); 
                请求处理程序。开始（）；} catch ( I / OException e ) { 
                e . 打印堆栈跟踪（）；} } }
                  
            
             
            
            
        
    

    public static void main ( String [ ] args ) { if ( args.length == 0 ) { System . _ 出来。println ( "用法：SimpleSocketServer <端口>" ); 系统。退出（0 ）；} int端口=整数。parseInt (参数[ 0 ] ); 系统。出来。打印   
    
          
        
              
              
        
            
        ( "在端口上启动服务器：" + port );  

        SimpleSocketServer服务器=新的SimpleSocketServer (端口); 
        服务器。启动服务器（）；  

        // 1 分钟后自动关机try { Thread . 睡眠（60000 ）；}捕捉（异常e ）{ 
            e 。打印堆栈跟踪（）；}
        
        
              
        
         
        
        

        服务器。停止服务器（）；} }
    


类RequestHandler扩展Thread {私有Socket套接字；RequestHandler ( Socket套接字) {这个。插座=插座；}   

     
     
    
        
    

    @Override public void run () { try { System . 出来。println ( "收到连接" );
     
    
        
        
              

            // 获取输入输出流BufferedReader in = new BufferedReader ( new InputStreamReader ( socket.getInputStream ( ) ) ) ; PrintWriter out = new PrintWriter ( socket.getOutputStream ( ) ) ;
                    
                 

            // 将我们的标头写出给客户端out . println ( "回声服务器 1.0" ); 出来。冲洗（）；
              
            

            // 向客户端回显行，直到客户端关闭连接或我们收到空行String line = in . 读线（）；while ( line ! = null && line.length ( ) > 0 ) { out . println ( "回声：" + line ); 出来。冲洗（）；
                线=在。读线（）；}
             
                 
            
                  
                 
            

            // 关闭我们的连接。关闭（）；出来。关闭（）；
            插座。关闭（）；
            
            

            系统。出来。println ( "连接关闭" ); }捕捉（异常e ）{ 
            e 。打印堆栈跟踪（）；} } }  
        
         
        
        
     a