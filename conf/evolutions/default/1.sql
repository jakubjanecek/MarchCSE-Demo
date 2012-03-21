# --- !Ups

CREATE TABLE IF NOT EXISTS `drink` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `price` INT NOT NULL,
  `kind` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = MyISAM;


# --- !Downs
 
DROP TABLE `drink`;
