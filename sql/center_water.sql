/*
Navicat MySQL Data Transfer

Source Server         : 47.103.218.11
Source Server Version : 50730
Source Host           : 47.103.218.11:3306
Source Database       : water

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-06-05 14:16:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for adminrole
-- ----------------------------
DROP TABLE IF EXISTS `adminrole`;
CREATE TABLE `adminrole` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `role_note` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`,`role_name`),
  KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` varchar(50) NOT NULL,
  `rolename` varchar(50) DEFAULT NULL,
  `adminname` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `modified` timestamp NULL DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`),
  KEY `area_id` (`area_id`),
  KEY `rolename` (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `area_id` varchar(30) NOT NULL,
  `area_name` varchar(30) NOT NULL,
  `area_note` text,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for area_spot
-- ----------------------------
DROP TABLE IF EXISTS `area_spot`;
CREATE TABLE `area_spot` (
  `area_id` varchar(30) NOT NULL,
  `spot_id` varchar(30) NOT NULL,
  `spot_name` varchar(30) NOT NULL,
  `spot_url` varchar(150) DEFAULT NULL,
  `spot_historyurl` varchar(150) DEFAULT NULL,
  `spot_intro` text,
  `spot_obserargs` varchar(300) DEFAULT NULL,
  `spot_obsermeth` varchar(30) DEFAULT NULL,
  `spot_posinfo` varchar(20) DEFAULT NULL,
  `spot_runtime` varchar(30) DEFAULT NULL,
  `info_id` bigint(20) NOT NULL,
  `info_node` varchar(255) NOT NULL,
  PRIMARY KEY (`area_id`,`spot_id`),
  KEY `spot_id` (`spot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for box_sensor
-- ----------------------------
DROP TABLE IF EXISTS `box_sensor`;
CREATE TABLE `box_sensor` (
  `area_id` varchar(30) NOT NULL,
  `spot_id` varchar(30) NOT NULL,
  `node_id` varchar(30) NOT NULL,
  `data_time` datetime NOT NULL,
  `Conductivity` float DEFAULT NULL,
  `Temperature` float DEFAULT NULL,
  `Pressure` float DEFAULT NULL,
  `Salinity` float DEFAULT NULL,
  `DissolvedOxygen` float DEFAULT NULL,
  `SPAD` float DEFAULT NULL,
  `Turbidity` float DEFAULT NULL,
  `AnDan` float DEFAULT NULL,
  `YaXiaoDan` float DEFAULT NULL,
  `LinSuanYan` float DEFAULT NULL,
  `ORP` float DEFAULT NULL,
  `info_id` bigint(20) NOT NULL,
  `info_node` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`area_id`,`spot_id`,`node_id`,`data_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_sync
-- ----------------------------
DROP TABLE IF EXISTS `data_sync`;
CREATE TABLE `data_sync` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_type` varchar(255) NOT NULL,
  `info_node` varchar(255) NOT NULL,
  `sync_type` varchar(255) NOT NULL,
  `sync_status` varchar(255) NOT NULL,
  `success_time` datetime DEFAULT NULL,
  `fail_time` datetime DEFAULT NULL,
  `fail_count` int(255) DEFAULT '0',
  `sync_msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`info_id`,`info_type`,`info_node`)
) ENGINE=InnoDB AUTO_INCREMENT=513 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for monitor_data
-- ----------------------------
DROP TABLE IF EXISTS `monitor_data`;
CREATE TABLE `monitor_data` (
  `spot_id` varchar(30) NOT NULL,
  `area_id` varchar(30) NOT NULL,
  `node_id` varchar(30) NOT NULL,
  `data_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Conductivity` float DEFAULT NULL,
  `Temperature` float DEFAULT NULL,
  `PH` float DEFAULT NULL,
  `Salinity` float DEFAULT NULL,
  `DissolvedOxygen` float DEFAULT NULL,
  `Turbidity` float DEFAULT NULL,
  `info_id` bigint(20) DEFAULT NULL,
  `info_node` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`spot_id`,`area_id`,`node_id`,`data_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for spot_node
-- ----------------------------
DROP TABLE IF EXISTS `spot_node`;
CREATE TABLE `spot_node` (
  `node_id` varchar(30) NOT NULL,
  `spot_id` varchar(30) NOT NULL,
  `area_id` varchar(30) NOT NULL,
  `node_name` varchar(30) NOT NULL,
  `node_type` varchar(30) NOT NULL,
  `node_note` text,
  `info_id` bigint(20) NOT NULL,
  `info_node` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`node_id`,`spot_id`,`area_id`),
  KEY `spot_id` (`spot_id`),
  KEY `area_id` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `area_id` varchar(50) NOT NULL,
  `spot_id` varchar(50) NOT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `modified` timestamp NULL DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id`),
  KEY `usrindex` (`username`) USING BTREE,
  KEY `area_id` (`area_id`),
  KEY `spot_id` (`spot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
