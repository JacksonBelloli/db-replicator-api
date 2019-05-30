-- ****************** SqlDBM: MySQL ******************;
-- ***************************************************;

DROP TABLE `LogsProcess`;


DROP TABLE `Execution`;


DROP TABLE `OrderProcess`;


DROP TABLE `DirectionProcess`;


DROP TABLE `UserDatabase`;


DROP TABLE `Process`;


DROP TABLE `User`;


DROP TABLE `TypeDatabase`;


DROP TABLE `LogsType`;



-- ************************************** `User`

CREATE TABLE `User`
(
 `id`         integer NOT NULL AUTO_INCREMENT ,
 `login`      varchar(255) NOT NULL ,
 `password`   varchar(255) NOT NULL ,
 `dt_insert`  datetime NOT NULL ,
 `dt_update`  datetime ,
 `dt_deleted` datetime ,

PRIMARY KEY (`id`)
);






-- ************************************** `TypeDatabase`

CREATE TABLE `TypeDatabase`
(
 `name`       varchar(50) NOT NULL ,
 `dt_insert`  datetime NOT NULL ,
 `dt_update`  datetime ,
 `dt_deleted` datetime ,

PRIMARY KEY (`name`)
);






-- ************************************** `LogsType`

CREATE TABLE `LogsType`
(
 `id`        integer NOT NULL AUTO_INCREMENT ,
 `type`      varchar(50) NOT NULL ,
 `dt_insert` datetime NOT NULL ,
 `dt_update` datetime ,
 `dt_delete` datetime ,

PRIMARY KEY (`id`)
);






-- ************************************** `UserDatabase`

CREATE TABLE `UserDatabase`
(
 `id`          integer NOT NULL AUTO_INCREMENT ,
 `description` varchar(200) NOT NULL ,
 `name`        varchar(50) NOT NULL ,
 `ip`          varchar(255) NOT NULL ,
 `port`        integer NOT NULL ,
 `db_type`     varchar(50) NOT NULL ,
 `dt_insert`   datetime NOT NULL ,
 `dt_update`   datetime ,
 `dt_deleted`  datetime ,
 `id_user`     integer NOT NULL ,

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
 `name`        varchar(100) NOT NULL ,
 `description` varchar(400) NOT NULL ,
 `error`       integer NOT NULL ,
 `dt_insert`   datetime NOT NULL ,
 `dt_update`   datetime ,
 `dt_deleted`  datetime ,
 `id_user`     integer NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_38` (`id_user`),
CONSTRAINT `FK_38` FOREIGN KEY `fkIdx_38` (`id_user`) REFERENCES `User` (`id`)
);






-- ************************************** `OrderProcess`

CREATE TABLE `OrderProcess`
(
 `id`            integer NOT NULL AUTO_INCREMENT ,
 `id_process`    integer NOT NULL ,
 `table_origin`  varchar(255) NOT NULL ,
 `table_destin`  varchar(255) NOT NULL ,
 `order_process` integer NOT NULL ,
 `error`         integer NOT NULL ,
 `key_name`      varchar(200) NOT NULL ,
 `max_lines`     integer NOT NULL ,
 `dt_insert`     datetime NOT NULL ,
 `dt_update`     datetime ,
 `dt_deleted`    datetime ,
 `id_user`       integer NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_145` (`id_user`),
CONSTRAINT `FK_145` FOREIGN KEY `fkIdx_145` (`id_user`) REFERENCES `User` (`id`),
KEY `fkIdx_50` (`id_process`),
CONSTRAINT `FK_50` FOREIGN KEY `fkIdx_50` (`id_process`) REFERENCES `Process` (`id`)
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
 `dt_start`        datetime ,
 `dt_finish`       datetime ,
 `dt_insert`       datetime NOT NULL ,
 `dt_update`       datetime ,
 `dt_deleted`      datetime ,
 `id_user`         integer NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_148` (`id_user`),
CONSTRAINT `FK_148` FOREIGN KEY `fkIdx_148` (`id_user`) REFERENCES `User` (`id`),
KEY `fkIdx_70` (`id_process`),
CONSTRAINT `FK_70` FOREIGN KEY `fkIdx_70` (`id_process`) REFERENCES `Process` (`id`),
KEY `fkIdx_95` (`id_origin`),
CONSTRAINT `FK_95` FOREIGN KEY `fkIdx_95` (`id_origin`) REFERENCES `UserDatabase` (`id`),
KEY `fkIdx_98` (`id_destin`),
CONSTRAINT `FK_98` FOREIGN KEY `fkIdx_98` (`id_destin`) REFERENCES `UserDatabase` (`id`)
);






-- ************************************** `Execution`

CREATE TABLE `Execution`
(
 `id`           integer NOT NULL AUTO_INCREMENT ,
 `id_direction` integer NOT NULL ,
 `dt_start`     datetime NOT NULL ,
 `dt_finish`    datetime NOT NULL ,
 `dt_insert`    datetime NOT NULL ,
 `dt_update`    datetime NOT NULL ,
 `dt_delete`    datetime NOT NULL ,
 `id_user`      integer NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_131` (`id_direction`),
CONSTRAINT `FK_131` FOREIGN KEY `fkIdx_131` (`id_direction`) REFERENCES `DirectionProcess` (`id`),
KEY `fkIdx_151` (`id_user`),
CONSTRAINT `FK_151` FOREIGN KEY `fkIdx_151` (`id_user`) REFERENCES `User` (`id`)
);






-- ************************************** `LogsProcess`

CREATE TABLE `LogsProcess`
(
 `id`           integer NOT NULL ,
 `id_execution` integer NOT NULL ,
 `message`      varchar(200) NOT NULL ,
 `type`         integer NOT NULL ,
 `dt_insert`    datetime NOT NULL ,
 `dt_update`    datetime ,
 `dt_deleted`   datetime ,
 `id_user`      integer NOT NULL ,

PRIMARY KEY (`id`),
KEY `fkIdx_121` (`type`),
CONSTRAINT `FK_121` FOREIGN KEY `fkIdx_121` (`type`) REFERENCES `LogsType` (`id`),
KEY `fkIdx_136` (`id_execution`),
CONSTRAINT `FK_136` FOREIGN KEY `fkIdx_136` (`id_execution`) REFERENCES `Execution` (`id`),
KEY `fkIdx_154` (`id_user`),
CONSTRAINT `FK_154` FOREIGN KEY `fkIdx_154` (`id_user`) REFERENCES `User` (`id`)
);




ALTER TABLE `DirectionProcess` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `LogsProcess` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `OrderProcess` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `Process` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `TypeDatabase` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `User` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `UserDatabase` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;

ALTER TABLE `Execution` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;
ALTER TABLE `LogsType` CHANGE `dt_update` `dt_update` TIMESTAMP on update CURRENT_TIMESTAMP NULL DEFAULT NULL;

 ALTER TABLE `DirectionProcess` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `LogsProcess` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `OrderProcess` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `Process` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `TypeDatabase` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `User` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `UserDatabase` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE `Execution` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE `LogsType` CHANGE `dt_insert` `dt_insert` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;



ALTER TABLE `Execution` DROP `dt_finish`;
ALTER TABLE `Execution` DROP `dt_start`;

ALTER TABLE `Execution` CHANGE `dt_delete` `dt_delete` DATETIME NULL;
ALTER TABLE `LogsProcess` CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT;
