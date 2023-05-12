---
title:  前后端登录功能实战03
date: 2022-10-26 16:43:36.232
updated: 2022-10-27 19:29:59.617
url: /archives/login03
categories: 
tags: 
---


## 接口文档
[登录接口文档](https://ceshiren.com/t/topic/22615)
[业务接口文档](https://ceshiren.com/t/topic/22615/2)
[全局错误码](https://ceshiren.com/t/topic/22615/4)
[线上接口服务](https://testplatform.hogwarts.ceshiren.com/)：https://testplatform.hogwarts.ceshiren.com/


既然需要编写登录的请求接口方法，对应我们需要有相关的后端接口服务才可以，但是目前我们没有开发后端服务，所以，先给大家提供一个线上的登录服务进行前端的编写调试。

首先我们看一下对应线上后端服务提供的swagger文档，在这里为了让大家更好的理解登录接口，还给大家准备了详细的接口文档，可以一起来看下：

对应页面有登录接口、业务接口中传参的具体方式，还有自定义错误码的对照表。


### 操作步骤
接口请求的相关步骤：
1. 在 `http.js`文件内修改baseURL地址。后端服务要指定到线上服务
2. 在 api 目录下创建`user.js`，声明对应的登录请求的方式：POST请求、路径是/login、传递参数为data
3. 在 `api.js`文件内引入对应登录请求的api，`api.js`是所有业务接口的入口，相当于业务目录
4. 在 `Login.vue` 文件内编写 click 的`login()`方法，对应login()方法调用js里面的api的login请求，携带着参数传递给后端。

#### user.js
- api/user.js
- 编写对应的登录接口的规则：
	- 请求方法method
	- 请求路径url
	- 请求参数data
- 导出 
```
import axios from "./http";

const user = {
    login(data){
        return axios({
            method: "POST",
            url: "/login",
            data: data
        })
    }
}
export default user
```

#### api.js
编写完对应的user.js后记得保存，在api.js里面进行引入，只有导入，才能把user放在api内
放在api.js后，才可以在vue文件内，使用$.api进行调用。
```js
//所有接口的入口，相当于目录
import testcase from './testcase'
import user from './user'


const api = {
    testcase,
    user
}

export default api
```

以上登录的接口已经准备完成。下面需要在登录页面中发送对应登录请求，即完成登录按钮的login方法的具体实现。


#### login()
```html
 methods: {
      login () {
        //登录请求携带json格式请求body体
        this.$api.user.login(
          {
            "username":"lily",
            "password":"123456"
          }
        ).then((result) => {
          //打印响应
          console.log(result)
        }).catch((err) => {
          console.log(err)

        });
      },
      register () {
        
      }
    },
```

- 拿到页面对应的数据，展示：

```
<template>
  <v-app id="login">
    <v-main>
        <v-container class="login">
            <v-row>
                <!-- xs:超小；sm:小；md:中等；lg:大 -->
                <v-col cols="12" sm="8">
                    <v-form
                        ref="form"
                        v-model="valid"
                        lazy-validation
                    >
                        <h1>登录</h1>
                        <!--  -->
                        <v-text-field
                        v-model="username"
                        :counter="10"
                        :rules="nameRules"
                        label="账号"
                        required
                        ></v-text-field>

                        <v-text-field
                            v-model="password"
                            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                            :rules="passwordRules"
                            :type="show1 ? 'text' : 'password'"
                            name="password"
                            label="密码"
                            hint="至少3个字符"
                            counter
                            @click:append="show1 = !show1"
                        ></v-text-field>

                       


                        <v-btn
                        :disabled="!valid"
                        color="success"
                        class="mr-4"
                        @click="login"
                        >
                        登录
                        </v-btn>

                        <v-btn
                        color="primary"
                        class="mr-4"
                        @click="register"
                        >
                        注册
                        </v-btn>

                       
                    </v-form>


                </v-col>
            </v-row>
        </v-container>
    </v-main>
    

  </v-app>
</template>

<script>
  export default {
    data: () => ({
      valid: true,
      show1: false,
      username: '',
      nameRules: [
        v => !!v || '账号不能为空',
        v => (v && v.length <= 10) || '您输入的账号格式不正确',
      ],
      password: '',
      passwordRules: [
        v => !!v || '密码不能为空',
        v => (v && v.length >= 3) || '您输入的密码格式有误',
      ],
    
    }),

    methods: {
      login () {
        //登录请求携带json格式请求body体
        this.$api.user.login(
          {
            "username": this.username,
            "password": this.password
          }
        ).then((result) => {
          //打印响应
          console.log(result)
        }).catch((err) => {
          console.log(err)

        });
      },
      register () {
        
      }
    },
  }
</script>
<style scoped>
.login {
  width: 500px;
  margin: auto;
  text-align: center;
}
</style>
```

## 状态码
此时前端页面正常往后端发送请求。
继续往下看，发现，对应的不同的业务码代表不同的含义。
比如：40013	user is not register	用户未注册
当我们在账户输入一个没有的用户名时，对应的返回数据包含40013该状态码，但是http请求的响应是200

这个时候，一定要分清什么是请求协议的状态码，什么是业务的状态码。
请求协议的状态码就是200，502这些；业务的状态码是公司内部根据业务不同，开发之间自定义的。


所以想要判断业务状态码对应提示内容时，需要首先对请求响应码进行判断
如果请求的响应码不是200，则对应的业务状态码也无需进行判断了。

### 响应状态码
```
.then((result) => {
  //打印响应
  console.log(result)
  //获取响应状态码
  console.log(result.status)
  if(200 == result.status){
    console.log("请求成功，开始判断业务码")
  }else{
    //弹窗提示登录失败
    window.alert("登录失败")
  }
```
### 业务状态码
如果登录获取登录的业务状态码
#### 登录成功
- 登录成功打印token信息
```
if(200 == result.status){
  console.log("请求成功，开始判断业务码")
  console.log(result.data.code)
  if(0 == result.data.code){
    //登录成功，打印token信息
    console.log(result.data.token)
  }
}else{
  //弹窗提示登录失败
  window.alert("登录失败")
}
```




#### 登录失败


##### 40013
```js
if(200 == result.status){
  console.log("请求成功，开始判断业务码")
  console.log(result.data.code)
  if(0 == result.data.code){
    //登录成功，打印token信息
    console.log(result.data.token)
  }else if(40013 == result.data.code){
    //弹窗提示
    window.alert("该用户未注册")
  }else if(40014 == result.data.code){
    //弹窗提示
    window.alert("密码错误")
  }else{
    //弹窗提示
    window.alert("登录失败")
  }
}else{
  //弹窗提示登录失败
  window.alert("登录失败")
}
```

#### token处理

正常登录成功之后，会把token保存在头信息中，并且页面进行跳转，跳转到登录成功后的系统首页
对应步骤为：

##### 保存token
- token保存到 localStorage

##### 页面跳转
- 登录成功后页面跳转到


```
 //登录成功，打印token信息
console.log(result.data.token)
//1. token保存到 localStorage
localStorage.setItem('token',result.data.token)
//2.页面跳转到首页
this.$router.push('/layout/case')
```

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210271650974.png)
结论：
登录信息保存在本地token内。

### 路由守卫
`src/router/index.js`
- 除了登录路由，其余路由都需要先验证是否有 token 信息
- 没有 token 信息时，强制进入登录页面


#### beforeeach
- 前置守卫，在每一次路由跳转之前都去进行一次守卫的方法判断。
- to：即将要进入的路由对象
- from：当前导航正好离开的路由路径
- next：继续进行的原来的流程，next()方法为空则代表原来路由去哪继续去即可。若next()方法里面加上了对应路由，则代表当前页面要跳转到next方法路由中。

```js
//路由守卫
router.beforeEach((to, from, next) =>{
  //1.是否去登录
  if('/login' == to.path){
    next();
  }else{
    //2.如果去的其他页面，则需要验证是否登录
    let token = localStorage.getItem('token');
    //3.如果token为空则代表没有登录过
    if(null == token || '' == token){
      //4.页面跳转到登录页面
      next('/login');
    }else{
      //登录过
      next();
    }
  }
})      
```
> 未保存之前，前端页面访问testcase页面，可以访问报错401。
   保存之后，清空token，访问testcase页面，页面跳转到登录页面。


### 401
- 对应 页面跳转正常，但是在测试用例界面报错

- 401：没有授权，未进行表明身份

>每次请求其他的路径都需要先判断是否登录成功，即对应token是否存在
```js
{
    "message": "Request failed with status code 401",
    "name": "AxiosError",
    "stack": "AxiosError: Request failed with status code 401\n    at settle (webpack-internal:///./node_modules/axios/lib/core/settle.js:24:12)\n    at XMLHttpRequest.onloadend (webpack-internal:///./node_modules/axios/lib/adapters/xhr.js:117:66)",
    "config": {
        "transitional": {
            "silentJSONParsing": true,
            "forcedJSONParsing": true,
            "clarifyTimeoutError": false
        },
        "transformRequest": [
            null
        ],
        "transformResponse": [
            null
        ],
        "timeout": 2500,
        "xsrfCookieName": "XSRF-TOKEN",
        "xsrfHeaderName": "X-XSRF-TOKEN",
        "maxContentLength": -1,
        "maxBodyLength": -1,
        "env": {},
        "headers": {
            "Accept": "application/json, text/plain, */*"
        },
        "baseURL": "https://testplatform.hogwarts.ceshiren.com/",
        "method": "get",
        "url": "/testcase"
    },
    "code": "ERR_BAD_REQUEST",
    "status": 401
}
```

### 请求拦截器
登录后所有接口请求的请求头自动携带 token 信息；
自动拦截请求，并放入请求头信息
- 请求头
```
{
    "Authorization": "Bearer {token}"
}
```
因为这是对应所有的请求需要进行拦截，所以拦截器需要配置在http.js文件内：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210271731458.png)
```js
instance
    .interceptors//拦截器
    .request//请求
    //规则
    .use(config =>{},error => {})
```
#### 完整demo
```
//添加请求拦截器
instance
    .interceptors//拦截器
    .request//请求
    //规则
    .use(config =>{
        //如果本地登录成功，则会获取到token信息
        if(localStorage.getItem('token')){
            // token信息拼装到请求头
            config.headers.Authorization = 'Bearer ' + localStorage.getItem('token')
            //命令行输入打印查看是否正常
            console.log('token请求头：',config.headers.Authorization)
        }
        return config;
    },error => {
        //返回一个 Promise因给定原因而被拒绝的对象
        return Promise.reject(error);
    })

```
### 响应拦截器
当登录不成功的时候，需要把本地的token进行清空操作。

```
//添加响应拦截器
instance
    .interceptors
    .response
    .use(config =>{}, error => {})
```
   
   
#### 完整demo
```
//添加响应拦截器
instance
    .interceptors
    .response
    .use(config =>{
        if(0 != config.data.code){
            //没有登录成功，清空token
            localStorage.removeItem('token');
        }
        return config;
    }, error => {
         //返回一个 Promise因给定原因而被拒绝的对象
         return Promise.reject(error);
    })
```
   
   
设置好之后，我们来尝试一下登录错误的场景。

查看下本地保存的 token 信息，已经被清除掉了。

到这里，我们的登录功能就完成了。


   
   
### 退出
```
<v-row>
    <v-col cols="11"></v-col>
    <v-col cols="1">
        <v-btn
        tile
        color="#82B1FF"
        @click="exit"
        >
         退出
        </v-btn>
    </v-col>
</v-row>
```


```
 methods: {
      exit(){
        localStorage.removeItem('token');
        this.$router.push('/login')
      }
     }
```
   
   
   
   
   
   