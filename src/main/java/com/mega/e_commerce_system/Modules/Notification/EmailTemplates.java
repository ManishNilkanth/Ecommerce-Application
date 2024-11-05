package com.mega.e_commerce_system.Modules.Notification;

import lombok.Getter;

public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment successfully proccessed"),

    ORDER_CONFIRMATION("order-confirmation.html","Order successfully proccessed");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
