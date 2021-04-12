create table if not exists tbl_companies
(
    id                    serial  not null
        constraint tbl_companies_pkey primary key,

    created_date          timestamptz,
    modified_date         timestamptz,

    name                  varchar,

    reference             uuid unique
);