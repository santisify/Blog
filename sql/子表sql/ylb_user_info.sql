create table ylb_user_info
(
    uid      varchar(255) not null,
    nickname varchar(255) null,
    sex      varchar(10)  null,
    brief    varchar(255) null,
    work     varchar(100) null,
    phone    varchar(255) null,
    qq       varchar(255) null,
    wechat   varchar(255) null,
    email    varchar(255) null,
    addr     varchar(255) null
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_user_info (uid, nickname, sex, brief, work, phone, qq, wechat, email, addr) VALUES ('506a99761b1346c7b9c324121af54e3a', 'jdj', null, null, null, null, null, null, '18838980203@163.com', null);
INSERT INTO blog.ylb_user_info (uid, nickname, sex, brief, work, phone, qq, wechat, email, addr) VALUES ('506a99761b1346c7b9c324121af54e3h', 'l鹭洲', null, '', '学生', '', '', '', '123456', '四川');
