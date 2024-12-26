package org.example.cosmocats;

import org.example.cosmocats.domain.Product;
import org.example.cosmocats.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder().baseUrl("http://localhost:8081").build();  // використовуємо порт WireMock
    }

    @Test
    void fetchProductsFromExternalApi_shouldReturnListOfProducts() {
        List<Product> products = productService.fetchProductsFromExternalApi(); // юзаємо метод, що юзає ваєрмок

        assertThat(products).isNotNull();
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Galaxy Explorer");
        assertThat(products.get(1).getName()).isEqualTo("Cosmic Ray Detector");
    }
}