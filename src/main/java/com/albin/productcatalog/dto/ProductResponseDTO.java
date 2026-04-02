package com.albin.productcatalog.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Data Transfer Object returned to the client after a product operation.
 * <p>
 * Flattens the {@code Product} entity graph into a single JSON-friendly
 * object, including the producer's name and ID instead of the full
 * producer entity.
 * </p>
 */
@Data
@Builder
public class ProductResponseDTO {

    /** Server-generated UUID of the product. */
    private String productId;

    /** Display name of the product. */
    private String productName;

    /** Retail price. */
    private BigDecimal price;

    /** Product category. */
    private String category;

    /** Name of the producer who manufactures this product. */
    private String producerName;

    /** UUID of the associated producer. */
    private String producerId;

    /** Dynamic attributes flattened from {@link com.albin.productcatalog.model.ProductAttribute} entities. */
    private Map<String, String> attributes;

    /** Timestamp when the product was first created. */
    private LocalDateTime createdAt;

    /** Timestamp of the most recent product update. */
    private LocalDateTime updatedAt;
}
