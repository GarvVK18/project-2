package com.ecommerce.inventory;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String sku;
    @Column(nullable = false)
    private int quantity;

    protected InventoryItem() {}
    public InventoryItem(String sku, int quantity) { this.sku = sku; this.quantity = quantity; }
    public Long getId() { return id; }
    public String getSku() { return sku; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}