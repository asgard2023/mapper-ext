/*
 Navicat MySQL Data Transfer

 Source Server         : hw
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.1.254:3306
 Source Schema         : mapper-extend

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 15/10/2022 17:42:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dfl_resource
-- ----------------------------
DROP TABLE IF EXISTS `dfl_resource`;
CREATE TABLE `dfl_resource`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口uri',
  `uri_id` int NULL DEFAULT NULL,
  `method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求类型(GET/POST/PUT)',
  `res_type` tinyint NULL DEFAULT NULL COMMENT '资源类型，0接口,1功能',
  `status` tinyint NULL DEFAULT NULL,
  `if_del` tinyint NULL DEFAULT NULL COMMENT '是否删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `modify_user` int NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_uri`(`uri`) USING BTREE,
  INDEX `idx_create`(`create_time`) USING BTREE,
  INDEX `idx_uri_id`(`uri_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单资源管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dfl_resource
-- ----------------------------
INSERT INTO `dfl_resource` VALUES (1, 'test', 'test', 1, 'get', 1, 1, 0, '2022-08-06 23:20:36', '2022-09-09 08:55:03', NULL, -1);
INSERT INTO `dfl_resource` VALUES (2, 'test2', 'test2', 2, 'get', 1, 0, 0, '2022-08-07 08:45:06', '2022-09-25 07:02:24', NULL, -1);
INSERT INTO `dfl_resource` VALUES (3, 'test_0', 'test_0', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:36', '2022-08-07 08:53:36', 0, NULL);
INSERT INTO `dfl_resource` VALUES (4, 'test_1', 'test_12', 1, 'get', NULL, 1, 0, '2022-08-07 08:53:37', '2022-09-25 07:02:40', 0, -1);
INSERT INTO `dfl_resource` VALUES (5, 'test_2', 'test_2bb', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-08 22:17:43', 0, -1);
INSERT INTO `dfl_resource` VALUES (6, 'test_3', 'test_3', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-07 08:53:37', 0, NULL);
INSERT INTO `dfl_resource` VALUES (7, 'test_4', 'test_4', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-07 08:53:37', 0, NULL);
INSERT INTO `dfl_resource` VALUES (8, 'test_5', 'test_5', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-07 08:53:37', 0, NULL);
INSERT INTO `dfl_resource` VALUES (9, 'test_6', 'test_6', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-07 08:53:37', 0, NULL);
INSERT INTO `dfl_resource` VALUES (10, 'test_7', 'test_7', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-07 08:53:37', 0, NULL);
INSERT INTO `dfl_resource` VALUES (11, 'test_8', 'test_8', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:37', '2022-08-07 08:53:37', 0, NULL);
INSERT INTO `dfl_resource` VALUES (12, 'test_9', 'test_9', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (13, 'test_10', 'test_10', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (14, 'test_11', 'test_11', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (15, 'test_12', 'test_12', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (16, 'test_13', 'test_13', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (17, 'test_14', 'test_14', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (18, 'test_15', 'test_15', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (19, 'test_16', 'test_16', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:38', '2022-08-07 08:53:38', 0, NULL);
INSERT INTO `dfl_resource` VALUES (20, 'test_17', 'test_17', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (21, 'test_18', 'test_18', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (22, 'test_19', 'test_19', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (23, 'test_20', 'test_20', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (24, 'test_21', 'test_21', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (25, 'test_22', 'test_22', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (26, 'test_23', 'test_23', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (27, 'test_24', 'test_24', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (28, 'test_25', 'test_25', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:39', '2022-08-07 08:53:39', 0, NULL);
INSERT INTO `dfl_resource` VALUES (29, 'test_26', 'test_26', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (30, 'test_27', 'test_27', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (31, 'test_28', 'test_28', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (32, 'test_29', 'test_29', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (33, 'test_30', 'test_30', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (34, 'test_31', 'test_31', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (35, 'test_32', 'test_32', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (36, 'test_33', 'test_33', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:40', '2022-08-07 08:53:40', 0, NULL);
INSERT INTO `dfl_resource` VALUES (37, 'test_34', 'test_34', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (38, 'test_35', 'test_35', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (39, 'test_36', 'test_36', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (40, 'test_37', 'test_37', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (41, 'test_38', 'test_38', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (42, 'test_39', 'test_39', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (43, 'test_40', 'test_40', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (44, 'test_41', 'test_41', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:41', '2022-08-07 08:53:41', 0, NULL);
INSERT INTO `dfl_resource` VALUES (45, 'test_42', 'test_42', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (46, 'test_43', 'test_43', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (47, 'test_44', 'test_44', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (48, 'test_45', 'test_45', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (49, 'test_46', 'test_46', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (50, 'test_47', 'test_47', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (51, 'test_48', 'test_48', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (52, 'test_49', 'test_49', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (53, 'test_50', 'test_50', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:42', '2022-08-07 08:53:42', 0, NULL);
INSERT INTO `dfl_resource` VALUES (54, 'test_51', 'test_51', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (55, 'test_52', 'test_52', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (56, 'test_53', 'test_53', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (57, 'test_54', 'test_54', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (58, 'test_55', 'test_55', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (59, 'test_56', 'test_56', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (60, 'test_57', 'test_57', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (61, 'test_58', 'test_58', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (62, 'test_59', 'test_59', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:43', '2022-08-07 08:53:43', 0, NULL);
INSERT INTO `dfl_resource` VALUES (63, 'test_60', 'test_60', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (64, 'test_61', 'test_61', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (65, 'test_62', 'test_62', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (66, 'test_63', 'test_63', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (67, 'test_64', 'test_64', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (68, 'test_65', 'test_65', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (69, 'test_66', 'test_66', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (70, 'test_67', 'test_67', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (71, 'test_68', 'test_68', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:44', '2022-08-07 08:53:44', 0, NULL);
INSERT INTO `dfl_resource` VALUES (72, 'test_69', 'test_69', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (73, 'test_70', 'test_70', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (74, 'test_71', 'test_71', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (75, 'test_72', 'test_72', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (76, 'test_73', 'test_73', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (77, 'test_74', 'test_74', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (78, 'test_75', 'test_75', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (79, 'test_76', 'test_76', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (80, 'test_77', 'test_77', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:45', '2022-08-07 08:53:45', 0, NULL);
INSERT INTO `dfl_resource` VALUES (81, 'test_78', 'test_78', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (82, 'test_79', 'test_79', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (83, 'test_80', 'test_80', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (84, 'test_81', 'test_81', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (85, 'test_82', 'test_82', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (86, 'test_83', 'test_83', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (87, 'test_84', 'test_84', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (88, 'test_85', 'test_85', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (89, 'test_86', 'test_86', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:46', '2022-08-07 08:53:46', 0, NULL);
INSERT INTO `dfl_resource` VALUES (90, 'test_87', 'test_87', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (91, 'test_88', 'test_88', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (92, 'test_89', 'test_89', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (93, 'test_90', 'test_90', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (94, 'test_91', 'test_91', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (95, 'test_92', 'test_92', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (96, 'test_93', 'test_93', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (97, 'test_94', 'test_94', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (98, 'test_95', 'test_95', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:47', '2022-08-07 08:53:47', 0, NULL);
INSERT INTO `dfl_resource` VALUES (99, 'test_96', 'test_96', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:48', '2022-08-07 08:53:48', 0, NULL);
INSERT INTO `dfl_resource` VALUES (100, 'test_97', 'test_97', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:48', '2022-08-07 08:53:48', 0, NULL);
INSERT INTO `dfl_resource` VALUES (101, 'test_98', 'test_98', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:48', '2022-08-07 08:53:48', 0, NULL);
INSERT INTO `dfl_resource` VALUES (102, 'test_99', 'test_99', NULL, NULL, NULL, 1, 0, '2022-08-07 08:53:48', '2022-08-07 08:53:48', 0, NULL);
INSERT INTO `dfl_resource` VALUES (103, '3333', '3333', 3, '', NULL, NULL, 1, '2022-08-08 21:34:04', '2022-08-08 21:35:15', -1, -1);

-- ----------------------------
-- Table structure for dfl_role
-- ----------------------------
DROP TABLE IF EXISTS `dfl_role`;
CREATE TABLE `dfl_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `if_del` tinyint NOT NULL COMMENT '是否删除',
  `status` tinyint NOT NULL COMMENT '状态:是否有效0无效，1有效',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` int NULL DEFAULT NULL COMMENT '创建人',
  `modify_user` int NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dfl_role
-- ----------------------------
INSERT INTO `dfl_role` VALUES (3, 'test', '测试', NULL, 0, 1, '2022-07-31 21:59:22', '2022-09-08 21:42:40', NULL, -1);
INSERT INTO `dfl_role` VALUES (4, 'test22', '测试 2', 'test', 0, 1, '2022-08-03 21:04:50', '2022-09-09 09:04:20', -1, -1);
INSERT INTO `dfl_role` VALUES (5, '906520', 'message', '123', 0, 0, '2022-08-17 10:06:15', '2022-10-15 09:48:17', -1, -1);
INSERT INTO `dfl_role` VALUES (6, 'test4', 'test4', NULL, 0, 1, '2022-09-09 18:48:08', '2022-10-15 09:48:08', -1, -1);
INSERT INTO `dfl_role` VALUES (7, 'test5', '测试5', NULL, 0, 1, '2022-09-09 18:51:26', '2022-09-09 18:51:26', -1, -1);
INSERT INTO `dfl_role` VALUES (8, 'test6', '这是一个测试', NULL, 0, 1, '2022-09-11 08:49:18', '2022-09-11 08:49:18', -1, -1);

-- ----------------------------
-- Table structure for dfl_user
-- ----------------------------
DROP TABLE IF EXISTS `dfl_user`;
CREATE TABLE `dfl_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `descs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人描术信息',
  `telephone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `if_del` tinyint NOT NULL COMMENT '是否删除',
  `status` tinyint NOT NULL COMMENT '状态:是否有效0无效，1有效',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `create_user` int NULL DEFAULT NULL,
  `modify_user` int NULL DEFAULT NULL,
  `register_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sys_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_nickname`(`nickname`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dfl_user
-- ----------------------------
INSERT INTO `dfl_user` VALUES (5, '测试', 'test', '测试个人信息', '123', 'test', 'test', 0, 1, NULL, '2022-08-01 22:36:21', '2022-08-04 07:16:44', NULL, -1, NULL, NULL);
INSERT INTO `dfl_user` VALUES (7, '測試22', 'test2', '測試2個人資訊', '12', 'aaa', NULL, 0, 1, NULL, '2022-08-03 20:24:31', '2022-09-24 11:41:03', -1, -1, NULL, NULL);
INSERT INTO `dfl_user` VALUES (9, '测试3', 'test3', '测试3个人信息', '12', 'aaa', NULL, 0, 1, NULL, '2022-08-03 20:24:31', '2022-09-24 08:13:29', -1, -1, NULL, NULL);
INSERT INTO `dfl_user` VALUES (10, '测试4', 'test4', '测试4个人信息', '12', 'aaa', NULL, 0, 1, NULL, '2022-08-03 20:24:31', '2022-09-24 08:14:05', -1, -1, NULL, NULL);
INSERT INTO `dfl_user` VALUES (11, '测试5', 'test5', '测试5个人信息', '12', 'aaa', NULL, 0, 1, NULL, '2022-08-03 20:24:31', '2022-09-18 08:43:17', -1, -1, NULL, NULL);
INSERT INTO `dfl_user` VALUES (12, '测试_0', 'test_0', '测试_0个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:16', '2022-09-18 08:44:32', NULL, -1, NULL, NULL);
INSERT INTO `dfl_user` VALUES (13, '测试_1', 'test_1', '测试_1个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:17', '2022-08-06 06:33:17', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (14, '测试_2', 'test_2', '测试_2个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:17', '2022-08-06 06:33:17', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (15, '测试_3', 'test_3', '测试_3个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:17', '2022-08-06 06:33:17', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (16, '测试_4', 'test_4', '测试_4个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (17, '测试_5', 'test_5', '测试_5个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (18, '测试_6', 'test_6', '测试_6个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (19, '测试_7', 'test_7', '测试_7个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (20, '测试_8', 'test_8', '测试_8个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (21, '测试_9', 'test_9', '测试_9个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (22, '测试_10', 'test_10', '测试_10个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (23, '测试_11', 'test_11', '测试_11个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (24, '测试_12', 'test_12', '测试_12个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:18', '2022-08-06 06:33:18', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (25, '测试_13', 'test_13', '测试_13个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (26, '测试_14', 'test_14', '测试_14个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (27, '测试_15', 'test_15', '测试_15个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (28, '测试_16', 'test_16', '测试_16个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (29, '测试_17', 'test_17', '测试_17个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (30, '测试_18', 'test_18', '测试_18个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (31, '测试_19', 'test_19', '测试_19个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (32, '测试_20', 'test_20', '测试_20个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (33, '测试_21', 'test_21', '测试_21个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:19', '2022-08-06 06:33:19', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (34, '测试_22', 'test_22', '测试_22个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (35, '测试_23', 'test_23', '测试_23个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (36, '测试_24', 'test_24', '测试_24个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (37, '测试_25', 'test_25', '测试_25个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (38, '测试_26', 'test_26', '测试_26个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (39, '测试_27', 'test_27', '测试_27个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (40, '测试_28', 'test_28', '测试_28个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (41, '测试_29', 'test_29', '测试_29个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (42, '测试_30', 'test_30', '测试_30个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (43, '测试_31', 'test_31', '测试_31个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:20', '2022-08-06 06:33:20', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (44, '测试_32', 'test_32', '测试_32个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (45, '测试_33', 'test_33', '测试_33个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (46, '测试_34', 'test_34', '测试_34个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (47, '测试_35', 'test_35', '测试_35个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (48, '测试_36', 'test_36', '测试_36个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (49, '测试_37', 'test_37', '测试_37个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (50, '测试_38', 'test_38', '测试_38个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (51, '测试_39', 'test_39', '测试_39个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (52, '测试_40', 'test_40', '测试_40个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:21', '2022-08-06 06:33:21', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (53, '测试_41', 'test_41', '测试_41个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (54, '测试_42', 'test_42', '测试_42个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (55, '测试_43', 'test_43', '测试_43个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (56, '测试_44', 'test_44', '测试_44个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (57, '测试_45', 'test_45', '测试_45个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (58, '测试_46', 'test_46', '测试_46个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (59, '测试_47', 'test_47', '测试_47个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (60, '测试_48', 'test_48', '测试_48个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (61, '测试_49', 'test_49', '测试_49个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (62, '测试_50', 'test_50', '测试_50个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:22', '2022-08-06 06:33:22', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (63, '测试_51', 'test_51', '测试_51个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (64, '测试_52', 'test_52', '测试_52个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (65, '测试_53', 'test_53', '测试_53个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (66, '测试_54', 'test_54', '测试_54个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (67, '测试_55', 'test_55', '测试_55个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (68, '测试_56', 'test_56', '测试_56个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (69, '测试_57', 'test_57', '测试_57个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (70, '测试_58', 'test_58', '测试_58个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (71, '测试_59', 'test_59', '测试_59个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:23', '2022-08-06 06:33:23', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (72, '测试_60', 'test_60', '测试_60个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (73, '测试_61', 'test_61', '测试_61个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (74, '测试_62', 'test_62', '测试_62个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (75, '测试_63', 'test_63', '测试_63个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (76, '测试_64', 'test_64', '测试_64个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (77, '测试_65', 'test_65', '测试_65个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (78, '测试_66', 'test_66', '测试_66个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (79, '测试_67', 'test_67', '测试_67个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (80, '测试_68', 'test_68', '测试_68个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:24', '2022-08-06 06:33:24', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (81, '测试_69', 'test_69', '测试_69个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:25', '2022-08-06 06:33:25', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (82, '测试_70', 'test_70', '测试_70个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:25', '2022-08-06 06:33:25', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (83, '测试_71', 'test_71', '测试_71个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:25', '2022-08-06 06:33:25', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (84, '测试_72', 'test_72', '测试_72个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:25', '2022-08-06 06:33:25', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (85, '测试_73', 'test_73', '测试_73个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:25', '2022-08-06 06:33:25', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (86, '测试_74', 'test_74', '测试_74个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (87, '测试_75', 'test_75', '测试_75个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (88, '测试_76', 'test_76', '测试_76个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (89, '测试_77', 'test_77', '测试_77个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (90, '测试_78', 'test_78', '测试_78个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (91, '测试_79', 'test_79', '测试_79个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (92, '测试_80', 'test_80', '测试_80个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (93, '测试_81', 'test_81', '测试_81个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (94, '测试_82', 'test_82', '测试_82个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (95, '测试_83', 'test_83', '测试_83个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:26', '2022-08-06 06:33:26', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (96, '测试_84', 'test_84', '测试_84个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (97, '测试_85', 'test_85', '测试_85个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (98, '测试_86', 'test_86', '测试_86个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (99, '测试_87', 'test_87', '测试_87个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (100, '测试_88', 'test_88', '测试_88个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (101, '测试_89', 'test_89', '测试_89个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (102, '测试_90', 'test_90', '测试_90个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (103, '测试_91', 'test_91', '测试_91个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (104, '测试_92', 'test_92', '测试_92个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:27', '2022-08-06 06:33:27', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (105, '测试_93', 'test_93', '测试_93个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (106, '测试_94', 'test_94', '测试_94个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (107, '测试_95', 'test_95', '测试_95个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (108, '测试_96', 'test_96', '测试_96个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (109, '测试_97', 'test_97', '测试_97个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (110, '测试_98', 'test_98', '测试_98个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);
INSERT INTO `dfl_user` VALUES (111, '测试_99', 'test_99', '测试_99个人信息', NULL, NULL, NULL, 0, 1, NULL, '2022-08-06 06:33:28', '2022-08-06 06:33:28', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
