create table persistent_logins
(
    username  varchar(64)                         not null,
    series    varchar(64)                         not null
        primary key,
    token     varchar(64)                         not null,
    last_used timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
)
    charset = utf8mb3
    row_format = DYNAMIC;

