drop database if exists itat_cart;

create database itat_cart;

use itat_cart;

create table t_user(
	id int(11) primary key auto_increment,
	user_name varchar(100),
	password varchar(100),
	nickname varchar(100),
	type int(5)
);
insert into t_user values(1,'admin','123','超级管理员',1);


create table t_address(
	id int(11) primary key auto_increment,
	name varchar(255),
	phone varchar(100),
	post_code varchar(100),
	user_id int(11),
	CONSTRAINT FOREIGN KEY(user_id) REFERENCES t_user(id)
);
INSERT INTO t_address VALUES ('1', 'admin地址1', 'admin_phone1', 'admin_postcode1', '1');
INSERT INTO t_address VALUES ('2', 'admin地址2', 'admin_phone2', 'admin_postcode2', '1');
INSERT INTO t_address VALUES ('3', 'admin地址3', 'admin_phone3', 'admin_postcode3', '1');



