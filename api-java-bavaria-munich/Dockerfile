# ---- Build ----
FROM amazoncorretto:8-alpine3.21-jdk AS build

WORKDIR /app

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew

COPY src ./src

RUN ./gradlew bootJar --no-daemon -x test \
    && cp build/libs/api-java-bavaria-munich-*.jar app.jar

# ---- Run ----
FROM amazoncorretto:8-alpine3.21-jre AS run

WORKDIR /app

RUN addgroup -g 10001 appgroup \
    && adduser -D -u 10001 -G appgroup appuser

COPY --from=build --chown=appuser:appgroup /app/app.jar /app/app.jar

USER appuser

ARG SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-Dspring.profiles.active=cloud", "-jar", "/app/app.jar"]

