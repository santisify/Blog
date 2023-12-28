create table ylb_collect
(
    id         int auto_increment
        primary key,
    uid        varchar(255) not null comment '用户id',
    article_id varchar(255) not null comment '作者id',
    bid        varchar(255) not null comment '文章id'
)
    charset = utf8mb3
    row_format = DYNAMIC;

