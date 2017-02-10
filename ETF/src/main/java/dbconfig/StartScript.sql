DROP DATABASE IF EXISTS etf;

CREATE DATABASE IF NOT EXISTS etf DEFAULT CHARACTER SET utf8 ;;

USE etf;

-- Table etfs
CREATE TABLE etfs (
  id   BIGINT PRIMARY KEY      NOT NULL AUTO_INCREMENT,
  etf_name       VARCHAR(100)  NOT NULL,
  ticker         VARCHAR(10)   NOT NULL UNIQUE,
  description    VARCHAR(1024) NOT NULL,
  top_holdings   VARCHAR(1024) NOT NULL,
  country_weight VARCHAR(1024) NOT NULL,
  sector_weight  VARCHAR(1024) NOT NULL
);

-- Table users
CREATE TABLE users
(
  id          BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username    VARCHAR(35) NOT NULL UNIQUE ,
  password    VARCHAR(35) NOT NULL,
  name        VARCHAR(35) NOT NULL,
  email       VARCHAR(35) NOT NULL UNIQUE
);

-- Table request_history
CREATE TABLE request_history (
  id            BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id       BIGINT,
  etf_id        BIGINT,
  datetime      DATETIME,
  FOREIGN KEY (etf_id) REFERENCES etfs (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);