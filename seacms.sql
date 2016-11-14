/*
Navicat MySQL Data Transfer

Source Server         : dev
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : seacms

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2016-07-14 14:05:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(50) NOT NULL COMMENT '文件名',
  `filePath` varchar(150) NOT NULL COMMENT '文件保存路径',
  `fileType` varchar(10) NOT NULL COMMENT '文件类型',
  `ownner` varchar(10) NOT NULL COMMENT '创建者，对应用户id',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '文件创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_file
-- ----------------------------
INSERT INTO `t_file` VALUES ('2', 'Koala.jpg', 'E:\\workspace\\server\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\CmsAdmin\\/upload/image/\\Koala.jpg', '头像', '46', '2016-04-06 11:31:48');
INSERT INTO `t_file` VALUES ('3', 'Desert.jpg', 'E:\\workspace\\server\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\CmsAdmin\\/upload/image/', '头像', '46', '2016-04-06 12:00:35');
INSERT INTO `t_file` VALUES ('4', 'Jellyfish.jpg', 'E:\\workspace\\server\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\CmsAdmin\\/upload/image/', '头像', '46', '2016-04-06 13:48:19');
INSERT INTO `t_file` VALUES ('5', 'Koala.jpg', '/upload/image/', '头像', '46', '2016-04-06 13:53:18');

-- ----------------------------
-- Table structure for `t_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleName` varchar(50) NOT NULL,
  `Action` varchar(20) DEFAULT NULL,
  `operator_id` int(10) NOT NULL COMMENT '操作用户的id，关联user表',
  `createTime` datetime NOT NULL,
  `operator_result` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------
INSERT INTO `t_log` VALUES ('1', '用户管理', '删除用户', '46', '2016-03-18 17:46:45', 'success');
INSERT INTO `t_log` VALUES ('2', '用户管理', '用户登录', '46', '2016-03-20 20:58:51', 'success');
INSERT INTO `t_log` VALUES ('3', '用户管理', '用户登录', '46', '2016-03-20 21:07:29', 'success');
INSERT INTO `t_log` VALUES ('4', '用户管理', '添加用户', '46', '2016-03-22 17:35:35', 'success');
INSERT INTO `t_log` VALUES ('5', '用户管理', '删除用户', '46', '2016-03-22 17:47:21', 'success');
INSERT INTO `t_log` VALUES ('6', '用户管理', '用户登录', '46', '2016-03-25 09:53:32', 'success');
INSERT INTO `t_log` VALUES ('7', '用户管理', '添加用户', '46', '2016-03-25 16:44:11', 'success');
INSERT INTO `t_log` VALUES ('8', '用户管理', '添加用户', '46', '2016-03-28 09:39:56', 'success');
INSERT INTO `t_log` VALUES ('9', '用户管理', '添加用户', '46', '2016-03-28 09:43:36', 'success');
INSERT INTO `t_log` VALUES ('10', '用户管理', '修改用户', '46', '2016-03-28 11:50:59', 'success');
INSERT INTO `t_log` VALUES ('11', '用户管理', '修改用户', '46', '2016-03-28 11:52:55', 'success');
INSERT INTO `t_log` VALUES ('14', '用户管理', '修改用户', '46', '2016-03-28 12:49:07', 'success');
INSERT INTO `t_log` VALUES ('15', '用户管理', '修改用户', '46', '2016-03-28 13:06:30', 'success');
INSERT INTO `t_log` VALUES ('16', '用户管理', '修改用户', '46', '2016-03-28 13:08:15', 'success');
INSERT INTO `t_log` VALUES ('25', '用户管理', '修改用户', '46', '2016-03-30 10:56:31', 'success');
INSERT INTO `t_log` VALUES ('26', '用户管理', '修改用户', '46', '2016-03-30 17:23:49', 'success');
INSERT INTO `t_log` VALUES ('27', '用户管理', '修改用户', '46', '2016-03-30 17:28:05', 'success');
INSERT INTO `t_log` VALUES ('28', '用户管理', '修改用户', '46', '2016-03-30 17:33:53', 'success');
INSERT INTO `t_log` VALUES ('29', '用户管理', '修改用户', '46', '2016-03-30 17:34:39', 'success');
INSERT INTO `t_log` VALUES ('30', '用户管理', '修改用户', '46', '2016-03-30 17:35:33', 'success');
INSERT INTO `t_log` VALUES ('31', '用户管理', '修改用户', '46', '2016-03-30 17:45:08', 'success');
INSERT INTO `t_log` VALUES ('32', '用户管理', '修改用户', '46', '2016-03-30 17:45:55', 'success');
INSERT INTO `t_log` VALUES ('33', '用户管理', '删除用户', '46', '2016-03-30 17:48:06', 'success');
INSERT INTO `t_log` VALUES ('34', '用户管理', '删除用户', '46', '2016-03-30 17:48:14', 'success');
INSERT INTO `t_log` VALUES ('35', '用户管理', '删除用户', '46', '2016-03-30 17:48:26', 'success');
INSERT INTO `t_log` VALUES ('36', '用户管理', '删除用户', '46', '2016-03-30 17:48:43', 'success');
INSERT INTO `t_log` VALUES ('37', '用户管理', '删除用户', '46', '2016-03-30 17:48:43', 'success');
INSERT INTO `t_log` VALUES ('38', '用户管理', '删除用户', '46', '2016-03-30 17:48:43', 'success');
INSERT INTO `t_log` VALUES ('39', '用户管理', '删除用户', '46', '2016-03-30 17:48:43', 'success');
INSERT INTO `t_log` VALUES ('40', '用户管理', '删除用户', '46', '2016-03-30 17:48:55', 'success');
INSERT INTO `t_log` VALUES ('41', '用户管理', '删除用户', '46', '2016-03-30 17:48:55', 'success');
INSERT INTO `t_log` VALUES ('42', '用户管理', '删除用户', '46', '2016-03-30 17:48:55', 'success');
INSERT INTO `t_log` VALUES ('43', '用户管理', '修改用户', '46', '2016-03-30 18:33:59', 'success');
INSERT INTO `t_log` VALUES ('44', '用户管理', '修改用户', '46', '2016-03-30 18:34:42', 'success');
INSERT INTO `t_log` VALUES ('45', '用户管理', '修改用户', '46', '2016-03-30 18:34:43', 'success');
INSERT INTO `t_log` VALUES ('46', '用户管理', '修改用户', '46', '2016-03-30 18:36:07', 'success');
INSERT INTO `t_log` VALUES ('47', '用户管理', '修改用户', '46', '2016-03-30 18:44:33', 'success');
INSERT INTO `t_log` VALUES ('48', '用户管理', '修改用户', '46', '2016-03-30 18:45:30', 'success');
INSERT INTO `t_log` VALUES ('49', '用户管理', '修改用户', '46', '2016-03-30 18:52:23', 'success');
INSERT INTO `t_log` VALUES ('50', '用户管理', '修改用户', '46', '2016-03-30 18:57:23', 'success');
INSERT INTO `t_log` VALUES ('51', '用户管理', '修改用户', '46', '2016-03-30 19:03:53', 'success');
INSERT INTO `t_log` VALUES ('52', '用户管理', '修改用户', '46', '2016-03-30 19:05:39', 'success');
INSERT INTO `t_log` VALUES ('53', '用户管理', '修改用户', '46', '2016-03-30 19:06:31', 'success');
INSERT INTO `t_log` VALUES ('54', '用户管理', '修改用户', '46', '2016-03-31 10:18:21', 'success');
INSERT INTO `t_log` VALUES ('55', '用户管理', '修改用户', '46', '2016-03-31 10:19:16', 'success');
INSERT INTO `t_log` VALUES ('56', '用户管理', '修改用户', '46', '2016-03-31 10:21:11', 'success');
INSERT INTO `t_log` VALUES ('57', '用户管理', '修改用户', '46', '2016-03-31 10:39:07', 'success');
INSERT INTO `t_log` VALUES ('58', '用户管理', '修改用户', '46', '2016-03-31 10:42:42', 'success');
INSERT INTO `t_log` VALUES ('59', '用户管理', '删除用户', '46', '2016-03-31 11:03:21', 'success');
INSERT INTO `t_log` VALUES ('60', '用户管理', '删除用户', '46', '2016-03-31 11:24:51', 'success');
INSERT INTO `t_log` VALUES ('61', '用户管理', '删除用户', '46', '2016-03-31 11:26:03', 'success');
INSERT INTO `t_log` VALUES ('62', '用户管理', '修改用户', '46', '2016-03-31 11:27:00', 'success');
INSERT INTO `t_log` VALUES ('63', '用户管理', '修改用户', '46', '2016-03-31 11:37:00', 'success');
INSERT INTO `t_log` VALUES ('64', '用户管理', '修改用户', '46', '2016-03-31 11:50:50', 'success');
INSERT INTO `t_log` VALUES ('65', '用户管理', '修改用户', '46', '2016-03-31 11:50:57', 'success');
INSERT INTO `t_log` VALUES ('66', '用户管理', '修改用户', '46', '2016-03-31 11:52:00', 'success');
INSERT INTO `t_log` VALUES ('67', '用户管理', '修改用户', '46', '2016-03-31 12:07:44', 'success');
INSERT INTO `t_log` VALUES ('68', '用户管理', '修改用户', '46', '2016-03-31 12:08:12', 'success');
INSERT INTO `t_log` VALUES ('69', '用户管理', '修改用户', '46', '2016-03-31 12:08:19', 'success');
INSERT INTO `t_log` VALUES ('70', '用户管理', '修改用户', '46', '2016-03-31 12:08:25', 'success');
INSERT INTO `t_log` VALUES ('71', '用户管理', '修改用户', '46', '2016-03-31 12:15:39', 'success');
INSERT INTO `t_log` VALUES ('72', '用户管理', '修改用户', '46', '2016-03-31 14:05:00', 'success');
INSERT INTO `t_log` VALUES ('73', '用户管理', '修改用户', '46', '2016-03-31 14:05:22', 'success');
INSERT INTO `t_log` VALUES ('74', '用户管理', '修改用户', '46', '2016-03-31 14:11:44', 'success');
INSERT INTO `t_log` VALUES ('75', '用户管理', '修改用户', '46', '2016-03-31 14:29:05', 'success');
INSERT INTO `t_log` VALUES ('76', '用户管理', '修改用户', '46', '2016-03-31 15:11:35', 'success');
INSERT INTO `t_log` VALUES ('77', '用户管理', '修改用户', '46', '2016-03-31 15:13:08', 'success');
INSERT INTO `t_log` VALUES ('78', '用户管理', '修改用户', '46', '2016-03-31 15:15:22', 'success');
INSERT INTO `t_log` VALUES ('79', '用户管理', '修改用户', '46', '2016-03-31 15:17:05', 'success');
INSERT INTO `t_log` VALUES ('80', '用户管理', '修改用户', '46', '2016-03-31 15:19:14', 'success');
INSERT INTO `t_log` VALUES ('81', '用户管理', '修改用户', '46', '2016-03-31 15:19:32', 'success');
INSERT INTO `t_log` VALUES ('82', '用户管理', '修改用户', '46', '2016-03-31 15:24:00', 'success');
INSERT INTO `t_log` VALUES ('83', '用户管理', '修改用户', '42', '2016-03-31 15:34:51', 'success');
INSERT INTO `t_log` VALUES ('84', '用户管理', '修改用户', '42', '2016-03-31 15:36:19', 'success');
INSERT INTO `t_log` VALUES ('85', '用户管理', '修改用户', '42', '2016-03-31 15:52:29', 'success');
INSERT INTO `t_log` VALUES ('86', '用户管理', '修改用户', '42', '2016-03-31 16:07:31', 'success');
INSERT INTO `t_log` VALUES ('87', '用户管理', '修改密码', '42', '2016-04-01 10:18:00', 'success');
INSERT INTO `t_log` VALUES ('88', '用户管理', '修改用户', '42', '2016-04-01 10:41:02', 'success');
INSERT INTO `t_log` VALUES ('89', '用户管理', '批量导入用户', '46', '2016-04-05 15:41:24', 'success');
INSERT INTO `t_log` VALUES ('90', '用户管理', '批量导入用户', '46', '2016-04-05 15:42:44', 'success');
INSERT INTO `t_log` VALUES ('91', '用户管理', '批量导入用户', '46', '2016-04-05 15:42:44', 'success');
INSERT INTO `t_log` VALUES ('92', '用户管理', '批量导入用户', '46', '2016-04-05 15:42:44', 'success');
INSERT INTO `t_log` VALUES ('93', '用户管理', '批量导入用户', '46', '2016-04-05 15:42:57', 'success');
INSERT INTO `t_log` VALUES ('94', '用户管理', '批量导入用户', '46', '2016-04-06 10:56:38', 'success');
INSERT INTO `t_log` VALUES ('95', '上传文件', '上传文件', '46', '2016-04-06 11:31:56', 'success');
INSERT INTO `t_log` VALUES ('96', '上传文件', '上传文件', '46', '2016-04-06 12:00:35', 'success');
INSERT INTO `t_log` VALUES ('97', '上传文件', '上传文件', '46', '2016-04-06 13:48:19', 'success');
INSERT INTO `t_log` VALUES ('98', '上传文件', '上传文件', '46', '2016-04-06 13:53:18', 'success');
INSERT INTO `t_log` VALUES ('99', '用户管理', '修改用户', '46', '2016-04-06 14:32:00', 'success');
INSERT INTO `t_log` VALUES ('100', '用户管理', '修改用户', '46', '2016-04-06 14:34:22', 'success');
INSERT INTO `t_log` VALUES ('101', '用户管理', '修改用户', '46', '2016-04-06 14:35:30', 'success');
INSERT INTO `t_log` VALUES ('102', '用户管理', '修改用户', '46', '2016-04-06 14:55:18', 'success');
INSERT INTO `t_log` VALUES ('103', '用户管理', '删除用户', '46', '2016-06-29 17:48:20', 'success');
INSERT INTO `t_log` VALUES ('104', '用户管理', '修改用户', '46', '2016-06-29 17:49:00', 'success');
INSERT INTO `t_log` VALUES ('105', '用户管理', '删除用户', '46', '2016-06-29 18:10:50', 'success');
INSERT INTO `t_log` VALUES ('106', '用户管理', '修改用户', '46', '2016-06-29 18:12:15', 'success');
INSERT INTO `t_log` VALUES ('107', '用户管理', '用户登录', '53', '2016-07-06 11:26:32', 'success');
INSERT INTO `t_log` VALUES ('108', '用户管理', '用户登录', '53', '2016-07-06 11:34:37', 'success');
INSERT INTO `t_log` VALUES ('109', '用户管理', '用户登录', '53', '2016-07-06 11:35:03', 'success');
INSERT INTO `t_log` VALUES ('110', '用户管理', '用户登录', '53', '2016-07-07 17:40:33', 'success');
INSERT INTO `t_log` VALUES ('111', '用户管理', '用户登录', '53', '2016-07-07 17:41:00', 'success');
INSERT INTO `t_log` VALUES ('112', '用户管理', '用户登录', '53', '2016-07-07 17:41:03', 'success');
INSERT INTO `t_log` VALUES ('113', '用户管理', '用户登录', '53', '2016-07-07 17:43:21', 'success');
INSERT INTO `t_log` VALUES ('114', '用户管理', '用户登录', '53', '2016-07-07 17:43:21', 'success');
INSERT INTO `t_log` VALUES ('115', '用户管理', '用户登录', '53', '2016-07-08 14:53:53', 'success');
INSERT INTO `t_log` VALUES ('116', '用户管理', '用户登录', '53', '2016-07-08 18:33:46', 'success');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(50) DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 NOT NULL,
  `realName` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 DEFAULT '1' COMMENT '0，女；1，男',
  `age` int(4) DEFAULT '18',
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `nativePlace` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '0,一般用户；1，管理员用户；2，会员',
  `hobby` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `photo` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  `lastLoginTime` timestamp NULL DEFAULT NULL,
  `power` varchar(4) CHARACTER SET utf8 DEFAULT '0' COMMENT '0,普通用户 1，会员   2 管理员',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=96 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('42', null, '13426370076', 'C33367701511B4F6020EC61DED352059', '王树勇', '1', '23', 'wanghsuyong@china.com, 朝阳区', '我问问', '内蒙古', '足球123', null, null, '2016-04-01 10:41:02', '2016-04-01 10:40:04', '1');
INSERT INTO `t_user` VALUES ('46', null, '13000000001', 'E10ADC3949BA59ABBE56E057F20F883E', '王树勇', '1', '32', 'wangsy@channelsoft.com', '北京朝阳区123', '内蒙古1234', '足球1234', '/upload/image/Koala.jpg', '2016-04-06 13:55:14', '2016-06-29 18:12:15', '2016-07-06 11:21:06', '1');
INSERT INTO `t_user` VALUES ('47', 'wangs', '13426371080', '123456', '王树勇', 'ç·', '0', 'wangshuyong@163.com', '', null, null, null, null, '2016-03-30 18:36:07', null, '1');
INSERT INTO `t_user` VALUES ('49', 'admin', '13426370014', '123456', '王二', '0', '0', 'wangshuyong@163.com', null, null, null, null, null, '2016-03-30 18:52:23', null, '0');
INSERT INTO `t_user` VALUES ('53', 'user', '13000000007', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '0', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, '2016-03-30 18:57:23', '2016-07-14 12:37:52', '0');
INSERT INTO `t_user` VALUES ('54', null, '13000000008', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('55', null, '13000000012', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('56', null, '13000000010', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('57', null, '13000000011', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('58', null, '13000000013', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('59', null, '13000000014', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('60', null, '13000000015', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('61', null, '13000000016', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('62', null, '13000000017', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('63', null, '13000000018', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('64', null, '13000000019', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('69', null, '13000000026', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '21', 'wangshuyong@163.com', '内蒙古', '内蒙古', '足球', null, null, null, null, '0');
INSERT INTO `t_user` VALUES ('82', 'sa', '12000000009', 'E10ADC3949BA59ABBE56E057F20F883E', '32', '男', '0', '32', '21', '32', '32', null, '2016-03-30 17:21:46', '2016-03-31 15:52:29', null, '0');
INSERT INTO `t_user` VALUES ('84', 'wang21', '13425460014', 'E10ADC3949BA59ABBE56E057F20F883E', '万曙光', '男', '0', 'wang@china.com', '欧阳', '12', '足球', null, '2016-03-30 17:21:46', '2016-03-31 15:36:19', null, '0');
INSERT INTO `t_user` VALUES ('86', 'wang2', '1200000004', 'E10ADC3949BA59ABBE56E057F20F883E', '李四', '男', '0', 'wang@china.com', '北京朝阳', '北京', '足球', null, null, '2016-03-31 15:15:22', null, '管理员');
INSERT INTO `t_user` VALUES ('95', 'wangshuyong1', '12000000071', 'E10ADC3949BA59ABBE56E057F20F883E', '王三', '男', '0', 'wang@china.com', '32', '北京', '足球123', null, '2016-03-30 17:21:46', '2016-03-31 15:13:08', null, '0');
