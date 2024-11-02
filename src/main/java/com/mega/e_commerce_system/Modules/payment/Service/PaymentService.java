package com.mega.e_commerce_system.Modules.payment.Service;

import com.mega.e_commerce_system.Modules.payment.Entities.Payment;
import com.mega.e_commerce_system.Modules.payment.Payload.PaymentRequest;
import com.mega.e_commerce_system.Modules.payment.Payload.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
}
