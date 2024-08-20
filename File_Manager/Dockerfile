# Используем базовый образ с Java
FROM openjdk:21-jdk-slim

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
RUN mvn dependency:go-offline

# Копируем исходный код в контейнер
COPY src ./src

# Копируем собранный JAR файл в рабочую директорию
COPY target/File_Manager-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, который будет использовать приложение
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
