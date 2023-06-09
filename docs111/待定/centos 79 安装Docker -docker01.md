---
title: centos 7.9 安装Docker 
date: 2022-08-02 17:41:08.746
updated: 2022-08-02 18:13:38.483
url: /archives/docker01
categories: 
- docker
- install
tags: 
- docker
- centos
- 腾讯云
---

## 安装步骤
##### 1. 使用 root 权限更新 yum 包
```
yum -y update
```


>这个命令不是必须执行的，看个人情况，后面出现不兼容的情况的话就必须update了

注意⚠️： 
- `yum -y update`：
	- 升级所有包同时也升级软件和系统内核
- `yum -y upgrade`：
	- 只升级所有包，不升级软件和系统内核

##### 2. 卸载旧版本

```
yum remove docker docker-common docker-selinux docker-engine
```

开始安装
##### 3. yum软件包
1. 安装需要的软件包，` yum-util` 提供`yum-config-manager`功能，另两个是`devicemapper`驱动依赖

```
yum install -y yum-utils device-mapper-persistent-data lvm2
```


##### 4. yum源设置
```javascript
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo（阿里仓库）
```


##### 5.查看可用版本
```javascript
yum list docker-ce --showduplicates | sort -r
```

##### 6.选择版本安装

选择一个版本并安装：`yum install docker-ce-版本号`

```javascript
yum -y install docker-ce-18.06.1.ce
```
##### 7.启动
启动 Docker 并设置开机自启
```
systemctl start docker //启动 Docker
systemctl enable docker //开机自启
```

##### 8.验证
```
docker version
```