create table registration(
id int auto_increment primary key,
name varchar(255) not null,
mobile varchar(255),
course varchar(255) not null
);

insert into registration(name, mobile, course) values
("Abebe Belay", "0331111234","PHP"),
("Kebede Kassa","09147855","Java"),
("Aster Awoke","0913456754","Python"),
("Teddy Afro","0331115678","Android");