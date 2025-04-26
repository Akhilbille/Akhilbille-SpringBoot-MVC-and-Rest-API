package com.codewithmosh.store.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private byte categoryId;
}
