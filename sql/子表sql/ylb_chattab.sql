create table ylb_chattab
(
    id   int auto_increment
        primary key,
    uid  varchar(255) null,
    ouid varchar(255) null
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_chattab (id, uid, ouid) VALUES (21, '506a99761b1346c7b9c324121af54e3h', '506a99761b1346c7b9c324121af54e3a');
