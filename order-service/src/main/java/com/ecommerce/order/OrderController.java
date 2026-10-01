package com.ecommerce.order;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderController(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        Order saved = orderRepository.save(order);
        eventPublisher.publish(saved);
        return saved;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}