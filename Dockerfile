FROM eclipse-temurin:17-jdk-jammy as builder
ADD ../target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]