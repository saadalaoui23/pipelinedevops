package com.trajets.exception;

public class HoraireNotFoundException extends RuntimeException {
    public HoraireNotFoundException(String message) {
        super(message);
    }
}