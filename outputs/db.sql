DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `author`      varchar(20) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(20) DEFAULT NULL,
    UNIQUE INDEX `UK_name` (`name`) ,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `book_rent`;
CREATE TABLE `book_rent`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `book_id`     bigint       DEFAULT NULL,
    `member_id`   varchar(20) DEFAULT NULL,
    `rental_date` datetime(6) DEFAULT NULL,
    `return_date` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `member_id`    varchar(20) NOT NULL,
    `password`     varchar(20) NOT NULL,
    `name`         varchar(20) NOT NULL,
    `age`          int          NOT NULL,
    `phone_number` int          NOT NULL,
    `email`        varchar(255) NOT NULL,
    UNIQUE INDEX `UK_member_id` (`member_id`) ,
    PRIMARY KEY (`id`)
);