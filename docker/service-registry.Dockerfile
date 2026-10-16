FROM gradle:8.14-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle --no-daemon :service-registry:bootJar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/service-registry/build/libs/*.jar /app/app.jar
EXPOSE 8761
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
