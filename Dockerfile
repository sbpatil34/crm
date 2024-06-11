FROM eclipse-temurin:17-jdk-jammy as builder
ADD ../target/*.jar app.jar
ADD /init.sql  init.sql
ENTRYPOINT ["java", "-jar", "app.jar"]