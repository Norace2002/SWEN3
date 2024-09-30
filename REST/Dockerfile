# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY out/target/openapi-spring-1.0.11.jar /app/RestServer.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/RestServer.jar"]
