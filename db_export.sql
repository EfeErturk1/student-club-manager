-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advisor`
--

DROP TABLE IF EXISTS `advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advisor` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `club_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpjfxoaub59ruyr5gaq4mt591n` (`club_id`),
  CONSTRAINT `FKpjfxoaub59ruyr5gaq4mt591n` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisor`
--

LOCK TABLES `advisor` WRITE;
/*!40000 ALTER TABLE `advisor` DISABLE KEYS */;
INSERT INTO `advisor` VALUES (2,'advisor1',1),(3,'advisor2',2),(4,'advisor3',3);
/*!40000 ALTER TABLE `advisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignees`
--

DROP TABLE IF EXISTS `assignees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignees` (
  `assignment_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`assignment_id`,`student_id`),
  KEY `FKqrj6paq3fxqp14klesjqg2444` (`student_id`),
  CONSTRAINT `FK351cqjr8dcdbv0e425ubjulq9` FOREIGN KEY (`assignment_id`) REFERENCES `assignment` (`assignment_id`),
  CONSTRAINT `FKqrj6paq3fxqp14klesjqg2444` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignees`
--

LOCK TABLES `assignees` WRITE;
/*!40000 ALTER TABLE `assignees` DISABLE KEYS */;
INSERT INTO `assignees` VALUES (1,5),(2,5),(3,5),(1,6),(2,6),(3,6),(1,7),(2,7);
/*!40000 ALTER TABLE `assignees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignment`
--

DROP TABLE IF EXISTS `assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignment` (
  `assignment_id` int NOT NULL AUTO_INCREMENT,
  `assignment_file` varchar(255) DEFAULT NULL,
  `club_id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `submission_des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`assignment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment`
--

LOCK TABLES `assignment` WRITE;
/*!40000 ALTER TABLE `assignment` DISABLE KEYS */;
INSERT INTO `assignment` VALUES (1,'sample pdf.pdf',1,'upload submission1 here','2021-12-27 13:30:00','submission1',_binary '\0',NULL),(2,NULL,1,'upload submission 2 here.','2022-01-13 13:30:00','submission 2',_binary '\0',NULL),(3,NULL,2,'play chess all night','2021-12-25 23:30:00','play chess',_binary '\0',NULL);
/*!40000 ALTER TABLE `assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club`
--

DROP TABLE IF EXISTS `club`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photos` varchar(64) DEFAULT NULL,
  `advisor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4vwpy5x9kwexguip9rcruvd0y` (`advisor_id`),
  CONSTRAINT `FK4vwpy5x9kwexguip9rcruvd0y` FOREIGN KEY (`advisor_id`) REFERENCES `advisor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club`
--

LOCK TABLES `club` WRITE;
/*!40000 ALTER TABLE `club` DISABLE KEYS */;
INSERT INTO `club` VALUES (1,'Just a random club at bilkent!','Club1','Fight-Club-Logo.png',2),(2,'Just another random club at bilkent!','Club2','800px_COLOURBOX38486261.jpg',3),(3,'Description field is to be edited later on by the club!','Club3','',4);
/*!40000 ALTER TABLE `club` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club_role`
--

DROP TABLE IF EXISTS `club_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `club_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK56n98yb289xt6p76ho7eg816w` (`club_id`),
  KEY `FK8m8im9a3imm1ibncnet4apy7y` (`student_id`),
  CONSTRAINT `FK56n98yb289xt6p76ho7eg816w` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`),
  CONSTRAINT `FK8m8im9a3imm1ibncnet4apy7y` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club_role`
--

LOCK TABLES `club_role` WRITE;
/*!40000 ALTER TABLE `club_role` DISABLE KEYS */;
INSERT INTO `club_role` VALUES (1,'PRESIDENT',1,5),(2,'MEMBER',2,5),(3,'BOARD_MEMBER',2,6),(4,'MEMBER',1,6),(5,'MEMBER',3,6),(6,'ACTIVE_MEMBER',3,7),(7,'MEMBER',1,7);
/*!40000 ALTER TABLE `club_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document` (
  `document_id` int NOT NULL AUTO_INCREMENT,
  `document_name` varchar(64) DEFAULT NULL,
  `submission_date` datetime DEFAULT NULL,
  `student_id` int NOT NULL,
  `for_assignment` int DEFAULT NULL,
  PRIMARY KEY (`document_id`),
  KEY `FK84f9wnmr26kfd9y9e711f01sq` (`student_id`),
  KEY `FKtej228chqrxxenipm7x1boot` (`for_assignment`),
  CONSTRAINT `FK84f9wnmr26kfd9y9e711f01sq` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKtej228chqrxxenipm7x1boot` FOREIGN KEY (`for_assignment`) REFERENCES `assignment` (`assignment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `club_id` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `event_date` varchar(255) DEFAULT NULL,
  `event_finish` varchar(255) DEFAULT NULL,
  `ge250` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photos` varchar(64) DEFAULT NULL,
  `quota` int NOT NULL,
  `remaining_quota` int NOT NULL,
  `status` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  CONSTRAINT `event_chk_1` CHECK ((`status` in (_utf8mb4'REJECTED',_utf8mb4'ACCEPTED',_utf8mb4'NOT_DECIDED')))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,1,'meet and greet','2021-12-26T10:30:00','2021-12-26T11:30:00',0,'event1','',100,100,'REJECTED'),(2,1,'general meeting 1. Please be there on time.','2021-12-29T10:30:00','2021-12-29T11:30:00',10,'general meeting 1','',150,150,'REJECTED'),(3,1,'general meeting 2 for this club.dont come late!','2021-12-31T10:30:00','2021-12-31T11:30:00',10,'general meeting 2','',50,50,'REJECTED'),(4,1,'meet and greet 2','2022-01-13T10:30:00','2022-01-13T11:30:00',0,'meet and greet 2','',45,45,'REJECTED'),(5,1,'general meeting 3 this time','2022-01-07T10:30:00','2022-01-07T11:30:00',10,'general meeting 3','agm_topimage.jpg',100,100,'ACCEPTED'),(6,1,'third iteration of meet and greet because we love it','2022-01-10T15:30:00','2022-01-10T18:30:00',0,'meet and greet','Group-of-smiling-Designers-Relaxing1366.jpg',150,148,'ACCEPTED'),(7,1,'football tournament for fun! have a 5 a side team. Winners get prizes too!','2022-02-17T10:30:00','2022-02-17T11:30:00',25,'football tournament','maxresdefault.jpg',160,160,'NOT_DECIDED'),(8,2,'chess tournament with prize','2021-12-26T10:30:00','2021-12-26T11:30:00',10,'chess tournament','17832.ef7f22ca.668x375o.7c3c4a5ed823@2x.jpeg',200,199,'ACCEPTED'),(9,2,'chess finals are a triller! have your seat now!','2022-01-24T10:30:00','2022-01-24T11:30:00',15,'chess finals','1016767.f036f2b7.668x375o.95ac52dd2725@2x.png',100,100,'NOT_DECIDED');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_participants`
--

DROP TABLE IF EXISTS `event_participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_participants` (
  `event_event_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`event_event_id`,`student_id`),
  KEY `FKl6btxiqyvvyglmntcpjxaemr` (`student_id`),
  CONSTRAINT `FKl6btxiqyvvyglmntcpjxaemr` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `FKo3jsxbunj1tep45v3rcaa62n8` FOREIGN KEY (`event_event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_participants`
--

LOCK TABLES `event_participants` WRITE;
/*!40000 ALTER TABLE `event_participants` DISABLE KEYS */;
INSERT INTO `event_participants` VALUES (6,5),(6,6),(8,6);
/*!40000 ALTER TABLE `event_participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `club_id` int NOT NULL,
  `date` varchar(64) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_request` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,1,NULL,'Club1 is now opened!. Description field is to be edited later on by the club!',_binary '\0',NULL),(2,2,NULL,'Club2 is now opened!. Description field is to be edited later on by the club!',_binary '\0',NULL),(3,3,NULL,'Club3 is now opened!. Description field is to be edited later on by the club!',_binary '\0',NULL),(4,1,NULL,'New member with name user1 has joined the club!',_binary '',NULL),(5,2,NULL,'New member with name user1 has joined the club!',_binary '',NULL),(6,1,'2021-12-26T10:30:00','There is a new Event with name event1',_binary '\0',NULL),(7,1,'2021-12-29T10:30:00','There is a new Event with name general meeting 1',_binary '\0',NULL),(8,1,NULL,'Assignment with name submission1 is assigned!',_binary '\0',NULL),(9,1,'2021-12-31T10:30:00','There is a new Event with name general meeting 2',_binary '\0',NULL),(10,1,'2022-01-13T10:30:00','There is a new Event with name meet and greet 2',_binary '\0',NULL),(11,2,NULL,'New member with name user2 has joined the club!',_binary '',NULL),(12,1,NULL,'New member with name user2 has joined the club!',_binary '',NULL),(13,3,NULL,'New member with name user2 has joined the club!',_binary '',NULL),(14,1,NULL,'Assignment with name submission 2 is assigned!',_binary '\0',NULL),(15,1,'2022-01-07T10:30:00','There is a new Event with name general meeting 3',_binary '\0','Club1'),(16,1,'2022-01-10T15:30:00','There is a new Event with name meet and greet',_binary '\0','Club1'),(17,1,NULL,'Event with name event1 is rejected!',_binary '\0','event1'),(18,1,NULL,'Event with name general meeting 1 is rejected!',_binary '\0','general meeting 1'),(19,1,NULL,'Event with name general meeting 2 is rejected!',_binary '\0','general meeting 2'),(20,1,NULL,'Event with name meet and greet 2 is rejected!',_binary '\0','meet and greet 2'),(21,1,NULL,'Event with name meet and greet is accepted!',_binary '\0','meet and greet'),(22,1,NULL,'Event with name general meeting 3 is accepted!',_binary '\0','general meeting 3'),(23,1,'2022-02-17T10:30:00','There is a new Event with name football tournament',_binary '\0','Club1'),(24,3,NULL,'New member with name user3 has joined the club!',_binary '','user3'),(25,1,NULL,'New member with name user3 has joined the club!',_binary '','user3'),(26,2,'2021-12-26T10:30:00','There is a new Event with name chess tournament',_binary '\0','Club2'),(27,2,NULL,'Assignment with name play chess is assigned!',_binary '\0','Club2'),(28,2,'2022-01-24T10:30:00','There is a new Event with name chess finals',_binary '\0','Club2'),(29,2,NULL,'Event with name chess tournament is accepted!',_binary '\0','chess tournament');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notified_clubs`
--

DROP TABLE IF EXISTS `notified_clubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notified_clubs` (
  `notification_id` int NOT NULL,
  `club_id` int NOT NULL,
  PRIMARY KEY (`notification_id`,`club_id`),
  KEY `FKdimrw7gymiimpij6lp0b3jn2p` (`club_id`),
  CONSTRAINT `FKdimrw7gymiimpij6lp0b3jn2p` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`),
  CONSTRAINT `FKn3ddhxvwhtp57rum93e2v9gfb` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notified_clubs`
--

LOCK TABLES `notified_clubs` WRITE;
/*!40000 ALTER TABLE `notified_clubs` DISABLE KEYS */;
INSERT INTO `notified_clubs` VALUES (4,1),(12,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(25,1),(5,2),(11,2),(29,2),(13,3),(24,3);
/*!40000 ALTER TABLE `notified_clubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notified_people`
--

DROP TABLE IF EXISTS `notified_people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notified_people` (
  `notification_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`notification_id`,`student_id`),
  KEY `FKcycuqjlbr9ybdfvpgmla37hi8` (`student_id`),
  CONSTRAINT `FK6bgrha46yffmkdabjflkghl4t` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`notification_id`),
  CONSTRAINT `FKcycuqjlbr9ybdfvpgmla37hi8` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notified_people`
--

LOCK TABLES `notified_people` WRITE;
/*!40000 ALTER TABLE `notified_people` DISABLE KEYS */;
INSERT INTO `notified_people` VALUES (6,5),(7,5),(8,5),(9,5),(10,5),(14,5),(15,5),(16,5),(23,5),(26,5),(27,5),(28,5),(14,6),(15,6),(16,6),(23,6),(26,6),(27,6),(28,6);
/*!40000 ALTER TABLE `notified_people` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'ROLE_ADMIN'),(1,'ROLE_ADVISOR'),(3,'ROLE_STUDENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL,
  `ge250` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `profile_photo_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (5,0,'user1','8198-1631656078.jpg'),(6,10,'user2','28003-1631171950.jpg'),(7,0,'user3','Kylian_Mbappe_PSG_Man_City_Champions_League_2021-22.jpeg');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `ge250` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@gmail.com',0,'admin','$2a$10$u1m7uXKUOuhK44DSzqhKhuUMQVCw2cnto8tyPOkk6LQqZ0lYJmHky',NULL,2),(2,'advisor1@gmail.com',0,'advisor1','$2a$10$HI9aBhZXXfmdNulLjfgnN.XsFJMRiHPwwZVOFoUbMLNan.s18YgZe',NULL,1),(3,'advisor2@gmail.com',0,'advisor2','$2a$10$hoTakg6SYMcT.9R8JFxlR.PnLagAGk0EP4p93X2sn.wzIFTEklg0O',NULL,1),(4,'advisor3@gmail.com',0,'advisor3','$2a$10$FAjrsK81YRPqQaoTy9/Xeebj1fNG4VG.cl9gqTJu6jWBcbIZxgYni',NULL,1),(5,'user1@ug.bilkent.edu.tr',0,'user1','$2a$10$jqZdM35jTpwg/u6M2YLufeJIIm0eRnVp/J0NcL7BUIxyoAwNF2xBq',NULL,3),(6,'user2@ug.bilkent.edu.tr',0,'user2','$2a$10$0V8AReehsrc8/mu7PBXOJuWdPi3GV56Bnm8TWJEtSCBrPLa1IIVpK',NULL,3),(7,'user3@ug.bilkent.edu.tr',0,'user3','$2a$10$fgNAm2tjQK2qd7hZOBWyk.LX.r0adW0tCoP5SCSJ9/UWIRngOz.g.',NULL,3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-26  5:40:03
