CREATE DATABASE quickdocument_db;

USE quickdocument_db;

CREATE TABLE ImageText(
	Id INT AUTO_INCREMENT,
    Content TEXT NOT NULL,
    ImgPath VARCHAR(255) NOT NULL,
    FULLTEXT(Content),
    
    CONSTRAINT ImageText_PK PRIMARY KEY(Id)
);