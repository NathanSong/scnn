/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3307
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3307
 Source Schema         : house_rental

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 07/03/2025 21:42:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `houseid` int(0) NOT NULL AUTO_INCREMENT COMMENT '出租单主键id',
  `statue` int(0) NOT NULL DEFAULT 0 COMMENT '房屋状态：0-未出租，1-已出租，2-需整改',
  `area` int(0) NULL DEFAULT NULL COMMENT '房屋面积',
  `floor` int(0) NULL DEFAULT NULL COMMENT '房屋楼层',
  `price` int(0) NULL DEFAULT NULL COMMENT '房屋租金/月',
  `community` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房子小区',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋地址',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋房型',
  `toward` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋朝向',
  `surrounding` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋周围环境',
  `decoration` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋装修',
  `housename` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋出租人',
  `housephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '房屋出租人电话',
  `tenantname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租客姓名',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`houseid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES (35, 1, 1, 11, 11, 'ass', '11', '1', '1', '1', '1', 'song', '1', 'scn', '586fdfd1-4e69-4739-ad2c-eabb6edec3bd.jpg');
INSERT INTO `house` VALUES (36, 0, 1, 1, 1, 'a', '1', '1', '1', '1', '1', 'song', '1', '', '108f8e73-b8e0-4782-82da-bc7dd2f41d74.jpg');
INSERT INTO `house` VALUES (38, 0, 1, 1, 1, 'qqq', '1', '1', '1', '1', '1', 'song', '1', '', 'fc004941-8322-484c-8dc9-61c81f14a7cb.jpg');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `messageid` int(0) NOT NULL AUTO_INCREMENT,
  `messagename` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `messagetitle` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`messageid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 'admin', '1231', '?3131', '2025-03-05 14:48:19');
INSERT INTO `message` VALUES (2, 'scn', 'ss', '1', '2025-03-05 14:49:51');
INSERT INTO `message` VALUES (3, 'scn', 'ceshi', '1', '2025-03-06 18:24:20');
INSERT INTO `message` VALUES (4, 'admin', '12', '水水水水水水水水水水水水水水水水水水水', '2025-03-06 22:08:27');
INSERT INTO `message` VALUES (5, 'admin', '测试', '111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111', '2025-03-06 22:08:42');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `reportid` int(0) NOT NULL AUTO_INCREMENT,
  `reporter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `reportee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `reporttitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `reportcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `reportdate` datetime(0) NULL DEFAULT NULL,
  `status` enum('pending','approved','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending',
  PRIMARY KEY (`reportid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES (3, 'scn', 'song', 'qq', 'ww', '2025-03-07 17:24:26', 'approved');
INSERT INTO `report` VALUES (4, 'scn', 'song', 'www', 's', '2025-03-07 17:30:56', 'rejected');
INSERT INTO `report` VALUES (5, 'scn', 'song', 's', 'ss', '2025-03-07 17:31:06', 'approved');
INSERT INTO `report` VALUES (6, 'scn', 'song', 's', 's', '2025-03-07 17:31:14', 'pending');
INSERT INTO `report` VALUES (7, 'song', 'song', 'ss', '1', '2025-03-07 19:03:10', 'pending');
INSERT INTO `report` VALUES (8, 'scn', 'song', 'sss', 'ss111', '2025-03-07 21:02:45', 'approved');
INSERT INTO `report` VALUES (9, 'scn', 'song', 'aaa1', 'qsd', '2025-03-07 21:07:58', 'approved');
INSERT INTO `report` VALUES (10, 'scn', 'song', 'saaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2025-03-07 21:24:58', 'approved');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userid` int(0) NOT NULL AUTO_INCREMENT,
  `admin` int(0) NULL DEFAULT NULL COMMENT '是否是管理员',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户姓名\r\n',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户身份证号',
  `userphone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机号',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (19, 1, 'admin', '123456', '1111', '11111');
INSERT INTO `user` VALUES (20, 2, 'scn', '111', '123', '123');
INSERT INTO `user` VALUES (25, 3, 'song', '123', '111', '111');
INSERT INTO `user` VALUES (26, 3, 'test', '123', '111', '111');
INSERT INTO `user` VALUES (27, 2, 'song1', '123', '111', '111');
INSERT INTO `user` VALUES (28, 2, 'sccn', '111', '11', '11');

SET FOREIGN_KEY_CHECKS = 1;
