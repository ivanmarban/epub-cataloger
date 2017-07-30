# ePub Cataloger
An e-book cataloging web application written in Java.

## Introduction
ePub Cataloger is an e-book cataloging web application written in Java that is based on [AppFuse stack][1]. The main purpose of this project was to gain a general understanding of all frameworks used by AppFuse and playing around with gradle.

Foundational frameworks used by AppFuse:
- Bootstrap and jQuery
- Hibernate, Spring and Spring Security
- Java 7, Annotations, JSP 2.1, Servlet 3.0
- Web Frameworks: Struts 2, Spring MVC

## Requirements
- Java 7.
- Gradle 2.x.
- MySQL 5.x database.
- Eclipse 4.x + Apache Tomcat 7 Runtime.

## Configuration
Common steps:
 1. Import database using epubcataloger.sql from src/main/resources directory.
 2. Set up jdbc.properties and mail.properties for your environment.

Create an Eclipse project:
 1. Run ```gradle clean eclipse``` to build project structure.
 2. Import project in Eclipse.
 3. Modify project properties and set up Targeted Runtime choosing Apache Tomcat 7.
 4. Add a URIEncoding="UTF-8" property to the connector of Apache Tomcat.

Run application:
 1. Run ```gradle jettyRun``` and you should be able to view the application in your browser at http://localhost:8080/epubcataloger.
 2. Log as administrator using admin/admin or as normal user with user/user

## Troubleshooting
If you receive OutOfMemory errors when using "gradle jettyRun", increase the memory available to Java.
For example, set your JAVA_OPTS environment variable to -Xmx1024M -XX:PermSize=512m.

[1]: http://appfuse.org/display/APF/Home
