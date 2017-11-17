-- MySQL Workbench Synchronization
-- Generated: 2017-11-17 20:41
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: cb-vaibhav

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE TABLE IF NOT EXISTS `wallet_app`.`user_wallet_tokens` (
  `user_wallet_id` BIGINT(19) NOT NULL,
  `token` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`user_wallet_id`),
  UNIQUE INDEX `token_UNIQUE` (`token` ASC),
  CONSTRAINT `user_wallet_id`
    FOREIGN KEY (`user_wallet_id`)
    REFERENCES `wallet_app`.`user_wallets` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `wallet_app`.`user_wallets` (
  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(19) NOT NULL,
  `app_id` BIGINT(19) NOT NULL,
  `mobile_number` VARCHAR(15) NOT NULL,
  `balance` INT(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `index2` (`user_id` ASC, `app_id` ASC, `mobile_number` ASC),
  INDEX `app_id_idx` (`app_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `wallet_app`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `app_id`
    FOREIGN KEY (`app_id`)
    REFERENCES `wallet_app`.`apps` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
