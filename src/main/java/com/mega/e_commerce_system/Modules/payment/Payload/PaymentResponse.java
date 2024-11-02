package com.mega.e_commerce_system.Modules.payment.Payload;

import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import com.mega.e_commerce_system.Modules.payment.Entities.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Integer paymentId;

    private Integer orderId;

    private Long customerId;

    private PaymentStatus paymentStatus;

    private PaymentMethod paymentMethod;

    private BigDecimal amount;

}
