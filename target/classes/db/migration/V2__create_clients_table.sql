create table users (
id serial primary key,
login varchar(30) not null,
password varchar(30) not null,
email varchar(40),
role varchar(15) not null,
active boolean default true,
task_id int
);