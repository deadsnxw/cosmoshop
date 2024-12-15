package org.example.cosmocats.mapper;

import org.example.cosmocats.domain.Product;
import org.example.cosmocats.dto.ProductRequestDTO;
import org.example.cosmocats.dto.ProductResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toResponseDTO(Product product);

    List<ProductResponseDTO> toResponseDTOList(List<Product> products);
}
