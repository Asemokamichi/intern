drop table if exists notifications;

create table notifications
(
    id            serial8 primary key,
    creation_date timestamp default now() not null,
    object_id       int8,
    user_id       int8                    not null,
    message       text                    not null,
    is_viewed     bool      default false
);
