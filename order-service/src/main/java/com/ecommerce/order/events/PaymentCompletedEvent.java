package com.ecommerce.order.events;

public record PaymentCompletedEvent(
        Long orderId,
        Long paymentId,
        String status) {
}
