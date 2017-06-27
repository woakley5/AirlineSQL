# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.7.18)
# Database: airline
# Generation Time: 2017-06-27 01:03:31 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table airplane
# ------------------------------------------------------------

DROP TABLE IF EXISTS `airplane`;

CREATE TABLE `airplane` (
  `model` varchar(45) DEFAULT NULL,
  `manufacturer` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `idairplane` int(11) NOT NULL,
  PRIMARY KEY (`idairplane`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `airplane` WRITE;
/*!40000 ALTER TABLE `airplane` DISABLE KEYS */;

INSERT INTO `airplane` (`model`, `manufacturer`, `age`, `idairplane`)
VALUES
	('737','Boeing','5',7),
	('737','Boeing','5',31),
	('737','Boeing','5',33),
	('737','Boeing','5',47),
	('737','Boeing','5',88),
	('737','Boeing','36',27748),
	('737','Boeing','27',72747);

/*!40000 ALTER TABLE `airplane` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table flight
# ------------------------------------------------------------

DROP TABLE IF EXISTS `flight`;

CREATE TABLE `flight` (
  `carrier` varchar(45) DEFAULT NULL,
  `fltNum` int(4) unsigned zerofill DEFAULT NULL,
  `origin` varchar(45) DEFAULT NULL,
  `destin` varchar(45) DEFAULT NULL,
  `departs` varchar(45) DEFAULT NULL,
  `arrival` varchar(45) DEFAULT NULL,
  `idflight` int(11) unsigned zerofill NOT NULL,
  PRIMARY KEY (`idflight`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;

INSERT INTO `flight` (`carrier`, `fltNum`, `origin`, `destin`, `departs`, `arrival`, `idflight`)
VALUES
	('JL',0119,'SFO','HND','0012','2254',00000012119),
	('AF',0001,'LAX','CDG','0400','1500',00000400001),
	('UA',0245,'LAX','CDG','0900','2000',00000900245),
	('VA',0223,'LAX','SEA','1000','1234',00001000223),
	('AL',0223,'LAX','SJO','1123','1642',00001123223),
	('AA',0992,'LAX','OGG','1300','1435',00001300992),
	('AA',0123,'LAX','SFO','6000','8150',00006000123),
	('UA',5727,'SBA','LAX','0855','0937',00008555727),
	('UA',2242,'SBA','SFO','1000','1124',00010002242);

/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table reservations
# ------------------------------------------------------------

DROP TABLE IF EXISTS `reservations`;

CREATE TABLE `reservations` (
  `idflight` int(11) unsigned zerofill DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `DOB` varchar(45) DEFAULT NULL,
  `PNR` varchar(5) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;

INSERT INTO `reservations` (`idflight`, `name`, `DOB`, `PNR`)
VALUES
	(00000900245,'William Oakley','04/30/1999','07B08'),
	(00000900245,'William Oakley','04/30/1999','CB981'),
	(00006000123,'Nick Oakley','05/03/2002','2C627'),
	(00000400001,'Nick Oakley','05/03/2002','256F4'),
	(00000012119,'Bill Oakley','10/07/1966','C02B9');

/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
