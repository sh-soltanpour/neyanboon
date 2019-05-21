#FROM maven as builder
#COPY . .
#RUN mvn clean package

FROM tomcat:9.0-jre8-alpine
COPY target/neyanboon-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/neyanboon.war
#RUN mvn clean