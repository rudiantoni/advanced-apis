# Phase implementation guides (v0.0.x)

This folder contains the **incremental** documentation for the `api-java-bavaria-munich` API: each file describes one phase of the project's evolution — goals, touched files, and reference code snippets to reproduce or review that step.

**Application order** is defined by **filename sort order** in this folder. Guides do not restate that order with file references — follow the sorted list.

**Phases** (`v0.0.0` … `v0.0.9`) are always **incremental**.

**Sub-phases** (`v0.0.Na` … `v0.0.Nz`) may be incremental or mutually exclusive alternatives; each sub-phase document states which at the top. A phase that has sub-phases includes an **index** document named `v0.0.N-idx.md` (title: `Phase N index`).

**Cross-references:** Phase and sub-phase bodies avoid citing other guides by filename or phase number. Use *previous* / *later* and functional descriptions instead (for example “the phase where Postgres was introduced”). **This README is the exception** — it may name and link every guide for navigation.

Snippets in **phase** documents are incremental — only what that step adds or changes is shown. `# ...` and `// ...` mean **keep settings from previous phases** and merge in the new blocks. **Sub-phase** snippets follow the incremental rule declared at the top of each sub-phase file.

| Version | Phase / Sub-Phase | Topic |
|---------|-------------------|-------|
| [v0.0.0](v0.0.0.md) | Phase 0 | Executable scaffold (Java 8, Spring Boot 2.7); reachability check endpoints; config placeholders and demonstration `AppConfig` |
| [v0.0.1](v0.0.1.md) | Phase 1 | `Product` entity, in-memory repository, DTOs, mapper, basic CRUD (`GET`, `POST`, `DELETE`) |
| [v0.0.2](v0.0.2.md) | Phase 2 | `PUT /products/{id}` with replace semantics |
| [v0.0.3](v0.0.3.md) | Phase 3 | `PATCH /products/{id}` with partial update (`JsonNullable`) |
| [v0.0.4](v0.0.4.md) | Phase 4 | Postgres + JPA replacing the in-memory adapter; SQL migrations |
| [v0.0.5](v0.0.5.md) | Phase 5 | Shared utilities — `JsonUtil` extension; `AppConfig` wires `ObjectMapper` into `JsonUtil` |
| [v0.0.6-idx](v0.0.6-idx.md) | Phase 6 index | Security layer overview — choose one sub-phase below |
| [v0.0.6a](v0.0.6a.md) | Sub-Phase 6a | API key header auth; public-route rules (path regex + optional HTTP methods); filter and Spring Security wiring |
| [v0.0.6b](v0.0.6b.md) | Sub-Phase 6b | JWT Bearer auth (mock login, `GET /users/me`, no roles); public-route rules; filter and Spring Security wiring |

For how the repository was bootstrapped from scratch (Gradle, wrapper, layout), see [CREATION.md](../CREATION.md).
