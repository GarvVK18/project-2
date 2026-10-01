# Distributed E-Commerce Microservices (Event-Driven)

Project 2 for the Java Full Stack internship.

## Week 1 scope
- Service Registry with Eureka
- Centralized Config Server
- API Gateway
- Order Service with PostgreSQL
- Inventory Service with PostgreSQL
- Payment Service with PostgreSQL
- Basic REST APIs
- JWT-ready gateway security foundation

## Services
| Service | Port |
|---|---:|
| API Gateway | 8080 |
| Order Service | 8081 |
| Inventory Service | 8082 |
| Payment Service | 8083 |
| Config Server | 8888 |
| Eureka Server | 8761 |

## Architecture
Client -> API Gateway -> Order / Inventory / Payment services

Eureka provides service discovery and Config Server provides centralized configuration. Kafka, Saga, Resilience4j and Kubernetes is planned for later weeks.

## Run order
1. Config Server
2. Eureka Server
3. Order, Inventory and Payment services
4. API Gateway

> Week 1 intentionally keeps the services small and focused on the foundation. Secrets and database credentials should be supplied through environment variables in local/production environments.

## Week 2 scope
- Kafka event-driven communication between Order, Inventory and Payment services
- Saga-style order workflow: order created -> inventory reserved -> payment completed
- Inventory failure event updates the order to CANCELLED
- Resilience4j circuit breakers and gateway fallbacks
- Kafka can be started locally with Docker Compose

### Week 2 event flow
```
Order Service
   |
   | order.created
   v
Inventory Service
   |
   | inventory.reserved
   v
Payment Service
   |
   | payment.completed
   v
Order Service -> COMPLETED
```

If inventory cannot reserve the requested stock:
```
Order -> order.created -> Inventory -> inventory.failed -> Order -> CANCELLED
```

### Start Kafka for Week 2
```bash
docker compose up -d kafka
```

The services use `KAFKA_BOOTSTRAP_SERVERS` with `localhost:9092` as the local default.
