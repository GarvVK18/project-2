package com.ecommerce.order;

import com.ecommerce.order.events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    public static final String ORDER_CREATED = "order.created";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    public OrderEventPublisher(
            KafkaTemplate<String, String> kafkaTemplate,
            com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Order order) {
        try {
            String payload = objectMapper.writeValueAsString(
                    new OrderCreatedEvent(order.getId(), order.getProductName(), order.getQuantity(), order.getPrice()));
            kafkaTemplate.send(ORDER_CREATED, String.valueOf(order.getId()), payload);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to publish order event", ex);
        }
    }
}
