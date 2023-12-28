create table ylb_user_role
(
    id          int auto_increment
        primary key,
    rolename    varchar(200) null comment '角色名称',
    description varchar(200) null comment '描述',
    create_time datetime     null comment '创建时间'
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_user_role (id, rolename, description, create_time) VALUES (1, 'admin', '管理员', null);
INSERT INTO blog.ylb_user_role (id, rolename, description, create_time) VALUES (2, 'user', '普通用户', null);
