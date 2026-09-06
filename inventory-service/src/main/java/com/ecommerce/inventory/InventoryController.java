package com.ecommerce.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryRepository repository;
    public InventoryController(InventoryRepository repository) { this.repository = repository; }

    @GetMapping
    public List<InventoryItem> all() { return repository.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryItem create(@RequestBody CreateInventoryRequest request) {
        return repository.save(new InventoryItem(request.sku(), request.quantity()));
    }

    @PatchMapping("/{sku}")
    public InventoryItem update(@PathVariable String sku, @RequestParam int quantity) {
        InventoryItem item = repository.findBySku(sku).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SKU not found"));
        item.setQuantity(quantity);
        return repository.save(item);
    }

    public record CreateInventoryRequest(String sku, int quantity) {}
}
