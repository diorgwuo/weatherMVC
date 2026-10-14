--liquibase formatted sql

--changeset dior:1

create table "users"
(
    id       serial primary key,
    login    varchar not null unique,
    password varchar not null

);

--rollback drop table users;