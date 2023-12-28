create table ylb_follow_fans
(
    user_id       varchar(255) not null comment '用户id',
    follower_id   varchar(255) not null comment '被关注者用户id',
    relation_type int          not null comment '关系  1关注  2粉丝'
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_follow_fans (user_id, follower_id, relation_type) VALUES ('506a99761b1346c7b9c324121af54e3a', '506a99761b1346c7b9c324121af54e3h', 1);
INSERT INTO blog.ylb_follow_fans (user_id, follower_id, relation_type) VALUES ('506a99761b1346c7b9c324121af54e3h', '506a99761b1346c7b9c324121af54e3a', 2);
INSERT INTO blog.ylb_follow_fans (user_id, follower_id, relation_type) VALUES ('506a99761b1346c7b9c324121af54e3h', '506a99761b1346c7b9c324121af54e3a', 1);
INSERT INTO blog.ylb_follow_fans (user_id, follower_id, relation_type) VALUES ('506a99761b1346c7b9c324121af54e3a', '506a99761b1346c7b9c324121af54e3h', 2);
