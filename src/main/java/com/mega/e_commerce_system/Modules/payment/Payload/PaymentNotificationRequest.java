package com.mega.e_commerce_system.Modules.payment.Payload;

import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentNotificationRequest {

    private String orderReference;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    String customerFirstname;

    String customerLastname;

    String customerEmail;

}
