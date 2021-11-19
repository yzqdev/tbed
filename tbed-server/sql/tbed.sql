/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : tbed

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 17/11/2021 10:33:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for album
-- ----------------------------
DROP TABLE IF EXISTS `album`;
CREATE TABLE `album`  (
  `album_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `album_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of album
-- ----------------------------

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of code
-- ----------------------------

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sourcekey` int NULL DEFAULT NULL,
  `emails` int NULL DEFAULT NULL COMMENT '邮箱配置',
  `webname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站名',
  `explain` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `video` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `backtype` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '背景类型',
  `links` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notice` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `baidu` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `domain` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `background1` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `background2` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sett` int NOT NULL COMMENT '首页样式',
  `webms` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `webkeywords` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `webfavicons` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `theme` int NULL DEFAULT 1 COMMENT '主题',
  `websubtitle` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `logo` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `aboutinfo` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 7, 1, 'Hellohao图床', 'Hellohao图像托管，是一家免费开源的图像托管，即时分享你的美好瞬间。', 'https://hellohao-cloud.oss-cn-beijing.aliyuncs.com/books_x264.mp4http://cdn.wwery.com/Hellohao/439550606022825.jpg', '1', '<p style=\"color:#7c7c88;\">&copy; 2019 <a href=\"http://www.Hellohao.cn/\" target=\"_blank\" title=\"Hellohao\">Hellohao</a><span>&nbsp;&nbsp;- All Rights Reserved</span> </p>', '也许...|这将是最好用的图床|为了更好的用户体验，建议您注册本站继续免费使用Hellohao图床。本站不得上传任何形式的非法图片，一旦发现，永久删除并禁封账户。情节严重者将相关资料交于相关部门处理。', 'var _hmt = _hmt || [];\n(function() {\n  var hm = document.createElement(\"script\");\n  hm.src = \"https://hm.baidu.com/hm.js?16c326e608c984c9bae6a63eda861888\";\n  var s = document.getElementsByTagName(\"script\")[0]; \n  s.parentNode.insertBefore(hm, s);\n})();\n\n', 'http://api.hellohao.cn', '', 'http://cdn.wwery.com/Hellohao/3e1901120091153.jpg', 1, 'Hellohao图像托管，是一家免费开源的图像托管，即时分享你的美好瞬间。', 'hellohao图床,图床,图片上传,开源图床,hellohao,图像托管，图片分享', 'https://hellohao.nos-eastchina1.126.net/BlogImg/favicon.ico', 2, '这将是你用过最优秀的图像托管程序', NULL, '<img width=\"300px\" src=\"http://img.wwery.com/Hellohao/rPscRYwz.png\">\n            <br />\n            <br />\n            <p>也许,这将是你用到最优秀的图像托管程序</p>\n            <p>本程序为Hellohao图象托管程序</p>\n            <br/>\n            <p style=\"color: #656565;\">作者：hellohao独立开发</p>\n            <p style=\"color: #656565;\">www.hellohao.cn</p>');

-- ----------------------------
-- Table structure for email_config
-- ----------------------------
DROP TABLE IF EXISTS `email_config`;
CREATE TABLE `email_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `emails` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权码',
  `email_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务器',
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口',
  `email_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `using` int NULL DEFAULT NULL COMMENT '1为可用，其他为不使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of email_config
-- ----------------------------
INSERT INTO `email_config` VALUES (1, '', '', '', '465', 'Hellohao图床', 0);

-- ----------------------------
-- Table structure for siteGroup
-- ----------------------------
DROP TABLE IF EXISTS `siteGroup`;
CREATE TABLE `siteGroup`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组名称',
  `key_id` int NULL DEFAULT NULL,
  `user_type` int NULL DEFAULT NULL,
  `compress` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of siteGroup
-- ----------------------------
INSERT INTO `siteGroup` VALUES (1, '默认群组', 5, 0, 0);

-- ----------------------------
-- Table structure for imgandalbum
-- ----------------------------
DROP TABLE IF EXISTS `imgandalbum`;
CREATE TABLE `imgandalbum`  (
  `imgname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `albumkey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notes` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT ' '
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of imgandalbum
-- ----------------------------
INSERT INTO `imgandalbum` VALUES ('http://img.wwery.com/ccx51/5GBv93Ks.jpg', 'TOALBUM228edN', NULL);

-- ----------------------------
-- Table structure for imgdata
-- ----------------------------
DROP TABLE IF EXISTS `imgdata`;
CREATE TABLE `imgdata`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `img_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片名',
  `img_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片链接',
  `user_id` int NULL DEFAULT NULL COMMENT '用户名',
  `update_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传时间',
  `sizes` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abnormal` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source` int NULL DEFAULT NULL COMMENT '存储源',
  `img_type` int NULL DEFAULT NULL,
  `explains` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `md5key` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `about` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `violation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_md5key_url`(`md5key`(255), `img_url`(255)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPRESSED;

-- ----------------------------
-- Records of imgdata
-- ----------------------------
INSERT INTO `imgdata` VALUES (14, 'tourist/H6u3reQy.jpg', 'http://127.0.0.1:8089/ota/tourist/H6u3reQy.jpg', 0, '2021-11-16 13:58:12', '2515536', '0:0:0:0:0:0:0:1', 5, 0, NULL, '5e3da6fd2c44645fa428af9293eaf685', 'da6edcc903fb42fc8d3aaa80c46b862d', 'image/jpeg', NULL, NULL);

-- ----------------------------
-- Table structure for imgreview
-- ----------------------------
DROP TABLE IF EXISTS `imgreview`;
CREATE TABLE `imgreview`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `api_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Using` int NULL DEFAULT NULL,
  `count` int NULL DEFAULT NULL COMMENT '拦截数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of imgreview
-- ----------------------------
INSERT INTO `imgreview` VALUES (1, '', '', '', 0, 0);

-- ----------------------------
-- Table structure for imgtemp
-- ----------------------------
DROP TABLE IF EXISTS `imgtemp`;
CREATE TABLE `imgtemp`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `imguid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deltime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of imgtemp
-- ----------------------------

-- ----------------------------
-- Table structure for keys
-- ----------------------------
DROP TABLE IF EXISTS `keys`;
CREATE TABLE `keys`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `AccessKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AccessSecret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Endpoint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Bucketname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RequestAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `storageType` int NULL DEFAULT NULL,
  `keyname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未定义策略名称' COMMENT '策略名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of keys
-- ----------------------------
INSERT INTO `keys` VALUES (1, '', '', '', '', '', 2, '阿里云');
INSERT INTO `keys` VALUES (2, '', '', '', '', '', 1, '网易云');
INSERT INTO `keys` VALUES (3, '', '', '0', '', '', 3, '又拍');
INSERT INTO `keys` VALUES (4, '', '', '', '', '', 4, '七牛云');
INSERT INTO `keys` VALUES (5, '0', '0', '0', '0', 'http://127.0.0.1:8089', 5, 'Localhost');
INSERT INTO `keys` VALUES (6, '', '', '', '', '', 6, '腾讯云');
INSERT INTO `keys` VALUES (8, '', '', '0', '', '', 8, 'UFile');
INSERT INTO `keys` VALUES (13, '', '', '', '0', '', 7, 'ftp');

-- ----------------------------
-- Table structure for sysconfig
-- ----------------------------
DROP TABLE IF EXISTS `sysconfig`;
CREATE TABLE `sysconfig`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `register` int NOT NULL COMMENT '是否可以注册',
  `checkduplicate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sysconfig
-- ----------------------------
INSERT INTO `sysconfig` VALUES (1, 1, '0');

-- ----------------------------
-- Table structure for upload_config
-- ----------------------------
DROP TABLE IF EXISTS `upload_config`;
CREATE TABLE `upload_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `filesizetourists` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `filesizeuser` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `imgcounttourists` int NULL DEFAULT NULL COMMENT '游客文件总数量, 超出则不允许加入队列',
  `imgcountuser` int NULL DEFAULT NULL COMMENT '用户文件总数量, 超出则不允许加入队列',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支持后缀',
  `urltype` int NULL DEFAULT NULL COMMENT 'url类型',
  `isupdate` int NULL DEFAULT NULL COMMENT '禁止游客上传',
  `api` int NOT NULL COMMENT '开启api',
  `visitor_storage` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_storage` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blacklist` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userclose` int NULL DEFAULT 0 COMMENT '用户上传开关',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of upload_config
-- ----------------------------
INSERT INTO `upload_config` VALUES (1, '10485760', '20971520', 5, 10, 'gif,jpg,jpeg,bmp,png,jfif,webp', 1, 1, 0, '524288000', '104857600', '', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL COMMENT '注册时间',
  `level` int NULL DEFAULT NULL COMMENT '等级',
  `uid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户唯一标识',
  `isok` int NOT NULL,
  `memory` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `group_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'YWRtaW4=', 'admin', '2019-01-10', 2, '087905143e29419f98494ebf55827723', 1, '2147483648', 1);
INSERT INTO `user` VALUES (3, 'yzqdev', 'MTIzNDU2', '1826814987@qq.com', '2021-11-15', 2, 'a1d21e9e30af4b9fb42668b77b1cf15f', 1, '104857600', 1);

-- ----------------------------
-- Table structure for usergroup
-- ----------------------------
DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `group_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of usergroup
-- ----------------------------
INSERT INTO `usergroup` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
