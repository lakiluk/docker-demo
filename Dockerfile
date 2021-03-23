FROM openjdk:11.0.6-jre-slim

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
