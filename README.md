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

Eureka provides service discovery and Config Server provides centralized configuration. Kafka handles asynchronous saga events.

## Week 2 scope
- Kafka event-driven communication between Order, Inventory and Payment services
- Saga-style order workflow: order created -> inventory reserved -> payment completed
- Inventory failure event updates the order to CANCELLED
- Resilience4j circuit breakers and gateway fallbacks

Start local Kafka:
```bash
docker compose up -d kafka
```

The services use `KAFKA_BOOTSTRAP_SERVERS` with `localhost:9092` as the local default.

## Week 3 — Containers and CI
- Multi-stage Java 17 Dockerfiles are in the root and `docker/`
- GitHub Actions builds the Gradle multi-project and runs tests
- Build individual service images from the repository root, e.g.:
```bash
docker build -f docker/order-service.Dockerfile -t project2-order-service:local .
``

## Week 4 — Kubernetes
Kubernetes manifests are in `k8s/`. Read [Week 3 & Week 4 deployment guide](docs/WEEK3_WEEK4_DEPLOYMENT.md) before deploying. The manifests are development templates and need image publishing, valid secrets, service-DNS configuration, and a reachable IAM issuer before the complete stack can run in a cluster.

## Verification status
Source files and deployment templates are committed. Do not treat builds, tests, Docker images, or Kubernetes deployment as verified until the GitHub Actions run and local/cluster checks have completed successfully.
