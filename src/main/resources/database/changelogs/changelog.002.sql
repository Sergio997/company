create table if not exists tbl_users
(
    id            serial      not null
    constraint tbl_users_pkey primary key,

    email         varchar(60) not null unique,
    firstname     varchar(60) not null,
    lastname      varchar(60) not null,
    password      varchar(255),
    enabled       boolean,
    role          varchar(60),

    version       integer     not null default 0,
    created_date  timestamptz,
    modified_date timestamptz,
    created_by    varchar(100),
    modified_by   varchar(100),
    reference     uuid        not null unique
    );