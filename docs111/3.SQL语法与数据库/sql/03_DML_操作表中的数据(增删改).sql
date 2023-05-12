/*
	DML 对表中的数据进行 增删改
	增加
		语法格式: insert into 表名 (字段名1,字段名2...) values(字段值1,字段值2...)
*/
-- 创建学生表
CREATE TABLE student(
	sid INT,
	sname VARCHAR(20),
	age INT,
	sex CHAR(1),
	address VARCHAR(40)
);

-- 向学生表中插入数据

-- 方式1 插入全部字段 将所有字段名都写出来
INSERT INTO student (sid,sname,age,sex,address) VALUES(1,'孙悟空',18,'男','花果山');

-- 方式2 插入全部字段 不写字段名
INSERT INTO student VALUES(2,'孙悟饭',5,'男','地球');

-- 方式3 插入指定字段
INSERT INTO student (sid,sname) VALUES(3,'蜘蛛精');


-- 注意事项
	-- 1.值与字段必须对应 个数&数据类型&长度 都必须一致
	INSERT INTO student (sid,sname,age,sex,address) VALUES(4,'孙悟空',18,'男','花果山');

	-- 2.在插入 varchar char date 类型的时候,必须要使用 单引号 或者双引号进行包裹
	INSERT INTO student (sid,sname,age,sex,address) VALUES(4,'孙悟空',18,'男','花果山');

	-- 3.如果插入空值 可以忽略不写 或者写 null
	INSERT INTO student (sid,sname) VALUES(5,'唐僧');
	INSERT INTO student (sid,sname,age,sex,address) VALUES(6,'八戒',NULL,NULL,NULL);
	

/*
	修改操作
		语法格式1: update 表名 set 列名 = 值
		语法格式2: update 表名 set 列名 = 值 [where 条件表达式: 字段名 = 值]
*/

-- 修改表中的所有的学生性别为女
UPDATE student SET sex = '女';  -- (慎用!!)

-- 带条件的修改 将sid为 1的数据,性别改为男
UPDATE student SET sex = '男' WHERE sid = 1;

-- 一次性修改多个列
-- 修改sid 为 5的这条数据, 年龄改为20, 地址改为 大唐
UPDATE student SET age = 20 , address = '大唐' WHERE sid = 5;


/*
	删除
		语法格式1: delete from 表名;
		语法格式2: delete from 表名 [where 条件];
*/

-- 删除 sid为 6的数据
DELETE FROM student WHERE sid = 6;

-- 删除所有数据
DELETE FROM student;


-- 删除所有数据的方式 两种
	-- 1. delete from 表; 不推荐, 对表中的数据逐条删除. 效率低
	-- 2. truncate table 表; 推荐, 删除整张表, 然后再创建一个一模一样的新表.

INSERT INTO student VALUES(1,'孙悟空',20,'男','花果山');
TRUNCATE TABLE student;


















