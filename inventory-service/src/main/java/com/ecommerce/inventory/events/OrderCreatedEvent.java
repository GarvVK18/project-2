package com.ecommerce.inventory.events;

public record OrderCreatedEvent(
        Long orderId,
        String productName,
        Integer quantity,
        Double price) {
}
