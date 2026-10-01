package com.ecommerce.payment;

import com.ecommerce.payment.events.PaymentCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PaymentEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Payment payment) {
        try {
            String payload = objectMapper.writeValueAsString(
                    new PaymentCompletedEvent(payment.getOrderId(), payment.getId(), payment.getStatus()));
            kafkaTemplate.send("payment.completed", String.valueOf(payment.getOrderId()), payload);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to publish payment event", ex);
        }
    }
}
