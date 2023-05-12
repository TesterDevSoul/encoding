
## DQL操作多表

多表查询
### 什么是多表查询

DQL: 查询多张表,获取到需要的数据。

比如 我们要查询家电分类下 都有哪些商品,那么我们就需要查询分类与商品这两张表


### 数据准备 

#### 数据库创建

创建db3_2 数据库，编码格式为utf8。


```sql
-- 创建 db3_2 数据库,指定编码
CREATE DATABASE db3_2 CHARACTER SET utf8;
```


#### 创建表

创建分类表与商品表。

创建**分类表**:

- 表名:category 。
- 表中字段:
  - ID：cid VARCHAR(32)  主键。
  - 类别名：cname varchar(50) 。

```sql
-- 分类表 (一方 主表) 
CREATE TABLE category (
  cid VARCHAR(32) PRIMARY KEY ,
  cname VARCHAR(50)
);

```

创建**商品表**:

```sql
-- 商品表 (多方 从表) 
CREATE TABLE products(
    pid VARCHAR(32) PRIMARY KEY ,
    pname VARCHAR(50),
    price INT,
    flag VARCHAR(2), -- 是否上架标记为:1表示上架、0表示下架 
    category_id VARCHAR(32),
    -- 添加外键约束
   FOREIGN KEY (category_id) REFERENCES category (cid)
);
```
#### 插入数据

分类表数据：

- c001,家电。
- c002,鞋服。
- c003,化妆品。
- c004,汽车。

商品表数据：

- p001,小米电视机,5000,1,c001。
- p002,格力空调,3000,1,c001。
- p003,美的冰箱,4500,1,c001。
- p004,篮球鞋,800,1,c002。
- p005,运动裤,200,1,c002。
- p006,T恤,300,1,c002。
- p007,冲锋衣,2000,1,c002。
- p008,神仙水,800,1,c003。


```sql
-- #分类数据
INSERT INTO category(cid,cname) VALUES('c001','家电');
INSERT INTO category(cid,cname) VALUES('c002','鞋服'); 
INSERT INTO category(cid,cname) VALUES('c003','化妆品'); 
INSERT INTO category(cid,cname) VALUES('c004','汽车');


-- #商品数据
INSERT INTO products(pid, pname,price,flag,category_id) VALUES('p001','小米电视机',5000,'1','c001');
INSERT INTO products(pid, pname,price,flag,category_id) VALUES('p002','格力空调',3000,'1','c001');
INSERT INTO products(pid, pname,price,flag,category_id) VALUES('p003','美的冰箱',4500,'1','c001');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p004','篮球鞋',800,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p005','运动裤',200,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p006','T恤',300,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p007','冲锋衣',2000,'1','c002');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p008','神仙水',800,'1','c003');
INSERT INTO products (pid, pname,price,flag,category_id) VALUES('p009','大宝',200,'1','c003');
```



在SQL中，我们可以通过JOIN关键字实现多表查询。

JOIN关键字可以将两个或多个表中的记录进行连接，以生成一张新表。

常见的JOIN操作有INNER JOIN、LEFT JOIN、RIGHT JOIN和FULL OUTER JOIN。



## 多表查询的分类 
 
### 内连接查询

内连接查询（INNER JOIN）是SQL中最常用的一种多表查询方式，它可以根据两个表中的某个共同的列（即连接键）将它们连接起来，然后返回符合条件的记录。

内连接的特点:

通过指定的条件去匹配两张表中的数据, 匹配上就显示,匹配不上就不显示。

比如通过: 从**表的外键** = **主表的主键** 方式去匹配 

#### 隐式内连接

SQL中的隐式内连接是指在SELECT语句中使用WHERE子句来连接两个表，从而实现多表查询的一种方式。

具体来说，我们可以使用WHERE子句来指定两个表中的某个列相等，以连接这两个表。



from子句 后面直接写 多个表名 使用where指定连接条件的。这种连接方式是 隐式内连接。

使用where条件过滤无用的数据。


```sql
SELECT 字段名 FROM 左表, 右表 WHERE 连接条件;
```

**需求练习**
例如，我们有两张表：students和scores。

其中students表包含学生姓名name和学生年龄age两个列，

scores表包含学生姓名name和学生成绩score两个列。

我们可以使用如下的SQL查询语句查询每个学生的姓名、年龄和成绩：

```sql
SELECT students.name, students.age, scores.score
FROM students, scores
WHERE students.name = scores.name;
```


在这个查询中，我们使用WHERE子句将students表和scores表连接起来，连接条件为两个表中的name列相等。查询结果将包含所有符合条件的记录，即学生的姓名、年龄和成绩。

>需要注意的是，在使用隐式内连接时，连接条件必须写在WHERE子句中，并且必须保证连接键在两个表中都存在，并且具有相同的数据类型。否则，查询将无法执行。



##### 需求1
1) 查询所有商品信息和对应的分类信息

```sql
-- 隐式内连接
SELECT * FROM products,category WHERE category_id = cid;
```

##### 需求2

通过给表起别名的方式, 方便我们的查询(有提示)

2) 查询商品表的 商品名称 和 价格,以及商品的分类信息


```sql
SELECT
    p.`pname`,
    p.`price`,
    c.`cname`
FROM products p , category c 
WHERE p.`category_id` = c.`cid`;
```

##### 需求3

1) 查询 格力空调是属于哪一分类下的商品

```sql
-- #查询 格力空调是属于哪一分类下的商品
SELECT p.`pname`,c.`cname` 
FROM products p , category c 
WHERE p.`category_id` = c.`cid` 
AND p.`pid` = 'p002';
```


#### 显式内连接

与隐式内连接相对的是显式内连接（INNER JOIN），

它使用JOIN关键字和ON子句来连接两个表，更加明确和易于理解。


因此，在实际的SQL开发中，建议使用显式内连接来进行多表查询。


使用 inner join ...on 这种方式, 就是显式内连接。

语法格式：

```sql
SELECT 字段名 FROM 左表 [INNER] JOIN 右表 ON 条件 

-- inner 可以省略
```


例如，我们有两张表：students和scores。

其中students表包含学生姓名name和学生年龄age两个列。

scores表包含学生姓名name和学生成绩score两个列。

我们可以使用如下的SQL查询语句查询每个学生的姓名、年龄和成绩：

```sql
SELECT students.name, students.age, scores.score
FROM students
INNER JOIN scores ON students.name = scores.name;
```

在这个查询中，我们使用INNER JOIN关键字将students表和scores表连接起来，并使用ON子句指定连接条件，

即students表和scores表中name列相等的记录。

查询结果将包含所有符合条件的记录，即学生的姓名、年龄和成绩。

>需要注意的是，在使用显式内连接时，连接条件必须写在ON子句中，并且必须保证连接键在两个表中都存在，并且具有相同的数据类型。否则，查询将无法执行。



##### 需求1
1) 查询所有商品信息和对应的分类信息

```sql
-- 显式内连接查询
SELECT * FROM products p INNER JOIN category c ON p.category_id = c.cid;
```
##### 需求2

2) 查询鞋服分类下,价格大于500的商品名称和价格

```sql
-- 查询鞋服分类下,价格大于500的商品名称和价格
-- 我们需要确定的几件事
-- 1.查询几张表 products & category
-- 2.表的连接条件 从表.外键 = 主表的主键
-- 3.查询的条件 cname = '鞋服' and price > 500 -- 4.要查询的字段 pname price



SELECT
    p.pname,
    p.price
FROM products p 
INNER JOIN category c ON p.category_id = c.cid 
WHERE p.price > 500 AND cname = '鞋服';
```


同时，显式内连接还可以进行更多类型的JOIN操作，
包括`LEFT JOIN`、`RIGHT JOIN`和`FULL OUTER JOIN`等。

### 外连接查询 

除了INNER JOIN，还有其他类型的JOIN操作，

包括LEFT JOIN、RIGHT JOIN和FULL OUTER JOIN等，

它们的使用方式和INNER JOIN类似，只是返回的结果不同。

其中，

LEFT JOIN返回左侧表中所有的记录，无论是否有对应的右侧表中的记录；

RIGHT JOIN返回右侧表中的所有记录，无论是否有对应的左侧表中的记录；

FULL OUTER JOIN返回左右两个表中的所有记录。

#### 左外连接

左外连接 , 使用 LEFT OUTER JOIN , `OUTER` 可以省略。

左外连接（LEFT JOIN）：

左外连接会返回左侧表中所有的记录，无论是否有对应的右侧表中的记录。

如果没有匹配的右侧表中的记录，则返回NULL值。


**左外连接的特点**

- 以左表为基准, 匹配右边表中的数据。
- 如果匹配的上,就展示匹配到的数据。
- 如果匹配不到, 左表中的数据正常展示, 右边的展示为null。

1) 语法格式

```sql
SELECT 字段名 
FROM 左表 
LEFT [OUTER] JOIN 右表 
ON 条件



SELECT 列名1，列名2，...
FROM 左表名
LEFT JOIN 右表名
ON 连接条件;
```


例如，假设我们有两张表，一张是学生信息表students，

包含学生ID和姓名两个字段，

另一张是学生成绩表scores，包含学生ID和成绩两个字段。

我们想要查询出每个学生的姓名和成绩，如果某个学生没有对应的成绩，则成绩列显示为NULL。


```sql
SELECT students.name, scores.score
FROM students
LEFT JOIN scores
ON students.id = scores.id;
```

在这个查询中，我们使用了LEFT JOIN关键字将students表和scores表进行了连接，并且指定了连接条件students.id = scores.id。

由于是左外连接，查询结果将包含所有左表中的记录，即所有的学生姓名，以及符合条件的右表中的成绩，如果没有符合条件的成绩，则成绩列将显示为NULL。

```sql
-- 左外连接查询
SELECT * FROM category c LEFT JOIN products p ON c.`cid`= p.`category_id`;
```



![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307211515.png)

##### 需求

2) 左外连接, 查询每个分类下的商品个数

```sql
-- # 查询每个分类下的商品个数 
/*
1.连接条件: 主表.主键 = 从表.外键 
2.查询条件: 每个分类 需要分组 
3.要查询的字段: 分类名称, 分类下商品个数
*/
SELECT
    c.`cname` AS '分类名称', 
    COUNT(p.`pid`) AS '商品个数'
FROM category c 
LEFT JOIN products p 
ON c.`cid` = p.`category_id`
GROUP BY c.`cname`;
```


#### 右外连接
右外连接（RIGHT JOIN）：

右外连接与左外连接相反，会返回右侧表中所有的记录，无论是否有对应的左侧表中的记录。

如果没有匹配的左侧表中的记录，则返回NULL值。

**右外连接的特点**：
- 以右表为基准，匹配左边表中的数据。
- 如果能匹配到，展示匹配到的数据。
- 如果匹配不到，右表中的数据正常展示, 左边展示为null。

语法：
```sql
SELECT 列名1，列名2，...
FROM 左表名
RIGHT JOIN 右表名
ON 连接条件;

```

例如，我们有两张表：学生表和成绩表。

学生表中有学生的姓名、年龄等信息，

成绩表中有学生的姓名和各科成绩信息。

我们可以使用右外连接查询每个学生的姓名、年龄和成绩，

如果某个学生在学生表中没有对应的记录，则学生信息显示为NULL。

```sql
SELECT s.姓名, s.年龄, c.数学成绩, c.英语成绩
FROM 学生表 s
RIGHT JOIN 成绩表 c
ON s.姓名 = c.姓名;

```


```sql
-- 右外连接查询
SELECT * FROM products p RIGHT JOIN category c ON p.`category_id` = c.`cid`;
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307212840.png)

#### 全外连接
全外连接（FULL OUTER JOIN）：

全外连接会返回左右两个表中的所有记录。

如果左侧表中没有对应的记录，则返回NULL值；

如果右侧表中没有对应的记录，则返回NULL值。

语法：
```sql
SELECT 列名1，列名2，...
FROM 左表名
FULL OUTER JOIN 右表名
ON 连接条件;

```

例如，我们有两张表：学生表和成绩表。

学生表中有学生的姓名、年龄等信息，

成绩表中有学生的姓名和各科成绩信息。

我们可以使用全外连接查询每个学生的姓名、年龄和成绩，

如果某个学生在学生表或成绩表中没有对应的记录，则信息显示为NULL。


```sql
SELECT s.姓名, s.年龄, c.数学成绩, c.英语成绩
FROM 学生表 s
FULL OUTER JOIN 成绩表 c
ON s.姓名 = c.姓名;

```
