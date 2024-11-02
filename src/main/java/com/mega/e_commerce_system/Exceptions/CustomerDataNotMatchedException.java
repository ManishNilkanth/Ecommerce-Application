package com.mega.e_commerce_system.Exceptions;

public class CustomerDataNotMatchedException extends RuntimeException {
    private String message;
    public CustomerDataNotMatchedException(String message) {
        super(message);
        this.message = message;
    }
}
