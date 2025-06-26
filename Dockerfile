FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY --from=build /app/target/test-0.0.1-SNAPSHOT.jar demo.jar
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
