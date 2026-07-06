# Phase implementation guides (v0.0.x)

This folder contains the **incremental** documentation for the `api-java-bavaria-munich` API: each file describes one phase of the project's evolution - goals, touched files, and reference code snippets to reproduce or review that step.

**Application order** is defined by **filename sort order** in this folder. Guides do not restate that order with file references - follow the sorted list.

**Phases** (`v0.0.0` … `v0.0.9`) are always **incremental**.

**Sub-phases** (`v0.0.Na` … `v0.0.Nz`) may be incremental or mutually exclusive alternatives; each sub-phase document states which at the top. A phase that has sub-phases includes an **index** document named `v0.0.N-idx.md` (title: `Phase N index`).

**Official sub-phase (`-official`):** For sub-phases that are **mutually exclusive** implementation choices (for example Sub-Phase **6a**, **6b**, or **6c** under the security phase), exactly **one** must be designated the **official** choice for the canonical guide path. Later phases assume that official sub-phase was implemented when they do not state otherwise. Mark the official guide by adding the suffix **`-official`** to the filename immediately after the sub-phase letter(s), before `.md` (for example `v0.0.6b-official.md` if Sub-Phase **6b** is official). Sibling alternatives in the same exclusive group keep their names **without** `-official`. There must be **at most one** `-official` file per exclusive group. On the canonical path, implement the `-official` guide and **skip** the other alternatives in that group; filename sort order still lists every file in the folder, but exclusive siblings other than `-official` are reference-only unless you deliberately switch approach.

**Cross-references:** Phase and sub-phase bodies avoid citing other guides by **filename** (for example `v0.0.4.md`). Prefer *previous* / *later* and **functional descriptions** (for example "the phase where Postgres was introduced", "the shared-utilities phase", "the official security sub-phase"). **Exceptions:** this README (navigation table and links); the **security phase index** (`v0.0.6-idx.md`, including its link back here); links to the **module** [README.md](../../README.md) for run instructions or JWT-only operational notes (for example default-user login credentials and BCrypt password storage in Sub-Phase **6b** / **6c**).

**Non-strict incremental snippets:** each phase shows only what it adds or changes. Placeholders mark omitted prior content - never a patch inside a method or config block.

| Kind | Unit | Placeholder | New or modified |
|------|------|-------------|-----------------|
| Java | field, method, constructor | `// ...` between members | show the **full** member |
| `application.yml` | top-level key or nested block (e.g. `app.security:`) | `# ...` between blocks | show the **full** block |
| `build.gradle` | line in `dependencies { }` | `// ...` inside the closure | show **full** new lines |
| SQL migrations | table section (banner + DDL for one table) | `-- ...` between sections | show the **full** section |

First introduction of a file in a phase: full snippet for that phase's scope only (no placeholder).

**Scaffold lifecycle:** The **scaffold phase** introduces placeholder config holders (`MY_CONST`, `myProperty`, demo `AppConfig`). The **shared-utilities phase** **replaces** the demo `AppConfig`. Whichever **security sub-phase** you pick **replaces** `AppConsts` and `AppProperties` with real security fields - remove scaffold members; do not merge old placeholders with new ones.

| Version | Phase / Sub-Phase | Topic |
|---------|-------------------|-------|
| [v0.0.0](v0.0.0.md) | Phase 0 | Executable scaffold (Java 8, Spring Boot 2.7); reachability check endpoints; config placeholders and demonstration `AppConfig` |
| [v0.0.1](v0.0.1.md) | Phase 1 | `Product` entity, in-memory repository, DTOs, mapper, basic CRUD (`GET`, `POST`, `DELETE`) |
| [v0.0.2](v0.0.2.md) | Phase 2 | `PUT /products/{id}` with replace semantics |
| [v0.0.3](v0.0.3.md) | Phase 3 | `PATCH /products/{id}` with partial update (`JsonNullable`) |
| [v0.0.4](v0.0.4.md) | Phase 4 | Postgres + JPA replacing the in-memory adapter; SQL migrations |
| [v0.0.5](v0.0.5.md) | Phase 5 | Shared utilities - `JsonUtil` extension (incl. preparatory `fromJsonStr` / `LIST_STRING` not used until later custom code); `AppConfig` replaces scaffold and wires `ObjectMapper` into `JsonUtil` |
| [v0.0.6-idx](v0.0.6-idx.md) | Phase 6 index | Security layer overview - choose one sub-phase below |
| [v0.0.6a](v0.0.6a.md) | Sub-Phase 6a | API key header auth; public-route rules (path regex + optional HTTP methods); filter and Spring Security wiring |
| [v0.0.6b](v0.0.6b.md) | Sub-Phase 6b | Basic JWT security layer using in-memory credentials via application properties (`default-users`; no database user lookup) |
| [v0.0.6c](v0.0.6c.md) | Sub-Phase 6c | Hybrid JWT security layer using in-memory credentials via application properties and database |

For how the repository was bootstrapped from scratch (Gradle, wrapper, layout), see [CREATION.md](../CREATION.md).
