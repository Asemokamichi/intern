FROM openjdk:21-jdk-slim
LABEL authors="gulnazsergey"

WORKDIR /app

COPY target/eureka_server-1.0-SNAPSHOT.jar /app/microservice.jar

ENTRYPOINT ["java", "-jar", "/app/microservice.jar"]