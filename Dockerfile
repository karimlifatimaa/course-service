# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper files to the container
COPY gradlew .
COPY gradle ./gradle

# Copy the build.gradle and settings.gradle files
COPY build.gradle .
COPY settings.gradle .

# Grant executable permissions to the Gradle wrapper
RUN chmod +x ./gradlew

# Download dependencies. This will speed up subsequent builds
RUN ./gradlew dependencies

# Copy the source code to the container
COPY src ./src

# Build the application
RUN ./gradlew build -x test

# Expose the port the app runs on
EXPOSE 8082

# Specify the command to run on container startup
ENTRYPOINT ["java", "-jar", "build/libs/course-service-0.0.1-SNAPSHOT.jar"]
