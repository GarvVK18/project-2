package com.ecommerce.order;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic orderCreatedTopic() {
        return new NewTopic("order.created", 3, (short) 1);
    }

    @Bean
    NewTopic inventoryReservedTopic() {
        return new NewTopic("inventory.reserved", 3, (short) 1);
    }

    @Bean
    NewTopic inventoryFailedTopic() {
        return new NewTopic("inventory.failed", 3, (short) 1);
    }

    @Bean
    NewTopic paymentCompletedTopic() {
        return new NewTopic("payment.completed", 3, (short) 1);
    }
}
