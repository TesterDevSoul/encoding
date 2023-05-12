---
title: Get Logger User
date: 2022-10-18 14:17:00.988
updated: 2022-10-18 14:41:53.533
url: /archives/springsecurity07
categories: 
- springsecurity
tags: 
- 安全
- security
---

# 获取当前登录用户
## 本章要点

## 获取登录用户信息


在传统web系统中, 我们将登录成功的用户放入session中，在需要的时候可以从session中获取用户。

那么Spring Security中我们如何获取当前已经登录的用户呢?

#### SecurityContextHolder 
保留系统当前的安全上下文`SecurityContext`，其中就包括当前使用系统的用户的信息。 
#### SecurityContext 
安全上下文,获取当前经过身份验证的主体或身份验证请求令牌

### 实现方式
有三种方式实现：
- `getCurrentUser()`
- `getCurrentUser2(Authentication authentication)`
- `getCurrentUser3(@AuthenticationPrincipal UserDetails userDetails)`

```java
package top.testeru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.testeru.domain.User;
import top.testeru.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/loginUser")
    //以json格式返回
    @ResponseBody
    public UserDetails getCurrentUser(){
        //强转获取用户信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()//获取当前的 SecurityContext
                .getAuthentication()//获取当前经过身份验证的主体，或身份验证请求令牌
                //获取认证信息。被认证的主体的身份。在使用用户名和密码的身份验证请求的情况下，这将是用户名。调用者应填充身份验证请求的主体
                .getPrincipal();
        //获取上下文，获取认证信息，再获取登录对象
        return userDetails;
    }
    @GetMapping("/loginUser2")
    //以json格式返回
    @ResponseBody
    public UserDetails getCurrentUser2(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }
    @GetMapping("/loginUser3")
    //以json格式返回
    @ResponseBody
    public UserDetails getCurrentUser3(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
}
```


![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210181416646.png)


![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210181437596.png)
## 总结
