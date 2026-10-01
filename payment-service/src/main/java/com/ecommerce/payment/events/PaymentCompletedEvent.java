package com.ecommerce.payment.events;

public record PaymentCompletedEvent(
        Long orderId,
        Long paymentId,
        String status) {
}
