package com.mega.e_commerce_system.Modules.payment.Payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class ProductPurchaseRequest {
    @NotNull(message = "product should not null")
    private Integer productId;

    @Positive(message = "product quantity should be positive")
    private double quantity;
}
