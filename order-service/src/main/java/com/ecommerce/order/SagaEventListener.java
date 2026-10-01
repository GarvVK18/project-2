package com.ecommerce.order;

import com.ecommerce.order.events.InventoryResultEvent;
import com.ecommerce.order.events.PaymentCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SagaEventListener {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public SagaEventListener(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "inventory.reserved", groupId = "order-saga")
    public void onInventoryReserved(String payload) {
        updateInventoryState(payload, "INVENTORY_RESERVED");
    }

    @KafkaListener(topics = "inventory.failed", groupId = "order-saga")
    public void onInventoryFailed(String payload) {
        updateInventoryState(payload, "CANCELLED");
    }

    @KafkaListener(topics = "payment.completed", groupId = "order-saga")
    public void onPaymentCompleted(String payload) {
        try {
            PaymentCompletedEvent event = objectMapper.readValue(payload, PaymentCompletedEvent.class);
            orderRepository.findById(event.orderId()).ifPresent(order -> {
                order.setStatus("COMPLETED");
                orderRepository.save(order);
            });
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to process payment event", ex);
        }
    }

    private void updateInventoryState(String payload, String status) {
        try {
            InventoryResultEvent event = objectMapper.readValue(payload, InventoryResultEvent.class);
            orderRepository.findById(event.orderId()).ifPresent(order -> {
                order.setStatus(status);
                orderRepository.save(order);
            });
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to process inventory event", ex);
        }
    }
}
