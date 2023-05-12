-- 查询所有的数据库列表
SHOW DATABASES;

/*
	对数据库操作的分类包含:
		CRUD
		C create 创建
		R retrieve 查询
		U update 修改
		D delete 删除
		使用数据库
*/

/*
	创建数据库 方式1: 指定名称的数据库
	latin1 编码
*/
CREATE DATABASE db1;

/*
	指定字符集的方式创建数据库
	utf8
*/
CREATE DATABASE db1_1 CHARACTER SET utf8;

/*
	查看数据库
*/
-- 切换数据库
USE db1_1;

-- 查询当前正在使用的数据库
SELECT DATABASE();

-- 查询MySql中有哪些数据库
SHOW DATABASES;

-- 修改数据库的字符集
-- 语法格式 alter database 数据库名 character set utf8;
ALTER DATABASE db1 CHARACTER SET utf8;

-- 查询当前数据库的基本信息
SHOW CREATE DATABASE db1;


-- 删除数据库
-- 语法格式 drop database 数据库名称 将数据库从MySql中永久删除
DROP DATABASE db1_1;   -- 慎用 

