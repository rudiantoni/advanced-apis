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

The [phases](docs/phases/) guides document the API evolution step by step: executable scaffold, in-memory `Product` CRUD, `PUT` replace, `PATCH` partial update, PostgreSQL with JPA and SQL migrations, shared utilities, then API security (**one of** API key, JWT in-memory, or JWT hybrid - pick one sub-phase). Follow the guides in filename order. Overview and index: [docs/phases/README.md](docs/phases/README.md).

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

Use this fat JAR, not `*-plain.jar` - the plain JAR has no embedded dependencies.

- Windows (CMD / PowerShell)
  ```bat
  java -jar build\libs\api-java-bavaria-munich-0.0.1-SNAPSHOT.jar
  ```
- Linux / macOS
  ```bash
  java -jar build/libs/api-java-bavaria-munich-0.0.1-SNAPSHOT.jar
  ```

Only Java 8 is required on the target machine (no Gradle).

## Security

Applies after you implement a **JWT** security sub-phase (**6b** or **6c** in the phase guides). Sub-Phase **6a** (API key) has no `/auth/login` endpoint; skip this section for that path.

### Default users (login credentials)

`app.security.default-users` stores BCrypt password hashes. Use these credentials at `POST /api/auth/login`:

| Email | Password |
|-------|----------|
| `admin@mail.com` | `adminpass` |
| `admin2@mail.com` | `admin2pass` |

### Login timing side-channel (known limitation)

The `/auth/login` flow returns the same HTTP **401 Unauthorized** whether the email is unknown or the password is wrong. The response body is also generic (`"Unauthorized"`), so callers cannot distinguish those cases from the payload alone.

However, the server may still take **slightly different time** on each path:

- **Unknown email** - lookup only; password comparison (`constantTimeEquals`) is skipped.
- **Known email, wrong password** - lookup plus constant-time password comparison.
- **Known email, correct password** - lookup, password comparison, and JWT generation.

That difference is a **timing side-channel**: in theory, an attacker who can measure many response times might infer whether an email is registered. Network jitter, database latency, and JVM behavior make this hard to exploit in practice, but it is not fully mitigated today.

**Planned mitigation:** hash passwords with `PasswordEncoder` and always run `matches()` on login (including a dummy hash when the user does not exist), so verification cost is similar on every attempt.

Passwords in config (`default-users`) and in the database are currently stored in **plaintext** during bootstrap; encryption/hashing is planned separately.
