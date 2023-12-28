create table ylb_download
(
    dname varchar(100) not null comment '资源名',
    ddesc varchar(500) not null comment '资源链接',
    dcode varchar(50)  not null comment '提取码'
)
    charset = utf8mb3
    row_format = DYNAMIC;

