CREATE DATABASE IF NOT EXISTS toprojects;

USE toprojects;

DROP TABLE IProject_Team;
DROP TABLE IProject_Employee;
DROP TABLE IProject;

CREATE TABLE IProject (
	id			INTEGER AUTO_INCREMENT PRIMARY KEY,
	projectId	VARCHAR(50) UNIQUE,
	deadline	DATE,
	startDate	DATE,
	budget		INTEGER
);

CREATE TABLE IProject_Team (
	id			INTEGER AUTO_INCREMENT PRIMARY KEY,
	teamId		VARCHAR(50),
	projectId	VARCHAR(50),
	FOREIGN KEY (projectId) REFERENCES IProject(projectId)
);

CREATE TABLE IProject_Employee (
	id			INTEGER AUTO_INCREMENT PRIMARY KEY,
	employeeId  VARCHAR(50),
	projectId	VARCHAR(50),
	FOREIGN KEY (projectId) REFERENCES IProject(projectId)
);

INSERT INTO IProject (projectId, deadline, startDate, budget) VALUES ("1", "2016-01-01", "2015-01-01", 10000);

/*
--DROP TABLE IProject;
--------------------------------------------------------------------
------- TEGO NIE TWORZYMY. To powinno byc w innych modulach--------
-------------------------------------------------------------------- 
--CREATE TABLE IEmployee (
--	id			INT AUTO_INCREMENT PRIMARY KEY,
--	employeeId	INT,
--	firstName	VARCHAR(50),
--	lastName	VARCHAR(50),
--	position	VARCHAR(50),
--	salary		INTEGER,
--	phone		VARCHAR(50),
--	date		DATE,
--	email		VARCHAR(50)
--);
--
--CREATE TABLE ITeam (
--	id			INT AUTO_INCREMENT PRIMARY KEY,
--	teamId		INT,
--	name		VARCHAR(50)
--);
*/