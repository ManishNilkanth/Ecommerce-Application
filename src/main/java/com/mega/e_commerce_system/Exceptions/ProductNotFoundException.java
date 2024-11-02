package com.mega.e_commerce_system.Exceptions;

public class ProductNotFoundException extends RuntimeException{

    private String resourceName;

    private String fieldName;

    private Integer fieldValue;

    public ProductNotFoundException(String resourceName, String fieldName, Integer fieldValue)
    {
        super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
