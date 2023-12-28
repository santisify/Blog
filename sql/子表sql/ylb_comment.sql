create table ylb_comment
(
    id          int auto_increment comment '自增id'
        primary key,
    comment_id  varchar(200) not null comment '评论唯一id',
    topic_id    varchar(200) not null comment '评论文章id',
    user_id     varchar(200) not null comment '评论者id',
    user_name   varchar(200) not null comment '评论者昵称',
    user_avatar varchar(500) not null comment '评论者头像',
    content     longtext     not null comment '评论内容',
    create_time datetime     not null comment '评论创建时间'
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_comment (id, comment_id, topic_id, user_id, user_name, user_avatar, content, create_time) VALUES (143, '3e0498f41bcf41c098f97505c4143386', '29b3308783e54cf994b26801097d88af', '506a99761b1346c7b9c324121af54e3h', 'qwer', '/images/avatar/man.png', '怀念啊', '2023-07-05 17:05:10');
INSERT INTO blog.ylb_comment (id, comment_id, topic_id, user_id, user_name, user_avatar, content, create_time) VALUES (144, 'f22e538c08e94a6891fbe964235fa3ab', '236af638525b4b52a4561ebac02de3ae', '506a99761b1346c7b9c324121af54e3h', 'qwer', '/images/avatar/man.png', '评论在这', '2023-07-05 17:15:22');
INSERT INTO blog.ylb_comment (id, comment_id, topic_id, user_id, user_name, user_avatar, content, create_time) VALUES (145, '2a977be6d6a44fdda222c80bab71410c', 'e9d0680ca82c4e28816c5b1ee272702b', '506a99761b1346c7b9c324121af54e3h', 'qwer', '/images/avatar/man.png', '杨曹俊tql', '2023-12-28 07:34:22');
