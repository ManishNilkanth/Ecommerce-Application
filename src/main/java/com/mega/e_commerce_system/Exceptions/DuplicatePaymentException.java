package com.mega.e_commerce_system.Exceptions;

public class DuplicatePaymentException extends RuntimeException {

    private String message;
    public  DuplicatePaymentException(String message) {
        super(message);
        this.message = message;
    }
}
