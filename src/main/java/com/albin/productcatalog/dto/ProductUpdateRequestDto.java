package com.albin.productcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class ProductUpdateRequestDto {

	private String productName;
	private BigDecimal price;
	private String category;
	private Map<String, String> attributes;
}
