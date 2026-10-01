package com.ecommerce.inventory.events;

public record InventoryResultEvent(
        Long orderId,
        String status) {
}
