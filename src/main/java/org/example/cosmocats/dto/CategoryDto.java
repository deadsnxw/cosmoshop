package org.example.cosmocats.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class CategoryDto {
    private UUID id;

    @NotBlank(message = "Category name cannot be blank.")
    @Size(max = 50, message = "Category name cannot exceed 50 characters.")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    private List<UUID> productIds;

    public List<UUID> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<UUID> productIds) {
        this.productIds = productIds;
    }
}
