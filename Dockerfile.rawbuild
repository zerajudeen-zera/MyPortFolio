# lightweight maven builder base image
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Create a tworkdirectory
WORKDIR /app

# copy the required source code and pom file
COPY pom.xml /app
COPY src ./src

RUN mvn clean package -DskipTests

#Runtime for the build

FROM eclipse-temurin:17-jdk-alpine

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]