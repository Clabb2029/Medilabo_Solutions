USE Medilabo_Solutions;

CREATE TABLE `doctor` (
    `id` int NOT NULL AUTO_INCREMENT,
    `lastname` varchar(40) NOT NULL,
    `firstname` varchar(40) NOT NULL,
    `username` varchar(254) NOT NULL,
    `password` varchar(254) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `doctor` VALUES
    (1,'Collombat','Patrick','patrick.collombat','$2a$10$v3V893iNpKqICQS.kp5aaejIAMNIZcI4O6YbnfoD6kCFBSzIWOLv2');

CREATE TABLE `patient` (
    `id` int NOT NULL AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,
    `birthdate` date NOT NULL,
    `firstname` varchar(100) NOT NULL,
    `gender` varchar(255) NOT NULL,
    `lastname` varchar(100) NOT NULL,
    `phone_number` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `patient` VALUES
    (1,'1 Brookside St','1966-12-31','Test','F','TestNone','100-222-3333'),
    (2,'2 High St','1945-06-24','Test','M','TestBorderline','200-333-4444'),
    (3,'3 Club Road','2004-06-18','Test','M','TestInDanger','300-444-5555'),
    (4,'4 Valley Dr','2002-06-28','Test','F','TestEarlyOnset','400-555-6666');
