# HR Management APIs

Spring Boot service for employees and leave requests. Postgres + Flyway; Swagger UI is bundled.

Employees support CRUD-style operations with soft delete. Leave can be created per employee, listed, fetched by id, and approved or rejected.

JSON uses snake_case (`application.yaml`).

## Run it

Postgres (matches default credentials in `application.yaml`):

```bash
docker compose up -d
```

```bash
./mvnw spring-boot:run
```

Base URL: `http://localhost:8080/api` (port 8080 unless you change `server.port`).

Flyway runs migrations from `src/main/resources/db/migration/` on startup.

Swagger: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)  
OpenAPI JSON: [http://localhost:8080/api/v3/api-docs](http://localhost:8080/api/v3/api-docs)

## `X-API-Version`

Calls to `/employees` and `/leaves` need:

```http
X-API-Version: 1
```

Otherwise those routes won’t match (404). Example:

```bash
curl -H "X-API-Version: 1" "http://localhost:8080/api/employees?page=0&size=20"
```

## Routes

Everything below is under `/api`.

**Employees**

- `POST /employees` — create  
- `GET /employees` — pageable list (`page`, `size`; size capped at 100, sorted by first name)  
- `GET /employees/{id}` — get  
- `PATCH /employees/{id}` — update  
- `DELETE /employees/{id}` — soft delete  
- `GET /employees/{id}/leave` — leave history  
- `POST /employees/{id}/leave` — new leave request  

**Leaves**

- `GET /leaves/{id}`  
- `PATCH /leaves/{id}/approve`  
- `PATCH /leaves/{id}/reject`  

## Config

See `src/main/resources/application.yaml` for datasource and context path (`/api`). JPA is `validate` only; schema comes from Flyway.

## Tests

```bash
./mvnw test
```
