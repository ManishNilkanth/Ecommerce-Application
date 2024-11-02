package com.mega.e_commerce_system.Modules.customer.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    // Customer Permissions
    CUSTOMER_VIEW("customer:view"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete"),

    // Product Permissions
    PRODUCT_VIEW("product:view"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    // Order Permissions
    ORDER_VIEW("order:view"),
    ORDER_CREATE("order:create"),
    ORDER_UPDATE("order:update"),
    ORDER_DELETE("order:delete"),

    ORDER_LINE_VIEW("order-line:view"),

    // Payment Permissions
    PAYMENT_VIEW("payment:view"),
    PAYMENT_CREATE("payment:create"),


    // Customer Address Permissions
    ADDRESS_VIEW("address:view"),
    ADDRESS_CREATE("address:create"),
    ADDRESS_UPDATE("address:update"),
    ADDRESS_DELETE("address:delete");

    @Getter
    private final String permission;


}
