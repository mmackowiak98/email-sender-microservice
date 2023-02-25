FROM openjdk:21-slim-buster as build

MAINTAINER mmackowiak.com

COPY target/config-server-0.0.1-SNAPSHOT.jar config-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/config-server-0.0.1-SNAPSHOT.jar"]