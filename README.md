HR Information System CONSOLE DEMO
Used Technology
IDE: Eclipse Mars.1 (4.5.1)
JDK: 1.8.0_92
JDBC driver for MySQL: mysql-connector-java-5.1.40-bin
MySQL Server 5.7.16
How Setup Project
1.	Download project from github link.
2.	Import project to Eclipse.
3.	Setup MySQL:
At first start MySQL server and set password to „pass“  at user „root“.
USERNAME = "root";
PASSWORD = "pass";
4.	Create schema „jooqtest“ in MySQL client
5.	Use following query statement to create table in jooqtest:
CREATE TABLE employee (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
Name CHAR(30) not null,
Age SMALLINT(3) not null,
Position CHAR(30) not null
);
6.	Run application in Eclipse
