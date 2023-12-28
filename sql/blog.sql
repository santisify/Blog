SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for ylb_blog
-- ----------------------------
DROP TABLE IF EXISTS `ylb_blog`;
CREATE TABLE `ylb_blog`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客id',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博客标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '正文',
  `sort` int NOT NULL COMMENT '排序  0普通 1置顶',
  `views` int NOT NULL COMMENT '浏览量',
  `author_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者id',
  `author_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者姓名',
  `author_avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者头像',
  `category_id` int NOT NULL COMMENT '分类id',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `original` int NOT NULL COMMENT '是否原创  0 否  1是',
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `indexfull_title_content`(`title`, `content`) WITH PARSER `ngram`
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_blog
-- ----------------------------
INSERT INTO `ylb_blog` VALUES (72, '236af638525b4b52a4561ebac02de3ae', '我的博客', '博客内容', 0, 1, '506a99761b1346c7b9c324121af54e3a', 'wasd', '/images/avatar/man.png', 1, '技术', '2023-07-05 16:55:44', '2023-07-05 16:55:44', 1);
INSERT INTO `ylb_blog` VALUES (73, '29b3308783e54cf994b26801097d88af', '随想录', '内容', 0, 1, '506a99761b1346c7b9c324121af54e3a', 'wasd', '/images/avatar/man.png', 1, '技术', '2023-07-05 16:56:10', '2023-07-05 16:56:10', 1);

-- ----------------------------
-- Table structure for ylb_blog_category
-- ----------------------------
DROP TABLE IF EXISTS `ylb_blog_category`;
CREATE TABLE `ylb_blog_category`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_blog_category
-- ----------------------------
INSERT INTO `ylb_blog_category` VALUES (1, '技术');
INSERT INTO `ylb_blog_category` VALUES (2, '提问');
INSERT INTO `ylb_blog_category` VALUES (3, '前端');
INSERT INTO `ylb_blog_category` VALUES (4, '后端');

-- ----------------------------
-- Table structure for ylb_chattab
-- ----------------------------
DROP TABLE IF EXISTS `ylb_chattab`;
CREATE TABLE `ylb_chattab`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ouid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_chattab
-- ----------------------------
INSERT INTO `ylb_chattab` VALUES (21, '506a99761b1346c7b9c324121af54e3h', '506a99761b1346c7b9c324121af54e3a');

-- ----------------------------
-- Table structure for ylb_collect
-- ----------------------------
DROP TABLE IF EXISTS `ylb_collect`;
CREATE TABLE `ylb_collect`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `article_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者id',
  `bid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_collect
-- ----------------------------

-- ----------------------------
-- Table structure for ylb_comment
-- ----------------------------
DROP TABLE IF EXISTS `ylb_comment`;
CREATE TABLE `ylb_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `comment_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论唯一id',
  `topic_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论文章id',
  `user_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论者id',
  `user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论者昵称',
  `user_avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论者头像',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 145 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_comment
-- ----------------------------
INSERT INTO `ylb_comment` VALUES (143, '3e0498f41bcf41c098f97505c4143386', '29b3308783e54cf994b26801097d88af', '506a99761b1346c7b9c324121af54e3h', 'qwer', '/images/avatar/man.png', '怀念啊', '2023-07-05 17:05:10');
INSERT INTO `ylb_comment` VALUES (144, 'f22e538c08e94a6891fbe964235fa3ab', '236af638525b4b52a4561ebac02de3ae', '506a99761b1346c7b9c324121af54e3h', 'qwer', '/images/avatar/man.png', '评论在这', '2023-07-05 17:15:22');

-- ----------------------------
-- Table structure for ylb_download
-- ----------------------------
DROP TABLE IF EXISTS `ylb_download`;
CREATE TABLE `ylb_download`  (
  `dname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名',
  `ddesc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源链接',
  `dcode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提取码'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_download
-- ----------------------------

-- ----------------------------
-- Table structure for ylb_follow_fans
-- ----------------------------
DROP TABLE IF EXISTS `ylb_follow_fans`;
CREATE TABLE `ylb_follow_fans`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `follower_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '被关注者用户id',
  `relation_type` int NOT NULL COMMENT '关系  1关注  2粉丝'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_follow_fans
-- ----------------------------
INSERT INTO `ylb_follow_fans` VALUES ('506a99761b1346c7b9c324121af54e3h', '506a99761b1346c7b9c324121af54e3a', 1);
INSERT INTO `ylb_follow_fans` VALUES ('506a99761b1346c7b9c324121af54e3a', '506a99761b1346c7b9c324121af54e3h', 2);

-- ----------------------------
-- Table structure for ylb_msg_store
-- ----------------------------
DROP TABLE IF EXISTS `ylb_msg_store`;
CREATE TABLE `ylb_msg_store`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `receiver_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收方uid',
  `sender_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送方uid',
  `msg_content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息体',
  `msg_time` datetime NOT NULL COMMENT '消息时间',
  `msg_type` int NOT NULL COMMENT '消息类型  0文本消息  1图片  2视频  3文件',
  `msg_isread` int NOT NULL COMMENT '是否已读  1已读  0未读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 222 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_msg_store
-- ----------------------------

-- ----------------------------
-- Table structure for ylb_user
-- ----------------------------
DROP TABLE IF EXISTS `ylb_user`;
CREATE TABLE `ylb_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` int NOT NULL,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `login_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_user
-- ----------------------------
INSERT INTO `ylb_user` VALUES (42, '506a99761b1346c7b9c324121af54e3a', 1, 'wasd', '$2a$10$Sy.fsxZeaF6f.9wa5Mmege07TKfvuDtvVqZhk6rpYHYlMBz5E2zO2', '2023-07-05 16:54:28', '2023-07-05 16:54:28', '/images/avatar/man.png');
INSERT INTO `ylb_user` VALUES (43, '506a99761b1346c7b9c324121af54e3h', 2, 'qwer', '$2a$10$Sy.fsxZeaF6f.9wa5Mmege07TKfvuDtvVqZhk6rpYHYlMBz5E2zO2', '2023-07-06 01:01:25', '2023-07-06 01:01:31', '/images/avatar/man.png');

-- ----------------------------
-- Table structure for ylb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ylb_user_info`;
CREATE TABLE `ylb_user_info`  (
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `brief` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_user_info
-- ----------------------------
INSERT INTO `ylb_user_info` VALUES ('506a99761b1346c7b9c324121af54e3a', '轻轻的草原', NULL, NULL, NULL, NULL, NULL, NULL, '18838980203@163.com', NULL);
INSERT INTO `ylb_user_info` VALUES ('506a99761b1346c7b9c324121af54e3h', 'l鹭洲', NULL, NULL, NULL, NULL, NULL, NULL, '2289533269@qq.com', NULL);

-- ----------------------------
-- Table structure for ylb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ylb_user_role`;
CREATE TABLE `ylb_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rolename` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ylb_user_role
-- ----------------------------
INSERT INTO `ylb_user_role` VALUES (1, 'admin', '管理员', NULL);
INSERT INTO `ylb_user_role` VALUES (2, 'user', '普通用户', NULL);

SET FOREIGN_KEY_CHECKS = 1;
