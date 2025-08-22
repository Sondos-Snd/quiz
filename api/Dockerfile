# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Cache Maven repo between builds for speed
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests clean package

FROM eclipse-temurin:21-jre
ENV JAVA_OPTS=""
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
# Render injects $PORT — bind to it
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8081} -jar app.jar"]
