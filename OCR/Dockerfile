# Stage 1: Build the application
FROM maven:3-eclipse-temurin-17-alpine AS build

# Set the working directory in the container
WORKDIR /app

# Copy only the pom.xml first (to leverage Maven's dependency caching)
COPY ./pom.xml .

# Pre-download dependencies (cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY ./src ./src

# Build the application (skipping tests for faster builds)
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime container
FROM openjdk:17-jdk-slim

# Install Ghostscript and Tesseract
RUN apt-get update &&  \
    apt-get install -y ghostscript tesseract-ocr wget && \
    apt-get clean

# Set the working directory in the container
WORKDIR /app

# Download Tesseract language data during build
RUN mkdir -p /app/tessdata && \
    wget -q -O /app/tessdata/eng.traineddata https://github.com/tesseract-ocr/tessdata_best/raw/main/eng.traineddata && \
    wget -q -O /app/tessdata/deu.traineddata https://github.com/tesseract-ocr/tessdata_best/raw/main/deu.traineddata

# Set TESSDATA_PREFIX environment variable
ENV TESSDATA_PREFIX=/app/tessdata/

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/OCR.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/OCR.jar"]
