CREATE DATABASE quickdocument_db;

CREATE USER 'quickdocument_backend'@'%' IDENTIFIED BY 'h21hB17';

GRANT ALL PRIVILEGES ON quickdocument_db.* TO 'quickdocument_backend'@'%';

USE quickdocument_db;

CREATE TABLE Image(
	Id INT AUTO_INCREMENT,
    UploadOn DATETIME NOT NULL,
    ImgPath VARCHAR(255) NOT NULL,
    
    CONSTRAINT Image_PK PRIMARY KEY(Id)
);


CREATE TABLE ImageText(
	Id INT AUTO_INCREMENT,
    Content TEXT NOT NULL,
    ImageId INT NOT NULL,
    FULLTEXT(Content),
    
    CONSTRAINT Actuality_PK PRIMARY KEY(Id),
    CONSTRAINT ImageText_Images_FK FOREIGN KEY (ImageId) REFERENCES Image(Id)
);