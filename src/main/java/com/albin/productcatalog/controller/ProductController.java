package com.albin.productcatalog.controller;

import com.albin.productcatalog.dto.ProductRequestDTO;
import com.albin.productcatalog.dto.ProductResponseDTO;
import com.albin.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

}
