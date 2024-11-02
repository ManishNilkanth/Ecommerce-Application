package com.mega.e_commerce_system.Modules.payment.Entities;

public enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
