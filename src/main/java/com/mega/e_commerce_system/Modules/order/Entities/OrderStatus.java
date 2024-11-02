package com.mega.e_commerce_system.Modules.order.Entities;

public enum OrderStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELED,
    SHIPPED,
    CONFIRM,
    PROCESSING,
    DELIVERED;


    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
