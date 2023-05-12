/*
	DQL 
		简单查询
			select 列名 from 表名;
*/

-- 查询emp 表中的所有数据
SELECT * FROM emp; -- * 表示所有的列

-- 查询所有数据 只显示 id 和 name
SELECT eid, ename FROM emp;

-- 查询所有的数据,然后给列名 改为中文
SELECT * FROM emp;

-- 别名查询 使用关键字 as

SELECT 
	eid AS '编号',
	ename AS '姓名',
	sex AS '性别',
	salary AS '薪资',
	hire_date AS '入职时间',
	dept_name '部门名称'  -- as 可以省略

FROM emp;

-- 查询一共有几个部门
SELECT dept_name FROM emp;

-- 去重操作 关键字 distinct
SELECT DISTINCT dept_name FROM emp;

-- 将我们的员工薪资数据 +1000 进行展示
SELECT ename, salary+1000 AS salary FROM emp;

-- 注意: 查询操作 不会对数据表中的数据进行修改,只是一种显示的方式.


/*
	条件查询
	语法格式: select 列名 from 表名 where 条件表达式
	
	比较运算符
		>  <  <=   >=   =  <> !=
		BETWEEN  ...AND...
		IN(集合)
		LIKE
		IS NULL
		
	逻辑运算符
		And
		Or
		Not
*/
# 查询员工姓名为黄蓉的员工信息
-- 1.查哪张表	2.查哪些字段	3.查询条件
SELECT * FROM emp WHERE ename = '黄蓉'


# 查询薪水价格为5000的员工信息
SELECT * FROM emp WHERE salary = 5000;


# 查询薪水价格不是5000的所有员工信息
SELECT * FROM emp WHERE salary != 5000;
SELECT * FROM emp WHERE salary <> 5000;

# 查询薪水价格大于6000元的所有员工信息
SELECT * FROM emp WHERE salary > 6000;

# 查询薪水价格在5000到10000之间所有员工信息
SELECT * FROM emp WHERE salary BETWEEN 5000 AND 10000;
SELECT * FROM emp WHERE salary >= 5000 AND salary  <= 10000;

# 查询薪水价格是3600或7200或者20000的所有员工信息
SELECT * FROM emp WHERE salary = 3600 OR salary = 7200 OR salary = 20000;
-- 方式2 使用 in() 匹配括号中的参数
SELECT * FROM emp WHERE salary IN(3600,7200,20000);

/*
	like '_精'
		% 通配符 ,表示匹配任意多个字符串
		_ 通配符 ,表示匹配一个字符
*/

# 查询含有'精'字的所有员工信息
SELECT * FROM emp WHERE ename LIKE '%精%';

# 查询以'孙'开头的所有员工信息
SELECT * FROM emp WHERE ename LIKE '孙%';

# 查询第二个字为'兔'的所有员工信息
SELECT * FROM emp WHERE ename LIKE '_兔%';

# 查询没有部门的员工信息
-- select * from emp where dept_name = null; 错误方式
SELECT * FROM emp WHERE dept_name IS NULL;

# 查询有部门的员工信息
SELECT * FROM emp WHERE dept_name IS NOT NULL;

-- 条件查询 先取出表中的每条数据,满足条件的就返回,不满足的就过滤.



























