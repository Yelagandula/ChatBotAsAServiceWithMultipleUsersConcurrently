# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to access the app
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
