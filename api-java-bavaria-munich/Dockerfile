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
COPY --chown=appuser:appgroup newrelic.jar /app/newrelic.jar
COPY --chown=appuser:appgroup newrelic.yml /app/newrelic.yml
COPY --chown=appuser:appgroup docker-entrypoint.sh /app/docker-entrypoint.sh

RUN chmod +x /app/docker-entrypoint.sh

USER appuser

ARG SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["/app/docker-entrypoint.sh"]
