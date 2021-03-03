package com.project.ece651.webapp.exceptions;

public class ApartmentNotFoundException extends RuntimeException {
    public ApartmentNotFoundException(String message) {
        super(message);
    }
    public ApartmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
