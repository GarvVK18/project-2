# Build from the repository root so Gradle can resolve all modules.
FROM gradle:8.14-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle --no-daemon :service-registry:bootJar :config-server:bootJar :api-gateway:bootJar :order-service:bootJar :inventory-service:bootJar :payment-service:bootJar

FROM eclipse-temurin:17-jre
WORKDIR /app
ARG SERVICE_JAR
COPY --from=build /workspace/${SERVICE_JAR} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
