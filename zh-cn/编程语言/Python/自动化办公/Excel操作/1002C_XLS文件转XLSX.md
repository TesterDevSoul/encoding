
## XLS转为XLSX



### 环境准备
```bash
pip install pandas openpyxl
```
### 代码解读

```python
# excel_file 文件转为 xlsx_file文件
# 使用 pandas 库、使用 `xlrd` 引擎读取名为 excel_file 的 Excel 文件
df = pd.read_excel(excel_file, engine="xlrd")
# 将 DataFrame 保存到新的 Excel 文件中，不包括索引列
df.to_excel(xlsx_file, index=False)
```

使用 pandas 库读取名为 excel_file 的 Excel 文件，并将数据加载到名为 df 的 pandas DataFrame 中，在这个过程中使用 `xlrd` 引擎。

excel_file 是一个字符串或者路径对象，表示您要读取的 Excel 文件名称和路径。

在大多数情况下，您可以直接使用文件名（如果文件位于当前目录），或者为文件名添加适当的路径信息（如果文件位于其他目录）。

### 场景：转单个文件的sheet1的内容


### 场景：转多个文件的sheet1的内容

### 场景：转单个文件的多sheet的内容

### 场景：转多个文件的多sheet的内容


### 环境准备
```bash
pip install pandas openpyxl
```
### 单个文件第一个sheet转换

将单个LiteMall.xls文件转换为LiteMall.xlsx。


```python
from pathlib import Path

import pandas as pd

file_path = '/Users/gaigai/Desktop'

folder = Path(file_path)
# xls文件名
xls_name = 'LiteMall.xls'
# 文件路径拼接
excel_file = folder / xls_name

df = pd.read_excel(excel_file, engine="xlrd")
new_name = xls_name.split(".")[0]
xlsx_name = new_name + '.xlsx'
# 使用 pathlib 进行路径拼接
xlsx_file = folder / xlsx_name
print(f"新生成的文件路径为：{xlsx_file}")
# 将 DataFrame 保存到新的 Excel 文件中，不包括索引列
df.to_excel(xlsx_file, index=False)
```


### 文件夹内第一个sheet转换

```python
import os
from pathlib import Path,PurePath
import pandas as pd

# xls 转换为 xlsx
src_path = '/Users/gaigai/Desktop/测试用例xls'
xlsx_path = '/Users/gaigai/Desktop/测试用例xlsx'

p=Path(src_path)
files=[x for x in p.iterdir() if PurePath(x).match('*.xls')]

# todo: 双层for循环读取文件夹内的每一行
# 对每一个文件进行重复处理
for file in files:
    # 使用了os库的os.path.getsize()函数来检查Excel文件的大小，
    # 如果文件大小大于0，将继续读取并处理该文件。
    # 将输出一个错误消息。这将避免抛出XLRDError: File size is 0 bytes错误。
    file_size = os.path.getsize(file)
    # 如果文件大小大于0，则继续读取文件
    if file_size > 0:
        df = pd.read_excel(file, engine="xlrd")
        name = file.name.split(".")[0]
        # 另存为新的 Excel 文件（.xlsx）  os.path.join(folder, file)
        # 在Python中，为了确保跨平台兼容性，建议使用os.path.join() 进行路径拼接。
        # 它会根据您的操作系统选择正确的路径分隔符（在Windows上是反斜杠 \，在其他大多数操作系统上是正斜杠 /）。
        # Windows: example_folder\example_file.txt
        # Linux 或 macOS: example_folder/example_file.txt
#        new_excel = os.path.join(xlsx_path, name + '.xlsx')
        #  Python 3.4 或更高版本，您还可以使用 pathlib 库进行路径操作，
        #  这是一个对路径操作进行了更高级封装的库，旨在替代 os.path。
        folder = Path(xlsx_path)
        file = name + '.xlsx'
        # 使用 pathlib 进行路径拼接
        new_excel = folder / file
        print(new_excel)
        # 将 DataFrame 保存到新的 Excel 文件中，不包括索引列
        df.to_excel(new_excel, index=False)
```



### 文件内所有的sheet转换

从名为example.xls的Excel文件中读取所有工作表，并将数据写入名为converted_example.xlsx的Excel文件。


## 生成图表

xlrd 和 xlwt 库不支持在 Excel 文件中插入图像；因此，您需要使用 openpyxl 库来完成此任务。openpyxl 库允许您读取和写入 Excel 文件，并支持多种 Excel 功能，包括插入图像。openpyxl读写 Excel 文件（XLSX）。


## 总结
- 总结一
- 总结二
- 总结三
https://github.com/Wechat-ggGitHub/Awesome-GitHub-Repo

[项目演示地址](https://github.com/testeru-pro/junit5-demo/tree/main/junit5-basic)


# 学习反馈

1. SpringBoot项目的父工程为( )。

   - [x] A. `spring-boot-starter-parent`
   - [ ] B.`spring-boot-starter-web`
   - [ ] C. `spring-boot-starter-father`
   - [ ] D. `spring-boot-starter-super`


<style>
  strong {
    color: #ea6010;
    font-weight: bolder;
  }
  .reveal blockquote {
    font-style: unset;
  }
</style>







