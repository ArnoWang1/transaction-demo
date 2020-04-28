/*
SQLyog Professional v12.09 (64 bit)
MySQL - 8.0.19 : Database - db_service_order
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_service_order` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_service_order`;

/*Table structure for table `tb_local_transaction` */

DROP TABLE IF EXISTS `tb_local_transaction`;

CREATE TABLE `tb_local_transaction` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '全局事务ID',
  `state` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '事务状态',
  `param` varchar(256) NOT NULL DEFAULT '' COMMENT '分服务事务参数',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最后修改时间',
  `version` int NOT NULL DEFAULT '1' COMMENT '数据版本号，乐观锁使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分服务事务记录表';

/*Data for the table `tb_local_transaction` */

LOCK TABLES `tb_local_transaction` WRITE;

UNLOCK TABLES;

/*Table structure for table `tb_order` */

DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_no` int NOT NULL DEFAULT '0' COMMENT '订单编号',
  `order_name` varchar(32) NOT NULL DEFAULT '--' COMMENT '订单名称',
  `score` int NOT NULL DEFAULT '0' COMMENT '订单积分',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='订单基础信息表';

/*Data for the table `tb_order` */

LOCK TABLES `tb_order` WRITE;

insert  into `tb_order`(`id`,`order_no`,`order_name`,`score`,`create_time`,`modify_time`) values (1,2020,'2020订单',0,'1970-01-01 00:00:00','1970-01-01 00:00:00');

UNLOCK TABLES;

/*Table structure for table `tb_order_level` */

DROP TABLE IF EXISTS `tb_order_level`;

CREATE TABLE `tb_order_level` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_no` int NOT NULL DEFAULT '0' COMMENT '订单编号',
  `level` int NOT NULL DEFAULT '1' COMMENT '订单等级',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='订单等级信息表';

/*Data for the table `tb_order_level` */

LOCK TABLES `tb_order_level` WRITE;

insert  into `tb_order_level`(`id`,`order_no`,`level`,`create_time`,`modify_time`) values (1,2020,1,'1970-01-01 00:00:00','1970-01-01 00:00:00');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
