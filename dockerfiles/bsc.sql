/*
Navicat MySQL Data Transfer

Source Server         : uat_bsc
Source Server Version : 50722
Source Host           : 10.150.251.92:3306
Source Database       : bsc

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-05-14 16:47:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ac_date`
-- ----------------------------
DROP TABLE IF EXISTS `ac_date`;
CREATE TABLE `ac_date` (
  `ID` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `BEFORE_LSAT_AC_DATE` varchar(12) DEFAULT NULL COMMENT '上上一日会计日',
  `LSAT_AC_DATE` varchar(12) DEFAULT NULL COMMENT '上一日会计日',
  `AC_DATE` varchar(12) NOT NULL COMMENT '会计日',
  `NEXT_AC_DATE` varchar(12) DEFAULT NULL COMMENT '下一会计日',
  `CREAT_TIME` datetime NOT NULL COMMENT '创建时间',
  `MODIFY_TIME` datetime NOT NULL COMMENT '修改时间',
  `TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='会计日表信息表';

-- ----------------------------
-- Records of ac_date
-- ----------------------------
