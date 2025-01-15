# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/lugha-translator.jar .

# Copy application.properties file into the container at /app
COPY src/main/resources/application.properties .

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run application when the container launches
CMD ["java", "-jar", "lugha-translator.jar"]

