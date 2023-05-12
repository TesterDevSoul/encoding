---
title:  前后端登录功能实战05
date: 2022-10-31 15:59:19.083
updated: 2022-11-02 10:24:19.981
url: /archives/login05
categories: 
tags: 
---

## SpringSecurity
### 添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

#### 启动

这时访问 [user](http://127.0.0.1:8080/users) 会自动跳转到登录页面，这个登录页面是 `SpringSecurity` 框架自己独有的。


### 自定义用户名、密码
每次启动都需要输入控制台的密码，很麻烦，自定义一个简单的密码进行登录
```
  security:
    user:
      name: user
      password: 123456
```



### form表单配置
- form表单提交请求
```java
package top.testeru.autosphere.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        authorize -> authorize
                                .antMatchers("/login").permitAll()//放行登录页面
                                .anyRequest().authenticated()
                )
                //开启表单认证
                .formLogin(
                        formLogin -> formLogin
                                .usernameParameter("username")//默认值为 "username"
                                .passwordParameter("password")//默认值为 "password"
                )
        ;
        return http.build();
    }
}

```


#### 定义登录后跳转路径页面
```java
@RestController
public class UserController {

    @Resource
    UserService userService;
    @GetMapping(value = "/")
    public String index(){
        System.out.println("首页");
        return "首页";
    }
    @GetMapping(value = "/users")
    public List<UserDTO> users(){
        System.out.println("users");
        return userService.list();
    }
}
```

输入地址后跳转到登录页面，输入账户、密码，成功后跳转到首页。

### 基于数据库认证 

[数据库认证 ](https://www.testeru.top/archives/springsecurity06)

单一用户配置在配置文件中，如果多个用户，这时，我们就需要使用数据库进行用户的保存。

#### service
从数据库查找对应用户
- 接口：UserService
```java
public interface UserService {

    UserDTO findByName(User user);
    List<UserDTO> list();
}
```
- 实现类：UserServiceImpl

```java
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserConverter userConverter;

    @Override
    public UserDTO findByName(User user) {
        User user1 = userMapper.selectOne(user);
        return userConverter.userForUserDTO(user1);
    }

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

- 自定义：MyUserDetailsServiceImpl
```java
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userParm = new User();
        userParm.setUsername(username);
        UserDTO user = userService.findByName(userParm);
        System.out.println(user);
        if(null == user){
            throw new UsernameNotFoundException("用户没有找到" + username);// 用户名没有找到
        }
        // 先声明一个权限集合, 因为构造方法里面不能传入null
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

//        String username, String password, boolean enabled, boolean accountNonExpired,
//        boolean credentialsNonExpired, boolean accountNonLocked,
        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(username,
                        "{noop}" + user.getPassword(),//不使用密码加密 noop代表不加密
                        true,//enabled,用户是否启用
                        true,//accountNonExpired,用户是否过期
                        true, //credentialsNonExpired,用户凭证是否过期
                        true,//accountNonLocked,用户是否锁定 true 代表未锁定
                        authorities);

        //代码框架自动拿前端传入的密码和user对象里面的密码进行匹配，框架自动完成；
        //如果密码一样，登录成功；如果密码不一致，则登录失败
        return userDetails;
    }
}

```
#### Security配置
- SecurityConfig

```
    @Resource
    MyUserDetailsServiceImpl myUserDetailsService;

    //身份验证管理器
    @Resource
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);// 使用自定义用户认证
    }

```





### 自定义表单登录页面


#### 前端更新配置
- 更改后端服务地址：`src/api/http.js`
```
baseURL : 'http://localhost:8080',
```

- 添加请求头为 `form` 表单
```js
import axios from "./http";

const user = {
    login(data){
        return axios({
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },  
            method: 'POST',
            url: '/login',
            data: data
        })
    }
}

export default user
```

- 账号添加name

```
name="username"
```

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211011058948.png)

#### CORS错误
启动后端服务，同时启动前端服务，前端访问首页，对应跳转到前端的登录页面。
此时输入用户名：user；密码为：123456，此时点击登录，对应控制台报错：
```
Access to XMLHttpRequest at 'http://127.0.0.1:8080/login' from origin 'http://localhost:8081' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

跨域问题，一般后端程序进行解决
### 跨域解决
请求端口号不同导致的跨域问题。
#### CORS解决跨域
CORS是一个W3C标准，全称是"跨域资源共享"(Cross-origin resource sharing)。CORS需要 浏览器和服务器同时支持。

目前，所有浏览器都支持该功能，IE浏览器不能低于IE10。浏览器在发 起真正的请求之前，会发起一个OPTIONS类型的预检请求，用于请求服务器是否允许跨域，在得 到许可的情况下才会发起请求。   

#### 跨域配置文件
```java
package top.testeru.autosphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author testeru.top
 * @version 1.0.0
 * @Project autosphere
 * @Description
 * @createTime 2022年11月01日 11:23:00
 */
@Configuration
public class CorsConfig {

    //跨域处理 跨域信息源
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        System.out.println("跨域配置");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // 允许cookies跨域
        //允许跨域的站点
        corsConfiguration.addAllowedOriginPattern("*");
        //允许跨域的http方法  允许提交请求的方法，*表示全部允许
        corsConfiguration.setAllowedMethods(Arrays.asList("OPTIONS","GET","POST"));
        corsConfiguration.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许

        //允许跨域的请求头
        corsConfiguration.addAllowedHeader("*");
        //允许带凭证
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }
}
```
#### 引用
[security-cors](https://docs.spring.io/spring-security/reference/5.7.4/servlet/integrations/cors.html)
```
 .cors(withDefaults())
```


![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211011353213.png)



### 自定义成功、失败逻辑
在某些场景下,用户登录成功或失败的情况下用户需要执行一些后续操作,比如登录日志的搜集, 或者在现在目前前后端分离的情况下用户登录成功和失败后需要给前台页面返回对应的错误信息, 有前台主导 登录成功或者失败的页面跳转. 

这个时候需要要到用到AuthenticationSuccessHandler与 AnthenticationFailureHandler.

**自定义成功处理:**
- 实现AuthenticationSuccessHandler接口，并重写onAnthenticationSuccesss()方法.

**自定义失败处理:**
- 实现AuthenticationFailureHandler接口，并重写onAuthenticationFailure()方法;


```java 
package top.testeru.autosphere.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@Service
// 继承实现类
// public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
// 实现接口
public class MyAuthenticationServiceImpl implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        System.out.println("登录成功");
        // 获取前端传到后端的全部参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paraName = parameterNames.nextElement();
            System.out.println("参数- " + paraName + " : " + request.getParameter(paraName));
        }
        // 这里写登录成功后的逻辑，可以验证其他信息，如验证码等。
        response.setContentType("application/json;charset=utf-8");
        String token = "{\"code\":0,\"msg\":\"login success\",\"token\":\"safd\"}";
        System.out.println(token);
        // 返回响应信息
		response.getWriter().write(token);
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("登录失败");
        // 获取前端传到后端的全部参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paraName = parameterNames.nextElement();
            System.out.println("登录失败参数- " + paraName + " : " + request.getParameter(paraName));
        }
    }
}
```
