/*
	创建表的语法格式
	create table 表名(
	   字段名称1 字段类型(长度),
	   字段名称2 字段类型,
	   字段名称3 字段类型 最后一个列不要添加逗号
	);
	
	MySql中常见的数据类型
		int 整型
		double 浮点型
		varchar 字符串
		date 日期类型 年月日 没有时分秒 yyyy-MM-dd
		datetime 日期时间类型 yyyy-MM-dd HH:mm:ss
		
*/

-- 创建商品分类表
/*
	表名 category
		cid int 分类id
		cname varchar 分类的名称
*/

-- 选择要使用的数据库
USE db1;

-- 创建分类表
CREATE TABLE category(
	cid INT,
	cname VARCHAR(20)
);

-- 创建测试表
/*
	表名 test1
		tid int 
		tdate date
*/
CREATE TABLE test1(
	tid INT,
	tdate DATE
);

-- 快速创建一个表结构相同的表（复制表结构）
-- 语法结构 create table 新表名称 like 旧表名称

-- 创建一个与test1表结构相同的 test2表
CREATE TABLE test2 LIKE test1;

-- 查看表结构
DESC test1;

-- 查看表
-- 查看当期那数据库中所有的数据表名
SHOW TABLES;

-- 查看创建表的 sql
SHOW CREATE TABLE category;

-- 查看表结构
DESC category;


-- 表的删除

-- 方式1: 将数据库中的某一张表永久删除
-- 语法格式: drop table 表名
DROP TABLE test1;

-- 方式2: 判断表是否存在, 如果存在就删除 如果不存在就不执行删除
DROP TABLE IF EXISTS test2;


/*
	修改表的名称
	修改表的字符集
	修改表中的某一列 (数据类型 名称 长度)
	向表中添加一列
	删除表中的某一列
*/

-- 修改表名称 语法格式: rename table 旧表名 to 新表名
RENAME TABLE category TO category1;

-- 修改表的字符集为 gbk 
-- 语法格式: alter table 表名 character set 字符集
ALTER TABLE category1 CHARACTER SET gbk; 

-- 向表中添加一个字段 关键字: add
-- 语法格式: alter table 表名 add 字段名称 字段类型(长度)
-- 添加分类描述字段
ALTER TABLE category1 ADD cdesc VARCHAR(20);


-- 修改表中列的类型或者长度 关键字 modify
-- 语法格式: alter table 表名 modify 字段名称 字段类型
-- 修改cdesc 字段的长度为 50
ALTER TABLE category1 MODIFY cdesc VARCHAR(50); -- 修改字段长度
ALTER TABLE category1 MODIFY cdesc CHAR(20); -- 修改字段类型

-- 修改列的名称 关键字: change
-- 语法格式: alter table 表名 change 旧列名 新列名 类型(长度)
-- 修改cdesc字段 名称改为 description varchar(30)
ALTER TABLE category1 CHANGE cdesc description VARCHAR(30);


-- 删除列 关键 drop
-- 语法格式: alter table 表名 drop 列名
ALTER TABLE category1 DROP description;














