create database fwdemo default charset utf8;

use fwdemo;

create table user (
    id int not null auto_increment primary key,
    name varchar(32) not null,
    age int not null,
    index idx_age(age),
    index idx_name(name)
);