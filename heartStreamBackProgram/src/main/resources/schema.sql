-- 创建数据库
-- 数据库名可以自定义
CREATE DATABASE IF NOT EXISTS my_database;
-- 使用数据库
USE my_database;
-- 创建表

-- 创建用户表
DROP TABLE
IF
	EXISTS `user`;
CREATE TABLE `user` (
	`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`username` VARCHAR ( 20 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
	`password` VARCHAR ( 20 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
	`sex` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别 0 保密 1 男 2 女',
	`email` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
	`image` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0.jpg' COMMENT '头像',
	PRIMARY KEY ( `ID` ) USING BTREE,
	UNIQUE INDEX `username` ( `username` ASC ) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

INSERT INTO `user`
VALUES
	( 1, 'zhangsan', 'zp10086', 0, 'zhangsan7743@qq.com', 'userImage/1.jpg' );
INSERT INTO `user`
VALUES
	( 2, 'liming', 'xyz112233', 1, 'XYMing21@qq.com', 'userImage/2_微信图片_20240607100552.jpg' );
INSERT INTO `user`
VALUES
	( 3, 'MiYa', 'newPass123', 2, '1234@qq.com', 'userImage/3_53df52839031be60a4.jpg' );
INSERT INTO `user`
VALUES
	( 4, 'Amiya', 'ARK1234567', 2, 'zz7749@qq.com', '0.jpg' );
INSERT INTO `user`
VALUES
	( 5, 'MiMi', 'xxx12345', 1, '12345@qq.com', '0.jpg' );

-- 创建音乐表
DROP TABLE
IF
	EXISTS `music`;
CREATE TABLE `music` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`title` VARCHAR ( 60 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '歌名',
	`artist` VARCHAR ( 60 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '艺术家',
	`album` VARCHAR ( 60 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专辑',
	`release_date` DATE NULL DEFAULT NULL COMMENT '发行日期',
	`file_path` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '储存路径',
	`image` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'musicImage/3cb09323e122849d3c77b8938465547.jpg' COMMENT '图片',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '音乐' ROW_FORMAT = Dynamic;

INSERT INTO `music`
VALUES
	( 1, '死灰复燃', '灯诱LampLure', '人生逃避號', '2024-03-22', 'musicSong/灯诱LampLure - 死灰复燃.mp3', 'musicImage/2563d5893877525af550544766af9a1.jpg' );
INSERT INTO `music`
VALUES
	( 2, 'Dive Back In Time', '白鲨JAWS', '时光代理人', '2021-05-07', 'musicSong/白鲨JAWS - Dive Back In Time.mp3', 'musicImage/d983a5806135d414d2ff09222d7e1dd.jpg' );
INSERT INTO `music`
VALUES
	( 3, 'The Bird Song', 'Noah Floersch', 'The Bird Song', '2024-08-23', 'musicSong/Noah Floersch - The Bird Song.mp3', 'musicImage/d801f6db9557aba333974c23bf871b6.jpg' );
INSERT INTO `music`
VALUES
	( 4, '♡B l u e ℉', 'BlueLee', '藍色的溫度', '2019-06-24', 'musicSong/BlueLee - ♡B l u e ℉.mp3', 'musicImage/8fc8cb475007c481d28eaae8371417a.jpg' );
INSERT INTO `music`
VALUES
	( 5, '人生逃避號', '灯诱LampLure', '人生躲避號', '2024-03-22', 'musicSong/灯诱LampLure - 人生逃避號.mp3', 'musicImage/2563d5893877525af550544766af9a1.jpg' );

-- 创建音乐种类表
DROP TABLE
IF
	EXISTS `category`;
CREATE TABLE `category` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`name` VARCHAR ( 30 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别',
	PRIMARY KEY ( `id` ) USING BTREE,
	UNIQUE INDEX `name` ( `name` ASC ) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类栏目' ROW_FORMAT = Dynamic;

INSERT INTO `category`
VALUES
	( 1, '孤独' );
INSERT INTO `category`
VALUES
	( 2, '忧伤' );
INSERT INTO `category`
VALUES
	( 6, '抑郁' );
INSERT INTO `category`
VALUES
	( 4, '欢快' );
INSERT INTO `category`
VALUES
	( 5, '治愈' );
INSERT INTO `category`
VALUES
	( 3, '活力' );

-- 创建小组表
DROP TABLE
IF
	EXISTS `user_group`;
CREATE TABLE `user_group` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`name` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小组名',
	`goal` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '目标',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '小组' ROW_FORMAT = Dynamic;

INSERT INTO `user_group`
VALUES
	( 1, 'Happy', 'happying', '2025-04-22 14:42:54' );
INSERT INTO `user_group`
VALUES
	( 2, 'Great', 'happy time', '2025-04-22 14:43:31' );
INSERT INTO `user_group`
VALUES
	( 3, 'Tech Enthusiasts', 'Share tech knowledge', '2025-04-23 21:14:37' );

-- 创建帖子表
DROP TABLE
IF
	EXISTS `post`;
CREATE TABLE `post` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`title` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '帖子内容',
	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `post_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `post`
VALUES
	( 1, 1, 'Teat Title', 'This is s test post', NULL );
INSERT INTO `post`
VALUES
	( 2, 2, 'goodTime', 'Happy', '2025-04-21 23:58:26' );

-- 创建小组帖子表
DROP TABLE
IF
	EXISTS `group_post`;
CREATE TABLE `group_post` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_id` BIGINT NOT NULL COMMENT '小组ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `group_id` ( `group_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `group_post_ibfk_1` FOREIGN KEY ( `group_id` ) REFERENCES `user_group` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `group_post_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '小组帖子' ROW_FORMAT = Dynamic;

-- 创建帖子评论表
DROP TABLE
IF
	EXISTS `post_comment`;
CREATE TABLE `post_comment` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`post_id` BIGINT NOT NULL COMMENT '帖子ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `post_id` ( `post_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `post_comment_ibfk_1` FOREIGN KEY ( `post_id` ) REFERENCES `post` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `post_comment_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子评论' ROW_FORMAT = Dynamic;

-- 创建回复表
DROP TABLE
IF
	EXISTS `reply`;
CREATE TABLE `reply` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`post_id` BIGINT NOT NULL COMMENT '帖子ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
	`created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `post_id` ( `post_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `reply_ibfk_1` FOREIGN KEY ( `post_id` ) REFERENCES `post` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `reply_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '回复' ROW_FORMAT = Dynamic;

INSERT INTO `reply`
VALUES
	( 1, 1, 3, 'Good', NULL );
INSERT INTO `reply`
VALUES
	( 2, 1, 5, 'Great', NULL );

-- 创建小组帖子评论表
DROP TABLE
IF
	EXISTS `group_post_comment`;
CREATE TABLE `group_post_comment` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_post_id` BIGINT NOT NULL COMMENT '小组帖子ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `group_post_id` ( `group_post_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `group_post_comment_ibfk_1` FOREIGN KEY ( `group_post_id` ) REFERENCES `group_post` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `group_post_comment_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '小组帖子评论' ROW_FORMAT = Dynamic;

-- 创建日记表
DROP TABLE
IF
	EXISTS `diary`;
CREATE TABLE `diary` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`title` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
	`is_published` TINYINT ( 1 ) NULL DEFAULT 0 COMMENT '是否发布 0 未发布 1 已发布',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
	`update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
	`like_count` INT NULL DEFAULT 0,
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `diary_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日记' ROW_FORMAT = Dynamic;

INSERT INTO `diary`
VALUES
	( 1, 1, 'My First Diary', 'Good Day!', 0, '2025-04-05 16:57:29', '2025-04-09 21:40:15', 2 );
INSERT INTO `diary`
VALUES
	( 2, 1, 'My First Diary', 'This is my first diary entry.', 1, '2025-04-05 17:15:47', '2025-04-09 21:40:51', 4 );
INSERT INTO `diary`
VALUES
	( 3, 2, 'Greate', 'Well', 1, '2025-04-09 21:25:48', '2025-04-09 21:25:48', 0 );
INSERT INTO `diary`
VALUES
	( 4, 4, 'Thanks', 'Greate App', 1, '2025-04-09 21:26:33', '2025-04-09 21:26:33', 0 );
INSERT INTO `diary`
VALUES
	( 5, 3, 'Ark', 'Arknights', 1, '2025-04-09 21:27:07', '2025-04-09 21:27:07', 0 );

-- 创建评论表
DROP TABLE
IF
	EXISTS `comment`;
CREATE TABLE `comment` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`diary_id` BIGINT NOT NULL COMMENT '日记ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `diary_id` ( `diary_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `comment_ibfk_1` FOREIGN KEY ( `diary_id` ) REFERENCES `diary` ( `id` ) ON DELETE CASCADE ON UPDATE RESTRICT,
	CONSTRAINT `comment_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论' ROW_FORMAT = Dynamic;

INSERT INTO `comment`
VALUES
	( 1, 2, 2, 'Great diary!', '2025-04-09 21:47:28' );
INSERT INTO `comment`
VALUES
	( 2, 2, 3, 'Good!', '2025-04-09 21:47:51' );
INSERT INTO `comment`
VALUES
	( 3, 5, 3, 'Well!', '2025-04-09 21:48:19' );

-- 创建日记浏览记录表
DROP TABLE
IF
	EXISTS `diary_record`;
CREATE TABLE `diary_record` (
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`diary_id` BIGINT NOT NULL COMMENT '日记ID',
	`read_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	INDEX `diary_id` ( `diary_id` ASC ) USING BTREE,
	CONSTRAINT `diary_record_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `diary_record_ibfk_2` FOREIGN KEY ( `diary_id` ) REFERENCES `diary` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日记浏览记录' ROW_FORMAT = Dynamic;

-- 创建音乐分类表
DROP TABLE
IF
	EXISTS `music_categories`;
CREATE TABLE `music_categories` (
	`music_id` INT UNSIGNED NOT NULL COMMENT '音乐ID',
	`category_id` INT UNSIGNED NOT NULL COMMENT '类别ID',
	PRIMARY KEY ( `music_id`, `category_id` ) USING BTREE,
	INDEX `category_id` ( `category_id` ASC ) USING BTREE,
	CONSTRAINT `music_categories_ibfk_1` FOREIGN KEY ( `music_id` ) REFERENCES `music` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `music_categories_ibfk_2` FOREIGN KEY ( `category_id` ) REFERENCES `category` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '音乐分类' ROW_FORMAT = Dynamic;

INSERT INTO `music_categories`
VALUES
	( 1, 1 );
INSERT INTO `music_categories`
VALUES
	( 4, 1 );
INSERT INTO `music_categories`
VALUES
	( 1, 2 );
INSERT INTO `music_categories`
VALUES
	( 2, 3 );
INSERT INTO `music_categories`
VALUES
	( 2, 4 );
INSERT INTO `music_categories`
VALUES
	( 3, 4 );
INSERT INTO `music_categories`
VALUES
	( 4, 5 );
INSERT INTO `music_categories`
VALUES
	( 4, 6 );

-- 创建音乐播放记录表
DROP TABLE
IF
	EXISTS `music_record`;
CREATE TABLE `music_record` (
	`user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
	`music_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '音乐ID',
	`time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	INDEX `music_id` ( `music_id` ASC ) USING BTREE,
	CONSTRAINT `music_record_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `music_record_ibfk_2` FOREIGN KEY ( `music_id` ) REFERENCES `music` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '播放记录' ROW_FORMAT = Dynamic;

-- 创建歌单表
DROP TABLE
IF
	EXISTS `playlist`;
CREATE TABLE `playlist` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`name` VARCHAR ( 255 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '歌单名',
	`user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
	`create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`ispublished` TINYINT ( 1 ) NULL DEFAULT 0 COMMENT '是否发布 0 未发布 1 已发布',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `playlist_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '歌单' ROW_FORMAT = Dynamic;

INSERT INTO `playlist`
VALUES
	( 1, '欢快', NULL, '2025-04-06 20:15:55', '2025-04-07 17:44:58', 1 );
INSERT INTO `playlist`
VALUES
	( 2, '孤独', NULL, '2025-04-06 20:17:54', '2025-04-07 17:45:02', 1 );
INSERT INTO `playlist`
VALUES
	( 4, '我的歌单', 1, '2025-04-07 21:12:08', '2025-04-07 21:12:08', 0 );

-- 创建歌单歌曲表
DROP TABLE
IF
	EXISTS `playlist_music`;
CREATE TABLE `playlist_music` (
	`playlist_id` BIGINT NOT NULL COMMENT '歌单ID',
	`music_id` INT UNSIGNED NOT NULL COMMENT '音乐ID',
	`add_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
	PRIMARY KEY ( `playlist_id`, `music_id` ) USING BTREE,
	INDEX `music_id` ( `music_id` ASC ) USING BTREE,
	CONSTRAINT `playlist_music_ibfk_1` FOREIGN KEY ( `playlist_id` ) REFERENCES `playlist` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `playlist_music_ibfk_2` FOREIGN KEY ( `music_id` ) REFERENCES `music` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '歌单音乐' ROW_FORMAT = Dynamic;

INSERT INTO `playlist_music`
VALUES
	( 1, 2, '2025-04-06 20:16:51' );
INSERT INTO `playlist_music`
VALUES
	( 1, 3, '2025-04-06 20:16:57' );
INSERT INTO `playlist_music`
VALUES
	( 2, 1, '2025-04-06 20:18:41' );
INSERT INTO `playlist_music`
VALUES
	( 2, 4, '2025-04-06 20:18:48' );
INSERT INTO `playlist_music`
VALUES
	( 4, 1, '2025-04-07 21:20:40' );
INSERT INTO `playlist_music`
VALUES
	( 4, 3, '2025-04-07 21:21:31' );

-- 创建心情日记表
DROP TABLE
IF
	EXISTS `mood_entry`;
CREATE TABLE `mood_entry` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
	`date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日期',
	`mood` VARCHAR ( 10 ) CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '心情',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `mood_entry_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '心情日历' ROW_FORMAT = Dynamic;

INSERT INTO `mood_entry`
VALUES
	( 1, 1, '2025-04-14 17:13:35', 'Happy' );
INSERT INTO `mood_entry`
VALUES
	( 2, 1, '2025-04-15 17:14:03', 'Sad' );
INSERT INTO `mood_entry`
VALUES
	( 3, 3, '2025-04-15 17:14:17', 'Calm' );
INSERT INTO `mood_entry`
VALUES
	( 4, 2, '2025-04-15 17:46:53', 'Happy' );

-- 创建收藏表
DROP TABLE
IF
	EXISTS `favorite`;
CREATE TABLE `favorite` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`post_id` BIGINT NOT NULL COMMENT '帖子ID',
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	INDEX `post_id` ( `post_id` ASC ) USING BTREE,
	CONSTRAINT `favorite_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `favorite_ibfk_2` FOREIGN KEY ( `post_id` ) REFERENCES `post` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏' ROW_FORMAT = Dynamic;

-- 创建经验分享表
DROP TABLE
IF
	EXISTS `experience_share`;
CREATE TABLE `experience_share` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`group_id` BIGINT NOT NULL,
	`content` TEXT CHARACTER
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
	`share_time` DATETIME NULL DEFAULT NULL,
	`user_id` INT UNSIGNED NULL DEFAULT NULL,
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `experience_share_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `experience_share`
VALUES
	( 1, 1, 'Happy Happy', NULL, 1 );
INSERT INTO `experience_share`
VALUES
	( 2, 1, '这是一条经验分享', '2025-04-23 20:12:05', 1 );

-- 创建小组成员表
DROP TABLE
IF
	EXISTS `group_member`;
CREATE TABLE `group_member` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_id` BIGINT NOT NULL COMMENT '小组ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`join_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `group_id` ( `group_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `group_member_ibfk_1` FOREIGN KEY ( `group_id` ) REFERENCES `user_group` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `group_member_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组员' ROW_FORMAT = Dynamic;

INSERT INTO `group_member`
VALUES
	( 1, 1, 3, '2025-04-23 20:17:30' );
INSERT INTO `group_member`
VALUES
	( 2, 1, 1, '2025-04-23 20:18:16' );
INSERT INTO `group_member`
VALUES
	( 3, 1, 4, '2025-04-23 20:20:47' );
INSERT INTO `group_member`
VALUES
	( 4, 3, 4, '2025-04-23 21:14:37' );

-- 创建打卡记录表
DROP TABLE
   IF
   	EXISTS `punch_record`;
   CREATE TABLE `punch_record` (
   	`id` BIGINT NOT NULL AUTO_INCREMENT,
   	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
   	`group_id` BIGINT NOT NULL COMMENT 'group_id',
   	`punch_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '打卡时间',
   	PRIMARY KEY ( `id` ) USING BTREE,
   	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
   	INDEX `group_id` ( `group_id` ASC ) USING BTREE,
   	CONSTRAINT `punch_record_ibfk_1` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
   	CONSTRAINT `punch_record_ibfk_2` FOREIGN KEY ( `group_id` ) REFERENCES `user_group` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT
   ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER
   SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '小组打卡' ROW_FORMAT = Dynamic;

   INSERT INTO `punch_record`
   VALUES
   	( 1, 1, 1, '2025-04-23 21:05:04' );

-- 创建小组打卡表
DROP TABLE
IF
	EXISTS `group_checkin`;
CREATE TABLE `group_checkin` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_id` BIGINT NOT NULL COMMENT '小组ID',
	`user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
	`checkin_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	`is_checked` TINYINT ( 1 ) NOT NULL,
	PRIMARY KEY ( `id` ) USING BTREE,
	INDEX `group_id` ( `group_id` ASC ) USING BTREE,
	INDEX `user_id` ( `user_id` ASC ) USING BTREE,
	CONSTRAINT `group_checkin_ibfk_1` FOREIGN KEY ( `group_id` ) REFERENCES `user_group` ( `id` ) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT `group_checkin_ibfk_2` FOREIGN KEY ( `user_id` ) REFERENCES `user` ( `ID` ) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '打卡' ROW_FORMAT = Dynamic;