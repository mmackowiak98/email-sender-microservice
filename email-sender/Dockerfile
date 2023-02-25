FROM openjdk:21-slim-buster as build

#Information around who maintains the image
MAINTAINER mmackowiak.com

# Add the application's jar to the container
COPY target/email-sender-api-0.0.1-SNAPSHOT.jar email-sender-api-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/email-sender-api-0.0.1-SNAPSHOT.jar"]