# Use Gradle to build the app
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app

# Copy project files
COPY . .

# Build the application without tests (to speed up CI/CD)
RUN ./gradlew build -x test

# Use a lightweight JDK image to run the app
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy only the built jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
