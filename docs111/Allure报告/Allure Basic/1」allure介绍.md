# Allure2 报告介绍
## 本章要点


## Allure2 介绍

在前面了解了一些测试框架的使用，比如`Java`中的是`JUnit5`/`TestNG`、`Python`中的`pytest`/`unittest`。 

测试框架运行测试用例生成的结果可以结合一些报告的框架来生成一个比较直观、美观的测试报告。

我们最终都会把测试结果或者是测试报告发送全组邮件或者是展示给其他部门协作的同事或者领导。

虽然目前测试框架也有自带的报告，但是比较简陋，不美观也不能直观的进行测试用例的统计。比如：测试用例整体的通过率、失败个数、跳过了多少条测试用例。

如果说想要展示一些复杂的信息，比如：在测试报告中展示错误的日志及其相关截图、或者执行的步骤及步骤的视频。或者是想要在报告中添加一些代码的片段，比如html、div的这些标签。

以上这些信息都需要我们在使用的测试框架的基础上来进行二次开发，才能进行展示。

那是否存在一个已有的测试报告框架让我们的报告展示的更完善， 比如：可以反应当前项目的整体进度、 在每一个测试套件的阶段都有一个汇总的信息，整体的项目可以通过一个曲线图的形式或者柱形图的形式来给我们展示出来，对应测试用例的趋势图展示成功率是逐渐上升的还是有波动的曲线还是有下降的。这些都是我们需要写一些代码进行二次开发才能实现的。

同时，我们想要去了解当前执行的一个成功率或者是失败率、或者想把当前项目的测试用例进行分类，并且通过测试报告来展现出来不同的分类层次，有大的层面上的分类，比如：家电业务，还有小的层面上的用例分类，比如：智能家居，再往下还有更小层面上的测试用例分类，比如：厨房、起居室等等。那应该怎样去解决？

还有，如果我们想为当前的测试用例起一个中文名称在测试报告中展示，方便其他人可以很容易的阅读我们整体的测试用例。

每一个测试用例都想要有一个很详细的展示，比如当前测试用例执行的步骤，在每一个关键步骤执行时对应的日志展示、截图展示、如果遇到报错后对应的错误详细信息。这些都能够体现在我们的测试报告中。

那说了这么多，其实我们今天所要学习的Allure2这个报告就能实现我们刚刚提到的这些功能。
Allure2报告框架可以满足我们大部分的需求，在工作当中，已经足够使用。哪怕说Allure2没有提供某些功能的相关注解，但是它还有一个很好的扩展性，可以根据自己公司特定的业务进行一个二次开发的定制。



Allure2 是一个**开源**的测试报告框架，可以帮助测试人员和开发人员更好地**展示和管理测试结果**。它支持多种测试框架（如JUnit、TestNG、Cucumber、Pytest等）和多种编程语言（如Java、Python等），能够生成详细、易于理解的测试报告。

不只是可以在Mac系统的电脑上运行，也可以在Window系统的电脑、Linux系统的服务器端运行该报告。
 
### 主要特点

Allure2 的主要特点包括：

##### 多样化的报告展示

Allure2 提供了多种报告展示方式，如饼图、散点图、韦恩图等，可以根据需要进行选择。

##### 支持历史记录

Allure2 支持将测试结果存储到数据库中，可以随时查看历史测试结果。

##### 支持多语言

Allure2 支持多种编程语言，可以在不同的开发环境中使用。
>降低学习成本，我们不需要去学习一个新的语言，再把测试框架和测试报告框架集成在一起。它自己就支持多种编程语言。

##### 易于集成

Allure2 可以与多种测试框架集成，包括JUnit、TestNG、Cucumber等，同时还可以与 CI/CD 工具（如Jenkins）进行集成。

##### 灵活的配置选项
Allure2 提供了多种配置选项，可以根据需要进行自定义配置。


总之，Allure2 是一个功能强大、易于使用的测试报告框架，可以帮助开发人员和测试人员更好地管理测试结果，提高测试效率和质量。


## Allure2 报告展示
首先，我们来看一下对应的报告展示。跳转到github查看。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/how_allure_works.png)

点击进入 [allure首页](https://allure-framework.github.io/allure-demo/5/)。


### 首页

Allure报告的首页是测试结果的总览页面，展示了测试用例的运行状态和各种图表数据。

左侧面板下面为页面默认语言切换「默认是英文的语言」。

Allure报告的首页右侧面板主要展示了**测试结果的概览**和**详细信息**。具体来说，这个面板的主要内容包括：


#### 1. 测试概览

展示了测试结果的概览信息，包括**测试用例总数**、**通过数**、**失败数**、**跳过数**等。

#### 2. 整体趋势

右侧面板还提供了一个“**历史记录**”的功能，可以查看测试结果的历史记录和**趋势**。
每次运行测试用例对应的成功、失败的占比都有对应的曲线图。
每一次迭代，一般都会与持续集成相结合。最终会绘制一个曲线图来把控整体的项目进度。 
#### 3. 测试套件


整个测试套件 的总体概况，会根据项目中一个大的类别展示出来对应的内容，目前大类别就是在测试类的层次，不会再细分到具体的测试方法中。

每个测试类会具体展示相对应正确与错误的用例个数。
#### 4. 其他
其他的还有一些环境的展示，类别的展示还有对应的执行器。

以上就是首页的总体的概览。



### 详情页
下面我们选择进入到suite页面去看一下对应的展示，也就是测试用例的详情页面。


Allure报告的详情页展示了每个测试用例的详细信息，包括测试用例的名称、描述、状态、参数、步骤、日志等。

在详情页的顶部，可以看到该测试用例的名称、状态（通过/失败/跳过）、描述信息、所属测试套件、开始时间、结束时间、持续时间等基本信息。此外，还可以在该部分中看到该测试用例的标签、参数、附加信息等。

>在详情页中，首先展示了测试用例的名称和状态，通过绿色表示“通过”，红色表示“失败”，黄色表示“跳过”。<br>
如果运行失败，则会有对应的失败原因，展示测试用例执行失败的原因，方便用户进行问题排查和分析。<br>
下面是测试用例的**描述信息**，展示测试用例的详细描述，可以帮助用户更好地了解该测试用例的功能和预期结果。<br>
在描述信息下方，展示了该测试用例的**执行日志**，包括输入和输出的信息等。<br>
此外，在日志下方，还可以看到该测试用例的**截图**，如果在执行过程中发生了错误，这些截图可以帮助用户更好地理解问题所在。


除此之外，详情页还展示了一些其他的信息，例如测试用例的标签、执行时间、作者等，这些信息可以帮助用户更好地管理和分析测试结果。


通过详情页，用户可以了解每个测试用例的详细情况，包括测试结果、执行日志、截图等等，并且可以通过失败原因、截图等信息进行问题分析和排查，从而更好地优化测试用例的执行结果。


总的来说，Allure报告的详情页提供了非常详细和全面的测试用例信息，能够帮助用户更好地理解和分析测试结果。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230314212637.png)



## Allure2 安装

Allure2 安装有以下几种方式，不同的安装方式对应的配置内容不同：

1. 命令行工具安装。

2. 压缩包解压安装。

3. Python插件安装。

4. Maven插件安装。



要安装 Allure2，您需要完成以下步骤：

### 1. 环境准备

确保已安装 `Java` 环境，版本需要在 **1.8** 或以上。


### 方式1：命令行工具安装
安装 `Allure2` 命令行工具。

可以使用以下命令在终端中进行安装：
```bash
# for Linux
$ sudo apt-add-repository ppa:qameta/allure
$ sudo apt-get update 
$ sudo apt-get install allure

# for Mac
$ brew install allure

# for Windows
$ scoop install allure

```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230310112323.png)


直接在命令行输入命令验证对应版本。
```bash
# 环境验证
allure --version
```

### 方式2：压缩包解压安装

压缩包下载方式有两种：

方式一：在 [Allure2 GitHub Release](https://github.com/allure-framework/allure2/releases) 页面下载对应操作系统版本的 `Allure2`。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230310113301.png)

方式二：在 [maven repo](https://repo1.maven.org/maven2/io/qameta/allure/allure-commandline/) 页面下载对应版本的 `Allure2`。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230310113043.png)

压缩包下载后，解压，将 `bin` 目录添加到环境变量中。

环境变量配置生效后，直接在命令行输入命令验证对应版本。
```bash
# 环境验证
allure --version
```

### 方式3：Python插件安装

安装`Allure Python`插件，需要先安装`Python`和`pip`包管理工具。

在安装前请确保在操作系统上安装了`Java`运行环境，因为`Allure`是用`Java`编写的。

以下是使用 `pip` 安装 `Allure Python` 插件的步骤：

1. 打开命令行终端。

2. 运行以下命令来安装 `Allure Python` 插件：

    ```bash
    pip install allure-pytest
    ```

3. 等待安装完成验证。

    ```python
    # linux/mac
    > pip list |grep allure
    allure-pytest         x.xx.x

    # windows
    > pip list |findstr allure
    allure-pytest         x.xx.x
    ```


### 方式4：Maven插件安装


要在`Maven`项目中安装`Allure`插件，需要在Maven项目的`pom.xml`文件中添加如下插件配置：

```xml
<properties>
    <allure.version>2.21.0</allure.version>
    <allure.maven.version>2.12.0</allure.maven.version>
    <allure.cmd.download.url>
        https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline
    </allure.cmd.download.url>
</properties>



<build>
  <plugins>
    <plugin>
      <groupId>io.qameta.allure</groupId>
      <artifactId>allure-maven</artifactId>
      <version>${allure.maven.version}</version>
      <configuration>
        <!-- allure报告的版本号 -->
        <reportVersion>${allure.version}</reportVersion>
        <allureDownloadUrl>${allure.cmd.download.url}/${allure.version}/allure-commandline-${allure.version}.zip</allureDownloadUrl>
        <!-- <resultsDirectory>${project.build.directory}/allure-results</resultsDirectory>
        <reportDirectory>${project.build.directory}/allure-report</reportDirectory> -->
      </configuration>
    </plugin>
  </plugins>
</build>
```

其中，`<groupId>`指定了插件的`groupId`，`<artifactId>`指定了插件的`artifactId`，`<version>`指定了插件的版本号。`<configuration>`中可以配置一些参数，例如报告的版本号。

在添加了插件配置之后，可以使用以下`Maven`命令来生成`Allure`测试报告：

```bash
mvn clean allure:report
```

命令行运行以上命令，不用管中间是否有对应ERROR，运行完后在该项目下查看隐藏的文件夹是否有allure，在allure文件夹下有对应版本号的文件，说明当前项目下对应的allure版本下载成功。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230310160755.png)




```bash
mvn clean test
mvn allure:report

# 上面的命令也可以合并为一个命令
# 去下载相关的allure版本，到当前项目的.allure文件夹内
mvn clean test --alluredir=target/allure-results allure:report 
mvn clean test -Dtest=指定运行测试类 allure:report
```


##### mvn clean test 
将测试结果生成为`Allure`需要的json格式。`XML`格式的测试结果转换为`Allure`测试报告。
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230313165430.png)

##### mvn allure:report

会下载`allure`到本项目下，并且生成站点文件site。

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230313165736.png)

```bash
# allure报告打开网站
mvn allure:serve
```

##### mvn allure:serve

启动一个本地服务器，可以在浏览器打开Allure报告。

生成的测试报告位于 `项目/target/site/allure-maven-plugin/index.html`，可以在浏览器中打开。


##### 注意⚠️

  使用命令行验证的前提是项目中有对应的测试用例，如果没有则无法生成对应的 allure-results 文件夹。


## Allure 运行原理


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230310180336.png)

具体流程如下：

1. **测试用例** 代码编写并使用 **测试框架**「`JUnit5`」运行测试。

2. **测试执行器**「`JUnit5 执行器`」运行测试用例，然后将测试结果传递给 **Allure 测试监听器**「即`Allure JUnit5 扩展类`」。

3. **Allure 测试监听器**「即`Allure JUnit5 扩展类`」将测试用例结果转为测试报告所需的格式「测试结果 `XML` 文件」，并将其写入 **Allure 报告生成器**。<br>`mvn clean test`

    >maven 的 surefire插件 会将测试用例结果写入 **默认的报告文件夹下**「即`surefire-reports`」，生成**测试结果 XML 文件**「`surefire-reports/TEST-包名.类名`」。

    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230313162318.png)


4. **Allure 报告生成器**「`Allure命令行工具`」 读取测试结果 `XML` 文件，并根据测试结果 `XML` 文件生成 **Allure 报告文件夹**「`allure-result`」，其中包含 **测试结果** 和 **报告页面** 。<br>`mvn allure:report`


    ![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230313165736.png)

5. 打开浏览器，使用 Allure 报告文件夹中的页面查看生成的测试报告。<br>`mvn allure:serve`




需要注意的是，在 JUnit5 中使用 Allure 生成测试报告，需要在 Maven 或 Gradle 中添加 **Allure JUnit5 扩展类** 和 **Allure Maven/Gradle 插件** 的依赖
。

此外，还需要在测试类中添加 **Allure 的注解**，以便将测试结果写入 Allure 报告。


## 指定Allure路径

`--alluredir` 是插件「`Python`插件、`Maven Surefire`插件」提供的一个参数，
可以在`pytest`命令行使用也可以在`mvn`命令行终端使用。如下：

```bash
# pytest命令行使用
pytest --alluredir = report目录

# mvn命令行使用 maven插件安装
mvn clean test --alluredir = report目录
```

例如：

根据指定目录生成报告：
```bash
# pytest命令行使用
pytest --alluredir=./report

# mvn命令行使用
mvn clean test --alluredir=target/allure-results
```

运行的步骤：

1. `mvn` 命令清除项目以前生成的`target`目录。
2. 运行测试用例。
3. 生成的allure结果数据保存到 `target/allure-results` 目录中。

`target/allure-results` 目录**优点**：

每次运行测试用例都会生成新的报告目录，不会在已有的目录内进行测试用例的叠加。


生成报告后运行 `serve` 打开测试报告，如下：
```bash
allure serve report目录

# mvn 直接找target/allure-results目录
mvn allure:serve 
```
`allure serve` 前提条件为`allure`配置了全局环境变量。

例如：

```bash
# pytest
allure serve ./report
#mvn
allure serve target/allure-results

# mvn 直接找target/allure-results目录
mvn allure:serve 
```

该命令将启动一个本地服务器，可以在浏览器打开Allure报告。