FROM openjdk:21-jdk-slim
LABEL authors="asemalikova&gulnazsergey"

WORKDIR /app

COPY target/notification_manager-0.0.1-SNAPSHOT.jar /app/microservice.jar

ENTRYPOINT ["java", "-jar", "/app/microservice.jar"]
