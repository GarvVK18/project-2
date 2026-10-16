FROM gradle:8.14-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle --no-daemon :inventory-service:bootJar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/inventory-service/build/libs/*.jar /app/app.jar
EXPOSE 8082
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
