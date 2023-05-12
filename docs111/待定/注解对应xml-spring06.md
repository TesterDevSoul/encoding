---
title: 注解对应xml
date: 2022-09-03 20:31:05.248
updated: 2022-09-03 20:31:05.248
url: /archives/spring06
categories: 
- spring
tags: 
- spring
---


## 注解对应xml

### @Configuration
- 标识当前类是`Spring`的⼀个配置类
### @ComponentScan
- 替代`xml`中的 `<context:component-scan/>`
### @Import
- 引⼊其他配置类,被引⼊的配置类可以不加`@Configuration`注解
### @PropertySource
- 引⼊外部`properties`⽂件，注意加`classpath`
### @Value
- 对成员变量赋值
### @Bean
- 将⼀个⽅法的返回值对象加⼊到`Spring`的容器当中管理
### @Qualifier
- 可以使⽤在⽅法参数上，表明对应的形参引⼊/注⼊的对象类型




![](https://cdn.jsdelivr.net/gh/testeru-top/images/spring/202205241618543.png)
### 配置修改
#### service包扫描
- [i] xml配置方式
```xml
<context:component-scan base-package="top.testeru.service"></context:component-scan>
```
- [p] 对应注解方式
```java
//@ComponentScan(basePackages = {"top.testeru.service"})
@ComponentScan("top.testeru.service")
public class SpringConfig {}
```
#### 配置数据源
- [i] xml配置方式
```xml
 <!--配置数据源-->
<bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <!--驱动, url, ⽤户名,密码-->
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

- [p] 对应注解方式
```java
@ComponentScan(basePackages = {"top.testeru.service"})
//@ComponentScan("top.testeru.service")
public class SpringConfig {

    @Bean("dataSource")
    public DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://110.40.250.165:3307/ssm?characterEncoding=UTF-8&useUnicode=true");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("luoluo606709");
        return dataSource;
    }
}
```


#### 配置工厂bean
- [i] xml配置方式
```xml
<bean id="sqlSessionFactory"
          class="org.mybatis.spring.SqlSessionFactoryBean">
    <!--关联数据源-->
    <property name="dataSource" ref="druidDataSource"/>
    <!--设置pojo的别名-->
    <property name="typeAliasesPackage" value="top.testeru.pojo"/>
</bean>
```

- [p] 对应注解方式
```java
@ComponentScan(basePackages = {"top.testeru.service"})
//@ComponentScan("top.testeru.service")
public class SpringConfig {
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(
            @Qualifier("dataSource") DataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("top.testeru.pojo");
        return factoryBean;
    }
}
```

#### 配置Mapper映射扫描
- [i] xml配置方式
```xml
<bean id="scannerConfigurer"
          class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <!--可省略-->
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    <!--dao映射扫描-->
    <property name="basePackage" value="top.testeru.dao"/>
</bean>
```
- [p] 对应注解方式
```java
@ComponentScan(basePackages = {"top.testeru.service"})
//@ComponentScan("top.testeru.service")
public class SpringConfig {
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("top.testeru.dao");
        return configurer;
    }
}
```


### 测试
```java
@ContextConfiguration(classes = SpringConfig.class)
class AccountServiceTest {}
```

### 优化
- 多个配置文件，最后汇总到`SpringConfig`内
#### SpringConfig

```java
@Configuration//作为Spring框架的主配置⽂件
//@ComponentScan(basePackages = {"top.testeru.service"})
@ComponentScan("top.testeru.service")//开启Spring容器的注解扫描
@Import({JdbcConfig.class,BeanConfig.class})//导⼊⼦配置⽂件
public class SpringConfig {
}
```

#### JdbcConfig

```java
@PropertySource("classpath:jdbc.properties")//⽤于指定与数据库相关配置的配置⽂件
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;


    @Bean("dataSource")
    public DruidDataSource getDruidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
```


#### BeanConfig
```java
//⽤于配置与Mybatis相关的配置
public class BeanConfig {
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(
            @Qualifier("dataSource") DataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //配置相关信息(数据源)
        factoryBean.setDataSource(dataSource);
        //配置相关信息(pojo别名映射)
        factoryBean.setTypeAliasesPackage("top.testeru.pojo");
        return factoryBean;
    }
//    配置Mapper映射扫描, 扫描所有的Mapper.xml⽂件与 ⽣成dao接⼝的实现类对象存到IOC容器中
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("top.testeru.dao");
        return configurer;
    }
}
```