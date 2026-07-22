# api-java-bavaria-munich

Java API in the [advanced-apis](../README.md) monorepo.

| Item             | Version |
|------------------|---------|
| Java             | 8       |
| Spring Boot      | 2.7.18  |
| Gradle (wrapper) | 8.14    |

## Documentation

| Topic | Location |
|-------|----------|
| Project bootstrap | [docs/CREATION.md](docs/CREATION.md) |
| Incremental implementation phases | [docs/phases/](docs/phases/) |

The [phases](docs/phases/) guides document the API evolution step by step: executable scaffold, externalized configuration and Docker, in-memory `Product` CRUD, `PUT` replace, `PATCH` partial update, PostgreSQL with JPA and SQL migrations, shared utilities, route protection (`public-routes`), authentication (**one of** API key, JWT in-memory, or JWT hybrid - pick one sub-phase; canonical path is JWT with database credentials), a unified API error response contract (`{"errors":[...]}`), then manual input validation for user create and product writes. Follow the guide order in [docs/phases/README.md](docs/phases/README.md) (navigation table).

## API documentation

With the app running (default port `8080` and context-path `/api`):

| Resource | URL |
|----------|-----|
| Swagger UI | http://localhost:8080/api/swagger-ui/index.html |
| OpenAPI JSON | http://localhost:8080/api/v3/api-docs |

## Security

### Default users (login credentials)

The following users are active only if you choose some memory-backed authentication, applies in the guides **8c** and **8d**. These are not applicable if you choose the api key approach (guide **8b**) or the official JWT approach (guide **8a**).

The property `app.security.default-users` stores BCrypt password hashes. Use these credentials at `POST /api/auth/login`:

| Email | Password |
|-------|----------|
| `admin@mail.com` | `adminpass` |
| `admin2@mail.com` | `admin2pass` |

Passwords in config (`default-users`) and in the database are stored as **BCrypt hashes**; login requests send the plaintext password in the JSON body.

## Run

All commands below assume the current directory is `api-java-bavaria-munich/`.

Requirements:

- **Java 8**
- **Gradle wrapper** (`gradlew` / `gradlew.bat` in this module; no global Gradle install required)

How you start the API depends on which guides you have already applied. Later steps **add** options; they do not remove earlier ones unless noted. Each subsection below gives the **full** commands for that stage. The same commands and instructions are also available in the guides where this is first introduced.

Currently, the ways to run the application are:

- IntelliJ IDEA
- Command line with Gradle `bootRun`
- Command line with Gradle `bootJar` + `java -jar`
- Docker Compose (from the container sub-phase onward)

### IntelliJ IDEA

This runs the application in its initial state:

1. Open this module so Gradle can sync.
2. Run → Edit Configurations… → add an **Application** (or **Spring Boot**) configuration.
3. **Main class:** `com.myapps.bavariamunich.ApiJavaBavariaMunichApplication`
4. **Working directory:** the module root (`…/api-java-bavaria-munich`)
5. Leave **Active profiles** empty.
6. Do not add profile VM options yet
7. To use spring profiles and/or New Relic agent
  7.1. Local Spring profile
    - Add this to VM Options: `-Dspring.profiles.active=local`
  7.2. Cloud Spring profile
    - Add this to VM Options: `-Dspring.profiles.active=cloud`
    - Load env vars from **`.env.local`**
  7.3. Local Spring profile with New Relic agent
    - Add this to VM Options: `-Dspring.profiles.active=local -javaagent:newrelic.jar`
    - Load at least `NEW_RELIC_*` env vars from **`.env.local`** and review its values
  7.4. Cloud Spring profile with New Relic agent
    - Add this to VM Options: `-Dspring.profiles.active=cloud -javaagent:newrelic.jar`
    - Load env vars from **`.env.local`** and review `NEW_RELIC_*` env vars values
8. Apply / OK, then Run.

### Gradle `bootRun`

- **Windows Git Bash:**
  ```bash
  # Standard
  ./gradlew bootRun

  # Run with local Spring profile
  ./gradlew bootRun --args='--spring.profiles.active=local'

  # Run with cloud Spring profile, setting example env vars in shell session
  export JWT_SECRET='your_jwt_secret_here'
  export PUBLIC_ROUTES='[{"route":"^/open(/.*)?$"}]'
  ./gradlew bootRun --args='--spring.profiles.active=cloud'

  # Run with local Spring profile and New Relic agent, setting env vars in shell session
  export NEW_RELIC_LICENSE_KEY='your_new_relic_license_key'
  export NEW_RELIC_APP_NAME='api-java-bavaria-munich'
  export NEW_RELIC_AGENT_ENABLED='true'
  export JAVA_TOOL_OPTIONS='-javaagent:newrelic.jar'
  ./gradlew bootRun --args='--spring.profiles.active=local'

  # Run with cloud Spring profile and New Relic agent, setting example env vars in shell session
  export NEW_RELIC_LICENSE_KEY='your_new_relic_license_key'
  export NEW_RELIC_APP_NAME='api-java-bavaria-munich'
  export NEW_RELIC_AGENT_ENABLED='true'
  export JAVA_TOOL_OPTIONS='-javaagent:newrelic.jar'
  ./gradlew bootRun --args='--spring.profiles.active=cloud'
  ```

### Gradle `bootJar` + `java -jar`

- **Windows Git Bash:**
  ```bash
  # Standard
  ./gradlew bootJar
  java -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar
  
  # Run with local Spring profile
  ./gradlew bootJar
  java -Dspring.profiles.active=local -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar

  # Run with cloud Spring profile, setting example env vars in shell session
  export JWT_SECRET='your_jwt_secret_here'
  export PUBLIC_ROUTES='[{"route":"^/open(/.*)?$"}]'
  ./gradlew bootJar
  java -Dspring.profiles.active=cloud -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar

  # Run with local Spring profile and New Relic agent, setting env vars in shell session
  export NEW_RELIC_LICENSE_KEY='your_new_relic_license_key'
  export NEW_RELIC_APP_NAME='api-java-bavaria-munich'
  export NEW_RELIC_AGENT_ENABLED='true'
  ./gradlew bootJar
  java -Dspring.profiles.active=local -javaagent:newrelic.jar -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar

  # Run with cloud Spring profile and New Relic agent, setting example env vars in shell session
  export NEW_RELIC_LICENSE_KEY='your_new_relic_license_key'
  export NEW_RELIC_APP_NAME='api-java-bavaria-munich'
  export NEW_RELIC_AGENT_ENABLED='true'
  ./gradlew bootJar
  java -Dspring.profiles.active=cloud -javaagent:newrelic.jar -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar
  ```

### Docker Compose

- **Windows Git Bash:**
  ```bash
  # Build (or rebuild) and start
  docker compose --env-file .env.local up -d --build
    
  # Start (image already built)
  docker compose --env-file .env.local up -d

  # Stop
  docker compose --env-file .env.local down
  ```

