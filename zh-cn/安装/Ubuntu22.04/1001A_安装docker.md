### 1. 更新 Ubuntu

打开终端，依次运行下列命令：

```bash
sudo apt update
sudo apt upgrade
sudo apt full-upgrade
```


### 2. 添加 Docker 库

首先，安装必要的证书并允许 apt 包管理器使用以下命令通过 HTTPS 使用存储库：

```bash
sudo apt install apt-transport-https ca-certificates curl software-properties-common gnupg lsb-release
```

然后，运行下列命令添加 Docker 的官方 GPG 密钥：


```bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
```


https://zhuanlan.zhihu.com/p/547169542
