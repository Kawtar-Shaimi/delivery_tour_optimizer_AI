# Delivery Tour Optimizer

## Overview
Spring Boot 3.2.6, Java 17 application to optimize delivery tours with classic algorithms and an AI-based optimizer (Ollama). Uses Liquibase for DB migrations, Spring Data JPA, and Springdoc OpenAPI for docs.

## Requirements
- Java 17+
- Maven 3.8+
- Optional: PostgreSQL (QA)
- Optional: Ollama running locally for AI optimizer

## Profiles
- dev (default): H2 in-memory DB, Liquibase enabled.
- qa: PostgreSQL via environment variables.

Activate profile:
```
-Dspring.profiles.active=dev
```

## Configuration
application.yml (common):
- spring.jpa.open-in-view=false
- Liquibase change-log: `classpath:db/changelog/db.changelog-master.yaml`
- Springdoc OpenAPI UI: `/swagger-ui.html`
- Optimizer type: `optimizer.type` (default `ai`)

application-dev.yml:
- H2 in-memory DB, H2 console enabled

application-qa.yml:
- PostgreSQL
- Vars: `DB_URL`, `DB_USER`, `DB_PASS`, `DB_DRIVER`

## Liquibase
Changelogs included in `db.changelog-master.yaml`:
- 001-004: base schema (warehouse, vehicle, tour, delivery)
- 005: customer
- 006: delivery history
- 007: add `deliveries.customer_id` + FK
- 008: indexes (deliveries.customer_id, customers.name)
- 009: NOT NULL on `deliveries.customer_id` with rollback

Run automatically at app startup.

## API
Swagger UI: http://localhost:8080/swagger-ui.html

- POST `/api/tours/optimize`
  - Body: `TourRequestDTO` with date, vehicleId, warehouseId, deliveryIds, optimizerType
  - Returns: `List<DeliveryDTO>`

- GET `/api/deliveries/search`
  - Params: `name`, `startDate`, `endDate`, `minDelay`, `page`, `size`
  - Returns: `Page<DeliveryDTO>`

- GET `/api/customers/search`
  - Params: `name`, `timeSlot`, `page`, `size`
  - Returns: `Page<Customer>`

## Optimizers
- NEAREST_NEIGHBOR (deterministic, fast)
- CLARKE_WRIGHT (if implemented in your codebase)
- AI (Ollama) when `optimizer.type=ai` and server is reachable

Switch via request (`optimizerType`), or property `optimizer.type` for AI bean activation.

## AI (Ollama)
- Configure in `application.yml`:
  - `spring.ai.ollama.base-url` (default `http://localhost:11434`)
  - `spring.ai.ollama.chat.options.model` (e.g., `llama3:8b` or `tinyllama`)
- `AIOptimizer` calls Ollama HTTP API and reorders deliveries from AI output.

## Run
```
mvn spring-boot:run
```

## Tests
- Unit tests for services
- Integration test: `IntegrationOptimizeTest` hitting `/api/tours/optimize` with `NEAREST_NEIGHBOR`

## Notes
- DTOs are returned from controllers to avoid LazyInitialization issues.
- New search endpoints are paginated and documented in Swagger.
