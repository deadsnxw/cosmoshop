package org.example.cosmocats.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;

    @NotBlank(message = "Product name cannot be blank.")
    @Size(max = 100, message = "Product name cannot exceed 100 characters.")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    private Double price;
}
