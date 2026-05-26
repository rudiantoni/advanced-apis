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

The [phases](docs/phases/) guides document the API evolution step by step: executable scaffold, in-memory `Product` CRUD, `PUT` replace, `PATCH` partial update, then PostgreSQL with JPA and SQL migrations. Read them in version order; each phase builds on the previous one. Overview and index: [docs/phases/README.md](docs/phases/README.md).

## Run

From the module root (`api-java-bavaria-munich/`).

- Requires **Java 8**
- Gradle is provided by the wrapper (`gradlew` / `gradlew.bat`).

### Development

- Windows (CMD / PowerShell)
  ```bat
  .\gradlew.bat bootRun
  ```
- Linux / macOS
  ```bash
  ./gradlew bootRun
  ```

### Tests

- Windows (CMD / PowerShell)
  ```bat
  .\gradlew.bat test
  ```
- Linux / macOS
  ```bash
  ./gradlew test
  ```

### Executable JAR

- Windows (CMD / PowerShell)
  ```bat
  .\gradlew.bat bootJar
  ```
- Linux / macOS
  ```bash
  ./gradlew bootJar
  ```

Output:

| OS | Path |
|----|------|
| Windows | `build\libs\api-java-bavaria-munich-0.0.1-SNAPSHOT.jar` |
| Linux / macOS | `build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar` |

Use this fat JAR, not `*-plain.jar` — the plain JAR has no embedded dependencies.

- Windows (CMD / PowerShell)
  ```bat
  java -jar build\libs\api-java-bavaria-munich-0.0.1-SNAPSHOT.jar
  ```
- Linux / macOS
  ```bash
  java -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar
  ```

Only Java 8 is required on the target machine (no Gradle).
