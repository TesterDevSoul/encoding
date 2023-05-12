
### SQL的分类

SQL（Structured Query Language）是一种用于关系型数据库管理系统（RDBMS）的标准化语言。根据功能和应用场景的不同，SQL可以分为以下几类：

|分类| 说明|
|---|---|
|数据定义语言 |简称DDL(Data Definition Language)，用来定义数据库对象:数据库，表，列 等。|
|数据操作语言 |简称DML(Data Manipulation Language)，用来对数据库中表的记录进行更新。|
|数据查询语言|简称DQL(Data Query Language)，用来查询数据库中表的记录。|
|数据控制语言  |简称DCL(Date Control Language)，用来定义数据库的访问权限和安全级别，  及创建用户。(了解) |


注: 我们重点学习 DML 与 DQL!

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230306165856.png)


#### DDL

数据定义语言（Data Definition Language，DDL）：用于定义数据库中的对象，如表、视图、索引等。常见的DDL语句有CREATE、ALTER和DROP等。

#### DML

数据操作语言（Data Manipulation Language，DML）：用于对数据库中的数据进行增、删、改等操作，常用的DML语句是INSERT、UPDATE和DELETE。

#### DQL
数据查询语言（Data Query Language，DQL）：用于从数据库中查询数据，常用的DQL语句是SELECT。


#### DCL

数据控制语言（Data Control Language，DCL）：用于控制数据库中的用户访问权限和数据安全性，常用的DCL语句有GRANT和REVOKE。

#### TCL

事务控制语言（Transaction Control Language，TCL）：用于管理事务的提交和回滚操作，常用的TCL语句有COMMIT和ROLLBACK。

总的来说，SQL语言可以分为数据定义语言（DDL）、数据查询语言（DQL）、数据操作语言（DML）、数据控制语言（DCL）和事务控制语言（TCL）五类。每一类语言都有其特定的语法和用途，掌握它们可以帮助您更好地操作和管理关系型数据库。


## DDL操作数据库

数据库的创建、查看、选择、修改、删除。

对数据库的操作分类包括：CURD

- **C**: create 创建
- **U**: update 修改
- **R**: retrieve 查询
- **D**: delete 删除
- 使用数据库


### 创建数据库

命令模版：

```sql
-- 创建指定名称的数据库。
create database 数据库名;
-- 创建指定名称的数据库，
-- 并且指定字符集(一般都指定utf-8)
create database 数据库名 
character set 字符集;
```

#### 练习
- 创建名为jd的数据库。
- 创建名为wechat的数据库并指定字符集为utf-8。

```sql
/*
 直接指定数据库名进行创建 
 一般数据库名为项目名称 
*/
CREATE DATABASE jd;

/*
	指定数据库名称，指定数据库的字符集
	一般都指定为 utf8,与Java中的编码保持一致 
*/
CREATE DATABASE wechat CHARACTER SET utf8;
```
不指定数据库编码格式时，会出现插入数据的时候乱码的问题。
默认数据库字符集为:latin1

### 查看/选择数据库
命令模版：

```sql
-- 切换数据库
use 数据库
-- 查看当前正在使用的数据库
select database();
-- 查看Mysql中都有哪些数据库
show databases;
--  查看一个数据库的定义信息
show create database 数据库名;
```

#### 练习

- 查看当前正在使用的数据库。
- 使用jd数据库。
- 数据库从jd 切换到 wechat。
- 查看Mysql中有哪些数据库。


```sql
-- 查看当前正在使用的数据库 
SELECT DATABASE();
USE jd;
-- 查看当前正在使用的数据库 
SELECT DATABASE();
-- 切换数据库 从jd 切换到 wechat
USE wechat;
-- 查看当前正在使用的数据库 
SELECT DATABASE();
-- 查看Mysql中有哪些数据库 
SHOW DATABASES;
-- 查看一个数据库的定义信息 
SHOW CREATE DATABASE wechat;
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230306174542.png)

#### 自带数据库

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307110013.png)


### 修改数据库

命令模版：

```sql
-- 数据库的字符集修改操作
alter database 数据库名 character set 字符集; 
```


#### 练习
- 将数据库jd 的字符集 修改为 utf8。
- 查看当前正在使用的数据库。
```sql
-- 将数据库jd 的字符集 修改为 utf8
ALTER DATABASE jd CHARACTER SET utf8;
-- 查看当前数据库的基本信息，发现编码已更改 
SHOW CREATE DATABASE jd;
```

### 删除数据库
```sql
-- 从MySql中永久的删除某个数据库
drop database 数据库名
```
#### 练习
- 将数据库 wechat 删除。
```sql
-- 删除某个数据库
DROP DATABASE wechat;
```


## DDL 操作数据表 

1. 修改表的名称。
2. 修改表的字符集。
3. 修改表中的某一列 「数据类型 名称 长度」。
4. 向表中添加一列。
5. 删除表中的某一列。


### MySQL常见的数据类型

常用的数据类型:

|类型|描述|
|---|---|
|int |整型|
|double| 浮点型|
|varchar |字符串型|
|date |日期类型，给是为 yyyy-MM-dd ,只有年月日，没有时分秒


1) 详细的数据类型(了解即可)

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230306195232.png)

#### char与varchar区别

注意⚠️:

MySQL中的 char类型与 varchar类型，都对应了 Java中的字符串String类型，**区别**在于: 

- char类型是固定长度的: 根据定义的字符串长度分配足够的空间。

- varchar类型是可变长度的: 只使用字符串长度所需的空间。
 
>比如:保存字符串 "abc"
```
x char(10) 占用10个字节
y varchar(10) 占用3个字节
```


**适用场景**:

- char类型适合存储 **固定长度**的字符串，比如：密码 ，性别一类

- varchar类型适合存储 在**一定范围内，有长度变化**的字符串

### 创建表

命令模版：

```sql
CREATE TABLE 表名(
    字段名称1 字段类型(长度)，
    字段名称2 字段类型 
);
```

注意⚠️：
1. 最后一列不要加逗号。
1. 先写名称再写类型，和Java声明相反。

#### 需求1: 创建商品分类表

在数据库jd中创建表：

- 表名：`category` 
- 表中字段:
    - 分类ID：`cid` ,为整型。
    - 分类名称：`cname`，为字符串类型，指定长度20。

```sql
-- 切换到数据库 jd
USE jd;
-- 创建商品分类表
CREATE TABLE category(
    cid INT,
    cname VARCHAR(20)
);
```

#### 需求2: 创建测试表

在数据库jd中创建表：

- 表名：`test1`。 
- 表中字段:
  - 测试ID：`tid` ,为整型。
  - 测试时间：`tdate` , 为年月日的日期类型。

```sql
-- 创建测试表
CREATE TABLE test1(
    tid INT,
    tdate DATE 
);
```
#### 需求3: 创建用户表

在数据库jd中创建表：

- 表名：`user`。 
- 表中字段:
  - 用户ID：`uid` ,为整型。
  - 用户姓名：`uname`，为字符串类型，指定长度50。
  - 用户性别：`sex`，为char(1)。
  - 地址：`adress`，为字符串类型，指定长度40。    
  - 创建时间：` create_time` , 为年月日的日期类型。

```sql
-- 创建用户表
CREATE TABLE user(
    uid INT,
    uname VARCHAR(50),
    sex INT,
    create_time DATE 
);
```
### 复制表结构

快速创建一个表结构相同的表(复制表结构) 。

命令模版「语法格式」：

```sql
create table 新表明 like 旧表名
```

#### 需求
- 创建一个表结构与 test1 相同的 test2表。
- 查看表test2结构。


```sql
-- 创建一个表结构与 test1 相同的 test2表 
CREATE TABLE test2 LIKE test1;
-- 查看表结构 
DESC test2;

-- 创建一个表结构与 category 相同的 category1表 
CREATE TABLE category1 LIKE category;

```



### 查看表
命令模版：

```sql
-- 查看当前数据库中的所有表名
show tables;
-- 查看数据表的结构
desc 表名; 
```
#### 需求1：查看商品分类表

- 查看当前数据库中的所有表名。
- 显示商品分类表`category`的结构。
- 查看创建表`category`的SQL语句。

```sql
-- 查看当前数据库中的所有表名
SHOW TABLES;
-- 显示当前数据表的结构 
DESC category;
-- 查看创建表的SQL语句
SHOW CREATE TABLE category;
```

#### 需求2：查看测试表

- 查看当前数据库中的所有表名。
- 显示测试表`test1`的结构。
- 查看创建表`test1`的SQL语句。

```sql
-- 查看当前数据库中的所有表名
SHOW TABLES;
-- 显示当前数据表的结构 
DESC test1;
-- 查看创建表的SQL语句
SHOW CREATE TABLE test1;
```


### 删除表

命令模版：

```sql
-- 删除表(从数据库中永久删除某一张表)
drop table 表名;
-- 判断表是否存在， 存在的话就删除,不存在就不执行删除
drop table if exists 表名;
```

#### 需求1: 删除表

- 直接删除test1表。
  >再次删除表的时候该表不存在，会报错。
- 先判断test2是否存在，存在的话就删除test2表。

```sql
-- 直接删除 test1 表 
DROP TABLE test1;

DROP TABLE IF EXISTS test1;-- 运行多次不报错

-- 先判断 再删除category1表
DROP TABLE IF EXISTS category1;


-- 先判断 再删除test2表
-- DROP TABLE IF EXISTS test2;
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307113303.png)

### 修改表 
#### 1) 修改表名 

命令模版：

```sql
rename table 旧表名 to 新表名
```

##### 需求1

将 `category` 表名改为 `category2`：

```sql
RENAME TABLE category TO category2;
```
##### 需求2

将 `test2` 表名改为 `test01`：

```sql
RENAME TABLE test2 TO test01;

```

#### 2) 修改表的字符集 


命令模版：

```sql
alter table 表名 character set 字符集
```

##### 需求

将`category2`表的字符集 修改为`gbk`。

```sql
alter table category2 character set gbk;
```

#### 3)向表中添加列
关键字：**ADD**

命令模版：

```sql
alert table 表名 add 字段名称 字段类型
```
##### 需求1

为分类表category2添加一个新的字段：
- 分类描述：cdesc
- 分类描述的数据类型：varchar(20)

```sql
--  为分类表添加一个新的字段为 分类描述 cdesc varchar(20) 
ALTER TABLE category2 ADD cdesc VARCHAR(20);
```


#### 4)修改表中列的数据类型或长度
关键字：**MODIFY**

命令模版：

```sql
alter table 表名 modify 字段名称 字段类型
```
##### 需求1

对分类表category2的描述字段cdesc进行修改：
数据类型改为char(50)。
数据类型长度改为varchar(50)。


```sql
-- 数据类型改为char(50)
ALTER TABLE category2 MODIFY cdesc CHAR(50);
-- 数据类型长度改为varchar(50)
ALTER TABLE category2 MODIFY cdesc VARCHAR(50);
```

#### 5)修改列名称 

关键字：**CHANGE**

命令模版：

```sql
alter table 表名 change 旧列名 新列名 类型(长度);
```

##### 需求

对分类表中的 desc字段进行更换, 更换为 description varchar(30)

```sql
ALTER TABLE category2 CHANGE cdesc description VARCHAR(30);
```

#### 6)删除列

关键字：**DROP**

命令模版：

```sql
alter table 表名 drop 列名;
```

##### 需求

删除分类表中description这列。

```sql
ALTER TABLE category2 DROP description;
```

