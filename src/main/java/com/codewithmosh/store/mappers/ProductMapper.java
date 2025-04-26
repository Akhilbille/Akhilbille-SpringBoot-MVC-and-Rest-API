package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CreateProductDto;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.dtos.RequestProductDto;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id",target="categoryId")
    ProductDto toDto(Product product);
    CreateProductDto toDto(RequestProductDto request);
    Product create(CreateProductDto dto);
    @Mapping(target = "id",ignore = true)
    void update(ProductDto productDto, @MappingTarget Product product);




}
