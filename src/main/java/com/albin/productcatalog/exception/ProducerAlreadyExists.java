package com.albin.productcatalog.exception;

/**
 * Thrown when an attempt is made to create a producer whose name
 * already exists in the database.
 */
public class ProducerAlreadyExists extends RuntimeException {
    public ProducerAlreadyExists(String message) {
        super(message);
    }
}
