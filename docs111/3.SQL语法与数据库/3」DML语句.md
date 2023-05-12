## DML 操作表中数据

SQL中的DML 用于对表中的数据进行增删改操作。

### 插入数据

命令模版：

```sql
insert into 表名 (字段名1，字段名2...) values(字段值1，字段值2...);
```

添加数据有3种方式：
1. 方式1: 插入全部字段， 将所有字段名都写出来。
2. 方式2: 插入全部字段，不写字段名。
3. 方式3:插入指定字段的值。


#### 需求: 创建学生表

代码准备，创建一个学生表:

- 表名:student 
- 表中字段:
  - 学员ID：sid int
  - 姓名：sname varchar(20) 
  - 年龄：age int
  - 性别：sex char(1)
  - 地址：address varchar(40)

```sql
-- 创建学生表
CREATE TABLE student(
    sid INT,
    sname VARCHAR(20),
    age INT,
    sex CHAR(1),
    address VARCHAR(40)
);
```

#### 需求1: 插入全部字段

方式1: 插入全部字段，将所有字段名都写出来。

- 插入数据为：
  - 学员ID：1
  - 姓名：孙悟空
  - 年龄：20
  - 性别：男
  - 地址：花果山
```sql
INSERT INTO student (sid,sname,age,sex,address) VALUES(1,'孙悟空',20,'男','花果 山');
```
#### 需求2: 插入全部字段，不写字段名

方式2: 插入全部字段，不写字段名。

- 插入数据为：
  - 学员ID：2
  - 姓名：孙悟饭
  - 年龄：10
  - 性别：男
  - 地址：地球


```sql
INSERT INTO student VALUES(2,'孙悟饭',10,'男','地球');
```

#### 需求3: 插入指定字段的值

方式3:插入指定字段的值。

- 插入数据为：
  - 姓名：白骨精


```sql
INSERT INTO student (sname) VALUES('白骨精');
```


#### 注意⚠️:

1. 值与字段必须要对应，个数相同&数据类型相同。 


```sql
-- 表的字段和插入值没有对应 少了一个字段对应值
INSERT INTO student(sid,sname,age,sex,address) VALUES(4,'孙悟空',20,'男');
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307150904.png)

```sql
-- 插入的长度与定义不同 sex是char一个 man是3个长度
INSERT INTO student(sid,sname,age,sex,address) VALUES(4,'孙悟空',20,'man','花果山');
```  
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307151124.png)
  
>值的数据大小，必须在字段指定的长度范围内。

2. `varchar` `char` `date`类型的值必须使用**单引号**，或者双引号 包裹。 建议单引号。

```sql
-- 报错
INSERT INTO student(sid,sname,age,sex,address) VALUES(4,孙悟空,20,'男','花果山');

INSERT INTO student(sid,sname,age,sex,address) VALUES(4,"孙悟空",20,'男','花果山');
```
![](https://cdn.jsdelivr.net/gh/TesterDevSoul/pic/manual/20230307151530.png)


3. 如果要插入空值，可以忽略不写，或者插入`null`。

```sql
INSERT INTO student(sid,sname) VALUES(5,'八戒');
INSERT INTO student(sid,sname,age) VALUES(6,'唐三藏',NULL);
```

4. 如果插入指定字段的值，必须要上写列名。
```sql
INSERT INTO student(address) VALUES('女儿国');
```

### 更改数据 

#### 不带条件的修改

命令模版：

```sql
update 表名 set 列名 = 值
```
#### 带条件的修改

命令模版：

```sql
update 表名 set 列名 = 值 [where 条件表达式:字段名 = 值 ]
```

#### 需求1: 不带条件修改

1)不带条件修改，**将所有的性别改为女**(慎用!!)。

```sql
UPDATE student SET sex = '女';
```

#### 需求2: 带条件的修改

2)带条件的修改，将sid 为5的学生，性别改为男。

```sql
-- 没有sid为3的行，不报错，但是执行行数为0
UPDATE student SET sex = '男' WHERE sid = 3;

UPDATE student SET sex = '男' WHERE sid = 5;

```

#### 需求3: 一次修改多个列

3)一次修改多个列， 将sid为 2 的学员，年龄改为 20，地址改为 北京。

```sql
UPDATE student SET age = 20,address = '北京' WHERE sid = 2;
```


### 删除数据 
- 删除所有数据
- 指定条件删除数据


#### 删除所有数据

命令模版：

```sql
delete from 表名
```
#### 指定条件删除数据

命令模版：

```sql
delete from 表名 [where 字段名 = 值]
```


#### 需求1: 指定条件删除数据
删除student表中sid 为 1 的数据。

```sql
DELETE FROM student WHERE sid = 1;
```
#### 需求2: 删除所有数据

删除student表中所有数据。

```sql
DELETE FROM student;
```

#### 删除表所有数据

如果要删除表中的所有数据,有两种做法：

1. `delete from 表名`：
   >不推荐。<br>有多少条记录就执行多少次删除操作。<br>效率低。
2. `truncate table 表名`：
   >推荐。<br>先删除整张表, 然后再重新创建一张一模一样的表。<br>效率高。
    ```sql
    truncate table student;
    ```

```sql
-- 创建学生表
CREATE TABLE student(
    sid INT,
    sname VARCHAR(20),
    age INT,
    sex CHAR(1),
    address VARCHAR(40)
);
```

