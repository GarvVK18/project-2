# Project 2 Status — Weeks 1–4

## Week 1 — Microservice foundation
- Eureka service registry and Spring Cloud Config Server
- API Gateway and Order, Inventory, Payment services
- PostgreSQL/JPA persistence and REST endpoints
- Gateway JWT resource-server foundation

## Week 2 — Event-driven workflow
- Kafka event configuration and topic declarations
- Order-created, inventory reservation/failure, and payment-completed events
- Saga state updates and inventory failure cancellation
- Resilience4j gateway circuit breakers and fallback endpoint

## Week 3 — Containers and CI
- Java 17 multi-stage Dockerfiles
- Service-specific Dockerfiles
- Docker build-context exclusions
- GitHub Actions Gradle build/test workflow

## Week 4 — Kubernetes and deployment documentation
- Namespace and service/deployment manifests
- Development PostgreSQL manifest with orders, inventory, and payments databases
- Readiness probes on application deployments
- Deployment and troubleshooting instructions

## Important verification caveats
- CI must run successfully on GitHub before calling the Gradle build verified.
- Docker images have not been built or published by these commits.
- Kubernetes manifests are development templates and still need environment-specific image names, IAM issuer, config-server URLs, secrets, and cluster validation.
- Kafka is a single-node development broker; production use requires secure, durable, highly available infrastructure.
- The gateway requires an available IAM/OIDC issuer for authenticated requests.
