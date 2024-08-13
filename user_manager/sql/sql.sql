drop table if exists users;
drop table if exists departments;

create table departments
(
    id serial8 primary key,
    title varchar(100) not null UNIQUE ,
    head_id  varchar(20),
    parent_department_id   integer,
    creation_date    varchar(30) not null,
    modification_date varchar(30) not null,
    foreign key (parent_department_id) references departments(id)
);



create table users
(
    id         serial8 primary key,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    contacts  varchar(100),

    position   varchar(20) not null,
    department_id   serial8,
    role       varchar(20) not null,
    creation_date    varchar(30) not null,
    modification_date varchar(30) not null,
    is_active BOOLEAN,
    FOREIGN KEY (department_id) REFERENCES departments(id)
);