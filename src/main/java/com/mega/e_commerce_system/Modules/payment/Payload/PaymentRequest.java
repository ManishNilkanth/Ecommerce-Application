package com.mega.e_commerce_system.Modules.payment.Payload;

import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotNull(message = "amount is null :: payment amount must be present")
    @Positive(message = "amount is not positive:: payment amount must be positive value")
    private BigDecimal amount;

    @NotNull(message = "Payment method is null :: payment method must be present")
    private PaymentMethod paymentMethod;

    @NotNull(message = "order id is null :: order id must be present")
    private Integer orderId;

    @NotNull(message = "customer is null :: customer must be present")
    CustomerData customer;
}
