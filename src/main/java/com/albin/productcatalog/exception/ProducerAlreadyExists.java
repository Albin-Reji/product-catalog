package com.albin.productcatalog.exception;

public class ProducerAlreadyExists extends RuntimeException {
    public ProducerAlreadyExists(String message) {
        super(message);
    }
}
