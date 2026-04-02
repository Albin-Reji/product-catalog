package com.albin.productcatalog.exception;

public class ProductNotFound extends RuntimeException {
	public ProductNotFound(String message) {
		super(message);
	}
}
