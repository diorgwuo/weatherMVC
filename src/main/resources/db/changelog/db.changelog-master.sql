--liquibase formatted sql

--changeset dior:1

create table "user"
(
    id       serial primary key,
    login    varchar not null unique,
    password varchar not null

);

--changeset dior:2

create table location
(
    id        serial primary key,
    name      varchar not null,
    latitude  decimal not null,
    longitude decimal not null,
    user_id   int     not null references "user"(id)
);

--changeset dior:3

create table session
(
    id         uuid primary key,
    expires_at timestamp with time zone not null,
    user_id    int not null references "user"(id)
);