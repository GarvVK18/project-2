package com.ecommerce.order.events;

public record InventoryResultEvent(
        Long orderId,
        String status) {
}
