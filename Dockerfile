# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Compile the application
RUN ./mvnw clean package

# Run the application
CMD ["java", "-jar", "target/your-application.jar"]