package org.example.cosmocats.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class Category {
    private UUID id;

    @NotBlank(message = "Category name cannot be blank.")
    @Size(max = 50, message = "Category name cannot exceed 50 characters.")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) { // залишив геттери і сеттери для products через те, що
        this.products = products; // ломбок не може створити їх корректно
    }
}
