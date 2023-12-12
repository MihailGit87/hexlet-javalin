DROP TABLE IF EXISTS courses;

CREATE TABLE courses(
id INT PRIMARY KEY AUTO_INCREMENT,
name varchar(255) NOT NULL,
description text
);

DROP TABLE if EXISTS users;

CREATE TABLE users (
id INT PRIMARY KEY AUTO-ICREMENT,
name varchar(255),
email varchar(255)
)
