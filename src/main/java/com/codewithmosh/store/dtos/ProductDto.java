package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private byte categoryId;

//    @JsonProperty("price")
//    public String getFormattedPrice() {
//        return "$" + price.toPlainString();
//    }

}
