/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : pro

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2019-09-23 09:20:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL,
  `available` bit(1) default NULL,
  `name` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  `parent_ids` varchar(255) default NULL,
  `permission` varchar(255) default NULL,
  `resource_type` enum('menu','button') default NULL,
  `url` varchar(255) default NULL,
  `create_date` datetime default NULL,
  `create_user` int(11) default NULL,
  `update_date` datetime default NULL,
  `update_user` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '\0', '用户管理', '0', '0/', 'userInfo:view', 'menu', '/userInfo/userList', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('2', '\0', '用户添加', '1', '0/1', 'userInfo:add', 'button', '/userInfo/userAdd', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('3', '\0', '用户删除', '1', '0/1', 'userInfo:del', 'button', '/userInfo/userDel', null, null, null, null);
INSERT INTO `sys_permission` VALUES ('4', '\0', '角色管理', '0', '0/', 'userInfo:del', 'button', '/userInfo/userDel', null, null, null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL,
  `available` bit(1) default NULL,
  `description` varchar(255) default NULL,
  `role` varchar(255) default NULL,
  `create_date` datetime default NULL,
  `create_user` int(11) default NULL,
  `update_date` datetime default NULL,
  `update_user` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '\0', '管理员', 'admin', null, null, null, null);
INSERT INTO `sys_role` VALUES ('2', '\0', 'VIP会员', 'vip', null, null, null, null);
INSERT INTO `sys_role` VALUES ('3', '', 'test', 'test', null, null, null, null);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  KEY `FK9q28ewrhntqeipl1t04kh1be7` (`role_id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('2', '3');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `uid` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKgkmyslkrfeyn9ukmolvek8b8f` (`uid`),
  CONSTRAINT `FKgkmyslkrfeyn9ukmolvek8b8f` FOREIGN KEY (`uid`) REFERENCES `user_info` (`uid`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `uid` int(11) NOT NULL,
  `name` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `salt` varchar(255) default NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(255) default NULL,
  `create_date` datetime default NULL,
  `create_user` int(11) default NULL,
  `update_date` datetime default NULL,
  `update_user` int(11) default NULL,
  PRIMARY KEY  (`uid`),
  UNIQUE KEY `UK_f2ksd6h8hsjtd57ipfq9myr64` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '管理员', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f', '0', 'admin', null, null, null, null);
INSERT INTO `user_info` VALUES ('2', '用户_1567409507087', 'c1a2d273a93593c6fd9d4338e35351f0', 'inrfSndj5i7Nfx+V0q1bOg==', '0', 'admins', null, null, null, null);
INSERT INTO `user_info` VALUES ('7', '用户_1567565385633', '27dabde7c47b221e5ab51a4d9923bd97', 'T3x4K9aKZTo9hgCjcELIzA==', '0', 'testttt', null, null, null, null);
INSERT INTO `user_info` VALUES ('9', '用户_1567570255604', '47a2d7441e9429ee57c6f9786a93f4ec', 'iU24/EJZPLNNQX9fNjiPnA==', '0', 'test11', null, null, null, null);
INSERT INTO `user_info` VALUES ('14', '用户_1568881220511', '5fe1061c23e34fb085495d797967a21e', 'o+tOECri6yrKizPCQwNFhA==', '0', '11', null, null, null, null);
