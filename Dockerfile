# Use the official Maven image as a base image
FROM maven:3.8.3-openjdk-11-slim AS build

WORKDIR /app

# Copy the entire project
COPY . .

# Expose the port the application runs on
EXPOSE 8080

# Run the Spring Boot application with Maven
CMD ["mvn", "spring-boot:run"]
