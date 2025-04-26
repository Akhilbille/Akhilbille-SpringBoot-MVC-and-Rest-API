package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Category category;

}
