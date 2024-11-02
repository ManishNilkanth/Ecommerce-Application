package com.mega.e_commerce_system.Exceptions;

public class InsufficientAmountException extends RuntimeException {

    private String message;

    public InsufficientAmountException(String message)
    {
        super(String.format(message));
        this.message = message;
    }
}
