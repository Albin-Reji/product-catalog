package com.albin.productcatalog.controller;

import com.albin.productcatalog.dto.ProductRequestDTO;
import com.albin.productcatalog.dto.ProductResponseDTO;
import com.albin.productcatalog.dto.ProductUpdateRequestDto;
import com.albin.productcatalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for <strong>Product</strong> CRUD operations.
 * <p>
 * Base path: {@code /api/v1/products}
 * </p>
 * Supports creating, reading (single + paginated), updating (partial),
 * and deleting products.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a new product and associates it with an existing producer.
     *
     * @param product the product details (including producerId and optional attributes)
     * @return the persisted product with HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    /**
     * Returns a paginated list of all products.
     *
     * @param page zero-based page index (default 0)
     * @param size number of items per page (default 10)
     * @return a {@link Page} of products with HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProduct(page, size));
    }

    /**
     * Retrieves a single product by its unique name.
     *
     * @param productName the product name to look up
     * @return the matching product with HTTP 200 (OK)
     */
    @GetMapping("/{productName}")
    public ResponseEntity<ProductResponseDTO> getProductByName(
            @PathVariable("productName")String productName
    ){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByName(productName));
    }

    /**
     * Partially updates a product's fields (name, price, category, attributes).
     * <p>
     * Only non-null fields in the request body will be applied.
     * </p>
     *
     * @param productId              UUID of the product to update
     * @param productUpdateRequestDto partial update payload
     * @return the updated product with HTTP 200 (OK)
     */
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable("productId") String productId,
            @RequestBody ProductUpdateRequestDto productUpdateRequestDto
    ){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId, productUpdateRequestDto));
    }

    /**
     * Deletes a product by its UUID.
     *
     * @param productId UUID of the product to delete
     * @return confirmation message with HTTP 200 (OK)
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductById(
            @PathVariable("productId")String productId
    ){
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }

}
