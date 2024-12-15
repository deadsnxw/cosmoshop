package org.example.cosmocats.mapper;

import org.example.cosmocats.domain.Product;
import org.example.cosmocats.domain.Category;
import org.example.cosmocats.domain.Order;
import org.example.cosmocats.dto.ProductDto;
import org.example.cosmocats.dto.CategoryDto;
import org.example.cosmocats.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CosmoCatsMapper {

    // Product mapping
    ProductDto toProductDto(Product product);
    Product toProduct(ProductDto productDto);

    // Category mapping
    @Mapping(source = "products", target = "productIds", qualifiedByName = "productToId")
    CategoryDto toCategoryDto(Category category);
    Category toCategory(CategoryDto categoryDto);

    // Order mapping
    @Mapping(source = "products", target = "productIds", qualifiedByName = "productToId")
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);

    // List mappings
    List<ProductDto> toProductDtoList(List<Product> products);
    List<CategoryDto> toCategoryDtoList(List<Category> categories);
    List<OrderDto> toOrderDtoList(List<Order> orders);

    // Custom method to map Product to its ID
    @Named("productToId")
    default UUID productToId(Product product) {
        return product.getId();
    }
}
