start transaction;

use `b2rwt6yaoachffgkfsym`;

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
INSERT INTO `administrator` VALUES (64,0,'Avd Reina Mercedes 1','admin1@gmail.es',NULL,'Admin1','+34647607406','http://tinyurl.com/picture.png','surname1',38,37);
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
INSERT INTO `category` VALUES (40,0,'CONFERENCE','CONFERENCIA',NULL),(41,0,'COMPUTER SCIENCE','CIENCIAS DE LA COMPUTACIÓN',40),(42,0,'ARTIFICIAL INTELLIGENCE','INTELIGENCIA ARTIFICIAL',41),(43,0,'DEEP LEARNING','DEEP LEARNING',42),(44,0,'INSTANCE-BASED LEARNING','APRENDIZAJE BASADO EN INSTANCIAS',42),(45,0,'REGRESSION','REGRESIÓN',42),(46,0,'SOFTWARE ENGINEERING','INGENIERÍA DEL SOFTWARE',41),(47,0,'BIG DATA','BIG DATA',46),(48,0,'BIG PROCESSING','GRAN PROCESAMIENTO',46),(49,0,'METHODS','MÉTODOS',46),(50,0,'TOOLS','HERRAMIENTAS',46),(51,0,'PHYSICS','FÍSICA',40),(52,0,'CINEMATIC','CINEMÁTICA',51),(53,0,'ELECTRICITY','ELECTRICIDAD',51),(54,0,'ELECTRONICS','ELECTRÓNICA',51),(55,0,'BIOLOGY','BIOLOGÍA',40),(56,0,'ANIMALS','ANIMALES',55),(57,0,'PLANTS','PLANTAS',55),(58,0,'FUNGI','HONGOS',55);
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
INSERT INTO `configuration_parameters` VALUES (39,0,'https://i.ibb.co/QPGwNPJ/conference.png','+34','Acme Conference','Welcome to Acme Conference! Your scientific event manager','¡Bienvenidos a Acme Conference! Su gestor de eventos científicos');
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
INSERT INTO `configuration_parameters_credit_card_make` VALUES (39,'VISA'),(39,'MCARD'),(39,'AMEX'),(39,'DINNERS'),(39,'FLY');
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
INSERT INTO `configuration_parameters_void_words` VALUES (39,'a'),(39,'able'),(39,'about'),(39,'across'),(39,'after'),(39,'all'),(39,'almost'),(39,'also'),(39,'am'),(39,'among'),(39,'an'),(39,'and'),(39,'any'),(39,'are'),(39,'as'),(39,'at'),(39,'be'),(39,'because'),(39,'been'),(39,'but'),(39,'by'),(39,'can'),(39,'cannot'),(39,'could'),(39,'dear'),(39,'did'),(39,'do'),(39,'does'),(39,'either'),(39,'else'),(39,'ever'),(39,'every'),(39,'for'),(39,'from'),(39,'get'),(39,'got'),(39,'had'),(39,'has'),(39,'have'),(39,'he'),(39,'her'),(39,'hers'),(39,'him'),(39,'his'),(39,'how'),(39,'however'),(39,'i'),(39,'if'),(39,'in'),(39,'into'),(39,'is'),(39,'it'),(39,'its'),(39,'just'),(39,'least'),(39,'let'),(39,'like'),(39,'likely'),(39,'may'),(39,'me'),(39,'might'),(39,'most'),(39,'must'),(39,'my'),(39,'neither'),(39,'no'),(39,'nor'),(39,'not'),(39,'of'),(39,'off'),(39,'often'),(39,'on'),(39,'only'),(39,'or'),(39,'other'),(39,'our'),(39,'own'),(39,'rather'),(39,'said'),(39,'say'),(39,'says'),(39,'she'),(39,'should'),(39,'since'),(39,'so'),(39,'some'),(39,'than'),(39,'that'),(39,'the'),(39,'their'),(39,'them'),(39,'then'),(39,'there'),(39,'these'),(39,'they'),(39,'this'),(39,'tis'),(39,'to'),(39,'too'),(39,'twas'),(39,'us'),(39,'wants'),(39,'was'),(39,'we'),(39,'were'),(39,'what'),(39,'when'),(39,'where'),(39,'which'),(39,'while'),(39,'who'),(39,'whom'),(39,'why'),(39,'will'),(39,'with'),(39,'would'),(39,'yet'),(39,'you'),(39,'your'),(39,'a'),(39,'acá'),(39,'ahí'),(39,'ajena'),(39,'ajeno'),(39,'ajenas'),(39,'ajenos'),(39,'al'),(39,'algo'),(39,'algún'),(39,'alguna'),(39,'alguno'),(39,'algunas'),(39,'algunos'),(39,'allá'),(39,'allí'),(39,'ambos'),(39,'ante'),(39,'antes'),(39,'aquel'),(39,'aquella'),(39,'aquello'),(39,'aquellas'),(39,'aquellos'),(39,'aquí'),(39,'arriba'),(39,'así'),(39,'atrás'),(39,'aun'),(39,'aunque'),(39,'bajo'),(39,'bastante'),(39,'bien'),(39,'cabe'),(39,'cada'),(39,'casi'),(39,'cierto'),(39,'cierta'),(39,'ciertos'),(39,'ciertas'),(39,'como'),(39,'con'),(39,'conmigo'),(39,'conseguimos'),(39,'conseguir'),(39,'consigo'),(39,'consigue'),(39,'consiguen'),(39,'consigues'),(39,'contigo'),(39,'contra'),(39,'cual'),(39,'cuales'),(39,'cualquier'),(39,'cualquiera'),(39,'cualquieras'),(39,'cuan'),(39,'cuando'),(39,'cuanto'),(39,'cuanta'),(39,'cuantas'),(39,'cuantos'),(39,'de'),(39,'dejar'),(39,'del'),(39,'demás'),(39,'demasiada'),(39,'demasiado'),(39,'demasiadas'),(39,'demasiados'),(39,'dentro'),(39,'desde'),(39,'donde'),(39,'dos'),(39,'el'),(39,'él'),(39,'ella'),(39,'ello'),(39,'ellas'),(39,'ellos'),(39,'empleáis'),(39,'emplean'),(39,'emplear'),(39,'empleas'),(39,'empleo'),(39,'en'),(39,'encima'),(39,'entonces'),(39,'entre'),(39,'era'),(39,'eras'),(39,'eramos'),(39,'eran'),(39,'eres'),(39,'es'),(39,'esa'),(39,'ese'),(39,'eso'),(39,'esos'),(39,'esas'),(39,'esta'),(39,'estas'),(39,'estaba'),(39,'estado'),(39,'estáis'),(39,'estamos'),(39,'están'),(39,'estar'),(39,'este'),(39,'esto'),(39,'esta'),(39,'estas'),(39,'estos'),(39,'estoy'),(39,'etc'),(39,'fin'),(39,'fue'),(39,'fueron'),(39,'fui'),(39,'fuimos'),(39,'gueno'),(39,'ha'),(39,'hace'),(39,'haces'),(39,'hacéis'),(39,'hacemos'),(39,'hacen'),(39,'hacer'),(39,'hacia'),(39,'hago'),(39,'hasta'),(39,'incluso'),(39,'intenta'),(39,'intentas'),(39,'intentáis'),(39,'intentamos'),(39,'intentan'),(39,'intentar'),(39,'intento'),(39,'ir'),(39,'jamás'),(39,'junto'),(39,'juntos'),(39,'la'),(39,'lo'),(39,'los'),(39,'las'),(39,'largo'),(39,'más'),(39,'me'),(39,'menos'),(39,'mi'),(39,'mis'),(39,'mía'),(39,'mias'),(39,'mientras'),(39,'mío'),(39,'mios'),(39,'misma'),(39,'mismo'),(39,'mismas'),(39,'mismos'),(39,'modo'),(39,'mucha'),(39,'mucho'),(39,'muchos'),(39,'muchas'),(39,'muchísima'),(39,'muchísimo'),(39,'muchísimas'),(39,'muchísimos'),(39,'mucho'),(39,'muchos'),(39,'muy'),(39,'nada'),(39,'ni'),(39,'ningún'),(39,'ninguna'),(39,'ningunas'),(39,'ningunos'),(39,'no'),(39,'nos'),(39,'nosotras'),(39,'nosotros'),(39,'nuestra'),(39,'nuestras'),(39,'nuestro'),(39,'nuestros'),(39,'nunca'),(39,'os'),(39,'otra'),(39,'otro'),(39,'otros'),(39,'otras'),(39,'para'),(39,'parecer'),(39,'pero'),(39,'poca'),(39,'poco'),(39,'pocas'),(39,'pocos'),(39,'podéis'),(39,'podemos'),(39,'poder'),(39,'podría'),(39,'podrías'),(39,'podríais'),(39,'podríamos'),(39,'podrían'),(39,'por'),(39,'por qué'),(39,'porque'),(39,'primero'),(39,'puede'),(39,'pueden'),(39,'puedo'),(39,'pues'),(39,'que'),(39,'qué'),(39,'querer'),(39,'quién'),(39,'quienes'),(39,'quienesquiera'),(39,'quienquiera'),(39,'quizá'),(39,'quizás'),(39,'sabe'),(39,'sabes'),(39,'saben'),(39,'sabéis'),(39,'sabemos'),(39,'saber'),(39,'se'),(39,'según'),(39,'ser'),(39,'si'),(39,'sí'),(39,'siempre'),(39,'siendo'),(39,'sin'),(39,'sino'),(39,'so'),(39,'sobre'),(39,'sois'),(39,'solamente'),(39,'solo'),(39,'sólo'),(39,'somos'),(39,'soy'),(39,'sr'),(39,'sra'),(39,'sres'),(39,'sta'),(39,'su'),(39,'sus'),(39,'suya'),(39,'suyo'),(39,'suyas'),(39,'suyos'),(39,'tal'),(39,'tales'),(39,'también'),(39,'tampoco'),(39,'tan'),(39,'tanta'),(39,'tanto'),(39,'tantas'),(39,'tantos'),(39,'te'),(39,'tenéis'),(39,'tenemos'),(39,'tener'),(39,'tengo'),(39,'ti'),(39,'tiempo'),(39,'tiene'),(39,'tienen'),(39,'toda'),(39,'todo'),(39,'todas'),(39,'todos'),(39,'tomar'),(39,'trabaja'),(39,'trabaja'),(39,'trabajáis'),(39,'trabajamos'),(39,'trabajan'),(39,'trabajar'),(39,'trabajas'),(39,'tras'),(39,'tú'),(39,'tu'),(39,'tus'),(39,'tuya'),(39,'tuyo'),(39,'tuyas'),(39,'tuyos'),(39,'último'),(39,'ultimo'),(39,'un'),(39,'una'),(39,'uno'),(39,'unas'),(39,'uno'),(39,'una'),(39,'unos'),(39,'unas'),(39,'usa'),(39,'usas'),(39,'usáis'),(39,'usamos'),(39,'usan'),(39,'usar'),(39,'uso'),(39,'usted'),(39,'ustedes'),(39,'va'),(39,'van'),(39,'vais'),(39,'valor'),(39,'vamos'),(39,'varias'),(39,'varios'),(39,'vaya'),(39,'verdadera'),(39,'vosotras'),(39,'vosotros'),(39,'voy'),(39,'vuestra'),(39,'vuestro'),(39,'vuestras'),(39,'vuestros'),(39,'y'),(39,'ya'),(39,'yo');
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
INSERT INTO `finder` VALUES (38,0,'Coding','2019-04-02 13:00:00','Conference',10,'2019-04-15');
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
INSERT INTO `folder` VALUES (65,0,'Out box',64),(66,0,'In box',64);
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
INSERT INTO `topic` VALUES (59,0,'INQUIRY','INVESTIGACION'),(60,0,'REBUTTAL','REFUTACION'),(61,0,'REGISTRATION','REGISTRO'),(62,0,'TOPICS','ASUNTOS'),(63,0,'OTHER','OTRO');
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
INSERT INTO `user_account` VALUES (37,0,'e00cf25ad42683b3df678c61f42c6bda','admin1');
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
INSERT INTO `user_account_authorities` VALUES (37,'ADMIN');
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

-- Dump completed on 2019-09-04 21:17:17
commit;