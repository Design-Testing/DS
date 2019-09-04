start transaction;

drop database if exists `Acme-Conference`;
create database `Acme-Conference`;

use `Acme-Conference`;

drop user 'acme-user'@'%';

drop user 'acme-manager'@'%';

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `Acme-Conference`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Conference`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Conference
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `hours` int(11) DEFAULT NULL,
  `minutes` int(11) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_attachments`
--

DROP TABLE IF EXISTS `activity_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_attachments` (
  `activity` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_attachments`
--

LOCK TABLES `activity_attachments` WRITE;
/*!40000 ALTER TABLE `activity_attachments` DISABLE KEYS */;
INSERT INTO `activity_attachments` VALUES (3289,'http://www.attachmentPanel1.com'),(3290,'http://www.attachmentPanel2.com'),(3291,'http://www.attachmenTutorial1.com'),(3294,'http://www.attachmenTutorial2.com'),(3337,'http://www.attachmenPresentation1.com');
/*!40000 ALTER TABLE `activity_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_speakers`
--

DROP TABLE IF EXISTS `activity_speakers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_speakers` (
  `activity` int(11) NOT NULL,
  `speakers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_speakers`
--

LOCK TABLES `activity_speakers` WRITE;
/*!40000 ALTER TABLE `activity_speakers` DISABLE KEYS */;
INSERT INTO `activity_speakers` VALUES (3289,3328),(3289,3329),(3290,3328),(3291,3328),(3294,3328),(3335,3328),(3337,3328);
/*!40000 ALTER TABLE `activity_speakers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_owgkncu7v2k9qvyv1xqqddiwp` (`finder`),
  UNIQUE KEY `UK_i7xei45auwq1f6vu25985riuh` (`user_account`),
  UNIQUE KEY `UK_awymvli3olnnumqow6wf060pa` (`email`),
  CONSTRAINT `FK_i7xei45auwq1f6vu25985riuh` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_owgkncu7v2k9qvyv1xqqddiwp` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_form`
--

DROP TABLE IF EXISTS `actor_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_accountpassword` varchar(255) DEFAULT NULL,
  `user_accountuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_a23l8pu51orvfk6mcaobjdcy0` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_form`
--

LOCK TABLES `actor_form` WRITE;
/*!40000 ALTER TABLE `actor_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_form`
--

DROP TABLE IF EXISTS `admin_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_accountpassword` varchar(255) DEFAULT NULL,
  `user_accountuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_48672he8pkk2s7g1ro1ei71g1` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_form`
--

LOCK TABLES `admin_form` WRITE;
/*!40000 ALTER TABLE `admin_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5yk18fb3vpgubbtu0jiroyqf0` (`finder`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  UNIQUE KEY `UK_jj3mmcc0vjobqibj67dvuwihk` (`email`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_5yk18fb3vpgubbtu0jiroyqf0` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (3323,0,'Avd Reina Mercedes 1','admin1@gmail.es',NULL,'Admin1','+34647607406','http://tinyurl.com/picture.png','surname1',3312,3256),(3324,0,'Avd Reina Mercedes 2','admin2@gmail.es',NULL,'Admin2','+34647307406','http://tinyurl.com/picture.png','surname1',3313,3257);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ng8tt0uu0j791w837h00d4owq` (`finder`),
  UNIQUE KEY `UK_rjptsoy3q9dcfysbnmy1g0g31` (`user_account`),
  UNIQUE KEY `UK_grm3merlhi91rac0mu26swyhf` (`email`),
  CONSTRAINT `FK_rjptsoy3q9dcfysbnmy1g0g31` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_ng8tt0uu0j791w837h00d4owq` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (3328,0,'Avd Reina Mercedes author1','author1@gmail.es',NULL,'Author1','+34658787526','http://tinyurl.com/picture.png','surname1',3314,3261,NULL),(3329,0,'Avd Reina Mercedes author2','author2@gmail.es',NULL,'Author2','+34758787526','http://tinyurl.com/picture.png','surname1',3315,3262,NULL);
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `title_en` varchar(255) DEFAULT NULL,
  `title_es` varchar(255) DEFAULT NULL,
  `father` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5ot81c3teoleyxk4l0vwkxg47` (`father`),
  CONSTRAINT `FK_5ot81c3teoleyxk4l0vwkxg47` FOREIGN KEY (`father`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (3270,0,'CONFERENCE','CONFERENCIA',NULL),(3271,0,'COMPUTER SCIENCE','CIENCIAS DE LA COMPUTACIÓN',3270),(3272,0,'ARTIFICIAL INTELLIGENCE','INTELIGENCIA ARTIFICIAL',3271),(3273,0,'DEEP LEARNING','DEEP LEARNING',3272),(3274,0,'INSTANCE-BASED LEARNING','APRENDIZAJE BASADO EN INSTANCIAS',3272),(3275,0,'REGRESSION','REGRESIÓN',3272),(3276,0,'SOFTWARE ENGINEERING','INGENIERÍA DEL SOFTWARE',3271),(3277,0,'BIG DATA','BIG DATA',3276),(3278,0,'BIG PROCESSING','GRAN PROCESAMIENTO',3276),(3279,0,'METHODS','MÉTODOS',3276),(3280,0,'TOOLS','HERRAMIENTAS',3276),(3281,0,'PHYSICS','FÍSICA',3270),(3282,0,'CINEMATIC','CINEMÁTICA',3281),(3283,0,'ELECTRICITY','ELECTRICIDAD',3281),(3284,0,'ELECTRONICS','ELECTRÓNICA',3281),(3285,0,'BIOLOGY','BIOLOGÍA',3270),(3286,0,'ANIMALS','ANIMALES',3285),(3287,0,'PLANTS','PLANTAS',3285),(3288,0,'FUNGI','HONGOS',3285);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `activity` int(11) DEFAULT NULL,
  `author` int(11) DEFAULT NULL,
  `conference` int(11) DEFAULT NULL,
  `report` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rt0ru1ucetq3fxswmlp77qesj` (`conference`),
  KEY `FK_o38yu3ajhqffr6v4rll8k2cgs` (`report`),
  CONSTRAINT `FK_o38yu3ajhqffr6v4rll8k2cgs` FOREIGN KEY (`report`) REFERENCES `report` (`id`),
  CONSTRAINT `FK_rt0ru1ucetq3fxswmlp77qesj` FOREIGN KEY (`conference`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (3307,1,'2019-04-15 13:00:00','This is a comment','Title of comment 1',NULL,3325,NULL,3374),(3308,1,'2019-04-15 13:00:00','This is a comment','Title of comment 2',NULL,3326,NULL,3375),(3309,1,'2019-04-15 13:00:00','This is a comment','Title of comment 3',NULL,NULL,3334,NULL),(3310,0,'2019-04-15 13:00:00','This is a comment','Title of comment 4',3291,NULL,NULL,NULL),(3311,0,'2019-04-15 13:00:00','This is a comment','Title of comment 5',3289,NULL,NULL,NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `acronym` varchar(255) DEFAULT NULL,
  `camera_ready` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `is_draft` bit(1) DEFAULT NULL,
  `notification` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `submission` datetime DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `venue` varchar(255) DEFAULT NULL,
  `category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ldxxtwdsnf262yu1ctl1awe02` (`category`),
  CONSTRAINT `FK_ldxxtwdsnf262yu1ctl1awe02` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` VALUES (3334,0,'deepLearning','2019-06-12 12:34:00','2019-06-14 10:11:00',140.89,'\0','2019-06-12 12:34:00','2019-06-12 12:34:00','2019-06-12 12:34:00','Summary of conference 1','Deep-Learning y sus aplicaciones en el ámbito de la salud','deepLearning',3273),(3336,0,'software','2019-06-12 12:34:00','2019-06-21 12:12:00',1520.89,'\0','2019-06-12 12:34:00','2019-06-13 11:27:00','2019-06-12 12:34:00','Summary of conference 2','La importancia de la Ingeniería del software','software',3276),(3338,0,'fisica','2019-08-04 00:00:00','2019-08-06 00:00:00',1520.89,'\0','2019-07-31 00:00:00','2019-08-05 00:00:00','2019-07-28 00:00:00','Summary of conference 3','El alcance de la Física Moderna','fisica',3281),(3339,0,'animales','2019-08-03 00:00:00','2019-08-06 00:00:00',1520.89,'\0','2019-07-31 00:00:00','2019-08-05 00:00:00','2019-07-16 00:00:00','Summary of conference 4','Los Animales y su evolución','animales',3286),(3340,0,'investigacionGeneral','2019-10-04 00:00:00','2019-10-06 00:00:00',1520.89,'','2019-10-01 00:00:00','2019-10-05 00:00:00','2019-09-16 00:00:00','Summary of conference 5','Conferencia sobre la Investigación','hall',3270),(3341,0,'android','2019-10-04 00:00:00','2019-10-06 00:00:00',1520.89,'\0','2019-10-01 00:00:00','2019-10-05 00:00:00','2019-08-20 00:00:00','Summary of conference 6','Herramientas de desarrollo para Android','android',3280),(3342,0,'bigdata','2019-11-04 00:00:00','2019-11-06 00:00:00',1520.89,'\0','2019-10-31 00:00:00','2019-11-05 00:00:00','2019-10-20 00:00:00','Summary of conference 7','Big data en la sociedad actual','Big Data',3277),(3343,0,'iOS','2019-09-15 18:18:00','2019-09-15 18:23:00',1520.89,'\0','2019-09-15 18:15:00','2019-09-15 18:20:00','2019-09-15 18:12:00','Summary of conference 8','Herramientas de desarrollo para iOS','iOS',3280);
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_activities`
--

DROP TABLE IF EXISTS `conference_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference_activities` (
  `conference` int(11) NOT NULL,
  `activities` int(11) NOT NULL,
  UNIQUE KEY `UK_8aa5t83jo5au40ul1o1mx9hp5` (`activities`),
  KEY `FK_60tvmyyemxoce7qvrh79gn6s7` (`conference`),
  CONSTRAINT `FK_60tvmyyemxoce7qvrh79gn6s7` FOREIGN KEY (`conference`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_activities`
--

LOCK TABLES `conference_activities` WRITE;
/*!40000 ALTER TABLE `conference_activities` DISABLE KEYS */;
INSERT INTO `conference_activities` VALUES (3334,3290),(3334,3291),(3334,3335),(3336,3289),(3336,3294),(3336,3337);
/*!40000 ALTER TABLE `conference_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_form`
--

DROP TABLE IF EXISTS `conference_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `acronym` varchar(255) DEFAULT NULL,
  `camera_ready` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `notification` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `submission` datetime DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `venue` varchar(255) DEFAULT NULL,
  `category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_djbvevyt7s29dwuni2gyb6teq` (`category`),
  CONSTRAINT `FK_djbvevyt7s29dwuni2gyb6teq` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_form`
--

LOCK TABLES `conference_form` WRITE;
/*!40000 ALTER TABLE `conference_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `conference_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters`
--

DROP TABLE IF EXISTS `configuration_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_phone_code` varchar(255) DEFAULT NULL,
  `sys_name` varchar(255) DEFAULT NULL,
  `welcome_message_en` varchar(255) DEFAULT NULL,
  `welcome_message_esp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters`
--

LOCK TABLES `configuration_parameters` WRITE;
/*!40000 ALTER TABLE `configuration_parameters` DISABLE KEYS */;
INSERT INTO `configuration_parameters` VALUES (3322,0,'https://i.ibb.co/QPGwNPJ/conference.png','+34','Acme Conference','Welcome to Acme Conference! Your scientific event manager','¡Bienvenidos a Acme Conference! Su gestor de eventos científicos');
/*!40000 ALTER TABLE `configuration_parameters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters_credit_card_make`
--

DROP TABLE IF EXISTS `configuration_parameters_credit_card_make`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters_credit_card_make` (
  `configuration_parameters` int(11) NOT NULL,
  `credit_card_make` varchar(255) DEFAULT NULL,
  KEY `FK_msvomwet3mpkas6chhws7tv92` (`configuration_parameters`),
  CONSTRAINT `FK_msvomwet3mpkas6chhws7tv92` FOREIGN KEY (`configuration_parameters`) REFERENCES `configuration_parameters` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters_credit_card_make`
--

LOCK TABLES `configuration_parameters_credit_card_make` WRITE;
/*!40000 ALTER TABLE `configuration_parameters_credit_card_make` DISABLE KEYS */;
INSERT INTO `configuration_parameters_credit_card_make` VALUES (3322,'VISA'),(3322,'MCARD'),(3322,'AMEX'),(3322,'DINNERS'),(3322,'FLY');
/*!40000 ALTER TABLE `configuration_parameters_credit_card_make` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_parameters_void_words`
--

DROP TABLE IF EXISTS `configuration_parameters_void_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_parameters_void_words` (
  `configuration_parameters` int(11) NOT NULL,
  `void_words` varchar(255) DEFAULT NULL,
  KEY `FK_pydh1a3eevs9slgqtu2yhjd9q` (`configuration_parameters`),
  CONSTRAINT `FK_pydh1a3eevs9slgqtu2yhjd9q` FOREIGN KEY (`configuration_parameters`) REFERENCES `configuration_parameters` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_parameters_void_words`
--

LOCK TABLES `configuration_parameters_void_words` WRITE;
/*!40000 ALTER TABLE `configuration_parameters_void_words` DISABLE KEYS */;
INSERT INTO `configuration_parameters_void_words` VALUES (3322,'a'),(3322,'able'),(3322,'about'),(3322,'across'),(3322,'after'),(3322,'all'),(3322,'almost'),(3322,'also'),(3322,'am'),(3322,'among'),(3322,'an'),(3322,'and'),(3322,'any'),(3322,'are'),(3322,'as'),(3322,'at'),(3322,'be'),(3322,'because'),(3322,'been'),(3322,'but'),(3322,'by'),(3322,'can'),(3322,'cannot'),(3322,'could'),(3322,'dear'),(3322,'did'),(3322,'do'),(3322,'does'),(3322,'either'),(3322,'else'),(3322,'ever'),(3322,'every'),(3322,'for'),(3322,'from'),(3322,'get'),(3322,'got'),(3322,'had'),(3322,'has'),(3322,'have'),(3322,'he'),(3322,'her'),(3322,'hers'),(3322,'him'),(3322,'his'),(3322,'how'),(3322,'however'),(3322,'i'),(3322,'if'),(3322,'in'),(3322,'into'),(3322,'is'),(3322,'it'),(3322,'its'),(3322,'just'),(3322,'least'),(3322,'let'),(3322,'like'),(3322,'likely'),(3322,'may'),(3322,'me'),(3322,'might'),(3322,'most'),(3322,'must'),(3322,'my'),(3322,'neither'),(3322,'no'),(3322,'nor'),(3322,'not'),(3322,'of'),(3322,'off'),(3322,'often'),(3322,'on'),(3322,'only'),(3322,'or'),(3322,'other'),(3322,'our'),(3322,'own'),(3322,'rather'),(3322,'said'),(3322,'say'),(3322,'says'),(3322,'she'),(3322,'should'),(3322,'since'),(3322,'so'),(3322,'some'),(3322,'than'),(3322,'that'),(3322,'the'),(3322,'their'),(3322,'them'),(3322,'then'),(3322,'there'),(3322,'these'),(3322,'they'),(3322,'this'),(3322,'tis'),(3322,'to'),(3322,'too'),(3322,'twas'),(3322,'us'),(3322,'wants'),(3322,'was'),(3322,'we'),(3322,'were'),(3322,'what'),(3322,'when'),(3322,'where'),(3322,'which'),(3322,'while'),(3322,'who'),(3322,'whom'),(3322,'why'),(3322,'will'),(3322,'with'),(3322,'would'),(3322,'yet'),(3322,'you'),(3322,'your'),(3322,'a'),(3322,'acá'),(3322,'ahí'),(3322,'ajena'),(3322,'ajeno'),(3322,'ajenas'),(3322,'ajenos'),(3322,'al'),(3322,'algo'),(3322,'algún'),(3322,'alguna'),(3322,'alguno'),(3322,'algunas'),(3322,'algunos'),(3322,'allá'),(3322,'allí'),(3322,'ambos'),(3322,'ante'),(3322,'antes'),(3322,'aquel'),(3322,'aquella'),(3322,'aquello'),(3322,'aquellas'),(3322,'aquellos'),(3322,'aquí'),(3322,'arriba'),(3322,'así'),(3322,'atrás'),(3322,'aun'),(3322,'aunque'),(3322,'bajo'),(3322,'bastante'),(3322,'bien'),(3322,'cabe'),(3322,'cada'),(3322,'casi'),(3322,'cierto'),(3322,'cierta'),(3322,'ciertos'),(3322,'ciertas'),(3322,'como'),(3322,'con'),(3322,'conmigo'),(3322,'conseguimos'),(3322,'conseguir'),(3322,'consigo'),(3322,'consigue'),(3322,'consiguen'),(3322,'consigues'),(3322,'contigo'),(3322,'contra'),(3322,'cual'),(3322,'cuales'),(3322,'cualquier'),(3322,'cualquiera'),(3322,'cualquieras'),(3322,'cuan'),(3322,'cuando'),(3322,'cuanto'),(3322,'cuanta'),(3322,'cuantas'),(3322,'cuantos'),(3322,'de'),(3322,'dejar'),(3322,'del'),(3322,'demás'),(3322,'demasiada'),(3322,'demasiado'),(3322,'demasiadas'),(3322,'demasiados'),(3322,'dentro'),(3322,'desde'),(3322,'donde'),(3322,'dos'),(3322,'el'),(3322,'él'),(3322,'ella'),(3322,'ello'),(3322,'ellas'),(3322,'ellos'),(3322,'empleáis'),(3322,'emplean'),(3322,'emplear'),(3322,'empleas'),(3322,'empleo'),(3322,'en'),(3322,'encima'),(3322,'entonces'),(3322,'entre'),(3322,'era'),(3322,'eras'),(3322,'eramos'),(3322,'eran'),(3322,'eres'),(3322,'es'),(3322,'esa'),(3322,'ese'),(3322,'eso'),(3322,'esos'),(3322,'esas'),(3322,'esta'),(3322,'estas'),(3322,'estaba'),(3322,'estado'),(3322,'estáis'),(3322,'estamos'),(3322,'están'),(3322,'estar'),(3322,'este'),(3322,'esto'),(3322,'esta'),(3322,'estas'),(3322,'estos'),(3322,'estoy'),(3322,'etc'),(3322,'fin'),(3322,'fue'),(3322,'fueron'),(3322,'fui'),(3322,'fuimos'),(3322,'gueno'),(3322,'ha'),(3322,'hace'),(3322,'haces'),(3322,'hacéis'),(3322,'hacemos'),(3322,'hacen'),(3322,'hacer'),(3322,'hacia'),(3322,'hago'),(3322,'hasta'),(3322,'incluso'),(3322,'intenta'),(3322,'intentas'),(3322,'intentáis'),(3322,'intentamos'),(3322,'intentan'),(3322,'intentar'),(3322,'intento'),(3322,'ir'),(3322,'jamás'),(3322,'junto'),(3322,'juntos'),(3322,'la'),(3322,'lo'),(3322,'los'),(3322,'las'),(3322,'largo'),(3322,'más'),(3322,'me'),(3322,'menos'),(3322,'mi'),(3322,'mis'),(3322,'mía'),(3322,'mias'),(3322,'mientras'),(3322,'mío'),(3322,'mios'),(3322,'misma'),(3322,'mismo'),(3322,'mismas'),(3322,'mismos'),(3322,'modo'),(3322,'mucha'),(3322,'mucho'),(3322,'muchos'),(3322,'muchas'),(3322,'muchísima'),(3322,'muchísimo'),(3322,'muchísimas'),(3322,'muchísimos'),(3322,'mucho'),(3322,'muchos'),(3322,'muy'),(3322,'nada'),(3322,'ni'),(3322,'ningún'),(3322,'ninguna'),(3322,'ningunas'),(3322,'ningunos'),(3322,'no'),(3322,'nos'),(3322,'nosotras'),(3322,'nosotros'),(3322,'nuestra'),(3322,'nuestras'),(3322,'nuestro'),(3322,'nuestros'),(3322,'nunca'),(3322,'os'),(3322,'otra'),(3322,'otro'),(3322,'otros'),(3322,'otras'),(3322,'para'),(3322,'parecer'),(3322,'pero'),(3322,'poca'),(3322,'poco'),(3322,'pocas'),(3322,'pocos'),(3322,'podéis'),(3322,'podemos'),(3322,'poder'),(3322,'podría'),(3322,'podrías'),(3322,'podríais'),(3322,'podríamos'),(3322,'podrían'),(3322,'por'),(3322,'por qué'),(3322,'porque'),(3322,'primero'),(3322,'puede'),(3322,'pueden'),(3322,'puedo'),(3322,'pues'),(3322,'que'),(3322,'qué'),(3322,'querer'),(3322,'quién'),(3322,'quienes'),(3322,'quienesquiera'),(3322,'quienquiera'),(3322,'quizá'),(3322,'quizás'),(3322,'sabe'),(3322,'sabes'),(3322,'saben'),(3322,'sabéis'),(3322,'sabemos'),(3322,'saber'),(3322,'se'),(3322,'según'),(3322,'ser'),(3322,'si'),(3322,'sí'),(3322,'siempre'),(3322,'siendo'),(3322,'sin'),(3322,'sino'),(3322,'so'),(3322,'sobre'),(3322,'sois'),(3322,'solamente'),(3322,'solo'),(3322,'sólo'),(3322,'somos'),(3322,'soy'),(3322,'sr'),(3322,'sra'),(3322,'sres'),(3322,'sta'),(3322,'su'),(3322,'sus'),(3322,'suya'),(3322,'suyo'),(3322,'suyas'),(3322,'suyos'),(3322,'tal'),(3322,'tales'),(3322,'también'),(3322,'tampoco'),(3322,'tan'),(3322,'tanta'),(3322,'tanto'),(3322,'tantas'),(3322,'tantos'),(3322,'te'),(3322,'tenéis'),(3322,'tenemos'),(3322,'tener'),(3322,'tengo'),(3322,'ti'),(3322,'tiempo'),(3322,'tiene'),(3322,'tienen'),(3322,'toda'),(3322,'todo'),(3322,'todas'),(3322,'todos'),(3322,'tomar'),(3322,'trabaja'),(3322,'trabaja'),(3322,'trabajáis'),(3322,'trabajamos'),(3322,'trabajan'),(3322,'trabajar'),(3322,'trabajas'),(3322,'tras'),(3322,'tú'),(3322,'tu'),(3322,'tus'),(3322,'tuya'),(3322,'tuyo'),(3322,'tuyas'),(3322,'tuyos'),(3322,'último'),(3322,'ultimo'),(3322,'un'),(3322,'una'),(3322,'uno'),(3322,'unas'),(3322,'uno'),(3322,'una'),(3322,'unos'),(3322,'unas'),(3322,'usa'),(3322,'usas'),(3322,'usáis'),(3322,'usamos'),(3322,'usan'),(3322,'usar'),(3322,'uso'),(3322,'usted'),(3322,'ustedes'),(3322,'va'),(3322,'van'),(3322,'vais'),(3322,'valor'),(3322,'vamos'),(3322,'varias'),(3322,'varios'),(3322,'vaya'),(3322,'verdadera'),(3322,'vosotras'),(3322,'vosotros'),(3322,'voy'),(3322,'vuestra'),(3322,'vuestro'),(3322,'vuestras'),(3322,'vuestros'),(3322,'y'),(3322,'ya'),(3322,'yo');
/*!40000 ALTER TABLE `configuration_parameters_void_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `maximum_fee` double DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (3312,0,'Coding','2019-04-02 13:00:00','Conference',10,'2019-04-15'),(3313,0,NULL,'2019-06-02 13:00:00','Literatura',NULL,'2019-06-02'),(3314,0,'Coding','2019-04-02 13:00:00','Conference',10,'2019-04-15'),(3315,0,NULL,'2019-06-02 13:00:00','Literatura',NULL,'2019-06-02'),(3316,0,'Coding','2019-04-02 13:00:00','Conference',10,'2019-04-15'),(3317,0,NULL,'2019-06-02 13:00:00','Literatura',NULL,'2019-06-02'),(3318,0,NULL,'2019-06-02 13:00:00','Asos',NULL,'2019-06-02'),(3319,0,'Coding','2019-04-02 13:00:00','Conference',10,'2019-04-15'),(3320,0,NULL,'2019-06-02 13:00:00','Literatura',NULL,'2019-06-02'),(3321,0,'Coding','2019-04-02 13:00:00','Conference',10,'2019-04-15');
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_conferences`
--

DROP TABLE IF EXISTS `finder_conferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_conferences` (
  `finder` int(11) NOT NULL,
  `conferences` int(11) NOT NULL,
  KEY `FK_nfuvovuvbk05r9vbyg5mbstxx` (`conferences`),
  KEY `FK_k666b9rxu2vqkol7uc9ostni9` (`finder`),
  CONSTRAINT `FK_k666b9rxu2vqkol7uc9ostni9` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_nfuvovuvbk05r9vbyg5mbstxx` FOREIGN KEY (`conferences`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_conferences`
--

LOCK TABLES `finder_conferences` WRITE;
/*!40000 ALTER TABLE `finder_conferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_conferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_lnclqq7vcaqpv8y5di1gg5bm3` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (3348,0,'Out box',3323),(3349,0,'In box',3323),(3350,0,'Out box',3324),(3351,0,'In box',3324),(3352,0,'Out box',3328),(3353,0,'In box',3328),(3354,0,'Out box',3329),(3355,0,'In box',3329),(3356,0,'Out box',3330),(3357,0,'In box',3330),(3358,0,'Out box',3331),(3359,0,'In box',3331),(3360,0,'Out box',3325),(3361,0,'In box',3325),(3362,0,'Out box',3326),(3363,0,'In box',3326),(3364,0,'Out box',3327),(3365,0,'In box',3327);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_messages`
--

DROP TABLE IF EXISTS `folder_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_messages` (
  `folder` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  KEY `FK_pd7js9rp0nie7ft4b2ltq7jx0` (`messages`),
  KEY `FK_p4c0hkadh5uwpdsjbyqfkauak` (`folder`),
  CONSTRAINT `FK_p4c0hkadh5uwpdsjbyqfkauak` FOREIGN KEY (`folder`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_pd7js9rp0nie7ft4b2ltq7jx0` FOREIGN KEY (`messages`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_messages`
--

LOCK TABLES `folder_messages` WRITE;
/*!40000 ALTER TABLE `folder_messages` DISABLE KEYS */;
INSERT INTO `folder_messages` VALUES (3348,3332),(3349,3333),(3351,3333),(3353,3332),(3354,3333);
/*!40000 ALTER TABLE `folder_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender` int(11) NOT NULL,
  `topic` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9e5snvu7d6i2dypiwctuvm8fs` (`topic`),
  CONSTRAINT `FK_9e5snvu7d6i2dypiwctuvm8fs` FOREIGN KEY (`topic`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (3332,0,'Este es el mensaje1','2019-06-12 12:34:00','Asunto del mensaje1',3323,3269),(3333,0,'Este es el mensaje2','2019-06-12 11:34:00','Asunto del mensaje2',3329,3267);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_recivers`
--

DROP TABLE IF EXISTS `message_recivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_recivers` (
  `message` int(11) NOT NULL,
  `recivers` int(11) NOT NULL,
  KEY `FK_1twp775dwmcambuhvkpejn2ac` (`message`),
  CONSTRAINT `FK_1twp775dwmcambuhvkpejn2ac` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_recivers`
--

LOCK TABLES `message_recivers` WRITE;
/*!40000 ALTER TABLE `message_recivers` DISABLE KEYS */;
INSERT INTO `message_recivers` VALUES (3332,3328),(3333,3323),(3333,3324);
/*!40000 ALTER TABLE `message_recivers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `panel`
--

DROP TABLE IF EXISTS `panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `panel` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `hours` int(11) DEFAULT NULL,
  `minutes` int(11) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `panel`
--

LOCK TABLES `panel` WRITE;
/*!40000 ALTER TABLE `panel` DISABLE KEYS */;
INSERT INTO `panel` VALUES (3289,0,1,15,'123','2019-06-12 12:34:00','Summany of panerl 1','Title of panel 1'),(3290,0,2,20,'124','2019-06-13 12:30:00','Summany of panerl 2','Title of panel 2');
/*!40000 ALTER TABLE `panel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paper`
--

DROP TABLE IF EXISTS `paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paper` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `document` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paper`
--

LOCK TABLES `paper` WRITE;
/*!40000 ALTER TABLE `paper` DISABLE KEYS */;
INSERT INTO `paper` VALUES (3296,0,'http://www.documentPaper1.com','Summary of paper 1','Title of paper 1'),(3297,0,'http://www.documentPaper2.com','Summary of paper 2','Title of paper 2'),(3298,0,'http://www.documentPaper3.com','Summary of paper 3','Title of paper 3 per conference summary'),(3299,0,'http://www.documentPaper4.com','Summary of paper 4','Title of paper 4'),(3300,0,'http://www.documentPaper5.com','Summary of paper 5','Title of paper 5'),(3301,0,'http://www.documentPaper6.com','Summary of paper 6','Title of paper 6'),(3302,0,'http://www.documentPaper7.com','Summary of paper 7','Title of paper 7'),(3303,0,'http://www.documentPaper8.com','Summary of paper 8','Title of paper 8'),(3304,0,'http://www.documentPaper9.com','Summary of paper 9','Title of paper 9'),(3305,0,'http://www.documentPaper21.com','Summary of paper 21','Title of paper 21'),(3306,0,'http://www.documentPaper22.com','Summary of paper 22','Title of paper 22');
/*!40000 ALTER TABLE `paper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paper_authors`
--

DROP TABLE IF EXISTS `paper_authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paper_authors` (
  `paper` int(11) NOT NULL,
  `authors` varchar(255) DEFAULT NULL,
  KEY `FK_ae9a28ln0ji506i8uoqhv563h` (`paper`),
  CONSTRAINT `FK_ae9a28ln0ji506i8uoqhv563h` FOREIGN KEY (`paper`) REFERENCES `paper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paper_authors`
--

LOCK TABLES `paper_authors` WRITE;
/*!40000 ALTER TABLE `paper_authors` DISABLE KEYS */;
INSERT INTO `paper_authors` VALUES (3296,'Author 1 of paper1'),(3296,'Author 2 of paper1'),(3297,'Author of paper2'),(3298,'Author of paper3'),(3299,'Author of paper4'),(3300,'Author of paper5'),(3301,'Author of paper6'),(3302,'Author of paper7'),(3303,'Author of paper8'),(3304,'Author of paper9'),(3305,'Author 1 of paper21'),(3305,'Author 2 of paper21'),(3306,'Author 1 of paper22');
/*!40000 ALTER TABLE `paper_authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presentation`
--

DROP TABLE IF EXISTS `presentation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presentation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `hours` int(11) DEFAULT NULL,
  `minutes` int(11) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `camera_ready_paper` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sgmmubx8ftlft0yxjdvnft0m8` (`camera_ready_paper`),
  CONSTRAINT `FK_sgmmubx8ftlft0yxjdvnft0m8` FOREIGN KEY (`camera_ready_paper`) REFERENCES `paper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentation`
--

LOCK TABLES `presentation` WRITE;
/*!40000 ALTER TABLE `presentation` DISABLE KEYS */;
INSERT INTO `presentation` VALUES (3335,0,1,15,'126','2019-06-10 12:34:00','Summany of presentation 2','Title of presentation 2',3306),(3337,0,1,15,'126','2019-06-12 12:34:00','Summany of presentation 1','Title of presentation 1',3305);
/*!40000 ALTER TABLE `presentation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `author` int(11) NOT NULL,
  `conference` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jpwxbrhvii9ekt338bmrdatfg` (`author`),
  KEY `FK_lc3odbpd5lgo7qc3w33ugwafj` (`conference`),
  CONSTRAINT `FK_lc3odbpd5lgo7qc3w33ugwafj` FOREIGN KEY (`conference`) REFERENCES `conference` (`id`),
  CONSTRAINT `FK_jpwxbrhvii9ekt338bmrdatfg` FOREIGN KEY (`author`) REFERENCES `author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
INSERT INTO `registration` VALUES (3344,0,'163',6,19,'HolderName 1','VISA','4716477920082572',3328,3334),(3345,0,'728',10,20,'HolderName 2','MASTER','5498128346540526',3329,3336);
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration_form`
--

DROP TABLE IF EXISTS `registration_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `author` int(11) NOT NULL,
  `conference` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j5mvycmxs6ghy7pdh5k24aaow` (`author`),
  KEY `FK_89gk1eg6ex1i9a1vq01mlenoa` (`conference`),
  CONSTRAINT `FK_89gk1eg6ex1i9a1vq01mlenoa` FOREIGN KEY (`conference`) REFERENCES `conference` (`id`),
  CONSTRAINT `FK_j5mvycmxs6ghy7pdh5k24aaow` FOREIGN KEY (`author`) REFERENCES `author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration_form`
--

LOCK TABLES `registration_form` WRITE;
/*!40000 ALTER TABLE `registration_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `registration_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `decision` varchar(255) DEFAULT NULL,
  `is_draft` bit(1) DEFAULT NULL,
  `originality` int(11) DEFAULT NULL,
  `quality` int(11) DEFAULT NULL,
  `readability` int(11) DEFAULT NULL,
  `reviewer` int(11) NOT NULL,
  `submission` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_26pbnwfa1gklnebnnsotvqt88` (`reviewer`),
  KEY `FK_a0lt5jqh9b7s1gw3q77nywxxn` (`submission`),
  CONSTRAINT `FK_a0lt5jqh9b7s1gw3q77nywxxn` FOREIGN KEY (`submission`) REFERENCES `submission` (`id`),
  CONSTRAINT `FK_26pbnwfa1gklnebnnsotvqt88` FOREIGN KEY (`reviewer`) REFERENCES `reviewer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (3374,0,'ACCEPT','\0',5,0,7,3325,3366),(3375,0,'ACCEPT','\0',8,7,10,3326,3367);
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviewer`
--

DROP TABLE IF EXISTS `reviewer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviewer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1wsbx4vhaxhkixxv3ej7vrrr3` (`finder`),
  UNIQUE KEY `UK_ite8gbxlfjyy7g7wqqiyjhkmn` (`user_account`),
  UNIQUE KEY `UK_h8t15e4s3v8vv07tp9rrt3qy3` (`email`),
  CONSTRAINT `FK_ite8gbxlfjyy7g7wqqiyjhkmn` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_1wsbx4vhaxhkixxv3ej7vrrr3` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviewer`
--

LOCK TABLES `reviewer` WRITE;
/*!40000 ALTER TABLE `reviewer` DISABLE KEYS */;
INSERT INTO `reviewer` VALUES (3325,0,'Avd Reina Mercedes reviewer1','reviewer1@gmail.es',NULL,'Reviewer1','+34647487526','http://tinyurl.com/picture.png','surname1',3316,3258),(3326,0,'Avd Reina Mercedes reviewer2','reviewer2@gmail.es',NULL,'Reviewer2','+34647467526','http://tinyurl.com/picture.png','surname1',3317,3259),(3327,0,'Avd La Salle','carlosgonzalez@gmail.es',NULL,'Carlos','+34647467526','http://tinyurl.com/picture.png','Gonzalez',3318,3260);
/*!40000 ALTER TABLE `reviewer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviewer_form`
--

DROP TABLE IF EXISTS `reviewer_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviewer_form` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_accountpassword` varchar(255) DEFAULT NULL,
  `user_accountuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m6gnac4y94r4wfex8lqdclhf5` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviewer_form`
--

LOCK TABLES `reviewer_form` WRITE;
/*!40000 ALTER TABLE `reviewer_form` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviewer_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviewer_form_keywords`
--

DROP TABLE IF EXISTS `reviewer_form_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviewer_form_keywords` (
  `reviewer_form` int(11) NOT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  KEY `FK_82h4mbn3ievoy7mkbe8pqj9rf` (`reviewer_form`),
  CONSTRAINT `FK_82h4mbn3ievoy7mkbe8pqj9rf` FOREIGN KEY (`reviewer_form`) REFERENCES `reviewer_form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviewer_form_keywords`
--

LOCK TABLES `reviewer_form_keywords` WRITE;
/*!40000 ALTER TABLE `reviewer_form_keywords` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviewer_form_keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviewer_keywords`
--

DROP TABLE IF EXISTS `reviewer_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviewer_keywords` (
  `reviewer` int(11) NOT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  KEY `FK_d1mpqlt4vmfyn53hbyt5la0uv` (`reviewer`),
  CONSTRAINT `FK_d1mpqlt4vmfyn53hbyt5la0uv` FOREIGN KEY (`reviewer`) REFERENCES `reviewer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviewer_keywords`
--

LOCK TABLES `reviewer_keywords` WRITE;
/*!40000 ALTER TABLE `reviewer_keywords` DISABLE KEYS */;
INSERT INTO `reviewer_keywords` VALUES (3325,'linux'),(3325,'windows'),(3325,'desarrollo'),(3326,'linux'),(3326,'iOS'),(3327,'asus'),(3327,'iris');
/*!40000 ALTER TABLE `reviewer_keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (3292,0,'Summary of section 1','Title of section 1'),(3293,0,'Summary of section 2','Title of section 2'),(3295,0,'Summary of section 3','Title of section 3');
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section_pictures`
--

DROP TABLE IF EXISTS `section_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section_pictures` (
  `section` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_fpgvw49vb6tytfbfcghj3o8sv` (`section`),
  CONSTRAINT `FK_fpgvw49vb6tytfbfcghj3o8sv` FOREIGN KEY (`section`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section_pictures`
--

LOCK TABLES `section_pictures` WRITE;
/*!40000 ALTER TABLE `section_pictures` DISABLE KEYS */;
INSERT INTO `section_pictures` VALUES (3292,'http://www.picture1Section1.com'),(3292,'http://www.picture2Section1.com'),(3293,'http://www.picture1Section2.com'),(3293,'http://www.picture2Section2.com');
/*!40000 ALTER TABLE `section_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tkkhqkty8xrwu9jmpb8jyij7c` (`finder`),
  UNIQUE KEY `UK_du2w5ldt8rvlvxvtr7vyxk7g3` (`user_account`),
  UNIQUE KEY `UK_7inh7wiji1x5vpu5u3vh0funf` (`email`),
  CONSTRAINT `FK_du2w5ldt8rvlvxvtr7vyxk7g3` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_tkkhqkty8xrwu9jmpb8jyij7c` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
INSERT INTO `sponsor` VALUES (3330,0,'Avd Reina Mercedes sponsor1','sponsor1@gmail.es',NULL,'sponsor1','+34658787526','http://tinyurl.com/picture.png','surname1',3319,3263),(3331,0,'Avd Reina Mercedes sponsor2','sponsor2@gmail.es',NULL,'sponsor2','+34758787526','http://tinyurl.com/picture.png','surname1',3320,3264);
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cvv` varchar(255) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `target_page` varchar(255) DEFAULT NULL,
  `sponsor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_huglhkud0ihqdljyou4eshra6` (`sponsor`),
  CONSTRAINT `FK_huglhkud0ihqdljyou4eshra6` FOREIGN KEY (`sponsor`) REFERENCES `sponsor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (3346,0,'https://i.imgur.com/7b8lu4b.png','533',6,19,'HolderName 3','AMEX','375278545368168','http://www.targetPage1.com',3330),(3347,0,'https://i.imgur.com/7b8lu4b.png','266',10,19,'HolderName 4','VISA','4532787155338743','http://www.targetPage2.com',3331);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submission`
--

DROP TABLE IF EXISTS `submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submission` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_notified` bit(1) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `author` int(11) NOT NULL,
  `camera_ready_paper` int(11) DEFAULT NULL,
  `conference` int(11) NOT NULL,
  `review_paper` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9ayhftkow8judgm0cblwdb9mi` (`ticker`),
  KEY `FK_ssk77t9sokwi9utdru9hvodul` (`author`),
  KEY `FK_lwhy0crx3d39kub8vb6tewa1q` (`camera_ready_paper`),
  KEY `FK_1vynnfw6cw1l40c8e342st672` (`conference`),
  KEY `FK_s7m88dyh0kq6f91kwfqjcjsgr` (`review_paper`),
  CONSTRAINT `FK_s7m88dyh0kq6f91kwfqjcjsgr` FOREIGN KEY (`review_paper`) REFERENCES `paper` (`id`),
  CONSTRAINT `FK_1vynnfw6cw1l40c8e342st672` FOREIGN KEY (`conference`) REFERENCES `conference` (`id`),
  CONSTRAINT `FK_lwhy0crx3d39kub8vb6tewa1q` FOREIGN KEY (`camera_ready_paper`) REFERENCES `paper` (`id`),
  CONSTRAINT `FK_ssk77t9sokwi9utdru9hvodul` FOREIGN KEY (`author`) REFERENCES `author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission`
--

LOCK TABLES `submission` WRITE;
/*!40000 ALTER TABLE `submission` DISABLE KEYS */;
INSERT INTO `submission` VALUES (3366,0,'\0','2019-06-11 17:25:00','UNDER-REVIEWED','AUT-EP8V',3328,NULL,3334,3296),(3367,0,'\0','2019-06-09 07:25:00','ACCEPTED','AUT-BQ28',3329,3298,3334,3297),(3368,0,'\0','2019-07-27 07:25:00','ACCEPTED','AUX-BQ28',3328,NULL,3338,3299),(3369,0,'\0','2019-08-20 07:25:00','ACCEPTED','AUX-BQ29',3328,NULL,3341,3300),(3370,0,'\0','2019-08-20 07:25:00','UNDER-REVIEWED','AUX-BQ23',3328,NULL,3341,3301),(3371,0,'\0','2019-08-20 07:25:00','UNDER-REVIEWED','AUX-BQ10',3328,NULL,3341,3302),(3372,0,'\0','2019-08-20 07:25:00','UNDER-REVIEWED','AUX-BQ11',3328,NULL,3341,3303),(3373,0,'\0','2019-08-20 07:25:00','UNDER-REVIEWED','AUX-BQ22',3328,NULL,3340,3304);
/*!40000 ALTER TABLE `submission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `english` varchar(255) DEFAULT NULL,
  `spanish` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (3265,0,'INQUIRY','INVESTIGACION'),(3266,0,'REBUTTAL','REFUTACION'),(3267,0,'REGISTRATION','REGISTRO'),(3268,0,'TOPICS','ASUNTOS'),(3269,0,'OTHER','OTRO');
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial`
--

DROP TABLE IF EXISTS `tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `hours` int(11) DEFAULT NULL,
  `minutes` int(11) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial`
--

LOCK TABLES `tutorial` WRITE;
/*!40000 ALTER TABLE `tutorial` DISABLE KEYS */;
INSERT INTO `tutorial` VALUES (3291,0,1,15,'126','2019-06-12 12:34:00','Summany of tutorial 1','Title of tutorial 1'),(3294,0,1,15,'126','2019-06-12 12:34:00','Summany of tutorial 2','Title of tutorial 2');
/*!40000 ALTER TABLE `tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_sections`
--

DROP TABLE IF EXISTS `tutorial_sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial_sections` (
  `tutorial` int(11) NOT NULL,
  `sections` int(11) NOT NULL,
  UNIQUE KEY `UK_fam9vwtnrx0m7vmnqngwoekdo` (`sections`),
  KEY `FK_8sl8cpfc93exnk3nv9a6okamu` (`tutorial`),
  CONSTRAINT `FK_8sl8cpfc93exnk3nv9a6okamu` FOREIGN KEY (`tutorial`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_fam9vwtnrx0m7vmnqngwoekdo` FOREIGN KEY (`sections`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_sections`
--

LOCK TABLES `tutorial_sections` WRITE;
/*!40000 ALTER TABLE `tutorial_sections` DISABLE KEYS */;
INSERT INTO `tutorial_sections` VALUES (3291,3292),(3291,3293),(3294,3295);
/*!40000 ALTER TABLE `tutorial_sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (3256,0,'e00cf25ad42683b3df678c61f42c6bda','admin1'),(3257,0,'c84258e9c39059a89ab77d846ddab909','admin2'),(3258,0,'6ce19528a40dde9521d97cf7ba264eca','reviewer1'),(3259,0,'2693b57f0f59df94caacefb811e99851','reviewer2'),(3260,0,'315d31e7c8f3a136610aafa220d689be','reviewer3'),(3261,0,'b312ba4ffd5245fa2a1ab819ec0d0347','author1'),(3262,0,'9bd97baef2b853ec00cc3cffd269f679','author2'),(3263,0,'42c63ad66d4dc07ed17753772bef96d6','sponsor1'),(3264,0,'3dc67f80a03324e01b1640f45d107485','sponsor2');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (3256,'ADMIN'),(3257,'ADMIN'),(3258,'REVIEWER'),(3259,'REVIEWER'),(3260,'REVIEWER'),(3261,'AUTHOR'),(3262,'AUTHOR'),(3263,'SPONSOR'),(3264,'SPONSOR');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-04 21:14:36
commit;