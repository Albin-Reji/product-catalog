package com.albin.productcatalog.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ProductResponseDTO {
    private String productId;
    private String productName;
    private BigDecimal price;
    private String category;
    private String producerName;
    private String producerId;
    private Map<String, String> attributes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
