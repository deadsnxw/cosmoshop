package org.example.cosmocats.service;

import org.example.cosmocats.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        Product product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Galaxy Explorer");
        product1.setDescription("Explore the galaxy with ease.");
        product1.setPrice(199.99);

        Product product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setName("Cosmic Ray Detector");
        product2.setDescription("Detect cosmic rays with precision.");
        product2.setPrice(299.99);

        products.add(product1);
        products.add(product2);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(UUID id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID());
        products.add(product);
        return product;
    }

    public Optional<Product> updateProduct(UUID id, Product updatedProduct) {
        return getProductById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            return existingProduct;
        });
    }

    public boolean deleteProduct(UUID id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}
