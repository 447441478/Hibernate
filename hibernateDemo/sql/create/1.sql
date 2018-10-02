create database if not exists hib character set utf8;

use hib;

create table student(
	id varchar(32) primary key,
	name varchar(20),
	age int,
	deptId varchar(32)
);

CREATE TABLE person(
	id VARCHAR(32) PRIMARY KEY ,
	NAME VARCHAR(32)
);