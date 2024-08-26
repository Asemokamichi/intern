FROM openjdk:21-jdk-slim
LABEL authors="suncake"

WORKDIR /app

COPY target/file_manager-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
