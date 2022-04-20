DROP SCHEMA IF EXISTS `onlinecourse`;

CREATE SCHEMA `onlinecourse`;

USE `onlinecourse`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `instructor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`),
  KEY `FK_USER_idx` (`instructor_id`),
  CONSTRAINT `FK_USER` FOREIGN KEY (`instructor_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKc0xls9e7uqc9o08ae0t2ywr6n` FOREIGN KEY (`instructor_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `course_student`;

CREATE TABLE `course_student` (
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  KEY `FKlmj50qx9k98b7li5li74nnylb` (`course_id`),
  KEY `FK2sxaxgn2f7wv33s5a91wm0uuk` (`student_id`),
  CONSTRAINT `FK2sxaxgn2f7wv33s5a91wm0uuk` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKlmj50qx9k98b7li5li74nnylb` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `place`;

CREATE TABLE `place` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `place` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `user_image_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_IMAGE_idx` (`user_image_id`),
  CONSTRAINT `FK8f10cke7w02e8wkt0mafbqj83` FOREIGN KEY (`user_image_id`) REFERENCES `user_image` (`id`),
  CONSTRAINT `FK_USER_IMAGE` FOREIGN KEY (`user_image_id`) REFERENCES `user_image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `user_image`;

CREATE TABLE `user_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file` mediumblob NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `filetype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `roles_id` int NOT NULL,
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKj9553ass9uctjrmh0gkqsmv0d` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
