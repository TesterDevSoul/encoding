---
title: Custom form login page
date: 2022-10-17 14:46:50.596
updated: 2022-10-17 15:38:34.263
url: /archives/springsecurity04
categories: 
- springsecurity
tags: 
- 安全
- security
---

#  自定义表单登录页面
## 本章要点
- [ ] formLogin认证
- [ ] 静态资源过滤
- [ ] HttpSecurity、WebSecurity区别
## 认证登录配置
### 新版本认证登录配置
#### formLogin认证
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171430948.png)

```java
package top.testeru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin()
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().build();
          
    }
}

```


### 旧版本认证登录配置
#### httpbasic认证
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171449331.png)
##### 对应代码
```java
http
.httpBasic()//开启httpbasic认证，需要选择认证方式，如果不选择默认为formLogin认证 
.and().authorizeRequests()//添加认证请求
.anyRequest().authenticated();//所有请求都需要认证才能访问
```

完整代码如下：
```java
package top.testeru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * http请求
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()//开启httpbasic认证，需要选择认证方式，如果不选择默认为formLogin认证 
                .and().authorizeRequests()
                .anyRequest().authenticated();//所有请求都需要认证才能访问
    }
}

```

#### formLogin认证
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171430948.png)
##### 对应代码

```java
http
.formLogin()//开启formLogin认证
.and().authorizeRequests()//添加认证请求
.anyRequest().authenticated();//所有请求都需要认证才能访问
```

完整代码如下：

```java
package top.testeru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * http请求
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()//开启formLogin认证
                .and().authorizeRequests()
                .anyRequest().authenticated();//所有请求都需要认证才能访问
    }
}
```

## 认证配置登录页面

```java
package top.testeru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin().loginPage("/login.html")
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().build();
    }
}
```
访问登录页面，对应的报错：
#### 问题一: 重定向过多
问题一: localhost将您重定向次数过多
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171512030.png)

```js
重定向次数过多
```

>因为设置登录页面为login.html 后面配置的是所有请求都登录认证,陷入了死循环. 所以需要将 login.html放行不需要登录认证

```java
http.formLogin().loginPage("/login.html")
     .and().authorizeRequests()
     .antMatchers("/login.html").permitAll()  //放行登录页面
     .anyRequest().authenticated()
     .and().build();
```

#### 问题二: 404
问题二: 访问login.html 报404错误

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171515833.png)

>spring boot整合thymeleaf 之后 所有的静态页面以放在 `resources/templates`下面,所以得通过请求访 问到模板页面, 将`/login.html`修改为`/toLoginPage`



#### 问题三:静态资源被拦截
问题三: 访问login.html 后发现页面没有相关样式

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171520056.png)

>因为访问login.html需要一些js , css , image等静态资源信息, 所以需要将静态资源放行, 不需要认证

```java
package top.testeru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin().loginPage("/toLoginPage")
                .and().authorizeRequests()
                .antMatchers("/toLoginPage").permitAll()//放行登录页面
                .anyRequest().authenticated()
                .and().build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //解决静态资源被拦截的问题
        return (web) -> web.ignoring().antMatchers("/css/**", "/images/**", "/js/**",
                "/favicon.ico");
    }
}
```


旧版本对应代码：
```java
package top.testeru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Configuration
public class SecurityOldConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**",
                "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.formLogin().loginPage("/toLoginPage")//开启formLogin认证
                .and().authorizeRequests()
                .antMatchers("/toLoginPage").permitAll()//放行登录页面
                .anyRequest().authenticated();//所有请求都需要认证才能访问

    }
}
```
## HttpSecurity、WebSecurity区别
Spring Security 中，安全构建器 HttpSecurity 和 WebSecurity 的区别是 :

1. WebSecurity 不仅通过 HttpSecurity 定义某些请求的安全控制，也通过其他方式定义其他某请求可以忽略安全控制;

2. HttpSecurity 仅用于定义需要安全控制的请求(当然 HttpSecurity 也可以指定某些请求不需要安全控制);

3. 可以认为 HttpSecurity 是 WebSecurity 的一部分， WebSecurity 是包含 HttpSecurity 的更大的一个概念;

4. 构建目标不同 
- WebSecurity 构建目标是整个 Spring Security 安全过滤器 FilterChainProxy
- HttpSecurity 的构建目标仅仅是 FilterChainProxy 中的一个 SecurityFilterChain


## 总结
- formLogin认证，新版本
- 静态资源过滤：(web)流
- WebSecurity包含HttpSecurity