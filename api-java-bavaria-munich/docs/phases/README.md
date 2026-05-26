# Phase implementation guides (v0.0.x)

This folder contains the **incremental** documentation for the `api-java-bavaria-munich` API: each file describes one phase of the project's evolution — goals, touched files, and reference code snippets to reproduce or review that step.

Phases must be followed in order: each version assumes the previous one.

| Version | Phase | Topic |
|---------|-------|-------|
| [v0.0.0](v0.0.0.md) | 0 | Executable scaffold (Java 8, Spring Boot 2.7), `GET /api/open/check` |
| [v0.0.1](v0.0.1.md) | 1 | `Product` entity, in-memory repository, DTOs, mapper, basic CRUD (`GET`, `POST`, `DELETE`) |
| [v0.0.2](v0.0.2.md) | 2 | `PUT /products/{id}` with replace semantics |
| [v0.0.3](v0.0.3.md) | 3 | `PATCH /products/{id}` with partial update (`JsonNullable`) |
| [v0.0.4](v0.0.4.md) | 4 | Postgres + JPA replacing the in-memory adapter; SQL migrations |

For how the repository was bootstrapped from scratch (Gradle, wrapper, layout), see [CREATION.md](../CREATION.md).
