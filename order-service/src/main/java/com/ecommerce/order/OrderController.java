package com.ecommerce.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository repository;
    public OrderController(OrderRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Order> all() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Order one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody CreateOrderRequest request) {
        return repository.save(new Order(request.customerId(), request.totalAmount()));
    }

    public record CreateOrderRequest(String customerId, BigDecimal totalAmount) {}
}
