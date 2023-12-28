create table ylb_user
(
    id          int auto_increment
        primary key,
    uid         varchar(200) not null,
    role_id     int          not null,
    username    varchar(100) not null,
    password    varchar(200) not null,
    login_time  datetime     not null,
    create_time datetime     not null,
    avatar      varchar(500) not null
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_user (id, uid, role_id, username, password, login_time, create_time, avatar) VALUES (42, '506a99761b1346c7b9c324121af54e3a', 1, 'wasd', '$2a$10$Sy.fsxZeaF6f.9wa5Mmege07TKfvuDtvVqZhk6rpYHYlMBz5E2zO2', '2023-07-05 16:54:28', '2023-07-05 16:54:28', '/images/avatar/man.png');
INSERT INTO blog.ylb_user (id, uid, role_id, username, password, login_time, create_time, avatar) VALUES (43, '506a99761b1346c7b9c324121af54e3h', 2, 'qwer', '$2a$10$Sy.fsxZeaF6f.9wa5Mmege07TKfvuDtvVqZhk6rpYHYlMBz5E2zO2', '2023-07-06 01:01:25', '2023-07-06 01:01:31', '/images/avatar/man.png');
