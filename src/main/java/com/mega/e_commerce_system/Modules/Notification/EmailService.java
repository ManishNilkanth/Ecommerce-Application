package com.mega.e_commerce_system.Modules.Notification;

import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import jakarta.mail.MessagingException;

import java.math.BigDecimal;
import java.util.List;

public interface EmailService {

    public void paymentSuccessfulEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference) throws MessagingException;

    public void orderConfirmationEmail( String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<ProductPurchaseResponse> products) throws MessagingException;
}
