create table ylb_blog_category
(
    id       int auto_increment
        primary key,
    category varchar(50) null comment '博客分类'
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO blog.ylb_blog_category (id, category) VALUES (1, '技术');
INSERT INTO blog.ylb_blog_category (id, category) VALUES (2, '提问');
INSERT INTO blog.ylb_blog_category (id, category) VALUES (3, '前端');
INSERT INTO blog.ylb_blog_category (id, category) VALUES (4, '后端');
