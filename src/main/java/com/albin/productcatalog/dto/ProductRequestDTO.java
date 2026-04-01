package com.albin.productcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class ProductRequestDTO {

    @NotNull
    private String producerId;
    @NotBlank
    private String productName;
    @NotBlank
    private BigDecimal price;
    private String category;
    private Map<String, String> attributes = new HashMap<>(); // key → value
}
