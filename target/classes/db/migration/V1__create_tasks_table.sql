create table tasks (
id serial primary key,
subject varchar(50),
message varchar(300),
priority int not null,
created_by int not null,
created_on date not null default current_date,
user_id int,
task_active boolean default true,
close_message varchar (300)
);