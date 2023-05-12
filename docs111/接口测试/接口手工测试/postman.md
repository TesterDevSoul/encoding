[接口测试用例设计](https://note.youdao.com/ynoteshare/index.html?id=bab907f77b6f1bfc7320021546f04d30&type=notebook&_time=1680274436754#/WEB6d7018eaa5d721e29ef579deb16da0e2)

接下来我们来学习一个非常好用的接口测试工具，那就是 postman。咱们会先来学习 postman 的基础使用。

# 目录
- Postman 工具准备
- Postman 使用

来看下内容安排，首先先介绍一下 postman 工具，然后搞定 postman 的安装。

接下来就来学习 postman 的基本使用。

## Postman特点

Postman是一个流行的API开发工具，可帮助开发人员设计、测试、调试和文档化API。Postman提供了一个用户友好的界面，可以让你轻松地创建HTTP请求，并查看响应。

以下是Postman的一些主要特点：

#### 可视化界面

Postman提供了一个直观的用户界面，可以方便地创建HTTP请求和查看响应。你可以使用POST、GET、PUT、DELETE和其他HTTP方法创建请求，并在收到响应后查看响应的内容。

#### 集成环境

Postman可以与许多其他工具和服务进行集成，如Git、JIRA和Slack。这使得Postman可以更好地融入你的工作流程中。

#### 环境管理

Postman允许你创建多个环境，每个环境都包含不同的变量。这使得你可以在测试不同的环境时轻松地切换变量。

>一般来说我们测试接口肯定是要会有很多套环境的，比如说生产环境、测试环境，正式环境等等。在 postman 中我们可以通过设置不同的环境变量，很方便的使用同一套测试用例，只是切换到不同的测试环境就可以了。

总的来说，Postman是一个强大的API开发工具，可以大大提高API开发和测试的效率。它提供了一个易于使用的界面和丰富的功能，适用于从初学者到专业开发人员的不同水平。


所以整体来说它支持的功能是非常适合来做接口测试的。

下面我们就来看看怎么样来安装这款工具。

## 安装

Postman 最早是浏览器插件。现在已经提供了独立的安装包，不再依赖于浏览器了。而且现在独立安装是支持跨平台的，MAC、Windows 和 Linux 系统都可以安装使用。


你可以按照以下步骤在Windows、MacOS和Linux上安装Postman：

1. 访问Postman官网，https://www.postman.com/downloads/，点击下载按钮，然后选择适合你操作系统的版本。

2. 下载完成后，双击下载的文件打开安装程序。

3. 在安装程序中选择你的安装目标，然后点击“Install”按钮开始安装。

等待安装完成后，打开Postman。

在Windows上，你可以在开始菜单中找到它；在MacOS上，你可以在启动台中找到它；在Linux上，你可以在应用程序菜单中找到它。

第一次打开Postman时，你需要创建一个帐户或登录到现有的帐户。

你也可以选择跳过这一步，但登录后可以使用更多的功能，如同步你的API集合和环境设置。

现在，你可以开始使用Postman了。通过点击“New”按钮创建新的请求，然后在请求编辑器中输入请求的URL、参数和头部，最后点击“Send”按钮发送请求并查看响应。

注意：在安装过程中，可能需要根据你的操作系统和安全设置输入管理员密码。另外，如果你遇到任何问题，请确保你的计算机满足Postman的最低系统要求。


## 页面介绍

页面大体分成了三大部分，**顶部栏**，**左侧栏**和**右侧栏**。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/blog_pic/postman/20230331233400.png)


### 顶部栏
Home：进入登录注册页面

Workspaces：选择工作区域（会员功能）

Reports：测试报告（付费功能）

Explore：探索 postman 更多功能

搜索框：快速检索过滤

右侧：系统设置区域

### 左侧栏

左侧边栏里面的功能很多

Collections：测试集

APIs：创建 API（需要注册）

Environments：管理环境

Mock Servers：提供 mock 服务

Monitors：监视器

History：历史记录

需要注册之后才能用的功能，比如 workspace，apis，mock servers，monitors 这些，咱们就先不详细介绍了。我们会挑选最重点最常用的功能给大家做介绍。



### 右侧栏
#### 顶部环境信息
- 查看当前使用的环境
- 查看当前环境中的变量
#### 请求配置区域
（新建一个请求页面）

提供各种请求方法（如GET、POST等），请求参数，请求header，请求执行前的设置项目，测试脚本等的配置功能

method：请求方法
URL & Params：URL可以输入request的地址，Params是对应的URL的参数。在Params中输入参数，会自动同步到URL。同理，URL里面输入带有参数的地址，也会自动同步参数到Params。
Send：发送当前请求。在发送请求之后，会自动下载该请求的response。
send and download 在发送请求后，把响应数据保存为本地的文件。
Save 可以直接保存request。也可以选择“Save as”。
Save as 可以保存 请求的名字以及简单的描述。还可以决定要不要把请求保存到Collection里面去。
Authorization 如果访问的服务需要授权，这里可以设置验证方式+填写验证所需的信息，比如用户名密码。
Headers 设置请求头信息。
Body
设置请求的 body。有四种方式进行设置： form-data、 urlencoded、raw 以及 binary。

POST 中要携带的请求数据可以通过 body 上传。

form-data：既可以上传键值对，也可以上传文件
x-www-form-urlencoded：会将表单内的数据转换为键值对
raw：可以上传任意格式的文本，比如说text、json、xml、html等
binary：只可以上传二进制数据，通常用来上传文件。
Pre-request Script
请求前需要执行的脚本可以放置在这里。主要进行一些环境以及全局变量的设置。

Tests
这里写测试用例的断言，会对测试结果进行一些判断。

postman中 的这些前置和断言脚本都是 javascript 脚本，如果我们会 js 语法的话，是可以自己写这些脚本的，但是 postman 当中也为我们提前封装好了一些常用的方法，让我们不需要去掌握js语法，就可以轻松使用这些预置好的脚本。

Generate Code
这里可以将request转化为各种语言的代码。比如python、java、shell、HTTP等。
刚才的这些功能也都给大家整理到笔记当中了，我们可以在笔记中对应着熟悉一下页面上的大体功能。具体这些功能怎么去用，后面的课程里会依次给大家做介绍。


## Postman 的使用演练
### GET
下面使用 Postman 发送一次 GET 请求。使用学院提供的官方演练地址进行演示
> https://httpbin.ceshiren.com/

- （1）进入 Postman 软件界面
- （2）在 URL 处填写 `https://httpbin.ceshiren.com/get`
- （3）选择 GET 请求方式
- （4）点击 send 按钮，查看返回内容


#### 直接发送请求


#### 带params参数请求
get请求带的是param，post请求带的是body


https://httpbin.ceshiren.com/get?para_key=para_value

两种添加方式，

一种直接在链接后添加参数

一种直接在Params下面添加key，value

### POST
https://httpbin.ceshiren.com/post

post请求只带body体参数

#### json - body体

{
    "jsonValue1": "one",
    "jsonValue2": "2"
}


Content-Type对应必须是json

#### form格式
form-data

在form参数内添加对应value值

