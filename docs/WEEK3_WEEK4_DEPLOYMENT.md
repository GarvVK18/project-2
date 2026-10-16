# Week 3 & Week 4 — Build and Deployment

## Week 3: Containerization and CI

- Root multi-stage Docker build and service-specific Dockerfiles are in the repository.
- GitHub Actions workflow `.github/workflows/ci.yml` runs Gradle build and tests with Java 17 on pushes and pull requests.
- Build context for all Dockerfiles is the repository root.

Build an image from the repository root, for example:

```bash
docker build -f docker/order-service.Dockerfile -t project2-order-service:local .
docker build -f docker/inventory-service.Dockerfile -t project2-inventory-service:local .
docker build -f docker/payment-service.Dockerfile -t project2-payment-service:local .
docker build -f docker/api-gateway.Dockerfile -t project2-api-gateway:local .
docker build -f docker/config-server.Dockerfile -t project2-config-server:local .
docker build -f docker/service-registry.Dockerfile -t project2-service-registry:local .
```

## Week 4: Kubernetes deployment templates

Manifest files:
- `k8s/namespace.yaml`
- `k8s/platform.yaml`
- `k8s/postgres-dev.yaml`

Apply in order:

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/postgres-dev.yaml
kubectl apply -f k8s/platform.yaml
kubectl -n ecommerce get pods,services
kubectl -n ecommerce rollout status deployment/api-gateway
```

Before applying in a real cluster:
1. Build and publish images to a registry, then change image names in `k8s/platform.yaml` to match.
2. Replace the development database password. Never commit production credentials.
3. The development PostgreSQL manifest initializes `orders`, `inventory`, and `payments` databases only on the first startup of a fresh database volume.
4. The gateway expects an OIDC issuer at `JWT_ISSUER_URI`; deploy the IAM server or configure the correct issuer.
5. Confirm Config Server's repository-backed config uses Kubernetes service DNS names, not `localhost`.
6. Kafka in this manifest is a single-node development broker, not a production Kafka deployment.

## Verification status

These files define build, CI, and deployment workflows. They do not prove that images have been built, CI has passed, or a Kubernetes cluster has successfully started the application. Run the commands above and review the GitHub Actions run before treating deployment as verified.
