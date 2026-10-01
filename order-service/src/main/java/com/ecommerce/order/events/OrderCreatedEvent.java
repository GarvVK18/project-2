package com.ecommerce.order.events;

public record OrderCreatedEvent(
        Long orderId,
        String productName,
        Integer quantity,
        Double price) {
}
