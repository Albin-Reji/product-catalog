package com.albin.productcatalog.exception;

public class ProducerNotFoundException extends RuntimeException {
    public ProducerNotFoundException(String message) {
        super(message);
    }
}
