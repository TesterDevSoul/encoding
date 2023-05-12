## DQL 查询表中数据 
### 数据准备
#### 创建表

代码准备，创建员工表:

- 表名:emp 。
- 表中字段:
  - 员工ID：eid int。
  - 姓名：ename varchar(20) 。
  - 性别：sex char(1)。
  - 薪资：salary double。
  - 入职时间：hire_date date。
  - 部门名称：dept_name varchar(10)。

```sql
-- 创建员工表
CREATE TABLE emp(
    eid INT,
    ename VARCHAR(20),
    sex CHAR(1),
    salary DOUBLE,
    hire_date DATE,
    dept_name VARCHAR(10)
);
```

#### 插入数据
- 1,孙悟空,男,7200,2013-02-04,教学部。
- 2,猪八戒,男,3600,2010-12-02,教学部。
- 3,唐僧,男,9000,2008-08-08,教学部。
- 4,白骨精,女,5000,2015-10-07,市场部。
- 5,蜘蛛精,女,5000,2011-03-14,市场部。
- 6,玉兔精,女,200,2000-03-14,市场部。
- 7,林黛玉,女,10000,2019-10-07,财务部。
- 8,黄蓉,女,3500,2011-09-14,财务部。
- 9,吴承恩,男,20000,2000-03-14,NULL。
- 10,孙悟饭,男, 10,2020-03-14,财务部。
- 11,兔八哥,女, 300,2010-03-14,财务部。
```sql
-- 添加数据
INSERT INTO emp VALUES(1,'孙悟空','男',7200,'2013-02-04','教学部'); 
INSERT INTO emp VALUES(2,'猪八戒','男',3600,'2010-12-02','教学部'); 
INSERT INTO emp VALUES(3,'唐僧','男',9000,'2008-08-08','教学部'); 
INSERT INTO emp VALUES(4,'白骨精','女',5000,'2015-10-07','市场部'); 
INSERT INTO emp VALUES(5,'蜘蛛精','女',5000,'2011-03-14','市场部'); 
INSERT INTO emp VALUES(6,'玉兔精','女',200,'2000-03-14','市场部'); 
INSERT INTO emp VALUES(7,'林黛玉','女',10000,'2019-10-07','财务部'); 
INSERT INTO emp VALUES(8,'黄蓉','女',3500,'2011-09-14','财务部'); 
INSERT INTO emp VALUES(9,'吴承恩','男',20000,'2000-03-14',NULL); 
INSERT INTO emp VALUES(10,'孙悟饭','男', 10,'2020-03-14',财务部);
INSERT INTO emp VALUES(11,'兔八哥','女', 300,'2010-03-14',财务部);
```

### 简单查询

查询不会对数据库中的数据进行修改，只是一种显示数据的方式。关键字为： **SELECT**。

查询包括：**简单查询**和**条件查询**。我们首先来看简单查询。

命令模版：

```sql
select 列名 from 表名

```

以上是一些常用的SQL查询操作，实际使用时，需要根据具体情况进行灵活组合和调整。同时，还需要注意SQL语句的语法和正确性，避免出现语法错误和逻辑错误。





#### 需求1: 查询所有数据

使用 * 表示所有列，类似正则匹配中的*。

查询emp中的所有数据。

```sql
-- 使用 * 表示所有列
SELECT * FROM emp; 
```

#### 需求2: 查询部分列

查询emp表中的所有记录，仅显示id和name字段。

```sql
SELECT eid,ename FROM emp;
```

#### 需求3: 别名查询

别名查询，使用关键字：**as**。

将所有的员工信息查询出来，并将列名改为中文。 

```sql
-- 使用 AS关键字,为列起别名 
SELECT
  eid AS '编号',
  ename AS '姓名' ,
  sex AS '性别',
  salary AS '薪资',
  hire_date '入职时间', -- AS 可以省略 
  dept_name '部门名称'
FROM emp;
```

#### 需求4: 去重查询

去重查询，使用去重关键字：**DISTINCT**。

查询员工表中一共有几个部门。

```sql
-- 使用distinct 关键字,去掉重复部门信息 
SELECT DISTINCT dept_name FROM emp;
```

#### 需求5: 运算查询

运算查询 (查询结果直接参与运算)。

将所有员工的工资 +3000 元进行显示。

```sql

SELECT ename,salary+3000 FROM emp;

```

### 条件查询 

如果查询语句中没有设置对应条件，就会查询所有的行信息。

实际应用中，一定要指明**查询条件**，对查询结果的记录进行过滤。

1. 先取出表中的每条数据。
2. 满足条件的数据就返回。
3. 不满足的就过滤掉。


![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307180248.png)

>该活动图显示了查询学生信息的流程，包括输入查询条件、执行查询语句和显示查询结果。




命令模版：

```sql
select 列名 from 表名 where 条件表达式
```


#### 运算符

比较运算符、逻辑运算符。

##### 1) 比较运算符

|运算符|说明|
|---|---|
|`> < >= <= = <> !=`|大于`>`、小于`<`、大于等于`>=`、小于等于`<=`、等于`=`、不等于 `<>`或者`!=`|
|`BETWEEN ...AND...`|显示在某一区间的值<br>例如: 2000-10000之间: Between 2000 and 10000|
|`IN(集合)`| 集合表示多个值,使用逗号分隔,例如: `name in (悟空，八戒)`<br>in中的每个数据都会作为一次条件,只要满足条件就会显示|
|`LIKE '%张%'`|模糊查询|
|`IS NULL`|查询某一列为NULL的值,注:不能写 `= NULL`|
     
##### 2) 逻辑运算符
   
|运算符|说明|
|---|---|
|`And &&`|多个条件同时成立。|
|`Or \|\|`|多个条件任一成立。|
|`Not`| 不成立，取反。|


#### 需求1

1. 查询员工**姓名**为**黄蓉**的员工信息。

2. 查询**薪水**价格为**5000**的员工信息。

3. 查询**薪水**价格**不是5000**的所有员工信息。

4. 查询**薪水**价格**大于6000**元的所有员工信息。

5. 查询**薪水**价格**在5000到10000之间**所有员工信息。

6. 查询**薪水**价格**是3600或7200或者20000**的所有员工信息。


```sql
--  1. 查询员工姓名为黄蓉的员工信息
SELECT * FROM emp WHERE ename = '黄蓉';

-- 2. 查询薪水价格为5000的员工信息
SELECT * FROM emp WHERE salary = 5000;

-- 3. 查询薪水价格不是5000的所有员工信息
SELECT * FROM emp WHERE salary != 5000; 
SELECT * FROM emp WHERE salary <> 5000;

-- 4. 查询薪水价格大于6000元的所有员工信息 
SELECT * FROM emp WHERE salary > 6000;

-- 5. 查询薪水价格在5000到10000之间所有员工信息
SELECT * FROM emp WHERE salary BETWEEN 5000 AND 10000;

-- 6. 查询薪水价格是3600或7200或者20000的所有员工信息

-- 方式1: or
SELECT * FROM emp WHERE salary = 3600 OR salary = 7200 OR salary = 20000;

-- 方式2: in() 匹配括号中指定的参数
SELECT * FROM emp WHERE salary IN(3600,7200,20000);

```



#### 需求2

1. 查询**含有**'**精**'字的所有员工信息。

2. 查询以'**孙**'**开头**的所有员工信息。

3. 查询第**二**个字为'**兔**'的所有员工信息。

4. 查询**没有部门**的员工信息。

5. 查询**有部门**的员工信息。

##### 通配符

|通配符 |说明|
|--- |---|
|% |表示匹配任意 **多个** 字符串 |
|_ |表示匹配 **一个** 字符|

```sql
-- 1. 查询含有'精'字的所有员工信息
SELECT * FROM emp WHERE ename LIKE '%精%';

-- 2. 查询以'孙'开头的所有员工信息
SELECT * FROM emp WHERE ename LIKE '孙%';

-- 3. 查询第二个字为'兔'的所有员工信息
SELECT * FROM emp WHERE ename LIKE '_兔%';

-- 4. 查询没有部门的员工信息
SELECT * FROM emp WHERE dept_name IS NULL; 
-- SELECT * FROM emp WHERE dept_name = NULL; 错误

-- 5. 查询有部门的员工信息
SELECT * FROM emp WHERE dept_name IS NOT NULL;
```



### 查询总结
```sql
-- 查询表中的所有数据
SELECT * FROM table_name;

-- 查询表中指定的列
-- “column1”和“column2”是要查询的列名。
SELECT column1, column2 FROM table_name;

-- 查询满足指定条件的行
-- “condition”是查询条件，例如“age > 18”表示查询年龄大于18岁的行。
SELECT * FROM table_name WHERE condition;

-- 对查询结果进行排序
-- “column_name”是要排序的列名，[ASC|DESC]表示升序或降序排列。
SELECT * FROM table_name ORDER BY column_name [ASC|DESC];


-- 对查询结果进行分组和聚合计算
-- “column1”是要分组的列名，
-- aggregate_function可以是SUM、AVG、COUNT等聚合函数，
-- 用于对指定列进行计算。
SELECT column1, aggregate_function(column2) FROM table_name GROUP BY column1;

-- 查询满足多个条件的行
-- “condition1”和“condition2”是查询条件，AND表示同时满足两个条件。
SELECT * FROM table_name WHERE condition1 AND condition2;

```


## DQL操作单表


### 排序

在SQL中，排序是指按照指定的列对查询结果进行升序或降序排列，常用的排序操作有两种方式：使用**ORDER BY**子句和使用**函数**进行排序。

需要注意的是，使用函数进行排序的语法相对复杂，需要熟练掌握SQL函数的使用方法和语法规则。同时，在实际应用中，使用ORDER BY子句进行排序更为常见和实用。

使用ORDER BY子句排序

ORDER BY子句是一种用于对查询结果进行排序的语句，通常放在SELECT语句的最后面。

通过 `ORDER BY` 子句,可以将查询出的结果进行排序(**排序只是显示效果,不会影响真实数据**) 。

其基本语法如下：


```sql
SELECT 字段名 FROM 表名 [WHERE 字段 = 值] ORDER BY 字段名 [ASC / DESC]

-- 其中，column1是要排序的列名，[ASC|DESC]表示升序或降序排列，默认为升序排列。
SELECT column1, column2, ... FROM table_name ORDER BY column1 [ASC|DESC];

```

其中，column1是要排序的列名，`[ASC|DESC]`表示升序或降序排列，默认为升序排列。

#### ASC 

表示升序排序(默认) 。

#### DESC 

表示降序排序。



举例来说，假设我们有一个学生表students，其中包含 学生姓名name、学生年龄age 和 学生成绩score 三个列，我们想要按照学生成绩对学生进行降序排列，可以使用如下的SQL查询语句：

```sql
SELECT name, age, score 
FROM students 
ORDER BY score DESC;

```
在这个查询中，我们使用了ORDER BY子句来按照成绩列进行降序排序，最终得到按照成绩降序排列的学生信息。

### 排序方式

#### 1) 单列排序
只按照某一个字段进行排序, 就是单列排序。

##### 需求1

使用 **salary** 字段,对emp表数据进行升序排序。

使用 **salary** 字段,对emp表数据进行降序排序。

```sql
-- 默认升序排序 ASC
SELECT * FROM emp ORDER BY salary;

-- 降序排序
SELECT * FROM emp ORDER BY salary DESC;
```

#### 2) 组合排序

**同时**对多个字段进行**排序**：

- 如果第一个字段相同；

- 就按照第二个字段进行排序；

- 以此类推。

##### 需求2

在**薪水**降序排序的基础上,再使用**id**进行排序, 如果薪水相同就以id做**降序**排序。

```sql
-- 组合排序
SELECT * FROM emp ORDER BY salary DESC, eid DESC;
```

###  聚合函数 

之前我们做的查询都是**横向查询**，它们都是根据条件一行一行的进行判断。

而使用**聚合函数**查询是**纵向查询**，它是**对某一列的值进行计算**，然后返回一个单一的值。(另外聚合函数会忽略null空值)

SQL中的聚合函数用于对一组数据进行计算，并返回一个单一的计算结果。

常用的聚合函数包括 SUM、AVG、COUNT、MIN 和 MAX 等，它们可以用于对数值类型、日期类型、文本类型等数据类型的计算。

命令模版：

```sql
SELECT 聚合函数(字段名) FROM 表名;
```
#### 常用的聚合函数
以下是几个常用的聚合函数的介绍：

|聚合函数|作用|
|---|---|
|`count`(字段)|统计指定列**不为NULL**的记录行数| 
|`sum`(字段) |计算指定列的数值**和**|
|`max`(字段) |计算指定列的最**大**值|
|`min`(字段) |计算指定列的最**小**值|
|`avg`(字段) |计算指定列的**平均**值|


##### SUM函数
SUM函数用于对某一列的数值进行求和计算。其基本语法如下：

```sql
-- column_name是要进行求和计算的列名。
SELECT SUM(column_name) FROM table_name;
```


举例来说，如果我们有一个订单表orders，其中包含订单编号order_id和订单金额amount两个列，我们可以使用如下的SQL查询语句对订单金额amount进行求和计算：

```sql
SELECT SUM(amount) FROM orders;
```

##### AVG函数
AVG函数用于对某一列的数值进行平均值计算。其基本语法如下：

```sql
-- column_name是要进行平均值计算的列名。
SELECT AVG(column_name) FROM table_name;
```


举例来说，如果我们有一个学生表students，其中包含学生姓名name、学生年龄age和学生成绩score三个列，我们可以使用如下的SQL查询语句对学生成绩进行平均值计算：

```sql
SELECT AVG(score) FROM students;
```
##### COUNT函数
COUNT函数用于对某一列的数据进行计数操作。其基本语法如下：

```sql
-- column_name是要进行计数操作的列名。
SELECT COUNT(column_name) FROM table_name;
```


举例来说，如果我们有一个学生表students，其中包含学生姓名name、学生年龄age和学生成绩score三个列，我们可以使用如下的SQL查询语句对学生数量进行计数：

```sql

SELECT COUNT(*) FROM students;
```
这里使用了*代表查询所有列，也可以使用某一特定列进行计数。

##### MIN函数和MAX函数
MIN函数用于对某一列的数据进行最小值计算，而MAX函数用于对某一列的数据进行最大值计算。它们的基本语法如下：

```sql
-- column_name是要进行最小值或最大值计算的列名。
SELECT MIN(column_name) FROM table_name;
SELECT MAX(column_name) FROM table_name;
```


举例来说，如果我们有一个订单表orders，其中包含订单编号order_id和订单金额amount两个列，我们可以使用如下的SQL查询语句对订单金额进行最小值和最大值计算：

```sql

SELECT MIN(amount) FROM orders;
SELECT MAX(amount) FROM orders;
```
需要注意的是，聚合函数通常需要配合GROUP BY子句一起使用，以实现对分组数据的聚合计算。


#### 需求1

1. 查询员工的总数。
2. 查看员工总薪水、最高薪水、最小薪水、薪水的平均值。
3. 查询薪水大于4000员工的个数。
4. 查询部门为'教学部'的所有员工的个数。
5. 查询部门为'市场部'所有员工的平均薪水，显示为：市场部平均薪资。



```sql
-- 1. 查询员工的总数。
-- 统计表中的记录条数 使用 count()
SELECT COUNT(eid) FROM emp; -- 使用某一个字段，该字段必须有值 
SELECT COUNT(*) FROM emp;-- 使用 *
SELECT COUNT(1) FROM emp; -- 使用 1,与 * 效果一样

-- 下面这条SQL 得到的总条数不准确,因为count函数忽略了空值 
-- 所以使用时注意不要使用带有null的列进行统计
SELECT COUNT(dept_name) FROM emp;

-- 2. 查看员工总薪水、最高薪水、最小薪水、薪水的平均值。
-- sum函数求和, max函数求最大, min函数求最小, avg函数求平均值 
SELECT
  SUM(salary) AS '总薪水', 
  MAX(salary) AS '最高薪水', 
  MIN(salary) AS '最低薪水', 
  AVG(salary) AS '平均薪水'
FROM emp;

-- 3. 查询薪水大于4000员工的个数。
SELECT COUNT(*) FROM emp WHERE salary > 4000;

-- 4. 查询部门为'教学部'的所有员工的个数。
SELECT COUNT(*) FROM emp WHERE dept_name = '教学部';

-- 5. 查询部门为'市场部'所有员工的平均薪水，显示为：市场部平均薪资。
SELECT
  AVG(salary) AS '市场部平均薪资' 
FROM emp
WHERE dept_name = '市场部';

```

### 分组

分组查询指的是使用 **GROUP BY** 语句：

- 对查询的信息进行分组，相同数据作为一组。

>在SQL中，GROUP BY是一种用于将行分组的查询操作，它通常与聚合函数（如SUM、AVG、COUNT等）一起使用，用于对分组后的行进行汇总计算。

命令模版：

```sql
SELECT 分组字段/聚合函数 
FROM 表名 GROUP BY 分组字段 [HAVING 条件];
```

```sql
SELECT column1, column2, aggregate_function(column3)
FROM table_name
WHERE condition
GROUP BY column1, column2;
```

其中，

SELECT子句用于指定要查询的列，

aggregate_function用于对指定列进行聚合计算，

FROM子句用于指定要查询的表，

WHERE子句用于指定查询条件，

GROUP BY子句用于指定要分组的列。

>举例来说，假设我们有一个学生成绩表，其中包含姓名、科目和成绩三列，我们想要按照科目对成绩进行分组计算平均值和总和，可以使用如下的SQL查询语句：

```sql
SELECT subject, AVG(score), SUM(score)
FROM scores
GROUP BY subject;
```
在这个查询中，我们使用了GROUP BY子句来按照科目列进行分组，然后使用**AVG**和**SUM**函数对成绩列进行聚合计算，最终得到每个科目的平均分和总分。

>需要注意的是，在GROUP BY子句中指定的**列**，**必须是SELECT子句中出现的列或者是聚合函数的参数**。<br>此外，如果SELECT子句中包含了聚合函数，则必须**在GROUP BY子句中指定所有未被聚合的列**。



#### 需求1

- 通过**性别**字段进行分组。

```sql
-- 按照性别进行分组操作
SELECT * FROM emp GROUP BY sex; 
-- 注意 这样写没有意义
```

分析: GROUP BY 分组过程

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307180506.png)



注意⚠️：

- 分组时可以查询要分组的字段，或者使用聚合函数进行统计操作。 

- 查询其他字段没有意义。

#### 需求1

通过**性别字段**进行分组，求各组的平均薪资。

```sql
SELECT sex, AVG(salary) FROM emp GROUP BY sex;
```


#### 需求2

1. 查询有几个部门。

2. 查询每个部门的平均薪资。
   
3. 查询每个部门的平均薪资, 部门名称不能为null。


```sql
-- 1. 查询有几个部门
SELECT dept_name AS '部门名称' 
FROM emp 
GROUP BY dept_name;

-- 2.查询每个部门的平均薪资
SELECT
  dept_name AS '部门名称', 
  AVG(salary) AS '平均薪资' 
FROM emp 
GROUP BY dept_name;

-- 3.查询每个部门的平均薪资, 部门名称不能为null 
SELECT
  dept_name AS '部门名称',
  AVG(salary) AS '平均薪资'
FROM emp 
WHERE dept_name IS NOT NULL 
GROUP BY dept_name;
```


#### 需求3

1. 查询平均薪资大于6000的部门，部门名称不能为null。


##### 分析

1. 需要在分组后,对数据进行过滤,使用 关键字 **hiving**


2. 分组操作中的`having`子语句，是用于在**分组后对数据进行过滤的**，作用类似于where条件。


SQL实现:
```sql
-- # 查询平均薪资大于6000的部门
-- 需要在分组后再次进行过滤,使用 having 
SELECT
    dept_name ,
    AVG(salary)
FROM emp  
WHERE dept_name IS NOT NULL 
GROUP BY dept_name 
HAVING AVG(salary) > 6000 ;
```

![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307200511.png)


### limit关键字 

SQL中的LIMIT关键字用于限制查询结果返回的记录数量。

通过使用LIMIT关键字，我们可以控制查询结果集的大小，避免在处理大量数据时出现性能问题。

- limit是限制的意思,用于 限制返回的查询结果的行数 (可以通过limit指定查询多少行数据) 
- limit 语法是 MySql的方言,用来完成分页



LIMIT关键字的基本语法如下：
```sql
-- limit offset , length; 
-- 关键字可以接受一个 或者两个 为0 或者正整数的参数
-- offset 起始行数, 从0开始记数, 如果省略 则默认为 0.
-- count 返回的行数
SELECT 字段1,字段2... FROM 表名 LIMIT offset , count;

-- offset表示要跳过的记录数量
-- count表示要返回的记录数量
SELECT column_name1, column_name2, ... FROM table_name LIMIT offset, count;
```


也就是说，LIMIT关键字可以将查询结果分页显示，以方便用户查看。

举例来说，如果我们有一个学生表students，其中包含学生姓名name、学生年龄age和学生成绩score三个列，我们可以使用如下的SQL查询语句获取前10个学生的信息

```sql
SELECT name, age, score FROM students LIMIT 0, 10;
```

这里的offset为0，表示从第1条记录开始返回；

count为10，表示最多返回10条记录。

如果我们要查询第**11**到第**20**个学生的信息，可以使用如下的SQL查询语句：

```sql
SELECT name, age, score FROM students LIMIT 10, 10;
```

这里的offset为10，表示跳过前10条记录；

count为10，表示最多返回10条记录。

#### 需求1

1. 查询emp表中的前 5条数据。
2. 查询emp表中从第4条开始,查询6条。

```sql
--  查询emp表中的前 5条数据
-- 参数1 起始值,默认是0 , 参数2 要查询的条数 

SELECT * FROM emp LIMIT 5;
SELECT * FROM emp LIMIT 0 , 5;

-- 查询emp表中 从第4条开始,查询6条 
-- 起始值默认是从0开始的.
SELECT * FROM emp LIMIT 3 , 6;
```


##### 需求2: 分页操作 
每页显示3条数据，第一页SQL，第二页SQL，第三页SQL语句分别为？

```sql
-- 分页操作 每页显示3条数据

-- 第1页 
SELECT * FROM emp LIMIT 0,3; 
-- 第2页 2-1=1 1*3=3
SELECT * FROM emp LIMIT 3,3; 
-- 第三页
SELECT * FROM emp LIMIT 6,3; 

-- 分页公式 起始索引 =(当前页 -1)* 每页条数 
-- limit是MySql中的方言

```
