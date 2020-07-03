/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50516
Source Host           : localhost:3306
Source Database       : water2

Target Server Type    : MYSQL
Target Server Version : 50516
File Encoding         : 65001

Date: 2020-06-05 14:16:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area_spot
-- ----------------------------
DROP TABLE IF EXISTS `area_spot`;
CREATE TABLE `area_spot` (
  `info_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`info_id`),
  KEY `spot_id` (`spot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for box_sensor
-- ----------------------------
DROP TABLE IF EXISTS `box_sensor`;
CREATE TABLE `box_sensor` (
  `info_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_sync
-- ----------------------------
DROP TABLE IF EXISTS `data_sync`;
CREATE TABLE `data_sync` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info_type` varchar(255) NOT NULL,
  `sync_type` varchar(255) NOT NULL,
  `sync_status` varchar(255) NOT NULL,
  `success_time` datetime DEFAULT NULL,
  `fail_time` datetime DEFAULT NULL,
  `fail_count` int(255) DEFAULT '0',
  `sync_msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`info_id`,`info_type`)
) ENGINE=InnoDB AUTO_INCREMENT=464 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for monitor_data
-- ----------------------------
DROP TABLE IF EXISTS `monitor_data`;
CREATE TABLE `monitor_data` (
  `info_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `spot_id` varchar(30) NOT NULL,
  `area_id` varchar(30) NOT NULL,
  `node_id` varchar(30) NOT NULL,
  `data_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Conductivity` float DEFAULT NULL,
  `Temperature` float DEFAULT NULL,
  `PH` float DEFAULT NULL,
  `Salinity` float DEFAULT NULL,
  `DissolvedOxygen` float DEFAULT NULL,
  `Turbidity` float DEFAULT NULL,
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for spot_node
-- ----------------------------
DROP TABLE IF EXISTS `spot_node`;
CREATE TABLE `spot_node` (
  `info_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `node_id` varchar(30) NOT NULL,
  `spot_id` varchar(30) NOT NULL,
  `area_id` varchar(30) NOT NULL,
  `node_name` varchar(30) NOT NULL,
  `node_type` varchar(30) NOT NULL,
  `node_note` text,
  PRIMARY KEY (`info_id`),
  KEY `spot_id` (`spot_id`),
  KEY `area_id` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
DROP TRIGGER IF EXISTS `insert_area_spot`;
DELIMITER ;;
CREATE TRIGGER `insert_area_spot` AFTER INSERT ON `area_spot` FOR EACH ROW begin
    insert into data_sync(info_id, info_type, sync_type,sync_status,sync_msg )
    values(new.info_id,'S','1','1','未开始数据同步');
 end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_area_spot`;
DELIMITER ;;
CREATE TRIGGER `update_area_spot` AFTER UPDATE ON `area_spot` FOR EACH ROW BEGIN
update data_sync set sync_type = '4', sync_status = 1, success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
where info_id = new.info_id and info_type = 'S';
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_area_spot`;
DELIMITER ;;
CREATE TRIGGER `delete_area_spot` BEFORE DELETE ON `area_spot` FOR EACH ROW begin
update data_sync set sync_type = '3', sync_status = '1',  success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
where info_id = old.info_id and info_type = 'S';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_box_sensor`;
DELIMITER ;;
CREATE TRIGGER `insert_box_sensor` AFTER INSERT ON `box_sensor` FOR EACH ROW begin
    insert into data_sync(info_id, info_type, sync_type,sync_status,sync_msg )
    values(new.info_id,'B','1','1','未开始数据同步');
 end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_box_sensor`;
DELIMITER ;;
CREATE TRIGGER `update_box_sensor` AFTER UPDATE ON `box_sensor` FOR EACH ROW BEGIN
update data_sync set sync_type = '4', sync_status = 1, success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
where info_id = new.info_id and info_type = 'B';
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_box_sensor`;
DELIMITER ;;
CREATE TRIGGER `delete_box_sensor` BEFORE DELETE ON `box_sensor` FOR EACH ROW begin
   update data_sync set sync_type = '3', sync_status = '1',  success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
   where info_id = old.info_id and info_type = 'B';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_monitor_data`;
DELIMITER ;;
CREATE TRIGGER `insert_monitor_data` AFTER INSERT ON `monitor_data` FOR EACH ROW begin
    insert into data_sync(info_id, info_type, sync_type,sync_status,sync_msg )
    values(new.info_id,'M','1','1','未开始数据同步');
 end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_monitor_data`;
DELIMITER ;;
CREATE TRIGGER `update_monitor_data` AFTER UPDATE ON `monitor_data` FOR EACH ROW BEGIN
update data_sync set sync_type = '4', sync_status = 1, success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
where info_id = new.info_id and info_type = 'M';
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_monitor_data`;
DELIMITER ;;
CREATE TRIGGER `delete_monitor_data` BEFORE DELETE ON `monitor_data` FOR EACH ROW begin
   update data_sync set sync_type = '3', sync_status = '1',  success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
   where info_id = old.info_id and info_type = 'M';
END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `insert_spot_node`;
DELIMITER ;;
CREATE TRIGGER `insert_spot_node` AFTER INSERT ON `spot_node` FOR EACH ROW begin
    insert into data_sync(info_id, info_type, sync_type,sync_status,sync_msg )
    values(new.info_id,'P','1','1','未开始数据同步');
 end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `update_spot_node`;
DELIMITER ;;
CREATE TRIGGER `update_spot_node` AFTER UPDATE ON `spot_node` FOR EACH ROW BEGIN
update data_sync set sync_type = '4', sync_status = 1, success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
where info_id = new.info_id and info_type = 'P';
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_spot_node`;
DELIMITER ;;
CREATE TRIGGER `delete_spot_node` BEFORE DELETE ON `spot_node` FOR EACH ROW begin
   update data_sync set sync_type = '3', sync_status = '1',  success_time = null, fail_time = null, fail_count = 0, sync_msg = '未开始数据同步'
   where info_id = old.info_id and info_type = 'P';
END
;;
DELIMITER ;
