package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.CreateProductDto;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.dtos.RequestProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(name ="categoryId", required = false) Byte categoryId
    ){
        List<Product> products;
        if(categoryId != null)
            products = productRepository.findByCategoryId(categoryId);
        else{
            products = productRepository.findAllByCategory();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id ){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productMapper.toDto(product));

    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody RequestProductDto request){
        CreateProductDto toDto = productMapper.toDto(request);
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if(category == null)
            throw new IllegalArgumentException("Category does not exist");
        toDto.setCategory(category);
        Product product = productMapper.create(toDto);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto request){
        var product  = productRepository.findById(id).orElse(null);
        if(product == null)
            return ResponseEntity.notFound().build();
        productMapper.update(request,product);
        productRepository.save(product);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "id") Long id  ){
        var product=  productRepository.findById(id).orElse(null);
        if(product == null)
            return ResponseEntity.noContent().build();
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();


    }



}
