# Quick Documents
## What is Quick Documents
Quick Document is an open source project design to test and compare Insfrastructure as a Service (IaaS) provided by most of the Cloud provider. This services will be used by users to save document like invoice or certificate as an image and then later allow them to search for these documents using keywords. It's a full functionnal and responsive web services that also enable users to switch from one language to another between the following ones: English French, German and Italian.

## Quick Document Database design
In order to simplify things, we have create one database with two tables. The first one will contain image information like image path, and uploaded date. And the second will be used to store text that has been extracted from each image as well a reference as that image. The text will be used later to search and filter images.

## Quick Document Techonology
Well Quick Document is powered by

 - [x] [Java 11] (https://docs.oracle.com/en/java/javase/11/docs/api/index.html)
 - [x] [Spring boot 2.3.0.RELEASE] (https://spring.io/blog/2020/05/15/spring-boot-2-3-0-available-now)
 - [x] [Free Template Language 2.3.0.RELEASE] (https://freemarker.apache.org/docs/versions_2_3.html)
 - [x] [Hibernate] (https://hibernate.org/)
 - [x] [MySql 8.0.024] (https://dev.mysql.com/doc/relnotes/mysql/8.0/en/news-8-0-24.html)
 - [x] [Tess4j 4.5.4] (https://libraries.io/maven/net.sourceforge.tess4j:tess4j/4.5.4) to reconive and read optical character on uploaded images
 - [x] [Bootstrap 5.1.1] (https://getbootstrap.com/docs/5.1/getting-started/introduction/)
 - [x] [Maven] (https://mvnrepository.com/)

## Quick Document Functionalities

- Upload images
- Search images
### Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

###Prerequisites
To run this application you need to have theses applications install on your operating system regardless on which oparating system your are using:

Java 11 to see which JDK version your are using simply open a terminal an type this command:
```
java -version
```
MySql 8.0.024 Server in to run the database script before deploy the application in the an Application Web Server.
Running the Application
To run the application first make sure that port 8080 is not use in your operating system. Then clone this repository and run the app with the following commands:
```
git clone https://github.com/patrickHub/quick-document.git
cd quick-document
mvn clean instal
```
You need to add a System Variable named TESSDATA_PREFIX that will hold path of the [OCR/tessdata](https://github.com/patrickHub/Quick-Documents/tree/main/OCR/tessdata) as value.

And then your can upload it into an application server as [Wildfly] (https://www.wildfly.org/), [Glassfish] (https://glassfish.org/) or [Tomcat] (http://tomcat.apache.org/) then start upload your images into [http://127.0.0.1:8080/QuickDocumentService]

## Authors
PatrickHub
