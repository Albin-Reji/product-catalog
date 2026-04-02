package com.albin.productcatalog.exception;

/**
 * Thrown when a producer lookup (by ID or name) finds no matching record
 * in the database.
 */
public class ProducerNotFoundException extends RuntimeException {
    public ProducerNotFoundException(String message) {
        super(message);
    }
}
