# Use an official Java runtime as a parent image
FROM openjdk:17.0.1-jdk-slim

# Set the application's JAR file
ARG JAR_FILE=build/libs/online-shop-api-0.1.0.jar

# Copy the JAR file into the image
COPY ${JAR_FILE} app.jar

# Set the startup command to run your Java application
ENTRYPOINT ["java","-jar","/app.jar"]
