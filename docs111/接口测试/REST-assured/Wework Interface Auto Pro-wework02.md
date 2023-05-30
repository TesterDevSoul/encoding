---
title: Wework Interface Auto Pro
date: 2022-08-26 09:43:55.792
updated: 2022-08-28 12:05:07.527
url: /archives/wework02
categories: 
- rest-assured
tags: 
---

# 一」token优化
## 问题提出
在前面获取`token`为传入对应参数获取，会存在以下问题：
- 如果企业更换，则需要改动代码参数
- 对应脚本存在硬编码格式
## 解决思路
为了让代码不存在硬编码格式，更换企业的时候确保脚本独立性，把企业相关认证参数放在**配置文件**，做到企业随时可切换，并且不改变代码。
## 方案
- 通过配置文件获取token
### 配置文件
- 方式一：`config.properties`
- 方式二：`config.yaml`

#### 方式一
- `config.properties`：
```properties
corpid=06bb
corpsecret=8gJzOXhq0
#key=value
#String=Object
```
>配置文件格式为 key、value 的 map结构

##### 工具类解析配置文件
```java

//解析全局配置文件的工具类
public class ConfigUtil {
    Properties properties = new Properties();

    public Object getProperty(String key){
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.get(key);
    }
}
```
##### 解析获取token

```java
//优化一：配置文件获取token ，不同企业直接改配置文件，解耦
public class TokenProTest {
    String corpid = "corpid";
    String corpsecret = "corpsecret";

    /**
     * 环境配置文件可以是properties，yaml
     * 方式一：config.properties----map结构
     */
    @Test
    public void getTokenFromConfig(){
        System.out.println(new ConfigUtil().getProperty("corpid"));
        System.out.println(new ConfigUtil().getProperty("corpsecret"));

        ConfigUtil configUtil = new ConfigUtil();
        //直接获取token
        String access_token = given().log().all()
                .queryParam(corpid, configUtil.getProperty(corpid))
                .queryParam(corpsecret, configUtil.getProperty(corpsecret))
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract().response()
                .path("access_token");
        System.out.println("token:"+access_token);
    }
}
```



#### 方式二
- `config.yaml`：

```yaml
#多环境
master:
  - corpid: wwe
    corpsecret: 6Yg
dev:
  - corpid: 1b
    corpsecret: 8g
test1:
  - corpid: 8682dbe1b
    corpsecret: gZIUTXezX
```


##### 解析获取token
>直接使用`json`解析为`map`结构。
```java
public class TokenProTest {
    String corpid = "corpid";
    String corpsecret = "corpsecret";

    @Test
    public void getTokenFromYaml(){
        MapperUtil<HashMap<String,List<HashMap<String,Object>>>> mapperUtil = new MapperUtil<HashMap<String,List<HashMap<String,Object>>>>();
        HashMap<String,List<HashMap<String,Object>>> config  = mapperUtil.getReadValue("src/test/resources/config.yaml");
        List<HashMap<String, Object>> master = config.get("master");

        RequestSpecification requestSpecification = given().log().all();
        master.forEach(stringObjectHashMap -> requestSpecification.queryParams(stringObjectHashMap));
        //直接获取token
        String access_token = 
        		      requestSpecification
                      		.when()
                                   .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                                .then().log().all()
                                   .extract().response()
                                   .path("access_token");
        System.out.println("token:"+access_token);
    }
}
```

# 二」参数传递模版优化

## 问题提出
- `body`体为字符串，在代码里每次都要改动代码
- 对应脚本存在硬编码格式
## 解决思路
确保脚本独立性，把`body`参数放在相关配置文件，做到不改变代码随时改变参数
>json格式的就放json文件，然后文件使用fasterxml进行解析
## 方案
通过配置文件json获取body体；传入参数有变动的时候需要每次更改配置文件的value值，这个时候就需要一个模板，把我们需要的参数和结构提前定义好，我们只需要修改其中对应的值即可，这就要引出今天的两位主角：

- `JsonPath`
- `Mustache`

### JsonPath
这里的`JsonPath`指的是[`Jayway JsonPath`](https://github.com/json-path/JsonPath)，`maven`依赖如下：
```
 <dependency>
    <groupId>com.jayway.jsonpath</groupId>
    <artifactId>json-path</artifactId>
    <version>2.7.0</version>
</dependency>
```
#### 步骤获取替换
```
{
  "name": "departName",
  "parentid": 1,
  "order": "{{orderNum}}"
}
```
以下实操演示均先分为以下两步：
1. 以`Jayway JsonPath`的`parse`方法对`json`文件内容进行解析获取
    ```java
   DocumentContext documentContext = JsonPath.parse(new File(JSON文件地址));
    ```

2. 再使用`set`方法以`Jayway JsonPath`的语法对解析结果进行修改
	```java
    documentContext.set(JSONPath路径,修改后的值);
    ```


```java
public class JsonPathTest {
    @Test
    public void test() throws IOException {
        //jsonpath
        DocumentContext documentContext = JsonPath.parse(new File("src/test/resources/depart3.json"));
        documentContext.set("$.name","111");
        String jsonString = documentContext.jsonString();
        System.out.println(jsonString);//{"name":"111","parentid":1,"order":"{{orderNum}}"}
    }
}
```
fasterjson报错：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/fasterxml/202208261125022.png)
>之所以有开头的warning，是因为redission默认code用的是jackson依赖fst;fst有问题，咱们不用他就行啦;序列化的选择有很多，选择避开fst

### Mustache

上面对应的`JsonPath`的模板技术，但是有缺点，就是`JsonPath`只能处理`json`格式的模板文件，如果是非`json`的就不行了。
这时候就需要有一个通用的模板技术，这就是`Mustache`。
`Mustache`,字面意思是**大胡子**，可能是因为它的模板符号是花括号，比较像大胡子吧。
例如[官网](https://mustache.github.io/)的首页这张图：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/restassured/202208261135409.png)

>根据上图已知，Mustache支持各种语言，常用的几乎都能囊括其中，这里有Java为例进行研究

`maven`依赖如下：

```xml
<dependency>
    <groupId>com.github.spullara.mustache.java</groupId>
    <artifactId>compiler</artifactId>
    <version>0.9.10</version>
</dependency>
```


#### 步骤获取替换
- `depart3.mustache`
```
{
  "name": "{{departName}}",
  "parentid": 1,
  "order": {{orderNum}}
}
```
>两种方式，一种替换后重新写入json文件，一种替换后直接赋值给变量。

##### 重新写入`json`文件
```java
    @Test
    public void test1() throws IOException {
        //创建HashMap,将要修改的字段存入map中
        HashMap<String,Object> map = new HashMap<>();
        map.put("departName","0000");
        map.put("orderNum",345.4f);
        //创建DefaultMustacheFactory对象
        DefaultMustacheFactory factory = new DefaultMustacheFactory();
//        compile方法编译文件内容，execute执行输出在这里插入图片描述
        Mustache mustache = factory.compile("depart3.mustache");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("src/test/resources/depart4.json"));

        mustache.execute(writer,map);
        writer.flush();
        writer.close();
    }
```
##### 直接赋值给变量
```java
 @Test
    public void test2() throws IOException {
        //创建HashMap,将要修改的字段存入map中
        HashMap<String,Object> map = new HashMap<>();
        map.put("departName","0000");
        map.put("orderNum",345.4f);
        //创建DefaultMustacheFactory对象
        DefaultMustacheFactory factory = new DefaultMustacheFactory();
//        compile方法编译文件内容，execute执行输出在这里插入图片描述
        Mustache mustache = factory.compile("depart3.mustache");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(stream);

        mustache.execute(writer,map);
        writer.flush();
        writer.close();
        String finalString = new String(stream.toByteArray());
        System.out.println("finalString:"+finalString);

    }
```

### 优缺点对比
如果实践了上面的两种模板方法会发现两者在使用上的优缺点。
#### 优点

`JsonPath`：可设置默认值，在`json`文件中可提前设置好默认值，字段不修改就使用默认值，需要修改再进行修改
`Mustache`：不受文件格式的影响，字段替换逻辑也简单清晰

#### 缺点

`JsonPath`：只适用于`json`文件
`Mustache`：灵活度不如`JsonPath`，提前必须指定好需要修改的字段

### 总结
如果接口的请求体都是以`json`为主的，那么推荐使用`JsonPath`，更灵活的控制接口参数的修改程度；
当遇到非`json`格式的文件时可用`Mustache`来解决模板化的问题，只是灵活度要稍稍下降一点。


# 三」获取结果断言优化

## 问题提出
- 断言`Response`的`body`内容，每次部门有了增删变化，对应获取部门的`body`体都不一样
## 解决思路
保证每次断言响应`Response`的	`body`模版
>只要模版中的关键字都存在即断言成功
## 方案
断言对应的响应模版，则需要使用`schema`断言
响应结果转换为schemas模版：[jsonschema](https://jsonschema.net/app/schemas/0)
```java
matchesJsonSchemaInClasspath(模版JSON)
```
- 完全代码：
```java
    @Test
    public void getDepart() {
        //直接获取
        Response response = given().log().all()
                .contentType("application/json; charset=utf-8")
                .queryParam("access_token", access_token)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/simplelist")
                .then().log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("depart.json"))
                .extract().response();
        Integer errcode = response.path("errcode");
        String errmsg = response.path("errmsg");
        //断言优化点：多断言
        assertAll(
                () -> assertEquals(0,errcode),
                () -> assertEquals("ok",errmsg)
        );
    }
```
# 四」删除接口优化

gpath路径验证：
```
import groovy.json.JsonSlurper

def jsonSlurper = new JsonSlurper()
def one = jsonSlurper.parseText('')
println(one)

def id = one.department.findAll{ it.order < 1000}.id
println(id)
```
需要运行[groovy](https://onecompiler.com/groovy/3xxb42jsb)代码
## 问题提出
- 删除的接口需要传入对应主键ID进行删除，如果没有可删除数据时，对应接口需要兼容异常报错

## 解决思路
主键虽然为int类型，但是最小值为1，从1开始自增往后，则如果没有可删除的数据时，对应ID参数传入-1；`Response` 响应的 `code`码校验是否一致，`msg`校验是否包含特定`str`即可。
## 方案
- 删除接口参数化，参数化传值的时候如果list为空，则代表没有可删除的数据，则返回-1

    ```java
    if(idList.size() == 0){
       return Stream.of(Arguments.arguments(-1));
    }else {
       return idList.stream();
    }
    ```


```java
    @ParameterizedTest(name = "删除部门的ID：{0}")
    @MethodSource
    @Order(3)
    public void deleteDepart(int id){
        //删除部门请求
        Response response = given().log().all()
                .contentType("application/json; charset=utf-8")
                .queryParam("access_token", access_token)
                .queryParam("id", id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all()
                .statusCode(200).extract().response();
        Integer errcode = response.path("errcode");
        String errmsg = response.path("errmsg");
        if(id<0){
            System.out.println("没有可删除的部门");

            //异常报错
            assertAll(
                    () -> assertEquals(40058, errcode),
                    () -> assertThat(errmsg,is(containsString("field `id` expect type `uint32`")))
            );
        }else {
            //删除成功断言
            assertAll(
                    () -> assertEquals(0, errcode),
                    () -> assertEquals("deleted", errmsg)
            );
        }
    }
    static Stream<Arguments> deleteDepart(){
        //获取所有部门
        Response response = given().log().all()
                .contentType("application/json; charset=utf-8")
                .queryParam("access_token", access_token)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/simplelist")
                .then().log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("depart.json"))
                .extract().response();
        List<Arguments> idList= response.path("department_id.findAll{ it.order < 1000}.id");
        //获取order小于1000的部门id列表
        System.out.println(idList);
        if(idList.size() == 0){
            return Stream.of(Arguments.arguments(-1));
        }else {
            return idList.stream();
        }
    }
```


# PO 封装优化








