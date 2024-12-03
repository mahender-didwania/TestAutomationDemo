FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/item-app-1.0-SNAPSHOT.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]