#
# Build stage
#
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only what's needed first (leveraging Docker layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Now copy the full source code
COPY src ./src

# Build the JAR
RUN mvn clean install -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the JAR built in the previous stage
COPY --from=build /app/target/test-0.0.1-SNAPSHOT.jar demo.jar

# Optional: set environment variable and expose port
ENV PORT=8080
#EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.jar"]
