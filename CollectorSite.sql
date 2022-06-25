CREATE DATABASE  IF NOT EXISTS `collector_site` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `collector_site`;
-- MySQL dump 10.13  Distrib 8.0.25, for Linux (x86_64)
--
-- Host: localhost    Database: collector_site
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

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
-- Table structure for table `autore`
--

DROP TABLE IF EXISTS `autore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autore` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `nome_artistico` varchar(45) DEFAULT NULL,
  `tipologia_autore` varchar(45) DEFAULT NULL,
  `version` int unsigned DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collezione`
--

DROP TABLE IF EXISTS `collezione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collezione` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `titolo` varchar(45) DEFAULT NULL,
  `privacy` varchar(45) DEFAULT NULL,
  `data_creazione` date DEFAULT NULL,
  `version` int unsigned DEFAULT '1',
  `utente_id` int unsigned NOT NULL,
  UNIQUE (`utente_id`,`titolo`),
  PRIMARY KEY (`id`),
  KEY `fk_collezione_utente1_idx` (`utente_id`),
  CONSTRAINT `fk_collezione_utente1` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collezione_condivisa_con`
--

DROP TABLE IF EXISTS `collezione_condivisa_con`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collezione_condivisa_con` (
  `collezione_id` int unsigned NOT NULL,
  `utente_id` int unsigned NOT NULL,
  PRIMARY KEY (`collezione_id`,`utente_id`),
  KEY `fk_collezione_has_utente_utente1_idx` (`utente_id`),
  KEY `fk_collezione_has_utente_collezione1_idx` (`collezione_id`),
  CONSTRAINT `fk_collezione_has_utente_collezione1` FOREIGN KEY (`collezione_id`) REFERENCES `collezione` (`id`),
  CONSTRAINT `fk_collezione_has_utente_utente1` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `collezione_disco`
--

DROP TABLE IF EXISTS `collezione_disco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collezione_disco` (
  `collezione_id` int unsigned NOT NULL,
  `disco_id` int unsigned NOT NULL,
  PRIMARY KEY (`collezione_id`,`disco_id`),
  KEY `fk_collezione_has_disco_disco1_idx` (`disco_id`),
  KEY `fk_collezione_has_disco_collezione1_idx` (`collezione_id`),
  CONSTRAINT `fk_collezione_has_disco_collezione1` FOREIGN KEY (`collezione_id`) REFERENCES `collezione` (`id`),
  CONSTRAINT `fk_collezione_has_disco_disco1` FOREIGN KEY (`disco_id`) REFERENCES `disco` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disco`
--

DROP TABLE IF EXISTS `disco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disco` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `titolo` varchar(45) DEFAULT NULL,
  `anno` varchar(4) DEFAULT NULL,
  `barcode` varchar(45) DEFAULT NULL,
  `etichetta` varchar(45) DEFAULT NULL,
  `genere` varchar(45) DEFAULT NULL,
  `formato` varchar(45) DEFAULT NULL,
  `stato_conservazione` varchar(45) DEFAULT NULL,
  `data_inserimento` date DEFAULT NULL,
  `version` int unsigned DEFAULT '1',
  `utente_id` int unsigned NOT NULL,
  `padre` int unsigned,
  PRIMARY KEY (`id`),
  KEY `fk_disco_utente1_idx` (`utente_id`),
  KEY `fk_disco_disco1_idx` (`padre`),
  CONSTRAINT `fk_disco_disco1` FOREIGN KEY (`padre`) REFERENCES `disco` (`id`),
  CONSTRAINT `fk_disco_utente1` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disco_autore`
--

DROP TABLE IF EXISTS `disco_autore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disco_autore` (
  `disco_id` int unsigned NOT NULL,
  `autore_id` int unsigned NOT NULL,
  PRIMARY KEY (`disco_id`,`autore_id`),
  KEY `fk_disco_has_autore_autore1_idx` (`autore_id`),
  KEY `fk_disco_has_autore_disco_idx` (`disco_id`),
  CONSTRAINT `fk_disco_has_autore_autore1` FOREIGN KEY (`autore_id`) REFERENCES `autore` (`id`),
  CONSTRAINT `fk_disco_has_autore_disco` FOREIGN KEY (`disco_id`) REFERENCES `disco` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disco_traccia`
--

DROP TABLE IF EXISTS `disco_traccia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disco_traccia` (
  `disco_id` int unsigned NOT NULL,
  `traccia_id` int unsigned NOT NULL,
  PRIMARY KEY (`disco_id`,`traccia_id`),
  KEY `fk_disco_has_traccia_traccia1_idx` (`traccia_id`),
  KEY `fk_disco_has_traccia_disco1_idx` (`disco_id`),
  CONSTRAINT `fk_disco_has_traccia_disco1` FOREIGN KEY (`disco_id`) REFERENCES `disco` (`id`),
  CONSTRAINT `fk_disco_has_traccia_traccia1` FOREIGN KEY (`traccia_id`) REFERENCES `traccia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `size` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `filename` varchar(45) DEFAULT NULL,
  `version` int unsigned DEFAULT '1',
  `disco_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_image_disco1_idx` (`disco_id`),
  CONSTRAINT `fk_image_disco1` FOREIGN KEY (`disco_id`) REFERENCES `disco` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `traccia`
--

DROP TABLE IF EXISTS `traccia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `traccia` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `titolo` varchar(45) DEFAULT NULL,
  `durata` int DEFAULT NULL,
  `iswc` varchar(11) DEFAULT NULL,
  `data_inserimento` date DEFAULT NULL,
  `version` int unsigned DEFAULT '1',
  `padre` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_traccia_traccia1_idx` (`padre`),
  CONSTRAINT `fk_traccia_traccia1` FOREIGN KEY (`padre`) REFERENCES `traccia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `traccia_autore`
--

DROP TABLE IF EXISTS `traccia_autore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `traccia_autore` (
  `traccia_id` int unsigned NOT NULL,
  `autore_id` int unsigned NOT NULL,
  PRIMARY KEY (`traccia_id`,`autore_id`),
  KEY `fk_traccia_has_autore_autore1_idx` (`autore_id`),
  KEY `fk_traccia_has_autore_traccia1_idx` (`traccia_id`),
  CONSTRAINT `fk_traccia_has_autore_autore1` FOREIGN KEY (`autore_id`) REFERENCES `autore` (`id`),
  CONSTRAINT `fk_traccia_has_autore_traccia1` FOREIGN KEY (`traccia_id`) REFERENCES `traccia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL UNIQUE,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `version` int unsigned DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-01 15:57:31