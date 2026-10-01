package com.ecommerce.payment.events;

public record InventoryReservedEvent(
        Long orderId,
        String status,
        Double amount) {
}
