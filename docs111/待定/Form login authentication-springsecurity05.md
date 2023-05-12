---
title: Form login authentication
date: 2022-10-17 15:41:48.018
updated: 2022-10-17 20:08:46.112
url: /archives/springsecurity05
categories: 
- springsecurity
tags: 
- 安全
- security
---

# 表单登录


## 表单登录认证

过滤器`UsernamePasswordAuthenticationFilter`是处理表单登录的，下面来通过源码观察下这个过滤器。

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171750676.png)
### 前端页面
```java
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>登录</title>
    <link rel="stylesheet" href="css/pintuer.css">
    <link rel="stylesheet" href="css/admin.css">
    <script src="js/jquery.js"></script>
    <script src="js/pintuer.js"></script>
</head>
<body>
<div class="bg"></div>
<div class="container">
    <div class="line bouncein">
        <div class="xs6 xm4 xs3-move xm4-move">
            <div style="height:150px;"></div>
            <div class="media media-y margin-big-bottom">
            </div>
            <form id="formLogin" action="/login" method="post">
                <div class="panel loginbox">
                    <div class="text-center margin-big padding-big-top"><h1>后台管理中心</h1></div>
                    <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                        <div class="form-group">
                            <div class="field field-icon-right">
                                <input type="text" class="input input-big" name="username" placeholder="登录账号"
                                       data-validate="required:请填写账号"/>
                                <span class="icon icon-user margin-small"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="field field-icon-right">
                                <input type="password" class="input input-big" name="password" placeholder="登录密码"
                                       data-validate="required:请填写密码"/>
                                <span class="icon icon-key margin-small"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="field">
                                <input type="text" class="input input-big" name="imageCode" placeholder="填写右侧的验证码"
                                       data-validate="required:请填写右侧的验证码"/>
                                <img src="/code/image" alt="" width="100" height="32" class="passcode"
                                     style="height:43px;cursor:pointer;" onclick="this.src=this.src+'?'">

                            </div>
                        </div>
                        <div class="form-group">
                            <div>
                                <!--记住我 name为remember-me value值可选true  yes 1 on 都行-->
                                <input type="checkbox" name="remember-me" value="true"/>记住我
                            </div>
                        </div>
<!--                        <div class="form-group">-->
<!--                            <div>-->
<!--                                <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>-->
<!--                            </div>-->
<!--                        </div>-->
                    </div>
                    <div style="padding:30px;">
                        <button type="submit" class="button button-block bg-main text-big input-big">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
```
### 新版本认证登录配置
#### 配置参数显示名
- 需要在表单认证的formLogin内进行配置
```java
formLogin
.usernameParameter("username")//默认值为 "username"
.passwordParameter("password")//默认值为 "password"
```
#### 配置自定义的登录页面
```java
formLogin
.loginPage("/toLoginPage")//自定义登录页面
```

#### 配置登录成功后跳转的路径
```java
formLogin
.successForwardUrl("/"))//登录成功后跳转的路径 index
```
#### 配置关闭csrf防护
```java
.csrf(csrfConfigurer -> csrfConfigurer.disable());//关闭csrf防护
```

#### 自定义登录的用户名密码
```java
    //自定义用户名密码
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("admin")
//                .withUsername("admin")
                .password("123456")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
```
完整代码如下：
```java
package top.testeru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                authorize -> authorize
                                .antMatchers("/toLoginPage").permitAll()//放行登录页面
                                .anyRequest().authenticated())
            //开启表单认证
            .formLogin(
                    formLogin -> formLogin
                                    .usernameParameter("username")//默认值为 "username"
                                    .passwordParameter("password")//默认值为 "password"
                                    .loginPage("/toLoginPage")//自定义登录页面
            //                        .failureUrl("/authentication/login?failed")
                                    .loginProcessingUrl("/login")// 登录处理Url 表单提交的路径
                                    .successForwardUrl("/"))//登录成功后跳转的路径 index
            .csrf(csrfConfigurer -> csrfConfigurer.disable());//关闭csrf防护
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //解决静态资源被拦截的问题
        return (web) -> web.ignoring().antMatchers("/css/**", "/images/**", "/js/**",
                "/favicon.ico");
    }
    //自定义用户名密码
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withDefaultPasswordEncoder()
                .username("admin")
//                .withUsername("admin")
                .password("123456")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
```

### 旧版本认证登录配置
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
        http.formLogin()//开启表单认证  开启formLogin认证
                .loginPage("/toLoginPage")//自定义登录页面
                .loginProcessingUrl("/login")// 登录处理Url 表单提交的路径
                .usernameParameter("user")//默认值为 "username"
                .passwordParameter("pwd")//默认值为 "password"
                .successForwardUrl("/")//登录成功后跳转的路径 index
                .and().authorizeRequests()
                .antMatchers("/toLoginPage").permitAll()//放行登录页面
                .anyRequest().authenticated();
        http.csrf().disable();
    }
}
```

### 对应关系图

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171551592.png)



![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171554412.png)

## 页面内iframe加载
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210171806008.png)

发现行内框架iframe这里出现问题了。
Spring Security下，`X-Frame-Options`默认为`DENY`。
非Spring Security环境下，`X-Frame-Options`的默认大多也是`DENY`。这种情况下，浏览器拒绝当前页面加载任何 Frame页面，设置含义如下:
### DENY
DENY：浏览器拒绝当前页面加载任何Frame页面 

此选择是默认的. 
### SAMEORIGIN
SAMEORIGIN：frame页面的地址只能为同源域名下的页面


```java
.headers(headersConfigurer -> headersConfigurer.frameOptions().sameOrigin())//加载同源域名下的iframe页面

```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210172008901.png)

## 总结
- 表单登录配置相关参数
- 使用页面内的iframe