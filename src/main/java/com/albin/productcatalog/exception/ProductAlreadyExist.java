package com.albin.productcatalog.exception;

/**
 * Thrown when an attempt is made to create a product whose name
 * already exists in the database.
 */
public class ProductAlreadyExist extends RuntimeException {
	public ProductAlreadyExist(String message) {
		super(message);
	}
}
