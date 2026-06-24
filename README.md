# tag-out-api

A Spring Boot microservice owned by **GK Global Enterprises LLC**. Currently exposes
health endpoints; API endpoints will be added as the service grows.

## Tech stack

- Java 21
- Spring Boot 3.4.1 (Spring Web + Spring Boot Actuator)
- Maven
- JUnit 5 / Spring MockMvc for tests

## Project layout

```
tag-out-api/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/gk/tagout/
    │   │   ├── TagOutApiApplication.java        # application entry point
    │   │   └── controller/HealthController.java # custom /api/health
    │   └── resources/application.yml            # app name, port, actuator config
    └── test/
        └── java/com/gk/tagout/controller/
            └── HealthControllerTest.java
```

The base package is `com.gk.tagout` — `gk` for the company, `tagout` for the service.
New API code belongs under this package (e.g. `com.gk.tagout.controller`,
`com.gk.tagout.service`).

## Endpoints

| Method | Path               | Description                                              |
|--------|--------------------|----------------------------------------------------------|
| GET    | `/api/health`      | Lightweight app-level health check (status, service, ts) |
| GET    | `/actuator/health` | Spring Boot Actuator health — use for k8s/LB probes      |

Example:

```bash
curl http://localhost:8080/api/health
# {"status":"UP","service":"tag-out-api","timestamp":"..."}
```

## Running

```bash
mvn spring-boot:run                       # run locally on port 8080
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8099   # alternate port
```

Or build and run the jar:

```bash
mvn clean package
java -jar target/tag-out-api-0.0.1-SNAPSHOT.jar
```

## Testing

```bash
mvn test
```

## Configuration

Settings live in `src/main/resources/application.yml`:

- `server.port` — HTTP port (default `8080`)
- `management.endpoints.web.exposure.include` — exposed actuator endpoints (`health`, `info`)
- `management.endpoint.health.show-details` — health detail verbosity (`always`)
