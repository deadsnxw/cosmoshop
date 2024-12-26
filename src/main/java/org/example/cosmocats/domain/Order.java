package org.example.cosmocats.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Order {
    private UUID id;

    @NotNull(message = "Order date cannot be null.")
    private LocalDateTime orderDate;

    @NotNull(message = "Order status is required.")
    private OrderStatus status;

    private List<Product> products;

    // Enum for order status
    public enum OrderStatus {
        PENDING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
