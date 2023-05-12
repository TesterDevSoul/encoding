```bash
touch ~/.gitignore_global 

vi ~/.gitignore_global
添加内容如下：
# Mac OS
**/.DS_Store

git config --global core.excludesfile ~/.gitignore_global
```