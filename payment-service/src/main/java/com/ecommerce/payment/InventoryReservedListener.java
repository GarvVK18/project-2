package com.ecommerce.payment;

import com.ecommerce.payment.events.InventoryReservedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InventoryReservedListener {

    private final PaymentRepository repository;
    private final PaymentEventPublisher publisher;
    private final ObjectMapper objectMapper;

    public InventoryReservedListener(
            PaymentRepository repository,
            PaymentEventPublisher publisher,
            ObjectMapper objectMapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "inventory.reserved", groupId = "payment-saga")
    public void handle(String payload) {
        try {
            InventoryReservedEvent event = objectMapper.readValue(payload, InventoryReservedEvent.class);
            Payment payment = repository.save(new Payment(event.orderId(), BigDecimal.valueOf(event.amount())));
            payment.setStatus("COMPLETED");
            payment = repository.save(payment);
            publisher.publish(payment);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to process inventory event", ex);
        }
    }
}
