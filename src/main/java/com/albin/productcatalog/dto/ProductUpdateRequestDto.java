package com.albin.productcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Data Transfer Object for <strong>partially updating</strong> an existing product.
 * <p>
 * All fields are optional. Only non-null fields will be applied during
 * the patch operation. Setting {@code attributes} replaces the entire
 * attribute set for the product.
 * </p>
 */
@Data
@Builder
public class ProductUpdateRequestDto {

	/** New product name (ignored if null or blank). */
	private String productName;

	/** New price (ignored if null). */
	private BigDecimal price;

	/** New category (ignored if null). */
	private String category;

	/**
	 * Replacement attribute map. When provided, existing attributes are
	 * cleared and replaced with these key-value pairs.
	 */
	private Map<String, String> attributes;
}
