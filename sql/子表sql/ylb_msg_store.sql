create table ylb_msg_store
(
    id           int auto_increment comment '消息id'
        primary key,
    receiver_uid varchar(255) not null comment '接收方uid',
    sender_uid   varchar(255) not null comment '发送方uid',
    msg_content  varchar(500) not null comment '消息体',
    msg_time     datetime     not null comment '消息时间',
    msg_type     int          not null comment '消息类型  0文本消息  1图片  2视频  3文件',
    msg_isread   int          not null comment '是否已读  1已读  0未读'
)
    charset = utf8mb3
    row_format = DYNAMIC;

