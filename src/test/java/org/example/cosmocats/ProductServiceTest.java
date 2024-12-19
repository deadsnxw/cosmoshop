package org.example.cosmocats;

import org.example.cosmocats.domain.Product;
import org.example.cosmocats.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(WebClient.builder());
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        Product existingProduct = productService.getAllProducts().get(0);
        Optional<Product> product = productService.getProductById(existingProduct.getId());
        assertTrue(product.isPresent());
        assertEquals(existingProduct.getId(), product.get().getId());
    }

    @Test
    void getProductById_ShouldReturnEmpty_WhenProductDoesNotExist() {
        Optional<Product> product = productService.getProductById(UUID.randomUUID());
        assertFalse(product.isPresent());
    }

    @Test
    void createProduct_ShouldAddProductToList() {
        Product newProduct = new Product();
        newProduct.setName("Starship Engine");
        newProduct.setDescription("A powerful engine for intergalactic travel.");
        newProduct.setPrice(499.99);

        Product createdProduct = productService.createProduct(newProduct);

        assertNotNull(createdProduct.getId());
        assertEquals(3, productService.getAllProducts().size());
        assertEquals("Starship Engine", createdProduct.getName());
    }

    @Test
    void updateProduct_ShouldUpdateExistingProduct_WhenProductExists() {
        Product existingProduct = productService.getAllProducts().get(0);
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(599.99);

        Optional<Product> result = productService.updateProduct(existingProduct.getId(), updatedProduct);

        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        assertEquals("Updated Description", result.get().getDescription());
        assertEquals(599.99, result.get().getPrice());
    }

    @Test
    void updateProduct_ShouldReturnEmpty_WhenProductDoesNotExist() {
        Product updatedProduct = new Product();
        updatedProduct.setName("Non-existent Product");
        updatedProduct.setDescription("Does not exist");
        updatedProduct.setPrice(199.99);

        Optional<Product> result = productService.updateProduct(UUID.randomUUID(), updatedProduct);

        assertFalse(result.isPresent());
    }

    @Test
    void deleteProduct_ShouldRemoveProduct_WhenProductExists() {
        Product existingProduct = productService.getAllProducts().get(0);

        boolean result = productService.deleteProduct(existingProduct.getId());

        assertTrue(result);
        assertEquals(1, productService.getAllProducts().size());
    }

    @Test
    void deleteProduct_ShouldReturnFalse_WhenProductDoesNotExist() {
        boolean result = productService.deleteProduct(UUID.randomUUID());

        assertFalse(result);
        assertEquals(2, productService.getAllProducts().size());
    }
}
