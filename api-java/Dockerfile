FROM gradle:8.5-jdk17 AS builder
WORKDIR /opt/app
COPY build.gradle settings.gradle /opt/app
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY . .
RUN gradle build --no-daemon -x test

#-------------------------------------------------
FROM openjdk:17 AS runner
COPY --from=builder /opt/app/build/libs/api-java-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
