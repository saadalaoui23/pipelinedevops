package com.trajets.exception;

public class LigneNotFoundException extends RuntimeException {
    public LigneNotFoundException(String message) {
        super(message);
    }
}