# Delivery Tour Optimizer

Optimisation de tournées de livraison avec Spring Boot, pagination intégrée, historique et intégration IA (Ollama).

## Stack
- Java 17
- Spring Boot 3.2.x
- Spring Data JPA, Validation
- Liquibase
- H2 (dev), PostgreSQL (qa)
- Springdoc OpenAPI (Swagger UI)
- Spring AI (Ollama)

## Démarrage rapide
1) Cloner et builder
```
mvn -q -DskipTests package
```
2) Lancer (profil dev par défaut)
```
mvn spring-boot:run
```
3) Swagger UI
- http://localhost:8080/swagger-ui/index.html

## Profils & Config
- `application.yml`
  - `spring.profiles.active=${ACTIVE_PROFILE:dev}`
  - `spring.jpa.open-in-view=false` (OSIV fermé)
  - Liquibase activé, master changelog: `db/changelog/db.changelog-master.yaml`
- `application-dev.yml`
  - H2 en mémoire, SQL log en DEBUG
- `application-qa.yml`
  - PostgreSQL via env: `DB_URL`, `DB_USER`, `DB_PASS`

Variables utiles (QA)
- `DB_URL=jdbc:postgresql://localhost:5432/delivery_optimizer_qa`
- `DB_USER=database_name`
- `DB_PASS=pass_word`

## Liquibase
- Master: `src/main/resources/db/changelog/db.changelog-master.yaml`
- Inclut notamment:
  - `008-add-indexes.yaml`: index sur deliveries.customer_id et customers.name
  - `009-set-customerid-not-null.yaml`: contrainte NOT NULL sur deliveries.customer_id (avec rollback)

## OSIV (Open Session In View)
- Configuré à `false` (fermé) pour éviter les lazy loads hors service.
- Conséquence: mapper vers des DTO côté service et charger explicitement les associations nécessaires (fetch join / projections / `@EntityGraph`).

## Endpoints clés
- POST `/api/tours/optimize`: optimise une tournée et retourne une page de `DeliveryDTO` (pagination par défaut: page=0, size=20)

Exemple de réponse (page):
```
{
  "content": [ { "id": 1, "address": "...", "deliveryOrder": 1, ... } ],
  "pageable": { "pageNumber": 0, "pageSize": 20, ... },
  "totalElements": 5,
  "totalPages": 1,
  "number": 0,
  "size": 20,
  "numberOfElements": 5,
  "first": true,
  "last": true,
  "empty": false
}
```

Paramètres optionnels standard Spring Data (si fournis): `?page=0&size=10&sort=deliveryOrder,asc`

Autres endpoints de recherche:
- GET `/api/deliveries/search` (Page<DeliveryDTO>)
- GET `/api/customers/search` (Page<Customer>)

## Tests
- Lancer tous les tests
```
mvn -q test
```
- Tests d’intégration: MockMvc (ex: `IntegrationOptimizeTest`) vérifie `$.content[0].id`, `$.pageable.pageNumber`, `$.totalElements`.

## Postman / Swagger
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Exemple Postman (POST /api/tours/optimize):
```
{
  "date": "2025-11-17",
  "vehicleId": 1,
  "warehouseId": 1,
  "deliveryIds": [1,2,3,4,5],
  "optimizerType": "NEAREST_NEIGHBOR"
}
```
- Tests Postman (snippets):
```
pm.test("has pageable", () => pm.expect(pm.response.json().pageable.pageNumber).to.eql(0));
pm.test("has content", () => pm.expect(pm.response.json().content.length).to.be.above(0));
```

## Intégration IA 
- Profil par défaut: `optimizer.type=ai` (configurable via `OPTIMIZER_TYPE`).
- Spring AI avec Ollama (HTTP) pour réordonner les livraisons.
- Variables:
  - `spring.ai.ollama.base-url=http://localhost:11434`
  - `spring.ai.ollama.chat.options.model=llama3:8b` (dev) ou `${AI_MODEL:tinyllama}` (défaut)
- En cas d’échec IA: fallback sur l’ordre original ou autre stratégie.

## Structure
```
src/main/java/com/dto/delivery_tour_optimizer_AI/
├─ controller/
├─ service/
├─ repository/
├─ model/
├─ dto/
└─ mapper/
```
- Pour QA, s’assurer que la base est accessible et que Liquibase est exécuté au démarrage.

