#FROM maven as builder
#COPY . .
#RUN mvn clean package

FROM tomcat:8.0.20-jre8
COPY target/neyanboon-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/neyanboon.war
#RUN mvn clean