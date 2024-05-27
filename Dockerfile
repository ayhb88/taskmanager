# Use an official JDK runtime as a parent image
FROM openjdk:17-slim-buster

# Set the working directory in the container
WORKDIR app

# Copy the application's jar file to the container
COPY build/libs/taskmanager-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entry point to run the jar file
CMD ["java","-jar","app.jar"]