# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

`tag-out-api` is a Spring Boot microservice owned by **GK Global Enterprises LLC**. It
currently exposes health endpoints and is the foundation for API endpoints added later.

- Java 21, Spring Boot 3.4.1, Maven.
- Dependencies: `spring-boot-starter-web`, `spring-boot-starter-actuator`, `spring-boot-starter-test`.
- No build tooling beyond Maven; no linter configured.

## Commands

```bash
mvn spring-boot:run                                  # run locally (port 8080)
mvn clean package                                    # build jar -> target/tag-out-api-0.0.1-SNAPSHOT.jar
java -jar target/tag-out-api-0.0.1-SNAPSHOT.jar      # run the built jar
mvn test                                             # run the full test suite

# Run a single test class:
mvn test -Dtest=HealthControllerTest

# Run on an alternate port (e.g. when 8080 is taken):
java -jar target/tag-out-api-0.0.1-SNAPSHOT.jar --server.port=8099
```

## Architecture

```
index/entry        controller            (future)
TagOutApiApplication -> HealthController  -> service / repository layers
```

- **`com.gk.tagout.TagOutApiApplication`** — `@SpringBootApplication` entry point; component
  scanning is rooted here, so all application code must live under `com.gk.tagout.*`.
- **`com.gk.tagout.controller`** — REST controllers. `HealthController` exposes `GET /api/health`,
  a lightweight app-level liveness check distinct from Actuator.
- Spring Boot Actuator provides `GET /actuator/health` — this is the endpoint for
  infrastructure probes (k8s liveness/readiness, load balancers).

## Package convention

Base package is `com.gk.tagout` — `gk` for GK Global Enterprises, `tagout` for the service.
Place new code in sub-packages by layer: `controller`, `service`, `repository`, `model`,
`config`. Do not introduce packages outside `com.gk.tagout` (they won't be component-scanned).

## Endpoints

| Method | Path               | Purpose                                          |
|--------|--------------------|--------------------------------------------------|
| GET    | `/api/health`      | App-level health: `{status, service, timestamp}` |
| GET    | `/actuator/health` | Actuator health for infra probes                 |

## Conventions

- Configuration lives in `src/main/resources/application.yml`, not `.properties`.
- Tests use JUnit 5 + Spring test slices. Controller tests use `@WebMvcTest` with `MockMvc`,
  one behaviour per `@Test`, mirroring the main package path under `src/test/java`.
- Comments explain *why* (e.g. why a second health endpoint exists), not *what* the code does.
- When adding an endpoint: add the controller method, a `@WebMvcTest` covering it, and a row
  in the endpoint table above (and in README.md).
