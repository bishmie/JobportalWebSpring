package com.example.careerfyJobPortal.execptionHandling;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}