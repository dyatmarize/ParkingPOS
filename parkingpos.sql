-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: parkingpos
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaksi` (
  `id_transaksi` int NOT NULL AUTO_INCREMENT,
  `plat_nomor` varchar(45) NOT NULL,
  `jenis_kendaraan` varchar(45) NOT NULL,
  `waktu_checkin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `waktu_checkout` timestamp NULL DEFAULT NULL,
  `metode_pembayaran` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaksi`
--

LOCK TABLES `transaksi` WRITE;
/*!40000 ALTER TABLE `transaksi` DISABLE KEYS */;
INSERT INTO `transaksi` VALUES (1,'B 1747 ZHF','Motor','2022-01-24 23:59:00','2022-01-25 01:08:23','Cashless'),(2,'B 1111 ZZZ','Motor','2022-01-25 01:20:43',NULL,NULL),(3,'B 6666 ZZZ','Mobil','2022-01-25 01:31:11','2022-01-25 01:31:28','Cash'),(4,'B 6 OP','Mobil','2022-01-25 01:34:55',NULL,NULL),(5,'B 4 RU','Motor','2022-01-25 01:38:52','2022-01-25 02:09:54','Cash'),(6,'B 1234 QWE','Mobil','2022-01-25 01:42:43',NULL,NULL),(7,'B 4545 SSS','Mobil','2022-01-25 02:53:52','2022-01-25 02:54:03','Cash'),(8,'B 5555 SSS','Mobil','2022-01-25 02:56:16','2022-01-25 02:56:23','Cash'),(9,'B 0000 BOS','Mobil','2022-01-25 03:42:09','2022-01-25 03:42:34','Cash'),(10,'B 4 LWI','Motor','2022-01-25 03:57:27','2022-01-25 03:57:35','Cash');
/*!40000 ALTER TABLE `transaksi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'parkingpos'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-25 11:09:32
