# Project 2 — Week 1 & Week 2 Status

## Week 1
- Eureka Service Registry
- Spring Cloud Config Server
- API Gateway
- Order, Inventory and Payment services
- PostgreSQL persistence
- Basic REST CRUD endpoints
- Gateway JWT resource-server foundation
- Actuator health/info endpoints

## Week 2
- Kafka dependency and configuration
- Order-created event
- Inventory reservation consumer
- Inventory success/failure events
- Payment consumer and completion event
- Order saga state updates
- Resilience4j circuit breakers
- Gateway fallback endpoint
- Kafka Docker Compose service
- Week 2 test guide

## Event flow

`order.created` -> Inventory -> `inventory.reserved` -> Payment -> `payment.completed` -> Order COMPLETED

Failure path:

`order.created` -> Inventory -> `inventory.failed` -> Order CANCELLED

## Verification

The source implementation and configuration are committed to `main`. Runtime verification still requires running Docker/Kafka/PostgreSQL and the Spring services locally; this status file does not claim that those runtime checks have been executed in this environment.
