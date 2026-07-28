# Phase implementation guides (v0.0.x)

This folder contains the **incremental** documentation for the `api-java-bavaria-munich` API: each file describes one phase of the project's evolution - goals, touched files, and reference code snippets to reproduce or review that step.

**Application order** is defined by the **navigation table** below (and by the same sequence when walking guides). For single-digit phase numbers (`v0.0.0` … `v0.0.9`), that order matches plain filename sort. From **`v0.0.10` onward**, do **not** rely on lexicographic filename sort alone (`v0.0.10` sorts before `v0.0.2`); follow the table. Guides do not restate that order with file references.

**Phases** (`v0.0.0` … `v0.0.11`) are always **incremental**.

**Sub-phases** (`v0.0.Na` … `v0.0.Nz`) may be incremental or mutually exclusive alternatives; each sub-phase document states which at the top. A phase that has sub-phases includes an **index** document named `v0.0.N-idx.md` (title: `Phase N index`).

**Official sub-phase (`-official`):** For sub-phases that are **mutually exclusive** implementation choices (for example the phase 8 sub-phases), exactly **one** must be designated the **official** choice for the canonical guide path. The official sub-phase must always be the **first** alternative in the letter sequence for that phase (for example Sub-Phase **8a** with `v0.0.8a-official.md`, not **8d**). Later phases assume that official sub-phase was implemented when they do not state otherwise. Mark the official guide by adding the suffix **`-official`** to the filename immediately after the sub-phase letter(s), before `.md`. Sibling alternatives in the same exclusive group keep their names **without** `-official`. There must be **at most one** `-official` file per exclusive group. On the canonical path, implement the `-official` guide and **skip** the other alternatives in that group; filename sort order still lists every file in the folder, but exclusive siblings other than `-official` are reference-only unless you deliberately switch approach.

**Cross-references:** Phase and sub-phase bodies avoid citing other guides by **filename** (for example `v0.0.5.md`). Prefer *previous* / *later* and **functional descriptions** (for example "the phase where Postgres was introduced", "the shared-utilities phase", "the official authentication sub-phase"). **Exceptions:** this README (navigation table and links); the **phases index** (`v0.0.N-idx.md`, including its link back here); links to the **module** [README.md](../../README.md) for run instructions or specific operational notes (for example the default users used in memory-backed login).

**Non-strict incremental snippets:** each phase shows only what it adds or changes. Placeholders mark omitted prior content - never a patch inside a method or config block.

| Kind | Unit | Placeholder | New or modified |
|------|------|-------------|-----------------|
| Java | field, method, constructor | `// ...` between members | show the **full** member |
| `application-local.yml` | top-level key or nested block (e.g. `app.security:`) | `# ...` between blocks | show the **full** block |
| `build.gradle` | line in `dependencies { }` | `// ...` inside the closure | show **full** new lines |
| SQL migrations | table section (banner + DDL for one table) | `-- ...` between sections | show the **full** section |

First introduction of a file in a phase: full snippet for that phase's scope only (no placeholder).

**Scaffold lifecycle:** The **scaffold phase** introduces placeholder config holders (`MY_CONST`, `myProperty`, demo `AppConfig`). The **shared-utilities phase** **replaces** the demo `AppConfig`. The **route-protection phase** **replaces** `AppConsts` and `AppProperties` scaffold members with public-route fields. **Authentication sub-phases** extend those holders with credential-specific fields.

| Version | Phase / Sub-Phase | Topic |
|---------|-------------------|-------|
| [v0.0.0](v0.0.0.md) | Phase 0 | Executable scaffold (Java 8, Spring Boot 2.7); reachability check endpoints; config placeholders and demonstration `AppConfig` |
| [v0.0.1-idx](v0.0.1-idx.md) | Phase 1 index | Platform integrations - canonical path includes Sub-Phases **1a**–**1d** |
| [v0.0.1a](v0.0.1a.md) | Sub-Phase 1a | Externalized configuration - profiles `local` / `cloud`, env vars, `.env.example` → `.env.local` |
| [v0.0.1b](v0.0.1b.md) | Sub-Phase 1b | Container image and local Compose - Dockerfile, `docker-entrypoint.sh`, module Compose, `.env.local`, `dev_default` network |
| [v0.0.1c](v0.0.1c.md) | Sub-Phase 1c | API documentation - springdoc OpenAPI 3 + Swagger UI (minimal `OpenApiConfig`) |
| [v0.0.1d](v0.0.1d.md) | Sub-Phase 1d | New Relic APM - Java agent (`-javaagent` + `NEW_RELIC_*`; Docker toggle via entrypoint) |
| [v0.0.2](v0.0.2.md) | Phase 2 | `Product` entity, in-memory repository, DTOs, mapper, basic CRUD (`GET`, `POST`, `DELETE`) |
| [v0.0.3](v0.0.3.md) | Phase 3 | `PUT /products/{id}` with replace semantics |
| [v0.0.4](v0.0.4.md) | Phase 4 | `PATCH /products/{id}` with partial update (`JsonNullable`) |
| [v0.0.5](v0.0.5.md) | Phase 5 | Postgres + JPA replacing the in-memory adapter; SQL migrations |
| [v0.0.6](v0.0.6.md) | Phase 6 | Shared utilities - `JsonUtil` extension (incl. preparatory `fromJsonStr` / `LIST_STRING` not used until later custom code); `AppConfig` replaces scaffold and wires `ObjectMapper` into `JsonUtil` |
| [v0.0.7](v0.0.7.md) | Phase 7 | Route protection - `public-routes`, `RouteProtectionFilter`, Swagger lock icons (no credentials yet) |
| [v0.0.8-idx](v0.0.8-idx.md) | Phase 8 index | Authentication layer - canonical path uses Sub-Phase **8a** (`-official`); alternatives **8b**–**8d** are reference-only |
| [v0.0.8a-official](v0.0.8a-official.md) | Sub-Phase 8a (**official**) | JWT with database credentials and user registration |
| [v0.0.8b](v0.0.8b.md) | Sub-Phase 8b (alternative) | API key header auth; replaces `RouteProtectionFilter` with `ApiKeyAuthFilter` |
| [v0.0.8c](v0.0.8c.md) | Sub-Phase 8c (alternative) | Basic JWT using in-memory `default-users` |
| [v0.0.8d](v0.0.8d.md) | Sub-Phase 8d (alternative) | Hybrid JWT - in-memory credentials and database users |
| [v0.0.9](v0.0.9.md) | Phase 9 | Unified API error contract - `{"errors":[...]}` for 401/404/malformed JSON; `ErrorResponseDto`, `MultiErrorException`, `RequestBodyController` |
| [v0.0.10](v0.0.10.md) | Phase 10 | Manual input validation - `ValidationUtil`, `UserValidationService` / `ProductValidationService`, `400` with multi-error `errors` on user create and product POST/PUT/PATCH |
| [v0.0.11-idx](v0.0.11-idx.md) | Phase 11 index | Error handling evolution - currently documents Sub-Phase **11a** and reserves later sub-phases |
| [v0.0.11a](v0.0.11a.md) | Sub-Phase 11a | Global MVC error handling for known failures; remove controller-level duplication and keep `{"errors":[...]}` |

For how the repository was bootstrapped from scratch (Gradle, wrapper, layout), see [CREATION.md](../CREATION.md).
