package com.albin.productcatalog.controller;

import com.albin.productcatalog.dto.ProductRequestDTO;
import com.albin.productcatalog.dto.ProductResponseDTO;
import com.albin.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProduct(page, size));
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductResponseDTO> getProductByName(
            @PathVariable("productName")String productName
    ){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByName(productName));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductById(
            @PathVariable("productId")String productId
    ){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }

}
