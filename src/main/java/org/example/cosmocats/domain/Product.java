package org.example.cosmocats.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.cosmocats.shared.validation.CosmicWordCheck;

import java.util.UUID;

public class Product {
    public interface CreateGroup {}
    public interface UpdateGroup {}

    @NotNull(groups = {CreateGroup.class, UpdateGroup.class})
    private UUID id;

    @CosmicWordCheck(groups = {CreateGroup.class, UpdateGroup.class})
    @NotBlank(message = "Product name cannot be blank.", groups = {CreateGroup.class, UpdateGroup.class})
    @Size(max = 100, message = "Product name cannot exceed 100 characters.", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters.", groups = {CreateGroup.class, UpdateGroup.class})
    private String description;

    @NotNull(message = "Price is required.", groups = {CreateGroup.class})
    @Min(value = 0, message = "Price must be greater than or equal to 0.", groups = {CreateGroup.class, UpdateGroup.class})
    private Double price;

    // залишив тут геттери та сеттери, бо виникають проблеми з маппером через ломбок
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}