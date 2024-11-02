package com.mega.e_commerce_system.Modules.order.Payload;

import com.mega.e_commerce_system.Modules.order.Entities.OrderStatus;
import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import com.mega.e_commerce_system.Modules.payment.Payload.CustomerData;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer id;

    private String reference;

    private OrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    private Long customerId;

}
