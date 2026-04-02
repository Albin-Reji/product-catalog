package com.albin.productcatalog.exception;

/**
 * Thrown when a product lookup (by ID or name) finds no matching record
 * in the database.
 */
public class ProductNotFound extends RuntimeException {
	public ProductNotFound(String message) {
		super(message);
	}
}
