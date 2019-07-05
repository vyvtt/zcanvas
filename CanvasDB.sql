CREATE DATABASE CanvasDB

USE CanvasDB

CREATE TABLE Canvas (
	id int IDENTITY(1,1) PRIMARY KEY,
	name NVARCHAR(250) NOT NULL,
	url VARCHAR(250) NOT NULL,
	hashURL int NOT NULL,
	image VARCHAR(250) NOT NULL,
	designer NVARCHAR(250),
	color VARCHAR(250)
);

CREATE TABLE Detail (
	id int IDENTITY(1,1) PRIMARY KEY,
	canvasId int NOT NULL FOREIGN KEY REFERENCES Canvas(id),
	width int NULL,
	length int NULL,
	unit VARCHAR(10) NULL,
	price int NULL
);

CREATE TABLE Category (
	id int IDENTITY(1,1) PRIMARY KEY,
	name NVARCHAR(250)
);

CREATE TABLE CategoryCanvas(
	CanvasId int FOREIGN KEY REFERENCES Canvas(id),
	CategoryId int FOREIGN KEY REFERENCES Category(id),
	PRIMARY KEY (CanvasId, CategoryId)
);

CREATE TABLE Location (
	id int IDENTITY(1,1) PRIMARY KEY,
	name NVARCHAR(250),
	image VARCHAR(250)
);

CREATE TABLE LocationCategory(
	LocationId int FOREIGN KEY REFERENCES Location(id),
	CategoryId int FOREIGN KEY REFERENCES Category(id),
	PRIMARY KEY (LocationId, CategoryId)
);

SELECT [CanvasId]
      ,[CategoryId]
  FROM [CanvasDB].[dbo].[CategoryCanvas]

  Select * from Canvas where Canvas.id in (select CanvasId from CategoryCanvas where CategoryCanvas.CategoryId = 15)

DELETE FROM CategoryCanvas

DELETE FROM Category
DBCC CHECKIDENT ('Category', RESEED, 0);

DELETE FROM Detail
DBCC CHECKIDENT ('Detail', RESEED, 0);

DELETE FROM Canvas
DBCC CHECKIDENT ('Canvas', RESEED, 0);
