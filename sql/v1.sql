DROP TABLE `OrderProcess`;


DROP TABLE `LogsProcess`;


DROP TABLE `DirectionProcess`;


DROP TABLE `UserDatabase`;


DROP TABLE `Process`;


DROP TABLE `User`;


DROP TABLE `TypeDatabase`;


DROP TABLE `KeyType`;


-- ************************************** `User`

CREATE TABLE `User`
(
 `id`         integer NOT NULL AUTO_INCREMENT ,
 `login`      varchar(255) NOT NULL ,
 `password`   varchar(255) NOT NULL ,
 `dt_insert`  timestamp NOT NULL ,
 `dt_update`  timestamp ,
 `dt_deleted` timestamp ,
PRIMARY KEY (`id`)
);



-- ************************************** `TypeDatabase`

CREATE TABLE `TypeDatabase`
(
 `name`       varchar(50) NOT NULL ,
 `dt_insert`  timestamp NOT NULL ,
 `dt_update`  timestamp ,
 `dt_deleted` timestamp ,
PRIMARY KEY (`name`)
);



-- ************************************** `KeyType`

CREATE TABLE `KeyType`
(
 `name`       varchar(255) NOT NULL ,
 `dt_insert`  timestamp NOT NULL ,
 `dt_update`  timestamp ,
 `dt_deleted` timestamp ,
PRIMARY KEY (`name`)
);



-- ************************************** `UserDatabase`

CREATE TABLE `UserDatabase`
(
 `id`         integer NOT NULL AUTO_INCREMENT ,
 `id_user`    integer NOT NULL ,
 `name`       varchar(50) NOT NULL ,
 `ip`         varchar(255) NOT NULL ,
 `port`       integer NOT NULL ,
 `url`        varchar(255) NOT NULL ,
 `db_type`    varchar(50) NOT NULL ,
 `dt_insert`  timestamp NOT NULL ,
 `dt_update`  timestamp ,
 `dt_deleted` timestamp ,
PRIMARY KEY (`id`),
KEY `fkIdx_23` (`id_user`),
CONSTRAINT `FK_23` FOREIGN KEY `fkIdx_23` (`id_user`) REFERENCES `User` (`id`),
KEY `fkIdx_31` (`db_type`),
CONSTRAINT `FK_31` FOREIGN KEY `fkIdx_31` (`db_type`) REFERENCES `TypeDatabase` (`name`)
);



-- ************************************** `Process`

CREATE TABLE `Process`
(
 `id`          integer NOT NULL AUTO_INCREMENT ,
 `id_user`     integer NOT NULL ,
 `name`        varchar(100) NOT NULL ,
 `description` varchar(100) NOT NULL ,
 `error`       integer NOT NULL ,
 `dt_insert`   timestamp NOT NULL ,
 `dt_update`   timestamp ,
 `dt_deleted`  timestamp ,
PRIMARY KEY (`id`),
KEY `fkIdx_38` (`id_user`),
CONSTRAINT `FK_38` FOREIGN KEY `fkIdx_38` (`id_user`) REFERENCES `User` (`id`)
);



-- ************************************** `OrderProcess`

CREATE TABLE `OrderProcess`
(
 `id`           integer NOT NULL AUTO_INCREMENT ,
 `id_process`   integer NOT NULL ,
 `table_origim` varchar(255) NOT NULL ,
 `table_destin` varchar(255) NOT NULL ,
 `order`        integer NOT NULL ,
 `error`        integer NOT NULL ,
 `key_type`     varchar(255) NOT NULL ,
 `max_lines`    integer NOT NULL ,
 `dt_insert`    timestamp NOT NULL ,
 `dt_update`    timestamp ,
 `dt_deleted`   timestamp ,
PRIMARY KEY (`id`),
KEY `fkIdx_50` (`id_process`),
CONSTRAINT `FK_50` FOREIGN KEY `fkIdx_50` (`id_process`) REFERENCES `Process` (`id`),
KEY `fkIdx_60` (`key_type`),
CONSTRAINT `FK_60` FOREIGN KEY `fkIdx_60` (`key_type`) REFERENCES `KeyType` (`name`)
);






-- ************************************** `LogsProcess`

CREATE TABLE `LogsProcess`
(
 `id`         integer NOT NULL ,
 `id_process` integer NOT NULL ,
 `message`    varchar(200) NOT NULL ,
 `dt_insert`  timestamp NOT NULL ,
 `dt_update`  timestamp ,
 `dt_deleted` timestamp ,
PRIMARY KEY (`id`),
KEY `fkIdx_111` (`id_process`),
CONSTRAINT `FK_111` FOREIGN KEY `fkIdx_111` (`id_process`) REFERENCES `Process` (`id`)
);






-- ************************************** `DirectionProcess`

CREATE TABLE `DirectionProcess`
(
 `id`              integer NOT NULL AUTO_INCREMENT ,
 `id_process`      integer NOT NULL ,
 `id_origin`       integer NOT NULL ,
 `id_destin`       integer NOT NULL ,
 `operation`       integer NOT NULL ,
 `user_origin`     varchar(255) NOT NULL ,
 `password_origin` varchar(255) NOT NULL ,
 `user_destin`     varchar(255) NOT NULL ,
 `password_destin` varchar(255) NOT NULL ,
 `able`            integer NOT NULL ,
 `retention`       integer NOT NULL ,
 `duration`        integer NOT NULL ,
 `dt_start`        timestamp ,
 `dt_finish`       timestamp ,
 `dt_insert`       timestamp NOT NULL ,
 `dt_update`       timestamp ,
 `dt_deleted`      timestamp ,
PRIMARY KEY (`id`),
KEY `fkIdx_70` (`id_process`),
CONSTRAINT `FK_70` FOREIGN KEY `fkIdx_70` (`id_process`) REFERENCES `Process` (`id`),
KEY `fkIdx_95` (`id_origin`),
CONSTRAINT `FK_95` FOREIGN KEY `fkIdx_95` (`id_origin`) REFERENCES `UserDatabase` (`id`),
KEY `fkIdx_98` (`id_destin`),
CONSTRAINT `FK_98` FOREIGN KEY `fkIdx_98` (`id_destin`) REFERENCES `UserDatabase` (`id`)
);


ALTER TABLE `DirectionProcess` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `KeyType` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `LogsProcess` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `OrderProcess` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `Process` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `TypeDatabase` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `User` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `UserDatabase` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;

ALTER TABLE `DirectionProcess` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `KeyType` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `LogsProcess` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `OrderProcess` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `Process` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `TypeDatabase` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `User` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `UserDatabase` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE `OrderProcess` ADD `key_name` VARCHAR(255) NOT NULL AFTER `error`;
ALTER TABLE `OrderProcess` CHANGE `table_origim` `table_origin` VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL;
ALTER TABLE `UserDatabase` DROP `url`;

ALTER TABLE `OrderProcess` CHANGE `order` `order_process` INT(11) NOT NULL;
ALTER TABLE OrderProcess DROP FOREIGN KEY FK_60;
ALTER TABLE `OrderProcess` DROP `key_type`;
DROP TABLE KeyType

ALTER TABLE `DirectionProcess` DROP `retention`;
ALTER TABLE `DirectionProcess` DROP `duration`;
