/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 10.0.75.1:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 23/04/2019 21:57:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_permission`;
CREATE TABLE `tb_sys_permission`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `resource_type` tinyint(255) NOT NULL COMMENT '资源类型 1-menu 2-按钮',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源路径',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限字符串',
  `parentId` int(11) NOT NULL DEFAULT 0 COMMENT '父编号',
  `parentIds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父编号列表',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态 0-否 1-是',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_permission
-- ----------------------------
INSERT INTO `tb_sys_permission` VALUES (1, '用户管理', 1, 'userInfo/userList', 'userInfo:view', 0, '0', 1, '2019-04-23 21:46:39', '2019-04-23 13:49:03');
INSERT INTO `tb_sys_permission` VALUES (2, '用户添加', 2, 'userInfo/userAdd', 'userInfo:add', 1, '0/1', 1, '2019-04-23 21:48:08', '2019-04-23 13:49:08');
INSERT INTO `tb_sys_permission` VALUES (3, '用户删除', 2, 'userInfo/userDel', 'userInfo:del', 0, '0/1', 1, '2019-04-23 21:50:18', '2019-04-23 13:50:36');

-- ----------------------------
-- Table structure for tb_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role`;
CREATE TABLE `tb_sys_role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态 0-否 1-是',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_role
-- ----------------------------
INSERT INTO `tb_sys_role` VALUES (1, 'admin', '管理员', 0, '2019-04-23 13:52:34', '2019-04-23 13:52:34');
INSERT INTO `tb_sys_role` VALUES (2, 'vip', 'VIP会员', 0, '2019-04-23 13:52:34', '2019-04-23 13:52:34');
INSERT INTO `tb_sys_role` VALUES (3, 'test', 'test', 1, '2019-04-23 13:52:34', '2019-04-23 13:52:34');

-- ----------------------------
-- Table structure for tb_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_role_permission`;
CREATE TABLE `tb_sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_id` int(11) NOT NULL DEFAULT 0,
  `role_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_role_permission
-- ----------------------------
INSERT INTO `tb_sys_role_permission` VALUES (1, 1, 1);
INSERT INTO `tb_sys_role_permission` VALUES (2, 2, 2);
INSERT INTO `tb_sys_role_permission` VALUES (3, 3, 3);

-- ----------------------------
-- Table structure for tb_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE `tb_sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(10) UNSIGNED NOT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '盐',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态 0-无效 1-有效',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_user
-- ----------------------------
INSERT INTO `tb_sys_user` VALUES (1, 1, 'admin', 'd3c59d25033dbf980d29554025c23a75', '管理员', '8d78869f470951332959580424d4bf4f', 0, '2019-04-23 13:44:18', '2019-04-23 13:44:18');

-- ----------------------------
-- Table structure for tb_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user_role`;
CREATE TABLE `tb_sys_user_role`  (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `uid` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_user_role
-- ----------------------------
INSERT INTO `tb_sys_user_role` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
