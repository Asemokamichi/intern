drop table if exists resolutions;

drop table if exists tasks;

create table tasks
(
    id            serial8 primary key,
    author_id     int8  ,
    creation_date timestamp   default now(),
    target_date   timestamp,
    finish_date   timestamp,
    status        varchar(20) default 'CREATED',
    type_task     varchar(20) not null,
    is_parallel   bool        default true,
    responsibles  varchar(100000),
    title         text,
    body          text
);

create table resolutions
(
    id            serial8 primary key,
    creation_date timestamp default now(),
    text          text,
    author_id     int8 ,
    task_id       int8 references tasks (id) on delete cascade,
    status        varchar
);