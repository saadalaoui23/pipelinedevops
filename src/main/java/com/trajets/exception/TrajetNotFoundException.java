package com.trajets.exception;

public class TrajetNotFoundException extends RuntimeException {
    public TrajetNotFoundException(String message) {
        super(message);
    }
}