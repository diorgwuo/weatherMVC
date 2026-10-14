--liquibase formatted sql

--changeset dior:2

create table session
(
    id         uuid primary key,
    expires_at timestamp with time zone not null,
    user_id    int not null references users(id)
);

--rollback drop table session;