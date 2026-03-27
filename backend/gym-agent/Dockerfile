# Stage 1: Build stage
FROM maven:3.9.9-eclipse-temurin-21-jammy as builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Create a non-root user for security
RUN useradd -m -u 1000 appuser

# Copy the built JAR from builder stage
COPY --from=builder --chown=appuser:appuser /app/target/gym-agent-*.jar app.jar

# Switch to non-root user
USER appuser

# Expose port 8080
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.launch.JarLauncher org.springframework.boot.health.check || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

