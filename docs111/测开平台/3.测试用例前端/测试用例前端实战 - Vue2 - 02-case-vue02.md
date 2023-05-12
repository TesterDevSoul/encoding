---
title: 测试用例前端实战 - Vue2 - 02
date: 2022-10-25 17:19:00.733
updated: 2022-10-25 18:00:19.56
url: /archives/case-vue02
categories: 
- front-end
- vue
tags: 
- vue
---



## 侧边栏导航

需要在`LayOut.vue`文件内添加对应侧边栏导航。


- 把下图数据直接复制到`v-navigation-drawer`标签内；

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241916249.png)


- 把`items`的列表直接复制到`script`标签的`data()`内。

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241917909.png)


### 修改显示文本
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241921239.png)

完整LayOut.vue：
```vue
<template>
  <v-app id="inspire">
    <v-navigation-drawer
      v-model="drawer"
      app
    >
      <!--  -->
       <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6">
            TesterU测试平台
          </v-list-item-title>
          <v-list-item-subtitle>
            www.testeru.top
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>TesterU测试平台</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <!--  -->
    </v-main>
  </v-app>
</template>

<script>
  export default {
    data: () => ({ 
      drawer: null,
        items: [
          { title: '测试用例', icon: 'mdi-view-dashboard' },
          { title: '测试任务', icon: 'mdi-image' },
          { title: '测试报告', icon: 'mdi-help-box' },
        ], 
        right: null,
     }),
  }
</script>
```

修改默认数据值后页面显示：


![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241925924.png)


## 点击侧边栏跳转

#### 创建测试任务页面
- TestTask.vue
```vue
<template>
  <div>测试任务</div>
</template>

<script>
export default {

}
</script>

<style>

</style>
```

#### 创建测试报告
- TestReport.vue
```vue
<template>
  <div>测试报告</div>
</template>

<script>
export default {

}
</script>

<style>

</style>
```


#### 配置对应路由

```vue
import TestTask from '../views/TestTask.vue'
import TestReport from '../views/TestReport.vue'

...

const routes = [
  {
    path: '/',
  
  }, 
  {
    path: '/layout',
    name: 'layout',
    component: LayOut
  },
  {
    path: '/testcase',
    name: 'testcase',
    component: TestCase
  },
  {
    path: '/testtask',
    name: 'testtask',
    component: TestTask
  },
  {
    path: '/testreport',
    name: 'testreport',
    component: TestReport
  }
]
...

```


### 问题一
访问首页直接跳转到用例页面布局 - `LayOut.vue`

### 解决方案
路由到首页`/`时重定向到`/layout`：

```js
  {
    path: '/',
    redirect: '/layout'
  }
```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241946237.gif)

### 问题二
在页面布局中，右侧布局显示 **测试用例** /**测试任务** /**测试报告** 
### 解决方案
使用 **嵌套路由** 方式进行页面正常显示。

#### 修改路由
- 在`layout`路由内添加对应`children`路由规则
```
const routes = [
...
  {
    path: '/layout',
    name: 'layout',
    component: LayOut,
    children: [
      {
        path: 'case',
        name: 'case',
        component: TestCase
      },
      {
        path: 'task',
        name: 'task',
        component: TestTask
      },
      {
        path: 'report',
        name: 'report',
        component: TestReport
      }
    ]
  },
  
]

...

```


#### 修改路由
在`LayOut.vue`布局内`main`标签添加路由视图`router-view`。

```vue
   <v-main>
      <!-- 嵌套路由正常显示 -->
      <router-view/>
    </v-main>
```
>router-view标签：把子路由的内容显示在该标签内。
效果如下图：
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210241956941.png)
### 问题三
目前点击侧边栏导航对应的 **测试用例** /**测试任务** /**测试报告** 无法正常跳转到测试用例页面「`TestCase.vue` /`TestTask.vue`  /`TestCase.vue`」。



### 解决方案
页面对应导航栏显示使用的是`v-list-item`标签。在该标签中查看API发现，使用 `link` 及 `href` 属性可以进行页面跳转。
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210242007463.png)

- `items`内配置跳转的路由地址，需要 **#开头**

- `v-list-item`标签内配置对应`href`动作
```vue
	...
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          :href="item.url"
        >
	...

<script>
	...
        items: [
          { title: '测试用例', icon: 'mdi-view-dashboard', url: '#/layout/case' },
          { title: '测试任务', icon: 'mdi-image' , url: '#/layout/task' },
          { title: '测试报告', icon: 'mdi-help-box', url: '#/layout/report'  },
        ]
        ...
</script>
```
## 页面展示问题

![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210251648227.png)

不是在页面正中间显示，需要进行修改。

原因：原有的App.vue文件内容存在，需要删除掉。

- `src/App.vue`：删除多余代码，设置app的 id 属性进行挂载。

```vue
<template>
  <v-app id="app">
    <router-view/>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>

```

## axios
### 创建http基本请求配置
- `src/api/http.js`
```vue
//http基本请求配置
//导入axios
import axios from "axios";

//创建axios实例
var instance = axios.create({
    //请求头信息
    'headers' : {

    },
    //基础后端服务地址
    baseURL : '',
    //默认前端请求超时时间 毫秒
    timeout : 2500,
})

//只有导出后才能正常在其他地方导入
export default instance
```


### 测试用例请求配置


```vue
//测试用例请求配置

//导入已经配置好的 axios
import axios from "./http";

const testcase = {
   //方法一：获取用例信息
   getTestCase(params){
    return axios({
        method: "GET",
        url: "/testcase",
        params: params //传递的参数拼接URL，用params
        
    })
   },
    //添加用例
    addTestCase(data){
        return axios({
            method: "POST",
            url: "/testcase",
            data: data //传递的参数是body
        })
    },
    //删除用例
    deleteTestCase(data){
        return axios({
            method: "DELETE",
            url: "/testcase",
            data: data //传递的参数是body
        })
    },
    //修改用例
    updateTestCase(data){
        return axios({
            method: "PUT",
            url: "/testcase",
            data: data //传递的参数是body   
        })
    }
}


//导出让其他文件可以使用
export default testcase
```




```vue
//所有接口的入口，相当于目录
import testcase from './testcase'


const api = {
    testcase,
}

export default api
```
### 导入入口js
```
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import api from './api/api'

Vue.prototype.$api = api
Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

```
![](https://cdn.jsdelivr.net/gh/testeru-top/top-images/tester/202210251722293.png)






