---
title: JSON Schema validation
date: 2022-08-16 17:59:10.64
updated: 2022-08-17 11:45:17.764
url: /archives/ra09
categories: 
- rest-assured
tags: 
- rest-assured
---

# JSON Schema验证

`REST Assured`从版本 2.1.0 开始支持[Json Schema](https://jsonschema.net/app/schemas/0)验证。


## 本章要点
- 验证`response`是否符合架构

## Json Schema
### 依赖导入
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>${rest-assured.version}</version>
</dependency>
```
### 验证步骤
- 1.发送请求，获取请求结果
- 2.在[Json Schema](https://jsonschema.net/app/schemas/0)网站进行结果转换
- 3.响应结果转换的`schema`存到文件`src/test/resources/schema.json`
- 4.断言，不进行数据断言进行响应模版的断言
### matchesJsonSchemaInClasspath

```java
    @Test
    public void schemaTest() {
            given()//发送请求参数
                .log().all()//添加请求日志
            .when() //GET请求 .get(); POST请求 .post()
                .get("https://demoqa.com/BookStore/v1/Books")
            .then()//获取响应
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
    }
```

>`matchesJsonSchemaInClasspath`是从静态导入`io.restassured.module.jsv.JsonSchemaValidator`的


