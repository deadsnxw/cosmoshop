package org.example.cosmocats.controller;

import jakarta.validation.Valid;
import org.example.cosmocats.domain.Product;
import org.example.cosmocats.dto.ProductRequestDTO;
import org.example.cosmocats.dto.ProductResponseDTO;
import org.example.cosmocats.service.ProductService;
import org.example.cosmocats.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // GET: /api/products - Отримати всі продукти
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDTO> response = productMapper.toResponseDTOList(products);
        return ResponseEntity.ok(response);
    }

    // GET: /api/products/{id} - Отримати продукт за ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(productMapper.toResponseDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: /api/products - Створити новий продукт
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequest) {
        Product product = productMapper.toEntity(productRequest);
        Product createdProduct = productService.createProduct(product);
        ProductResponseDTO response = productMapper.toResponseDTO(createdProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT: /api/products/{id} - Оновити існуючий продукт
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDTO productRequest
    ) {
        Product updatedProduct = productMapper.toEntity(productRequest);
        return productService.updateProduct(id, updatedProduct)
                .map(product -> ResponseEntity.ok(productMapper.toResponseDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: /api/products/{id} - Видалити продукт за ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
