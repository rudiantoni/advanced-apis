# Project creation (from scratch)

Record of how `this project` was created manually, without Spring Initializr.

## Prerequisites

- **Java 8** — target runtime for this API.
- **Gradle 8** — only needed on the machine that generates the wrapper; builds then use `gradlew`.

---

## 1. Create the folder structure

Standard Gradle layout: production code under `src/main`, tests under `src/test`, build files at the root.

```
api-java-bavaria-munich/
├── build.gradle
├── settings.gradle
└── src/
    ├── main/java/...
    ├── main/resources/
    └── test/java/...
```

---

## 2. Generate the Gradle Wrapper

The wrapper pins the Gradle version in the repo so builds are reproducible without a global Gradle install. Requires a global Gradle 8 install for this one-time step.

- Windows (CMD / PowerShell)
  ```bat
  cd api-java-bavaria-munich
  gradle wrapper --gradle-version 8.14
  ```
- Linux / macOS
  ```bash
  cd api-java-bavaria-munich
  gradle wrapper --gradle-version 8.14
  ```

This creates `gradlew`, `gradlew.bat`, and `gradle/wrapper/`. Later builds use the wrapper only — see [README.md](../README.md).

---

## 3. Configure project files

### `build.gradle`

Defines the Java project, Spring Boot 2.7.18, Java 8 compatibility, and minimal dependencies (`web` + tests). JPA and Security are commented out for later use.

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.18'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
}

group = 'com.myapps'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

### `settings.gradle`

Gradle project name (used for the JAR and tasks).

```gradle
rootProject.name = 'api-java-bavaria-munich'
```

### `ApiJavaBavariaMunichApplication.java`

Main class: `@SpringBootApplication` starts the Spring context and the embedded server.

```java
package com.myapps.bavariamunich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiJavaBavariaMunichApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiJavaBavariaMunichApplication.class, args);
    }
}
```

Path: `src/main/java/com/myapps/bavariamunich/`.

### `application.yml`

Spring configuration file; created empty — ports and properties can be added later.

### `ApiJavaBavariaMunichApplicationTests.java`

Smoke test: verifies the Spring context starts without errors (`contextLoads`).

```java
package com.myapps.bavariamunich;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiJavaBavariaMunichApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

Path: `src/test/java/com/myapps/bavariamunich/`.

---

To run or package the application, see [README.md](../README.md).
