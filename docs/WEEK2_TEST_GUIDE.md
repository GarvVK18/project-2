# Week 2 Test Guide

## 1. Start Kafka
```bash
docker compose up -d kafka
```

## 2. Start services
Start Config Server, Eureka, Order, Inventory, Payment and API Gateway in the documented order.

## 3. Prepare inventory
Create an inventory item whose SKU matches the order product name.

## 4. Create an order
POST `/orders` through the gateway.

Example:
```json
{
  "productName": "SKU-100",
  "quantity": 1,
  "price": 499.0
}
```

The order service publishes `order.created`.

## 5. Observe the saga
Inventory consumes `order.created` and publishes `inventory.reserved` or `inventory.failed`.
Payment consumes `inventory.reserved` and publishes `payment.completed`.
Order consumes the result and changes the order status to `COMPLETED` or `CANCELLED`.

## 6. Circuit breaker
Stop one downstream service and call its gateway endpoint repeatedly. The gateway should eventually open the Resilience4j circuit and return the fallback response.

> Runtime verification is still required on a machine with Docker, Kafka and PostgreSQL. This repository contains the Week 2 implementation and repeatable test steps; passing runtime tests should be recorded after execution.
