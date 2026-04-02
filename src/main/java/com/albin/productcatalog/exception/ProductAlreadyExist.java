package com.albin.productcatalog.exception;

public class ProductAlreadyExist extends RuntimeException {
	public ProductAlreadyExist(String message) {
		super(message);
	}
}
