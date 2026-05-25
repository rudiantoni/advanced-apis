# advanced-apis

## Dependencies

### Databases

- Postgres 18 (dev compose)
  - Start in background (-d) but wait (--wait) for healthcheck
    ```bat
    cd repository_root/docker/dev
    docker compose up -d --wait postgres_18
    ```
  - Stop
    ```bat
    cd repository_root/docker/dev
    docker compose down postgres_18
    ```

## Java projects

Version table

| Project                 | Java | Spring Boot | Database    | Build tool            | Others |
|-------------------------|------|-------------|-------------|-----------------------|--------|
| api-java-bavaria-munich | 8    | 2.7.18      | Postgres 18 | Gradle (wrapper) 8.14 | spring-boot-starter-web 2.7.18 <br> spring-boot-starter-data-jpa 2.7.18 <br> org.postgresql:postgresql 42.3.8 <br> jackson-databind-nullable 0.2.10 |


- countries
  - Java: germany
    - Java 8: bavaria
        - Spring 2.7: munich
    - Java 11: berlin
        - Spring 2.7: munich
    - Java 17: bremen
        - Spring 2.7: munich
        - Spring 3.5: frankfurt
        - Spring 4.0: cologne
    - Java 21: hamburg
        - Spring 2.7: munich
        - Spring 3.5: frankfurt
        - Spring 4.0: cologne
    - Java 25: saxony
        - Spring 3.5: frankfurt
        - Spring 4.0: cologne

  - Kotlin: finland
  - Python: netherlands
  - TypeScript: sweden
  - JavaScript: portugal
