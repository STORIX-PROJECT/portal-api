FROM openjdk:25-ea-21-jdk-slim

WORKDIR /app

COPY build/libs/portal-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]