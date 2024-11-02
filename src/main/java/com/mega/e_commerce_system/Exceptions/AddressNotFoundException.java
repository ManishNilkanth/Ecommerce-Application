package com.mega.e_commerce_system.Exceptions;

public class AddressNotFoundException extends RuntimeException {


        private String resourceName;

        private String fieldName;

        private Long fieldValue;

        public AddressNotFoundException(String resourceName, String fieldName, Long fieldValue)
        {
            super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));

            this.fieldName = fieldName;
            this.resourceName = resourceName;
            this.fieldValue = fieldValue;
        }


}
