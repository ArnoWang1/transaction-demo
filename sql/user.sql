/*
SQLyog Professional v12.09 (64 bit)
MySQL - 8.0.19 : Database - db_service_user
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_service_user` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `db_service_user`;

/*Table structure for table `tb_main_transaction` */

DROP TABLE IF EXISTS `tb_main_transaction`;

CREATE TABLE `tb_main_transaction` (
  `id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '全局事务ID',
  `state` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '事务状态',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主服务事务记录表';

/*Data for the table `tb_main_transaction` */

LOCK TABLES `tb_main_transaction` WRITE;

UNLOCK TABLES;

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '--' COMMENT '用户账号',
  `nickname` varchar(32) NOT NULL DEFAULT '--' COMMENT '用户昵称',
  `score` int NOT NULL DEFAULT '0' COMMENT '用户积分',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户基础信息表';

/*Data for the table `tb_user` */

LOCK TABLES `tb_user` WRITE;

insert  into `tb_user`(`id`,`username`,`nickname`,`score`,`create_time`,`modify_time`) values (1,'admin','管理员',0,'1970-01-01 00:00:00','1970-01-01 00:00:00');

UNLOCK TABLES;

/*Table structure for table `tb_user_level` */

DROP TABLE IF EXISTS `tb_user_level`;

CREATE TABLE `tb_user_level` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '--' COMMENT '用户账号',
  `level` int NOT NULL DEFAULT '1' COMMENT '用户等级',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户等级信息表';

/*Data for the table `tb_user_level` */

LOCK TABLES `tb_user_level` WRITE;

insert  into `tb_user_level`(`id`,`username`,`level`,`create_time`,`modify_time`) values (1,'admin',1,'1970-01-01 00:00:00','1970-01-01 00:00:00');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
