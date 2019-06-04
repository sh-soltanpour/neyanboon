FROM maven:3.6.1-jdk-8-alpine as builder
COPY . .
RUN mvn clean package

FROM tomcat:9.0-jre8-alpine
COPY --from=builder target/neyanboon-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/neyanboon.war