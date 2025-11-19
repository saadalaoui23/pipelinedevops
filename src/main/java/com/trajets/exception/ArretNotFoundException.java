package com.trajets.exception;

public class ArretNotFoundException extends RuntimeException {
    public ArretNotFoundException(String message) {
        super(message);
    }
}