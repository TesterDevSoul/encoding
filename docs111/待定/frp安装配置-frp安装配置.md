---
title: frp安装配置
date: 2021-08-08 11:24:31.0
updated: 2022-08-08 15:45:28.594
url: /archives/frp安装配置
categories: 
tags: 
---

# 服务端
frpserver端
## 下载
从gitHub上下载最新版本：
```shell
wget https://github.com/fatedier/frp/releases/download/v0.44.0/frp_0.44.0_linux_amd64.tar.gz
```
## 解压
```shell
tar xf frp_0.44.0_linux_amd64.tar.gz 
```

## 配置server文件
- 进入到解压文件夹内
```
cd frp_0.44.0_linux_amd64/
```
- 创建编写server配置文件
```
vim frps.ini
```

## 启动
```
./frps -c ./frps.ini
```
## 环境配置
```
vim /lib/systemd/system/frps.service
```
- 文件内容
```
[Unit]
Description=frps service
After=network.target syslog.target
Wants=network.target
 
[Service]
Type=simple
ExecStart=/root/frp_0.44.0_linux_amd64/frps -c /root/frp_0.44.0_linux_amd64/frps.ini
 
[Install]
WantedBy=multi-user.target
```

## 防火墙设置
```
systemctl enable frps
```
- 重启生效
```
reboot
systemctl status frps    #重启服务器后执行此命令查看frp运行状态
systemctl status frps -l
ss -antl #查看启动的端口号
```


# client安装
- docker安装frpc
- 配置client配置文件