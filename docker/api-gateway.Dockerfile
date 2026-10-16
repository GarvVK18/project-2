FROM gradle:8.14-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle --no-daemon :api-gateway:bootJar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/api-gateway/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
