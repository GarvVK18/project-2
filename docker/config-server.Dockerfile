FROM gradle:8.14-jdk17 AS build
WORKDIR /workspace
COPY . .
RUN gradle --no-daemon :config-server:bootJar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/config-server/build/libs/*.jar /app/app.jar
EXPOSE 8888
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75.0","-jar","/app/app.jar"]
