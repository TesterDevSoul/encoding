---
title: nginx安装
date: 2021-08-08 11:34:43.0
updated: 2022-08-08 15:45:18.42
url: /archives/nginx安装
categories: 
tags: 
---

177  2022-08-03 12:22:13 sudo yum install epel-release
  178  2022-08-03 12:22:28 sudo yum install nginx
  179  2022-08-03 12:24:21 which nginx
  180  2022-08-03 13:59:47 rpm -ql nginx
  181  2022-08-03 14:01:40 cd /var/lib/nginx
  182  2022-08-03 14:01:41 ls
  183  2022-08-03 14:01:52 cd /usr/share/nginx
  184  2022-08-03 14:01:53 ls
  185  2022-08-03 14:23:44 cd ~
  186  2022-08-03 14:23:50 cd /etc/nginx
  187  2022-08-03 14:23:57 ./sbin/nginx -t
  188  2022-08-03 14:24:03 sbin/nginx -t
  
  
  nginx配置文件更改后使用：
  
  189  2022-08-03 14:24:10 nginx -t
  190  2022-08-03 14:24:18 nginx -t
  191  2022-08-03 14:24:52 nginx -s reload
  
  
  192  2022-08-03 14:25:03 nginx -v
  193  2022-08-03 14:26:49 vi 
  194  2022-08-03 14:27:13 cd /etc
  195  2022-08-03 14:27:15 ls
  196  2022-08-03 14:27:20 cat hosts
  197  2022-08-03 14:27:35 vi hosts
  198  2022-08-03 14:28:16 nginx -t
  199  2022-08-03 14:36:04 nginx -t
  200  2022-08-03 14:37:22 nginx -t
  201  2022-08-03 14:37:39 nginx -s reload
  202  2022-08-03 14:37:53 sudo systemctl start nginx
  203  2022-08-03 14:37:56 sudo systemctl start firewalld.service
  204  2022-08-03 14:38:00 nginx -s reload
  205  2022-08-03 14:38:05 sudo firewall-cmd --permanent --zone=public --add-service=http
  206  2022-08-03 14:38:08 sudo firewall-cmd --permanent --zone=public --add-service=https
  207  2022-08-03 14:38:12 sudo firewall-cmd --reload
  208  2022-08-03 14:40:45 cat /var/log/nginx/error.log
  209  2022-08-03 14:41:05 cat /var/log/nginx/error.log
  210  2022-08-03 14:41:59 ifconfig
  211  2022-08-03 14:42:47 ping 110.40.250.165
  212  2022-08-03 14:50:50 nginx -s reload
  213  2022-08-03 14:51:54 nginx -s reload
  214  2022-08-03 14:52:33 nginx -s reload
  215  2022-08-03 14:52:45 nginx -t
  216  2022-08-03 14:53:18 cat /var/log/nginx/error.log
  217  2022-08-03 14:53:50 cd /etc
  218  2022-08-03 14:53:57 vi hosts
  219  2022-08-03 14:54:28 nginx -s reload
  220  2022-08-03 14:54:51 cat /var/log/nginx/error.log
  221  2022-08-03 14:57:05 nginx -s reload
  222  2022-08-03 14:57:27 cat /var/log/nginx/error.log
  223  2022-08-03 14:59:36 nginx -s reload
  224  2022-08-03 14:59:51 cat /var/log/nginx/error.log
  225  2022-08-03 15:02:35 cat /var/log/nginx/error.log
  226  2022-08-03 15:05:36 cat /var/log/nginx/error.log
  227  2022-08-03 15:06:15 nginx -s reload
  228  2022-08-03 15:06:36 cat /var/log/nginx/error.log
  229  2022-08-03 15:07:56 nginx -s reload
  230  2022-08-03 15:07:57 nginx -s reload
  231  2022-08-03 15:08:15 cat /var/log/nginx/error.log
  232  2022-08-03 15:12:02 nginx -s reload
  233  2022-08-03 15:12:04 cat /var/log/nginx/error.log
  234  2022-08-03 15:12:18 nginx -t
  235  2022-08-03 15:12:22 nginx -s reload
  236  2022-08-03 15:13:08 cat /var/log/nginx/error.log
  237  2022-08-03 15:13:58 nginx -s reload
  238  2022-08-03 15:15:10 nginx -s reload
  239  2022-08-03 15:15:15 nginx -t
  240  2022-08-08 10:23:24 cat /var/log/nginx/error.log