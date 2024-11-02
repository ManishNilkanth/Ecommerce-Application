package com.mega.e_commerce_system.Modules.order.Payload;

import com.mega.e_commerce_system.Modules.customer.Payload.CustomerResponse;
import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;

import com.mega.e_commerce_system.Modules.product.Payload.ProductPurchaseResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderConfirmation {

    private String orderReference;

    private BigDecimal totalAmount;

    private PaymentMethod paymentMethod;

    private CustomerResponse customer;

    private List<ProductPurchaseResponse> products;


}
