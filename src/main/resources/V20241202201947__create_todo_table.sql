
CREATE TABLE `todos` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `text` varchar(255) NOT NULL,
                         `done` tinyint(1) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;