drop table if exists responsibles;

drop table if exists resolutions;

drop table if exists notifications;

drop table if exists tasks;

drop table if exists users;


create table users
(
    id         serial8 primary key,
    username   varchar(20) not null,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    password   varchar(20) not null,
    position   varchar(20) not null,
    role       varchar(20) not null
);

create table tasks
(
    id            serial8 primary key,
    author_id     int8 references users (id) on delete cascade ,
    creation_date timestamp   default now(),
    target_date   timestamp,
    finish_date   timestamp,
    status        varchar(20) default 'CREATED',
    type_task     varchar(20) not null,
    is_parallel   bool        default true,
    title         text,
    body          text
);

create table responsibles
(
    id                  serial8 primary key,
    task_id             int8 references tasks (id) on delete cascade ,
    responsible_user_id int8 references users (id) on delete cascade
);

create table resolutions
(
    id            serial8 primary key,
    creation_date timestamp default now(),
    text          text,
    author_id     int8 references users (id) on delete cascade ,
    task_id       int8 references tasks (id) on delete cascade ,
    status        varchar
);

create table notifications
(
    id            serial8 primary key,
    creation_date timestamp default now() not null,
    task_id       int8 references tasks (id) on delete cascade ,
    user_id       int8 references users (id) on delete cascade ,
    message       text                    not null,
    is_viewed     bool      default false
);


INSERT INTO users (username, first_name, last_name, password, position, role)
VALUES ('john_doe', 'John', 'Doe', 'password123', 'MANAGER', 'ADMIN'),
       ('jane_smith', 'Jane', 'Smith', 'password456', 'DEVELOPER', 'EMPLOYEE'),
       ('peter_jackson', 'Peter', 'Jackson', 'password789', 'DEPUTY_MANAGER', 'EMPLOYEE');

