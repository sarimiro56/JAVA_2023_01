create database app;
use app;
create table person(
    name varchar(20) not null,
    phone varchar(20) not null,
    email varchar(30),
    age int,
    primary key(phone)
);

select * from person;

create table dayday(
	id int primary key auto_increment,
    day_num char(8),
    day_content varchar(50)
);

insert into dayday values(null, '20230427', '오늘은 일정표 만든날');
insert into dayday values(null, '20230427', '자바 수업날');
insert into dayday values(null, '20230430', '4월 마지막 날');
insert into dayday values(null, '20230429', '오늘은 치킨이다');

select * from dayday;
