CREATE DATABASE quickdocument_db;

CREATE USER 'quickdocument_backend'@'%' IDENTIFIED BY 'h21hB17';

GRANT ALL PRIVILEGES ON quickdocument_db.* TO 'quickdocument_backend'@'%';

USE quickdocument_db;

CREATE TABLE ImageText(
	Id INT AUTO_INCREMENT,
    Content TEXT NOT NULL,
    ImgPath VARCHAR(255) NOT NULL,
    FULLTEXT(Content),
    
    CONSTRAINT ImageText_PK PRIMARY KEY(Id)
);