package com.albin.productcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object for <strong>creating</strong> a new product.
 * <p>
 * The caller must supply the {@code producerId} of an existing producer
 * so the new product can be linked to its manufacturer.
 * </p>
 */
@Data
public class ProductRequestDTO {

    /** UUID of the existing producer to associate this product with. */
    @NotNull
    private String producerId;

    /** Unique display name for the product. */
    @NotBlank
    private String productName;

    /** Retail price – must not be null. */
    @NotBlank
    private BigDecimal price;

    /** Optional product category (e.g. "Electronics"). */
    private String category;

    /**
     * Optional dynamic attributes as key-value pairs.
     * Example: {@code {"color": "red", "weight": "1.5kg"}}
     */
    private Map<String, String> attributes = new HashMap<>(); // key → value
}
