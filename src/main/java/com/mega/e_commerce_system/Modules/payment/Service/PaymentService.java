package com.mega.e_commerce_system.Modules.payment.Service;

import com.mega.e_commerce_system.Modules.payment.Payload.PaymentRequest;
import com.mega.e_commerce_system.Modules.payment.Payload.PaymentResponse;
import jakarta.mail.MessagingException;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request) throws MessagingException;
}
