---
title: 前后端登录功能实战02
date: 2022-10-26 11:51:21.366
updated: 2022-10-26 16:43:08.469
url: /archives/login02
categories: 
- vue
tags: 
- vue
---



## 登录页面
### 项目创建
##### 1. 安装脚手架
```shell
npm install --location=global @vue/cli
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241503392.png)
##### 2. vue-ui
- 启动Vue-UI界面
```shell
vue ui
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241508699.png)

##### 3. 界面新建项目
- 创建项目名为：`auto-ui`
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241545810.gif)

##### 3. 界面新建项目
##### 5. 安装插件
- router：`cli-plugin-router`

- vuetify：`vue-cli-plugin-vuetify`


![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241550171.png)
##### 6. 安装依赖

- axios
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241555054.png)

##### 7. 编译启动

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241605661.png)


编译成功后，点击启动app，自动跳转页面如下：

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241607661.png)

######  编译报错


```shell
error  Component name "Home" should always be multi-word  vue/multi-word-component-names
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241557623.png)

######  问题分析
- 命名不标准的问题
- 可能和`eslint`配置项有关


######  解决方案
修改项目中的配置
- `vue.config.js`添加：
```js
// vue.config.js
module.exports = defineConfig({
  ...
  lintOnSave: false,  //保证编译不会报错
});
```

完整代码如下：
```js
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: [
    'vuetify'
  ],
  lintOnSave: false,  //保证编译不会报错
})

```

### 目录解读

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241611492.png)
#### assets
放置图片
#### components
vue公共组件
#### plugins
插件目录，目前只是vuetify的内容
#### router
路由目录，配置路由在该目录下的js进行。
#### views
功能的界面

#### App.vue
- 根组件，所有的界面起点。
#### main.js
- 前端界面的入口。
- 全局性配置在该js内
### 拷贝测试用例相关页面内容

### 登录空白页面
- 登录页面创建：`src/views/Login.vue`
```vue
<template>
    <div>
        登录页面
    </div>
</template>

<script>
export default {

}
</script>

<style>

</style>
```

- 路由配置：`src/router/index.js`
```js
import Login from '../views/Login.vue'

....
const routes = [
 ...
  {
    path: '/login',
    name: 'login',
    component: Login,
    
  }, 
  ...
  
]
...

```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210261148266.png)

#### 访问页面
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210261147073.png)

## 登录页面布局

### v-app
在`template`标签内添加 `v-app`标签：
```
<v-app id="login">
</v-app>
```

- `v-app` 组件确保正确的跨浏览器兼容性所必需的。
- `id`  设置组件上的` DOM id `


### v-main
在 `v-app`标签内添加 `v-main`标签：
```
    <v-main class="grey lighten-3">       
    </v-main>
```
- 根据应用组件来调整内容
### Grid system
- 使用 [网格系统](https://vuetifyjs.com/zh-Hans/components/grids/) 进行登录模块的布局

#### v-container

- 将网站内容居中和水平填充的功能
```js
<template>
  <v-app id="login">
    <v-main class="grey lighten-3">
    <v-container class="login">

    </v-container>
    </v-main>
  </v-app>
</template>
```

#### v-row
- 是 v-col 的容器组件，若使用 v-col 则需要先添加该标签
```js
<template>
  <v-app id="login">
    <v-main class="grey lighten-3">
    <v-container class="login">
      <v-row> 
      
      </v-row>
    </v-container>
    </v-main>
  </v-app>
</template>
```
#### v-col 
- 网格内包裹内容 
- `cols` ：column简写，属性定义列宽，提供 1 到 12 的值；这里设置列宽为最大值

列表大小：
- `xs`是maxsmall简写：超小
- `sm`是small简写：小
- `md`是medium简写：中等
- `lg`是large简写：大；

栅格系统都会自动的把每行row分为12列， xs=*、sm=* 、md=*和 lg=* 后面跟的参数表示在当前的屏幕中的占列数。在这里我们使用8

```js
<template>
  <v-app id="login">
    <v-main class="grey lighten-3">
    <v-container class="login">
      <v-row> 
        <v-col cols="12" sm="8">
         
        </v-col>
      </v-row>
    </v-container>
    </v-main>
  </v-app>
</template>
```


### Forms
前面的这些标签其实是为了进行页面布局。我们最核心的元素是在 v-form 标签当中。
因为登录、注册这样的内容肯定是一个表单进行提交，所以，我们需要在前端页面创建一个[表单](https://vuetifyjs.com/zh-Hans/components/forms/)的格式。


#### 布局
选择带有[提交与验证 & 清除](https://vuetifyjs.com/zh-Hans/components/forms/#section-63d04ea44e0e9a8c8bc1-26-6e059664)的表单格式，放入登录页面的`v-col`标签，对应页面显示如下：

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210261413693.png)
```
<v-form
  ref="form"
  v-model="valid"
  lazy-validation
>
  <h1>登录</h1>
  <v-text-field
    v-model="name"
    :counter="10"
    :rules="nameRules"
    label="账号"
    required
  ></v-text-field>
 <v-text-field
      v-model="password"
     :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
     :rules="passwordRules"
     :type="show ? 'text' : 'password'"
     label="密码"
     hint="至少3个字符"
     counter
     @click:append="show = !show"
  ></v-text-field>

```
#### v-text-field
可以看到对应表单输入框使用的是`<v-text-field>`	标签，对应属性如下：

- `v-model`：和 `script`标签内的`data` 中的字段进行双向绑定，这样方便后面获取到输入框中的值
- `:rules`：`v-bind` 绑定了 `rules` 属性，具体规则写在 `data` 中
- `lable`：是输入框的默认值
- `required`：表示输入框是必填项，如果不填写会有对应提示信息


密码又有自己独特的特性，比如需要隐藏，比如密码长度有限制，必须大于6个字符才可以，我们使用[密码输入](https://vuetifyjs.com/zh-Hans/components/text-fields/#section-5bc678018f935165)文本框 

#### 修改规则
- 密码小于3个字符，对应**登录按钮置灰** 并且 提示：**您输入的密码有误，请重新输入**
```js
<script>
  export default {
    data: () => ({
      valid: true,
      name: '',
      nameRules: [
        v => !!v || '账号不能为空',
        v => (v && v.length <= 20) || '您输入的账号格式不正确',
      ],
      password: '',
      passwordRules: [
        v => !!v || '密码不能为空',
        v => (v && v.length >= 3)|| '您输入的密码格式有误',
      ],
    }),

    methods: {
    ...
```

- name 和 password 分别接收输入框的值。
- nameRules 和 passwordRules 定义输入框的规则。
>因为用户名和密码都需要输入，所以这里设置的都是不能为空，为空会展示后面的提示信息；
>另外分别进行一个长度的限制，这里账号先简单设置为不能超过20个字符，如果超过则提示：您输入的账号格式不正确



#### 居中表单
 调整样式位置居中，添加如下style内容：
```js
<style scoped>
.login {
  width: 500px;
  margin: auto;
  text-align: center;
}
</style>
```

#### 提交按钮
对应表单的提交按钮使用的是`v-btn`标签：
```js
<v-btn
  :disabled="!valid"
  color="success"
  class="mr-4"
  @click="login"
>
  登录
</v-btn>
```
- `:disabled`：检查每个规则的状态，绑定disabled按钮上的属性，如果表单对应的规则都满足，则可点击。
- `color` ：指定了按钮的颜色
- `class="mr-4"` ：使用栅格布局，控制了两个按钮之间的间距
- `@click` 绑定了点击事件监听器，在点击按钮的时候，会调用对应的方法，具体的方法在 methods 中定义



注册按钮：
```js
<v-btn
    color="primary"
    class="mr-4"
    @click="register"
>
    注册
</v-btn>
```

最终效果如下：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210261518159.png)


现在发现在输入用户名和密码之后，点击登录按钮，现在还完全没有反应。为什么呢？

对应的在登录按钮上绑定的 login 方法里还什么逻辑都没有，所以导致按钮没有反应，那接下来我们就来看看怎么才能点击按钮就发出登录请求。






















