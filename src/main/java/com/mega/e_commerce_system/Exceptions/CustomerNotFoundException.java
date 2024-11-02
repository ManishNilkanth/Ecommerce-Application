package com.mega.e_commerce_system.Exceptions;

public class CustomerNotFoundException extends RuntimeException{

    private String resourceName;

    private String fieldName;

    private Long fieldValue;

    public CustomerNotFoundException(String resourceName, String fieldName, Long fieldValue)
    {
        super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
