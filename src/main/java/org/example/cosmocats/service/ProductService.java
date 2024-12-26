package org.example.cosmocats.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.cosmocats.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();
    private final WebClient webClient;
    private Validator validator;

    public ProductService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Ініціалізація локальних продуктів
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

    public void updateProduct(Product product) {
        Set<ConstraintViolation<Product>> violations = validator.validate(product, Product.UpdateGroup.class);
    }

    // Робота з локальними продуктами
    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(UUID id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID());
        Set<ConstraintViolation<Product>> violations = validator.validate(product, Product.CreateGroup.class);
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
        Optional<Product> product = getProductById(id);
        if (product.isPresent()) {
            products.removeIf(p -> p.getId().equals(id));
            return true; // Продукт був видалений
        }
        return false; // Продукт не існував
    }

    // метод для роботи з заглушкой
    public List<Product> fetchProductsFromExternalApi() {
        return webClient.get()
                .uri("/api/v1/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
    }
}
