package org.example.cosmocats;

import org.example.cosmocats.controller.ProductController;
import org.example.cosmocats.domain.Product;
import org.example.cosmocats.dto.ProductRequestDTO;
import org.example.cosmocats.dto.ProductResponseDTO;
import org.example.cosmocats.mapper.ProductMapper;
import org.example.cosmocats.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private ProductRequestDTO productRequestDTO;
    private ProductResponseDTO productResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Galaxy Explorer");
        product.setDescription("Explore the galaxy with ease.");
        product.setPrice(199.99);

        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Galaxy Explorer");
        productRequestDTO.setDescription("Explore the galaxy with ease.");
        productRequestDTO.setPrice(199.99);

        productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product));
        when(productMapper.toResponseDTOList(any())).thenReturn(Arrays.asList(productResponseDTO));

        ResponseEntity<List<ProductResponseDTO>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Galaxy Explorer", response.getBody().get(0).getName());
    }

    @Test
    void testGetProductById_Found() {
        when(productService.getProductById(any(UUID.class))).thenReturn(Optional.of(product));
        when(productMapper.toResponseDTO(any(Product.class))).thenReturn(productResponseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.getProductById(product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Galaxy Explorer", response.getBody().getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById(any(UUID.class))).thenReturn(Optional.empty());

        ResponseEntity<ProductResponseDTO> response = productController.getProductById(UUID.randomUUID());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateProduct_Found() {
        when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(Optional.of(product));
        when(productMapper.toEntity(any(ProductRequestDTO.class))).thenReturn(product);
        when(productMapper.toResponseDTO(any(Product.class))).thenReturn(productResponseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.updateProduct(product.getId(), productRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Galaxy Explorer", response.getBody().getName());
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(Optional.empty());

        ResponseEntity<ProductResponseDTO> response = productController.updateProduct(UUID.randomUUID(), productRequestDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteProduct_Success() {
        when(productService.deleteProduct(any(UUID.class))).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct(product.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productService.deleteProduct(any(UUID.class))).thenReturn(false);

        ResponseEntity<Void> response = productController.deleteProduct(UUID.randomUUID());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}