package com.grandstrand.dao;

/**
 * Unchecked exception wrapping SQLException.
 * Keeps JDBC details from leaking into the service layer.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
