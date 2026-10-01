package com.ecommerce.inventory;

import com.ecommerce.inventory.events.InventoryResultEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public InventoryEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(String topic, Long orderId, String status, Double amount) {
        try {
            String payload = objectMapper.writeValueAsString(new InventoryResultEvent(orderId, status, amount));
            kafkaTemplate.send(topic, String.valueOf(orderId), payload);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to publish inventory event", ex);
        }
    }
}
