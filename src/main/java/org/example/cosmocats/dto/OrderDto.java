package org.example.cosmocats.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;

    @NotNull(message = "Order date cannot be null.")
    private LocalDateTime orderDate;

    @NotNull(message = "Order status is required.")
    private String status;

    private List<UUID> productIds;

    public List<UUID> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<UUID> productIds) {
        this.productIds = productIds;
    }
}
