--liquibase formatted sql

--changeset dior:3

create table location
(
    id        serial primary key,
    name      varchar not null,
    latitude  decimal not null,
    longitude decimal not null,
    user_id   int     not null references users(id)
);

--rollback drop table location;