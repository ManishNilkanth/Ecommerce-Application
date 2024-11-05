package com.mega.e_commerce_system.Modules.order.Payload;

import com.mega.e_commerce_system.Modules.payment.Entities.PaymentMethod;
import com.mega.e_commerce_system.Modules.payment.Payload.ProductPurchaseRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @Positive(message = "amount should be positive value")
    private BigDecimal amount;

    @NotNull(message = "payment method should be chosen")
    private PaymentMethod paymentMethod;

    @NotNull(message = "customer id should not null")
    private Long customerId;

    @NotEmpty(message = "products should not empty")
    private List<ProductPurchaseRequest> products;
}
