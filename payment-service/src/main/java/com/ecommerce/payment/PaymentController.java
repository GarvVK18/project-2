package com.ecommerce.payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentRepository repository;
    public PaymentController(PaymentRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Payment> all() { return repository.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment create(@RequestBody CreatePaymentRequest request) {
        return repository.save(new Payment(request.orderId(), request.amount()));
    }

    @PatchMapping("/{id}/status")
    public Payment status(@PathVariable Long id, @RequestParam String value) {
        Payment payment = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        payment.setStatus(value.toUpperCase());
        return repository.save(payment);
    }

    public record CreatePaymentRequest(Long orderId, BigDecimal amount) {}
}
