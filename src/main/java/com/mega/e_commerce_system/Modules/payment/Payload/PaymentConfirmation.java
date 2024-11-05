package com.mega.e_commerce_system.Modules.payment.Payload;

import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PaymentConfirmation {

    private String orderReference;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private String customerFirstname;

    private String customerLastname;

    private String customerEmail;

}
