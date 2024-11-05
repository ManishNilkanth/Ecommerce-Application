package com.mega.e_commerce_system.Modules.order.Payload;

import com.mega.e_commerce_system.Modules.order.Entities.OrderStatus;
import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import lombok.*;

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
