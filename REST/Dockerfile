# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim AS build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files from the Rest subdirectory into the container
COPY ./out/pom.xml ./
COPY ./out/src ./src

# Run Maven build to generate the JAR file
RUN mvn clean package -DskipTests

# Step 2: Create a lightweight JDK container and copy the JAR from the build stage
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the app directory
COPY --from=build /app/target/openapi-spring-1.0.11.jar /app/RestServer.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8081:8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/RestServer.jar"]
