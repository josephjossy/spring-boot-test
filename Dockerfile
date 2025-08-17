# ---- Build Stage ----
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app

# Copy Gradle build files first (for better caching of dependencies)
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY gradle ./gradle

# Download dependencies (helps cache Gradle deps between builds)
RUN gradle dependencies || return 0

# Copy the rest of the project
COPY . .

# Build the application without running tests
RUN ./gradlew clean build -x test

# ---- Run Stage ----
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy only the JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
