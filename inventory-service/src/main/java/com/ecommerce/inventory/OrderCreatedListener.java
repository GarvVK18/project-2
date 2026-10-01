package com.ecommerce.inventory;

import com.ecommerce.inventory.events.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final InventoryRepository repository;
    private final InventoryEventPublisher publisher;
    private final ObjectMapper objectMapper;

    public OrderCreatedListener(
            InventoryRepository repository,
            InventoryEventPublisher publisher,
            ObjectMapper objectMapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order.created", groupId = "inventory-saga")
    public void handle(String payload) {
        try {
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            InventoryItem item = repository.findBySku(event.productName()).orElse(null);

            if (item == null || item.getQuantity() < event.quantity()) {
                publisher.publish("inventory.failed", event.orderId(), "INSUFFICIENT_STOCK");
                return;
            }

            item.setQuantity(item.getQuantity() - event.quantity());
            repository.save(item);
            publisher.publish("inventory.reserved", event.orderId(), "RESERVED");
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to process order event", ex);
        }
    }
}
