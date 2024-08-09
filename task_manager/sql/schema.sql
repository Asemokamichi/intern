drop table if exists resolutions;

drop table if exists notifications;

drop table if exists tasks;

drop table if exists users;


create table users
(
    id         serial8 primary key,
    username   varchar not null,
    first_name varchar not null,
    last_name  varchar not null,
    password   varchar not null,
    position   varchar not null,
    role       varchar not null
);

create table tasks
(
    id            serial8 primary key,
    author_id     int8 references users (id) on delete cascade,
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
    author_id     int8 references users (id) on delete cascade,
    task_id       int8 references tasks (id) on delete cascade,
    status        varchar
);

create table notifications
(
    id            serial8 primary key,
    creation_date timestamp default now() not null,
    task_id       int8 references tasks (id) on delete cascade,
    user_id       int8 references users (id) on delete cascade,
    message       text                    not null,
    is_viewed     bool      default false
);


INSERT INTO users (username, first_name, last_name, password, position, role)
VALUES ('john_doe', 'John', 'Doe', 'password123', 'MANAGER', 'ADMIN'),
       ('jane_smith', 'Jane', 'Smith', 'password456', 'DEVELOPER', 'EMPLOYEE'),
       ('peter_jackson', 'Peter', 'Jackson', 'password789', 'DEPUTY_MANAGER', 'EMPLOYEE');


INSERT INTO users (username, first_name, last_name, password, position, role)
VALUES ('assem_doe', 'John', 'Doe', 'password123', 'MANAGER', 'ADMIN'),
       ('zhad_smith', 'Jane', 'Smith', 'password456', 'DEVELOPER', 'EMPLOYEE');

DO
$$
    DECLARE
        i INT := 1;
    BEGIN
        WHILE i <= 10000
            LOOP
                INSERT INTO users (username, first_name, last_name, password, position, role)
                VALUES ('user_' || i, -- username
                        'first_name_' || i, -- first_name
                        'last_name_' || i, -- last_name
                        md5(random()::text), -- password (хэшируется для безопасности)
                        (CASE
                             WHEN i % 5 = 0 THEN 'Manager'
                             WHEN i % 5 = 1 THEN 'Developer'
                             WHEN i % 5 = 2 THEN 'Tester'
                             WHEN i % 5 = 3 THEN 'Analyst'
                             ELSE 'Support'
                            END), -- position (разные позиции для разнообразия)
                        (CASE
                             WHEN i % 2 = 0 THEN 'ADMIN'
                             ELSE 'USER'
                            END) -- role (чередование ролей)
                       );
                i := i + 1;
            END LOOP;
    END
$$;
