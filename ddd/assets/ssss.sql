/*
SQLyog v10.2 
MySQL - 5.5.15 : Database - crm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`crm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `crm`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `foodid` varchar(30) DEFAULT NULL,
  `content` varchar(30) DEFAULT NULL,
  `memo` varchar(20) DEFAULT NULL,
  `foodname` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`id`,`username`,`foodid`,`content`,`memo`,`foodname`) values (1,'lk','1','很好吃','西餐','西餐'),(2,'123','2','这里很好吃','dsf','西餐'),(3,'','2','3333333','dsf','饮料');

/*Table structure for table `food` */

DROP TABLE IF EXISTS `food`;

CREATE TABLE `food` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `url` varchar(30) DEFAULT NULL,
  `price` varchar(30) DEFAULT NULL,
  `monthcount` varchar(30) DEFAULT NULL,
  `minutes` varchar(30) DEFAULT NULL,
  `memo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `food` */

insert  into `food`(`id`,`name`,`url`,`price`,`monthcount`,`minutes`,`memo`) values (2,'时尚餐厅','好喝','10','2016','12',NULL);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `phone` varchar(40) DEFAULT NULL,
  `flag` varchar(20) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `price` varchar(20) DEFAULT NULL,
  `memo` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=432438 DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`id`,`name`,`phone`,`flag`,`content`,`price`,`memo`,`address`) values (1,'2','18457547786','已接单','5,6,7元美食专区-田园汉堡; 5,6,7元美食专区-新奥尔良烤翅; ','14','null','4'),(2,'123','13737697762','已配送','5,6,7元美食专区-薯条小; 5,6,7元美食专区-薯条小; 5,6,7元美食专区-薯条小; ','21','null','6'),(432437,'58','58','已提交','','0','null','8');

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `pwd` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `realname` varchar(30) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`name`,`pwd`,`phone`,`realname`,`address`,`role`) values (1,'555','123','18276419941','李克','服务台','admin'),(2,'683','378','18276419941','张三','服务台','admin'),(3,'123','123','13737697762','张三','6','user'),(4,'56','56','18624567654','李四','7','user'),(5,'77','77','17376546789','李力','4','user'),(6,'1','1','1','1','1','user'),(7,'lk','lk','18457547786','ss','4','user'),(8,'kk','kk','12456896543','lik','3','user'),(9,'23','23','2','','3','user'),(10,'36','36','36','6','6','user'),(11,'58','58','58','58','8','user'),(12,'','','','','','user'),(13,'','','','','','user');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
