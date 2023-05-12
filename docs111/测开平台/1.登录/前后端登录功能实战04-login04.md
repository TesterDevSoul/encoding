---
title: 前后端登录功能实战04
date: 2022-10-31 10:54:42.123
updated: 2022-10-31 15:56:51.386
url: /archives/login04
categories: 
tags: 
---

# 前后端登录功能实战04

## 本章重点

## 项目创建
### idea创建Spring项目
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210311123078.png)

### pom

- 添加 `tk.mybatis`
- 添加 `generator` 插件

```xml
<swagger.version>3.0.0</swagger.version>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.version}</version>
</dependency>
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>4.2.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>${swagger.version}</version>
</dependency>

<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.4.1</version>
    
    <!--插件设置-->
    <configuration>
        <!--允许移动生成的文件-->
        <verbose>true</verbose>
        <!--启用覆盖-->
        <overwrite>true</overwrite>
        <!--自动生成配置 如果名字是generatorConfig.xml可以省略配置-->
        <configurationFile>src/main/resources/generator/generatorConfig.xml</configurationFile>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.31</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>4.2.2</version>
        </dependency>
    </dependencies>
</plugin>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.1</version>
    <configuration>
        <source>${java.version}</source>
        <target>${java.version}</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>${mapstruct.version}</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```
### Mapper插件
#### MySqlExtensionMapper
- `src/main/java/top/testeru/autosphere/common/MySqlExtensionMapper.java`
```
package top.testeru.autosphere.common;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MySqlExtensionMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T> {
}

```
#### BaseEntityNew
- 实体类继承
```
package top.testeru.autosphere.entity;

import java.io.Serializable;

public abstract class BaseEntityNew implements Serializable {
    private static final long serialVersionUID = 2054813493011812469L;
}
```
#### config
- `src/main/resources/generator/config.properties`
```
# 数据库配置 A
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://110.40.250.165:3307/autosphere?characterEncoding=UTF-8&useUnicode=true&nullCatalogMeansCurrent=true
jdbc.username=root
jdbc.password=
# 通用Mapper配置
mapper.plugin=tk.mybatis.mapper.generator.MapperPlugin
mapper.Mapper=top.testeru.autosphere.common.MySqlExtensionMapper
#dao类和实体类的位置
java.targetProject=src/main/java
#实体类的包名
java.targetPackage=top.testeru.autosphere.entity
#实体类的根包名
java.rootClass=top.testeru.autosphere.entity.BaseEntityNew
#mapper.xml位置
mapper.targetProject=src/main/resources
#mapperXML文件路径
mapper.targetPackage=mapper

#dao类的包名
java.targetMapperPackage=top.testeru.autosphere.dao
#数据库表名
table.schema=autosphere
table.name=user
table.domain=User
```


#### generatorConfig
- `src/main/resources/generator/generatorConfig.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">


<!-- 配置生成器 -->
<generatorConfiguration>
    <!--执行generator插件生成文件的命令： call mvn mybatis-generator:generate -e -->
    <!-- 引入配置文件 -->
    <properties resource="generator/config.properties"/>
    <!--classPathEntry:数据库的JDBC驱动,换成你自己的驱动位置 可选 -->
    <!--    <classPathEntry location="${jdbc.jar.path}"/>-->

    <!-- 一个数据库一个context 配置对象环境-->
    <!--    id="MysqlTable" ：此上下文的唯一标识符。此值将用于一些错误消息。
    targetRuntime="MyBatis3Simple"：为了避免生成Example相关的代码和方法。如果需要则改为Mybatis3
    defaultModelType="flat" ：每个表只生成一个实体类    -->
    <context id="MysqlTable" targetRuntime="MyBatis3Simple" defaultModelType="flat">

        <!-- 配置起始与结束标识符 指明数据库的用于标记数据库对象名的符号-->
        <!-- ORACLE就是双引号，MYSQL默认是`反引号  数据库使用mysql,所以前后的分隔符都设为”`”-->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>


        <!--用来定义一个插件，用于扩展或者修改MBG生成的代码，不常用，可以配置0个或者多个，个数不受限制。
        只有一个Type标签，其中填插件的全限定名。
        常用的有缓存插件，序列化插件，RowBounds插件，ToString插件等。-->
        <plugin type="${mapper.plugin}">
            <property name="mappers" value="${mapper.Mapper}"/>
        </plugin>



        <!--数据库连接配置-->
        <jdbcConnection driverClass="${jdbc.driver}"
                        connectionURL="${jdbc.url}"
                        userId="${jdbc.username}"
                        password="${jdbc.password}">
        </jdbcConnection>

        <!-- 配置生成的实体类位置 type使用XMLMAPPER，会使接口和XML完全分离。
            targetPackage：放置生成的类的包。 MyBatis Generator 将根据需要为生成的包创建文件夹
            targetProject：包所在的project下的位置，指定了将保存对象的项目和源文件夹。该目录不存在，MyBatis Generator 将不会创建该目录        -->
    <javaModelGenerator targetPackage="${java.targetPackage}" targetProject="${java.targetProject}">
        <!-- 设置一个根对象，
        如果设置了这个根对象，那么生成的keyClass或者recordClass会继承这个类；在Table的rootClass属性中可以覆盖该选项            注意：如果在key class或者record class中有root class相同的属性，MBG就不会重新生成这些属性了，包括：                1，属性名相同，类型相同，有相同的getter/setter方法；
        rootClass：所有实体类的父类，如果父类定义了一些字段以及对应的getter、setter方法，那么实体类中就不会再生成。必须要类的安全限定名，如com.momo.test.BasePo         -->
        <property name="rootClass"
                  value="${java.rootClass}"/>
    </javaModelGenerator>


        <!-- sqlMapGenerator：配置SQL映射生成器（Mapper.xml文件）的属性，可选且最多配置1个  配置映射位置
                只有两个必选属性(和实体类的差不多)：
                targetPackage：生成映射文件存放的包名，可能会受到其他配置的影响。
                 targetProject：指定目标targetPackage的项目路径，可以用相对路径或者绝对路径        -->
        <sqlMapGenerator targetPackage="${mapper.targetPackage}" targetProject="${mapper.targetProject}"/>

        <!-- 配置接口位置
            typetype="XMLMAPPER"：接口和XML完全分离；所有方法都在XML中，接口用依赖Xml文件
            targetPackage：生成Mapper文件存放的包名，可能会受到其他配置的影响。
            targetProject：指定目标targetPackage的项目路径，可以用相对路径
             -->
        <javaClientGenerator targetPackage="${java.targetMapperPackage}" targetProject="${java.targetProject}"
                             type="XMLMAPPER"/>



        <!-- 生成用户的相关类 -->
        <table schema="${table.schema}" tableName="${table.name}" domainObjectName="${table.domain}" enableCountByExample="false" enableDeleteByExample="false"
               enableSelectByExample="false" enableUpdateByExample="false" >
            <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>
```

最终生成对应目录如下图：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210311402281.png)






### Swagger配置
- `top/testeru/autosphere/config/SwaggerConfiguration.java`
```java
package top.testeru.autosphere.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("autosphere系统")
                // 描述
                .description("autosphere接口文档")
                // 创建人信息
                .contact(new Contact("testeru", "", "testeru@testeru.com"))
                // 项目API版本号
                .version("1.0.0")
                .build();
    }


    @Bean
    public Docket docket() {
        //Swagger 的配置主要围绕Docket bean 进行：
        return new Docket(DocumentationType.OAS_30)
                //配置是否启用Swagger，如果是false，在浏览器将无法访问，默认是true
                .enable(true)
                .groupName("autosphere_interface")
                .apiInfo(apiInfo())
//                .globalRequestParameters(globalRequestParameters())
//在定义Docket bean 之后，它的select()方法返回一个ApiSelectorBuilder的实例，它提供了一种控制 Swagger 暴露的端点的方法
                .select()
                //any 任何请求都扫描  none   任何请求都不扫描
//                .apis(RequestHandlerSelectors.any())
                  .apis(RequestHandlerSelectors.basePackage("top.testeru.autosphere"))
                .paths(PathSelectors.any()).build();
//我们可以在RequestHandlerSelectors和PathSelectors的帮助下配置用于选择RequestHandler的谓词。
// 对两者都使用any()将使我们的整个 API 的文档可以通过 Swagger 获得。
    }

    //生成全局通用参数
    private List<RequestParameter> globalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();

        //   公共请求参数生成器-token
        RequestParameter tokenParameter = new RequestParameterBuilder()
                .in(ParameterType.HEADER)//在swagger里显示header
                .name("token")//header的参数名为 token
                .description("对应的token值")

                .required(true)//对应参数是否为必传，如果不是必传参数则设置为false
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)))

                .build();
        //为消费者提供帮助建立模型  更新标量类型
        //   公共请求参数生成器-udid
        RequestParameter udidParameter = new RequestParameterBuilder()
                .in(ParameterType.QUERY)//在swagger里显示header
                .name("udid")//header的参数名为 token
                .description("设备的ID")
                .required(false)//对应参数是否为必传，如果不是必传参数则设置为false
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)))
                .build();

        parameters.add(tokenParameter);
        parameters.add(udidParameter);
        return parameters;
//        Collections.singletonList(parameterBuilder.build());
    }
}
```

### 响应体
- `R<T>`
```java

```


- `ResultCode`
```java

```

### 实体类
#### UserDTO
- `src/main/java/top/testeru/autosphere/dto/UserDTO.java`
```java
package top.testeru.autosphere.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户实体类",description = "请求参数的用户实体类")
@Data
public class UserDTO {

    @ApiModelProperty(value = "用户名",example = "admin",required = true)
    private String userName;

    @ApiModelProperty(value = "密码",example = "admin",required = true)
    private String password;
    
   //邮箱
    private String email;   
}
```



### 实体类转换
- `src/main/java/top/testeru/autosphere/converter/UserConverter.java`
```java
package top.testeru.autosphere.converter;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import top.testeru.autosphere.dto.UserDTO;
import top.testeru.autosphere.entity.User;


@Mapper(componentModel = "spring")
public interface UserConverter {
//    UserDTO ---->  User

    @Mappings({
            @Mapping(target = "userName",source = "userName"),
            @Mapping(target = "password",source = "password"),
            @Mapping(target = "email",source = "email")
    })
    User userDTOForUser(UserDTO userDTO);



    @Mappings({
            @Mapping(target = "userName",source = "userName"),
            @Mapping(target = "password",source = "password"),
            @Mapping(target = "email",source = "email")
    })
    UserDTO userForUserDTO(User user);
}

```
### 数据库查询用户
#### mapper
使用`mapper`默认的`selectAll()`。
#### service
##### 接口
```java
package top.testeru.autosphere.service;

import top.testeru.autosphere.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> list();
}
```
##### 实现类
 * `@Autowired`：默认按byType装配Bean，如果发现多个类型相同的Bean，再根据byName装配Bean，如果找到了则装配成功，找不到则装配失败。
 * `@Resource`：默认按byName装配Bean，如果byName没有找到对应的Bean，再根据byType装配Bean，如果找到了则装配成功，找不到则装配失败。


```java
package top.testeru.autosphere.service.impl;

import org.springframework.stereotype.Service;
import top.testeru.autosphere.converter.UserConverter;
import top.testeru.autosphere.dao.UserMapper;
import top.testeru.autosphere.dto.UserDTO;
import top.testeru.autosphere.entity.User;
import top.testeru.autosphere.service.UserService;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserConverter userConverter;

    @Override
    public List<UserDTO> list() {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = userMapper.selectAll();
        userList.forEach(user -> {
            userDTOList.add(userConverter.userForUserDTO(user));
        });
        return userDTOList;
    }
}
```
#### controller
- LoginController
```java
package top.testeru.autosphere.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.testeru.autosphere.dto.UserDTO;
import top.testeru.autosphere.service.UserService;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {

    @Resource
    UserService userService;

    @GetMapping(value = "/toLoginPage")
    public String toLoginPage(){
        System.out.println("eeee");
        return "login";

    }

    @GetMapping(value = "/users")
    public List<UserDTO> users(){
        System.out.println("users");
        return userService.list();
    }
}
```

### MapperScan
- 启动类配置MapperScan
```
//启动类
//默认一般创建为：artifactId+Application
//类名必须以Application结尾
@SpringBootApplication
//@Configuration  + @EnableAutoConfiguration + @ComponentScan
@MapperScan("top.testeru.autosphere.dao")
public class AutosphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutosphereApplication.class, args);
    }
```


### 结束效果
准备结束，由于表中没有对应的数据，运行效果如下图：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210311537757.png)

##### [swagger](http://127.0.0.1:8080/swagger-ui/index.html)

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210311544453.png)


