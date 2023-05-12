---
title:  前后端登录功能实战06
date: 2022-11-07 15:20:59.805
updated: 2022-11-09 14:30:13.358
url: /archives/login06
categories: 
tags: 
---

# 
## 身份认证
### 流程
客户端/前端 发送一个带用户名密码的请求，请求的方式为POST，请求后端代码的路径为：/auth/login；
这个时候对应服务端接收到了相关的登录参数，到指定的请求路径下的方法。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211071630959.png)

如果请求成功，则对应的方法返回一个响应结果并带着生成的token参数。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211071652284.png)

后面如果客户端/前端再发送其他的请求给后端，在请求头中带着对应token则完成对应权限验证；如果token验证成功，则正常获取请求的响应结果。

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211071700243.png)

## 技术
完成一个登录功能的技术可能涉及到以下：
- Spring Web：请求路径
- Mybatis：数据连接相关
- Spring Security：Spring的安全框架
- JWT(jjwt)：JWT生成token
- MySQL JDBC driver

## JWT
### 介绍

服务器通过账户、密码与数据库匹配认证通过以后，会生成一个 JSON 对象，发回给用户，如下：
```
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022
}
```
后面用户与服务端发送的请求，都要带着这个 JSON 对象。一般该对象会组成一个字符串，放在请求头中。

服务器完全靠请求头的信息进行用户身份权限的认定，同时，为了防止用户篡改数据，服务器在生成这个对象的时候，会加上签名，这个签名是当前服务器唯一的一个签名。

通过该认证，服务器不保存任何 session 数据。即，服务器变成无状态了，这样对服务器而言，比较容易实现扩展。

### 数据结构
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

可以看到JWT是一个很长的字符串，整个字符串中间用 **点**（`.`）分隔成三个部分。
##### 注意
JWT 内部是没有换行的
#### 组成 
JWT 的三个部分依次如下：

- `Header`（**头部**）
- `Payload`（**负载**）
- `Signature`（**签名**）

```
Header.Payload.Signature
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211081115981.png)


##### Header
```
{
  "alg": "HS256",
  "typ": "JWT"
}
```
- 是一个 `JSON` 对象
- 描述 JWT 的元数据
- `alg`：表示 签名的算法（`algorithm`），默认是 `HMAC SHA256`（写成 `HS256`）；
- `typ`：表示 这个令牌（`token`）的类型（`type`），JWT 令牌统一写为`JWT`。

最后， JSON 对象使用 Base64URL 算法转成字符串：
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

##### Payload

```
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```
- 是一个 JSON 对象
- 存放实际需要传递的数据
- JWT 规定了7个官方字段进行选用：
	- `iss` (issuer)：**签发人**
    - `exp` (expiration time)：**过期时间**
    - `sub` (subject)：**主题**
    - `aud` (audience)：**受众**
    - `nbf` (Not Before)：**生效时间**
    - `iat` (Issued At)：**签发时间**
    - `jti` (JWT ID)：**编号**
除了官方字段，你还可以在这个部分定义私有字段。

##### 注意
JWT 默认是不加密的，任何人都可以读到，所以不要把秘密信息放在这个部分。

JSON 对象使用 Base64URL 算法转成字符串：
```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ
```

##### Signature

```
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```
- 对前两部分的签名，防止数据篡改

步骤：
- 服务端指定一个密钥（secret）。
	- 这个密钥只有服务器才知道，不能泄露给用户。
- 使用 Header 里面指定的签名算法（默认是 `HMAC SHA256`），按照公式产生签名


>算出签名以后，把 Header、Payload、Signature 三个部分拼成一个字符串，每个部分之间用"点"（.）分隔，就可以返回给用户。

### 使用方式
常见的两种方式：

客户端收到服务器返回的 JWT
- 储存在 `Cookie` 里面
- 储存在 `localStorage`

此后，客户端每次与服务器通信，都要带上这个 JWT。

你可以把它放在 **Cookie 里面自动发送**，但是这样**不能跨域**，所以更好的做法是放在 HTTP 请求的头信息`Authorization`字段里面。

```
Authorization: Bearer <token>
```

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211081418272.png)


## 流程

客户端发送请求到服务器上真实的请求，需要通过一系列的过滤器；
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211081424106.png)

并且Spring Web框架会有一个FilterChainProxy，里面会有一系列的过滤器，这些过滤器主要是Spring Security框架提供的，里面有15个细分功能的过滤器。每一个过滤器都负责应用中特定的安全功能。

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211081432874.png)


在Spring Security框架的过滤器中，需要根据服务端的业务需求插入自定义的过滤器，比如：JWT Token Filter
### JWT Filter
自定义的 JWT Token Filter 会检查发送过来的请求是否可用，并且对应的请求携带的令牌是否完整。

如果验证通过，则直接方法对应请求路径的方法；如果验证没有通过，则直接返回无权限错误码。


## Security
- 对应介绍看 Security 专栏


```
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //跨域问题解决
            .cors(withDefaults())
            .csrf().disable()
     .and()
            //  Session 创建为无状态的
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .anyRequest().permitAll()//允许所有请求通过
        ;
    }
}
```

- 以上为最基本的配置，这样我们所有的请求都又可以直接访问
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211082007301.png)


### 创建用户登录接口
- /auth/login

#### service
- 接口
```java
public interface UserService {
    Optional<UserDto> selectByName(String UserName);
}
```
- 实现类

```java
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    UserConverter userConverter;
    @Override
    public Optional<UserDto> selectByName(String userName) {
        User user = new User();
        user.setUsername(userName);
        //从数据库中查找
        User findBySqlUser = userMapper.selectOne(user);
        UserDto userDto = userConverter.userForUserDto(findBySqlUser);
        return Optional.of(userDto);
    }
}
```






- SecurityConfig
```java
    @Resource
    CustomUserDetailService customUserDetailService;
    //身份验证管理器 -- 配置用户登录信息，就是前面自动注入的service类
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }
```

- CustomUserDetailService
```java
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Resource
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.selectByName(username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "没有登录权限"));
        System.out.println(user);
        if(null == user){
            throw new UsernameNotFoundException("用户没有找到" + username);// 用户名没有找到
        }
        // 先声明一个权限集合, 因为构造方法里面不能传入null
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(username,
                        "{bcrypt}" + user.getPassword(),//不使用密码加密 noop代表不加密
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

以上是用户登录的验证，那么通过访问哪个接口进行该内容的验证呢？

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211091105622.png)

### 登录请求

#### AuthReqestDto
接口传递的请求登录的实体类

```java
public class AuthReqestDto {
    private String username;

    private String password;
}
```
#### AuthResponseDto
登录成功后对应接口返回的实体类
```java
public class AuthResponseDto {
    private static final long serialVersionUID = -8752513311904244663L;
    private String name;

    private  String accessToken;
}
```
#### AuthController
身份验证的请求api

- SecurityConfig

```java
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
```

需要对传递的 Authentication 对象进行身份验证，如果成功，则返回完全填充的 Authentication 对象（包括授予的权限）
- AuthController
```java
@RestController
public class AuthController {

    @Resource
    AuthenticationManager authenticationManager;
    
    @Resource
    UserService userService;

    @PostMapping("/auth/login")
    public R login( @RequestBody AuthReqestDto authReqestDto){
        try {
            //返回一个身份认证对象
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReqestDto.getUsername(), authReqestDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            UserDto user1 = userService.selectByName(user.getUsername()).get();
            String accessToken = "jwt access token";
            AuthResponseDto authResponseDto = new AuthResponseDto(user.getUsername(), accessToken);
            return R.ok().token(authResponseDto.getAccessToken()).message("登录成功");

        }catch (BadCredentialsException ex){
            //401 未授权
           return R.error().code(401).message("授权失败");
        }
    }
}
```

- 规则配置：


```java
.and()
            .authorizeRequests()
//                .anyRequest().permitAll()//允许所有请求通过
                .antMatchers(
                        HttpMethod.POST,
                        List.of(ApiPathExclusion.PostApiPathExclusion.values()).stream()
                                .map(apiPath -> apiPath.getPath()).toArray(String[]::new))
                .permitAll()
                .anyRequest().authenticated()

```
## JWT

```java
package top.testeru.qasphere.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import top.testeru.qasphere.dto.UserDto;
import top.testeru.qasphere.entity.User;

import java.security.Key;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;

@Component
public class JWTUtils {
	static final Logger logger = getLogger(lookup().lookupClass());
	
	public static String TOKEN_ISSUER = "qasphere";

	private static String BASE_SECRET_STRING = "asdfghjkl";

	private final Long EXPIRE_DURATION = TimeUnit.HOURS.toMillis(2); //token过期时间


	public String generateAccessToken(UserDto user){
		final Claims claims = new DefaultClaims();
		claims.put("user_id", user.getId());
//		claims.put("account_creation_timestamp",
//				user.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		claims.put("ename", user.getEmail());
		claims.put("password", user.getPassword() + BASE_SECRET_STRING);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(TOKEN_ISSUER)
				.setSubject(user.getId() + "," + user.getUsername())
//				.setAudience("localhost")
				.setIssuedAt(new Date(System.currentTimeMillis() + EXPIRE_DURATION))//过期时间
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS256, BASE_SECRET_STRING)
				.compact();
	}


	public boolean validateAccessToken(final String token) {
		try {
			Jwts
				.parser()
				.setSigningKey(BASE_SECRET_STRING)
				.parseClaimsJws(token)
				.getBody();
			return true;
		}catch (ExpiredJwtException e){
			logger.error("JWT解析异常：", e);
		}catch (IllegalArgumentException e){
			logger.error("token 为空，null，或空白：",e);
		}catch (MalformedJwtException e){
			logger.error("JWT令牌无效：",e);
		}catch (UnsupportedJwtException e){
			logger.error("不支持JWT令牌：",e);
		}catch (SignatureException e){
			logger.error("令牌签名无效：",e);
		}
		return false;
	}

	public String getSubject(String token){
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(BASE_SECRET_STRING)
				.parseClaimsJws(token)
				.getBody();
	}
}
```

- 响应
```java
        http
            //跨域问题解决
            .cors(withDefaults())
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
            })
```
### JWT Filter


![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202211091414921.png)

- OncePerRequestFilter：每次的请求都进行过滤
```java
@Component
public class JWTTokenFilter extends OncePerRequestFilter {

    //重写实际进行过滤操作的doFilterInternal抽象方法
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    
        //尝试获取TOKEN
        
        String header = req.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        System.out.println("token:"+token);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
}
```


- security 框架进行配置

```
 .and()
                .addFilterAt(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        ;
```







token解析
- JWTUtils
```
public boolean validateAccessToken(final String token) {
		try {
			Jwts
				.parser()
				.setSigningKey(BASE_SECRET_STRING)
				.parseClaimsJws(token)
				.getBody();
			return true;
		}catch (ExpiredJwtException e){
			logger.error("JWT解析异常：", e);
		}catch (IllegalArgumentException e){
			logger.error("token 为空，null，或空白：",e);
		}catch (MalformedJwtException e){
			logger.error("JWT令牌无效：",e);
		}catch (UnsupportedJwtException e){
			logger.error("不支持JWT令牌：",e);
		}catch (SignatureException e){
			logger.error("令牌签名无效：",e);
		}
		return false;
	}
    public String getSubject(String token){
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(BASE_SECRET_STRING)
				.parseClaimsJws(token)
				.getBody();
	}
```



- 判断是否有请求头
```java

@Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        //尝试获取TOKEN
        //如果没有请求头，则直接跳转
       if(!hasAuthorizationHeader(httpServletRequest)){
           filterChain.doFilter(httpServletRequest, httpServletResponse);
           return;

       }
}
private boolean hasAuthorizationHeader(HttpServletRequest req){
        String header = req.getHeader("Authorization");
        System.out.println("Authorization-header:" + header);

        if ((ObjectUtils.isEmpty(header)) || !header.startsWith("Bearer "))
            return false;
        return true;
    }
}
```


```java
private String getAccessToken(HttpServletRequest req){
        String header = req.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        System.out.println("token:"+token);
        return token;
    }
```


```
private void setAuthorizationContext(String accessToken, HttpServletRequest httpServletRequest) {
        User userDetails = getUserDetails(accessToken);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, null);
        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    private User getUserDetails(String accessToken) {
        User user = new User();
        String[] subjectArray = jwtUtils.getSubject(accessToken).split(",");
        user.setId(Integer.parseInt(subjectArray[0]));
        user.setUsername(subjectArray[1]);
        return user;

    }
```


